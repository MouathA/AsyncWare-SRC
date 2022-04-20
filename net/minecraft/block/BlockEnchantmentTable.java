package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class BlockEnchantmentTable extends BlockContainer
{
    protected BlockEnchantmentTable() {
        super(Material.rock, MapColor.redColor);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.75f, 1.0f);
        this.setLightOpacity(0);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityEnchantmentTable();
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        super.onBlockPlacedBy(world, blockPos, blockState, entityLivingBase, itemStack);
        if (itemStack.hasDisplayName()) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityEnchantmentTable) {
                ((TileEntityEnchantmentTable)tileEntity).setCustomName(itemStack.getDisplayName());
            }
        }
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        super.randomDisplayTick(world, blockPos, blockState, random);
        while (true) {
            if (random.nextInt(16) == 0) {
                while (true) {
                    if (world.getBlockState(blockPos.add(-2, 0, 2)).getBlock() == Blocks.bookshelf) {
                        if (!world.isAirBlock(blockPos.add(0, 0, 0))) {
                            break;
                        }
                        world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, blockPos.getX() + 0.5, blockPos.getY() + 2.0, blockPos.getZ() + 0.5, -2 + random.nextFloat() - 0.5, 0 - random.nextFloat() - 1.0f, 2 + random.nextFloat() - 0.5, new int[0]);
                    }
                    int n = 0;
                    ++n;
                }
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    public int getRenderType() {
        return 3;
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return true;
        }
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityEnchantmentTable) {
            entityPlayer.displayGui((IInteractionObject)tileEntity);
        }
        return true;
    }
}
