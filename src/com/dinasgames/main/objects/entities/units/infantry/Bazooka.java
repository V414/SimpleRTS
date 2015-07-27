/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.objects.entities.units.infantry;

import com.dinasgames.lwjgl.util.CircleShape;
import com.dinasgames.lwjgl.util.Color;
import com.dinasgames.lwjgl.util.RectangleShape;
import com.dinasgames.main.math.Vector2f;
import com.dinasgames.main.objects.GameObjectType;
import com.dinasgames.main.scenes.Scene;

public class Bazooka extends Infantry {
  
  CircleShape mShapeBody;
  RectangleShape mShapeGun;
  Vector2f mGunSize;
  float mGunRotation;

  public Bazooka(Scene scene){
      
    super(scene);
      
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
        
        mShapeBody = new CircleShape(mSize.x);
        
        mShapeBody.setFillColor(Color.WHITE);
        mShapeBody.setOutlineColor(Color.BLACK);
        mShapeBody.setOutlineThickness(2.f);
        mShapeBody.setOriginCenter();
        mShapeBody.setScene(mScene);
        mShapeBody.render(mRenderer);
        
        mShapeGun = new RectangleShape(mGunSize);
        
        mShapeGun.setFillColor(Color.BLACK);
        mShapeGun.setOutlineColor(Color.BLACK);
        mShapeGun.setOutlineThickness(0.f);
        mShapeGun.setSize(mGunSize);
        mShapeGun.setScene(mScene);
        mShapeGun.setOrigin(mShapeBody.getPosition().x, mShapeBody.getPosition().y/2+3);
        mShapeGun.render(mRenderer);
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