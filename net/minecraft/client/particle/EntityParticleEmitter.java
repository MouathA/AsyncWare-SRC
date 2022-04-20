package net.minecraft.client.particle;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;

public class EntityParticleEmitter extends EntityFX
{
    private Entity attachedEntity;
    private int age;
    private EnumParticleTypes particleTypes;
    private int lifetime;
    
    @Override
    public void onUpdate() {
        while (true) {
            final double n = this.rand.nextFloat() * 2.0f - 1.0f;
            final double n2 = this.rand.nextFloat() * 2.0f - 1.0f;
            final double n3 = this.rand.nextFloat() * 2.0f - 1.0f;
            if (n * n + n2 * n2 + n3 * n3 <= 1.0) {
                this.worldObj.spawnParticle(this.particleTypes, false, this.attachedEntity.posX + n * this.attachedEntity.width / 4.0, this.attachedEntity.getEntityBoundingBox().minY + this.attachedEntity.height / 2.0f + n2 * this.attachedEntity.height / 4.0, this.attachedEntity.posZ + n3 * this.attachedEntity.width / 4.0, n, n2 + 0.2, n3, new int[0]);
            }
            int n4 = 0;
            ++n4;
        }
    }
    
    public EntityParticleEmitter(final World world, final Entity attachedEntity, final EnumParticleTypes particleTypes) {
        super(world, attachedEntity.posX, attachedEntity.getEntityBoundingBox().minY + attachedEntity.height / 2.0f, attachedEntity.posZ, attachedEntity.motionX, attachedEntity.motionY, attachedEntity.motionZ);
        this.attachedEntity = attachedEntity;
        this.lifetime = 3;
        this.particleTypes = particleTypes;
        this.onUpdate();
    }
    
    @Override
    public int getFXLayer() {
        return 3;
    }
    
    @Override
    public void renderParticle(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
    }
}
