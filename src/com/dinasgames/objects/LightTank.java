package com.dinasgames.objects;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class LightTank extends Vehicle{
  
  public LightTank(float mX, float mY, int team, int id){
    this.team = team;
    this.id = id;
    this.mX = mX;
    this.mY = mY;
    
    objectWidth = 35;
    objectHeight = 20;
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
  private void createDrawShapes(){
    drawShapes = new Shape[5];
    drawShapeType = new String[5];
    drawShapeColor = new Color[5];
    
    drawShapes[0] = new Rectangle2D.Float(mX-cameraX, mY-cameraY, 
            getObjectWidth(), getObjectHeight());
    drawShapeType[0] = "draw";
    drawShapeColor[0] = Color.black;
    
    drawShapes[1] = new Rectangle2D.Float(mX+1-cameraX, mY+1-cameraY, 
            getObjectWidth()-1, getObjectHeight()-1);
    drawShapeType[1] = "fill";
    drawShapeColor[1] = Color.red;
    
    float turretWidth = 12;
    float turretHeight = 12;
    
    drawShapes[2] = new Rectangle2D.Float(getOriginX()-cameraX, getOriginY()-cameraY-turretHeight/2, turretWidth, turretHeight);
    drawShapeType[2] = "draw";
    drawShapeColor[2] = Color.black;
    
    drawShapes[3] = new Rectangle2D.Float(getOriginX()+1-cameraX, getOriginY()-cameraY-turretHeight/2+1, turretWidth-1, turretHeight-1);
    drawShapeType[3] = "fill";
    drawShapeColor[3] = Color.red;
    
    float gunWidth = 3;
    float gunStartX = getOriginX()-cameraX+turretWidth/2;
    float gunStartY = getOriginY()-cameraY-(gunWidth/2);
    
    drawShapes[4] = new Rectangle2D.Float(gunStartX, gunStartY, getObjectWidth()/1.5f, gunWidth);
    drawShapeType[4] = "fill";
    drawShapeColor[4] = Color.black;
  }
  
}