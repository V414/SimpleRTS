package com.dinasgames.main.behaviours;

import com.dinasgames.main.commands.MoveToResourceCommand;
import com.dinasgames.main.objects.entities.buildings.OilDerrick;
import com.dinasgames.main.objects.entities.buildings.Warehouse;
import com.dinasgames.main.objects.entities.units.vehicles.land.SupplyTruck;
import com.dinasgames.engine.system.Time;

public class BLoadAtResource extends Behaviour {
    
    public static int ID = 3;
    private SupplyTruck supplyTruck;
    private final Warehouse warehouse;
    private final OilDerrick oilDerrick;
    float mTimePassed;
    
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

        if(isAtTarget() == false){  
          supplyTruck.addBehaviour(new BMoveTo(self, oilDerrick.getX(), oilDerrick.getY()));
        }
      }
        
    }
    
    @Override
    public void update(Time timePassed) {
      
      if(isAtTarget() == true){ 
        mTimePassed += timePassed.asSeconds();
        
        if(mTimePassed >= supplyTruck.getLoadingTime()){
          supplyTruck.setCarryingAmount(supplyTruck.getCarryingAmount()+1);
          mTimePassed = 0;
        }
        
        if(supplyTruck.getCarryingAmount() >= supplyTruck.getCarryingMax()){
          supplyTruck.issueCommand(new MoveToResourceCommand(oilDerrick, warehouse));
        }
      }
    }
    
    private boolean isAtTarget(){
      int radius = 40;
      float truckX = supplyTruck.getX();
      float truckY = supplyTruck.getY();

      float targetX = oilDerrick.getX();
      float targetY = oilDerrick.getY();

      if(truckX > targetX-radius && truckX < targetX+radius &&
              truckY > targetY-radius && truckY < targetY+radius){
        return true;
      }else{
        return false;
}
    }
    
    @Override
    public int getUniqueID() {
        return ID;
    }
    
}
