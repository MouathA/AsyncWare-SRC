package net.minecraft.client.particle;

import net.minecraft.world.*;

public class EntityCritFX extends EntitySmokeFX
{
    protected EntityCritFX(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        super(world, n, n2, n3, n4, n5, n6, 2.5f);
    }
    
    public static class Factory implements IParticleFactory
    {
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityCritFX(world, n2, n3, n4, n5, n6, n7);
        }
    }
}
