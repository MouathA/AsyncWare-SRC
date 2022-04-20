package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.block.material.*;

public class BlockTripWire extends Block
{
    public static final PropertyBool ATTACHED;
    public static final PropertyBool SUSPENDED;
    public static final PropertyBool DISARMED;
    public static final PropertyBool SOUTH;
    public static final PropertyBool WEST;
    public static final PropertyBool POWERED;
    public static final PropertyBool NORTH;
    public static final PropertyBool EAST;
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        final boolean booleanValue = (boolean)blockState.getValue(BlockTripWire.ATTACHED);
        if (!(boolean)blockState.getValue(BlockTripWire.SUSPENDED)) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.09375f, 1.0f);
        }
        else if (!booleanValue) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
        else {
            this.setBlockBounds(0.0f, 0.0625f, 0.0f, 1.0f, 0.15625f, 1.0f);
        }
    }
    
    private void notifyHook(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final EnumFacing[] array = { EnumFacing.SOUTH, EnumFacing.WEST };
        while (0 < array.length) {
            final EnumFacing enumFacing = array[0];
            while (true) {
                final BlockPos offset = blockPos.offset(enumFacing, 1);
                final IBlockState blockState2 = world.getBlockState(offset);
                if (blockState2.getBlock() == Blocks.tripwire_hook) {
                    if (blockState2.getValue(BlockTripWireHook.FACING) == enumFacing.getOpposite()) {
                        Blocks.tripwire_hook.func_176260_a(world, offset, blockState2, false, true, 1, blockState);
                        break;
                    }
                    break;
                }
                else {
                    if (blockState2.getBlock() != Blocks.tripwire) {
                        break;
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
    public void onBlockAdded(final World world, final BlockPos blockPos, IBlockState withProperty) {
        withProperty = withProperty.withProperty(BlockTripWire.SUSPENDED, !World.doesBlockHaveSolidTopSurface(world, blockPos.down()));
        world.setBlockState(blockPos, withProperty, 3);
        this.notifyHook(world, blockPos, withProperty);
    }
    
    private void updateState(final World world, final BlockPos blockPos) {
        final IBlockState blockState = world.getBlockState(blockPos);
        final boolean booleanValue = (boolean)blockState.getValue(BlockTripWire.POWERED);
        final List entitiesWithinAABBExcludingEntity = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(blockPos.getX() + this.minX, blockPos.getY() + this.minY, blockPos.getZ() + this.minZ, blockPos.getX() + this.maxX, blockPos.getY() + this.maxY, blockPos.getZ() + this.maxZ));
        if (!entitiesWithinAABBExcludingEntity.isEmpty()) {
            final Iterator<Entity> iterator = entitiesWithinAABBExcludingEntity.iterator();
            while (iterator.hasNext()) {
                if (!iterator.next().doesEntityNotTriggerPressurePlate()) {
                    break;
                }
            }
        }
        if (!booleanValue) {
            final IBlockState withProperty = blockState.withProperty(BlockTripWire.POWERED, true);
            world.setBlockState(blockPos, withProperty, 3);
            this.notifyHook(world, blockPos, withProperty);
        }
        world.scheduleUpdate(blockPos, this, this.tickRate(world));
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final IBlockState blockState, final Entity entity) {
        if (!world.isRemote && !(boolean)blockState.getValue(BlockTripWire.POWERED)) {
            this.updateState(world, blockPos);
        }
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockTripWire.POWERED, (n & 0x1) > 0).withProperty(BlockTripWire.SUSPENDED, (n & 0x2) > 0).withProperty(BlockTripWire.ATTACHED, (n & 0x4) > 0).withProperty(BlockTripWire.DISARMED, (n & 0x8) > 0);
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    public static boolean isConnectedTo(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        final IBlockState blockState2 = blockAccess.getBlockState(blockPos.offset(enumFacing));
        final Block block = blockState2.getBlock();
        if (block == Blocks.tripwire_hook) {
            return blockState2.getValue(BlockTripWireHook.FACING) == enumFacing.getOpposite();
        }
        return block == Blocks.tripwire && (boolean)blockState.getValue(BlockTripWire.SUSPENDED) == (boolean)blockState2.getValue(BlockTripWire.SUSPENDED);
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.string;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if ((boolean)blockState.getValue(BlockTripWire.SUSPENDED) != !World.doesBlockHaveSolidTopSurface(world, blockToAir.down())) {
            this.dropBlockAsItem(world, blockToAir, blockState, 0);
            world.setBlockToAir(blockToAir);
        }
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockTripWire.POWERED, BlockTripWire.SUSPENDED, BlockTripWire.ATTACHED, BlockTripWire.DISARMED, BlockTripWire.NORTH, BlockTripWire.EAST, BlockTripWire.WEST, BlockTripWire.SOUTH });
    }
    
    @Override
    public void onBlockHarvested(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer) {
        if (!world.isRemote && entityPlayer.getCurrentEquippedItem() != null && entityPlayer.getCurrentEquippedItem().getItem() == Items.shears) {
            world.setBlockState(blockPos, blockState.withProperty(BlockTripWire.DISARMED, true), 4);
        }
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.string;
    }
    
    @Override
    public IBlockState getActualState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        return blockState.withProperty(BlockTripWire.NORTH, isConnectedTo(blockAccess, blockPos, blockState, EnumFacing.NORTH)).withProperty(BlockTripWire.EAST, isConnectedTo(blockAccess, blockPos, blockState, EnumFacing.EAST)).withProperty(BlockTripWire.SOUTH, isConnectedTo(blockAccess, blockPos, blockState, EnumFacing.SOUTH)).withProperty(BlockTripWire.WEST, isConnectedTo(blockAccess, blockPos, blockState, EnumFacing.WEST));
    }
    
    @Override
    public void randomTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        if (blockState.getValue(BlockTripWire.POWERED)) {}
        if (blockState.getValue(BlockTripWire.SUSPENDED)) {}
        if (blockState.getValue(BlockTripWire.ATTACHED)) {}
        if (blockState.getValue(BlockTripWire.DISARMED)) {}
        return 0;
    }
    
    public BlockTripWire() {
        super(Material.circuits);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockTripWire.POWERED, false).withProperty(BlockTripWire.SUSPENDED, false).withProperty(BlockTripWire.ATTACHED, false).withProperty(BlockTripWire.DISARMED, false).withProperty(BlockTripWire.NORTH, false).withProperty(BlockTripWire.EAST, false).withProperty(BlockTripWire.SOUTH, false).withProperty(BlockTripWire.WEST, false));
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.15625f, 1.0f);
        this.setTickRandomly(true);
    }
    
    static {
        POWERED = PropertyBool.create("powered");
        SUSPENDED = PropertyBool.create("suspended");
        ATTACHED = PropertyBool.create("attached");
        DISARMED = PropertyBool.create("disarmed");
        NORTH = PropertyBool.create("north");
        EAST = PropertyBool.create("east");
        SOUTH = PropertyBool.create("south");
        WEST = PropertyBool.create("west");
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.notifyHook(world, blockPos, blockState.withProperty(BlockTripWire.POWERED, true));
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!world.isRemote && (boolean)world.getBlockState(blockPos).getValue(BlockTripWire.POWERED)) {
            this.updateState(world, blockPos);
        }
    }
}
