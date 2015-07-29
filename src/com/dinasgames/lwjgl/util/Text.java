/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.lwjgl.util;

import com.dinasgames.main.math.BoundingBox;
import com.dinasgames.main.math.Vector2f;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Jack
 */
public class Text extends Renderable implements Drawable {

  public static class Style {
    public static int Regular       = 0;
    public static int Bold          = 1 << 0;
    public static int Italic        = 1 << 1;
    public static int Underlined    = 1 << 2;
    public static int StrikeThrough = 1 << 3;
  }
  
  protected String      mText;
  protected Font        mFont;
  protected int         mCharacterSize;
  protected Color       mColor;
  protected int         mStyle;
  protected VertexCache mVerts;
  protected BoundingBox mBounds;
  protected boolean     mGeoNeedUpdate;
  protected Texture     mTexture;
  protected TrueTypeFont mActualFont;
  
  public Text() {
    mText           = "";
    mFont           = null;
    mCharacterSize  = 30;
    mStyle          = Style.Regular;
    mColor          = Color.WHITE;
    mVerts          = new VertexCache(PrimitiveType.Triangles);
    mBounds         = new BoundingBox();
    mGeoNeedUpdate  = false;
  }
  
  public Text(String text, Font font, int characterSize) {
    this();
    
    mText           = text;
    mFont           = font;
    mCharacterSize  = characterSize;
    
    mGeoNeedUpdate  = true;
    
  }
  
  public Text(String text, Font font) {
    this(text, font, 20);
  }
  
  public Text setText(String text) {
    if(!mText.equals(text)) {
      mText = text;
      mGeoNeedUpdate = true;
    }
    return this;
  }
  
  public Text setFont(Font font) {
    if(mFont != font) {
      mFont = font;
      mGeoNeedUpdate = true;
    }
    return this;
  }
  
  public Text setCharacterSize(int size) {
    if(mCharacterSize != size) {
      mCharacterSize = size;
      mGeoNeedUpdate = true;
    }
    return this;
  }
  
  public Text setStyle(int style) {
    if(mStyle != style) {
      mStyle = style;
      mGeoNeedUpdate = true;
    }
    return this;
  }
  
  public Text setColor(Color color) {
    if(!mColor.equals(color)) {
      
      mColor = color;
      
      if(!mGeoNeedUpdate) {
        
        for(int i = 0; i < mVerts.getSize(); i++) {
          //mVerts.get(i).color = color;
          mVerts.setColor(i, color);
        }
      }
      
    }
    return this;
  }
  
  public String getText() {
    return mText;
  }
  
  public Font getFont() {
    return mFont;
  }
  
  public int getStyle() {
    return mStyle;
  }
    
  public Color getColor() {
    return new Color(mColor);
  }
  
  public int getCharacterSize() {
    return mCharacterSize;
  }
  
  public Vector2f findCharacterPos(int idx) {
    
    return new Vector2f();
    
  }
  
  public BoundingBox getLocalBounds() {
    ensureGeoUpdate();
    return mBounds;
  }
  
  public BoundingBox getGlobalBounds() {
    return getTransform().transformRect(getLocalBounds());
  }
  
  protected void ensureGeoUpdate() {
    
    if(!mGeoNeedUpdate) {
      return;
    }
    
    mGeoNeedUpdate = false;
    
    mActualFont = mFont.getFont(mCharacterSize, mStyle);
    mTexture = new Texture(mActualFont.getTexture());
    mActualFont.createString( mVerts, mText, mBounds );
    
    // Update colours
    for(int i = 0; i < mVerts.getSize(); i++) {
        //mVerts.get(i).color = color;
        mVerts.setColor(i, mColor);
    }
    
  }
  
  @Override
  public void draw(RenderTarget target, RenderStates states) {
    
    if(mFont != null) {
      
      ensureGeoUpdate();
      
      // Apply local transform
      states.transform.multiply(getTransform());
      
      // Apply a blend mode for text
      states.blendMode = BlendMode.BlendAlpha;
      
      // Apply the texture
      states.texture = mTexture;
      
      // Render the text
      //target.draw(this, states);
      target.draw(mVerts, states);
      
    }
    
  }
  
}
