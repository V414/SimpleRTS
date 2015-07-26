///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.dinasgames.main.Graphics;
//
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.GraphicsConfiguration;
//import java.awt.image.VolatileImage;
//
///**
// *
// * @author Jack
// */
//public class Canvas extends java.awt.Canvas {
//    
//    public interface Listener {
//        
//        public void render(Graphics2D g);
//        
//    };
//    
//    protected VolatileImage mImage;
//    protected Listener mListener;
//    
//    public Canvas() {
//        mListener = null;
//    }
//    
//    public void setListener(Listener listener) {
//        mListener = listener;
//    }
//    
//    @Override
//    public void update(Graphics g) {
//        paint(g);
//    }
//    
//    @Override
//    public void paint(Graphics g2) {
//        
//        createBackBuffer();
//        
//        do {
//            
//            GraphicsConfiguration gc = this.getGraphicsConfiguration();
//            int valCode = mImage.validate(gc);
//            
//            if(valCode == VolatileImage.IMAGE_INCOMPATIBLE) {
//                createBackBuffer();
//            }
//            
//            Graphics2D g = (Graphics2D)mImage.getGraphics();
//            
//            if(mListener != null) {
//                mListener.render(g);
//            }
//            
//            g2.drawImage(mImage, 0, 0, this);
//            
//            
//        } while(mImage.contentsLost());
//        
//    }
//    
//    public void createBackBuffer() {
//        GraphicsConfiguration gc = getGraphicsConfiguration();
//        mImage = gc.createCompatibleVolatileImage(getWidth(), getHeight());
//    }
//    
//}
