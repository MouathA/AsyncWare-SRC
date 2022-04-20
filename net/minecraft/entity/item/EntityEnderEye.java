package net.minecraft.entity.item;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;

public class EntityEnderEye extends Entity
{
    private double targetY;
    private double targetZ;
    private boolean shatterOrDrop;
    private int despawnTimer;
    private double targetX;
    
    public EntityEnderEye(final World world) {
        super(world);
        this.setSize(0.25f, 0.25f);
    }
    
    @Override
    public boolean isInRangeToRenderDist(final double n) {
        double n2 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0;
        if (Double.isNaN(n2)) {
            n2 = 4.0;
        }
        final double n3 = n2 * 64.0;
        return n < n3 * n3;
    }
    
    @Override
    public void setVelocity(final double motionX, final double motionY, final double motionZ) {
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        if (this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f) {
            final float sqrt_double = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
            final float n = (float)(MathHelper.func_181159_b(motionX, motionZ) * 180.0 / 3.141592653589793);
            this.rotationYaw = n;
            this.prevRotationYaw = n;
            final float n2 = (float)(MathHelper.func_181159_b(motionY, sqrt_double) * 180.0 / 3.141592653589793);
            this.rotationPitch = n2;
            this.prevRotationPitch = n2;
        }
    }
    
