package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;

public class EntityBlockDustFX extends EntityDiggingFX
{
    protected EntityBlockDustFX(final World world, final double n, final double n2, final double n3, final double motionX, final double motionY, final double motionZ, final IBlockState blockState) {
        super(world, n, n2, n3, motionX, motionY, motionZ, blockState);
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
    }
    
    public static class Factory implements IParticleFactory
    {
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            final IBlockState stateById = Block.getStateById(array[0]);
            return (stateById.getBlock().getRenderType() == -1) ? null : new EntityBlockDustFX(world, n2, n3, n4, n5, n6, n7, stateById).func_174845_l();
        }
    }
}
