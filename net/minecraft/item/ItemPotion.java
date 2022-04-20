package net.minecraft.item;

import net.minecraft.init.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.potion.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.stats.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;
import com.google.common.collect.*;

public class ItemPotion extends Item
{
    private Map effectCache;
    private static final Map SUB_ITEMS_CACHE;
    
    @Override
    public String getItemStackDisplayName(final ItemStack itemStack) {
        if (itemStack.getMetadata() == 0) {
            return "\u0691\u068c\u069d\u0695\u06d6\u069d\u0695\u0688\u068c\u0681\u06a8\u0697\u068c\u0691\u0697\u0696\u06d6\u0696\u0699\u0695\u069d".trim();
        }
        String string = "";
        if (itemStack.getMetadata() != 0) {
            string = "\u0688\u0697\u068c\u0691\u0697\u0696\u06d6\u0688\u068a\u069d\u069e\u0691\u0680\u06d6\u069f\u068a\u069d\u0696\u0699\u069c\u069d".trim() + " ";
        }
        final List effects = Items.potionitem.getEffects(itemStack);
        if (effects != null && !effects.isEmpty()) {
            return string + StatCollector.translateToLocal(effects.get(0).getEffectName() + ".postfix").trim();
        }
        return StatCollector.translateToLocal(PotionHelper.getPotionPrefix(itemStack.getMetadata())).trim() + " " + super.getItemStackDisplayName(itemStack);
    }
    