    @Override
    public void onUpdate() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_0        
        //     2: getfield        net/minecraft/entity/item/EntityEnderEye.posX:D
        //     5: putfield        net/minecraft/entity/item/EntityEnderEye.lastTickPosX:D
        //     8: aload_0        
        //     9: aload_0        
        //    10: getfield        net/minecraft/entity/item/EntityEnderEye.posY:D
        //    13: putfield        net/minecraft/entity/item/EntityEnderEye.lastTickPosY:D
        //    16: aload_0        
        //    17: aload_0        
        //    18: getfield        net/minecraft/entity/item/EntityEnderEye.posZ:D
        //    21: putfield        net/minecraft/entity/item/EntityEnderEye.lastTickPosZ:D
        //    24: aload_0        
        //    25: invokespecial   net/minecraft/entity/Entity.onUpdate:()V
        //    28: aload_0        
        //    29: dup            
        //    30: getfield        net/minecraft/entity/item/EntityEnderEye.posX:D
        //    33: aload_0        
        //    34: getfield        net/minecraft/entity/item/EntityEnderEye.motionX:D
        //    37: dadd           
        //    38: putfield        net/minecraft/entity/item/EntityEnderEye.posX:D
        //    41: aload_0        
        //    42: dup            
        //    43: getfield        net/minecraft/entity/item/EntityEnderEye.posY:D
        //    46: aload_0        
        //    47: getfield        net/minecraft/entity/item/EntityEnderEye.motionY:D
        //    50: dadd           
        //    51: putfield        net/minecraft/entity/item/EntityEnderEye.posY:D
        //    54: aload_0        
        //    55: dup            
        //    56: getfield        net/minecraft/entity/item/EntityEnderEye.posZ:D
        //    59: aload_0        
        //    60: getfield        net/minecraft/entity/item/EntityEnderEye.motionZ:D
        //    63: dadd           
        //    64: putfield        net/minecraft/entity/item/EntityEnderEye.posZ:D
        //    67: aload_0        
        //    68: getfield        net/minecraft/entity/item/EntityEnderEye.motionX:D
        //    71: aload_0        
        //    72: getfield        net/minecraft/entity/item/EntityEnderEye.motionX:D
        //    75: dmul           
        //    76: aload_0        
        //    77: getfield        net/minecraft/entity/item/EntityEnderEye.motionZ:D
        //    80: aload_0        
        //    81: getfield        net/minecraft/entity/item/EntityEnderEye.motionZ:D
        //    84: dmul           
        //    85: dadd           
        //    86: invokestatic    net/minecraft/util/MathHelper.sqrt_double:(D)F
        //    89: fstore_1       
        //    90: aload_0        
        //    91: aload_0        
        //    92: getfield        net/minecraft/entity/item/EntityEnderEye.motionX:D
        //    95: aload_0        
        //    96: getfield        net/minecraft/entity/item/EntityEnderEye.motionZ:D
        //    99: invokestatic    net/minecraft/util/MathHelper.func_181159_b:(DD)D
        //   102: ldc2_w          180.0
        //   105: dmul           
        //   106: ldc2_w          3.141592653589793
        //   109: ddiv           
        //   110: d2f            
        //   111: putfield        net/minecraft/entity/item/EntityEnderEye.rotationYaw:F
        //   114: aload_0        
        //   115: aload_0        
        //   116: getfield        net/minecraft/entity/item/EntityEnderEye.motionY:D
        //   119: fload_1        
        //   120: f2d            
        //   121: invokestatic    net/minecraft/util/MathHelper.func_181159_b:(DD)D
        //   124: ldc2_w          180.0
        //   127: dmul           
        //   128: ldc2_w          3.141592653589793
        //   131: ddiv           
        //   132: d2f            
        //   133: putfield        net/minecraft/entity/item/EntityEnderEye.rotationPitch:F
        //   136: aload_0        
        //   137: getfield        net/minecraft/entity/item/EntityEnderEye.rotationPitch:F
        //   140: aload_0        
        //   141: getfield        net/minecraft/entity/item/EntityEnderEye.prevRotationPitch:F
        //   144: fsub           
        //   145: ldc             -180.0
        //   147: fcmpg          
        //   148: ifge            165
        //   151: aload_0        
        //   152: dup            
        //   153: getfield        net/minecraft/entity/item/EntityEnderEye.prevRotationPitch:F
        //   156: ldc             360.0
        //   158: fsub           
        //   159: putfield        net/minecraft/entity/item/EntityEnderEye.prevRotationPitch:F
        //   162: goto            136
        //   165: aload_0        
        //   166: getfield        net/minecraft/entity/item/EntityEnderEye.rotationPitch:F
        //   169: aload_0        
        //   170: getfield        net/minecraft/entity/item/EntityEnderEye.prevRotationPitch:F
        //   173: fsub           
        //   174: ldc             180.0
        //   176: fcmpl          
        //   177: iflt            194
        //   180: aload_0        
        //   181: dup            
        //   182: getfield        net/minecraft/entity/item/EntityEnderEye.prevRotationPitch:F
        //   185: ldc             360.0
        //   187: fadd           
        //   188: putfield        net/minecraft/entity/item/EntityEnderEye.prevRotationPitch:F
        //   191: goto            165
        //   194: aload_0        
        //   195: getfield        net/minecraft/entity/item/EntityEnderEye.rotationYaw:F
        //   198: aload_0        
        //   199: getfield        net/minecraft/entity/item/EntityEnderEye.prevRotationYaw:F
        //   202: fsub           
        //   203: ldc             -180.0
        //   205: fcmpg          
        //   206: ifge            223
        //   209: aload_0        
        //   210: dup            
        //   211: getfield        net/minecraft/entity/item/EntityEnderEye.prevRotationYaw:F
        //   214: ldc             360.0
        //   216: fsub           
        //   217: putfield        net/minecraft/entity/item/EntityEnderEye.prevRotationYaw:F
        //   220: goto            194
        //   223: aload_0        
        //   224: getfield        net/minecraft/entity/item/EntityEnderEye.rotationYaw:F
        //   227: aload_0        
        //   228: getfield        net/minecraft/entity/item/EntityEnderEye.prevRotationYaw:F
        //   231: fsub           
        //   232: ldc             180.0
        //   234: fcmpl          
        //   235: iflt            252
        //   238: aload_0        
        //   239: dup            
        //   240: getfield        net/minecraft/entity/item/EntityEnderEye.prevRotationYaw:F
        //   243: ldc             360.0
        //   245: fadd           
        //   246: putfield        net/minecraft/entity/item/EntityEnderEye.prevRotationYaw:F
        //   249: goto            223
        //   252: aload_0        
        //   253: aload_0        
        //   254: getfield        net/minecraft/entity/item/EntityEnderEye.prevRotationPitch:F
        //   257: aload_0        
        //   258: getfield        net/minecraft/entity/item/EntityEnderEye.rotationPitch:F
        //   261: aload_0        
        //   262: getfield        net/minecraft/entity/item/EntityEnderEye.prevRotationPitch:F
        //   265: fsub           
        //   266: ldc             0.2
        //   268: fmul           
        //   269: fadd           
        //   270: putfield        net/minecraft/entity/item/EntityEnderEye.rotationPitch:F
        //   273: aload_0        
        //   274: aload_0        
        //   275: getfield        net/minecraft/entity/item/EntityEnderEye.prevRotationYaw:F
        //   278: aload_0        
        //   279: getfield        net/minecraft/entity/item/EntityEnderEye.rotationYaw:F
        //   282: aload_0        
        //   283: getfield        net/minecraft/entity/item/EntityEnderEye.prevRotationYaw:F
        //   286: fsub           
        //   287: ldc             0.2
        //   289: fmul           
        //   290: fadd           
        //   291: putfield        net/minecraft/entity/item/EntityEnderEye.rotationYaw:F
        //   294: aload_0        
        //   295: getfield        net/minecraft/entity/item/EntityEnderEye.worldObj:Lnet/minecraft/world/World;
        //   298: getfield        net/minecraft/world/World.isRemote:Z
        //   301: ifne            471
        //   304: aload_0        
        //   305: getfield        net/minecraft/entity/item/EntityEnderEye.targetX:D
        //   308: aload_0        
        //   309: getfield        net/minecraft/entity/item/EntityEnderEye.posX:D
        //   312: dsub           
        //   313: dstore_2       
        //   314: aload_0        
        //   315: getfield        net/minecraft/entity/item/EntityEnderEye.targetZ:D
        //   318: aload_0        
        //   319: getfield        net/minecraft/entity/item/EntityEnderEye.posZ:D
        //   322: dsub           
        //   323: dstore          4
        //   325: dload_2        
        //   326: dload_2        
        //   327: dmul           
        //   328: dload           4
        //   330: dload           4
        //   332: dmul           
        //   333: dadd           
        //   334: invokestatic    java/lang/Math.sqrt:(D)D
        //   337: d2f            
        //   338: fstore          6
        //   340: dload           4
        //   342: dload_2        
        //   343: invokestatic    net/minecraft/util/MathHelper.func_181159_b:(DD)D
        //   346: d2f            
        //   347: fstore          7
        //   349: fload_1        
        //   350: f2d            
        //   351: fload           6
        //   353: fload_1        
        //   354: fsub           
        //   355: f2d            
        //   356: ldc2_w          0.0025
        //   359: dmul           
        //   360: dadd           
        //   361: dstore          8
        //   363: fload           6
        //   365: fconst_1       
        //   366: fcmpg          
        //   367: ifge            390
        //   370: dload           8
        //   372: ldc2_w          0.8
        //   375: dmul           
        //   376: dstore          8
        //   378: aload_0        
        //   379: dup            
        //   380: getfield        net/minecraft/entity/item/EntityEnderEye.motionY:D
        //   383: ldc2_w          0.8
        //   386: dmul           
        //   387: putfield        net/minecraft/entity/item/EntityEnderEye.motionY:D
        //   390: aload_0        
        //   391: fload           7
        //   393: f2d            
        //   394: invokestatic    java/lang/Math.cos:(D)D
        //   397: dload           8
        //   399: dmul           
        //   400: putfield        net/minecraft/entity/item/EntityEnderEye.motionX:D
        //   403: aload_0        
        //   404: fload           7
        //   406: f2d            
        //   407: invokestatic    java/lang/Math.sin:(D)D
        //   410: dload           8
        //   412: dmul           
        //   413: putfield        net/minecraft/entity/item/EntityEnderEye.motionZ:D
        //   416: aload_0        
        //   417: getfield        net/minecraft/entity/item/EntityEnderEye.posY:D
        //   420: aload_0        
        //   421: getfield        net/minecraft/entity/item/EntityEnderEye.targetY:D
        //   424: dcmpg          
        //   425: ifge            450
        //   428: aload_0        
        //   429: dup            
        //   430: getfield        net/minecraft/entity/item/EntityEnderEye.motionY:D
        //   433: dconst_1       
        //   434: aload_0        
        //   435: getfield        net/minecraft/entity/item/EntityEnderEye.motionY:D
        //   438: dsub           
        //   439: ldc2_w          0.014999999664723873
        //   442: dmul           
        //   443: dadd           
        //   444: putfield        net/minecraft/entity/item/EntityEnderEye.motionY:D
        //   447: goto            471
        //   450: aload_0        
        //   451: dup            
        //   452: getfield        net/minecraft/entity/item/EntityEnderEye.motionY:D
        //   455: ldc2_w          -1.0
        //   458: aload_0        
        //   459: getfield        net/minecraft/entity/item/EntityEnderEye.motionY:D
        //   462: dsub           
        //   463: ldc2_w          0.014999999664723873
        //   466: dmul           
        //   467: dadd           
        //   468: putfield        net/minecraft/entity/item/EntityEnderEye.motionY:D
        //   471: ldc             0.25
        //   473: fstore_2       
        //   474: aload_0        
        //   475: invokevirtual   net/minecraft/entity/item/EntityEnderEye.isInWater:()Z
        //   478: ifeq            551
        //   481: aload_0        
        //   482: getfield        net/minecraft/entity/item/EntityEnderEye.worldObj:Lnet/minecraft/world/World;
        //   485: getstatic       net/minecraft/util/EnumParticleTypes.WATER_BUBBLE:Lnet/minecraft/util/EnumParticleTypes;
        //   488: aload_0        
        //   489: getfield        net/minecraft/entity/item/EntityEnderEye.posX:D
        //   492: aload_0        
        //   493: getfield        net/minecraft/entity/item/EntityEnderEye.motionX:D
        //   496: fload_2        
        //   497: f2d            
        //   498: dmul           
        //   499: dsub           
        //   500: aload_0        
        //   501: getfield        net/minecraft/entity/item/EntityEnderEye.posY:D
        //   504: aload_0        
        //   505: getfield        net/minecraft/entity/item/EntityEnderEye.motionY:D
        //   508: fload_2        
        //   509: f2d            
        //   510: dmul           
        //   511: dsub           
        //   512: aload_0        
        //   513: getfield        net/minecraft/entity/item/EntityEnderEye.posZ:D
        //   516: aload_0        
        //   517: getfield        net/minecraft/entity/item/EntityEnderEye.motionZ:D
        //   520: fload_2        
        //   521: f2d            
        //   522: dmul           
        //   523: dsub           
        //   524: aload_0        
        //   525: getfield        net/minecraft/entity/item/EntityEnderEye.motionX:D
        //   528: aload_0        
        //   529: getfield        net/minecraft/entity/item/EntityEnderEye.motionY:D
        //   532: aload_0        
        //   533: getfield        net/minecraft/entity/item/EntityEnderEye.motionZ:D
        //   536: iconst_0       
        //   537: newarray        I
        //   539: invokevirtual   net/minecraft/world/World.spawnParticle:(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V
        //   542: iinc            3, 1
        //   545: goto            481
        //   548: goto            648
        //   551: aload_0        
        //   552: getfield        net/minecraft/entity/item/EntityEnderEye.worldObj:Lnet/minecraft/world/World;
        //   555: getstatic       net/minecraft/util/EnumParticleTypes.PORTAL:Lnet/minecraft/util/EnumParticleTypes;
        //   558: aload_0        
        //   559: getfield        net/minecraft/entity/item/EntityEnderEye.posX:D
        //   562: aload_0        
        //   563: getfield        net/minecraft/entity/item/EntityEnderEye.motionX:D
        //   566: fload_2        
        //   567: f2d            
        //   568: dmul           
        //   569: dsub           
        //   570: aload_0        
        //   571: getfield        net/minecraft/entity/item/EntityEnderEye.rand:Ljava/util/Random;
        //   574: invokevirtual   java/util/Random.nextDouble:()D
        //   577: ldc2_w          0.6
        //   580: dmul           
        //   581: dadd           
        //   582: ldc2_w          0.3
        //   585: dsub           
        //   586: aload_0        
        //   587: getfield        net/minecraft/entity/item/EntityEnderEye.posY:D
        //   590: aload_0        
        //   591: getfield        net/minecraft/entity/item/EntityEnderEye.motionY:D
        //   594: fload_2        
        //   595: f2d            
        //   596: dmul           
        //   597: dsub           
        //   598: ldc2_w          0.5
        //   601: dsub           
        //   602: aload_0        
        //   603: getfield        net/minecraft/entity/item/EntityEnderEye.posZ:D
        //   606: aload_0        
        //   607: getfield        net/minecraft/entity/item/EntityEnderEye.motionZ:D
        //   610: fload_2        
        //   611: f2d            
        //   612: dmul           
        //   613: dsub           
        //   614: aload_0        
        //   615: getfield        net/minecraft/entity/item/EntityEnderEye.rand:Ljava/util/Random;
        //   618: invokevirtual   java/util/Random.nextDouble:()D
        //   621: ldc2_w          0.6
        //   624: dmul           
        //   625: dadd           
        //   626: ldc2_w          0.3
        //   629: dsub           
        //   630: aload_0        
        //   631: getfield        net/minecraft/entity/item/EntityEnderEye.motionX:D
        //   634: aload_0        
        //   635: getfield        net/minecraft/entity/item/EntityEnderEye.motionY:D
        //   638: aload_0        
        //   639: getfield        net/minecraft/entity/item/EntityEnderEye.motionZ:D
        //   642: iconst_0       
        //   643: newarray        I
        //   645: invokevirtual   net/minecraft/world/World.spawnParticle:(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V
        //   648: aload_0        
        //   649: getfield        net/minecraft/entity/item/EntityEnderEye.worldObj:Lnet/minecraft/world/World;
        //   652: getfield        net/minecraft/world/World.isRemote:Z
        //   655: ifne            777
        //   658: aload_0        
        //   659: aload_0        
        //   660: getfield        net/minecraft/entity/item/EntityEnderEye.posX:D
        //   663: aload_0        
        //   664: getfield        net/minecraft/entity/item/EntityEnderEye.posY:D
        //   667: aload_0        
        //   668: getfield        net/minecraft/entity/item/EntityEnderEye.posZ:D
        //   671: invokevirtual   net/minecraft/entity/item/EntityEnderEye.setPosition:(DDD)V
        //   674: aload_0        
        //   675: dup            
        //   676: getfield        net/minecraft/entity/item/EntityEnderEye.despawnTimer:I
        //   679: iconst_1       
        //   680: iadd           
        //   681: putfield        net/minecraft/entity/item/EntityEnderEye.despawnTimer:I
        //   684: aload_0        
        //   685: getfield        net/minecraft/entity/item/EntityEnderEye.despawnTimer:I
        //   688: bipush          80
        //   690: if_icmple       777
        //   693: aload_0        
        //   694: getfield        net/minecraft/entity/item/EntityEnderEye.worldObj:Lnet/minecraft/world/World;
        //   697: getfield        net/minecraft/world/World.isRemote:Z
        //   700: ifne            777
        //   703: aload_0        
        //   704: invokevirtual   net/minecraft/entity/item/EntityEnderEye.setDead:()V
        //   707: aload_0        
        //   708: getfield        net/minecraft/entity/item/EntityEnderEye.shatterOrDrop:Z
        //   711: ifeq            758
        //   714: aload_0        
        //   715: getfield        net/minecraft/entity/item/EntityEnderEye.worldObj:Lnet/minecraft/world/World;
        //   718: new             Lnet/minecraft/entity/item/EntityItem;
        //   721: dup            
        //   722: aload_0        
        //   723: getfield        net/minecraft/entity/item/EntityEnderEye.worldObj:Lnet/minecraft/world/World;
        //   726: aload_0        
        //   727: getfield        net/minecraft/entity/item/EntityEnderEye.posX:D
        //   730: aload_0        
        //   731: getfield        net/minecraft/entity/item/EntityEnderEye.posY:D
        //   734: aload_0        
        //   735: getfield        net/minecraft/entity/item/EntityEnderEye.posZ:D
        //   738: new             Lnet/minecraft/item/ItemStack;
        //   741: dup            
        //   742: getstatic       net/minecraft/init/Items.ender_eye:Lnet/minecraft/item/Item;
        //   745: invokespecial   net/minecraft/item/ItemStack.<init>:(Lnet/minecraft/item/Item;)V
        //   748: invokespecial   net/minecraft/entity/item/EntityItem.<init>:(Lnet/minecraft/world/World;DDDLnet/minecraft/item/ItemStack;)V
        //   751: invokevirtual   net/minecraft/world/World.spawnEntityInWorld:(Lnet/minecraft/entity/Entity;)Z
        //   754: pop            
        //   755: goto            777
        //   758: aload_0        
        //   759: getfield        net/minecraft/entity/item/EntityEnderEye.worldObj:Lnet/minecraft/world/World;
        //   762: sipush          2003
        //   765: new             Lnet/minecraft/util/BlockPos;
        //   768: dup            
        //   769: aload_0        
        //   770: invokespecial   net/minecraft/util/BlockPos.<init>:(Lnet/minecraft/entity/Entity;)V
        //   773: iconst_0       
        //   774: invokevirtual   net/minecraft/world/World.playAuxSFX:(ILnet/minecraft/util/BlockPos;I)V
        //   777: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void moveTowards(final BlockPos blockPos) {
        final double targetX = blockPos.getX();
        final int y = blockPos.getY();
        final double targetZ = blockPos.getZ();
        final double n = targetX - this.posX;
        final double n2 = targetZ - this.posZ;
        final float sqrt_double = MathHelper.sqrt_double(n * n + n2 * n2);
        if (sqrt_double > 12.0f) {
            this.targetX = this.posX + n / sqrt_double * 12.0;
            this.targetZ = this.posZ + n2 / sqrt_double * 12.0;
            this.targetY = this.posY + 8.0;
        }
        else {
            this.targetX = targetX;
            this.targetY = y;
            this.targetZ = targetZ;
        }
        this.despawnTimer = 0;
        this.shatterOrDrop = (this.rand.nextInt(5) > 0);
    }
    
    public EntityEnderEye(final World world, final double n, final double n2, final double n3) {
        super(world);
        this.despawnTimer = 0;
        this.setSize(0.25f, 0.25f);
        this.setPosition(n, n2, n3);
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    @Override
    protected void entityInit() {
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    @Override
    public float getBrightness(final float n) {
        return 1.0f;
    }
    
    @Override
    public int getBrightnessForRender(final float n) {
        return 15728880;
    }
    
    @Override
    public boolean canAttackWithItem() {
        return false;
    }
}
