package net.minecraft.world.biome;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.init.*;

public class BiomeGenDesert extends BiomeGenBase
{
    @Override
    public void decorate(final World world, final Random random, final BlockPos blockPos) {
        super.decorate(world, random, blockPos);
        if (random.nextInt(1000) == 0) {
            new WorldGenDesertWells().generate(world, random, world.getHeight(blockPos.add(random.nextInt(16) + 8, 0, random.nextInt(16) + 8)).up());
        }
    }
    
    public BiomeGenDesert(final int n) {
        super(n);
        this.spawnableCreatureList.clear();
        this.topBlock = Blocks.sand.getDefaultState();
        this.fillerBlock = Blocks.sand.getDefaultState();
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.deadBushPerChunk = 2;
        this.theBiomeDecorator.reedsPerChunk = 50;
        this.theBiomeDecorator.cactiPerChunk = 10;
        this.spawnableCreatureList.clear();
    }
}
