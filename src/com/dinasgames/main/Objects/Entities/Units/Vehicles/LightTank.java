package com.dinasgames.main.Objects.Entities.Units.Vehicles;

import com.dinasgames.main.Graphics.RectangleShape;
import com.dinasgames.main.Math.RandomNumber;
import com.dinasgames.main.Math.Vector2f;
import com.dinasgames.main.Objects.GameObjectType;
import com.dinasgames.main.Scenes.Scene;
import java.awt.Color;

public class LightTank extends Vehicle{
  
  RectangleShape mShapeBody;
  RectangleShape mShapeTurret;
  Vector2f mTurretSize;

  protected LightTank(){
    mShapeBody = null;
    mShapeTurret = null;
    mTurretSize = null;
    mHealthMax = 100.f;
    mHealth = mHealthMax;
  }
  
  public static LightTank create() {
        
        // Add a test unit to the scene
        int id = Scene.getCurrent().add(new LightTank());
        
        // Make a reference to this object and return it
        LightTank ghost = new LightTank();
        ghost.setID(id);
        ghost.makeReference();
        
        return ghost;
        
    }
    
//    @Override
//    public void setSize(Vector2f size) {
//        super.setSize(size);
//        mTurretSize = new Vector2f(size.x / 2.f, size.y / 2.f);
//        if(isReference()) {
//            ref().setSize(size);
//        }
//    }
  
    @Override
    public void onCreate() {
        
        super.onCreate();
        
        setSize(40.f, 20.f);
        mTurretSize = new Vector2f(mSize.x/2-4, mSize.y-4);
        
        mShapeBody = RectangleShape.create();
        
        mShapeBody.setFillColor(Color.white);
        mShapeBody.setOutlineColor(Color.black);
        mShapeBody.setOutlineThickness(2.f);
        mShapeBody.setSize(mSize);
        mShapeBody.setRotation(RandomNumber.between(0.f, 360.f));
        
        mShapeTurret = RectangleShape.create();
        
        mShapeTurret.setFillColor(Color.white);
        mShapeTurret.setOutlineColor(Color.black);
        mShapeTurret.setOutlineThickness(2.f);
        mShapeTurret.setSize(mTurretSize);
        mShapeTurret.setRotation(RandomNumber.between(0.f,360.f));
        
    }
    
    @Override
    public void onNewOwner() {
        
        mShapeBody.setFillColor(mOwner.getColor());
        mShapeTurret.setFillColor(mOwner.getColor());
        
    }
    
    @Override
    public void onTick(double time) {
        
        super.onTick(time);
        
        //mPosition.x --;
        
    }
    
    @Override
    public void onRender() {
        
        super.onRender();
        
        mShapeBody.setPosition(mPosition);
        mShapeBody.setRotation(mRotation);
        
        mShapeTurret.setPosition(mPosition.x+7, mPosition.y+2);
        mShapeTurret.setRotation(mRotation);
        
    }
    
    @Override
    public int getTypeID() {
        return super.getTypeID() | GameObjectType.LightTank.getID();
    }
    
    @Override
    public String getTypeString() {
        return "LightTank";
    }
    
    @Override
    protected boolean hasValidReference() {
        return (this.ref() != null);
    }

    private LightTank ref() {
        return (LightTank)Scene.getCurrent().get(mID);
    }
  
}

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