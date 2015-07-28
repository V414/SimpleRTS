package com.dinasgames.main.objects.entities.units.vehicles.land;

import com.dinasgames.lwjgl.util.Color;
import com.dinasgames.lwjgl.util.RectangleShape;
import com.dinasgames.lwjgl.util.Renderer;
import com.dinasgames.main.World;
import com.dinasgames.main.math.Point;
import com.dinasgames.main.math.RandomNumber;
import com.dinasgames.main.math.Vector2f;
import com.dinasgames.main.objects.GameObjectType;
import com.dinasgames.main.objects.RenderEvents;
import com.dinasgames.main.objects.SceneEvents;
import com.dinasgames.main.objects.entities.Entity;
import com.dinasgames.main.players.Player;
import com.dinasgames.main.scenes.Scene;
import com.dinasgames.main.system.Time;

public class LightTank extends LandVehicle implements RenderEvents, SceneEvents {
  
    /**
     * The main body of the tank.
     */
    protected RectangleShape mShapeBody;
    
    /**
     * The turret body.
     */
    protected RectangleShape mShapeBody2;
    
    /**
     * The tank turret.
     */
    protected RectangleShape mShapeTurret;
    
    /**
     * The tank tracks.
     */
    protected RectangleShape mShapeTracks;
    
    /**
     * The rotation of the turret.
     */
    protected float mTurretRotation;          // << The rotation of the turret
  
    public LightTank( Scene scene ) {
        
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
        this.mSpeed         = 30.f;
        this.mAcceleration  = 34.7f;
        this.mDeceleration  = 30.f;
        
        // Setup Entity attributes
        this.mWidth     = 35.f;
        this.mHeight    = 15.f;
        
        // Setup listener events
        LightTank self = this;
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
        mShapeTracks.setOutlineColor(Color.BLACK);
        mShapeTracks.setOutlineThickness(2.f);
        mShapeTracks.setOriginCenter();
        
        mShapeBody = new RectangleShape(mWidth, mHeight);
        
        mShapeBody.setFillColor(mOwnerColor);
        mShapeBody.setOutlineColor(Color.BLACK);
        mShapeBody.setOutlineThickness(2.f);
        mShapeBody.setRotation(RandomNumber.between(0.f, 360.f));
        mShapeBody.setOriginCenter();
        
        mShapeBody2 = new RectangleShape( mWidth / 2.f, mHeight - 4.f );
        //new Vector2f(mShapeBody.getSize()).divide(2.f,1.f).subtract(0.f,4.f));
        
        mShapeBody2.setFillColor(mOwnerColor);
        mShapeBody2.setOutlineColor(Color.BLACK);
        mShapeBody2.setOutlineThickness(2.f);
        mShapeBody2.setOriginCenter();
        
        mShapeTurret = new RectangleShape(mShapeBody.getWidth() / 2.f, mShapeBody2.getHeight() / 4.f);
        
        mShapeTurret.setFillColor(Color.BLACK);
        mShapeTurret.setOutlineColor(Color.BLACK);
        mShapeTurret.setOutlineThickness(2.f);
        mShapeTurret.setOrigin(0.f, mShapeTurret.getHeight() / 2.f);
        
        // Add them to the renderer
        r.add(mShapeTracks);
        r.add(mShapeBody);
        r.add(mShapeBody2);
        r.add(mShapeTurret);
        
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
        
        // Move the turret so that it is at the end of the 2nd body shape
        
        // First take the current position
        Vector2f turretPosition = new Vector2f(mX, mY);
        
        // Then move it so that it is in front of our 2nd body shape
        turretPosition.add(Point.inDirection( mShapeBody2.getWidth() / 2.f, -mShapeBody2.getRotation()));
        
        // Finally apply the new position
        mShapeTurret.setPosition(turretPosition);
        mShapeTurret.setRotation(mShapeBody2.getRotation());
        
    }
    
    @Override
    public void onRenderRemove(Renderer r) {
        
        super.onRenderRemove(r);
        
        // Remove our render objects
        r.remove(mShapeBody);
        r.remove(mShapeBody2);
        r.remove(mShapeTurret);
        r.remove(mShapeTracks);
        
    }
    
    public void setBodyColor(Color color) {
        
        if(mShapeBody != null ) {
            mShapeBody.setFillColor(color);
        }
        
        if(mShapeBody2 != null) {
            mShapeBody2.setFillColor(color);
        }
        
    }
    

    
//    @Override
//    public void onDestroy() {
//      mShapeTracks.remove();
//      mShapeBody.remove();
//      mShapeBody2.remove();
//      mShapeTurret.remove();
//      mHealthbar.remove();
//    }
//    
//    @Override
//    public void onRender() {
//        
//        super.onRender();
//        
//        mShapeTracks.setPosition(mPosition);
//        mShapeTracks.setRotation(mRotation);
//        
//        mShapeBody.setPosition(mPosition);
//        mShapeBody.setRotation(mRotation);
//        
//        mShapeBody2.setPosition(mPosition);
//        
//        if(target != null){
//          double b = target.getPosition().y - mPosition.y;
//          double a = target.getPosition().x - mPosition.x;
//          float targetAngle = Math.round(Math.toDegrees(Math.atan2(b, a)));
//          
//          if(targetAngle < 0){
//              targetAngle += 360;
//          }
//          
//          mShapeBody2.setRotation(targetAngle);
//        }else{
//           mShapeBody2.setRotation(mTurretRotation);
//        }
//        
//       
//        
//        // Move the turret so that it is at the end of the 2nd body shape
//        
//        // First take the current position
//        Vector2f turretPosition = new Vector2f(mPosition);
//        
//        // Then move it so that it is in front of our 2nd body shape
//        turretPosition.add(Point.inDirection( mShapeBody2.getWidth() / 2.f, -mShapeBody2.getRotation()));
//        
//        // Finally apply the new position
//        mShapeTurret.setPosition(turretPosition);
//        mShapeTurret.setRotation(mShapeBody2.getRotation());
//        
//        // Move the turret back a bit
//        //mShapeTurret.setPosition(Point.inDirection(-10.f, mRotation));
//        
//    }
    
    @Override
    public int getTypeID() {
        return super.getTypeID() | GameObjectType.LightTank.getID();
    }
    
    @Override
    public String getTypeString() {
        return "LightTank";
    }
  
}