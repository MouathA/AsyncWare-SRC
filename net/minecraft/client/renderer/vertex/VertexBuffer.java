package net.minecraft.client.renderer.vertex;

import net.minecraft.client.renderer.*;
import java.nio.*;
import org.lwjgl.opengl.*;

public class VertexBuffer
{
    private int count;
    private final VertexFormat vertexFormat;
    private int glBufferId;
    
    public void bindBuffer() {
        OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, this.glBufferId);
    }
    
    public void func_181722_a(final ByteBuffer byteBuffer) {
        this.bindBuffer();
        OpenGlHelper.glBufferData(OpenGlHelper.GL_ARRAY_BUFFER, byteBuffer, 35044);
        this.unbindBuffer();
        this.count = byteBuffer.limit() / this.vertexFormat.getNextOffset();
    }
    
    public VertexBuffer(final VertexFormat vertexFormat) {
        this.vertexFormat = vertexFormat;
        this.glBufferId = OpenGlHelper.glGenBuffers();
    }
    
    public void unbindBuffer() {
        OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, 0);
    }
    
    public void deleteGlBuffers() {
        if (this.glBufferId >= 0) {
            OpenGlHelper.glDeleteBuffers(this.glBufferId);
            this.glBufferId = -1;
        }
    }
    
    public void drawArrays(final int n) {
        GL11.glDrawArrays(n, 0, this.count);
    }
}
