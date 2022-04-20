package net.minecraft.client.entity;

import net.minecraft.client.network.*;
import net.minecraft.client.*;
import net.minecraft.network.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.inventory.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.command.server.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.audio.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import com.nquantum.event.impl.*;
import net.minecraft.init.*;
import net.minecraft.client.gui.*;

public class EntityPlayerSP extends AbstractClientPlayer
{
    public float timeInPortal;
    private int horseJumpPowerCounter;
    public final NetHandlerPlayClient sendQueue;
    private int positionUpdateTicks;
    private float lastReportedPitch;
    public float prevRenderArmYaw;
    private boolean serverSneakState;
    private String clientBrand;
    private boolean serverSprintState;
    public MovementInput movementInput;
    private boolean hasValidHealth;
    protected Minecraft mc;
    public float prevTimeInPortal;
    private double lastReportedPosX;
    private final StatFileWriter statWriter;
    private float horseJumpPower;
    private double lastReportedPosZ;
    private double lastReportedPosY;
    public float renderArmPitch;
    public float renderArmYaw;
    protected int sprintToggleTimer;
    public float prevRenderArmPitch;
    private float lastReportedYaw;
    public int sprintingTicksLeft;
    
    public void updateEntityActionState() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokespecial   net/minecraft/client/entity/AbstractClientPlayer.updateEntityActionState:()V
        //     4: aload_0        
        //     5: if_acmpne       107
        //     8: aload_0        
        //     9: aload_0        
        //    10: getfield        net/minecraft/client/entity/EntityPlayerSP.movementInput:Lnet/minecraft/util/MovementInput;
        //    13: getfield        net/minecraft/util/MovementInput.moveStrafe:F
        //    16: putfield        net/minecraft/client/entity/EntityPlayerSP.moveStrafing:F
        //    19: aload_0        
        //    20: aload_0        
        //    21: getfield        net/minecraft/client/entity/EntityPlayerSP.movementInput:Lnet/minecraft/util/MovementInput;
        //    24: getfield        net/minecraft/util/MovementInput.moveForward:F
        //    27: putfield        net/minecraft/client/entity/EntityPlayerSP.moveForward:F
        //    30: aload_0        
        //    31: aload_0        
        //    32: getfield        net/minecraft/client/entity/EntityPlayerSP.movementInput:Lnet/minecraft/util/MovementInput;
        //    35: getfield        net/minecraft/util/MovementInput.jump:Z
        //    38: putfield        net/minecraft/client/entity/EntityPlayerSP.isJumping:Z
        //    41: aload_0        
        //    42: aload_0        
        //    43: getfield        net/minecraft/client/entity/EntityPlayerSP.renderArmYaw:F
        //    46: putfield        net/minecraft/client/entity/EntityPlayerSP.prevRenderArmYaw:F
        //    49: aload_0        
        //    50: aload_0        
        //    51: getfield        net/minecraft/client/entity/EntityPlayerSP.renderArmPitch:F
        //    54: putfield        net/minecraft/client/entity/EntityPlayerSP.prevRenderArmPitch:F
        //    57: aload_0        
        //    58: aload_0        
        //    59: getfield        net/minecraft/client/entity/EntityPlayerSP.renderArmPitch:F
        //    62: f2d            
        //    63: aload_0        
        //    64: getfield        net/minecraft/client/entity/EntityPlayerSP.rotationPitch:F
        //    67: aload_0        
        //    68: getfield        net/minecraft/client/entity/EntityPlayerSP.renderArmPitch:F
        //    71: fsub           
        //    72: f2d            
        //    73: ldc2_w          0.5
        //    76: dmul           
        //    77: dadd           
        //    78: d2f            
        //    79: putfield        net/minecraft/client/entity/EntityPlayerSP.renderArmPitch:F
        //    82: aload_0        
        //    83: aload_0        
        //    84: getfield        net/minecraft/client/entity/EntityPlayerSP.renderArmYaw:F
        //    87: f2d            
        //    88: aload_0        
        //    89: getfield        net/minecraft/client/entity/EntityPlayerSP.rotationYaw:F
        //    92: aload_0        
        //    93: getfield        net/minecraft/client/entity/EntityPlayerSP.renderArmYaw:F
        //    96: fsub           
        //    97: f2d            
        //    98: ldc2_w          0.5
        //   101: dmul           
        //   102: dadd           
        //   103: d2f            
        //   104: putfield        net/minecraft/client/entity/EntityPlayerSP.renderArmYaw:F
        //   107: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void sendChatMessage(final String message) {
        final EventChat eventChat = new EventChat();
        eventChat.call();
        if (eventChat.isCancelled()) {
            return;
        }
        eventChat.message = message;
        this.sendQueue.addToSendQueue(new C01PacketChatMessage(message));
    }
    
    @Override
    protected void damageEntity(final DamageSource damageSource, final float n) {
        if (!this.isEntityInvulnerable(damageSource)) {
            this.setHealth(this.getHealth() - n);
        }
    }
    
    @Override
    public boolean isUser() {
        return true;
    }
    
    @Override
    public void addStat(final StatBase statBase, final int n) {
        if (statBase != null && statBase.isIndependent) {
            super.addStat(statBase, n);
        }
    }
    
    @Override
    public void displayGui(final IInteractionObject interactionObject) {
        final String guiID = interactionObject.getGuiID();
        if ("minecraft:crafting_table".equals(guiID)) {
            this.mc.displayGuiScreen(new GuiCrafting(this.inventory, this.worldObj));
        }
        else if ("minecraft:enchanting_table".equals(guiID)) {
            this.mc.displayGuiScreen(new GuiEnchantment(this.inventory, this.worldObj, interactionObject));
        }
        else if ("minecraft:anvil".equals(guiID)) {
            this.mc.displayGuiScreen(new GuiRepair(this.inventory, this.worldObj));
        }
    }
    
    @Override
    public void onLivingUpdate() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/client/entity/EntityPlayerSP.sprintingTicksLeft:I
        //     4: ifle            29
        //     7: aload_0        
        //     8: dup            
        //     9: getfield        net/minecraft/client/entity/EntityPlayerSP.sprintingTicksLeft:I
        //    12: iconst_1       
        //    13: isub           
        //    14: putfield        net/minecraft/client/entity/EntityPlayerSP.sprintingTicksLeft:I
        //    17: aload_0        
        //    18: getfield        net/minecraft/client/entity/EntityPlayerSP.sprintingTicksLeft:I
        //    21: ifne            29
        //    24: aload_0        
        //    25: iconst_0       
        //    26: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.setSprinting:(Z)V
        //    29: aload_0        
        //    30: getfield        net/minecraft/client/entity/EntityPlayerSP.sprintToggleTimer:I
        //    33: ifle            46
        //    36: aload_0        
        //    37: dup            
        //    38: getfield        net/minecraft/client/entity/EntityPlayerSP.sprintToggleTimer:I
        //    41: iconst_1       
        //    42: isub           
        //    43: putfield        net/minecraft/client/entity/EntityPlayerSP.sprintToggleTimer:I
        //    46: aload_0        
        //    47: aload_0        
        //    48: getfield        net/minecraft/client/entity/EntityPlayerSP.timeInPortal:F
        //    51: putfield        net/minecraft/client/entity/EntityPlayerSP.prevTimeInPortal:F
        //    54: aload_0        
        //    55: getfield        net/minecraft/client/entity/EntityPlayerSP.inPortal:Z
        //    58: ifeq            173
        //    61: aload_0        
        //    62: getfield        net/minecraft/client/entity/EntityPlayerSP.mc:Lnet/minecraft/client/Minecraft;
        //    65: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //    68: ifnull          95
        //    71: aload_0        
        //    72: getfield        net/minecraft/client/entity/EntityPlayerSP.mc:Lnet/minecraft/client/Minecraft;
        //    75: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //    78: invokevirtual   net/minecraft/client/gui/GuiScreen.doesGuiPauseGame:()Z
        //    81: ifne            95
        //    84: aload_0        
        //    85: getfield        net/minecraft/client/entity/EntityPlayerSP.mc:Lnet/minecraft/client/Minecraft;
        //    88: aconst_null    
        //    89: checkcast       Lnet/minecraft/client/gui/GuiScreen;
        //    92: invokevirtual   net/minecraft/client/Minecraft.displayGuiScreen:(Lnet/minecraft/client/gui/GuiScreen;)V
        //    95: aload_0        
        //    96: getfield        net/minecraft/client/entity/EntityPlayerSP.timeInPortal:F
        //    99: fconst_0       
        //   100: fcmpl          
        //   101: ifne            139
        //   104: aload_0        
        //   105: getfield        net/minecraft/client/entity/EntityPlayerSP.mc:Lnet/minecraft/client/Minecraft;
        //   108: invokevirtual   net/minecraft/client/Minecraft.getSoundHandler:()Lnet/minecraft/client/audio/SoundHandler;
        //   111: new             Lnet/minecraft/util/ResourceLocation;
        //   114: dup            
        //   115: ldc             "portal.trigger"
        //   117: invokespecial   net/minecraft/util/ResourceLocation.<init>:(Ljava/lang/String;)V
        //   120: aload_0        
        //   121: getfield        net/minecraft/client/entity/EntityPlayerSP.rand:Ljava/util/Random;
        //   124: invokevirtual   java/util/Random.nextFloat:()F
        //   127: ldc             0.4
        //   129: fmul           
        //   130: ldc             0.8
        //   132: fadd           
        //   133: invokestatic    net/minecraft/client/audio/PositionedSoundRecord.create:(Lnet/minecraft/util/ResourceLocation;F)Lnet/minecraft/client/audio/PositionedSoundRecord;
        //   136: invokevirtual   net/minecraft/client/audio/SoundHandler.playSound:(Lnet/minecraft/client/audio/ISound;)V
        //   139: aload_0        
        //   140: dup            
        //   141: getfield        net/minecraft/client/entity/EntityPlayerSP.timeInPortal:F
        //   144: ldc_w           0.0125
        //   147: fadd           
        //   148: putfield        net/minecraft/client/entity/EntityPlayerSP.timeInPortal:F
        //   151: aload_0        
        //   152: getfield        net/minecraft/client/entity/EntityPlayerSP.timeInPortal:F
        //   155: fconst_1       
        //   156: fcmpl          
        //   157: iflt            165
        //   160: aload_0        
        //   161: fconst_1       
        //   162: putfield        net/minecraft/client/entity/EntityPlayerSP.timeInPortal:F
        //   165: aload_0        
        //   166: iconst_0       
        //   167: putfield        net/minecraft/client/entity/EntityPlayerSP.inPortal:Z
        //   170: goto            262
        //   173: aload_0        
        //   174: getstatic       net/minecraft/potion/Potion.confusion:Lnet/minecraft/potion/Potion;
        //   177: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.isPotionActive:(Lnet/minecraft/potion/Potion;)Z
        //   180: ifeq            227
        //   183: aload_0        
        //   184: getstatic       net/minecraft/potion/Potion.confusion:Lnet/minecraft/potion/Potion;
        //   187: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getActivePotionEffect:(Lnet/minecraft/potion/Potion;)Lnet/minecraft/potion/PotionEffect;
        //   190: invokevirtual   net/minecraft/potion/PotionEffect.getDuration:()I
        //   193: bipush          60
        //   195: if_icmple       227
        //   198: aload_0        
        //   199: dup            
        //   200: getfield        net/minecraft/client/entity/EntityPlayerSP.timeInPortal:F
        //   203: ldc_w           0.006666667
        //   206: fadd           
        //   207: putfield        net/minecraft/client/entity/EntityPlayerSP.timeInPortal:F
        //   210: aload_0        
        //   211: getfield        net/minecraft/client/entity/EntityPlayerSP.timeInPortal:F
        //   214: fconst_1       
        //   215: fcmpl          
        //   216: ifle            262
        //   219: aload_0        
        //   220: fconst_1       
        //   221: putfield        net/minecraft/client/entity/EntityPlayerSP.timeInPortal:F
        //   224: goto            262
        //   227: aload_0        
        //   228: getfield        net/minecraft/client/entity/EntityPlayerSP.timeInPortal:F
        //   231: fconst_0       
        //   232: fcmpl          
        //   233: ifle            248
        //   236: aload_0        
        //   237: dup            
        //   238: getfield        net/minecraft/client/entity/EntityPlayerSP.timeInPortal:F
        //   241: ldc_w           0.05
        //   244: fsub           
        //   245: putfield        net/minecraft/client/entity/EntityPlayerSP.timeInPortal:F
        //   248: aload_0        
        //   249: getfield        net/minecraft/client/entity/EntityPlayerSP.timeInPortal:F
        //   252: fconst_0       
        //   253: fcmpg          
        //   254: ifge            262
        //   257: aload_0        
        //   258: fconst_0       
        //   259: putfield        net/minecraft/client/entity/EntityPlayerSP.timeInPortal:F
        //   262: aload_0        
        //   263: getfield        net/minecraft/client/entity/EntityPlayerSP.timeUntilPortal:I
        //   266: ifle            279
        //   269: aload_0        
        //   270: dup            
        //   271: getfield        net/minecraft/client/entity/EntityPlayerSP.timeUntilPortal:I
        //   274: iconst_1       
        //   275: isub           
        //   276: putfield        net/minecraft/client/entity/EntityPlayerSP.timeUntilPortal:I
        //   279: getstatic       com/nquantum/Asyncware.instance:Lcom/nquantum/Asyncware;
        //   282: getfield        com/nquantum/Asyncware.moduleManager:Lcom/nquantum/module/ModuleManager;
        //   285: ldc_w           "No Slowdown"
        //   288: invokevirtual   com/nquantum/module/ModuleManager.getModuleByName:(Ljava/lang/String;)Lcom/nquantum/module/Module;
        //   291: invokevirtual   com/nquantum/module/Module.isToggled:()Z
        //   294: istore_1       
        //   295: aload_0        
        //   296: getfield        net/minecraft/client/entity/EntityPlayerSP.movementInput:Lnet/minecraft/util/MovementInput;
        //   299: getfield        net/minecraft/util/MovementInput.jump:Z
        //   302: istore_2       
        //   303: aload_0        
        //   304: getfield        net/minecraft/client/entity/EntityPlayerSP.movementInput:Lnet/minecraft/util/MovementInput;
        //   307: getfield        net/minecraft/util/MovementInput.sneak:Z
        //   310: istore_3       
        //   311: ldc             0.8
        //   313: fstore          4
        //   315: aload_0        
        //   316: getfield        net/minecraft/client/entity/EntityPlayerSP.movementInput:Lnet/minecraft/util/MovementInput;
        //   319: getfield        net/minecraft/util/MovementInput.moveForward:F
        //   322: fload           4
        //   324: fcmpl          
        //   325: iflt            332
        //   328: iconst_1       
        //   329: goto            333
        //   332: iconst_0       
        //   333: istore          5
        //   335: aload_0        
        //   336: getfield        net/minecraft/client/entity/EntityPlayerSP.movementInput:Lnet/minecraft/util/MovementInput;
        //   339: invokevirtual   net/minecraft/util/MovementInput.updatePlayerMoveState:()V
        //   342: aload_0        
        //   343: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.isUsingItem:()Z
        //   346: ifeq            411
        //   349: aload_0        
        //   350: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.isRiding:()Z
        //   353: ifne            411
        //   356: aload_0        
        //   357: getfield        net/minecraft/client/entity/EntityPlayerSP.movementInput:Lnet/minecraft/util/MovementInput;
        //   360: dup            
        //   361: getfield        net/minecraft/util/MovementInput.moveStrafe:F
        //   364: iload_1        
        //   365: ifeq            374
        //   368: ldc_w           0.99
        //   371: goto            377
        //   374: ldc_w           0.2
        //   377: fmul           
        //   378: putfield        net/minecraft/util/MovementInput.moveStrafe:F
        //   381: aload_0        
        //   382: getfield        net/minecraft/client/entity/EntityPlayerSP.movementInput:Lnet/minecraft/util/MovementInput;
        //   385: dup            
        //   386: getfield        net/minecraft/util/MovementInput.moveForward:F
        //   389: iload_1        
        //   390: ifeq            399
        //   393: ldc_w           0.99
        //   396: goto            402
        //   399: ldc_w           0.2
        //   402: fmul           
        //   403: putfield        net/minecraft/util/MovementInput.moveForward:F
        //   406: aload_0        
        //   407: iconst_0       
        //   408: putfield        net/minecraft/client/entity/EntityPlayerSP.sprintToggleTimer:I
        //   411: aload_0        
        //   412: aload_0        
        //   413: getfield        net/minecraft/client/entity/EntityPlayerSP.posX:D
        //   416: aload_0        
        //   417: getfield        net/minecraft/client/entity/EntityPlayerSP.width:F
        //   420: f2d            
        //   421: ldc2_w          0.35
        //   424: dmul           
        //   425: dsub           
        //   426: aload_0        
        //   427: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //   430: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //   433: ldc2_w          0.5
        //   436: dadd           
        //   437: aload_0        
        //   438: getfield        net/minecraft/client/entity/EntityPlayerSP.posZ:D
        //   441: aload_0        
        //   442: getfield        net/minecraft/client/entity/EntityPlayerSP.width:F
        //   445: f2d            
        //   446: ldc2_w          0.35
        //   449: dmul           
        //   450: dadd           
        //   451: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.pushOutOfBlocks:(DDD)Z
        //   454: pop            
        //   455: aload_0        
        //   456: aload_0        
        //   457: getfield        net/minecraft/client/entity/EntityPlayerSP.posX:D
        //   460: aload_0        
        //   461: getfield        net/minecraft/client/entity/EntityPlayerSP.width:F
        //   464: f2d            
        //   465: ldc2_w          0.35
        //   468: dmul           
        //   469: dsub           
        //   470: aload_0        
        //   471: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //   474: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //   477: ldc2_w          0.5
        //   480: dadd           
        //   481: aload_0        
        //   482: getfield        net/minecraft/client/entity/EntityPlayerSP.posZ:D
        //   485: aload_0        
        //   486: getfield        net/minecraft/client/entity/EntityPlayerSP.width:F
        //   489: f2d            
        //   490: ldc2_w          0.35
        //   493: dmul           
        //   494: dsub           
        //   495: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.pushOutOfBlocks:(DDD)Z
        //   498: pop            
        //   499: aload_0        
        //   500: aload_0        
        //   501: getfield        net/minecraft/client/entity/EntityPlayerSP.posX:D
        //   504: aload_0        
        //   505: getfield        net/minecraft/client/entity/EntityPlayerSP.width:F
        //   508: f2d            
        //   509: ldc2_w          0.35
        //   512: dmul           
        //   513: dadd           
        //   514: aload_0        
        //   515: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //   518: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //   521: ldc2_w          0.5
        //   524: dadd           
        //   525: aload_0        
        //   526: getfield        net/minecraft/client/entity/EntityPlayerSP.posZ:D
        //   529: aload_0        
        //   530: getfield        net/minecraft/client/entity/EntityPlayerSP.width:F
        //   533: f2d            
        //   534: ldc2_w          0.35
        //   537: dmul           
        //   538: dsub           
        //   539: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.pushOutOfBlocks:(DDD)Z
        //   542: pop            
        //   543: aload_0        
        //   544: aload_0        
        //   545: getfield        net/minecraft/client/entity/EntityPlayerSP.posX:D
        //   548: aload_0        
        //   549: getfield        net/minecraft/client/entity/EntityPlayerSP.width:F
        //   552: f2d            
        //   553: ldc2_w          0.35
        //   556: dmul           
        //   557: dadd           
        //   558: aload_0        
        //   559: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //   562: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //   565: ldc2_w          0.5
        //   568: dadd           
        //   569: aload_0        
        //   570: getfield        net/minecraft/client/entity/EntityPlayerSP.posZ:D
        //   573: aload_0        
        //   574: getfield        net/minecraft/client/entity/EntityPlayerSP.width:F
        //   577: f2d            
        //   578: ldc2_w          0.35
        //   581: dmul           
        //   582: dadd           
        //   583: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.pushOutOfBlocks:(DDD)Z
        //   586: pop            
        //   587: aload_0        
        //   588: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getFoodStats:()Lnet/minecraft/util/FoodStats;
        //   591: invokevirtual   net/minecraft/util/FoodStats.getFoodLevel:()I
        //   594: i2f            
        //   595: ldc_w           6.0
        //   598: fcmpl          
        //   599: ifgt            612
        //   602: aload_0        
        //   603: getfield        net/minecraft/client/entity/EntityPlayerSP.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //   606: getfield        net/minecraft/entity/player/PlayerCapabilities.allowFlying:Z
        //   609: ifeq            616
        //   612: iconst_1       
        //   613: goto            617
        //   616: iconst_0       
        //   617: istore          6
        //   619: aload_0        
        //   620: getfield        net/minecraft/client/entity/EntityPlayerSP.onGround:Z
        //   623: ifeq            714
        //   626: iload_3        
        //   627: ifne            714
        //   630: iload           5
        //   632: ifne            714
        //   635: aload_0        
        //   636: getfield        net/minecraft/client/entity/EntityPlayerSP.movementInput:Lnet/minecraft/util/MovementInput;
        //   639: getfield        net/minecraft/util/MovementInput.moveForward:F
        //   642: fload           4
        //   644: fcmpl          
        //   645: iflt            714
        //   648: aload_0        
        //   649: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.isSprinting:()Z
        //   652: ifne            714
        //   655: iload           6
        //   657: ifeq            714
        //   660: aload_0        
        //   661: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.isUsingItem:()Z
        //   664: ifne            714
        //   667: aload_0        
        //   668: getstatic       net/minecraft/potion/Potion.blindness:Lnet/minecraft/potion/Potion;
        //   671: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.isPotionActive:(Lnet/minecraft/potion/Potion;)Z
        //   674: ifne            714
        //   677: aload_0        
        //   678: getfield        net/minecraft/client/entity/EntityPlayerSP.sprintToggleTimer:I
        //   681: ifgt            709
        //   684: aload_0        
        //   685: getfield        net/minecraft/client/entity/EntityPlayerSP.mc:Lnet/minecraft/client/Minecraft;
        //   688: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   691: getfield        net/minecraft/client/settings/GameSettings.keyBindSprint:Lnet/minecraft/client/settings/KeyBinding;
        //   694: invokevirtual   net/minecraft/client/settings/KeyBinding.isKeyDown:()Z
        //   697: ifne            709
        //   700: aload_0        
        //   701: bipush          7
        //   703: putfield        net/minecraft/client/entity/EntityPlayerSP.sprintToggleTimer:I
        //   706: goto            714
        //   709: aload_0        
        //   710: iconst_1       
        //   711: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.setSprinting:(Z)V
        //   714: aload_0        
        //   715: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.isSprinting:()Z
        //   718: ifne            777
        //   721: aload_0        
        //   722: getfield        net/minecraft/client/entity/EntityPlayerSP.movementInput:Lnet/minecraft/util/MovementInput;
        //   725: getfield        net/minecraft/util/MovementInput.moveForward:F
        //   728: fload           4
        //   730: fcmpl          
        //   731: iflt            777
        //   734: iload           6
        //   736: ifeq            777
        //   739: aload_0        
        //   740: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.isUsingItem:()Z
        //   743: ifne            777
        //   746: aload_0        
        //   747: getstatic       net/minecraft/potion/Potion.blindness:Lnet/minecraft/potion/Potion;
        //   750: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.isPotionActive:(Lnet/minecraft/potion/Potion;)Z
        //   753: ifne            777
        //   756: aload_0        
        //   757: getfield        net/minecraft/client/entity/EntityPlayerSP.mc:Lnet/minecraft/client/Minecraft;
        //   760: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   763: getfield        net/minecraft/client/settings/GameSettings.keyBindSprint:Lnet/minecraft/client/settings/KeyBinding;
        //   766: invokevirtual   net/minecraft/client/settings/KeyBinding.isKeyDown:()Z
        //   769: ifeq            777
        //   772: aload_0        
        //   773: iconst_1       
        //   774: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.setSprinting:(Z)V
        //   777: aload_0        
        //   778: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.isSprinting:()Z
        //   781: ifeq            814
        //   784: aload_0        
        //   785: getfield        net/minecraft/client/entity/EntityPlayerSP.movementInput:Lnet/minecraft/util/MovementInput;
        //   788: getfield        net/minecraft/util/MovementInput.moveForward:F
        //   791: fload           4
        //   793: fcmpg          
        //   794: iflt            809
        //   797: aload_0        
        //   798: getfield        net/minecraft/client/entity/EntityPlayerSP.isCollidedHorizontally:Z
        //   801: ifne            809
        //   804: iload           6
        //   806: ifne            814
        //   809: aload_0        
        //   810: iconst_0       
        //   811: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.setSprinting:(Z)V
        //   814: aload_0        
        //   815: getfield        net/minecraft/client/entity/EntityPlayerSP.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //   818: getfield        net/minecraft/entity/player/PlayerCapabilities.allowFlying:Z
        //   821: ifeq            923
        //   824: aload_0        
        //   825: getfield        net/minecraft/client/entity/EntityPlayerSP.mc:Lnet/minecraft/client/Minecraft;
        //   828: getfield        net/minecraft/client/Minecraft.playerController:Lnet/minecraft/client/multiplayer/PlayerControllerMP;
        //   831: invokevirtual   net/minecraft/client/multiplayer/PlayerControllerMP.isSpectatorMode:()Z
        //   834: ifeq            862
        //   837: aload_0        
        //   838: getfield        net/minecraft/client/entity/EntityPlayerSP.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //   841: getfield        net/minecraft/entity/player/PlayerCapabilities.isFlying:Z
        //   844: ifne            923
        //   847: aload_0        
        //   848: getfield        net/minecraft/client/entity/EntityPlayerSP.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //   851: iconst_1       
        //   852: putfield        net/minecraft/entity/player/PlayerCapabilities.isFlying:Z
        //   855: aload_0        
        //   856: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.sendPlayerAbilities:()V
        //   859: goto            923
        //   862: iload_2        
        //   863: ifne            923
        //   866: aload_0        
        //   867: getfield        net/minecraft/client/entity/EntityPlayerSP.movementInput:Lnet/minecraft/util/MovementInput;
        //   870: getfield        net/minecraft/util/MovementInput.jump:Z
        //   873: ifeq            923
        //   876: aload_0        
        //   877: getfield        net/minecraft/client/entity/EntityPlayerSP.flyToggleTimer:I
        //   880: ifne            892
        //   883: aload_0        
        //   884: bipush          7
        //   886: putfield        net/minecraft/client/entity/EntityPlayerSP.flyToggleTimer:I
        //   889: goto            923
        //   892: aload_0        
        //   893: getfield        net/minecraft/client/entity/EntityPlayerSP.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //   896: aload_0        
        //   897: getfield        net/minecraft/client/entity/EntityPlayerSP.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //   900: getfield        net/minecraft/entity/player/PlayerCapabilities.isFlying:Z
        //   903: ifne            910
        //   906: iconst_1       
        //   907: goto            911
        //   910: iconst_0       
        //   911: putfield        net/minecraft/entity/player/PlayerCapabilities.isFlying:Z
        //   914: aload_0        
        //   915: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.sendPlayerAbilities:()V
        //   918: aload_0        
        //   919: iconst_0       
        //   920: putfield        net/minecraft/client/entity/EntityPlayerSP.flyToggleTimer:I
        //   923: aload_0        
        //   924: getfield        net/minecraft/client/entity/EntityPlayerSP.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //   927: getfield        net/minecraft/entity/player/PlayerCapabilities.isFlying:Z
        //   930: ifeq            999
        //   933: aload_0        
        //   934: if_acmpne       999
        //   937: aload_0        
        //   938: getfield        net/minecraft/client/entity/EntityPlayerSP.movementInput:Lnet/minecraft/util/MovementInput;
        //   941: getfield        net/minecraft/util/MovementInput.sneak:Z
        //   944: ifeq            968
        //   947: aload_0        
        //   948: dup            
        //   949: getfield        net/minecraft/client/entity/EntityPlayerSP.motionY:D
        //   952: aload_0        
        //   953: getfield        net/minecraft/client/entity/EntityPlayerSP.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //   956: invokevirtual   net/minecraft/entity/player/PlayerCapabilities.getFlySpeed:()F
        //   959: ldc_w           3.0
        //   962: fmul           
        //   963: f2d            
        //   964: dsub           
        //   965: putfield        net/minecraft/client/entity/EntityPlayerSP.motionY:D
        //   968: aload_0        
        //   969: getfield        net/minecraft/client/entity/EntityPlayerSP.movementInput:Lnet/minecraft/util/MovementInput;
        //   972: getfield        net/minecraft/util/MovementInput.jump:Z
        //   975: ifeq            999
        //   978: aload_0        
        //   979: dup            
        //   980: getfield        net/minecraft/client/entity/EntityPlayerSP.motionY:D
        //   983: aload_0        
        //   984: getfield        net/minecraft/client/entity/EntityPlayerSP.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //   987: invokevirtual   net/minecraft/entity/player/PlayerCapabilities.getFlySpeed:()F
        //   990: ldc_w           3.0
        //   993: fmul           
        //   994: f2d            
        //   995: dadd           
        //   996: putfield        net/minecraft/client/entity/EntityPlayerSP.motionY:D
        //   999: aload_0        
        //  1000: ifnull          1149
        //  1003: aload_0        
        //  1004: getfield        net/minecraft/client/entity/EntityPlayerSP.horseJumpPowerCounter:I
        //  1007: ifge            1032
        //  1010: aload_0        
        //  1011: dup            
        //  1012: getfield        net/minecraft/client/entity/EntityPlayerSP.horseJumpPowerCounter:I
        //  1015: iconst_1       
        //  1016: iadd           
        //  1017: putfield        net/minecraft/client/entity/EntityPlayerSP.horseJumpPowerCounter:I
        //  1020: aload_0        
        //  1021: getfield        net/minecraft/client/entity/EntityPlayerSP.horseJumpPowerCounter:I
        //  1024: ifne            1032
        //  1027: aload_0        
        //  1028: fconst_0       
        //  1029: putfield        net/minecraft/client/entity/EntityPlayerSP.horseJumpPower:F
        //  1032: iload_2        
        //  1033: ifeq            1059
        //  1036: aload_0        
        //  1037: getfield        net/minecraft/client/entity/EntityPlayerSP.movementInput:Lnet/minecraft/util/MovementInput;
        //  1040: getfield        net/minecraft/util/MovementInput.jump:Z
        //  1043: ifne            1059
        //  1046: aload_0        
        //  1047: bipush          -10
        //  1049: putfield        net/minecraft/client/entity/EntityPlayerSP.horseJumpPowerCounter:I
        //  1052: aload_0        
        //  1053: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.sendHorseJump:()V
        //  1056: goto            1154
        //  1059: iload_2        
        //  1060: ifne            1086
        //  1063: aload_0        
        //  1064: getfield        net/minecraft/client/entity/EntityPlayerSP.movementInput:Lnet/minecraft/util/MovementInput;
        //  1067: getfield        net/minecraft/util/MovementInput.jump:Z
        //  1070: ifeq            1086
        //  1073: aload_0        
        //  1074: iconst_0       
        //  1075: putfield        net/minecraft/client/entity/EntityPlayerSP.horseJumpPowerCounter:I
        //  1078: aload_0        
        //  1079: fconst_0       
        //  1080: putfield        net/minecraft/client/entity/EntityPlayerSP.horseJumpPower:F
        //  1083: goto            1154
        //  1086: iload_2        
        //  1087: ifeq            1154
        //  1090: aload_0        
        //  1091: dup            
        //  1092: getfield        net/minecraft/client/entity/EntityPlayerSP.horseJumpPowerCounter:I
        //  1095: iconst_1       
        //  1096: iadd           
        //  1097: putfield        net/minecraft/client/entity/EntityPlayerSP.horseJumpPowerCounter:I
        //  1100: aload_0        
        //  1101: getfield        net/minecraft/client/entity/EntityPlayerSP.horseJumpPowerCounter:I
        //  1104: bipush          10
        //  1106: if_icmpge       1125
        //  1109: aload_0        
        //  1110: aload_0        
        //  1111: getfield        net/minecraft/client/entity/EntityPlayerSP.horseJumpPowerCounter:I
        //  1114: i2f            
        //  1115: ldc_w           0.1
        //  1118: fmul           
        //  1119: putfield        net/minecraft/client/entity/EntityPlayerSP.horseJumpPower:F
        //  1122: goto            1154
        //  1125: aload_0        
        //  1126: ldc             0.8
        //  1128: fconst_2       
        //  1129: aload_0        
        //  1130: getfield        net/minecraft/client/entity/EntityPlayerSP.horseJumpPowerCounter:I
        //  1133: bipush          9
        //  1135: isub           
        //  1136: i2f            
        //  1137: fdiv           
        //  1138: ldc_w           0.1
        //  1141: fmul           
        //  1142: fadd           
        //  1143: putfield        net/minecraft/client/entity/EntityPlayerSP.horseJumpPower:F
        //  1146: goto            1154
        //  1149: aload_0        
        //  1150: fconst_0       
        //  1151: putfield        net/minecraft/client/entity/EntityPlayerSP.horseJumpPower:F
        //  1154: aload_0        
        //  1155: invokespecial   net/minecraft/client/entity/AbstractClientPlayer.onLivingUpdate:()V
        //  1158: aload_0        
        //  1159: getfield        net/minecraft/client/entity/EntityPlayerSP.onGround:Z
        //  1162: ifeq            1200
        //  1165: aload_0        
        //  1166: getfield        net/minecraft/client/entity/EntityPlayerSP.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //  1169: getfield        net/minecraft/entity/player/PlayerCapabilities.isFlying:Z
        //  1172: ifeq            1200
        //  1175: aload_0        
        //  1176: getfield        net/minecraft/client/entity/EntityPlayerSP.mc:Lnet/minecraft/client/Minecraft;
        //  1179: getfield        net/minecraft/client/Minecraft.playerController:Lnet/minecraft/client/multiplayer/PlayerControllerMP;
        //  1182: invokevirtual   net/minecraft/client/multiplayer/PlayerControllerMP.isSpectatorMode:()Z
        //  1185: ifne            1200
        //  1188: aload_0        
        //  1189: getfield        net/minecraft/client/entity/EntityPlayerSP.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //  1192: iconst_0       
        //  1193: putfield        net/minecraft/entity/player/PlayerCapabilities.isFlying:Z
        //  1196: aload_0        
        //  1197: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.sendPlayerAbilities:()V
        //  1200: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    protected void sendHorseJump() {
        this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.RIDING_JUMP, (int)(this.getHorseJumpPower() * 100.0f)));
    }
    
    public EntityPlayerSP(final Minecraft mc, final World world, final NetHandlerPlayClient sendQueue, final StatFileWriter statWriter) {
        super(world, sendQueue.getGameProfile());
        this.sendQueue = sendQueue;
        this.statWriter = statWriter;
        this.mc = mc;
        this.dimension = 0;
    }
    
    @Override
    public void displayGUIChest(final IInventory inventory) {
        final String s = (inventory instanceof IInteractionObject) ? ((IInteractionObject)inventory).getGuiID() : "minecraft:container";
        if ("minecraft:chest".equals(s)) {
            this.mc.displayGuiScreen(new GuiChest(this.inventory, inventory));
        }
        else if ("minecraft:hopper".equals(s)) {
            this.mc.displayGuiScreen(new GuiHopper(this.inventory, inventory));
        }
        else if ("minecraft:furnace".equals(s)) {
            this.mc.displayGuiScreen(new GuiFurnace(this.inventory, inventory));
        }
        else if ("minecraft:brewing_stand".equals(s)) {
            this.mc.displayGuiScreen(new GuiBrewingStand(this.inventory, inventory));
        }
        else if ("minecraft:beacon".equals(s)) {
            this.mc.displayGuiScreen(new GuiBeacon(this.inventory, inventory));
        }
        else if (!"minecraft:dispenser".equals(s) && !"minecraft:dropper".equals(s)) {
            this.mc.displayGuiScreen(new GuiChest(this.inventory, inventory));
        }
        else {
            this.mc.displayGuiScreen(new GuiDispenser(this.inventory, inventory));
        }
    }
    
    @Override
    public void openEditSign(final TileEntitySign tileEntitySign) {
        this.mc.displayGuiScreen(new GuiEditSign(tileEntitySign));
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final int n, final String s) {
        return n <= 0;
    }
    
    public void onUpdateWalkingPlayer() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: aload_0        
        //     5: getfield        net/minecraft/client/entity/EntityPlayerSP.rotationYaw:F
        //     8: aload_0        
        //     9: getfield        net/minecraft/client/entity/EntityPlayerSP.rotationPitch:F
        //    12: aload_0        
        //    13: getfield        net/minecraft/client/entity/EntityPlayerSP.onGround:Z
        //    16: aload_0        
        //    17: getfield        net/minecraft/client/entity/EntityPlayerSP.posX:D
        //    20: aload_0        
        //    21: getfield        net/minecraft/client/entity/EntityPlayerSP.posY:D
        //    24: aload_0        
        //    25: getfield        net/minecraft/client/entity/EntityPlayerSP.posZ:D
        //    28: invokespecial   com/nquantum/event/impl/EventPreMotionUpdate.<init>:(FFZDDD)V
        //    31: astore_1       
        //    32: aload_1        
        //    33: invokevirtual   com/nquantum/event/impl/EventPreMotionUpdate.call:()Lcom/nquantum/event/Event;
        //    36: pop            
        //    37: aload_0        
        //    38: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.isSprinting:()Z
        //    41: istore_2       
        //    42: iload_2        
        //    43: aload_0        
        //    44: getfield        net/minecraft/client/entity/EntityPlayerSP.serverSprintState:Z
        //    47: if_icmpeq       98
        //    50: iload_2        
        //    51: ifeq            75
        //    54: aload_0        
        //    55: getfield        net/minecraft/client/entity/EntityPlayerSP.sendQueue:Lnet/minecraft/client/network/NetHandlerPlayClient;
        //    58: new             Lnet/minecraft/network/play/client/C0BPacketEntityAction;
        //    61: dup            
        //    62: aload_0        
        //    63: getstatic       net/minecraft/network/play/client/C0BPacketEntityAction$Action.START_SPRINTING:Lnet/minecraft/network/play/client/C0BPacketEntityAction$Action;
        //    66: invokespecial   net/minecraft/network/play/client/C0BPacketEntityAction.<init>:(Lnet/minecraft/entity/Entity;Lnet/minecraft/network/play/client/C0BPacketEntityAction$Action;)V
        //    69: invokevirtual   net/minecraft/client/network/NetHandlerPlayClient.addToSendQueue:(Lnet/minecraft/network/Packet;)V
        //    72: goto            93
        //    75: aload_0        
        //    76: getfield        net/minecraft/client/entity/EntityPlayerSP.sendQueue:Lnet/minecraft/client/network/NetHandlerPlayClient;
        //    79: new             Lnet/minecraft/network/play/client/C0BPacketEntityAction;
        //    82: dup            
        //    83: aload_0        
        //    84: getstatic       net/minecraft/network/play/client/C0BPacketEntityAction$Action.STOP_SPRINTING:Lnet/minecraft/network/play/client/C0BPacketEntityAction$Action;
        //    87: invokespecial   net/minecraft/network/play/client/C0BPacketEntityAction.<init>:(Lnet/minecraft/entity/Entity;Lnet/minecraft/network/play/client/C0BPacketEntityAction$Action;)V
        //    90: invokevirtual   net/minecraft/client/network/NetHandlerPlayClient.addToSendQueue:(Lnet/minecraft/network/Packet;)V
        //    93: aload_0        
        //    94: iload_2        
        //    95: putfield        net/minecraft/client/entity/EntityPlayerSP.serverSprintState:Z
        //    98: aload_0        
        //    99: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.isSneaking:()Z
        //   102: istore_3       
        //   103: iload_3        
        //   104: aload_0        
        //   105: getfield        net/minecraft/client/entity/EntityPlayerSP.serverSneakState:Z
        //   108: if_icmpeq       159
        //   111: iload_3        
        //   112: ifeq            136
        //   115: aload_0        
        //   116: getfield        net/minecraft/client/entity/EntityPlayerSP.sendQueue:Lnet/minecraft/client/network/NetHandlerPlayClient;
        //   119: new             Lnet/minecraft/network/play/client/C0BPacketEntityAction;
        //   122: dup            
        //   123: aload_0        
        //   124: getstatic       net/minecraft/network/play/client/C0BPacketEntityAction$Action.START_SNEAKING:Lnet/minecraft/network/play/client/C0BPacketEntityAction$Action;
        //   127: invokespecial   net/minecraft/network/play/client/C0BPacketEntityAction.<init>:(Lnet/minecraft/entity/Entity;Lnet/minecraft/network/play/client/C0BPacketEntityAction$Action;)V
        //   130: invokevirtual   net/minecraft/client/network/NetHandlerPlayClient.addToSendQueue:(Lnet/minecraft/network/Packet;)V
        //   133: goto            154
        //   136: aload_0        
        //   137: getfield        net/minecraft/client/entity/EntityPlayerSP.sendQueue:Lnet/minecraft/client/network/NetHandlerPlayClient;
        //   140: new             Lnet/minecraft/network/play/client/C0BPacketEntityAction;
        //   143: dup            
        //   144: aload_0        
        //   145: getstatic       net/minecraft/network/play/client/C0BPacketEntityAction$Action.STOP_SNEAKING:Lnet/minecraft/network/play/client/C0BPacketEntityAction$Action;
        //   148: invokespecial   net/minecraft/network/play/client/C0BPacketEntityAction.<init>:(Lnet/minecraft/entity/Entity;Lnet/minecraft/network/play/client/C0BPacketEntityAction$Action;)V
        //   151: invokevirtual   net/minecraft/client/network/NetHandlerPlayClient.addToSendQueue:(Lnet/minecraft/network/Packet;)V
        //   154: aload_0        
        //   155: iload_3        
        //   156: putfield        net/minecraft/client/entity/EntityPlayerSP.serverSneakState:Z
        //   159: aload_0        
        //   160: if_acmpne       540
        //   163: aload_0        
        //   164: getfield        net/minecraft/client/entity/EntityPlayerSP.posX:D
        //   167: aload_0        
        //   168: getfield        net/minecraft/client/entity/EntityPlayerSP.lastReportedPosX:D
        //   171: dsub           
        //   172: dstore          4
        //   174: aload_0        
        //   175: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //   178: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //   181: aload_0        
        //   182: getfield        net/minecraft/client/entity/EntityPlayerSP.lastReportedPosY:D
        //   185: dsub           
        //   186: dstore          6
        //   188: aload_0        
        //   189: getfield        net/minecraft/client/entity/EntityPlayerSP.posZ:D
        //   192: aload_0        
        //   193: getfield        net/minecraft/client/entity/EntityPlayerSP.lastReportedPosZ:D
        //   196: dsub           
        //   197: dstore          8
        //   199: aload_0        
        //   200: getfield        net/minecraft/client/entity/EntityPlayerSP.rotationYaw:F
        //   203: aload_0        
        //   204: getfield        net/minecraft/client/entity/EntityPlayerSP.lastReportedYaw:F
        //   207: fsub           
        //   208: f2d            
        //   209: dstore          10
        //   211: aload_0        
        //   212: getfield        net/minecraft/client/entity/EntityPlayerSP.rotationPitch:F
        //   215: aload_0        
        //   216: getfield        net/minecraft/client/entity/EntityPlayerSP.lastReportedPitch:F
        //   219: fsub           
        //   220: f2d            
        //   221: dstore          12
        //   223: dload           4
        //   225: dload           4
        //   227: dmul           
        //   228: dload           6
        //   230: dload           6
        //   232: dmul           
        //   233: dadd           
        //   234: dload           8
        //   236: dload           8
        //   238: dmul           
        //   239: dadd           
        //   240: ldc2_w          9.0E-4
        //   243: dcmpl          
        //   244: ifgt            256
        //   247: aload_0        
        //   248: getfield        net/minecraft/client/entity/EntityPlayerSP.positionUpdateTicks:I
        //   251: bipush          20
        //   253: if_icmplt       260
        //   256: iconst_1       
        //   257: goto            261
        //   260: iconst_0       
        //   261: istore          14
        //   263: dload           10
        //   265: dconst_0       
        //   266: dcmpl          
        //   267: ifne            277
        //   270: dload           12
        //   272: dconst_0       
        //   273: dcmpl          
        //   274: ifeq            281
        //   277: iconst_1       
        //   278: goto            282
        //   281: iconst_0       
        //   282: istore          15
        //   284: aload_0        
        //   285: getfield        net/minecraft/client/entity/EntityPlayerSP.ridingEntity:Lnet/minecraft/entity/Entity;
        //   288: ifnonnull       437
        //   291: goto            382
        //   294: iload           15
        //   296: ifeq            343
        //   299: aload_0        
        //   300: getfield        net/minecraft/client/entity/EntityPlayerSP.sendQueue:Lnet/minecraft/client/network/NetHandlerPlayClient;
        //   303: new             Lnet/minecraft/network/play/client/C03PacketPlayer$C06PacketPlayerPosLook;
        //   306: dup            
        //   307: aload_0        
        //   308: getfield        net/minecraft/client/entity/EntityPlayerSP.posX:D
        //   311: aload_0        
        //   312: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //   315: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //   318: aload_0        
        //   319: getfield        net/minecraft/client/entity/EntityPlayerSP.posZ:D
        //   322: aload_0        
        //   323: getfield        net/minecraft/client/entity/EntityPlayerSP.rotationYaw:F
        //   326: aload_0        
        //   327: getfield        net/minecraft/client/entity/EntityPlayerSP.rotationPitch:F
        //   330: aload_0        
        //   331: getfield        net/minecraft/client/entity/EntityPlayerSP.onGround:Z
        //   334: invokespecial   net/minecraft/network/play/client/C03PacketPlayer$C06PacketPlayerPosLook.<init>:(DDDFFZ)V
        //   337: invokevirtual   net/minecraft/client/network/NetHandlerPlayClient.addToSendQueue:(Lnet/minecraft/network/Packet;)V
        //   340: goto            474
        //   343: goto            382
        //   346: aload_0        
        //   347: getfield        net/minecraft/client/entity/EntityPlayerSP.sendQueue:Lnet/minecraft/client/network/NetHandlerPlayClient;
        //   350: new             Lnet/minecraft/network/play/client/C03PacketPlayer$C04PacketPlayerPosition;
        //   353: dup            
        //   354: aload_0        
        //   355: getfield        net/minecraft/client/entity/EntityPlayerSP.posX:D
        //   358: aload_0        
        //   359: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //   362: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //   365: aload_0        
        //   366: getfield        net/minecraft/client/entity/EntityPlayerSP.posZ:D
        //   369: aload_0        
        //   370: getfield        net/minecraft/client/entity/EntityPlayerSP.onGround:Z
        //   373: invokespecial   net/minecraft/network/play/client/C03PacketPlayer$C04PacketPlayerPosition.<init>:(DDDZ)V
        //   376: invokevirtual   net/minecraft/client/network/NetHandlerPlayClient.addToSendQueue:(Lnet/minecraft/network/Packet;)V
        //   379: goto            474
        //   382: iload           15
        //   384: ifeq            416
        //   387: aload_0        
        //   388: getfield        net/minecraft/client/entity/EntityPlayerSP.sendQueue:Lnet/minecraft/client/network/NetHandlerPlayClient;
        //   391: new             Lnet/minecraft/network/play/client/C03PacketPlayer$C05PacketPlayerLook;
        //   394: dup            
        //   395: aload_0        
        //   396: getfield        net/minecraft/client/entity/EntityPlayerSP.rotationYaw:F
        //   399: aload_0        
        //   400: getfield        net/minecraft/client/entity/EntityPlayerSP.rotationPitch:F
        //   403: aload_0        
        //   404: getfield        net/minecraft/client/entity/EntityPlayerSP.onGround:Z
        //   407: invokespecial   net/minecraft/network/play/client/C03PacketPlayer$C05PacketPlayerLook.<init>:(FFZ)V
        //   410: invokevirtual   net/minecraft/client/network/NetHandlerPlayClient.addToSendQueue:(Lnet/minecraft/network/Packet;)V
        //   413: goto            474
        //   416: aload_0        
        //   417: getfield        net/minecraft/client/entity/EntityPlayerSP.sendQueue:Lnet/minecraft/client/network/NetHandlerPlayClient;
        //   420: new             Lnet/minecraft/network/play/client/C03PacketPlayer;
        //   423: dup            
        //   424: aload_0        
        //   425: getfield        net/minecraft/client/entity/EntityPlayerSP.onGround:Z
        //   428: invokespecial   net/minecraft/network/play/client/C03PacketPlayer.<init>:(Z)V
        //   431: invokevirtual   net/minecraft/client/network/NetHandlerPlayClient.addToSendQueue:(Lnet/minecraft/network/Packet;)V
        //   434: goto            474
        //   437: aload_0        
        //   438: getfield        net/minecraft/client/entity/EntityPlayerSP.sendQueue:Lnet/minecraft/client/network/NetHandlerPlayClient;
        //   441: new             Lnet/minecraft/network/play/client/C03PacketPlayer$C06PacketPlayerPosLook;
        //   444: dup            
        //   445: aload_0        
        //   446: getfield        net/minecraft/client/entity/EntityPlayerSP.motionX:D
        //   449: ldc2_w          -999.0
        //   452: aload_0        
        //   453: getfield        net/minecraft/client/entity/EntityPlayerSP.motionZ:D
        //   456: aload_0        
        //   457: getfield        net/minecraft/client/entity/EntityPlayerSP.rotationYaw:F
        //   460: aload_0        
        //   461: getfield        net/minecraft/client/entity/EntityPlayerSP.rotationPitch:F
        //   464: aload_0        
        //   465: getfield        net/minecraft/client/entity/EntityPlayerSP.onGround:Z
        //   468: invokespecial   net/minecraft/network/play/client/C03PacketPlayer$C06PacketPlayerPosLook.<init>:(DDDFFZ)V
        //   471: invokevirtual   net/minecraft/client/network/NetHandlerPlayClient.addToSendQueue:(Lnet/minecraft/network/Packet;)V
        //   474: aload_0        
        //   475: dup            
        //   476: getfield        net/minecraft/client/entity/EntityPlayerSP.positionUpdateTicks:I
        //   479: iconst_1       
        //   480: iadd           
        //   481: putfield        net/minecraft/client/entity/EntityPlayerSP.positionUpdateTicks:I
        //   484: goto            519
        //   487: aload_0        
        //   488: aload_0        
        //   489: getfield        net/minecraft/client/entity/EntityPlayerSP.posX:D
        //   492: putfield        net/minecraft/client/entity/EntityPlayerSP.lastReportedPosX:D
        //   495: aload_0        
        //   496: aload_0        
        //   497: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //   500: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //   503: putfield        net/minecraft/client/entity/EntityPlayerSP.lastReportedPosY:D
        //   506: aload_0        
        //   507: aload_0        
        //   508: getfield        net/minecraft/client/entity/EntityPlayerSP.posZ:D
        //   511: putfield        net/minecraft/client/entity/EntityPlayerSP.lastReportedPosZ:D
        //   514: aload_0        
        //   515: iconst_0       
        //   516: putfield        net/minecraft/client/entity/EntityPlayerSP.positionUpdateTicks:I
        //   519: iload           15
        //   521: ifeq            540
        //   524: aload_0        
        //   525: aload_0        
        //   526: getfield        net/minecraft/client/entity/EntityPlayerSP.rotationYaw:F
        //   529: putfield        net/minecraft/client/entity/EntityPlayerSP.lastReportedYaw:F
        //   532: aload_0        
        //   533: aload_0        
        //   534: getfield        net/minecraft/client/entity/EntityPlayerSP.rotationPitch:F
        //   537: putfield        net/minecraft/client/entity/EntityPlayerSP.lastReportedPitch:F
        //   540: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public void displayGUIHorse(final EntityHorse entityHorse, final IInventory inventory) {
        this.mc.displayGuiScreen(new GuiScreenHorseInventory(this.inventory, inventory, entityHorse));
    }
    
    @Override
    public void openEditCommandBlock(final CommandBlockLogic commandBlockLogic) {
        this.mc.displayGuiScreen(new GuiCommandBlock(commandBlockLogic));
    }
    
    @Override
    public void sendPlayerAbilities() {
        this.sendQueue.addToSendQueue(new C13PacketPlayerAbilities(this.capabilities));
    }
    
    public String getClientBrand() {
        return this.clientBrand;
    }
    
    @Override
    public void addChatComponentMessage(final IChatComponent chatComponent) {
        this.mc.ingameGUI.getChatGUI().printChatMessage(chatComponent);
    }
    
    @Override
    public BlockPos getPosition() {
        return new BlockPos(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5);
    }
    
    @Override
    public void onCriticalHit(final Entity entity) {
        this.mc.effectRenderer.emitParticleAtEntity(entity, EnumParticleTypes.CRIT);
    }
    
    @Override
    public void respawnPlayer() {
        this.sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
    }
    
    public boolean isServerWorld() {
        return true;
    }
    
    public StatFileWriter getStatFileWriter() {
        return this.statWriter;
    }
    
    public void setPlayerSPHealth(final float n) {
        if (this.hasValidHealth) {
            final float lastDamage = this.getHealth() - n;
            if (lastDamage <= 0.0f) {
                this.setHealth(n);
                if (lastDamage < 0.0f) {
                    this.hurtResistantTime = this.maxHurtResistantTime / 2;
                }
            }
            else {
                this.lastDamage = lastDamage;
                this.setHealth(this.getHealth());
                this.hurtResistantTime = this.maxHurtResistantTime;
                this.damageEntity(DamageSource.generic, lastDamage);
                final int n2 = 10;
                this.maxHurtTime = n2;
                this.hurtTime = n2;
            }
        }
        else {
            this.setHealth(n);
            this.hasValidHealth = true;
        }
    }
    
    public float getHorseJumpPower() {
        return this.horseJumpPower;
    }
    
    @Override
    public void heal(final float n) {
    }
    
    @Override
    public boolean isSneaking() {
        return this.movementInput != null && this.movementInput.sneak && !this.sleeping;
    }
    
    @Override
    public void displayVillagerTradeGui(final IMerchant merchant) {
        this.mc.displayGuiScreen(new GuiMerchant(this.inventory, merchant, this.worldObj));
    }
    
    public float getDirection() {
        float rotationYaw = this.rotationYaw;
        if (this.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float n;
        if (this.moveForward < 0.0f) {
            n = -0.5f;
        }
        else if (this.moveForward > 0.0f) {
            n = 0.5f;
        }
        else {
            n = 1.0f;
        }
        if (this.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * n;
        }
        if (this.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * n;
        }
        return rotationYaw * 0.017453292f;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        return false;
    }
    
    @Override
    public void mountEntity(final Entity entity) {
        super.mountEntity(entity);
        if (entity instanceof EntityMinecart) {
            this.mc.getSoundHandler().playSound(new MovingSoundMinecartRiding(this, (EntityMinecart)entity));
        }
    }
    
    @Override
    public EntityItem dropOneItem(final boolean b) {
        this.sendQueue.addToSendQueue(new C07PacketPlayerDigging(b ? C07PacketPlayerDigging.Action.DROP_ALL_ITEMS : C07PacketPlayerDigging.Action.DROP_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        return null;
    }
    
    @Override
    protected boolean pushOutOfBlocks(final double p0, final double p1, final double p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/client/entity/EntityPlayerSP.noClip:Z
        //     4: ifeq            9
        //     7: iconst_0       
        //     8: ireturn        
        //     9: new             Lnet/minecraft/util/BlockPos;
        //    12: dup            
        //    13: dload_1        
        //    14: dload_3        
        //    15: dload           5
        //    17: invokespecial   net/minecraft/util/BlockPos.<init>:(DDD)V
        //    20: astore          7
        //    22: dload_1        
        //    23: aload           7
        //    25: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //    28: i2d            
        //    29: dsub           
        //    30: dstore          8
        //    32: dload           5
        //    34: aload           7
        //    36: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //    39: i2d            
        //    40: dsub           
        //    41: dstore          10
        //    43: aload_0        
        //    44: aload           7
        //    46: ifne            190
        //    49: ldc2_w          9999.0
        //    52: dstore          13
        //    54: aload_0        
        //    55: aload           7
        //    57: invokevirtual   net/minecraft/util/BlockPos.west:()Lnet/minecraft/util/BlockPos;
        //    60: ifne            75
        //    63: dload           8
        //    65: dload           13
        //    67: dcmpg          
        //    68: ifge            75
        //    71: dload           8
        //    73: dstore          13
        //    75: aload_0        
        //    76: aload           7
        //    78: invokevirtual   net/minecraft/util/BlockPos.east:()Lnet/minecraft/util/BlockPos;
        //    81: ifne            100
        //    84: dconst_1       
        //    85: dload           8
        //    87: dsub           
        //    88: dload           13
        //    90: dcmpg          
        //    91: ifge            100
        //    94: dconst_1       
        //    95: dload           8
        //    97: dsub           
        //    98: dstore          13
        //   100: aload_0        
        //   101: aload           7
        //   103: invokevirtual   net/minecraft/util/BlockPos.north:()Lnet/minecraft/util/BlockPos;
        //   106: ifne            121
        //   109: dload           10
        //   111: dload           13
        //   113: dcmpg          
        //   114: ifge            121
        //   117: dload           10
        //   119: dstore          13
        //   121: aload_0        
        //   122: aload           7
        //   124: invokevirtual   net/minecraft/util/BlockPos.south:()Lnet/minecraft/util/BlockPos;
        //   127: ifne            146
        //   130: dconst_1       
        //   131: dload           10
        //   133: dsub           
        //   134: dload           13
        //   136: dcmpg          
        //   137: ifge            146
        //   140: dconst_1       
        //   141: dload           10
        //   143: dsub           
        //   144: dstore          13
        //   146: ldc_w           0.1
        //   149: fstore          15
        //   151: goto            172
        //   154: aload_0        
        //   155: fload           15
        //   157: fneg           
        //   158: f2d            
        //   159: putfield        net/minecraft/client/entity/EntityPlayerSP.motionX:D
        //   162: goto            183
        //   165: aload_0        
        //   166: fload           15
        //   168: f2d            
        //   169: putfield        net/minecraft/client/entity/EntityPlayerSP.motionX:D
        //   172: goto            183
        //   175: aload_0        
        //   176: fload           15
        //   178: fneg           
        //   179: f2d            
        //   180: putfield        net/minecraft/client/entity/EntityPlayerSP.motionZ:D
        //   183: aload_0        
        //   184: fload           15
        //   186: f2d            
        //   187: putfield        net/minecraft/client/entity/EntityPlayerSP.motionZ:D
        //   190: iconst_0       
        //   191: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0190 (coming from #0187).
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
    public void onEnchantmentCritical(final Entity entity) {
        this.mc.effectRenderer.emitParticleAtEntity(entity, EnumParticleTypes.CRIT_MAGIC);
    }
    
    @Override
    public void playSound(final String s, final float n, final float n2) {
        this.worldObj.playSound(this.posX, this.posY, this.posZ, s, n, n2, false);
    }
    
    public void setXPStats(final float experience, final int experienceTotal, final int experienceLevel) {
        this.experience = experience;
        this.experienceTotal = experienceTotal;
        this.experienceLevel = experienceLevel;
    }
    
    public float getSpeed() {
        return (float)Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
    }
    
    public void closeScreenAndDropStack() {
        this.inventory.setItemStack(null);
        super.closeScreen();
        this.mc.displayGuiScreen(null);
    }
    
    public void closeScreen() {
        this.sendQueue.addToSendQueue(new C0DPacketCloseWindow(this.openContainer.windowId));
        this.closeScreenAndDropStack();
    }
    
    public void setSpeed(final float n) {
        this.motionX = -(Math.sin(this.getDirection()) * n);
        this.motionZ = Math.cos(this.getDirection()) * n;
    }
    
    @Override
    protected void joinEntityItemWithWorld(final EntityItem entityItem) {
    }
    
    @Override
    public void swingItem() {
        super.swingItem();
        this.sendQueue.addToSendQueue(new C0APacketAnimation());
    }
    
    @Override
    public void setSprinting(final boolean sprinting) {
        super.setSprinting(sprinting);
        this.sprintingTicksLeft = (sprinting ? 600 : 0);
    }
    
    @Override
    public void addChatMessage(final IChatComponent chatComponent) {
        this.mc.ingameGUI.getChatGUI().printChatMessage(chatComponent);
    }
    
    public void sendHorseInventory() {
        this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.OPEN_INVENTORY));
    }
    
    @Override
    public void onUpdate() {
        if (this.worldObj.isBlockLoaded(new BlockPos(this.posX, 0.0, this.posZ))) {
            new EventUpdate().call();
            super.onUpdate();
            if (this.isRiding()) {
                this.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
                this.sendQueue.addToSendQueue(new C0CPacketInput(this.moveStrafing, this.moveForward, this.movementInput.jump, this.movementInput.sneak));
            }
            else {
                this.onUpdateWalkingPlayer();
                new EventPostMotionUpdate(this.rotationYaw, this.rotationPitch, this.onGround, this.posX, this.posY, this.posZ).call();
            }
        }
    }
    
    public void setClientBrand(final String clientBrand) {
        this.clientBrand = clientBrand;
    }
    
    @Override
    public void displayGUIBook(final ItemStack itemStack) {
        if (itemStack.getItem() == Items.writable_book) {
            this.mc.displayGuiScreen(new GuiScreenBook(this, itemStack, true));
        }
    }
}
