package com.dinasgames.main.objects.commands;

import com.dinasgames.main.math.Vector2f;
import com.dinasgames.main.objects.GameObjectType;
import com.dinasgames.main.objects.behaviours.BLoadAtResource;
import com.dinasgames.main.objects.behaviours.BMoveTo;
import com.dinasgames.main.objects.behaviours.BUnloadAtWarehouse;
import com.dinasgames.main.objects.entities.Entity;
import com.dinasgames.main.objects.entities.buildings.OilDerrick;
import com.dinasgames.main.objects.entities.buildings.Warehouse;
import com.dinasgames.main.objects.entities.units.vehicles.land.SupplyTruck;

public class MoveToResourceCommand extends Command {
    
    protected OilDerrick oilDerrick;
    protected Warehouse warehouse;
    
    public MoveToResourceCommand( OilDerrick oilDerrick, Warehouse warehouse) {
        this.oilDerrick = oilDerrick;
        this.warehouse = warehouse;
    }
    
    @Override
    public void issue(Object self) {
        
        super.issue(self);
        
        if(self instanceof SupplyTruck) {
            
            SupplyTruck e = (SupplyTruck) self;
            
            // Ensure this unit doesn't already have a MoveTo behaviour
            e.removeBehaviour(BMoveTo.ID);
            e.removeBehaviour(BUnloadAtWarehouse.ID);
            e.removeBehaviour(BLoadAtResource.ID);
            
            // Make the object move to x,y
            
            if(e.getCarryingAmount() >= e.getCarryingMax()){
              e.addBehaviour(new BUnloadAtWarehouse(e, warehouse, oilDerrick));
            }else{
              e.addBehaviour(new BLoadAtResource(e, warehouse, oilDerrick));
            }
            
        }
        
    }
    
}
