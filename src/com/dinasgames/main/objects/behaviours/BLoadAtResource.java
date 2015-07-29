package com.dinasgames.main.objects.behaviours;

import com.dinasgames.main.World;
import com.dinasgames.main.math.Point;
import com.dinasgames.main.objects.GameObject;
import com.dinasgames.main.objects.commands.MoveToResourceCommand;
import com.dinasgames.main.objects.entities.buildings.OilDerrick;
import com.dinasgames.main.objects.entities.buildings.Warehouse;
import com.dinasgames.main.objects.entities.units.Unit;
import com.dinasgames.main.objects.entities.units.vehicles.land.SupplyTruck;
import com.dinasgames.main.system.Time;
import com.dinasgames.net.StateValue;

public class BLoadAtResource extends Behaviour {
    
    public static int ID = 3;
    private SupplyTruck supplyTruck;
    private final Warehouse warehouse;
    private final OilDerrick oilDerrick;
    
  /**
   * Default constructor.
   * @param self
   * @param warehouse
   * @param oilDerrick
   */
    public BLoadAtResource( Object self, Warehouse warehouse, OilDerrick oilDerrick ) {
        
      super(self);
      this.warehouse = warehouse;
      this.oilDerrick = oilDerrick;

      if(self != null && self instanceof SupplyTruck) {

        supplyTruck = (SupplyTruck) self;

        supplyTruck.addBehaviour(new BMoveTo(self, oilDerrick.getX(), oilDerrick.getY()));
      }
        
    }
    
    @Override
    public void update(Time timePassed) {
      int radius = 40;
      float truckX = supplyTruck.getX();
      float truckY = supplyTruck.getY();

      float targetX = oilDerrick.getX();
      float targetY = oilDerrick.getY();

      if(truckX > targetX-radius && truckX < targetX+radius &&
              truckY > targetY-radius && truckY < targetY+radius){
        supplyTruck.setCarryingAmount(supplyTruck.getCarryingMax());
        
        supplyTruck.issueCommand(new MoveToResourceCommand(oilDerrick, warehouse));
      }
    }
    
    @Override
    public int getUniqueID() {
        return ID;
    }
    
}
