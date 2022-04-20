package net.minecraft.item;

import net.minecraft.block.*;
import net.minecraft.entity.item.*;
import java.text.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.event.*;
import net.minecraft.nbt.*;
import com.google.common.collect.*;
import net.minecraft.init.*;
import net.minecraft.enchantment.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public final class ItemStack
{
    public int stackSize;
    private boolean canPlaceOnCacheResult;
    private Block canDestroyCacheBlock;
    private EntityItemFrame itemFrame;
    public int animationsToGo;
    private Block canPlaceOnCacheBlock;
    public static final DecimalFormat DECIMALFORMAT;
    private NBTTagCompound stackTagCompound;
    private int itemDamage;
    private boolean canDestroyCacheResult;
    private Item item;
    
    public boolean canDestroy(final Block canDestroyCacheBlock) {
        if (canDestroyCacheBlock == this.canDestroyCacheBlock) {
            return this.canDestroyCacheResult;
        }
        this.canDestroyCacheBlock = canDestroyCacheBlock;
        if (this != null && this.stackTagCompound.hasKey("CanDestroy", 9)) {
            final NBTTagList tagList = this.stackTagCompound.getTagList("CanDestroy", 8);
            while (0 < tagList.tagCount()) {
                if (Block.getBlockFromName(tagList.getStringTagAt(0)) == canDestroyCacheBlock) {
                    return this.canDestroyCacheResult = true;
                }
                int n = 0;
                ++n;
            }
        }
        return this.canDestroyCacheResult = false;
    }
    
    public Multimap getAttributeModifiers() {
        Object o;
        if (this != null && this.stackTagCompound.hasKey("AttributeModifiers", 9)) {
            o = HashMultimap.create();
            final NBTTagList tagList = this.stackTagCompound.getTagList("AttributeModifiers", 10);
            while (0 < tagList.tagCount()) {
                final NBTTagCompound compoundTag = tagList.getCompoundTagAt(0);
                final AttributeModifier attributeModifierFromNBT = SharedMonsterAttributes.readAttributeModifierFromNBT(compoundTag);
                if (attributeModifierFromNBT != null && attributeModifierFromNBT.getID().getLeastSignificantBits() != 0L && attributeModifierFromNBT.getID().getMostSignificantBits() != 0L) {
                    ((Multimap)o).put((Object)compoundTag.getString("AttributeName"), (Object)attributeModifierFromNBT);
                }
                int n = 0;
                ++n;
            }
        }
        else {
            o = this.getItem().getItemAttributeModifiers();
        }
        return (Multimap)o;
    }
    
    public ItemStack(final Block block, final int n) {
        this(block, n, 0);
    }
    
    public void setItemFrame(final EntityItemFrame itemFrame) {
        this.itemFrame = itemFrame;
    }
    
    public boolean isItemEqual(final ItemStack itemStack) {
        return itemStack != null && this.item == itemStack.item && this.itemDamage == itemStack.itemDamage;
    }
    
    public float getStrVsBlock(final Block block) {
        return this.getItem().getStrVsBlock(this, block);
    }
    
    public void hitEntity(final EntityLivingBase entityLivingBase, final EntityPlayer entityPlayer) {
        if (this.item.hitEntity(this, entityLivingBase, entityPlayer)) {
            entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
        }
    }
    
    public ItemStack(final Block block, final int n, final int n2) {
        this(Item.getItemFromBlock(block), n, n2);
    }
    
    static {
        DECIMALFORMAT = new DecimalFormat("#.###");
    }
    
    public boolean isItemEnchantable() {
        return this.getItem().isItemTool(this) && this != null;
    }
    
    public ItemStack(final Item item, final int stackSize, final int itemDamage) {
        this.canDestroyCacheBlock = null;
        this.canDestroyCacheResult = false;
        this.canPlaceOnCacheBlock = null;
        this.canPlaceOnCacheResult = false;
        this.item = item;
        this.stackSize = stackSize;
        this.itemDamage = itemDamage;
        if (this.itemDamage < 0) {
            this.itemDamage = 0;
        }
    }
    
    public ItemStack onItemUseFinish(final World world, final EntityPlayer entityPlayer) {
        return this.getItem().onItemUseFinish(this, world, entityPlayer);
    }
    
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        if (nbtTagCompound.hasKey("id", 8)) {
            this.item = Item.getByNameOrId(nbtTagCompound.getString("id"));
        }
        else {
            this.item = Item.getItemById(nbtTagCompound.getShort("id"));
        }
        this.stackSize = nbtTagCompound.getByte("Count");
        this.itemDamage = nbtTagCompound.getShort("Damage");
        if (this.itemDamage < 0) {
            this.itemDamage = 0;
        }
        if (nbtTagCompound.hasKey("tag", 10)) {
            this.stackTagCompound = nbtTagCompound.getCompoundTag("tag");
            if (this.item != null) {
                this.item.updateItemStackNBT(this.stackTagCompound);
            }
        }
    }
    
    public boolean getIsItemStackEqual(final ItemStack itemStack) {
        return this.isItemStackEqual(itemStack);
    }
    
    public boolean hasEffect() {
        return this.getItem().hasEffect(this);
    }
    
    public int getMaxDamage() {
        return this.item.getMaxDamage();
    }
    
    public String getUnlocalizedName() {
        return this.item.getUnlocalizedName(this);
    }
    
    public IChatComponent getChatComponent() {
        final ChatComponentText chatComponentText = new ChatComponentText(this.getDisplayName());
        if (this == null) {
            chatComponentText.getChatStyle().setItalic(true);
        }
        final IChatComponent appendText = new ChatComponentText("[").appendSibling(chatComponentText).appendText("]");
        if (this.item != null) {
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            this.writeToNBT(nbtTagCompound);
            appendText.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ChatComponentText(nbtTagCompound.toString())));
            appendText.getChatStyle().setColor(this.getRarity().rarityColor);
        }
        return appendText;
    }
    
    public void setTagCompound(final NBTTagCompound stackTagCompound) {
        this.stackTagCompound = stackTagCompound;
    }
    
    public void onBlockDestroyed(final World world, final Block block, final BlockPos blockPos, final EntityPlayer entityPlayer) {
        if (this.item.onBlockDestroyed(this, world, block, blockPos, entityPlayer)) {
            entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
        }
    }
    
    public void onPlayerStoppedUsing(final World world, final EntityPlayer entityPlayer, final int n) {
        this.getItem().onPlayerStoppedUsing(this, world, entityPlayer, n);
    }
    
    public ItemStack setStackDisplayName(final String s) {
        if (this.stackTagCompound == null) {
            this.stackTagCompound = new NBTTagCompound();
        }
        if (!this.stackTagCompound.hasKey("display", 10)) {
            this.stackTagCompound.setTag("display", new NBTTagCompound());
        }
        this.stackTagCompound.getCompoundTag("display").setString("Name", s);
        return this;
    }
    
    public NBTTagCompound getTagCompound() {
        return this.stackTagCompound;
    }
    
    public boolean getHasSubtypes() {
        return this.item.getHasSubtypes();
    }
    
    public boolean canHarvestBlock(final Block block) {
        return this.item.canHarvestBlock(block);
    }
    
    public EntityItemFrame getItemFrame() {
        return this.itemFrame;
    }
    
    public int getItemDamage() {
        return this.itemDamage;
    }
    
    public EnumAction getItemUseAction() {
        return this.getItem().getItemUseAction(this);
    }
    
    public void clearCustomName() {
        if (this.stackTagCompound != null && this.stackTagCompound.hasKey("display", 10)) {
            final NBTTagCompound compoundTag = this.stackTagCompound.getCompoundTag("display");
            compoundTag.removeTag("Name");
            if (compoundTag.hasNoTags()) {
                this.stackTagCompound.removeTag("display");
                if (this.stackTagCompound.hasNoTags()) {
                    this.setTagCompound(null);
                }
            }
        }
    }
    
    public void setTagInfo(final String s, final NBTBase nbtBase) {
        if (this.stackTagCompound == null) {
            this.setTagCompound(new NBTTagCompound());
        }
        this.stackTagCompound.setTag(s, nbtBase);
    }
    
    public void setRepairCost(final int n) {
        if (this != null) {
            this.stackTagCompound = new NBTTagCompound();
        }
        this.stackTagCompound.setInteger("RepairCost", n);
    }
    
    public ItemStack(final Item item) {
        this(item, 1);
    }
    
    public static boolean areItemsEqual(final ItemStack itemStack, final ItemStack itemStack2) {
        return (itemStack == null && itemStack2 == null) || (itemStack != null && itemStack2 != null && itemStack.isItemEqual(itemStack2));
    }
    
    public void setItem(final Item item) {
        this.item = item;
    }
    
    public boolean isStackable() {
        return this.getMaxStackSize() > 1 && (this != null || this == null);
    }
    
    public int getRepairCost() {
        return (this != null && this.stackTagCompound.hasKey("RepairCost", 3)) ? this.stackTagCompound.getInteger("RepairCost") : 0;
    }
    
    public boolean isOnItemFrame() {
        return this.itemFrame != null;
    }
    
    public static ItemStack copyItemStack(final ItemStack itemStack) {
        return (itemStack == null) ? null : itemStack.copy();
    }
    
    @Override
    public String toString() {
        return this.stackSize + "x" + this.item.getUnlocalizedName() + "@" + this.itemDamage;
    }
    
    public ItemStack splitStack(final int n) {
        final ItemStack itemStack = new ItemStack(this.item, n, this.itemDamage);
        if (this.stackTagCompound != null) {
            itemStack.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
        }
        this.stackSize -= n;
        return itemStack;
    }
    
    public void onCrafting(final World world, final EntityPlayer entityPlayer, final int n) {
        entityPlayer.addStat(StatList.objectCraftStats[Item.getIdFromItem(this.item)], n);
        this.item.onCreated(this, world, entityPlayer);
    }
    
    public ItemStack useItemRightClick(final World world, final EntityPlayer entityPlayer) {
        return this.getItem().onItemRightClick(this, world, entityPlayer);
    }
    
    public ItemStack copy() {
        final ItemStack itemStack = new ItemStack(this.item, this.stackSize, this.itemDamage);
        if (this.stackTagCompound != null) {
            itemStack.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
        }
        return itemStack;
    }
    
    public ItemStack(final Block block) {
        this(block, 1);
    }
    
    public ItemStack(final Item item, final int n) {
        this(item, n, 0);
    }
    
    public void damageItem(final int p0, final EntityLivingBase p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: instanceof      Lnet/minecraft/entity/player/EntityPlayer;
        //     4: ifeq            20
        //     7: aload_2        
        //     8: checkcast       Lnet/minecraft/entity/player/EntityPlayer;
        //    11: getfield        net/minecraft/entity/player/EntityPlayer.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //    14: getfield        net/minecraft/entity/player/PlayerCapabilities.isCreativeMode:Z
        //    17: ifne            113
        //    20: aload_0        
        //    21: ifnonnull       113
        //    24: aload_0        
        //    25: iload_1        
        //    26: aload_2        
        //    27: invokevirtual   net/minecraft/entity/EntityLivingBase.getRNG:()Ljava/util/Random;
        //    30: ifnonnull       113
        //    33: aload_2        
        //    34: aload_0        
        //    35: invokevirtual   net/minecraft/entity/EntityLivingBase.renderBrokenItemStack:(Lnet/minecraft/item/ItemStack;)V
        //    38: aload_0        
        //    39: dup            
        //    40: getfield        net/minecraft/item/ItemStack.stackSize:I
        //    43: iconst_1       
        //    44: isub           
        //    45: putfield        net/minecraft/item/ItemStack.stackSize:I
        //    48: aload_2        
        //    49: instanceof      Lnet/minecraft/entity/player/EntityPlayer;
        //    52: ifeq            96
        //    55: aload_2        
        //    56: checkcast       Lnet/minecraft/entity/player/EntityPlayer;
        //    59: astore_3       
        //    60: aload_3        
        //    61: getstatic       net/minecraft/stats/StatList.objectBreakStats:[Lnet/minecraft/stats/StatBase;
        //    64: aload_0        
        //    65: getfield        net/minecraft/item/ItemStack.item:Lnet/minecraft/item/Item;
        //    68: invokestatic    net/minecraft/item/Item.getIdFromItem:(Lnet/minecraft/item/Item;)I
        //    71: aaload         
        //    72: invokevirtual   net/minecraft/entity/player/EntityPlayer.triggerAchievement:(Lnet/minecraft/stats/StatBase;)V
        //    75: aload_0        
        //    76: getfield        net/minecraft/item/ItemStack.stackSize:I
        //    79: ifne            96
        //    82: aload_0        
        //    83: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //    86: instanceof      Lnet/minecraft/item/ItemBow;
        //    89: ifeq            96
        //    92: aload_3        
        //    93: invokevirtual   net/minecraft/entity/player/EntityPlayer.destroyCurrentEquippedItem:()V
        //    96: aload_0        
        //    97: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   100: ifge            108
        //   103: aload_0        
        //   104: iconst_0       
        //   105: putfield        net/minecraft/item/ItemStack.stackSize:I
        //   108: aload_0        
        //   109: iconst_0       
        //   110: putfield        net/minecraft/item/ItemStack.itemDamage:I
        //   113: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0113 (coming from #0030).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
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
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public boolean canEditBlocks() {
        return this.getItem().canItemEditBlocks();
    }
    
    public boolean canPlaceOn(final Block canPlaceOnCacheBlock) {
        if (canPlaceOnCacheBlock == this.canPlaceOnCacheBlock) {
            return this.canPlaceOnCacheResult;
        }
        this.canPlaceOnCacheBlock = canPlaceOnCacheBlock;
        if (this != null && this.stackTagCompound.hasKey("CanPlaceOn", 9)) {
            final NBTTagList tagList = this.stackTagCompound.getTagList("CanPlaceOn", 8);
            while (0 < tagList.tagCount()) {
                if (Block.getBlockFromName(tagList.getStringTagAt(0)) == canPlaceOnCacheBlock) {
                    return this.canPlaceOnCacheResult = true;
                }
                int n = 0;
                ++n;
            }
        }
        return this.canPlaceOnCacheResult = false;
    }
    
    public void addEnchantment(final Enchantment enchantment, final int n) {
        if (this.stackTagCompound == null) {
            this.setTagCompound(new NBTTagCompound());
        }
        if (!this.stackTagCompound.hasKey("ench", 9)) {
            this.stackTagCompound.setTag("ench", new NBTTagList());
        }
        final NBTTagList tagList = this.stackTagCompound.getTagList("ench", 10);
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setShort("id", (short)enchantment.effectId);
        nbtTagCompound.setShort("lvl", (byte)n);
        tagList.appendTag(nbtTagCompound);
    }
    
    public NBTTagCompound writeToNBT(final NBTTagCompound nbtTagCompound) {
        final ResourceLocation resourceLocation = (ResourceLocation)Item.itemRegistry.getNameForObject(this.item);
        nbtTagCompound.setString("id", (resourceLocation == null) ? "minecraft:air" : resourceLocation.toString());
        nbtTagCompound.setByte("Count", (byte)this.stackSize);
        nbtTagCompound.setShort("Damage", (short)this.itemDamage);
        if (this.stackTagCompound != null) {
            nbtTagCompound.setTag("tag", this.stackTagCompound);
        }
        return nbtTagCompound;
    }
    
    public NBTTagList getEnchantmentTagList() {
        return (this.stackTagCompound == null) ? null : this.stackTagCompound.getTagList("ench", 10);
    }
    
    public static ItemStack loadItemStackFromNBT(final NBTTagCompound nbtTagCompound) {
        final ItemStack itemStack = new ItemStack();
        itemStack.readFromNBT(nbtTagCompound);
        return (itemStack.getItem() != null) ? itemStack : null;
    }
    
    private ItemStack() {
        this.canDestroyCacheBlock = null;
        this.canDestroyCacheResult = false;
        this.canPlaceOnCacheBlock = null;
        this.canPlaceOnCacheResult = false;
    }
    
    public int getMaxItemUseDuration() {
        return this.getItem().getMaxItemUseDuration(this);
    }
    
    public int getMaxStackSize() {
        return this.getItem().getItemStackLimit();
    }
    
    public NBTTagCompound getSubCompound(final String s, final boolean b) {
        if (this.stackTagCompound != null && this.stackTagCompound.hasKey(s, 10)) {
            return this.stackTagCompound.getCompoundTag(s);
        }
        if (b) {
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            this.setTagInfo(s, nbtTagCompound);
            return nbtTagCompound;
        }
        return null;
    }
    
    public List getTooltip(final EntityPlayer entityPlayer, final boolean b) {
        final ArrayList arrayList = Lists.newArrayList();
        String s = this.getDisplayName();
        if (this == null) {
            s = EnumChatFormatting.ITALIC + s;
        }
        String s2 = s + EnumChatFormatting.RESET;
        if (b) {
            String s3 = "";
            if (s2.length() > 0) {
                s2 += " (";
                s3 = ")";
            }
            final int idFromItem = Item.getIdFromItem(this.item);
            if (this.getHasSubtypes()) {
                s2 += String.format("#%04d/%d%s", idFromItem, this.itemDamage, s3);
            }
            else {
                s2 += String.format("#%04d%s", idFromItem, s3);
            }
        }
        else if (this == null && this.item == Items.filled_map) {
            s2 = s2 + " #" + this.itemDamage;
        }
        arrayList.add(s2);
        if (this != null && this.stackTagCompound.hasKey("HideFlags", 99)) {
            this.stackTagCompound.getInteger("HideFlags");
        }
        this.item.addInformation(this, entityPlayer, arrayList, b);
        int short1 = 0;
        if (this != null) {
            final NBTTagList enchantmentTagList = this.getEnchantmentTagList();
            if (enchantmentTagList != null) {
                while (0 < enchantmentTagList.tagCount()) {
                    short1 = enchantmentTagList.getCompoundTagAt(0).getShort("id");
                    final short short2 = enchantmentTagList.getCompoundTagAt(0).getShort("lvl");
                    if (Enchantment.getEnchantmentById(0) != null) {
                        arrayList.add(Enchantment.getEnchantmentById(0).getTranslatedName(short2));
                    }
                    int n = 0;
                    ++n;
                }
            }
            if (this.stackTagCompound.hasKey("display", 10)) {
                final NBTTagCompound compoundTag = this.stackTagCompound.getCompoundTag("display");
                if (compoundTag.hasKey("color", 3)) {
                    if (b) {
                        arrayList.add("Color: #" + Integer.toHexString(compoundTag.getInteger("color")).toUpperCase());
                    }
                    else {
                        arrayList.add(EnumChatFormatting.ITALIC + "\ue481\ue49c\ue48d\ue485\ue4c6\ue48c\ue491\ue48d\ue48c");
                    }
                }
                if (compoundTag.getTagId("Lore") == 9) {
                    final NBTTagList tagList = compoundTag.getTagList("Lore", 8);
                    if (tagList.tagCount() > 0) {
                        while (0 < tagList.tagCount()) {
                            arrayList.add(EnumChatFormatting.DARK_PURPLE + "" + EnumChatFormatting.ITALIC + tagList.getStringTagAt(0));
                            ++short1;
                        }
                    }
                }
            }
        }
        final Multimap attributeModifiers = this.getAttributeModifiers();
        if (!attributeModifiers.isEmpty()) {
            arrayList.add("");
            for (final Map.Entry<K, AttributeModifier> entry : attributeModifiers.entries()) {
                final AttributeModifier attributeModifier = entry.getValue();
                double amount = attributeModifier.getAmount();
                if (attributeModifier.getID() == Item.itemModifierUUID) {
                    amount += EnchantmentHelper.func_152377_a(this, EnumCreatureAttribute.UNDEFINED);
                }
                double n2;
                if (attributeModifier.getOperation() != 1 && attributeModifier.getOperation() != 2) {
                    n2 = amount;
                }
                else {
                    n2 = amount * 100.0;
                }
                if (amount > 0.0) {
                    arrayList.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("attribute.modifier.plus." + attributeModifier.getOperation(), ItemStack.DECIMALFORMAT.format(n2), StatCollector.translateToLocal("attribute.name." + (String)entry.getKey())));
                }
                else {
                    if (amount >= 0.0) {
                        continue;
                    }
                    arrayList.add(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted("attribute.modifier.take." + attributeModifier.getOperation(), ItemStack.DECIMALFORMAT.format(n2 * -1.0), StatCollector.translateToLocal("attribute.name." + (String)entry.getKey())));
                }
            }
        }
        if (this != null && this.getTagCompound().getBoolean("Unbreakable")) {
            arrayList.add(EnumChatFormatting.BLUE + "\ue481\ue49c\ue48d\ue485\ue4c6\ue49d\ue486\ue48a\ue49a\ue48d\ue489\ue483\ue489\ue48a\ue484\ue48d");
        }
        if (this != null && this.stackTagCompound.hasKey("CanDestroy", 9)) {
            final NBTTagList tagList2 = this.stackTagCompound.getTagList("CanDestroy", 8);
            if (tagList2.tagCount() > 0) {
                arrayList.add("");
                arrayList.add(EnumChatFormatting.GRAY + "\ue481\ue49c\ue48d\ue485\ue4c6\ue48b\ue489\ue486\ue4aa\ue49a\ue48d\ue489\ue483");
                while (0 < tagList2.tagCount()) {
                    final Block blockFromName = Block.getBlockFromName(tagList2.getStringTagAt(0));
                    if (blockFromName != null) {
                        arrayList.add(EnumChatFormatting.DARK_GRAY + blockFromName.getLocalizedName());
                    }
                    else {
                        arrayList.add(EnumChatFormatting.DARK_GRAY + "missingno");
                    }
                    ++short1;
                }
            }
        }
        if (this != null && this.stackTagCompound.hasKey("CanPlaceOn", 9)) {
            final NBTTagList tagList3 = this.stackTagCompound.getTagList("CanPlaceOn", 8);
            if (tagList3.tagCount() > 0) {
                arrayList.add("");
                arrayList.add(EnumChatFormatting.GRAY + "\ue481\ue49c\ue48d\ue485\ue4c6\ue48b\ue489\ue486\ue4b8\ue484\ue489\ue48b\ue48d");
                while (0 < tagList3.tagCount()) {
                    final Block blockFromName2 = Block.getBlockFromName(tagList3.getStringTagAt(0));
                    if (blockFromName2 != null) {
                        arrayList.add(EnumChatFormatting.DARK_GRAY + blockFromName2.getLocalizedName());
                    }
                    else {
                        arrayList.add(EnumChatFormatting.DARK_GRAY + "missingno");
                    }
                    ++short1;
                }
            }
        }
        if (b) {
            if (this == null) {
                arrayList.add("Durability: " + (this.getMaxDamage() - this.getItemDamage()) + " / " + this.getMaxDamage());
            }
            arrayList.add(EnumChatFormatting.DARK_GRAY + ((ResourceLocation)Item.itemRegistry.getNameForObject(this.item)).toString());
            if (this != null) {
                arrayList.add(EnumChatFormatting.DARK_GRAY + "NBT: " + this.getTagCompound().getKeySet().size() + " tag(s)");
            }
        }
        return arrayList;
    }
    
    public String getDisplayName() {
        String s = this.getItem().getItemStackDisplayName(this);
        if (this.stackTagCompound != null && this.stackTagCompound.hasKey("display", 10)) {
            final NBTTagCompound compoundTag = this.stackTagCompound.getCompoundTag("display");
            if (compoundTag.hasKey("Name", 8)) {
                s = compoundTag.getString("Name");
            }
        }
        return s;
    }
    
    public int getMetadata() {
        return this.itemDamage;
    }
    
    public static boolean areItemStackTagsEqual(final ItemStack itemStack, final ItemStack itemStack2) {
        return (itemStack == null && itemStack2 == null) || (itemStack != null && itemStack2 != null && (itemStack.stackTagCompound != null || itemStack2.stackTagCompound == null) && (itemStack.stackTagCompound == null || itemStack.stackTagCompound.equals(itemStack2.stackTagCompound)));
    }
    
    public static boolean areItemStacksEqual(final ItemStack itemStack, final ItemStack itemStack2) {
        return (itemStack == null && itemStack2 == null) || (itemStack != null && itemStack2 != null && itemStack.isItemStackEqual(itemStack2));
    }
    
    public boolean onItemUse(final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        final boolean onItemUse = this.getItem().onItemUse(this, entityPlayer, world, blockPos, enumFacing, n, n2, n3);
        if (onItemUse) {
            entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
        }
        return onItemUse;
    }
    
    public void updateAnimation(final World world, final Entity entity, final int n, final boolean b) {
        if (this.animationsToGo > 0) {
            --this.animationsToGo;
        }
        this.item.onUpdate(this, world, entity, n, b);
    }
    
    public void setItemDamage(final int itemDamage) {
        this.itemDamage = itemDamage;
        if (this.itemDamage < 0) {
            this.itemDamage = 0;
        }
    }
    
    public Item getItem() {
        return this.item;
    }
    
    private boolean isItemStackEqual(final ItemStack itemStack) {
        return this.stackSize == itemStack.stackSize && this.item == itemStack.item && this.itemDamage == itemStack.itemDamage && (this.stackTagCompound != null || itemStack.stackTagCompound == null) && (this.stackTagCompound == null || this.stackTagCompound.equals(itemStack.stackTagCompound));
    }
    
    public EnumRarity getRarity() {
        return this.getItem().getRarity(this);
    }
    
    public boolean interactWithEntity(final EntityPlayer entityPlayer, final EntityLivingBase entityLivingBase) {
        return this.item.itemInteractionForEntity(this, entityPlayer, entityLivingBase);
    }
}
