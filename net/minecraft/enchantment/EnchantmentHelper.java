package net.minecraft.enchantment;

import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import java.util.*;
import com.google.common.collect.*;

public class EnchantmentHelper
{
    private static final ModifierLiving enchantmentModifierLiving;
    private static final Random enchantmentRand;
    private static final DamageIterator ENCHANTMENT_ITERATOR_DAMAGE;
    private static final ModifierDamage enchantmentModifierDamage;
    private static final HurtIterator ENCHANTMENT_ITERATOR_HURT;
    
    public static void applyThornEnchantments(final EntityLivingBase user, final Entity attacker) {
        EnchantmentHelper.ENCHANTMENT_ITERATOR_HURT.attacker = attacker;
        EnchantmentHelper.ENCHANTMENT_ITERATOR_HURT.user = user;
        if (user != null) {
            applyEnchantmentModifierArray(EnchantmentHelper.ENCHANTMENT_ITERATOR_HURT, user.getInventory());
        }
        if (attacker instanceof EntityPlayer) {
            applyEnchantmentModifier(EnchantmentHelper.ENCHANTMENT_ITERATOR_HURT, user.getHeldItem());
        }
    }
    
    public static int getEnchantmentLevel(final int n, final ItemStack itemStack) {
        if (itemStack == null) {
            return 0;
        }
        final NBTTagList enchantmentTagList = itemStack.getEnchantmentTagList();
        if (enchantmentTagList == null) {
            return 0;
        }
        while (0 < enchantmentTagList.tagCount()) {
            final short short1 = enchantmentTagList.getCompoundTagAt(0).getShort("id");
            final short short2 = enchantmentTagList.getCompoundTagAt(0).getShort("lvl");
            if (short1 == n) {
                return short2;
            }
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    public static ItemStack getEnchantedItem(final Enchantment enchantment, final EntityLivingBase entityLivingBase) {
        final ItemStack[] inventory = entityLivingBase.getInventory();
        while (0 < inventory.length) {
            final ItemStack itemStack = inventory[0];
            if (itemStack != null && getEnchantmentLevel(enchantment.effectId, itemStack) > 0) {
                return itemStack;
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    public static void setEnchantments(final Map map, final ItemStack itemStack) {
        final NBTTagList list = new NBTTagList();
        for (final int intValue : map.keySet()) {
            final Enchantment enchantmentById = Enchantment.getEnchantmentById(intValue);
            if (enchantmentById != null) {
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                nbtTagCompound.setShort("id", (short)intValue);
                nbtTagCompound.setShort("lvl", (short)(int)map.get(intValue));
                list.appendTag(nbtTagCompound);
                if (itemStack.getItem() != Items.enchanted_book) {
                    continue;
                }
                Items.enchanted_book.addEnchantment(itemStack, new EnchantmentData(enchantmentById, (int)map.get(intValue)));
            }
        }
        if (list.tagCount() > 0) {
            if (itemStack.getItem() != Items.enchanted_book) {
                itemStack.setTagInfo("ench", list);
            }
        }
        else if (itemStack.hasTagCompound()) {
            itemStack.getTagCompound().removeTag("ench");
        }
    }
    
    public static int getEfficiencyModifier(final EntityLivingBase entityLivingBase) {
        return getEnchantmentLevel(Enchantment.efficiency.effectId, entityLivingBase.getHeldItem());
    }
    
    private static void applyEnchantmentModifier(final IModifier modifier, final ItemStack itemStack) {
        if (itemStack != null) {
            final NBTTagList enchantmentTagList = itemStack.getEnchantmentTagList();
            if (enchantmentTagList != null) {
                while (0 < enchantmentTagList.tagCount()) {
                    final short short1 = enchantmentTagList.getCompoundTagAt(0).getShort("id");
                    final short short2 = enchantmentTagList.getCompoundTagAt(0).getShort("lvl");
                    if (Enchantment.getEnchantmentById(short1) != null) {
                        modifier.calculateModifier(Enchantment.getEnchantmentById(short1), short2);
                    }
                    int n = 0;
                    ++n;
                }
            }
        }
    }
    
    public static int getFireAspectModifier(final EntityLivingBase entityLivingBase) {
        return getEnchantmentLevel(Enchantment.fireAspect.effectId, entityLivingBase.getHeldItem());
    }
    
    public static int getMaxEnchantmentLevel(final int n, final ItemStack[] array) {
        if (array == null) {
            return 0;
        }
        while (0 < array.length) {
            if (getEnchantmentLevel(n, array[0]) > 0) {}
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    public static int getKnockbackModifier(final EntityLivingBase entityLivingBase) {
        return getEnchantmentLevel(Enchantment.knockback.effectId, entityLivingBase.getHeldItem());
    }
    
    public static int getFortuneModifier(final EntityLivingBase entityLivingBase) {
        return getEnchantmentLevel(Enchantment.fortune.effectId, entityLivingBase.getHeldItem());
    }
    
    public static void applyArthropodEnchantments(final EntityLivingBase user, final Entity target) {
        EnchantmentHelper.ENCHANTMENT_ITERATOR_DAMAGE.user = user;
        EnchantmentHelper.ENCHANTMENT_ITERATOR_DAMAGE.target = target;
        if (user != null) {
            applyEnchantmentModifierArray(EnchantmentHelper.ENCHANTMENT_ITERATOR_DAMAGE, user.getInventory());
        }
        if (user instanceof EntityPlayer) {
            applyEnchantmentModifier(EnchantmentHelper.ENCHANTMENT_ITERATOR_DAMAGE, user.getHeldItem());
        }
    }
    
    static {
        enchantmentRand = new Random();
        enchantmentModifierDamage = new ModifierDamage(null);
        enchantmentModifierLiving = new ModifierLiving(null);
        ENCHANTMENT_ITERATOR_HURT = new HurtIterator(null);
        ENCHANTMENT_ITERATOR_DAMAGE = new DamageIterator(null);
    }
    
    public static int getLureModifier(final EntityLivingBase entityLivingBase) {
        return getEnchantmentLevel(Enchantment.lure.effectId, entityLivingBase.getHeldItem());
    }
    
    public static float func_152377_a(final ItemStack itemStack, final EnumCreatureAttribute entityLiving) {
        EnchantmentHelper.enchantmentModifierLiving.livingModifier = 0.0f;
        EnchantmentHelper.enchantmentModifierLiving.entityLiving = entityLiving;
        applyEnchantmentModifier(EnchantmentHelper.enchantmentModifierLiving, itemStack);
        return EnchantmentHelper.enchantmentModifierLiving.livingModifier;
    }
    
    public static boolean getSilkTouchModifier(final EntityLivingBase entityLivingBase) {
        return getEnchantmentLevel(Enchantment.silkTouch.effectId, entityLivingBase.getHeldItem()) > 0;
    }
    
    public static int getDepthStriderModifier(final Entity entity) {
        return getMaxEnchantmentLevel(Enchantment.depthStrider.effectId, entity.getInventory());
    }
    
    public static Map getEnchantments(final ItemStack itemStack) {
        final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap();
        final NBTTagList list = (itemStack.getItem() == Items.enchanted_book) ? Items.enchanted_book.getEnchantments(itemStack) : itemStack.getEnchantmentTagList();
        if (list != null) {
            while (0 < list.tagCount()) {
                linkedHashMap.put((int)list.getCompoundTagAt(0).getShort("id"), (int)list.getCompoundTagAt(0).getShort("lvl"));
                int n = 0;
                ++n;
            }
        }
        return linkedHashMap;
    }
    
    public static boolean getAquaAffinityModifier(final EntityLivingBase entityLivingBase) {
        return getMaxEnchantmentLevel(Enchantment.aquaAffinity.effectId, entityLivingBase.getInventory()) > 0;
    }
    
    public static int getLuckOfSeaModifier(final EntityLivingBase entityLivingBase) {
        return getEnchantmentLevel(Enchantment.luckOfTheSea.effectId, entityLivingBase.getHeldItem());
    }
    
    public static Map mapEnchantmentData(final int n, final ItemStack itemStack) {
        final Item item = itemStack.getItem();
        Map<Integer, EnchantmentData> hashMap = null;
        final boolean b = itemStack.getItem() == Items.book;
        final Enchantment[] enchantmentsBookList = Enchantment.enchantmentsBookList;
        while (0 < enchantmentsBookList.length) {
            final Enchantment enchantment = enchantmentsBookList[0];
            if (enchantment != null && (enchantment.type.canEnchantItem(item) || b)) {
                for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); ++i) {
                    if (n >= enchantment.getMinEnchantability(i) && n <= enchantment.getMaxEnchantability(i)) {
                        if (hashMap == null) {
                            hashMap = (Map<Integer, EnchantmentData>)Maps.newHashMap();
                        }
                        hashMap.put(enchantment.effectId, new EnchantmentData(enchantment, i));
                    }
                }
            }
            int n2 = 0;
            ++n2;
        }
        return hashMap;
    }
    
    public static int getEnchantmentModifierDamage(final ItemStack[] array, final DamageSource source) {
        EnchantmentHelper.enchantmentModifierDamage.damageModifier = 0;
        EnchantmentHelper.enchantmentModifierDamage.source = source;
        applyEnchantmentModifierArray(EnchantmentHelper.enchantmentModifierDamage, array);
        if (EnchantmentHelper.enchantmentModifierDamage.damageModifier > 25) {
            EnchantmentHelper.enchantmentModifierDamage.damageModifier = 25;
        }
        else if (EnchantmentHelper.enchantmentModifierDamage.damageModifier < 0) {
            EnchantmentHelper.enchantmentModifierDamage.damageModifier = 0;
        }
        return (EnchantmentHelper.enchantmentModifierDamage.damageModifier + 1 >> 1) + EnchantmentHelper.enchantmentRand.nextInt((EnchantmentHelper.enchantmentModifierDamage.damageModifier >> 1) + 1);
    }
    
    private static void applyEnchantmentModifierArray(final IModifier modifier, final ItemStack[] array) {
        while (0 < array.length) {
            applyEnchantmentModifier(modifier, array[0]);
            int n = 0;
            ++n;
        }
    }
    
    public static List buildEnchantmentList(final Random random, final ItemStack itemStack, final int n) {
        final int itemEnchantability = itemStack.getItem().getItemEnchantability();
        if (itemEnchantability <= 0) {
            return null;
        }
        final int n2 = itemEnchantability / 2;
        final int n3 = (int)((1 + random.nextInt((n2 >> 1) + 1) + random.nextInt((n2 >> 1) + 1) + n) * (1.0f + (random.nextFloat() + random.nextFloat() - 1.0f) * 0.15f) + 0.5f);
        List<EnchantmentData> arrayList = null;
        final Map mapEnchantmentData = mapEnchantmentData(1, itemStack);
        if (mapEnchantmentData != null && !mapEnchantmentData.isEmpty()) {
            final EnchantmentData enchantmentData = (EnchantmentData)WeightedRandom.getRandomItem(random, mapEnchantmentData.values());
            if (enchantmentData != null) {
                arrayList = (List<EnchantmentData>)Lists.newArrayList();
                arrayList.add(enchantmentData);
                while (random.nextInt(50) <= 1) {
                    final Iterator<Integer> iterator = mapEnchantmentData.keySet().iterator();
                    while (iterator.hasNext()) {
                        final Integer n4 = iterator.next();
                        final Iterator<EnchantmentData> iterator2 = arrayList.iterator();
                        while (iterator2.hasNext() && iterator2.next().enchantmentobj.canApplyTogether(Enchantment.getEnchantmentById(n4))) {}
                        iterator.remove();
                    }
                    if (!mapEnchantmentData.isEmpty()) {
                        arrayList.add((EnchantmentData)WeightedRandom.getRandomItem(random, mapEnchantmentData.values()));
                    }
                }
            }
        }
        return arrayList;
    }
    
    public static ItemStack addRandomEnchantment(final Random random, final ItemStack itemStack, final int n) {
        final List buildEnchantmentList = buildEnchantmentList(random, itemStack, n);
        final boolean b = itemStack.getItem() == Items.book;
        if (b) {
            itemStack.setItem(Items.enchanted_book);
        }
        if (buildEnchantmentList != null) {
            for (final EnchantmentData enchantmentData : buildEnchantmentList) {
                if (b) {
                    Items.enchanted_book.addEnchantment(itemStack, enchantmentData);
                }
                else {
                    itemStack.addEnchantment(enchantmentData.enchantmentobj, enchantmentData.enchantmentLevel);
                }
            }
        }
        return itemStack;
    }
    
    public static int getLootingModifier(final EntityLivingBase entityLivingBase) {
        return getEnchantmentLevel(Enchantment.looting.effectId, entityLivingBase.getHeldItem());
    }
    
    public static int calcItemStackEnchantability(final Random random, final int n, final int n2, final ItemStack itemStack) {
        if (itemStack.getItem().getItemEnchantability() <= 0) {
            return 0;
        }
        final int n3 = random.nextInt(8) + 1 + 7 + random.nextInt(16);
        return (n == 0) ? Math.max(n3 / 3, 1) : ((n == 1) ? (n3 * 2 / 3 + 1) : Math.max(n3, 30));
    }
    
    public static int getRespiration(final Entity entity) {
        return getMaxEnchantmentLevel(Enchantment.respiration.effectId, entity.getInventory());
    }
    
    interface IModifier
    {
        void calculateModifier(final Enchantment p0, final int p1);
    }
    
    static final class ModifierDamage implements IModifier
    {
        public DamageSource source;
        public int damageModifier;
        
        private ModifierDamage() {
        }
        
        @Override
        public void calculateModifier(final Enchantment enchantment, final int n) {
            this.damageModifier += enchantment.calcModifierDamage(n, this.source);
        }
        
        ModifierDamage(final EnchantmentHelper$1 object) {
            this();
        }
    }
    
    static final class DamageIterator implements IModifier
    {
        public EntityLivingBase user;
        public Entity target;
        
        @Override
        public void calculateModifier(final Enchantment enchantment, final int n) {
            enchantment.onEntityDamaged(this.user, this.target, n);
        }
        
        DamageIterator(final EnchantmentHelper$1 object) {
            this();
        }
        
        private DamageIterator() {
        }
    }
    
    static final class HurtIterator implements IModifier
    {
        public Entity attacker;
        public EntityLivingBase user;
        
        @Override
        public void calculateModifier(final Enchantment enchantment, final int n) {
            enchantment.onUserHurt(this.user, this.attacker, n);
        }
        
        HurtIterator(final EnchantmentHelper$1 object) {
            this();
        }
        
        private HurtIterator() {
        }
    }
    
    static final class ModifierLiving implements IModifier
    {
        public float livingModifier;
        public EnumCreatureAttribute entityLiving;
        
        private ModifierLiving() {
        }
        
        ModifierLiving(final EnchantmentHelper$1 object) {
            this();
        }
        
        @Override
        public void calculateModifier(final Enchantment enchantment, final int n) {
            this.livingModifier += enchantment.calcDamageByCreature(n, this.entityLiving);
        }
    }
}
