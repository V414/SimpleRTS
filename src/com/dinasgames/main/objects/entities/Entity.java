package com.dinasgames.main.objects.entities;

import com.dinasgames.engine.graphics.Color;
import com.dinasgames.engine.graphics.HealthbarShape;
import com.dinasgames.engine.graphics.Renderer;
import com.dinasgames.engine.math.BoundingBox;
import com.dinasgames.engine.math.Vector2f;
import com.dinasgames.main.behaviours.Behaviour;
import com.dinasgames.main.objects.GameObjectType;
import com.dinasgames.main.objects.LogicEvents;
import com.dinasgames.main.objects.NetworkedObject;
import com.dinasgames.main.objects.RenderEvents;
import com.dinasgames.main.objects.SceneEvents;
import com.dinasgames.main.players.Player;
import com.dinasgames.main.scenes.Scene;
import com.dinasgames.engine.system.Time;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jack
 */
public class Entity extends NetworkedObject implements SceneEvents, RenderEvents, LogicEvents {
    
    protected Player mOwner;                // << The current Player object that owns this Entity.
    protected boolean mSelected;            // << Whether this object is selected or not.
    protected float mHealth;                // << How many health points this Entity has.
    protected float mHealthMax;             // << The maximum amount of health points this Entity can have.
    protected HealthbarShape mHealthbar;    // << The healthbar shape used by this Entity.
    protected float mWidth, mHeight;        // << The size of this Entity.
    protected BoundingBox mBoundingBox;     // << The Bounding Box surrounding this Entity.
    protected boolean mDead;                // << Whether this object is dead.
    protected List<Behaviour> mBehaviours;  // << A list of behaviours that this Entity will execute.
    protected Color mOwnerColor;            // << The team color of our owner.
    
    public interface Events {
        
        public void onHealthChange( float oldHealth, float newHealth );
        public void onMaxHealthChange( float oldMaxHealth, float newMaxHealth );
        public void onNewOwner( Player oldOwner, Player newOwner );
        public void onDeath();
        public void onSizeChange( Vector2f oldSize, Vector2f newSize );
        public void onSelected();
        public void onDeselected();
        
    };
    
    /**
     * The default constructor for an Entity.
     * @param scene 
     */
    protected Entity( Scene scene ) {
        
        super(scene);
        
        mHealth         = 0.f;
        mHealthMax      = 0.f;
        mBoundingBox    = new BoundingBox();
        mDead           = false;
        mSelected       = false;
        mOwner          = null;
        mWidth          = 0.f;
        mHeight         = 0.f;
        mBehaviours     = new ArrayList();
        mOwnerColor     = Color.WHITE;
        
    }
    
    @Override
    public int getTypeID() {
        return super.getTypeID() | GameObjectType.Entity.getID();
    }
    
    @Override
    public String getTypeString() {
        return "Entity";
    }
    
    // Events
    public void onDeath() {
        mDead = true;
    }
    
    @Override
    public void onSceneAdd( Scene s ) {
        
        
    }
    
    @Override
    public void onSceneRemove(Scene scene) {

    }
    
    @Override
    public void onRenderAdd( Renderer r ) {

        // Setup our healthbar
        mHealthbar = new HealthbarShape();
        
        mHealthbar.setHeight(5.f);
        mHealthbar.setFillColor(Color.BLACK);
        mHealthbar.setForegroundColor(Color.RED);
        mHealthbar.setOutlineThickness(1.f);
        mHealthbar.setOutlineColor(Color.BLACK);
        mHealthbar.setHealth(mHealth);
        mHealthbar.setMaxHealth(mHealthMax);
        
        // We want to be in front!
        mHealthbar.setDepth(-100);
        
        // Add our healthbar to the renderer
        r.add(mHealthbar);
        
    }
    
    @Override
    public void onRenderRemove( Renderer r ) {
        
        // Remove our healthvar from the renderer
        r.remove(mHealthbar);
        
    }

