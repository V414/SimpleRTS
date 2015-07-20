/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.Players;

import com.dinasgames.main.Controllers.LocalController;
import com.dinasgames.main.Inputs.LocalInput;
import com.dinasgames.main.Math.Vector2f;
import com.dinasgames.main.System.Mouse;
import java.awt.Color;

/**
 *
 * @author Jack
 */
public class LocalPlayer extends Player {
    
    protected LocalPlayer() {
        
        // Create a Keyboard & Mouse controller for this player
        mController = new LocalController();
        
        // Setup our selection box
        mSelectionBox.setFillColor(new Color(255,255,255,128));
        mSelectionBox.setOutlineColor(new Color(255,255,255,255));
        mSelectionBox.setOutlineThickness(2.f);
    }
    
    public static LocalPlayer create() {
        
        int id = PlayerList.getCurrent().add(new LocalPlayer());
        
        LocalPlayer ghost = new LocalPlayer();
        ghost.makeReference();
        ghost.setID(id);
        
        return ghost;
        
    }
    
    @Override
    protected boolean hasValidReference() {
        return (ref() != null);
    }

    private LocalPlayer ref() {
        return (LocalPlayer)PlayerList.getCurrent().get(mID);
    }
    
    @Override
    public void update() {
        
        super.update();
        
        // Handle local player input
        LocalInput input = new LocalInput(getLocalInput());
        
        if(mSelectionBox.isVisible()) {
            
            updateSelection(input.mousePosition);
            
            // If the mouse isn't being pressed anymore
            if(!input.mousePressed) {
                
                // TODO: handle selection here!
                
                // Reset
                stopSelection();
                
            }
            
        }else{
            
            // If the user click then start the selection box
            if(input.mousePressed) {
                
                startSelection(input.mousePosition);
                
            }
            
        }
        
    }
    
    public LocalController getLocalController() {
        if(isReference()) {
            return ref().getLocalController();
        }
        return (LocalController)mController;
    }
    
    public LocalInput getLocalInput() {
        if(isReference()) {
            return ref().getLocalInput();
        }
        return (LocalInput)getLocalController().getInput();
    }
    
}
