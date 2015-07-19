package com.dinasgames.objects;

public class GameObject{
  
  protected float mX;
  protected float mY;
  protected int objectWidth;
  protected int objectHeight;
  protected int id;
  protected int team;
  protected float angle;
  protected String objectType;
  protected DrawShapes[] drawShapes;
  protected float cameraX;
  protected float cameraY;
  
  public GameObject(){
    
  }
  
  //Main OnTick method - checked every tick.
  public void onTick(float cameraX, float cameraY){
    this.cameraX = cameraX;
    this.cameraY = cameraY;
  }
  
  //Getters
  
  public int getTeam(){
    return team;
  }
  
  public DrawShapes[] getDrawShapes(){
    return drawShapes;
  }
  
  public float getAngle(){
    return angle;
  }
  
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
  
  public void setAngle(float angle){
    this.angle = angle;
  }
  
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