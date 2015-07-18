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
  private void createDrawShapes(){
    drawShapes = new Shape[3];
    drawShapeType = new String[3];
    drawShapeColor = new Color[3];
    System.out.println(cameraX);
    
    drawShapes[0] = new Ellipse2D.Float(mX-cameraX, mY-cameraY, 
            getObjectWidth(), getObjectHeight());
    drawShapeType[0] = "draw";
    drawShapeColor[0] = Color.black;
    
    drawShapes[1] = new Ellipse2D.Float(mX-cameraX, mY-cameraY, 
            getObjectWidth(), getObjectHeight());
    drawShapeType[1] = "fill";
    drawShapeColor[1] = Color.blue;
    
    float gunWidth = 2;
    float gunStartX = getOriginX()-cameraX;
    float gunStartY = getOriginY()-cameraY+getObjectHeight()/4-gunWidth;
    
    drawShapes[2] = new Rectangle2D.Float(gunStartX, gunStartY, getObjectWidth(), gunWidth);
    drawShapeType[2] = "fill";
    drawShapeColor[2] = Color.black;
  }
  
}