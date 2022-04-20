package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntityHugeExplodeFX extends EntityFX
{
    private int timeSinceStart;
    private int maximumTime;
    
    protected EntityHugeExplodeFX(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        super(world, n, n2, n3, 0.0, 0.0, 0.0);
        this.maximumTime = 8;
    }
    
    @Override
    public void renderParticle(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
    }
    
    @Override
    public void onUpdate() {
        while (true) {
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0, this.posY + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0, this.posZ + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0, this.timeSinceStart / (float)this.maximumTime, 0.0, 0.0, new int[0]);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public int getFXLayer() {
        return 1;
    }
    
    public static class Factory implements IParticleFactory
    {
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityHugeExplodeFX(world, n2, n3, n4, n5, n6, n7);
        }
    }
}
