/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.lwjgl.util;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;

/**
 *
 * @author Jack
 */
public class GL {
    
    public static final int version = 20;
    public static GLContext currentContext;
    
    public enum MatrixMode {
        Projection,
        ModelView,
        Texture
    }
    
    public static void setColor(GLColor color) {
        GL11.glColor4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }
    
    public static void setColor(Color color) {
        setColor(color.toGLColor());
    }
    
    public static int clampToEdge() {
        
        if(version >= 12 ) {
            return GL12.GL_CLAMP_TO_EDGE;
        }
        
        if(version >= 11) {
            return GL11.GL_CLAMP;
        }
        
        return -1;
        
    }
    
    public static void clearColor(GLColor color) {
        if(version >= 11) {
            GL11.glClearColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        }
    }
    
    public static void clearColor(Color color) {
        clearColor(color.toGLColor());
    }
    
    public static void setViewport(float x, float y, float width, float height) {
        
        if(version >= 11) {
            GL11.glViewport((int)x, (int)y, (int)width, (int)height);
        }
    }
    
    public static void loadIdentity() {
        if(version >= 11) {
            GL11.glLoadIdentity();
        }
    }
    
    public static void bindTexture(int textureId) {
        if(version >= 11) {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
        }
    }
    
    public static void bindTexture(Texture texture) {
        
        if(texture == null) {
            bindTexture(0);
            return;
        }
        
        bindTexture(texture.getTextureID());
        
    }
    
    public static void setMatrixMode(MatrixMode mode) {

        if(version >= 11) {
            int glMode = -1;
            switch(mode) {
                case Projection: glMode = GL11.GL_PROJECTION; break;
                case ModelView: glMode = GL11.GL_MODELVIEW; break;
                case Texture: glMode = GL11.GL_TEXTURE; break;
            }
            if(glMode != -1) {
                GL11.glMatrixMode(glMode);
            }
        }
    }
    
    public static void blendFactor(int colorSrcFactor, int colorDstFactor, int alphaSrcFactor, int alphaDstFactor) {
        
        // OpenGL 1.4
        if(version >= 14) {
            GL14.glBlendFuncSeparate(   colorSrcFactor, colorDstFactor,
                                        alphaSrcFactor, alphaDstFactor);
            return;
        }
        
        // OpenGL 1.1
        if(version >= 11) {
            GL11.glBlendFunc(   colorSrcFactor, colorDstFactor);
            return;
        }
        
    }
    
    public static void blendEquation(int colorEquation, int alphaEquation) {
        
        if(version >= 20) {
            GL20.glBlendEquationSeparate(   colorEquation, alphaEquation);
            return;
        }
        
        if(version >= 14) {
            GL14.glBlendEquation(colorEquation);
            return;
        }
        
    }
    
    public static void loadMatrix(float[] matrix) {
        
        //if(true){return;}
        
        if(version >= 11) {
            FloatBuffer buffer = BufferUtils.createFloatBuffer(matrix.length);
            for(int i = 0; i < matrix.length; i++) {
                buffer.put(matrix[i]);
            }
            buffer.flip();
            GL11.glLoadMatrixf(buffer);
            return;
        }
        
    }
    
    public static int createVBOID() {
        if(version < 15) {
            return -1;
        }
        IntBuffer buffer = BufferUtils.createIntBuffer(1);
        GL15.glGenBuffers(buffer);
        return buffer.get(0);
    }
    
    public static void bindBuffer(int type, int buffer) {
        if(version < 15) {
            return;
        }
        GL15.glBindBuffer(type, buffer);
    }
    
    public static void vertexBufferData(int id, FloatBuffer buffer) {
        
        if(version < 15) {
            return;
        }
        
        bindBuffer(GL15.GL_ARRAY_BUFFER, id);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        
    }
    
    public static void colorBufferData(int id, FloatBuffer buffer) {
        
        if(version < 15) {
            return;
        }
        
        bindBuffer(GL15.GL_ARRAY_BUFFER, id);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        
    }
    
    public static void indexBufferData(int id, FloatBuffer buffer) {
        
        if(version < 15) {
            return;
        }
        
        bindBuffer(GL15.GL_ARRAY_BUFFER, id);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        
    }
    
    protected static FloatBuffer vertexBuffer, colorBuffer, indexBuffer;
    protected static int vertexBufferID = -1, colorBufferID = -1, indexBufferID = -1;
    
    protected static void init() {
        
        if(vertexBufferID == -1) {
            vertexBufferID = createVBOID();
        }
        
        if(colorBufferID == -1) {
            colorBufferID = createVBOID();
        }
        
        if(indexBufferID == -1) {
            indexBufferID = createVBOID();
        }
        
    }
    
    public static void render(Vertex[] verts) {
        
        init();
        
        int numberIndices = verts.length / 3;
        vertexBuffer = BufferUtils.createFloatBuffer(verts.length * 2);
        colorBuffer = BufferUtils.createFloatBuffer(verts.length);
        indexBuffer = BufferUtils.createFloatBuffer(verts.length);
        
        vertexBuffer.clear();
        colorBuffer.clear();
        indexBuffer.clear();
        
        for(Vertex vert : verts) {
            
            vertexBuffer.put(vert.x);
            vertexBuffer.put(vert.y);
            
            GLColor c = vert.color.toGLColor();
            
            colorBuffer.put(c.getRed());
            colorBuffer.put(c.getGreen());
            colorBuffer.put(c.getBlue());
            colorBuffer.put(c.getAlpha());
            
        }
        
        vertexBufferData( vertexBufferID, vertexBuffer );
        colorBufferData( colorBufferID, colorBuffer );
        
        // Push verticies to OpenGL
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBufferID);
        GL11.glVertexPointer(2, GL11.GL_FLOAT, 0, 0);

        // Push color values to OpenGL
        GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorBufferID);
        GL11.glColorPointer(4, GL11.GL_FLOAT, 0, 0);
        
        // Draw the shape
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBufferID);
        GL11.glDrawElements(GL11.GL_TRIANGLES, numberIndices, GL11.GL_UNSIGNED_INT, 0);
        
        // Disalble client state for vertex and color
        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
        
    }
    
    public static void setContext(GLContext context) {
      currentContext = context;
    }
    
}
