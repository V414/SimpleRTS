package com.dinasgames.main.Objects.Entities.Units;

import com.dinasgames.main.Math.Vector2f;
import com.dinasgames.main.Objects.Entities.Entity;
import com.dinasgames.main.Objects.GameObjectType;
import com.dinasgames.main.Scenes.Scene;

public class Unit extends Entity {
    
    protected Vector2f mTargetPosition;
    protected float mVelocity;
    
    
    protected Unit() {
        mTargetPosition = new Vector2f(0.f,0.f);
    }
    
    protected Unit(Scene scene) {
        this();
        mScene = scene;
    }
    
    protected void moveUnit(){
      if((mPosition.x < mTargetPosition.x - mVelocity ||
              mPosition.x > mTargetPosition.x + mVelocity) &&
              (mPosition.y < mTargetPosition.y - mVelocity ||
              mPosition.y > mTargetPosition.y + mVelocity)){
        double b = mTargetPosition.y - mPosition.y;
        double a = mTargetPosition.x - mPosition.x;
        mRotation = Math.round(Math.toDegrees(Math.atan2(b, a)));

        //Preventing angles going into minus numbers when travelling backwards
        if(mRotation < 0){
            mRotation += 360;
        }
        
        float nX = mPosition.x + (Math.round(mVelocity * Math.cos(Math.toRadians(mRotation))));
        float nY = mPosition.y + (Math.round(mVelocity * Math.sin(Math.toRadians(mRotation))));

        mPosition.x = nX;
        mPosition.y = nY;
      }
    }
    
    @Override
    public int getTypeID() {
        return super.getTypeID() | GameObjectType.Unit.getID();
    }
    
    @Override
    public String getTypeString() {
        return "Unit";
    }
    
    
    @Override
    protected void recalculateBoundingBox() {
        super.recalculateBoundingBox();
        mBoundingBox.setSize(mSize);
    }
    
    public Vector2f getTargetPosition(){
      return mTargetPosition;
    }
    
    public void setTargetPosition(Vector2f mTargetPosition){
      this.mTargetPosition = mTargetPosition;
    }
    
    public void setTargetPosition(float x, float y){
      this.mTargetPosition.x = x;
      this.mTargetPosition.y = y;
    }
    
}

//package com.dinasgames.objects;
//
//import java.awt.Color;
//import java.awt.geom.Rectangle2D;
//
//public class Unit extends GameObject{
//  
//  protected int healthMax;
//  protected int healthNow;
//  
//  protected int ammoMax;
//  protected int ammoNow;
//  
//  protected boolean isSelected;
//  protected DrawShapes[] unitStatusBars;
//  
//  public Unit(){
//    
//  }
//  
//  protected void drawUnitStatusBars(){
//    unitStatusBars = new DrawShapes[3];
//    
//    Rectangle2D fillHealthBar = new Rectangle2D.Float(
//            mX-cameraX, mY-cameraY-8, 
//            getObjectWidth(), 4);
//    unitStatusBars[0] = new DrawShapes(fillHealthBar, Color.black, "fill");
//    
//    float percentPlayerHealth = (float) healthNow/healthMax;
//    
//    Rectangle2D fillHealthBarHealth = new Rectangle2D.Float(
//            mX-cameraX, mY-cameraY-8, 
//            percentPlayerHealth*getObjectWidth(), 4);
//    unitStatusBars[1] = new DrawShapes(fillHealthBarHealth, Color.red, "fill");
//    
//    
//    Rectangle2D drawBody = new Rectangle2D.Float(
//            mX-cameraX, mY-cameraY-8, 
//            getObjectWidth(), 4);
//    unitStatusBars[2] = new DrawShapes(drawBody, Color.black, "draw");
//  }
//
//  //Getters
//  
//  public DrawShapes[] getStatusBars(){
//    return unitStatusBars;
//  }
//  
//  public boolean getIsSelected(){
//    return isSelected;
//  }
//  
//  public int getAmmoNow(){
//    return ammoNow;
//  }
//  
//  public int getAmmoMax(){
//    return ammoMax;
//  }
//  
//  public int getHealthNow(){
//    return healthNow;
//  }
//  
//  public int getHealthMax(){
//    return healthMax;
//  }
//  
//  
//  //Setters
//  
//  public void setIsSelected(boolean isSelected){
//    this.isSelected = isSelected;
//  }
//  
//  public void setAmmoNow(int ammoNow){
//    this.ammoNow = ammoNow;
//  }
//  
//  public void setHealthNow(int healthNow){
//    this.healthNow = healthNow;
//  }
//}