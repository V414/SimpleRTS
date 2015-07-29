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
public interface LogSystem {
  
  void info(String s);
  void warn(String s);
  void warn(String s, Throwable e);
  //void error(Throwable s);
  void error(String s);
  void error(Throwable s);
  void error(String s, Throwable e);
  void debug(String s);
  
}
