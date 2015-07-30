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

public class OilDerrick extends Building implements RenderEvents{
  
  RectangleShape mShapeBody;
  RectangleShape mTower;
  RectangleShape mLine;
  
  public OilDerrick(Scene scene){
    super(scene);

    mHealthMax      = 1000.f;
    mHealth         = mHealthMax;
    mWidth          = 30.f;
    mHeight         = 20.f;
    
    OilDerrick self = this;
    
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
  
  public void setBodyColor(Color color) {
        //mShapeBody.setFillColor(color);
    }
  
    @Override
    public void onRenderAdd(Renderer r) {
      super.onRenderAdd(r);

      mShapeBody = new RectangleShape(mWidth, mHeight);
      mShapeBody.setFillColor(new Color(60, 60, 60, 255));
      mShapeBody.setOutlineColor(Color.BLACK());
      mShapeBody.setOutlineThickness(2.f);
      mShapeBody.setOriginCenter();

      mTower = new RectangleShape(mWidth/3, mHeight/1.4f);
      mTower.setFillColor(new Color(20, 20, 20, 255));
      mTower.setOutlineColor(Color.BLACK());
      mTower.setOutlineThickness(2.f);
      mTower.setOrigin(+mWidth/2-mWidth+mWidth/2.5f, mHeight/2-mHeight/7);

      mLine = new RectangleShape(mWidth/2, mHeight/8);
      mLine.setFillColor(new Color(20, 20, 20, 255));
      mLine.setOutlineColor(Color.BLACK());
      mLine.setOutlineThickness(2.f);
      mLine.setOrigin(mWidth/2-mWidth/6, +mHeight/16);

      r.add(mShapeBody);
      r.add(mTower);
      r.add(mLine);
    }
    
    @Override
    public void onRenderUpdate(Renderer r) {
      super.onRenderUpdate(r);
        
      mShapeBody.setPosition(mX, mY);
      mShapeBody.setRotation(mRotation);

      mTower.setPosition(mX, mY);
      mTower.setRotation(mRotation);
      
      mLine.setPosition(mX, mY);
      mLine.setRotation(mRotation);
        
    }
    
    @Override
    public void onRenderRemove(Renderer r) {
        
        super.onRenderRemove(r);
        
        r.remove(mShapeBody);
        r.remove(mTower);
        r.remove(mLine);
        
    }
    
  @Override
  public int getTypeID() {
      return super.getTypeID() | GameObjectType.OilDerrick.getID();
  }

  @Override
  public String getTypeString() {
      return "OilDerrick";
  }
}
