package net.minecraft.world;

import net.minecraft.world.chunk.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.world.biome.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.material.*;

public class ChunkCache implements IBlockAccess
{
    protected Chunk[][] chunkArray;
    protected World worldObj;
    protected boolean hasExtendedLevels;
    protected int chunkZ;
    protected int chunkX;
    
    @Override
    public boolean extendedLevelsInChunkCache() {
        return this.hasExtendedLevels;
    }
    
    @Override
    public WorldType getWorldType() {
        return this.worldObj.getWorldType();
    }
    
    @Override
    public IBlockState getBlockState(final BlockPos blockPos) {
        if (blockPos.getY() >= 0 && blockPos.getY() < 256) {
            final int n = (blockPos.getX() >> 4) - this.chunkX;
            final int n2 = (blockPos.getZ() >> 4) - this.chunkZ;
            if (n >= 0 && n < this.chunkArray.length && n2 >= 0 && n2 < this.chunkArray[n].length) {
                final Chunk chunk = this.chunkArray[n][n2];
                if (chunk != null) {
                    return chunk.getBlockState(blockPos);
                }
            }
        }
        return Blocks.air.getDefaultState();
    }
    
    public ChunkCache(final World worldObj, final BlockPos blockPos, final BlockPos blockPos2, final int n) {
        this.worldObj = worldObj;
        this.chunkX = blockPos.getX() - n >> 4;
        this.chunkZ = blockPos.getZ() - n >> 4;
        final int n2 = blockPos2.getX() + n >> 4;
        final int n3 = blockPos2.getZ() + n >> 4;
        this.chunkArray = new Chunk[n2 - this.chunkX + 1][n3 - this.chunkZ + 1];
        this.hasExtendedLevels = true;
        for (int i = this.chunkX; i <= n2; ++i) {
            for (int j = this.chunkZ; j <= n3; ++j) {
                this.chunkArray[i - this.chunkX][j - this.chunkZ] = worldObj.getChunkFromChunkCoords(i, j);
            }
        }
        for (int k = blockPos.getX() >> 4; k <= blockPos2.getX() >> 4; ++k) {
            for (int l = blockPos.getZ() >> 4; l <= blockPos2.getZ() >> 4; ++l) {
                final Chunk chunk = this.chunkArray[k - this.chunkX][l - this.chunkZ];
                if (chunk != null && !chunk.getAreLevelsEmpty(blockPos.getY(), blockPos2.getY())) {
                    this.hasExtendedLevels = false;
                }
            }
        }
    }
    
    @Override
    public int getCombinedLight(final BlockPos blockPos, final int n) {
        final int lightForExt = this.getLightForExt(EnumSkyBlock.SKY, blockPos);
        int lightForExt2 = this.getLightForExt(EnumSkyBlock.BLOCK, blockPos);
        if (lightForExt2 < n) {
            lightForExt2 = n;
        }
        return lightForExt << 20 | lightForExt2 << 4;
    }
    
    @Override
    public BiomeGenBase getBiomeGenForCoords(final BlockPos blockPos) {
        return this.worldObj.getBiomeGenForCoords(blockPos);
    }
    
    private int getLightForExt(final EnumSkyBlock enumSkyBlock, final BlockPos blockPos) {
        if (enumSkyBlock == EnumSkyBlock.SKY && this.worldObj.provider.getHasNoSky()) {
            return 0;
        }
        if (blockPos.getY() < 0 || blockPos.getY() >= 256) {
            return enumSkyBlock.defaultLightValue;
        }
        if (this.getBlockState(blockPos).getBlock().getUseNeighborBrightness()) {
            final EnumFacing[] values = EnumFacing.values();
            while (0 < values.length) {
                if (this.getLightFor(enumSkyBlock, blockPos.offset(values[0])) > 0) {}
                int n = 0;
                ++n;
            }
            return 0;
        }
        final int n2 = (blockPos.getX() >> 4) - this.chunkX;
        return this.chunkArray[0][(blockPos.getZ() >> 4) - this.chunkZ].getLightFor(enumSkyBlock, blockPos);
    }
    
    @Override
    public TileEntity getTileEntity(final BlockPos blockPos) {
        return this.chunkArray[(blockPos.getX() >> 4) - this.chunkX][(blockPos.getZ() >> 4) - this.chunkZ].getTileEntity(blockPos, Chunk.EnumCreateEntityType.IMMEDIATE);
    }
    
    @Override
    public boolean isAirBlock(final BlockPos blockPos) {
        return this.getBlockState(blockPos).getBlock().getMaterial() == Material.air;
    }
    
    @Override
    public int getStrongPower(final BlockPos blockPos, final EnumFacing enumFacing) {
        final IBlockState blockState = this.getBlockState(blockPos);
        return blockState.getBlock().getStrongPower(this, blockPos, blockState, enumFacing);
    }
    
    public int getLightFor(final EnumSkyBlock enumSkyBlock, final BlockPos blockPos) {
        if (blockPos.getY() >= 0 && blockPos.getY() < 256) {
            return this.chunkArray[(blockPos.getX() >> 4) - this.chunkX][(blockPos.getZ() >> 4) - this.chunkZ].getLightFor(enumSkyBlock, blockPos);
        }
        return enumSkyBlock.defaultLightValue;
    }
}
