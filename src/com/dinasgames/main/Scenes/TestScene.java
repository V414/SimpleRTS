package com.dinasgames.main.Scenes;

import com.dinasgames.main.Math.RandomNumber;
import com.dinasgames.main.Math.Vector2f;
import com.dinasgames.main.Objects.Entities.Units.Infantry.Rifleman;
import com.dinasgames.main.Objects.Entities.Units.Vehicles.LightTank;
import com.dinasgames.main.Players.Player;


/**
 * Test Scene, called by test Game
 * @author Jack
 */
public class TestScene extends Scene {
    
    @Override
    public void onCreate() {
        
        //Spawn 10 units for the local player
        for(int i = 0; i < 10; i++) {
            Rifleman rifleman = Rifleman.create();
            rifleman.setPosition(RandomNumber.between(new Vector2f(100.f, 100.f), new Vector2f(300.f, 300.f)));
            rifleman.setOwner(Player.getLocalPlayer());
            
            rifleman.setTargetPosition(rifleman.getPosition());
        }
        
        // Create a light tank of another team
        LightTank lightTank = LightTank.create();
        lightTank.setPosition(200, 300);
        
    }
    
}
