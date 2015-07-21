package com.dinasgames.main.Graphics;

import com.dinasgames.main.Camera;
import com.dinasgames.main.Networking.Network;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

public class CircleShape extends Shape{
  
  Ellipse2D mCircle;
    
    protected CircleShape() {
        mCircle = null;
    }
    
    public static CircleShape create() {
        
        if(Network.isServer()) {
            return new CircleShape();
        }
        
        // Add a rectangle shape to the render queue
        int id = Renderer.getCurrent().add(new CircleShape());
        
        // Create a ghost
        CircleShape ghost = new CircleShape();
        ghost.makeReference();
        ghost.setID(id);
        
        return ghost;
        
    }
    
    @Override
    protected boolean hasValidReference() {
        return (ref() != null);
    }

    private CircleShape ref() {
        return (CircleShape)Renderer.getCurrent().get(mID);
    }
    
    @Override
    public void recalculate() {
        //mRectangle = new Rectangle((int)mPosition.x, (int)mPosition.y, (int)mSize.x, (int)mSize.y);
        mCircle = new Ellipse2D.Float(0, 0, (int)mSize.x, (int)mSize.y);
    }
    
    @Override
    public void render(Graphics2D g) {
        
        if(mCircle == null) {
            return;
        }
        
        if(mSize.x == 0.f && mSize.y == 0.f) {
            return;
        }
        
        AffineTransform oldTransform = g.getTransform();
        
        // Apply camera transformation
        g.translate(-Camera.getCurrent().getPosition().x, -Camera.getCurrent().getPosition().y);
        
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
            g.draw(mCircle);
            g.setStroke(oldStroke);
        }
        
        // Draw rectangle fill
        if(mFillColor.getAlpha() > 0) {
            g.setColor(mFillColor);
            g.fill(mCircle);
        }        
        
        // Reset transform
        g.setTransform(oldTransform);
        
    }
}