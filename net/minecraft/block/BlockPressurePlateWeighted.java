package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class BlockPressurePlateWeighted extends BlockBasePressurePlate
{
    public static final PropertyInteger POWER;
    private final int field_150068_a;
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockPressurePlateWeighted.POWER });
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return (int)blockState.getValue(BlockPressurePlateWeighted.POWER);
    }
    
    static {
        POWER = PropertyInteger.create("power", 0, 15);
    }
    
    @Override
    public int tickRate(final World world) {
        return 10;
    }
    
    @Override
    protected IBlockState setRedstoneStrength(final IBlockState blockState, final int n) {
        return blockState.withProperty(BlockPressurePlateWeighted.POWER, n);
    }
    
    protected BlockPressurePlateWeighted(final Material material, final int field_150068_a, final MapColor mapColor) {
        super(material, mapColor);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockPressurePlateWeighted.POWER, 0));
        this.field_150068_a = field_150068_a;
    }
    
    @Override
    protected int getRedstoneStrength(final IBlockState blockState) {
        return (int)blockState.getValue(BlockPressurePlateWeighted.POWER);
    }
    
    protected BlockPressurePlateWeighted(final Material material, final int n) {
        this(material, n, material.getMaterialMapColor());
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockPressurePlateWeighted.POWER, n);
    }
    
    @Override
    protected int computeRedstoneStrength(final World world, final BlockPos blockPos) {
        final int min = Math.min(world.getEntitiesWithinAABB(Entity.class, this.getSensitiveAABB(blockPos)).size(), this.field_150068_a);
        if (min > 0) {
            return MathHelper.ceiling_float_int(Math.min(this.field_150068_a, min) / (float)this.field_150068_a * 15.0f);
        }
        return 0;
    }
}
