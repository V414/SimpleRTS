/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.players;
import com.dinasgames.engine.graphics.Renderer;
import com.dinasgames.main.scenes.Scene;
import com.dinasgames.engine.system.Time;

/**
 *
 * @author Jack
 */
public class PlayerList {
    
    public static final int MAX_PLAYERS = 8;
    
    protected Player[] mPlayers;
    protected Renderer mRenderer;
    protected Scene mScene;
    
    public PlayerList setRenderer(Renderer renderer) {
        mRenderer = renderer;
        for(int i = 0; i < MAX_PLAYERS; i++) {
            if(mPlayers[i] != null) {
                mPlayers[i].setRenderer(renderer);
            }
        }
        return this;
    }
    
    public PlayerList setScene(Scene scene) {
        mScene = scene;
        for(int i = 0; i < MAX_PLAYERS; i++) {
            if(mPlayers[i] != null) {
                mPlayers[i].setScene(scene);
            }
        }
        return this;
    }
    
    public final int getMaxPlayers() {
        return MAX_PLAYERS;
    }
    
    public PlayerList() {
        mPlayers = new Player[MAX_PLAYERS];
        for(int i = 0; i < MAX_PLAYERS; i++) {
            mPlayers[i] = null;
        }
    }
    
    public void clear() {
        for(int i = 0; i < MAX_PLAYERS; i++) {
            if(mPlayers[i] != null) {
                mPlayers[i].onRemove();
            }
            mPlayers[i] = null;
        }
    }
    
    public int add(Player player) {
        for(int i = 0; i < MAX_PLAYERS; i++) {
            if(mPlayers[i] == null) {
                
                mPlayers[i] = player;
                mPlayers[i].setID(i);
                mPlayers[i].setScene(mScene);
                mPlayers[i].setRenderer(mRenderer);
                
                return i;
            }
        }
        return -1;
    }
    
    public void remove(int idx) {
        if(idx < 0 || idx >= MAX_PLAYERS) {
            return;
        }
        if(mPlayers[idx] != null) {
            mPlayers[idx].onRemove();
        }
        mPlayers[idx] = null;
    }
    
    public Player get(int idx) {
        if(idx < 0 || idx >= MAX_PLAYERS) {
            return null;
        }
        return mPlayers[idx];
    }
    
    public void update(Time timePassed) {
        
        for(int i = 0; i < MAX_PLAYERS; i++) {
            if(mPlayers[i] != null) {
                mPlayers[i].update(timePassed);
            }
        }
        
    }
    
}
