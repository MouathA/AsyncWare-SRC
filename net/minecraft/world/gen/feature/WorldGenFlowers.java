package net.minecraft.world.gen.feature;

import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;

public class WorldGenFlowers extends WorldGenerator
{
    private BlockFlower flower;
    private IBlockState field_175915_b;
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        while (true) {
            final BlockPos add = blockPos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
            if (world.isAirBlock(add) && (!world.provider.getHasNoSky() || add.getY() < 255) && this.flower.canBlockStay(world, add, this.field_175915_b)) {
                world.setBlockState(add, this.field_175915_b, 2);
            }
            int n = 0;
            ++n;
        }
    }
    
    public void setGeneratedBlock(final BlockFlower flower, final BlockFlower.EnumFlowerType enumFlowerType) {
        this.flower = flower;
        this.field_175915_b = flower.getDefaultState().withProperty(flower.getTypeProperty(), enumFlowerType);
    }
    
    public WorldGenFlowers(final BlockFlower blockFlower, final BlockFlower.EnumFlowerType enumFlowerType) {
        this.setGeneratedBlock(blockFlower, enumFlowerType);
    }
}
