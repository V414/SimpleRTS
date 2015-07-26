package com.dinasgames.main.Objects;

import com.dinasgames.lwjgl.util.Renderer;
import com.dinasgames.main.Scenes.Scene;
import com.dinasgames.main.Math.Vector2f;

/**
 *
 * @author Jack
 */
public class GameObject {
    
    /**
     * Whether this is a reference to an object.
     */
    protected boolean mIsReference;
    
    /**
     * The unique ID for this object.
     */
    protected int mID;
    
    /**
     * The position of the game object.
     */
    protected Vector2f mPosition;
    
    /**
     * The rotation is degrees of this game object.
     */
    protected float mRotation;
    
    protected Scene mScene;
    protected Renderer mRenderer;
    
    protected GameObject() {
        mPosition = new Vector2f(0.f,0.f);
        mRotation = 0.f;
        mID = -1;
        mIsReference = false;
    }
    
    protected GameObject(Scene scene) {
        this();
        mScene = scene;
        mRenderer = mScene.getRenderer();
    }

    protected GameObject addToScene() {
        if(mScene != null) {
            mScene.add(this);
        }
        return this;
    }
    
    public GameObject setScene(Scene scene) {
        mScene = scene;
        return this;
    }
    
    public GameObject setRenderer(Renderer renderer) {
        mRenderer = renderer;
        return this;
    }
    
    public Scene getScene() {
        return mScene;
    }
    
    public Renderer getRenderer() {
        return mRenderer;
    }
    
    public boolean hasType(GameObjectType type) {
        return (getTypeID() & type.getID()) > 0;
    }
    
    public int getTypeID() {
        return GameObjectType.GameObject.getID();
    }
    
    public String getTypeString() {
        return "GameObject";
    }
    
    // Events
    public void onCreate() {
        
    }
    
    public void onDestroy() {
        // TODO: deselect unit globally!
    }
    
    public void onTick(double time) {
        
    }
    
    public void onRender() {
        
    }
    
    
    
    // Setter methods
    public void setID(int id) {
        mID = id;
    }
    
    public void move(Vector2f offset) {
        mPosition.x += offset.x;
        mPosition.y += offset.y;
    }
    
    public void move(float x, float y) {
        mPosition.x += x;
        mPosition.y += y;
    }
    
    public void setPosition(Vector2f position) {
        mPosition = position;
    }
    
    public void setPosition(float x, float y) {
        mPosition.x = x;
        mPosition.y = y;
    }
    
    public void setRotation(float rotation) {
        mRotation = rotation;
    }
    
    public void rotate(float offset) {
        mRotation += offset;
    }
    
    // Getter methods
    public Vector2f getPosition() {
        return mPosition;
    }
    
    public float getRotation() {
        return mRotation;
    }
    
    public int getID() {
        return mID;
    }
    
    public void destroy() {
        if(mScene != null) {
            mScene.remove(mID);
        }
    }
    
}
