package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;

public class BlockPressurePlate extends BlockBasePressurePlate
{
    private final Sensitivity sensitivity;
    public static final PropertyBool POWERED;
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockPressurePlate.POWERED, n == 1);
    }
    
    @Override
    protected int computeRedstoneStrength(final World world, final BlockPos blockPos) {
        final AxisAlignedBB sensitiveAABB = this.getSensitiveAABB(blockPos);
        List list = null;
        switch (BlockPressurePlate$1.$SwitchMap$net$minecraft$block$BlockPressurePlate$Sensitivity[this.sensitivity.ordinal()]) {
            case 1: {
                list = world.getEntitiesWithinAABBExcludingEntity(null, sensitiveAABB);
                break;
            }
            case 2: {
                list = world.getEntitiesWithinAABB(EntityLivingBase.class, sensitiveAABB);
                break;
            }
            default: {
                return 0;
            }
        }
        if (!list.isEmpty()) {
            final Iterator<Entity> iterator = list.iterator();
            while (iterator.hasNext()) {
                if (!iterator.next().doesEntityNotTriggerPressurePlate()) {
                    return 15;
                }
            }
        }
        return 0;
    }
    
    static {
        POWERED = PropertyBool.create("powered");
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockPressurePlate.POWERED });
    }
    
    @Override
    protected int getRedstoneStrength(final IBlockState blockState) {
        return blockState.getValue(BlockPressurePlate.POWERED) ? 15 : 0;
    }
    
    protected BlockPressurePlate(final Material material, final Sensitivity sensitivity) {
        super(material);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockPressurePlate.POWERED, false));
        this.sensitivity = sensitivity;
    }
    
    @Override
    protected IBlockState setRedstoneStrength(final IBlockState blockState, final int n) {
        return blockState.withProperty(BlockPressurePlate.POWERED, n > 0);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return ((boolean)blockState.getValue(BlockPressurePlate.POWERED)) ? 1 : 0;
    }
    
    public enum Sensitivity
    {
        MOBS("MOBS", 1), 
        EVERYTHING("EVERYTHING", 0);
        
        private static final Sensitivity[] $VALUES;
        
        static {
            $VALUES = new Sensitivity[] { Sensitivity.EVERYTHING, Sensitivity.MOBS };
        }
        
        private Sensitivity(final String s, final int n) {
        }
    }
}
