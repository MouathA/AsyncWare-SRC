package net.minecraft.world.gen.feature;

import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import java.util.*;
import net.minecraft.block.*;

public class WorldGenSwamp extends WorldGenAbstractTree
{
    private static final IBlockState field_181649_b;
    private static final IBlockState field_181648_a;
    
    public WorldGenSwamp() {
        super(false);
    }
    
    private void func_181647_a(final World world, BlockPos blockPos, final PropertyBool propertyBool) {
        final IBlockState withProperty = Blocks.vine.getDefaultState().withProperty(propertyBool, true);
        this.setBlockAndNotifyAdequately(world, blockPos, withProperty);
        int n = 0;
        for (blockPos = blockPos.down(); world.getBlockState(blockPos).getBlock().getMaterial() == Material.air; blockPos = blockPos.down(), --n) {
            this.setBlockAndNotifyAdequately(world, blockPos, withProperty);
        }
    }
    
    @Override
    public boolean generate(final World world, final Random random, BlockPos down) {
        final int n = random.nextInt(4) + 5;
        while (world.getBlockState(down.down()).getBlock().getMaterial() == Material.water) {
            down = down.down();
        }
        if (down.getY() >= 1 && down.getY() + n + 1 <= 256) {
            for (int i = down.getY(); i <= down.getY() + 1 + n; ++i) {
                if (i == down.getY()) {}
                if (i >= down.getY() + 1 + n - 2) {}
                final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
                if (down.getX() - 0 <= down.getX() + 0) {}
            }
            return false;
        }
        return false;
    }
    
    static {
        field_181648_a = Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.OAK);
        field_181649_b = Blocks.leaves.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.OAK).withProperty(BlockOldLeaf.CHECK_DECAY, false);
    }
}
