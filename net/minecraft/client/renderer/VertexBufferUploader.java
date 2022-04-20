package net.minecraft.client.renderer;

import net.minecraft.client.renderer.vertex.*;

public class VertexBufferUploader extends WorldVertexBufferUploader
{
    private VertexBuffer vertexBuffer;
    
    public void setVertexBuffer(final VertexBuffer vertexBuffer) {
        this.vertexBuffer = vertexBuffer;
    }
    
    public VertexBufferUploader() {
        this.vertexBuffer = null;
    }
    
    @Override
    public void func_181679_a(final WorldRenderer worldRenderer) {
        worldRenderer.reset();
        this.vertexBuffer.func_181722_a(worldRenderer.getByteBuffer());
    }
}
