package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.block.*;
import net.minecraft.dispenser.*;
import com.google.common.base.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.init.*;

public class ItemArmor extends Item
{
    private final ArmorMaterial material;
    public final int damageReduceAmount;
    public final int armorType;
    private static final IBehaviorDispenseItem dispenserBehavior;
    public final int renderIndex;
    
    public void removeColor(final ItemStack itemStack) {
        if (this.material == ArmorMaterial.LEATHER) {
            final NBTTagCompound tagCompound = itemStack.getTagCompound();
            if (tagCompound != null) {
                final NBTTagCompound compoundTag = tagCompound.getCompoundTag("display");
                if (compoundTag.hasKey("color")) {
                    compoundTag.removeTag("color");
                }
            }
        }
    }
    
    public ItemArmor(final ArmorMaterial material, final int renderIndex, final int armorType) {
        this.material = material;
        this.armorType = armorType;
        this.renderIndex = renderIndex;
        this.damageReduceAmount = material.getDamageReductionAmount(armorType);
        this.setMaxDamage(material.getDurability(armorType));
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabCombat);
        BlockDispenser.dispenseBehaviorRegistry.putObject(this, ItemArmor.dispenserBehavior);
    }
    
    static {
        ItemArmor.maxDamageArray = new int[] { 11, 16, 15, 13 };
        ItemArmor.EMPTY_SLOT_NAMES = new String[] { "minecraft:items/empty_armor_slot_helmet", "minecraft:items/empty_armor_slot_chestplate", "minecraft:items/empty_armor_slot_leggings", "minecraft:items/empty_armor_slot_boots" };
        dispenserBehavior = new BehaviorDefaultDispenseItem() {
            @Override
            protected ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack itemStack) {
                final BlockPos offset = blockSource.getBlockPos().offset(BlockDispenser.getFacing(blockSource.getBlockMetadata()));
                final int x = offset.getX();
                final int y = offset.getY();
                final int z = offset.getZ();
                final List entitiesWithinAABB = blockSource.getWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1), Predicates.and(EntitySelectors.NOT_SPECTATING, (Predicate)new EntitySelectors.ArmoredMob(itemStack)));
                if (entitiesWithinAABB.size() > 0) {
                    final EntityLivingBase entityLivingBase = entitiesWithinAABB.get(0);
                    final int n = (entityLivingBase instanceof EntityPlayer) ? 1 : 0;
                    final int armorPosition = EntityLiving.getArmorPosition(itemStack);
                    final ItemStack copy = itemStack.copy();
                    copy.stackSize = 1;
                    entityLivingBase.setCurrentItemOrArmor(armorPosition - n, copy);
                    if (entityLivingBase instanceof EntityLiving) {
                        ((EntityLiving)entityLivingBase).setEquipmentDropChance(armorPosition, 2.0f);
                    }
                    --itemStack.stackSize;
                    return itemStack;
                }
                return super.dispenseStack(blockSource, itemStack);
            }
        };
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack itemStack, final int n) {
        if (n > 0) {
            return 16777215;
        }
        this.getColor(itemStack);
        return 16777215;
    }
    
    @Override
    public int getItemEnchantability() {
        return this.material.getEnchantability();
    }
    
    static int[] access$000() {
        return ItemArmor.maxDamageArray;
    }
    
    public ArmorMaterial getArmorMaterial() {
        return this.material;
    }
    
    public boolean hasColor(final ItemStack itemStack) {
        return this.material == ArmorMaterial.LEATHER && itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("display", 10) && itemStack.getTagCompound().getCompoundTag("display").hasKey("color", 3);
    }
    
    @Override
    public boolean getIsRepairable(final ItemStack itemStack, final ItemStack itemStack2) {
        return this.material.getRepairItem() == itemStack2.getItem() || super.getIsRepairable(itemStack, itemStack2);
    }
    
    public void setColor(final ItemStack itemStack, final int n) {
        if (this.material != ArmorMaterial.LEATHER) {
            throw new UnsupportedOperationException("Can't dye non-leather!");
        }
        NBTTagCompound tagCompound = itemStack.getTagCompound();
        if (tagCompound == null) {
            tagCompound = new NBTTagCompound();
            itemStack.setTagCompound(tagCompound);
        }
        final NBTTagCompound compoundTag = tagCompound.getCompoundTag("display");
        if (!tagCompound.hasKey("display", 10)) {
            tagCompound.setTag("display", compoundTag);
        }
        compoundTag.setInteger("color", n);
    }
    
    public int getColor(final ItemStack itemStack) {
        if (this.material != ArmorMaterial.LEATHER) {
            return -1;
        }
        final NBTTagCompound tagCompound = itemStack.getTagCompound();
        if (tagCompound != null) {
            final NBTTagCompound compoundTag = tagCompound.getCompoundTag("display");
            if (compoundTag != null && compoundTag.hasKey("color", 3)) {
                return compoundTag.getInteger("color");
            }
        }
        return 10511680;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        final int n = EntityLiving.getArmorPosition(itemStack) - 1;
        if (entityPlayer.getCurrentArmor(n) == null) {
            entityPlayer.setCurrentItemOrArmor(n, itemStack.copy());
            itemStack.stackSize = 0;
        }
        return itemStack;
    }
    
    public enum ArmorMaterial
    {
        LEATHER("LEATHER", 0, "leather", 5, new int[] { 1, 3, 2, 1 }, 15);
        
        private final int maxDamageFactor;
        
        GOLD("GOLD", 3, "gold", 7, new int[] { 2, 5, 3, 1 }, 25), 
        DIAMOND("DIAMOND", 4, "diamond", 33, new int[] { 3, 8, 6, 3 }, 10);
        
        private final String name;
        
        CHAIN("CHAIN", 1, "chainmail", 15, new int[] { 2, 5, 4, 1 }, 12);
        
        private final int enchantability;
        
        IRON("IRON", 2, "iron", 15, new int[] { 2, 6, 5, 2 }, 9);
        
        private final int[] damageReductionAmountArray;
        private static final ArmorMaterial[] $VALUES;
        
        private ArmorMaterial(final String s, final int n, final String name, final int maxDamageFactor, final int[] damageReductionAmountArray, final int enchantability) {
            this.name = name;
            this.maxDamageFactor = maxDamageFactor;
            this.damageReductionAmountArray = damageReductionAmountArray;
            this.enchantability = enchantability;
        }
        
        public int getDamageReductionAmount(final int n) {
            return this.damageReductionAmountArray[n];
        }
        
        public Item getRepairItem() {
            return (this == ArmorMaterial.LEATHER) ? Items.leather : ((this == ArmorMaterial.CHAIN) ? Items.iron_ingot : ((this == ArmorMaterial.GOLD) ? Items.gold_ingot : ((this == ArmorMaterial.IRON) ? Items.iron_ingot : ((this == ArmorMaterial.DIAMOND) ? Items.diamond : null))));
        }
        
        static {
            $VALUES = new ArmorMaterial[] { ArmorMaterial.LEATHER, ArmorMaterial.CHAIN, ArmorMaterial.IRON, ArmorMaterial.GOLD, ArmorMaterial.DIAMOND };
        }
        
        public int getEnchantability() {
            return this.enchantability;
        }
        
        public String getName() {
            return this.name;
        }
        
        public int getDurability(final int n) {
            return ItemArmor.access$000()[n] * this.maxDamageFactor;
        }
    }
}
