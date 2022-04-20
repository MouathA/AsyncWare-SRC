package net.minecraft.block;

import com.google.common.base.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.nbt.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.state.pattern.*;
import net.minecraft.entity.*;

public class BlockSkull extends BlockContainer
{
    public static final PropertyBool NODROP;
    public static final PropertyDirection FACING;
    private static final Predicate IS_WITHER_SKELETON;
    private BlockPattern witherBasePattern;
    private BlockPattern witherPattern;
    
    public boolean canDispenserPlace(final World world, final BlockPos blockPos, final ItemStack itemStack) {
        return itemStack.getMetadata() == 1 && blockPos.getY() >= 2 && world.getDifficulty() != EnumDifficulty.PEACEFUL && !world.isRemote && this.getWitherBasePattern().match(world, blockPos) != null;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.skull;
    }
    
    static {
        FACING = PropertyDirection.create("facing");
        NODROP = PropertyBool.create("nodrop");
        IS_WITHER_SKELETON = (Predicate)new Predicate() {
            public boolean apply(final Object o) {
                return this.apply((BlockWorldState)o);
            }
            
            public boolean apply(final BlockWorldState blockWorldState) {
                return blockWorldState.getBlockState() != null && blockWorldState.getBlockState().getBlock() == Blocks.skull && blockWorldState.getTileEntity() instanceof TileEntitySkull && ((TileEntitySkull)blockWorldState.getTileEntity()).getSkullType() == 1;
            }
        };
    }
    
    @Override
    public int getDamageValue(final World world, final BlockPos blockPos) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        return (tileEntity instanceof TileEntitySkull) ? ((TileEntitySkull)tileEntity).getSkullType() : super.getDamageValue(world, blockPos);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getCollisionBoundingBox(world, blockPos, blockState);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockSkull.FACING, EnumFacing.getFront(n & 0x7)).withProperty(BlockSkull.NODROP, (n & 0x8) > 0);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        switch (BlockSkull$2.$SwitchMap$net$minecraft$util$EnumFacing[((EnumFacing)blockAccess.getBlockState(blockPos).getValue(BlockSkull.FACING)).ordinal()]) {
            default: {
                this.setBlockBounds(0.25f, 0.0f, 0.25f, 0.75f, 0.5f, 0.75f);
                break;
            }
            case 2: {
                this.setBlockBounds(0.25f, 0.25f, 0.5f, 0.75f, 0.75f, 1.0f);
                break;
            }
            case 3: {
                this.setBlockBounds(0.25f, 0.25f, 0.0f, 0.75f, 0.75f, 0.5f);
                break;
            }
            case 4: {
                this.setBlockBounds(0.5f, 0.25f, 0.25f, 1.0f, 0.75f, 0.75f);
                break;
            }
            case 5: {
                this.setBlockBounds(0.0f, 0.25f, 0.25f, 0.5f, 0.75f, 0.75f);
                break;
            }
        }
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntitySkull();
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!world.isRemote) {
            if (!(boolean)blockState.getValue(BlockSkull.NODROP)) {
                final TileEntity tileEntity = world.getTileEntity(blockPos);
                if (tileEntity instanceof TileEntitySkull) {
                    final TileEntitySkull tileEntitySkull = (TileEntitySkull)tileEntity;
                    final ItemStack itemStack = new ItemStack(Items.skull, 1, this.getDamageValue(world, blockPos));
                    if (tileEntitySkull.getSkullType() == 3 && tileEntitySkull.getPlayerProfile() != null) {
                        itemStack.setTagCompound(new NBTTagCompound());
                        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                        NBTUtil.writeGameProfile(nbtTagCompound, tileEntitySkull.getPlayerProfile());
                        itemStack.getTagCompound().setTag("SkullOwner", nbtTagCompound);
                    }
                    Block.spawnAsEntity(world, blockPos, itemStack);
                }
            }
            super.breakBlock(world, blockPos, blockState);
        }
    }
    
    @Override
    public String getLocalizedName() {
        return "\u5620\u563d\u5638\u5631\u567a\u5627\u563f\u5621\u5638\u5638\u567a\u5627\u563f\u5631\u5638\u5631\u5620\u563b\u563a\u567a\u563a\u5635\u5639\u5631";
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
    }
    
    public void checkWitherSpawn(final World world, final BlockPos blockPos, final TileEntitySkull tileEntitySkull) {
        if (tileEntitySkull.getSkullType() == 1 && blockPos.getY() >= 2 && world.getDifficulty() != EnumDifficulty.PEACEFUL && !world.isRemote) {
            final BlockPattern.PatternHelper match = this.getWitherPattern().match(world, blockPos);
            if (match != null) {
                while (true) {
                    final BlockWorldState translateOffset = match.translateOffset(0, 0, 0);
                    world.setBlockState(translateOffset.getPos(), translateOffset.getBlockState().withProperty(BlockSkull.NODROP, true), 2);
                    int n = 0;
                    ++n;
                }
            }
        }
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        final int n = 0x0 | ((EnumFacing)blockState.getValue(BlockSkull.FACING)).getIndex();
        if (blockState.getValue(BlockSkull.NODROP)) {}
        return 0;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockSkull.FACING, BlockSkull.NODROP });
    }
    
    protected BlockSkull() {
        super(Material.circuits);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockSkull.FACING, EnumFacing.NORTH).withProperty(BlockSkull.NODROP, false));
        this.setBlockBounds(0.25f, 0.0f, 0.25f, 0.75f, 0.5f, 0.75f);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.skull;
    }
    
    @Override
    public void onBlockHarvested(final World world, final BlockPos blockPos, IBlockState withProperty, final EntityPlayer entityPlayer) {
        if (entityPlayer.capabilities.isCreativeMode) {
            withProperty = withProperty.withProperty(BlockSkull.NODROP, true);
            world.setBlockState(blockPos, withProperty, 4);
        }
        super.onBlockHarvested(world, blockPos, withProperty, entityPlayer);
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    protected BlockPattern getWitherBasePattern() {
        if (this.witherBasePattern == null) {
            this.witherBasePattern = FactoryBlockPattern.start().aisle("   ", "###", "~#~").where('#', BlockWorldState.hasState((Predicate)BlockStateHelper.forBlock(Blocks.soul_sand))).where('~', BlockWorldState.hasState((Predicate)BlockStateHelper.forBlock(Blocks.air))).build();
        }
        return this.witherBasePattern;
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty(BlockSkull.FACING, entityLivingBase.getHorizontalFacing()).withProperty(BlockSkull.NODROP, false);
    }
    
    protected BlockPattern getWitherPattern() {
        if (this.witherPattern == null) {
            this.witherPattern = FactoryBlockPattern.start().aisle("^^^", "###", "~#~").where('#', BlockWorldState.hasState((Predicate)BlockStateHelper.forBlock(Blocks.soul_sand))).where('^', BlockSkull.IS_WITHER_SKELETON).where('~', BlockWorldState.hasState((Predicate)BlockStateHelper.forBlock(Blocks.air))).build();
        }
        return this.witherPattern;
    }
}
