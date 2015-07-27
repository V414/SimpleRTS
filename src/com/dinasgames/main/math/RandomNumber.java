/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.math;

import java.util.Random;

/**
 *
 * @author Jack
 */
public class RandomNumber {
    
    public static int between(int lower, int higher) {
        
        Random r = new Random();

        return r.nextInt((higher-lower) + 1) + lower;
        
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
    
    /**
     * Chooses a random element from a list given.
     * @param <T>
     * @param list
     * @return The random element
     */
    public static <T> T choose(T[] list) {
        return list[between(0, list.length - 1)];
    }
    
}
