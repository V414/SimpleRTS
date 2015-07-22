/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.Graphics;

import com.dinasgames.main.Scenes.Scene;
import java.awt.Graphics2D;

/**
 *
 * @author Jack
 */
public class Renderable {
    
    protected Renderer mRenderer;
    protected Scene mScene;
    protected boolean mIsReference;
    protected boolean mVisible;
    protected int mDepth;
    protected int mID;
    
    Renderable() {
        mVisible = true;
        mDepth = 0;
        mID = -1;
        mIsReference = false;
        mScene = null;
    }
    
    public Renderable setScene(Scene scene) {
        mScene = scene;
        return this;
    }
    
    public Renderable setRenderer(Renderer renderer) {
        mRenderer = renderer;
        if(mID < 0 && renderer != null) {
            mID = renderer.add(this);
        }
        return this;
    }
        
    public Scene getScene() {
        return mScene;
    }
    
    public Renderer getRenderer() {
        return mRenderer;
    }
    
//    public boolean isReference() {
//        return (mIsReference && hasValidReference());
//    }
//    
//    public void makeReference() {
//        mIsReference = true;
//    }
//    
//    protected boolean hasValidReference() {
//        return (ref() != null);
//    }
//
//    private Renderable ref() {
//        return Renderer.getCurrent().get(mID);
//    }

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
        mDepth = depth;
        return this;
    }
    
    public int getDepth() {
        return mDepth;
    }
    
    public boolean isVisible() {
        return mVisible;
    }
    
    public void setVisible(boolean vis) {
        mVisible = vis;
    }
    
    public void hide() {
        mVisible = false;
    }
    
    public void show() {
        mVisible = true;
    }
    
    public void render(Graphics2D g) {
        
//        g.setColor(Color.red);
//        g.fillRect(100, 100, 200,200);
        
    }
    
}
