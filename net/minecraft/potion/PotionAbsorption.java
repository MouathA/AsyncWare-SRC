package net.minecraft.potion;

import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;

public class PotionAbsorption extends Potion
{
    protected PotionAbsorption(final int n, final ResourceLocation resourceLocation, final boolean b, final int n2) {
        super(n, resourceLocation, b, n2);
    }
    
    @Override
    public void applyAttributesModifiersToEntity(final EntityLivingBase entityLivingBase, final BaseAttributeMap baseAttributeMap, final int n) {
        entityLivingBase.setAbsorptionAmount(entityLivingBase.getAbsorptionAmount() + 4 * (n + 1));
        super.applyAttributesModifiersToEntity(entityLivingBase, baseAttributeMap, n);
    }
    
    @Override
    public void removeAttributesModifiersFromEntity(final EntityLivingBase entityLivingBase, final BaseAttributeMap baseAttributeMap, final int n) {
        entityLivingBase.setAbsorptionAmount(entityLivingBase.getAbsorptionAmount() - 4 * (n + 1));
        super.removeAttributesModifiersFromEntity(entityLivingBase, baseAttributeMap, n);
    }
}
