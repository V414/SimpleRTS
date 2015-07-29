/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.engine.window;

import com.dinasgames.engine.graphics.GL;
import com.dinasgames.engine.system.Keyboard;
import com.dinasgames.engine.system.Mouse;
import com.dinasgames.engine.graphics.RenderTarget;
import com.dinasgames.engine.graphics.View;
import com.dinasgames.engine.math.Vector2f;
import com.dinasgames.engine.math.Vector2i;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import static org.lwjgl.glfw.GLFW.GLFW_FOCUSED;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_2;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_3;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwHideWindow;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11; //.GL_COLOR_BUFFER_BIT;
//import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
//import static org.lwjgl.opengl.GL11.GL_FALSE;
//import static org.lwjgl.opengl.GL11.GL_TRUE;
//import static org.lwjgl.opengl.GL11.glClear;
//import static org.lwjgl.opengl.GL11.glClearColor;
import org.lwjgl.opengl.GLContext;
import static org.lwjgl.system.MemoryUtil.NULL;



/**
 *
 * @author Jack
 */
public class Window extends RenderTarget {
    
    protected long mHandle;
    
    protected DoubleBuffer b1 = BufferUtils.createDoubleBuffer(1);
    protected DoubleBuffer b2 = BufferUtils.createDoubleBuffer(1);
    
    public Window() throws RuntimeException {
        
        
        
        // Setup some hints
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE,    GL11.GL_FALSE);       // <<< Window won't be visible when created
        glfwWindowHint(GLFW_RESIZABLE,  GL11.GL_FALSE);       // <<< Window won't be resizable when created
        glfwWindowHint(GLFW_FOCUSED ,   GL11.GL_TRUE);        // <<< The window will have focus when created.
        
        // Attempt to create the window
        mHandle = glfwCreateWindow(640, 480, "Hello World!", NULL, NULL);
        if ( mHandle == NULL ) {
            throw new RuntimeException("Failed to create the GLFW window");
        }
        
        // Get the resolution of the primary monitor
        ByteBuffer videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        
        // Center our window
        glfwSetWindowPos(
            mHandle,
            (GLFWvidmode.width(videoMode) - 640) / 2,
            (GLFWvidmode.height(videoMode) - 480) / 2
        );

        // Make the OpenGL context current
        glfwMakeContextCurrent(mHandle);
        
        // Enable v-sync
       glfwSwapInterval(0);

        // Make the window visible
        glfwShowWindow(mHandle);
        
        GL.setContext(GLContext.createFromCurrent());
        
//        GL11.glMatrixMode(GL11.GL_PROJECTION);
//        GL11.glViewport(0, 0, 640, 480);
//        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        
        
        
        initialize();
        
        // TODO:
        
        // Window damage
        // glfwSetWindowRefreshCallback(m_handle, window_refresh_callback);
//        void window_refresh_callback(GLFWwindow* window)
//        {
//            draw_editor_ui(window);
//            glfwSwapBuffers(window);
//        }
        
        // Fullscreen
//        GLFWwindow* window = glfwCreateWindow(640, 480, "My Title", glfwGetPrimaryMonitor(), NULL);
        
        
        // Window maximised
//const GLFWvidmode* mode = glfwGetVideoMode(monitor);
//glfwWindowHint(GLFW_RED_BITS, mode->redBits);
//glfwWindowHint(GLFW_GREEN_BITS, mode->greenBits);
//glfwWindowHint(GLFW_BLUE_BITS, mode->blueBits);
//glfwWindowHint(GLFW_REFRESH_RATE, mode->refreshRate);
//GLFWwindow* window = glfwCreateWindow(mode->width, mode->height, "My Title", monitor, NULL);
        
        // Window close event
        // glfwSetWindowCloseCallback(window, window_close_callback);
//void window_close_callback(GLFWwindow* window)
//{
//    if (!time_to_close)
//        glfwSetWindowShouldClose(window, GL_FALSE);
//}
        



        
        //GL11.glMatrixMode(GL11.GL_PROJECTION);
        //GL11.glLoadIdentity();
        
