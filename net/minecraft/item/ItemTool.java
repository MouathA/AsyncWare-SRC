package net.minecraft.item;

import java.util.*;
import net.minecraft.creativetab.*;
import com.google.common.collect.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class ItemTool extends Item
{
    protected ToolMaterial toolMaterial;
    private float damageVsEntity;
    private Set effectiveBlocks;
    protected float efficiencyOnProperMaterial;
    
    protected ItemTool(final float n, final ToolMaterial toolMaterial, final Set effectiveBlocks) {
        this.efficiencyOnProperMaterial = 4.0f;
        this.toolMaterial = toolMaterial;
        this.effectiveBlocks = effectiveBlocks;
        this.maxStackSize = 1;
        this.setMaxDamage(toolMaterial.getMaxUses());
        this.efficiencyOnProperMaterial = toolMaterial.getEfficiencyOnProperMaterial();
        this.damageVsEntity = n + toolMaterial.getDamageVsEntity();
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public Multimap getItemAttributeModifiers() {
        final Multimap itemAttributeModifiers = super.getItemAttributeModifiers();
        itemAttributeModifiers.put((Object)SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), (Object)new AttributeModifier(ItemTool.itemModifierUUID, "Tool modifier", this.damageVsEntity, 0));
        return itemAttributeModifiers;
    }
    
    @Override
    public boolean isFull3D() {
        return true;
    }
    
    @Override
    public float getStrVsBlock(final ItemStack itemStack, final Block block) {
        return this.effectiveBlocks.contains(block) ? this.efficiencyOnProperMaterial : 1.0f;
    }
    
    @Override
    public boolean onBlockDestroyed(final ItemStack itemStack, final World world, final Block block, final BlockPos blockPos, final EntityLivingBase entityLivingBase) {
        if (block.getBlockHardness(world, blockPos) != 0.0) {
            itemStack.damageItem(1, entityLivingBase);
        }
        return true;
    }
    
    public String getToolMaterialName() {
        return this.toolMaterial.toString();
    }
    
    @Override
    public int getItemEnchantability() {
        return this.toolMaterial.getEnchantability();
    }
    
    public ToolMaterial getToolMaterial() {
        return this.toolMaterial;
    }
    
    @Override
    public boolean getIsRepairable(final ItemStack itemStack, final ItemStack itemStack2) {
        return this.toolMaterial.getRepairItem() == itemStack2.getItem() || super.getIsRepairable(itemStack, itemStack2);
    }
    
    @Override
    public boolean hitEntity(final ItemStack itemStack, final EntityLivingBase entityLivingBase, final EntityLivingBase entityLivingBase2) {
        itemStack.damageItem(2, entityLivingBase2);
        return true;
    }
}
