package com.dinasgames.main.objects.behaviours;

import com.dinasgames.main.World;
import com.dinasgames.main.math.Point;
import com.dinasgames.main.objects.GameObject;
import com.dinasgames.main.objects.GameObjectType;
import static com.dinasgames.main.objects.behaviours.BLoadAtResource.ID;
import com.dinasgames.main.objects.commands.MoveToResourceCommand;
import com.dinasgames.main.objects.entities.buildings.Building;
import com.dinasgames.main.objects.entities.buildings.NewBuilding;
import com.dinasgames.main.objects.entities.buildings.OilDerrick;
import com.dinasgames.main.objects.entities.buildings.PowerPlant;
import com.dinasgames.main.objects.entities.buildings.Warehouse;
import com.dinasgames.main.objects.entities.units.Unit;
import com.dinasgames.main.objects.entities.units.vehicles.land.Bulldozer;
import com.dinasgames.main.objects.entities.units.vehicles.land.SupplyTruck;
import com.dinasgames.main.scenes.Scene;
import com.dinasgames.main.system.Time;
import com.dinasgames.net.StateValue;

public class BBuildBuilding extends Behaviour {
    
    public static int ID = 2;
    
    protected Bulldozer bulldozer;
    protected NewBuilding newBuilding;
    protected Scene scene;
    protected boolean mBuilt;
    
  /**
   * Default constructor.
   * @param self
   * @param scene
   * @param newBuilding
   */
    public BBuildBuilding( Object self, Scene scene, NewBuilding newBuilding ) {
        
      super(self);
      this.newBuilding = newBuilding;
      this.scene = scene;

      if(self != null && self instanceof Bulldozer) {

        bulldozer = (Bulldozer) self;
        
        bulldozer.addBehaviour(new BMoveTo(self, newBuilding.getX(), newBuilding.getY()));

      }
        
    }
    
    @Override
    public void update(Time timePassed) {
      
      if(!mBuilt) {
        int radius = 40;
        float truckX = bulldozer.getX()+bulldozer.getWidth()/2;
        float truckY = bulldozer.getY()+bulldozer.getHeight()/2;

        float targetX = newBuilding.getX()+newBuilding.getWidth()/2;
        float targetY = newBuilding.getY()+newBuilding.getHeight()/2;

        if(truckX > targetX-radius && truckX < targetX+radius &&
                truckY > targetY-radius && truckY < targetY+radius){


          addBuilding();
          mBuilt = true;
        }
      }
      
    }
    
    private void addBuilding(){
      if(newBuilding.getFinishedBuilding().hasType(GameObjectType.PowerPlant)){
        PowerPlant powerPlant = new PowerPlant(scene);
        powerPlant.setPosition(newBuilding.getPosition());
        powerPlant.setOwner(newBuilding.getOwner());
        newBuilding.destroy();
      }
    }
    
    @Override
    public int getUniqueID() {
        return ID;
    }
    
}
