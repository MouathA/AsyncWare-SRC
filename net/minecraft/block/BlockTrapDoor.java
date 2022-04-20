package net.minecraft.block;

import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;
import com.google.common.base.*;
import net.minecraft.util.*;

public class BlockTrapDoor extends Block
{
    public static final PropertyBool OPEN;
    public static final PropertyEnum HALF;
    public static final PropertyDirection FACING;
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, IBlockState cycleProperty, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (this.blockMaterial == Material.iron) {
            return true;
        }
        cycleProperty = cycleProperty.cycleProperty(BlockTrapDoor.OPEN);
        world.setBlockState(blockPos, cycleProperty, 2);
        world.playAuxSFXAtEntity(entityPlayer, ((boolean)cycleProperty.getValue(BlockTrapDoor.OPEN)) ? 1003 : 1006, blockPos, 0);
        return true;
    }
    
    @Override
    public boolean canPlaceBlockOnSide(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        return !enumFacing.getAxis().isVertical() && world.getBlockState(blockPos.offset(enumFacing.getOpposite())).getBlock() != 0;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockTrapDoor.FACING, getFacing(n)).withProperty(BlockTrapDoor.OPEN, (n & 0x4) != 0x0).withProperty(BlockTrapDoor.HALF, ((n & 0x8) == 0x0) ? DoorHalf.BOTTOM : DoorHalf.TOP);
    }
    
    public void setBounds(final IBlockState blockState) {
        if (blockState.getBlock() == this) {
            final boolean b = blockState.getValue(BlockTrapDoor.HALF) == DoorHalf.TOP;
            final Boolean b2 = (Boolean)blockState.getValue(BlockTrapDoor.OPEN);
            final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockTrapDoor.FACING);
            if (b) {
                this.setBlockBounds(0.0f, 0.8125f, 0.0f, 1.0f, 1.0f, 1.0f);
            }
            else {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.1875f, 1.0f);
            }
            if (b2) {
                if (enumFacing == EnumFacing.NORTH) {
                    this.setBlockBounds(0.0f, 0.0f, 0.8125f, 1.0f, 1.0f, 1.0f);
                }
                if (enumFacing == EnumFacing.SOUTH) {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.1875f);
                }
                if (enumFacing == EnumFacing.WEST) {
                    this.setBlockBounds(0.8125f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                }
                if (enumFacing == EnumFacing.EAST) {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 0.1875f, 1.0f, 1.0f);
                }
            }
        }
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        final int n = 0x0 | getMetaForFacing((EnumFacing)blockState.getValue(BlockTrapDoor.FACING));
        if (blockState.getValue(BlockTrapDoor.OPEN)) {}
        if (blockState.getValue(BlockTrapDoor.HALF) == DoorHalf.TOP) {}
        return 0;
    }
    
    @Override
    public MovingObjectPosition collisionRayTrace(final World world, final BlockPos blockPos, final Vec3 vec3, final Vec3 vec4) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.collisionRayTrace(world, blockPos, vec3, vec4);
    }
    
    protected BlockTrapDoor(final Material material) {
        super(material);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockTrapDoor.FACING, EnumFacing.NORTH).withProperty(BlockTrapDoor.OPEN, false).withProperty(BlockTrapDoor.HALF, DoorHalf.BOTTOM));
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean isPassable(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return !(boolean)blockAccess.getBlockState(blockPos).getValue(BlockTrapDoor.OPEN);
    }
    
    protected static EnumFacing getFacing(final int n) {
        switch (n & 0x3) {
            case 0: {
                return EnumFacing.NORTH;
            }
            case 1: {
                return EnumFacing.SOUTH;
            }
            case 2: {
                return EnumFacing.WEST;
            }
            default: {
                return EnumFacing.EAST;
            }
        }
    }
    
    protected static int getMetaForFacing(final EnumFacing enumFacing) {
        switch (BlockTrapDoor$1.$SwitchMap$net$minecraft$util$EnumFacing[enumFacing.ordinal()]) {
            case 1: {
                return 0;
            }
            case 2: {
                return 1;
            }
            case 3: {
                return 2;
            }
            default: {
                return 3;
            }
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getCollisionBoundingBox(world, blockPos, blockState);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (!world.isRemote) {
            if (world.getBlockState(blockToAir.offset(((EnumFacing)blockState.getValue(BlockTrapDoor.FACING)).getOpposite())).getBlock() != 0) {
                world.setBlockToAir(blockToAir);
                this.dropBlockAsItem(world, blockToAir, blockState, 0);
            }
            else {
                final boolean blockPowered = world.isBlockPowered(blockToAir);
                if ((blockPowered || block.canProvidePower()) && (boolean)blockState.getValue(BlockTrapDoor.OPEN) != blockPowered) {
                    world.setBlockState(blockToAir, blockState.withProperty(BlockTrapDoor.OPEN, blockPowered), 2);
                    world.playAuxSFXAtEntity(null, blockPowered ? 1003 : 1006, blockToAir, 0);
                }
            }
        }
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        IBlockState blockState = this.getDefaultState();
        if (enumFacing.getAxis().isHorizontal()) {
            blockState = blockState.withProperty(BlockTrapDoor.FACING, enumFacing).withProperty(BlockTrapDoor.OPEN, false).withProperty(BlockTrapDoor.HALF, (n2 > 0.5f) ? DoorHalf.TOP : DoorHalf.BOTTOM);
        }
        return blockState;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockTrapDoor.FACING, BlockTrapDoor.OPEN, BlockTrapDoor.HALF });
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.40625f, 0.0f, 1.0f, 0.59375f, 1.0f);
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.setBounds(blockAccess.getBlockState(blockPos));
    }
    
    static {
        FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
        OPEN = PropertyBool.create("open");
        HALF = PropertyEnum.create("half", DoorHalf.class);
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBox(final World world, final BlockPos blockPos) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getSelectedBoundingBox(world, blockPos);
    }
    
    public enum DoorHalf implements IStringSerializable
    {
        private static final DoorHalf[] $VALUES;
        
        BOTTOM("BOTTOM", 1, "bottom");
        
        private final String name;
        
        TOP("TOP", 0, "top");
        
        static {
            $VALUES = new DoorHalf[] { DoorHalf.TOP, DoorHalf.BOTTOM };
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        private DoorHalf(final String s, final int n, final String name) {
            this.name = name;
        }
    }
}
