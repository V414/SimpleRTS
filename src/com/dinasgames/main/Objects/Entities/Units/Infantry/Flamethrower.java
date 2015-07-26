package com.dinasgames.main.Objects.Entities.Units.Infantry;

import com.dinasgames.lwjgl.util.CircleShape;
import com.dinasgames.lwjgl.util.Color;
import com.dinasgames.lwjgl.util.RectangleShape;
import com.dinasgames.main.Math.Vector2f;
import com.dinasgames.main.Objects.GameObjectType;
import com.dinasgames.main.Scenes.Scene;

public class Flamethrower extends Infantry {
  
  CircleShape mShapeBody;
  RectangleShape mShapeGun;
  RectangleShape mShapeGunEnd;
  RectangleShape mShapeBackpack;
  Vector2f mGunSize;
  float mGunRotation;

  public Flamethrower(Scene scene){
      
    super(scene);
    
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
        mShapeGun.setScene(mScene);
        mShapeGun.setOrigin(mShapeBody.getPosition().x, mShapeBody.getPosition().y/2);
        mShapeGun.render(mRenderer);
        
        mShapeGunEnd = new RectangleShape(mGunSize.x*2, mGunSize.x*2);
        
        mShapeGunEnd.setFillColor(Color.BLACK);
        mShapeGunEnd.setOutlineColor(Color.BLACK);
        mShapeGunEnd.setOutlineThickness(0.f);
        mShapeGunEnd.setScene(mScene);
        mShapeGunEnd.setOrigin(mShapeBody.getPosition().x+mGunSize.x/2, mShapeBody.getPosition().y/2-mGunSize.y+mGunSize.x*1.5f);
        mShapeGunEnd.render(mRenderer);
        
        mShapeBackpack = new RectangleShape(mSize.x/2, mSize.y);
        
        mShapeBackpack.setFillColor(Color.BLACK);
        mShapeBackpack.setOutlineColor(Color.BLACK);
        mShapeBackpack.setOutlineThickness(0.f);
        mShapeBackpack.setScene(mScene);
        mShapeBackpack.setOrigin(mSize.x-(mSize.x/3), mSize.y/2);
        mShapeBackpack.render(mRenderer);
        
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