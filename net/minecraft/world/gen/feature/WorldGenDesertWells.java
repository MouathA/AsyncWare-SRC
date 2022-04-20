package net.minecraft.world.gen.feature;

import net.minecraft.block.state.*;
import net.minecraft.block.state.pattern.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import com.google.common.base.*;

public class WorldGenDesertWells extends WorldGenerator
{
    private final IBlockState field_175911_b;
    private final IBlockState field_175910_d;
    private static final BlockStateHelper field_175913_a;
    private final IBlockState field_175912_c;
    
    public WorldGenDesertWells() {
        this.field_175911_b = Blocks.stone_slab.getDefaultState().withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.SAND).withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM);
        this.field_175912_c = Blocks.sandstone.getDefaultState();
        this.field_175910_d = Blocks.flowing_water.getDefaultState();
    }
    
    @Override
    public boolean generate(final World world, final Random random, BlockPos down) {
        while (world.isAirBlock(down) && down.getY() > 2) {
            down = down.down();
        }
        if (!WorldGenDesertWells.field_175913_a.apply(world.getBlockState(down))) {
            return false;
        }
        while (!world.isAirBlock(down.add(1, -1, -1)) || !world.isAirBlock(down.add(1, -2, -1))) {
            int n = 0;
            ++n;
        }
        return false;
    }
    
    static {
        field_175913_a = BlockStateHelper.forBlock(Blocks.sand).where(BlockSand.VARIANT, Predicates.equalTo((Object)BlockSand.EnumType.SAND));
    }
}
