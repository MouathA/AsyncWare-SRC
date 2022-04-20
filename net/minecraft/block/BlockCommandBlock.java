package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.command.server.*;

public class BlockCommandBlock extends BlockContainer
{
    public static final PropertyBool TRIGGERED;
    
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockCommandBlock.TRIGGERED, (n & 0x1) > 0);
    }
    
    @Override
    public int getComparatorInputOverride(final World world, final BlockPos blockPos) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        return (tileEntity instanceof TileEntityCommandBlock) ? ((TileEntityCommandBlock)tileEntity).getCommandBlockLogic().getSuccessCount() : 0;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        if (!world.isRemote) {
            final boolean blockPowered = world.isBlockPowered(blockPos);
            final boolean booleanValue = (boolean)blockState.getValue(BlockCommandBlock.TRIGGERED);
            if (blockPowered && !booleanValue) {
                world.setBlockState(blockPos, blockState.withProperty(BlockCommandBlock.TRIGGERED, true), 4);
                world.scheduleUpdate(blockPos, this, this.tickRate(world));
            }
            else if (!blockPowered && booleanValue) {
                world.setBlockState(blockPos, blockState.withProperty(BlockCommandBlock.TRIGGERED, false), 4);
            }
        }
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        return tileEntity instanceof TileEntityCommandBlock && ((TileEntityCommandBlock)tileEntity).getCommandBlockLogic().tryOpenEditCommandBlock(entityPlayer);
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityCommandBlock) {
            ((TileEntityCommandBlock)tileEntity).getCommandBlockLogic().trigger(world);
            world.updateComparatorOutputLevel(blockPos, this);
        }
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 0;
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty(BlockCommandBlock.TRIGGERED, false);
    }
    
    static {
        TRIGGERED = PropertyBool.create("triggered");
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        if (blockState.getValue(BlockCommandBlock.TRIGGERED)) {}
        return 0;
    }
    
    @Override
    public int getRenderType() {
        return 3;
    }
    
    public BlockCommandBlock() {
        super(Material.iron, MapColor.adobeColor);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockCommandBlock.TRIGGERED, false));
    }
    
    @Override
    public int tickRate(final World world) {
        return 1;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockCommandBlock.TRIGGERED });
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityCommandBlock();
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityCommandBlock) {
            final CommandBlockLogic commandBlockLogic = ((TileEntityCommandBlock)tileEntity).getCommandBlockLogic();
            if (itemStack.hasDisplayName()) {
                commandBlockLogic.setName(itemStack.getDisplayName());
            }
            if (!world.isRemote) {
                commandBlockLogic.setTrackOutput(world.getGameRules().getBoolean("sendCommandFeedback"));
            }
        }
    }
}
