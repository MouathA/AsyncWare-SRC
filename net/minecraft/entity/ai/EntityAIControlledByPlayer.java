package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;

public class EntityAIControlledByPlayer extends EntityAIBase
{
    private float currentSpeed;
    private int speedBoostTime;
    private final float maxSpeed;
    private final EntityLiving thisEntity;
    private int maxSpeedBoostTime;
    private boolean speedBoosted;
    
    @Override
    public void resetTask() {
        this.speedBoosted = false;
        this.currentSpeed = 0.0f;
    }
    
    public boolean isSpeedBoosted() {
        return this.speedBoosted;
    }
    
    @Override
    public void updateTask() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.thisEntity:Lnet/minecraft/entity/EntityLiving;
        //     4: getfield        net/minecraft/entity/EntityLiving.riddenByEntity:Lnet/minecraft/entity/Entity;
        //     7: checkcast       Lnet/minecraft/entity/player/EntityPlayer;
        //    10: astore_1       
        //    11: aload_0        
        //    12: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.thisEntity:Lnet/minecraft/entity/EntityLiving;
        //    15: checkcast       Lnet/minecraft/entity/EntityCreature;
        //    18: astore_2       
        //    19: aload_1        
        //    20: getfield        net/minecraft/entity/player/EntityPlayer.rotationYaw:F
        //    23: aload_0        
        //    24: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.thisEntity:Lnet/minecraft/entity/EntityLiving;
        //    27: getfield        net/minecraft/entity/EntityLiving.rotationYaw:F
        //    30: fsub           
        //    31: invokestatic    net/minecraft/util/MathHelper.wrapAngleTo180_float:(F)F
        //    34: ldc             0.5
        //    36: fmul           
        //    37: fstore_3       
        //    38: fload_3        
        //    39: ldc             5.0
        //    41: fcmpl          
        //    42: ifle            48
        //    45: ldc             5.0
        //    47: fstore_3       
        //    48: fload_3        
        //    49: ldc             -5.0
        //    51: fcmpg          
        //    52: ifge            58
        //    55: ldc             -5.0
        //    57: fstore_3       
        //    58: aload_0        
        //    59: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.thisEntity:Lnet/minecraft/entity/EntityLiving;
        //    62: aload_0        
        //    63: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.thisEntity:Lnet/minecraft/entity/EntityLiving;
        //    66: getfield        net/minecraft/entity/EntityLiving.rotationYaw:F
        //    69: fload_3        
        //    70: fadd           
        //    71: invokestatic    net/minecraft/util/MathHelper.wrapAngleTo180_float:(F)F
        //    74: putfield        net/minecraft/entity/EntityLiving.rotationYaw:F
        //    77: aload_0        
        //    78: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.currentSpeed:F
        //    81: aload_0        
        //    82: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.maxSpeed:F
        //    85: fcmpg          
        //    86: ifge            110
        //    89: aload_0        
        //    90: dup            
        //    91: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.currentSpeed:F
        //    94: aload_0        
        //    95: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.maxSpeed:F
        //    98: aload_0        
        //    99: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.currentSpeed:F
        //   102: fsub           
        //   103: ldc             0.01
        //   105: fmul           
        //   106: fadd           
        //   107: putfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.currentSpeed:F
        //   110: aload_0        
        //   111: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.currentSpeed:F
        //   114: aload_0        
        //   115: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.maxSpeed:F
        //   118: fcmpl          
        //   119: ifle            130
        //   122: aload_0        
        //   123: aload_0        
        //   124: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.maxSpeed:F
        //   127: putfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.currentSpeed:F
        //   130: aload_0        
        //   131: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.thisEntity:Lnet/minecraft/entity/EntityLiving;
        //   134: getfield        net/minecraft/entity/EntityLiving.posX:D
        //   137: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   140: istore          4
        //   142: aload_0        
        //   143: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.thisEntity:Lnet/minecraft/entity/EntityLiving;
        //   146: getfield        net/minecraft/entity/EntityLiving.posY:D
        //   149: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   152: istore          5
        //   154: aload_0        
        //   155: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.thisEntity:Lnet/minecraft/entity/EntityLiving;
        //   158: getfield        net/minecraft/entity/EntityLiving.posZ:D
        //   161: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   164: istore          6
        //   166: aload_0        
        //   167: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.currentSpeed:F
        //   170: fstore          7
        //   172: aload_0        
        //   173: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.speedBoosted:Z
        //   176: ifeq            230
        //   179: aload_0        
        //   180: dup            
        //   181: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.speedBoostTime:I
        //   184: dup_x1         
        //   185: iconst_1       
        //   186: iadd           
        //   187: putfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.speedBoostTime:I
        //   190: aload_0        
        //   191: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.maxSpeedBoostTime:I
        //   194: if_icmple       202
        //   197: aload_0        
        //   198: iconst_0       
        //   199: putfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.speedBoosted:Z
        //   202: fload           7
        //   204: fload           7
        //   206: ldc             1.15
        //   208: fmul           
        //   209: aload_0        
        //   210: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.speedBoostTime:I
        //   213: i2f            
        //   214: aload_0        
        //   215: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.maxSpeedBoostTime:I
        //   218: i2f            
        //   219: fdiv           
        //   220: ldc             3.1415927
        //   222: fmul           
        //   223: invokestatic    net/minecraft/util/MathHelper.sin:(F)F
        //   226: fmul           
        //   227: fadd           
        //   228: fstore          7
        //   230: ldc             0.91
        //   232: fstore          8
        //   234: aload_0        
        //   235: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.thisEntity:Lnet/minecraft/entity/EntityLiving;
        //   238: getfield        net/minecraft/entity/EntityLiving.onGround:Z
        //   241: ifeq            294
        //   244: aload_0        
        //   245: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.thisEntity:Lnet/minecraft/entity/EntityLiving;
        //   248: getfield        net/minecraft/entity/EntityLiving.worldObj:Lnet/minecraft/world/World;
        //   251: new             Lnet/minecraft/util/BlockPos;
        //   254: dup            
        //   255: iload           4
        //   257: i2f            
        //   258: invokestatic    net/minecraft/util/MathHelper.floor_float:(F)I
        //   261: iload           5
        //   263: i2f            
        //   264: invokestatic    net/minecraft/util/MathHelper.floor_float:(F)I
        //   267: iconst_1       
        //   268: isub           
        //   269: iload           6
        //   271: i2f            
        //   272: invokestatic    net/minecraft/util/MathHelper.floor_float:(F)I
        //   275: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   278: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   281: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   286: getfield        net/minecraft/block/Block.slipperiness:F
        //   289: ldc             0.91
        //   291: fmul           
        //   292: fstore          8
        //   294: ldc             0.16277136
        //   296: fload           8
        //   298: fload           8
        //   300: fmul           
        //   301: fload           8
        //   303: fmul           
        //   304: fdiv           
        //   305: fstore          9
        //   307: aload_2        
        //   308: getfield        net/minecraft/entity/EntityCreature.rotationYaw:F
        //   311: ldc             3.1415927
        //   313: fmul           
        //   314: ldc             180.0
        //   316: fdiv           
        //   317: invokestatic    net/minecraft/util/MathHelper.sin:(F)F
        //   320: fstore          10
        //   322: aload_2        
        //   323: getfield        net/minecraft/entity/EntityCreature.rotationYaw:F
        //   326: ldc             3.1415927
        //   328: fmul           
        //   329: ldc             180.0
        //   331: fdiv           
        //   332: invokestatic    net/minecraft/util/MathHelper.cos:(F)F
        //   335: fstore          11
        //   337: aload_2        
        //   338: invokevirtual   net/minecraft/entity/EntityCreature.getAIMoveSpeed:()F
        //   341: fload           9
        //   343: fmul           
        //   344: fstore          12
        //   346: fload           7
        //   348: fconst_1       
        //   349: invokestatic    java/lang/Math.max:(FF)F
        //   352: fstore          13
        //   354: fload           12
        //   356: fload           13
        //   358: fdiv           
        //   359: fstore          13
        //   361: fload           7
        //   363: fload           13
        //   365: fmul           
        //   366: fstore          14
        //   368: fload           14
        //   370: fload           10
        //   372: fmul           
        //   373: fneg           
        //   374: fstore          15
        //   376: fload           14
        //   378: fload           11
        //   380: fmul           
        //   381: fstore          16
        //   383: fload           15
        //   385: invokestatic    net/minecraft/util/MathHelper.abs:(F)F
        //   388: fload           16
        //   390: invokestatic    net/minecraft/util/MathHelper.abs:(F)F
        //   393: fcmpl          
        //   394: ifle            445
        //   397: fload           15
        //   399: fconst_0       
        //   400: fcmpg          
        //   401: ifge            418
        //   404: fload           15
        //   406: aload_0        
        //   407: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.thisEntity:Lnet/minecraft/entity/EntityLiving;
        //   410: getfield        net/minecraft/entity/EntityLiving.width:F
        //   413: fconst_2       
        //   414: fdiv           
        //   415: fsub           
        //   416: fstore          15
        //   418: fload           15
        //   420: fconst_0       
        //   421: fcmpl          
        //   422: ifle            439
        //   425: fload           15
        //   427: aload_0        
        //   428: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.thisEntity:Lnet/minecraft/entity/EntityLiving;
        //   431: getfield        net/minecraft/entity/EntityLiving.width:F
        //   434: fconst_2       
        //   435: fdiv           
        //   436: fadd           
        //   437: fstore          15
        //   439: fconst_0       
        //   440: fstore          16
        //   442: goto            490
        //   445: fconst_0       
        //   446: fstore          15
        //   448: fload           16
        //   450: fconst_0       
        //   451: fcmpg          
        //   452: ifge            469
        //   455: fload           16
        //   457: aload_0        
        //   458: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.thisEntity:Lnet/minecraft/entity/EntityLiving;
        //   461: getfield        net/minecraft/entity/EntityLiving.width:F
        //   464: fconst_2       
        //   465: fdiv           
        //   466: fsub           
        //   467: fstore          16
        //   469: fload           16
        //   471: fconst_0       
        //   472: fcmpl          
        //   473: ifle            490
        //   476: fload           16
        //   478: aload_0        
        //   479: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.thisEntity:Lnet/minecraft/entity/EntityLiving;
        //   482: getfield        net/minecraft/entity/EntityLiving.width:F
        //   485: fconst_2       
        //   486: fdiv           
        //   487: fadd           
        //   488: fstore          16
        //   490: aload_0        
        //   491: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.thisEntity:Lnet/minecraft/entity/EntityLiving;
        //   494: getfield        net/minecraft/entity/EntityLiving.posX:D
        //   497: fload           15
        //   499: f2d            
        //   500: dadd           
        //   501: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   504: istore          17
        //   506: aload_0        
        //   507: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.thisEntity:Lnet/minecraft/entity/EntityLiving;
        //   510: getfield        net/minecraft/entity/EntityLiving.posZ:D
        //   513: fload           16
        //   515: f2d            
        //   516: dadd           
        //   517: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   520: istore          18
        //   522: aload_0        
        //   523: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.thisEntity:Lnet/minecraft/entity/EntityLiving;
        //   526: getfield        net/minecraft/entity/EntityLiving.width:F
        //   529: fconst_1       
        //   530: fadd           
        //   531: invokestatic    net/minecraft/util/MathHelper.floor_float:(F)I
        //   534: istore          19
        //   536: aload_0        
        //   537: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.thisEntity:Lnet/minecraft/entity/EntityLiving;
        //   540: getfield        net/minecraft/entity/EntityLiving.height:F
        //   543: aload_1        
        //   544: getfield        net/minecraft/entity/player/EntityPlayer.height:F
        //   547: fadd           
        //   548: fconst_1       
        //   549: fadd           
        //   550: invokestatic    net/minecraft/util/MathHelper.floor_float:(F)I
        //   553: istore          20
        //   555: aload_0        
        //   556: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.thisEntity:Lnet/minecraft/entity/EntityLiving;
        //   559: getfield        net/minecraft/entity/EntityLiving.width:F
        //   562: fconst_1       
        //   563: fadd           
        //   564: invokestatic    net/minecraft/util/MathHelper.floor_float:(F)I
        //   567: istore          21
        //   569: iload           4
        //   571: iload           17
        //   573: if_icmpne       583
        //   576: iload           6
        //   578: iload           18
        //   580: if_icmpeq       786
        //   583: aload_0        
        //   584: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.thisEntity:Lnet/minecraft/entity/EntityLiving;
        //   587: getfield        net/minecraft/entity/EntityLiving.worldObj:Lnet/minecraft/world/World;
        //   590: new             Lnet/minecraft/util/BlockPos;
        //   593: dup            
        //   594: iload           4
        //   596: iload           5
        //   598: iload           6
        //   600: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   603: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   606: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   611: astore          22
        //   613: aload_0        
        //   614: aload           22
        //   616: ifne            668
        //   619: aload           22
        //   621: invokevirtual   net/minecraft/block/Block.getMaterial:()Lnet/minecraft/block/material/Material;
        //   624: getstatic       net/minecraft/block/material/Material.air:Lnet/minecraft/block/material/Material;
        //   627: if_acmpne       664
        //   630: aload_0        
        //   631: aload_0        
        //   632: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.thisEntity:Lnet/minecraft/entity/EntityLiving;
        //   635: getfield        net/minecraft/entity/EntityLiving.worldObj:Lnet/minecraft/world/World;
        //   638: new             Lnet/minecraft/util/BlockPos;
        //   641: dup            
        //   642: iload           4
        //   644: iload           5
        //   646: iconst_1       
        //   647: isub           
        //   648: iload           6
        //   650: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   653: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   656: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   661: ifne            668
        //   664: iconst_1       
        //   665: goto            669
        //   668: iconst_0       
        //   669: istore          23
        //   671: iload           23
        //   673: ifeq            786
        //   676: iconst_0       
        //   677: aload_0        
        //   678: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.thisEntity:Lnet/minecraft/entity/EntityLiving;
        //   681: getfield        net/minecraft/entity/EntityLiving.worldObj:Lnet/minecraft/world/World;
        //   684: aload_0        
        //   685: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.thisEntity:Lnet/minecraft/entity/EntityLiving;
        //   688: iload           17
        //   690: iload           5
        //   692: iload           18
        //   694: iload           19
        //   696: iload           20
        //   698: iload           21
        //   700: iconst_0       
        //   701: iconst_0       
        //   702: iconst_1       
        //   703: invokestatic    net/minecraft/world/pathfinder/WalkNodeProcessor.func_176170_a:(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/entity/Entity;IIIIIIZZZ)I
        //   706: if_icmpne       786
        //   709: iconst_1       
        //   710: aload_0        
        //   711: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.thisEntity:Lnet/minecraft/entity/EntityLiving;
        //   714: getfield        net/minecraft/entity/EntityLiving.worldObj:Lnet/minecraft/world/World;
        //   717: aload_0        
        //   718: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.thisEntity:Lnet/minecraft/entity/EntityLiving;
        //   721: iload           4
        //   723: iload           5
        //   725: iconst_1       
        //   726: iadd           
        //   727: iload           6
        //   729: iload           19
        //   731: iload           20
        //   733: iload           21
        //   735: iconst_0       
        //   736: iconst_0       
        //   737: iconst_1       
        //   738: invokestatic    net/minecraft/world/pathfinder/WalkNodeProcessor.func_176170_a:(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/entity/Entity;IIIIIIZZZ)I
        //   741: if_icmpne       786
        //   744: iconst_1       
        //   745: aload_0        
        //   746: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.thisEntity:Lnet/minecraft/entity/EntityLiving;
        //   749: getfield        net/minecraft/entity/EntityLiving.worldObj:Lnet/minecraft/world/World;
        //   752: aload_0        
        //   753: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.thisEntity:Lnet/minecraft/entity/EntityLiving;
        //   756: iload           17
        //   758: iload           5
        //   760: iconst_1       
        //   761: iadd           
        //   762: iload           18
        //   764: iload           19
        //   766: iload           20
        //   768: iload           21
        //   770: iconst_0       
        //   771: iconst_0       
        //   772: iconst_1       
        //   773: invokestatic    net/minecraft/world/pathfinder/WalkNodeProcessor.func_176170_a:(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/entity/Entity;IIIIIIZZZ)I
        //   776: if_icmpne       786
        //   779: aload_2        
        //   780: invokevirtual   net/minecraft/entity/EntityCreature.getJumpHelper:()Lnet/minecraft/entity/ai/EntityJumpHelper;
        //   783: invokevirtual   net/minecraft/entity/ai/EntityJumpHelper.setJumping:()V
        //   786: aload_1        
        //   787: getfield        net/minecraft/entity/player/EntityPlayer.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //   790: getfield        net/minecraft/entity/player/PlayerCapabilities.isCreativeMode:Z
        //   793: ifne            910
        //   796: aload_0        
        //   797: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.currentSpeed:F
        //   800: aload_0        
        //   801: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.maxSpeed:F
        //   804: ldc             0.5
        //   806: fmul           
        //   807: fcmpl          
        //   808: iflt            910
        //   811: aload_0        
        //   812: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.thisEntity:Lnet/minecraft/entity/EntityLiving;
        //   815: invokevirtual   net/minecraft/entity/EntityLiving.getRNG:()Ljava/util/Random;
        //   818: invokevirtual   java/util/Random.nextFloat:()F
        //   821: ldc             0.006
        //   823: fcmpg          
        //   824: ifge            910
        //   827: aload_0        
        //   828: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.speedBoosted:Z
        //   831: ifne            910
        //   834: aload_1        
        //   835: invokevirtual   net/minecraft/entity/player/EntityPlayer.getHeldItem:()Lnet/minecraft/item/ItemStack;
        //   838: astore          22
        //   840: aload           22
        //   842: ifnull          910
        //   845: aload           22
        //   847: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   850: getstatic       net/minecraft/init/Items.carrot_on_a_stick:Lnet/minecraft/item/Item;
        //   853: if_acmpne       910
        //   856: aload           22
        //   858: iconst_1       
        //   859: aload_1        
        //   860: invokevirtual   net/minecraft/item/ItemStack.damageItem:(ILnet/minecraft/entity/EntityLivingBase;)V
        //   863: aload           22
        //   865: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   868: ifne            910
        //   871: new             Lnet/minecraft/item/ItemStack;
        //   874: dup            
        //   875: getstatic       net/minecraft/init/Items.fishing_rod:Lnet/minecraft/item/ItemFishingRod;
        //   878: invokespecial   net/minecraft/item/ItemStack.<init>:(Lnet/minecraft/item/Item;)V
        //   881: astore          23
        //   883: aload           23
        //   885: aload           22
        //   887: invokevirtual   net/minecraft/item/ItemStack.getTagCompound:()Lnet/minecraft/nbt/NBTTagCompound;
        //   890: invokevirtual   net/minecraft/item/ItemStack.setTagCompound:(Lnet/minecraft/nbt/NBTTagCompound;)V
        //   893: aload_1        
        //   894: getfield        net/minecraft/entity/player/EntityPlayer.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //   897: getfield        net/minecraft/entity/player/InventoryPlayer.mainInventory:[Lnet/minecraft/item/ItemStack;
        //   900: aload_1        
        //   901: getfield        net/minecraft/entity/player/EntityPlayer.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //   904: getfield        net/minecraft/entity/player/InventoryPlayer.currentItem:I
        //   907: aload           23
        //   909: aastore        
        //   910: aload_0        
        //   911: getfield        net/minecraft/entity/ai/EntityAIControlledByPlayer.thisEntity:Lnet/minecraft/entity/EntityLiving;
        //   914: fconst_0       
        //   915: fload           7
        //   917: invokevirtual   net/minecraft/entity/EntityLiving.moveEntityWithHeading:(FF)V
        //   920: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0664 (coming from #0661).
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
    public void startExecuting() {
        this.currentSpeed = 0.0f;
    }
    
    @Override
    public boolean shouldExecute() {
        return this.thisEntity.isEntityAlive() && this.thisEntity.riddenByEntity != null && this.thisEntity.riddenByEntity instanceof EntityPlayer && (this.speedBoosted || this.thisEntity.canBeSteered());
    }
    
    public boolean isControlledByPlayer() {
        return !this.isSpeedBoosted() && this.currentSpeed > this.maxSpeed * 0.3f;
    }
    
    public void boostSpeed() {
        this.speedBoosted = true;
        this.speedBoostTime = 0;
        this.maxSpeedBoostTime = this.thisEntity.getRNG().nextInt(841) + 140;
    }
    
    public EntityAIControlledByPlayer(final EntityLiving thisEntity, final float maxSpeed) {
        this.thisEntity = thisEntity;
        this.maxSpeed = maxSpeed;
        this.setMutexBits(7);
    }
}
