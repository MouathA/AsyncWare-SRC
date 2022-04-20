package net.minecraft.client.renderer.chunk;

import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public class ListedRenderChunk extends RenderChunk
{
    private final int baseDisplayList;
    
    @Override
    public void deleteGlResources() {
        super.deleteGlResources();
        GLAllocation.deleteDisplayLists(this.baseDisplayList, EnumWorldBlockLayer.values().length);
    }
    
    public int getDisplayList(final EnumWorldBlockLayer enumWorldBlockLayer, final CompiledChunk compiledChunk) {
        return compiledChunk.isLayerEmpty(enumWorldBlockLayer) ? -1 : (this.baseDisplayList + enumWorldBlockLayer.ordinal());
    }
    
    public ListedRenderChunk(final World world, final RenderGlobal renderGlobal, final BlockPos blockPos, final int n) {
        super(world, renderGlobal, blockPos, n);
        this.baseDisplayList = GLAllocation.generateDisplayLists(EnumWorldBlockLayer.values().length);
    }
}
