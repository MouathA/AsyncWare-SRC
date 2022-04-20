package net.minecraft.world.gen;

import net.minecraft.world.gen.feature.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;

public class GeneratorBushFeature extends WorldGenerator
{
    private BlockBush field_175908_a;
    
    public GeneratorBushFeature(final BlockBush field_175908_a) {
        this.field_175908_a = field_175908_a;
    }
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        while (true) {
            final BlockPos add = blockPos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
            if (world.isAirBlock(add) && (!world.provider.getHasNoSky() || add.getY() < 255) && this.field_175908_a.canBlockStay(world, add, this.field_175908_a.getDefaultState())) {
                world.setBlockState(add, this.field_175908_a.getDefaultState(), 2);
            }
            int n = 0;
            ++n;
        }
    }
}
