package com.dinasgames.main.Scenes;

import com.dinasgames.main.Games.Game;
import com.dinasgames.main.Graphics.Image;
import com.dinasgames.main.Graphics.MapShape;
import com.dinasgames.main.Maps.BlankMap;
import com.dinasgames.main.Maps.FunkyMap;
import com.dinasgames.main.Maps.Map;
import com.dinasgames.main.Math.RandomNumber;
import com.dinasgames.main.Math.Vector2f;
import com.dinasgames.main.Objects.Entities.Buildings.PowerPlant;
import com.dinasgames.main.Objects.Entities.Units.Infantry.Rifleman;
import com.dinasgames.main.Objects.Entities.Units.Vehicles.LightTank;


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
        
        Map map = new FunkyMap();
        map.createMap();
        
        Map map2 = new BlankMap();
        map2.createMap();
        
        new MapShape(map).setDepth(100).setScene(this).setRenderer(mRenderer);
        //mRenderer.add(new MapShape(map).setDepth(100).setRenderer(mRenderer).setScene(this));
        //mRenderer.add(new MapShape(map2).setPosition(1000.f, 0.f).setDepth(100).setRenderer(mRenderer).setScene(this));
        //mRenderer.add(new Image(map.getChunkList().get(0).mImage));
        
        return this;
        
    }
    
}
