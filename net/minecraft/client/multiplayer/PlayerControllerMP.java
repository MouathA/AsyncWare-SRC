package net.minecraft.client.multiplayer;

import net.minecraft.client.*;
import net.minecraft.client.network.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import com.nquantum.event.impl.*;
import net.minecraft.client.entity.*;
import com.nquantum.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;
import net.minecraft.entity.passive.*;
import net.minecraft.stats.*;
import net.minecraft.network.play.client.*;

public class PlayerControllerMP
{
    private WorldSettings.GameType currentGameType;
    private float stepSoundTickCounter;
    private final Minecraft mc;
    private final NetHandlerPlayClient netClientHandler;
    private int blockHitDelay;
    private float curBlockDamageMP;
    private boolean isHittingBlock;
    private int currentPlayerItem;
    private ItemStack currentItemHittingBlock;
    private BlockPos currentBlock;
    
    public void sendPacketDropItem(final ItemStack itemStack) {
        if (this.currentGameType.isCreative() && itemStack != null) {
            this.netClientHandler.addToSendQueue(new C10PacketCreativeInventoryAction(-1, itemStack));
        }
    }
    
    public boolean sendUseItem(final EntityPlayer entityPlayer, final World world, final ItemStack itemStack) {
        if (this.currentGameType == WorldSettings.GameType.SPECTATOR) {
            return false;
        }
        this.syncCurrentPlayItem();
        this.netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(entityPlayer.inventory.getCurrentItem()));
        final int stackSize = itemStack.stackSize;
        final ItemStack useItemRightClick = itemStack.useItemRightClick(world, entityPlayer);
        if (useItemRightClick != itemStack || (useItemRightClick != null && useItemRightClick.stackSize != stackSize)) {
            entityPlayer.inventory.mainInventory[entityPlayer.inventory.currentItem] = useItemRightClick;
            if (useItemRightClick.stackSize == 0) {
                entityPlayer.inventory.mainInventory[entityPlayer.inventory.currentItem] = null;
            }
            return true;
        }
        return false;
    }
    
    public void setPlayerCapabilities(final EntityPlayer entityPlayer) {
        this.currentGameType.configurePlayerCapabilities(entityPlayer.capabilities);
    }
    
    public boolean func_178894_a(final EntityPlayer entityPlayer, final Entity entity, final MovingObjectPosition movingObjectPosition) {
        this.syncCurrentPlayItem();
        final Vec3 vec3 = new Vec3(movingObjectPosition.hitVec.xCoord - entity.posX, movingObjectPosition.hitVec.yCoord - entity.posY, movingObjectPosition.hitVec.zCoord - entity.posZ);
        this.netClientHandler.addToSendQueue(new C02PacketUseEntity(entity, vec3));
        return this.currentGameType != WorldSettings.GameType.SPECTATOR && entity.interactAt(entityPlayer, vec3);
    }
    
    public boolean clickBlock(final BlockPos p0, final EnumFacing p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/client/multiplayer/PlayerControllerMP.currentGameType:Lnet/minecraft/world/WorldSettings$GameType;
        //     4: invokevirtual   net/minecraft/world/WorldSettings$GameType.isAdventure:()Z
        //     7: ifeq            82
        //    10: aload_0        
        //    11: getfield        net/minecraft/client/multiplayer/PlayerControllerMP.currentGameType:Lnet/minecraft/world/WorldSettings$GameType;
        //    14: getstatic       net/minecraft/world/WorldSettings$GameType.SPECTATOR:Lnet/minecraft/world/WorldSettings$GameType;
        //    17: if_acmpne       22
        //    20: iconst_0       
        //    21: ireturn        
        //    22: aload_0        
        //    23: getfield        net/minecraft/client/multiplayer/PlayerControllerMP.mc:Lnet/minecraft/client/Minecraft;
        //    26: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    29: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.isAllowEdit:()Z
        //    32: ifne            82
        //    35: aload_0        
        //    36: getfield        net/minecraft/client/multiplayer/PlayerControllerMP.mc:Lnet/minecraft/client/Minecraft;
        //    39: getfield        net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //    42: aload_1        
        //    43: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //    46: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //    51: astore_3       
        //    52: aload_0        
        //    53: getfield        net/minecraft/client/multiplayer/PlayerControllerMP.mc:Lnet/minecraft/client/Minecraft;
        //    56: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    59: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getCurrentEquippedItem:()Lnet/minecraft/item/ItemStack;
        //    62: astore          4
        //    64: aload           4
        //    66: ifnonnull       71
        //    69: iconst_0       
        //    70: ireturn        
        //    71: aload           4
        //    73: aload_3        
        //    74: invokevirtual   net/minecraft/item/ItemStack.canDestroy:(Lnet/minecraft/block/Block;)Z
        //    77: ifne            82
        //    80: iconst_0       
        //    81: ireturn        
        //    82: aload_0        
        //    83: getfield        net/minecraft/client/multiplayer/PlayerControllerMP.mc:Lnet/minecraft/client/Minecraft;
        //    86: getfield        net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //    89: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getWorldBorder:()Lnet/minecraft/world/border/WorldBorder;
        //    92: aload_1        
        //    93: invokevirtual   net/minecraft/world/border/WorldBorder.contains:(Lnet/minecraft/util/BlockPos;)Z
        //    96: ifne            101
        //    99: iconst_0       
        //   100: ireturn        
        //   101: aload_0        
        //   102: getfield        net/minecraft/client/multiplayer/PlayerControllerMP.currentGameType:Lnet/minecraft/world/WorldSettings$GameType;
        //   105: invokevirtual   net/minecraft/world/WorldSettings$GameType.isCreative:()Z
        //   108: ifeq            148
        //   111: aload_0        
        //   112: getfield        net/minecraft/client/multiplayer/PlayerControllerMP.netClientHandler:Lnet/minecraft/client/network/NetHandlerPlayClient;
        //   115: new             Lnet/minecraft/network/play/client/C07PacketPlayerDigging;
        //   118: dup            
        //   119: getstatic       net/minecraft/network/play/client/C07PacketPlayerDigging$Action.START_DESTROY_BLOCK:Lnet/minecraft/network/play/client/C07PacketPlayerDigging$Action;
        //   122: aload_1        
        //   123: aload_2        
        //   124: invokespecial   net/minecraft/network/play/client/C07PacketPlayerDigging.<init>:(Lnet/minecraft/network/play/client/C07PacketPlayerDigging$Action;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)V
        //   127: invokevirtual   net/minecraft/client/network/NetHandlerPlayClient.addToSendQueue:(Lnet/minecraft/network/Packet;)V
        //   130: aload_0        
        //   131: getfield        net/minecraft/client/multiplayer/PlayerControllerMP.mc:Lnet/minecraft/client/Minecraft;
        //   134: aload_0        
        //   135: aload_1        
        //   136: aload_2        
        //   137: invokestatic    net/minecraft/client/multiplayer/PlayerControllerMP.clickBlockCreative:(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/multiplayer/PlayerControllerMP;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)V
        //   140: aload_0        
        //   141: iconst_5       
        //   142: putfield        net/minecraft/client/multiplayer/PlayerControllerMP.blockHitDelay:I
        //   145: goto            386
        //   148: aload_0        
        //   149: getfield        net/minecraft/client/multiplayer/PlayerControllerMP.isHittingBlock:Z
        //   152: ifeq            160
        //   155: aload_0        
        //   156: aload_1        
        //   157: ifnonnull       386
        //   160: aload_0        
        //   161: getfield        net/minecraft/client/multiplayer/PlayerControllerMP.isHittingBlock:Z
        //   164: ifeq            189
        //   167: aload_0        
        //   168: getfield        net/minecraft/client/multiplayer/PlayerControllerMP.netClientHandler:Lnet/minecraft/client/network/NetHandlerPlayClient;
        //   171: new             Lnet/minecraft/network/play/client/C07PacketPlayerDigging;
        //   174: dup            
        //   175: getstatic       net/minecraft/network/play/client/C07PacketPlayerDigging$Action.ABORT_DESTROY_BLOCK:Lnet/minecraft/network/play/client/C07PacketPlayerDigging$Action;
        //   178: aload_0        
        //   179: getfield        net/minecraft/client/multiplayer/PlayerControllerMP.currentBlock:Lnet/minecraft/util/BlockPos;
        //   182: aload_2        
        //   183: invokespecial   net/minecraft/network/play/client/C07PacketPlayerDigging.<init>:(Lnet/minecraft/network/play/client/C07PacketPlayerDigging$Action;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)V
        //   186: invokevirtual   net/minecraft/client/network/NetHandlerPlayClient.addToSendQueue:(Lnet/minecraft/network/Packet;)V
        //   189: aload_0        
        //   190: getfield        net/minecraft/client/multiplayer/PlayerControllerMP.netClientHandler:Lnet/minecraft/client/network/NetHandlerPlayClient;
        //   193: new             Lnet/minecraft/network/play/client/C07PacketPlayerDigging;
        //   196: dup            
        //   197: getstatic       net/minecraft/network/play/client/C07PacketPlayerDigging$Action.START_DESTROY_BLOCK:Lnet/minecraft/network/play/client/C07PacketPlayerDigging$Action;
        //   200: aload_1        
        //   201: aload_2        
        //   202: invokespecial   net/minecraft/network/play/client/C07PacketPlayerDigging.<init>:(Lnet/minecraft/network/play/client/C07PacketPlayerDigging$Action;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)V
        //   205: invokevirtual   net/minecraft/client/network/NetHandlerPlayClient.addToSendQueue:(Lnet/minecraft/network/Packet;)V
        //   208: aload_0        
        //   209: getfield        net/minecraft/client/multiplayer/PlayerControllerMP.mc:Lnet/minecraft/client/Minecraft;
        //   212: getfield        net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   215: aload_1        
        //   216: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   219: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   224: astore_3       
        //   225: aload_3        
        //   226: invokevirtual   net/minecraft/block/Block.getMaterial:()Lnet/minecraft/block/material/Material;
        //   229: getstatic       net/minecraft/block/material/Material.air:Lnet/minecraft/block/material/Material;
        //   232: if_acmpeq       239
        //   235: iconst_1       
        //   236: goto            240
        //   239: iconst_0       
        //   240: istore          4
        //   242: iload           4
        //   244: ifeq            275
        //   247: aload_0        
        //   248: getfield        net/minecraft/client/multiplayer/PlayerControllerMP.curBlockDamageMP:F
        //   251: fconst_0       
        //   252: fcmpl          
        //   253: ifne            275
        //   256: aload_3        
        //   257: aload_0        
        //   258: getfield        net/minecraft/client/multiplayer/PlayerControllerMP.mc:Lnet/minecraft/client/Minecraft;
        //   261: getfield        net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   264: aload_1        
        //   265: aload_0        
        //   266: getfield        net/minecraft/client/multiplayer/PlayerControllerMP.mc:Lnet/minecraft/client/Minecraft;
        //   269: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   272: invokevirtual   net/minecraft/block/Block.onBlockClicked:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/entity/player/EntityPlayer;)V
        //   275: iload           4
        //   277: ifeq            317
        //   280: aload_3        
        //   281: aload_0        
        //   282: getfield        net/minecraft/client/multiplayer/PlayerControllerMP.mc:Lnet/minecraft/client/Minecraft;
        //   285: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   288: aload_0        
        //   289: getfield        net/minecraft/client/multiplayer/PlayerControllerMP.mc:Lnet/minecraft/client/Minecraft;
        //   292: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   295: getfield        net/minecraft/client/entity/EntityPlayerSP.worldObj:Lnet/minecraft/world/World;
        //   298: aload_1        
        //   299: invokevirtual   net/minecraft/block/Block.getPlayerRelativeBlockHardness:(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;)F
        //   302: fconst_1       
        //   303: fcmpl          
        //   304: iflt            317
        //   307: aload_0        
        //   308: aload_1        
        //   309: aload_2        
        //   310: invokevirtual   net/minecraft/client/multiplayer/PlayerControllerMP.onPlayerDestroyBlock:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)Z
        //   313: pop            
        //   314: goto            386
        //   317: aload_0        
        //   318: iconst_1       
        //   319: putfield        net/minecraft/client/multiplayer/PlayerControllerMP.isHittingBlock:Z
        //   322: aload_0        
        //   323: aload_1        
        //   324: putfield        net/minecraft/client/multiplayer/PlayerControllerMP.currentBlock:Lnet/minecraft/util/BlockPos;
        //   327: aload_0        
        //   328: aload_0        
        //   329: getfield        net/minecraft/client/multiplayer/PlayerControllerMP.mc:Lnet/minecraft/client/Minecraft;
        //   332: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   335: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getHeldItem:()Lnet/minecraft/item/ItemStack;
        //   338: putfield        net/minecraft/client/multiplayer/PlayerControllerMP.currentItemHittingBlock:Lnet/minecraft/item/ItemStack;
        //   341: aload_0        
        //   342: fconst_0       
        //   343: putfield        net/minecraft/client/multiplayer/PlayerControllerMP.curBlockDamageMP:F
        //   346: aload_0        
        //   347: fconst_0       
        //   348: putfield        net/minecraft/client/multiplayer/PlayerControllerMP.stepSoundTickCounter:F
        //   351: aload_0        
        //   352: getfield        net/minecraft/client/multiplayer/PlayerControllerMP.mc:Lnet/minecraft/client/Minecraft;
        //   355: getfield        net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   358: aload_0        
        //   359: getfield        net/minecraft/client/multiplayer/PlayerControllerMP.mc:Lnet/minecraft/client/Minecraft;
        //   362: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   365: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getEntityId:()I
        //   368: aload_0        
        //   369: getfield        net/minecraft/client/multiplayer/PlayerControllerMP.currentBlock:Lnet/minecraft/util/BlockPos;
        //   372: aload_0        
        //   373: getfield        net/minecraft/client/multiplayer/PlayerControllerMP.curBlockDamageMP:F
        //   376: ldc_w           10.0
        //   379: fmul           
        //   380: f2i            
        //   381: iconst_1       
        //   382: isub           
        //   383: invokevirtual   net/minecraft/client/multiplayer/WorldClient.sendBlockBreakProgress:(ILnet/minecraft/util/BlockPos;I)V
        //   386: iconst_1       
        //   387: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0160 (coming from #0157).
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
    
    public static void clickBlockCreative(final Minecraft minecraft, final PlayerControllerMP playerControllerMP, final BlockPos blockPos, final EnumFacing enumFacing) {
        if (!minecraft.theWorld.extinguishFire(minecraft.thePlayer, blockPos, enumFacing)) {
            playerControllerMP.onPlayerDestroyBlock(blockPos, enumFacing);
        }
    }
    
    public boolean onPlayerDestroyBlock(final BlockPos blockToAir, final EnumFacing enumFacing) {
        if (this.currentGameType.isAdventure()) {
            if (this.currentGameType == WorldSettings.GameType.SPECTATOR) {
                return false;
            }
            if (!this.mc.thePlayer.isAllowEdit()) {
                final Block block = this.mc.theWorld.getBlockState(blockToAir).getBlock();
                final ItemStack currentEquippedItem = this.mc.thePlayer.getCurrentEquippedItem();
                if (currentEquippedItem == null) {
                    return false;
                }
                if (!currentEquippedItem.canDestroy(block)) {
                    return false;
                }
            }
        }
        if (this.currentGameType.isCreative() && this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
            return false;
        }
        final WorldClient theWorld = this.mc.theWorld;
        final IBlockState blockState = theWorld.getBlockState(blockToAir);
        final Block block2 = blockState.getBlock();
        if (block2.getMaterial() == Material.air) {
            return false;
        }
        theWorld.playAuxSFX(2001, blockToAir, Block.getStateId(blockState));
        final boolean setBlockToAir = theWorld.setBlockToAir(blockToAir);
        if (setBlockToAir) {
            block2.onBlockDestroyedByPlayer(theWorld, blockToAir, blockState);
        }
        this.currentBlock = new BlockPos(this.currentBlock.getX(), -1, this.currentBlock.getZ());
        if (!this.currentGameType.isCreative()) {
            final ItemStack currentEquippedItem2 = this.mc.thePlayer.getCurrentEquippedItem();
            if (currentEquippedItem2 != null) {
                currentEquippedItem2.onBlockDestroyed(theWorld, block2, blockToAir, this.mc.thePlayer);
                if (currentEquippedItem2.stackSize == 0) {
                    this.mc.thePlayer.destroyCurrentEquippedItem();
                }
            }
        }
        return setBlockToAir;
    }
    
    public void sendSlotPacket(final ItemStack itemStack, final int n) {
        if (this.currentGameType.isCreative()) {
            this.netClientHandler.addToSendQueue(new C10PacketCreativeInventoryAction(n, itemStack));
        }
    }
    
    public float getBlockReachDistance() {
        final EventGetBlockReach eventGetBlockReach = new EventGetBlockReach();
        eventGetBlockReach.call();
        if (eventGetBlockReach.reach != -1.0f) {
            return eventGetBlockReach.reach;
        }
        return this.currentGameType.isCreative() ? 5.0f : 4.5f;
    }
    
    public WorldSettings.GameType getCurrentGameType() {
        return this.currentGameType;
    }
    
    public void updateController() {
        this.syncCurrentPlayItem();
        if (this.netClientHandler.getNetworkManager().isChannelOpen()) {
            this.netClientHandler.getNetworkManager().processReceivedPackets();
        }
        else {
            this.netClientHandler.getNetworkManager().checkDisconnected();
        }
    }
    
    public ItemStack windowClick(final int n, final int n2, final int n3, final int n4, final EntityPlayer entityPlayer) {
        final short nextTransactionID = entityPlayer.openContainer.getNextTransactionID(entityPlayer.inventory);
        final ItemStack slotClick = entityPlayer.openContainer.slotClick(n2, n3, n4, entityPlayer);
        this.netClientHandler.addToSendQueue(new C0EPacketClickWindow(n, n2, n3, n4, slotClick, nextTransactionID));
        return slotClick;
    }
    
    public void sendEnchantPacket(final int n, final int n2) {
        this.netClientHandler.addToSendQueue(new C11PacketEnchantItem(n, n2));
    }
    
    public boolean onPlayerRightClick(final EntityPlayerSP entityPlayerSP, final WorldClient worldClient, final ItemStack itemStack, final BlockPos blockPos, final EnumFacing enumFacing, final Vec3 vec3) {
        this.syncCurrentPlayItem();
        final float n = (float)(vec3.xCoord - blockPos.getX());
        final float n2 = (float)(vec3.yCoord - blockPos.getY());
        final float n3 = (float)(vec3.zCoord - blockPos.getZ());
        if (!this.mc.theWorld.getWorldBorder().contains(blockPos)) {
            return false;
        }
        if (this.currentGameType != WorldSettings.GameType.SPECTATOR) {
            final IBlockState blockState = worldClient.getBlockState(blockPos);
            if ((entityPlayerSP.isSneaking() && entityPlayerSP.getHeldItem() != null) || blockState.getBlock().onBlockActivated(worldClient, blockPos, blockState, entityPlayerSP, enumFacing, n, n2, n3)) {}
        }
        this.netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(blockPos, enumFacing.getIndex(), entityPlayerSP.inventory.getCurrentItem(), n, n2, n3));
        return true;
    }
    
    public boolean shouldDrawHUD() {
        return this.currentGameType.isSurvivalOrAdventure();
    }
    
    public boolean func_181040_m() {
        return this.isHittingBlock;
    }
    
    public boolean gameIsSurvivalOrAdventure() {
        return this.currentGameType.isSurvivalOrAdventure();
    }
    
    public void resetBlockRemoving() {
        if (this.isHittingBlock && !Asyncware.instance.moduleManager.getModuleByName("Abort Breaking").isToggled()) {
            this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.currentBlock, EnumFacing.DOWN));
            this.isHittingBlock = false;
            this.curBlockDamageMP = 0.0f;
            this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), this.currentBlock, -1);
        }
    }
    
    public boolean extendedReach() {
        return this.currentGameType.isCreative();
    }
    
    public boolean isSpectator() {
        return this.currentGameType == WorldSettings.GameType.SPECTATOR;
    }
    
    public void attackEntity(final EntityPlayer entityPlayer, final Entity entity) {
        this.syncCurrentPlayItem();
        this.netClientHandler.addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
        if (this.currentGameType != WorldSettings.GameType.SPECTATOR) {
            entityPlayer.attackTargetEntityWithCurrentItem(entity);
        }
    }
    
    public boolean isSpectatorMode() {
        return this.currentGameType == WorldSettings.GameType.SPECTATOR;
    }
    
    public boolean onPlayerDamageBlock(final BlockPos blockPos, final EnumFacing enumFacing) {
        this.syncCurrentPlayItem();
        if (this.blockHitDelay > 0) {
            --this.blockHitDelay;
            return true;
        }
        if (this.currentGameType.isCreative() && this.mc.theWorld.getWorldBorder().contains(blockPos)) {
            this.blockHitDelay = 5;
            this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, enumFacing));
            clickBlockCreative(this.mc, this, blockPos, enumFacing);
            return true;
        }
        if (blockPos != null) {
            return this.clickBlock(blockPos, enumFacing);
        }
        final Block block = this.mc.theWorld.getBlockState(blockPos).getBlock();
        if (block.getMaterial() == Material.air) {
            return this.isHittingBlock = false;
        }
        this.curBlockDamageMP += block.getPlayerRelativeBlockHardness(this.mc.thePlayer, this.mc.thePlayer.worldObj, blockPos);
        if (this.stepSoundTickCounter % 4.0f == 0.0f) {
            this.mc.getSoundHandler().playSound(new PositionedSoundRecord(new ResourceLocation(block.stepSound.getStepSound()), (block.stepSound.getVolume() + 1.0f) / 8.0f, block.stepSound.getFrequency() * 0.5f, blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f));
        }
        ++this.stepSoundTickCounter;
        if (this.curBlockDamageMP >= 1.0f) {
            this.isHittingBlock = false;
            this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, enumFacing));
            this.onPlayerDestroyBlock(blockPos, enumFacing);
            this.curBlockDamageMP = 0.0f;
            this.stepSoundTickCounter = 0.0f;
            this.blockHitDelay = 5;
        }
        this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), this.currentBlock, (int)(this.curBlockDamageMP * 10.0f) - 1);
        return true;
    }
    
    public boolean isRidingHorse() {
        return this.mc.thePlayer.isRiding() && this.mc.thePlayer.ridingEntity instanceof EntityHorse;
    }
    
    public PlayerControllerMP(final Minecraft mc, final NetHandlerPlayClient netClientHandler) {
        this.currentBlock = new BlockPos(-1, -1, -1);
        this.currentGameType = WorldSettings.GameType.SURVIVAL;
        this.mc = mc;
        this.netClientHandler = netClientHandler;
    }
    
    public void flipPlayer(final EntityPlayer entityPlayer) {
        entityPlayer.rotationYaw = -180.0f;
    }
    
    public boolean isInCreativeMode() {
        return this.currentGameType.isCreative();
    }
    
    public boolean interactWithEntitySendPacket(final EntityPlayer entityPlayer, final Entity entity) {
        this.syncCurrentPlayItem();
        this.netClientHandler.addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.INTERACT));
        return this.currentGameType != WorldSettings.GameType.SPECTATOR && entityPlayer.interactWith(entity);
    }
    
    public void onStoppedUsingItem(final EntityPlayer entityPlayer) {
        this.syncCurrentPlayItem();
        this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        entityPlayer.stopUsingItem();
    }
    
    public boolean isNotCreative() {
        return !this.currentGameType.isCreative();
    }
    
    public boolean sendFakeUseItem(final EntityPlayer entityPlayer, final World world, final ItemStack itemStack) {
        if (this.currentGameType == WorldSettings.GameType.SPECTATOR) {
            return false;
        }
        this.syncCurrentPlayItem();
        final int stackSize = itemStack.stackSize;
        final ItemStack useItemRightClick = itemStack.useItemRightClick(world, entityPlayer);
        if (useItemRightClick != itemStack || (useItemRightClick != null && useItemRightClick.stackSize != stackSize)) {
            entityPlayer.inventory.mainInventory[entityPlayer.inventory.currentItem] = useItemRightClick;
            if (useItemRightClick.stackSize == 0) {
                entityPlayer.inventory.mainInventory[entityPlayer.inventory.currentItem] = null;
            }
            return true;
        }
        return false;
    }
    
    public EntityPlayerSP func_178892_a(final World world, final StatFileWriter statFileWriter) {
        return new EntityPlayerSP(this.mc, world, this.netClientHandler, statFileWriter);
    }
    
    public void setGameType(final WorldSettings.GameType currentGameType) {
        (this.currentGameType = currentGameType).configurePlayerCapabilities(this.mc.thePlayer.capabilities);
    }
    
    public void syncCurrentPlayItem() {
        final int currentItem = this.mc.thePlayer.inventory.currentItem;
        if (currentItem != this.currentPlayerItem) {
            this.currentPlayerItem = currentItem;
            this.netClientHandler.addToSendQueue(new C09PacketHeldItemChange(this.currentPlayerItem));
        }
    }
}
