package com.dinasgames.main.Objects.Entities.Units;

import com.dinasgames.main.Math.Point;
import com.dinasgames.main.Math.Vector2f;
import com.dinasgames.main.Objects.Entities.Entity;
import com.dinasgames.main.Objects.GameObject;
import com.dinasgames.main.Objects.GameObjectType;
import com.dinasgames.main.Scenes.Scene;
import java.util.List;

public class Unit extends Entity {
    
    protected Vector2f mTargetPosition;
    protected float mSpeed;
    protected Unit target;
    
    
    protected Unit() {
        mTargetPosition = new Vector2f(0.f,0.f);
        target = null;
    }
    
    protected Unit(Scene scene) {
        this();
        mScene = scene;
    }
    
    protected void moveUnit(){
        
        
        
        if(Point.distance(mPosition, mTargetPosition) > mSpeed) {
            mPosition.add( Point.inDirection(mSpeed, Point.direction(mPosition, mTargetPosition)) );
            mRotation = -(float)Point.direction(mPosition, mTargetPosition);
        }
        
//        if(Math.abs(Point.distance(mPosition, mTargetPosition)) > mSpeed) {
//            
//            float angle = (float)Point.direction(mPosition, mTargetPosition);
//            
//            mPosition.x += Point.inDirectionX ( mSpeed, angle );
//            mPosition.y += Point.inDirectionY( mSpeed, angle );
//            
//            System.out.println(angle);
//            
//        }
//        
//      if((mPosition.x < mTargetPosition.x - mSpeed ||
//              mPosition.x > mTargetPosition.x + mSpeed) &&
//              (mPosition.y < mTargetPosition.y - mSpeed ||
//              mPosition.y > mTargetPosition.y + mSpeed)){
//        double b = mTargetPosition.y - mPosition.y;
//        double a = mTargetPosition.x - mPosition.x;
//        mRotation = Math.round(Math.toDegrees(Math.atan2(b, a)));
//
//        //Preventing angles going into minus numbers when travelling backwards
//        if(mRotation < 0){
//            mRotation += 360;
//        }
//        
//        float nX = (float) (mPosition.x + (mSpeed * Math.cos(Math.toRadians(mRotation))));
//        float nY = (float) (mPosition.y + (mSpeed * Math.sin(Math.toRadians(mRotation))));
//
//        mPosition.x = nX;
//        mPosition.y = nY;
//      }
    }
    
    public void setTarget(GameObject[] list){
      
      int distanceToEnemy = 99999;
      
      for(GameObject gameObject : list){
        if(gameObject != null && gameObject.hasType(GameObjectType.Unit)){
          Unit unit = (Unit) gameObject;
          
          if(unit.getOwner() != this.getOwner()){
            Vector2f thisOrigin = new Vector2f(mPosition.x + mSize.x/2, 
                    mPosition.y + mSize.y/2);
            
            Vector2f unitOrigin = new Vector2f(unit.getPosition().x + unit.getSize().x/2, 
                    unit.getPosition().y + unit.getSize().y/2);
            
            
            int distanceX;
            if(unitOrigin.x > thisOrigin.x){
                distanceX = Math.round(thisOrigin.x - unitOrigin.x);
            }else{
                distanceX = Math.round(thisOrigin.x - unitOrigin.x);
            }

            int distanceY;
            if(unitOrigin.y > thisOrigin.y){
                distanceY = Math.round(thisOrigin.y - unitOrigin.y);
            }else{
                distanceY = Math.round(thisOrigin.y - unitOrigin.y);
            }

            int distanceFromUnitSquared = (distanceX*distanceX) + (distanceY*distanceY);
            int distanceFromUnit = (int) Math.sqrt(distanceFromUnitSquared);

            if(distanceFromUnit < distanceToEnemy){
                target = unit;
                distanceToEnemy = distanceFromUnit;
            }
          }
        }
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