package com.dinasgames.main.Objects.Entities.Units.Vehicles;

import com.dinasgames.main.Objects.Entities.Units.Unit;
import com.dinasgames.main.Objects.GameObjectType;

public class Vehicle extends Unit{
  
    @Override
    public int getTypeID() {
        return super.getTypeID() | GameObjectType.Vehicle.getID();
    }

    @Override
    public String getTypeString() {
        return "Vehicle";
    }
  
}
