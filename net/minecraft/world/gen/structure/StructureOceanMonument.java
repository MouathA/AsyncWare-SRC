package net.minecraft.world.gen.structure;

import net.minecraft.world.biome.*;
import net.minecraft.entity.monster.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public class StructureOceanMonument extends MapGenStructure
{
    private static final List field_175803_h;
    public static final List field_175802_d;
    private int field_175801_g;
    private int field_175800_f;
    
    public StructureOceanMonument(final Map map) {
        this();
        for (final Map.Entry<String, V> entry : map.entrySet()) {
            if (entry.getKey().equals("spacing")) {
                this.field_175800_f = MathHelper.parseIntWithDefaultAndMax((String)entry.getValue(), this.field_175800_f, 1);
            }
            else {
                if (!entry.getKey().equals("separation")) {
                    continue;
                }
                this.field_175801_g = MathHelper.parseIntWithDefaultAndMax((String)entry.getValue(), this.field_175801_g, 1);
            }
        }
    }
    
    @Override
    protected StructureStart getStructureStart(final int n, final int n2) {
        return new StartMonument(this.worldObj, this.rand, n, n2);
    }
    
    @Override
    protected boolean canSpawnStructureAtCoords(int n, int n2) {
        final int n3 = n;
        final int n4 = n2;
        if (n < 0) {
            n -= this.field_175800_f - 1;
        }
        if (n2 < 0) {
            n2 -= this.field_175800_f - 1;
        }
        final int n5 = n / this.field_175800_f;
        final int n6 = n2 / this.field_175800_f;
        final Random setRandomSeed = this.worldObj.setRandomSeed(n5, n6, 10387313);
        final int n7 = n5 * this.field_175800_f;
        final int n8 = n6 * this.field_175800_f;
        final int n9 = n7 + (setRandomSeed.nextInt(this.field_175800_f - this.field_175801_g) + setRandomSeed.nextInt(this.field_175800_f - this.field_175801_g)) / 2;
        final int n10 = n8 + (setRandomSeed.nextInt(this.field_175800_f - this.field_175801_g) + setRandomSeed.nextInt(this.field_175800_f - this.field_175801_g)) / 2;
        if (n3 == n9 && n4 == n10) {
            if (this.worldObj.getWorldChunkManager().getBiomeGenerator(new BlockPos(n3 * 16 + 8, 64, n4 * 16 + 8), null) != BiomeGenBase.deepOcean) {
                return false;
            }
            if (this.worldObj.getWorldChunkManager().areBiomesViable(n3 * 16 + 8, n4 * 16 + 8, 29, StructureOceanMonument.field_175802_d)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String getStructureName() {
        return "Monument";
    }
    
    public StructureOceanMonument() {
        this.field_175800_f = 32;
        this.field_175801_g = 5;
    }
    
    public List func_175799_b() {
        return StructureOceanMonument.field_175803_h;
    }
    
    static {
        field_175802_d = Arrays.asList(BiomeGenBase.ocean, BiomeGenBase.deepOcean, BiomeGenBase.river, BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver);
        (field_175803_h = Lists.newArrayList()).add(new BiomeGenBase.SpawnListEntry(EntityGuardian.class, 1, 2, 4));
    }
    
    public static class StartMonument extends StructureStart
    {
        private boolean field_175790_d;
        private Set field_175791_c;
        
        public StartMonument(final World world, final Random random, final int n, final int n2) {
            super(n, n2);
            this.field_175791_c = Sets.newHashSet();
            this.func_175789_b(world, random, n, n2);
        }
        
        public StartMonument() {
            this.field_175791_c = Sets.newHashSet();
        }
        
        @Override
        public void func_175787_b(final ChunkCoordIntPair chunkCoordIntPair) {
            super.func_175787_b(chunkCoordIntPair);
            this.field_175791_c.add(chunkCoordIntPair);
        }
        
        @Override
        public void writeToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeToNBT(nbtTagCompound);
            final NBTTagList list = new NBTTagList();
            for (final ChunkCoordIntPair chunkCoordIntPair : this.field_175791_c) {
                final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                nbtTagCompound2.setInteger("X", chunkCoordIntPair.chunkXPos);
                nbtTagCompound2.setInteger("Z", chunkCoordIntPair.chunkZPos);
                list.appendTag(nbtTagCompound2);
            }
            nbtTagCompound.setTag("Processed", list);
        }
        
        @Override
        public boolean func_175788_a(final ChunkCoordIntPair chunkCoordIntPair) {
            return !this.field_175791_c.contains(chunkCoordIntPair) && super.func_175788_a(chunkCoordIntPair);
        }
        
        @Override
        public void readFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readFromNBT(nbtTagCompound);
            if (nbtTagCompound.hasKey("Processed", 9)) {
                final NBTTagList tagList = nbtTagCompound.getTagList("Processed", 10);
                while (0 < tagList.tagCount()) {
                    final NBTTagCompound compoundTag = tagList.getCompoundTagAt(0);
                    this.field_175791_c.add(new ChunkCoordIntPair(compoundTag.getInteger("X"), compoundTag.getInteger("Z")));
                    int n = 0;
                    ++n;
                }
            }
        }
        
        @Override
        public void generateStructure(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (!this.field_175790_d) {
                this.components.clear();
                this.func_175789_b(world, random, this.getChunkPosX(), this.getChunkPosZ());
            }
            super.generateStructure(world, random, structureBoundingBox);
        }
        
        private void func_175789_b(final World world, final Random random, final int n, final int n2) {
            random.setSeed(world.getSeed());
            random.setSeed(n * random.nextLong() ^ n2 * random.nextLong() ^ world.getSeed());
            this.components.add(new StructureOceanMonumentPieces.MonumentBuilding(random, n * 16 + 8 - 29, n2 * 16 + 8 - 29, EnumFacing.Plane.HORIZONTAL.random(random)));
            this.updateBoundingBox();
            this.field_175790_d = true;
        }
    }
}
