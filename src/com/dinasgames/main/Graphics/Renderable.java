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
    
    protected int mDepth;
    protected int mID;
    
    Renderable() {
        mDepth = 0;
        mID = -1;
    }
    
    private Renderable self() {
        return Renderer.getCurrent().get(mID);
    }

    public void remove() {
        if(inRenderQueue()) {
            Renderer.getCurrent().remove(mID);
            mID = -1;
        }
    }
    
    public boolean inRenderQueue() {
        return (mID >= 0);
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
        if(inRenderQueue()) {
            self().setDepth(depth);
        }
        return this;
    }
    
    public int getDepth() {
        if(inRenderQueue()) {
            return self().getDepth();
        }
        return mDepth;
    }
    
    public void render(Graphics2D g) {
        
//        g.setColor(Color.red);
//        g.fillRect(100, 100, 200,200);
        
    }
    
}
