package com.dinasgames.main.objects.entities.buildings;

import com.dinasgames.engine.graphics.Color;
import com.dinasgames.engine.graphics.shapes.RectangleShape;
import com.dinasgames.engine.graphics.Renderer;
import com.dinasgames.engine.math.Vector2f;
import com.dinasgames.main.objects.GameObjectType;
import com.dinasgames.main.objects.RenderEvents;
import com.dinasgames.main.objects.entities.Entity;
import com.dinasgames.main.players.Player;
import com.dinasgames.main.scenes.Scene;

public class Barracks extends Building implements RenderEvents{
  
  RectangleShape mShapeBody;
  RectangleShape mTower1;
  RectangleShape mTower2;
  RectangleShape mTower3;
  RectangleShape mTower4;
  
  public Barracks(Scene scene){
    super(scene);

    mHealthMax      = 1000.f;
    mHealth         = mHealthMax;
    mWidth          = 50.f;
    mHeight         = 30.f;
    
    Barracks self = this;
    
    this.addListener(new Entity.Events(){

      @Override
      public void onHealthChange(float oldHealth, float newHealth) {

      }

      @Override
      public void onMaxHealthChange(float oldMaxHealth, float newMaxHealth) {

      }

      @Override
      public void onNewOwner(Player oldOwner, Player newOwner) {
        if(newOwner != null) {
          self.setBodyColor( newOwner.getColor() );
        }else{
          self.setBodyColor( Color.WHITE );
        }
      }

      @Override
      public void onDeath() {

      }

      @Override
      public void onSizeChange(Vector2f oldSize, Vector2f newSize) {

      }

      @Override
      public void onSelected() {

      }

      @Override
      public void onDeselected() {

      }
    });
  }
  
  public void setBodyColor(Color color) {
        //mShapeBody.setFillColor(color);
    }
  
    @Override
    public void onRenderAdd(Renderer r) {
        
        super.onRenderAdd(r);
        
        mShapeBody = new RectangleShape(mWidth, mHeight);
        
        mShapeBody.setFillColor(mOwnerColor);
        mShapeBody.setOutlineColor(Color.BLACK);
        mShapeBody.setOutlineThickness(2.f);
        mShapeBody.setOriginCenter();

        mTower1 = new RectangleShape(mWidth/6, mWidth/6);
        mTower1.setFillColor(mOwnerColor);
        mTower1.setOutlineColor(Color.BLACK);
        mTower1.setOutlineThickness(2.f);
        mTower1.setOrigin(mWidth/2-3, mHeight/2-3);

        mTower2 = new RectangleShape(mWidth/6, mWidth/6);
        mTower2.setFillColor(mOwnerColor);
        mTower2.setOutlineColor(Color.BLACK);
        mTower2.setOutlineThickness(2.f);
        mTower2.setOrigin(-mWidth/2+mWidth/6+3, mHeight/2-3);

        mTower3 = new RectangleShape(mWidth/6, mWidth/6);
        mTower3.setFillColor(mOwnerColor);
        mTower3.setOutlineColor(Color.BLACK);
        mTower3.setOutlineThickness(2.f);
        mTower3.setOrigin(mWidth/2-3, -mHeight/2+mWidth/6+3);

        mTower4 = new RectangleShape(mWidth/6, mWidth/6);
        mTower4.setFillColor(mOwnerColor);
        mTower4.setOutlineColor(Color.BLACK);
        mTower4.setOutlineThickness(2.f);
        mTower4.setOrigin(-mWidth/2+3+mWidth/6, -mHeight/2+mWidth/6+3);
        
        r.add(mShapeBody);
        r.add(mTower1);
        r.add(mTower2);
        r.add(mTower3);
        r.add(mTower4);
        
    }
    
    @Override
    public void onRenderUpdate(Renderer r) {
      super.onRenderUpdate(r);
        
      mShapeBody.setPosition(mX, mY);
      mShapeBody.setRotation(mRotation);

      mTower1.setPosition(mX, mY);
      mTower1.setRotation(mRotation);
      
      mTower2.setPosition(mX, mY);
      mTower2.setRotation(mRotation);
      
      mTower3.setPosition(mX, mY);
      mTower3.setRotation(mRotation);
      
      mTower4.setPosition(mX, mY);
      mTower4.setRotation(mRotation);
        
    }
    
    @Override
    public void onRenderRemove(Renderer r) {
        
        super.onRenderRemove(r);
        
        r.remove(mShapeBody);
        r.remove(mTower1);
        r.remove(mTower2);
        r.remove(mTower3);
        r.remove(mTower4);
        
    }
    
  @Override
  public int getTypeID() {
      return super.getTypeID() | GameObjectType.Barracks.getID();
  }

  @Override
  public String getTypeString() {
      return "Barracks";
  }
}
