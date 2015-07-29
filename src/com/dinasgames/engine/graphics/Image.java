/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.engine.graphics;

import com.dinasgames.engine.math.BoundingBox;
import com.dinasgames.engine.math.Vector2i;
import java.io.InputStream;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;

/**
 *
 * @author Jack
 */
public class Image {
    
    protected int mWidth, mHeight;
    protected byte[] mPixels;
    
    public Image() {
        mWidth = mHeight = 0;
        mPixels = null;
    }
    
    /**
     * Create an image of width,height in pixels and fill it with color. WARNING: Any previous data will be overwritten.
     * If given a width of 0 and a height of 0 this function will clear the current image data.
     * @param width
     * @param height
     * @param color
     * @return 
     */
    public Image create( int width, int height, Color color ) {
        
        if(width > 0 && height > 0) {
            
            // Apply the new size
            mWidth = width;
            mHeight = height;
            
            int size = width * height * 4;

            // Create a new pixel array
            mPixels = new byte[size];
            
            // Fill it with the color
            int i = 0;
            while(i < size) {
                
                mPixels[i++] = (byte)color.getRed();
                mPixels[i++] = (byte)color.getGreen();
                mPixels[i++] = (byte)color.getBlue();
                mPixels[i++] = (byte)color.getAlpha();

            }
            
        }else{
            mWidth = mHeight = 0;
            mPixels = null;
        }
        
        return this;
    }
    
    /**
     * Create an image of size width,height using the pixel data from pixels
     * @param width
     * @param height
     * @param pixels
     * @return 
     */
    public Image create( int width, int height, byte[] pixels ) {
        
        if( pixels != null && width > 0 && height > 0 ) {
            
            // Apply the size
            mWidth = width;
            mHeight = height;
            
            // Copy the pixels
            int size = width * height * 4;
            
            if(pixels.length < size) {
                // Not enough pixels!!!
                System.out.println("WARNING: Image::create has been given pixels of the wrong size.");
            }
            
            mPixels = new byte[size];
                        
            for(int i = 0; i < size; i ++) {
                mPixels[i] = pixels[i];
            }
            
        }else{
            
            mWidth = mHeight = 0;
            mPixels = null;
            
        }
        
        return this;
        
    }
    
    /**
     * Load an image from a file using filename. Supported formats include PNG, BMP, JPG and TGA.
     * @param filename
     * @return 
     */
    public boolean loadFromFile(String filename) {
        
        ImageLoader.ImageInfo info = new ImageLoader.ImageInfo();
        
        boolean result = ImageLoader.getInstance().loadImageFromFile(filename, info);
        
        if(!result) {
            return false;
        }
        
        // Copy pixels
        mPixels     = info.data;
        mWidth      = info.width;
        mHeight     = info.height;
        
        return true;
        
    }
    
//    public boolean loadFromMemory(String data) {
//        
//        Vector2i size = new Vector2i(0, 0);
//        
//        boolean result = ImageLoader.getInstance().loadImageFromFile(data, mPixels, size);
//        mWidth = size.x;
//        mHeight = size.y;
//        
//        return result;
//        
//    }
//    
//    public boolean loadFromStream(InputStream stream) {
//        
//        Vector2i size = new Vector2i(0, 0);
//        
//        boolean result = ImageLoader.getInstance().loadImageFromFile(stream, mPixels, size);
//        mWidth = size.x;
//        mHeight = size.y;
//        
//        return result;
//        
//    }
    
    /**
     * Save the contained image to filename. Supported formats are PNG, TGA and BMP.
     * @param filename
     * @return 
     */
    public boolean saveToFile(String filename) {
        
        return ImageLoader.getInstance().saveImageToFile(filename, mPixels, mWidth, mHeight);
        
    }
    
    /**
     * Get the size of the contained image.
     * @return 
     */
    public Vector2i getSize() {
        return new Vector2i(mWidth, mHeight);
    }
    
