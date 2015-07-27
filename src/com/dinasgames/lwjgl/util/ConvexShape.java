/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.lwjgl.util;

import com.dinasgames.main.math.Vector2f;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jack
 */
public class ConvexShape extends Shape {
    
    List<Vector2f> mPoints;
    
    public ConvexShape(int pointCount) {
        mPoints = new ArrayList();
        setPointCount(pointCount);
    }
    
    @Override
    public ConvexShape setOriginCenter() {
        System.out.println("ConvexShape::setOriginCenter has not been implemented. TODO.");
        return this;
    }
    
    public ConvexShape setPointCount(int count) {
        while(mPoints.size() > count) {
            mPoints.remove(mPoints.size() - 1);
        }
        while(mPoints.size() < count) {
            mPoints.add(new Vector2f());
        }
        update();
        return this;
    }
    
    @Override
    public int getPointCount() {
        return mPoints.size();
    }
    
    public ConvexShape setPoint(int index, Vector2f point) {
        mPoints.set(index, new Vector2f(point));
        update();
        return this;
    }
    
    @Override
    public Vector2f getPoint(int index) {
        return mPoints.get(index);
    }
    
}
