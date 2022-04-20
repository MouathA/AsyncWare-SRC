package net.minecraft.util;

import net.minecraft.inventory.*;
import net.minecraft.tileentity.*;
import net.minecraft.item.*;
import com.google.common.collect.*;
import java.util.*;

public class WeightedRandomChestContent extends WeightedRandom.Item
{
    private int maxStackSize;
    private ItemStack theItemId;
    private int minStackSize;
    
    public static void generateChestContents(final Random p0, final List p1, final IInventory p2, final int p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: iload_3        
        //     2: if_icmpge       143
        //     5: aload_0        
        //     6: aload_1        
        //     7: invokestatic    net/minecraft/util/WeightedRandom.getRandomItem:(Ljava/util/Random;Ljava/util/Collection;)Lnet/minecraft/util/WeightedRandom$Item;
        //    10: checkcast       Lnet/minecraft/util/WeightedRandomChestContent;
        //    13: astore          5
        //    15: aload           5
        //    17: getfield        net/minecraft/util/WeightedRandomChestContent.minStackSize:I
        //    20: aload_0        
        //    21: aload           5
        //    23: getfield        net/minecraft/util/WeightedRandomChestContent.maxStackSize:I
        //    26: aload           5
        //    28: getfield        net/minecraft/util/WeightedRandomChestContent.minStackSize:I
        //    31: isub           
        //    32: iconst_1       
        //    33: iadd           
        //    34: invokevirtual   java/util/Random.nextInt:(I)I
        //    37: iadd           
        //    38: istore          6
        //    40: aload           5
        //    42: getfield        net/minecraft/util/WeightedRandomChestContent.theItemId:Lnet/minecraft/item/ItemStack;
        //    45: invokevirtual   net/minecraft/item/ItemStack.getMaxStackSize:()I
        //    48: iload           6
        //    50: if_icmplt       91
        //    53: aload           5
        //    55: getfield        net/minecraft/util/WeightedRandomChestContent.theItemId:Lnet/minecraft/item/ItemStack;
        //    58: invokevirtual   net/minecraft/item/ItemStack.copy:()Lnet/minecraft/item/ItemStack;
        //    61: astore          7
        //    63: aload           7
        //    65: iload           6
        //    67: putfield        net/minecraft/item/ItemStack.stackSize:I
        //    70: aload_2        
        //    71: aload_0        
        //    72: aload_2        
        //    73: invokeinterface net/minecraft/inventory/IInventory.getSizeInventory:()I
        //    78: invokevirtual   java/util/Random.nextInt:(I)I
        //    81: aload           7
        //    83: invokeinterface net/minecraft/inventory/IInventory.setInventorySlotContents:(ILnet/minecraft/item/ItemStack;)V
        //    88: goto            137
        //    91: iconst_0       
        //    92: iload           6
        //    94: if_icmpge       137
        //    97: aload           5
        //    99: getfield        net/minecraft/util/WeightedRandomChestContent.theItemId:Lnet/minecraft/item/ItemStack;
        //   102: invokevirtual   net/minecraft/item/ItemStack.copy:()Lnet/minecraft/item/ItemStack;
        //   105: astore          8
        //   107: aload           8
        //   109: iconst_1       
        //   110: putfield        net/minecraft/item/ItemStack.stackSize:I
        //   113: aload_2        
        //   114: aload_0        
        //   115: aload_2        
        //   116: invokeinterface net/minecraft/inventory/IInventory.getSizeInventory:()I
        //   121: invokevirtual   java/util/Random.nextInt:(I)I
        //   124: aload           8
        //   126: invokeinterface net/minecraft/inventory/IInventory.setInventorySlotContents:(ILnet/minecraft/item/ItemStack;)V
        //   131: iinc            7, 1
        //   134: goto            91
        //   137: iinc            4, 1
        //   140: goto            0
        //   143: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2895)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
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
    
    public static void generateDispenserContents(final Random p0, final List p1, final TileEntityDispenser p2, final int p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: iload_3        
        //     2: if_icmpge       135
        //     5: aload_0        
        //     6: aload_1        
        //     7: invokestatic    net/minecraft/util/WeightedRandom.getRandomItem:(Ljava/util/Random;Ljava/util/Collection;)Lnet/minecraft/util/WeightedRandom$Item;
        //    10: checkcast       Lnet/minecraft/util/WeightedRandomChestContent;
        //    13: astore          5
        //    15: aload           5
        //    17: getfield        net/minecraft/util/WeightedRandomChestContent.minStackSize:I
        //    20: aload_0        
        //    21: aload           5
        //    23: getfield        net/minecraft/util/WeightedRandomChestContent.maxStackSize:I
        //    26: aload           5
        //    28: getfield        net/minecraft/util/WeightedRandomChestContent.minStackSize:I
        //    31: isub           
        //    32: iconst_1       
        //    33: iadd           
        //    34: invokevirtual   java/util/Random.nextInt:(I)I
        //    37: iadd           
        //    38: istore          6
        //    40: aload           5
        //    42: getfield        net/minecraft/util/WeightedRandomChestContent.theItemId:Lnet/minecraft/item/ItemStack;
        //    45: invokevirtual   net/minecraft/item/ItemStack.getMaxStackSize:()I
        //    48: iload           6
        //    50: if_icmplt       87
        //    53: aload           5
        //    55: getfield        net/minecraft/util/WeightedRandomChestContent.theItemId:Lnet/minecraft/item/ItemStack;
        //    58: invokevirtual   net/minecraft/item/ItemStack.copy:()Lnet/minecraft/item/ItemStack;
        //    61: astore          7
        //    63: aload           7
        //    65: iload           6
        //    67: putfield        net/minecraft/item/ItemStack.stackSize:I
        //    70: aload_2        
        //    71: aload_0        
        //    72: aload_2        
        //    73: invokevirtual   net/minecraft/tileentity/TileEntityDispenser.getSizeInventory:()I
        //    76: invokevirtual   java/util/Random.nextInt:(I)I
        //    79: aload           7
        //    81: invokevirtual   net/minecraft/tileentity/TileEntityDispenser.setInventorySlotContents:(ILnet/minecraft/item/ItemStack;)V
        //    84: goto            129
        //    87: iconst_0       
        //    88: iload           6
        //    90: if_icmpge       129
        //    93: aload           5
        //    95: getfield        net/minecraft/util/WeightedRandomChestContent.theItemId:Lnet/minecraft/item/ItemStack;
        //    98: invokevirtual   net/minecraft/item/ItemStack.copy:()Lnet/minecraft/item/ItemStack;
        //   101: astore          8
        //   103: aload           8
        //   105: iconst_1       
        //   106: putfield        net/minecraft/item/ItemStack.stackSize:I
        //   109: aload_2        
        //   110: aload_0        
        //   111: aload_2        
        //   112: invokevirtual   net/minecraft/tileentity/TileEntityDispenser.getSizeInventory:()I
        //   115: invokevirtual   java/util/Random.nextInt:(I)I
        //   118: aload           8
        //   120: invokevirtual   net/minecraft/tileentity/TileEntityDispenser.setInventorySlotContents:(ILnet/minecraft/item/ItemStack;)V
        //   123: iinc            7, 1
        //   126: goto            87
        //   129: iinc            4, 1
        //   132: goto            0
        //   135: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2895)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
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
    
    public WeightedRandomChestContent(final net.minecraft.item.Item item, final int n, final int minStackSize, final int maxStackSize, final int n2) {
        super(n2);
        this.theItemId = new ItemStack(item, 1, n);
        this.minStackSize = minStackSize;
        this.maxStackSize = maxStackSize;
    }
    
    public static List func_177629_a(final List list, final WeightedRandomChestContent... array) {
        final ArrayList arrayList = Lists.newArrayList((Iterable)list);
        Collections.addAll(arrayList, array);
        return arrayList;
    }
    
    public WeightedRandomChestContent(final ItemStack theItemId, final int minStackSize, final int maxStackSize, final int n) {
        super(n);
        this.theItemId = theItemId;
        this.minStackSize = minStackSize;
        this.maxStackSize = maxStackSize;
    }
}
