package com.dinasgames.main.Players;

import com.dinasgames.main.Controllers.Controller;
import com.dinasgames.main.Games.Game;
import com.dinasgames.main.Games.LocalGame;
import com.dinasgames.main.Graphics.RectangleShape;
import com.dinasgames.main.Graphics.Renderer;
import com.dinasgames.main.Math.BoundingBox;
import com.dinasgames.main.Math.Vector2f;
import com.dinasgames.main.Networking.Network;
import com.dinasgames.main.Objects.Entities.Entity;
import com.dinasgames.main.Objects.Entities.Units.Unit;
import com.dinasgames.main.Objects.GameObjectType;
import com.dinasgames.main.Objects.Utils.EntitySelection;
import com.dinasgames.main.Scenes.Scene;
import java.awt.Color;
import java.util.List;

/**
 *
 * @author Jack
 */
public class Player {
    
    public final int GROUP_COUNT = 10;
    
    // Player colors
    public final Color[] PLAYER_COLORS = {
        
        Color.red,
        Color.blue,
        Color.yellow,
        Color.pink,
        Color.cyan,
        Color.orange,
        Color.green,
        Color.magenta
        
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
        mSelectionShape.setRenderer(mRenderer);
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
        mSelectionShape = new RectangleShape();
        mSelectionShape.setDepth(-1000);
        mSelectionShape.setPosition(0.f,0.f);
        mSelectionShape.setSize(0.f,0.f);
        mSelectionShape.hide();
        mSelectionShape.setScene(mScene);
        mSelectionShape.setRenderer(mRenderer);
        
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
            return Color.white;
        }
        return PLAYER_COLORS[mID];
    }
    
    public int getID() {
        return mID;
    }
    
    public void setID(int id) {
        mID = id;
    }
    
    public void update() {
        
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
          unit.setTargetPosition(mousePosition);
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
    
}
