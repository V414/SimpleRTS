/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.Graphics;

import com.dinasgames.main.Camera;
import com.dinasgames.main.Networking.Network;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;

/**
 *
 * @author Jack
 */
public class RectangleShape extends Shape {
    
    Rectangle mRectangle;
    
    public RectangleShape() {
        mRectangle = null;
    }
    
    @Override
    public void recalculate() {
        //mRectangle = new Rectangle((int)mPosition.x, (int)mPosition.y, (int)mSize.x, (int)mSize.y);
        mRectangle = new Rectangle(0, 0, (int)mSize.x, (int)mSize.y);
    }
    
    @Override
    public void render(Graphics2D g) {
        
        if(mRectangle == null) {
            return;
        }
        
        if(mSize.x == 0.f && mSize.y == 0.f) {
            return;
        }
        
        AffineTransform oldTransform = g.getTransform();
        
        // Apply camera transformation
        if(mScene != null && mScene.getCamera() != null) {
            g.translate(-mScene.getCamera().getPosition().x, -mScene.getCamera().getPosition().y);
        }
        
        // Apply frame transformation
        //g.translate(Window.getCurrent().getFrame().getWidth() / 2.0, Window.getCurrent().getFrame().getHeight() / 2.0);
        
        // Apply our position
        g.translate(mPosition.x, mPosition.y);
        
        // Apply our rotation
        g.rotate(Math.toRadians(mRotation), mOrigin.x, mOrigin.y);
        
        // Apply our scale
        g.scale(mScale.x, mScale.y);
        
        // Draw rectangle outline
        if(mOutlineThickness > 0.f && mOutlineColor.getAlpha() > 0) {
            Stroke oldStroke = g.getStroke();
            g.setStroke(new BasicStroke(mOutlineThickness));
            g.setColor(mOutlineColor);
            g.draw(mRectangle);
            g.setStroke(oldStroke);
        }
        
        // Draw rectangle fill
        if(mFillColor.getAlpha() > 0) {
            g.setColor(mFillColor);
            g.fill(mRectangle);
        }        
        
        // Reset transform
        g.setTransform(oldTransform);
        
    }
    
}
