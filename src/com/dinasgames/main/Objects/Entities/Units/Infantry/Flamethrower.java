package com.dinasgames.main.Objects.Entities.Units.Infantry;

import com.dinasgames.main.Graphics.CircleShape;
import com.dinasgames.main.Graphics.RectangleShape;
import com.dinasgames.main.Math.Point;
import com.dinasgames.main.Math.Vector2f;
import com.dinasgames.main.Objects.GameObjectType;
import com.dinasgames.main.Scenes.Scene;
import java.awt.Color;

public class Flamethrower extends Infantry {
  
  CircleShape mShapeBody;
  RectangleShape mShapeGun;
  RectangleShape mShapeGunEnd;
  RectangleShape mShapeBackpack;
  Vector2f mGunSize;
  float mGunRotation;

  public Flamethrower(Scene scene){
      
    mScene = scene;
    mShapeBody = null;
    mShapeGun = null;
    mShapeGunEnd = null;
    mShapeBackpack = null;
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
        mGunSize = new Vector2f(2, 10);
        
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
        mShapeGun.setOrigin(mShapeBody.getPosition().x, mShapeBody.getPosition().y/2);
        
        mShapeGunEnd = new RectangleShape();
        
        mShapeGunEnd.setFillColor(Color.black);
        mShapeGunEnd.setOutlineColor(Color.black);
        mShapeGunEnd.setOutlineThickness(0.f);
        mShapeGunEnd.setSize(mGunSize.x*2, mGunSize.x*2);
        mShapeGunEnd.setScene(mScene);
        mShapeGunEnd.setRenderer(mRenderer);
        mShapeGunEnd.setOrigin(mShapeBody.getPosition().x+mGunSize.x/2, mShapeBody.getPosition().y/2-mGunSize.y+mGunSize.x*1.5f);
        
        mShapeBackpack = new RectangleShape();
        
        mShapeBackpack.setFillColor(Color.black);
        mShapeBackpack.setOutlineColor(Color.black);
        mShapeBackpack.setOutlineThickness(0.f);
        mShapeBackpack.setSize(mSize.x/2, mSize.y);
        mShapeBackpack.setScene(mScene);
        mShapeBackpack.setRenderer(mRenderer);
        mShapeBackpack.setOrigin(mSize.x-(mSize.x/3), mSize.y/2);
        
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
        
        mShapeGunEnd.setPosition(mPosition);
        mShapeGunEnd.setRotation(mRotation + mGunRotation);
        
        mShapeBackpack.setPosition(mPosition);
        mShapeBackpack.setRotation(mRotation);
        
        
        
//        Redundant Code?
//        Vector2f gunPosition = new Vector2f(mPosition);
//        gunPosition.add(Point.inDirection( mShapeGun.getWidth(), -mShapeGun.getRotation()));
//        
//        mShapeGun.setPosition(gunPosition);
        
    }
    
    @Override
    public void onDestroy() {
      mShapeBody.remove();
      mShapeGun.remove();
      mShapeGunEnd.remove();
      mShapeBackpack.remove();
      mHealthbar.remove();
    }
    
    @Override
    public int getTypeID() {
        return super.getTypeID() | GameObjectType.Flamethrower.getID();
    }
    
    @Override
    public String getTypeString() {
        return "Flamethrower";
    }

}