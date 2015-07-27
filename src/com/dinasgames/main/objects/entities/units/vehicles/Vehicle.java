package com.dinasgames.main.objects.entities.units.vehicles;

import com.dinasgames.main.objects.entities.units.Unit;
import com.dinasgames.main.objects.GameObjectType;
import com.dinasgames.main.scenes.Scene;

public class Vehicle extends Unit{
  
    protected Vehicle(Scene scene) {
        super(scene);
    }
    
    @Override
    public int getTypeID() {
        return super.getTypeID() | GameObjectType.Vehicle.getID();
    }

    @Override
    public String getTypeString() {
        return "Vehicle";
    }
  
}
