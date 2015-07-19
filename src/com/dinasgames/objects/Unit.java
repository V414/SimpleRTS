package com.dinasgames.objects;

public class Unit extends GameObject{
  
  protected int healthMax;
  protected int healthNow;
  
  protected int ammoMax;
  protected int ammoNow;
  
  protected boolean isSelected;
  
  public Unit(){
    
  }

  //Getters
  
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