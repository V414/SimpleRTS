package com.dinasgames.main.Objects.Entities.Units.Vehicles;

import com.dinasgames.main.Graphics.RectangleShape;
import com.dinasgames.main.Math.Point;
import com.dinasgames.main.Math.RandomNumber;
import com.dinasgames.main.Math.Vector2f;
import com.dinasgames.main.Objects.GameObjectType;
import com.dinasgames.main.Scenes.Scene;
import java.awt.Color;

public class LightTank extends Vehicle {
  
  RectangleShape mShapeBody;
  RectangleShape mShapeBody2;
  RectangleShape mShapeTurret;
  RectangleShape mShapeTracks;
  
  float mTurretRotation;
  //Vector2f mTurretSize;

  public LightTank(Scene scene){
    mScene = scene;
    mShapeTracks = null;
    mShapeBody = null;
    mShapeTurret = null;
    //mTurretSize = null;
    mHealthMax = 100.f;
    mHealth = mHealthMax;
    mSpeed = 2.f;
    mTurretRotation = 0.f;
    mMaxRange = 150;
    mDamage = 20;
    mMaxReloadingTime = 120;
    
    addToScene();
    
  }
    
//    @Override
//    public void setSize(Vector2f size) {
//        super.setSize(size);
//        mTurretSize = new Vector2f(size.x / 2.f, size.y / 2.f);
//        if(isReference()) {
//            ref().setSize(size);
//        }
//    }
  
    @Override
    public void onCreate() {
        
        super.onCreate();
        
        setSize(35.f, 15.f);
        //mTurretSize = new Vector2f(mSize.x/2-4, mSize.y-4);
        
        mShapeTracks = new RectangleShape();
        
        mShapeTracks.setFillColor(new Color(20, 20, 20));
        mShapeTracks.setOutlineColor(Color.black);
        mShapeTracks.setOutlineThickness(2.f);
        mShapeTracks.setSize(mSize.x/1.1f, mSize.y*1.5f);
        mShapeTracks.setScene(mScene);
        mShapeTracks.setRenderer(mRenderer);
        mShapeTracks.setOriginCenter();
        
        mShapeBody = new RectangleShape();
        
        mShapeBody.setFillColor(Color.white);
        mShapeBody.setOutlineColor(Color.black);
        mShapeBody.setOutlineThickness(2.f);
        mShapeBody.setSize(mSize);
        mShapeBody.setRotation(RandomNumber.between(0.f, 360.f));
        mShapeBody.setScene(mScene);
        mShapeBody.setRenderer(mRenderer);
        mShapeBody.setOriginCenter();
        
        mShapeBody2 = new RectangleShape();
        
        mShapeBody2.setFillColor(Color.white);
        mShapeBody2.setOutlineColor(Color.black);
        mShapeBody2.setOutlineThickness(2.f);
        mShapeBody2.setSize(new Vector2f(mShapeBody.getSize()).divide(2.f,1.f).subtract(0.f,4.f));
        mShapeBody2.setScene(mScene);
        mShapeBody2.setRenderer(mRenderer);
        mShapeBody2.setOriginCenter();
        
        mShapeTurret = new RectangleShape();
        
        mShapeTurret.setFillColor(Color.black);
        mShapeTurret.setOutlineColor(Color.black);
        mShapeTurret.setOutlineThickness(2.f);
        mShapeTurret.setSize(mShapeBody.getWidth() / 2.f, mShapeBody2.getHeight() / 4.f);
        mShapeTurret.setScene(mScene);
        mShapeTurret.setRenderer(mRenderer);
        mShapeTurret.setOrigin(0.f, mShapeTurret.getHeight() / 2.f);
        
        // Set a random rotation for our turret
        mTurretRotation = 0;
        
    }
    
    @Override
    public void onNewOwner() {
        
        // Apply new owner colours
        mShapeBody.setFillColor(mOwner.getColor());
        mShapeBody2.setFillColor(mOwner.getColor());
        
    }
    
    @Override
    public void onTick(double time) {
        
        super.onTick(time);
        setTarget(mScene.getObjectsList());
        shootTarget();
        moveUnit();
        
    }
    
    @Override
    public void onDestroy() {
      mShapeTracks.remove();
      mShapeBody.remove();
      mShapeBody2.remove();
      mShapeTurret.remove();
      mHealthbar.remove();
    }
    
