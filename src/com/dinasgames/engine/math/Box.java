/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.engine.math;

/**
 *
 * @author Jack
 */
public class Box {
    
    public static class MatrixInput {
        public double px, py, sx, sy, rot, ox, oy;
        public MatrixInput(double px, double py, double sx, double sy, double rot, double ox, double oy) {
            this.px = px;
            this.py = py;
            this.sx = sx;
            this.sy = sy;
            this.rot = rot;
            this.ox = ox;
            this.oy = oy;
        }
    };
    
    public static class Points {
        public Vector2d point[] = new Vector2d[4];
    };
    
    public static Points calculateMatrix(MatrixInput in) {
        
        double[] x,y;
        x = new double[2];
        y = new double[2];
        
        // Something to store the 4 points in
        Points p = new Points();
        
        // Origin
        x[0] = Point.inDirectionX(in.ox, in.rot) + Point.inDirectionX(in.oy, in.rot - 90.0);
        y[0] = Point.inDirectionY(in.ox, in.rot) + Point.inDirectionY(in.oy, in.rot - 90.0);
        
        // Size
        x[1] = Point.inDirectionX(in.sx - in.ox, in.rot) + Point.inDirectionX(in.sy - in.oy, in.rot - 90.0);
        y[1] = Point.inDirectionY(in.sx - in.ox, in.rot) + Point.inDirectionY(in.sy - in.oy, in.rot - 90.0);
        
        // Top left
        p.point[0] = new Vector2d( in.px - x[0], in.py - y[0] );
        
        // Top right
        p.point[1] = new Vector2d( in.px - x[0] + Point.inDirectionX(in.sx, in.rot), in.py - y[0] + Point.inDirectionY(in.sx, in.rot) );
        
        // Bottom right
        p.point[2] = new Vector2d( in.px + x[1], in.py + y[1] );
        
        // Bootom left
        p.point[3] = new Vector2d( in.px - x[0] + Point.inDirectionX(in.sy, in.rot + 270.0), in.py - y[0] + Point.inDirectionY(in.sy, in.rot + 270.0) );        
        
        return p;
        
    }
    
}
