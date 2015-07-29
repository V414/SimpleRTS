package com.dinasgames.main.objects.behaviours;

import com.dinasgames.main.World;
import com.dinasgames.main.math.Point;
import com.dinasgames.main.objects.GameObject;
import static com.dinasgames.main.objects.behaviours.BLoadAtResource.ID;
import com.dinasgames.main.objects.commands.MoveToResourceCommand;
import com.dinasgames.main.objects.entities.buildings.OilDerrick;
import com.dinasgames.main.objects.entities.buildings.Warehouse;
import com.dinasgames.main.objects.entities.units.Unit;
import com.dinasgames.main.objects.entities.units.vehicles.land.SupplyTruck;
import com.dinasgames.main.system.Time;
import com.dinasgames.net.StateValue;

public class BUnloadAtWarehouse extends Behaviour {
    
    public static int ID = 2;
    private SupplyTruck supplyTruck;
    private final Warehouse warehouse;
    private final OilDerrick oilDerrick;
    
  /**
   * Default constructor.
   * @param self
   * @param warehouse
   * @param oilDerrick
   */
    public BUnloadAtWarehouse( Object self, Warehouse warehouse, OilDerrick oilDerrick ) {
        
      super(self);
      this.warehouse = warehouse;
      this.oilDerrick = oilDerrick;

      if(self != null && self instanceof SupplyTruck) {

        supplyTruck = (SupplyTruck) self;
        
        supplyTruck.addBehaviour(new BMoveTo(self, warehouse.getX(), warehouse.getY()));

      }
        
    }
    
    @Override
    public void update(Time timePassed) {
      int radius = 40;
      float truckX = supplyTruck.getX()+supplyTruck.getWidth()/2;
      float truckY = supplyTruck.getY()+supplyTruck.getHeight()/2;

      float targetX = warehouse.getX()+warehouse.getWidth()/2;
      float targetY = warehouse.getY()+warehouse.getHeight()/2;

      if(truckX > targetX-radius && truckX < targetX+radius &&
              truckY > targetY-radius && truckY < targetY+radius){
        supplyTruck.setCarryingAmount(0);
        
        supplyTruck.issueCommand(new MoveToResourceCommand(oilDerrick, warehouse));
      }
    }
    
    @Override
    public int getUniqueID() {
        return ID;
    }
    
}
