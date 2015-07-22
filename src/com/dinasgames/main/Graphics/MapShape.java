/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.Graphics;

import com.dinasgames.main.Maps.Map;
import com.dinasgames.main.Maps.Map.MapChunk;
import com.dinasgames.main.Math.Vector2f;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author Jack
 */
public class MapShape extends Shape {
    
    protected Map mMap;
    
    public MapShape() {
        mMap = null;
    }
    
    public MapShape(Map map) {
        mMap = map;
    }
    
    public MapShape setMap(Map map) {
        mMap = map;
        return this;
    }
    
    public Map getMap() {
        return mMap;
    }
    
    @Override
    public void render(Graphics2D g) {
        
        if(mMap == null) {
            return;
        }
        
//        if(mSize.x == 0.f && mSize.y == 0.f) {
//            return;
//        }
        
        AffineTransform oldTransform = g.getTransform();
        
        // Apply camera transformation
        if(mScene != null && mScene.getCamera() != null) {
            g.translate(-mScene.getCamera().getPosition().x, -mScene.getCamera().getPosition().y);
        }
        
        // Apply frame transformation
        //g.translate(Window.getCurrent().getFrame().getWidth() / 2.0, Window.getCurrent().getFrame().getHeight() / 2.0);
        
        // Apply our position
        g.translate(mPosition.x, mPosition.y);
        
        // Actually apply the origin!
        g.translate(-mOrigin.x, -mOrigin.y);
        
        // Apply our rotation
        g.rotate(Math.toRadians(mRotation), mOrigin.x, mOrigin.y);
        
        // Apply our scale
        g.scale(mScale.x, mScale.y);
        
        // Draw the image
        //g.drawImage(mImage, null, 0, 0);
        int chunkSize = mMap.getChunkSize();
        for(int x = 0; x < mMap.getWidth(); x++) {
            for(int y = 0; y < mMap.getHeight(); y++) {
                MapChunk chunk = mMap.getChunkAt(x,y);
                if(chunk == null) {
                    continue;
                }
                g.drawImage(chunk.mImage, null, chunk.mChunkX * chunkSize, chunk.mChunkY * chunkSize);
            }
        }
        
        // Reset transform
        g.setTransform(oldTransform);
        
    }
    
}