package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;

public abstract class BlockLog extends BlockRotatedPillar
{
    public static final PropertyEnum LOG_AXIS;
    
    static {
        LOG_AXIS = PropertyEnum.create("axis", EnumAxis.class);
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (world.isAreaLoaded(blockPos.add(-5, -5, -5), blockPos.add(5, 5, 5))) {
            for (final BlockPos blockPos2 : BlockPos.getAllInBox(blockPos.add(-4, -4, -4), blockPos.add(4, 4, 4))) {
                final IBlockState blockState2 = world.getBlockState(blockPos2);
                if (blockState2.getBlock().getMaterial() == Material.leaves && !(boolean)blockState2.getValue(BlockLeaves.CHECK_DECAY)) {
                    world.setBlockState(blockPos2, blockState2.withProperty(BlockLeaves.CHECK_DECAY, true), 4);
                }
            }
        }
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return super.onBlockPlaced(world, blockPos, enumFacing, n, n2, n3, n4, entityLivingBase).withProperty(BlockLog.LOG_AXIS, EnumAxis.fromFacingAxis(enumFacing.getAxis()));
    }
    
    public BlockLog() {
        super(Material.wood);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setHardness(2.0f);
        this.setStepSound(BlockLog.soundTypeWood);
    }
    
    public enum EnumAxis implements IStringSerializable
    {
        NONE("NONE", 3, "none"), 
        X("X", 0, "x");
        
        private final String name;
        
        Z("Z", 2, "z"), 
        Y("Y", 1, "y");
        
        private static final EnumAxis[] $VALUES;
        
        static {
            $VALUES = new EnumAxis[] { EnumAxis.X, EnumAxis.Y, EnumAxis.Z, EnumAxis.NONE };
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        private EnumAxis(final String s, final int n, final String name) {
            this.name = name;
        }
        
        public static EnumAxis fromFacingAxis(final EnumFacing.Axis axis) {
            switch (BlockLog$1.$SwitchMap$net$minecraft$util$EnumFacing$Axis[axis.ordinal()]) {
                case 1: {
                    return EnumAxis.X;
                }
                case 2: {
                    return EnumAxis.Y;
                }
                case 3: {
                    return EnumAxis.Z;
                }
                default: {
                    return EnumAxis.NONE;
                }
            }
        }
    }
}