    @Override
    public void onTick(Time timePassed) {
        
        // Update the bounding box
        recalculateBoundingBox();
        
        // Update behaviours
        for(Behaviour b : mBehaviours) {
            b.update(timePassed);
        }
        
    }

    @Override
    public void onRenderUpdate( Renderer r ) {
        
        // Update healthbar
        if(mSelected) {
            
            // Update the healthbar position
            mHealthbar.setPosition(mBoundingBox.x, mBoundingBox.y-10);
            
            // Update the healthbar health
            mHealthbar.setHealth(mHealth);
            
            // Update the healthbar size
            mHealthbar.setSize(mBoundingBox.width, mHealthbar.getSize().y);
            
            // Make sure the healthbar is visible
            if(!mHealthbar.isVisible()) {
                mHealthbar.show();
            }
            
        }else{
            
            // Hide the healthbar when this entity isn't selected
            if(mHealthbar.isVisible()) {
                mHealthbar.hide();
            }
            
        }
        
    }
    
    /**
     * Used to calculate the BoundingBox.
     */
    protected void recalculateBoundingBox() {
        //mBoundingBox.setPosition(mPosition.x-mSize.x/2, mPosition.y-mSize.y/2);
        mBoundingBox.setPosition( mX - mWidth / 2.f, mY - mHeight / 2.f );
    }
    
    /**
     * Give this entity an owner.
     * @param owner 
     */
    public void setOwner(Player owner) {
        
        if(owner == null && mOwner == null) {
            return;
        }
        
        if(mOwner != null && this.mOwner.equals(owner)) {
            return;
        }
        
        for(Object listener : mListeners) {
            if(listener instanceof Events) {
                ((Events)listener).onNewOwner( mOwner, owner );
            }
        }
        
        mOwner = owner;
        
        if(mOwner != null) {
            mOwnerColor = mOwner.getColor();
        }else{
            mOwnerColor = Color.WHITE;
        }

    }
    
    /**
     * Returns the current owner of this entity.
     * @return 
     */
    public Player getOwner() {
        return mOwner;
    }
    
    /**
     * Set the size of this entity.
     * @param width
     * @param height 
     */
    public void setSize(float width, float height) {
        
        if(mWidth == width && mHeight == height) {
            return;
        }
        
        for(Object listener : mListeners) {
            if(listener instanceof Events) {
                ((Events)listener).onSizeChange( new Vector2f(mWidth, mHeight), new Vector2f(width, height) );
            }
        }
        
        mWidth = width;
        mHeight = height;
        
    }
    
    /**
     * Alternative to setSize(float,float).
     * @param size 
     */
    public void setSize(Vector2f size) {
        setSize(size.x,size.y);
    }
    
    /**
     * Scale the object using a factor.
     * @param xfactor
     * @param yfactor 
     */
    public void scale(float xfactor, float yfactor) {
        setSize( mWidth * xfactor, mHeight * yfactor );
    }
    
    /**
     * Scale the object using a factor.
     * @param factor 
     */
    public void scale(Vector2f factor) {
        scale(factor.x, factor.y);
    }
    
    /**
     * Get the width of this object.
     * @return 
     */
    public float getWidth() {
        return mWidth;
    }
    
    /**
     * Get the height of this object.
     * @return 
     */
    public float getHeight() {
        return mHeight;
    }
    
    /**
     * Get the size of this object.
     * @return 
     */
    public Vector2f getSize() {
        return new Vector2f(mWidth, mHeight);
    }
    
    /**
     * Select this Entity.
     */
    public void select() {
        
        if(mSelected) {
            return;
        }
        
        for(Object listener : mListeners) {
            if(listener instanceof Events) {
                ((Events)listener).onSelected();
            }
        }
        
        mSelected = true;
        
    }
    
    /**
     * Deselect this Entity.
     */
    public void deselect() {
        
        if(!mSelected) {
            return;
        }
        
        for(Object listener : mListeners) {
            if(listener instanceof Events) {
                ((Events)listener).onDeselected();
            }
        }
        
        mSelected = false;
        
    }
    
