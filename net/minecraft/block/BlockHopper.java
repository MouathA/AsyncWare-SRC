package net.minecraft.block;

import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.inventory.*;
import net.minecraft.block.properties.*;
import com.google.common.base.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;

public class BlockHopper extends BlockContainer
{
    public static final PropertyDirection FACING;
    public static final PropertyBool ENABLED;
    
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
        if (tileEntity instanceof TileEntityHopper) {
            entityPlayer.displayGUIChest((IInventory)tileEntity);
            entityPlayer.triggerAchievement(StatList.field_181732_P);
        }
        return true;
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.updateState(world, blockPos, blockState);
    }
    
    public static boolean isEnabled(final int n) {
        return (n & 0x8) != 0x8;
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityHopper) {
            InventoryHelper.dropInventoryItems(world, blockPos, (IInventory)tileEntity);
            world.updateComparatorOutputLevel(blockPos, this);
        }
        super.breakBlock(world, blockPos, blockState);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        final int n = 0x0 | ((EnumFacing)blockState.getValue(BlockHopper.FACING)).getIndex();
        if (!(boolean)blockState.getValue(BlockHopper.ENABLED)) {}
        return 0;
    }
    
    static {
        FACING = PropertyDirection.create("facing", (Predicate)new Predicate() {
            public boolean apply(final Object o) {
                return this.apply((EnumFacing)o);
            }
            
            public boolean apply(final EnumFacing enumFacing) {
                return enumFacing != EnumFacing.UP;
            }
        });
        ENABLED = PropertyBool.create("enabled");
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        EnumFacing enumFacing2 = enumFacing.getOpposite();
        if (enumFacing2 == EnumFacing.UP) {
            enumFacing2 = EnumFacing.DOWN;
        }
        return this.getDefaultState().withProperty(BlockHopper.FACING, enumFacing2).withProperty(BlockHopper.ENABLED, true);
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT_MIPPED;
    }
    
    @Override
    public int getRenderType() {
        return 3;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return true;
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        super.onBlockPlacedBy(world, blockPos, blockState, entityLivingBase, itemStack);
        if (itemStack.hasDisplayName()) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityHopper) {
                ((TileEntityHopper)tileEntity).setCustomName(itemStack.getDisplayName());
            }
        }
    }
    
    public BlockHopper() {
        super(Material.iron, MapColor.stoneColor);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockHopper.FACING, EnumFacing.DOWN).withProperty(BlockHopper.ENABLED, true));
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        this.updateState(world, blockPos, blockState);
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    public static EnumFacing getFacing(final int n) {
        return EnumFacing.getFront(n & 0x7);
    }
    
    private void updateState(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final boolean b = !world.isBlockPowered(blockPos);
        if (b != (boolean)blockState.getValue(BlockHopper.ENABLED)) {
            world.setBlockState(blockPos, blockState.withProperty(BlockHopper.ENABLED, b), 4);
        }
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityHopper();
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockHopper.FACING, getFacing(n)).withProperty(BlockHopper.ENABLED, isEnabled(n));
    }
    
    @Override
    public void addCollisionBoxesToList(final World world, final BlockPos blockPos, final IBlockState blockState, final AxisAlignedBB axisAlignedBB, final List list, final Entity entity) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.625f, 1.0f);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        final float n = 0.125f;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, n, 1.0f, 1.0f);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, n);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        this.setBlockBounds(1.0f - n, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        this.setBlockBounds(0.0f, 0.0f, 1.0f - n, 1.0f, 1.0f, 1.0f);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockHopper.FACING, BlockHopper.ENABLED });
    }
}
