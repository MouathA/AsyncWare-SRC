package net.minecraft.world.biome;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.entity.passive.*;

public class BiomeGenForest extends BiomeGenBase
{
    protected static final WorldGenForest field_150629_aC;
    protected static final WorldGenCanopyTree field_150631_aE;
    private int field_150632_aF;
    protected static final WorldGenForest field_150630_aD;
    
    @Override
    public int getGrassColorAtPos(final BlockPos blockPos) {
        final int grassColorAtPos = super.getGrassColorAtPos(blockPos);
        return (this.field_150632_aF == 3) ? ((grassColorAtPos & 0xFEFEFE) + 2634762 >> 1) : grassColorAtPos;
    }
    
    @Override
    public void decorate(final World world, final Random random, final BlockPos blockPos) {
        if (this.field_150632_aF != 3) {
            int n = random.nextInt(5) - 3;
            if (this.field_150632_aF == 1) {
                n += 2;
            }
            super.decorate(world, random, blockPos);
            return;
        }
        while (true) {
            final int n2 = 9 + random.nextInt(3);
            final int n3 = 9 + random.nextInt(3);
            final BlockPos height = world.getHeight(blockPos.add(n2, 0, 0));
            if (random.nextInt(20) == 0) {
                new WorldGenBigMushroom().generate(world, random, height);
            }
            else {
                final WorldGenAbstractTree genBigTreeChance = this.genBigTreeChance(random);
                genBigTreeChance.func_175904_e();
                if (genBigTreeChance.generate(world, random, height)) {
                    genBigTreeChance.func_180711_a(world, random, height);
                }
            }
            int n4 = 0;
            ++n4;
        }
    }
    
    @Override
    protected BiomeGenBase func_150557_a(final int color, final boolean b) {
        if (this.field_150632_aF == 2) {
            this.field_150609_ah = 353825;
            this.color = color;
            if (b) {
                this.field_150609_ah = (this.field_150609_ah & 0xFEFEFE) >> 1;
            }
            return this;
        }
        return super.func_150557_a(color, b);
    }
    
    @Override
    public BlockFlower.EnumFlowerType pickRandomFlower(final Random random, final BlockPos blockPos) {
        if (this.field_150632_aF == 1) {
            final BlockFlower.EnumFlowerType enumFlowerType = BlockFlower.EnumFlowerType.values()[(int)(MathHelper.clamp_double((1.0 + BiomeGenForest.GRASS_COLOR_NOISE.func_151601_a(blockPos.getX() / 48.0, blockPos.getZ() / 48.0)) / 2.0, 0.0, 0.9999) * BlockFlower.EnumFlowerType.values().length)];
            return (enumFlowerType == BlockFlower.EnumFlowerType.BLUE_ORCHID) ? BlockFlower.EnumFlowerType.POPPY : enumFlowerType;
        }
        return super.pickRandomFlower(random, blockPos);
    }
    
    public BiomeGenForest(final int n, final int field_150632_aF) {
        super(n);
        this.field_150632_aF = field_150632_aF;
        this.theBiomeDecorator.treesPerChunk = 10;
        this.theBiomeDecorator.grassPerChunk = 2;
        if (this.field_150632_aF == 1) {
            this.theBiomeDecorator.treesPerChunk = 6;
            this.theBiomeDecorator.flowersPerChunk = 100;
            this.theBiomeDecorator.grassPerChunk = 1;
        }
        this.setFillerBlockMetadata(5159473);
        this.setTemperatureRainfall(0.7f, 0.8f);
        if (this.field_150632_aF == 2) {
            this.field_150609_ah = 353825;
            this.color = 3175492;
            this.setTemperatureRainfall(0.6f, 0.6f);
        }
        if (this.field_150632_aF == 0) {
            this.spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 5, 4, 4));
        }
        if (this.field_150632_aF == 3) {
            this.theBiomeDecorator.treesPerChunk = -999;
        }
    }
    
    static {
        field_150629_aC = new WorldGenForest(false, true);
        field_150630_aD = new WorldGenForest(false, false);
        field_150631_aE = new WorldGenCanopyTree(false);
    }
    
    @Override
    public WorldGenAbstractTree genBigTreeChance(final Random random) {
        return (this.field_150632_aF == 3 && random.nextInt(3) > 0) ? BiomeGenForest.field_150631_aE : ((this.field_150632_aF != 2 && random.nextInt(5) != 0) ? this.worldGeneratorTrees : BiomeGenForest.field_150630_aD);
    }
    
    @Override
    protected BiomeGenBase createMutatedBiome(final int n) {
        if (this.biomeID == BiomeGenBase.forest.biomeID) {
            final BiomeGenForest biomeGenForest = new BiomeGenForest(n, 1);
            biomeGenForest.setHeight(new Height(this.minHeight, this.maxHeight + 0.2f));
            biomeGenForest.setBiomeName("Flower Forest");
            biomeGenForest.func_150557_a(6976549, true);
            biomeGenForest.setFillerBlockMetadata(8233509);
            return biomeGenForest;
        }
        return (this.biomeID != BiomeGenBase.birchForest.biomeID && this.biomeID != BiomeGenBase.birchForestHills.biomeID) ? new BiomeGenMutated(this, n, this) {
            final BiomeGenForest this$0;
            
            @Override
            public void decorate(final World world, final Random random, final BlockPos blockPos) {
                this.baseBiome.decorate(world, random, blockPos);
            }
        } : new BiomeGenMutated(this, n, this) {
            final BiomeGenForest this$0;
            
            @Override
            public WorldGenAbstractTree genBigTreeChance(final Random random) {
                return random.nextBoolean() ? BiomeGenForest.field_150629_aC : BiomeGenForest.field_150630_aD;
            }
        };
    }
}
