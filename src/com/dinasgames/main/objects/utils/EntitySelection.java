package com.dinasgames.main.objects.utils;

import com.dinasgames.main.objects.entities.Entity;
import java.util.ArrayList;
import java.util.List;


/**
 * The selection box class
 * @author Jack
 */
public class EntitySelection {
    
    public static final int MAX_SELECTIONS = 100;
    
    protected Entity[] mSelection;
    
    public EntitySelection() {
        mSelection = new Entity[MAX_SELECTIONS];
        for(int i = 0; i < MAX_SELECTIONS; i++) {
            mSelection[i] = null;
        }
    }
    
    // Clear selection
    public void clear() {
        for(int i = 0; i < MAX_SELECTIONS; i++) {
            if(mSelection[i] != null) {
                mSelection[i].deselect();
                mSelection[i] = null;
            }
        }
    }
    
    // Select single
    public int select(Entity e) {
        if(e == null) {
            return -1;
        }
        for(int i = 0; i < MAX_SELECTIONS; i++) {
            if(mSelection[i] == null) {
                mSelection[i] = e;
                e.select();
                return i;
            }
        }
        return -1;
    }
    
    // Select multiple
    public void select(Entity[] e) {
        for(int i = 0; i < e.length; i++) {
            select(e[i]);
        }
    }
    
    // Select multiple
    public void select(List<Entity> e) {
        for(int i = 0; i < e.size(); i++) {
            select(e.get(i));
        }
    }
    
    // Select multiple
    public void deselect(List<Entity> e) {
        for(int i = 0; i < e.size(); i++) {
            deselect(e.get(i));
        }
    }
    
    // Deselect multiple
    public void deselect(Entity[] e) {
        for(int i = 0; i < e.length; i++) {
            deselect(e[i]);
        }
    }
    
    // Deselect entity
    public void deselect(Entity e) {
        for(int i = 0; i < MAX_SELECTIONS; i++) {
            if(mSelection[i] != null && mSelection[i].getID() == e.getID()) {
                deselect(i);
                return;
            }
        }
    }
    
    // Deselect by index
    public void deselect(int idx) {
        if(idx < 0 || idx >= MAX_SELECTIONS) {
            return;
        }
        if(mSelection[idx] != null) {
            mSelection[idx].deselect();
            mSelection[idx] = null;
        }
    }
    
    public int size() {
        return MAX_SELECTIONS;
    }
    
    public Entity getSelection(int idx) {
        if(idx < 0 || idx >= MAX_SELECTIONS) {
            return null;
        }
        return mSelection[idx];
    }
    
    public List<Entity> getSelections() {
        List<Entity> list = new ArrayList();
        for(int i = 0; i < MAX_SELECTIONS; i ++) {
            if(mSelection[i] != null) {
                list.add(mSelection[i]);
            }
        }
        return list;
    }
    
}
