package net.minecraft.world.biome;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.world.gen.feature.*;

public class BiomeGenSnow extends BiomeGenBase
{
    private WorldGenIceSpike field_150616_aD;
    private boolean field_150615_aC;
    private WorldGenIcePath field_150617_aE;
    
    @Override
    public void decorate(final World world, final Random random, final BlockPos blockPos) {
        if (!this.field_150615_aC) {
            super.decorate(world, random, blockPos);
            return;
        }
        while (true) {
            this.field_150616_aD.generate(world, random, world.getHeight(blockPos.add(random.nextInt(16) + 8, 0, random.nextInt(16) + 8)));
            int n = 0;
            ++n;
        }
    }
    
    public BiomeGenSnow(final int n, final boolean field_150615_aC) {
        super(n);
        this.field_150616_aD = new WorldGenIceSpike();
        this.field_150617_aE = new WorldGenIcePath(4);
        this.field_150615_aC = field_150615_aC;
        if (field_150615_aC) {
            this.topBlock = Blocks.snow.getDefaultState();
        }
        this.spawnableCreatureList.clear();
    }
    
    @Override
    public WorldGenAbstractTree genBigTreeChance(final Random random) {
        return new WorldGenTaiga2(false);
    }
    
    @Override
    protected BiomeGenBase createMutatedBiome(final int n) {
        final BiomeGenBase setHeight = new BiomeGenSnow(n, true).func_150557_a(13828095, true).setBiomeName(this.biomeName + " Spikes").setEnableSnow().setTemperatureRainfall(0.0f, 0.5f).setHeight(new Height(this.minHeight + 0.1f, this.maxHeight + 0.1f));
        setHeight.minHeight = this.minHeight + 0.3f;
        setHeight.maxHeight = this.maxHeight + 0.4f;
        return setHeight;
    }
}
