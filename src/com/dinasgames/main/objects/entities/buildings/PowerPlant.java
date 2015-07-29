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
    protected RectangleShape mCoolingTowerConnector;
    
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
        
        mShapeBody = new RectangleShape(mWidth, mHeight);
        
        mShapeBody.setFillColor(mOwnerColor);
        mShapeBody.setOutlineColor(Color.BLACK);
        mShapeBody.setOutlineThickness(2.f);
        mShapeBody.setOriginCenter();

        mCoolingTowerLeft = new CircleShape(mHeight/4);
        mCoolingTowerLeft.setFillColor(mOwnerColor);
        mCoolingTowerLeft.setOutlineColor(Color.BLACK);
        mCoolingTowerLeft.setOutlineThickness(4.f);
        mCoolingTowerLeft.setOrigin(mWidth/2-mWidth/12, mHeight/4);

        mCoolingTowerRight = new CircleShape(mHeight/4);
        mCoolingTowerRight.setFillColor(mOwnerColor);
        mCoolingTowerRight.setOutlineColor(Color.BLACK);
        mCoolingTowerRight.setOutlineThickness(4.f);
        mCoolingTowerRight.setOrigin(-mWidth/2+mHeight/2+mWidth/12, mHeight/4);

        mCoolingTowerConnector = new RectangleShape(mWidth/6, mWidth/6);
        mCoolingTowerConnector.setFillColor(mOwnerColor);
        mCoolingTowerConnector.setOutlineColor(Color.BLACK);
        mCoolingTowerConnector.setOutlineThickness(2.f);
        mCoolingTowerConnector.setOrigin(mWidth/12, mWidth/12);
        
        
        
        r.add(mShapeBody);
        r.add(mCoolingTowerConnector);
        r.add(mCoolingTowerLeft);
        r.add(mCoolingTowerRight);
        
        
    }
    
    @Override
    public void onRenderUpdate(Renderer r) {
        
        super.onRenderUpdate(r);
        
        mShapeBody.setPosition(mX, mY);
        mShapeBody.setRotation(mRotation);

        mCoolingTowerLeft.setPosition(mX, mY);
        mCoolingTowerLeft.setRotation(mRotation);

        mCoolingTowerRight.setPosition(mX, mY);
        mCoolingTowerRight.setRotation(mRotation);

        mCoolingTowerConnector.setPosition(mX, mY);
        mCoolingTowerConnector.setRotation(mRotation);
        
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
