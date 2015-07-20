/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.Players;

import com.dinasgames.main.Controllers.Controller;
import com.dinasgames.main.Graphics.RectangleShape;
import com.dinasgames.main.Math.Vector2f;

/**
 *
 * @author Jack
 */
public class Player {
    
    protected int mID;
    protected boolean mIsReference;
    protected Controller mController;
    
    // Selction box
    protected RectangleShape mSelectionBox;
    protected Vector2f mSelectionBoxStart;
    
    protected Player() {
        
        mID = -1;
        mController = null;
        mIsReference = false;
        
        // Setup selection box
        mSelectionBox = RectangleShape.create();
        mSelectionBox.setDepth(-1000);
        mSelectionBox.setPosition(0.f,0.f);
        mSelectionBox.setSize(0.f,0.f);
        mSelectionBox.hide();
        
        mSelectionBoxStart = new Vector2f(0.f,0.f);
        
    }
    
    public boolean isReference() {
        return (mIsReference && hasValidReference());
    }
    
    public void makeReference() {
        
        mIsReference = true;
        
        // Remove selection box
        mSelectionBox.remove();
        mSelectionBox = null;
        
    }
    
    public boolean isSelectionBoxShowing() {
        if(isReference()) {
            return ref().isSelectionBoxShowing();
        }
        return mSelectionBox.isVisible();
    }
    
    public RectangleShape getSelectionBox() {
        if(isReference()) {
            return ref().getSelectionBox();
        }
        return mSelectionBox;
    }
    
    protected boolean hasValidReference() {
        return (ref() != null);
    }

    private Player ref() {
        return PlayerList.getCurrent().get(mID);
    }
    
    public int getID() {
        return mID;
    }
    
    public void setID(int id) {
        mID = id;
    }
    
    public void update() {
        
        // Update controller
        if(mController != null) {
            mController.update();
        }
        
    }
    
    public void onRemove() {
        
        if(mSelectionBox != null) {
            mSelectionBox.remove();
        }
        
    }
    
    public Controller getController() {
        if(isReference()) {
            return ref().getController();
        }
        return mController;
    }
    
    protected void startSelection(Vector2f mousePosition) {
        
        if(isReference()) {
            ref().startSelection(mousePosition);
            return;
        }
        
        mSelectionBoxStart = new Vector2f(mousePosition);
        mSelectionBox.setPosition(mousePosition);
        mSelectionBox.setSize(0.f,0.f);
        mSelectionBox.show();
        
    }
    
    protected void updateSelection(Vector2f mousePosition) {
        
        if(isReference()) {
            ref().updateSelection(mousePosition);
            return;
        }
        
        // Get the start position
        Vector2f startPosition = new Vector2f(mSelectionBoxStart);
        
        // Ensure the selection box doesn't have a negative size
        float minX = Math.min( startPosition.x, mousePosition.x );
        float minY = Math.min( startPosition.y, mousePosition.y );
        float maxX = Math.max( startPosition.x, mousePosition.x );
        float maxY = Math.max( startPosition.y, mousePosition.y );
        
        // Update the selection box
        mSelectionBox.setPosition(minX, minY);
        mSelectionBox.setSize(maxX - minX, maxY - minY);
        
    }
    
    protected void stopSelection() {
        
        if(isReference()) {
            ref().stopSelection();
            return;
        }
        
        mSelectionBoxStart = new Vector2f(0.f,0.f);
        mSelectionBox.setPosition(0.f, 0.f);
        mSelectionBox.setSize(0.f,0.f);
        mSelectionBox.hide();
        
    }
    
}
