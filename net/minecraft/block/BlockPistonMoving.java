package net.minecraft.block;

import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.init.*;

public class BlockPistonMoving extends BlockContainer
{
    public static final PropertyDirection FACING;
    public static final PropertyEnum TYPE;
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityPiston) {
            ((TileEntityPiston)tileEntity).clearPistonTileEntity();
        }
        else {
            super.breakBlock(world, blockPos, blockState);
        }
    }
    
    private TileEntityPiston getTileEntity(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final TileEntity tileEntity = blockAccess.getTileEntity(blockPos);
        return (tileEntity instanceof TileEntityPiston) ? ((TileEntityPiston)tileEntity) : null;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return false;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockPistonMoving.FACING, BlockPistonMoving.TYPE });
    }
    
    @Override
    public void onBlockDestroyedByPlayer(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final BlockPos offset = blockPos.offset(((EnumFacing)blockState.getValue(BlockPistonMoving.FACING)).getOpposite());
        final IBlockState blockState2 = world.getBlockState(offset);
        if (blockState2.getBlock() instanceof BlockPistonBase && (boolean)blockState2.getValue(BlockPistonBase.EXTENDED)) {
            world.setBlockToAir(offset);
        }
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockToAir, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (!world.isRemote && world.getTileEntity(blockToAir) == null) {
            world.setBlockToAir(blockToAir);
            return true;
        }
        return false;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockPistonMoving.FACING, BlockPistonExtension.getFacing(n)).withProperty(BlockPistonMoving.TYPE, ((n & 0x8) > 0) ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        final int n = 0x0 | ((EnumFacing)blockState.getValue(BlockPistonMoving.FACING)).getIndex();
        if (blockState.getValue(BlockPistonMoving.TYPE) == BlockPistonExtension.EnumPistonType.STICKY) {}
        return 0;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return null;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final TileEntityPiston tileEntity = this.getTileEntity(world, blockPos);
        if (tileEntity == null) {
            return null;
        }
        float progress = tileEntity.getProgress(0.0f);
        if (tileEntity.isExtending()) {
            progress = 1.0f - progress;
        }
        return this.getBoundingBox(world, blockPos, tileEntity.getPistonState(), progress, tileEntity.getFacing());
    }
    
    public static TileEntity newTileEntity(final IBlockState blockState, final EnumFacing enumFacing, final boolean b, final boolean b2) {
        return new TileEntityPiston(blockState, enumFacing, b, b2);
    }
    
    static {
        FACING = BlockPistonExtension.FACING;
        TYPE = BlockPistonExtension.TYPE;
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    public BlockPistonMoving() {
        super(Material.piston);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockPistonMoving.FACING, EnumFacing.NORTH).withProperty(BlockPistonMoving.TYPE, BlockPistonExtension.EnumPistonType.DEFAULT));
        this.setHardness(-1.0f);
    }
    
    public AxisAlignedBB getBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final EnumFacing enumFacing) {
        if (blockState.getBlock() == this || blockState.getBlock().getMaterial() == Material.air) {
            return null;
        }
        final AxisAlignedBB collisionBoundingBox = blockState.getBlock().getCollisionBoundingBox(world, blockPos, blockState);
        if (collisionBoundingBox == null) {
            return null;
        }
        double minX = collisionBoundingBox.minX;
        double minY = collisionBoundingBox.minY;
        double minZ = collisionBoundingBox.minZ;
        double maxX = collisionBoundingBox.maxX;
        double maxY = collisionBoundingBox.maxY;
        double maxZ = collisionBoundingBox.maxZ;
        if (enumFacing.getFrontOffsetX() < 0) {
            minX -= enumFacing.getFrontOffsetX() * n;
        }
        else {
            maxX -= enumFacing.getFrontOffsetX() * n;
        }
        if (enumFacing.getFrontOffsetY() < 0) {
            minY -= enumFacing.getFrontOffsetY() * n;
        }
        else {
            maxY -= enumFacing.getFrontOffsetY() * n;
        }
        if (enumFacing.getFrontOffsetZ() < 0) {
            minZ -= enumFacing.getFrontOffsetZ() * n;
        }
        else {
            maxZ -= enumFacing.getFrontOffsetZ() * n;
        }
        return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
    }
    
    @Override
    public MovingObjectPosition collisionRayTrace(final World world, final BlockPos blockPos, final Vec3 vec3, final Vec3 vec4) {
        return null;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return null;
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        if (!world.isRemote) {
            final TileEntityPiston tileEntity = this.getTileEntity(world, blockPos);
            if (tileEntity != null) {
                final IBlockState pistonState = tileEntity.getPistonState();
                pistonState.getBlock().dropBlockAsItem(world, blockPos, pistonState, 0);
            }
        }
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return null;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        if (!world.isRemote) {
            world.getTileEntity(blockPos);
        }
    }
    
    @Override
    public boolean canPlaceBlockOnSide(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        return false;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final TileEntityPiston tileEntity = this.getTileEntity(blockAccess, blockPos);
        if (tileEntity != null) {
            final Block block = tileEntity.getPistonState().getBlock();
            if (block == this || block.getMaterial() == Material.air) {
                return;
            }
            float progress = tileEntity.getProgress(0.0f);
            if (tileEntity.isExtending()) {
                progress = 1.0f - progress;
            }
            block.setBlockBoundsBasedOnState(blockAccess, blockPos);
            if (block == Blocks.piston || block == Blocks.sticky_piston) {
                progress = 0.0f;
            }
            final EnumFacing facing = tileEntity.getFacing();
            this.minX = block.getBlockBoundsMinX() - facing.getFrontOffsetX() * progress;
            this.minY = block.getBlockBoundsMinY() - facing.getFrontOffsetY() * progress;
            this.minZ = block.getBlockBoundsMinZ() - facing.getFrontOffsetZ() * progress;
            this.maxX = block.getBlockBoundsMaxX() - facing.getFrontOffsetX() * progress;
            this.maxY = block.getBlockBoundsMaxY() - facing.getFrontOffsetY() * progress;
            this.maxZ = block.getBlockBoundsMaxZ() - facing.getFrontOffsetZ() * progress;
        }
    }
}
