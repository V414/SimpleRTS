package com.dinasgames.objects;

import java.awt.Color;
import java.awt.Shape;

public class GameObject{
  
  protected float mX;
  protected float mY;
  protected int objectWidth;
  protected int objectHeight;
  protected int id;
  protected int team;
  protected float angle;
  protected String objectType;
  protected Shape[] drawShapes;
  protected Color[] drawShapeColor;
  protected String[] drawShapeType;
  protected float cameraX;
  protected float cameraY;
  
  public GameObject(){
    
  }
  
  public void onTick(float cameraX, float cameraY){
    this.cameraX = cameraX;
    this.cameraY = cameraY;
  }
  
  //Getters
  
  public int getTeam(){
    return team;
  }
  
  public Color[] getDrawShapeColor(){
    return drawShapeColor;
  }
  
  public String[] getDrawShapeType(){
    return drawShapeType;
  }
  
  public Shape[] getDrawShapes(){
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