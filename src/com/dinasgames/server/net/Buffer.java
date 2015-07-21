/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.server.net;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

/**
 *
 * @author Jack
 */
public class Buffer {
    
    protected int mReadPos, mWritePos;
    protected ByteBuffer mBuffer;
    
    public Buffer() {
        mBuffer = null;
        mReadPos = mWritePos = 0;
    }
    
    public Buffer(byte[] data) {
        if(data != null && data.length > 0) {
            resize(data.length);
            for(int i = 0; i < data.length; i++) {
                mBuffer.put(i, data[i]);
            }
            onWrite(data.length);
        }
    }
    
    public Buffer(Buffer data) {
        this(data.copyData());
        this.mReadPos = data.getReadPosition();
        this.mWritePos = data.getWritePosition();
    }
    
    protected void resize(int size) {
        
        if(mBuffer == null) {
            mBuffer = ByteBuffer.allocate(size);
        }else{
            ByteBuffer oldBuffer = mBuffer;
            mBuffer = ByteBuffer.allocate(mWritePos + size);
            mBuffer.put(oldBuffer.array());
        }
        
    }
    
    public int getWritePosition() {
        return mWritePos;
    }
    
    public int getReadPosition() {
        return mReadPos;
    }
    
    public byte[] getData() {
        if(mBuffer == null) {
            return null;
        }
        if(!mBuffer.hasArray()) {
            return null;
        }
        return mBuffer.array();
    }
    
    public byte[] copyData() {
        
        if(mBuffer == null) {
            return null;
        }
        
        if(!mBuffer.hasArray()) {
            return null;
        }
        
        byte[] src = mBuffer.array();
        byte[] cpy = new byte[src.length];
        for(int i = 0; i < src.length; i++) {
            cpy[i] = src[i];
        }
        
        return cpy;
        
    }
    
    protected void onWrite(int size) {
        mWritePos += size;
    }
    
    protected void onRead(int size) {
        mReadPos += size;
    }
    
    public void setWritePosition(int p) {
        mWritePos = p;
    }
    
    public void setReadPosition(int p) {
        mReadPos = p;
    }
    
    /**
     * Clear the buffer.
     */
    public void clear() {
        mReadPos = 0;
        mWritePos = 0;
        mBuffer = null;
    }
    
    /**
     * Get the size of the buffer in bytes.
     * @return 
     */
    public int size() {
        if(mBuffer == null) {
            return 0;
        }
        return mWritePos;
    }
    
    /**
     * Writes a char value to the buffer.
     * @param value 
     */
    public void writeChar(char value) {
        resize(2);
        mBuffer.putChar(mWritePos, value);
        onWrite(2);
    }
    
    /**
     * Writes an int value to the buffer.
     * @param value 
     */
    public void writeInt(int value) {
        resize(4);
        mBuffer.putInt(mWritePos, value);
        onWrite(4);
    }
    
    /**
     * Writes a double value to the buffer.
     * @param value 
     */
    public void writeDouble(double value) {
        resize(8);
        mBuffer.putDouble(mWritePos, value);
        onWrite(8);
    }
    
    /**
     * Writes a float value to the buffer.
     * @param value 
     */
    public void writeFloat(float value) {
        resize(4);
        mBuffer.putFloat(mWritePos, value);
        onWrite(4);
    }
    
    /**
     * Writes a long value to the buffer.
     * @param value 
     */
    public void writeLong(long value) {
        resize(8);
        mBuffer.putLong(mWritePos, value);
        onWrite(8);
    }
    
    /**
     * Writes a short value to the buffer.
     * @param value 
     */
    public void writeShort(short value) {
        resize(2);
        mBuffer.putShort(mWritePos, value);
        onWrite(2);
    }
    
    /**
     * Writes a String value to the buffer.
     * @param value 
     */
    public void writeString(String value) {
        writeInt(value.length());
        for(int i = 0; i < value.length(); i++) {
            writeChar(value.charAt(i));
        }
    }
    
    public void writeBuffer(Buffer other) {
        
    }
    
    /**
     * Read a char from the buffer.
     * @return 
     */
    public char readChar() {
        char c = mBuffer.getChar(mReadPos);
        onRead(2);
        return c;
    }
    
    /**
     * Read a short from the buffer.
     * @return 
     */
    public short readShort() {
        short c = mBuffer.getShort(mReadPos);
        onRead(2);
        return c;
    }
    
    /**
     * Read an int from the buffer.
     * @return 
     */
    public int readInt() {
        int c = mBuffer.getInt(mReadPos);
        onRead(4);
        return c;
    }
    
    /**
     * Read a long from the buffer
     * @return 
     */
    public long readLong() {
        long c = mBuffer.getLong(mReadPos);
        onRead(8);
        return c;
    }
    
    /**
     * Read a float from the buffer
     * @return 
     */
    public float readFloat() {
        float c = mBuffer.getFloat(mReadPos);
        onRead(4);
        return c;
    }
    
    /**
     * Read a double from the buffer.
     * @return 
     */
    public double readDouble() {
        double c = mBuffer.getDouble(mReadPos);
        onRead(8);
        return c;
    }
    
    /**
     * Read a string from the buffer.
     * @return
     */
    public String readString() {
        String s = new String();
        int size = readInt();
        for(int i = 0; i < size; i ++) {
            s += readChar();
        }
        return s;
    }
    
}
