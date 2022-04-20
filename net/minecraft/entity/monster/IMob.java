package net.minecraft.entity.monster;

import net.minecraft.entity.passive.*;
import com.google.common.base.*;
import net.minecraft.entity.*;

public interface IMob extends IAnimals
{
    public static final Predicate mobSelector = new Predicate() {
        public boolean apply(final Object o) {
            return this.apply((Entity)o);
        }
        
        public boolean apply(final Entity entity) {
            return entity instanceof IMob;
        }
    };
    public static final Predicate VISIBLE_MOB_SELECTOR = new Predicate() {
        public boolean apply(final Entity entity) {
            return entity instanceof IMob && !entity.isInvisible();
        }
        
        public boolean apply(final Object o) {
            return this.apply((Entity)o);
        }
    };
}
