package com.dinasgames.main.objects.entities.units.vehicles.land;

import com.dinasgames.engine.graphics.Color;
import com.dinasgames.engine.graphics.shapes.RectangleShape;
import com.dinasgames.engine.graphics.Renderer;
import com.dinasgames.engine.graphics.shapes.CircleShape;
import com.dinasgames.engine.math.Vector2f;
import com.dinasgames.main.objects.GameObjectType;
import com.dinasgames.main.objects.RenderEvents;
import com.dinasgames.main.objects.SceneEvents;
import com.dinasgames.main.objects.entities.Entity;
import com.dinasgames.main.players.Player;
import com.dinasgames.main.scenes.Scene;

public class MissileBattery extends LandVehicle implements RenderEvents, SceneEvents{
  
  protected RectangleShape mMainBody;
  protected RectangleShape mCabin;
  protected RectangleShape mTruck;
  protected RectangleShape mWheelsBack;
  protected RectangleShape mWheelsMiddle1;
  protected RectangleShape mWheelsMiddle2;
  protected RectangleShape mWheelsFront;
  protected CircleShape mMissile;
  
  protected int carryingAmount = 0;
  protected int carryingMax = 30;
  
  public MissileBattery(Scene scene){
    super(scene);

    // Setup Unit attributes
    this.mHealthMax     = 100.f;
    this.mHealth        = this.mHealthMax;

    // It gets to its top speed in around 12 seconds
    this.mSpeed         = 7.f;
    this.mAcceleration  = 34.7f;
    this.mDeceleration  = 30.f;

    // Setup Entity attributes
    this.mWidth     = 60.f;
    this.mHeight    = 15.f;

    // Setup listener events
    MissileBattery self = this;
    
    this.addListener(new Entity.Events(){

      @Override
      public void onHealthChange(float oldHealth, float newHealth) {}

      @Override
      public void onMaxHealthChange(float oldMaxHealth, float newMaxHealth) {}

      @Override
      public void onNewOwner(Player oldOwner, Player newOwner) {
        if(newOwner != null) {
          self.setBodyColor( newOwner.getColor() );
        }else{
          self.setBodyColor( Color.WHITE );
        }
      }

      @Override
      public void onDeath() {}

      @Override
      public void onSizeChange(Vector2f oldSize, Vector2f newSize) {}

      @Override
      public void onSelected() {}

      @Override
      public void onDeselected() {}
    });
    
  }
  
    @Override
    public void onSceneAdd(Scene scene) {
        
        super.onSceneAdd(scene);
        
        
        
    }
    
    @Override
    public void onSceneRemove(Scene scene) {
        super.onSceneRemove(scene);
    }
    
