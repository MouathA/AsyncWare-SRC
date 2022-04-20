package net.minecraft.util;

import net.minecraft.entity.*;

public class CombatEntry
{
    private final float damage;
    private final DamageSource damageSrc;
    private final float fallDistance;
    private final int field_94567_b;
    private final String field_94566_e;
    private final float health;
    
    public boolean isLivingDamageSrc() {
        return this.damageSrc.getEntity() instanceof EntityLivingBase;
    }
    
    public String func_94562_g() {
        return this.field_94566_e;
    }
    
    public CombatEntry(final DamageSource damageSrc, final int field_94567_b, final float health, final float damage, final String field_94566_e, final float fallDistance) {
        this.damageSrc = damageSrc;
        this.field_94567_b = field_94567_b;
        this.damage = damage;
        this.health = health;
        this.field_94566_e = field_94566_e;
        this.fallDistance = fallDistance;
    }
    
    public IChatComponent getDamageSrcDisplayName() {
        return (this.getDamageSrc().getEntity() == null) ? null : this.getDamageSrc().getEntity().getDisplayName();
    }
    
    public DamageSource getDamageSrc() {
        return this.damageSrc;
    }
    
    public float func_94563_c() {
        return this.damage;
    }
    
    public float getDamageAmount() {
        return (this.damageSrc == DamageSource.outOfWorld) ? Float.MAX_VALUE : this.fallDistance;
    }
}
