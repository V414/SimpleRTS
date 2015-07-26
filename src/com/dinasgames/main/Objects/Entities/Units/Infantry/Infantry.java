package com.dinasgames.main.Objects.Entities.Units.Infantry;

import com.dinasgames.main.Objects.Entities.Units.Unit;
import com.dinasgames.main.Objects.GameObjectType;
import com.dinasgames.main.Scenes.Scene;

public class Infantry extends Unit {
  
    public Infantry(Scene scene) {
        super(scene);
    }
  
    @Override
    public int getTypeID() {
        return super.getTypeID() | GameObjectType.Infantry.getID();
    }

    @Override
    public String getTypeString() {
        return "Infantry";
    }


}