    @Override
    public void onRenderAdd(Renderer r) {
        
        super.onRenderAdd(r);
        
        // Create our render objects
        mMainBody = new RectangleShape(mWidth, mHeight);
        mMainBody.setFillColor(mOwnerColor);
        mMainBody.setOutlineColor(Color.BLACK);
        mMainBody.setOutlineThickness(2.f);
        mMainBody.setOriginCenter();
        
        mTruck = new RectangleShape(mWidth/2f, mHeight-(mHeight/2.5f));
        mTruck.setFillColor(mOwnerColor);
        mTruck.setOutlineColor(Color.BLACK);
        mTruck.setOutlineThickness(2.f);
        mTruck.setOrigin(mWidth/2-1.f, mHeight/2-mHeight/5);
        
        mCabin = new RectangleShape(mWidth/4, mHeight-(mHeight/5));
        mCabin.setFillColor(mOwnerColor);
        mCabin.setOutlineColor(Color.BLACK);
        mCabin.setOutlineThickness(2.f);
        mCabin.setOrigin(-mWidth/2 + mWidth/4 + 1.f, mHeight/2-mHeight/10);
        
        mWheelsFront = new RectangleShape(mWidth/8, mHeight*1.4f);
        mWheelsFront.setFillColor(Color.BLACK);
        mWheelsFront.setOutlineColor(Color.BLACK);
        mWheelsFront.setOutlineThickness(2.f);
        mWheelsFront.setOrigin(mWidth/2-mWidth/15, mHeight/2+mHeight/5);
        
        mWheelsMiddle1 = new RectangleShape(mWidth/8, mHeight*1.4f);
        mWheelsMiddle1.setFillColor(Color.BLACK);
        mWheelsMiddle1.setOutlineColor(Color.BLACK);
        mWheelsMiddle1.setOutlineThickness(2.f);
        mWheelsMiddle1.setOrigin(-mWidth/20, mHeight/2+mHeight/5);
        
        mWheelsMiddle2 = new RectangleShape(mWidth/8, mHeight*1.4f);
        mWheelsMiddle2.setFillColor(Color.BLACK);
        mWheelsMiddle2.setOutlineColor(Color.BLACK);
        mWheelsMiddle2.setOutlineThickness(2.f);
        mWheelsMiddle2.setOrigin(mWidth/5, mHeight/2+mHeight/5);
        
        mWheelsBack = new RectangleShape(mWidth/8, mHeight*1.4f);
        mWheelsBack.setFillColor(Color.BLACK);
        mWheelsBack.setOutlineColor(Color.BLACK);
        mWheelsBack.setOutlineThickness(2.f);
        mWheelsBack.setOrigin(mWidth/2-mWidth+mWidth/6+mWidth/15, mHeight/2+mHeight/5);
        
        mMissile = new CircleShape(mHeight/6);
        mMissile.setPointCount(3);
        mMissile.setFillColor(Color.BLACK);
        mMissile.setOutlineColor(Color.BLACK);
        mMissile.setOutlineThickness(2.f);
        mMissile.setOrigin(mHeight/12+1.f, mHeight/12+6.f);
        
        
        // Add them to the renderer
        r.add(mWheelsFront);
        r.add(mWheelsMiddle1);
        r.add(mWheelsMiddle2);
        r.add(mWheelsBack);
        r.add(mMainBody);
        r.add(mTruck);
        r.add(mCabin);
        r.add(mMissile);
        
        
        
    }
    
    @Override
    public void onRenderUpdate(Renderer r) {
        
        super.onRenderUpdate(r);
        
        mMainBody.setPosition(mX, mY);
        mMainBody.setRotation(mRotation);
        
        mTruck.setPosition(mX, mY);
        mTruck.setRotation(mRotation);
        
        mCabin.setPosition(mX, mY);
        mCabin.setRotation(mRotation);
        
        mWheelsFront.setPosition(mX, mY);
        mWheelsFront.setRotation(mRotation);
        
        mWheelsBack.setPosition(mX, mY);
        mWheelsBack.setRotation(mRotation);
        
        mWheelsMiddle1.setPosition(mX, mY);
        mWheelsMiddle1.setRotation(mRotation);
        
        mWheelsMiddle2.setPosition(mX, mY);
        mWheelsMiddle2.setRotation(mRotation);
        
        mMissile.setPosition(mX, mY);
        mMissile.setRotation(mRotation+90);
        
    }
    
    @Override
    public void onRenderRemove(Renderer r) {
        
        super.onRenderRemove(r);
        
        r.remove(mMainBody);
        r.remove(mTruck);
        r.remove(mCabin);
        r.remove(mWheelsFront);
        r.remove(mWheelsBack);
        r.remove(mWheelsMiddle1);
        r.remove(mWheelsMiddle2);
        r.remove(mMissile);
    }
    
    public void setBodyColor(Color color) {
        if(mMainBody != null && mTruck != null && mCabin != null) {
            mMainBody.setFillColor(color);
            mTruck.setFillColor(color);
            mCabin.setFillColor(color);
        }
    }
    
    @Override
    public int getTypeID() {
        return super.getTypeID() | GameObjectType.MissileBattery.getID();
    }
    
    @Override
    public String getTypeString() {
        return "MissileBattery";
    }
    
    public int getCarryingAmount(){
      return carryingAmount;
    }
    
    public int getCarryingMax(){
      return carryingMax;
    }
    
    public void setCarryingAmount(int carryingAmount){
      this.carryingAmount = carryingAmount;
    }
  
}