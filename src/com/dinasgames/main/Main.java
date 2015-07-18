//Our Main Method - should be quite empty for now

package com.dinasgames.main;

import javax.swing.SwingUtilities;

public class Main{
  
  public Main(){
    
  }
  public static void main(String args[]){
    SwingUtilities.invokeLater(new Runnable(){
      @Override
      public void run(){
        Window window = new Window();
      }
    });
  }
  
}