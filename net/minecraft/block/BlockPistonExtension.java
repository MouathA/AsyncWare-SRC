package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

public class BlockPistonExtension extends Block
{
    public static final PropertyDirection FACING;
    public static final PropertyBool SHORT;
    public static final PropertyEnum TYPE;
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.applyHeadBounds(blockAccess.getBlockState(blockPos));
    }
    
    private void applyCoreBounds(final IBlockState blockState) {
        switch (BlockPistonExtension$1.$SwitchMap$net$minecraft$util$EnumFacing[((EnumFacing)blockState.getValue(BlockPistonExtension.FACING)).ordinal()]) {
            case 1: {
                this.setBlockBounds(0.375f, 0.25f, 0.375f, 0.625f, 1.0f, 0.625f);
                break;
            }
            case 2: {
                this.setBlockBounds(0.375f, 0.0f, 0.375f, 0.625f, 0.75f, 0.625f);
                break;
            }
            case 3: {
                this.setBlockBounds(0.25f, 0.375f, 0.25f, 0.75f, 0.625f, 1.0f);
                break;
            }
            case 4: {
                this.setBlockBounds(0.25f, 0.375f, 0.0f, 0.75f, 0.625f, 0.75f);
                break;
            }
            case 5: {
                this.setBlockBounds(0.375f, 0.25f, 0.25f, 0.625f, 0.75f, 1.0f);
                break;
            }
            case 6: {
                this.setBlockBounds(0.0f, 0.375f, 0.25f, 0.75f, 0.625f, 0.75f);
                break;
            }
        }
    }
    
    @Override
    public boolean canPlaceBlockOnSide(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        return false;
    }
    
    public BlockPistonExtension() {
        super(Material.piston);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockPistonExtension.FACING, EnumFacing.NORTH).withProperty(BlockPistonExtension.TYPE, EnumPistonType.DEFAULT).withProperty(BlockPistonExtension.SHORT, false));
        this.setStepSound(BlockPistonExtension.soundTypePiston);
        this.setHardness(0.5f);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        final int n = 0x0 | ((EnumFacing)blockState.getValue(BlockPistonExtension.FACING)).getIndex();
        if (blockState.getValue(BlockPistonExtension.TYPE) == EnumPistonType.STICKY) {}
        return 0;
    }
    
    @Override
    public void addCollisionBoxesToList(final World world, final BlockPos blockPos, final IBlockState blockState, final AxisAlignedBB axisAlignedBB, final List list, final Entity entity) {
        this.applyHeadBounds(blockState);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        this.applyCoreBounds(blockState);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return true;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return (world.getBlockState(blockPos).getValue(BlockPistonExtension.TYPE) == EnumPistonType.STICKY) ? Item.getItemFromBlock(Blocks.sticky_piston) : Item.getItemFromBlock(Blocks.piston);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockPistonExtension.FACING, getFacing(n)).withProperty(BlockPistonExtension.TYPE, ((n & 0x8) > 0) ? EnumPistonType.STICKY : EnumPistonType.DEFAULT);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        final BlockPos offset = blockToAir.offset(((EnumFacing)blockState.getValue(BlockPistonExtension.FACING)).getOpposite());
        final IBlockState blockState2 = world.getBlockState(offset);
        if (blockState2.getBlock() != Blocks.piston && blockState2.getBlock() != Blocks.sticky_piston) {
            world.setBlockToAir(blockToAir);
        }
        else {
            blockState2.getBlock().onNeighborBlockChange(world, offset, blockState2, block);
        }
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    public void applyHeadBounds(final IBlockState blockState) {
        final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockPistonExtension.FACING);
        if (enumFacing != null) {
            switch (BlockPistonExtension$1.$SwitchMap$net$minecraft$util$EnumFacing[enumFacing.ordinal()]) {
                case 1: {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.25f, 1.0f);
                    break;
                }
                case 2: {
                    this.setBlockBounds(0.0f, 0.75f, 0.0f, 1.0f, 1.0f, 1.0f);
                    break;
                }
                case 3: {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.25f);
                    break;
                }
                case 4: {
                    this.setBlockBounds(0.0f, 0.0f, 0.75f, 1.0f, 1.0f, 1.0f);
                    break;
                }
                case 5: {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 0.25f, 1.0f, 1.0f);
                    break;
                }
                case 6: {
                    this.setBlockBounds(0.75f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                    break;
                }
            }
        }
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 0;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return false;
    }
    
    @Override
    public void breakBlock(final World world, BlockPos offset, final IBlockState blockState) {
        super.breakBlock(world, offset, blockState);
        offset = offset.offset(((EnumFacing)blockState.getValue(BlockPistonExtension.FACING)).getOpposite());
        final IBlockState blockState2 = world.getBlockState(offset);
        if ((blockState2.getBlock() == Blocks.piston || blockState2.getBlock() == Blocks.sticky_piston) && (boolean)blockState2.getValue(BlockPistonBase.EXTENDED)) {
            blockState2.getBlock().dropBlockAsItem(world, offset, blockState2, 0);
            world.setBlockToAir(offset);
        }
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockPistonExtension.FACING, BlockPistonExtension.TYPE, BlockPistonExtension.SHORT });
    }
    
    static {
        FACING = PropertyDirection.create("facing");
        TYPE = PropertyEnum.create("type", EnumPistonType.class);
        SHORT = PropertyBool.create("short");
    }
    
    @Override
    public void onBlockHarvested(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer) {
        if (entityPlayer.capabilities.isCreativeMode) {
            final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockPistonExtension.FACING);
            if (enumFacing != null) {
                final BlockPos offset = blockPos.offset(enumFacing.getOpposite());
                final Block block = world.getBlockState(offset).getBlock();
                if (block == Blocks.piston || block == Blocks.sticky_piston) {
                    world.setBlockToAir(offset);
                }
            }
        }
        super.onBlockHarvested(world, blockPos, blockState, entityPlayer);
    }
    
    public static EnumFacing getFacing(final int n) {
        final int n2 = n & 0x7;
        return (n2 > 5) ? null : EnumFacing.getFront(n2);
    }
    
    public enum EnumPistonType implements IStringSerializable
    {
        private static final EnumPistonType[] $VALUES;
        private final String VARIANT;
        
        STICKY("STICKY", 1, "sticky"), 
        DEFAULT("DEFAULT", 0, "normal");
        
        @Override
        public String getName() {
            return this.VARIANT;
        }
        
        private EnumPistonType(final String s, final int n, final String variant) {
            this.VARIANT = variant;
        }
        
        @Override
        public String toString() {
            return this.VARIANT;
        }
        
        static {
            $VALUES = new EnumPistonType[] { EnumPistonType.DEFAULT, EnumPistonType.STICKY };
        }
    }
}
