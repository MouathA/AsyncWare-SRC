package net.minecraft.block;

import net.minecraft.block.properties.*;
import com.google.common.base.*;
import java.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;

public class BlockRailDetector extends BlockRailBase
{
    public static final PropertyEnum SHAPE;
    public static final PropertyBool POWERED;
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!world.isRemote && (boolean)blockState.getValue(BlockRailDetector.POWERED)) {
            this.updatePoweredState(world, blockPos, blockState);
        }
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
    
    protected List findMinecarts(final World world, final BlockPos blockPos, final Class clazz, final Predicate... array) {
        final AxisAlignedBB dectectionBox = this.getDectectionBox(blockPos);
        return (array.length != 1) ? world.getEntitiesWithinAABB(clazz, dectectionBox) : world.getEntitiesWithinAABB(clazz, dectectionBox, array[0]);
    }
    
    private void updatePoweredState(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final boolean booleanValue = (boolean)blockState.getValue(BlockRailDetector.POWERED);
        if (!this.findMinecarts(world, blockPos, EntityMinecart.class, new Predicate[0]).isEmpty()) {}
        if (!booleanValue) {
            world.setBlockState(blockPos, blockState.withProperty(BlockRailDetector.POWERED, true), 3);
            world.notifyNeighborsOfStateChange(blockPos, this);
            world.notifyNeighborsOfStateChange(blockPos.down(), this);
            world.markBlockRangeForRenderUpdate(blockPos, blockPos);
        }
        world.scheduleUpdate(blockPos, this, this.tickRate(world));
        world.updateComparatorOutputLevel(blockPos, this);
    }
    
    private AxisAlignedBB getDectectionBox(final BlockPos blockPos) {
        return new AxisAlignedBB(blockPos.getX() + 0.2f, blockPos.getY(), blockPos.getZ() + 0.2f, blockPos.getX() + 1 - 0.2f, blockPos.getY() + 1 - 0.2f, blockPos.getZ() + 1 - 0.2f);
    }
    
    @Override
    public int getWeakPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return blockState.getValue(BlockRailDetector.POWERED) ? 15 : 0;
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        super.onBlockAdded(world, blockPos, blockState);
        this.updatePoweredState(world, blockPos, blockState);
    }
    
    static {
        SHAPE = PropertyEnum.create("shape", EnumRailDirection.class, (Predicate)new Predicate() {
            public boolean apply(final EnumRailDirection enumRailDirection) {
                return enumRailDirection != EnumRailDirection.NORTH_EAST && enumRailDirection != EnumRailDirection.NORTH_WEST && enumRailDirection != EnumRailDirection.SOUTH_EAST && enumRailDirection != EnumRailDirection.SOUTH_WEST;
            }
            
            public boolean apply(final Object o) {
                return this.apply((EnumRailDirection)o);
            }
        });
        POWERED = PropertyBool.create("powered");
    }
    
    @Override
    public void randomTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
    }
    
    @Override
    public int getStrongPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return blockState.getValue(BlockRailDetector.POWERED) ? ((enumFacing == EnumFacing.UP) ? 15 : 0) : 0;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        final int n = 0x0 | ((EnumRailDirection)blockState.getValue(BlockRailDetector.SHAPE)).getMetadata();
        if (blockState.getValue(BlockRailDetector.POWERED)) {}
        return 0;
    }
    
    @Override
    public IProperty getShapeProperty() {
        return BlockRailDetector.SHAPE;
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    public BlockRailDetector() {
        super(true);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockRailDetector.POWERED, false).withProperty(BlockRailDetector.SHAPE, EnumRailDirection.NORTH_SOUTH));
        this.setTickRandomly(true);
    }
    
    @Override
    public int getComparatorInputOverride(final World world, final BlockPos blockPos) {
        if (world.getBlockState(blockPos).getValue(BlockRailDetector.POWERED)) {
            final List minecarts = this.findMinecarts(world, blockPos, EntityMinecartCommandBlock.class, new Predicate[0]);
            if (!minecarts.isEmpty()) {
                return minecarts.get(0).getCommandBlockLogic().getSuccessCount();
            }
            final List minecarts2 = this.findMinecarts(world, blockPos, EntityMinecart.class, EntitySelectors.selectInventories);
            if (!minecarts2.isEmpty()) {
                return Container.calcRedstoneFromInventory(minecarts2.get(0));
            }
        }
        return 0;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockRailDetector.SHAPE, BlockRailDetector.POWERED });
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final IBlockState blockState, final Entity entity) {
        if (!world.isRemote && !(boolean)blockState.getValue(BlockRailDetector.POWERED)) {
            this.updatePoweredState(world, blockPos, blockState);
        }
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockRailDetector.SHAPE, EnumRailDirection.byMetadata(n & 0x7)).withProperty(BlockRailDetector.POWERED, (n & 0x8) > 0);
    }
    
    @Override
    public int tickRate(final World world) {
        return 20;
    }
}
