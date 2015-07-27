//Our Main Method - should be quite empty for now

package com.dinasgames.lwjgl;

import com.dinasgames.lwjgl.util.CircleShape;
import com.dinasgames.lwjgl.util.Color;
import com.dinasgames.lwjgl.util.ConvexShape;
import com.dinasgames.lwjgl.util.Image;
import com.dinasgames.lwjgl.util.LWJGL;
import com.dinasgames.lwjgl.util.Mouse;
import com.dinasgames.lwjgl.util.RectangleShape;
import com.dinasgames.lwjgl.util.RenderStates;
import com.dinasgames.lwjgl.util.RenderWindow;
import com.dinasgames.lwjgl.util.Renderable;
import com.dinasgames.lwjgl.util.Renderer;
import com.dinasgames.lwjgl.util.Texture;
import com.dinasgames.lwjgl.util.View;
import com.dinasgames.lwjgl.util.Window;
import com.dinasgames.main.math.RandomNumber;
import com.dinasgames.main.math.Vector2f;
import com.dinasgames.main.system.Time;
import com.dinasgames.main.system.Timer;
import org.lwjgl.Sys;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
 
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
 
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main {
    
    public static class HelloWorld {
 
        // We need to strongly reference callback instances.
        private GLFWErrorCallback errorCallback;
        private GLFWKeyCallback   keyCallback;

        // The window handle
        private long window;

        public void run() {
            System.out.println("Hello LWJGL " + Sys.getVersion() + "!");

            try {
                init();
                loop();

                // Release window and window callbacks
                glfwDestroyWindow(window);
                keyCallback.release();
            } finally {
                // Terminate GLFW and release the GLFWerrorfun
                glfwTerminate();
                errorCallback.release();
            }
        }

        private void init() {
            // Setup an error callback. The default implementation
            // will print the error message in System.err.
            glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));

            // Initialize GLFW. Most GLFW functions will not work before doing this.
            if ( glfwInit() != GL11.GL_TRUE )
                throw new IllegalStateException("Unable to initialize GLFW");

            // Configure our window
            glfwDefaultWindowHints(); // optional, the current window hints are already the default
            glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
            glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable

            int WIDTH = 300;
            int HEIGHT = 300;

            // Create the window
            window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
            if ( window == NULL )
                throw new RuntimeException("Failed to create the GLFW window");

            // Setup a key callback. It will be called every time a key is pressed, repeated or released.
            glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
                @Override
                public void invoke(long window, int key, int scancode, int action, int mods) {
                    if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                        glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop
                }
            });

            // Get the resolution of the primary monitor
            ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            // Center our window
            glfwSetWindowPos(
                window,
                (GLFWvidmode.width(vidmode) - WIDTH) / 2,
                (GLFWvidmode.height(vidmode) - HEIGHT) / 2
            );

            // Make the OpenGL context current
            glfwMakeContextCurrent(window);
            // Enable v-sync
            glfwSwapInterval(1);

            // Make the window visible
            glfwShowWindow(window);
        }

        private void loop() {
            // This line is critical for LWJGL's interoperation with GLFW's
            // OpenGL context, or any context that is managed externally.
            // LWJGL detects the context that is current in the current thread,
            // creates the ContextCapabilities instance and makes the OpenGL
            // bindings available for use.
            GLContext.createFromCurrent();

            // Set the clear color
            glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

            // Run the rendering loop until the user has attempted to close
            // the window or has pressed the ESCAPE key.
            while ( glfwWindowShouldClose(window) == GL_FALSE ) {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

                glfwSwapBuffers(window); // swap the color buffers

                // Poll for window events. The key callback above will only be
                // invoked during this call.
                glfwPollEvents();
            }
        }
    
    }
 
    protected static int fps = 0;
    
    public static void main(String[] args) {
        //new HelloWorld().run();
        
        fps = 0;
        
        try {
        
            
            
            LWJGL.init();
            
            // Try load some images
            //Image image = new Image();
            //boolean result = image.loadFromFile("E:\\10696391_10152692979881840_4672426392263692820_n.jpg");
            //boolean result = image.loadFromFile("E:\\AF2.png");
            //image.flipVertically();
            //boolean result2 = image.saveToFile("E:\\output.png");
            //System.out.println("Result: " + result);
            //System.out.println("Result 2: " + result2);
            
            int width, height;
            width = 1280;
            height = 720;
            
            // Create window
            RenderWindow window = new RenderWindow();
            
            //Renderer renderer = new Renderer();
            
            int ww, hh;
            ww = 400;
            hh = 400;
            
            Image image3 = new Image();
            
            image3.create(ww, hh, Color.ORANGE);
            
            for(int x = 0; x < ww; x++) {
                for(int y = 0; y < hh; y++) {
                    image3.setPixel(x, y, Color.getRandom());
                }
            }
            
            Texture t2 = new Texture();
            t2.loadFromImage(image3);
            
            RectangleShape s = new RectangleShape((float)ww, (float)hh);
            s.setTexture(t2);
            
            window.getRenderer().add(s);
            
            Texture t = new Texture();
            t.loadFromFile("E:\\AF2.png");
            
            for(int i = 0; i < 10; i++) {
                RectangleShape r = new RectangleShape((float)32, (float)32);

                r.setPosition((i+1)*64.f, (i+1)*48 );
                r.setFillColor(new Color(RandomNumber.between(0, 255), RandomNumber.between(0, 255), RandomNumber.between(0, 255), RandomNumber.between(0, 255)));
                r.setOutlineColor(new Color(RandomNumber.between(0, 255), RandomNumber.between(0, 255), RandomNumber.between(0, 255), RandomNumber.between(0, 255)));
                r.setOutlineThickness(10.f);
                r.setOrigin(32.f / 2.f, 32.f / 2.f);
                r.setRotation(0.f);
                r.setTexture(t);

                window.getRenderer().add(r);
            }
            
            window.setSize(width, height);
            window.center();
            
            

            Timer.every(Time.seconds(1.f), () -> {
                
                System.out.println("FPS: " + fps);
                System.out.println("Draw count: " + window.getRenderer().getDrawCount());
                
                fps = 0;
                
            });
            
            Timer.every(Time.seconds(1.f), () ->{
                
                Image image = window.capture();
                image.saveToFile("E:\\output.png");
                
            });
            
            while(!window.shouldClose()) {
                
                // Update window logic & events
                window.update();
                Timer.update();
                
                for(int i = 0; i < 10; i++) {
                    //window.getRenderer().get(i).move(1.f,0.f);
                }
                
                //r.rotate(1.f);
                
                // Render
                //window.clear(Color.BLACK);
                //window.draw(r);
                //renderer.render(window);
                
                
                
                //renderer.render(window);                
                
//                FloatBuffer cBuffer = BufferUtils.createFloatBuffer(9);
//                cBuffer.put(1).put(0).put(0);
//                cBuffer.put(0).put(1).put(0);
//                cBuffer.put(0).put(0).put(1);
//                cBuffer.flip();
//
//                FloatBuffer vBuffer = BufferUtils.createFloatBuffer(9);
//                vBuffer.put(-0.5f).put(1.f).put(0.0f);
//                vBuffer.put(+0.5f).put(-0.5f).put(0.0f);
//                vBuffer.put(+0.5f).put(+0.5f).put(0.0f);
//                vBuffer.flip();
//
//                GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
//                GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
//
//                GL11.glColorPointer(3, /* stride */3 << 2, cBuffer);
//                GL11.glVertexPointer(3, /* stride */3 << 2, vBuffer);
//
//                GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, /* elements */3);
//
//                GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
//                GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
//                
                window.display();
                
                fps++;
                
                
            }
            
        } catch( RuntimeException e ) {
            e.printStackTrace();
            
        } finally {
            LWJGL.free();
        }
        
    };
    
}
