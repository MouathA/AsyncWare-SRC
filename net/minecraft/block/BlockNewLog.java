package net.minecraft.block;

import net.minecraft.block.properties.*;
import com.google.common.base.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import java.util.*;

public class BlockNewLog extends BlockLog
{
    public static final PropertyEnum VARIANT;
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        final int n = 0x0 | ((BlockPlanks.EnumType)blockState.getValue(BlockNewLog.VARIANT)).getMetadata() - 4;
        switch (BlockNewLog$2.$SwitchMap$net$minecraft$block$BlockLog$EnumAxis[((EnumAxis)blockState.getValue(BlockNewLog.LOG_AXIS)).ordinal()]) {
            case 1: {}
        }
        return 0;
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return ((BlockPlanks.EnumType)blockState.getValue(BlockNewLog.VARIANT)).getMetadata() - 4;
    }
    
    public BlockNewLog() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.ACACIA).withProperty(BlockNewLog.LOG_AXIS, EnumAxis.Y));
    }
    
    static {
        VARIANT = PropertyEnum.create("variant", BlockPlanks.EnumType.class, (Predicate)new Predicate() {
            public boolean apply(final BlockPlanks.EnumType enumType) {
                return enumType.getMetadata() >= 4;
            }
            
            public boolean apply(final Object o) {
                return this.apply((BlockPlanks.EnumType)o);
            }
        });
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState withProperty = this.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.byMetadata((n & 0x3) + 4));
        IBlockState blockState = null;
        switch (n & 0xC) {
            case 0: {
                blockState = withProperty.withProperty(BlockNewLog.LOG_AXIS, EnumAxis.Y);
                break;
            }
            case 4: {
                blockState = withProperty.withProperty(BlockNewLog.LOG_AXIS, EnumAxis.X);
                break;
            }
            case 8: {
                blockState = withProperty.withProperty(BlockNewLog.LOG_AXIS, EnumAxis.Z);
                break;
            }
            default: {
                blockState = withProperty.withProperty(BlockNewLog.LOG_AXIS, EnumAxis.NONE);
                break;
            }
        }
        return blockState;
    }
    
    @Override
    protected ItemStack createStackedBlock(final IBlockState blockState) {
        return new ItemStack(Item.getItemFromBlock(this), 1, ((BlockPlanks.EnumType)blockState.getValue(BlockNewLog.VARIANT)).getMetadata() - 4);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockNewLog.VARIANT, BlockNewLog.LOG_AXIS });
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        final BlockPlanks.EnumType enumType = (BlockPlanks.EnumType)blockState.getValue(BlockNewLog.VARIANT);
        switch ((EnumAxis)blockState.getValue(BlockNewLog.LOG_AXIS)) {
            default: {
                switch (enumType) {
                    default: {
                        return MapColor.stoneColor;
                    }
                    case DARK_OAK: {
                        return BlockPlanks.EnumType.DARK_OAK.func_181070_c();
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
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        list.add(new ItemStack(item, 1, BlockPlanks.EnumType.ACACIA.getMetadata() - 4));
        list.add(new ItemStack(item, 1, BlockPlanks.EnumType.DARK_OAK.getMetadata() - 4));
    }
}
