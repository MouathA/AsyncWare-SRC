package net.minecraft.util;

import com.google.common.base.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;

public final class EntitySelectors
{
    public static final Predicate selectAnything;
    public static final Predicate IS_STANDALONE;
    public static final Predicate selectInventories;
    public static final Predicate NOT_SPECTATING;
    
    static {
        selectAnything = (Predicate)new Predicate() {
            public boolean apply(final Entity entity) {
                return entity.isEntityAlive();
            }
            
            public boolean apply(final Object o) {
                return this.apply((Entity)o);
            }
        };
        IS_STANDALONE = (Predicate)new Predicate() {
            public boolean apply(final Object o) {
                return this.apply((Entity)o);
            }
            
            public boolean apply(final Entity entity) {
                return entity.isEntityAlive() && entity.riddenByEntity == null && entity.ridingEntity == null;
            }
        };
        selectInventories = (Predicate)new Predicate() {
            public boolean apply(final Entity entity) {
                return entity instanceof IInventory && entity.isEntityAlive();
            }
            
            public boolean apply(final Object o) {
                return this.apply((Entity)o);
            }
        };
        NOT_SPECTATING = (Predicate)new Predicate() {
            public boolean apply(final Entity entity) {
                return !(entity instanceof EntityPlayer) || !((EntityPlayer)entity).isSpectator();
            }
            
            public boolean apply(final Object o) {
                return this.apply((Entity)o);
            }
        };
    }
    
    public static class ArmoredMob implements Predicate
    {
        private final ItemStack armor;
        
        public ArmoredMob(final ItemStack armor) {
            this.armor = armor;
        }
        
        public boolean apply(final Entity entity) {
            if (!entity.isEntityAlive()) {
                return false;
            }
            if (!(entity instanceof EntityLivingBase)) {
                return false;
            }
            final EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            return entityLivingBase.getEquipmentInSlot(EntityLiving.getArmorPosition(this.armor)) == null && ((entityLivingBase instanceof EntityLiving) ? ((EntityLiving)entityLivingBase).canPickUpLoot() : (entityLivingBase instanceof EntityArmorStand || entityLivingBase instanceof EntityPlayer));
        }
        
        public boolean apply(final Object o) {
            return this.apply((Entity)o);
        }
    }
}
