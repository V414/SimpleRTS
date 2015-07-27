package com.dinasgames.main.objects.entities.buildings;

import com.dinasgames.lwjgl.util.Color;
import com.dinasgames.lwjgl.util.RectangleShape;
import com.dinasgames.main.objects.GameObjectType;
import com.dinasgames.main.scenes.Scene;

public class Warehouse extends Building{
  
  RectangleShape mShapeBody;
  
  public Warehouse(Scene scene){
    
      super(scene);
      
    mShapeBody = null;
    mHealthMax = 1000.f;
    mHealth = mHealthMax;
   
    addToScene();
    
  }
  
  @Override
  public void onCreate() {
    super.onCreate();
        
    setSize(40.f, 60.f);
    
    mShapeBody = new RectangleShape(mSize);
        
    mShapeBody.setFillColor(Color.WHITE);
    mShapeBody.setOutlineColor(Color.BLACK);
    mShapeBody.setOutlineThickness(4.f);
    mShapeBody.setOriginCenter();
    mShapeBody.setScene(mScene);
    mShapeBody.render(mRenderer);
  }
  
  @Override
  public void onNewOwner() {
    mShapeBody.setFillColor(mOwner.getColor());
  }
    
    @Override
    public void onTick(double time) {
      super.onTick(time);
    }
    
    @Override
    public void onRender() {
      super.onRender();

      mShapeBody.setPosition(mPosition);
      mShapeBody.setRotation(mRotation);
    }
  
  @Override
  public int getTypeID() {
      return super.getTypeID() | GameObjectType.PowerPlant.getID();
  }

  @Override
  public String getTypeString() {
      return "PowerPlant";
  }
}