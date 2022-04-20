package net.minecraft.world.biome;

import net.minecraft.init.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.*;

public class BiomeEndDecorator extends BiomeDecorator
{
    protected WorldGenerator spikeGen;
    
    public BiomeEndDecorator() {
        this.spikeGen = new WorldGenSpikes(Blocks.end_stone);
    }
    
    @Override
    protected void genDecorations(final BiomeGenBase biomeGenBase) {
        this.generateOres();
        if (this.randomGenerator.nextInt(5) == 0) {
            this.spikeGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.field_180294_c.add(this.randomGenerator.nextInt(16) + 8, 0, this.randomGenerator.nextInt(16) + 8)));
        }
        if (this.field_180294_c.getX() == 0 && this.field_180294_c.getZ() == 0) {
            final EntityDragon entityDragon = new EntityDragon(this.currentWorld);
            entityDragon.setLocationAndAngles(0.0, 128.0, 0.0, this.randomGenerator.nextFloat() * 360.0f, 0.0f);
            this.currentWorld.spawnEntityInWorld(entityDragon);
        }
    }
}
