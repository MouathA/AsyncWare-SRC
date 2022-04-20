package net.minecraft.block;

import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockWall extends Block
{
    public static final PropertyBool WEST;
    public static final PropertyBool UP;
    public static final PropertyBool EAST;
    public static final PropertyEnum VARIANT;
    public static final PropertyBool SOUTH;
    public static final PropertyBool NORTH;
    
    public boolean canConnectTo(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final Block block = blockAccess.getBlockState(blockPos).getBlock();
        return block != Blocks.barrier && (block == this || block instanceof BlockFenceGate || (block.blockMaterial.isOpaque() && block.isFullCube() && block.blockMaterial != Material.gourd));
    }
    
    public BlockWall(final Block block) {
        super(block.blockMaterial);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockWall.UP, false).withProperty(BlockWall.NORTH, false).withProperty(BlockWall.EAST, false).withProperty(BlockWall.SOUTH, false).withProperty(BlockWall.WEST, false).withProperty(BlockWall.VARIANT, EnumType.NORMAL));
        this.setHardness(block.blockHardness);
        this.setResistance(block.blockResistance / 3.0f);
        this.setStepSound(block.stepSound);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        final EnumType[] values = EnumType.values();
        while (0 < values.length) {
            list.add(new ItemStack(item, 1, values[0].getMetadata()));
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockWall.VARIANT)).getMetadata();
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        this.maxY = 1.5;
        return super.getCollisionBoundingBox(world, blockPos, blockState);
    }
    
    @Override
    public IBlockState getActualState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        return blockState.withProperty(BlockWall.UP, !blockAccess.isAirBlock(blockPos.up())).withProperty(BlockWall.NORTH, this.canConnectTo(blockAccess, blockPos.north())).withProperty(BlockWall.EAST, this.canConnectTo(blockAccess, blockPos.east())).withProperty(BlockWall.SOUTH, this.canConnectTo(blockAccess, blockPos.south())).withProperty(BlockWall.WEST, this.canConnectTo(blockAccess, blockPos.west()));
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockWall.VARIANT, EnumType.byMetadata(n));
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockWall.VARIANT)).getMetadata();
    }
    
    static {
        UP = PropertyBool.create("up");
        NORTH = PropertyBool.create("north");
        EAST = PropertyBool.create("east");
        SOUTH = PropertyBool.create("south");
        WEST = PropertyBool.create("west");
        VARIANT = PropertyEnum.create("variant", EnumType.class);
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return enumFacing != EnumFacing.DOWN || super.shouldSideBeRendered(blockAccess, blockPos, enumFacing);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final boolean canConnectTo = this.canConnectTo(blockAccess, blockPos.north());
        final boolean canConnectTo2 = this.canConnectTo(blockAccess, blockPos.south());
        final boolean canConnectTo3 = this.canConnectTo(blockAccess, blockPos.west());
        final boolean canConnectTo4 = this.canConnectTo(blockAccess, blockPos.east());
        float n = 0.25f;
        float n2 = 0.75f;
        float n3 = 0.25f;
        float n4 = 0.75f;
        float n5 = 1.0f;
        if (canConnectTo) {
            n3 = 0.0f;
        }
        if (canConnectTo2) {
            n4 = 1.0f;
        }
        if (canConnectTo3) {
            n = 0.0f;
        }
        if (canConnectTo4) {
            n2 = 1.0f;
        }
        if (canConnectTo && canConnectTo2 && !canConnectTo3 && !canConnectTo4) {
            n5 = 0.8125f;
            n = 0.3125f;
            n2 = 0.6875f;
        }
        else if (!canConnectTo && !canConnectTo2 && canConnectTo3 && canConnectTo4) {
            n5 = 0.8125f;
            n3 = 0.3125f;
            n4 = 0.6875f;
        }
        this.setBlockBounds(n, 0.0f, n3, n2, n5, n4);
    }
    
    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal(this.getUnlocalizedName() + "." + EnumType.NORMAL.getUnlocalizedName() + ".name");
    }
    
    @Override
    public boolean isPassable(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return false;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockWall.UP, BlockWall.NORTH, BlockWall.EAST, BlockWall.WEST, BlockWall.SOUTH, BlockWall.VARIANT });
    }
    
    public enum EnumType implements IStringSerializable
    {
        private final String name;
        private static final EnumType[] $VALUES;
        private String unlocalizedName;
        private final int meta;
        private static final EnumType[] META_LOOKUP;
        
        MOSSY("MOSSY", 1, 1, "mossy_cobblestone", "mossy"), 
        NORMAL("NORMAL", 0, 0, "cobblestone", "normal");
        
        @Override
        public String toString() {
            return this.name;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        private EnumType(final String s, final int n, final int meta, final String name, final String unlocalizedName) {
            this.meta = meta;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
        }
        
        public static EnumType byMetadata(final int n) {
            if (0 >= EnumType.META_LOOKUP.length) {}
            return EnumType.META_LOOKUP[0];
        }
        
        static {
            $VALUES = new EnumType[] { EnumType.NORMAL, EnumType.MOSSY };
            META_LOOKUP = new EnumType[values().length];
            final EnumType[] values = values();
            while (0 < values.length) {
                final EnumType enumType = values[0];
                EnumType.META_LOOKUP[enumType.getMetadata()] = enumType;
                int n = 0;
                ++n;
            }
        }
        
        public int getMetadata() {
            return this.meta;
        }
        
        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }
    }
}
