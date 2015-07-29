package com.dinasgames.engine.system;

import com.dinasgames.engine.graphics.View;
import com.dinasgames.engine.math.Vector2f;
import com.dinasgames.main.scenes.Scene;

/**
 *
 * @author Jack
 */
public class Mouse {
    
    public static enum Button {
        Left,
        Middle,
        Right
    }
    
    public static float windowX = 0;
    public static float windowY = 0;
    public static float x = 0.f;
    public static float y = 0.f;
    
    public static boolean[] mButtonState = new boolean[4];
    
    public static void tick(Scene scene) {
        
        if(scene == null) {
            
            x = windowX;
            y = windowY;
            
            return;
        }
        
        View currentView = scene.getView();
        
        Vector2f camera = currentView.getPosition();
        
        x = windowX + camera.x;
        y = windowY + camera.y;
        
    }
    
    public static Vector2f getPosition() {
        return new Vector2f(x,y);
    }
    
    private static void initMouseState() {
        if(mButtonState == null) {
            for(int i = 0; i < 4; i ++) {
                mButtonState[i] = false;
            }
        }
    }
    
    public static boolean isButtonPressed(Button button) {
        
        initMouseState();
        
        switch(button) {
            
            case Left: {
                return mButtonState[1];
            }
                
            case Middle: {
                return mButtonState[3];
            }
                
            case Right: { 
                return mButtonState[2];
            }
            
        }
        
        return false;
        
    }
    
}
