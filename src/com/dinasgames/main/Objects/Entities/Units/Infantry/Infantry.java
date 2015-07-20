package com.dinasgames.main.Objects.Entities.Units.Infantry;

import com.dinasgames.main.Objects.Entities.Units.Unit;
import com.dinasgames.main.Objects.GameObjectType;
import com.dinasgames.main.Scenes.Scene;

public class Infantry extends Unit {
  
    public Infantry(){



    }
  
    @Override
    public int getTypeID() {
        return super.getTypeID() | GameObjectType.Infantry.getID();
    }

    @Override
    public String getTypeString() {
        return "Infantry";
    }

    @Override
    protected boolean hasValidReference() {
        return (this.ref() != null);
    }

    private Infantry ref() {
        return (Infantry)Scene.getCurrent().get(mID);
    }

}