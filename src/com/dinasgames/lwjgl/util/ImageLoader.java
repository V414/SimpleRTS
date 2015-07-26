/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.lwjgl.util;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;
import org.lwjgl.stb.STBImageWrite;

/**
 *
 * @author Jack
 */
public class ImageLoader {
    
    static ImageLoader Instance = new ImageLoader();
    
    public static class ImageInfo {
        byte[] data;
        int width, height, length;
    };
    
    public static ImageLoader getInstance() {
        
        return Instance;
        
    }
    
    public boolean loadImageFromFile( String filename, ImageInfo info ) {
        
        // Some buffers to store info
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        
        // Reques the image info to ensure that JPG is handled properly
        if( STBImage.stbi_info(filename, width, height, channels) <= 0 ) {
            System.out.println("Failed to load image \"" + filename + "\". Reason: " + STBImage.stbi_failure_reason());
            return false;
        }
        
        // Get the width & height of the image
        info.width = width.get(0);
        info.height = height.get(0);
        
        // Check for a bad image size
        if(info.width <= 0 || info.height <= 0) {
            System.out.println("Failed to load image \"" + filename + "\". Reason: " + STBImage.stbi_failure_reason());
            return false;
        }
        
        // Get the number of channels in ths image
        int channelSize = channels.get(0);
        
        // Read the image
        ByteBuffer buffer = STBImage.stbi_load(filename, width, height, channels, channelSize);
        
        // Ensure we have a valid buffer
        if(buffer == null) {
            System.out.println("Failed to load image \"" + filename + "\". Reason: " + STBImage.stbi_failure_reason());
            return false;
        }
        
        // Convert the data into RGBA
        info.length = info.width * info.height * 4;
        info.data = new byte[info.length];

        //buffer.flip();

        int i = 0;
        int j = 0;
        while( i < info.length ) {

            // Write the channel data that is available
            for(int k = 0; k < channelSize; k++) {
                info.data[i++] = buffer.get(j++);
                //info.data[i++] = buffer.get();
            }

            // Ensure we have RGBA even if it isn't there
            for(int k = channelSize; k < 4; k++) {
                info.data[i++] = (byte)255;
            }

        }
            
        // Free the image data as we have copied it
        STBImage.stbi_image_free(buffer);

        return true;

    }
    
    public boolean saveImageToFile( String filename, byte[] pixels, int width, int height ) {
        
        if(pixels == null || pixels.length == 0 || width <= 0 || height <= 0) {
            return false;
        }
        
        ByteBuffer buffer = BufferUtils.createByteBuffer(pixels.length);
        for(int i = 0; i < pixels.length; i++) {
            buffer.put(pixels[i]);
        }
        
        buffer.flip();
        
        if(filename.length() > 3) {
            
            String ext = filename.substring(filename.length() - 3).toLowerCase();
            
            if(ext.equals("bmp")) {
                if(STBImageWrite.stbi_write_bmp(filename, width, height, 4, buffer) > 0) {
                    return true;
                }
            }else if(ext.equals("tga")) {
                if(STBImageWrite.stbi_write_tga(filename, width, height, 4, buffer) > 0) {
                    return true;
                }
            }else if(ext.equals("png")) {
                if(STBImageWrite.stbi_write_png(filename, width, height, 4, buffer, 0) > 0) {
                    return true;
                }
            }
            
            System.out.println("Failed to save image \"" + filename + "\". Unsupported format " + ext);
            return false;
            
        }
        
        System.out.println("Failed to save image \"" + filename + "\".");
        
        return false;
        
    }
    
}
