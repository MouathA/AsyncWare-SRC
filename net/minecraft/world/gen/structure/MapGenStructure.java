package net.minecraft.world.gen.structure;

import net.minecraft.world.gen.*;
import net.minecraft.world.chunk.*;
import net.minecraft.util.*;
import optfine.*;
import net.minecraft.world.storage.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import java.util.*;
import com.google.common.collect.*;

public abstract class MapGenStructure extends MapGenBase
{
    private MapGenStructureData structureData;
    protected Map structureMap;
    private LongHashMap structureLongMap;
    private static final String __OBFID = "CL_00000505";
    
    public BlockPos getClosestStrongholdPos(final World worldObj, final BlockPos blockPos) {
        this.func_143027_a(this.worldObj = worldObj);
        this.rand.setSeed(worldObj.getSeed());
        this.rand.setSeed((blockPos.getX() >> 4) * this.rand.nextLong() ^ (blockPos.getZ() >> 4) * this.rand.nextLong() ^ worldObj.getSeed());
        this.recursiveGenerate(worldObj, blockPos.getX() >> 4, blockPos.getZ() >> 4, 0, 0, null);
        double n = Double.MAX_VALUE;
        BlockPos blockPos2 = null;
        for (final StructureStart structureStart : this.structureMap.values()) {
            if (structureStart.isSizeableStructure()) {
                final BlockPos boundingBoxCenter = structureStart.getComponents().get(0).getBoundingBoxCenter();
                final double distanceSq = boundingBoxCenter.distanceSq(blockPos);
                if (distanceSq >= n) {
                    continue;
                }
                n = distanceSq;
                blockPos2 = boundingBoxCenter;
            }
        }
        if (blockPos2 != null) {
            return blockPos2;
        }
        final List coordList = this.getCoordList();
        if (coordList != null) {
            BlockPos blockPos3 = null;
            for (final BlockPos next : coordList) {
                final double distanceSq2 = next.distanceSq(blockPos);
                if (distanceSq2 < n) {
                    n = distanceSq2;
                    blockPos3 = next;
                }
            }
            return blockPos3;
        }
        return null;
    }
    
    @Override
    protected final void recursiveGenerate(final World world, final int n, final int n2, final int n3, final int n4, final ChunkPrimer chunkPrimer) {
        this.func_143027_a(world);
        if (!this.structureLongMap.containsItem(ChunkCoordIntPair.chunkXZ2Int(n, n2))) {
            this.rand.nextInt();
            if (this.canSpawnStructureAtCoords(n, n2)) {
                final StructureStart structureStart = this.getStructureStart(n, n2);
                this.structureMap.put(ChunkCoordIntPair.chunkXZ2Int(n, n2), structureStart);
                this.structureLongMap.add(ChunkCoordIntPair.chunkXZ2Int(n, n2), structureStart);
                this.func_143026_a(n, n2, structureStart);
            }
        }
    }
    
    public boolean func_175795_b(final BlockPos blockPos) {
        this.func_143027_a(this.worldObj);
        return this.func_175797_c(blockPos) != null;
    }
    
    protected abstract StructureStart getStructureStart(final int p0, final int p1);
    
    private void func_143026_a(final int n, final int n2, final StructureStart structureStart) {
        this.structureData.writeInstance(structureStart.writeStructureComponentsToNBT(n, n2), n, n2);
        this.structureData.markDirty();
    }
    
    protected abstract boolean canSpawnStructureAtCoords(final int p0, final int p1);
    
    public abstract String getStructureName();
    
    private void func_143027_a(final World world) {
        if (this.structureData == null) {
            if (Reflector.ForgeWorld_getPerWorldStorage.exists()) {
                this.structureData = (MapGenStructureData)((MapStorage)Reflector.call(world, Reflector.ForgeWorld_getPerWorldStorage, new Object[0])).loadData(MapGenStructureData.class, this.getStructureName());
            }
            else {
                this.structureData = (MapGenStructureData)world.loadItemData(MapGenStructureData.class, this.getStructureName());
            }
            if (this.structureData == null) {
                this.structureData = new MapGenStructureData(this.getStructureName());
                if (Reflector.ForgeWorld_getPerWorldStorage.exists()) {
                    ((MapStorage)Reflector.call(world, Reflector.ForgeWorld_getPerWorldStorage, new Object[0])).setData(this.getStructureName(), this.structureData);
                }
                else {
                    world.setItemData(this.getStructureName(), this.structureData);
                }
            }
            else {
                final NBTTagCompound tagCompound = this.structureData.getTagCompound();
                final Iterator iterator = tagCompound.getKeySet().iterator();
                while (iterator.hasNext()) {
                    final NBTBase tag = tagCompound.getTag(iterator.next());
                    if (tag.getId() == 10) {
                        final NBTTagCompound nbtTagCompound = (NBTTagCompound)tag;
                        if (!nbtTagCompound.hasKey("ChunkX") || !nbtTagCompound.hasKey("ChunkZ")) {
                            continue;
                        }
                        final int integer = nbtTagCompound.getInteger("ChunkX");
                        final int integer2 = nbtTagCompound.getInteger("ChunkZ");
                        final StructureStart structureStart = MapGenStructureIO.getStructureStart(nbtTagCompound, world);
                        if (structureStart == null) {
                            continue;
                        }
                        this.structureMap.put(ChunkCoordIntPair.chunkXZ2Int(integer, integer2), structureStart);
                        this.structureLongMap.add(ChunkCoordIntPair.chunkXZ2Int(integer, integer2), structureStart);
                    }
                }
            }
        }
    }
    
    public boolean generateStructure(final World world, final Random random, final ChunkCoordIntPair chunkCoordIntPair) {
        this.func_143027_a(world);
        final int n = (chunkCoordIntPair.chunkXPos << 4) + 8;
        final int n2 = (chunkCoordIntPair.chunkZPos << 4) + 8;
        for (final StructureStart structureStart : this.structureMap.values()) {
            if (structureStart.isSizeableStructure() && structureStart.func_175788_a(chunkCoordIntPair) && structureStart.getBoundingBox().intersectsWith(n, n2, n + 15, n2 + 15)) {
                structureStart.generateStructure(world, random, new StructureBoundingBox(n, n2, n + 15, n2 + 15));
                structureStart.func_175787_b(chunkCoordIntPair);
                this.func_143026_a(structureStart.getChunkPosX(), structureStart.getChunkPosZ(), structureStart);
            }
        }
        return true;
    }
    
    protected List getCoordList() {
        return null;
    }
    
    public boolean func_175796_a(final World world, final BlockPos blockPos) {
        this.func_143027_a(world);
        for (final StructureStart structureStart : this.structureMap.values()) {
            if (structureStart.isSizeableStructure() && structureStart.getBoundingBox().isVecInside(blockPos)) {
                return true;
            }
        }
        return false;
    }
    
    public MapGenStructure() {
        this.structureMap = Maps.newHashMap();
        this.structureLongMap = new LongHashMap();
    }
    
    protected StructureStart func_175797_c(final BlockPos blockPos) {
        for (final StructureStart structureStart : this.structureMap.values()) {
            if (structureStart.isSizeableStructure() && structureStart.getBoundingBox().isVecInside(blockPos)) {
                final Iterator iterator2 = structureStart.getComponents().iterator();
                while (iterator2.hasNext()) {
                    if (iterator2.next().getBoundingBox().isVecInside(blockPos)) {
                        return structureStart;
                    }
                }
            }
        }
        return null;
    }
}
