/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.objects;

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
    TestUnit    (0x00000040),
    Rifleman    (0x00000080),
    LightTank   (0x00000100),
    Vehicle     (0x00000200),
    PowerPlant  (0x00000400),
    Bulldozer   (0x00000800),
    Flamethrower(0x00001000),
    Bazooka     (0x00002000),
    LandVehicle (0x00004000),
    SeaVehicle  (0x00008000),
    AirVehicle  (0x00010000);
    
    // Constructor
    private final int mId;
    
    GameObjectType(int id) {
        mId = id;
    }

    public int getID() {
        return mId;
    }
    
}
