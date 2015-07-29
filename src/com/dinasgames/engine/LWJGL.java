/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.engine;

import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author Jack
 */
public class LWJGL {
    
    protected static GLFWErrorCallback errorCallback;
    protected static GLFWKeyCallback   keyCallback;
    
    public static void init() {
        
        glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( glfwInit() != GL11.GL_TRUE ) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        
    }
    
    public static void free() {
        glfwTerminate();
        errorCallback.release();
    }
    
}
