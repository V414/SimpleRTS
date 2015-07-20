/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.Scenes;

import com.dinasgames.main.Camera;
import com.dinasgames.main.Game;
import com.dinasgames.main.Objects.GameObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Iterator;

/**
 * Class used to store and manage game objects.
 * @author Jack
 */
public class Scene {
    
    public final int MAX_OBJECTS = 1024;
    
    protected Camera mCamera;
    protected GameObject[] mObjects;
    
    public static Scene getCurrent() {
        return Game.current.getScene();
    }
    
    public Scene() {
        mObjects = new GameObject[MAX_OBJECTS];
        //mObjects = Collections.synchronizedList(new ArrayList<GameObject>());
        mCamera = new Camera();
        
        for(int i = 0; i < MAX_OBJECTS; i ++) {
            mObjects[i] = null;
        }
        
    }
    
    // Events
    public void onCreate() {
        
    }
    
    public void clear() {
        for(int i = 0; i < MAX_OBJECTS; i ++) {
            if(mObjects[i] != null) {
                mObjects[i].onDestroy();
                mObjects[i] = null;
            }
        }
    }
    
    
    
    public int add(GameObject obj) {
        for(int i = 0; i < MAX_OBJECTS; i ++) {
            if(mObjects[i] == null) {
                
                mObjects[i] = obj;
                
                obj.setID(i);
                obj.onCreate();
                
                return i;
            }
        }
        //mObjects.add(obj);
        //obj.onCreate();
        return -1;
    }
    
    public void remove(GameObject obj) {
        //mObjects.remove(obj);
        for(int i = 0; i < MAX_OBJECTS; i ++) {
            if(mObjects[i] == obj) {
                mObjects[i].onDestroy();
                mObjects[i] = null;
            }
        }
    }
    
    public void remove(int idx) {
        //mObjects.remove(idx);
        if(idx < 0 || idx >= MAX_OBJECTS) {
            return;
        }
        if(mObjects[idx] != null) {
            mObjects[idx].onDestroy();
        }
        mObjects[idx] = null;
    }
    
    public GameObject get(int idx) {
        if(idx < 0 || idx >= MAX_OBJECTS) {
            return null;
        }
        return mObjects[idx];
    }
    
    public void tick(double time) {
        
        // Update camera
        mCamera.tick();
        
        // Update game objects
//        synchronized(mObjects) {
//            Iterator<GameObject> it = mObjects.iterator();
//            while(it.hasNext()) {
//                GameObject o = it.next();
//                o.onTick(time);
//                o.onRender();
//            }
//        }
        
        synchronized(mObjects) {
            for(int i = 0; i < MAX_OBJECTS; i ++) {
                if(mObjects[i] != null) {
                    mObjects[i].onTick(time);
                    mObjects[i].onRender();
                }
            }
        }
        
    }
    
    public Camera getCamera() {
        return mCamera;
    }
    
}