/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.engine.graphics;

import com.dinasgames.engine.math.BoundingBox;
import java.awt.FontFormatException;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;


/**
 *
 * @author Jack
 */
public class Font {
  
  private static Map<String, Font> _fontResource = new HashMap();
  
  public static Font getSystemFont( String familyName, int style ) {
    
    // Check if we have already loaded this font
    if(_fontResource.containsKey(familyName)) {
      return _fontResource.get(familyName);
    }
    
    // Load it
    Font newFont = new Font();
    if(!newFont.loadFromSystem(familyName, style, 12)) {
      return null;
    }
    
    _fontResource.put(familyName, newFont);
    
    return newFont;
    
  }
  
  public static Font getSystemFont( String familyName ) {
    return getSystemFont( familyName, 0 );
  }
  
  public static Font get( String filename ) {
    
    // Check if we have already loaded this font
    if(_fontResource.containsKey(filename)) {
      return _fontResource.get(filename);
    }
    
    // Load it
    Font newFont = new Font();
    if(!newFont.loadFromFile(filename)) {
      return null;
    }
    
    _fontResource.put(filename, newFont);
    
    return newFont;
    
  }
  
  public class TrueFont {
    public int style, size;
    public TrueTypeFont font;
  }
  
  protected java.awt.Font       mAwtFont;
  protected List<TrueFont>      mFontList;
  
  /**
   * Default constructor.
   */
  public Font() {
    //mInfo = new Info();
    mFontList = new ArrayList();
  }
  
  /**
   * Load a system font using family name, style and size.
   * @param familyName
   * @param style
   * @param size
   * @return 
   */
  public boolean loadFromSystem( String familyName, int style, int size ) {
    mAwtFont = new java.awt.Font( familyName, style, size );
    return (mAwtFont != null);
  }
  
  /**
   * Load a True Type Font from a file.
   * @param filename
   * @return 
   */
  public boolean loadFromFile( String filename ) {
    try {
      InputStream inputStream = ResourceLoader.getResourceAsStream(filename);
      mAwtFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, inputStream);
      return true;
    } catch( FontFormatException | IOException e ) {}
    return false;
  }
  
  /**
   * Function used internally to generate a font of a particular size and style.
   * @param characterSize
   * @param style
   * @return 
   */
  protected TrueTypeFont createFont( int characterSize, int style ) {
    java.awt.Font newFont = mAwtFont.deriveFont(style, (int)characterSize);
    return new TrueTypeFont(newFont, true);
  }
  
  /**
   * Function used to get a version of this font of a particular size and style.
   * @param characterSize
   * @param style
   * @return 
   */
  public TrueTypeFont getFont( int characterSize, int style ) {
    
    for(TrueFont font : mFontList) {
      if(font.size == characterSize && font.style == style) {
        return font.font;
      }
    }
    
    TrueFont newFont = new TrueFont();
    newFont.size = characterSize;
    newFont.style = style;
    newFont.font = createFont( characterSize, style );
    
    mFontList.add(newFont);
    
    return newFont.font;
    
  }
  
}
