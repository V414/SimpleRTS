package com.dinasgames.main.Players;

import com.dinasgames.main.Controllers.Controller;
import com.dinasgames.main.Games.Game;
import com.dinasgames.main.Games.LocalGame;
import com.dinasgames.main.Games.SimpleGame;
import com.dinasgames.main.Graphics.RectangleShape;
import com.dinasgames.main.Math.BoundingBox;
import com.dinasgames.main.Math.Vector2f;
import com.dinasgames.main.Networking.Network;
import com.dinasgames.main.Objects.Entities.Entity;
import com.dinasgames.main.Objects.Entities.Units.Unit;
import com.dinasgames.main.Objects.GameObjectType;
import com.dinasgames.main.Objects.Utils.EntitySelection;
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
    
    // Attributes
    
    // Selection
    protected EntitySelection mEntitySelection;
    protected EntitySelection[] mEntitySelectionGroups;
    
    // Selction box
    protected RectangleShape mSelectionShape;
    protected BoundingBox mSelectionBox;
    protected Vector2f mSelectionStart;
    
    public static Player getLocalPlayer() {
        if(Network.isServer()) {
            return null;
        }
        return ((SimpleGame)Game.current).getPlayer();
    }
    
    protected Player() {
        
        mID = -1;
        mController = null;
        mIsReference = false;
        
        // Setup selection box
        mSelectionShape = RectangleShape.create();
        mSelectionShape.setDepth(-1000);
        mSelectionShape.setPosition(0.f,0.f);
        mSelectionShape.setSize(0.f,0.f);
        mSelectionShape.hide();
        
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
    
    public boolean isReference() {
        return (mIsReference && hasValidReference());
    }
    
    public void makeReference() {
        
        mIsReference = true;
        
        // Remove selection box
        mSelectionShape.remove();
        mSelectionShape = null;
        
    }
    
    public boolean isSelectionBoxShowing() {
        if(isReference()) {
            return ref().isSelectionBoxShowing();
        }
        return mSelectionShape.isVisible();
    }
    
    public BoundingBox getSelectionBox() {
        if(isReference()) {
            return ref().getSelectionBox();
        }
        return mSelectionBox;
    }
    
    public Color getColor() {
        if(isReference()) {
            return ref().getColor();
        }
        if(mID < 0) {
            return Color.white;
        }
        return PLAYER_COLORS[mID];
    }
    
    protected boolean hasValidReference() {
        return (ref() != null);
    }

    private Player ref() {
        return PlayerList.getCurrent().get(mID);
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
        if(isReference()) {
            return ref().getController();
        }
        return mController;
    }
    
    protected void updateSelectionShape() {
        mSelectionShape.setPosition(mSelectionBox.left, mSelectionBox.top);
        mSelectionShape.setSize(mSelectionBox.width, mSelectionBox.height);
    }
    
    protected void setNewTargetPosition(Vector2f mousePosition) {
      if(isReference()) {
        ref().setNewTargetPosition(mousePosition);
        return;
      }
      
      Entity[] selectedEntities = mEntitySelection.getSelections();
      
      for (Entity selectedEntity : selectedEntities) {
        if (selectedEntity.hasType(GameObjectType.Unit)) {
          Unit unit = (Unit) selectedEntity;
          unit.setTargetPosition(mousePosition);
        }
      }
    }
    
    protected void startSelection(Vector2f mousePosition) {
        
        if(isReference()) {
            ref().startSelection(mousePosition);
            return;
        }
        
        mSelectionStart = new Vector2f(mousePosition);
        mSelectionBox.setPosition(mousePosition);
        mSelectionBox.setSize(0.f, 0.f);
        
        updateSelectionShape();
        mSelectionShape.show();
        
    }
    
    protected void updateSelection(Vector2f mousePosition) {
        
        if(isReference()) {
            ref().updateSelection(mousePosition);
            return;
        }
        
        // Ensure the selection box doesn't have a negative size
        float minX = Math.min( mSelectionStart.x, mousePosition.x );
        float minY = Math.min( mSelectionStart.y, mousePosition.y );
        float maxX = Math.max( mSelectionStart.x, mousePosition.x );
        float maxY = Math.max( mSelectionStart.y, mousePosition.y );
        
        mSelectionBox.setRectangle(minX, minY, maxX, maxY);
        
        updateSelectionShape();
        
    }
    
    protected void stopSelection() {
        
        if(isReference()) {
            ref().stopSelection();
            return;
        }
        
        selectObjects();
        
        mSelectionShape.hide();
        
    }
    
    public Player getReference() {
        Player r = new Player();
        r.makeReference();
        r.setID(mID);
        return r;
    }
    
}
