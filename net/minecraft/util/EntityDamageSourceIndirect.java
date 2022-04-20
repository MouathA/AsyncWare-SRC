package net.minecraft.util;

import net.minecraft.entity.*;
import net.minecraft.item.*;

public class EntityDamageSourceIndirect extends EntityDamageSource
{
    private Entity indirectEntity;
    
    @Override
    public IChatComponent getDeathMessage(final EntityLivingBase entityLivingBase) {
        final IChatComponent chatComponent = (this.indirectEntity == null) ? this.damageSourceEntity.getDisplayName() : this.indirectEntity.getDisplayName();
        final ItemStack itemStack = (this.indirectEntity instanceof EntityLivingBase) ? ((EntityLivingBase)this.indirectEntity).getHeldItem() : null;
        final String string = "death.attack." + this.damageType;
        final String string2 = string + ".item";
        return (itemStack != null && itemStack.hasDisplayName() && StatCollector.canTranslate(string2)) ? new ChatComponentTranslation(string2, new Object[] { entityLivingBase.getDisplayName(), chatComponent, itemStack.getChatComponent() }) : new ChatComponentTranslation(string, new Object[] { entityLivingBase.getDisplayName(), chatComponent });
    }
    
    @Override
    public Entity getSourceOfDamage() {
        return this.damageSourceEntity;
    }
    
    public EntityDamageSourceIndirect(final String s, final Entity entity, final Entity indirectEntity) {
        super(s, entity);
        this.indirectEntity = indirectEntity;
    }
    
    @Override
    public Entity getEntity() {
        return this.indirectEntity;
    }
}
