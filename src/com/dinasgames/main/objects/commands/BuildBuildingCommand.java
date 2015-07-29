package com.dinasgames.main.objects.commands;

import com.dinasgames.main.math.Vector2f;
import com.dinasgames.main.objects.GameObjectType;
import com.dinasgames.main.objects.behaviours.BBuildBuilding;
import com.dinasgames.main.objects.behaviours.BLoadAtResource;
import com.dinasgames.main.objects.behaviours.BMoveTo;
import com.dinasgames.main.objects.behaviours.BUnloadAtWarehouse;
import com.dinasgames.main.objects.entities.Entity;
import com.dinasgames.main.objects.entities.buildings.NewBuilding;
import com.dinasgames.main.objects.entities.buildings.OilDerrick;
import com.dinasgames.main.objects.entities.buildings.Warehouse;
import com.dinasgames.main.objects.entities.units.vehicles.land.Bulldozer;
import com.dinasgames.main.objects.entities.units.vehicles.land.SupplyTruck;
import com.dinasgames.main.scenes.Scene;

public class BuildBuildingCommand extends Command {
    
    protected NewBuilding newBuilding;
    protected Scene scene;
    
    public BuildBuildingCommand(NewBuilding newBuilding, Scene scene) {
        this.newBuilding = newBuilding;
        this.scene = scene;
    }
    
    @Override
    public void issue(Object self) {
        
        super.issue(self);
        
        if(self instanceof Bulldozer) {
            
            Bulldozer e = (Bulldozer) self;
            
            // Ensure this unit doesn't already have a MoveTo behaviour
            e.removeBehaviour(BMoveTo.ID);
            e.removeBehaviour(BBuildBuilding.ID);
            
            // Make the object move to x,y
            
            e.addBehaviour(new BBuildBuilding(e, scene, newBuilding));
        }
        
    }
    
}
