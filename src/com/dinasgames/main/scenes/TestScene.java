package com.dinasgames.main.scenes;

//import com.dinasgames.engine.graphics.MapDrawer;
import com.dinasgames.main.games.Game;
import com.dinasgames.main.games.LocalGame;
import com.dinasgames.main.maps.FunkyMap;
import com.dinasgames.main.maps.Map;
import com.dinasgames.main.maps.SymmetricalMap;
import com.dinasgames.engine.math.Vector2f;
import com.dinasgames.main.objects.entities.buildings.PowerPlant;
import com.dinasgames.main.objects.entities.buildings.Barracks;
import com.dinasgames.main.objects.entities.buildings.OilDerrick;
import com.dinasgames.main.objects.entities.buildings.Warehouse;
import com.dinasgames.main.objects.entities.units.infantry.Bazooka;
import com.dinasgames.main.objects.entities.units.infantry.Flamethrower;
import com.dinasgames.main.objects.entities.units.infantry.Rifleman;
import com.dinasgames.main.objects.entities.units.vehicles.land.Bulldozer;
import com.dinasgames.main.objects.entities.units.vehicles.land.HeavyTank;
import com.dinasgames.main.objects.entities.units.vehicles.land.IFV;
import com.dinasgames.main.objects.entities.units.vehicles.land.LightTank;
import com.dinasgames.main.objects.entities.units.vehicles.land.MediumTank;
import com.dinasgames.main.objects.entities.units.vehicles.land.MissileBattery;
import com.dinasgames.main.objects.entities.units.vehicles.land.SPArtillery;
import com.dinasgames.main.objects.entities.units.vehicles.land.SupplyTruck;
import com.dinasgames.main.players.Player;
import com.dinasgames.main.players.PlayerList;
import com.dinasgames.main.players.RemotePlayer;
import java.util.List;


/**
 * Test Scene, called by test Game
 * @author Jack
 */
public class TestScene extends Scene {
    
    public TestScene(Game game) {
        super(game);
    }
    
    public void spawnPlayerBases( PlayerList players, List<Vector2f> spawnLocations ) {
        for(int i = 0; i < spawnLocations.size(); i++) {
            
            Vector2f spawnLocation = spawnLocations.get(i);
            Player player = players.get(i);
            
            if(spawnLocation != null && player != null) {
                spawnPlayerBase( spawnLocation, player );
            }
            
        }
    }
    
    public void spawnPlayerBase( Vector2f pos, Player owner ) {
        
        // Spawn the player with a bulldozer and a warehouse
        Warehouse w = new Warehouse(this);
        w.setPosition(pos);
        w.setOwner(owner);
        
        PowerPlant p = new PowerPlant(this);
        p.setPosition(pos.x, pos.y - 80.f);
        p.setOwner(owner);
        
        Bulldozer b = new Bulldozer(this);
        b.setPosition(pos.add(0.f, 80.f));
        b.setOwner(owner);
        
        LightTank t = new LightTank(this);
        t.setPosition(pos.x + 40.f, pos.y);
        t.setOwner(owner);
        
        Rifleman r = new Rifleman(this);
        r.setPosition(pos.x - 40.f, pos.y);
        r.setOwner(owner);
        
        Flamethrower f = new Flamethrower(this);
        f.setPosition(pos.x - 80.f, pos.y);
        f.setOwner(owner);
        
        Bazooka bb = new Bazooka(this);
        bb.setPosition(pos.x + 80.f, pos.y);
        bb.setOwner(owner);
        
    }
    
    public void setupMap(Map map) {
        
        if(map == null) {
            return;
        }
        
        // Generate the map
        //map.createMap();
        
        List<Vector2f> spawnLocations = map.getPlayerStartPositions();
        PlayerList playerList = ((LocalGame)mGame).getPlayerList();
        
        // Render the map
//        MapDrawer mapDrawer = new MapDrawer(map);
//        mapDrawer.setScene(this);
//        mapDrawer.render(mRenderer);
        
        // Add some fake players
        for(int i = 0; i < spawnLocations.size() - 1; i++) {
            playerList.add(new RemotePlayer(null));
        }
        
        // Spawn the players
        spawnPlayerBases( playerList, spawnLocations );
        
        // Render the map
        map.setDepth(1000);
        map.render(getRenderer());
        map.getGridShape().render(getRenderer()).setDepth(-1000);
        
    }
    
