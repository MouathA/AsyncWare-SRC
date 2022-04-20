package net.minecraft.client.renderer;

import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import java.util.*;

public class RegionRenderCache extends ChunkCache
{
    private final BlockPos position;
    private IBlockState[] blockStates;
    private static final IBlockState DEFAULT_STATE;
    private int[] combinedLights;
    
    static {
        DEFAULT_STATE = Blocks.air.getDefaultState();
    }
    
    private int getPositionIndex(final BlockPos blockPos) {
        return (blockPos.getX() - this.position.getX()) * 400 + (blockPos.getZ() - this.position.getZ()) * 20 + (blockPos.getY() - this.position.getY());
    }
    
    private IBlockState getBlockStateRaw(final BlockPos blockPos) {
        if (blockPos.getY() >= 0 && blockPos.getY() < 256) {
            return this.chunkArray[(blockPos.getX() >> 4) - this.chunkX][(blockPos.getZ() >> 4) - this.chunkZ].getBlockState(blockPos);
        }
        return RegionRenderCache.DEFAULT_STATE;
    }
    
    @Override
    public TileEntity getTileEntity(final BlockPos blockPos) {
        return this.chunkArray[(blockPos.getX() >> 4) - this.chunkX][(blockPos.getZ() >> 4) - this.chunkZ].getTileEntity(blockPos, Chunk.EnumCreateEntityType.QUEUED);
    }
    
    @Override
    public IBlockState getBlockState(final BlockPos blockPos) {
        final int positionIndex = this.getPositionIndex(blockPos);
        IBlockState blockStateRaw = this.blockStates[positionIndex];
        if (blockStateRaw == null) {
            blockStateRaw = this.getBlockStateRaw(blockPos);
            this.blockStates[positionIndex] = blockStateRaw;
        }
        return blockStateRaw;
    }
    
    public RegionRenderCache(final World world, final BlockPos blockPos, final BlockPos blockPos2, final int n) {
        super(world, blockPos, blockPos2, n);
        this.position = blockPos.subtract(new Vec3i(n, n, n));
        this.combinedLights = new int[8000];
        Arrays.fill(this.combinedLights, -1);
        this.blockStates = new IBlockState[8000];
    }
    
    @Override
    public int getCombinedLight(final BlockPos blockPos, final int n) {
        final int positionIndex = this.getPositionIndex(blockPos);
        int combinedLight = this.combinedLights[positionIndex];
        if (combinedLight == -1) {
            combinedLight = super.getCombinedLight(blockPos, n);
            this.combinedLights[positionIndex] = combinedLight;
        }
        return combinedLight;
    }
}
