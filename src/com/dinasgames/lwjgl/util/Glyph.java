/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.lwjgl.util;

import com.dinasgames.main.math.BoundingBox;

/**
 * A glyph is a visual representation of a character.
 * @author Jack
 */
public class Glyph {
  
  /**
   * Offset to move horizontally to the next character.
   */
  public float advance;
  
  /**
   * The bounding box for the glyph relative to the baseline.
   */
  public BoundingBox bounds;
  
  /**
   * The texture coordinates of the glyph inside the font texture.
   */
  public BoundingBox textureRect;
  
  public Glyph() {
    advance = 0.f;
    bounds = new BoundingBox();
    textureRect = new BoundingBox();
  }
  
}
