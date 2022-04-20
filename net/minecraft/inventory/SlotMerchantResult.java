package net.minecraft.inventory;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class SlotMerchantResult extends Slot
{
    private final IMerchant theMerchant;
    private int field_75231_g;
    private final InventoryMerchant theMerchantInventory;
    private EntityPlayer thePlayer;
    
    public SlotMerchantResult(final EntityPlayer thePlayer, final IMerchant theMerchant, final InventoryMerchant theMerchantInventory, final int n, final int n2, final int n3) {
        super(theMerchantInventory, n, n2, n3);
        this.thePlayer = thePlayer;
        this.theMerchant = theMerchant;
        this.theMerchantInventory = theMerchantInventory;
    }
    
    @Override
    public void onPickupFromSlot(final EntityPlayer p0, final ItemStack p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_2        
        //     2: invokevirtual   net/minecraft/inventory/SlotMerchantResult.onCrafting:(Lnet/minecraft/item/ItemStack;)V
        //     5: aload_0        
        //     6: getfield        net/minecraft/inventory/SlotMerchantResult.theMerchantInventory:Lnet/minecraft/inventory/InventoryMerchant;
        //     9: invokevirtual   net/minecraft/inventory/InventoryMerchant.getCurrentRecipe:()Lnet/minecraft/village/MerchantRecipe;
        //    12: astore_3       
        //    13: aload_3        
        //    14: ifnull          124
        //    17: aload_0        
        //    18: getfield        net/minecraft/inventory/SlotMerchantResult.theMerchantInventory:Lnet/minecraft/inventory/InventoryMerchant;
        //    21: iconst_0       
        //    22: invokevirtual   net/minecraft/inventory/InventoryMerchant.getStackInSlot:(I)Lnet/minecraft/item/ItemStack;
        //    25: astore          4
        //    27: aload_0        
        //    28: getfield        net/minecraft/inventory/SlotMerchantResult.theMerchantInventory:Lnet/minecraft/inventory/InventoryMerchant;
        //    31: iconst_1       
        //    32: invokevirtual   net/minecraft/inventory/InventoryMerchant.getStackInSlot:(I)Lnet/minecraft/item/ItemStack;
        //    35: astore          5
        //    37: aload_0        
        //    38: aload_3        
        //    39: aload           4
        //    41: aload           5
        //    43: ifnull          55
        //    46: aload_0        
        //    47: aload_3        
        //    48: aload           5
        //    50: aload           4
        //    52: ifnull          124
        //    55: aload_0        
        //    56: getfield        net/minecraft/inventory/SlotMerchantResult.theMerchant:Lnet/minecraft/entity/IMerchant;
        //    59: aload_3        
        //    60: invokeinterface net/minecraft/entity/IMerchant.useRecipe:(Lnet/minecraft/village/MerchantRecipe;)V
        //    65: aload_1        
        //    66: getstatic       net/minecraft/stats/StatList.timesTradedWithVillagerStat:Lnet/minecraft/stats/StatBase;
        //    69: invokevirtual   net/minecraft/entity/player/EntityPlayer.triggerAchievement:(Lnet/minecraft/stats/StatBase;)V
        //    72: aload           4
        //    74: ifnull          88
        //    77: aload           4
        //    79: getfield        net/minecraft/item/ItemStack.stackSize:I
        //    82: ifgt            88
        //    85: aconst_null    
        //    86: astore          4
        //    88: aload           5
        //    90: ifnull          104
        //    93: aload           5
        //    95: getfield        net/minecraft/item/ItemStack.stackSize:I
        //    98: ifgt            104
        //   101: aconst_null    
        //   102: astore          5
        //   104: aload_0        
        //   105: getfield        net/minecraft/inventory/SlotMerchantResult.theMerchantInventory:Lnet/minecraft/inventory/InventoryMerchant;
        //   108: iconst_0       
        //   109: aload           4
        //   111: invokevirtual   net/minecraft/inventory/InventoryMerchant.setInventorySlotContents:(ILnet/minecraft/item/ItemStack;)V
        //   114: aload_0        
        //   115: getfield        net/minecraft/inventory/SlotMerchantResult.theMerchantInventory:Lnet/minecraft/inventory/InventoryMerchant;
        //   118: iconst_1       
        //   119: aload           5
        //   121: invokevirtual   net/minecraft/inventory/InventoryMerchant.setInventorySlotContents:(ILnet/minecraft/item/ItemStack;)V
        //   124: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0124 (coming from #0121).
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
    
    @Override
    public boolean isItemValid(final ItemStack itemStack) {
        return false;
    }
    
    @Override
    protected void onCrafting(final ItemStack itemStack) {
        itemStack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.field_75231_g);
        this.field_75231_g = 0;
    }
    
    @Override
    public ItemStack decrStackSize(final int n) {
        if (this.getHasStack()) {
            this.field_75231_g += Math.min(n, this.getStack().stackSize);
        }
        return super.decrStackSize(n);
    }
    
    @Override
    protected void onCrafting(final ItemStack itemStack, final int n) {
        this.field_75231_g += n;
        this.onCrafting(itemStack);
    }
}
