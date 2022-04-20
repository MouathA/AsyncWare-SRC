package net.minecraft.block;

import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.*;
import com.google.common.base.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;

public class BlockBanner extends BlockContainer
{
    public static final PropertyDirection FACING;
    public static final PropertyInteger ROTATION;
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityBanner) {
            final ItemStack itemStack = new ItemStack(Items.banner, 1, ((TileEntityBanner)tileEntity).getBaseColor());
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            tileEntity.writeToNBT(nbtTagCompound);
            nbtTagCompound.removeTag("x");
            nbtTagCompound.removeTag("y");
            nbtTagCompound.removeTag("z");
            nbtTagCompound.removeTag("id");
            itemStack.setTagInfo("BlockEntityTag", nbtTagCompound);
            Block.spawnAsEntity(world, blockPos, itemStack);
        }
        else {
            super.dropBlockAsItemWithChance(world, blockPos, blockState, n, n2);
        }
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return !this.func_181087_e(world, blockPos) && super.canPlaceBlockAt(world, blockPos);
    }
    
    static {
        FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
        ROTATION = PropertyInteger.create("rotation", 0, 15);
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.banner;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityBanner();
    }
    
    @Override
    public void harvestBlock(final World world, final EntityPlayer entityPlayer, final BlockPos blockPos, final IBlockState blockState, final TileEntity tileEntity) {
        if (tileEntity instanceof TileEntityBanner) {
            final TileEntityBanner tileEntityBanner = (TileEntityBanner)tileEntity;
            final ItemStack itemStack = new ItemStack(Items.banner, 1, ((TileEntityBanner)tileEntity).getBaseColor());
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            TileEntityBanner.func_181020_a(nbtTagCompound, tileEntityBanner.getBaseColor(), tileEntityBanner.func_181021_d());
            itemStack.setTagInfo("BlockEntityTag", nbtTagCompound);
            Block.spawnAsEntity(world, blockPos, itemStack);
        }
        else {
            super.harvestBlock(world, entityPlayer, blockPos, blockState, null);
        }
    }
    
    @Override
    public boolean func_181623_g() {
        return true;
    }
    
    @Override
    public String getLocalizedName() {
        return "\ud17e\ud163\ud172\ud17a\ud139\ud175\ud176\ud179\ud179\ud172\ud165\ud139\ud160\ud17f\ud17e\ud163\ud172\ud139\ud179\ud176\ud17a\ud172";
    }
    
    protected BlockBanner() {
        super(Material.wood);
        final float n = 0.25f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, 1.0f, 0.5f + n);
    }
    
    @Override
    public boolean isPassable(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return true;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.banner;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBox(final World world, final BlockPos blockPos) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getSelectedBoundingBox(world, blockPos);
    }
    
    public static class BlockBannerHanging extends BlockBanner
    {
        @Override
        public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
            final EnumFacing enumFacing = (EnumFacing)blockAccess.getBlockState(blockPos).getValue(BlockBannerHanging.FACING);
            final float n = 0.0f;
            final float n2 = 0.78125f;
            final float n3 = 0.0f;
            final float n4 = 1.0f;
            final float n5 = 0.125f;
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            switch (BlockBanner$1.$SwitchMap$net$minecraft$util$EnumFacing[enumFacing.ordinal()]) {
                default: {
                    this.setBlockBounds(n3, n, 1.0f - n5, n4, n2, 1.0f);
                    break;
                }
                case 2: {
                    this.setBlockBounds(n3, n, 0.0f, n4, n2, n5);
                    break;
                }
                case 3: {
                    this.setBlockBounds(1.0f - n5, n, n3, 1.0f, n2, n4);
                    break;
                }
                case 4: {
                    this.setBlockBounds(0.0f, n, n3, n5, n2, n4);
                    break;
                }
            }
        }
        
        public BlockBannerHanging() {
            this.setDefaultState(this.blockState.getBaseState().withProperty(BlockBannerHanging.FACING, EnumFacing.NORTH));
        }
        
        @Override
        public IBlockState getStateFromMeta(final int n) {
            EnumFacing enumFacing = EnumFacing.getFront(n);
            if (enumFacing.getAxis() == EnumFacing.Axis.Y) {
                enumFacing = EnumFacing.NORTH;
            }
            return this.getDefaultState().withProperty(BlockBannerHanging.FACING, enumFacing);
        }
        
        @Override
        protected BlockState createBlockState() {
            return new BlockState(this, new IProperty[] { BlockBannerHanging.FACING });
        }
        
        @Override
        public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
            if (!world.getBlockState(blockToAir.offset(((EnumFacing)blockState.getValue(BlockBannerHanging.FACING)).getOpposite())).getBlock().getMaterial().isSolid()) {
                this.dropBlockAsItem(world, blockToAir, blockState, 0);
                world.setBlockToAir(blockToAir);
            }
            super.onNeighborBlockChange(world, blockToAir, blockState, block);
        }
        
        @Override
        public int getMetaFromState(final IBlockState blockState) {
            return ((EnumFacing)blockState.getValue(BlockBannerHanging.FACING)).getIndex();
        }
    }
    
    public static class BlockBannerStanding extends BlockBanner
    {
        @Override
        public int getMetaFromState(final IBlockState blockState) {
            return (int)blockState.getValue(BlockBannerStanding.ROTATION);
        }
        
        @Override
        public IBlockState getStateFromMeta(final int n) {
            return this.getDefaultState().withProperty(BlockBannerStanding.ROTATION, n);
        }
        
        @Override
        public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
            if (!world.getBlockState(blockToAir.down()).getBlock().getMaterial().isSolid()) {
                this.dropBlockAsItem(world, blockToAir, blockState, 0);
                world.setBlockToAir(blockToAir);
            }
            super.onNeighborBlockChange(world, blockToAir, blockState, block);
        }
        
        @Override
        protected BlockState createBlockState() {
            return new BlockState(this, new IProperty[] { BlockBannerStanding.ROTATION });
        }
        
        public BlockBannerStanding() {
            this.setDefaultState(this.blockState.getBaseState().withProperty(BlockBannerStanding.ROTATION, 0));
        }
    }
}
