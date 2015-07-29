/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.lwjgl.util;

import com.dinasgames.main.math.BoundingBox;
import com.dinasgames.main.math.Vector2f;
import com.dinasgames.main.math.Vector2i;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Jack
 */
public class RenderTarget {
    
    protected class StatusCache {
    
        public static final int VertexCacheSize = 4;
        
        public boolean      glStatesSet;
        public boolean      viewChanged;    ///< Has the current view changed since last draw?
        public BlendMode    lastBlendMode;  ///< Cached blending mode
        public boolean      useVertexCache; ///< Did we previously use the vertex cache?
        public Vertex       vertexCache[]; ///< Pre-transformed vertices cache // VertexCacheSize
        public int          lastTextureId;
        
        public StatusCache() {
            vertexCache = new Vertex[VertexCacheSize];
            for(int i = 0; i < VertexCacheSize; i ++) {
                vertexCache[i] = new Vertex();
            }
        }
    
    }
    
    protected View          mDefaultView;
    protected View          mView;
    protected StatusCache   mCache;
    
    public int factorToGlConstant(BlendMode.Factor blendFactor) {
        
        switch (blendFactor)
        {
            case Zero:             return GL11.GL_ZERO;
            case One:              return GL11.GL_ONE;
            case SrcColor:         return GL11.GL_SRC_COLOR;
            case OneMinusSrcColor: return GL11.GL_ONE_MINUS_SRC_COLOR;
            case DstColor:         return GL11.GL_DST_COLOR;
            case OneMinusDstColor: return GL11.GL_ONE_MINUS_DST_COLOR;
            case SrcAlpha:         return GL11.GL_SRC_ALPHA;
            case OneMinusSrcAlpha: return GL11.GL_ONE_MINUS_SRC_ALPHA;
            case DstAlpha:         return GL11.GL_DST_ALPHA;
            case OneMinusDstAlpha: return GL11.GL_ONE_MINUS_DST_ALPHA;
        }
        
        return -1;
        
    }
    
    public Vector2i getSize() {
        return new Vector2i();
    }
    
    public int getWidth() {
        return 0;
    }
    
    public int getHeight() {
        return 0;
    }
    
    public int equationToGlConstant(BlendMode.Equation blendEquation) {
        
        switch (blendEquation)
        {
            case Add:             return GL11.GL_ADD;
            case Subtract:        return GL13.GL_SUBTRACT;
        }
        
        return -1;
        
    }
    
    public RenderTarget() {
        mDefaultView = new View();
        mView = new View();
        mCache = new StatusCache();
        mCache.glStatesSet = false;
        mCache.lastTextureId = 0;
    }
    
    public boolean activate(boolean active) {
        return false;
    }
    
    public RenderTarget clear(Color color) {
        if(activate(true)) {
            applyTexture(null);
            GL.clearColor(color);
        }
        return this;
    }
    
    public RenderTarget setView(View view) {
        mView = view;
        mCache.viewChanged = true;
        return this;
    }
    
    public View getView() {
        return mView;
    }
    
    public View getDefaultView() {
        return mDefaultView;
    }
    
    public BoundingBox getViewport(View view) {
        
        float width = (float)getSize().x;
        float height = (float)getSize().y;
        BoundingBox viewport = view.getViewport();
        
        return new BoundingBox( 0.5f + width * viewport.x,
                                0.5f + height  * viewport.y,
                                0.5f + width * viewport.width,
                                0.5f + height  * viewport.height);
        
    }
    
    public Vector2f mapPixelToCoords(Vector2f point) {
        return mapPixelToCoords(point, getView());
    }
    
    public Vector2f mapPixelToCoords(Vector2f point, View view) {
        
        Vector2f normalized = new Vector2f();
        BoundingBox viewport = getViewport(view);
        normalized.x = -1.f + 2.f * (point.x - viewport.x) / viewport.width;
        normalized.y = 1.f - 2.f * (point.y - viewport.y)  / viewport.height;
        
        return view.getInverseTransform().transformPoint(normalized);
        
    }
    
    public Vector2f mapCoordsToPixel(Vector2f point) {
        return mapCoordsToPixel(point, getView());
    }
    
    public Vector2f mapCoordsToPixel(Vector2f point, View view) {
        
         // First, transform the point by the view matrix
        Vector2f normalized = view.getTransform().transformPoint(point);

        // Then convert to viewport coordinates
        Vector2f pixel = new Vector2f();
        BoundingBox viewport = getViewport(view);
        pixel.x = (( normalized.x + 1.f) / 2.f * viewport.width  + viewport.x);
        pixel.y = ((-normalized.y + 1.f) / 2.f * viewport.height + viewport.y);

        return pixel;
        
    }
    
    public void draw(Drawable drawable) {
        draw(drawable, RenderStates.getDefault());
    }
    
    public void draw(Drawable drawable, RenderStates states) {
        drawable.draw(this, states);
    }
    
    public void draw(VertexArray verts, RenderStates states) {
        draw(verts.toArray(), verts.getPrimitiveType(), states);
    }
    
