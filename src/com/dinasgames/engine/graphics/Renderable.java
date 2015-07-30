/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.engine.graphics;

import com.dinasgames.main.scenes.Scene;

/**
 *
 * @author Jack
 */
public class Renderable extends Transformable implements Drawable {
    
    protected Renderer mRenderer;
    protected Scene mScene;
    protected boolean mVisible, mGUI;
    protected int mDepth;
    protected int mID;
    
    public Renderable() {
        mVisible = true;
        mDepth = 0;
        mID = -1;
        mScene = null;
        mGUI = false;
    }
    
    public Renderable setGUI(boolean flag) {
      mGUI = flag;
      return this;
    }
    
    public Renderable setScene(Scene scene) {
        mScene = scene;
        return this;
    }
    
    public Renderable render(Renderer renderer) {
        
        // Ensure this is a valid renderer
        if(renderer == null) {
            return this;
        }
        
        // If we are already being rendered. Stop doing so.
        if(mRenderer != null) {
            mRenderer.remove(getID());
        }
        
        mRenderer = renderer;
        mRenderer.add(this);
        
        return this;
        
    }
        
    public Scene getScene() {
        return mScene;
    }
    
    public Renderer getRenderer() {
        return mRenderer;
    }
    
    public boolean inView(View view) {
        return true;
    }
    
    public void remove() {
        if(mID < 0) {
            return;
        }
        if(mRenderer != null) {
            mRenderer.remove(mID);
        }
    }
    
    public Renderable setID(int id) {
        mID = id;
        return this;
    }
    
    public int getID() {
        return mID;
    }
    
    public Renderable setDepth(int depth) {
        if(mDepth != depth) {
            onDepthChange(mDepth, depth);
            mDepth = depth;
        }
        return this;
    }
    
    public int getDepth() {
        return mDepth;
    }
    
    public boolean isVisible() {
        return mVisible;
    }
    
    public void setVisible(boolean vis) {
        if(mVisible != vis) {
            onVisibilityChange(mVisible, vis);
            mVisible = vis;
        }
    }
    
    public void hide() {
        setVisible(false);
    }
    
    public void show() {
        setVisible(true);
    }
    
    // Events
    public void onAdd() {
        
    }
    
    public void onRemove() {
        
    }
    
    public void onDepthChange(int oldValue, int newValue) {
        
    }
    
    public void onVisibilityChange(boolean oldValue, boolean newValue) {
        
    }

    @Override
    public void draw(RenderTarget target, RenderStates states) {
        // Base object doesn't render
    }
    
    /**
     * Check whether this is a GUI element.
     * @return 
     */
    public boolean getGUI() {
      return mGUI;
    }
    
}
