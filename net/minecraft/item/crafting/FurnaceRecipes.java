package net.minecraft.item.crafting;

import java.util.*;
import com.google.common.collect.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.item.*;

public class FurnaceRecipes
{
    private Map experienceList;
    private static final FurnaceRecipes smeltingBase;
    private Map smeltingList;
    
    public float getSmeltingExperience(final ItemStack p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/item/crafting/FurnaceRecipes.experienceList:Ljava/util/Map;
        //     4: invokeinterface java/util/Map.entrySet:()Ljava/util/Set;
        //     9: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //    14: astore_2       
        //    15: aload_2        
        //    16: invokeinterface java/util/Iterator.hasNext:()Z
        //    21: ifeq            64
        //    24: aload_2        
        //    25: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    30: checkcast       Ljava/util/Map$Entry;
        //    33: astore_3       
        //    34: aload_0        
        //    35: aload_1        
        //    36: aload_3        
        //    37: invokeinterface java/util/Map$Entry.getKey:()Ljava/lang/Object;
        //    42: checkcast       Lnet/minecraft/item/ItemStack;
        //    45: if_acmpne       61
        //    48: aload_3        
        //    49: invokeinterface java/util/Map$Entry.getValue:()Ljava/lang/Object;
        //    54: checkcast       Ljava/lang/Float;
        //    57: invokevirtual   java/lang/Float.floatValue:()F
        //    60: freturn        
        //    61: goto            15
        //    64: fconst_0       
        //    65: freturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0015 (coming from #0061).
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
    
    private FurnaceRecipes() {
        this.smeltingList = Maps.newHashMap();
        this.experienceList = Maps.newHashMap();
        this.addSmeltingRecipeForBlock(Blocks.iron_ore, new ItemStack(Items.iron_ingot), 0.7f);
        this.addSmeltingRecipeForBlock(Blocks.gold_ore, new ItemStack(Items.gold_ingot), 1.0f);
        this.addSmeltingRecipeForBlock(Blocks.diamond_ore, new ItemStack(Items.diamond), 1.0f);
        this.addSmeltingRecipeForBlock(Blocks.sand, new ItemStack(Blocks.glass), 0.1f);
        this.addSmelting(Items.porkchop, new ItemStack(Items.cooked_porkchop), 0.35f);
        this.addSmelting(Items.beef, new ItemStack(Items.cooked_beef), 0.35f);
        this.addSmelting(Items.chicken, new ItemStack(Items.cooked_chicken), 0.35f);
        this.addSmelting(Items.rabbit, new ItemStack(Items.cooked_rabbit), 0.35f);
        this.addSmelting(Items.mutton, new ItemStack(Items.cooked_mutton), 0.35f);
        this.addSmeltingRecipeForBlock(Blocks.cobblestone, new ItemStack(Blocks.stone), 0.1f);
        this.addSmeltingRecipe(new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.DEFAULT_META), new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.CRACKED_META), 0.1f);
        this.addSmelting(Items.clay_ball, new ItemStack(Items.brick), 0.3f);
        this.addSmeltingRecipeForBlock(Blocks.clay, new ItemStack(Blocks.hardened_clay), 0.35f);
        this.addSmeltingRecipeForBlock(Blocks.cactus, new ItemStack(Items.dye, 1, EnumDyeColor.GREEN.getDyeDamage()), 0.2f);
        this.addSmeltingRecipeForBlock(Blocks.log, new ItemStack(Items.coal, 1, 1), 0.15f);
        this.addSmeltingRecipeForBlock(Blocks.log2, new ItemStack(Items.coal, 1, 1), 0.15f);
        this.addSmeltingRecipeForBlock(Blocks.emerald_ore, new ItemStack(Items.emerald), 1.0f);
        this.addSmelting(Items.potato, new ItemStack(Items.baked_potato), 0.35f);
        this.addSmeltingRecipeForBlock(Blocks.netherrack, new ItemStack(Items.netherbrick), 0.1f);
        this.addSmeltingRecipe(new ItemStack(Blocks.sponge, 1, 1), new ItemStack(Blocks.sponge, 1, 0), 0.15f);
        final ItemFishFood.FishType[] values = ItemFishFood.FishType.values();
        while (0 < values.length) {
            final ItemFishFood.FishType fishType = values[0];
            if (fishType.canCook()) {
                this.addSmeltingRecipe(new ItemStack(Items.fish, 1, fishType.getMetadata()), new ItemStack(Items.cooked_fish, 1, fishType.getMetadata()), 0.35f);
            }
            int n = 0;
            ++n;
        }
        this.addSmeltingRecipeForBlock(Blocks.coal_ore, new ItemStack(Items.coal), 0.1f);
        this.addSmeltingRecipeForBlock(Blocks.redstone_ore, new ItemStack(Items.redstone), 0.7f);
        this.addSmeltingRecipeForBlock(Blocks.lapis_ore, new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeDamage()), 0.2f);
        this.addSmeltingRecipeForBlock(Blocks.quartz_ore, new ItemStack(Items.quartz), 0.2f);
    }
    
    public static FurnaceRecipes instance() {
        return FurnaceRecipes.smeltingBase;
    }
    
    public void addSmelting(final Item item, final ItemStack itemStack, final float n) {
        this.addSmeltingRecipe(new ItemStack(item, 1, 32767), itemStack, n);
    }
    
    public ItemStack getSmeltingResult(final ItemStack p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/item/crafting/FurnaceRecipes.smeltingList:Ljava/util/Map;
        //     4: invokeinterface java/util/Map.entrySet:()Ljava/util/Set;
        //     9: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //    14: astore_2       
        //    15: aload_2        
        //    16: invokeinterface java/util/Iterator.hasNext:()Z
        //    21: ifeq            61
        //    24: aload_2        
        //    25: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    30: checkcast       Ljava/util/Map$Entry;
        //    33: astore_3       
        //    34: aload_0        
        //    35: aload_1        
        //    36: aload_3        
        //    37: invokeinterface java/util/Map$Entry.getKey:()Ljava/lang/Object;
        //    42: checkcast       Lnet/minecraft/item/ItemStack;
        //    45: if_acmpne       58
        //    48: aload_3        
        //    49: invokeinterface java/util/Map$Entry.getValue:()Ljava/lang/Object;
        //    54: checkcast       Lnet/minecraft/item/ItemStack;
        //    57: areturn        
        //    58: goto            15
        //    61: aconst_null    
        //    62: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0015 (coming from #0058).
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
    
    public void addSmeltingRecipeForBlock(final Block block, final ItemStack itemStack, final float n) {
        this.addSmelting(Item.getItemFromBlock(block), itemStack, n);
    }
    
    static {
        smeltingBase = new FurnaceRecipes();
    }
    
    public void addSmeltingRecipe(final ItemStack itemStack, final ItemStack itemStack2, final float n) {
        this.smeltingList.put(itemStack, itemStack2);
        this.experienceList.put(itemStack2, n);
    }
    
    public Map getSmeltingList() {
        return this.smeltingList;
    }
}
