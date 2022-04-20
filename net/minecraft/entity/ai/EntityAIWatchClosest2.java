package net.minecraft.entity.ai;

import net.minecraft.entity.*;

public class EntityAIWatchClosest2 extends EntityAIWatchClosest
{
    public EntityAIWatchClosest2(final EntityLiving entityLiving, final Class clazz, final float n, final float n2) {
        super(entityLiving, clazz, n, n2);
        this.setMutexBits(3);
    }
}
