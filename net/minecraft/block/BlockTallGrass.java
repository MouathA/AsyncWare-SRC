package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.stats.*;
import net.minecraft.init.*;
import net.minecraft.util.*;

public class BlockTallGrass extends BlockBush implements IGrowable
{
    public static final PropertyEnum TYPE;
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockTallGrass.TYPE });
    }
    
    @Override
    public boolean isReplaceable(final World world, final BlockPos blockPos) {
        return true;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return (random.nextInt(8) == 0) ? Items.wheat_seeds : null;
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        while (true) {
            list.add(new ItemStack(item, 1, 1));
            int n = 0;
            ++n;
        }
    }
    
    static {
        TYPE = PropertyEnum.create("type", EnumType.class);
    }
    
    @Override
    public EnumOffsetType getOffsetType() {
        return EnumOffsetType.XYZ;
    }
    
    @Override
    public int getRenderColor(final IBlockState blockState) {
        if (blockState.getBlock() != this) {
            return super.getRenderColor(blockState);
        }
        return (blockState.getValue(BlockTallGrass.TYPE) == EnumType.DEAD_BUSH) ? 16777215 : ColorizerGrass.getGrassColor(0.5, 1.0);
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        return blockAccess.getBiomeGenForCoords(blockPos).getGrassColorAtPos(blockPos);
    }
    
    protected BlockTallGrass() {
        super(Material.vine);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockTallGrass.TYPE, EnumType.DEAD_BUSH));
        final float n = 0.4f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, 0.8f, 0.5f + n);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((EnumType)blockState.getValue(BlockTallGrass.TYPE)).getMeta();
    }
    
    @Override
    public void harvestBlock(final World world, final EntityPlayer entityPlayer, final BlockPos blockPos, final IBlockState blockState, final TileEntity tileEntity) {
        if (!world.isRemote && entityPlayer.getCurrentEquippedItem() != null && entityPlayer.getCurrentEquippedItem().getItem() == Items.shears) {
            entityPlayer.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
            Block.spawnAsEntity(world, blockPos, new ItemStack(Blocks.tallgrass, 1, ((EnumType)blockState.getValue(BlockTallGrass.TYPE)).getMeta()));
        }
        else {
            super.harvestBlock(world, entityPlayer, blockPos, blockState, tileEntity);
        }
    }
    
    @Override
    public boolean canUseBonemeal(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        return true;
    }
    
    @Override
    public int getDamageValue(final World world, final BlockPos blockPos) {
        final IBlockState blockState = world.getBlockState(blockPos);
        return blockState.getBlock().getMetaFromState(blockState);
    }
    
    @Override
    public boolean canGrow(final World world, final BlockPos blockPos, final IBlockState blockState, final boolean b) {
        return blockState.getValue(BlockTallGrass.TYPE) != EnumType.DEAD_BUSH;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockTallGrass.TYPE, EnumType.byMetadata(n));
    }
    
    @Override
    public void grow(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        BlockDoublePlant.EnumPlantType enumPlantType = BlockDoublePlant.EnumPlantType.GRASS;
        if (blockState.getValue(BlockTallGrass.TYPE) == EnumType.FERN) {
            enumPlantType = BlockDoublePlant.EnumPlantType.FERN;
        }
        if (Blocks.double_plant.canPlaceBlockAt(world, blockPos)) {
            Blocks.double_plant.placeAt(world, blockPos, enumPlantType, 2);
        }
    }
    
    @Override
    public boolean canBlockStay(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return this.canPlaceBlockOn(world.getBlockState(blockPos.down()).getBlock());
    }
    
    @Override
    public int quantityDroppedWithBonus(final int n, final Random random) {
        return 1 + random.nextInt(n * 2 + 1);
    }
    
    @Override
    public int getBlockColor() {
        return ColorizerGrass.getGrassColor(0.5, 1.0);
    }
    
    public enum EnumType implements IStringSerializable
    {
        private final String name;
        
        GRASS("GRASS", 1, 1, "tall_grass");
        
        private final int meta;
        
        DEAD_BUSH("DEAD_BUSH", 0, 0, "dead_bush");
        
        private static final EnumType[] META_LOOKUP;
        
        FERN("FERN", 2, 2, "fern");
        
        private static final EnumType[] $VALUES;
        
        public int getMeta() {
            return this.meta;
        }
        
        public static EnumType byMetadata(final int n) {
            if (0 >= EnumType.META_LOOKUP.length) {}
            return EnumType.META_LOOKUP[0];
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        static {
            $VALUES = new EnumType[] { EnumType.DEAD_BUSH, EnumType.GRASS, EnumType.FERN };
            META_LOOKUP = new EnumType[values().length];
            final EnumType[] values = values();
            while (0 < values.length) {
                final EnumType enumType = values[0];
                EnumType.META_LOOKUP[enumType.getMeta()] = enumType;
                int n = 0;
                ++n;
            }
        }
        
        private EnumType(final String s, final int n, final int meta, final String name) {
            this.meta = meta;
            this.name = name;
        }
    }
}
