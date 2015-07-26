/*
 * Copyright 1997-2008 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 *
 */

package com.dinasgames.main.Math;


/**
 * A 2-element vector that is represented by single-precision floating
 * point x,y coordinates.
 *
 */
public class Vector2i extends Tuple2i implements java.io.Serializable {

    // Combatible with 1.1
    static final long serialVersionUID = -2168194326883512320L;

    /**
     * Constructs and initializes a Vector2f from the specified xy coordinates.
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Vector2i(int x, int y)
    {
      super(x,y);
    }


    /**
     * Constructs and initializes a Vector2f from the specified array.
     * @param v the array of length 2 containing xy in order
     */
    public Vector2i(int[] v)
    {
      super(v);
    }


    /**
     * Constructs and initializes a Vector2f from the specified Vector2f.
     * @param v1 the Vector2f containing the initialization x y data
     */
    public Vector2i(Vector2i v1)
    {
       super(v1);
    }


    /**
     * Constructs and initializes a Vector2f from the specified Tuple2f.
     * @param t1 the Tuple2f containing the initialization x y data
     */
    public Vector2i(Tuple2i t1)
    {
       super(t1);
    }



    /**
     * Constructs and initializes a Vector2f to (0,0).
     */
    public Vector2i()
    {
        super();
    }


  /**
   * Computes the dot product of the this vector and vector v1.
   * @param v1 the other vector
   */
  public final int dot(Vector2i v1)
    {
      return (this.x*v1.x + this.y*v1.y);
    }


    /**
     * Returns the length of this vector.
     * @return the length of this vector
     */
    public final int length()
    {
        return (int) Math.sqrt(this.x*this.x + this.y*this.y);
    }

    /**
     * Returns the squared length of this vector.
     * @return the squared length of this vector
     */
    public final int lengthSquared()
    {
        return (this.x*this.x + this.y*this.y);
    }

    /**
     * Sets the value of this vector to the normalization of vector v1.
     * @param v1 the un-normalized vector
     */
    public final void normalize(Vector2i v1)
    {
        int norm;

        norm = (int) (1.0/Math.sqrt(v1.x*v1.x + v1.y*v1.y));
        this.x = v1.x*norm;
        this.y = v1.y*norm;
    }

    /**
     * Normalizes this vector in place.
     */
    public final void normalize()
    {
        float norm;

        norm = (float)
               (1.0/Math.sqrt(this.x*this.x + this.y*this.y));
        this.x *= norm;
        this.y *= norm;
    }


  /**
    *   Returns the angle in radians between this vector and the vector
    *   parameter; the return value is constrained to the range [0,PI].
    *   @param v1    the other vector
    *   @return   the angle in radians in the range [0,PI]
    */
   public final int angle(Vector2i v1)
   {
      double vDot = this.dot(v1) / ( this.length()*v1.length() );
      if( vDot < -1.0) vDot = -1.0;
      if( vDot >  1.0) vDot =  1.0;
      return((int) (Math.acos( vDot )));
   }

   public Vector2i add(float x, float y) {
        this.x += x;
        this.y += y;
        return this;
    }
   
    public Vector2i subtract(float x, float y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Vector2i multiply(float x, float y) {
        this.x *= x;
        this.y *= y;
        return this;
    }
    
    public Vector2i divide(float x, float y) {
        this.x /= x;
        this.y /= y;
        return this;
    }
    
    public Vector2i add(Vector2i other) {
        return add(other.x,other.y);
    }
    
    public Vector2i subtract(Vector2i other) {
        return subtract(other.x,other.y);
    }
    
    public Vector2i multiply(Vector2i other) {
        return multiply(other.x,other.y);
    }
    
    public Vector2i divide(Vector2i other) {
        return divide(other.x,other.y);
    }
    
    public Vector2i add(int other) {
        return add(other,other);
    }
    
    public Vector2i subtract(int other) {
        return subtract(other,other);
    }
    
    public Vector2i multiply(int other) {
        return multiply(other,other);
    }
    
    public Vector2i divide(int other) {
        return divide(other,other);
    }

    public static Vector2i add(Vector2i left, Vector2i right) {
        return new Vector2i(left).add(right);
    }
    
    public static Vector2i subtract(Vector2i left, Vector2i right) {
        return new Vector2i(left).subtract(right);
    }
    
    public static Vector2i multiply(Vector2i left, Vector2i right) {
        return new Vector2i(left).multiply(right);
    }
    
    public static Vector2i divide(Vector2i left, Vector2i right) {
        return new Vector2i(left).divide(right);
    }
    
}
