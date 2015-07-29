package com.dinasgames.main.objects.entities.units.vehicles.land;

import com.dinasgames.engine.graphics.Color;
import com.dinasgames.engine.graphics.shapes.RectangleShape;
import com.dinasgames.engine.graphics.Renderer;
import com.dinasgames.engine.math.Vector2f;
import com.dinasgames.main.objects.GameObjectType;
import com.dinasgames.main.objects.RenderEvents;
import com.dinasgames.main.objects.SceneEvents;
import com.dinasgames.main.objects.entities.Entity;
import com.dinasgames.main.players.Player;
import com.dinasgames.main.scenes.Scene;
import com.dinasgames.engine.system.Time;

public class Bulldozer extends LandVehicle implements SceneEvents, RenderEvents {
  
    /**
     * The main body for the bulldozer.
     */
    RectangleShape mShapeBody;

    /**
    * The bulldozer cabin
    */
    RectangleShape mShapeCabin;

    /**
    * Bucket connector.
    */
    RectangleShape mShapeBucketConnector;

    /**
    * The bucket shape.
    */
    RectangleShape mShapeBucket;

    /**
    * Tracks.
    */
    RectangleShape mShapeTracks;
  
    /**
     * The default constructor.
     * @param scene 
     */
    public Bulldozer(Scene scene) {
        
        super(scene);
        
        // Setup Unit attributes
        this.mHealthMax     = 100.f;
        this.mHealth        = this.mHealthMax;
        this.mSpeed         = 5.f;
        this.mAttackTime    = Time.seconds(1.f);
        this.mDamage        = 50.f;
        this.mMaxAmmo       = 1;
        this.mRange         = 100.f;
        this.mReloadTime    = Time.seconds(1.f);
        
        // Setup Entity attributes
        this.mWidth     = 20.f;
        this.mHeight    = 10.f;
        
        // Setup listener events
        Bulldozer self = this;
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
        if(mShapeBody != null && mShapeCabin != null && mShapeBucket != null) {
            mShapeBody.setFillColor(color);
            mShapeCabin.setFillColor(color);
            mShapeBucket.setFillColor(color);
        }
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
        mShapeTracks = new RectangleShape( mWidth / 1.2f, mHeight - 8.f );

        mShapeTracks.setFillColor(new Color(20, 20, 20));
        mShapeTracks.setOutlineColor(Color.BLACK);
        mShapeTracks.setOutlineThickness(2.f);
        mShapeTracks.setOriginCenter();

        mShapeBody = new RectangleShape(mWidth, mHeight);

        mShapeBody.setFillColor(mOwnerColor);
        mShapeBody.setOutlineColor(Color.BLACK);
        mShapeBody.setOutlineThickness(2.f);
        mShapeBody.setOriginCenter();

        //mShapeCabin = new RectangleShape(new Vector2f(mShapeBody.getSize()).divide(2.f, 1.f).subtract(4.f));
        mShapeCabin = new RectangleShape(mWidth / 2.f - 4.f, mHeight - 4.f);

        mShapeCabin.setFillColor(mOwnerColor);
        mShapeCabin.setOutlineColor(Color.BLACK);
        mShapeCabin.setOutlineThickness(2.f);
        mShapeCabin.setOrigin( mWidth / 2.f - 2.f , mHeight / 2.f - 2.f);

        //mShapeBucketConnector = new RectangleShape(new Vector2f(mShapeBody.getSize()).divide(6.f, 2.f));
        mShapeBucketConnector = new RectangleShape(mWidth / 6.f, mHeight / 2.f);

        mShapeBucketConnector.setFillColor(Color.BLACK);
        mShapeBucketConnector.setOutlineColor(Color.BLACK);
        mShapeBucketConnector.setOutlineThickness(2.f);
        mShapeBucketConnector.setOrigin(-(mWidth / 2.f), mHeight / 2.f - mHeight / 4.f);

        //mShapeBucket = new RectangleShape(new Vector2f(mShapeBody.getSize().x/6, mShapeBody.getSize().y+6));
        mShapeBucket = new RectangleShape( mWidth / 6.f, mHeight + 6.f );

        mShapeBucket.setFillColor(mOwnerColor);
        mShapeBucket.setOutlineColor(Color.BLACK);
        mShapeBucket.setOutlineThickness(2.f);
        mShapeBucket.setOrigin( -(mWidth / 2.f) - 5.f, mHeight / 2.f + 3.f);
        
        // Add to the renderer
        r.add(mShapeTracks);
        r.add(mShapeBody);
        r.add(mShapeBucketConnector);
        r.add(mShapeCabin);
        r.add(mShapeBucket);
        
    }
    
    @Override
    public void onRenderUpdate(Renderer r) {
        
        super.onRenderUpdate(r);
        
        mShapeTracks.setPosition(mX, mY);
        mShapeTracks.setRotation(mRotation);
        
        mShapeBody.setPosition(mX, mY);
        mShapeBody.setRotation(mRotation);
        
        mShapeCabin.setPosition(mX, mY);
        mShapeCabin.setRotation(mRotation);
        
        mShapeBucketConnector.setPosition(mX, mY);
        mShapeBucketConnector.setRotation(mRotation);
        
        mShapeBucket.setPosition(mX, mY);
        mShapeBucket.setRotation(mRotation);
        
    }
    
    @Override
    public void onRenderRemove(Renderer r) {
        
        super.onRenderRemove(r);
        
        // Remove our render objects
        r.remove(mShapeBody);
        r.remove(mShapeCabin);
        r.remove(mShapeBucketConnector);
        r.remove(mShapeBucket);
        r.remove(mShapeTracks);
        
    }
    
    @Override
    public int getTypeID() {
        return super.getTypeID() | GameObjectType.Bulldozer.getID();
    }
    
    @Override
    public String getTypeString() {
        return "Bulldozer";
    }
  
}