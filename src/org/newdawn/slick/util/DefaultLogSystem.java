/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.newdawn.slick.util;

/**
 *
 * @author Jack
 */
public class DefaultLogSystem implements LogSystem {
    
  @Override
  public  void info(String s){
      System.out.println(s);
  }

  @Override
  public  void warn(String s){
      System.out.println("WARNNING - " + s);
  }

  @Override
  public  void error(String s){
      System.out.println("ERROR - " + s);
  }

  @Override
  public void warn(String s, Throwable e) {
    System.out.println("WARNING - " + s + " - " + e.getMessage());
  }

  @Override
  public void error(Throwable s) {
    System.out.println("ERROR - " + s.getMessage());
    s.printStackTrace();
  }

  @Override
  public void error(String s, Throwable e) {
    System.out.println("ERROR - " + s + " - " + e.getMessage());
    e.printStackTrace();
  }

  @Override
  public void debug(String s) {
    System.out.println("DEBUG - " + s);
  }
  
}
