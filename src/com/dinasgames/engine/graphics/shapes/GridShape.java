/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.engine.graphics.shapes;

import com.dinasgames.engine.graphics.Color;
import com.dinasgames.engine.graphics.PrimitiveType;
import com.dinasgames.engine.graphics.RenderStates;
import com.dinasgames.engine.graphics.RenderTarget;
import com.dinasgames.engine.graphics.Renderable;
import com.dinasgames.engine.graphics.Vertex;
import com.dinasgames.engine.graphics.VertexCache;
import com.dinasgames.engine.graphics.View;
import com.dinasgames.engine.math.BoundingBox;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jack
 */
public class GridShape extends Renderable {
  
  /**
   * A representation of a cell used internally.
   */
  protected class Cell {
    
    protected int x,y;
    public Color color;
    
    /**
     * Default constructor
     * @param color
     * @param x
     * @param y 
     */
    public Cell( Color color, int x, int y ) {
      this.color = color;
      this.x = x;
      this.y = y;
    }
    
    /**
     * Test whether this cell is within the view at a given size.
     * @param view
     * @param w
     * @param h
     * @return 
     */
    public boolean inView(View view, int w, int h) {
      
      if(view == null) {
        return false;
      }
      
      BoundingBox bounds = new BoundingBox();
      bounds.setPosition(x * w, y * h);
      bounds.setSize(w,h);
      
      return view.intersects( bounds );
      
    }
    
  }
  
  protected boolean mRedraw;
  protected float mCellWidth, mCellHeight;
  protected int mCols, mRows;
  protected VertexCache mVerts;
  protected Cell[] mCells;
  protected View mRenderView;
  
  /**
   * Default constructor.
   */
  public GridShape() {
    mVerts = new VertexCache(PrimitiveType.Triangles);
    mCols = 0;
    mRows = 0;
    mRedraw = false;
  }
  
  /**
   * Set the cell size in pixels.
   * @param width
   * @param height 
   */
  public void setCellSize(float width, float height) {
    mCellWidth = width;
    mCellHeight = height;
    mRedraw = true;
  }
  
  /**
   * Resize the grid. Note: This will remove old data
   * @param cols
   * @param rows 
   */
  public void resize( int cols, int rows ) {
    
    mCols = cols;
    mRows = rows;

    mCells = new Cell[ cols * rows ];
    for(int x = 0; x < cols; x++) {
      for(int y = 0; y < rows; y++) {
        mCells[ (y * cols) + x ] = new Cell( Color.TRANSPARENT(), x, y );
      }
    }
    
    mRedraw = true;
    
  }
  
  /**
   * Change the color of a cell.
   * @param x
   * @param y
   * @param color 
   */
  public void setColor( int x, int y, Color color ) {
    
    int idx = (y * mCols) + x;
    
    if(idx < 0 || idx >= mCells.length) {
      return;
    }
    
    mCells[idx].color = color;
    
  }
  
  /**
   * Internal function used to redraw the grid.
   */
  protected void redraw() {
    
    if(!mRedraw) {
      return;
    }
    
    if(mCells == null || mCells.length == 0) {
      return;
    }
    
    // Figure out which cells are in the view
    List<Cell> cellsToRender = new ArrayList();
    for(int x = 0; x < mCols; x++) {
      for(int y = 0; y < mRows; y++) {
        
        // Store this cell
        Cell thisCell = mCells[ (y * mCols) + x ];
        
        // Calculate the bounds of the cell
        BoundingBox bounds = new BoundingBox();
        bounds.setRectangle( thisCell.x, thisCell.y, thisCell.x + mCellWidth, thisCell.y + mCellHeight );
        
        // Apply the camera transformation!
        bounds.move(mRenderView.getCenter());
        
        if(mRenderView.intersects(bounds)) {
          cellsToRender.add(thisCell);
        }
        
      }
    }
    
    // Ensure we have enough space to store the verticies required
    mVerts.resize( cellsToRender.size() * 6 );
    
    // Setup our verticies
    int i = 0;
    for(Cell cell : cellsToRender) {
      
      float top       = cell.x * mCellWidth;
      float left      = cell.y * mCellHeight;
      float right     = left + mCellWidth;
      float bottom    = top + mCellHeight;
      
      // First triangle
      mVerts.set(i++, left, top, cell.color);     // Top Left
      mVerts.set(i++, right, top, cell.color);    // Top Right
      mVerts.set(i++, left, bottom, cell.color);  // Bottom left
      
      // Second triangle
      mVerts.set(i++, left, bottom, cell.color);   // Bottom left
      mVerts.set(i++, right, top, cell.color);     // Top right
      mVerts.set(i++, right, bottom, cell.color);  // Bottom right
      
    }
    
    mRedraw = false;
    
  }
  
  @Override
  public void draw(RenderTarget target, RenderStates states) {
    
    // We need to redraw on view changes
    if((mRenderView != null && !target.getView().equals(mRenderView)) || mRenderView == null) {
      mRedraw = true;
      mRenderView = new View(target.getView());
      if(target.getView().getCenter().y > 400) {
        int a = 0;
      }
    }
    
    // Redraw the grid if needed
    if(mRedraw) {
      redraw();
    }
    
    // Apply transform
    states.transform.multiply(getTransform());
    
    // Handle UI Render objects
    states.gui = mGUI;
    
    // Draw verts
    target.draw(mVerts, states);
    
  }
  
}
