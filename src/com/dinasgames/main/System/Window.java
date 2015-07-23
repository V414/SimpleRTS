/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.System;

import com.dinasgames.main.Graphics.Renderer;
import com.dinasgames.main.Math.Vector2f;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

/**
 * A class to create and manage a window.
 * @author Jack
 */
public class Window implements WindowListener {
    
    protected boolean mIsOpen;
    protected JFrame mFrame;
    protected Renderer mRenderer;
    
    public Window(String title, float width, float height) {
        
        // Initalize the JFrame
        mFrame = new JFrame();
        mFrame.setIgnoreRepaint(true);
        mFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        //mFrame.setMinimumSize(new Dimension(width, height));
        mFrame.setResizable(false);
        mFrame.setTitle(title);
        
        mIsOpen = true;
        
        // Initialize the renderer
        mRenderer = new Renderer(mFrame, width, height);
        
        mFrame.setLocationRelativeTo(null);
        
        // Make the JFrame visible
        mFrame.setVisible(true);

        // Listen for window events
        mFrame.addWindowListener(this);
        mFrame.addMouseListener(mRenderer.getMouseAdapter());
        mFrame.addKeyListener(mRenderer.getKeyboardAdapter());
        mFrame.requestFocus();
        
    }
    
//    public Window(String title) {
//        
//        // Construct the window
//        this(1280, 720);
//        
//        // Setup using parameters
//        mFrame.setTitle(title);
//        
//    }
    
//    public Window(String title, int width, int height) {
//        
//        // Construct the window
//        this();
//        
//        // Setup using parameters
//        mFrame.setTitle(title);
//        //mFrame.setSize(width, height);
//        mFrame.setLocationRelativeTo(null);
//        
//        //mRenderer.setSize(width, height);
//        
//    }
    
    public Vector2f getSize() {
        return new Vector2f(mFrame.getSize().width, mFrame.getSize().height);
    }
    
    public void setTitle(String title) {
        
        if(!isOpen()) {
            return;
        }
        
        mFrame.setTitle(title);
        
    }
    
    public void close() {
        mFrame.setVisible(false);
        mFrame.dispose();
        mIsOpen = false;
    }
    
    public boolean isOpen() {
        return mIsOpen;
    }
    
    public void render() {
        
        // Render the window
        mRenderer.render(mFrame);
        
    }
    
    public Renderer getRenderer() {
        return mRenderer;
    }
    
    public JFrame getFrame() {
        return mFrame;
    }
    
    
    public void windowDeactivated(WindowEvent e) {
        
    }
    
    public void windowActivated(WindowEvent e) {
        
    }
    
    public void windowDeiconified(WindowEvent e) {
        
    }
    
    public void windowIconified(WindowEvent e) {
        
    }
    
    public void windowClosing(WindowEvent e) {
    }
    
    public void windowClosed(WindowEvent e) {
        mIsOpen = false;
    }
    
    public void windowOpened(WindowEvent e) {
        mIsOpen = true;
    }
    
}
