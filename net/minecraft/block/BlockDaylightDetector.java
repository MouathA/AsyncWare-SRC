package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class BlockDaylightDetector extends BlockContainer
{
    public static final PropertyInteger POWER;
    private final boolean inverted;
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockDaylightDetector.POWER, n);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return (int)blockState.getValue(BlockDaylightDetector.POWER);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Item.getItemFromBlock(Blocks.daylight_detector);
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (!entityPlayer.isAllowEdit()) {
            return super.onBlockActivated(world, blockPos, blockState, entityPlayer, enumFacing, n, n2, n3);
        }
        if (world.isRemote) {
            return true;
        }
        if (this.inverted) {
            world.setBlockState(blockPos, Blocks.daylight_detector.getDefaultState().withProperty(BlockDaylightDetector.POWER, blockState.getValue(BlockDaylightDetector.POWER)), 4);
            Blocks.daylight_detector.updatePower(world, blockPos);
        }
        else {
            world.setBlockState(blockPos, Blocks.daylight_detector_inverted.getDefaultState().withProperty(BlockDaylightDetector.POWER, blockState.getValue(BlockDaylightDetector.POWER)), 4);
            Blocks.daylight_detector_inverted.updatePower(world, blockPos);
        }
        return true;
    }
    
    static {
        POWER = PropertyInteger.create("power", 0, 15);
    }
    
    @Override
    public int getRenderType() {
        return 3;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityDaylightDetector();
    }
    
    @Override
    public int getWeakPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return (int)blockState.getValue(BlockDaylightDetector.POWER);
    }
    
    public BlockDaylightDetector(final boolean inverted) {
        super(Material.wood);
        this.inverted = inverted;
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockDaylightDetector.POWER, 0));
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.375f, 1.0f);
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setHardness(0.2f);
        this.setStepSound(BlockDaylightDetector.soundTypeWood);
        this.setUnlocalizedName("daylightDetector");
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Item.getItemFromBlock(Blocks.daylight_detector);
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        if (!this.inverted) {
            super.getSubBlocks(item, creativeTabs, list);
        }
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockDaylightDetector.POWER });
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    public void updatePower(final World world, final BlockPos blockPos) {
        if (!world.provider.getHasNoSky()) {
            final IBlockState blockState = world.getBlockState(blockPos);
            final int n = world.getLightFor(EnumSkyBlock.SKY, blockPos) - world.getSkylightSubtracted();
            final float celestialAngleRadians = world.getCelestialAngleRadians(1.0f);
            int clamp_int = MathHelper.clamp_int(Math.round(n * MathHelper.cos(celestialAngleRadians + (((celestialAngleRadians < 3.1415927f) ? 0.0f : 6.2831855f) - celestialAngleRadians) * 0.2f)), 0, 15);
            if (this.inverted) {
                clamp_int = 15 - clamp_int;
            }
            if ((int)blockState.getValue(BlockDaylightDetector.POWER) != clamp_int) {
                world.setBlockState(blockPos, blockState.withProperty(BlockDaylightDetector.POWER, clamp_int), 3);
            }
        }
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.375f, 1.0f);
    }
}
