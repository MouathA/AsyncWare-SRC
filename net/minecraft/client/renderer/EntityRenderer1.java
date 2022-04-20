package net.minecraft.client.renderer;

import com.google.common.base.*;
import net.minecraft.entity.*;

class EntityRenderer1 implements Predicate
{
    final EntityRenderer field_90032_a;
    
    public boolean apply(final Entity entity) {
        return entity.canBeCollidedWith();
    }
    
    EntityRenderer1(final EntityRenderer field_90032_a) {
        this.field_90032_a = field_90032_a;
    }
    
    public boolean apply(final Object o) {
        return this.apply((Entity)o);
    }
}
