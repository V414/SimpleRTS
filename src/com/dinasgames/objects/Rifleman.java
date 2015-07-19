package com.dinasgames.objects;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class Rifleman extends Infantry{
  
  public Rifleman(float mX, float mY, int team, int id){
    this.team = team;
    this.id = id;
    this.mX = mX;
    this.mY = mY;
    
    objectWidth = 10;
    objectHeight = 10;
    objectType = "Rifleman";
    createDrawShapes();
  }
  
  @Override
  public void onTick(float cameraX, float cameraY){
    this.cameraX = cameraX;
    this.cameraY = cameraY;
    
    createDrawShapes();
  }
  
  //Creates array for the paint method in the game class to paint.
  //Fill must come before draw
  private void createDrawShapes(){
    drawShapes = new DrawShapes[3];
    
    Ellipse2D fillBody = new Ellipse2D.Float(mX-cameraX, mY-cameraY, 
            getObjectWidth(), getObjectHeight());
    drawShapes[0] = new DrawShapes(fillBody, Color.blue, "fill");
    
    
    Ellipse2D drawBody = new Ellipse2D.Float(mX-cameraX, mY-cameraY, 
            getObjectWidth(), getObjectHeight());
    drawShapes[1] = new DrawShapes(drawBody, Color.black, "draw");
    
    float gunWidth = 2;
    float gunStartX = getOriginX()-cameraX;
    float gunStartY = getOriginY()-cameraY+getObjectHeight()/4-gunWidth;
    
    Rectangle2D fillGun = new Rectangle2D.Float(gunStartX, gunStartY, getObjectWidth(), gunWidth);
    drawShapes[2] = new DrawShapes(fillGun, Color.black, "fill");
  }
  
}