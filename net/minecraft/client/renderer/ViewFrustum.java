package net.minecraft.client.renderer;

import net.minecraft.world.*;
import net.minecraft.client.renderer.chunk.*;
import net.minecraft.util.*;

public class ViewFrustum
{
    protected final World world;
    protected int countChunksX;
    public RenderChunk[] renderChunks;
    protected int countChunksZ;
    protected final RenderGlobal renderGlobal;
    protected int countChunksY;
    
    protected void createRenderChunks(final IRenderChunkFactory renderChunkFactory) {
        this.renderChunks = new RenderChunk[this.countChunksX * this.countChunksY * this.countChunksZ];
        while (0 < this.countChunksX) {
            while (0 < this.countChunksY) {
                while (0 < this.countChunksZ) {
                    final int n = (0 * this.countChunksY + 0) * this.countChunksX + 0;
                    final BlockPos blockPos = new BlockPos(0, 0, 0);
                    final RenderChunk[] renderChunks = this.renderChunks;
                    final int n2 = n;
                    final World world = this.world;
                    final RenderGlobal renderGlobal = this.renderGlobal;
                    final BlockPos blockPos2 = blockPos;
                    final int n3 = 0;
                    int n4 = 0;
                    ++n4;
                    renderChunks[n2] = renderChunkFactory.makeRenderChunk(world, renderGlobal, blockPos2, n3);
                    int n5 = 0;
                    ++n5;
                }
                int n6 = 0;
                ++n6;
            }
            int n7 = 0;
            ++n7;
        }
    }
    
    protected RenderChunk getRenderChunk(final BlockPos blockPos) {
        final int bucketInt = MathHelper.bucketInt(blockPos.getX(), 16);
        final int bucketInt2 = MathHelper.bucketInt(blockPos.getY(), 16);
        final int bucketInt3 = MathHelper.bucketInt(blockPos.getZ(), 16);
        if (bucketInt2 >= 0 && bucketInt2 < this.countChunksY) {
            int n = bucketInt % this.countChunksX;
            if (n < 0) {
                n += this.countChunksX;
            }
            int n2 = bucketInt3 % this.countChunksZ;
            if (n2 < 0) {
                n2 += this.countChunksZ;
            }
            return this.renderChunks[(n2 * this.countChunksY + bucketInt2) * this.countChunksX + n];
        }
        return null;
    }
    
    protected void setCountChunksXYZ(final int n) {
        final int n2 = n * 2 + 1;
        this.countChunksX = n2;
        this.countChunksY = 16;
        this.countChunksZ = n2;
    }
    
    public void deleteGlResources() {
        final RenderChunk[] renderChunks = this.renderChunks;
        while (0 < renderChunks.length) {
            renderChunks[0].deleteGlResources();
            int n = 0;
            ++n;
        }
    }
    
    private int func_178157_a(final int n, final int n2, final int n3) {
        final int n4 = n3 * 16;
        int n5 = n4 - n + n2 / 2;
        if (n5 < 0) {
            n5 -= n2 - 1;
        }
        return n4 - n5 / n2 * n2;
    }
    
    public void markBlocksForUpdate(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final int bucketInt = MathHelper.bucketInt(n, 16);
        final int bucketInt2 = MathHelper.bucketInt(n2, 16);
        final int bucketInt3 = MathHelper.bucketInt(n3, 16);
        final int bucketInt4 = MathHelper.bucketInt(n4, 16);
        final int bucketInt5 = MathHelper.bucketInt(n5, 16);
        final int bucketInt6 = MathHelper.bucketInt(n6, 16);
        for (int i = bucketInt; i <= bucketInt4; ++i) {
            int n7 = i % this.countChunksX;
            if (n7 < 0) {
                n7 += this.countChunksX;
            }
            for (int j = bucketInt2; j <= bucketInt5; ++j) {
                int n8 = j % this.countChunksY;
                if (n8 < 0) {
                    n8 += this.countChunksY;
                }
                for (int k = bucketInt3; k <= bucketInt6; ++k) {
                    int n9 = k % this.countChunksZ;
                    if (n9 < 0) {
                        n9 += this.countChunksZ;
                    }
                    this.renderChunks[(n9 * this.countChunksY + n8) * this.countChunksX + n7].setNeedsUpdate(true);
                }
            }
        }
    }
    
    public void updateChunkPositions(final double n, final double n2) {
        final int n3 = MathHelper.floor_double(n) - 8;
        final int n4 = MathHelper.floor_double(n2) - 8;
        final int n5 = this.countChunksX * 16;
        while (0 < this.countChunksX) {
            final int func_178157_a = this.func_178157_a(n3, n5, 0);
            while (0 < this.countChunksZ) {
                final int func_178157_a2 = this.func_178157_a(n4, n5, 0);
                while (0 < this.countChunksY) {
                    final RenderChunk renderChunk = this.renderChunks[(0 * this.countChunksY + 0) * this.countChunksX + 0];
                    final BlockPos position = new BlockPos(func_178157_a, 0, func_178157_a2);
                    if (!position.equals(renderChunk.getPosition())) {
                        renderChunk.setPosition(position);
                    }
                    int n6 = 0;
                    ++n6;
                }
                int n7 = 0;
                ++n7;
            }
            int n8 = 0;
            ++n8;
        }
    }
    
    public ViewFrustum(final World world, final int countChunksXYZ, final RenderGlobal renderGlobal, final IRenderChunkFactory renderChunkFactory) {
        this.renderGlobal = renderGlobal;
        this.world = world;
        this.setCountChunksXYZ(countChunksXYZ);
        this.createRenderChunks(renderChunkFactory);
    }
}
