package net.minecraft.world.gen.feature;

import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import java.util.*;

public class WorldGenTrees extends WorldGenAbstractTree
{
    private final boolean vinesGrow;
    private final IBlockState metaLeaves;
    private static final IBlockState field_181653_a;
    private static final IBlockState field_181654_b;
    private final int minTreeHeight;
    private final IBlockState metaWood;
    
    private void func_181650_b(final World world, BlockPos blockPos, final PropertyBool propertyBool) {
        this.func_181651_a(world, blockPos, propertyBool);
        int n = 0;
        for (blockPos = blockPos.down(); world.getBlockState(blockPos).getBlock().getMaterial() == Material.air; blockPos = blockPos.down(), --n) {
            this.func_181651_a(world, blockPos, propertyBool);
        }
    }
    
    public WorldGenTrees(final boolean b, final int minTreeHeight, final IBlockState metaWood, final IBlockState metaLeaves, final boolean vinesGrow) {
        super(b);
        this.minTreeHeight = minTreeHeight;
        this.metaWood = metaWood;
        this.metaLeaves = metaLeaves;
        this.vinesGrow = vinesGrow;
    }
    
    private void func_181651_a(final World world, final BlockPos blockPos, final PropertyBool propertyBool) {
        this.setBlockAndNotifyAdequately(world, blockPos, Blocks.vine.getDefaultState().withProperty(propertyBool, true));
    }
    
    public WorldGenTrees(final boolean b) {
        this(b, 4, WorldGenTrees.field_181653_a, WorldGenTrees.field_181654_b, false);
    }
    
    private void func_181652_a(final World world, final int n, final BlockPos blockPos, final EnumFacing enumFacing) {
        this.setBlockAndNotifyAdequately(world, blockPos, Blocks.cocoa.getDefaultState().withProperty(BlockCocoa.AGE, n).withProperty(BlockCocoa.FACING, enumFacing));
    }
    
    static {
        field_181653_a = Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.OAK);
        field_181654_b = Blocks.leaves.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.OAK).withProperty(BlockLeaves.CHECK_DECAY, false);
    }
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        final int n = random.nextInt(3) + this.minTreeHeight;
        if (blockPos.getY() >= 1 && blockPos.getY() + n + 1 <= 256) {
            for (int i = blockPos.getY(); i <= blockPos.getY() + 1 + n; ++i) {
                if (i == blockPos.getY()) {}
                if (i >= blockPos.getY() + 1 + n - 2) {}
                final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
                final int n2 = blockPos.getX() - 3;
                if (0 <= blockPos.getX() + 3) {}
            }
            return false;
        }
        return false;
    }
}
