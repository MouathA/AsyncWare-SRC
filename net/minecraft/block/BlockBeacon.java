package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.*;

public class BlockBeacon extends BlockContainer
{
    public BlockBeacon() {
        super(Material.glass, MapColor.diamondColor);
        this.setHardness(3.0f);
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public int getRenderType() {
        return 3;
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        super.onBlockPlacedBy(world, blockPos, blockState, entityLivingBase, itemStack);
        if (itemStack.hasDisplayName()) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityBeacon) {
                ((TileEntityBeacon)tileEntity).setName(itemStack.getDisplayName());
            }
        }
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return true;
        }
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityBeacon) {
            entityPlayer.displayGUIChest((IInventory)tileEntity);
            entityPlayer.triggerAchievement(StatList.field_181730_N);
        }
        return true;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityBeacon();
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityBeacon) {
            ((TileEntityBeacon)tileEntity).updateBeacon();
            world.addBlockEvent(blockPos, this, 1, 0);
        }
    }
    
    public static void updateColorAsync(final World world, final BlockPos blockPos) {
        HttpUtil.field_180193_a.submit((Runnable)new Runnable(world, blockPos) {
            final BlockPos val$glassPos;
            final World val$worldIn;
            
            @Override
            public void run() {
                final Chunk chunkFromBlockCoords = this.val$worldIn.getChunkFromBlockCoords(this.val$glassPos);
                for (int i = this.val$glassPos.getY() - 1; i >= 0; --i) {
                    final BlockPos blockPos = new BlockPos(this.val$glassPos.getX(), i, this.val$glassPos.getZ());
                    if (!chunkFromBlockCoords.canSeeSky(blockPos)) {
                        break;
                    }
                    if (this.val$worldIn.getBlockState(blockPos).getBlock() == Blocks.beacon) {
                        ((WorldServer)this.val$worldIn).addScheduledTask(new Runnable(this, blockPos) {
                            final BlockBeacon$1 this$0;
                            final BlockPos val$blockpos;
                            
                            @Override
                            public void run() {
                                final TileEntity tileEntity = this.this$0.val$worldIn.getTileEntity(this.val$blockpos);
                                if (tileEntity instanceof TileEntityBeacon) {
                                    ((TileEntityBeacon)tileEntity).updateBeacon();
                                    this.this$0.val$worldIn.addBlockEvent(this.val$blockpos, Blocks.beacon, 1, 0);
                                }
                            }
                        });
                    }
                }
            }
        });
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
}
