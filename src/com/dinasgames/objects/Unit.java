package com.dinasgames.objects;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

public class Unit extends GameObject{
  
  protected int healthMax;
  protected int healthNow;
  protected int ammoMax;
  protected int ammoNow;
  protected float velocity;
  protected float targetX;
  protected float targetY;
  
  protected boolean isSelected;
  protected DrawShapes[] unitStatusBars;
  
  public Unit(){
    
  }
  
  protected void moveToDestination(){
    if(isAtDestination() == false){
    double a = targetX - getOriginX();
    double b = targetY - getOriginY();

    angle = Math.round(Math.toDegrees(Math.atan2(b, a)));

    if(angle < 0){
      angle += 360;
    }
    
      System.out.println("MX: " +getOriginX());
      System.out.println("TX: " +targetX);
      float nX = mX + (Math.round((velocity*delta) * Math.cos(Math.toRadians(angle))));
      float nY = mY + (Math.round((velocity*delta) * Math.sin(Math.toRadians(angle))));

    
      mX = nX;
      mY = nY;
    }
  }
  
  private boolean isAtDestination(){
    if(((Math.round(getOriginX()) < Math.round(targetX)+velocity && 
            Math.round(getOriginX()) > Math.round(targetX)-velocity)) && 
            (Math.round(getOriginY()) < Math.round(targetY)+velocity && 
            Math.round(getOriginY()) > Math.round(targetY)-velocity)){
      return true;
    }
    return false;
  }
  
  protected void drawUnitStatusBars(){
    unitStatusBars = new DrawShapes[6];
    
    Rectangle2D fillHealthBar = new Rectangle2D.Float(
            mX-cameraX, mY-cameraY-13 ,
            getObjectWidth(), 3);
    unitStatusBars[0] = new DrawShapes(fillHealthBar, Color.black, "fill");
    
    float percentUnitHealth = (float) healthNow/healthMax;
    
    Rectangle2D fillHealthBarHealth = new Rectangle2D.Float(
            mX-cameraX, mY-cameraY-13, 
            percentUnitHealth*getObjectWidth(), 3);
    unitStatusBars[1] = new DrawShapes(fillHealthBarHealth, Color.red, "fill");
    
    
    Rectangle2D drawHealthBarEdge = new Rectangle2D.Float(
            mX-cameraX, mY-cameraY-13, 
            getObjectWidth(), 3);
    unitStatusBars[2] = new DrawShapes(drawHealthBarEdge, Color.black, "draw");
    
    
    
    Rectangle2D fillAmmoBar = new Rectangle2D.Float(
            mX-cameraX, mY-cameraY-7, 
            getObjectWidth(), 3);
    unitStatusBars[3] = new DrawShapes(fillAmmoBar, Color.black, "fill");
    
    float percentUnitAmmo = (float) healthNow/healthMax;
    
    Rectangle2D fillAmmoBarAmmo = new Rectangle2D.Float(
            mX-cameraX, mY-cameraY-7, 
            percentUnitAmmo*getObjectWidth(), 3);
    unitStatusBars[4] = new DrawShapes(fillAmmoBarAmmo, Color.blue, "fill");
    
    
    Rectangle2D drawAmmoBarEdge = new Rectangle2D.Float(
            mX-cameraX, mY-cameraY-7, 
            getObjectWidth(), 3);
    unitStatusBars[5] = new DrawShapes(drawAmmoBarEdge, Color.black, "draw");
  }

  //Getters
  
  public DrawShapes[] getStatusBars(){
    return unitStatusBars;
  }
  
  public boolean getIsSelected(){
    return isSelected;
  }
  
  public int getAmmoNow(){
    return ammoNow;
  }
  
  public int getAmmoMax(){
    return ammoMax;
  }
  
  public int getHealthNow(){
    return healthNow;
  }
  
  public int getHealthMax(){
    return healthMax;
  }
  
  
  //Setters
  
  public void setIsSelected(boolean isSelected){
    this.isSelected = isSelected;
  }
  
  public void setAmmoNow(int ammoNow){
    this.ammoNow = ammoNow;
  }
  
  public void setHealthNow(int healthNow){
    this.healthNow = healthNow;
  }
  
  public void setTargetX(float targetX){
    this.targetX = targetX;
  }
  
  public void setTargetY(float targetY){
    this.targetY = targetY;
  }
}