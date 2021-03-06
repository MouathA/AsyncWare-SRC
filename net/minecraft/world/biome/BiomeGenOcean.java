package net.minecraft.world.biome;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.world.chunk.*;

public class BiomeGenOcean extends BiomeGenBase
{
    @Override
    public TempCategory getTempCategory() {
        return TempCategory.OCEAN;
    }
    
    @Override
    public void genTerrainBlocks(final World world, final Random random, final ChunkPrimer chunkPrimer, final int n, final int n2, final double n3) {
        super.genTerrainBlocks(world, random, chunkPrimer, n, n2, n3);
    }
    
    public BiomeGenOcean(final int n) {
        super(n);
        this.spawnableCreatureList.clear();
    }
}
