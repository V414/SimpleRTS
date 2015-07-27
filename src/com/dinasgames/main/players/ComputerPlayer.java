/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.players;

import com.dinasgames.main.controllers.ComputerController;

/**
 *
 * @author Jack
 */
public class ComputerPlayer extends Player {
 
    public ComputerPlayer() {
        mController = new ComputerController();
    }
    
    @Override
    public void update() {
        super.update();
    }
    
}
