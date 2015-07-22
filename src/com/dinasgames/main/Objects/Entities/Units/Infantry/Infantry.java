package com.dinasgames.main.Objects.Entities.Units.Infantry;

import com.dinasgames.main.Objects.Entities.Units.Unit;
import com.dinasgames.main.Objects.GameObjectType;

public class Infantry extends Unit {
  

  
    @Override
    public int getTypeID() {
        return super.getTypeID() | GameObjectType.Infantry.getID();
    }

    @Override
    public String getTypeString() {
        return "Infantry";
    }


}