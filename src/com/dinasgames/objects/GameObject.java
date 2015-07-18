package com.dinasgames.objects;

public class GameObject{
  
  protected float mX;
  protected float mY;
  protected int objectWidth;
  protected int objectHeight;
  protected int id;
  
  public GameObject(){
    
  }
  
  
  //Getters
  
  public int getID(){
    return id;
  }
  
  public float getX(){
    return mX;
  }
  
  public float getY(){
    return mY;
  }
  
  public float getOriginX(){
    return mX+(objectWidth/2);
  }
  
  public float getOriginY(){
    return mY+(objectHeight/2);
  }
  
  public int getObjectWidth(){
    return objectWidth;
  }
  
  public int getObjectHeight(){
    return objectHeight;
  }
  
  
  //Setters
  
  public void setID(int id){
    this.id = id;
  }
  
  public void setX(float mX){
    this.mX = mX;
  }
  
  public void setY(float mY){
    this.mY = mY;
  }
  
}