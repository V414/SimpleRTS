/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main;

/**
 *
 * @author Jack
 */
public class Version {
    
    public static final Version current = new Version(0, 0, 100, 0);
    
    public int major, minor, build, release;
    
    public Version(int a, int b, int c, int d) {
        this.major = a;
        this.minor = b;
        this.build = c;
        this.release = d;
    }
    
    public static boolean compare(Version a, Version b) {
        return (
                    a.major         == b.major
                    && a.minor      == b.minor
                    && a.build      == b.build
                    && a.release    == b.release
                );
    }
    
    @Override
    public String toString() {
        return Integer.toString(major) + "." + Integer.toString(minor) + "." + Integer.toString(build) + "." + Integer.toString(release);
    }
    
}
