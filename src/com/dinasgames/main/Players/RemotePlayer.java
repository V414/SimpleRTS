/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.Players;

import com.dinasgames.main.Controllers.RemoteController;

/**
 *
 * @author Jack
 */
public class RemotePlayer extends Player {
    
    public RemotePlayer() {
        mController = new RemoteController();
    }
    
    @Override
    public void update() {
        super.update();
    }
    
}
