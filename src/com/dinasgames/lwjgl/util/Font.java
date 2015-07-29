/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.lwjgl.util;

import com.dinasgames.main.math.BoundingBox;
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
  
  /**
   * Class holding information about a font.
   */
  //public class Info {
    
//    public String familyName;
//    
//    public Info() {
//      familyName = "";
//    }
//    
//    public Info(Info other) {
//      familyName = other.familyName;
//    }
//    
//  }
  
  public class TrueFont {
    public int style, size;
    public TrueTypeFont font;
  }
  
  //protected Map<Integer, Texture> mTextureMap;
  protected java.awt.Font       mAwtFont;
//  protected TrueTypeFont        mFont;
//  protected Info                mInfo;
//  protected Map<Integer, Page>  mPages;
//  protected Kerning             mKerning;
  protected List<TrueFont>      mFontList;
  
  /**
   * Default constructor.
   */
  public Font() {
    //mInfo = new Info();
    mFontList = new ArrayList();
  }
//  
//  /**
//   * Copy constructor.
//   * @param other 
//   */
//  public Font(Font other) {
//    // TODO
//  }
//  
//  /**
//   * Destructor.
//   */
//  public void destruct() {
//    cleanup();
//  }
//  
//  /**
//   * Get the font info.
//   * @return 
//   */
//  public Info getInfo() {
//    return new Info(mInfo);
//  }
//  
//  public Glyph getGlyph(int codePoint, int characterSize, boolean bold) {
//    
//    Map<Integer, Glyph> glyphs = mPages.get(characterSize).glyphs;
//    
//    int key = ((bold ? 1 : 0) << 31) | codePoint;
//    
//    Glyph g = glyphs.get(key);
//    
//    if(g != null) {
//      return g;
//    }
//    
//    Glyph glyph = loadGlyph(codePoint, characterSize, bold);
//    glyphs.put(key, glyph);
//    return glyph;
//    
//  }
//  
//  public float getKerning(int first, int second, int characterSize) {
//    
//    if(first == 0 || second == 0) {
//      return 0.f;
//    }
//    
//    if(mKerning == null) {
//      return 0.f;
//    }
//    
//    if(!setCurrentSize(characterSize)) {
//      return 0.f;
//    }
////    FontRenderContext context = new FontRenderContext(null, true, false);
////    GlyphVector vec = mAwtFont.createGlyphVector(context, new int[]{ first, second });
////    vec.getGlyphCode(first);
////    GlyphVector.getGlyphCode(first)
////    int index1 = getCharIndex(first);
////    int index2 = getCharIndex(second);
////    
////    int k = mKerning.getKerning(mKerning.getValues(first), second);
//    
//    return (float)k;
//    
//    // TODO
//    //java.awt.Font.getFont(null, null).getBaselineFor(c)
//    
//    
//    //return 0.f;
//    
//  }
//  
//  public float getLineSpacing(int characterSize) {
//    if(setCurrentSize(characterSize)) {
//      return 0.f;
//    }
//    return 0.f;
//  }
//  
//  public float getUnderlinePosition(int characterSize) {
//    return 0.f;
//  }
//  
//  public float getUnderlineThickness(int characterSize) {
//    return 0.f;
//  }
//  
//  public Texture getTexture(int characterSize) {
//    return null;
//  }
//  
//  protected class Row {
//    
//    public int width, top, height;
//    
//    public Row( int rowTop, int rowHeight ) {
//      width = 0;
//      top = rowTop;
//      height = rowHeight;
//    }
//    
//    public Row(Row other) {
//      width = other.width;
//      top = other.top;
//      height = other.height;
//    }
//    
//  }
//  
//  protected class Page {
//    
//    public Map<Integer, Glyph>  glyphs;
//    public Texture     texture;
//    public int         nextRow;
//    public List<Row>   rows;
//    
//    public Page() {
//      glyphs    = new HashMap();
//      rows      = new ArrayList();
//      texture   = null;
//      nextRow   = 0;
//    }
//    
//    public Page(Page other) {
//      glyphs = new HashMap(other.glyphs);
//      rows = new ArrayList(other.rows);
//      texture = new Texture(other.texture);
//      nextRow = other.nextRow;
//    }
//    
//  }
//  
//  protected void cleanup() {
//    
//  }
//  
//  protected Glyph loadGlyph(int codePoint, int characterSize, boolean bold) {
//    return null;
//  }
//  
//  protected BoundingBox findGlyphRect(Page page, int width, int height) {
//    return null;
//  }
//  
//  protected boolean setCurrentSize(int characterSize) {
//    return false;
//  }
  
  
  
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
  
  protected TrueTypeFont createFont( int characterSize, int style ) {
    java.awt.Font newFont = mAwtFont.deriveFont(style, (int)characterSize);
    return new TrueTypeFont(newFont, true);
  }
  
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