    public ItemPotion() {
        this.effectCache = Maps.newHashMap();
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabs.tabBrewing);
    }
    
    public int getColorFromDamage(final int n) {
        return PotionHelper.getLiquidColor(n, false);
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack itemStack, final int n) {
        return (n > 0) ? 16777215 : this.getColorFromDamage(itemStack.getMetadata());
    }
    
    @Override
    public void getSubItems(final Item p0, final CreativeTabs p1, final List p2) {
        // 
        // This method could not be decompiled.
        // 
        // Could not show original bytecode, likely due to the same error.
        // 
        // The error that occurred was:
        // 
        // com.strobel.assembler.metadata.MethodBodyParseException: An error occurred while parsing the bytecode of method 'net/minecraft/item/ItemPotion.getSubItems:(Lnet/minecraft/item/Item;Lnet/minecraft/creativetab/CreativeTabs;Ljava/util/List;)V'.
        //     at com.strobel.assembler.metadata.MethodReader.readBody(MethodReader.java:66)
        //     at com.strobel.assembler.metadata.MethodDefinition.tryLoadBody(MethodDefinition.java:729)
        //     at com.strobel.assembler.metadata.MethodDefinition.getBody(MethodDefinition.java:83)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:202)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // Caused by: java.lang.IndexOutOfBoundsException: No instruction found at offset 20.
        //     at com.strobel.assembler.ir.InstructionCollection.atOffset(InstructionCollection.java:38)
        //     at com.strobel.assembler.metadata.MethodReader.readBodyCore(MethodReader.java:235)
        //     at com.strobel.assembler.metadata.MethodReader.readBody(MethodReader.java:62)
        //     ... 17 more
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public boolean hasEffect(final ItemStack itemStack) {
        final List effects = this.getEffects(itemStack);
        return effects != null && !effects.isEmpty();
    }
    
    public List getEffects(final int n) {
        List potionEffects = this.effectCache.get(n);
        if (potionEffects == null) {
            potionEffects = PotionHelper.getPotionEffects(n, false);
            this.effectCache.put(n, potionEffects);
        }
        return potionEffects;
    }
    
    @Override
    public EnumAction getItemUseAction(final ItemStack itemStack) {
        return EnumAction.DRINK;
    }
    
    @Override
    public void addInformation(final ItemStack itemStack, final EntityPlayer entityPlayer, final List list, final boolean b) {
        if (itemStack.getMetadata() != 0) {
            final List effects = Items.potionitem.getEffects(itemStack);
            final HashMultimap create = HashMultimap.create();
            if (effects != null && !effects.isEmpty()) {
                for (final PotionEffect potionEffect : effects) {
                    String s = StatCollector.translateToLocal(potionEffect.getEffectName()).trim();
                    final Potion potion = Potion.potionTypes[potionEffect.getPotionID()];
                    final Map attributeModifierMap = potion.getAttributeModifierMap();
                    if (attributeModifierMap != null && attributeModifierMap.size() > 0) {
                        for (final Map.Entry<K, AttributeModifier> entry : attributeModifierMap.entrySet()) {
                            final AttributeModifier attributeModifier = entry.getValue();
                            ((Multimap)create).put((Object)((IAttribute)entry.getKey()).getAttributeUnlocalizedName(), (Object)new AttributeModifier(attributeModifier.getName(), potion.getAttributeModifierAmount(potionEffect.getAmplifier(), attributeModifier), attributeModifier.getOperation()));
                        }
                    }
                    if (potionEffect.getAmplifier() > 0) {
                        s = s + " " + StatCollector.translateToLocal("potion.potency." + potionEffect.getAmplifier()).trim();
                    }
                    if (potionEffect.getDuration() > 20) {
                        s = s + " (" + Potion.getDurationString(potionEffect) + ")";
                    }
                    if (potion.isBadEffect()) {
                        list.add(EnumChatFormatting.RED + s);
                    }
                    else {
                        list.add(EnumChatFormatting.GRAY + s);
                    }
                }
            }
            else {
                list.add(EnumChatFormatting.GRAY + "\u0688\u0697\u068c\u0691\u0697\u0696\u06d6\u069d\u0695\u0688\u068c\u0681".trim());
            }
            if (!((Multimap)create).isEmpty()) {
                list.add("");
                list.add(EnumChatFormatting.DARK_PURPLE + "\u0688\u0697\u068c\u0691\u0697\u0696\u06d6\u069d\u069e\u069e\u069d\u069b\u068c\u068b\u06d6\u068f\u0690\u069d\u0696\u06bc\u068a\u0699\u0696\u0693");
                for (final Map.Entry<K, AttributeModifier> entry2 : ((Multimap)create).entries()) {
                    final AttributeModifier attributeModifier2 = entry2.getValue();
                    final double amount = attributeModifier2.getAmount();
                    double amount2;
                    if (attributeModifier2.getOperation() != 1 && attributeModifier2.getOperation() != 2) {
                        amount2 = attributeModifier2.getAmount();
                    }
                    else {
                        amount2 = attributeModifier2.getAmount() * 100.0;
                    }
                    if (amount > 0.0) {
                        list.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("attribute.modifier.plus." + attributeModifier2.getOperation(), ItemStack.DECIMALFORMAT.format(amount2), StatCollector.translateToLocal("attribute.name." + (String)entry2.getKey())));
                    }
                    else {
                        if (amount >= 0.0) {
                            continue;
                        }
                        list.add(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted("attribute.modifier.take." + attributeModifier2.getOperation(), ItemStack.DECIMALFORMAT.format(amount2 * -1.0), StatCollector.translateToLocal("attribute.name." + (String)entry2.getKey())));
                    }
                }
            }
        }
    }
    
    @Override
    public int getMaxItemUseDuration(final ItemStack itemStack) {
        return 32;
    }
    
    public boolean isEffectInstant(final int n) {
        final List effects = this.getEffects(n);
        if (effects != null && !effects.isEmpty()) {
            final Iterator<PotionEffect> iterator = effects.iterator();
            while (iterator.hasNext()) {
                if (Potion.potionTypes[iterator.next().getPotionID()].isInstant()) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }
    
    @Override
    public ItemStack onItemUseFinish(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (!entityPlayer.capabilities.isCreativeMode) {
            --itemStack.stackSize;
        }
        if (!world.isRemote) {
            final List effects = this.getEffects(itemStack);
            if (effects != null) {
                final Iterator<PotionEffect> iterator = effects.iterator();
                while (iterator.hasNext()) {
                    entityPlayer.addPotionEffect(new PotionEffect(iterator.next()));
                }
            }
        }
        entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        if (!entityPlayer.capabilities.isCreativeMode) {
            if (itemStack.stackSize <= 0) {
                return new ItemStack(Items.glass_bottle);
            }
            entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
        }
        return itemStack;
    }
    
    public List getEffects(final ItemStack itemStack) {
        if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("CustomPotionEffects", 9)) {
            final ArrayList arrayList = Lists.newArrayList();
            final NBTTagList tagList = itemStack.getTagCompound().getTagList("CustomPotionEffects", 10);
            while (0 < tagList.tagCount()) {
                final PotionEffect customPotionEffectFromNBT = PotionEffect.readCustomPotionEffectFromNBT(tagList.getCompoundTagAt(0));
                if (customPotionEffectFromNBT != null) {
                    arrayList.add(customPotionEffectFromNBT);
                }
                int n = 0;
                ++n;
            }
            return arrayList;
        }
        List potionEffects = this.effectCache.get(itemStack.getMetadata());
        if (potionEffects == null) {
            potionEffects = PotionHelper.getPotionEffects(itemStack.getMetadata(), false);
            this.effectCache.put(itemStack.getMetadata(), potionEffects);
        }
        return potionEffects;
    }
    
    static {
        SUB_ITEMS_CACHE = Maps.newLinkedHashMap();
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (itemStack.getMetadata() != 0) {
            if (!entityPlayer.capabilities.isCreativeMode) {
                --itemStack.stackSize;
            }
            world.playSoundAtEntity(entityPlayer, "random.bow", 0.5f, 0.4f / (ItemPotion.itemRand.nextFloat() * 0.4f + 0.8f));
            if (!world.isRemote) {
                world.spawnEntityInWorld(new EntityPotion(world, entityPlayer, itemStack));
            }
            entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
            return itemStack;
        }
        entityPlayer.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
        return itemStack;
    }
}
