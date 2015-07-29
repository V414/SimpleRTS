package com.dinasgames.main.objects.entities.buildings;

import com.dinasgames.engine.graphics.shapes.CircleShape;
import com.dinasgames.engine.graphics.Color;
import com.dinasgames.engine.graphics.shapes.RectangleShape;
import com.dinasgames.engine.graphics.Renderer;
import com.dinasgames.engine.math.Vector2f;
import com.dinasgames.main.objects.GameObjectType;
import com.dinasgames.main.objects.RenderEvents;
import com.dinasgames.main.objects.entities.Entity;
import com.dinasgames.main.players.Player;
import com.dinasgames.main.scenes.Scene;

public class NewBuilding extends Building implements RenderEvents {
  
    /**
     * Main body.
     */
    protected RectangleShape mShapeBody;
    protected Building finishedBuilding;
    
    /**
     * Default constructor.
     * @param scene 
    * @param building  
     */
    public NewBuilding(Scene scene, Building building) {
        
        super(scene);
        finishedBuilding = building;
        
        // Setup Entity attributes
        mHealthMax      = 1000.f;
        mHealth         = mHealthMax;
        mWidth          = finishedBuilding.getWidth();
        mHeight         = finishedBuilding.getHeight();
        finishedBuilding.setPosition(building.getPosition());
        finishedBuilding.setOwner(building.getOwner());
        
        NewBuilding self = this;
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
      
    }
  
    @Override
    public void onRenderAdd(Renderer r) {
        
        super.onRenderAdd(r);
        
        mShapeBody = new RectangleShape(mWidth, mHeight);
        
        mShapeBody.setFillColor(new Color(50, 50, 50, 100));
        mShapeBody.setOutlineColor(new Color(20, 20, 20, 100));
        mShapeBody.setOutlineThickness(5.f);
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
    
    public Building getFinishedBuilding(){
      return finishedBuilding;
    }
    
    public void setFinishedBuilding(Building finishedBuilding){
      this.finishedBuilding = finishedBuilding;
    }
  
    @Override
    public int getTypeID() {
        return super.getTypeID() | GameObjectType.NewBuilding.getID();
    }

    @Override
    public String getTypeString() {
        return "NewBuilding";
    }
    
}
