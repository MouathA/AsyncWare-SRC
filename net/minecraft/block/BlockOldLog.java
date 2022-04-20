package net.minecraft.block;

import net.minecraft.block.properties.*;
import com.google.common.base.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.creativetab.*;
import java.util.*;

public class BlockOldLog extends BlockLog
{
    public static final PropertyEnum VARIANT;
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        final int n = 0x0 | ((BlockPlanks.EnumType)blockState.getValue(BlockOldLog.VARIANT)).getMetadata();
        switch (BlockOldLog$2.$SwitchMap$net$minecraft$block$BlockLog$EnumAxis[((EnumAxis)blockState.getValue(BlockOldLog.LOG_AXIS)).ordinal()]) {
            case 1: {}
        }
        return 0;
    }
    
    static {
        VARIANT = PropertyEnum.create("variant", BlockPlanks.EnumType.class, (Predicate)new Predicate() {
            public boolean apply(final Object o) {
                return this.apply((BlockPlanks.EnumType)o);
            }
            
            public boolean apply(final BlockPlanks.EnumType enumType) {
                return enumType.getMetadata() < 4;
            }
        });
    }
    
    public BlockOldLog() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.OAK).withProperty(BlockOldLog.LOG_AXIS, EnumAxis.Y));
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        final BlockPlanks.EnumType enumType = (BlockPlanks.EnumType)blockState.getValue(BlockOldLog.VARIANT);
        switch ((EnumAxis)blockState.getValue(BlockOldLog.LOG_AXIS)) {
            default: {
                switch (enumType) {
                    default: {
                        return BlockPlanks.EnumType.SPRUCE.func_181070_c();
                    }
                    case SPRUCE: {
                        return BlockPlanks.EnumType.DARK_OAK.func_181070_c();
                    }
                    case BIRCH: {
                        return MapColor.quartzColor;
                    }
                    case JUNGLE: {
                        return BlockPlanks.EnumType.SPRUCE.func_181070_c();
                    }
                }
                break;
            }
            case Y: {
                return enumType.func_181070_c();
            }
        }
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockOldLog.VARIANT, BlockOldLog.LOG_AXIS });
    }
    
    @Override
    protected ItemStack createStackedBlock(final IBlockState blockState) {
        return new ItemStack(Item.getItemFromBlock(this), 1, ((BlockPlanks.EnumType)blockState.getValue(BlockOldLog.VARIANT)).getMetadata());
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState withProperty = this.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.byMetadata((n & 0x3) % 4));
        IBlockState blockState = null;
        switch (n & 0xC) {
            case 0: {
                blockState = withProperty.withProperty(BlockOldLog.LOG_AXIS, EnumAxis.Y);
                break;
            }
            case 4: {
                blockState = withProperty.withProperty(BlockOldLog.LOG_AXIS, EnumAxis.X);
                break;
            }
            case 8: {
                blockState = withProperty.withProperty(BlockOldLog.LOG_AXIS, EnumAxis.Z);
                break;
            }
            default: {
                blockState = withProperty.withProperty(BlockOldLog.LOG_AXIS, EnumAxis.NONE);
                break;
            }
        }
        return blockState;
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((BlockPlanks.EnumType)blockState.getValue(BlockOldLog.VARIANT)).getMetadata();
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        list.add(new ItemStack(item, 1, BlockPlanks.EnumType.OAK.getMetadata()));
        list.add(new ItemStack(item, 1, BlockPlanks.EnumType.SPRUCE.getMetadata()));
        list.add(new ItemStack(item, 1, BlockPlanks.EnumType.BIRCH.getMetadata()));
        list.add(new ItemStack(item, 1, BlockPlanks.EnumType.JUNGLE.getMetadata()));
    }
}