        //GL11.glMatrixMode(GL11.GL_MODELVIEW);
        
    }
    
    /**
     * Set the size of the displayed view. USE WITH CAUTION!
     * @param width
     * @param height
     * @return 
     */
    public Window setFrameSize(int width, int height) {
        
//        GL11.glMatrixMode(GL11.GL_PROJECTION);
//        GL11.glLoadIdentity();
//        GL11.glOrtho(0, width, height, 0, 0, 1);
//        GL11.glMatrixMode(GL11.GL_MODELVIEW);
//        GL11.glLoadIdentity();
//        
//        glfwMakeContextCurrent(mHandle);
//        GLContext.createFromCurrent();
        
        setView(new View(0.f, 0.f, (float)width, (float)height));
        
//        GL11.glMatrixMode(GL11.GL_PROJECTION);
//        GL11.glViewport(0, 0, width, height);
//        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        
        return this;
    }
    
    /**
     * Get the operating system handle for this window.
     * @return 
     */
    public long getHandle() {
        return mHandle;
    }
    
    /**
     * Set the position of the window on screen.
     * @param x
     * @param y
     * @return 
     */
    public Window setPosition(int x, int y) {
        glfwSetWindowPos(mHandle, x, y);
        return this;
    }
    
    /**
     * Set the size of the window.
     * @param width
     * @param height
     * @return 
     */
    public Window setSize(int width, int height) {
        glfwSetWindowSize(mHandle, width, height);
        setFrameSize(width,height);
        return this;
    }
    
    /**
     * Set the window caption.
     * @param title
     * @return 
     */
    public Window setTitle(String title) {
        glfwSetWindowTitle(mHandle, title);
        return this;
    }
    
    /**
     * Show the window.
     * @return 
     */
    public Window show() {
        glfwShowWindow(mHandle);
        return this;
    }
    
    /**
     * Hide the window.
     * @return 
     */
    public Window hide() {
        glfwHideWindow(mHandle);
        return this;
    }
    
    /**
     * Center the window.
     * @return 
     */
    public Window center() {
        ByteBuffer videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        Vector2i windowSize = getSize();
        
        // Center our window
        glfwSetWindowPos(
            mHandle,
            (GLFWvidmode.width(videoMode) - windowSize.x) / 2,
            (GLFWvidmode.height(videoMode) - windowSize.y) / 2
        );
        return this;
    }
    
    /**
     * Returns whether the window should be closed. i.e. the window has been asked to close by the operating system.
     * @return 
     */
    public boolean shouldClose() {
        return (glfwWindowShouldClose(mHandle) == GL11.GL_TRUE);
    }
    
    /**
     * Close the window.
     * @return 
     */
    public Window close() {
        glfwDestroyWindow(mHandle);
        return this;
    }
    
    /**
     * Get the window size.
     * @return 
     */
    @Override
    public Vector2i getSize() {
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        GLFW.glfwGetWindowSize(mHandle, w, h);
        return new Vector2i( w.get(0), h.get(0));
    }
    
    @Override
    public int getWidth() {
        return getSize().x;
    }
    
    @Override
    public int getHeight() {
        return getSize().y;
    }
    
    /**
     * Check if the window is in focus.
     * @return 
     */
    public boolean isFocused() {
        return (GLFW.glfwGetWindowAttrib(mHandle, GLFW.GLFW_FOCUSED) == 1);
    }
    
    /**
     * Check if the window is resizable.
     * @return 
     */
    public boolean isResizable() {
        return (GLFW.glfwGetWindowAttrib(mHandle, GLFW.GLFW_RESIZABLE) == 1);
    }
    
    /**
     * Check if the window is visible.
     * @return 
     */
    public boolean isVisible() {
        return (GLFW.glfwGetWindowAttrib(mHandle, GLFW.GLFW_VISIBLE) == 1);
    }
    
    /**
     * Get the window position.
     * @return 
     */
    public Vector2f getPosition() {
        IntBuffer x = BufferUtils.createIntBuffer(1);
        IntBuffer y = BufferUtils.createIntBuffer(1);
        GLFW.glfwGetWindowPos(mHandle, x, y);
        return new Vector2f( (float)x.get(0), (float)y.get(0));
    }
    
    public Window update() {
        
        // Poll window events
        glfwPollEvents();
        
        // Update mouse position
        glfwGetCursorPos(mHandle, b1, b2);
        
        Mouse.windowX = (float)b1.get(0);
        Mouse.windowY = (float)b2.get(0);
        
        // Update mouse button states
        Mouse.mButtonState[1] = ( glfwGetMouseButton(mHandle, GLFW_MOUSE_BUTTON_1) == 1 );
        Mouse.mButtonState[2] = ( glfwGetMouseButton(mHandle, GLFW_MOUSE_BUTTON_2) == 1 );
        Mouse.mButtonState[3] = ( glfwGetMouseButton(mHandle, GLFW_MOUSE_BUTTON_3) == 1 );
        
        // Update keyboard input
        Keyboard.initKeys();
        for(int i = 0; i < Keyboard.KEY_COUNT; i++) {
            Keyboard.stateList[i] = (glfwGetKey(mHandle, Keyboard.nativeList[i]) == 1);
        }
        
        return this;
    }
    
//    /**
//     * Clear the display with color.
//     * @param color
//     * @return 
//     */
//    public Window clear(GLColor color) {
//        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // clear the framebuffer
//        GL11.glClearColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() );
//        return this;
//    }
//    
//    /**
//     * Clear the display with a white colour.
//     * @return 
//     */
//    public Window clear() {
//        return clear(GLColor.WHITE);
//    }
//    
//    /**
//     * Clear the display with color.
//     * @param color
//     * @return 
//     */
//    public Window clear(Color color) {
//        return clear(color.toGLColor());
//    }
    
    @Override
    public boolean activate(boolean active) {
        if(GLFW.glfwGetCurrentContext() != mHandle) {
            glfwMakeContextCurrent(mHandle);
        }
        //glfwMakeContextCurrent(mHandle);
        return true;
    }
    
    public boolean setActive(boolean active) {
        return activate(active);
    }
    
    public Window display() {
        
//        GL11.glColor4f(0.5f, 0.5f, 1.0f, 1.0f);
//        
//        GL11.glBegin(GL11.GL_QUADS);
//            GL11.glVertex2f(100.f, 100.f);
//            GL11.glVertex2f(200.f, 100.f);
//            GL11.glVertex2f(200.f, 200.f);
//            GL11.glVertex2f(100.f, 200.f);
//        GL11.glEnd();
        
        
        glfwSwapBuffers(mHandle); // swap the color buffers
        
        return this;
        
    }
    
}
