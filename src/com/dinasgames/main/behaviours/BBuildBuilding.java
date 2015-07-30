package com.dinasgames.main.behaviours;

import com.dinasgames.main.objects.GameObjectType;
import com.dinasgames.main.objects.entities.buildings.NewBuilding;
import com.dinasgames.main.objects.entities.buildings.PowerPlant;
import com.dinasgames.main.objects.entities.buildings.Warehouse;
import com.dinasgames.main.objects.entities.units.vehicles.land.Bulldozer;
import com.dinasgames.main.scenes.Scene;
import com.dinasgames.engine.system.Time;
import com.dinasgames.main.objects.entities.buildings.Barracks;

public class BBuildBuilding extends Behaviour {
    
    public static int ID = 3;
    
    protected Bulldozer bulldozer;
    protected NewBuilding newBuilding;
    protected Scene scene;
    protected boolean mBuilt;
    protected float mTimePassed;
    
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
        
        if(isAtTarget() == false){
          bulldozer.addBehaviour(new BMoveTo(self, newBuilding.getX(), newBuilding.getY()));
        }
      }
        
    }
    
    @Override
    public void update(Time timePassed) {
      
      if(!mBuilt) {
        mTimePassed += timePassed.asSeconds();
        
        if(isAtTarget() == true){
          if(mTimePassed >= bulldozer.getBuildingSpeed()){
            mTimePassed = 0;
            newBuilding.setBuildingProgressPercent(newBuilding.getBuildingProgressPercent() + bulldozer.getBuildingAmount());
            
            if(newBuilding.getBuildingProgressPercent() >= 100){
              addBuilding();
              mBuilt = true;
            }
          }
        }
      }
      
    }
    
    private boolean isAtTarget(){
      int radius = 40;
      float truckX = bulldozer.getX()+bulldozer.getWidth()/2;
      float truckY = bulldozer.getY()+bulldozer.getHeight()/2;

      float targetX = newBuilding.getX()+newBuilding.getWidth()/2;
      float targetY = newBuilding.getY()+newBuilding.getHeight()/2;

      if(truckX > targetX-radius && truckX < targetX+radius &&
              truckY > targetY-radius && truckY < targetY+radius){
        return true;
      }else{
        return false;
      } 
    }

    private void addBuilding(){
      if(newBuilding.getFinishedBuilding().hasType(GameObjectType.PowerPlant)){
        PowerPlant building = new PowerPlant(scene);
        building.setPosition(newBuilding.getPosition());
        building.setOwner(newBuilding.getOwner());
        newBuilding.destroy();
      }else if(newBuilding.getFinishedBuilding().hasType(GameObjectType.Barracks)){
        Barracks building = new Barracks(scene);
        building.setPosition(newBuilding.getPosition());
        building.setOwner(newBuilding.getOwner());
        newBuilding.destroy();
      }else if(newBuilding.getFinishedBuilding().hasType(GameObjectType.Warehouse)){
        Warehouse building = new Warehouse(scene);
        building.setPosition(newBuilding.getPosition());
        building.setOwner(newBuilding.getOwner());
        newBuilding.destroy();
      }
    }
    
    @Override
    public int getUniqueID() {
        return ID;
    }
    
}
