package com.dinasgames.main.Players;

import com.dinasgames.main.Controllers.LocalController;
import com.dinasgames.main.Inputs.LocalInput;
import com.dinasgames.main.Math.BoundingBox;
import com.dinasgames.main.Math.Vector2f;
import com.dinasgames.main.Objects.Entities.Entity;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Functions relating to the local player
 * @author Jack
 */
public class LocalPlayer extends Player {
    
    public LocalPlayer() {
        
        // Create a Keyboard & Mouse controller for this player
        mController = new LocalController();
        
        // Setup our selection box
        mSelectionShape.setFillColor(new Color(255,255,255,128));
        mSelectionShape.setOutlineColor(new Color(255,255,255,255));
        mSelectionShape.setOutlineThickness(2.f);
    }
    
    @Override
    public Entity checkIfObject(Vector2f mousePosition){
      
      List<Entity> entityList = mScene.findObjects();

      for (Entity entity : entityList) {
        if((mousePosition.x > entity.getBoundingBox().x 
            && mousePosition.x < entity.getBoundingBox().x + entity.getWidth()) 
            && (mousePosition.y > entity.getBoundingBox().y
            && mousePosition.y < entity.getBoundingBox().y + entity.getHeight())){
          return entity;
        }
      }
      
      return null;
    }
    
    @Override
    public void onSelectObjects(BoundingBox selectionArea) {
        
        if(mScene == null) {
            return;
        }
        
        // Find all the selectable objects (child of Entity)
        List<Entity> entityList = mScene.findObjects();
        
        // Now find the ones that are within our selectionArea
        List<Entity> entitiesInArea = new ArrayList();
        for (Entity entity : entityList) {
            BoundingBox box = entity.getBoundingBox();
            if (selectionArea.contains(box)) {
                // This unit is within our selectionArea
                entitiesInArea.add(entity);
            }
        }
        
        // Now we have the entities within our selection area
        // Next thing is to ensure that this player owns them, otherwise you'd select the enemies units :L
        List<Entity> toSelect = new ArrayList();
        
        Iterator<Entity> it = entitiesInArea.iterator();
        while(it.hasNext()) {
            Entity e = it.next();
            if(e.getOwner() == null) {
                continue;
            }
            if(e.getOwner().getID() == mID) {
                toSelect.add(e);
            }
        }
        
        selectObjects(toSelect);
        
    }
    
    @Override
    public void update() {
        
        super.update();
        
        // Handle local player input
        LocalInput input = new LocalInput(getLocalInput());
        
        
        float speed = 0.5f;
        
        if(input.shift) {
            speed *= 2.f;
        }
        
        if(input.control){
            speed /= 2.f;
        }
        
        if(input.left) {
            mScene.getCamera().move(-speed, 0.f);
        }
        
        if(input.right) {
            mScene.getCamera().move(speed, 0.f);
        }
        
        if(input.up) {
            mScene.getCamera().move(0.f, -speed);
        }
        
        if(input.down) {
            mScene.getCamera().move(0.f, speed);
        }
        
        
        if(isSelectionBoxShowing()) {
            
            updateSelection(input.mousePosition);
            
            // If the mouse isn't being pressed anymore
            if(!input.mousePressed) {
                
                // When holding shift you can add to the current selection
                if(!input.shift) {
                    clearSelection();
                }
                
                // Stop seleting (selection process is in here)
                stopSelection();
                
            }
            
        }else{
            
            // If the user click then start the selection box
            if(input.mousePressed) {
                
              if(checkIfObject(input.mousePosition) != null){
                mEntitySelection.clear();
                mEntitySelection.select(checkIfObject(input.mousePosition));
              }else{
                startSelection(input.mousePosition);
              }
            }
            
        }
        
        if(input.mousePressedR){
          setNewTargetPosition(input.mousePosition);
        }
        
    }
    
    public LocalController getLocalController() {
        return (LocalController)mController;
    }
    
    public LocalInput getLocalInput() {
        return (LocalInput)getLocalController().getInput();
    }
    
}
