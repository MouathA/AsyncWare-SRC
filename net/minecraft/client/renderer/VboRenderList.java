package net.minecraft.client.renderer;

import net.minecraft.util.*;
import net.minecraft.client.renderer.chunk.*;
import java.util.*;
import net.minecraft.client.renderer.vertex.*;
import org.lwjgl.opengl.*;

public class VboRenderList extends ChunkRenderContainer
{
    @Override
    public void renderChunkLayer(final EnumWorldBlockLayer enumWorldBlockLayer) {
        if (this.initialized) {
            for (final RenderChunk renderChunk : this.renderChunks) {
                final VertexBuffer vertexBufferByLayer = renderChunk.getVertexBufferByLayer(enumWorldBlockLayer.ordinal());
                this.preRenderChunk(renderChunk);
                renderChunk.multModelviewMatrix();
                vertexBufferByLayer.bindBuffer();
                this.setupArrayPointers();
                vertexBufferByLayer.drawArrays(7);
            }
            OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, 0);
            this.renderChunks.clear();
        }
    }
    
    private void setupArrayPointers() {
        GL11.glVertexPointer(3, 5126, 28, 0L);
        GL11.glColorPointer(4, 5121, 28, 12L);
        GL11.glTexCoordPointer(2, 5126, 28, 16L);
        OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glTexCoordPointer(2, 5122, 28, 24L);
        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
    }
}
