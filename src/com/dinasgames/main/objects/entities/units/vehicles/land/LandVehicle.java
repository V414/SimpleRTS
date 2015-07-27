/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.objects.entities.units.vehicles.land;

import com.dinasgames.main.objects.entities.units.vehicles.Vehicle;
import com.dinasgames.main.objects.GameObjectType;
import com.dinasgames.main.scenes.Scene;

/**
 *
 * @author Jack
 */
public class LandVehicle extends Vehicle {
    
    protected LandVehicle(Scene scene) {
        super(scene);
    }
    
    @Override
    public int getTypeID() {
        return super.getTypeID() | GameObjectType.LandVehicle.getID();
    }

    @Override
    public String getTypeString() {
        return "LandVehicle";
    }
    
}
