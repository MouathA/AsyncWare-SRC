package net.minecraft.world.biome;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.world.chunk.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.gen.feature.*;

public class BiomeGenHills extends BiomeGenBase
{
    private WorldGenerator theWorldGenerator;
    private int field_150636_aF;
    private int field_150638_aH;
    private int field_150635_aE;
    private int field_150637_aG;
    private WorldGenTaiga2 field_150634_aD;
    
    @Override
    public void genTerrainBlocks(final World world, final Random random, final ChunkPrimer chunkPrimer, final int n, final int n2, final double n3) {
        this.topBlock = Blocks.grass.getDefaultState();
        this.fillerBlock = Blocks.dirt.getDefaultState();
        if ((n3 < -1.0 || n3 > 2.0) && this.field_150638_aH == this.field_150637_aG) {
            this.topBlock = Blocks.gravel.getDefaultState();
            this.fillerBlock = Blocks.gravel.getDefaultState();
        }
        else if (n3 > 1.0 && this.field_150638_aH != this.field_150636_aF) {
            this.topBlock = Blocks.stone.getDefaultState();
            this.fillerBlock = Blocks.stone.getDefaultState();
        }
        this.generateBiomeTerrain(world, random, chunkPrimer, n, n2, n3);
    }
    
    @Override
    public void decorate(final World world, final Random random, final BlockPos blockPos) {
        super.decorate(world, random, blockPos);
        int n = 3 + random.nextInt(6);
        while (true) {
            random.nextInt(16);
            this.theWorldGenerator.generate(world, random, blockPos.add(0, random.nextInt(64), random.nextInt(16)));
            ++n;
        }
    }
    
    private BiomeGenHills mutateHills(final BiomeGenBase biomeGenBase) {
        this.field_150638_aH = this.field_150637_aG;
        this.func_150557_a(biomeGenBase.color, true);
        this.setBiomeName(biomeGenBase.biomeName + " M");
        this.setHeight(new Height(biomeGenBase.minHeight, biomeGenBase.maxHeight));
        this.setTemperatureRainfall(biomeGenBase.temperature, biomeGenBase.rainfall);
        return this;
    }
    
    @Override
    public WorldGenAbstractTree genBigTreeChance(final Random random) {
        return (random.nextInt(3) > 0) ? this.field_150634_aD : super.genBigTreeChance(random);
    }
    
    @Override
    protected BiomeGenBase createMutatedBiome(final int n) {
        return new BiomeGenHills(n, false).mutateHills(this);
    }
    
    protected BiomeGenHills(final int n, final boolean b) {
        super(n);
        this.theWorldGenerator = new WorldGenMinable(Blocks.monster_egg.getDefaultState().withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.STONE), 9);
        this.field_150634_aD = new WorldGenTaiga2(false);
        this.field_150635_aE = 0;
        this.field_150636_aF = 1;
        this.field_150637_aG = 2;
        this.field_150638_aH = this.field_150635_aE;
        if (b) {
            this.theBiomeDecorator.treesPerChunk = 3;
            this.field_150638_aH = this.field_150636_aF;
        }
    }
}
