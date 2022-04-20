package net.minecraft.world.gen.feature;

import net.minecraft.block.state.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;

public class WorldGenMegaPineTree extends WorldGenHugeTrees
{
    private boolean useBaseHeight;
    private static final IBlockState field_181635_g;
    private static final IBlockState field_181633_e;
    private static final IBlockState field_181634_f;
    
    private void func_150541_c(final World world, final int n, final int n2, final int n3, final int n4, final Random random) {
        final int n5 = random.nextInt(5) + (this.useBaseHeight ? this.baseHeight : 3);
        for (int i = n3 - n5; i <= n3; ++i) {
            final int n6 = n3 - i;
            final int n7 = n4 + MathHelper.floor_float(n6 / (float)n5 * 3.5f);
            this.func_175925_a(world, new BlockPos(n, i, n2), n7 + ((n6 > 0 && n7 == 0 && (i & 0x1) == 0x0) ? 1 : 0));
        }
    }
    
    @Override
    public void func_180711_a(final World world, final Random random, final BlockPos blockPos) {
        this.func_175933_b(world, blockPos.west().north());
        this.func_175933_b(world, blockPos.east(2).north());
        this.func_175933_b(world, blockPos.west().south(2));
        this.func_175933_b(world, blockPos.east(2).south(2));
        while (true) {
            final int nextInt = random.nextInt(64);
            final int n = nextInt % 8;
            final int n2 = nextInt / 8;
            if (n == 0 || n == 7 || n2 == 0 || n2 == 7) {
                this.func_175933_b(world, blockPos.add(-3 + n, 0, -3 + n2));
            }
            int n3 = 0;
            ++n3;
        }
    }
    
    private void func_175934_c(final World world, final BlockPos blockPos) {
        BlockPos up;
        while (true) {
            up = blockPos.up(2);
            final Block block = world.getBlockState(up).getBlock();
            if (block == Blocks.grass || block == Blocks.dirt) {
                break;
            }
            if (block.getMaterial() != Material.air) {}
            int n = 0;
            --n;
        }
        this.setBlockAndNotifyAdequately(world, up, WorldGenMegaPineTree.field_181635_g);
    }
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        final int func_150533_a = this.func_150533_a(random);
        if (!this.func_175929_a(world, random, blockPos, func_150533_a)) {
            return false;
        }
        this.func_150541_c(world, blockPos.getX(), blockPos.getZ(), blockPos.getY() + func_150533_a, 0, random);
        while (0 < func_150533_a) {
            final Block block = world.getBlockState(blockPos.up(0)).getBlock();
            if (block.getMaterial() == Material.air || block.getMaterial() == Material.leaves) {
                this.setBlockAndNotifyAdequately(world, blockPos.up(0), this.woodMetadata);
            }
            if (0 < func_150533_a - 1) {
                final Block block2 = world.getBlockState(blockPos.add(1, 0, 0)).getBlock();
                if (block2.getMaterial() == Material.air || block2.getMaterial() == Material.leaves) {
                    this.setBlockAndNotifyAdequately(world, blockPos.add(1, 0, 0), this.woodMetadata);
                }
                final Block block3 = world.getBlockState(blockPos.add(1, 0, 1)).getBlock();
                if (block3.getMaterial() == Material.air || block3.getMaterial() == Material.leaves) {
                    this.setBlockAndNotifyAdequately(world, blockPos.add(1, 0, 1), this.woodMetadata);
                }
                final Block block4 = world.getBlockState(blockPos.add(0, 0, 1)).getBlock();
                if (block4.getMaterial() == Material.air || block4.getMaterial() == Material.leaves) {
                    this.setBlockAndNotifyAdequately(world, blockPos.add(0, 0, 1), this.woodMetadata);
                }
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    static {
        field_181633_e = Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE);
        field_181634_f = Blocks.leaves.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.SPRUCE).withProperty(BlockLeaves.CHECK_DECAY, false);
        field_181635_g = Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL);
    }
    
    public WorldGenMegaPineTree(final boolean b, final boolean useBaseHeight) {
        super(b, 13, 15, WorldGenMegaPineTree.field_181633_e, WorldGenMegaPineTree.field_181634_f);
        this.useBaseHeight = useBaseHeight;
    }
    
    private void func_175933_b(final World world, final BlockPos blockPos) {
        while (true) {
            if (Math.abs(-2) != 2 || Math.abs(-2) != 2) {
                this.func_175934_c(world, blockPos.add(-2, 0, -2));
            }
            int n = 0;
            ++n;
        }
    }
}
