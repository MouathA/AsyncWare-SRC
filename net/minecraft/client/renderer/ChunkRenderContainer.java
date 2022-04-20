package net.minecraft.client.renderer;

import java.util.*;
import com.google.common.collect.*;
import net.minecraft.client.renderer.chunk.*;
import net.minecraft.util.*;

public abstract class ChunkRenderContainer
{
    protected boolean initialized;
    private double viewEntityY;
    private double viewEntityZ;
    private double viewEntityX;
    protected List renderChunks;
    
    public void initialize(final double viewEntityX, final double viewEntityY, final double viewEntityZ) {
        this.initialized = true;
        this.renderChunks.clear();
        this.viewEntityX = viewEntityX;
        this.viewEntityY = viewEntityY;
        this.viewEntityZ = viewEntityZ;
    }
    
    public ChunkRenderContainer() {
        this.renderChunks = Lists.newArrayListWithCapacity(17424);
    }
    
    public abstract void renderChunkLayer(final EnumWorldBlockLayer p0);
    
    public void preRenderChunk(final RenderChunk renderChunk) {
        final BlockPos position = renderChunk.getPosition();
        GlStateManager.translate((float)(position.getX() - this.viewEntityX), (float)(position.getY() - this.viewEntityY), (float)(position.getZ() - this.viewEntityZ));
    }
    
    public void addRenderChunk(final RenderChunk renderChunk, final EnumWorldBlockLayer enumWorldBlockLayer) {
        this.renderChunks.add(renderChunk);
    }
}
