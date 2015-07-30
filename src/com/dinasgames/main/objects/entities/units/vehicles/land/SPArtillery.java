package com.dinasgames.main.objects.entities.units.vehicles.land;

import com.dinasgames.engine.graphics.Color;
import com.dinasgames.engine.graphics.shapes.RectangleShape;
import com.dinasgames.engine.graphics.Renderer;
import com.dinasgames.engine.math.RandomNumber;
import com.dinasgames.engine.math.Vector2f;
import com.dinasgames.main.objects.GameObjectType;
import com.dinasgames.main.objects.RenderEvents;
import com.dinasgames.main.objects.SceneEvents;
import com.dinasgames.main.objects.entities.Entity;
import com.dinasgames.main.players.Player;
import com.dinasgames.main.scenes.Scene;
import com.dinasgames.engine.system.Time;

public class SPArtillery extends LandVehicle implements RenderEvents, SceneEvents {
  
    /**
     * The main body of the tank.
     */
    protected RectangleShape mShapeBody;
    
    /**
     * The turret body.
     */
    protected RectangleShape mShapeBody2;
    
    /**
     * The tank gun.
     */
    protected RectangleShape mShapeGun;
    
    /**
     * The gun end
     */
    protected RectangleShape mShapeGunEnd;
    
    /**
     * The tank tracks.
     */
    protected RectangleShape mShapeTracks;
    
    /**
     * The rotation of the turret.
     */
    protected float mTurretRotation;          // << The rotation of the turret
  
    public SPArtillery( Scene scene ) {
        
        super(scene);
        
        // Setup LightTank attributes
        this.mTurretRotation = 0.f;
        
        // Setup Unit attributes
        this.mHealthMax     = 100.f;
        this.mHealth        = this.mHealthMax;
        this.mAttackTime    = Time.seconds(1.f);
        this.mDamage        = 50.f;
        this.mMaxAmmo       = 1;
        this.mRange         = 100.f;
        this.mReloadTime    = Time.seconds(1.f);
        
        // Tank moves at 25mph
        // It gets to its top speed in around 12 seconds
        this.mSpeed         = 10.f;
        this.mAcceleration  = 34.7f;
        this.mDeceleration  = 30.f;
        
        // Setup Entity attributes
        this.mWidth     = 35.f;
        this.mHeight    = 15.f;
        
        // Setup listener events
        SPArtillery self = this;
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
        mShapeTracks = new RectangleShape(mWidth/1.1f, mHeight*1.5f);
        
        mShapeTracks.setFillColor(new Color(20, 20, 20));
        mShapeTracks.setOutlineColor(Color.BLACK());
        mShapeTracks.setOutlineThickness(2.f);
        mShapeTracks.setOriginCenter();
        
        mShapeBody = new RectangleShape(mWidth, mHeight);
        
        mShapeBody.setFillColor(mOwnerColor);
        mShapeBody.setOutlineColor(Color.BLACK());
        mShapeBody.setOutlineThickness(2.f);
        mShapeBody.setRotation(RandomNumber.between(0.f, 360.f));
        mShapeBody.setOriginCenter();
        
        mShapeBody2 = new RectangleShape( mWidth/4, mHeight-(mHeight/6) );
        
        mShapeBody2.setFillColor(mOwnerColor);
        mShapeBody2.setOutlineColor(Color.BLACK());
        mShapeBody2.setOutlineThickness(2.f);
        mShapeBody2.setOrigin((mWidth/2)-(mWidth/15), 0+(mShapeBody2.getHeight()/2));

        mShapeGun = new RectangleShape(mWidth/1.3f, mHeight/8);
        
        mShapeGun.setFillColor(Color.BLACK());
        mShapeGun.setOutlineColor(Color.BLACK());
        mShapeGun.setOutlineThickness(2.f);
        mShapeGun.setOrigin(0.f+(mWidth/10), mShapeGun.getHeight() / 2.f);
        
        mShapeGunEnd = new RectangleShape(mHeight/5, mHeight/4);
        
        mShapeGunEnd.setFillColor(Color.BLACK());
        mShapeGunEnd.setOutlineColor(Color.BLACK());
        mShapeGunEnd.setOutlineThickness(2.f);
        mShapeGunEnd.setOrigin(0.f+(mWidth/10)-mWidth/1.3f, mShapeGunEnd.getHeight() / 2.f);
        
        // Add them to the renderer
        r.add(mShapeTracks);
        r.add(mShapeBody);
        r.add(mShapeBody2);
        r.add(mShapeGun);
        r.add(mShapeGunEnd);
        
    }
    
    @Override
    public void onRenderUpdate(Renderer r) {
        
        super.onRenderUpdate(r);
        
        // Update our render objects
        mShapeTracks.setPosition(mX, mY);
        mShapeTracks.setRotation(mRotation);
        
        mShapeBody.setPosition(mX, mY);
        mShapeBody.setRotation(mRotation);
        
        mShapeBody2.setPosition(mX, mY);
        mShapeBody2.setRotation(mRotation);

        mShapeGun.setPosition(mX, mY);
        mShapeGun.setRotation(mRotation);
        
        mShapeGunEnd.setPosition(mX, mY);
        mShapeGunEnd.setRotation(mRotation);
        
    }
    
    @Override
    public void onRenderRemove(Renderer r) {
        
        super.onRenderRemove(r);
        
        // Remove our render objects
        r.remove(mShapeBody);
        r.remove(mShapeBody2);
        r.remove(mShapeGun);
        r.remove(mShapeTracks);
        r.remove(mShapeGunEnd);
        
    }
    
    public void setBodyColor(Color color) {
        
        if(mShapeBody != null ) {
            mShapeBody.setFillColor(color);
        }
        
        if(mShapeBody2 != null) {
            mShapeBody2.setFillColor(color);
        }
        
    }
    
    @Override
    public int getTypeID() {
        return super.getTypeID() | GameObjectType.SPArtillery.getID();
    }
    
    @Override
    public String getTypeString() {
        return "SPArtillery";
    }
  
}