package com.dinasgames.main.Objects.Entities.Buildings;

import com.dinasgames.main.Objects.Entities.Entity;
import com.dinasgames.main.Objects.GameObjectType;

public class Building extends Entity {
  
  public Building(){
    
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