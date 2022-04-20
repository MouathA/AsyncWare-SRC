package net.minecraft.entity.projectile;

import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;

public abstract class EntityFireball extends Entity
{
    private boolean inGround;
    private int ticksInAir;
    private Block inTile;
    private int xTile;
    public EntityLivingBase shootingEntity;
    public double accelerationY;
    private int ticksAlive;
    public double accelerationZ;
    private int zTile;
    private int yTile;
    public double accelerationX;
    
    protected float getMotionFactor() {
        return 0.95f;
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return true;
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        this.xTile = nbtTagCompound.getShort("xTile");
        this.yTile = nbtTagCompound.getShort("yTile");
        this.zTile = nbtTagCompound.getShort("zTile");
        if (nbtTagCompound.hasKey("inTile", 8)) {
            this.inTile = Block.getBlockFromName(nbtTagCompound.getString("inTile"));
        }
        else {
            this.inTile = Block.getBlockById(nbtTagCompound.getByte("inTile") & 0xFF);
        }
        this.inGround = (nbtTagCompound.getByte("inGround") == 1);
        if (nbtTagCompound.hasKey("direction", 9)) {
            final NBTTagList tagList = nbtTagCompound.getTagList("direction", 6);
            this.motionX = tagList.getDoubleAt(0);
            this.motionY = tagList.getDoubleAt(1);
            this.motionZ = tagList.getDoubleAt(2);
        }
        else {
            this.setDead();
        }
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }
        this.setBeenAttacked();
        if (damageSource.getEntity() != null) {
            final Vec3 lookVec = damageSource.getEntity().getLookVec();
            if (lookVec != null) {
                this.motionX = lookVec.xCoord;
                this.motionY = lookVec.yCoord;
                this.motionZ = lookVec.zCoord;
                this.accelerationX = this.motionX * 0.1;
                this.accelerationY = this.motionY * 0.1;
                this.accelerationZ = this.motionZ * 0.1;
            }
            if (damageSource.getEntity() instanceof EntityLivingBase) {
                this.shootingEntity = (EntityLivingBase)damageSource.getEntity();
            }
            return true;
        }
        return false;
    }
    
    @Override
    protected void entityInit() {
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
    public float getBrightness(final float n) {
        return 1.0f;
    }
    
    public EntityFireball(final World world, final EntityLivingBase shootingEntity, double n, double n2, double n3) {
        super(world);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.shootingEntity = shootingEntity;
        this.setSize(1.0f, 1.0f);
        this.setLocationAndAngles(shootingEntity.posX, shootingEntity.posY, shootingEntity.posZ, shootingEntity.rotationYaw, shootingEntity.rotationPitch);
        this.setPosition(this.posX, this.posY, this.posZ);
        final double motionX = 0.0;
        this.motionZ = motionX;
        this.motionY = motionX;
        this.motionX = motionX;
        n += this.rand.nextGaussian() * 0.4;
        n2 += this.rand.nextGaussian() * 0.4;
        n3 += this.rand.nextGaussian() * 0.4;
        final double n4 = MathHelper.sqrt_double(n * n + n2 * n2 + n3 * n3);
        this.accelerationX = n / n4 * 0.1;
        this.accelerationY = n2 / n4 * 0.1;
        this.accelerationZ = n3 / n4 * 0.1;
    }
    
    @Override
    public void onUpdate() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/entity/projectile/EntityFireball.worldObj:Lnet/minecraft/world/World;
        //     4: getfield        net/minecraft/world/World.isRemote:Z
        //     7: ifne            45
        //    10: aload_0        
        //    11: getfield        net/minecraft/entity/projectile/EntityFireball.shootingEntity:Lnet/minecraft/entity/EntityLivingBase;
        //    14: ifnull          27
        //    17: aload_0        
        //    18: getfield        net/minecraft/entity/projectile/EntityFireball.shootingEntity:Lnet/minecraft/entity/EntityLivingBase;
        //    21: getfield        net/minecraft/entity/EntityLivingBase.isDead:Z
        //    24: ifne            1069
        //    27: aload_0        
        //    28: getfield        net/minecraft/entity/projectile/EntityFireball.worldObj:Lnet/minecraft/world/World;
        //    31: new             Lnet/minecraft/util/BlockPos;
        //    34: dup            
        //    35: aload_0        
        //    36: invokespecial   net/minecraft/util/BlockPos.<init>:(Lnet/minecraft/entity/Entity;)V
        //    39: invokevirtual   net/minecraft/world/World.isBlockLoaded:(Lnet/minecraft/util/BlockPos;)Z
        //    42: ifeq            1069
        //    45: aload_0        
        //    46: invokespecial   net/minecraft/entity/Entity.onUpdate:()V
        //    49: aload_0        
        //    50: iconst_1       
        //    51: invokevirtual   net/minecraft/entity/projectile/EntityFireball.setFire:(I)V
        //    54: aload_0        
        //    55: getfield        net/minecraft/entity/projectile/EntityFireball.inGround:Z
        //    58: ifeq            205
        //    61: aload_0        
        //    62: getfield        net/minecraft/entity/projectile/EntityFireball.worldObj:Lnet/minecraft/world/World;
        //    65: new             Lnet/minecraft/util/BlockPos;
        //    68: dup            
        //    69: aload_0        
        //    70: getfield        net/minecraft/entity/projectile/EntityFireball.xTile:I
        //    73: aload_0        
        //    74: getfield        net/minecraft/entity/projectile/EntityFireball.yTile:I
        //    77: aload_0        
        //    78: getfield        net/minecraft/entity/projectile/EntityFireball.zTile:I
        //    81: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //    84: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //    87: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //    92: aload_0        
        //    93: getfield        net/minecraft/entity/projectile/EntityFireball.inTile:Lnet/minecraft/block/Block;
        //    96: if_acmpne       124
        //    99: aload_0        
        //   100: dup            
        //   101: getfield        net/minecraft/entity/projectile/EntityFireball.ticksAlive:I
        //   104: iconst_1       
        //   105: iadd           
        //   106: putfield        net/minecraft/entity/projectile/EntityFireball.ticksAlive:I
        //   109: aload_0        
        //   110: getfield        net/minecraft/entity/projectile/EntityFireball.ticksAlive:I
        //   113: sipush          600
        //   116: if_icmpne       123
        //   119: aload_0        
        //   120: invokevirtual   net/minecraft/entity/projectile/EntityFireball.setDead:()V
        //   123: return         
        //   124: aload_0        
        //   125: iconst_0       
        //   126: putfield        net/minecraft/entity/projectile/EntityFireball.inGround:Z
        //   129: aload_0        
        //   130: dup            
        //   131: getfield        net/minecraft/entity/projectile/EntityFireball.motionX:D
        //   134: aload_0        
        //   135: getfield        net/minecraft/entity/projectile/EntityFireball.rand:Ljava/util/Random;
        //   138: invokevirtual   java/util/Random.nextFloat:()F
        //   141: ldc_w           0.2
        //   144: fmul           
        //   145: f2d            
        //   146: dmul           
        //   147: putfield        net/minecraft/entity/projectile/EntityFireball.motionX:D
        //   150: aload_0        
        //   151: dup            
        //   152: getfield        net/minecraft/entity/projectile/EntityFireball.motionY:D
        //   155: aload_0        
        //   156: getfield        net/minecraft/entity/projectile/EntityFireball.rand:Ljava/util/Random;
        //   159: invokevirtual   java/util/Random.nextFloat:()F
        //   162: ldc_w           0.2
        //   165: fmul           
        //   166: f2d            
        //   167: dmul           
        //   168: putfield        net/minecraft/entity/projectile/EntityFireball.motionY:D
        //   171: aload_0        
        //   172: dup            
        //   173: getfield        net/minecraft/entity/projectile/EntityFireball.motionZ:D
        //   176: aload_0        
        //   177: getfield        net/minecraft/entity/projectile/EntityFireball.rand:Ljava/util/Random;
        //   180: invokevirtual   java/util/Random.nextFloat:()F
        //   183: ldc_w           0.2
        //   186: fmul           
        //   187: f2d            
        //   188: dmul           
        //   189: putfield        net/minecraft/entity/projectile/EntityFireball.motionZ:D
        //   192: aload_0        
        //   193: iconst_0       
        //   194: putfield        net/minecraft/entity/projectile/EntityFireball.ticksAlive:I
        //   197: aload_0        
        //   198: iconst_0       
        //   199: putfield        net/minecraft/entity/projectile/EntityFireball.ticksInAir:I
        //   202: goto            215
        //   205: aload_0        
        //   206: dup            
        //   207: getfield        net/minecraft/entity/projectile/EntityFireball.ticksInAir:I
        //   210: iconst_1       
        //   211: iadd           
        //   212: putfield        net/minecraft/entity/projectile/EntityFireball.ticksInAir:I
        //   215: new             Lnet/minecraft/util/Vec3;
        //   218: dup            
        //   219: aload_0        
        //   220: getfield        net/minecraft/entity/projectile/EntityFireball.posX:D
        //   223: aload_0        
        //   224: getfield        net/minecraft/entity/projectile/EntityFireball.posY:D
        //   227: aload_0        
        //   228: getfield        net/minecraft/entity/projectile/EntityFireball.posZ:D
        //   231: invokespecial   net/minecraft/util/Vec3.<init>:(DDD)V
        //   234: astore_1       
        //   235: new             Lnet/minecraft/util/Vec3;
        //   238: dup            
        //   239: aload_0        
        //   240: getfield        net/minecraft/entity/projectile/EntityFireball.posX:D
        //   243: aload_0        
        //   244: getfield        net/minecraft/entity/projectile/EntityFireball.motionX:D
        //   247: dadd           
        //   248: aload_0        
        //   249: getfield        net/minecraft/entity/projectile/EntityFireball.posY:D
        //   252: aload_0        
        //   253: getfield        net/minecraft/entity/projectile/EntityFireball.motionY:D
        //   256: dadd           
        //   257: aload_0        
        //   258: getfield        net/minecraft/entity/projectile/EntityFireball.posZ:D
        //   261: aload_0        
        //   262: getfield        net/minecraft/entity/projectile/EntityFireball.motionZ:D
        //   265: dadd           
        //   266: invokespecial   net/minecraft/util/Vec3.<init>:(DDD)V
        //   269: astore_2       
        //   270: aload_0        
        //   271: getfield        net/minecraft/entity/projectile/EntityFireball.worldObj:Lnet/minecraft/world/World;
        //   274: aload_1        
        //   275: aload_2        
        //   276: invokevirtual   net/minecraft/world/World.rayTraceBlocks:(Lnet/minecraft/util/Vec3;Lnet/minecraft/util/Vec3;)Lnet/minecraft/util/MovingObjectPosition;
        //   279: astore_3       
        //   280: new             Lnet/minecraft/util/Vec3;
        //   283: dup            
        //   284: aload_0        
        //   285: getfield        net/minecraft/entity/projectile/EntityFireball.posX:D
        //   288: aload_0        
        //   289: getfield        net/minecraft/entity/projectile/EntityFireball.posY:D
        //   292: aload_0        
        //   293: getfield        net/minecraft/entity/projectile/EntityFireball.posZ:D
        //   296: invokespecial   net/minecraft/util/Vec3.<init>:(DDD)V
        //   299: astore_1       
        //   300: new             Lnet/minecraft/util/Vec3;
        //   303: dup            
        //   304: aload_0        
        //   305: getfield        net/minecraft/entity/projectile/EntityFireball.posX:D
        //   308: aload_0        
        //   309: getfield        net/minecraft/entity/projectile/EntityFireball.motionX:D
        //   312: dadd           
        //   313: aload_0        
        //   314: getfield        net/minecraft/entity/projectile/EntityFireball.posY:D
        //   317: aload_0        
        //   318: getfield        net/minecraft/entity/projectile/EntityFireball.motionY:D
        //   321: dadd           
        //   322: aload_0        
        //   323: getfield        net/minecraft/entity/projectile/EntityFireball.posZ:D
        //   326: aload_0        
        //   327: getfield        net/minecraft/entity/projectile/EntityFireball.motionZ:D
        //   330: dadd           
        //   331: invokespecial   net/minecraft/util/Vec3.<init>:(DDD)V
        //   334: astore_2       
        //   335: aload_3        
        //   336: ifnull          368
        //   339: new             Lnet/minecraft/util/Vec3;
        //   342: dup            
        //   343: aload_3        
        //   344: getfield        net/minecraft/util/MovingObjectPosition.hitVec:Lnet/minecraft/util/Vec3;
        //   347: getfield        net/minecraft/util/Vec3.xCoord:D
        //   350: aload_3        
        //   351: getfield        net/minecraft/util/MovingObjectPosition.hitVec:Lnet/minecraft/util/Vec3;
        //   354: getfield        net/minecraft/util/Vec3.yCoord:D
        //   357: aload_3        
        //   358: getfield        net/minecraft/util/MovingObjectPosition.hitVec:Lnet/minecraft/util/Vec3;
        //   361: getfield        net/minecraft/util/Vec3.zCoord:D
        //   364: invokespecial   net/minecraft/util/Vec3.<init>:(DDD)V
        //   367: astore_2       
        //   368: aconst_null    
        //   369: astore          4
        //   371: aload_0        
        //   372: getfield        net/minecraft/entity/projectile/EntityFireball.worldObj:Lnet/minecraft/world/World;
        //   375: aload_0        
        //   376: aload_0        
        //   377: invokevirtual   net/minecraft/entity/projectile/EntityFireball.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //   380: aload_0        
        //   381: getfield        net/minecraft/entity/projectile/EntityFireball.motionX:D
        //   384: aload_0        
        //   385: getfield        net/minecraft/entity/projectile/EntityFireball.motionY:D
        //   388: aload_0        
        //   389: getfield        net/minecraft/entity/projectile/EntityFireball.motionZ:D
        //   392: invokevirtual   net/minecraft/util/AxisAlignedBB.addCoord:(DDD)Lnet/minecraft/util/AxisAlignedBB;
        //   395: dconst_1       
        //   396: dconst_1       
        //   397: dconst_1       
        //   398: invokevirtual   net/minecraft/util/AxisAlignedBB.expand:(DDD)Lnet/minecraft/util/AxisAlignedBB;
        //   401: invokevirtual   net/minecraft/world/World.getEntitiesWithinAABBExcludingEntity:(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/AxisAlignedBB;)Ljava/util/List;
        //   404: astore          5
        //   406: dconst_0       
        //   407: dstore          6
        //   409: iconst_0       
        //   410: aload           5
        //   412: invokeinterface java/util/List.size:()I
        //   417: if_icmpge       540
        //   420: aload           5
        //   422: iconst_0       
        //   423: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   428: checkcast       Lnet/minecraft/entity/Entity;
        //   431: astore          9
        //   433: aload           9
        //   435: invokevirtual   net/minecraft/entity/Entity.canBeCollidedWith:()Z
        //   438: ifeq            534
        //   441: aload           9
        //   443: aload_0        
        //   444: getfield        net/minecraft/entity/projectile/EntityFireball.shootingEntity:Lnet/minecraft/entity/EntityLivingBase;
        //   447: invokevirtual   net/minecraft/entity/Entity.isEntityEqual:(Lnet/minecraft/entity/Entity;)Z
        //   450: ifeq            462
        //   453: aload_0        
        //   454: getfield        net/minecraft/entity/projectile/EntityFireball.ticksInAir:I
        //   457: bipush          25
        //   459: if_icmplt       534
        //   462: ldc_w           0.3
        //   465: fstore          10
        //   467: aload           9
        //   469: invokevirtual   net/minecraft/entity/Entity.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //   472: fload           10
        //   474: f2d            
        //   475: fload           10
        //   477: f2d            
        //   478: fload           10
        //   480: f2d            
        //   481: invokevirtual   net/minecraft/util/AxisAlignedBB.expand:(DDD)Lnet/minecraft/util/AxisAlignedBB;
        //   484: astore          11
        //   486: aload           11
        //   488: aload_1        
        //   489: aload_2        
        //   490: invokevirtual   net/minecraft/util/AxisAlignedBB.calculateIntercept:(Lnet/minecraft/util/Vec3;Lnet/minecraft/util/Vec3;)Lnet/minecraft/util/MovingObjectPosition;
        //   493: astore          12
        //   495: aload           12
        //   497: ifnull          534
        //   500: aload_1        
        //   501: aload           12
        //   503: getfield        net/minecraft/util/MovingObjectPosition.hitVec:Lnet/minecraft/util/Vec3;
        //   506: invokevirtual   net/minecraft/util/Vec3.squareDistanceTo:(Lnet/minecraft/util/Vec3;)D
        //   509: dstore          13
        //   511: dload           13
        //   513: dload           6
        //   515: dcmpg          
        //   516: iflt            526
        //   519: dload           6
        //   521: dconst_0       
        //   522: dcmpl          
        //   523: ifne            534
        //   526: aload           9
        //   528: astore          4
        //   530: dload           13
        //   532: dstore          6
        //   534: iinc            8, 1
        //   537: goto            409
        //   540: aload           4
        //   542: ifnull          555
        //   545: new             Lnet/minecraft/util/MovingObjectPosition;
        //   548: dup            
        //   549: aload           4
        //   551: invokespecial   net/minecraft/util/MovingObjectPosition.<init>:(Lnet/minecraft/entity/Entity;)V
        //   554: astore_3       
        //   555: aload_3        
        //   556: ifnull          564
        //   559: aload_0        
        //   560: aload_3        
        //   561: invokevirtual   net/minecraft/entity/projectile/EntityFireball.onImpact:(Lnet/minecraft/util/MovingObjectPosition;)V
        //   564: aload_0        
        //   565: dup            
        //   566: getfield        net/minecraft/entity/projectile/EntityFireball.posX:D
        //   569: aload_0        
        //   570: getfield        net/minecraft/entity/projectile/EntityFireball.motionX:D
        //   573: dadd           
        //   574: putfield        net/minecraft/entity/projectile/EntityFireball.posX:D
        //   577: aload_0        
        //   578: dup            
        //   579: getfield        net/minecraft/entity/projectile/EntityFireball.posY:D
        //   582: aload_0        
        //   583: getfield        net/minecraft/entity/projectile/EntityFireball.motionY:D
        //   586: dadd           
        //   587: putfield        net/minecraft/entity/projectile/EntityFireball.posY:D
        //   590: aload_0        
        //   591: dup            
        //   592: getfield        net/minecraft/entity/projectile/EntityFireball.posZ:D
        //   595: aload_0        
        //   596: getfield        net/minecraft/entity/projectile/EntityFireball.motionZ:D
        //   599: dadd           
        //   600: putfield        net/minecraft/entity/projectile/EntityFireball.posZ:D
        //   603: aload_0        
        //   604: getfield        net/minecraft/entity/projectile/EntityFireball.motionX:D
        //   607: aload_0        
        //   608: getfield        net/minecraft/entity/projectile/EntityFireball.motionX:D
        //   611: dmul           
        //   612: aload_0        
        //   613: getfield        net/minecraft/entity/projectile/EntityFireball.motionZ:D
        //   616: aload_0        
        //   617: getfield        net/minecraft/entity/projectile/EntityFireball.motionZ:D
        //   620: dmul           
        //   621: dadd           
        //   622: invokestatic    net/minecraft/util/MathHelper.sqrt_double:(D)F
        //   625: fstore          8
        //   627: aload_0        
        //   628: aload_0        
        //   629: getfield        net/minecraft/entity/projectile/EntityFireball.motionZ:D
        //   632: aload_0        
        //   633: getfield        net/minecraft/entity/projectile/EntityFireball.motionX:D
        //   636: invokestatic    net/minecraft/util/MathHelper.func_181159_b:(DD)D
        //   639: ldc2_w          180.0
        //   642: dmul           
        //   643: ldc2_w          3.141592653589793
        //   646: ddiv           
        //   647: d2f            
        //   648: ldc_w           90.0
        //   651: fadd           
        //   652: putfield        net/minecraft/entity/projectile/EntityFireball.rotationYaw:F
        //   655: aload_0        
        //   656: fload           8
        //   658: f2d            
        //   659: aload_0        
        //   660: getfield        net/minecraft/entity/projectile/EntityFireball.motionY:D
        //   663: invokestatic    net/minecraft/util/MathHelper.func_181159_b:(DD)D
        //   666: ldc2_w          180.0
        //   669: dmul           
        //   670: ldc2_w          3.141592653589793
        //   673: ddiv           
        //   674: d2f            
        //   675: ldc_w           90.0
        //   678: fsub           
        //   679: putfield        net/minecraft/entity/projectile/EntityFireball.rotationPitch:F
        //   682: aload_0        
        //   683: getfield        net/minecraft/entity/projectile/EntityFireball.rotationPitch:F
        //   686: aload_0        
        //   687: getfield        net/minecraft/entity/projectile/EntityFireball.prevRotationPitch:F
        //   690: fsub           
        //   691: ldc_w           -180.0
        //   694: fcmpg          
        //   695: ifge            713
        //   698: aload_0        
        //   699: dup            
        //   700: getfield        net/minecraft/entity/projectile/EntityFireball.prevRotationPitch:F
        //   703: ldc_w           360.0
        //   706: fsub           
        //   707: putfield        net/minecraft/entity/projectile/EntityFireball.prevRotationPitch:F
        //   710: goto            682
        //   713: aload_0        
        //   714: getfield        net/minecraft/entity/projectile/EntityFireball.rotationPitch:F
        //   717: aload_0        
        //   718: getfield        net/minecraft/entity/projectile/EntityFireball.prevRotationPitch:F
        //   721: fsub           
        //   722: ldc_w           180.0
        //   725: fcmpl          
        //   726: iflt            744
        //   729: aload_0        
        //   730: dup            
        //   731: getfield        net/minecraft/entity/projectile/EntityFireball.prevRotationPitch:F
        //   734: ldc_w           360.0
        //   737: fadd           
        //   738: putfield        net/minecraft/entity/projectile/EntityFireball.prevRotationPitch:F
        //   741: goto            713
        //   744: aload_0        
        //   745: getfield        net/minecraft/entity/projectile/EntityFireball.rotationYaw:F
        //   748: aload_0        
        //   749: getfield        net/minecraft/entity/projectile/EntityFireball.prevRotationYaw:F
        //   752: fsub           
        //   753: ldc_w           -180.0
        //   756: fcmpg          
        //   757: ifge            775
        //   760: aload_0        
        //   761: dup            
        //   762: getfield        net/minecraft/entity/projectile/EntityFireball.prevRotationYaw:F
        //   765: ldc_w           360.0
        //   768: fsub           
        //   769: putfield        net/minecraft/entity/projectile/EntityFireball.prevRotationYaw:F
        //   772: goto            744
        //   775: aload_0        
        //   776: getfield        net/minecraft/entity/projectile/EntityFireball.rotationYaw:F
        //   779: aload_0        
        //   780: getfield        net/minecraft/entity/projectile/EntityFireball.prevRotationYaw:F
        //   783: fsub           
        //   784: ldc_w           180.0
        //   787: fcmpl          
        //   788: iflt            806
        //   791: aload_0        
        //   792: dup            
        //   793: getfield        net/minecraft/entity/projectile/EntityFireball.prevRotationYaw:F
        //   796: ldc_w           360.0
        //   799: fadd           
        //   800: putfield        net/minecraft/entity/projectile/EntityFireball.prevRotationYaw:F
        //   803: goto            775
        //   806: aload_0        
        //   807: aload_0        
        //   808: getfield        net/minecraft/entity/projectile/EntityFireball.prevRotationPitch:F
        //   811: aload_0        
        //   812: getfield        net/minecraft/entity/projectile/EntityFireball.rotationPitch:F
        //   815: aload_0        
        //   816: getfield        net/minecraft/entity/projectile/EntityFireball.prevRotationPitch:F
        //   819: fsub           
        //   820: ldc_w           0.2
        //   823: fmul           
        //   824: fadd           
        //   825: putfield        net/minecraft/entity/projectile/EntityFireball.rotationPitch:F
        //   828: aload_0        
        //   829: aload_0        
        //   830: getfield        net/minecraft/entity/projectile/EntityFireball.prevRotationYaw:F
        //   833: aload_0        
        //   834: getfield        net/minecraft/entity/projectile/EntityFireball.rotationYaw:F
        //   837: aload_0        
        //   838: getfield        net/minecraft/entity/projectile/EntityFireball.prevRotationYaw:F
        //   841: fsub           
        //   842: ldc_w           0.2
        //   845: fmul           
        //   846: fadd           
        //   847: putfield        net/minecraft/entity/projectile/EntityFireball.rotationYaw:F
        //   850: aload_0        
        //   851: invokevirtual   net/minecraft/entity/projectile/EntityFireball.getMotionFactor:()F
        //   854: fstore          9
        //   856: aload_0        
        //   857: invokevirtual   net/minecraft/entity/projectile/EntityFireball.isInWater:()Z
        //   860: ifeq            943
        //   863: ldc_w           0.25
        //   866: fstore          11
        //   868: aload_0        
        //   869: getfield        net/minecraft/entity/projectile/EntityFireball.worldObj:Lnet/minecraft/world/World;
        //   872: getstatic       net/minecraft/util/EnumParticleTypes.WATER_BUBBLE:Lnet/minecraft/util/EnumParticleTypes;
        //   875: aload_0        
        //   876: getfield        net/minecraft/entity/projectile/EntityFireball.posX:D
        //   879: aload_0        
        //   880: getfield        net/minecraft/entity/projectile/EntityFireball.motionX:D
        //   883: fload           11
        //   885: f2d            
        //   886: dmul           
        //   887: dsub           
        //   888: aload_0        
        //   889: getfield        net/minecraft/entity/projectile/EntityFireball.posY:D
        //   892: aload_0        
        //   893: getfield        net/minecraft/entity/projectile/EntityFireball.motionY:D
        //   896: fload           11
        //   898: f2d            
        //   899: dmul           
        //   900: dsub           
        //   901: aload_0        
        //   902: getfield        net/minecraft/entity/projectile/EntityFireball.posZ:D
        //   905: aload_0        
        //   906: getfield        net/minecraft/entity/projectile/EntityFireball.motionZ:D
        //   909: fload           11
        //   911: f2d            
        //   912: dmul           
        //   913: dsub           
        //   914: aload_0        
        //   915: getfield        net/minecraft/entity/projectile/EntityFireball.motionX:D
        //   918: aload_0        
        //   919: getfield        net/minecraft/entity/projectile/EntityFireball.motionY:D
        //   922: aload_0        
        //   923: getfield        net/minecraft/entity/projectile/EntityFireball.motionZ:D
        //   926: iconst_0       
        //   927: newarray        I
        //   929: invokevirtual   net/minecraft/world/World.spawnParticle:(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V
        //   932: iinc            10, 1
        //   935: goto            863
        //   938: ldc_w           0.8
        //   941: fstore          9
        //   943: aload_0        
        //   944: dup            
        //   945: getfield        net/minecraft/entity/projectile/EntityFireball.motionX:D
        //   948: aload_0        
        //   949: getfield        net/minecraft/entity/projectile/EntityFireball.accelerationX:D
        //   952: dadd           
        //   953: putfield        net/minecraft/entity/projectile/EntityFireball.motionX:D
        //   956: aload_0        
        //   957: dup            
        //   958: getfield        net/minecraft/entity/projectile/EntityFireball.motionY:D
        //   961: aload_0        
        //   962: getfield        net/minecraft/entity/projectile/EntityFireball.accelerationY:D
        //   965: dadd           
        //   966: putfield        net/minecraft/entity/projectile/EntityFireball.motionY:D
        //   969: aload_0        
        //   970: dup            
        //   971: getfield        net/minecraft/entity/projectile/EntityFireball.motionZ:D
        //   974: aload_0        
        //   975: getfield        net/minecraft/entity/projectile/EntityFireball.accelerationZ:D
        //   978: dadd           
        //   979: putfield        net/minecraft/entity/projectile/EntityFireball.motionZ:D
        //   982: aload_0        
        //   983: dup            
        //   984: getfield        net/minecraft/entity/projectile/EntityFireball.motionX:D
        //   987: fload           9
        //   989: f2d            
        //   990: dmul           
        //   991: putfield        net/minecraft/entity/projectile/EntityFireball.motionX:D
        //   994: aload_0        
        //   995: dup            
        //   996: getfield        net/minecraft/entity/projectile/EntityFireball.motionY:D
        //   999: fload           9
        //  1001: f2d            
        //  1002: dmul           
        //  1003: putfield        net/minecraft/entity/projectile/EntityFireball.motionY:D
        //  1006: aload_0        
        //  1007: dup            
        //  1008: getfield        net/minecraft/entity/projectile/EntityFireball.motionZ:D
        //  1011: fload           9
        //  1013: f2d            
        //  1014: dmul           
        //  1015: putfield        net/minecraft/entity/projectile/EntityFireball.motionZ:D
        //  1018: aload_0        
        //  1019: getfield        net/minecraft/entity/projectile/EntityFireball.worldObj:Lnet/minecraft/world/World;
        //  1022: getstatic       net/minecraft/util/EnumParticleTypes.SMOKE_NORMAL:Lnet/minecraft/util/EnumParticleTypes;
        //  1025: aload_0        
        //  1026: getfield        net/minecraft/entity/projectile/EntityFireball.posX:D
        //  1029: aload_0        
        //  1030: getfield        net/minecraft/entity/projectile/EntityFireball.posY:D
        //  1033: ldc2_w          0.5
        //  1036: dadd           
        //  1037: aload_0        
        //  1038: getfield        net/minecraft/entity/projectile/EntityFireball.posZ:D
        //  1041: dconst_0       
        //  1042: dconst_0       
        //  1043: dconst_0       
        //  1044: iconst_0       
        //  1045: newarray        I
        //  1047: invokevirtual   net/minecraft/world/World.spawnParticle:(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V
        //  1050: aload_0        
        //  1051: aload_0        
        //  1052: getfield        net/minecraft/entity/projectile/EntityFireball.posX:D
        //  1055: aload_0        
        //  1056: getfield        net/minecraft/entity/projectile/EntityFireball.posY:D
        //  1059: aload_0        
        //  1060: getfield        net/minecraft/entity/projectile/EntityFireball.posZ:D
        //  1063: invokevirtual   net/minecraft/entity/projectile/EntityFireball.setPosition:(DDD)V
        //  1066: goto            1073
        //  1069: aload_0        
        //  1070: invokevirtual   net/minecraft/entity/projectile/EntityFireball.setDead:()V
        //  1073: return         
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
    
    public EntityFireball(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        super(world);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.setSize(1.0f, 1.0f);
        this.setLocationAndAngles(n, n2, n3, this.rotationYaw, this.rotationPitch);
        this.setPosition(n, n2, n3);
        final double n7 = MathHelper.sqrt_double(n4 * n4 + n5 * n5 + n6 * n6);
        this.accelerationX = n4 / n7 * 0.1;
        this.accelerationY = n5 / n7 * 0.1;
        this.accelerationZ = n6 / n7 * 0.1;
    }
    
    @Override
    public int getBrightnessForRender(final float n) {
        return 15728880;
    }
    
    public EntityFireball(final World world) {
        super(world);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.setSize(1.0f, 1.0f);
    }
    
    @Override
    public float getCollisionBorderSize() {
        return 1.0f;
    }
    
    protected abstract void onImpact(final MovingObjectPosition p0);
    
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setShort("xTile", (short)this.xTile);
        nbtTagCompound.setShort("yTile", (short)this.yTile);
        nbtTagCompound.setShort("zTile", (short)this.zTile);
        final ResourceLocation resourceLocation = (ResourceLocation)Block.blockRegistry.getNameForObject(this.inTile);
        nbtTagCompound.setString("inTile", (resourceLocation == null) ? "" : resourceLocation.toString());
        nbtTagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
        nbtTagCompound.setTag("direction", this.newDoubleNBTList(this.motionX, this.motionY, this.motionZ));
    }
}
