package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import com.google.common.base.*;
import net.minecraft.block.material.*;
import java.util.*;
import net.minecraft.block.state.*;

public class BlockStem extends BlockBush implements IGrowable
{
    public static final PropertyInteger AGE;
    private final Block crop;
    public static final PropertyDirection FACING;
    
    @Override
    public void grow(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        this.growStem(world, blockPos, blockState);
    }
    
    protected BlockStem(final Block crop) {
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockStem.AGE, 0).withProperty(BlockStem.FACING, EnumFacing.UP));
        this.crop = crop;
        this.setTickRandomly(true);
        final float n = 0.125f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, 0.25f, 0.5f + n);
        this.setCreativeTab(null);
    }
    
    public void growStem(final World world, final BlockPos blockPos, final IBlockState blockState) {
        world.setBlockState(blockPos, blockState.withProperty(BlockStem.AGE, Math.min(7, (int)blockState.getValue(BlockStem.AGE) + MathHelper.getRandomIntegerInRange(world.rand, 2, 5))), 2);
    }
    
    protected Item getSeedItem() {
        return (this.crop == Blocks.pumpkin) ? Items.pumpkin_seeds : ((this.crop == Blocks.melon_block) ? Items.melon_seeds : null);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return (int)blockState.getValue(BlockStem.AGE);
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        return this.getRenderColor(blockAccess.getBlockState(blockPos));
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        super.dropBlockAsItemWithChance(world, blockPos, blockState, n, n2);
        if (!world.isRemote) {
            final Item seedItem = this.getSeedItem();
            if (seedItem != null) {
                final int intValue = (int)blockState.getValue(BlockStem.AGE);
                while (true) {
                    if (world.rand.nextInt(15) <= intValue) {
                        Block.spawnAsEntity(world, blockPos, new ItemStack(seedItem));
                    }
                    int n3 = 0;
                    ++n3;
                }
            }
        }
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockStem.AGE, n);
    }
    
    @Override
    public boolean canGrow(final World world, final BlockPos blockPos, final IBlockState blockState, final boolean b) {
        return (int)blockState.getValue(BlockStem.AGE) != 7;
    }
    
    @Override
    public int getRenderColor(final IBlockState blockState) {
        if (blockState.getBlock() != this) {
            return super.getRenderColor(blockState);
        }
        final int intValue = (int)blockState.getValue(BlockStem.AGE);
        return intValue * 32 << 16 | 255 - intValue * 8 << 8 | intValue * 4;
    }
    
    static {
        AGE = PropertyInteger.create("age", 0, 7);
        FACING = PropertyDirection.create("facing", (Predicate)new Predicate() {
            public boolean apply(final EnumFacing enumFacing) {
                return enumFacing != EnumFacing.DOWN;
            }
            
            public boolean apply(final Object o) {
                return this.apply((EnumFacing)o);
            }
        });
    }
    
    protected boolean canPlaceBlockOn(final Block block) {
        return block == Blocks.farmland;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return null;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        final Item seedItem = this.getSeedItem();
        return (seedItem != null) ? seedItem : null;
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        final float n = 0.125f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, 0.25f, 0.5f + n);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.maxY = ((int)blockAccess.getBlockState(blockPos).getValue(BlockStem.AGE) * 2 + 2) / 16.0f;
        final float n = 0.125f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, (float)this.maxY, 0.5f + n);
    }
    
    @Override
    public void updateTick(final World world, BlockPos offset, IBlockState withProperty, final Random random) {
        super.updateTick(world, offset, withProperty, random);
        if (world.getLightFromNeighbors(offset.up()) >= 9 && random.nextInt((int)(25.0f / BlockCrops.getGrowthChance(this, world, offset)) + 1) == 0) {
            final int intValue = (int)withProperty.getValue(BlockStem.AGE);
            if (intValue < 7) {
                withProperty = withProperty.withProperty(BlockStem.AGE, intValue + 1);
                world.setBlockState(offset, withProperty, 2);
            }
            else {
                final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
                while (iterator.hasNext()) {
                    if (world.getBlockState(offset.offset(iterator.next())).getBlock() == this.crop) {
                        return;
                    }
                }
                offset = offset.offset(EnumFacing.Plane.HORIZONTAL.random(random));
                final Block block = world.getBlockState(offset.down()).getBlock();
                if (world.getBlockState(offset).getBlock().blockMaterial == Material.air && (block == Blocks.farmland || block == Blocks.dirt || block == Blocks.grass)) {
                    world.setBlockState(offset, this.crop.getDefaultState());
                }
            }
        }
    }
    
    @Override
    public IBlockState getActualState(IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        blockState = blockState.withProperty(BlockStem.FACING, EnumFacing.UP);
        for (final EnumFacing enumFacing : EnumFacing.Plane.HORIZONTAL) {
            if (blockAccess.getBlockState(blockPos.offset(enumFacing)).getBlock() == this.crop) {
                blockState = blockState.withProperty(BlockStem.FACING, enumFacing);
                break;
            }
        }
        return blockState;
    }
    
    @Override
    public boolean canUseBonemeal(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        return true;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockStem.AGE, BlockStem.FACING });
    }
}
