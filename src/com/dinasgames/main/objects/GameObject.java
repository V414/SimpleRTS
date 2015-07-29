package com.dinasgames.main.objects;

import com.dinasgames.main.scenes.Scene;
import com.dinasgames.engine.math.Vector2f;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jack
 */
public class GameObject {
    
    private boolean mMarkedForDestruction;
    
    protected int mId;                  // << The unique ID given to this object by the scene. A negative ID means that this object hasn't been added to the scene yet
    protected float mX, mY;             // << The position of this object in the scene.
    protected float mRotation;          // << The rotation of this object in degrees
    protected List<Object> mListeners;  // << A list of objects listening for events.
    
    /**
     * A series of events that can be used to detect changes on position, rotation & id.
     */
    public interface Events {
        
        public void onIdChange( int oldId, int newId );
        public void onPositionChange( Vector2f oldPosition, Vector2f newPosition );
        public void onRotationChange( float oldValue, float newValue );
        
    };
    
    /**
     * The default constructor for the GameObject.
     */
    protected GameObject() {
        
        mId         = -1;
        mX          = 0.f;
        mY          = 0.f;
        mRotation   = 0.f;
        mListeners  = new ArrayList();
        
    }
    
    /**
     * A useful constructor to add this object to a scene when it is first created.
     * @param scene 
     */
    protected GameObject(Scene scene) {
        
        this();
        
        if(scene != null) {
            scene.add(this);
        }
        
    }
    
    /**
     * Check whether this object has a type flag. i.e. object.hasType(GameObjectType.Unit)
     * @param type
     * @return 
     */
    public boolean hasType(GameObjectType type) {
        return (getTypeID() & type.getID()) > 0;
    }
    
    /**
     * Get the unique type id of this object. NOTE: This ID contains all the parent types.
     * @return 
     */
    public int getTypeID() {
        return GameObjectType.GameObject.getID();
    }
    
    /**
     * Get the type of this object in string form.
     * @return 
     */
    public String getTypeString() {
        return "GameObject";
    }
    
    /**
     * This function sets the unique ID of the object. NOTE: Don't use this function as it is used internally.
     * @param id 
     */
    public void setID(int id) {
        
        // Check if the ID has changed
        if(id == this.mId) {
            return;
        }
        
        // Call onIdChange event
        for(Object listener : mListeners) {
            if(listener instanceof Events) {
                ((Events)listener).onIdChange(mId, id);
            }
        }
        
        // Update our ID
        this.mId = id;
        
    }
    
    /**
     * Move the object position by offset.
     * @param offset 
     */
    public void move(Vector2f offset) {
        setPosition( mX + offset.x, mY + offset.y );
    }
    
    /**
     * Move the object position by x,y.
     * @param x
     * @param y 
     */
    public void move(float x, float y) {
        setPosition( mX + x, mY + y );
    }
    
    /**
     * Set the position of this object to position.
     * @param position 
     */
    public void setPosition(Vector2f position) {
        setPosition(position.x, position.y);
    }
    
    /**
     * Set the position of this object to x,y.
     * @param x
     * @param y 
     */
    public void setPosition(float x, float y) {
        
        // Check if the position has changed.
        if(this.mX == x && this.mY == y) {
            return;
        }
        
        // Call event
        for(Object listener : mListeners) {
            if(listener instanceof Events) {
                ((Events)listener).onPositionChange( new Vector2f(mX, mY), new Vector2f(x,y) );
            }
        }
        
        // Update our position
        this.mX = x;
        this.mY = y;
        
    }
    
    /**
     * Set the rotation to angle in degrees.
     * @param angle 
     */
    public void setRotation(float angle) {
        
        // Check if it has changed
        if(this.mRotation == angle) {
            return;
        }
        
        // Call event
        for(Object listener : mListeners) {
            if(listener instanceof Events) {
                ((Events)listener).onRotationChange( mRotation, angle );
            }
        }
        
        // Update the value
        this.mRotation = angle;
        
    }
    
    /**
     * Rotate the object by offset degrees.
     * @param offset 
     */
    public void rotate(float offset) {
        setRotation( mRotation + offset );
    }
    
    /**
     * Returns a new Vector2f with the current position value in.
     * @return 
     */
    public Vector2f getPosition() {
        return new Vector2f( mX, mY );
    }
    
    /**
     * Returns the current x coordinate.
     * @return 
     */
    public float getX() {
        return mX;
    }
    
    /**
     * Returns the current y coordinate.
     * @return 
     */
    public float getY() {
        return mY;
    }
    
    /**
     * Returns the current rotation in degrees.
     * @return 
     */
    public float getRotation() {
        return mRotation;
    }
    
    /**
     * Returns the objects unique ID.
     * @return 
     */
    public int getID() {
        return mId;
    }
    
    /**
     * Add a listener object.
     * @param obj 
     */
    public void addListener( Object obj ) {
        mListeners.add(obj);
    }
    
    /**
     * Remove a listener object.
     * @param obj 
     */
    public void removeListener( Object obj ) {
        mListeners.remove(obj);
    }
    
    /**
     * Get a list of objects listening for events.
     * @return 
     */
    public List<Object> getListeners() {
        return mListeners;
    }
    
    public boolean equals(GameObject other) {
        return (other.getID() == mId);
    }
    
    /**
     * Mark this object to be destroyed upon the next game tick.
     */
    public void destroy() {
      mMarkedForDestruction = true;
    }
    
    /**
     * Check if this object has been marked for deletion.
     * @return 
     */
    public boolean isMarkedForDestruction() {
      return mMarkedForDestruction;
    }
    
}
