package com.dinasgames.main.Objects.Entities.Units.Vehicles;

import com.dinasgames.main.Objects.Entities.Units.Unit;
import com.dinasgames.main.Objects.GameObjectType;
import com.dinasgames.main.Scenes.Scene;

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