    /**
     * Returns true if this object is selected.
     * @return 
     */
    public boolean isSelected() {
        return mSelected;
    }
    
    /**
     * Set the selected value.
     * @param selected 
     */
    public void setSelected(boolean selected) {
        
        if(selected) {
            select();
        }else{
            deselect();
        }
        
    }
    
    /**
     * Get the current bounding box.
     * @return 
     */
    public BoundingBox getBoundingBox() {
        return mBoundingBox;
    }
    
    /**
     * Check whether this object is dead.
     * @return 
     */
    public boolean isDead() {
        return mDead;
    }
    
    /**
     * Get the current health value.
     * @return 
     */
    public float getHealth() {
        return mHealth;
    }
    
    /**
     * Get the maximum health this object can have.
     * @return 
     */
    public float getMaxHealth() {
        return mHealthMax;
    }
    
    /**
     * Set the health value for this object. Note: You cannot change the health of a dead Entity.
     * @param hp 
     */
    public void setHealth(float hp) {
        
        if(mDead) {
            return;
        }
        
        if(mHealth == hp) {
            return;
        }
        
        hp = Math.max( 0.f, Math.min( mHealthMax, hp ) );
        
        for(Object listener : mListeners) {
            if(listener instanceof Events) {
                ((Events)listener).onHealthChange( mHealth, hp );
            }
        }
        
        mHealth = hp;
        
        // Check if this Entity is dead
        if(mHealth <= 0.f) {
            
            mDead = true;
            
            for(Object listener : mListeners) {
                if(listener instanceof Events) {
                    ((Events)listener).onDeath();
                }
            }
            
        }
        
    }
    
    /**
     * Set the maximum health an Entity can have. Note: You cannot change the maximum health value of a dead Entity.
     * @param max 
     */
    public void setMaxHealth(float max) {
        
        if(mDead) {
            return;
        }
        
        if(mHealthMax == max) {
            return;
        }
        
        for(Object listener : mListeners) {
            if(listener instanceof Events) {
                ((Events)listener).onMaxHealthChange( mHealthMax, max );
            }
        }
        
        mHealthMax = max;
        
    }
    
    /**
     * Apply damage to an Entity. Note: A negative value will heal the Entity. Damage cannot be applied to a dead Entity.
     * @param amount 
     */
    public void damage(float amount) {
        setHealth( mHealth - amount );
    }
    
    /**
     * Apply healing to an Entity. Note: A negative value will damage the Entity. Healing cannot be applied to a dead Entity.
     * @param amount 
     */
    public void heal(float amount) {
        setHealth( mHealth + amount );
    }
    
    /**
     * Kill an entity. Note: This function has no effect on a dead Entity.
     */
    public void kill() {
        setHealth(0.f);
    }
    
    /**
     * Check whether this object has a certain behaviour.
     * @param other
     * @return 
     */
    public boolean hasBehaviour(Behaviour other) {
        for(Behaviour b : mBehaviours) {
            if(b.equals(other)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Check whether this object has a certain behaviour.
     * @param type
     * @return 
     */
    public boolean hasBehaviour(int type) {
        for(Behaviour b : mBehaviours) {
            if(b.getUniqueID() == type) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Get the behaviour of type for this object.
     * @param type
     * @return 
     */
    public Behaviour getBehaviour(int type) {
        for(Behaviour b : mBehaviours) {
            if(b.getUniqueID() == type) {
                return b;
            }
        }
        return null;
    }
    
/**
     * Get the behaviour of type for this object.
     * @param type
     * @return 
     */
    public void removeBehaviour(int type) {
        for(Behaviour b : mBehaviours) {
            if(b.getUniqueID() == type) {
                mBehaviours.remove(b);
                return;
            }
        }
    }
    
    /**
     * Add a new type of behaviour to this object.
     * @param b 
     */
    public void addBehaviour(Behaviour b) {
        
        if(hasBehaviour(b)) {
            return;
        }
        
        mBehaviours.add(b);
        
    }
    
}
