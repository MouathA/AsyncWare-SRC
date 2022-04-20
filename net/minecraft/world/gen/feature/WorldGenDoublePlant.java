package net.minecraft.world.gen.feature;

import net.minecraft.block.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;

public class WorldGenDoublePlant extends WorldGenerator
{
    private BlockDoublePlant.EnumPlantType field_150549_a;
    
    public void setPlantType(final BlockDoublePlant.EnumPlantType field_150549_a) {
        this.field_150549_a = field_150549_a;
    }
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        while (true) {
            final BlockPos add = blockPos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
            if (world.isAirBlock(add) && (!world.provider.getHasNoSky() || add.getY() < 254) && Blocks.double_plant.canPlaceBlockAt(world, add)) {
                Blocks.double_plant.placeAt(world, add, this.field_150549_a, 2);
            }
            int n = 0;
            ++n;
        }
    }
}
