package com.dinasgames.main.Objects.Entities.Units.Infantry;

import com.dinasgames.main.Graphics.CircleShape;
import com.dinasgames.main.Graphics.RectangleShape;
import com.dinasgames.main.Math.Point;
import com.dinasgames.main.Math.Vector2f;
import com.dinasgames.main.Objects.GameObjectType;
import com.dinasgames.main.Scenes.Scene;
import java.awt.Color;

public class Bazooka extends Infantry {
  
  CircleShape mShapeBody;
  RectangleShape mShapeGun;
  Vector2f mGunSize;
  float mGunRotation;

  public Bazooka(Scene scene){
      
    mScene = scene;
    mShapeBody = null;
    mShapeGun = null;
    mHealthMax = 100.f;
    mHealth = mHealthMax;
    mSpeed = 0.2f;
    mGunRotation = 270.f;
    
    addToScene();
    
  }
    
    @Override
    public void onCreate() {
        
        super.onCreate();
        
        setSize(10.f, 10.f);
        mGunSize = new Vector2f(4, 13);
        
        mShapeBody = new CircleShape();
        
        mShapeBody.setFillColor(Color.white);
        mShapeBody.setOutlineColor(Color.black);
        mShapeBody.setOutlineThickness(2.f);
        mShapeBody.setSize(mSize);
        mShapeBody.setOriginCenter();
        mShapeBody.setScene(mScene);
        mShapeBody.setRenderer(mRenderer);
        
        mShapeGun = new RectangleShape();
        
        mShapeGun.setFillColor(Color.black);
        mShapeGun.setOutlineColor(Color.black);
        mShapeGun.setOutlineThickness(0.f);
        mShapeGun.setSize(mGunSize);
        mShapeGun.setScene(mScene);
        mShapeGun.setRenderer(mRenderer);
        mShapeGun.setOrigin(mShapeBody.getPosition().x, mShapeBody.getPosition().y/2+3);
    }
    
    @Override
    public void onNewOwner() {
        
        mShapeBody.setFillColor(mOwner.getColor());
        
    }
    
    @Override
    public void onTick(double time) {
        
        super.onTick(time);
        setTarget(mScene.getObjectsList());
        moveUnit();
        
    }
    
    @Override
    public void onRender() {
        
        super.onRender();
        
        mShapeBody.setPosition(mPosition);
        mShapeBody.setRotation(mRotation);
        
        mShapeGun.setPosition(mPosition);
        mShapeGun.setRotation(mRotation + mGunRotation);
    }
    
    @Override
    public void onDestroy() {
      mShapeBody.remove();
      mShapeGun.remove();
      mHealthbar.remove();
    }
    
    @Override
    public int getTypeID() {
        return super.getTypeID() | GameObjectType.Bazooka.getID();
    }
    
    @Override
    public String getTypeString() {
        return "Bazooka";
    }

}