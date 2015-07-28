/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.objects;

import com.dinasgames.main.scenes.Scene;

/**
 *
 * @author Jack
 */
public interface SceneEvents {
    
    void onSceneAdd( Scene scene );      // << Called when this object is added to a scene
    void onSceneRemove( Scene scene );   // << Called when this object is removed from a scene
    
}
