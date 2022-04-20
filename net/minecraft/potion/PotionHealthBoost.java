package net.minecraft.potion;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.util.*;

public class PotionHealthBoost extends Potion
{
    @Override
    public void removeAttributesModifiersFromEntity(final EntityLivingBase entityLivingBase, final BaseAttributeMap baseAttributeMap, final int n) {
        super.removeAttributesModifiersFromEntity(entityLivingBase, baseAttributeMap, n);
        if (entityLivingBase.getHealth() > entityLivingBase.getMaxHealth()) {
            entityLivingBase.setHealth(entityLivingBase.getMaxHealth());
        }
    }
    
    public PotionHealthBoost(final int n, final ResourceLocation resourceLocation, final boolean b, final int n2) {
        super(n, resourceLocation, b, n2);
    }
}
