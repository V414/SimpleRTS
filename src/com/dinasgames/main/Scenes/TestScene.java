package com.dinasgames.main.Scenes;

import com.dinasgames.main.Games.Game;
import com.dinasgames.main.Math.RandomNumber;
import com.dinasgames.main.Math.Vector2f;
import com.dinasgames.main.Objects.Entities.Buildings.PowerPlant;
import com.dinasgames.main.Objects.Entities.Units.Infantry.Rifleman;
import com.dinasgames.main.Objects.Entities.Units.Vehicles.LightTank;
import com.dinasgames.main.Players.Player;


/**
 * Test Scene, called by test Game
 * @author Jack
 */
public class TestScene extends Scene {
    
    public TestScene(Game game) {
        super(game);
    }
    
    @Override
    public Scene onCreate() {
        
        //Spawn 10 units for the local player
        for(int i = 0; i < 10; i++) {
            Rifleman rifleman = new Rifleman(this);
            rifleman.setPosition(RandomNumber.between(new Vector2f(100.f, 100.f), new Vector2f(300.f, 300.f)));
            rifleman.setOwner(getLocalPlayer());
            
            rifleman.setTargetPosition(rifleman.getPosition());
        }
        
        // Create a light tank of another team
        LightTank lightTank = new LightTank(this);
        lightTank.setPosition(200, 300);
        lightTank.setOwner(getLocalPlayer());
        lightTank.setTargetPosition(lightTank.getPosition());
        
        PowerPlant powerPlant = new PowerPlant(this);
        powerPlant.setPosition(500, 400);
        powerPlant.setOwner(getLocalPlayer());
        
        
        return this;
        
    }
    
}
