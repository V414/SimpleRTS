package com.dinasgames.main.Scenes;

import com.dinasgames.main.Games.Game;
import com.dinasgames.main.Games.LocalGame;
import com.dinasgames.main.Graphics.Image;
import com.dinasgames.main.Graphics.MapShape;
import com.dinasgames.main.Maps.BlankMap;
import com.dinasgames.main.Maps.FunkyMap;
import com.dinasgames.main.Maps.Map;
import com.dinasgames.main.Maps.SymmetricalMap;
import com.dinasgames.main.Math.RandomNumber;
import com.dinasgames.main.Math.Vector2f;
import com.dinasgames.main.Objects.Entities.Buildings.PowerPlant;
import com.dinasgames.main.Objects.Entities.Units.Infantry.Rifleman;
import com.dinasgames.main.Objects.Entities.Units.Vehicles.LightTank;
import com.dinasgames.main.Players.Player;
import com.dinasgames.main.Players.RemotePlayer;


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
        
        LocalGame localGame = (LocalGame)mGame;
        for(int i = 0; i < localGame.getPlayerList().getMaxPlayers() - 1; i++) {
            localGame.getPlayerList().add(new RemotePlayer());
        }
        
        //Spawn 10 units for the local player
        for(int i = 0; i < 10; i++) {
            Rifleman rifleman = new Rifleman(this);
            rifleman.setPosition(RandomNumber.between(new Vector2f(100.f, 100.f), new Vector2f(300.f, 300.f)));
            rifleman.setOwner(getLocalPlayer());
            
            rifleman.setTargetPosition(rifleman.getPosition());
        }
        
        for(int i = 0; i < localGame.getPlayerList().getMaxPlayers(); i++) {
            LightTank tank = new LightTank(this);
            tank.setPosition(200, 300 + (i * 50));
            tank.setOwner(localGame.getPlayerList().get(i));
        }
        
        // Create a light tank of another team
        //LightTank lightTank = new LightTank(this);
        //lightTank.setPosition(200, 300);
        //lightTank.setOwner(newPlayer);
        //lightTank.setTargetPosition(lightTank.getPosition());
        
        PowerPlant powerPlant = new PowerPlant(this);
        powerPlant.setPosition(500, 400);
        powerPlant.setOwner(getLocalPlayer());
        
        Map map = new SymmetricalMap();
        map.createMap();
        
        
        new MapShape(map).setDepth(100).setScene(this).setRenderer(mRenderer);
        //mRenderer.add(new MapShape(map).setDepth(100).setRenderer(mRenderer).setScene(this));
        //mRenderer.add(new MapShape(map2).setPosition(1000.f, 0.f).setDepth(100).setRenderer(mRenderer).setScene(this));
        //mRenderer.add(new Image(map.getChunkList().get(0).mImage));
        
        return this;
        
    }
    
}
