/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.objects.entities.units.infantry;

import com.dinasgames.engine.graphics.shapes.CircleShape;
import com.dinasgames.engine.graphics.Color;
import com.dinasgames.engine.graphics.shapes.RectangleShape;
import com.dinasgames.engine.graphics.Renderer;
import com.dinasgames.engine.math.Point;
import com.dinasgames.engine.math.Vector2f;
import com.dinasgames.main.objects.GameObjectType;
import com.dinasgames.main.objects.entities.Entity;
import com.dinasgames.main.players.Player;
import com.dinasgames.main.scenes.Scene;
import com.dinasgames.engine.system.Time;

public class Flamethrower extends Infantry {

    /**
     * The soldiers body
     */
    protected CircleShape mShapeBody;
    
    /**
     * Gun shape
     */
    protected RectangleShape mShapeGun;
    
    /**
     * The end of the gun shape
     */
    protected RectangleShape mShapeGunEnd;
    
    /**
     * The back pack.
     */
    protected RectangleShape mShapeBackpack;
    
    /**
     * The current gun angle in degrees.
     */
    protected float mGunRotation;
    
    /**
     * The default constructor.
     * @param scene 
     */
    public Flamethrower(Scene scene) {
        
        super(scene);
        
        // Setup Rifleman attributes
        this.mGunRotation = 270.f;
        
        // Setup Unit attributes
        this.mHealthMax     = 100.f;
        this.mHealth        = this.mHealthMax;
        this.mSpeed         = 10.f;
        this.mAttackTime    = Time.seconds(.1f);
        this.mDamage        = 3.f;
        this.mMaxAmmo       = 1;
        this.mRange         = 50.f;
        this.mReloadTime    = Time.seconds(.5f);
        
        // Setup Entity attributes
        this.mWidth     = 10.f;
        this.mHeight    = 10.f;
        
        // Setup listener events
        Flamethrower self = this;
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
        mShapeBody = new CircleShape(mWidth / 2.f);
        
        mShapeBody.setFillColor(mOwnerColor);
        mShapeBody.setOutlineColor(Color.BLACK);
        mShapeBody.setOutlineThickness(2.f);
        mShapeBody.setOriginCenter();
        
        mShapeGun = new RectangleShape(2.f, 10.f);
        
        mShapeGun.setFillColor(Color.BLACK);
        mShapeGun.setOutlineColor(Color.BLACK);
        mShapeGun.setOutlineThickness(0.f);
        //mShapeGun.setOrigin(mShapeBody.getPosition().x, mShapeBody.getPosition().y/2);
        mShapeGun.setOrigin(0.f, mHeight / 2.f);
        
        mShapeGunEnd = new RectangleShape( 4.f, 4.f );
        
        mShapeGunEnd.setFillColor(Color.BLACK);
        mShapeGunEnd.setOutlineColor(Color.BLACK);
        mShapeGunEnd.setOutlineThickness(0.f);
        //mShapeGunEnd.setOrigin(mShapeBody.getPosition().x+mGunSize.x/2, mShapeBody.getPosition().y/2-mGunSize.y+mGunSize.x*1.5f);
        mShapeGunEnd.setOrigin( mWidth / 2.f, - mHeight + mWidth * 1.5f ); // WTF!?
        
        mShapeBackpack = new RectangleShape( 1.f, 5.f );
        
        mShapeBackpack.setFillColor(Color.BLACK);
        mShapeBackpack.setOutlineColor(Color.BLACK);
        mShapeBackpack.setOutlineThickness(0.f);
        //mShapeBackpack.setOrigin(mSize.x-(mSize.x/3), mSize.y/2);
        mShapeBackpack.setOrigin( mWidth - (mWidth / 3.f), mHeight / 2.f );
        
        // Add them to the renderer
        r.add(mShapeBody);
        r.add(mShapeGun);
        r.add(mShapeGunEnd);
        r.add(mShapeBackpack);
        
    }
    
    @Override
    public void onRenderUpdate(Renderer r) {
        
        super.onRenderUpdate(r);
        
        mShapeBody.setPosition(mX,mY);
        mShapeBody.setRotation(mRotation);
        
        mShapeGun.setPosition(mX,mY);
        mShapeGun.setRotation(mRotation + mGunRotation);
        
        mShapeGunEnd.setPosition(mX,mY);
        mShapeGunEnd.setRotation(mRotation + mGunRotation);
        
        mShapeBackpack.setPosition(mX,mY);
        mShapeBackpack.setRotation(mRotation);
        
    }
    
    @Override
    public void onRenderRemove(Renderer r) {
        
        super.onRenderRemove(r);
        
        // Remove our render objects
        r.remove(mShapeBody);
        r.remove(mShapeGun);
        r.remove(mShapeGunEnd);
        r.remove(mShapeBackpack);
        
    }
    
    public void setBodyColor(Color color) {
        if(mShapeBody != null) {
            mShapeBody.setFillColor(color);
        }
    }
    
    @Override
    public int getTypeID() {
        return super.getTypeID() | GameObjectType.Flamethrower.getID();
    }
    
    @Override
    public String getTypeString() {
        return "Flamethrower";
    }

}