package com.dinasgames.main.Objects.Entities.Units.Infantry;

import com.dinasgames.main.Graphics.CircleShape;
import com.dinasgames.main.Graphics.RectangleShape;
import com.dinasgames.main.Math.Point;
import com.dinasgames.main.Math.Vector2f;
import com.dinasgames.main.Objects.GameObjectType;
import com.dinasgames.main.Scenes.Scene;
import java.awt.Color;

public class Rifleman extends Infantry {
  
  CircleShape mShapeBody;
  RectangleShape mShapeGun;
  Vector2f mGunSize;
  float mGunRotation;

  public Rifleman(Scene scene){
      
    mScene = scene;
    mShapeBody = null;
    mShapeGun = null;
    mHealthMax = 100.f;
    mHealth = mHealthMax;
    mSpeed = 0.2f;
    mGunRotation = 270.f;
    
    addToScene();
    
  }
    
    @Override
    public void onCreate() {
        
        super.onCreate();
        
        setSize(10.f, 10.f);
        mGunSize = new Vector2f(2, 10);
        
        mShapeBody = new CircleShape();
        
        mShapeBody.setFillColor(Color.white);
        mShapeBody.setOutlineColor(Color.black);
        mShapeBody.setOutlineThickness(2.f);
        mShapeBody.setSize(mSize);
        mShapeBody.setOriginCenter();
        mShapeBody.setScene(mScene);
        mShapeBody.setRenderer(mRenderer);
        
        mShapeGun = new RectangleShape();
        
        mShapeGun.setFillColor(Color.black);
        mShapeGun.setOutlineColor(Color.black);
        mShapeGun.setOutlineThickness(0.f);
        mShapeGun.setSize(mGunSize);
        mShapeGun.setScene(mScene);
        mShapeGun.setRenderer(mRenderer);
        mShapeGun.setOrigin(mShapeBody.getPosition().x, mShapeBody.getPosition().y/2);
        
    }
    
    @Override
    public void onNewOwner() {
        
        mShapeBody.setFillColor(mOwner.getColor());
        
    }
    
    @Override
    public void onTick(double time) {
        
        super.onTick(time);
        setTarget(mScene.getObjectsList());
        moveUnit();
        
    }
    
    @Override
    public void onRender() {
        
        super.onRender();
        
        mShapeBody.setPosition(mPosition);
        mShapeBody.setRotation(mRotation);
        
        mShapeGun.setPosition(mPosition);
        mShapeGun.setRotation(mRotation + mGunRotation);
        
        Vector2f gunPosition = new Vector2f(mPosition);
        gunPosition.add(Point.inDirection( mShapeGun.getWidth(), -mShapeGun.getRotation()));
        
        mShapeGun.setPosition(gunPosition);
        
    }
    
    @Override
    public int getTypeID() {
        return super.getTypeID() | GameObjectType.Rifleman.getID();
    }
    
    @Override
    public String getTypeString() {
        return "Rifleman";
    }
    
  
}

//package com.dinasgames.objects;
//
//import java.awt.Color;
//import java.awt.geom.Ellipse2D;
//import java.awt.geom.Rectangle2D;
//
//public class Rifleman extends Infantry{
//  
//  public Rifleman(float mX, float mY, int team, int id){
//    this.team = team;
//    this.id = id;
//    this.mX = mX;
//    this.mY = mY;
//    
//    objectWidth = 10;
//    objectHeight = 10;
//    objectType = "Rifleman";
//    healthMax = 100;
//    healthNow = 50;
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
//    drawShapes = new DrawShapes[3];
//    
//    Ellipse2D fillBody = new Ellipse2D.Float(mX-cameraX, mY-cameraY, 
//            getObjectWidth(), getObjectHeight());
//    drawShapes[0] = new DrawShapes(fillBody, Color.blue, "fill");
//    
//    
//    Ellipse2D drawBody = new Ellipse2D.Float(mX-cameraX, mY-cameraY, 
//            getObjectWidth(), getObjectHeight());
//    drawShapes[1] = new DrawShapes(drawBody, Color.black, "draw");
//    
//    float gunWidth = 2;
//    float gunStartX = getOriginX()-cameraX;
//    float gunStartY = getOriginY()-cameraY+getObjectHeight()/4-gunWidth;
//    
//    Rectangle2D fillGun = new Rectangle2D.Float(gunStartX, gunStartY, getObjectWidth(), gunWidth);
//    drawShapes[2] = new DrawShapes(fillGun, Color.black, "fill");
//  }
//  
//}
