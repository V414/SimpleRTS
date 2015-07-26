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
  CircleShape mCoolingTower1;
  CircleShape mCoolingTowerInside1;
  CircleShape mCoolingTower2;
  CircleShape mCoolingTowerInside2;
  RectangleShape mShapeConnector;
  
  public PowerPlant(Scene scene){
    mScene = scene;
    mShapeBody = null;
    mCoolingTower1 = null;
    mCoolingTower2 = null;
    mCoolingTowerInside1 = null;
    mCoolingTowerInside2 = null;
    mShapeConnector = null;
    mHealthMax = 1000.f;
    mHealth = mHealthMax;
    
    addToScene();
  }
  
  @Override
  public void onCreate() {
    super.onCreate();
        
    setSize(50.f, 30.f);
    
    mShapeBody = new RectangleShape();
        
    mShapeBody.setFillColor(Color.black);
    mShapeBody.setOutlineColor(Color.black);
    mShapeBody.setOutlineThickness(2.f);
    mShapeBody.setSize(mSize);
    mShapeBody.setOriginCenter();
    mShapeBody.setScene(mScene);
    mShapeBody.setRenderer(mRenderer);
    
    mShapeConnector = new RectangleShape();
        
    mShapeConnector.setFillColor(Color.white);
    mShapeConnector.setOutlineColor(Color.black);
    mShapeConnector.setOutlineThickness(2.f);
    mShapeConnector.setSize(mSize.x/5, mSize.y/2);
    mShapeConnector.setOriginCenter();
    mShapeConnector.setScene(mScene);
    mShapeConnector.setRenderer(mRenderer);
    
    Vector2f ctSize = new Vector2f((mSize.x/5)*2.2f, (mSize.x/5)*2.2f);
    Vector2f ctiSize = new Vector2f((mSize.x/5)*1.5f, (mSize.x/5)*1.5f);
    
    mCoolingTower1 = new CircleShape();
        
    mCoolingTower1.setFillColor(Color.white);
    mCoolingTower1.setOutlineColor(Color.black);
    mCoolingTower1.setOutlineThickness(2.f);
    mCoolingTower1.setSize(ctSize);
    mCoolingTower1.setOrigin(ctSize.x/2+mSize.x/4, ctSize.y/2);
    mCoolingTower1.setScene(mScene);
    mCoolingTower1.setRenderer(mRenderer);
    
    mCoolingTower2 = new CircleShape();
        
    mCoolingTower2.setFillColor(Color.white);
    mCoolingTower2.setOutlineColor(Color.black);
    mCoolingTower2.setOutlineThickness(2.f);
    mCoolingTower2.setSize(ctSize);
    mCoolingTower2.setOrigin(ctSize.x/2-mSize.x/4, ctSize.y/2);
    mCoolingTower2.setScene(mScene);
    mCoolingTower2.setRenderer(mRenderer);
    
    mCoolingTowerInside1 = new CircleShape();
        
    mCoolingTowerInside1.setFillColor(Color.black);
    mCoolingTowerInside1.setOutlineColor(Color.black);
    mCoolingTowerInside1.setOutlineThickness(2.f);
    mCoolingTowerInside1.setSize(ctiSize);
    mCoolingTowerInside1.setOrigin(ctiSize.x/2+mSize.x/4, ctiSize.y/2);
    mCoolingTowerInside1.setScene(mScene);
    mCoolingTowerInside1.setRenderer(mRenderer);
    
    mCoolingTowerInside2 = new CircleShape();
        
    mCoolingTowerInside2.setFillColor(Color.black);
    mCoolingTowerInside2.setOutlineColor(Color.black);
    mCoolingTowerInside2.setOutlineThickness(2.f);
    mCoolingTowerInside2.setSize(ctiSize);
    mCoolingTowerInside2.setOrigin(ctiSize.x/2-mSize.x/4, ctiSize.y/2);
    mCoolingTowerInside2.setScene(mScene);
    mCoolingTowerInside2.setRenderer(mRenderer);
    
    
  }
  
  @Override
  public void onNewOwner() {
    mShapeBody.setFillColor(mOwner.getColor());
    mShapeConnector.setFillColor(mOwner.getColor());
    mCoolingTower1.setFillColor(mOwner.getColor());
    mCoolingTower2.setFillColor(mOwner.getColor());
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
      
      mShapeConnector.setPosition(mPosition);
      mShapeConnector.setRotation(mRotation);
      
      mCoolingTower1.setPosition(mPosition);
      mCoolingTower1.setRotation(mRotation);
      
      mCoolingTower2.setPosition(mPosition);
      mCoolingTower2.setRotation(mRotation);
      
      mCoolingTowerInside1.setPosition(mPosition);
      mCoolingTowerInside1.setRotation(mRotation);
      
      mCoolingTowerInside2.setPosition(mPosition);
      mCoolingTowerInside2.setRotation(mRotation);
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