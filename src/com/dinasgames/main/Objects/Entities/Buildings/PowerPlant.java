package com.dinasgames.main.Objects.Entities.Buildings;

import com.dinasgames.main.Graphics.CircleShape;
import com.dinasgames.main.Graphics.RectangleShape;
import com.dinasgames.main.Math.Point;
import com.dinasgames.main.Math.Vector2f;
import com.dinasgames.main.Objects.GameObjectType;
import com.dinasgames.main.Scenes.Scene;
import java.awt.Color;

public class PowerPlant extends Building{
  
  RectangleShape mShapeBody;
  
  public PowerPlant(Scene scene){
    mScene = scene;
    mShapeBody = null;
    mHealthMax = 1000.f;
    mHealth = mHealthMax;
    
    addToScene();
  }
  
  @Override
  public void onCreate() {
    super.onCreate();
        
    setSize(30.f, 30.f);
    
    mShapeBody = new RectangleShape();
        
    mShapeBody.setFillColor(Color.white);
    mShapeBody.setOutlineColor(Color.black);
    mShapeBody.setOutlineThickness(2.f);
    mShapeBody.setSize(mSize);
    mShapeBody.setOriginCenter();
    mShapeBody.setScene(mScene);
    mShapeBody.setRenderer(mRenderer);
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