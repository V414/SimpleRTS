/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.Graphics;

import com.dinasgames.main.Math.Vector2f;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author Jack
 */
public class Image extends Shape {
    
    protected BufferedImage mImage;
    
    public Image() {
        mImage = null;
    }
    
    public Image(BufferedImage image) {
        mImage = image;
        mSize = new Vector2f(image.getWidth(), image.getHeight());
    }
    
    public Image setImage(BufferedImage image) {
        mImage = image;
        mSize = new Vector2f(image.getWidth(), image.getHeight());
        return this;
    }
    
    public BufferedImage getImage() {
        return mImage;
    }
    
    @Override
    public void render(Graphics2D g) {
        
        if(mImage == null) {
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
        
        // Actually apply the origin!
        g.translate(-mOrigin.x, -mOrigin.y);
        
        // Apply our rotation
        g.rotate(Math.toRadians(mRotation), mOrigin.x, mOrigin.y);
        
        // Apply our scale
        g.scale(mScale.x, mScale.y);
        
        // Draw the image
        g.drawImage(mImage, null, 0, 0);
        
        // Reset transform
        g.setTransform(oldTransform);
        
    }
    
}