    /**
     * Get the width of the contained image.
     * @return 
     */
    public int getWidth() {
        return mWidth;
    }
    
    /**
     * Get the height of the contained image.
     * @return 
     */
    public int getHeight() {
        return mHeight;
    }
    
    /**
     * This function replaces any colour matching color with a new alpha value of alpha. Given 0 as alpha this will make the pixels transparent.
     * @param color
     * @param alpha
     * @return 
     */
    public Image createMaskFromColor(Color color, byte alpha) {
        
        if(mPixels != null) {
            
            int i = 0;
            while(i < mPixels.length) {
                
                if(mPixels[i] == color.r && mPixels[i + 1] == color.g && mPixels[i + 2] == color.b && mPixels[i + 3] == color.a) {
                    mPixels[i+3] = alpha;
                }
                
                i += 4;
                
            }
            
        }
        
        return this;
        
    }
    
    /**
     * Get the raw pixel data stored within this image.
     * @return 
     */
    public byte[] getPixelsPtr() {
        return mPixels;
    }
    
    /**
     * Copy the image source onto destX,destY of this image using sourceBox as a clipping box.
     * @param source
     * @param destX
     * @param destY
     * @param sourceBox
     * @param applyAlpha
     * @return 
     */
    public Image copy( Image source, int destX, int destY, BoundingBox sourceBox, boolean applyAlpha ) {
        
        // Ensure both images are valid
        if( source.getWidth() == 0 || source.getHeight() == 0 || mWidth == 0 || mHeight == 0 ) {
            return this;
        }
        
        BoundingBox srcBox = sourceBox;
        if(srcBox.width == 0 && srcBox.height == 0) {
            srcBox.x = 0;
            srcBox.y = 0;
            srcBox.width = source.getWidth();
            srcBox.height = source.getHeight();
        }else{
            if(srcBox.x < 0) { srcBox.x = 0; }
            if(srcBox.y < 0) { srcBox.y = 0; }
            if(srcBox.width > mWidth){ srcBox.width = mWidth; }
            if(srcBox.height > mHeight) { srcBox.height = mHeight; }
        }
        
        int width = (int)srcBox.width;
        int height = (int)srcBox.height;
        
        if( destX + width > mWidth ) { width = mWidth - destX; }
        if( destY + height > mHeight ) { height = mHeight - destY; }
        
        if(width <= 0 || height <= 0) {
            return this;
        }
        
        int pitch = width * 4;
        int rows = height;
        int srcStride = source.getWidth() * 4;
        int dstStride = mWidth * 4;
        int srcPixels = ((int)srcBox.x + (int)srcBox.y + source.getWidth()) * 4;
        int dstPixels = (destX + destY + mWidth) * 4;
        byte[] src = source.getPixelsPtr();
        byte[] dst = mPixels;
        
        if(applyAlpha) {
            
            for(int i = 0; i < rows; ++i) {
                for( int j = 0; j < width; ++j) {
                    
                    int srcI = srcPixels + (j * 4);
                    int dstI = dstPixels + (j * 4);
                    
                    byte alpha = src[srcI + 3];
                    dst[dstI + 0] = (byte) ((src[srcI + 0] * alpha + dst[dstI + 0] * (255 - alpha)) / 255);
                    dst[dstI + 1] = (byte) ((src[srcI + 1] * alpha + dst[dstI + 1] * (255 - alpha)) / 255);
                    dst[dstI + 2] = (byte) ((src[srcI + 2] * alpha + dst[dstI + 2] * (255 - alpha)) / 255);
                    dst[dstI + 3] = (byte)(alpha + dst[dstI + 3] * (255 - alpha) / 255);
                    
                }
                srcPixels += srcStride;
                dstPixels += dstStride;
            
            }
        }else{
            
            for(int i = 0; i < rows; ++i) {
                
                for(int q = 0; q < pitch; q++) {
                    dst[srcPixels + q] = src[dstPixels + q];
                }
                
                srcPixels += srcStride;
                dstPixels += dstStride;
            }
            
        }
        
        return this;
        
    }
    
