package com.nquantum.module.player;

import com.nquantum.module.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import com.nquantum.event.impl.*;
import com.nquantum.event.*;

public class AutoSoup extends Module
{
    public AutoSoup() {
        super("AutoSoup", 0, Category.PLAYER);
    }
    
    private void stackBowls() {
        if (this.mc.currentScreen instanceof GuiChest) {
            return;
        }
        while (true) {
            final ItemStack stack = this.mc.thePlayer.inventoryContainer.getSlot(9).getStack();
            if (stack != null && stack.getItem() == Items.bowl) {
                this.mc.playerController.windowClick(0, 9, 0, 0, this.mc.thePlayer);
                this.mc.playerController.windowClick(0, 18, 0, 0, this.mc.thePlayer);
            }
            int n = 0;
            ++n;
        }
    }
    
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc             "Auto Soup"
        //     3: invokevirtual   com/nquantum/module/player/AutoSoup.setDisplayName:(Ljava/lang/String;)V
        //     6: aload_0        
        //     7: getfield        com/nquantum/module/player/AutoSoup.mc:Lnet/minecraft/client/Minecraft;
        //    10: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    13: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getHealth:()F
        //    16: ldc             13.0
        //    18: fcmpg          
        //    19: ifgt            37
        //    22: aload_0        
        //    23: if_icmpge       33
        //    26: aload_0        
        //    27: invokespecial   com/nquantum/module/player/AutoSoup.eatAndDropSoup:()V
        //    30: goto            37
        //    33: aload_0        
        //    34: invokespecial   com/nquantum/module/player/AutoSoup.getSoupFromInventory:()V
        //    37: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private boolean isHotbarFull() {
        while (this.mc.thePlayer.inventoryContainer.getSlot(36).getStack() != null) {
            int n = 0;
            ++n;
        }
        return false;
    }
    
    private void getSoupFromInventory() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        com/nquantum/module/player/AutoSoup.mc:Lnet/minecraft/client/Minecraft;
        //     4: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //     7: instanceof      Lnet/minecraft/client/gui/inventory/GuiChest;
        //    10: ifne            77
        //    13: aload_0        
        //    14: invokespecial   com/nquantum/module/player/AutoSoup.stackBowls:()V
        //    17: aload_0        
        //    18: getfield        com/nquantum/module/player/AutoSoup.mc:Lnet/minecraft/client/Minecraft;
        //    21: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    24: getfield        net/minecraft/client/entity/EntityPlayerSP.inventoryContainer:Lnet/minecraft/inventory/Container;
        //    27: bipush          9
        //    29: invokevirtual   net/minecraft/inventory/Container.getSlot:(I)Lnet/minecraft/inventory/Slot;
        //    32: invokevirtual   net/minecraft/inventory/Slot.getStack:()Lnet/minecraft/item/ItemStack;
        //    35: astore_2       
        //    36: aload_2        
        //    37: ifnull          71
        //    40: aload_0        
        //    41: aload_2        
        //    42: ifnonnull       71
        //    45: aload_0        
        //    46: getfield        com/nquantum/module/player/AutoSoup.mc:Lnet/minecraft/client/Minecraft;
        //    49: getfield        net/minecraft/client/Minecraft.playerController:Lnet/minecraft/client/multiplayer/PlayerControllerMP;
        //    52: iconst_0       
        //    53: bipush          9
        //    55: iconst_0       
        //    56: iconst_1       
        //    57: aload_0        
        //    58: getfield        com/nquantum/module/player/AutoSoup.mc:Lnet/minecraft/client/Minecraft;
        //    61: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    64: invokevirtual   net/minecraft/client/multiplayer/PlayerControllerMP.windowClick:(IIIILnet/minecraft/entity/player/EntityPlayer;)Lnet/minecraft/item/ItemStack;
        //    67: pop            
        //    68: goto            77
        //    71: iinc            1, 1
        //    74: goto            17
        //    77: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0071 (coming from #0042).
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
    
    private void eatAndDropSoup() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        com/nquantum/module/player/AutoSoup.mc:Lnet/minecraft/client/Minecraft;
        //     4: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //     7: getfield        net/minecraft/client/entity/EntityPlayerSP.inventoryContainer:Lnet/minecraft/inventory/Container;
        //    10: bipush          36
        //    12: invokevirtual   net/minecraft/inventory/Container.getSlot:(I)Lnet/minecraft/inventory/Slot;
        //    15: invokevirtual   net/minecraft/inventory/Slot.getStack:()Lnet/minecraft/item/ItemStack;
        //    18: astore_2       
        //    19: aload_2        
        //    20: ifnull          127
        //    23: aload_0        
        //    24: aload_2        
        //    25: ifnonnull       127
        //    28: aload_0        
        //    29: invokespecial   com/nquantum/module/player/AutoSoup.stackBowls:()V
        //    32: aload_0        
        //    33: getfield        com/nquantum/module/player/AutoSoup.mc:Lnet/minecraft/client/Minecraft;
        //    36: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    39: getfield        net/minecraft/client/entity/EntityPlayerSP.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //    42: getfield        net/minecraft/entity/player/InventoryPlayer.currentItem:I
        //    45: istore_3       
        //    46: aload_0        
        //    47: getfield        com/nquantum/module/player/AutoSoup.mc:Lnet/minecraft/client/Minecraft;
        //    50: invokevirtual   net/minecraft/client/Minecraft.getNetHandler:()Lnet/minecraft/client/network/NetHandlerPlayClient;
        //    53: new             Lnet/minecraft/network/play/client/C09PacketHeldItemChange;
        //    56: dup            
        //    57: iconst_0       
        //    58: invokespecial   net/minecraft/network/play/client/C09PacketHeldItemChange.<init>:(I)V
        //    61: invokevirtual   net/minecraft/client/network/NetHandlerPlayClient.addToSendQueue:(Lnet/minecraft/network/Packet;)V
        //    64: aload_0        
        //    65: getfield        com/nquantum/module/player/AutoSoup.mc:Lnet/minecraft/client/Minecraft;
        //    68: getfield        net/minecraft/client/Minecraft.playerController:Lnet/minecraft/client/multiplayer/PlayerControllerMP;
        //    71: invokevirtual   net/minecraft/client/multiplayer/PlayerControllerMP.updateController:()V
        //    74: aload_0        
        //    75: getfield        com/nquantum/module/player/AutoSoup.mc:Lnet/minecraft/client/Minecraft;
        //    78: invokevirtual   net/minecraft/client/Minecraft.getNetHandler:()Lnet/minecraft/client/network/NetHandlerPlayClient;
        //    81: new             Lnet/minecraft/network/play/client/C08PacketPlayerBlockPlacement;
        //    84: dup            
        //    85: new             Lnet/minecraft/util/BlockPos;
        //    88: dup            
        //    89: iconst_m1      
        //    90: iconst_m1      
        //    91: iconst_m1      
        //    92: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //    95: iconst_m1      
        //    96: aload_2        
        //    97: fconst_0       
        //    98: fconst_0       
        //    99: fconst_0       
        //   100: invokespecial   net/minecraft/network/play/client/C08PacketPlayerBlockPlacement.<init>:(Lnet/minecraft/util/BlockPos;ILnet/minecraft/item/ItemStack;FFF)V
        //   103: invokevirtual   net/minecraft/client/network/NetHandlerPlayClient.addToSendQueue:(Lnet/minecraft/network/Packet;)V
        //   106: aload_0        
        //   107: getfield        com/nquantum/module/player/AutoSoup.mc:Lnet/minecraft/client/Minecraft;
        //   110: invokevirtual   net/minecraft/client/Minecraft.getNetHandler:()Lnet/minecraft/client/network/NetHandlerPlayClient;
        //   113: new             Lnet/minecraft/network/play/client/C09PacketHeldItemChange;
        //   116: dup            
        //   117: iload_3        
        //   118: invokespecial   net/minecraft/network/play/client/C09PacketHeldItemChange.<init>:(I)V
        //   121: invokevirtual   net/minecraft/client/network/NetHandlerPlayClient.addToSendQueue:(Lnet/minecraft/network/Packet;)V
        //   124: goto            133
        //   127: iinc            1, 1
        //   130: goto            0
        //   133: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0127 (coming from #0025).
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
}
