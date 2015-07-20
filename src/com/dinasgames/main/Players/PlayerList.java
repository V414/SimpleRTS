/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.Players;

import com.dinasgames.main.Game;

/**
 *
 * @author Jack
 */
public class PlayerList {
    
    public static final int MAX_PLAYERS = 8;
    
    protected Player[] mPlayers;
    
    public static PlayerList getCurrent() {
        return Game.current.getPlayerList();
    }
    
    public PlayerList() {
        mPlayers = new Player[MAX_PLAYERS];
        for(int i = 0; i < MAX_PLAYERS; i++) {
            mPlayers[i] = null;
        }
    }
    
    public void clear() {
        for(int i = 0; i < MAX_PLAYERS; i++) {
            mPlayers[i] = null;
        }
    }
    
    public int add(Player player) {
        for(int i = 0; i < MAX_PLAYERS; i++) {
            if(mPlayers[i] == null) {
                mPlayers[i] = player;
                return i;
            }
        }
        return -1;
    }
    
    public void remove(int idx) {
        if(idx < 0 || idx >= MAX_PLAYERS) {
            return;
        }
        mPlayers[idx] = null;
    }
    
    public Player get(int idx) {
        if(idx < 0 || idx >= MAX_PLAYERS) {
            return null;
        }
        return mPlayers[idx];
    }
    
}
