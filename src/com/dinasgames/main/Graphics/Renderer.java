//package com.dinasgames.main.Graphics;
//
//import com.dinasgames.main.Math.BoundingBox;
//import com.dinasgames.main.Math.Vector2f;
//import com.dinasgames.main.System.Mouse;
//import com.dinasgames.main.System.Keyboard;
//import javax.swing.JFrame;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.image.BufferStrategy;
//import java.awt.Color;
//import java.awt.RenderingHints;
////import java.awt.Toolkit;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.image.VolatileImage;
//
///**
// * A class used to render a list to a window.
// * @author Jack
// */
//public class Renderer {
//    
//    public final int MAX_RENDER_OBJECTS = 1024;
//    
//    protected long mDrawCount;
//    protected BoundingBox mView;
//    protected Color mBackgroundColor;
//    protected boolean[] mRenderFlag;
//    protected Renderable[] mRenderObjects;
//    protected Canvas mCanvas;
//    protected BufferStrategy mBuffer;
//    protected Graphics mGraphics;
//    protected MouseAdapter mMouseAdapter;
//    protected KeyAdapter mKeyboardAdapter;
//
//    public Renderer(JFrame window, float width, float height) {
//        
//        mRenderObjects  = new Renderable[MAX_RENDER_OBJECTS];
//        mRenderFlag     = new boolean[MAX_RENDER_OBJECTS];
//        mBackgroundColor = Color.white;
//        mDrawCount      = 0;
//        
//        // Init render list
//        for(int i = 0; i < MAX_RENDER_OBJECTS; i ++) {
//            mRenderObjects[i] = null;
//            mRenderFlag[i] = false;
//        }
//        
//        // Initialize the Canvas
//        mCanvas = new Canvas();
//        mCanvas.setIgnoreRepaint(true);
//        mCanvas.setSize((int)width, (int)height);
//        
//        // Add the canvas to the JFrame
//        window.add(mCanvas);
//        window.pack();
//        
//        // Create a buffer strategy with 2 buffers?
//        mCanvas.createBufferStrategy(2);
//        
//        // Get the created buffer strategy and store it
//        mBuffer = mCanvas.getBufferStrategy();
//        
//        mMouseAdapter = new MouseAdapter() {
//            
//           @Override
//           public void mouseMoved(MouseEvent e) {
//               
//               // Apply these changes globally
//               Mouse.onMouseMoved(e.getX(), e.getY());
//               
//           }
//           
//           @Override
//           public void mouseDragged(MouseEvent e) {
//               
//               // Bugfix: Mouse position not updating when clicking at the same time
//               Mouse.onMouseMoved(e.getX(), e.getY());
//               
//           }
//           
//           @Override
//           public void mousePressed(MouseEvent e) {
//               Mouse.onMousePressed(e);
//           }
//           
//           @Override
//           public void mouseReleased(MouseEvent e) {
//               Mouse.onMouseReleased(e);
//           }
//            
//        };
//        
//        mKeyboardAdapter = new KeyAdapter() {
//            
//            @Override
//            public void keyPressed(KeyEvent e) {
//                Keyboard.onKeyPressed(e);
//            }
//            
//            @Override
//            public void keyReleased(KeyEvent e) {
//                Keyboard.onKeyReleased(e);
//            }
//            
//        };
//        
//        mCanvas.addMouseListener(mMouseAdapter);
//        mCanvas.addMouseMotionListener(mMouseAdapter);
//        mCanvas.addKeyListener(mKeyboardAdapter);
//        mCanvas.requestFocus();
//        mCanvas.requestFocusInWindow();
//        
//        mCanvas.setListener((Graphics2D g) -> {
//            
//            onRender(g);
//            
//        });
//        
//    }
//    
//    public Vector2f getSize() {
//        return new Vector2f(mCanvas.getWidth(), mCanvas.getHeight());
//    }
//    
//    public Renderer setSize(float width, float height) {
//        mCanvas.setSize((int)width, (int)height);
//        return this;
//    }
//    
//    protected void onRender(Graphics2D g) {
//        
//        // Background color
//        g.setColor(mBackgroundColor);
//        g.fillRect( 0, 0, 1279, 719 );
//        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        
//        // Render objects
//        mDrawCount = 0;
//            
//        // Reset render flags
//        synchronized(mRenderFlag) {
//            for(int i = 0; i < MAX_RENDER_OBJECTS; i ++) {
//                mRenderFlag[i] = false;
//            }
//        }
//
//        // Draw shapes in order of highest to lowest (negative is toward the screen)
//        while(true) {
//
//            int renderObject = -1;
//            int highestDepth  = 0;
//
//            // Find the render object with the lowest depth
//            for(int i = 0; i < MAX_RENDER_OBJECTS; i++) {
//                if(mRenderObjects[i] != null && !mRenderFlag[i] && mRenderObjects[i].isVisible() && mRenderObjects[i].inView(mView)) {
//                    if(renderObject == -1) {
//                        renderObject = i;
//                        highestDepth = mRenderObjects[i].getDepth();
//                    }else{
//                        if(mRenderObjects[i].getDepth() > highestDepth) {
//                            renderObject = i;
//                            highestDepth = mRenderObjects[i].getDepth();    
//                        }
//                    }
//                }
//            }
//
//            // Check if all the objects have been drawn
//            if(renderObject == -1) {
//                break;
//            }
//
//            // Draw it
//            mRenderObjects[renderObject].render(g);
//            mDrawCount++;
//
//            // Flag it as being rendered
//            mRenderFlag[renderObject] = true;
//
//        }
//        
//    }
//    
//    public Renderer setView(BoundingBox view) {
//        mView = view;
//        return this;
//    }
//    
//    public MouseAdapter getMouseAdapter() {
//        return mMouseAdapter;
//    }
//    
//    public KeyAdapter getKeyboardAdapter() {
//        return mKeyboardAdapter;
//    }
//    
//    public Renderer setBackgroundColor(Color color) {
//        mBackgroundColor = color;
//        return this;
//    }
//    
//    public Color getBackgroundColor() {
//        return mBackgroundColor;
//    }
//    
//    public Renderer clear() {
//        for(int i = 0; i < MAX_RENDER_OBJECTS; i++) {
//            mRenderObjects[i] = null;
//        }
//        return this;
//    }
//    
//    public int add(Renderable r) {
//        for(int i = 0; i < MAX_RENDER_OBJECTS; i++) {
//            if(mRenderObjects[i] == null) {
//                
//                mRenderObjects[i] = r;
//                r.onAdd();
//                
//                return i;
//                
//            }
//        }
//        return -1;
//    }
//    
//    public Renderer remove(int idx) {
//        if(idx < 0 || idx >= MAX_RENDER_OBJECTS) {
//            return this;
//        }
//        if(mRenderObjects[idx] != null) {
//            mRenderObjects[idx].onRemove();
//        }
//        mRenderObjects[idx] = null;
//        return this;
//    }
//    
//    public Renderable get(int idx) {
//        if(idx < 0 || idx >= MAX_RENDER_OBJECTS) {
//            return null;
//        }
//        return mRenderObjects[idx];
//    }
//    
//    public long getDrawCount() {
//        return mDrawCount;
//    }
//    
//    
//    
//    public void render(JFrame window) {
//        
////        mGraphics = mBuffer.getDrawGraphics();
//        
//        mCanvas.update(mCanvas.getGraphics());
//        
////        if(!mBuffer.contentsLost()) {
////            mBuffer.show();
////        }
////        
////        mGraphics.dispose();
//        
////        try {
////            
////            mGraphics = mBuffer.getDrawGraphics();
////            mGraphics.setColor(mBackgroundColor);
////            mGraphics.fillRect( 0, 0, 1279, 719 );
////            
////            mDrawCount = 0;
////            
////            // Reset render flags
////            synchronized(mRenderFlag) {
////                for(int i = 0; i < MAX_RENDER_OBJECTS; i ++) {
////                    mRenderFlag[i] = false;
////                }
////            }
////            
////            // Draw shapes in order of highest to lowest (negative is toward the screen)
////            Graphics2D g = (Graphics2D)mGraphics;
////            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
////                    RenderingHints.VALUE_ANTIALIAS_ON);
////            while(true) {
////                
////                int renderObject = -1;
////                int highestDepth  = 0;
////                
////                // Find the render object with the lowest depth
////                for(int i = 0; i < MAX_RENDER_OBJECTS; i++) {
////                    if(mRenderObjects[i] != null && !mRenderFlag[i] && mRenderObjects[i].isVisible() && mRenderObjects[i].inView(mView)) {
////                        if(renderObject == -1) {
////                            renderObject = i;
////                            highestDepth = mRenderObjects[i].getDepth();
////                        }else{
////                            if(mRenderObjects[i].getDepth() > highestDepth) {
////                                renderObject = i;
////                                highestDepth = mRenderObjects[i].getDepth();    
////                            }
////                        }
////                    }
////                }
////                
////                // Check if all the objects have been drawn
////                if(renderObject == -1) {
////                    break;
////                }
////                
////                // Draw it
////                mRenderObjects[renderObject].render(g);
////                mDrawCount++;
////                
////                // Flag it as being rendered
////                mRenderFlag[renderObject] = true;
////                
////            }
////            
////            
////            // Blit the back buffer to the screen... ?
////            if(!mBuffer.contentsLost()) {
////                
////                mBuffer.show();
////                
////                //Toolkit.getDefaultToolkit().sync();
////                
////            }
////            
////            //Thread.yield();
////            
////        } finally {
////            
////            if(mGraphics != null) {
////                mGraphics.dispose();
////            }
////            
////        }
//        
//    }
//    
//}
