package com.nquantum.module.player;

import com.nquantum.module.*;
import com.nquantum.event.impl.*;
import com.nquantum.event.*;

public class AutoPot extends Module
{
    private boolean potting;
    
    private void splashPot() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        com/nquantum/module/player/AutoPot.mc:Lnet/minecraft/client/Minecraft;
        //     4: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //     7: getfield        net/minecraft/client/entity/EntityPlayerSP.inventoryContainer:Lnet/minecraft/inventory/Container;
        //    10: bipush          36
        //    12: invokevirtual   net/minecraft/inventory/Container.getSlot:(I)Lnet/minecraft/inventory/Slot;
        //    15: invokevirtual   net/minecraft/inventory/Slot.getStack:()Lnet/minecraft/item/ItemStack;
        //    18: astore_2       
        //    19: aload_2        
        //    20: ifnull          219
        //    23: aload_0        
        //    24: aload_2        
        //    25: ifnonnull       219
        //    28: aload_0        
        //    29: getfield        com/nquantum/module/player/AutoPot.mc:Lnet/minecraft/client/Minecraft;
        //    32: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    35: getfield        net/minecraft/client/entity/EntityPlayerSP.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //    38: getfield        net/minecraft/entity/player/InventoryPlayer.currentItem:I
        //    41: istore_3       
        //    42: aload_0        
        //    43: iconst_1       
        //    44: putfield        com/nquantum/module/player/AutoPot.potting:Z
        //    47: aload_0        
        //    48: getfield        com/nquantum/module/player/AutoPot.mc:Lnet/minecraft/client/Minecraft;
        //    51: invokevirtual   net/minecraft/client/Minecraft.getNetHandler:()Lnet/minecraft/client/network/NetHandlerPlayClient;
        //    54: new             Lnet/minecraft/network/play/client/C03PacketPlayer$C05PacketPlayerLook;
        //    57: dup            
        //    58: aload_0        
        //    59: getfield        com/nquantum/module/player/AutoPot.mc:Lnet/minecraft/client/Minecraft;
        //    62: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    65: getfield        net/minecraft/client/entity/EntityPlayerSP.rotationYaw:F
        //    68: ldc             90.0
        //    70: aload_0        
        //    71: getfield        com/nquantum/module/player/AutoPot.mc:Lnet/minecraft/client/Minecraft;
        //    74: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    77: getfield        net/minecraft/client/entity/EntityPlayerSP.onGround:Z
        //    80: invokespecial   net/minecraft/network/play/client/C03PacketPlayer$C05PacketPlayerLook.<init>:(FFZ)V
        //    83: invokevirtual   net/minecraft/client/network/NetHandlerPlayClient.addToSendQueue:(Lnet/minecraft/network/Packet;)V
        //    86: aload_0        
        //    87: getfield        com/nquantum/module/player/AutoPot.mc:Lnet/minecraft/client/Minecraft;
        //    90: invokevirtual   net/minecraft/client/Minecraft.getNetHandler:()Lnet/minecraft/client/network/NetHandlerPlayClient;
        //    93: new             Lnet/minecraft/network/play/client/C09PacketHeldItemChange;
        //    96: dup            
        //    97: iconst_0       
        //    98: invokespecial   net/minecraft/network/play/client/C09PacketHeldItemChange.<init>:(I)V
        //   101: invokevirtual   net/minecraft/client/network/NetHandlerPlayClient.addToSendQueue:(Lnet/minecraft/network/Packet;)V
        //   104: aload_0        
        //   105: getfield        com/nquantum/module/player/AutoPot.mc:Lnet/minecraft/client/Minecraft;
        //   108: getfield        net/minecraft/client/Minecraft.playerController:Lnet/minecraft/client/multiplayer/PlayerControllerMP;
        //   111: invokevirtual   net/minecraft/client/multiplayer/PlayerControllerMP.updateController:()V
        //   114: aload_0        
        //   115: getfield        com/nquantum/module/player/AutoPot.mc:Lnet/minecraft/client/Minecraft;
        //   118: invokevirtual   net/minecraft/client/Minecraft.getNetHandler:()Lnet/minecraft/client/network/NetHandlerPlayClient;
        //   121: new             Lnet/minecraft/network/play/client/C08PacketPlayerBlockPlacement;
        //   124: dup            
        //   125: new             Lnet/minecraft/util/BlockPos;
        //   128: dup            
        //   129: iconst_m1      
        //   130: iconst_m1      
        //   131: iconst_m1      
        //   132: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   135: iconst_m1      
        //   136: aload_2        
        //   137: fconst_0       
        //   138: fconst_0       
        //   139: fconst_0       
        //   140: invokespecial   net/minecraft/network/play/client/C08PacketPlayerBlockPlacement.<init>:(Lnet/minecraft/util/BlockPos;ILnet/minecraft/item/ItemStack;FFF)V
        //   143: invokevirtual   net/minecraft/client/network/NetHandlerPlayClient.addToSendQueue:(Lnet/minecraft/network/Packet;)V
        //   146: aload_0        
        //   147: getfield        com/nquantum/module/player/AutoPot.mc:Lnet/minecraft/client/Minecraft;
        //   150: invokevirtual   net/minecraft/client/Minecraft.getNetHandler:()Lnet/minecraft/client/network/NetHandlerPlayClient;
        //   153: new             Lnet/minecraft/network/play/client/C09PacketHeldItemChange;
        //   156: dup            
        //   157: iload_3        
        //   158: invokespecial   net/minecraft/network/play/client/C09PacketHeldItemChange.<init>:(I)V
        //   161: invokevirtual   net/minecraft/client/network/NetHandlerPlayClient.addToSendQueue:(Lnet/minecraft/network/Packet;)V
        //   164: aload_0        
        //   165: iconst_0       
        //   166: putfield        com/nquantum/module/player/AutoPot.potting:Z
        //   169: aload_0        
        //   170: getfield        com/nquantum/module/player/AutoPot.mc:Lnet/minecraft/client/Minecraft;
        //   173: invokevirtual   net/minecraft/client/Minecraft.getNetHandler:()Lnet/minecraft/client/network/NetHandlerPlayClient;
        //   176: new             Lnet/minecraft/network/play/client/C03PacketPlayer$C05PacketPlayerLook;
        //   179: dup            
        //   180: aload_0        
        //   181: getfield        com/nquantum/module/player/AutoPot.mc:Lnet/minecraft/client/Minecraft;
        //   184: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   187: getfield        net/minecraft/client/entity/EntityPlayerSP.rotationYaw:F
        //   190: aload_0        
        //   191: getfield        com/nquantum/module/player/AutoPot.mc:Lnet/minecraft/client/Minecraft;
        //   194: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   197: getfield        net/minecraft/client/entity/EntityPlayerSP.rotationPitch:F
        //   200: aload_0        
        //   201: getfield        com/nquantum/module/player/AutoPot.mc:Lnet/minecraft/client/Minecraft;
        //   204: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   207: getfield        net/minecraft/client/entity/EntityPlayerSP.onGround:Z
        //   210: invokespecial   net/minecraft/network/play/client/C03PacketPlayer$C05PacketPlayerLook.<init>:(FFZ)V
        //   213: invokevirtual   net/minecraft/client/network/NetHandlerPlayClient.addToSendQueue:(Lnet/minecraft/network/Packet;)V
        //   216: goto            225
        //   219: iinc            1, 1
        //   222: goto            0
        //   225: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0219 (coming from #0025).
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
    
