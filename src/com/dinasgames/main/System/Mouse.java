///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.dinasgames.main.System;
//
//import com.dinasgames.main.Math.Vector2f;
//import com.dinasgames.main.Scenes.Scene;
//import java.awt.event.MouseEvent;
//
///**
// *
// * @author Jack
// */
//public class Mouse {
//    
//    public static enum Button {
//        Left,
//        Middle,
//        Right
//    }
//    
//    protected static boolean[] mButtonState = null;
//    protected static Vector2f mPosition = new Vector2f(0.f,0.f);
//    protected static Vector2f mRealPosition = new Vector2f(0.f,0.f);
//    protected static Scene mScene = null;
//    
//    public static void setScene(Scene scene) {
//        mScene = scene;
//    }
//    
//    public static void tick() {
//        
//        // Update the position with recent data from mouseMoved event
//        mPosition.x = mRealPosition.x;
//        mPosition.y = mRealPosition.y;
//        
//        // Apply camera transformation if we have been assigned one
//        if(mScene != null && mScene.getCamera() != null) {
//            mPosition.x += mScene.getCamera().getPosition().x;
//            mPosition.y += mScene.getCamera().getPosition().y;
//        }
//        
//    }
//    
//    public static void setPosition(Vector2f position) {
//        mPosition = position;
//    }
//    
//    public static Vector2f getPosition() {
//        return mPosition;
//    }
//    
//    public static void onMouseMoved(float x, float y) {
//        mRealPosition.x = x;
//        mRealPosition.y = y;
//    }
//    
//    private static void initMouseState() {
//        if(mButtonState == null) {
//            mButtonState = new boolean[4];
//            for(int i = 0; i < 4; i ++) {
//                mButtonState[i] = false;
//            }
//        }
//    }
//    
//    public static boolean isButtonPressed(Button button) {
//        
//        initMouseState();
//        
//        switch(button) {
//            
//            case Left: {
//                return mButtonState[1];
//            }
//                
//            case Middle: {
//                return mButtonState[2];
//            }
//                
//            case Right: { 
//                return mButtonState[3];
//            }
//            
//        }
//        
//        return false;
//        
//    }
//    
//    public static void onMousePressed(MouseEvent e) {
//        initMouseState();
//        if(e.getButton() < 4) {
//            mButtonState[e.getButton()] = true;
//        }
//    }
//    
//    public static void onMouseReleased(MouseEvent e) {
//        initMouseState();
//        if(e.getButton() < 4) {
//            mButtonState[e.getButton()] = false;
//        }
//    }
//    
//}
