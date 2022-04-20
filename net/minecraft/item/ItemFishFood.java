package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.potion.*;
import net.minecraft.creativetab.*;
import java.util.*;
import com.google.common.collect.*;

public class ItemFishFood extends ItemFood
{
    private final boolean cooked;
    
    @Override
    public float getSaturationModifier(final ItemStack itemStack) {
        final FishType byItemStack = FishType.byItemStack(itemStack);
        return (this.cooked && byItemStack.canCook()) ? byItemStack.getCookedSaturationModifier() : byItemStack.getUncookedSaturationModifier();
    }
    
    @Override
    protected void onFoodEaten(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (FishType.byItemStack(itemStack) == FishType.PUFFERFISH) {
            entityPlayer.addPotionEffect(new PotionEffect(Potion.poison.id, 1200, 3));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.id, 300, 2));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.id, 300, 1));
        }
        super.onFoodEaten(itemStack, world, entityPlayer);
    }
    
    @Override
    public String getPotionEffect(final ItemStack itemStack) {
        return (FishType.byItemStack(itemStack) == FishType.PUFFERFISH) ? "+0-1+2+3+13&4-4" : null;
    }
    
    @Override
    public int getHealAmount(final ItemStack itemStack) {
        final FishType byItemStack = FishType.byItemStack(itemStack);
        return (this.cooked && byItemStack.canCook()) ? byItemStack.getCookedHealAmount() : byItemStack.getUncookedHealAmount();
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack itemStack) {
        final FishType byItemStack = FishType.byItemStack(itemStack);
        return this.getUnlocalizedName() + "." + byItemStack.getUnlocalizedName() + "." + ((this.cooked && byItemStack.canCook()) ? "cooked" : "raw");
    }
    
    @Override
    public void getSubItems(final Item item, final CreativeTabs creativeTabs, final List list) {
        final FishType[] values = FishType.values();
        while (0 < values.length) {
            final FishType fishType = values[0];
            if (!this.cooked || fishType.canCook()) {
                list.add(new ItemStack(this, 1, fishType.getMetadata()));
            }
            int n = 0;
            ++n;
        }
    }
    
    public ItemFishFood(final boolean cooked) {
        super(0, 0.0f, false);
        this.cooked = cooked;
    }
    
    public enum FishType
    {
        private static final FishType[] $VALUES;
        
        PUFFERFISH("PUFFERFISH", 3, 3, "pufferfish", 1, 0.1f);
        
        private final int cookedHealAmount;
        private final float cookedSaturationModifier;
        private final String unlocalizedName;
        private boolean cookable;
        
        COD("COD", 0, 0, "cod", 2, 0.1f, 5, 0.6f);
        
        private static final Map META_LOOKUP;
        private final float uncookedSaturationModifier;
        private final int meta;
        private final int uncookedHealAmount;
        
        CLOWNFISH("CLOWNFISH", 2, 2, "clownfish", 1, 0.1f), 
        SALMON("SALMON", 1, 1, "salmon", 2, 0.1f, 6, 0.8f);
        
        private FishType(final String s, final int n, final int meta, final String unlocalizedName, final int uncookedHealAmount, final float uncookedSaturationModifier) {
            this.cookable = false;
            this.meta = meta;
            this.unlocalizedName = unlocalizedName;
            this.uncookedHealAmount = uncookedHealAmount;
            this.uncookedSaturationModifier = uncookedSaturationModifier;
            this.cookedHealAmount = 0;
            this.cookedSaturationModifier = 0.0f;
            this.cookable = false;
        }
        
        public static FishType byItemStack(final ItemStack itemStack) {
            return (itemStack.getItem() instanceof ItemFishFood) ? byMetadata(itemStack.getMetadata()) : FishType.COD;
        }
        
        public static FishType byMetadata(final int n) {
            final FishType fishType = FishType.META_LOOKUP.get(n);
            return (fishType == null) ? FishType.COD : fishType;
        }
        
        private FishType(final String s, final int n, final int meta, final String unlocalizedName, final int uncookedHealAmount, final float uncookedSaturationModifier, final int cookedHealAmount, final float cookedSaturationModifier) {
            this.cookable = false;
            this.meta = meta;
            this.unlocalizedName = unlocalizedName;
            this.uncookedHealAmount = uncookedHealAmount;
            this.uncookedSaturationModifier = uncookedSaturationModifier;
            this.cookedHealAmount = cookedHealAmount;
            this.cookedSaturationModifier = cookedSaturationModifier;
            this.cookable = true;
        }
        
        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }
        
        public float getUncookedSaturationModifier() {
            return this.uncookedSaturationModifier;
        }
        
        public boolean canCook() {
            return this.cookable;
        }
        
        public int getCookedHealAmount() {
            return this.cookedHealAmount;
        }
        
        public int getUncookedHealAmount() {
            return this.uncookedHealAmount;
        }
        
        static {
            $VALUES = new FishType[] { FishType.COD, FishType.SALMON, FishType.CLOWNFISH, FishType.PUFFERFISH };
            META_LOOKUP = Maps.newHashMap();
            final FishType[] values = values();
            while (0 < values.length) {
                final FishType fishType = values[0];
                FishType.META_LOOKUP.put(fishType.getMetadata(), fishType);
                int n = 0;
                ++n;
            }
        }
        
        public int getMetadata() {
            return this.meta;
        }
        
        public float getCookedSaturationModifier() {
            return this.cookedSaturationModifier;
        }
    }
}
