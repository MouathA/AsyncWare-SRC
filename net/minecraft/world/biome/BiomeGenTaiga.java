package net.minecraft.world.biome;

import net.minecraft.entity.passive.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.world.chunk.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.world.gen.feature.*;

public class BiomeGenTaiga extends BiomeGenBase
{
    private static final WorldGenBlockBlob field_150643_aG;
    private int field_150644_aH;
    private static final WorldGenTaiga1 field_150639_aC;
    private static final WorldGenTaiga2 field_150640_aD;
    private static final WorldGenMegaPineTree field_150642_aF;
    private static final WorldGenMegaPineTree field_150641_aE;
    
    public BiomeGenTaiga(final int n, final int field_150644_aH) {
        super(n);
        this.field_150644_aH = field_150644_aH;
        this.spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 8, 4, 4));
        this.theBiomeDecorator.treesPerChunk = 10;
        if (field_150644_aH != 1 && field_150644_aH != 2) {
            this.theBiomeDecorator.grassPerChunk = 1;
            this.theBiomeDecorator.mushroomsPerChunk = 1;
        }
        else {
            this.theBiomeDecorator.grassPerChunk = 7;
            this.theBiomeDecorator.deadBushPerChunk = 1;
            this.theBiomeDecorator.mushroomsPerChunk = 3;
        }
    }
    
    @Override
    public void genTerrainBlocks(final World world, final Random random, final ChunkPrimer chunkPrimer, final int n, final int n2, final double n3) {
        if (this.field_150644_aH == 1 || this.field_150644_aH == 2) {
            this.topBlock = Blocks.grass.getDefaultState();
            this.fillerBlock = Blocks.dirt.getDefaultState();
            if (n3 > 1.75) {
                this.topBlock = Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT);
            }
            else if (n3 > -0.95) {
                this.topBlock = Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL);
            }
        }
        this.generateBiomeTerrain(world, random, chunkPrimer, n, n2, n3);
    }
    
    @Override
    public WorldGenerator getRandomWorldGenForGrass(final Random random) {
        return (random.nextInt(5) > 0) ? new WorldGenTallGrass(BlockTallGrass.EnumType.FERN) : new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
    }
    
    @Override
    public void decorate(final World world, final Random random, final BlockPos blockPos) {
        int nextInt = 0;
        if (this.field_150644_aH == 1 || this.field_150644_aH == 2) {
            nextInt = random.nextInt(3);
        }
        BiomeGenTaiga.DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.FERN);
        while (true) {
            final int n = random.nextInt(16) + 8;
            final int n2 = random.nextInt(16) + 8;
            BiomeGenTaiga.DOUBLE_PLANT_GENERATOR.generate(world, random, blockPos.add(0, random.nextInt(world.getHeight(blockPos.add(0, 0, n2)).getY() + 32), n2));
            ++nextInt;
        }
    }
    
    static {
        field_150639_aC = new WorldGenTaiga1();
        field_150640_aD = new WorldGenTaiga2(false);
        field_150641_aE = new WorldGenMegaPineTree(false, false);
        field_150642_aF = new WorldGenMegaPineTree(false, true);
        field_150643_aG = new WorldGenBlockBlob(Blocks.mossy_cobblestone, 0);
    }
    
    @Override
    protected BiomeGenBase createMutatedBiome(final int n) {
        return (this.biomeID == BiomeGenBase.megaTaiga.biomeID) ? new BiomeGenTaiga(n, 2).func_150557_a(5858897, true).setBiomeName("Mega Spruce Taiga").setFillerBlockMetadata(5159473).setTemperatureRainfall(0.25f, 0.8f).setHeight(new Height(this.minHeight, this.maxHeight)) : super.createMutatedBiome(n);
    }
    
    @Override
    public WorldGenAbstractTree genBigTreeChance(final Random random) {
        return ((this.field_150644_aH == 1 || this.field_150644_aH == 2) && random.nextInt(3) == 0) ? ((this.field_150644_aH != 2 && random.nextInt(13) != 0) ? BiomeGenTaiga.field_150641_aE : BiomeGenTaiga.field_150642_aF) : ((random.nextInt(3) == 0) ? BiomeGenTaiga.field_150639_aC : BiomeGenTaiga.field_150640_aD);
    }
}
