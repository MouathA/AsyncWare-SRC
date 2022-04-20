package net.minecraft.block;

import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class BlockPane extends Block
{
    public static final PropertyBool EAST;
    public static final PropertyBool WEST;
    private final boolean canDrop;
    public static final PropertyBool SOUTH;
    public static final PropertyBool NORTH;
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    static {
        NORTH = PropertyBool.create("north");
        EAST = PropertyBool.create("east");
        SOUTH = PropertyBool.create("south");
        WEST = PropertyBool.create("west");
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public void addCollisionBoxesToList(final World world, final BlockPos blockPos, final IBlockState blockState, final AxisAlignedBB axisAlignedBB, final List list, final Entity entity) {
        final boolean canPaneConnectToBlock = this.canPaneConnectToBlock(world.getBlockState(blockPos.north()).getBlock());
        final boolean canPaneConnectToBlock2 = this.canPaneConnectToBlock(world.getBlockState(blockPos.south()).getBlock());
        final boolean canPaneConnectToBlock3 = this.canPaneConnectToBlock(world.getBlockState(blockPos.west()).getBlock());
        final boolean canPaneConnectToBlock4 = this.canPaneConnectToBlock(world.getBlockState(blockPos.east()).getBlock());
        if ((!canPaneConnectToBlock3 || !canPaneConnectToBlock4) && (canPaneConnectToBlock3 || canPaneConnectToBlock4 || canPaneConnectToBlock || canPaneConnectToBlock2)) {
            if (canPaneConnectToBlock3) {
                this.setBlockBounds(0.0f, 0.0f, 0.4375f, 0.5f, 1.0f, 0.5625f);
                super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
            }
            else if (canPaneConnectToBlock4) {
                this.setBlockBounds(0.5f, 0.0f, 0.4375f, 1.0f, 1.0f, 0.5625f);
                super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
            }
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.4375f, 1.0f, 1.0f, 0.5625f);
            super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        }
        if ((!canPaneConnectToBlock || !canPaneConnectToBlock2) && (canPaneConnectToBlock3 || canPaneConnectToBlock4 || canPaneConnectToBlock || canPaneConnectToBlock2)) {
            if (canPaneConnectToBlock) {
                this.setBlockBounds(0.4375f, 0.0f, 0.0f, 0.5625f, 1.0f, 0.5f);
                super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
            }
            else if (canPaneConnectToBlock2) {
                this.setBlockBounds(0.4375f, 0.0f, 0.5f, 0.5625f, 1.0f, 1.0f);
                super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
            }
        }
        else {
            this.setBlockBounds(0.4375f, 0.0f, 0.0f, 0.5625f, 1.0f, 1.0f);
            super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        }
    }
    
    protected BlockPane(final Material material, final boolean canDrop) {
        super(material);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockPane.NORTH, false).withProperty(BlockPane.EAST, false).withProperty(BlockPane.SOUTH, false).withProperty(BlockPane.WEST, false));
        this.canDrop = canDrop;
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    public final boolean canPaneConnectToBlock(final Block block) {
        return block.isFullBlock() || block == this || block == Blocks.glass || block == Blocks.stained_glass || block == Blocks.stained_glass_pane || block instanceof BlockPane;
    }
    
    protected boolean canSilkHarvest() {
        return true;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return this.canDrop ? super.getItemDropped(blockState, random, n) : null;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockPane.NORTH, BlockPane.EAST, BlockPane.WEST, BlockPane.SOUTH });
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        float n = 0.4375f;
        float n2 = 0.5625f;
        float n3 = 0.4375f;
        float n4 = 0.5625f;
        final boolean canPaneConnectToBlock = this.canPaneConnectToBlock(blockAccess.getBlockState(blockPos.north()).getBlock());
        final boolean canPaneConnectToBlock2 = this.canPaneConnectToBlock(blockAccess.getBlockState(blockPos.south()).getBlock());
        final boolean canPaneConnectToBlock3 = this.canPaneConnectToBlock(blockAccess.getBlockState(blockPos.west()).getBlock());
        final boolean canPaneConnectToBlock4 = this.canPaneConnectToBlock(blockAccess.getBlockState(blockPos.east()).getBlock());
        if ((!canPaneConnectToBlock3 || !canPaneConnectToBlock4) && (canPaneConnectToBlock3 || canPaneConnectToBlock4 || canPaneConnectToBlock || canPaneConnectToBlock2)) {
            if (canPaneConnectToBlock3) {
                n = 0.0f;
            }
            else if (canPaneConnectToBlock4) {
                n2 = 1.0f;
            }
        }
        else {
            n = 0.0f;
            n2 = 1.0f;
        }
        if ((!canPaneConnectToBlock || !canPaneConnectToBlock2) && (canPaneConnectToBlock3 || canPaneConnectToBlock4 || canPaneConnectToBlock || canPaneConnectToBlock2)) {
            if (canPaneConnectToBlock) {
                n3 = 0.0f;
            }
            else if (canPaneConnectToBlock2) {
                n4 = 1.0f;
            }
        }
        else {
            n3 = 0.0f;
            n4 = 1.0f;
        }
        this.setBlockBounds(n, 0.0f, n3, n2, 1.0f, n4);
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT_MIPPED;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return 0;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return blockAccess.getBlockState(blockPos).getBlock() != this && super.shouldSideBeRendered(blockAccess, blockPos, enumFacing);
    }
    
    @Override
    public IBlockState getActualState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        return blockState.withProperty(BlockPane.NORTH, this.canPaneConnectToBlock(blockAccess.getBlockState(blockPos.north()).getBlock())).withProperty(BlockPane.SOUTH, this.canPaneConnectToBlock(blockAccess.getBlockState(blockPos.south()).getBlock())).withProperty(BlockPane.WEST, this.canPaneConnectToBlock(blockAccess.getBlockState(blockPos.west()).getBlock())).withProperty(BlockPane.EAST, this.canPaneConnectToBlock(blockAccess.getBlockState(blockPos.east()).getBlock()));
    }
}
