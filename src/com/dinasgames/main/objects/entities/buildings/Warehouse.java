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

public class Warehouse extends Building implements RenderEvents {
  
    /**
     * The warehouse body
     */
    protected RectangleShape mShapeBody;
  
    public Warehouse(Scene scene) {
        
        super(scene);
        
        // Setup Entity attributes
        mHealthMax      = 1000.f;
        mHealth         = mHealthMax;
        mWidth          = 40.f;
        mHeight         = 60.f;
        
        Warehouse self = this;
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
                    self.setBodyColor( Color.WHITE() );
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
        if(mShapeBody != null) {
            mShapeBody.setFillColor(color);
        }
    }
    
    @Override
    public void onRenderAdd(Renderer r) {
        
        super.onRenderAdd(r);
        
        mShapeBody = new RectangleShape(mWidth, mHeight);

        mShapeBody.setFillColor(mOwnerColor);
        mShapeBody.setOutlineColor(Color.BLACK());
        mShapeBody.setOutlineThickness(4.f);
        mShapeBody.setOriginCenter();
        
        r.add(mShapeBody);
        
    }
    
    @Override
    public void onRenderUpdate(Renderer r) {
        
        super.onRenderUpdate(r);
        
        mShapeBody.setPosition(mX, mY);
        mShapeBody.setRotation(mRotation);
        
    }
    
    @Override
    public void onRenderRemove(Renderer r) {
        
        super.onRenderRemove(r);
        
        r.remove(mShapeBody);
        
    }
  
  @Override
  public int getTypeID() {
      return super.getTypeID() | GameObjectType.Warehouse.getID();
  }

  @Override
  public String getTypeString() {
      return "Warehouse";
  }
}