package net.minecraft.block;

import net.minecraft.entity.player.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public class BlockFenceGate extends BlockDirectional
{
    public static final PropertyBool IN_WALL;
    public static final PropertyBool POWERED;
    public static final PropertyBool OPEN;
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (blockState.getValue(BlockFenceGate.OPEN)) {
            blockState = blockState.withProperty(BlockFenceGate.OPEN, false);
            world.setBlockState(blockPos, blockState, 2);
        }
        else {
            final EnumFacing fromAngle = EnumFacing.fromAngle(entityPlayer.rotationYaw);
            if (blockState.getValue(BlockFenceGate.FACING) == fromAngle.getOpposite()) {
                blockState = blockState.withProperty(BlockFenceGate.FACING, fromAngle);
            }
            blockState = blockState.withProperty(BlockFenceGate.OPEN, true);
            world.setBlockState(blockPos, blockState, 2);
        }
        world.playAuxSFXAtEntity(entityPlayer, ((boolean)blockState.getValue(BlockFenceGate.OPEN)) ? 1003 : 1006, blockPos, 0);
        return true;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return true;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return world.getBlockState(blockPos.down()).getBlock().getMaterial().isSolid() && super.canPlaceBlockAt(world, blockPos);
    }
    
    public BlockFenceGate(final BlockPlanks.EnumType enumType) {
        super(Material.wood, enumType.func_181070_c());
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockFenceGate.OPEN, false).withProperty(BlockFenceGate.POWERED, false).withProperty(BlockFenceGate.IN_WALL, false));
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public boolean isPassable(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return (boolean)blockAccess.getBlockState(blockPos).getValue(BlockFenceGate.OPEN);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (((EnumFacing)blockAccess.getBlockState(blockPos).getValue(BlockFenceGate.FACING)).getAxis() == EnumFacing.Axis.Z) {
            this.setBlockBounds(0.0f, 0.0f, 0.375f, 1.0f, 1.0f, 0.625f);
        }
        else {
            this.setBlockBounds(0.375f, 0.0f, 0.0f, 0.625f, 1.0f, 1.0f);
        }
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        final int n = 0x0 | ((EnumFacing)blockState.getValue(BlockFenceGate.FACING)).getHorizontalIndex();
        if (blockState.getValue(BlockFenceGate.POWERED)) {}
        if (blockState.getValue(BlockFenceGate.OPEN)) {}
        return 0;
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty(BlockFenceGate.FACING, entityLivingBase.getHorizontalFacing()).withProperty(BlockFenceGate.OPEN, false).withProperty(BlockFenceGate.POWERED, false).withProperty(BlockFenceGate.IN_WALL, false);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        if (!world.isRemote) {
            final boolean blockPowered = world.isBlockPowered(blockPos);
            if (blockPowered || block.canProvidePower()) {
                if (blockPowered && !(boolean)blockState.getValue(BlockFenceGate.OPEN) && !(boolean)blockState.getValue(BlockFenceGate.POWERED)) {
                    world.setBlockState(blockPos, blockState.withProperty(BlockFenceGate.OPEN, true).withProperty(BlockFenceGate.POWERED, true), 2);
                    world.playAuxSFXAtEntity(null, 1003, blockPos, 0);
                }
                else if (!blockPowered && (boolean)blockState.getValue(BlockFenceGate.OPEN) && (boolean)blockState.getValue(BlockFenceGate.POWERED)) {
                    world.setBlockState(blockPos, blockState.withProperty(BlockFenceGate.OPEN, false).withProperty(BlockFenceGate.POWERED, false), 2);
                    world.playAuxSFXAtEntity(null, 1006, blockPos, 0);
                }
                else if (blockPowered != (boolean)blockState.getValue(BlockFenceGate.POWERED)) {
                    world.setBlockState(blockPos, blockState.withProperty(BlockFenceGate.POWERED, blockPowered), 2);
                }
            }
        }
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public IBlockState getActualState(IBlockState withProperty, final IBlockAccess blockAccess, final BlockPos blockPos) {
        final EnumFacing.Axis axis = ((EnumFacing)withProperty.getValue(BlockFenceGate.FACING)).getAxis();
        if ((axis == EnumFacing.Axis.Z && (blockAccess.getBlockState(blockPos.west()).getBlock() == Blocks.cobblestone_wall || blockAccess.getBlockState(blockPos.east()).getBlock() == Blocks.cobblestone_wall)) || (axis == EnumFacing.Axis.X && (blockAccess.getBlockState(blockPos.north()).getBlock() == Blocks.cobblestone_wall || blockAccess.getBlockState(blockPos.south()).getBlock() == Blocks.cobblestone_wall))) {
            withProperty = withProperty.withProperty(BlockFenceGate.IN_WALL, true);
        }
        return withProperty;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (blockState.getValue(BlockFenceGate.OPEN)) {
            return null;
        }
        return (((EnumFacing)blockState.getValue(BlockFenceGate.FACING)).getAxis() == EnumFacing.Axis.Z) ? new AxisAlignedBB(blockPos.getX(), blockPos.getY(), blockPos.getZ() + 0.375f, blockPos.getX() + 1, blockPos.getY() + 1.5f, blockPos.getZ() + 0.625f) : new AxisAlignedBB(blockPos.getX() + 0.375f, blockPos.getY(), blockPos.getZ(), blockPos.getX() + 0.625f, blockPos.getY() + 1.5f, blockPos.getZ() + 1);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockFenceGate.FACING, EnumFacing.getHorizontal(n)).withProperty(BlockFenceGate.OPEN, (n & 0x4) != 0x0).withProperty(BlockFenceGate.POWERED, (n & 0x8) != 0x0);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockFenceGate.FACING, BlockFenceGate.OPEN, BlockFenceGate.POWERED, BlockFenceGate.IN_WALL });
    }
    
    static {
        OPEN = PropertyBool.create("open");
        POWERED = PropertyBool.create("powered");
        IN_WALL = PropertyBool.create("in_wall");
    }
}
