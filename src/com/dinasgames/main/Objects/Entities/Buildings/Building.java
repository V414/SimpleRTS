package com.dinasgames.main.Objects.Entities.Buildings;

import com.dinasgames.main.Objects.Entities.Entity;
import com.dinasgames.main.Objects.GameObjectType;
import com.dinasgames.main.Scenes.Scene;

public class Building extends Entity {
  
  protected Building(){
    
  }
  
  protected Building(Scene scene) {
      super(scene);
  }
  
  @Override
  protected void recalculateBoundingBox() {
    super.recalculateBoundingBox();
    mBoundingBox.setSize(mSize);
  }
  
  @Override
  public int getTypeID() {
      return super.getTypeID() | GameObjectType.Building.getID();
  }

  @Override
  public String getTypeString() {
      return "Building";
  }
  
}