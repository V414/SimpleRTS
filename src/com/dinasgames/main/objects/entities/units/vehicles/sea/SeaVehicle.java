/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.objects.entities.units.vehicles.sea;

import com.dinasgames.main.objects.entities.units.vehicles.Vehicle;
import com.dinasgames.main.objects.GameObjectType;
import com.dinasgames.main.scenes.Scene;

/**
 *
 * @author Jack
 */
public class SeaVehicle extends Vehicle {
    
    protected SeaVehicle(Scene scene) {
        super(scene);
    }
    
    @Override
    public int getTypeID() {
        return super.getTypeID() | GameObjectType.SeaVehicle.getID();
    }

    @Override
    public String getTypeString() {
        return "SeaVehicle";
    }
    
}
