/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.Players;

import com.dinasgames.main.Controllers.LocalController;

/**
 *
 * @author Jack
 */
public class LocalPlayer extends Player {
    
    public LocalPlayer() {
        mController = new LocalController();
    }
    
    @Override
    public void update() {
        super.update();
    }
    
}
