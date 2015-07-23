/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.Math;

/**
 *
 * @author Jack
 */
public class Point {
    
    public static final double PI = 3.142;
    
    /**
     * Converts radians into degrees.
     * @param radians
     * @return 
     */
    public static double radiansToDegrees(double radians) {
        return (radians * 180.0) / PI;
    }
    
    /**
     * Converts degrees into radians.
     * @param degrees
     * @return 
     */
    public static double degreesToRadians(double degrees) {
        return (degrees * (PI / 180.0));
    }
    
    /**
     * Calculates the angle between two points in degrees.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return 
     */
    public static double direction(double x1, double y1, double x2, double y2) {
        return radiansToDegrees(Math.atan2(y1 - y2, x2 - x1));
    }
    
    /**
     * Calculate the angle between two points in degrees.
     * @param p1
     * @param p2
     * @return 
     */
    public static double direction(Vector2f p1, Vector2f p2) {
        return direction(p1.x, p1.y, p2.x, p2.y);
    }
    
    /**
     * Calculates the distance in pixels between two points.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return 
     */    
    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt( ( (y2-y1) * (y2-y1)) + ((x2-x1) * (x2-x1)) );
    }
    
    /**
     * Calculates the distance in pixels between two points.
     * @param p1
     * @param p2
     * @return 
     */
    public static double distance(Vector2f p1, Vector2f p2) {
        return distance(p1.x,p1.y,p2.x,p2.y);
    }
    
    /**
     * Calculates the x point if you were to move distance in direction. Direction is assumed to be in degrees.
     * @param distance
     * @param direction
     * @return 
     */
    public static double inDirectionX(double distance, double direction) {
        return Math.cos(degreesToRadians(direction)) * distance;
    }
    
    /**
     * Calculate the y point if you were to move distance in direction. Direction is assumed to be in degrees.
     * @param distance
     * @param direction
     * @return 
     */
    public static double inDirectionY(double distance, double direction) {
        return -Math.sin(degreesToRadians(direction)) * distance;
    }
    
    /**
     * Calculate the point if you were to move distance in a direction. Direction is assumed to be in degrees.
     * @param distance
     * @param direction
     * @return 
     */
    public static Vector2f inDirection(double distance, double direction) {
        return new Vector2f((float)inDirectionX(distance,direction), (float)inDirectionY(distance,direction));
    }
    
}
