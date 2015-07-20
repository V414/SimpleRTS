/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.Graphics;

import java.awt.Graphics2D;

/**
 *
 * @author Jack
 */
public class Renderable {
    
    protected boolean mIsReference;
    protected boolean mVisible;
    protected int mDepth;
    protected int mID;
    
    Renderable() {
        mVisible = true;
        mDepth = 0;
        mID = -1;
        mIsReference = false;
    }
    
    public boolean isReference() {
        return (mIsReference && hasValidReference());
    }
    
    public void makeReference() {
        mIsReference = true;
    }
    
    protected boolean hasValidReference() {
        return (ref() != null);
    }

    private Renderable ref() {
        return Renderer.getCurrent().get(mID);
    }

    public void remove() {
        if(mID < 0) {
            return;
        }
        Renderer.getCurrent().remove(mID);
        mID = -1;
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
        if(isReference()) {
            ref().setDepth(depth);
        }
        return this;
    }
    
    public int getDepth() {
        if(isReference()) {
            return ref().getDepth();
        }
        return mDepth;
    }
    
    public boolean isVisible() {
        if(isReference()) {
            return ref().isVisible();
        }
        return mVisible;
    }
    
    public void setVisible(boolean vis) {
        if(isReference()) {
            ref().setVisible(vis);
            return;
        }
        mVisible = vis;
    }
    
    public void hide() {
        if(isReference()) {
            ref().hide();
            return;
        }
        mVisible = false;
    }
    
    public void show() {
        if(isReference()) {
            ref().show();
            return;
        }
        mVisible = true;
    }
    
    public void render(Graphics2D g) {
        
//        g.setColor(Color.red);
//        g.fillRect(100, 100, 200,200);
        
    }
    
    public Renderable getReference() {
        Renderable r = new Renderable();
        r.makeReference();
        r.setID(mID);
        return r;
    }
    
}
