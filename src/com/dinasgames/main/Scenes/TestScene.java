/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.Scenes;

import com.dinasgames.main.Math.RandomNumber;
import com.dinasgames.main.Math.Vector2f;
import com.dinasgames.main.Objects.Entities.Units.Infantry.Rifleman;
import com.dinasgames.main.Objects.Entities.Units.TestUnit;
import com.dinasgames.main.Objects.Entities.Units.Vehicles.LightTank;



/**
 *
 * @author Jack
 */
public class TestScene extends Scene {
    
    @Override
    public void onCreate() {
        
        // Spawn 5 test units randomly around the screen
        for(int i = 0; i < 5; i ++) {
            TestUnit unit = TestUnit.create();
            unit.setPosition(RandomNumber.between(new Vector2f(0.f,0.f), new Vector2f(1280.f, 720.f)));
        } 
        
        Rifleman rifleman = Rifleman.create();
        rifleman.setPosition(500, 500);
        
        LightTank lightTank = LightTank.create();
        lightTank.setPosition(200, 300);
        
    }
    
}
