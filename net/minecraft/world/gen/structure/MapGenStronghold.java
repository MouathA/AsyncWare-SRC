package net.minecraft.world.gen.structure;

import com.google.common.collect.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.*;

public class MapGenStronghold extends MapGenStructure
{
    private List field_151546_e;
    private double field_82671_h;
    private int field_82672_i;
    private boolean ranBiomeCheck;
    private ChunkCoordIntPair[] structureCoords;
    
    public MapGenStronghold(final Map map) {
        this();
        for (final Map.Entry<String, V> entry : map.entrySet()) {
            if (entry.getKey().equals("distance")) {
                this.field_82671_h = MathHelper.parseDoubleWithDefaultAndMax((String)entry.getValue(), this.field_82671_h, 1.0);
            }
            else if (entry.getKey().equals("count")) {
                this.structureCoords = new ChunkCoordIntPair[MathHelper.parseIntWithDefaultAndMax((String)entry.getValue(), this.structureCoords.length, 1)];
            }
            else {
                if (!entry.getKey().equals("spread")) {
                    continue;
                }
                this.field_82672_i = MathHelper.parseIntWithDefaultAndMax((String)entry.getValue(), this.field_82672_i, 1);
            }
        }
    }
    
    @Override
    protected List getCoordList() {
        final ArrayList arrayList = Lists.newArrayList();
        final ChunkCoordIntPair[] structureCoords = this.structureCoords;
        while (0 < structureCoords.length) {
            final ChunkCoordIntPair chunkCoordIntPair = structureCoords[0];
            if (chunkCoordIntPair != null) {
                arrayList.add(chunkCoordIntPair.getCenterBlock(64));
            }
            int n = 0;
            ++n;
        }
        return arrayList;
    }
    
    @Override
    protected boolean canSpawnStructureAtCoords(final int n, final int n2) {
        if (!this.ranBiomeCheck) {
            final Random random = new Random();
            random.setSeed(this.worldObj.getSeed());
            double n3 = random.nextDouble() * 3.141592653589793 * 2.0;
            while (0 < this.structureCoords.length) {
                final double n4 = (1.25 * 1 + random.nextDouble()) * this.field_82671_h * 1;
                int n5 = (int)Math.round(Math.cos(n3) * n4);
                int n6 = (int)Math.round(Math.sin(n3) * n4);
                final BlockPos biomePosition = this.worldObj.getWorldChunkManager().findBiomePosition((n5 << 4) + 8, (n6 << 4) + 8, 112, this.field_151546_e, random);
                if (biomePosition != null) {
                    n5 = biomePosition.getX() >> 4;
                    n6 = biomePosition.getZ() >> 4;
                }
                this.structureCoords[0] = new ChunkCoordIntPair(n5, n6);
                n3 += 6.283185307179586 * 1 / this.field_82672_i;
                if (0 == this.field_82672_i) {
                    final int n7 = 1 + (2 + random.nextInt(5));
                    this.field_82672_i += 1 + random.nextInt(2);
                }
                int n8 = 0;
                ++n8;
            }
            this.ranBiomeCheck = true;
        }
        final ChunkCoordIntPair[] structureCoords = this.structureCoords;
        while (0 < structureCoords.length) {
            final ChunkCoordIntPair chunkCoordIntPair = structureCoords[0];
            if (n == chunkCoordIntPair.chunkXPos && n2 == chunkCoordIntPair.chunkZPos) {
                return true;
            }
            int n9 = 0;
            ++n9;
        }
        return false;
    }
    
    @Override
    protected StructureStart getStructureStart(final int n, final int n2) {
        Start start;
        for (start = new Start(this.worldObj, this.rand, n, n2); start.getComponents().isEmpty() || ((StructureStrongholdPieces.Stairs2)start.getComponents().get(0)).strongholdPortalRoom == null; start = new Start(this.worldObj, this.rand, n, n2)) {}
        return start;
    }
    
    @Override
    public String getStructureName() {
        return "Stronghold";
    }
    
    public MapGenStronghold() {
        this.structureCoords = new ChunkCoordIntPair[3];
        this.field_82671_h = 32.0;
        this.field_82672_i = 3;
        this.field_151546_e = Lists.newArrayList();
        final BiomeGenBase[] biomeGenArray = BiomeGenBase.getBiomeGenArray();
        while (0 < biomeGenArray.length) {
            final BiomeGenBase biomeGenBase = biomeGenArray[0];
            if (biomeGenBase != null && biomeGenBase.minHeight > 0.0f) {
                this.field_151546_e.add(biomeGenBase);
            }
            int n = 0;
            ++n;
        }
    }
    
    public static class Start extends StructureStart
    {
        public Start() {
        }
        
        public Start(final World world, final Random random, final int n, final int n2) {
            super(n, n2);
            final StructureStrongholdPieces.Stairs2 stairs2 = new StructureStrongholdPieces.Stairs2(0, random, (n << 4) + 2, (n2 << 4) + 2);
            this.components.add(stairs2);
            stairs2.buildComponent(stairs2, this.components, random);
            final List field_75026_c = stairs2.field_75026_c;
            while (!field_75026_c.isEmpty()) {
                field_75026_c.remove(random.nextInt(field_75026_c.size())).buildComponent(stairs2, this.components, random);
            }
            this.updateBoundingBox();
            this.markAvailableHeight(world, random, 10);
        }
    }
}
