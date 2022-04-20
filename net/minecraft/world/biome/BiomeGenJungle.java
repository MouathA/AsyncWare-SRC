package net.minecraft.world.biome;

import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.entity.passive.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;

public class BiomeGenJungle extends BiomeGenBase
{
    private static final IBlockState field_181621_aF;
    private static final IBlockState field_181622_aG;
    private boolean field_150614_aC;
    private static final IBlockState field_181620_aE;
    
    @Override
    public WorldGenerator getRandomWorldGenForGrass(final Random random) {
        return (random.nextInt(4) == 0) ? new WorldGenTallGrass(BlockTallGrass.EnumType.FERN) : new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
    }
    
    @Override
    public WorldGenAbstractTree genBigTreeChance(final Random random) {
        return (random.nextInt(10) == 0) ? this.worldGeneratorBigTree : ((random.nextInt(2) == 0) ? new WorldGenShrub(BiomeGenJungle.field_181620_aE, BiomeGenJungle.field_181622_aG) : ((!this.field_150614_aC && random.nextInt(3) == 0) ? new WorldGenMegaJungle(false, 10, 20, BiomeGenJungle.field_181620_aE, BiomeGenJungle.field_181621_aF) : new WorldGenTrees(false, 4 + random.nextInt(7), BiomeGenJungle.field_181620_aE, BiomeGenJungle.field_181621_aF, true)));
    }
    
    public BiomeGenJungle(final int n, final boolean field_150614_aC) {
        super(n);
        this.field_150614_aC = field_150614_aC;
        if (field_150614_aC) {
            this.theBiomeDecorator.treesPerChunk = 2;
        }
        else {
            this.theBiomeDecorator.treesPerChunk = 50;
        }
        this.theBiomeDecorator.grassPerChunk = 25;
        this.theBiomeDecorator.flowersPerChunk = 4;
        if (!field_150614_aC) {
            this.spawnableMonsterList.add(new SpawnListEntry(EntityOcelot.class, 2, 1, 1));
        }
        this.spawnableCreatureList.add(new SpawnListEntry(EntityChicken.class, 10, 4, 4));
    }
    
    @Override
    public void decorate(final World world, final Random random, final BlockPos blockPos) {
        super.decorate(world, random, blockPos);
        final int n = random.nextInt(16) + 8;
        int n2 = random.nextInt(16) + 8;
        new WorldGenMelon().generate(world, random, blockPos.add(n, random.nextInt(world.getHeight(blockPos.add(n, 0, 0)).getY() * 2), 0));
        final WorldGenVines worldGenVines = new WorldGenVines();
        while (true) {
            worldGenVines.generate(world, random, blockPos.add(random.nextInt(16) + 8, 128, random.nextInt(16) + 8));
            ++n2;
        }
    }
    
    static {
        field_181620_aE = Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.JUNGLE);
        field_181621_aF = Blocks.leaves.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.JUNGLE).withProperty(BlockLeaves.CHECK_DECAY, false);
        field_181622_aG = Blocks.leaves.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.OAK).withProperty(BlockLeaves.CHECK_DECAY, false);
    }
}
