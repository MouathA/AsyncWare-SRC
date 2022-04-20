package com.nquantum.util.font;

import java.nio.*;

public class TextureData
{
    private ByteBuffer buffer;
    private int textureId;
    private int height;
    private int width;
    
    public int getWidth() {
        return this.width;
    }
    
    public TextureData(final int textureId, final int width, final int height, final ByteBuffer buffer) {
        this.textureId = textureId;
        this.width = width;
        this.height = height;
        this.buffer = buffer;
    }
    
    public int getTextureId() {
        return this.textureId;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public ByteBuffer getBuffer() {
        return this.buffer;
    }
}
