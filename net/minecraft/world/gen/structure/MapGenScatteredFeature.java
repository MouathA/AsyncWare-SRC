package net.minecraft.world.gen.structure;

import com.google.common.collect.*;
import net.minecraft.entity.monster.*;
import net.minecraft.world.biome.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;

public class MapGenScatteredFeature extends MapGenStructure
{
    private List scatteredFeatureSpawnList;
    private int minDistanceBetweenScatteredFeatures;
    private int maxDistanceBetweenScatteredFeatures;
    private static final List biomelist;
    
    public List getScatteredFeatureSpawnList() {
        return this.scatteredFeatureSpawnList;
    }
    
    public boolean func_175798_a(final BlockPos blockPos) {
        final StructureStart func_175797_c = this.func_175797_c(blockPos);
        return func_175797_c != null && func_175797_c instanceof Start && !func_175797_c.components.isEmpty() && ((StructureComponent)func_175797_c.components.getFirst()) instanceof ComponentScatteredFeaturePieces.SwampHut;
    }
    
    public MapGenScatteredFeature() {
        this.scatteredFeatureSpawnList = Lists.newArrayList();
        this.maxDistanceBetweenScatteredFeatures = 32;
        this.minDistanceBetweenScatteredFeatures = 8;
        this.scatteredFeatureSpawnList.add(new BiomeGenBase.SpawnListEntry(EntityWitch.class, 1, 1, 1));
    }
    
    @Override
    protected boolean canSpawnStructureAtCoords(int n, int n2) {
        final int n3 = n;
        final int n4 = n2;
        if (n < 0) {
            n -= this.maxDistanceBetweenScatteredFeatures - 1;
        }
        if (n2 < 0) {
            n2 -= this.maxDistanceBetweenScatteredFeatures - 1;
        }
        final int n5 = n / this.maxDistanceBetweenScatteredFeatures;
        final int n6 = n2 / this.maxDistanceBetweenScatteredFeatures;
        final Random setRandomSeed = this.worldObj.setRandomSeed(n5, n6, 14357617);
        final int n7 = n5 * this.maxDistanceBetweenScatteredFeatures;
        final int n8 = n6 * this.maxDistanceBetweenScatteredFeatures;
        final int n9 = n7 + setRandomSeed.nextInt(this.maxDistanceBetweenScatteredFeatures - this.minDistanceBetweenScatteredFeatures);
        final int n10 = n8 + setRandomSeed.nextInt(this.maxDistanceBetweenScatteredFeatures - this.minDistanceBetweenScatteredFeatures);
        if (n3 == n9 && n4 == n10) {
            final BiomeGenBase biomeGenerator = this.worldObj.getWorldChunkManager().getBiomeGenerator(new BlockPos(n3 * 16 + 8, 0, n4 * 16 + 8));
            if (biomeGenerator == null) {
                return false;
            }
            final Iterator<BiomeGenBase> iterator = MapGenScatteredFeature.biomelist.iterator();
            while (iterator.hasNext()) {
                if (biomeGenerator == iterator.next()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public MapGenScatteredFeature(final Map map) {
        this();
        for (final Map.Entry<String, V> entry : map.entrySet()) {
            if (entry.getKey().equals("distance")) {
                this.maxDistanceBetweenScatteredFeatures = MathHelper.parseIntWithDefaultAndMax((String)entry.getValue(), this.maxDistanceBetweenScatteredFeatures, this.minDistanceBetweenScatteredFeatures + 1);
            }
        }
    }
    
    static {
        biomelist = Arrays.asList(BiomeGenBase.desert, BiomeGenBase.desertHills, BiomeGenBase.jungle, BiomeGenBase.jungleHills, BiomeGenBase.swampland);
    }
    
    @Override
    public String getStructureName() {
        return "Temple";
    }
    
    @Override
    protected StructureStart getStructureStart(final int n, final int n2) {
        return new Start(this.worldObj, this.rand, n, n2);
    }
    
    public static class Start extends StructureStart
    {
        public Start() {
        }
        
        public Start(final World world, final Random random, final int n, final int n2) {
            super(n, n2);
            final BiomeGenBase biomeGenForCoords = world.getBiomeGenForCoords(new BlockPos(n * 16 + 8, 0, n2 * 16 + 8));
            if (biomeGenForCoords != BiomeGenBase.jungle && biomeGenForCoords != BiomeGenBase.jungleHills) {
                if (biomeGenForCoords == BiomeGenBase.swampland) {
                    this.components.add(new ComponentScatteredFeaturePieces.SwampHut(random, n * 16, n2 * 16));
                }
                else if (biomeGenForCoords == BiomeGenBase.desert || biomeGenForCoords == BiomeGenBase.desertHills) {
                    this.components.add(new ComponentScatteredFeaturePieces.DesertPyramid(random, n * 16, n2 * 16));
                }
            }
            else {
                this.components.add(new ComponentScatteredFeaturePieces.JunglePyramid(random, n * 16, n2 * 16));
            }
            this.updateBoundingBox();
        }
    }
}
