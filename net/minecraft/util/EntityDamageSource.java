package net.minecraft.util;

import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;

public class EntityDamageSource extends DamageSource
{
    protected Entity damageSourceEntity;
    private boolean isThornsDamage;
    
    public boolean getIsThornsDamage() {
        return this.isThornsDamage;
    }
    
    @Override
    public IChatComponent getDeathMessage(final EntityLivingBase entityLivingBase) {
        final ItemStack itemStack = (this.damageSourceEntity instanceof EntityLivingBase) ? ((EntityLivingBase)this.damageSourceEntity).getHeldItem() : null;
        final String string = "death.attack." + this.damageType;
        final String string2 = string + ".item";
        return (itemStack != null && itemStack.hasDisplayName() && StatCollector.canTranslate(string2)) ? new ChatComponentTranslation(string2, new Object[] { entityLivingBase.getDisplayName(), this.damageSourceEntity.getDisplayName(), itemStack.getChatComponent() }) : new ChatComponentTranslation(string, new Object[] { entityLivingBase.getDisplayName(), this.damageSourceEntity.getDisplayName() });
    }
    
    public EntityDamageSource(final String s, final Entity damageSourceEntity) {
        super(s);
        this.isThornsDamage = false;
        this.damageSourceEntity = damageSourceEntity;
    }
    
    @Override
    public boolean isDifficultyScaled() {
        return this.damageSourceEntity != null && this.damageSourceEntity instanceof EntityLivingBase && !(this.damageSourceEntity instanceof EntityPlayer);
    }
    
    public EntityDamageSource setIsThornsDamage() {
        this.isThornsDamage = true;
        return this;
    }
    
    @Override
    public Entity getEntity() {
        return this.damageSourceEntity;
    }
}
