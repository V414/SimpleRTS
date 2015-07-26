package com.dinasgames.lwjgl.util;

/**
 * A class used to render a list to a window.
 * @author Jack
 */
public class Renderer {
    
    public final int MAX_RENDER_OBJECTS = 1024;
    
    protected long mDrawCount;
    protected boolean[] mRenderFlag;
    protected Renderable[] mRenderObjects;

    public Renderer() {
        
        mRenderObjects  = new Renderable[MAX_RENDER_OBJECTS];
        mRenderFlag     = new boolean[MAX_RENDER_OBJECTS];
        mDrawCount      = 0;
        
        // Init render list
        for(int i = 0; i < MAX_RENDER_OBJECTS; i ++) {
            mRenderObjects[i] = null;
            mRenderFlag[i] = false;
        }
        
       
        
    }
    
    public Renderer render(RenderTarget target, RenderStates states) {

        // Get the current render target view
        View view = target.getView();
        
        // Render objects
        mDrawCount = 0;
            
        // Reset render flags
        synchronized(mRenderFlag) {
            for(int i = 0; i < MAX_RENDER_OBJECTS; i ++) {
                mRenderFlag[i] = false;
            }
        }

        // Draw shapes in order of highest to lowest (negative is toward the screen)
        while(true) {

            int renderObject = -1;
            int highestDepth  = 0;

            // Find the render object with the lowest depth
            for(int i = 0; i < MAX_RENDER_OBJECTS; i++) {
                
                // Make sure this render object exists
                if(mRenderObjects[i] == null) {
                    continue;
                }
                
                // Make sure it hasn't already been rendered
                if(mRenderFlag[i] == true) {
                    continue;
                }
                
                // Make sure it is visible
                if(!mRenderObjects[i].isVisible()) {
                    continue;
                }
                
                // Make sure it is within the view specified
                if( (view != null && !mRenderObjects[i].inView(view) ) ) {
                    continue;
                }
                
                if(renderObject == -1) {
                    // We haven't had an object yet, make this the render object
                    renderObject = i;
                    highestDepth = mRenderObjects[i].getDepth();
                }else{
                    // We have had an object, check if this one needs to be drawn first
                    if(mRenderObjects[i].getDepth() > highestDepth) {
                        renderObject = i;
                        highestDepth = mRenderObjects[i].getDepth();    
                    }
                }
                
            }

            // Check if all the objects have been drawn
            if(renderObject == -1) {
                break;
            }

            // Draw it
            mRenderObjects[renderObject].draw(target, new RenderStates(states));
            mDrawCount++;

            // Flag it as being rendered
            mRenderFlag[renderObject] = true;

        }
        
        return this;
        
    }
    
    public Renderer render(RenderTarget target) {
        return render(target, RenderStates.getDefault());
    }
    
    public Renderer clear() {
        for(int i = 0; i < MAX_RENDER_OBJECTS; i++) {
            mRenderObjects[i] = null;
        }
        return this;
    }
    
    public int add(Renderable r) {
        for(int i = 0; i < MAX_RENDER_OBJECTS; i++) {
            if(mRenderObjects[i] == null) {
                
                mRenderObjects[i] = r;
                r.onAdd();
                
                return i;
                
            }
        }
        return -1;
    }
    
    public Renderer remove(int idx) {
        if(idx < 0 || idx >= MAX_RENDER_OBJECTS) {
            return this;
        }
        if(mRenderObjects[idx] != null) {
            mRenderObjects[idx].onRemove();
        }
        mRenderObjects[idx] = null;
        return this;
    }
    
    public Renderable get(int idx) {
        if(idx < 0 || idx >= MAX_RENDER_OBJECTS) {
            return null;
        }
        return mRenderObjects[idx];
    }
    
    public long getDrawCount() {
        return mDrawCount;
    }
    
}
