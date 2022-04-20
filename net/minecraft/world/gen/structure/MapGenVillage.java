package net.minecraft.world.gen.structure;

import net.minecraft.world.biome.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;

public class MapGenVillage extends MapGenStructure
{
    private int terrainType;
    private int field_82665_g;
    private int field_82666_h;
    public static final List villageSpawnBiomes;
    
    static {
        villageSpawnBiomes = Arrays.asList(BiomeGenBase.plains, BiomeGenBase.desert, BiomeGenBase.savanna);
    }
    
    public MapGenVillage(final Map map) {
        this();
        for (final Map.Entry<String, V> entry : map.entrySet()) {
            if (entry.getKey().equals("size")) {
                this.terrainType = MathHelper.parseIntWithDefaultAndMax((String)entry.getValue(), this.terrainType, 0);
            }
            else {
                if (!entry.getKey().equals("distance")) {
                    continue;
                }
                this.field_82665_g = MathHelper.parseIntWithDefaultAndMax((String)entry.getValue(), this.field_82665_g, this.field_82666_h + 1);
            }
        }
    }
    
    public MapGenVillage() {
        this.field_82665_g = 32;
        this.field_82666_h = 8;
    }
    
    @Override
    public String getStructureName() {
        return "Village";
    }
    
    @Override
    protected boolean canSpawnStructureAtCoords(int n, int n2) {
        final int n3 = n;
        final int n4 = n2;
        if (n < 0) {
            n -= this.field_82665_g - 1;
        }
        if (n2 < 0) {
            n2 -= this.field_82665_g - 1;
        }
        final int n5 = n / this.field_82665_g;
        final int n6 = n2 / this.field_82665_g;
        final Random setRandomSeed = this.worldObj.setRandomSeed(n5, n6, 10387312);
        final int n7 = n5 * this.field_82665_g;
        final int n8 = n6 * this.field_82665_g;
        final int n9 = n7 + setRandomSeed.nextInt(this.field_82665_g - this.field_82666_h);
        final int n10 = n8 + setRandomSeed.nextInt(this.field_82665_g - this.field_82666_h);
        return n3 == n9 && n4 == n10 && this.worldObj.getWorldChunkManager().areBiomesViable(n3 * 16 + 8, n4 * 16 + 8, 0, MapGenVillage.villageSpawnBiomes);
    }
    
    @Override
    protected StructureStart getStructureStart(final int n, final int n2) {
        return new Start(this.worldObj, this.rand, n, n2, this.terrainType);
    }
    
    public static class Start extends StructureStart
    {
        private boolean hasMoreThanTwoComponents;
        
        public Start(final World world, final Random random, final int n, final int n2, final int n3) {
            super(n, n2);
            final StructureVillagePieces.Start start = new StructureVillagePieces.Start(world.getWorldChunkManager(), 0, random, (n << 4) + 2, (n2 << 4) + 2, StructureVillagePieces.getStructureVillageWeightedPieceList(random, n3), n3);
            this.components.add(start);
            start.buildComponent(start, this.components, random);
            final List field_74930_j = start.field_74930_j;
            final List field_74932_i = start.field_74932_i;
            int n4 = 0;
            while (!field_74930_j.isEmpty() || !field_74932_i.isEmpty()) {
                if (field_74930_j.isEmpty()) {
                    n4 = random.nextInt(field_74932_i.size());
                    field_74932_i.remove(0).buildComponent(start, this.components, random);
                }
                else {
                    n4 = random.nextInt(field_74930_j.size());
                    field_74930_j.remove(0).buildComponent(start, this.components, random);
                }
            }
            this.updateBoundingBox();
            final Iterator iterator = this.components.iterator();
            while (iterator.hasNext()) {
                if (!(iterator.next() instanceof StructureVillagePieces.Road)) {
                    ++n4;
                }
            }
            this.hasMoreThanTwoComponents = false;
        }
        
        @Override
        public void readFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readFromNBT(nbtTagCompound);
            this.hasMoreThanTwoComponents = nbtTagCompound.getBoolean("Valid");
        }
        
        public Start() {
        }
        
        @Override
        public void writeToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeToNBT(nbtTagCompound);
            nbtTagCompound.setBoolean("Valid", this.hasMoreThanTwoComponents);
        }
        
        @Override
        public boolean isSizeableStructure() {
            return this.hasMoreThanTwoComponents;
        }
    }
}
