package com.dinasgames.main.objects.entities.buildings;

import com.dinasgames.lwjgl.util.CircleShape;
import com.dinasgames.lwjgl.util.Color;
import com.dinasgames.lwjgl.util.RectangleShape;
import com.dinasgames.lwjgl.util.Renderer;
import com.dinasgames.main.math.Vector2f;
import com.dinasgames.main.objects.GameObjectType;
import com.dinasgames.main.objects.RenderEvents;
import com.dinasgames.main.objects.entities.Entity;
import com.dinasgames.main.players.Player;
import com.dinasgames.main.scenes.Scene;

public class PowerPlant extends Building implements RenderEvents {
  
    /**
     * Main body.
     */
    protected RectangleShape mShapeBody;
    
    /**
     * Left cooling tower
     */
    protected CircleShape mCoolingTowerLeft;
    
    /**
     * Right cooling tower
     */
    protected CircleShape mCoolingTowerRight;
    
    /**
     * Cooling tower connector.
     */
    protected CircleShape mCoolingTowerConnector;
    
    /**
     * Default constructor.
     * @param scene 
     */
    public PowerPlant(Scene scene) {
        
        super(scene);
        
        // Setup Entity attributes
        mHealthMax      = 1000.f;
        mHealth         = mHealthMax;
        mWidth          = 50.f;
        mHeight         = 30.f;
        
        PowerPlant self = this;
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
        
        
        
        r.add(mShapeBody);
        r.add(mCoolingTowerLeft);
        r.add(mCoolingTowerRight);
        r.add(mCoolingTowerConnector);
        
    }
    
    @Override
    public void onRenderUpdate(Renderer r) {
        
        super.onRenderUpdate(r);
        
    }
    
    @Override
    public void onRenderRemove(Renderer r) {
        
        super.onRenderRemove(r);
        
        r.remove(mShapeBody);
        r.remove(mCoolingTowerLeft);
        r.remove(mCoolingTowerRight);
        r.remove(mCoolingTowerConnector);
        
    }
  
    @Override
    public int getTypeID() {
        return super.getTypeID() | GameObjectType.PowerPlant.getID();
    }

    @Override
    public String getTypeString() {
        return "PowerPlant";
    }
    
}
