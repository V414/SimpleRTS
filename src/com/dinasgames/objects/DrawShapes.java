package com.dinasgames.objects;

import java.awt.Color;
import java.awt.Shape;

public class DrawShapes{
  private final Shape shape;
  private final Color color;
  private final String drawType;
  
  public DrawShapes(Shape shape, Color color, String drawType){
    this.shape = shape;
    this.color = color;
    this.drawType = drawType;
  }

  public Shape getShape(){
    return shape;
  }
  
  public Color getColor(){
    return color;
  }
  
  public String drawType(){
    return drawType;
  }
  
}