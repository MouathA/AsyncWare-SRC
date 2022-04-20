package net.minecraft.world.gen.feature;

import net.minecraft.block.state.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;

public class WorldGenForest extends WorldGenAbstractTree
{
    private static final IBlockState field_181629_a;
    private static final IBlockState field_181630_b;
    private boolean useExtraRandomHeight;
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        int n = random.nextInt(3) + 5;
        if (this.useExtraRandomHeight) {
            n += random.nextInt(7);
        }
        if (blockPos.getY() >= 1 && blockPos.getY() + n + 1 <= 256) {
            for (int i = blockPos.getY(); i <= blockPos.getY() + 1 + n; ++i) {
                if (i == blockPos.getY()) {}
                if (i >= blockPos.getY() + 1 + n - 2) {}
                final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
                if (blockPos.getX() - 0 <= blockPos.getX() + 0) {}
            }
            return false;
        }
        return false;
    }
    
    public WorldGenForest(final boolean b, final boolean useExtraRandomHeight) {
        super(b);
        this.useExtraRandomHeight = useExtraRandomHeight;
    }
    
    static {
        field_181629_a = Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.BIRCH);
        field_181630_b = Blocks.leaves.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.BIRCH).withProperty(BlockOldLeaf.CHECK_DECAY, false);
    }
}
