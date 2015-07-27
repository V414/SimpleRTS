/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.lwjgl.util;

import com.dinasgames.main.Math.BoundingBox;
import com.dinasgames.main.Math.Vector2i;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 *
 * @author Jack
 */
public class Texture {
    
    static protected long id = 1;
    
    private long getUniqueId() {
        return id++;
    }
    
    private static int checkMaximumTextureSize() {
        return GL11.glGetInteger(GL11.GL_MAX_TEXTURE_SIZE);
    }
    
    static protected int MAX_TEXTURE_SIZE = checkMaximumTextureSize();
    
    public static enum CoordinateType {
        Normalized,
        Pixels
    };
    
    int mWidth, mHeight, mActualWidth, mActualHeight, mTexture;
    long mCacheId;
    boolean mSmooth, mRepeated, mPixelsFlipped;
    
    public Texture() {
        mWidth = mHeight = mActualWidth = mActualHeight = 0;
        mTexture = 0;
        mSmooth = mRepeated = mPixelsFlipped = false;
        mCacheId = getUniqueId();
    }
    
    public Texture(Texture other) {
        this();
        this.mSmooth = other.isSmoothed();
        this.mRepeated = other.isRepeated();
        if(other.getTextureID() > 0) {
            loadFromImage(other.copyToImage());
        }
    }
    
    public void destruct() {
        if(mTexture > 0) {
            GL11.glDeleteTextures(mTexture);
        }
    }
    
    public boolean create( int width, int height ) {
        
        if(width == 0 && height == 0) {
            return false;
        }
        
        Vector2i actualSize = new Vector2i(getValidSize(width), getValidSize(height));
        
        int maxSize = getMaximumSize();
        if((actualSize.x > maxSize) || (actualSize.y > maxSize)) {
            System.out.println("Failed to create texture, its internal size is too high (" + actualSize.x + ", " + actualSize.y + ") maximum is (" + maxSize + ", " + maxSize + ")");
            return false;
        }
        
        mWidth = width;
        mHeight = height;
        mActualWidth = actualSize.x;
        mActualHeight = actualSize.y;
        mPixelsFlipped = false;
        
        if(mTexture <= 0) {
            mTexture = GL11.glGenTextures();
        }
        
        int textureEdgeClamp = GL.clampToEdge();
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, mTexture);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, mActualWidth, mActualHeight, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, 0);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, ( mRepeated ? GL11.GL_REPEAT : textureEdgeClamp ));
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, ( mRepeated ? GL11.GL_REPEAT : textureEdgeClamp ));
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, ( mSmooth ? GL11.GL_LINEAR : GL11.GL_NEAREST ));
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, ( mSmooth ? GL11.GL_LINEAR : GL11.GL_NEAREST ));
        mCacheId = getUniqueId();

        return true;
        
    }
    
    public boolean loadFromFile(String filename, BoundingBox area) {
        
        Image image = new Image();
        
        if(!image.loadFromFile(filename)) {
            return false;
        }
        
        return loadFromImage(image, area);
        
    }
    