    @Override
    public Scene onCreate() {
      
        setupMap(new SymmetricalMap());
       
        Barracks barracks = new Barracks(this);
        barracks.setPosition(200, 200);
        barracks.setOwner(getLocalPlayer());
        
        SupplyTruck supplyTruck = new SupplyTruck(this);
        supplyTruck.setPosition(100, 200);
        supplyTruck.setOwner(getLocalPlayer());
        
        OilDerrick oilDerrick = new OilDerrick(this);
        oilDerrick.setPosition(500, 500);
        oilDerrick.setOwner(getLocalPlayer());
        
        MediumTank mediumTank = new MediumTank(this);
        mediumTank.setPosition(300, 300);
        mediumTank.setOwner(getLocalPlayer());
        
        HeavyTank heavyTank = new HeavyTank(this);
        heavyTank.setPosition(300, 200);
        heavyTank.setOwner(getLocalPlayer());
        
        SPArtillery spArt = new SPArtillery(this);
        spArt.setPosition(300, 100);
        spArt.setOwner(getLocalPlayer());
        
        IFV ifv = new IFV(this);
        ifv.setPosition(400, 100);
        ifv.setOwner(getLocalPlayer());
        
        MissileBattery missileBattery = new MissileBattery(this);
        missileBattery.setPosition(200, 100);
        missileBattery.setOwner(getLocalPlayer());
        
        
//      Map map = new SymmetricalMap();
//      map.createMap();
//      Player[] players = new Player[map.getMaxPlayers()];
//      
//      for(int i = 0; i < map.getMaxPlayers()-1; i++){
//        players[i] = new RemotePlayer();
//        ((LocalGame)mGame).getPlayerList().add(players[i]);
//      }
//      
//      List<Vector2f> playerStart = map.getPlayerStart();
//        
//        LocalGame localGame = (LocalGame)mGame;
//        for(int i = 0; i < playerStart.size() - 1; i++) {
//            localGame.getPlayerList().add(new RemotePlayer());
//        }
//        
//        //Spawn 10 units for the local player
//        for(int i = 0; i < 10; i++) {
//            Rifleman rifleman = new Rifleman(this);
//            rifleman.setPosition(RandomNumber.between(new Vector2f(100.f, 100.f), new Vector2f(300.f, 300.f)));
//            rifleman.setOwner(getLocalPlayer());
//            rifleman.setRenderer(mRenderer);
//        }
//        
//        for(int i = 0; i < localGame.getPlayerList().getMaxPlayers(); i++) {
//            LightTank tank = new LightTank(this);
//            tank.setPosition(200, 300 + (i * 50));
//            tank.setTargetPosition(200, 300 + (i*50));
//            tank.setOwner(localGame.getPlayerList().get(i));
//            tank.setRenderer(mRenderer);
//        }
//        
//        Bulldozer bulldozer = new Bulldozer(this);
//        bulldozer.setPosition(600, 400);
//        bulldozer.setTargetPosition(600, 400);
//        bulldozer.setOwner(getLocalPlayer());
//        
//        Flamethrower flamethrower = new Flamethrower(this);
//        flamethrower.setPosition(650, 400);
//        flamethrower.setTargetPosition(650, 400);
//        flamethrower.setOwner(getLocalPlayer());
//        
//        Bazooka bazooka = new Bazooka(this);
//        bazooka.setPosition(500, 400);
//        bazooka.setTargetPosition(500, 400);
//        bazooka.setOwner(getLocalPlayer());
//        
//        // Create a light tank of another team
//        //LightTank lightTank = new LightTank(this);
//        //lightTank.setPosition(200, 300);
//        //lightTank.setOwner(newPlayer);
//        //lightTank.setTargetPosition(lightTank.getPosition());
//        
//        PowerPlant powerPlant1 = new PowerPlant(this);
//        powerPlant1.setPosition(playerStart[0]);
//        powerPlant1.setOwner(getLocalPlayer());
//        
//        Warehouse warehouse = new Warehouse(this);
//        warehouse.setPosition(playerStart[0].x+100, playerStart[0].y-100);
//        warehouse.setOwner(getLocalPlayer());
//        
//        PowerPlant powerPlant2 = new PowerPlant(this);
//        powerPlant2.setPosition(playerStart[1]);
//        powerPlant2.setOwner(players[0]);
//        
//        PowerPlant powerPlant3 = new PowerPlant(this);
//        powerPlant3.setPosition(playerStart[2]);
//        powerPlant3.setOwner(players[1]);
//        
//        PowerPlant powerPlant4 = new PowerPlant(this);
//        powerPlant4.setPosition(playerStart[3]);
//        powerPlant4.setOwner(players[2]);
//
//        
//        MapDrawer mapDrawer = new MapDrawer(map);
//        mapDrawer.setScene(this);
//        mapDrawer.render(mRenderer);
        
        //new MapShape(map).setDepth(100).setScene(this).setRenderer(mRenderer);
        //mRenderer.add(new MapShape(map).setDepth(100).setRenderer(mRenderer).setScene(this));
        //mRenderer.add(new MapShape(map2).setPosition(1000.f, 0.f).setDepth(100).setRenderer(mRenderer).setScene(this));
        //mRenderer.add(new Image(map.getChunkList().get(0).mImage));
        
        return this;
        
    }
    
}
