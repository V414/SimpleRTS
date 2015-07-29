/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.lwjgl.util;

import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;


/**
 *
 * @author Jack
 */
public class Font {
  
  protected TrueTypeFont mFont;
  
  public Font() {
    
  }
  
  /**
   * Load a system font using family name, style and size.
   * @param familyName
   * @param style
   * @param size
   * @return 
   */
  public boolean loadFromSystem( String familyName, int style, int size ) {
    if(mFont == null) {
      java.awt.Font awtFont = new java.awt.Font( familyName, style, size );
      mFont = new TrueTypeFont( awtFont, true );
      return true;
    }
    return false;
  }
  
  /**
   * Load a True Type Font from a file.
   * @param filename
   * @return 
   */
  public boolean loadFromFile( String filename ) {
    try {
      InputStream inputStream = ResourceLoader.getResourceAsStream(filename);
      java.awt.Font awtFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, inputStream);
      mFont = new TrueTypeFont(awtFont, true);
      return true;
    } catch( FontFormatException | IOException e ) {}
    return false;
  }
  
}
