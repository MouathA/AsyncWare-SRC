package net.minecraft.world.gen.feature;

import net.minecraft.block.state.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;

public class WorldGenTallGrass extends WorldGenerator
{
    private final IBlockState tallGrassState;
    
    @Override
    public boolean generate(final World world, final Random random, BlockPos down) {
        Block block;
        while (((block = world.getBlockState(down).getBlock()).getMaterial() == Material.air || block.getMaterial() == Material.leaves) && down.getY() > 0) {
            down = down.down();
        }
        while (true) {
            final BlockPos add = down.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
            if (world.isAirBlock(add) && Blocks.tallgrass.canBlockStay(world, add, this.tallGrassState)) {
                world.setBlockState(add, this.tallGrassState, 2);
            }
            int n = 0;
            ++n;
        }
    }
    
    public WorldGenTallGrass(final BlockTallGrass.EnumType enumType) {
        this.tallGrassState = Blocks.tallgrass.getDefaultState().withProperty(BlockTallGrass.TYPE, enumType);
    }
}
