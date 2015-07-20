package com.dinasgames.main.Objects.Entities.Units.Vehicles;

//package com.dinasgames.objects;
//
//import java.awt.Color;
//import java.awt.geom.Rectangle2D;
//
//public class LightTank extends Vehicle{
//  
//  public LightTank(float mX, float mY, int team, int id){
//    this.team = team;
//    this.id = id;
//    this.mX = mX;
//    this.mY = mY;
//    
//    objectWidth = 35;
//    objectHeight = 20;
//    objectType = "Rifleman";
//    healthMax = 100;
//    healthNow = 100;
//    createDrawShapes();
//  }
//  
//  @Override
//  public void onTick(float cameraX, float cameraY){
//    this.cameraX = cameraX;
//    this.cameraY = cameraY;
//    
//    createDrawShapes();
//    drawUnitStatusBars();
//  }
//  
//  //Creates array for the paint method in the game class to paint.
//  //Fill must come before draw
//  private void createDrawShapes(){
//    drawShapes = new DrawShapes[5];
//    
//    Rectangle2D tankFill = new Rectangle2D.Float(mX-cameraX, mY-cameraY, 
//            getObjectWidth(), getObjectHeight());
//    drawShapes[0] = new DrawShapes(tankFill, Color.red, "fill");
//    
//    Rectangle2D tankDraw = new Rectangle2D.Float(mX-cameraX, mY-cameraY, 
//            getObjectWidth(), getObjectHeight());
//    drawShapes[1] = new DrawShapes(tankDraw, Color.black, "draw");
//    
//    
//    float turretWidth = 12;
//    float turretHeight = 12;
//    
//    Rectangle2D turretFill = new Rectangle2D.Float(getOriginX()-cameraX, getOriginY()-cameraY-turretHeight/2, turretWidth, turretHeight);
//    drawShapes[2] = new DrawShapes(turretFill, Color.red, "fill");
//    
//    Rectangle2D turretDraw = new Rectangle2D.Float(getOriginX()-cameraX, getOriginY()-cameraY-turretHeight/2, turretWidth, turretHeight);
//    drawShapes[3] = new DrawShapes(turretDraw, Color.black, "draw");
//    
//    
//    float gunWidth = 3;
//    float gunStartX = getOriginX()-cameraX+turretWidth/2;
//    float gunStartY = getOriginY()-cameraY-(gunWidth/2);
//    
//    Rectangle2D gunDraw = new Rectangle2D.Float(gunStartX, gunStartY, getObjectWidth()/1.5f, gunWidth);
//    drawShapes[4] = new DrawShapes(gunDraw, Color.black, "fill");
//  }
//  
//}