///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.dinasgames.main;
//
//import com.dinasgames.main.Math.BoundingBox;
//import com.dinasgames.main.Math.Vector2f;
//
///**
// *
// * @author Jack
// */
//public class Camera {
//    
//    protected BoundingBox mView;
//    
//    public Camera(float width, float height) {
//        mView = new BoundingBox(0.f, 0.f, width, height);
//    }
//    
//    public void tick() {
//        
//    }
//    
//    public BoundingBox getView() {
//        return mView;
//    }
//    
//    // Setters
//    public Camera setPosition(Vector2f position) {
//        mView.setPosition(position);
//        return this;
//    }
//    
//    public Camera setPosition(float x, float y) {
//        mView.setPosition(x, y);
//        return this;
//    }
//    
//    public Camera setSize(float x, float y) {
//        mView.width = x;
//        mView.height = y;
//        return this;
//    }
//    
//    public Camera setSize(Vector2f size) {
//        mView.width = size.x;
//        mView.height = size.y;
//        return this;
//    }
//    
//    public Camera setRectangle(float left, float top, float right, float bottom) {
//        mView.x = left;
//        mView.y = top;
//        mView.width = right - left;
//        mView.height = bottom - top;
//        return this;
//    }
//    
//    public Camera setView(BoundingBox view) {
//        mView = view;
//        return this;
//    }
//    
//    // Getters
//    public Vector2f getPosition() {
//        return mView.getPosition();
//    }
//    
//    // Other
//    public Camera move(Vector2f offset) {
//        mView.x += offset.x;
//        mView.y += offset.y;
//        return this;
//    }
//    
//    public Camera move(float x, float y) {
//        mView.x += x;
//        mView.y += y;
//        return this;
//    }
//    
//}
