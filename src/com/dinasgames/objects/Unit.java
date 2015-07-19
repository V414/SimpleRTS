package com.dinasgames.objects;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

public class Unit extends GameObject{
  
  protected int healthMax;
  protected int healthNow;
  
  protected int ammoMax;
  protected int ammoNow;
  
  protected boolean isSelected;
  protected DrawShapes[] unitStatusBars;
  
  public Unit(){
    
  }
  
  protected void drawUnitStatusBars(){
    unitStatusBars = new DrawShapes[3];
    
    Rectangle2D fillHealthBar = new Rectangle2D.Float(
            mX-cameraX, mY-cameraY-8, 
            getObjectWidth(), 4);
    unitStatusBars[0] = new DrawShapes(fillHealthBar, Color.black, "fill");
    
    float percentPlayerHealth = (float) healthNow/healthMax;
    
    Rectangle2D fillHealthBarHealth = new Rectangle2D.Float(
            mX-cameraX, mY-cameraY-8, 
            percentPlayerHealth*getObjectWidth(), 4);
    unitStatusBars[1] = new DrawShapes(fillHealthBarHealth, Color.red, "fill");
    
    
    Rectangle2D drawBody = new Rectangle2D.Float(
            mX-cameraX, mY-cameraY-8, 
            getObjectWidth(), 4);
    unitStatusBars[2] = new DrawShapes(drawBody, Color.black, "draw");
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
}