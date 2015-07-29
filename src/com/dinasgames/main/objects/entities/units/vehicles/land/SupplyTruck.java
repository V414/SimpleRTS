package com.dinasgames.main.objects.entities.units.vehicles.land;

import com.dinasgames.lwjgl.util.Color;
import com.dinasgames.lwjgl.util.RectangleShape;
import com.dinasgames.lwjgl.util.Renderer;
import com.dinasgames.main.math.RandomNumber;
import com.dinasgames.main.math.Vector2f;
import com.dinasgames.main.objects.GameObjectType;
import com.dinasgames.main.objects.RenderEvents;
import com.dinasgames.main.objects.SceneEvents;
import com.dinasgames.main.objects.entities.Entity;
import com.dinasgames.main.objects.entities.buildings.OilDerrick;
import com.dinasgames.main.objects.entities.buildings.Warehouse;
import com.dinasgames.main.players.Player;
import com.dinasgames.main.scenes.Scene;

public class SupplyTruck extends LandVehicle implements RenderEvents, SceneEvents{
  
  protected RectangleShape mMainBody;
  protected RectangleShape mCabin;
  protected RectangleShape mTruck;
  protected RectangleShape mWheelsBack;
  protected RectangleShape mWheelsFront;
  
  protected int carryingAmount = 30;
  protected int carryingMax = 30;
  
  public SupplyTruck(Scene scene){
    super(scene);

    // Setup Unit attributes
    this.mHealthMax     = 100.f;
    this.mHealth        = this.mHealthMax;

    // It gets to its top speed in around 12 seconds
    this.mSpeed         = 15.f;
    this.mAcceleration  = 34.7f;
    this.mDeceleration  = 30.f;

    // Setup Entity attributes
    this.mWidth     = 35.f;
    this.mHeight    = 15.f;

    // Setup listener events
    SupplyTruck self = this;
    
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
        
        mTruck = new RectangleShape(mWidth/2f, mHeight-(mHeight/5));
        mTruck.setFillColor(mOwnerColor);
        mTruck.setOutlineColor(Color.BLACK);
        mTruck.setOutlineThickness(2.f);
        mTruck.setOrigin(mWidth/2-mWidth/10, mHeight/2-mHeight/10);
        
        mCabin = new RectangleShape(mWidth/4, mHeight-(mHeight/5));
        mCabin.setFillColor(mOwnerColor);
        mCabin.setOutlineColor(Color.BLACK);
        mCabin.setOutlineThickness(2.f);
        mCabin.setOrigin(mWidth/2-mWidth + mWidth/3, mHeight/2-mHeight/10);
        
        mWheelsFront = new RectangleShape(mWidth/6, mHeight*1.4f);
        mWheelsFront.setFillColor(Color.BLACK);
        mWheelsFront.setOutlineColor(Color.BLACK);
        mWheelsFront.setOutlineThickness(2.f);
        mWheelsFront.setOrigin(mWidth/2-mWidth/10, mHeight/2+mHeight/5);
        
        mWheelsBack = new RectangleShape(mWidth/6, mHeight*1.4f);
        mWheelsBack.setFillColor(Color.BLACK);
        mWheelsBack.setOutlineColor(Color.BLACK);
        mWheelsBack.setOutlineThickness(2.f);
        mWheelsBack.setOrigin(mWidth/2-mWidth+mWidth/6+mWidth/10, mHeight/2+mHeight/5);
        
        
        // Add them to the renderer
        r.add(mWheelsFront);
        r.add(mWheelsBack);
        r.add(mMainBody);
        r.add(mTruck);
        r.add(mCabin);
        
        
        
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
        
    }
    
    @Override
    public void onRenderRemove(Renderer r) {
        
        super.onRenderRemove(r);
        
        r.remove(mMainBody);
        r.remove(mTruck);
        r.remove(mCabin);
        r.remove(mWheelsFront);
        r.remove(mWheelsBack);
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
        return super.getTypeID() | GameObjectType.SupplyTruck.getID();
    }
    
    @Override
    public String getTypeString() {
        return "SupplyTruck";
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