    private void getPotsFromInventory() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        com/nquantum/module/player/AutoPot.mc:Lnet/minecraft/client/Minecraft;
        //     4: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //     7: instanceof      Lnet/minecraft/client/gui/inventory/GuiChest;
        //    10: ifne            73
        //    13: aload_0        
        //    14: getfield        com/nquantum/module/player/AutoPot.mc:Lnet/minecraft/client/Minecraft;
        //    17: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    20: getfield        net/minecraft/client/entity/EntityPlayerSP.inventoryContainer:Lnet/minecraft/inventory/Container;
        //    23: bipush          9
        //    25: invokevirtual   net/minecraft/inventory/Container.getSlot:(I)Lnet/minecraft/inventory/Slot;
        //    28: invokevirtual   net/minecraft/inventory/Slot.getStack:()Lnet/minecraft/item/ItemStack;
        //    31: astore_2       
        //    32: aload_2        
        //    33: ifnull          67
        //    36: aload_0        
        //    37: aload_2        
        //    38: ifnonnull       67
        //    41: aload_0        
        //    42: getfield        com/nquantum/module/player/AutoPot.mc:Lnet/minecraft/client/Minecraft;
        //    45: getfield        net/minecraft/client/Minecraft.playerController:Lnet/minecraft/client/multiplayer/PlayerControllerMP;
        //    48: iconst_0       
        //    49: bipush          9
        //    51: iconst_0       
        //    52: iconst_1       
        //    53: aload_0        
        //    54: getfield        com/nquantum/module/player/AutoPot.mc:Lnet/minecraft/client/Minecraft;
        //    57: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    60: invokevirtual   net/minecraft/client/multiplayer/PlayerControllerMP.windowClick:(IIIILnet/minecraft/entity/player/EntityPlayer;)Lnet/minecraft/item/ItemStack;
        //    63: pop            
        //    64: goto            73
        //    67: iinc            1, 1
        //    70: goto            13
        //    73: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0067 (coming from #0038).
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
    
    public boolean isPotting() {
        return this.potting;
    }
    
    private boolean isHotbarFull() {
        while (this.mc.thePlayer.inventoryContainer.getSlot(36).getStack() != null) {
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public AutoPot() {
        super("AutoPot", 0, Category.PLAYER);
    }
    
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate eventUpdate) {
        this.setDisplayName("Auto Pot");
        if (this.mc.thePlayer.getHealth() <= 12.0f && this != null) {
            this.splashPot();
        }
    }
}
