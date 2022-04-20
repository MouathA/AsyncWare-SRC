package net.minecraft.block;

import net.minecraft.world.biome.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.properties.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

public abstract class BlockLeaves extends BlockLeavesBase
{
    int[] surroundings;
    public static final PropertyBool DECAYABLE;
    protected boolean isTransparent;
    public static final PropertyBool CHECK_DECAY;
    protected int iconIndex;
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        return BiomeColorHelper.getFoliageColorAtPos(blockAccess, blockPos);
    }
    
    protected int getSaplingDropChance(final IBlockState blockState) {
        return 20;
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return (random.nextInt(20) == 0) ? 1 : 0;
    }
    
    @Override
    public boolean isVisuallyOpaque() {
        return false;
    }
    
    protected void dropApple(final World world, final BlockPos blockPos, final IBlockState blockState, final int n) {
    }
    
    @Override
    public int getBlockColor() {
        return ColorizerFoliage.getFoliageColor(0.5, 1.0);
    }
    
    static {
        DECAYABLE = PropertyBool.create("decayable");
        CHECK_DECAY = PropertyBool.create("check_decay");
    }
    
    public BlockLeaves() {
        super(Material.leaves, false);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setHardness(0.2f);
        this.setLightOpacity(1);
        this.setStepSound(BlockLeaves.soundTypeGrass);
    }
    
    public void setGraphicsLevel(final boolean b) {
        this.isTransparent = b;
        this.fancyGraphics = b;
        this.iconIndex = (b ? 0 : 1);
    }
    
    @Override
    public int getRenderColor(final IBlockState blockState) {
        return ColorizerFoliage.getFoliageColorBasic();
    }
    
    @Override
    public boolean isOpaqueCube() {
        return !this.fancyGraphics;
    }
    
    private void destroy(final World world, final BlockPos blockToAir) {
        this.dropBlockAsItem(world, blockToAir, world.getBlockState(blockToAir), 0);
        world.setBlockToAir(blockToAir);
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (world.canLightningStrike(blockPos.up()) && !World.doesBlockHaveSolidTopSurface(world, blockPos.down()) && random.nextInt(15) == 1) {
            world.spawnParticle(EnumParticleTypes.DRIP_WATER, blockPos.getX() + random.nextFloat(), blockPos.getY() - 0.05, blockPos.getZ() + random.nextFloat(), 0.0, 0.0, 0.0, new int[0]);
        }
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        if (!world.isAreaLoaded(new BlockPos(x - 2, y - 2, z - 2), new BlockPos(x + 2, y + 2, z + 2))) {
            return;
        }
        while (true) {
            final BlockPos add = blockPos.add(-1, -1, -1);
            final IBlockState blockState2 = world.getBlockState(add);
            if (blockState2.getBlock().getMaterial() == Material.leaves && !(boolean)blockState2.getValue(BlockLeaves.CHECK_DECAY)) {
                world.setBlockState(add, blockState2.withProperty(BlockLeaves.CHECK_DECAY, true), 4);
            }
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Item.getItemFromBlock(Blocks.sapling);
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!world.isRemote && (boolean)blockState.getValue(BlockLeaves.CHECK_DECAY) && (boolean)blockState.getValue(BlockLeaves.DECAYABLE)) {
            final int x = blockPos.getX();
            final int y = blockPos.getY();
            final int z = blockPos.getZ();
            if (this.surroundings == null) {
                this.surroundings = new int[32768];
            }
            if (world.isAreaLoaded(new BlockPos(x - 5, y - 5, z - 5), new BlockPos(x + 5, y + 5, z + 5))) {
                final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
                while (true) {
                    final Block block = world.getBlockState(mutableBlockPos.func_181079_c(x + 1, y - 4, z - 4)).getBlock();
                    if (block != Blocks.log && block != Blocks.log2) {
                        if (block.getMaterial() == Material.leaves) {
                            this.surroundings[892] = -2;
                        }
                        else {
                            this.surroundings[892] = -1;
                        }
                    }
                    else {
                        this.surroundings[892] = 0;
                    }
                    int n = 0;
                    ++n;
                }
            }
            else if (this.surroundings[0] >= 0) {
                world.setBlockState(blockPos, blockState.withProperty(BlockLeaves.CHECK_DECAY, false), 4);
            }
            else {
                this.destroy(world, blockPos);
            }
        }
    }
    
    public abstract BlockPlanks.EnumType getWoodType(final int p0);
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return this.isTransparent ? EnumWorldBlockLayer.CUTOUT_MIPPED : EnumWorldBlockLayer.SOLID;
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        if (!world.isRemote) {
            this.getSaplingDropChance(blockState);
            if (n2 > 0) {}
            if (world.rand.nextInt(40) == 0) {
                Block.spawnAsEntity(world, blockPos, new ItemStack(this.getItemDropped(blockState, world.rand, n2), 1, this.damageDropped(blockState)));
            }
            if (n2 > 0) {}
            this.dropApple(world, blockPos, blockState, 40);
        }
    }
}
