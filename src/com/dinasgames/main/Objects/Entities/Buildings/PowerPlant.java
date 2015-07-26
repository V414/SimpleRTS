package com.dinasgames.main.Objects.Entities.Buildings;

import com.dinasgames.lwjgl.util.CircleShape;
import com.dinasgames.lwjgl.util.Color;
import com.dinasgames.lwjgl.util.RectangleShape;
import com.dinasgames.main.Math.Vector2f;
import com.dinasgames.main.Objects.GameObjectType;
import com.dinasgames.main.Scenes.Scene;

public class PowerPlant extends Building{
  
  RectangleShape mShapeBody;
  CircleShape mCoolingTower1;
  CircleShape mCoolingTowerInside1;
  CircleShape mCoolingTower2;
  CircleShape mCoolingTowerInside2;
  RectangleShape mShapeConnector;
  
  public PowerPlant(Scene scene){
    
      super(scene);
      
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
    
    mShapeBody = new RectangleShape(mSize);
        
    mShapeBody.setFillColor(Color.BLACK);
    mShapeBody.setOutlineColor(Color.BLACK);
    mShapeBody.setOutlineThickness(2.f);
    mShapeBody.setOriginCenter();
    mShapeBody.setScene(mScene);
    mShapeBody.render(mRenderer);
    
    mShapeConnector = new RectangleShape(new Vector2f(mSize).divide(5.f, 2.f));
        
    mShapeConnector.setFillColor(Color.WHITE);
    mShapeConnector.setOutlineColor(Color.BLACK);
    mShapeConnector.setOutlineThickness(2.f);
    mShapeConnector.setOriginCenter();
    mShapeConnector.setScene(mScene);
    mShapeConnector.render(mRenderer);
    
    Vector2f ctSize = new Vector2f((mSize.x/5)*2.2f, (mSize.x/5)*2.2f).divide(2);
    Vector2f ctiSize = new Vector2f((mSize.x/5)*1.5f, (mSize.x/5)*1.5f).divide(2);
    
    mCoolingTower1 = new CircleShape(ctSize.x);
        
    mCoolingTower1.setFillColor(Color.WHITE);
    mCoolingTower1.setOutlineColor(Color.BLACK);
    mCoolingTower1.setOutlineThickness(2.f);
    mCoolingTower1.setOrigin(ctSize.x/2+mSize.x/4, ctSize.y/2);
    mCoolingTower1.setScene(mScene);
    mCoolingTower1.render(mRenderer);
    
    mCoolingTower2 = new CircleShape(ctSize.x);
        
    mCoolingTower2.setFillColor(Color.WHITE);
    mCoolingTower2.setOutlineColor(Color.BLACK);
    mCoolingTower2.setOutlineThickness(2.f);
    mCoolingTower2.setOrigin(ctSize.x/2-mSize.x/4, ctSize.y/2);
    mCoolingTower2.setScene(mScene);
    mCoolingTower2.render(mRenderer);
    
    mCoolingTowerInside1 = new CircleShape(ctiSize.x);
        
    mCoolingTowerInside1.setFillColor(new Color(20, 20, 20));
    mCoolingTowerInside1.setOutlineColor(Color.BLACK);
    mCoolingTowerInside1.setOutlineThickness(2.f);
    mCoolingTowerInside1.setOrigin(ctiSize.x/2+mSize.x/4, ctiSize.y/2);
    mCoolingTowerInside1.setScene(mScene);
    mCoolingTowerInside1.render(mRenderer);
    
    mCoolingTowerInside2 = new CircleShape(ctiSize.x);
        
    mCoolingTowerInside2.setFillColor(new Color(20, 20, 20));
    mCoolingTowerInside2.setOutlineColor(Color.BLACK);
    mCoolingTowerInside2.setOutlineThickness(2.f);
    mCoolingTowerInside2.setOrigin(ctiSize.x/2-mSize.x/4, ctiSize.y/2);
    mCoolingTowerInside2.setScene(mScene);
    mCoolingTowerInside2.render(mRenderer);
    
    
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
