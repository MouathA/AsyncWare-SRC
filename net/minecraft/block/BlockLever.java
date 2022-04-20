package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockLever extends Block
{
    public static final PropertyEnum FACING;
    public static final PropertyBool POWERED;
    
    @Override
    public boolean canPlaceBlockOnSide(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        return func_181090_a(world, blockPos, enumFacing.getOpposite());
    }
    
    @Override
    public int getStrongPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return blockState.getValue(BlockLever.POWERED) ? ((((EnumOrientation)blockState.getValue(BlockLever.FACING)).getFacing() == enumFacing) ? 15 : 0) : 0;
    }
    
    public static int getMetadataForFacing(final EnumFacing enumFacing) {
        switch (BlockLever$1.$SwitchMap$net$minecraft$util$EnumFacing[enumFacing.ordinal()]) {
            case 1: {
                return 0;
            }
            case 2: {
                return 5;
            }
            case 3: {
                return 4;
            }
            case 4: {
                return 3;
            }
            case 5: {
                return 2;
            }
            case 6: {
                return 1;
            }
            default: {
                return -1;
            }
        }
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    protected static boolean func_181090_a(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        return BlockButton.func_181088_a(world, blockPos, enumFacing);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (blockToAir < blockState && !func_181090_a(world, blockToAir, ((EnumOrientation)blockState.getValue(BlockLever.FACING)).getFacing().getOpposite())) {
            this.dropBlockAsItem(world, blockToAir, blockState, 0);
            world.setBlockToAir(blockToAir);
        }
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (blockState.getValue(BlockLever.POWERED)) {
            world.notifyNeighborsOfStateChange(blockPos, this);
            world.notifyNeighborsOfStateChange(blockPos.offset(((EnumOrientation)blockState.getValue(BlockLever.FACING)).getFacing().getOpposite()), this);
        }
        super.breakBlock(world, blockPos, blockState);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockLever.FACING, EnumOrientation.byMetadata(n & 0x7)).withProperty(BlockLever.POWERED, (n & 0x8) > 0);
    }
    
    static {
        FACING = PropertyEnum.create("facing", EnumOrientation.class);
        POWERED = PropertyBool.create("powered");
    }
    
    protected BlockLever() {
        super(Material.circuits);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockLever.FACING, EnumOrientation.NORTH).withProperty(BlockLever.POWERED, false));
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final float n = 0.1875f;
        switch (BlockLever$1.$SwitchMap$net$minecraft$block$BlockLever$EnumOrientation[((EnumOrientation)blockAccess.getBlockState(blockPos).getValue(BlockLever.FACING)).ordinal()]) {
            case 1: {
                this.setBlockBounds(0.0f, 0.2f, 0.5f - n, n * 2.0f, 0.8f, 0.5f + n);
                break;
            }
            case 2: {
                this.setBlockBounds(1.0f - n * 2.0f, 0.2f, 0.5f - n, 1.0f, 0.8f, 0.5f + n);
                break;
            }
            case 3: {
                this.setBlockBounds(0.5f - n, 0.2f, 0.0f, 0.5f + n, 0.8f, n * 2.0f);
                break;
            }
            case 4: {
                this.setBlockBounds(0.5f - n, 0.2f, 1.0f - n * 2.0f, 0.5f + n, 0.8f, 1.0f);
                break;
            }
            case 5:
            case 6: {
                final float n2 = 0.25f;
                this.setBlockBounds(0.5f - n2, 0.0f, 0.5f - n2, 0.5f + n2, 0.6f, 0.5f + n2);
                break;
            }
            case 7:
            case 8: {
                final float n3 = 0.25f;
                this.setBlockBounds(0.5f - n3, 0.4f, 0.5f - n3, 0.5f + n3, 1.0f, 0.5f + n3);
                break;
            }
        }
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        final IBlockState withProperty = this.getDefaultState().withProperty(BlockLever.POWERED, false);
        if (func_181090_a(world, blockPos, enumFacing.getOpposite())) {
            return withProperty.withProperty(BlockLever.FACING, EnumOrientation.forFacings(enumFacing, entityLivingBase.getHorizontalFacing()));
        }
        for (final EnumFacing enumFacing2 : EnumFacing.Plane.HORIZONTAL) {
            if (enumFacing2 != enumFacing && func_181090_a(world, blockPos, enumFacing2.getOpposite())) {
                return withProperty.withProperty(BlockLever.FACING, EnumOrientation.forFacings(enumFacing2, entityLivingBase.getHorizontalFacing()));
            }
        }
        if (World.doesBlockHaveSolidTopSurface(world, blockPos.down())) {
            return withProperty.withProperty(BlockLever.FACING, EnumOrientation.forFacings(EnumFacing.UP, entityLivingBase.getHorizontalFacing()));
        }
        return withProperty;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public int getWeakPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return blockState.getValue(BlockLever.POWERED) ? 15 : 0;
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, IBlockState cycleProperty, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return true;
        }
        cycleProperty = cycleProperty.cycleProperty(BlockLever.POWERED);
        world.setBlockState(blockPos, cycleProperty, 3);
        world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, "random.click", 0.3f, ((boolean)cycleProperty.getValue(BlockLever.POWERED)) ? 0.6f : 0.5f);
        world.notifyNeighborsOfStateChange(blockPos, this);
        world.notifyNeighborsOfStateChange(blockPos.offset(((EnumOrientation)cycleProperty.getValue(BlockLever.FACING)).getFacing().getOpposite()), this);
        return true;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockLever.FACING, BlockLever.POWERED });
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        final int n = 0x0 | ((EnumOrientation)blockState.getValue(BlockLever.FACING)).getMetadata();
        if (blockState.getValue(BlockLever.POWERED)) {}
        return 0;
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
    
    public enum EnumOrientation implements IStringSerializable
    {
        EAST("EAST", 1, 1, "east", EnumFacing.EAST), 
        WEST("WEST", 2, 2, "west", EnumFacing.WEST), 
        UP_Z("UP_Z", 5, 5, "up_z", EnumFacing.UP);
        
        private final String name;
        private static final EnumOrientation[] $VALUES;
        
        DOWN_Z("DOWN_Z", 7, 7, "down_z", EnumFacing.DOWN);
        
        private final EnumFacing facing;
        private static final EnumOrientation[] META_LOOKUP;
        
        UP_X("UP_X", 6, 6, "up_x", EnumFacing.UP), 
        SOUTH("SOUTH", 3, 3, "south", EnumFacing.SOUTH), 
        DOWN_X("DOWN_X", 0, 0, "down_x", EnumFacing.DOWN);
        
        private final int meta;
        
        NORTH("NORTH", 4, 4, "north", EnumFacing.NORTH);
        
        @Override
        public String toString() {
            return this.name;
        }
        
        public static EnumOrientation byMetadata(final int n) {
            if (0 >= EnumOrientation.META_LOOKUP.length) {}
            return EnumOrientation.META_LOOKUP[0];
        }
        
        public static EnumOrientation forFacings(final EnumFacing enumFacing, final EnumFacing enumFacing2) {
            switch (BlockLever$1.$SwitchMap$net$minecraft$util$EnumFacing[enumFacing.ordinal()]) {
                case 1: {
                    switch (BlockLever$1.$SwitchMap$net$minecraft$util$EnumFacing$Axis[enumFacing2.getAxis().ordinal()]) {
                        case 1: {
                            return EnumOrientation.DOWN_X;
                        }
                        case 2: {
                            return EnumOrientation.DOWN_Z;
                        }
                        default: {
                            throw new IllegalArgumentException("Invalid entityFacing " + enumFacing2 + " for facing " + enumFacing);
                        }
                    }
                    break;
                }
                case 2: {
                    switch (BlockLever$1.$SwitchMap$net$minecraft$util$EnumFacing$Axis[enumFacing2.getAxis().ordinal()]) {
                        case 1: {
                            return EnumOrientation.UP_X;
                        }
                        case 2: {
                            return EnumOrientation.UP_Z;
                        }
                        default: {
                            throw new IllegalArgumentException("Invalid entityFacing " + enumFacing2 + " for facing " + enumFacing);
                        }
                    }
                    break;
                }
                case 3: {
                    return EnumOrientation.NORTH;
                }
                case 4: {
                    return EnumOrientation.SOUTH;
                }
                case 5: {
                    return EnumOrientation.WEST;
                }
                case 6: {
                    return EnumOrientation.EAST;
                }
                default: {
                    throw new IllegalArgumentException("Invalid facing: " + enumFacing);
                }
            }
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        public int getMetadata() {
            return this.meta;
        }
        
        private EnumOrientation(final String s, final int n, final int meta, final String name, final EnumFacing facing) {
            this.meta = meta;
            this.name = name;
            this.facing = facing;
        }
        
        public EnumFacing getFacing() {
            return this.facing;
        }
        
        static {
            $VALUES = new EnumOrientation[] { EnumOrientation.DOWN_X, EnumOrientation.EAST, EnumOrientation.WEST, EnumOrientation.SOUTH, EnumOrientation.NORTH, EnumOrientation.UP_Z, EnumOrientation.UP_X, EnumOrientation.DOWN_Z };
            META_LOOKUP = new EnumOrientation[values().length];
            final EnumOrientation[] values = values();
            while (0 < values.length) {
                final EnumOrientation enumOrientation = values[0];
                EnumOrientation.META_LOOKUP[enumOrientation.getMetadata()] = enumOrientation;
                int n = 0;
                ++n;
            }
        }
    }
}
