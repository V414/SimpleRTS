package com.dinasgames.main.objects.entities.units;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.dinasgames.main.Objects.Entities.Units;
//
//import com.dinasgames.main.Graphics.RectangleShape;
//import com.dinasgames.main.Math.RandomNumber;
//import com.dinasgames.main.Objects.GameObjectType;
//import com.dinasgames.main.Scenes.Scene;
//import java.awt.Color;
//
///**
// *
// * @author Jack
// */
//public class TestUnit extends Unit {
//    
//    RectangleShape mShape;
//    
//    protected TestUnit() {
//        mShape = null;
//        mHealthMax = 10.f;
//        mHealth = mHealthMax;
//    }
//    
//    
//    
//    public static TestUnit create() {
//        
//        // Add a test unit to the scene
//        int id = Scene.getCurrent().add(new TestUnit());
//        
//        // Make a reference to this object and return it
//        TestUnit ghost = new TestUnit();
//        ghost.setID(id);
//        ghost.makeReference();
//        
//        return ghost;
//        
//    }
//    
//    @Override
//    public void onCreate() {
//        
//        super.onCreate();
//        
//        setSize(20.f, 40.f);
//        
//        mShape = RectangleShape.create();
//        
//        mShape.setFillColor(Color.yellow);
//        mShape.setOutlineColor(Color.black);
//        mShape.setOutlineThickness(2.f);
//        mShape.setSize(mSize);
//        mShape.setRotation(RandomNumber.between(0.f, 360.f));
//        
//    }
//    
//    @Override
//    public void onTick(double time) {
//        
//        super.onTick(time);
//        
//        mPosition.x ++;
//        
//    }
//    
//    @Override
//    public void onRender() {
//        
//        super.onRender();
//        
//        mShape.setPosition(mPosition);
//        mShape.setRotation(mRotation);
//        
//    }
//    
//    @Override
//    public int getTypeID() {
//        return super.getTypeID() | GameObjectType.TestUnit.getID();
//    }
//    
//    @Override
//    public String getTypeString() {
//        return "TestUnit";
//    }
//    
//    @Override
//    protected boolean hasValidReference() {
//        return (this.ref() != null);
//    }
//
//    private TestUnit ref() {
//        return (TestUnit)Scene.getCurrent().get(mID);
//    }
//    
//}
