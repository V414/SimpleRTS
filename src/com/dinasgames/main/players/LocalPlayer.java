package com.dinasgames.main.players;

import com.dinasgames.lwjgl.util.Color;
import com.dinasgames.lwjgl.util.View;
import com.dinasgames.main.controllers.LocalController;
import com.dinasgames.main.games.LocalGame;
import com.dinasgames.main.inputs.LocalInput;
import com.dinasgames.main.math.BoundingBox;
import com.dinasgames.main.math.Vector2f;
import com.dinasgames.main.math.Vector2i;
import com.dinasgames.main.objects.entities.Entity;
import com.dinasgames.main.system.Time;
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
    public void update(Time timePassed) {
        
        super.update(timePassed);
        
        // Handle local player input
        View currentView = mScene.getView();
        LocalInput previousInput = new LocalInput(getPreviousLocalInput());
        LocalInput input = new LocalInput(getLocalInput());
        LocalGame localGame = null;
        
        try {
            localGame = (LocalGame)mScene.getGame();
        } catch( Exception e ) {
            // Ignore
        }
        
        // Camera movement via keyboard keys
        float speed = 800.f * timePassed.asSeconds();
        
        if(input.shift) {
            speed *= 2.f;
        }
        
        if(input.control){
            speed /= 2.f;
        }
        
        if(input.left) {
            currentView.move(-speed, 0.f);
        }
        
        if(input.right) {
            currentView.move(speed, 0.f);
        }
        
        if(input.up) {
            currentView.move(0.f, -speed);
        }
        
        if(input.down) {
            currentView.move(0.f, speed);
        }
        
        // Camera movement via mouse being near the edge of the screen
        if(localGame != null && localGame.getWindow() != null) {
            
            float nearBorder    = 20.f; // <<< Pixels away from border that causes the view to move in this direction
            float moveSpeed     = 1000.f * timePassed.asSeconds(); // <<< Pixels that the camera will move when the mouse touches a border
            Vector2i windowSize = localGame.getWindow().getSize();
            Vector2f uiMousePos = new Vector2f(input.mousePosition)
                                  .subtract(currentView.getPosition());
            
            // Left border
            if( uiMousePos.x <= nearBorder ) {
                currentView.move( -moveSpeed, 0.f );
            }
            
            // Right border
            if( uiMousePos.x >= windowSize.x - nearBorder ) {
                currentView.move( moveSpeed, 0.f );
            }
            
            // Top border
            if( uiMousePos.y <= nearBorder ) {
                currentView.move( 0.f, -moveSpeed );
            }
            
            // Bottom border
            if( uiMousePos.y >= windowSize.y - nearBorder ) {
                currentView.move( 0.f, moveSpeed );
            }            
            
        }
        
        // Handle selection box
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
        
        if(input.mousePressedR && !previousInput.mousePressedR){
          setNewTargetPosition(input.mousePosition);
        }
        
        // Apply new view
        mScene.setView(currentView);
        
    }
    
    public LocalController getLocalController() {
        return (LocalController)mController;
    }
    
    public LocalInput getLocalInput() {
        return (LocalInput)getLocalController().getInput();
    }
    
    public LocalInput getPreviousLocalInput() {
        return (LocalInput)getLocalController().getPreviousInput();
    }
    
}