    public Image copy( Image source, int destX, int destY, BoundingBox sourceBox ) {
        return copy( source, destX, destY, sourceBox, false );
    }
    
    public Image copy( Image source, int destX, int destY ) {
        return copy( source, destX, destY, new BoundingBox(0, 0, 0, 0) );
    }
    
    /**
     * Replace the pixel at x,y with the given color.
     * @param x
     * @param y
     * @param color
     * @return 
     */
    public Image setPixel( int x, int y, Color color ) {
        
        if(mPixels == null) {
            return this;
        }
        
        int i = (x + y * mWidth) * 4;
        mPixels[i++] = (byte)color.r;
        mPixels[i++] = (byte)color.g;
        mPixels[i++] = (byte)color.b;
        mPixels[i++] = (byte)color.a;
        
        return this;
        
    }
    
    /**
     * Get the color of the given pixel x,y.
     * @param x
     * @param y
     * @return 
     */
    public Color getPixel( int x, int y ) {
        
        if(mPixels == null) {
            return null;
        }
        
        int i = (x + y * mWidth) * 4;
        byte r = mPixels[i++];
        byte g = mPixels[i++];
        byte b = mPixels[i++];
        byte a = mPixels[i++];
        
        return new Color( r, g, b, a );
        
    }
    
    /**
     * Flip the image horizontally.
     * @return 
     */
    public Image flipHorizontally() {
        
        if(mPixels != null) {
            
            int rowSize = mWidth * 4;
            
            for(int y = 0; y < mHeight; ++y) {
                
                int left = y * rowSize;
                int right = (y + 1) * rowSize - 4;
                
                for(int x = 0; x < mWidth / 2; ++x ) {
                    
                    byte r,g,b,a;
                    r = mPixels[left];
                    g = mPixels[left+1];
                    b = mPixels[left+2];
                    a = mPixels[left+3];
                    
                    mPixels[left] = mPixels[right];
                    mPixels[left+1] = mPixels[right+1];
                    mPixels[left+2] = mPixels[right+2];
                    mPixels[left+3] = mPixels[right+3];
                    
                    mPixels[right] = r;
                    mPixels[right+1] = g;
                    mPixels[right+2] = b;
                    mPixels[right+3] = a;
                    
                    left += 4;
                    right -= 4;
                    
                }
                
            }
            
        }
        
        return this;
        
    }
    
    /**
     * Flip the image vertically.
     * @return 
     */
    public Image flipVertically() {
        
        if(mPixels != null) {
            
            int rowSize = mWidth * 4;
            
            int top = 0;
            int bottom = mPixels.length - rowSize;
            
            byte[] cpy = new byte[rowSize];
            
            for(int y = 0; y < mHeight / 2; ++y) {    
                
                for(int i = 0; i < rowSize; i++) {
                    cpy[i] = mPixels[top+i];
                }
                
                for(int i = 0; i < rowSize; i++) {
                    mPixels[top+i] = mPixels[bottom+i];
                }
                
                for(int i = 0; i < rowSize; i++) {
                    mPixels[bottom+i] = cpy[i];
                }
                
                top += rowSize;
                bottom -= rowSize;
                
            }
            
        }
        
        return this;
        
    }
    
    public ByteBuffer toByteBuffer() {
        ByteBuffer b = BufferUtils.createByteBuffer(mPixels.length);
        for(int i = 0; i < mPixels.length; i++) {
            b.put(mPixels[i]);
        }
        b.flip();
        return b;
    }
    
    public Image create(int width, int height, ByteBuffer buffer) {
        int len = width * height * 4;
        byte[] p = new byte[len];
        for(int i = 0; i < len; i++) {
            p[i] = buffer.get();
        }
        return create(width, height, p);
    }
    
}
