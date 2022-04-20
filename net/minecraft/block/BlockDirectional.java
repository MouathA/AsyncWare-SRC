package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import com.google.common.base.*;
import net.minecraft.block.material.*;

public abstract class BlockDirectional extends Block
{
    public static final PropertyDirection FACING;
    
    protected BlockDirectional(final Material material) {
        super(material);
    }
    
    static {
        FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
    }
    
    protected BlockDirectional(final Material material, final MapColor mapColor) {
        super(material, mapColor);
    }
}
