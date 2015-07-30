package com.dinasgames.main.players;

import com.dinasgames.engine.graphics.Color;
import com.dinasgames.engine.graphics.shapes.RectangleShape;
import com.dinasgames.engine.graphics.Renderer;
import com.dinasgames.main.controllers.Controller;
import com.dinasgames.engine.math.BoundingBox;
import com.dinasgames.engine.math.Vector2f;
import com.dinasgames.main.objects.GameObject;
import com.dinasgames.main.objects.entities.Entity;
import com.dinasgames.main.objects.entities.units.Unit;
import com.dinasgames.main.objects.GameObjectType;
import com.dinasgames.main.commands.BuildBuildingCommand;
import com.dinasgames.main.commands.MoveCommand;
import com.dinasgames.main.commands.MoveToResourceCommand;
import com.dinasgames.main.objects.entities.buildings.NewBuilding;
import com.dinasgames.main.objects.entities.buildings.OilDerrick;
import com.dinasgames.main.objects.entities.buildings.Warehouse;
import com.dinasgames.main.objects.utils.EntitySelection;
import com.dinasgames.main.scenes.Scene;
import com.dinasgames.engine.system.Time;
import java.util.List;

/**
 *
 * @author Jack
 */
public class Player {
    
    public final int GROUP_COUNT = 10;
    
    // Player colors
    public final Color[] PLAYER_COLORS = {
        
        //Color.RED,
        new Color(140, 0, 0, 255),
        Color.BLUE(),
        Color.YELLOW(),
        Color.PINK(),
        Color.CYAN(),
        Color.ORANGE(),
        Color.GREEN(),
        Color.MEGENTA()
        
    };
    
    protected int mID;
    protected boolean mIsReference;
    protected Controller mController;
    protected Scene mScene;
    protected Renderer mRenderer;
    
    // Attributes
    
    // Selection
    protected EntitySelection mEntitySelection;
    protected EntitySelection[] mEntitySelectionGroups;
    
    // Selction box
    protected RectangleShape mSelectionShape;
    protected BoundingBox mSelectionBox;
    protected Vector2f mSelectionStart;
    
    public Player setRenderer(Renderer renderer) {
        mRenderer = renderer;
        mSelectionShape.render(mRenderer);
        return this;
    }
    
    public Player setScene(Scene scene) {
        mScene = scene;
        mSelectionShape.setScene(mScene);
        return this;
    }
    
    protected Player() {
        
        mID = -1;
        mController = null;
        mIsReference = false;
        
        // Setup selection box
        mSelectionShape = new RectangleShape(0.f, 0.f);
        mSelectionShape.setDepth(-1000);
        mSelectionShape.setPosition(0.f,0.f);
        mSelectionShape.setSize(0.f,0.f);
        mSelectionShape.hide();
        mSelectionShape.setScene(mScene);
        mSelectionShape.render(mRenderer);
        
        mSelectionBox = new BoundingBox();
        mSelectionStart = new Vector2f();
        
        // Setup selections
        mEntitySelection = new EntitySelection();
        mEntitySelectionGroups = new EntitySelection[GROUP_COUNT];
        
        // Create selection groups
        for(int i = 0; i < GROUP_COUNT; i++) {
            mEntitySelectionGroups[i] = new EntitySelection();
        }
        
    }
    
    // Events
    public void onSelectObjects(BoundingBox selectionArea) {
        
    }
    
    public Entity checkIfObject(Vector2f mousePosition){
      List<Entity> entityList = mScene.findObjects();

      for(Entity entity : entityList) {
          if(entity.getBoundingBox().contains(mousePosition) && entity.getOwner().getID() == mID) {
              return entity;
          }
      }
      
      return null;
    }
    
    public void clearSelection() {
        mEntitySelection.clear();
    }
    
    public void selectObjects() {
        
        // Call selection event
        onSelectObjects(mSelectionBox);
        
    }
    
    public void selectObjects(List<Entity>list) {
        mEntitySelection.select(list);
    }
    
    public boolean isSelectionBoxShowing() {
        return mSelectionShape.isVisible();
    }
    
    public BoundingBox getSelectionBox() {
        return mSelectionBox;
    }
    
    public Color getColor() {
        if(mID < 0) {
            return Color.WHITE();
        }
        return PLAYER_COLORS[mID];
    }
    
    public int getID() {
        return mID;
    }
    
    public void setID(int id) {
        mID = id;
    }
    
    public void update(Time timePassed) {
        
        // Update controller
        if(mController != null) {
            mController.update();
        }
        
    }
    
    public void onRemove() {
        
        if(mSelectionBox != null) {
            mSelectionShape.remove();
        }
        
    }
    
    public Controller getController() {
        return mController;
    }
    
    protected void updateSelectionShape() {
        mSelectionShape.setPosition(mSelectionBox.x, mSelectionBox.y);
        mSelectionShape.setSize(mSelectionBox.width, mSelectionBox.height);
    }
    
    protected void setNewTargetPosition(Vector2f mousePosition) {
      
      List<Entity> selectedEntities = mEntitySelection.getSelections();
      
      for (Entity selectedEntity : selectedEntities) {
        if (selectedEntity.hasType(GameObjectType.Unit)) {
          Unit unit = (Unit) selectedEntity;
          
          if(unit.hasType(GameObjectType.SupplyTruck)){
            if(checkIfObject(mousePosition) != null && checkIfObject(mousePosition).hasType(GameObjectType.OilDerrick)){
            OilDerrick oilDerrick = (OilDerrick) checkIfObject(mousePosition);
              for(GameObject object : mScene.getObjectsList()){
                if(object.hasType(GameObjectType.Warehouse)){
                  Warehouse warehouse = (Warehouse) object;
                  if(warehouse.getOwner() == unit.getOwner()){
                    unit.issueCommand(new MoveToResourceCommand(oilDerrick, warehouse));
                    break;
                  }
                }
              }
            }else{
              unit.issueCommand(new MoveCommand(mousePosition));
            }
          }else if(unit.hasType(GameObjectType.Bulldozer)){
            if(checkIfObject(mousePosition) != null && checkIfObject(mousePosition).hasType(GameObjectType.NewBuilding)){
              NewBuilding newBuilding = (NewBuilding) checkIfObject(mousePosition);
              if(newBuilding.getOwner() == unit.getOwner()){
                unit.issueCommand(new BuildBuildingCommand(newBuilding, mScene));
              }
            }else{
            unit.issueCommand(new MoveCommand(mousePosition));
            }
          }else{
          unit.issueCommand(new MoveCommand(mousePosition));
          }
        }
      }
    }
    
    protected void startSelection(Vector2f mousePosition) {
        
        mSelectionStart = new Vector2f(mousePosition);
        mSelectionBox.setPosition(mousePosition);
        mSelectionBox.setSize(0.f, 0.f);
        
        updateSelectionShape();
        mSelectionShape.show();
        
    }
    
    protected void updateSelection(Vector2f mousePosition) {
        
        // Ensure the selection box doesn't have a negative size
        float minX = Math.min( mSelectionStart.x, mousePosition.x );
        float minY = Math.min( mSelectionStart.y, mousePosition.y );
        float maxX = Math.max( mSelectionStart.x, mousePosition.x );
        float maxY = Math.max( mSelectionStart.y, mousePosition.y );
        
        mSelectionBox.setRectangle(minX, minY, maxX, maxY);
        
        updateSelectionShape();
        
    }
    
    protected void stopSelection() {
        
        selectObjects();
        
        mSelectionShape.hide();
        
    }
    
    public boolean equals(Player other) {
        return (other.getID() == mID);
    }
    
}
