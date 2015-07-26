package com.dinasgames.main.Objects.Entities.Units.Vehicles;

import com.dinasgames.main.Graphics.RectangleShape;
import com.dinasgames.main.Math.Point;
import com.dinasgames.main.Math.RandomNumber;
import com.dinasgames.main.Math.Vector2f;
import com.dinasgames.main.Objects.GameObjectType;
import com.dinasgames.main.Scenes.Scene;
import java.awt.Color;

public class Bulldozer extends Vehicle {
  
  RectangleShape mShapeBody;
  RectangleShape mShapeCabin;
  RectangleShape mShapeBucketConnector;
  RectangleShape mShapeBucket;
  RectangleShape mShapeTracks;
  
  
  public Bulldozer(Scene scene){
    mScene = scene;
    mShapeBody = null;
    mShapeCabin = null;
    mShapeBucketConnector = null;
    mShapeBucket = null;
    mShapeTracks = null;
    
    mHealthMax = 100.f;
    mHealth = mHealthMax;
    mSpeed = 0.7f;
    
    addToScene();
    
  }

  @Override
  public void onCreate() {

      super.onCreate();

      setSize(20.f, 10.f);
      //mTurretSize = new Vector2f(mSize.x/2-4, mSize.y-4);
      
      mShapeTracks = new RectangleShape();
      
      mShapeTracks.setFillColor(new Color(20, 20, 20));
      mShapeTracks.setOutlineColor(Color.black);
      mShapeTracks.setOutlineThickness(2.f);
      mShapeTracks.setSize(mSize.x/1.2f, mSize.y+8);
      mShapeTracks.setRotation(0);
      mShapeTracks.setScene(mScene);
      mShapeTracks.setRenderer(mRenderer);
      mShapeTracks.setOriginCenter();
      
      

      mShapeBody = new RectangleShape();

      mShapeBody.setFillColor(Color.white);
      mShapeBody.setOutlineColor(Color.black);
      mShapeBody.setOutlineThickness(2.f);
      mShapeBody.setSize(mSize);
      mShapeBody.setRotation(0);
      mShapeBody.setScene(mScene);
      mShapeBody.setRenderer(mRenderer);
      mShapeBody.setOriginCenter();

      mShapeCabin = new RectangleShape();

      mShapeCabin.setFillColor(Color.white);
      mShapeCabin.setOutlineColor(Color.black);
      mShapeCabin.setOutlineThickness(2.f);
      mShapeCabin.setSize(new Vector2f(mShapeBody.getSize().x/2-4, mShapeBody.getSize().y-4));
      mShapeCabin.setScene(mScene);
      mShapeCabin.setRenderer(mRenderer);
      mShapeCabin.setOrigin(mSize.x/2-2, mShapeBody.getSize().y/2-2f);
      
      mShapeBucketConnector = new RectangleShape();
      
      mShapeBucketConnector.setFillColor(Color.black);
      mShapeBucketConnector.setOutlineColor(Color.black);
      mShapeBucketConnector.setOutlineThickness(2.f);
      mShapeBucketConnector.setSize(new Vector2f(mShapeBody.getSize().x/6, mShapeBody.getSize().y/2));
      mShapeBucketConnector.setScene(mScene);
      mShapeBucketConnector.setRenderer(mRenderer);
      mShapeBucketConnector.setOrigin(-mSize.x/2, mSize.y/2-mShapeBody.getSize().y/4);

      mShapeBucket = new RectangleShape();

      mShapeBucket.setFillColor(Color.white);
      mShapeBucket.setOutlineColor(Color.black);
      mShapeBucket.setOutlineThickness(2.f);
      mShapeBucket.setSize(new Vector2f(mShapeBody.getSize().x/6, mShapeBody.getSize().y+6));
      mShapeBucket.setScene(mScene);
      mShapeBucket.setRenderer(mRenderer);
      mShapeBucket.setOrigin(-mSize.x/2-5, mSize.y/2+3);
        
    }
    
    @Override
    public void onNewOwner() {
        
        // Apply new owner colours
        mShapeBody.setFillColor(mOwner.getColor());
        mShapeCabin.setFillColor(mOwner.getColor());
        mShapeBucket.setFillColor(mOwner.getColor());
        
    }
    
    @Override
    public void onTick(double time) {
        
        super.onTick(time);
        moveUnit();
        
    }
    
    @Override
    public void onRender() {
        
        super.onRender();
        
        mShapeTracks.setPosition(mPosition);
        mShapeTracks.setRotation(mRotation);
        
        mShapeBody.setPosition(mPosition);
        mShapeBody.setRotation(mRotation);
        
        mShapeCabin.setPosition(mPosition);
        mShapeCabin.setRotation(mRotation);
        
        mShapeBucketConnector.setPosition(mPosition);
        mShapeBucketConnector.setRotation(mRotation);
        
        mShapeBucket.setPosition(mPosition);
        mShapeBucket.setRotation(mRotation);
        
        
        
       
        
//        // Move the turret so that it is at the end of the 2nd body shape
//        
//        // First take the current position
//        Vector2f turretPosition = new Vector2f(mPosition);
//        
//        // Then move it so that it is in front of our 2nd body shape
//        turretPosition.add(Point.inDirection( mShapeBody2.getWidth() / 2.f, -mShapeBody2.getRotation()));
//        
//        // Finally apply the new position
//        mShapeTurret.setPosition(turretPosition);
//        mShapeTurret.setRotation(mShapeBody2.getRotation());
//        
//        // Move the turret back a bit
//        //mShapeTurret.setPosition(Point.inDirection(-10.f, mRotation));
//        
    }
    
    @Override
    public void onDestroy() {
      mShapeTracks.remove();
      mShapeBody.remove();
      mShapeCabin.remove();
      mShapeBucketConnector.remove();
      mShapeBucket.remove();
      mHealthbar.remove();
    }
    
    @Override
    public int getTypeID() {
        return super.getTypeID() | GameObjectType.Bulldozer.getID();
    }
    
    @Override
    public String getTypeString() {
        return "Bulldozer";
    }
  
}