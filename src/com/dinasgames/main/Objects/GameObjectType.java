/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.Objects;

/**
 *
 * @author Jack
 */
public enum GameObjectType {
    
    // Types of object
    GameObject  (0x00000001),
    Building    (0x00000002),
    Unit        (0x00000004),
    Infantry    (0x00000008),
    TestObject  (0x00000010),
    Entity      (0x00000020),
    TestUnit    (0x00000040);
    
    // Constructor
    private final int mId;
    
    GameObjectType(int id) {
        mId = id;
    }

    public int getID() {
        return mId;
    }
    
}
