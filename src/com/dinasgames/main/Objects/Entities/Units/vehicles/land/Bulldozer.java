package com.dinasgames.main.Objects.Entities.Units.vehicles.land;

import com.dinasgames.lwjgl.util.Color;
import com.dinasgames.lwjgl.util.RectangleShape;
import com.dinasgames.main.Math.Vector2f;
import com.dinasgames.main.Objects.GameObjectType;
import com.dinasgames.main.Scenes.Scene;

public class Bulldozer extends LandVehicle {
  
  RectangleShape mShapeBody;
  RectangleShape mShapeCabin;
  RectangleShape mShapeBucketConnector;
  RectangleShape mShapeBucket;
  RectangleShape mShapeTracks;
  
  
  public Bulldozer(Scene scene){
    
      super(scene);
      
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
      
      mShapeTracks = new RectangleShape(new Vector2f(mSize).divide(1.2f, 1.f).subtract(0.f,8.f));
      
      mShapeTracks.setFillColor(new Color(20, 20, 20));
      mShapeTracks.setOutlineColor(Color.BLACK);
      mShapeTracks.setOutlineThickness(2.f);
      mShapeTracks.setRotation(0);
      mShapeTracks.setScene(mScene);
      mShapeTracks.setOriginCenter();
      mShapeTracks.render(mRenderer);
      
      

      mShapeBody = new RectangleShape(mSize);

      mShapeBody.setFillColor(Color.WHITE);
      mShapeBody.setOutlineColor(Color.BLACK);
      mShapeBody.setOutlineThickness(2.f);
      mShapeBody.setRotation(0);
      mShapeBody.setScene(mScene);
      mShapeBody.setOriginCenter();
      mShapeBody.render(mRenderer);

      mShapeCabin = new RectangleShape(new Vector2f(mShapeBody.getSize()).divide(2.f, 1.f).subtract(4.f));

      mShapeCabin.setFillColor(Color.WHITE);
      mShapeCabin.setOutlineColor(Color.BLACK);
      mShapeCabin.setOutlineThickness(2.f);
      mShapeCabin.setScene(mScene);
      mShapeCabin.setOrigin(mSize.x/2-2, mShapeBody.getSize().y/2-2f);
      mShapeCabin.render(mRenderer);
      
      mShapeBucketConnector = new RectangleShape(new Vector2f(mShapeBody.getSize()).divide(6.f, 2.f));
      
      mShapeBucketConnector.setFillColor(Color.BLACK);
      mShapeBucketConnector.setOutlineColor(Color.BLACK);
      mShapeBucketConnector.setOutlineThickness(2.f);
      mShapeBucketConnector.setScene(mScene);
      mShapeBucketConnector.setOrigin(-mSize.x/2, mSize.y/2-mShapeBody.getSize().y/4);
      mShapeBucketConnector.render(mRenderer);

      mShapeBucket = new RectangleShape(new Vector2f(mShapeBody.getSize().x/6, mShapeBody.getSize().y+6));

      mShapeBucket.setFillColor(Color.WHITE);
      mShapeBucket.setOutlineColor(Color.BLACK);
      mShapeBucket.setOutlineThickness(2.f);
      mShapeBucket.setScene(mScene);
      mShapeBucket.setOrigin(-mSize.x/2-5, mSize.y/2+3);
      mShapeBucket.render(mRenderer);
        
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