//    public boolean loadFromMemory(byte[] data, BoundingBox area) {
//        Image image = new Image();
//        return image.loadFromMemory(data) && loadFromImage(image, area);
//    }
    
    
    public boolean loadFromFile(String filename) {
        return loadFromFile(filename, new BoundingBox());
    }
    
    public boolean loadFromImage( Image image ) {
        return loadFromImage( image, new BoundingBox() );
    }
    
    public boolean loadFromImage( Image image, BoundingBox area ) {
        
        int width = image.getWidth();
        int height = image.getHeight();
        
        if( area.width == 0 || area.height == 0 || (area.x <= 0 && area.y <= 0 && area.width >= width && area.height >= height)) {
            
            // Load entire image
            if(create(width, height)) {
                
                update(image);
                GL11.glFlush();
                
                return true;
            }
            
            return false;
            
        }else{
            
            // Load part of the image
            BoundingBox r = new BoundingBox(area);
            if(r.x < 0) { r.x = 0; }
            if(r.y < 0) { r.y = 0; }
            if(r.x + r.width > width) { r.width = width - r.x; }
            if(r.y + r.height > height) { r.height = height - r.y; }
            
            if(create((int)r.width, (int)r.height)) {
                
                ByteBuffer pixels = image.toByteBuffer();
                int pos = 4 * ((int)r.x + (width * (int)r.y));
                
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, mTexture);
                
                for(int i = 0; i < r.height; i ++) {
                    GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, i, (int)r.width, 1, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
                    pos += 4 * width;
                }
                
                GL11.glFlush();
                
                return true;
            }
            
            return false;
            
        }
        
    }
    
    public Image copyToImage() {
        
        if(mTexture <= 0) {
            return null;
        }
        
        ByteBuffer pixels = BufferUtils.createByteBuffer(mWidth * mHeight * 4);
        
        if((mWidth == mActualWidth && mHeight == mActualHeight) && !mPixelsFlipped) {
            // Direct copy
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, mTexture);
            GL11.glGetTexImage(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
        }else{
            
            ByteBuffer allPixels = BufferUtils.createByteBuffer(mActualWidth * mActualHeight * 4);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, mTexture);
            GL11.glGetTexImage(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, allPixels);
            
            byte[] src = allPixels.array();
            byte[] dst = pixels.array();
            int srcPitch = mActualWidth * 4;
            int dstPitch = mActualHeight * 4;
            int srcI = 0;
            int dstI = 0;
            
            if(mPixelsFlipped) {
                srcI += srcPitch * (mHeight -1);
                srcPitch = -srcPitch;
            }
            
            int k = 0;
            for(int i = 0; i < mHeight; i++) {
                for(int j = 0; j < dstPitch; j++) {
                    dst[k++] = src[i+j];
                }                
                srcI += srcPitch;
                dstI += dstPitch;
            }
            
        }
        
        // Create the image
        Image image = new Image();
        image.create(mWidth, mHeight, pixels.array());
        
        return image;
        
    }
    
    protected void update(byte[] pixels) {
        ByteBuffer p = BufferUtils.createByteBuffer(pixels.length);
        for(int i = 0; i < pixels.length; i++) {
            p.put(pixels[i]);
        }
        p.flip();
        update(p, mWidth, mHeight, 0, 0);
    }
    
    protected void update(ByteBuffer pixels) {
        
        update(pixels, mWidth, mHeight, 0, 0);
    }
    
    protected void update(byte[] pixels, int width, int height, int x, int y) {
        ByteBuffer p = BufferUtils.createByteBuffer(pixels.length);
        for(int i = 0; i < pixels.length; i++) {
            p.put(pixels[i]);
        }
        p.flip();
        update(p, width, height, x, y);
    }
    
    protected void update(ByteBuffer pixels, int width, int height, int x, int y) {
        
        assert(x + width <= mWidth);
        assert(y + height <= mHeight);
        
        if(pixels != null && mTexture > 0) {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, mTexture);
            GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, x, y, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
            mPixelsFlipped = false;
            mCacheId = getUniqueId();
        }
        
    }
    
    protected void update(Image image, int x, int y) {
        update(image.getPixelsPtr(), image.getWidth(), image.getHeight(), x, y);
    }
    
    protected void update(Image image) {
        update(image.getPixelsPtr(), image.getWidth(), image.getHeight(), 0, 0);
    }
    
    protected void update(Window window) {
        update(window, 0, 0);
    }
    
    protected void update(Window window, int x, int y) {
        
        assert(x + window.getWidth() <= mWidth);
        assert(y + window.getHeight() <= mHeight);
        
        if(mTexture > 0 && window.setActive(true)) {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, mTexture);
            GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, x, y, 0, 0, window.getWidth(), window.getHeight());
            mPixelsFlipped = true;
            mCacheId = getUniqueId();
        }
        
    }
    
    public Texture setSmooth(boolean smooth) {
        
        if(smooth != mSmooth) {
            
            mSmooth = smooth;
            
            if(mTexture > 0) {
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, mTexture);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, ( mSmooth ? GL11.GL_LINEAR : GL11.GL_NEAREST ));
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, ( mSmooth ? GL11.GL_LINEAR : GL11.GL_NEAREST ));
            }
            
        }
     
        return this;
        
    }
    
    public Texture setRepeated(boolean repeated) {
        
        if(mRepeated != repeated) {
            
            mRepeated = repeated;
            
            if(mTexture > 0) {
                
                int textureEdgeClamp = GL.clampToEdge();
        
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, mTexture);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, ( mRepeated ? GL11.GL_REPEAT : textureEdgeClamp ));
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, ( mRepeated ? GL11.GL_REPEAT : textureEdgeClamp ));
                
            }
            
        }
        
        return this;
        
    }
    
    public static void bind(Texture texture, CoordinateType type) {

        if(texture != null && texture.getTextureID() > 0) {
            
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
            
            if((type == CoordinateType.Pixels) || texture.getPixelsFlipped()) {
                
                float matrix[] = new float[]{   1.f, 0.f, 0.f, 0.f,
                                                0.f, 1.f, 0.f, 0.f,
                                                0.f, 0.f, 1.f, 0.f,
                                                0.f, 0.f, 0.f, 1.f
                };
                
                if(type == CoordinateType.Pixels) {
                    matrix[0] = 1.f / texture.getActualWidth();
                    matrix[5] = 1.f / texture.getActualHeight();
                }
                
                if(texture.getPixelsFlipped()) {
                    matrix[5] = -matrix[5];
                    matrix[13] = (float)(texture.getHeight() / texture.getActualHeight());
                }
                
                GL.setMatrixMode(GL.MatrixMode.Texture);
                GL.loadMatrix(matrix);
                
                GL.setMatrixMode(GL.MatrixMode.ModelView);
                
            }
            
        }else{
            
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
            
            GL.setMatrixMode(GL.MatrixMode.Texture);
            GL.loadIdentity();
            
            GL.setMatrixMode(GL.MatrixMode.ModelView);
            
        }
        
    }
    
    public static int getValidSize(int size) {
        
        if(GL.version >= 20) {
            return size;
        }
        
        int powerOfTwo = 1;
        while(powerOfTwo < size) {
            powerOfTwo *= 2;
        }
        
        return powerOfTwo;
        
    }
    
    public static void bind(Texture texture) {
        bind(texture, CoordinateType.Normalized);
    }
    
    public boolean getPixelsFlipped() {
        return mPixelsFlipped;
    }
    
    public Vector2i getActualSize() {
        return new Vector2i(mActualWidth, mActualHeight);
    }
    
    public int getActualHeight() {
        return mActualHeight;
    }
    
    public int getActualWidth() {
        return mActualWidth;
    }
    
    public int getHeight() {
        return mHeight;
    }
    
    public int getWidth() {
        return mWidth;
    }
    
    public Vector2i getSize() {
        return new Vector2i(mWidth, mHeight);
    }
    
    public int getTextureID() {
        return mTexture;
    }
    
    public boolean isSmoothed() {
        return mSmooth;
    }
    
    public boolean isRepeated() {
        return mRepeated;
    }
    
    public static int getMaximumSize() {
        
        return MAX_TEXTURE_SIZE;
        
    }
    
    public long getCacheId() {
        return mCacheId;
    }
    
    public boolean equals(Texture other) {
        return other.getCacheId() == mCacheId;
    }
    
}
