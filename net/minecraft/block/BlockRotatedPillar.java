package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;

public abstract class BlockRotatedPillar extends Block
{
    public static final PropertyEnum AXIS;
    
    protected BlockRotatedPillar(final Material material) {
        super(material, material.getMaterialMapColor());
    }
    
    protected BlockRotatedPillar(final Material material, final MapColor mapColor) {
        super(material, mapColor);
    }
    
    static {
        AXIS = PropertyEnum.create("axis", EnumFacing.Axis.class);
    }
}
