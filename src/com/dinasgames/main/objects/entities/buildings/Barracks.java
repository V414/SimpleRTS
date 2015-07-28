package com.dinasgames.main.objects.entities.buildings;

import com.dinasgames.lwjgl.util.Color;
import com.dinasgames.lwjgl.util.RectangleShape;
import com.dinasgames.main.math.Vector2f;
import com.dinasgames.main.objects.GameObjectType;
import com.dinasgames.main.scenes.Scene;

public class Barracks extends Building{
  
  RectangleShape mShapeBody;
  RectangleShape mTower1;
  RectangleShape mTower2;
  RectangleShape mTower3;
  RectangleShape mTower4;
  
  public Barracks(Scene scene){
    
      super(scene);
      
    mShapeBody = null;
    mTower1 = null;
    mTower2 = null;
    mTower3 = null;
    mTower4 = null;
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
    
    mTower1 = new RectangleShape(mSize.x/6, mSize.x/6);
    mTower1.setFillColor(Color.BLACK);
    mTower1.setOutlineColor(Color.BLACK);
    mTower1.setOutlineThickness(2.f);
    mTower1.setOrigin(mSize.x/2-3, mSize.y/2-3);
    mTower1.setScene(mScene);
    mTower1.render(mRenderer);
    
    mTower2 = new RectangleShape(mSize.x/6, mSize.x/6);
    mTower2.setFillColor(Color.BLACK);
    mTower2.setOutlineColor(Color.BLACK);
    mTower2.setOutlineThickness(2.f);
    mTower2.setOrigin(-mSize.x/2+mSize.x/6+3, mSize.y/2-3);
    mTower2.setScene(mScene);
    mTower2.render(mRenderer);
    
    mTower3 = new RectangleShape(mSize.x/6, mSize.x/6);
    mTower3.setFillColor(Color.BLACK);
    mTower3.setOutlineColor(Color.BLACK);
    mTower3.setOutlineThickness(2.f);
    mTower3.setOrigin(mSize.x/2-3, -mSize.y/2+mSize.x/6+3);
    mTower3.setScene(mScene);
    mTower3.render(mRenderer);
    
    mTower4 = new RectangleShape(mSize.x/6, mSize.x/6);
    mTower4.setFillColor(Color.BLACK);
    mTower4.setOutlineColor(Color.BLACK);
    mTower4.setOutlineThickness(2.f);
    mTower4.setOrigin(-mSize.x/2+3+mSize.x/6, -mSize.y/2+mSize.x/6+3);
    mTower4.setScene(mScene);
    mTower4.render(mRenderer);
  }
  
  @Override
  public void onNewOwner() {
    mShapeBody.setFillColor(mOwner.getColor());
    mTower1.setFillColor(mOwner.getColor());
    mTower2.setFillColor(mOwner.getColor());
    mTower3.setFillColor(mOwner.getColor());
    mTower4.setFillColor(mOwner.getColor());
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
      
      mTower1.setPosition(mPosition);
      mTower1.setRotation(mRotation);
      
      mTower2.setPosition(mPosition);
      mTower2.setRotation(mRotation);
      
      mTower3.setPosition(mPosition);
      mTower3.setRotation(mRotation);
      
      mTower4.setPosition(mPosition);
      mTower4.setRotation(mRotation);
    }
  
  @Override
  public int getTypeID() {
      return super.getTypeID() | GameObjectType.Barracks.getID();
  }

  @Override
  public String getTypeString() {
      return "Barracks";
  }
}
