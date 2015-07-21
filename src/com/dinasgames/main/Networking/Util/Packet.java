///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.dinasgames.main.Networking.Util;
//
//import java.util.ArrayList;
//import java.util.Vector;
//
///**
// *
// * @author Jack
// */
//public class Packet {
//    
//    protected int mReadPos, mSendPos;
//    protected boolean mIsValid;
//    protected Vector mData;
//    
//    public Packet() {
//        mReadPos = mSendPos = 0;
//        mIsValid = true;
//        mData = new Vector();
//    }
//    
//    public void append(byte[] data) {
//        
//        if(data != null && data.length > 0) {
//            int start = mData.size();
//            mData.setSize(start + data.length);
//            for(int i = start; i < start + data.length; i++) {
//                mData.set(i, data[i-start]);
//            }
//        }
//        
//    }
//    
//    public void clear() {
//        mData.clear();
//        mReadPos = 0;
//        mIsValid = true;
//    }
//    
//    byte[] getData() {
//        return (mData.isEmpty() ? (char[])mData.toArray() : null);
//    }
//    
//    
//    
//}
