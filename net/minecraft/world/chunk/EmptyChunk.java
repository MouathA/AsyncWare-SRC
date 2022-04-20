package net.minecraft.world.chunk;

import net.minecraft.tileentity.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import java.util.*;
import com.google.common.base.*;

public class EmptyChunk extends Chunk
{
    @Override
    public void addTileEntity(final BlockPos blockPos, final TileEntity tileEntity) {
    }
    
    @Override
    public void removeTileEntity(final BlockPos blockPos) {
    }
    
    public void generateHeightMap() {
    }
    
    @Override
    public int getLightSubtracted(final BlockPos blockPos, final int n) {
        return 0;
    }
    
    @Override
    public TileEntity getTileEntity(final BlockPos blockPos, final EnumCreateEntityType enumCreateEntityType) {
        return null;
    }
    
    public boolean canSeeSky(final BlockPos blockPos) {
        return false;
    }
    
    @Override
    public boolean isEmpty() {
        return true;
    }
    
    public EmptyChunk(final World world, final int n, final int n2) {
        super(world, n, n2);
    }
    
    @Override
    public void addEntity(final Entity entity) {
    }
    
    @Override
    public Block getBlock(final BlockPos blockPos) {
        return Blocks.air;
    }
    
    @Override
    public void onChunkLoad() {
    }
    
    @Override
    public int getBlockLightOpacity(final BlockPos blockPos) {
        return 255;
    }
    
    @Override
    public void addTileEntity(final TileEntity tileEntity) {
    }
    
    @Override
    public boolean needsSaving(final boolean b) {
        return false;
    }
    
    @Override
    public boolean getAreLevelsEmpty(final int n, final int n2) {
        return true;
    }
    
    @Override
    public int getHeightValue(final int n, final int n2) {
        return 0;
    }
    
    @Override
    public void setChunkModified() {
    }
    
    @Override
    public void removeEntity(final Entity entity) {
    }
    
    @Override
    public int getLightFor(final EnumSkyBlock enumSkyBlock, final BlockPos blockPos) {
        return enumSkyBlock.defaultLightValue;
    }
    
    @Override
    public void setLightFor(final EnumSkyBlock enumSkyBlock, final BlockPos blockPos, final int n) {
    }
    
    @Override
    public void onChunkUnload() {
    }
    
    @Override
    public boolean isAtLocation(final int n, final int n2) {
        return n == this.xPosition && n2 == this.zPosition;
    }
    
    @Override
    public Random getRandomWithSeed(final long n) {
        return new Random(this.getWorld().getSeed() + this.xPosition * this.xPosition * 4987142 + this.xPosition * 5947611 + this.zPosition * this.zPosition * 4392871L + this.zPosition * 389711 ^ n);
    }
    
    @Override
    public void removeEntityAtIndex(final Entity entity, final int n) {
    }
    
    @Override
    public void getEntitiesOfTypeWithinAAAB(final Class clazz, final AxisAlignedBB axisAlignedBB, final List list, final Predicate predicate) {
    }
    
    @Override
    public int getBlockMetadata(final BlockPos blockPos) {
        return 0;
    }
    
    @Override
    public void generateSkylightMap() {
    }
    
    @Override
    public void getEntitiesWithinAABBForEntity(final Entity entity, final AxisAlignedBB axisAlignedBB, final List list, final Predicate predicate) {
    }
}
