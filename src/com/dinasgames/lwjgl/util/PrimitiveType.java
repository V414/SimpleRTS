/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.lwjgl.util;

/**
 *
 * @author Jack
 */
public enum PrimitiveType {
    
    Points,         ///< List of individual points
    Lines,          ///< List of individual lines
    LinesStrip,     ///< List of connected lines, a point uses the previous point to form a line
    Triangles,      ///< List of individual triangles
    TrianglesStrip, ///< List of connected triangles, a point uses the two previous points to form a triangle
    TrianglesFan,   ///< List of connected triangles, a point uses the common center and the previous point to form a triangle
    Quads           ///< List of individual quads (deprecated, don't work with OpenGL ES)
}
