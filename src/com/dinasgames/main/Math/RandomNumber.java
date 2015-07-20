/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.Math;

import java.util.Random;

/**
 *
 * @author Jack
 */
public class RandomNumber {
    
    public static int between(int lower, int higher) {
        
        Random r = new Random();

        return (r.nextInt() * (higher - lower)) + lower;
        
    }
    
    public static float between(float lower, float higher) {
        
        Random r = new Random();

        return (r.nextFloat() * (higher - lower)) + lower;
        
    }
    
    public static double between(double lower, double higher) {
        
        Random r = new Random();

        return (r.nextDouble() * (higher - lower)) + lower;
        
    }
    
    public static Vector2f between(Vector2f lower, Vector2f higher) {
        
        return new Vector2f( between(lower.x,higher.x), between(lower.y,higher.y) );
        
    }
    
}