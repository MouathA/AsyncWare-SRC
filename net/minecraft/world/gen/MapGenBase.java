package net.minecraft.world.gen;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.world.chunk.*;

public class MapGenBase
{
    protected World worldObj;
    protected int range;
    protected Random rand;
    
    protected void recursiveGenerate(final World world, final int n, final int n2, final int n3, final int n4, final ChunkPrimer chunkPrimer) {
    }
    
    public void generate(final IChunkProvider chunkProvider, final World worldObj, final int n, final int n2, final ChunkPrimer chunkPrimer) {
        final int range = this.range;
        this.worldObj = worldObj;
        this.rand.setSeed(worldObj.getSeed());
        final long nextLong = this.rand.nextLong();
        final long nextLong2 = this.rand.nextLong();
        for (int i = n - range; i <= n + range; ++i) {
            for (int j = n2 - range; j <= n2 + range; ++j) {
                this.rand.setSeed(i * nextLong ^ j * nextLong2 ^ worldObj.getSeed());
                this.recursiveGenerate(worldObj, i, j, n, n2, chunkPrimer);
            }
        }
    }
    
    public MapGenBase() {
        this.range = 8;
        this.rand = new Random();
    }
}
