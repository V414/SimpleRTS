/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.net;

/**
 *
 * @author Jack
 */
public class IntCurveData extends CurveData {
    
    int value;
    
    public IntCurveData( int value ) {
        this.value = value;
    }
    
    @Override
    public Object getValue() {
        return (Integer)value;
    }
    
    @Override
    public void setValue(Object value) {
        if(value != null && value instanceof Integer) {
            this.value = (Integer)value;
        }
    }
    
    @Override
    public Object interpolate(  Curve.CurvePoint a, Curve.CurvePoint b, int timePassed  ) {      
        if( a != null && b != null ) {
            
            if(a.value instanceof Integer && b.value instanceof Integer) {
                
                float dt = Math.max( 0.f, Math.min( 1.f, ((float)(timePassed - a.time) / (float)(b.time - a.time))  ) );
                int start = (Integer)a.value;
                int end = (Integer)b.value;
                
                this.value = (int)( (float)start + (( (float)end - (float)start ) * dt) );
                
            }
            
        }
        return getValue();
    }
    
}
