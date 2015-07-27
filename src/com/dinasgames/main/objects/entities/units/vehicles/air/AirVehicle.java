/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.objects.entities.units.vehicles.air;

import com.dinasgames.main.objects.entities.units.vehicles.Vehicle;
import com.dinasgames.main.objects.GameObjectType;
import com.dinasgames.main.scenes.Scene;

/**
 *
 * @author Jack
 */
public class AirVehicle extends Vehicle {
    
    protected AirVehicle(Scene scene) {
        super(scene);
    }
    
    @Override
    public int getTypeID() {
        return super.getTypeID() | GameObjectType.AirVehicle.getID();
    }

    @Override
    public String getTypeString() {
        return "AirVehicle";
    }
    
}
