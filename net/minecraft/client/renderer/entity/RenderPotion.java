package net.minecraft.client.renderer.entity;

import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class RenderPotion extends RenderSnowball
{
    public ItemStack func_177082_d(final EntityPotion entityPotion) {
        return new ItemStack(this.field_177084_a, 1, entityPotion.getPotionDamage());
    }
    
    @Override
    public ItemStack func_177082_d(final Entity entity) {
        return this.func_177082_d((EntityPotion)entity);
    }
    
    public RenderPotion(final RenderManager renderManager, final RenderItem renderItem) {
        super(renderManager, Items.potionitem, renderItem);
    }
}
