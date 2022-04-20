package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import com.google.common.base.*;
import net.minecraft.util.*;

public class BlockDoor extends Block
{
    public static final PropertyEnum HALF;
    public static final PropertyBool POWERED;
    public static final PropertyEnum HINGE;
    public static final PropertyDirection FACING;
    public static final PropertyBool OPEN;
    
    private void setBoundBasedOnMeta(final int n) {
        final float n2 = 0.1875f;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 2.0f, 1.0f);
        final EnumFacing facing = getFacing(n);
        final boolean open = isOpen(n);
        final boolean hingeLeft = isHingeLeft(n);
        if (open) {
            if (facing == EnumFacing.EAST) {
                if (!hingeLeft) {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, n2);
                }
                else {
                    this.setBlockBounds(0.0f, 0.0f, 1.0f - n2, 1.0f, 1.0f, 1.0f);
                }
            }
            else if (facing == EnumFacing.SOUTH) {
                if (!hingeLeft) {
                    this.setBlockBounds(1.0f - n2, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                }
                else {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, n2, 1.0f, 1.0f);
                }
            }
            else if (facing == EnumFacing.WEST) {
                if (!hingeLeft) {
                    this.setBlockBounds(0.0f, 0.0f, 1.0f - n2, 1.0f, 1.0f, 1.0f);
                }
                else {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, n2);
                }
            }
            else if (facing == EnumFacing.NORTH) {
                if (!hingeLeft) {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, n2, 1.0f, 1.0f);
                }
                else {
                    this.setBlockBounds(1.0f - n2, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                }
            }
        }
        else if (facing == EnumFacing.EAST) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, n2, 1.0f, 1.0f);
        }
        else if (facing == EnumFacing.SOUTH) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, n2);
        }
        else if (facing == EnumFacing.WEST) {
            this.setBlockBounds(1.0f - n2, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        else if (facing == EnumFacing.NORTH) {
            this.setBlockBounds(0.0f, 0.0f, 1.0f - n2, 1.0f, 1.0f, 1.0f);
        }
    }
    
    @Override
    public IBlockState getActualState(IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (blockState.getValue(BlockDoor.HALF) == EnumDoorHalf.LOWER) {
            final IBlockState blockState2 = blockAccess.getBlockState(blockPos.up());
            if (blockState2.getBlock() == this) {
                blockState = blockState.withProperty(BlockDoor.HINGE, blockState2.getValue(BlockDoor.HINGE)).withProperty(BlockDoor.POWERED, blockState2.getValue(BlockDoor.POWERED));
            }
        }
        else {
            final IBlockState blockState3 = blockAccess.getBlockState(blockPos.down());
            if (blockState3.getBlock() == this) {
                blockState = blockState.withProperty(BlockDoor.FACING, blockState3.getValue(BlockDoor.FACING)).withProperty(BlockDoor.OPEN, blockState3.getValue(BlockDoor.OPEN));
            }
        }
        return blockState;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getCollisionBoundingBox(world, blockPos, blockState);
    }
    
    private Item getItem() {
        return (this == Blocks.iron_door) ? Items.iron_door : ((this == Blocks.spruce_door) ? Items.spruce_door : ((this == Blocks.birch_door) ? Items.birch_door : ((this == Blocks.jungle_door) ? Items.jungle_door : ((this == Blocks.acacia_door) ? Items.acacia_door : ((this == Blocks.dark_oak_door) ? Items.dark_oak_door : Items.oak_door)))));
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    protected BlockDoor(final Material material) {
        super(material);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockDoor.FACING, EnumFacing.NORTH).withProperty(BlockDoor.OPEN, false).withProperty(BlockDoor.HINGE, EnumHingePosition.LEFT).withProperty(BlockDoor.POWERED, false).withProperty(BlockDoor.HALF, EnumDoorHalf.LOWER));
    }
    
    protected static boolean isOpen(final int n) {
        return (n & 0x4) != 0x0;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return (blockState.getValue(BlockDoor.HALF) == EnumDoorHalf.UPPER) ? null : this.getItem();
    }
    
    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal((this.getUnlocalizedName() + ".name").replaceAll("tile", "item"));
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockDoor.HALF, BlockDoor.FACING, BlockDoor.OPEN, BlockDoor.HINGE, BlockDoor.POWERED });
    }
    
    protected static boolean isTop(final int n) {
        return (n & 0x8) != 0x0;
    }
    
    public static EnumFacing getFacing(final int n) {
        return EnumFacing.getHorizontal(n & 0x3).rotateYCCW();
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return blockPos.getY() < 255 && (World.doesBlockHaveSolidTopSurface(world, blockPos.down()) && super.canPlaceBlockAt(world, blockPos) && super.canPlaceBlockAt(world, blockPos.up()));
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (blockState.getValue(BlockDoor.HALF) == EnumDoorHalf.UPPER) {
            final BlockPos down = blockToAir.down();
            final IBlockState blockState2 = world.getBlockState(down);
            if (blockState2.getBlock() != this) {
                world.setBlockToAir(blockToAir);
            }
            else if (block != this) {
                this.onNeighborBlockChange(world, down, blockState2, block);
            }
        }
        else {
            final BlockPos up = blockToAir.up();
            final IBlockState blockState3 = world.getBlockState(up);
            if (blockState3.getBlock() != this) {
                world.setBlockToAir(blockToAir);
            }
            if (!World.doesBlockHaveSolidTopSurface(world, blockToAir.down())) {
                world.setBlockToAir(blockToAir);
                if (blockState3.getBlock() == this) {
                    world.setBlockToAir(up);
                }
            }
            if (!world.isRemote) {
                this.dropBlockAsItem(world, blockToAir, blockState, 0);
            }
        }
    }
    
    public static int combineMetadata(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        final int metaFromState = blockState.getBlock().getMetaFromState(blockState);
        final boolean top = isTop(metaFromState);
        final IBlockState blockState2 = blockAccess.getBlockState(blockPos.down());
        final int metaFromState2 = blockState2.getBlock().getMetaFromState(blockState2);
        final int n = top ? metaFromState2 : metaFromState;
        final IBlockState blockState3 = blockAccess.getBlockState(blockPos.up());
        final int metaFromState3 = blockState3.getBlock().getMetaFromState(blockState3);
        final int n2 = top ? metaFromState : metaFromState3;
        return removeHalfBit(n) | (top ? 8 : 0) | (((n2 & 0x1) != 0x0) ? 16 : 0) | (((n2 & 0x2) != 0x0) ? 32 : 0);
    }
    
    @Override
    public void onBlockHarvested(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer) {
        final BlockPos down = blockPos.down();
        if (entityPlayer.capabilities.isCreativeMode && blockState.getValue(BlockDoor.HALF) == EnumDoorHalf.UPPER && world.getBlockState(down).getBlock() == this) {
            world.setBlockToAir(down);
        }
    }
    
    static {
        FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
        OPEN = PropertyBool.create("open");
        HINGE = PropertyEnum.create("hinge", EnumHingePosition.class);
        POWERED = PropertyBool.create("powered");
        HALF = PropertyEnum.create("half", EnumDoorHalf.class);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.setBoundBasedOnMeta(combineMetadata(blockAccess, blockPos));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        if (blockState.getValue(BlockDoor.HALF) == EnumDoorHalf.UPPER) {
            if (blockState.getValue(BlockDoor.HINGE) == EnumHingePosition.RIGHT) {}
            if (blockState.getValue(BlockDoor.POWERED)) {}
        }
        else {
            final int n = 0x0 | ((EnumFacing)blockState.getValue(BlockDoor.FACING)).rotateY().getHorizontalIndex();
            if (blockState.getValue(BlockDoor.OPEN)) {}
        }
        return 0;
    }
    
    public void toggleDoor(final World world, final BlockPos blockPos, final boolean b) {
        final IBlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() == this) {
            final BlockPos blockPos2 = (blockState.getValue(BlockDoor.HALF) == EnumDoorHalf.LOWER) ? blockPos : blockPos.down();
            final IBlockState blockState2 = (blockPos == blockPos2) ? blockState : world.getBlockState(blockPos2);
            if (blockState2.getBlock() == this && (boolean)blockState2.getValue(BlockDoor.OPEN) != b) {
                world.setBlockState(blockPos2, blockState2.withProperty(BlockDoor.OPEN, b), 2);
                world.markBlockRangeForRenderUpdate(blockPos2, blockPos);
                world.playAuxSFXAtEntity(null, b ? 1003 : 1006, blockPos, 0);
            }
        }
    }
    
    @Override
    public int getMobilityFlag() {
        return 1;
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBox(final World world, final BlockPos blockPos) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getSelectedBoundingBox(world, blockPos);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return ((n & 0x8) > 0) ? this.getDefaultState().withProperty(BlockDoor.HALF, EnumDoorHalf.UPPER).withProperty(BlockDoor.HINGE, ((n & 0x1) > 0) ? EnumHingePosition.RIGHT : EnumHingePosition.LEFT).withProperty(BlockDoor.POWERED, (n & 0x2) > 0) : this.getDefaultState().withProperty(BlockDoor.HALF, EnumDoorHalf.LOWER).withProperty(BlockDoor.FACING, EnumFacing.getHorizontal(n & 0x3).rotateYCCW()).withProperty(BlockDoor.OPEN, (n & 0x4) > 0);
    }
    
    @Override
    public MovingObjectPosition collisionRayTrace(final World world, final BlockPos blockPos, final Vec3 vec3, final Vec3 vec4) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.collisionRayTrace(world, blockPos, vec3, vec4);
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, IBlockState cycleProperty, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (this.blockMaterial == Material.iron) {
            return true;
        }
        final BlockPos blockPos2 = (cycleProperty.getValue(BlockDoor.HALF) == EnumDoorHalf.LOWER) ? blockPos : blockPos.down();
        final IBlockState blockState = blockPos.equals(blockPos2) ? cycleProperty : world.getBlockState(blockPos2);
        if (blockState.getBlock() != this) {
            return false;
        }
        cycleProperty = blockState.cycleProperty(BlockDoor.OPEN);
        world.setBlockState(blockPos2, cycleProperty, 2);
        world.markBlockRangeForRenderUpdate(blockPos2, blockPos);
        world.playAuxSFXAtEntity(entityPlayer, ((boolean)cycleProperty.getValue(BlockDoor.OPEN)) ? 1003 : 1006, blockPos, 0);
        return true;
    }
    
    protected static boolean isHingeLeft(final int n) {
        return (n & 0x10) != 0x0;
    }
    
    protected static int removeHalfBit(final int n) {
        return n & 0x7;
    }
    
    @Override
    public boolean isPassable(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return isOpen(combineMetadata(blockAccess, blockPos));
    }
    
    public static boolean isOpen(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return isOpen(combineMetadata(blockAccess, blockPos));
    }
    
    public static EnumFacing getFacing(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return getFacing(combineMetadata(blockAccess, blockPos));
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return this.getItem();
    }
    
    public enum EnumHingePosition implements IStringSerializable
    {
        RIGHT("RIGHT", 1);
        
        private static final EnumHingePosition[] $VALUES;
        
        LEFT("LEFT", 0);
        
        @Override
        public String getName() {
            return (this == EnumHingePosition.LEFT) ? "left" : "right";
        }
        
        private EnumHingePosition(final String s, final int n) {
        }
        
        @Override
        public String toString() {
            return this.getName();
        }
        
        static {
            $VALUES = new EnumHingePosition[] { EnumHingePosition.LEFT, EnumHingePosition.RIGHT };
        }
    }
    
    public enum EnumDoorHalf implements IStringSerializable
    {
        private static final EnumDoorHalf[] $VALUES;
        
        LOWER("LOWER", 1), 
        UPPER("UPPER", 0);
        
        static {
            $VALUES = new EnumDoorHalf[] { EnumDoorHalf.UPPER, EnumDoorHalf.LOWER };
        }
        
        @Override
        public String toString() {
            return this.getName();
        }
        
        private EnumDoorHalf(final String s, final int n) {
        }
        
        @Override
        public String getName() {
            return (this == EnumDoorHalf.UPPER) ? "upper" : "lower";
        }
    }
}
