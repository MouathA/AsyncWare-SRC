package net.minecraft.world.gen.structure;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.nbt.*;

public abstract class StructureStart
{
    private int chunkPosX;
    private int chunkPosZ;
    protected LinkedList components;
    protected StructureBoundingBox boundingBox;
    
    public StructureBoundingBox getBoundingBox() {
        return this.boundingBox;
    }
    
    public void readStructureComponentsFromNBT(final World world, final NBTTagCompound nbtTagCompound) {
        this.chunkPosX = nbtTagCompound.getInteger("ChunkX");
        this.chunkPosZ = nbtTagCompound.getInteger("ChunkZ");
        if (nbtTagCompound.hasKey("BB")) {
            this.boundingBox = new StructureBoundingBox(nbtTagCompound.getIntArray("BB"));
        }
        final NBTTagList tagList = nbtTagCompound.getTagList("Children", 10);
        while (0 < tagList.tagCount()) {
            this.components.add(MapGenStructureIO.getStructureComponent(tagList.getCompoundTagAt(0), world));
            int n = 0;
            ++n;
        }
        this.readFromNBT(nbtTagCompound);
    }
    
    public int getChunkPosZ() {
        return this.chunkPosZ;
    }
    
    protected void updateBoundingBox() {
        this.boundingBox = StructureBoundingBox.getNewBoundingBox();
        final Iterator iterator = this.components.iterator();
        while (iterator.hasNext()) {
            this.boundingBox.expandTo(iterator.next().getBoundingBox());
        }
    }
    
    public LinkedList getComponents() {
        return this.components;
    }
    
    public boolean func_175788_a(final ChunkCoordIntPair chunkCoordIntPair) {
        return true;
    }
    
    public int getChunkPosX() {
        return this.chunkPosX;
    }
    
    protected void setRandomHeight(final World world, final Random random, final int n, final int n2) {
        final int n3 = n2 - n + 1 - this.boundingBox.getYSize();
        if (n3 > 1) {
            final int n4 = n + random.nextInt(n3);
        }
        final int n5 = 1 - this.boundingBox.minY;
        this.boundingBox.offset(0, n5, 0);
        final Iterator iterator = this.components.iterator();
        while (iterator.hasNext()) {
            iterator.next().func_181138_a(0, n5, 0);
        }
    }
    
    public NBTTagCompound writeStructureComponentsToNBT(final int n, final int n2) {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setString("id", MapGenStructureIO.getStructureStartName(this));
        nbtTagCompound.setInteger("ChunkX", n);
        nbtTagCompound.setInteger("ChunkZ", n2);
        nbtTagCompound.setTag("BB", this.boundingBox.toNBTTagIntArray());
        final NBTTagList list = new NBTTagList();
        final Iterator iterator = this.components.iterator();
        while (iterator.hasNext()) {
            list.appendTag(iterator.next().createStructureBaseNBT());
        }
        nbtTagCompound.setTag("Children", list);
        this.writeToNBT(nbtTagCompound);
        return nbtTagCompound;
    }
    
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    protected void markAvailableHeight(final World world, final Random random, final int n) {
        final int n2 = world.func_181545_F() - n;
        int n3 = this.boundingBox.getYSize() + 1;
        if (n3 < n2) {
            n3 += random.nextInt(n2 - n3);
        }
        final int n4 = n3 - this.boundingBox.maxY;
        this.boundingBox.offset(0, n4, 0);
        final Iterator iterator = this.components.iterator();
        while (iterator.hasNext()) {
            iterator.next().func_181138_a(0, n4, 0);
        }
    }
    
    public void func_175787_b(final ChunkCoordIntPair chunkCoordIntPair) {
    }
    
    public StructureStart() {
        this.components = new LinkedList();
    }
    
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    public void generateStructure(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        final Iterator iterator = this.components.iterator();
        while (iterator.hasNext()) {
            final StructureComponent structureComponent = iterator.next();
            if (structureComponent.getBoundingBox().intersectsWith(structureBoundingBox) && !structureComponent.addComponentParts(world, random, structureBoundingBox)) {
                iterator.remove();
            }
        }
    }
    
    public StructureStart(final int chunkPosX, final int chunkPosZ) {
        this.components = new LinkedList();
        this.chunkPosX = chunkPosX;
        this.chunkPosZ = chunkPosZ;
    }
    
    public boolean isSizeableStructure() {
        return true;
    }
}
