package net.minecraft.entity.passive;

import net.minecraft.entity.player.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.pathfinding.*;
import com.google.common.base.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.util.*;

public class EntityWolf extends EntityTameable
{
    private float headRotationCourseOld;
    private boolean isWet;
    private float prevTimeWolfIsShaking;
    private float timeWolfIsShaking;
    private boolean isShaking;
    private float headRotationCourse;
    
    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }
    
    @Override
    public float getEyeHeight() {
        return this.height * 0.8f;
    }
    
    @Override
    public boolean canMateWith(final EntityAnimal entityAnimal) {
        if (entityAnimal == this) {
            return false;
        }
        if (!this.isTamed()) {
            return false;
        }
        if (!(entityAnimal instanceof EntityWolf)) {
            return false;
        }
        final EntityWolf entityWolf = (EntityWolf)entityAnimal;
        return entityWolf.isTamed() && !entityWolf.isSitting() && (this.isInLove() && entityWolf.isInLove());
    }
    
    @Override
    public void onUpdate() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokespecial   net/minecraft/entity/passive/EntityTameable.onUpdate:()V
        //     4: aload_0        
        //     5: aload_0        
        //     6: getfield        net/minecraft/entity/passive/EntityWolf.headRotationCourse:F
        //     9: putfield        net/minecraft/entity/passive/EntityWolf.headRotationCourseOld:F
        //    12: aload_0        
        //    13: if_icmpne       37
        //    16: aload_0        
        //    17: dup            
        //    18: getfield        net/minecraft/entity/passive/EntityWolf.headRotationCourse:F
        //    21: fconst_1       
        //    22: aload_0        
        //    23: getfield        net/minecraft/entity/passive/EntityWolf.headRotationCourse:F
        //    26: fsub           
        //    27: ldc             0.4
        //    29: fmul           
        //    30: fadd           
        //    31: putfield        net/minecraft/entity/passive/EntityWolf.headRotationCourse:F
        //    34: goto            55
        //    37: aload_0        
        //    38: dup            
        //    39: getfield        net/minecraft/entity/passive/EntityWolf.headRotationCourse:F
        //    42: fconst_0       
        //    43: aload_0        
        //    44: getfield        net/minecraft/entity/passive/EntityWolf.headRotationCourse:F
        //    47: fsub           
        //    48: ldc             0.4
        //    50: fmul           
        //    51: fadd           
        //    52: putfield        net/minecraft/entity/passive/EntityWolf.headRotationCourse:F
        //    55: aload_0        
        //    56: invokevirtual   net/minecraft/entity/passive/EntityWolf.isWet:()Z
        //    59: ifeq            85
        //    62: aload_0        
        //    63: iconst_1       
        //    64: putfield        net/minecraft/entity/passive/EntityWolf.isWet:Z
        //    67: aload_0        
        //    68: iconst_0       
        //    69: putfield        net/minecraft/entity/passive/EntityWolf.isShaking:Z
        //    72: aload_0        
        //    73: fconst_0       
        //    74: putfield        net/minecraft/entity/passive/EntityWolf.timeWolfIsShaking:F
        //    77: aload_0        
        //    78: fconst_0       
        //    79: putfield        net/minecraft/entity/passive/EntityWolf.prevTimeWolfIsShaking:F
        //    82: goto            329
        //    85: aload_0        
        //    86: getfield        net/minecraft/entity/passive/EntityWolf.isWet:Z
        //    89: ifne            99
        //    92: aload_0        
        //    93: getfield        net/minecraft/entity/passive/EntityWolf.isShaking:Z
        //    96: ifeq            329
        //    99: aload_0        
        //   100: getfield        net/minecraft/entity/passive/EntityWolf.isShaking:Z
        //   103: ifeq            329
        //   106: aload_0        
        //   107: getfield        net/minecraft/entity/passive/EntityWolf.timeWolfIsShaking:F
        //   110: fconst_0       
        //   111: fcmpl          
        //   112: ifne            145
        //   115: aload_0        
        //   116: ldc             "mob.wolf.shake"
        //   118: aload_0        
        //   119: invokevirtual   net/minecraft/entity/passive/EntityWolf.getSoundVolume:()F
        //   122: aload_0        
        //   123: getfield        net/minecraft/entity/passive/EntityWolf.rand:Ljava/util/Random;
        //   126: invokevirtual   java/util/Random.nextFloat:()F
        //   129: aload_0        
        //   130: getfield        net/minecraft/entity/passive/EntityWolf.rand:Ljava/util/Random;
        //   133: invokevirtual   java/util/Random.nextFloat:()F
        //   136: fsub           
        //   137: ldc             0.2
        //   139: fmul           
        //   140: fconst_1       
        //   141: fadd           
        //   142: invokevirtual   net/minecraft/entity/passive/EntityWolf.playSound:(Ljava/lang/String;FF)V
        //   145: aload_0        
        //   146: aload_0        
        //   147: getfield        net/minecraft/entity/passive/EntityWolf.timeWolfIsShaking:F
        //   150: putfield        net/minecraft/entity/passive/EntityWolf.prevTimeWolfIsShaking:F
        //   153: aload_0        
        //   154: dup            
        //   155: getfield        net/minecraft/entity/passive/EntityWolf.timeWolfIsShaking:F
        //   158: ldc             0.05
        //   160: fadd           
        //   161: putfield        net/minecraft/entity/passive/EntityWolf.timeWolfIsShaking:F
        //   164: aload_0        
        //   165: getfield        net/minecraft/entity/passive/EntityWolf.prevTimeWolfIsShaking:F
        //   168: fconst_2       
        //   169: fcmpl          
        //   170: iflt            193
        //   173: aload_0        
        //   174: iconst_0       
        //   175: putfield        net/minecraft/entity/passive/EntityWolf.isWet:Z
        //   178: aload_0        
        //   179: iconst_0       
        //   180: putfield        net/minecraft/entity/passive/EntityWolf.isShaking:Z
        //   183: aload_0        
        //   184: fconst_0       
        //   185: putfield        net/minecraft/entity/passive/EntityWolf.prevTimeWolfIsShaking:F
        //   188: aload_0        
        //   189: fconst_0       
        //   190: putfield        net/minecraft/entity/passive/EntityWolf.timeWolfIsShaking:F
        //   193: aload_0        
        //   194: getfield        net/minecraft/entity/passive/EntityWolf.timeWolfIsShaking:F
        //   197: ldc             0.4
        //   199: fcmpl          
        //   200: ifle            329
        //   203: aload_0        
        //   204: invokevirtual   net/minecraft/entity/passive/EntityWolf.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //   207: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //   210: d2f            
        //   211: fstore_1       
        //   212: aload_0        
        //   213: getfield        net/minecraft/entity/passive/EntityWolf.timeWolfIsShaking:F
        //   216: ldc             0.4
        //   218: fsub           
        //   219: ldc             3.1415927
        //   221: fmul           
        //   222: invokestatic    net/minecraft/util/MathHelper.sin:(F)F
        //   225: ldc             7.0
        //   227: fmul           
        //   228: f2i            
        //   229: istore_2       
        //   230: iconst_0       
        //   231: iload_2        
        //   232: if_icmpge       329
        //   235: aload_0        
        //   236: getfield        net/minecraft/entity/passive/EntityWolf.rand:Ljava/util/Random;
        //   239: invokevirtual   java/util/Random.nextFloat:()F
        //   242: fconst_2       
        //   243: fmul           
        //   244: fconst_1       
        //   245: fsub           
        //   246: aload_0        
        //   247: getfield        net/minecraft/entity/passive/EntityWolf.width:F
        //   250: fmul           
        //   251: ldc             0.5
        //   253: fmul           
        //   254: fstore          4
        //   256: aload_0        
        //   257: getfield        net/minecraft/entity/passive/EntityWolf.rand:Ljava/util/Random;
        //   260: invokevirtual   java/util/Random.nextFloat:()F
        //   263: fconst_2       
        //   264: fmul           
        //   265: fconst_1       
        //   266: fsub           
        //   267: aload_0        
        //   268: getfield        net/minecraft/entity/passive/EntityWolf.width:F
        //   271: fmul           
        //   272: ldc             0.5
        //   274: fmul           
        //   275: fstore          5
        //   277: aload_0        
        //   278: getfield        net/minecraft/entity/passive/EntityWolf.worldObj:Lnet/minecraft/world/World;
        //   281: getstatic       net/minecraft/util/EnumParticleTypes.WATER_SPLASH:Lnet/minecraft/util/EnumParticleTypes;
        //   284: aload_0        
        //   285: getfield        net/minecraft/entity/passive/EntityWolf.posX:D
        //   288: fload           4
        //   290: f2d            
        //   291: dadd           
        //   292: fload_1        
        //   293: ldc             0.8
        //   295: fadd           
        //   296: f2d            
        //   297: aload_0        
        //   298: getfield        net/minecraft/entity/passive/EntityWolf.posZ:D
        //   301: fload           5
        //   303: f2d            
        //   304: dadd           
        //   305: aload_0        
        //   306: getfield        net/minecraft/entity/passive/EntityWolf.motionX:D
        //   309: aload_0        
        //   310: getfield        net/minecraft/entity/passive/EntityWolf.motionY:D
        //   313: aload_0        
        //   314: getfield        net/minecraft/entity/passive/EntityWolf.motionZ:D
        //   317: iconst_0       
        //   318: newarray        I
        //   320: invokevirtual   net/minecraft/world/World.spawnParticle:(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V
        //   323: iinc            3, 1
        //   326: goto            230
        //   329: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public boolean interact(final EntityPlayer p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/entity/player/EntityPlayer.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //     4: invokevirtual   net/minecraft/entity/player/InventoryPlayer.getCurrentItem:()Lnet/minecraft/item/ItemStack;
        //     7: astore_2       
        //     8: aload_0        
        //     9: invokevirtual   net/minecraft/entity/passive/EntityWolf.isTamed:()Z
        //    12: ifeq            259
        //    15: aload_2        
        //    16: ifnull          194
        //    19: aload_2        
        //    20: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //    23: instanceof      Lnet/minecraft/item/ItemFood;
        //    26: ifeq            119
        //    29: aload_2        
        //    30: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //    33: checkcast       Lnet/minecraft/item/ItemFood;
        //    36: astore_3       
        //    37: aload_3        
        //    38: invokevirtual   net/minecraft/item/ItemFood.isWolfsFavoriteMeat:()Z
        //    41: ifeq            116
        //    44: aload_0        
        //    45: getfield        net/minecraft/entity/passive/EntityWolf.dataWatcher:Lnet/minecraft/entity/DataWatcher;
        //    48: bipush          18
        //    50: invokevirtual   net/minecraft/entity/DataWatcher.getWatchableObjectFloat:(I)F
        //    53: ldc             20.0
        //    55: fcmpg          
        //    56: ifge            116
        //    59: aload_1        
        //    60: getfield        net/minecraft/entity/player/EntityPlayer.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //    63: getfield        net/minecraft/entity/player/PlayerCapabilities.isCreativeMode:Z
        //    66: ifne            79
        //    69: aload_2        
        //    70: dup            
        //    71: getfield        net/minecraft/item/ItemStack.stackSize:I
        //    74: iconst_1       
        //    75: isub           
        //    76: putfield        net/minecraft/item/ItemStack.stackSize:I
        //    79: aload_0        
        //    80: aload_3        
        //    81: aload_2        
        //    82: invokevirtual   net/minecraft/item/ItemFood.getHealAmount:(Lnet/minecraft/item/ItemStack;)I
        //    85: i2f            
        //    86: invokevirtual   net/minecraft/entity/passive/EntityWolf.heal:(F)V
        //    89: aload_2        
        //    90: getfield        net/minecraft/item/ItemStack.stackSize:I
        //    93: ifgt            114
        //    96: aload_1        
        //    97: getfield        net/minecraft/entity/player/EntityPlayer.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //   100: aload_1        
        //   101: getfield        net/minecraft/entity/player/EntityPlayer.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //   104: getfield        net/minecraft/entity/player/InventoryPlayer.currentItem:I
        //   107: aconst_null    
        //   108: checkcast       Lnet/minecraft/item/ItemStack;
        //   111: invokevirtual   net/minecraft/entity/player/InventoryPlayer.setInventorySlotContents:(ILnet/minecraft/item/ItemStack;)V
        //   114: iconst_1       
        //   115: ireturn        
        //   116: goto            194
        //   119: aload_2        
        //   120: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   123: getstatic       net/minecraft/init/Items.dye:Lnet/minecraft/item/Item;
        //   126: if_acmpne       194
        //   129: aload_2        
        //   130: invokevirtual   net/minecraft/item/ItemStack.getMetadata:()I
        //   133: invokestatic    net/minecraft/item/EnumDyeColor.byDyeDamage:(I)Lnet/minecraft/item/EnumDyeColor;
        //   136: astore_3       
        //   137: aload_3        
        //   138: aload_0        
        //   139: invokevirtual   net/minecraft/entity/passive/EntityWolf.getCollarColor:()Lnet/minecraft/item/EnumDyeColor;
        //   142: if_acmpeq       194
        //   145: aload_0        
        //   146: aload_3        
        //   147: invokevirtual   net/minecraft/entity/passive/EntityWolf.setCollarColor:(Lnet/minecraft/item/EnumDyeColor;)V
        //   150: aload_1        
        //   151: getfield        net/minecraft/entity/player/EntityPlayer.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //   154: getfield        net/minecraft/entity/player/PlayerCapabilities.isCreativeMode:Z
        //   157: ifne            192
        //   160: aload_2        
        //   161: dup            
        //   162: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   165: iconst_1       
        //   166: isub           
        //   167: dup_x1         
        //   168: putfield        net/minecraft/item/ItemStack.stackSize:I
        //   171: ifgt            192
        //   174: aload_1        
        //   175: getfield        net/minecraft/entity/player/EntityPlayer.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //   178: aload_1        
        //   179: getfield        net/minecraft/entity/player/EntityPlayer.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //   182: getfield        net/minecraft/entity/player/InventoryPlayer.currentItem:I
        //   185: aconst_null    
        //   186: checkcast       Lnet/minecraft/item/ItemStack;
        //   189: invokevirtual   net/minecraft/entity/player/InventoryPlayer.setInventorySlotContents:(ILnet/minecraft/item/ItemStack;)V
        //   192: iconst_1       
        //   193: ireturn        
        //   194: aload_0        
        //   195: aload_1        
        //   196: invokevirtual   net/minecraft/entity/passive/EntityWolf.isOwner:(Lnet/minecraft/entity/EntityLivingBase;)Z
        //   199: ifeq            423
        //   202: aload_0        
        //   203: getfield        net/minecraft/entity/passive/EntityWolf.worldObj:Lnet/minecraft/world/World;
        //   206: getfield        net/minecraft/world/World.isRemote:Z
        //   209: ifne            423
        //   212: aload_0        
        //   213: aload_2        
        //   214: ifnonnull       423
        //   217: aload_0        
        //   218: getfield        net/minecraft/entity/passive/EntityWolf.aiSit:Lnet/minecraft/entity/ai/EntityAISit;
        //   221: aload_0        
        //   222: invokevirtual   net/minecraft/entity/passive/EntityWolf.isSitting:()Z
        //   225: ifne            232
        //   228: iconst_1       
        //   229: goto            233
        //   232: iconst_0       
        //   233: invokevirtual   net/minecraft/entity/ai/EntityAISit.setSitting:(Z)V
        //   236: aload_0        
        //   237: iconst_0       
        //   238: putfield        net/minecraft/entity/passive/EntityWolf.isJumping:Z
        //   241: aload_0        
        //   242: getfield        net/minecraft/entity/passive/EntityWolf.navigator:Lnet/minecraft/pathfinding/PathNavigate;
        //   245: invokevirtual   net/minecraft/pathfinding/PathNavigate.clearPathEntity:()V
        //   248: aload_0        
        //   249: aconst_null    
        //   250: checkcast       Lnet/minecraft/entity/EntityLivingBase;
        //   253: invokevirtual   net/minecraft/entity/passive/EntityWolf.setAttackTarget:(Lnet/minecraft/entity/EntityLivingBase;)V
        //   256: goto            423
        //   259: aload_2        
        //   260: ifnull          423
        //   263: aload_2        
        //   264: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   267: getstatic       net/minecraft/init/Items.bone:Lnet/minecraft/item/Item;
        //   270: if_acmpne       423
        //   273: aload_0        
        //   274: ifeq            423
        //   277: aload_1        
        //   278: getfield        net/minecraft/entity/player/EntityPlayer.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //   281: getfield        net/minecraft/entity/player/PlayerCapabilities.isCreativeMode:Z
        //   284: ifne            297
        //   287: aload_2        
        //   288: dup            
        //   289: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   292: iconst_1       
        //   293: isub           
        //   294: putfield        net/minecraft/item/ItemStack.stackSize:I
        //   297: aload_2        
        //   298: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   301: ifgt            322
        //   304: aload_1        
        //   305: getfield        net/minecraft/entity/player/EntityPlayer.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //   308: aload_1        
        //   309: getfield        net/minecraft/entity/player/EntityPlayer.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //   312: getfield        net/minecraft/entity/player/InventoryPlayer.currentItem:I
        //   315: aconst_null    
        //   316: checkcast       Lnet/minecraft/item/ItemStack;
        //   319: invokevirtual   net/minecraft/entity/player/InventoryPlayer.setInventorySlotContents:(ILnet/minecraft/item/ItemStack;)V
        //   322: aload_0        
        //   323: getfield        net/minecraft/entity/passive/EntityWolf.worldObj:Lnet/minecraft/world/World;
        //   326: getfield        net/minecraft/world/World.isRemote:Z
        //   329: ifne            421
        //   332: aload_0        
        //   333: getfield        net/minecraft/entity/passive/EntityWolf.rand:Ljava/util/Random;
        //   336: iconst_3       
        //   337: invokevirtual   java/util/Random.nextInt:(I)I
        //   340: ifne            406
        //   343: aload_0        
        //   344: iconst_1       
        //   345: invokevirtual   net/minecraft/entity/passive/EntityWolf.setTamed:(Z)V
        //   348: aload_0        
        //   349: getfield        net/minecraft/entity/passive/EntityWolf.navigator:Lnet/minecraft/pathfinding/PathNavigate;
        //   352: invokevirtual   net/minecraft/pathfinding/PathNavigate.clearPathEntity:()V
        //   355: aload_0        
        //   356: aconst_null    
        //   357: checkcast       Lnet/minecraft/entity/EntityLivingBase;
        //   360: invokevirtual   net/minecraft/entity/passive/EntityWolf.setAttackTarget:(Lnet/minecraft/entity/EntityLivingBase;)V
        //   363: aload_0        
        //   364: getfield        net/minecraft/entity/passive/EntityWolf.aiSit:Lnet/minecraft/entity/ai/EntityAISit;
        //   367: iconst_1       
        //   368: invokevirtual   net/minecraft/entity/ai/EntityAISit.setSitting:(Z)V
        //   371: aload_0        
        //   372: ldc             20.0
        //   374: invokevirtual   net/minecraft/entity/passive/EntityWolf.setHealth:(F)V
        //   377: aload_0        
        //   378: aload_1        
        //   379: invokevirtual   net/minecraft/entity/player/EntityPlayer.getUniqueID:()Ljava/util/UUID;
        //   382: invokevirtual   java/util/UUID.toString:()Ljava/lang/String;
        //   385: invokevirtual   net/minecraft/entity/passive/EntityWolf.setOwnerId:(Ljava/lang/String;)V
        //   388: aload_0        
        //   389: iconst_1       
        //   390: invokevirtual   net/minecraft/entity/passive/EntityWolf.playTameEffect:(Z)V
        //   393: aload_0        
        //   394: getfield        net/minecraft/entity/passive/EntityWolf.worldObj:Lnet/minecraft/world/World;
        //   397: aload_0        
        //   398: bipush          7
        //   400: invokevirtual   net/minecraft/world/World.setEntityState:(Lnet/minecraft/entity/Entity;B)V
        //   403: goto            421
        //   406: aload_0        
        //   407: iconst_0       
        //   408: invokevirtual   net/minecraft/entity/passive/EntityWolf.playTameEffect:(Z)V
        //   411: aload_0        
        //   412: getfield        net/minecraft/entity/passive/EntityWolf.worldObj:Lnet/minecraft/world/World;
        //   415: aload_0        
        //   416: bipush          6
        //   418: invokevirtual   net/minecraft/world/World.setEntityState:(Lnet/minecraft/entity/Entity;B)V
        //   421: iconst_1       
        //   422: ireturn        
        //   423: aload_0        
        //   424: aload_1        
        //   425: invokespecial   net/minecraft/entity/passive/EntityTameable.interact:(Lnet/minecraft/entity/player/EntityPlayer;)Z
        //   428: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0423 (coming from #0214).
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
    protected Item getDropItem() {
        return Item.getItemById(-1);
    }
    
    @Override
    protected void playStepSound(final BlockPos blockPos, final Block block) {
        this.playSound("mob.wolf.step", 0.15f, 1.0f);
    }
    
    public float getInterestedAngle(final float n) {
        return (this.headRotationCourseOld + (this.headRotationCourse - this.headRotationCourseOld) * n) * 0.15f * 3.1415927f;
    }
    
    public void setAngry(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(16);
        if (b) {
            this.dataWatcher.updateObject(16, (byte)(watchableObjectByte | 0x2));
        }
        else {
            this.dataWatcher.updateObject(16, (byte)(watchableObjectByte & 0xFFFFFFFD));
        }
    }
    
    public EntityWolf(final World world) {
        super(world);
        this.setSize(0.6f, 0.8f);
        ((PathNavigateGround)this.getNavigator()).setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4f));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0, true));
        this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0, 10.0f, 2.0f));
        this.tasks.addTask(6, new EntityAIMate(this, 1.0));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0));
        this.tasks.addTask(8, new EntityAIBeg(this, 8.0f));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(4, new EntityAITargetNonTamed(this, EntityAnimal.class, false, (Predicate)new Predicate(this) {
            final EntityWolf this$0;
            
            public boolean apply(final Object o) {
                return this.apply((Entity)o);
            }
            
            public boolean apply(final Entity entity) {
                return entity instanceof EntitySheep || entity instanceof EntityRabbit;
            }
        }));
        this.targetTasks.addTask(5, new EntityAINearestAttackableTarget(this, EntitySkeleton.class, false));
        this.setTamed(false);
    }
    
    @Override
    public void setTamed(final boolean tamed) {
        super.setTamed(tamed);
        if (tamed) {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
        }
        else {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
        }
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0);
    }
    
    @Override
    public boolean shouldAttackEntity(final EntityLivingBase entityLivingBase, final EntityLivingBase entityLivingBase2) {
        if (!(entityLivingBase instanceof EntityCreeper) && !(entityLivingBase instanceof EntityGhast)) {
            if (entityLivingBase instanceof EntityWolf) {
                final EntityWolf entityWolf = (EntityWolf)entityLivingBase;
                if (entityWolf.isTamed() && entityWolf.getOwner() == entityLivingBase2) {
                    return false;
                }
            }
            return (!(entityLivingBase instanceof EntityPlayer) || !(entityLivingBase2 instanceof EntityPlayer) || ((EntityPlayer)entityLivingBase2).canAttackPlayer((EntityPlayer)entityLivingBase)) && (!(entityLivingBase instanceof EntityHorse) || !((EntityHorse)entityLivingBase).isTame());
        }
        return false;
    }
    
    @Override
    protected boolean canDespawn() {
        return !this.isTamed() && this.ticksExisted > 2400;
    }
    
    public float getTailRotation() {
        return (this != 0) ? 1.5393804f : (this.isTamed() ? ((0.55f - (20.0f - this.dataWatcher.getWatchableObjectFloat(18)) * 0.02f) * 3.1415927f) : 0.62831855f);
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable entityAgeable) {
        return this.createChild(entityAgeable);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean("Angry", this.isAngry());
        nbtTagCompound.setByte("CollarColor", (byte)this.getCollarColor().getDyeDamage());
    }
    
    @Override
    public EntityWolf createChild(final EntityAgeable entityAgeable) {
        final EntityWolf entityWolf = new EntityWolf(this.worldObj);
        final String ownerId = this.getOwnerId();
        if (ownerId != null && ownerId.trim().length() > 0) {
            entityWolf.setOwnerId(ownerId);
            entityWolf.setTamed(true);
        }
        return entityWolf;
    }
    
    @Override
    public void handleStatusUpdate(final byte b) {
        if (b == 8) {
            this.isShaking = true;
            this.timeWolfIsShaking = 0.0f;
            this.prevTimeWolfIsShaking = 0.0f;
        }
        else {
            super.handleStatusUpdate(b);
        }
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(18, new Float(this.getHealth()));
        this.dataWatcher.addObject(19, new Byte((byte)0));
        this.dataWatcher.addObject(20, new Byte((byte)EnumDyeColor.RED.getMetadata()));
    }
    
    @Override
    public void setAttackTarget(final EntityLivingBase attackTarget) {
        super.setAttackTarget(attackTarget);
        if (attackTarget == null) {
            this.setAngry(false);
        }
        else if (!this.isTamed()) {
            this.setAngry(true);
        }
    }
    
    @Override
    public int getMaxSpawnedInChunk() {
        return 8;
    }
    
    public boolean allowLeashing() {
        return this != 0 && super.allowLeashing();
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }
        final Entity entity = damageSource.getEntity();
        this.aiSit.setSitting(false);
        if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow)) {
            n = (n + 1.0f) / 2.0f;
        }
        return super.attackEntityFrom(damageSource, n);
    }
    
    @Override
    protected String getLivingSound() {
        return (this != 0) ? "mob.wolf.growl" : ((this.rand.nextInt(3) == 0) ? ((this.isTamed() && this.dataWatcher.getWatchableObjectFloat(18) < 10.0f) ? "mob.wolf.whine" : "mob.wolf.panting") : "mob.wolf.bark");
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896);
        if (this.isTamed()) {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
        }
        else {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
        }
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0);
    }
    
    @Override
    protected void updateAITasks() {
        this.dataWatcher.updateObject(18, this.getHealth());
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.wolf.hurt";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.wolf.death";
    }
    
    public float getShakeAngle(final float n, final float n2) {
        float n3 = (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * n + n2) / 1.8f;
        if (n3 < 0.0f) {
            n3 = 0.0f;
        }
        else if (n3 > 1.0f) {
            n3 = 1.0f;
        }
        return MathHelper.sin(n3 * 3.1415927f) * MathHelper.sin(n3 * 3.1415927f * 11.0f) * 0.15f * 3.1415927f;
    }
    
    public EnumDyeColor getCollarColor() {
        return EnumDyeColor.byDyeDamage(this.dataWatcher.getWatchableObjectByte(20) & 0xF);
    }
    
    public void setBegging(final boolean b) {
        if (b) {
            this.dataWatcher.updateObject(19, 1);
        }
        else {
            this.dataWatcher.updateObject(19, 0);
        }
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        final boolean attackEntity = entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float)(int)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
        if (attackEntity) {
            this.applyEnchantments(this, entity);
        }
        return attackEntity;
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.worldObj.isRemote && this.isWet && !this.isShaking && !this.hasPath() && this.onGround) {
            this.isShaking = true;
            this.timeWolfIsShaking = 0.0f;
            this.prevTimeWolfIsShaking = 0.0f;
            this.worldObj.setEntityState(this, (byte)8);
        }
        if (!this.worldObj.isRemote && this.getAttackTarget() == null && this != 0) {
            this.setAngry(false);
        }
    }
    
    public void setCollarColor(final EnumDyeColor enumDyeColor) {
        this.dataWatcher.updateObject(20, (byte)(enumDyeColor.getDyeDamage() & 0xF));
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.setAngry(nbtTagCompound.getBoolean("Angry"));
        if (nbtTagCompound.hasKey("CollarColor", 99)) {
            this.setCollarColor(EnumDyeColor.byDyeDamage(nbtTagCompound.getByte("CollarColor")));
        }
    }
    
    public float getShadingWhileWet(final float n) {
        return 0.75f + (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * n) / 2.0f * 0.25f;
    }
    
    public boolean isWolfWet() {
        return this.isWet;
    }
    
    @Override
    public int getVerticalFaceSpeed() {
        return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
    }
}
