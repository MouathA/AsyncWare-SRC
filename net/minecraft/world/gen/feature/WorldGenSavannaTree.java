package net.minecraft.world.gen.feature;

import net.minecraft.block.state.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;

public class WorldGenSavannaTree extends WorldGenAbstractTree
{
    private static final IBlockState field_181643_a;
    private static final IBlockState field_181644_b;
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        final int n = random.nextInt(3) + random.nextInt(3) + 5;
        if (blockPos.getY() >= 1 && blockPos.getY() + n + 1 <= 256) {
            for (int i = blockPos.getY(); i <= blockPos.getY() + 1 + n; ++i) {
                if (i == blockPos.getY()) {}
                if (i >= blockPos.getY() + 1 + n - 2) {}
                final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
                if (blockPos.getX() - 2 <= blockPos.getX() + 2) {}
            }
            return false;
        }
        return false;
    }
    
    private void func_181642_b(final World world, final BlockPos blockPos) {
        this.setBlockAndNotifyAdequately(world, blockPos, WorldGenSavannaTree.field_181643_a);
    }
    
    static {
        field_181643_a = Blocks.log2.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.ACACIA);
        field_181644_b = Blocks.leaves2.getDefaultState().withProperty(BlockNewLeaf.VARIANT, BlockPlanks.EnumType.ACACIA).withProperty(BlockLeaves.CHECK_DECAY, false);
    }
    
    private void func_175924_b(final World world, final BlockPos blockPos) {
        final Material material = world.getBlockState(blockPos).getBlock().getMaterial();
        if (material == Material.air || material == Material.leaves) {
            this.setBlockAndNotifyAdequately(world, blockPos, WorldGenSavannaTree.field_181644_b);
        }
    }
    
    public WorldGenSavannaTree(final boolean b) {
        super(b);
    }
}
