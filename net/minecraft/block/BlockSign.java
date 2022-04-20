package net.minecraft.block;

import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.block.material.*;

public class BlockSign extends BlockContainer
{
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    public boolean isPassable(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return true;
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return true;
        }
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        return tileEntity instanceof TileEntitySign && ((TileEntitySign)tileEntity).executeCommand(entityPlayer);
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return !this.func_181087_e(world, blockPos) && super.canPlaceBlockAt(world, blockPos);
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntitySign();
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.sign;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.sign;
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    protected BlockSign() {
        super(Material.wood);
        final float n = 0.25f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, 1.0f, 0.5f + n);
    }
    
    @Override
    public boolean func_181623_g() {
        return true;
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBox(final World world, final BlockPos blockPos) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getSelectedBoundingBox(world, blockPos);
    }
}