    @Override
    public void onRender() {
        
        super.onRender();
        
        mShapeTracks.setPosition(mPosition);
        mShapeTracks.setRotation(mRotation);
        
        mShapeBody.setPosition(mPosition);
        mShapeBody.setRotation(mRotation);
        
        mShapeBody2.setPosition(mPosition);
        
        if(target != null){
          double b = target.getPosition().y - mPosition.y;
          double a = target.getPosition().x - mPosition.x;
          float targetAngle = Math.round(Math.toDegrees(Math.atan2(b, a)));
          
          if(targetAngle < 0){
              targetAngle += 360;
          }
          
          mShapeBody2.setRotation(targetAngle);
        }else{
           mShapeBody2.setRotation(mTurretRotation);
        }
        
       
        
        // Move the turret so that it is at the end of the 2nd body shape
        
        // First take the current position
        Vector2f turretPosition = new Vector2f(mPosition);
        
        // Then move it so that it is in front of our 2nd body shape
        turretPosition.add(Point.inDirection( mShapeBody2.getWidth() / 2.f, -mShapeBody2.getRotation()));
        
        // Finally apply the new position
        mShapeTurret.setPosition(turretPosition);
        mShapeTurret.setRotation(mShapeBody2.getRotation());
        
        // Move the turret back a bit
        //mShapeTurret.setPosition(Point.inDirection(-10.f, mRotation));
        
    }
    
    @Override
    public int getTypeID() {
        return super.getTypeID() | GameObjectType.LightTank.getID();
    }
    
    @Override
    public String getTypeString() {
        return "LightTank";
    }
  
}

//package com.dinasgames.objects;
//
//import java.awt.Color;
//import java.awt.geom.Rectangle2D;
//
//public class LightTank extends Vehicle{
//  
//  public LightTank(float mX, float mY, int team, int id){
//    this.team = team;
//    this.id = id;
//    this.mX = mX;
//    this.mY = mY;
//    
//    objectWidth = 35;
//    objectHeight = 20;
//    objectType = "Rifleman";
//    healthMax = 100;
//    healthNow = 100;
//    createDrawShapes();
//  }
//  
//  @Override
//  public void onTick(float cameraX, float cameraY){
//    this.cameraX = cameraX;
//    this.cameraY = cameraY;
//    
//    createDrawShapes();
//    drawUnitStatusBars();
//  }
//  
//  //Creates array for the paint method in the game class to paint.
//  //Fill must come before draw
//  private void createDrawShapes(){
//    drawShapes = new DrawShapes[5];
//    
//    Rectangle2D tankFill = new Rectangle2D.Float(mX-cameraX, mY-cameraY, 
//            getObjectWidth(), getObjectHeight());
//    drawShapes[0] = new DrawShapes(tankFill, Color.red, "fill");
//    
//    Rectangle2D tankDraw = new Rectangle2D.Float(mX-cameraX, mY-cameraY, 
//            getObjectWidth(), getObjectHeight());
//    drawShapes[1] = new DrawShapes(tankDraw, Color.black, "draw");
//    
//    
//    float turretWidth = 12;
//    float turretHeight = 12;
//    
//    Rectangle2D turretFill = new Rectangle2D.Float(getOriginX()-cameraX, getOriginY()-cameraY-turretHeight/2, turretWidth, turretHeight);
//    drawShapes[2] = new DrawShapes(turretFill, Color.red, "fill");
//    
//    Rectangle2D turretDraw = new Rectangle2D.Float(getOriginX()-cameraX, getOriginY()-cameraY-turretHeight/2, turretWidth, turretHeight);
//    drawShapes[3] = new DrawShapes(turretDraw, Color.black, "draw");
//    
//    
//    float gunWidth = 3;
//    float gunStartX = getOriginX()-cameraX+turretWidth/2;
//    float gunStartY = getOriginY()-cameraY-(gunWidth/2);
//    
//    Rectangle2D gunDraw = new Rectangle2D.Float(gunStartX, gunStartY, getObjectWidth()/1.5f, gunWidth);
//    drawShapes[4] = new DrawShapes(gunDraw, Color.black, "fill");
//  }
//  
//}
