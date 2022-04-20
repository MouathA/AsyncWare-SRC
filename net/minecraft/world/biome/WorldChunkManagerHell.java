package net.minecraft.world.biome;

import net.minecraft.util.*;
import java.util.*;

public class WorldChunkManagerHell extends WorldChunkManager
{
    private float rainfall;
    private BiomeGenBase biomeGenerator;
    
    @Override
    public BiomeGenBase[] getBiomeGenAt(final BiomeGenBase[] array, final int n, final int n2, final int n3, final int n4, final boolean b) {
        return this.loadBlockGeneratorData(array, n, n2, n3, n4);
    }
    
    @Override
    public BiomeGenBase getBiomeGenerator(final BlockPos blockPos) {
        return this.biomeGenerator;
    }
    
    public WorldChunkManagerHell(final BiomeGenBase biomeGenerator, final float rainfall) {
        this.biomeGenerator = biomeGenerator;
        this.rainfall = rainfall;
    }
    
    @Override
    public BlockPos findBiomePosition(final int n, final int n2, final int n3, final List list, final Random random) {
        return list.contains(this.biomeGenerator) ? new BlockPos(n - n3 + random.nextInt(n3 * 2 + 1), 0, n2 - n3 + random.nextInt(n3 * 2 + 1)) : null;
    }
    
    @Override
    public boolean areBiomesViable(final int n, final int n2, final int n3, final List list) {
        return list.contains(this.biomeGenerator);
    }
    
    @Override
    public float[] getRainfall(float[] array, final int n, final int n2, final int n3, final int n4) {
        if (array == null || array.length < n3 * n4) {
            array = new float[n3 * n4];
        }
        Arrays.fill(array, 0, n3 * n4, this.rainfall);
        return array;
    }
    
    @Override
    public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] array, final int n, final int n2, final int n3, final int n4) {
        if (array == null || array.length < n3 * n4) {
            array = new BiomeGenBase[n3 * n4];
        }
        Arrays.fill(array, 0, n3 * n4, this.biomeGenerator);
        return array;
    }
    
    @Override
    public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] array, final int n, final int n2, final int n3, final int n4) {
        if (array == null || array.length < n3 * n4) {
            array = new BiomeGenBase[n3 * n4];
        }
        Arrays.fill(array, 0, n3 * n4, this.biomeGenerator);
        return array;
    }
}
