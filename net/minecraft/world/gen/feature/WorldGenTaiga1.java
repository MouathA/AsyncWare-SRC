package net.minecraft.world.gen.feature;

import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;

public class WorldGenTaiga1 extends WorldGenAbstractTree
{
    private static final IBlockState field_181636_a;
    private static final IBlockState field_181637_b;
    
    public WorldGenTaiga1() {
        super(false);
    }
    
    static {
        field_181636_a = Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE);
        field_181637_b = Blocks.leaves.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.SPRUCE).withProperty(BlockLeaves.CHECK_DECAY, false);
    }
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        final int n = random.nextInt(5) + 7;
        final int n2 = 1 + random.nextInt(n - (n - random.nextInt(2) - 3) + 1);
        if (blockPos.getY() >= 1 && blockPos.getY() + n + 1 <= 256) {
            if (blockPos.getY() <= blockPos.getY() + 1 + n) {}
            return false;
        }
        return false;
    }
}
