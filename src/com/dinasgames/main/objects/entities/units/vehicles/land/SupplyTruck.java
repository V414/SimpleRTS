package com.dinasgames.main.objects.entities.units.vehicles.land;

import com.dinasgames.engine.graphics.Color;
import com.dinasgames.engine.graphics.shapes.RectangleShape;
import com.dinasgames.engine.graphics.Renderer;
import com.dinasgames.engine.graphics.statusbars.StatusBar;
import com.dinasgames.engine.math.RandomNumber;
import com.dinasgames.engine.math.Vector2f;
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
  protected StatusBar loadingProgress;
  
  protected int carryingAmount = 0;
  protected int carryingMax = 30;
  
  //In seconds
  protected int loadingTime = 1;
  
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
    this.mWidth     = 30.f;
    this.mHeight    = 13.f;

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
          self.setBodyColor( Color.WHITE() );
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
        mMainBody.setOutlineColor(Color.BLACK());
        mMainBody.setOutlineThickness(2.f);
        mMainBody.setOriginCenter();
        
        mTruck = new RectangleShape(mWidth/2f, mHeight-(mHeight/5));
        mTruck.setFillColor(mOwnerColor);
        mTruck.setOutlineColor(Color.BLACK());
        mTruck.setOutlineThickness(2.f);
        mTruck.setOrigin(mWidth/2-mWidth/10, mHeight/2-mHeight/10);
        
        mCabin = new RectangleShape(mWidth/4, mHeight-(mHeight/5));
        mCabin.setFillColor(mOwnerColor);
        mCabin.setOutlineColor(Color.BLACK());
        mCabin.setOutlineThickness(2.f);
        mCabin.setOrigin(mWidth/2-mWidth + mWidth/3, mHeight/2-mHeight/10);
        
        mWheelsFront = new RectangleShape(mWidth/6, mHeight*1.4f);
        mWheelsFront.setFillColor(Color.BLACK());
        mWheelsFront.setOutlineColor(Color.BLACK());
        mWheelsFront.setOutlineThickness(2.f);
        mWheelsFront.setOrigin(mWidth/2-mWidth/10, mHeight/2+mHeight/5);
        
        mWheelsBack = new RectangleShape(mWidth/6, mHeight*1.4f);
        mWheelsBack.setFillColor(Color.BLACK());
        mWheelsBack.setOutlineColor(Color.BLACK());
        mWheelsBack.setOutlineThickness(2.f);
        mWheelsBack.setOrigin(mWidth/2-mWidth+mWidth/6+mWidth/10, mHeight/2+mHeight/5);
        
        loadingProgress = new StatusBar();
        
        loadingProgress.setDepth(-100);
        loadingProgress.setHeight(3.f);
        loadingProgress.setFillColor(Color.BLACK());
        loadingProgress.setForegroundColor(Color.GREEN());
        loadingProgress.setOutlineThickness(1.f);
        loadingProgress.setOutlineColor(Color.BLACK());
        loadingProgress.setCurrentValue(carryingAmount);
        loadingProgress.setMaxValue(carryingMax);
        
        
        // Add them to the renderer
        
        r.add(mWheelsFront);
        r.add(mWheelsBack);
        r.add(mMainBody);
        r.add(mTruck);
        r.add(mCabin);
        r.add(loadingProgress);
        
        
        
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
        
        if(mSelected) {
          loadingProgress.setPosition(mBoundingBox.x, mBoundingBox.y-17);

          // Update the healthbar health
          loadingProgress.setCurrentValue(carryingAmount);

          // Update the healthbar size
          loadingProgress.setSize(mBoundingBox.width, loadingProgress.getSize().y);

          // Make sure the healthbar is visible
          if(!loadingProgress.isVisible()) {
            loadingProgress.show();
          }
        }else{
          if(loadingProgress.isVisible()) {
              loadingProgress.hide();
          }
        }
    }
    
    @Override
    public void onRenderRemove(Renderer r) {
        
        super.onRenderRemove(r);
        
        r.remove(mMainBody);
        r.remove(mTruck);
        r.remove(mCabin);
        r.remove(mWheelsFront);
        r.remove(mWheelsBack);
        r.remove(loadingProgress);
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
    
    public int getLoadingTime(){
      return loadingTime;
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
    
    public void setLoadingTime(int loadingTime){
      this.loadingTime = loadingTime;
    }
  
}
