package net.minecraft.block;

import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;

public class BlockFence extends Block
{
    public static final PropertyBool WEST;
    public static final PropertyBool EAST;
    public static final PropertyBool SOUTH;
    public static final PropertyBool NORTH;
    
    public boolean canConnectTo(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final Block block = blockAccess.getBlockState(blockPos).getBlock();
        return block != Blocks.barrier && ((block instanceof BlockFence && block.blockMaterial == this.blockMaterial) || block instanceof BlockFenceGate || (block.blockMaterial.isOpaque() && block.isFullCube() && block.blockMaterial != Material.gourd));
    }
    
    static {
        NORTH = PropertyBool.create("north");
        EAST = PropertyBool.create("east");
        SOUTH = PropertyBool.create("south");
        WEST = PropertyBool.create("west");
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockFence.NORTH, BlockFence.EAST, BlockFence.WEST, BlockFence.SOUTH });
    }
    
    @Override
    public void addCollisionBoxesToList(final World world, final BlockPos blockPos, final IBlockState blockState, final AxisAlignedBB axisAlignedBB, final List list, final Entity entity) {
        final boolean canConnectTo = this.canConnectTo(world, blockPos.north());
        final boolean canConnectTo2 = this.canConnectTo(world, blockPos.south());
        final boolean canConnectTo3 = this.canConnectTo(world, blockPos.west());
        final boolean canConnectTo4 = this.canConnectTo(world, blockPos.east());
        float n = 0.375f;
        float n2 = 0.625f;
        float n3 = 0.375f;
        float n4 = 0.625f;
        if (canConnectTo) {
            n3 = 0.0f;
        }
        if (canConnectTo2) {
            n4 = 1.0f;
        }
        if (canConnectTo || canConnectTo2) {
            this.setBlockBounds(n, 0.0f, n3, n2, 1.5f, n4);
            super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        }
        float n5 = 0.375f;
        float n6 = 0.625f;
        if (canConnectTo3) {
            n = 0.0f;
        }
        if (canConnectTo4) {
            n2 = 1.0f;
        }
        if (canConnectTo3 || canConnectTo4 || (!canConnectTo && !canConnectTo2)) {
            this.setBlockBounds(n, 0.0f, n5, n2, 1.5f, n6);
            super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        }
        if (canConnectTo) {
            n5 = 0.0f;
        }
        if (canConnectTo2) {
            n6 = 1.0f;
        }
        this.setBlockBounds(n, 0.0f, n5, n2, 1.0f, n6);
    }
    
    public BlockFence(final Material material) {
        this(material, material.getMaterialMapColor());
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        return world.isRemote || ItemLead.attachToFence(entityPlayer, world, blockPos);
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return true;
    }
    
    public BlockFence(final Material material, final MapColor mapColor) {
        super(material, mapColor);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockFence.NORTH, false).withProperty(BlockFence.EAST, false).withProperty(BlockFence.SOUTH, false).withProperty(BlockFence.WEST, false));
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final boolean canConnectTo = this.canConnectTo(blockAccess, blockPos.north());
        final boolean canConnectTo2 = this.canConnectTo(blockAccess, blockPos.south());
        final boolean canConnectTo3 = this.canConnectTo(blockAccess, blockPos.west());
        final boolean canConnectTo4 = this.canConnectTo(blockAccess, blockPos.east());
        float n = 0.375f;
        float n2 = 0.625f;
        float n3 = 0.375f;
        float n4 = 0.625f;
        if (canConnectTo) {
            n3 = 0.0f;
        }
        if (canConnectTo2) {
            n4 = 1.0f;
        }
        if (canConnectTo3) {
            n = 0.0f;
        }
        if (canConnectTo4) {
            n2 = 1.0f;
        }
        this.setBlockBounds(n, 0.0f, n3, n2, 1.0f, n4);
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public IBlockState getActualState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        return blockState.withProperty(BlockFence.NORTH, this.canConnectTo(blockAccess, blockPos.north())).withProperty(BlockFence.EAST, this.canConnectTo(blockAccess, blockPos.east())).withProperty(BlockFence.SOUTH, this.canConnectTo(blockAccess, blockPos.south())).withProperty(BlockFence.WEST, this.canConnectTo(blockAccess, blockPos.west()));
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return 0;
    }
    
    @Override
    public boolean isPassable(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return false;
    }
}
