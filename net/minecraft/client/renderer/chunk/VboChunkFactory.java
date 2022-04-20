package net.minecraft.client.renderer.chunk;

import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public class VboChunkFactory implements IRenderChunkFactory
{
    @Override
    public RenderChunk makeRenderChunk(final World world, final RenderGlobal renderGlobal, final BlockPos blockPos, final int n) {
        return new RenderChunk(world, renderGlobal, blockPos, n);
    }
}
