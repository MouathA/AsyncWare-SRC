package net.minecraft.enchantment;

import com.google.common.collect.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.entity.*;

public abstract class Enchantment
{
    public static final Enchantment depthStrider;
    public static final Enchantment efficiency;
    public static final Enchantment fortune;
    public final int effectId;
    public static final Enchantment aquaAffinity;
    public static final Enchantment respiration;
    public static final Enchantment punch;
    public static final Enchantment fireAspect;
    public static final Enchantment flame;
    public static final Enchantment power;
    public static final Enchantment silkTouch;
    private static final Enchantment[] enchantmentsList;
    public static final Enchantment thorns;
    public static final Enchantment lure;
    public static final Enchantment smite;
    public static final Enchantment blastProtection;
    public static final Enchantment baneOfArthropods;
    protected String name;
    public static final Enchantment fireProtection;
    public static final Enchantment sharpness;
    public static final Enchantment featherFalling;
    public EnumEnchantmentType type;
    public static final Enchantment looting;
    public static final Enchantment[] enchantmentsBookList;
    public static final Enchantment knockback;
    private final int weight;
    public static final Enchantment luckOfTheSea;
    private static final Map locationEnchantments;
    public static final Enchantment unbreaking;
    public static final Enchantment protection;
    public static final Enchantment projectileProtection;
    public static final Enchantment infinity;
    
    public String getTranslatedName(final int n) {
        return StatCollector.translateToLocal(this.getName()) + " " + StatCollector.translateToLocal("enchantment.level." + n);
    }
    
    public int getWeight() {
        return this.weight;
    }
    
    public int getMinLevel() {
        return 1;
    }
    
    public int getMinEnchantability(final int n) {
        return 1 + n * 10;
    }
    
    public float calcDamageByCreature(final int n, final EnumCreatureAttribute enumCreatureAttribute) {
        return 0.0f;
    }
    
    public static Enchantment getEnchantmentByLocation(final String s) {
        return Enchantment.locationEnchantments.get(new ResourceLocation(s));
    }
    
    static {
        enchantmentsList = new Enchantment[256];
        locationEnchantments = Maps.newHashMap();
        protection = new EnchantmentProtection(0, new ResourceLocation("protection"), 10, 0);
        fireProtection = new EnchantmentProtection(1, new ResourceLocation("fire_protection"), 5, 1);
        featherFalling = new EnchantmentProtection(2, new ResourceLocation("feather_falling"), 5, 2);
        blastProtection = new EnchantmentProtection(3, new ResourceLocation("blast_protection"), 2, 3);
        projectileProtection = new EnchantmentProtection(4, new ResourceLocation("projectile_protection"), 5, 4);
        respiration = new EnchantmentOxygen(5, new ResourceLocation("respiration"), 2);
        aquaAffinity = new EnchantmentWaterWorker(6, new ResourceLocation("aqua_affinity"), 2);
        thorns = new EnchantmentThorns(7, new ResourceLocation("thorns"), 1);
        depthStrider = new EnchantmentWaterWalker(8, new ResourceLocation("depth_strider"), 2);
        sharpness = new EnchantmentDamage(16, new ResourceLocation("sharpness"), 10, 0);
        smite = new EnchantmentDamage(17, new ResourceLocation("smite"), 5, 1);
        baneOfArthropods = new EnchantmentDamage(18, new ResourceLocation("bane_of_arthropods"), 5, 2);
        knockback = new EnchantmentKnockback(19, new ResourceLocation("knockback"), 5);
        fireAspect = new EnchantmentFireAspect(20, new ResourceLocation("fire_aspect"), 2);
        looting = new EnchantmentLootBonus(21, new ResourceLocation("looting"), 2, EnumEnchantmentType.WEAPON);
        efficiency = new EnchantmentDigging(32, new ResourceLocation("efficiency"), 10);
        silkTouch = new EnchantmentUntouching(33, new ResourceLocation("silk_touch"), 1);
        unbreaking = new EnchantmentDurability(34, new ResourceLocation("unbreaking"), 5);
        fortune = new EnchantmentLootBonus(35, new ResourceLocation("fortune"), 2, EnumEnchantmentType.DIGGER);
        power = new EnchantmentArrowDamage(48, new ResourceLocation("power"), 10);
        punch = new EnchantmentArrowKnockback(49, new ResourceLocation("punch"), 2);
        flame = new EnchantmentArrowFire(50, new ResourceLocation("flame"), 2);
        infinity = new EnchantmentArrowInfinite(51, new ResourceLocation("infinity"), 1);
        luckOfTheSea = new EnchantmentLootBonus(61, new ResourceLocation("luck_of_the_sea"), 2, EnumEnchantmentType.FISHING_ROD);
        lure = new EnchantmentFishingSpeed(62, new ResourceLocation("lure"), 2, EnumEnchantmentType.FISHING_ROD);
        final ArrayList arrayList = Lists.newArrayList();
        final Enchantment[] enchantmentsList2 = Enchantment.enchantmentsList;
        while (0 < enchantmentsList2.length) {
            final Enchantment enchantment = enchantmentsList2[0];
            if (enchantment != null) {
                arrayList.add(enchantment);
            }
            int n = 0;
            ++n;
        }
        enchantmentsBookList = arrayList.toArray(new Enchantment[arrayList.size()]);
    }
    
    public Enchantment setName(final String name) {
        this.name = name;
        return this;
    }
    
    public boolean canApply(final ItemStack itemStack) {
        return this.type.canEnchantItem(itemStack.getItem());
    }
    
    public int calcModifierDamage(final int n, final DamageSource damageSource) {
        return 0;
    }
    
    public static Set func_181077_c() {
        return Enchantment.locationEnchantments.keySet();
    }
    
    public int getMaxLevel() {
        return 1;
    }
    
    public void onEntityDamaged(final EntityLivingBase entityLivingBase, final Entity entity, final int n) {
    }
    
    public static Enchantment getEnchantmentById(final int n) {
        return (n >= 0 && n < Enchantment.enchantmentsList.length) ? Enchantment.enchantmentsList[n] : null;
    }
    
    public int getMaxEnchantability(final int n) {
        return this.getMinEnchantability(n) + 5;
    }
    
    protected Enchantment(final int effectId, final ResourceLocation resourceLocation, final int weight, final EnumEnchantmentType type) {
        this.effectId = effectId;
        this.weight = weight;
        this.type = type;
        if (Enchantment.enchantmentsList[effectId] != null) {
            throw new IllegalArgumentException("Duplicate enchantment id!");
        }
        Enchantment.enchantmentsList[effectId] = this;
        Enchantment.locationEnchantments.put(resourceLocation, this);
    }
    
    public void onUserHurt(final EntityLivingBase entityLivingBase, final Entity entity, final int n) {
    }
    
    public boolean canApplyTogether(final Enchantment enchantment) {
        return this != enchantment;
    }
    
    public String getName() {
        return "enchantment." + this.name;
    }
}
