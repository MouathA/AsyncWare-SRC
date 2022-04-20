package net.minecraft.block;

import java.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.dispenser.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.stats.*;

public class BlockDispenser extends BlockContainer
{
    public static final PropertyDirection FACING;
    protected Random rand;
    public static final PropertyBool TRIGGERED;
    public static final RegistryDefaulted dispenseBehaviorRegistry;
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        final int n = 0x0 | ((EnumFacing)blockState.getValue(BlockDispenser.FACING)).getIndex();
        if (blockState.getValue(BlockDispenser.TRIGGERED)) {}
        return 0;
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!world.isRemote) {
            this.dispense(world, blockPos);
        }
    }
    
    static {
        FACING = PropertyDirection.create("facing");
        TRIGGERED = PropertyBool.create("triggered");
        dispenseBehaviorRegistry = new RegistryDefaulted(new BehaviorDefaultDispenseItem());
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityDispenser) {
            InventoryHelper.dropInventoryItems(world, blockPos, (IInventory)tileEntity);
            world.updateComparatorOutputLevel(blockPos, this);
        }
        super.breakBlock(world, blockPos, blockState);
    }
    
    public static IPosition getDispensePosition(final IBlockSource blockSource) {
        final EnumFacing facing = getFacing(blockSource.getBlockMetadata());
        return new PositionImpl(blockSource.getX() + 0.7 * facing.getFrontOffsetX(), blockSource.getY() + 0.7 * facing.getFrontOffsetY(), blockSource.getZ() + 0.7 * facing.getFrontOffsetZ());
    }
    
    protected BlockDispenser() {
        super(Material.rock);
        this.rand = new Random();
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockDispenser.FACING, EnumFacing.NORTH).withProperty(BlockDispenser.TRIGGERED, false));
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityDispenser();
    }
    
    protected IBehaviorDispenseItem getBehavior(final ItemStack itemStack) {
        return (IBehaviorDispenseItem)BlockDispenser.dispenseBehaviorRegistry.getObject((itemStack == null) ? null : itemStack.getItem());
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    @Override
    public int getRenderType() {
        return 3;
    }
    
    private void setDefaultDirection(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!world.isRemote) {
            EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockDispenser.FACING);
            final boolean fullBlock = world.getBlockState(blockPos.north()).getBlock().isFullBlock();
            final boolean fullBlock2 = world.getBlockState(blockPos.south()).getBlock().isFullBlock();
            if (enumFacing == EnumFacing.NORTH && fullBlock && !fullBlock2) {
                enumFacing = EnumFacing.SOUTH;
            }
            else if (enumFacing == EnumFacing.SOUTH && fullBlock2 && !fullBlock) {
                enumFacing = EnumFacing.NORTH;
            }
            else {
                final boolean fullBlock3 = world.getBlockState(blockPos.west()).getBlock().isFullBlock();
                final boolean fullBlock4 = world.getBlockState(blockPos.east()).getBlock().isFullBlock();
                if (enumFacing == EnumFacing.WEST && fullBlock3 && !fullBlock4) {
                    enumFacing = EnumFacing.EAST;
                }
                else if (enumFacing == EnumFacing.EAST && fullBlock4 && !fullBlock3) {
                    enumFacing = EnumFacing.WEST;
                }
            }
            world.setBlockState(blockPos, blockState.withProperty(BlockDispenser.FACING, enumFacing).withProperty(BlockDispenser.TRIGGERED, false), 2);
        }
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockDispenser.FACING, getFacing(n)).withProperty(BlockDispenser.TRIGGERED, (n & 0x8) > 0);
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        super.onBlockAdded(world, blockPos, blockState);
        this.setDefaultDirection(world, blockPos, blockState);
    }
    
    @Override
    public IBlockState getStateForEntityRender(final IBlockState blockState) {
        return this.getDefaultState().withProperty(BlockDispenser.FACING, EnumFacing.SOUTH);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockDispenser.FACING, BlockDispenser.TRIGGERED });
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        world.setBlockState(blockPos, blockState.withProperty(BlockDispenser.FACING, BlockPistonBase.getFacingFromEntity(world, blockPos, entityLivingBase)), 2);
        if (itemStack.hasDisplayName()) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityDispenser) {
                ((TileEntityDispenser)tileEntity).setCustomName(itemStack.getDisplayName());
            }
        }
    }
    
    @Override
    public int getComparatorInputOverride(final World world, final BlockPos blockPos) {
        return Container.calcRedstone(world.getTileEntity(blockPos));
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return true;
        }
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityDispenser) {
            entityPlayer.displayGUIChest((IInventory)tileEntity);
            if (tileEntity instanceof TileEntityDropper) {
                entityPlayer.triggerAchievement(StatList.field_181731_O);
            }
            else {
                entityPlayer.triggerAchievement(StatList.field_181733_Q);
            }
        }
        return true;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        final boolean b = world.isBlockPowered(blockPos) || world.isBlockPowered(blockPos.up());
        final boolean booleanValue = (boolean)blockState.getValue(BlockDispenser.TRIGGERED);
        if (b && !booleanValue) {
            world.scheduleUpdate(blockPos, this, this.tickRate(world));
            world.setBlockState(blockPos, blockState.withProperty(BlockDispenser.TRIGGERED, true), 4);
        }
        else if (!b && booleanValue) {
            world.setBlockState(blockPos, blockState.withProperty(BlockDispenser.TRIGGERED, false), 4);
        }
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty(BlockDispenser.FACING, BlockPistonBase.getFacingFromEntity(world, blockPos, entityLivingBase)).withProperty(BlockDispenser.TRIGGERED, false);
    }
    
    public static EnumFacing getFacing(final int n) {
        return EnumFacing.getFront(n & 0x7);
    }
    
    protected void dispense(final World world, final BlockPos blockPos) {
        final BlockSourceImpl blockSourceImpl = new BlockSourceImpl(world, blockPos);
        final TileEntityDispenser tileEntityDispenser = (TileEntityDispenser)blockSourceImpl.getBlockTileEntity();
        if (tileEntityDispenser != null) {
            final int dispenseSlot = tileEntityDispenser.getDispenseSlot();
            if (dispenseSlot < 0) {
                world.playAuxSFX(1001, blockPos, 0);
            }
            else {
                final ItemStack stackInSlot = tileEntityDispenser.getStackInSlot(dispenseSlot);
                final IBehaviorDispenseItem behavior = this.getBehavior(stackInSlot);
                if (behavior != IBehaviorDispenseItem.itemDispenseBehaviorProvider) {
                    final ItemStack dispense = behavior.dispense(blockSourceImpl, stackInSlot);
                    tileEntityDispenser.setInventorySlotContents(dispenseSlot, (dispense.stackSize <= 0) ? null : dispense);
                }
            }
        }
    }
    
    @Override
    public int tickRate(final World world) {
        return 4;
    }
}