    public void draw(VertexCache verts, RenderStates states) {
        draw(verts, verts.getType(), states);
    }
    
    public void draw(VertexCache verts, PrimitiveType type, RenderStates states) {
        
        if(verts == null) {
            return;
        }
        
        if(verts.getSize() <= 0) {
            return;
        }
        
        FloatBuffer v, c, t;
        v = verts.getVertexBuffer();
        c = verts.getColorBuffer();
        t = verts.getTexcoordsBuffer();
        
        if(v == null || c == null || t == null) {
            return;
        }
        
        //if(true) {return;}
        
        if(!activate(true)) {
            return;
        }
        
        if(!mCache.glStatesSet) {
            resetGLStates();
        }
        
        applyTransform(states.transform);
        
        if(mCache.viewChanged) {
            applyCurrentView();
        }

        if(mCache.lastBlendMode != states.blendMode) {
            applyBlendMode(states.blendMode);
        }
        
        int textureId = (states.texture != null ? states.texture.getTextureID() : 0);
        if(textureId != mCache.lastTextureId) {
            applyTexture(states.texture);
        }
        
//        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
//        GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);                
//        GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);                
        
        // Give the buffers to OpenGL
        GL11.glVertexPointer(2, 0, v);
        GL11.glColorPointer(4, 0, c);
        GL11.glTexCoordPointer(2, 0, t);
        
        int mode = -1;
            
        switch(type) {
            case Points:         mode = GL11.GL_POINTS;   break;
            case Lines:          mode = GL11.GL_LINES;   break;
            case LinesStrip:     mode = GL11.GL_LINE_STRIP;   break;
            case Triangles:      mode = GL11.GL_TRIANGLES;   break;
            case TrianglesStrip: mode = GL11.GL_TRIANGLE_STRIP;   break;
            case TrianglesFan:   mode = GL11.GL_TRIANGLE_FAN;   break;
            case Quads:          mode = GL11.GL_QUADS;   break;
        }

        if(mode == -1) {
            return;
        }

        // Draw the verticies
        GL11.glDrawArrays(mode, 0, verts.getSize());
        
    }
    
    public void draw(Vertex[] verts, PrimitiveType type, RenderStates states) {
        
        if(verts == null) {
            return;
        }
        
        if(verts.length == 0) {
            return;
        }
        
        if(activate(true)) {
            
            if(!mCache.glStatesSet) {
                resetGLStates();
            }
            
            boolean useVertexCache = (verts.length <= StatusCache.VertexCacheSize);
            if(useVertexCache) {
                
                for(int i = 0; i < verts.length; i++) {
                    
                    Vertex v = mCache.vertexCache[i];
                    
                    Vector2f p = Transform.multiply(states.transform, new Vector2f(verts[i].x, verts[i].y));
                    
                    v.x = p.x;
                    v.y = p.y;
                    
                    v.color = verts[i].color;
                    v.tx = verts[i].tx;
                    v.ty = verts[i].ty;
                    
                    
                }
                
                if(!mCache.useVertexCache) {
                    applyTransform(Transform.Identity);
                }
                
            }else{
                applyTransform(states.transform);
            }
            
            if(mCache.viewChanged) {
                applyCurrentView();
            }
            
            if(!mCache.lastBlendMode.equals(states.blendMode)) {
                applyBlendMode(states.blendMode);
            }
            
            int textureId = (states.texture != null ? states.texture.getTextureID() : 0);
            if(textureId != mCache.lastTextureId) {
                applyTexture(states.texture);
            }
            
            if(useVertexCache) {
                if(!mCache.useVertexCache) {
                    verts = new Vertex[mCache.vertexCache.length];
                    for(int i = 0; i < mCache.vertexCache.length; i++) {
                        verts[i] = new Vertex(mCache.vertexCache[i]);
                    }
                }else{
                    //verts = null;
                    return;
                }
            }
            
            if(verts != null) {
                
                // Create some buffers to store vertex and color data
                FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(verts.length * 2);
                FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(verts.length * 4);
                FloatBuffer tcBuffer = BufferUtils.createFloatBuffer(verts.length * 2);

                // Copy the vertex and color data to the buffers
                for(Vertex vert : verts) {
                    
                    // Write vertex coordinate (XY)
                    vertexBuffer.put(vert.x);
                    vertexBuffer.put(vert.y);
                    
                    tcBuffer.put(vert.tx);
                    tcBuffer.put(vert.ty);
                    
                    // Write Color (RGBA)
                    GLColor c = vert.color.toGLColor();
                    colorBuffer.put(c.getRed());
                    colorBuffer.put(c.getGreen());
                    colorBuffer.put(c.getBlue());
                    colorBuffer.put(c.getAlpha());
                    
                }
                
                // Flip the buffer so it can be used
                vertexBuffer.flip();
                colorBuffer.flip();
                tcBuffer.flip();
                
                GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
                GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
                GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
                
                // Give the buffers to OpenGL
                GL11.glVertexPointer(2, 0, vertexBuffer);
                GL11.glColorPointer(4, 0, colorBuffer);
                GL11.glTexCoordPointer(2, 0, tcBuffer);

            }
            
            int mode = -1;
            
            switch(type) {
                case Points:         mode = GL11.GL_POINTS;   break;
                case Lines:          mode = GL11.GL_LINES;   break;
                case LinesStrip:     mode = GL11.GL_LINE_STRIP;   break;
                case Triangles:      mode = GL11.GL_TRIANGLES;   break;
                case TrianglesStrip: mode = GL11.GL_TRIANGLE_STRIP;   break;
                case TrianglesFan:   mode = GL11.GL_TRIANGLE_FAN;   break;
                case Quads:          mode = GL11.GL_QUADS;   break;
            }
            
            if(mode == -1) {
                return;
            }
            
            if(verts != null) {
                GL11.glDrawArrays(mode, 0, verts.length);
            }
            
            mCache.useVertexCache = useVertexCache;
            
        }
        
    }
    
