package net.minecraft.block;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.stats.*;
import net.minecraft.inventory.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.state.*;
import com.google.common.base.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

public class BlockEnderChest extends BlockContainer
{
    public static final PropertyDirection FACING;
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        while (true) {
            final int n = random.nextInt(2) * 2 - 1;
            final int n2 = random.nextInt(2) * 2 - 1;
            world.spawnParticle(EnumParticleTypes.PORTAL, blockPos.getX() + 0.5 + 0.25 * n, blockPos.getY() + random.nextFloat(), blockPos.getZ() + 0.5 + 0.25 * n2, random.nextFloat() * n, (random.nextFloat() - 0.5) * 0.125, random.nextFloat() * n2, new int[0]);
            int n3 = 0;
            ++n3;
        }
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        world.setBlockState(blockPos, blockState.withProperty(BlockEnderChest.FACING, entityLivingBase.getHorizontalFacing().getOpposite()), 2);
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        final InventoryEnderChest inventoryEnderChest = entityPlayer.getInventoryEnderChest();
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (inventoryEnderChest == null || !(tileEntity instanceof TileEntityEnderChest)) {
            return true;
        }
        if (world.getBlockState(blockPos.up()).getBlock().isNormalCube()) {
            return true;
        }
        if (world.isRemote) {
            return true;
        }
        inventoryEnderChest.setChestTileEntity((TileEntityEnderChest)tileEntity);
        entityPlayer.displayGUIChest(inventoryEnderChest);
        entityPlayer.triggerAchievement(StatList.field_181738_V);
        return true;
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 8;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityEnderChest();
    }
    
    @Override
    public int getRenderType() {
        return 2;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        EnumFacing enumFacing = EnumFacing.getFront(n);
        if (enumFacing.getAxis() == EnumFacing.Axis.Y) {
            enumFacing = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty(BlockEnderChest.FACING, enumFacing);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockEnderChest.FACING });
    }
    
    static {
        FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
    }
    
    protected BlockEnderChest() {
        super(Material.rock);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockEnderChest.FACING, EnumFacing.NORTH));
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumFacing)blockState.getValue(BlockEnderChest.FACING)).getIndex();
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty(BlockEnderChest.FACING, entityLivingBase.getHorizontalFacing().getOpposite());
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Item.getItemFromBlock(Blocks.obsidian);
    }
    
    protected boolean canSilkHarvest() {
        return true;
    }
}
