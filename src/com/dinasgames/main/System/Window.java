/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.System;

import com.dinasgames.main.Games.Game;
import com.dinasgames.main.Games.LocalGame;
import com.dinasgames.main.Games.SimpleGame;
import com.dinasgames.main.Graphics.Renderer;
import com.dinasgames.main.Networking.Network;
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
    
    public static Window getCurrent() {
        if(Network.isServer()) {
            return null;
        }
        return ((SimpleGame)Game.current).getWindow();
    }
    
    public Window() {
        
        // Initalize the JFrame
        mFrame = new JFrame();
        mFrame.setIgnoreRepaint(true);
        mFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        mIsOpen = true;
        
        // Initialize the renderer
        mRenderer = new Renderer(mFrame);
        
        // Make the JFrame visible
        mFrame.setVisible(true);

        // Listen for window events
        mFrame.addWindowListener(this);
        
    }
    
    public Window(String title) {
        
        // Construct the window
        this();
        
        // Setup using parameters
        mFrame.setTitle(title);
        
    }
    
    public Window(String title, int width, int height) {
        
        // Construct the window
        this();
        
        // Setup using parameters
        mFrame.setTitle(title);
        mFrame.setSize(width, height);
        
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