    public void draw(Text text, RenderStates states) {
      
      // Invalid text object?
      if(text == null) {
          return;
      }

      // Get some info from the text object
      Font font = text.getFont();
      String str = text.getText();
      Color color = text.getColor();
      int characterSize = text.getCharacterSize();
      int style = java.awt.Font.BOLD;

      // No font? can't draw.
      if(font == null) {
        return;
      }

      // No text to draw?
      if(str.length() <= 0) {
          return;
      }

      // Small text?
      if(characterSize <= 0) {
        return;
      }

      // Activate this context
      if(!activate(true)) {
          return;
      }

      // Reset the GL states if required
      if(!mCache.glStatesSet) {
          resetGLStates();
      }

      // Apply the text transform
      applyTransform(states.transform);

      // Ensure the view is up to date
      if(mCache.viewChanged) {
          applyCurrentView();
      }

      // Apply the blend mode
      if(!mCache.lastBlendMode.equals(states.blendMode)) {
          applyBlendMode(states.blendMode);
      }

      // Get the font texture
      TrueTypeFont t = font.getFont(characterSize, style);
      states.texture = new Texture(t.getTexture());

      // Apply the font texture
      int textureId = (states.texture != null ? states.texture.getTextureID() : 0);
      if(textureId != mCache.lastTextureId) {
          applyTexture(states.texture);
      }
      
      // Draw the text
      t.drawString( 0, 0, str, color.toSlickColor() );
      
    }
    
    public void pushGLStates() {
        
        //if(true){return;}
        
        if(activate(true)) {
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glPushMatrix();
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glPushMatrix();
            GL11.glMatrixMode(GL11.GL_TEXTURE);
            GL11.glPushMatrix();
        }
        resetGLStates();
        
    }
    
    public void popGLStates() {
        
        //if(true){return;}
        
        if(activate(true)) {
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glPopMatrix();
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glPopMatrix();
            GL11.glMatrixMode(GL11.GL_TEXTURE);
            GL11.glPopMatrix();
        }
        
    }
    
    public void resetGLStates() {
        
        //if(true){return;}
        
        if(activate(true)) {
            //GL11.glShadeModel(GL11.GL_SMOOTH);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
            GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
            GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
            mCache.glStatesSet = true;

            applyBlendMode(BlendMode.BlendAlpha);
            applyTransform(Transform.Identity);
            applyTexture(null);
            
            mCache.useVertexCache = false;
            
            setView(new View(getView()));
            
        }
    }
    
    protected void initialize() {
        mDefaultView.reset( 0.f, 0.f, getSize().x, getSize().y );
        mView = new View(mDefaultView);
        mCache.glStatesSet = false;
    }
    
    protected void applyCurrentView() {
        
        //if(true){return;}
        
        // Set the viewport
        BoundingBox viewport = getViewport(mView);
        float top = getSize().y - (viewport.y + viewport.height);
        GL.setViewport(viewport.x, top, viewport.width, viewport.height);
        
        // Set the projection matrix
        GL.setMatrixMode(GL.MatrixMode.Projection);
        GL.loadMatrix(mView.getTransform().getMatrix());
        
        GL.setMatrixMode(GL.MatrixMode.ModelView);
        
        mCache.viewChanged = false;
        
    }
    
    protected void applyBlendMode(BlendMode mode) {
        
        GL.blendFactor(factorToGlConstant(mode.colorSrcFactor),
                                    factorToGlConstant(mode.colorDstFactor),
                                    factorToGlConstant(mode.alphaSrcFactor),
                                    factorToGlConstant(mode.alphaDstFactor));
        
        GL.blendEquation(   equationToGlConstant(mode.colorEquation),
                            equationToGlConstant(mode.alphaEquation));
        
        mCache.lastBlendMode = mode;
        
    }
    
    protected void applyTransform(Transform transform) {

        //if(true){return;}
        
        GL.loadMatrix(transform.getMatrix());
        
    }
    
    protected void applyTexture(Texture texture) {
        
        Texture.bind(texture, Texture.CoordinateType.Pixels);
        
        mCache.lastTextureId = (texture != null ? texture.getTextureID() : 0);
        
    }
    
    
    
}
