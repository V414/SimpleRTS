/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.lwjgl.util;

import com.dinasgames.main.math.BoundingBox;
import com.dinasgames.main.math.Vector2f;

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
  protected VertexArray mVerts;
  protected BoundingBox mBounds;
  protected boolean     mGeoNeedUpdate;
  
  public Text() {
    mText           = "";
    mFont           = null;
    mCharacterSize  = 30;
    mStyle          = Style.Regular;
    mColor          = Color.WHITE;
    mVerts          = new VertexArray(PrimitiveType.Triangles);
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
        for(int i = 0; i < mVerts.getVertexCount(); i++) {
          mVerts.get(i).color = color;
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
    
//    if(!mGeoNeedUpdate) {
//      return;
//    }
//    
//    mGeoNeedUpdate = false;
//    
//    mVerts.clear();
//    mBounds = new BoundingBox();
//    
//    if(mFont == null) {
//      return;
//    }
//    
//    if(mText.length() <= 0) {
//      return;
//    }
//    
//    boolean bold              = (mStyle & Style.Bold) != 0;
//    boolean underlined        = (mStyle & Style.Underlined) != 0;
//    boolean strikethrough     = (mStyle & Style.StrikeThrough) != 0;
//    boolean italic            = (mStyle & Style.Italic) != 0;
//    float underlineOffset     = mFont.getUnderlinePosition(mCharacterSize);
//    float underlineThickness  = mFont.getUnderlineThickness(mCharacterSize);
//    
//    // Compute the location of the strike through dynamically
//    // We use the center point of the lowercase 'x' glyph as the reference
//    // We reuse the underline thickness as the thickness of the strike through as well
//    FloatRect xBounds = m_font->getGlyph(L'x', m_characterSize, bold).bounds;
//    float strikeThroughOffset = xBounds.top + xBounds.height / 2.f;
//
//    // Precompute the variables needed by the algorithm
//    float hspace = static_cast<float>(m_font->getGlyph(L' ', m_characterSize, bold).advance);
//    float vspace = static_cast<float>(m_font->getLineSpacing(m_characterSize));
//    float x      = 0.f;
//    float y      = static_cast<float>(m_characterSize);
//
//    // Create one quad for each character
//    float minX = static_cast<float>(m_characterSize);
//    float minY = static_cast<float>(m_characterSize);
//    float maxX = 0.f;
//    float maxY = 0.f;
//    Uint32 prevChar = 0;
//    for (std::size_t i = 0; i < m_string.getSize(); ++i)
//    {
//        Uint32 curChar = m_string[i];
//
//        // Apply the kerning offset
//        x += static_cast<float>(m_font->getKerning(prevChar, curChar, m_characterSize));
//        prevChar = curChar;
//
//        // If we're using the underlined style and there's a new line, draw a line
//        if (underlined && (curChar == L'\n'))
//        {
//            float top = std::floor(y + underlineOffset - (underlineThickness / 2) + 0.5f);
//            float bottom = top + std::floor(underlineThickness + 0.5f);
//
//            m_vertices.append(Vertex(Vector2f(0, top),    m_color, Vector2f(1, 1)));
//            m_vertices.append(Vertex(Vector2f(x, top),    m_color, Vector2f(1, 1)));
//            m_vertices.append(Vertex(Vector2f(0, bottom), m_color, Vector2f(1, 1)));
//            m_vertices.append(Vertex(Vector2f(0, bottom), m_color, Vector2f(1, 1)));
//            m_vertices.append(Vertex(Vector2f(x, top),    m_color, Vector2f(1, 1)));
//            m_vertices.append(Vertex(Vector2f(x, bottom), m_color, Vector2f(1, 1)));
//        }
//
//        // If we're using the strike through style and there's a new line, draw a line across all characters
//        if (strikeThrough && (curChar == L'\n'))
//        {
//            float top = std::floor(y + strikeThroughOffset - (underlineThickness / 2) + 0.5f);
//            float bottom = top + std::floor(underlineThickness + 0.5f);
//
//            m_vertices.append(Vertex(Vector2f(0, top),    m_color, Vector2f(1, 1)));
//            m_vertices.append(Vertex(Vector2f(x, top),    m_color, Vector2f(1, 1)));
//            m_vertices.append(Vertex(Vector2f(0, bottom), m_color, Vector2f(1, 1)));
//            m_vertices.append(Vertex(Vector2f(0, bottom), m_color, Vector2f(1, 1)));
//            m_vertices.append(Vertex(Vector2f(x, top),    m_color, Vector2f(1, 1)));
//            m_vertices.append(Vertex(Vector2f(x, bottom), m_color, Vector2f(1, 1)));
//        }
//
//        // Handle special characters
//        if ((curChar == ' ') || (curChar == '\t') || (curChar == '\n'))
//        {
//            // Update the current bounds (min coordinates)
//            minX = std::min(minX, x);
//            minY = std::min(minY, y);
//
//            switch (curChar)
//            {
//                case ' ':  x += hspace;        break;
//                case '\t': x += hspace * 4;    break;
//                case '\n': y += vspace; x = 0; break;
//            }
//
//            // Update the current bounds (max coordinates)
//            maxX = std::max(maxX, x);
//            maxY = std::max(maxY, y);
//
//            // Next glyph, no need to create a quad for whitespace
//            continue;
//        }
//
//        // Extract the current glyph's description
//        const Glyph& glyph = m_font->getGlyph(curChar, m_characterSize, bold);
//
//        float left   = glyph.bounds.left;
//        float top    = glyph.bounds.top;
//        float right  = glyph.bounds.left + glyph.bounds.width;
//        float bottom = glyph.bounds.top  + glyph.bounds.height;
//
//        float u1 = static_cast<float>(glyph.textureRect.left);
//        float v1 = static_cast<float>(glyph.textureRect.top);
//        float u2 = static_cast<float>(glyph.textureRect.left + glyph.textureRect.width);
//        float v2 = static_cast<float>(glyph.textureRect.top  + glyph.textureRect.height);
//
//        // Add a quad for the current character
//        m_vertices.append(Vertex(Vector2f(x + left  - italic * top,    y + top),    m_color, Vector2f(u1, v1)));
//        m_vertices.append(Vertex(Vector2f(x + right - italic * top,    y + top),    m_color, Vector2f(u2, v1)));
//        m_vertices.append(Vertex(Vector2f(x + left  - italic * bottom, y + bottom), m_color, Vector2f(u1, v2)));
//        m_vertices.append(Vertex(Vector2f(x + left  - italic * bottom, y + bottom), m_color, Vector2f(u1, v2)));
//        m_vertices.append(Vertex(Vector2f(x + right - italic * top,    y + top),    m_color, Vector2f(u2, v1)));
//        m_vertices.append(Vertex(Vector2f(x + right - italic * bottom, y + bottom), m_color, Vector2f(u2, v2)));
//
//        // Update the current bounds
//        minX = std::min(minX, x + left - italic * bottom);
//        maxX = std::max(maxX, x + right - italic * top);
//        minY = std::min(minY, y + top);
//        maxY = std::max(maxY, y + bottom);
//
//        // Advance to the next character
//        x += glyph.advance;
//    }
//
//    // If we're using the underlined style, add the last line
//    if (underlined)
//    {
//        float top = std::floor(y + underlineOffset - (underlineThickness / 2) + 0.5f);
//        float bottom = top + std::floor(underlineThickness + 0.5f);
//
//        m_vertices.append(Vertex(Vector2f(0, top),    m_color, Vector2f(1, 1)));
//        m_vertices.append(Vertex(Vector2f(x, top),    m_color, Vector2f(1, 1)));
//        m_vertices.append(Vertex(Vector2f(0, bottom), m_color, Vector2f(1, 1)));
//        m_vertices.append(Vertex(Vector2f(0, bottom), m_color, Vector2f(1, 1)));
//        m_vertices.append(Vertex(Vector2f(x, top),    m_color, Vector2f(1, 1)));
//        m_vertices.append(Vertex(Vector2f(x, bottom), m_color, Vector2f(1, 1)));
//    }
//
//    // If we're using the strike through style, add the last line across all characters
//    if (strikeThrough)
//    {
//        float top = std::floor(y + strikeThroughOffset - (underlineThickness / 2) + 0.5f);
//        float bottom = top + std::floor(underlineThickness + 0.5f);
//
//        m_vertices.append(Vertex(Vector2f(0, top),    m_color, Vector2f(1, 1)));
//        m_vertices.append(Vertex(Vector2f(x, top),    m_color, Vector2f(1, 1)));
//        m_vertices.append(Vertex(Vector2f(0, bottom), m_color, Vector2f(1, 1)));
//        m_vertices.append(Vertex(Vector2f(0, bottom), m_color, Vector2f(1, 1)));
//        m_vertices.append(Vertex(Vector2f(x, top),    m_color, Vector2f(1, 1)));
//        m_vertices.append(Vertex(Vector2f(x, bottom), m_color, Vector2f(1, 1)));
//    }
//
//    // Update the bounding rectangle
//    mBounds.x = minX;
//    mBounds.y = minY;
//    mBounds.width = maxX - minX;
//    mBounds.height = maxY - minY;
    
  }
  
  @Override
  public void draw(RenderTarget target, RenderStates states) {
    
    if(mFont != null) {
      
      ensureGeoUpdate();
      
      states.transform.multiply(getTransform());
      //states.blendMode = new BlendMode(BlendMode.Factor.SrcAlpha, BlendMode.Factor.OneMinusSrcAlpha, BlendMode.Equation.Add);
      target.draw(this, states);
//      states.texture = mFont.getTexture(mCharacterSize);
//      target.draw(mVerts, states);
      
    }
    
  }
  
}
