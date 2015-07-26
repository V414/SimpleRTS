///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.dinasgames.lwjgl.util;
//
//import java.nio.ByteBuffer;
//import org.lwjgl.BufferUtils;
//
///**
// *
// * @author Jack
// */
//public class Bytes {
//    
//    protected ByteBuffer data;
//    protected int size;
//    
//    public Bytes() {
//        data = null;
//        size = 0;
//    }
//    
//    public Bytes resize(int size) {
//        
//        if(size == this.size) {
//            return this;
//        }
//        
//        if(data == null) {
//            data = BufferUtils.createByteBuffer(size);
//            for(int i = 0; i < size; i++) {
//                data.put((byte)0);
//            }
//        }else{
//            
//            ByteBuffer newData = BufferUtils.createByteBuffer(size);
//            
//            if(size > this.size) {
//                
//                // Grow
//                newData.put(data);
//                
//                for(int i = 0; i < )
//                
//            }else{
//                
//                // Shrink
//                
//            }
//            
//            this.data = newData;
//            
//        }
//        
//        this.size = size;
//        
//        return this;
//        
//    }
//    
//}
