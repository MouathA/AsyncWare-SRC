package net.minecraft.world.gen.feature;

import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;

public class WorldGenTaiga2 extends WorldGenAbstractTree
{
    private static final IBlockState field_181646_b;
    private static final IBlockState field_181645_a;
    
    static {
        field_181645_a = Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE);
        field_181646_b = Blocks.leaves.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.SPRUCE).withProperty(BlockLeaves.CHECK_DECAY, false);
    }
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        final int n = random.nextInt(4) + 6;
        final int n2 = n - (1 + random.nextInt(2));
        final int n3 = 2 + random.nextInt(2);
        if (blockPos.getY() >= 1 && blockPos.getY() + n + 1 <= 256) {
            if (blockPos.getY() <= blockPos.getY() + 1 + n) {}
            return false;
        }
        return false;
    }
    
    public WorldGenTaiga2(final boolean b) {
        super(b);
    }
}
