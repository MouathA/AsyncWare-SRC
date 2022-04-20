package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import com.google.common.base.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;

public class BlockFurnace extends BlockContainer
{
    private static boolean keepInventory;
    public static final PropertyDirection FACING;
    private final boolean isBurning;
    
    @Override
    public int getComparatorInputOverride(final World world, final BlockPos blockPos) {
        return Container.calcRedstone(world.getTileEntity(blockPos));
    }
    
    @Override
    public int getRenderType() {
        return 3;
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return true;
        }
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityFurnace) {
            entityPlayer.displayGUIChest((IInventory)tileEntity);
            entityPlayer.triggerAchievement(StatList.field_181741_Y);
        }
        return true;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Item.getItemFromBlock(Blocks.furnace);
    }
    
    @Override
    public IBlockState getStateForEntityRender(final IBlockState blockState) {
        return this.getDefaultState().withProperty(BlockFurnace.FACING, EnumFacing.SOUTH);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumFacing)blockState.getValue(BlockFurnace.FACING)).getIndex();
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.setDefaultFacing(world, blockPos, blockState);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockFurnace.FACING });
    }
    
    protected BlockFurnace(final boolean isBurning) {
        super(Material.rock);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockFurnace.FACING, EnumFacing.NORTH));
        this.isBurning = isBurning;
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        world.setBlockState(blockPos, blockState.withProperty(BlockFurnace.FACING, entityLivingBase.getHorizontalFacing().getOpposite()), 2);
        if (itemStack.hasDisplayName()) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityFurnace) {
                ((TileEntityFurnace)tileEntity).setCustomInventoryName(itemStack.getDisplayName());
            }
        }
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty(BlockFurnace.FACING, entityLivingBase.getHorizontalFacing().getOpposite());
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        EnumFacing enumFacing = EnumFacing.getFront(n);
        if (enumFacing.getAxis() == EnumFacing.Axis.Y) {
            enumFacing = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty(BlockFurnace.FACING, enumFacing);
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityFurnace();
    }
    
    static {
        FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (this.isBurning) {
            final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockFurnace.FACING);
            final double n = blockPos.getX() + 0.5;
            final double n2 = blockPos.getY() + random.nextDouble() * 6.0 / 16.0;
            final double n3 = blockPos.getZ() + 0.5;
            final double n4 = 0.52;
            final double n5 = random.nextDouble() * 0.6 - 0.3;
            switch (BlockFurnace$1.$SwitchMap$net$minecraft$util$EnumFacing[enumFacing.ordinal()]) {
                case 1: {
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, n - n4, n2, n3 + n5, 0.0, 0.0, 0.0, new int[0]);
                    world.spawnParticle(EnumParticleTypes.FLAME, n - n4, n2, n3 + n5, 0.0, 0.0, 0.0, new int[0]);
                    break;
                }
                case 2: {
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, n + n4, n2, n3 + n5, 0.0, 0.0, 0.0, new int[0]);
                    world.spawnParticle(EnumParticleTypes.FLAME, n + n4, n2, n3 + n5, 0.0, 0.0, 0.0, new int[0]);
                    break;
                }
                case 3: {
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, n + n5, n2, n3 - n4, 0.0, 0.0, 0.0, new int[0]);
                    world.spawnParticle(EnumParticleTypes.FLAME, n + n5, n2, n3 - n4, 0.0, 0.0, 0.0, new int[0]);
                    break;
                }
                case 4: {
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, n + n5, n2, n3 + n4, 0.0, 0.0, 0.0, new int[0]);
                    world.spawnParticle(EnumParticleTypes.FLAME, n + n5, n2, n3 + n4, 0.0, 0.0, 0.0, new int[0]);
                    break;
                }
            }
        }
    }
    
    private void setDefaultFacing(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!world.isRemote) {
            final Block block = world.getBlockState(blockPos.north()).getBlock();
            final Block block2 = world.getBlockState(blockPos.south()).getBlock();
            final Block block3 = world.getBlockState(blockPos.west()).getBlock();
            final Block block4 = world.getBlockState(blockPos.east()).getBlock();
            EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockFurnace.FACING);
            if (enumFacing == EnumFacing.NORTH && block.isFullBlock() && !block2.isFullBlock()) {
                enumFacing = EnumFacing.SOUTH;
            }
            else if (enumFacing == EnumFacing.SOUTH && block2.isFullBlock() && !block.isFullBlock()) {
                enumFacing = EnumFacing.NORTH;
            }
            else if (enumFacing == EnumFacing.WEST && block3.isFullBlock() && !block4.isFullBlock()) {
                enumFacing = EnumFacing.EAST;
            }
            else if (enumFacing == EnumFacing.EAST && block4.isFullBlock() && !block3.isFullBlock()) {
                enumFacing = EnumFacing.WEST;
            }
            world.setBlockState(blockPos, blockState.withProperty(BlockFurnace.FACING, enumFacing), 2);
        }
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Item.getItemFromBlock(Blocks.furnace);
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!BlockFurnace.keepInventory) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityFurnace) {
                InventoryHelper.dropInventoryItems(world, blockPos, (IInventory)tileEntity);
                world.updateComparatorOutputLevel(blockPos, this);
            }
        }
        super.breakBlock(world, blockPos, blockState);
    }
    
    public static void setState(final boolean b, final World world, final BlockPos blockPos) {
        final IBlockState blockState = world.getBlockState(blockPos);
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        BlockFurnace.keepInventory = true;
        if (b) {
            world.setBlockState(blockPos, Blocks.lit_furnace.getDefaultState().withProperty(BlockFurnace.FACING, blockState.getValue(BlockFurnace.FACING)), 3);
            world.setBlockState(blockPos, Blocks.lit_furnace.getDefaultState().withProperty(BlockFurnace.FACING, blockState.getValue(BlockFurnace.FACING)), 3);
        }
        else {
            world.setBlockState(blockPos, Blocks.furnace.getDefaultState().withProperty(BlockFurnace.FACING, blockState.getValue(BlockFurnace.FACING)), 3);
            world.setBlockState(blockPos, Blocks.furnace.getDefaultState().withProperty(BlockFurnace.FACING, blockState.getValue(BlockFurnace.FACING)), 3);
        }
        BlockFurnace.keepInventory = false;
        if (tileEntity != null) {
            tileEntity.validate();
            world.setTileEntity(blockPos, tileEntity);
        }
    }
}
