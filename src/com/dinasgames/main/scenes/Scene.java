package com.dinasgames.main.scenes;

import com.dinasgames.lwjgl.util.Renderer;
import com.dinasgames.lwjgl.util.View;
import com.dinasgames.main.games.Game;
import com.dinasgames.main.games.LocalGame;
import com.dinasgames.main.games.WindowGame;
import com.dinasgames.main.objects.GameObject;
import com.dinasgames.main.objects.GameObjectType;
import com.dinasgames.main.players.LocalPlayer;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to store and manage game objects.
 * @author Jack
 */
public class Scene {
    
    public static final int MAX_OBJECTS = 1024;
    
    protected Renderer mRenderer;
    protected Game mGame;
    protected GameObject[] mObjects;
    
    protected Scene() {
        mRenderer = null;
        mGame = null;
        mObjects = new GameObject[MAX_OBJECTS];
        
        for(int i = 0; i < MAX_OBJECTS; i ++) {
            mObjects[i] = null;
        }
        
    }
    
    protected Scene(Game game) {
        this();
        mGame = game;
        
        // Attempt to get the renderer
        try {
            WindowGame windowGame = (WindowGame)mGame;
            if(windowGame != null) {
                mRenderer = windowGame.getRenderer();
            }
        } catch( Exception e ) {
            System.out.println("No renderer present.");
        }
        
        // Attempt to get the window size
//        try {
//            WindowGame windowGame = (WindowGame)mGame;
//            if(game != null) {
//                //Dimension windowSize = windowGame.getWindow().getFrame().getSize();
//                //mCamera.setSize(new Vector2f(windowSize.width, windowSize.height));
//            }
//        } catch(Exception e) {
//            // Ignore
//        }
        
    }
    
    public Game getGame() {
        return mGame;
    }
    
    public Scene setGame(Game game) {
        mGame = game;
        return this;
    }
    
    // Events
    public Scene onCreate() {
        return this;
    }
    
    public Scene clear() {
        for(int i = 0; i < MAX_OBJECTS; i ++) {
            if(mObjects[i] != null) {
                mObjects[i].onDestroy();
                mObjects[i] = null;
            }
        }
        return this;
    }
    
    public <T extends GameObject> List<T> findObjects() {
        List<T> list = new ArrayList();
        for(int i = 0; i < MAX_OBJECTS; i++) {
            if(mObjects[i] != null) {
                T cast = null;
                try {
                    cast = (T)mObjects[i];
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
                
                obj.setScene(this);
                obj.setID(i);
                obj.onCreate();
                
                return i;
            }
        }
        //mObjects.add(obj);
        //obj.onCreate();
        return -1;
    }
    
    public Scene remove(GameObject obj) {
        //mObjects.remove(obj);
        for(int i = 0; i < MAX_OBJECTS; i ++) {
            if(mObjects[i] == obj) {
                mObjects[i].onDestroy();
                mObjects[i] = null;
            }
        }
        return this;
    }
    
    public Scene remove(int idx) {
        //mObjects.remove(idx);
        if(idx < 0 || idx >= MAX_OBJECTS) {
            return this;
        }
        if(mObjects[idx] != null) {
            mObjects[idx].onDestroy();
        }
        mObjects[idx] = null;
        return this;
    }
    
    public GameObject get(int idx) {
        if(idx < 0 || idx >= MAX_OBJECTS) {
            return null;
        }
        return mObjects[idx];
    }
    
    public Scene tick(double time) {
        
        // Update camera
        //mCamera.tick();
        
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
        
        return this;
        
    }

    public Scene setView(View view) {
        try {
            ((WindowGame)mGame).getWindow().setView(view);
        } catch( Exception e ) {}
        return this;
    }
    
    public View getView() {
        try {
            return ((WindowGame)mGame).getWindow().getView();
        } catch( Exception e ) {}
        return null;
    }
    
    public LocalPlayer getLocalPlayer() {
        if(mGame == null) {
            return null;
        }
        return ((LocalGame)mGame).getPlayer();
    }
    
    public Scene setRenderer(Renderer renderer) {
        mRenderer = renderer;
        return this;
    }
    
    public Renderer getRenderer() {
        return mRenderer;
    }
    
    public GameObject[] getObjectsList(){
      return mObjects;
    }
    
}
