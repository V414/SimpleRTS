/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.net;

import com.dinasgames.main.system.Time;

/**
 *
 * @author Jack
 */
public class PhysicsObject extends GameObject {
    
    protected float mVelocityX, mVelocityY, mAccelerationRate, mDecelerationRate;
    
    @Override
    public void onTick( Time timePassed ) {
        this.x += mVelocityX;
        this.y += mVelocityY;
    }
    
}
