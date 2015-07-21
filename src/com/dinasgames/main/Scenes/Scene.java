package com.dinasgames.main.Scenes;

import com.dinasgames.main.Camera;
import com.dinasgames.main.Games.Game;
import com.dinasgames.main.Games.LocalGame;
import com.dinasgames.main.Games.SimpleGame;
import com.dinasgames.main.Networking.Network;
import com.dinasgames.main.Objects.GameObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to store and manage game objects.
 * @author Jack
 */
public class Scene {
    
    public static final int MAX_OBJECTS = 1024;
    
    protected Camera mCamera;
    protected GameObject[] mObjects;
    
    public static Scene getCurrent() {
        return ((SimpleGame)Game.current).getScene();
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
    
    public static <T extends GameObject> List<T> findObjects() {
        List<T> list = new ArrayList();
        for(int i = 0; i < MAX_OBJECTS; i++) {
            if(getCurrent().mObjects[i] != null) {
                T cast = null;
                try {
                    cast = (T)getCurrent().mObjects[i];
                }
                catch(Exception e) {
                    continue;
                }
                if(cast != null) {
                    list.add(cast);
                }
            }
        }
        return list;
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
