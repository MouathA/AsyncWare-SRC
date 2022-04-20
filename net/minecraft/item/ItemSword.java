package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.player.*;
import com.google.common.collect.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;

public class ItemSword extends Item
{
    private final ToolMaterial material;
    private float attackDamage;
    
    public ItemSword(final ToolMaterial material) {
        this.material = material;
        this.maxStackSize = 1;
        this.setMaxDamage(material.getMaxUses());
        this.setCreativeTab(CreativeTabs.tabCombat);
        this.attackDamage = 4.0f + material.getDamageVsEntity();
    }
    
    @Override
    public boolean onBlockDestroyed(final ItemStack itemStack, final World world, final Block block, final BlockPos blockPos, final EntityLivingBase entityLivingBase) {
        if (block.getBlockHardness(world, blockPos) != 0.0) {
            itemStack.damageItem(2, entityLivingBase);
        }
        return true;
    }
    
    @Override
    public int getMaxItemUseDuration(final ItemStack itemStack) {
        return 72000;
    }
    
    @Override
    public boolean hitEntity(final ItemStack itemStack, final EntityLivingBase entityLivingBase, final EntityLivingBase entityLivingBase2) {
        itemStack.damageItem(1, entityLivingBase2);
        return true;
    }
    
    @Override
    public EnumAction getItemUseAction(final ItemStack itemStack) {
        return EnumAction.BLOCK;
    }
    
    @Override
    public float getStrVsBlock(final ItemStack itemStack, final Block block) {
        if (block == Blocks.web) {
            return 15.0f;
        }
        final Material material = block.getMaterial();
        return (material != Material.plants && material != Material.vine && material != Material.coral && material != Material.leaves && material != Material.gourd) ? 1.0f : 1.5f;
    }
    
    @Override
    public boolean isFull3D() {
        return true;
    }
    
    public String getToolMaterialName() {
        return this.material.toString();
    }
    
    public float getDamageVsEntity() {
        return this.material.getDamageVsEntity();
    }
    
    @Override
    public boolean getIsRepairable(final ItemStack itemStack, final ItemStack itemStack2) {
        return this.material.getRepairItem() == itemStack2.getItem() || super.getIsRepairable(itemStack, itemStack2);
    }
    
    @Override
    public boolean canHarvestBlock(final Block block) {
        return block == Blocks.web;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        entityPlayer.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
        return itemStack;
    }
    
    @Override
    public int getItemEnchantability() {
        return this.material.getEnchantability();
    }
    
    @Override
    public Multimap getItemAttributeModifiers() {
        final Multimap itemAttributeModifiers = super.getItemAttributeModifiers();
        itemAttributeModifiers.put((Object)SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), (Object)new AttributeModifier(ItemSword.itemModifierUUID, "Weapon modifier", this.attackDamage, 0));
        return itemAttributeModifiers;
    }
}
