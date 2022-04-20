package net.minecraft.entity.boss;

import net.minecraft.entity.monster.*;
import com.google.common.base.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.potion.*;
import net.minecraft.init.*;
import net.minecraft.stats.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;

public class EntityWither extends EntityMob implements IRangedAttackMob, IBossDisplayData
{
    private float[] field_82218_g;
    private int[] field_82224_i;
    private int blockBreakCounter;
    private float[] field_82217_f;
    private static final Predicate attackEntitySelector;
    private int[] field_82223_h;
    private float[] field_82221_e;
    private float[] field_82220_d;
    
    private double func_82214_u(final int n) {
        if (n <= 0) {
            return this.posX;
        }
        return this.posX + MathHelper.cos((this.renderYawOffset + 180 * (n - 1)) / 180.0f * 3.1415927f) * 1.3;
    }
    
    public void func_82206_m() {
        this.setInvulTime(220);
        this.setHealth(this.getMaxHealth() / 3.0f);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("Invul", this.getInvulTime());
    }
    
    static {
        attackEntitySelector = (Predicate)new Predicate() {
            public boolean apply(final Object o) {
                return this.apply((Entity)o);
            }
            
            public boolean apply(final Entity entity) {
                return entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD;
            }
        };
    }
    
    private void launchWitherSkullToCoords(final int n, final double n2, final double n3, final double n4, final boolean b) {
        this.worldObj.playAuxSFXAtEntity(null, 1014, new BlockPos(this), 0);
        final double func_82214_u = this.func_82214_u(n);
        final double func_82208_v = this.func_82208_v(n);
        final double func_82213_w = this.func_82213_w(n);
        final EntityWitherSkull entityWitherSkull = new EntityWitherSkull(this.worldObj, this, n2 - func_82214_u, n3 - func_82208_v, n4 - func_82213_w);
        if (b) {
            entityWitherSkull.setInvulnerable(true);
        }
        entityWitherSkull.posY = func_82208_v;
        entityWitherSkull.posX = func_82214_u;
        entityWitherSkull.posZ = func_82213_w;
        this.worldObj.spawnEntityInWorld(entityWitherSkull);
    }
    
    @Override
    public void setInWeb() {
    }
    
    public float func_82210_r(final int n) {
        return this.field_82220_d[n];
    }
    
    public float func_82207_a(final int n) {
        return this.field_82221_e[n];
    }
    
    public int getWatchedTargetId(final int n) {
        return this.dataWatcher.getWatchableObjectInt(17 + n);
    }
    
    @Override
    public void mountEntity(final Entity entity) {
        this.ridingEntity = null;
    }
    
    private double func_82208_v(final int n) {
        return (n <= 0) ? (this.posY + 3.0) : (this.posY + 2.2);
    }
    
    @Override
    public int getBrightnessForRender(final float n) {
        return 15728880;
    }
    
    public int getInvulTime() {
        return this.dataWatcher.getWatchableObjectInt(20);
    }
    
    @Override
    protected void despawnEntity() {
        this.entityAge = 0;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }
        if (damageSource == DamageSource.drown || damageSource.getEntity() instanceof EntityWither) {
            return false;
        }
        if (this.getInvulTime() > 0 && damageSource != DamageSource.outOfWorld) {
            return false;
        }
        if (this <= 0 && damageSource.getSourceOfDamage() instanceof EntityArrow) {
            return false;
        }
        final Entity entity = damageSource.getEntity();
        if (entity != null && !(entity instanceof EntityPlayer) && entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getCreatureAttribute() == this.getCreatureAttribute()) {
            return false;
        }
        if (this.blockBreakCounter <= 0) {
            this.blockBreakCounter = 20;
        }
        while (0 < this.field_82224_i.length) {
            final int[] field_82224_i = this.field_82224_i;
            final int n2 = 0;
            field_82224_i[n2] += 3;
            int n3 = 0;
            ++n3;
        }
        return super.attackEntityFrom(damageSource, n);
    }
    
    @Override
    protected void updateAITasks() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   net/minecraft/entity/boss/EntityWither.getInvulTime:()I
        //     4: ifle            102
        //     7: aload_0        
        //     8: invokevirtual   net/minecraft/entity/boss/EntityWither.getInvulTime:()I
        //    11: iconst_1       
        //    12: isub           
        //    13: istore_1       
        //    14: goto            78
        //    17: aload_0        
        //    18: getfield        net/minecraft/entity/boss/EntityWither.worldObj:Lnet/minecraft/world/World;
        //    21: aload_0        
        //    22: aload_0        
        //    23: getfield        net/minecraft/entity/boss/EntityWither.posX:D
        //    26: aload_0        
        //    27: getfield        net/minecraft/entity/boss/EntityWither.posY:D
        //    30: aload_0        
        //    31: invokevirtual   net/minecraft/entity/boss/EntityWither.getEyeHeight:()F
        //    34: f2d            
        //    35: dadd           
        //    36: aload_0        
        //    37: getfield        net/minecraft/entity/boss/EntityWither.posZ:D
        //    40: ldc             7.0
        //    42: iconst_0       
        //    43: aload_0        
        //    44: getfield        net/minecraft/entity/boss/EntityWither.worldObj:Lnet/minecraft/world/World;
        //    47: invokevirtual   net/minecraft/world/World.getGameRules:()Lnet/minecraft/world/GameRules;
        //    50: ldc             "mobGriefing"
        //    52: invokevirtual   net/minecraft/world/GameRules.getBoolean:(Ljava/lang/String;)Z
        //    55: invokevirtual   net/minecraft/world/World.newExplosion:(Lnet/minecraft/entity/Entity;DDDFZZ)Lnet/minecraft/world/Explosion;
        //    58: pop            
        //    59: aload_0        
        //    60: getfield        net/minecraft/entity/boss/EntityWither.worldObj:Lnet/minecraft/world/World;
        //    63: sipush          1013
        //    66: new             Lnet/minecraft/util/BlockPos;
        //    69: dup            
        //    70: aload_0        
        //    71: invokespecial   net/minecraft/util/BlockPos.<init>:(Lnet/minecraft/entity/Entity;)V
        //    74: iconst_0       
        //    75: invokevirtual   net/minecraft/world/World.playBroadcastSound:(ILnet/minecraft/util/BlockPos;I)V
        //    78: aload_0        
        //    79: iconst_1       
        //    80: invokevirtual   net/minecraft/entity/boss/EntityWither.setInvulTime:(I)V
        //    83: aload_0        
        //    84: getfield        net/minecraft/entity/boss/EntityWither.ticksExisted:I
        //    87: bipush          10
        //    89: irem           
        //    90: ifne            99
        //    93: aload_0        
        //    94: ldc             10.0
        //    96: invokevirtual   net/minecraft/entity/boss/EntityWither.heal:(F)V
        //    99: goto            810
        //   102: aload_0        
        //   103: invokespecial   net/minecraft/entity/monster/EntityMob.updateAITasks:()V
        //   106: aload_0        
        //   107: getfield        net/minecraft/entity/boss/EntityWither.ticksExisted:I
        //   110: aload_0        
        //   111: getfield        net/minecraft/entity/boss/EntityWither.field_82223_h:[I
        //   114: iconst_0       
        //   115: iaload         
        //   116: if_icmplt       581
        //   119: aload_0        
        //   120: getfield        net/minecraft/entity/boss/EntityWither.field_82223_h:[I
        //   123: iconst_0       
        //   124: aload_0        
        //   125: getfield        net/minecraft/entity/boss/EntityWither.ticksExisted:I
        //   128: bipush          10
        //   130: iadd           
        //   131: aload_0        
        //   132: getfield        net/minecraft/entity/boss/EntityWither.rand:Ljava/util/Random;
        //   135: bipush          10
        //   137: invokevirtual   java/util/Random.nextInt:(I)I
        //   140: iadd           
        //   141: iastore        
        //   142: aload_0        
        //   143: getfield        net/minecraft/entity/boss/EntityWither.worldObj:Lnet/minecraft/world/World;
        //   146: invokevirtual   net/minecraft/world/World.getDifficulty:()Lnet/minecraft/world/EnumDifficulty;
        //   149: getstatic       net/minecraft/world/EnumDifficulty.NORMAL:Lnet/minecraft/world/EnumDifficulty;
        //   152: if_acmpeq       168
        //   155: aload_0        
        //   156: getfield        net/minecraft/entity/boss/EntityWither.worldObj:Lnet/minecraft/world/World;
        //   159: invokevirtual   net/minecraft/world/World.getDifficulty:()Lnet/minecraft/world/EnumDifficulty;
        //   162: getstatic       net/minecraft/world/EnumDifficulty.HARD:Lnet/minecraft/world/EnumDifficulty;
        //   165: if_acmpne       298
        //   168: aload_0        
        //   169: getfield        net/minecraft/entity/boss/EntityWither.field_82224_i:[I
        //   172: iconst_0       
        //   173: iaload         
        //   174: istore_3       
        //   175: aload_0        
        //   176: getfield        net/minecraft/entity/boss/EntityWither.field_82224_i:[I
        //   179: iconst_0       
        //   180: aload_0        
        //   181: getfield        net/minecraft/entity/boss/EntityWither.field_82224_i:[I
        //   184: iconst_0       
        //   185: iaload         
        //   186: iconst_1       
        //   187: iadd           
        //   188: iastore        
        //   189: iload_3        
        //   190: bipush          15
        //   192: if_icmple       298
        //   195: ldc             10.0
        //   197: fstore          4
        //   199: ldc_w           5.0
        //   202: fstore          5
        //   204: aload_0        
        //   205: getfield        net/minecraft/entity/boss/EntityWither.rand:Ljava/util/Random;
        //   208: aload_0        
        //   209: getfield        net/minecraft/entity/boss/EntityWither.posX:D
        //   212: fload           4
        //   214: f2d            
        //   215: dsub           
        //   216: aload_0        
        //   217: getfield        net/minecraft/entity/boss/EntityWither.posX:D
        //   220: fload           4
        //   222: f2d            
        //   223: dadd           
        //   224: invokestatic    net/minecraft/util/MathHelper.getRandomDoubleInRange:(Ljava/util/Random;DD)D
        //   227: dstore          6
        //   229: aload_0        
        //   230: getfield        net/minecraft/entity/boss/EntityWither.rand:Ljava/util/Random;
        //   233: aload_0        
        //   234: getfield        net/minecraft/entity/boss/EntityWither.posY:D
        //   237: fload           5
        //   239: f2d            
        //   240: dsub           
        //   241: aload_0        
        //   242: getfield        net/minecraft/entity/boss/EntityWither.posY:D
        //   245: fload           5
        //   247: f2d            
        //   248: dadd           
        //   249: invokestatic    net/minecraft/util/MathHelper.getRandomDoubleInRange:(Ljava/util/Random;DD)D
        //   252: dstore          8
        //   254: aload_0        
        //   255: getfield        net/minecraft/entity/boss/EntityWither.rand:Ljava/util/Random;
        //   258: aload_0        
        //   259: getfield        net/minecraft/entity/boss/EntityWither.posZ:D
        //   262: fload           4
        //   264: f2d            
        //   265: dsub           
        //   266: aload_0        
        //   267: getfield        net/minecraft/entity/boss/EntityWither.posZ:D
        //   270: fload           4
        //   272: f2d            
        //   273: dadd           
        //   274: invokestatic    net/minecraft/util/MathHelper.getRandomDoubleInRange:(Ljava/util/Random;DD)D
        //   277: dstore          10
        //   279: aload_0        
        //   280: iconst_2       
        //   281: dload           6
        //   283: dload           8
        //   285: dload           10
        //   287: iconst_1       
        //   288: invokespecial   net/minecraft/entity/boss/EntityWither.launchWitherSkullToCoords:(IDDDZ)V
        //   291: aload_0        
        //   292: getfield        net/minecraft/entity/boss/EntityWither.field_82224_i:[I
        //   295: iconst_0       
        //   296: iconst_0       
        //   297: iastore        
        //   298: aload_0        
        //   299: iconst_1       
        //   300: invokevirtual   net/minecraft/entity/boss/EntityWither.getWatchedTargetId:(I)I
        //   303: istore_2       
        //   304: goto            427
        //   307: aload_0        
        //   308: getfield        net/minecraft/entity/boss/EntityWither.worldObj:Lnet/minecraft/world/World;
        //   311: iconst_0       
        //   312: invokevirtual   net/minecraft/world/World.getEntityByID:(I)Lnet/minecraft/entity/Entity;
        //   315: astore_3       
        //   316: aload_3        
        //   317: ifnull          418
        //   320: aload_3        
        //   321: invokevirtual   net/minecraft/entity/Entity.isEntityAlive:()Z
        //   324: ifeq            418
        //   327: aload_0        
        //   328: aload_3        
        //   329: invokevirtual   net/minecraft/entity/boss/EntityWither.getDistanceSqToEntity:(Lnet/minecraft/entity/Entity;)D
        //   332: ldc2_w          900.0
        //   335: dcmpg          
        //   336: ifgt            418
        //   339: aload_0        
        //   340: aload_3        
        //   341: invokevirtual   net/minecraft/entity/boss/EntityWither.canEntityBeSeen:(Lnet/minecraft/entity/Entity;)Z
        //   344: ifeq            418
        //   347: aload_3        
        //   348: instanceof      Lnet/minecraft/entity/player/EntityPlayer;
        //   351: ifeq            376
        //   354: aload_3        
        //   355: checkcast       Lnet/minecraft/entity/player/EntityPlayer;
        //   358: getfield        net/minecraft/entity/player/EntityPlayer.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //   361: getfield        net/minecraft/entity/player/PlayerCapabilities.disableDamage:Z
        //   364: ifeq            376
        //   367: aload_0        
        //   368: iconst_1       
        //   369: iconst_0       
        //   370: invokevirtual   net/minecraft/entity/boss/EntityWither.updateWatchedTargetId:(II)V
        //   373: goto            581
        //   376: aload_0        
        //   377: iconst_2       
        //   378: aload_3        
        //   379: checkcast       Lnet/minecraft/entity/EntityLivingBase;
        //   382: invokespecial   net/minecraft/entity/boss/EntityWither.launchWitherSkullToEntity:(ILnet/minecraft/entity/EntityLivingBase;)V
        //   385: aload_0        
        //   386: getfield        net/minecraft/entity/boss/EntityWither.field_82223_h:[I
        //   389: iconst_0       
        //   390: aload_0        
        //   391: getfield        net/minecraft/entity/boss/EntityWither.ticksExisted:I
        //   394: bipush          40
        //   396: iadd           
        //   397: aload_0        
        //   398: getfield        net/minecraft/entity/boss/EntityWither.rand:Ljava/util/Random;
        //   401: bipush          20
        //   403: invokevirtual   java/util/Random.nextInt:(I)I
        //   406: iadd           
        //   407: iastore        
        //   408: aload_0        
        //   409: getfield        net/minecraft/entity/boss/EntityWither.field_82224_i:[I
        //   412: iconst_0       
        //   413: iconst_0       
        //   414: iastore        
        //   415: goto            581
        //   418: aload_0        
        //   419: iconst_1       
        //   420: iconst_0       
        //   421: invokevirtual   net/minecraft/entity/boss/EntityWither.updateWatchedTargetId:(II)V
        //   424: goto            581
        //   427: aload_0        
        //   428: getfield        net/minecraft/entity/boss/EntityWither.worldObj:Lnet/minecraft/world/World;
        //   431: ldc             Lnet/minecraft/entity/EntityLivingBase;.class
        //   433: aload_0        
        //   434: invokevirtual   net/minecraft/entity/boss/EntityWither.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //   437: ldc2_w          20.0
        //   440: ldc2_w          8.0
        //   443: ldc2_w          20.0
        //   446: invokevirtual   net/minecraft/util/AxisAlignedBB.expand:(DDD)Lnet/minecraft/util/AxisAlignedBB;
        //   449: getstatic       net/minecraft/entity/boss/EntityWither.attackEntitySelector:Lcom/google/common/base/Predicate;
        //   452: getstatic       net/minecraft/util/EntitySelectors.NOT_SPECTATING:Lcom/google/common/base/Predicate;
        //   455: invokestatic    com/google/common/base/Predicates.and:(Lcom/google/common/base/Predicate;Lcom/google/common/base/Predicate;)Lcom/google/common/base/Predicate;
        //   458: invokevirtual   net/minecraft/world/World.getEntitiesWithinAABB:(Ljava/lang/Class;Lnet/minecraft/util/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;
        //   461: astore_3       
        //   462: aload_3        
        //   463: invokeinterface java/util/List.isEmpty:()Z
        //   468: ifne            581
        //   471: aload_3        
        //   472: aload_0        
        //   473: getfield        net/minecraft/entity/boss/EntityWither.rand:Ljava/util/Random;
        //   476: aload_3        
        //   477: invokeinterface java/util/List.size:()I
        //   482: invokevirtual   java/util/Random.nextInt:(I)I
        //   485: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   490: checkcast       Lnet/minecraft/entity/EntityLivingBase;
        //   493: astore          5
        //   495: aload           5
        //   497: aload_0        
        //   498: if_acmpeq       566
        //   501: aload           5
        //   503: invokevirtual   net/minecraft/entity/EntityLivingBase.isEntityAlive:()Z
        //   506: ifeq            566
        //   509: aload_0        
        //   510: aload           5
        //   512: invokevirtual   net/minecraft/entity/boss/EntityWither.canEntityBeSeen:(Lnet/minecraft/entity/Entity;)Z
        //   515: ifeq            566
        //   518: aload           5
        //   520: instanceof      Lnet/minecraft/entity/player/EntityPlayer;
        //   523: ifeq            553
        //   526: aload           5
        //   528: checkcast       Lnet/minecraft/entity/player/EntityPlayer;
        //   531: getfield        net/minecraft/entity/player/EntityPlayer.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //   534: getfield        net/minecraft/entity/player/PlayerCapabilities.disableDamage:Z
        //   537: ifne            581
        //   540: aload_0        
        //   541: iconst_1       
        //   542: aload           5
        //   544: invokevirtual   net/minecraft/entity/EntityLivingBase.getEntityId:()I
        //   547: invokevirtual   net/minecraft/entity/boss/EntityWither.updateWatchedTargetId:(II)V
        //   550: goto            581
        //   553: aload_0        
        //   554: iconst_1       
        //   555: aload           5
        //   557: invokevirtual   net/minecraft/entity/EntityLivingBase.getEntityId:()I
        //   560: invokevirtual   net/minecraft/entity/boss/EntityWither.updateWatchedTargetId:(II)V
        //   563: goto            581
        //   566: aload_3        
        //   567: aload           5
        //   569: invokeinterface java/util/List.remove:(Ljava/lang/Object;)Z
        //   574: pop            
        //   575: iinc            4, 1
        //   578: goto            462
        //   581: iinc            1, 1
        //   584: goto            106
        //   587: aload_0        
        //   588: invokevirtual   net/minecraft/entity/boss/EntityWither.getAttackTarget:()Lnet/minecraft/entity/EntityLivingBase;
        //   591: ifnull          609
        //   594: aload_0        
        //   595: iconst_0       
        //   596: aload_0        
        //   597: invokevirtual   net/minecraft/entity/boss/EntityWither.getAttackTarget:()Lnet/minecraft/entity/EntityLivingBase;
        //   600: invokevirtual   net/minecraft/entity/EntityLivingBase.getEntityId:()I
        //   603: invokevirtual   net/minecraft/entity/boss/EntityWither.updateWatchedTargetId:(II)V
        //   606: goto            615
        //   609: aload_0        
        //   610: iconst_0       
        //   611: iconst_0       
        //   612: invokevirtual   net/minecraft/entity/boss/EntityWither.updateWatchedTargetId:(II)V
        //   615: aload_0        
        //   616: getfield        net/minecraft/entity/boss/EntityWither.blockBreakCounter:I
        //   619: ifle            795
        //   622: aload_0        
        //   623: dup            
        //   624: getfield        net/minecraft/entity/boss/EntityWither.blockBreakCounter:I
        //   627: iconst_1       
        //   628: isub           
        //   629: putfield        net/minecraft/entity/boss/EntityWither.blockBreakCounter:I
        //   632: aload_0        
        //   633: getfield        net/minecraft/entity/boss/EntityWither.blockBreakCounter:I
        //   636: ifne            795
        //   639: aload_0        
        //   640: getfield        net/minecraft/entity/boss/EntityWither.worldObj:Lnet/minecraft/world/World;
        //   643: invokevirtual   net/minecraft/world/World.getGameRules:()Lnet/minecraft/world/GameRules;
        //   646: ldc             "mobGriefing"
        //   648: invokevirtual   net/minecraft/world/GameRules.getBoolean:(Ljava/lang/String;)Z
        //   651: ifeq            795
        //   654: aload_0        
        //   655: getfield        net/minecraft/entity/boss/EntityWither.posY:D
        //   658: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   661: istore_1       
        //   662: aload_0        
        //   663: getfield        net/minecraft/entity/boss/EntityWither.posX:D
        //   666: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   669: istore_2       
        //   670: aload_0        
        //   671: getfield        net/minecraft/entity/boss/EntityWither.posZ:D
        //   674: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   677: istore_3       
        //   678: iload_3        
        //   679: iconst_m1      
        //   680: iadd           
        //   681: istore          10
        //   683: new             Lnet/minecraft/util/BlockPos;
        //   686: dup            
        //   687: iconst_m1      
        //   688: iconst_1       
        //   689: iload           10
        //   691: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   694: astore          11
        //   696: aload_0        
        //   697: getfield        net/minecraft/entity/boss/EntityWither.worldObj:Lnet/minecraft/world/World;
        //   700: aload           11
        //   702: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   705: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   710: astore          12
        //   712: aload           12
        //   714: invokevirtual   net/minecraft/block/Block.getMaterial:()Lnet/minecraft/block/material/Material;
        //   717: getstatic       net/minecraft/block/material/Material.air:Lnet/minecraft/block/material/Material;
        //   720: if_acmpeq       751
        //   723: aload           12
        //   725: if_acmpeq       751
        //   728: aload_0        
        //   729: getfield        net/minecraft/entity/boss/EntityWither.worldObj:Lnet/minecraft/world/World;
        //   732: aload           11
        //   734: iconst_1       
        //   735: invokevirtual   net/minecraft/world/World.destroyBlock:(Lnet/minecraft/util/BlockPos;Z)Z
        //   738: ifne            744
        //   741: goto            748
        //   744: iconst_1       
        //   745: goto            749
        //   748: iconst_0       
        //   749: istore          4
        //   751: iinc            7, 1
        //   754: goto            678
        //   757: iinc            6, 1
        //   760: goto            678
        //   763: iinc            5, 1
        //   766: goto            678
        //   769: goto            795
        //   772: aload_0        
        //   773: getfield        net/minecraft/entity/boss/EntityWither.worldObj:Lnet/minecraft/world/World;
        //   776: aconst_null    
        //   777: checkcast       Lnet/minecraft/entity/player/EntityPlayer;
        //   780: sipush          1012
        //   783: new             Lnet/minecraft/util/BlockPos;
        //   786: dup            
        //   787: aload_0        
        //   788: invokespecial   net/minecraft/util/BlockPos.<init>:(Lnet/minecraft/entity/Entity;)V
        //   791: iconst_0       
        //   792: invokevirtual   net/minecraft/world/World.playAuxSFXAtEntity:(Lnet/minecraft/entity/player/EntityPlayer;ILnet/minecraft/util/BlockPos;I)V
        //   795: aload_0        
        //   796: getfield        net/minecraft/entity/boss/EntityWither.ticksExisted:I
        //   799: bipush          20
        //   801: irem           
        //   802: ifne            810
        //   805: aload_0        
        //   806: fconst_1       
        //   807: invokevirtual   net/minecraft/entity/boss/EntityWither.heal:(F)V
        //   810: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void launchWitherSkullToEntity(final int n, final EntityLivingBase entityLivingBase) {
        this.launchWitherSkullToCoords(n, entityLivingBase.posX, entityLivingBase.posY + entityLivingBase.getEyeHeight() * 0.5, entityLivingBase.posZ, n == 0 && this.rand.nextFloat() < 0.001f);
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.wither.hurt";
    }
    
    @Override
    public void onLivingUpdate() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: dup            
        //     2: getfield        net/minecraft/entity/boss/EntityWither.motionY:D
        //     5: ldc2_w          0.6000000238418579
        //     8: dmul           
        //     9: putfield        net/minecraft/entity/boss/EntityWither.motionY:D
        //    12: aload_0        
        //    13: getfield        net/minecraft/entity/boss/EntityWither.worldObj:Lnet/minecraft/world/World;
        //    16: getfield        net/minecraft/world/World.isRemote:Z
        //    19: ifne            216
        //    22: aload_0        
        //    23: iconst_0       
        //    24: invokevirtual   net/minecraft/entity/boss/EntityWither.getWatchedTargetId:(I)I
        //    27: ifle            216
        //    30: aload_0        
        //    31: getfield        net/minecraft/entity/boss/EntityWither.worldObj:Lnet/minecraft/world/World;
        //    34: aload_0        
        //    35: iconst_0       
        //    36: invokevirtual   net/minecraft/entity/boss/EntityWither.getWatchedTargetId:(I)I
        //    39: invokevirtual   net/minecraft/world/World.getEntityByID:(I)Lnet/minecraft/entity/Entity;
        //    42: astore_1       
        //    43: aload_1        
        //    44: ifnull          216
        //    47: aload_0        
        //    48: getfield        net/minecraft/entity/boss/EntityWither.posY:D
        //    51: aload_1        
        //    52: getfield        net/minecraft/entity/Entity.posY:D
        //    55: dcmpg          
        //    56: iflt            79
        //    59: aload_0        
        //    60: ifgt            114
        //    63: aload_0        
        //    64: getfield        net/minecraft/entity/boss/EntityWither.posY:D
        //    67: aload_1        
        //    68: getfield        net/minecraft/entity/Entity.posY:D
        //    71: ldc2_w          5.0
        //    74: dadd           
        //    75: dcmpg          
        //    76: ifge            114
        //    79: aload_0        
        //    80: getfield        net/minecraft/entity/boss/EntityWither.motionY:D
        //    83: dconst_0       
        //    84: dcmpg          
        //    85: ifge            93
        //    88: aload_0        
        //    89: dconst_0       
        //    90: putfield        net/minecraft/entity/boss/EntityWither.motionY:D
        //    93: aload_0        
        //    94: dup            
        //    95: getfield        net/minecraft/entity/boss/EntityWither.motionY:D
        //    98: ldc2_w          0.5
        //   101: aload_0        
        //   102: getfield        net/minecraft/entity/boss/EntityWither.motionY:D
        //   105: dsub           
        //   106: ldc2_w          0.6000000238418579
        //   109: dmul           
        //   110: dadd           
        //   111: putfield        net/minecraft/entity/boss/EntityWither.motionY:D
        //   114: aload_1        
        //   115: getfield        net/minecraft/entity/Entity.posX:D
        //   118: aload_0        
        //   119: getfield        net/minecraft/entity/boss/EntityWither.posX:D
        //   122: dsub           
        //   123: dstore_2       
        //   124: aload_1        
        //   125: getfield        net/minecraft/entity/Entity.posZ:D
        //   128: aload_0        
        //   129: getfield        net/minecraft/entity/boss/EntityWither.posZ:D
        //   132: dsub           
        //   133: dstore          4
        //   135: dload_2        
        //   136: dload_2        
        //   137: dmul           
        //   138: dload           4
        //   140: dload           4
        //   142: dmul           
        //   143: dadd           
        //   144: dstore          6
        //   146: dload           6
        //   148: ldc2_w          9.0
        //   151: dcmpl          
        //   152: ifle            216
        //   155: dload           6
        //   157: invokestatic    net/minecraft/util/MathHelper.sqrt_double:(D)F
        //   160: f2d            
        //   161: dstore          8
        //   163: aload_0        
        //   164: dup            
        //   165: getfield        net/minecraft/entity/boss/EntityWither.motionX:D
        //   168: dload_2        
        //   169: dload           8
        //   171: ddiv           
        //   172: ldc2_w          0.5
        //   175: dmul           
        //   176: aload_0        
        //   177: getfield        net/minecraft/entity/boss/EntityWither.motionX:D
        //   180: dsub           
        //   181: ldc2_w          0.6000000238418579
        //   184: dmul           
        //   185: dadd           
        //   186: putfield        net/minecraft/entity/boss/EntityWither.motionX:D
        //   189: aload_0        
        //   190: dup            
        //   191: getfield        net/minecraft/entity/boss/EntityWither.motionZ:D
        //   194: dload           4
        //   196: dload           8
        //   198: ddiv           
        //   199: ldc2_w          0.5
        //   202: dmul           
        //   203: aload_0        
        //   204: getfield        net/minecraft/entity/boss/EntityWither.motionZ:D
        //   207: dsub           
        //   208: ldc2_w          0.6000000238418579
        //   211: dmul           
        //   212: dadd           
        //   213: putfield        net/minecraft/entity/boss/EntityWither.motionZ:D
        //   216: aload_0        
        //   217: getfield        net/minecraft/entity/boss/EntityWither.motionX:D
        //   220: aload_0        
        //   221: getfield        net/minecraft/entity/boss/EntityWither.motionX:D
        //   224: dmul           
        //   225: aload_0        
        //   226: getfield        net/minecraft/entity/boss/EntityWither.motionZ:D
        //   229: aload_0        
        //   230: getfield        net/minecraft/entity/boss/EntityWither.motionZ:D
        //   233: dmul           
        //   234: dadd           
        //   235: ldc2_w          0.05000000074505806
        //   238: dcmpl          
        //   239: ifle            266
        //   242: aload_0        
        //   243: aload_0        
        //   244: getfield        net/minecraft/entity/boss/EntityWither.motionZ:D
        //   247: aload_0        
        //   248: getfield        net/minecraft/entity/boss/EntityWither.motionX:D
        //   251: invokestatic    net/minecraft/util/MathHelper.func_181159_b:(DD)D
        //   254: d2f            
        //   255: ldc_w           57.295776
        //   258: fmul           
        //   259: ldc_w           90.0
        //   262: fsub           
        //   263: putfield        net/minecraft/entity/boss/EntityWither.rotationYaw:F
        //   266: aload_0        
        //   267: invokespecial   net/minecraft/entity/monster/EntityMob.onLivingUpdate:()V
        //   270: aload_0        
        //   271: getfield        net/minecraft/entity/boss/EntityWither.field_82218_g:[F
        //   274: iconst_0       
        //   275: aload_0        
        //   276: getfield        net/minecraft/entity/boss/EntityWither.field_82221_e:[F
        //   279: iconst_0       
        //   280: faload         
        //   281: fastore        
        //   282: aload_0        
        //   283: getfield        net/minecraft/entity/boss/EntityWither.field_82217_f:[F
        //   286: iconst_0       
        //   287: aload_0        
        //   288: getfield        net/minecraft/entity/boss/EntityWither.field_82220_d:[F
        //   291: iconst_0       
        //   292: faload         
        //   293: fastore        
        //   294: iinc            1, 1
        //   297: goto            270
        //   300: aload_0        
        //   301: iconst_1       
        //   302: invokevirtual   net/minecraft/entity/boss/EntityWither.getWatchedTargetId:(I)I
        //   305: istore_2       
        //   306: aconst_null    
        //   307: astore_3       
        //   308: goto            320
        //   311: aload_0        
        //   312: getfield        net/minecraft/entity/boss/EntityWither.worldObj:Lnet/minecraft/world/World;
        //   315: iconst_0       
        //   316: invokevirtual   net/minecraft/world/World.getEntityByID:(I)Lnet/minecraft/entity/Entity;
        //   319: astore_3       
        //   320: aload_3        
        //   321: ifnull          480
        //   324: aload_0        
        //   325: iconst_1       
        //   326: invokespecial   net/minecraft/entity/boss/EntityWither.func_82214_u:(I)D
        //   329: dstore          4
        //   331: aload_0        
        //   332: iconst_1       
        //   333: invokespecial   net/minecraft/entity/boss/EntityWither.func_82208_v:(I)D
        //   336: dstore          6
        //   338: aload_0        
        //   339: iconst_1       
        //   340: invokespecial   net/minecraft/entity/boss/EntityWither.func_82213_w:(I)D
        //   343: dstore          8
        //   345: aload_3        
        //   346: getfield        net/minecraft/entity/Entity.posX:D
        //   349: dload           4
        //   351: dsub           
        //   352: dstore          10
        //   354: aload_3        
        //   355: getfield        net/minecraft/entity/Entity.posY:D
        //   358: aload_3        
        //   359: invokevirtual   net/minecraft/entity/Entity.getEyeHeight:()F
        //   362: f2d            
        //   363: dadd           
        //   364: dload           6
        //   366: dsub           
        //   367: dstore          12
        //   369: aload_3        
        //   370: getfield        net/minecraft/entity/Entity.posZ:D
        //   373: dload           8
        //   375: dsub           
        //   376: dstore          14
        //   378: dload           10
        //   380: dload           10
        //   382: dmul           
        //   383: dload           14
        //   385: dload           14
        //   387: dmul           
        //   388: dadd           
        //   389: invokestatic    net/minecraft/util/MathHelper.sqrt_double:(D)F
        //   392: f2d            
        //   393: dstore          16
        //   395: dload           14
        //   397: dload           10
        //   399: invokestatic    net/minecraft/util/MathHelper.func_181159_b:(DD)D
        //   402: ldc2_w          180.0
        //   405: dmul           
        //   406: ldc2_w          3.141592653589793
        //   409: ddiv           
        //   410: d2f            
        //   411: ldc_w           90.0
        //   414: fsub           
        //   415: fstore          18
        //   417: dload           12
        //   419: dload           16
        //   421: invokestatic    net/minecraft/util/MathHelper.func_181159_b:(DD)D
        //   424: ldc2_w          180.0
        //   427: dmul           
        //   428: ldc2_w          3.141592653589793
        //   431: ddiv           
        //   432: dneg           
        //   433: d2f            
        //   434: fstore          19
        //   436: aload_0        
        //   437: getfield        net/minecraft/entity/boss/EntityWither.field_82220_d:[F
        //   440: iconst_0       
        //   441: aload_0        
        //   442: aload_0        
        //   443: getfield        net/minecraft/entity/boss/EntityWither.field_82220_d:[F
        //   446: iconst_0       
        //   447: faload         
        //   448: fload           19
        //   450: ldc_w           40.0
        //   453: invokespecial   net/minecraft/entity/boss/EntityWither.func_82204_b:(FFF)F
        //   456: fastore        
        //   457: aload_0        
        //   458: getfield        net/minecraft/entity/boss/EntityWither.field_82221_e:[F
        //   461: iconst_0       
        //   462: aload_0        
        //   463: aload_0        
        //   464: getfield        net/minecraft/entity/boss/EntityWither.field_82221_e:[F
        //   467: iconst_0       
        //   468: faload         
        //   469: fload           18
        //   471: ldc             10.0
        //   473: invokespecial   net/minecraft/entity/boss/EntityWither.func_82204_b:(FFF)F
        //   476: fastore        
        //   477: goto            502
        //   480: aload_0        
        //   481: getfield        net/minecraft/entity/boss/EntityWither.field_82221_e:[F
        //   484: iconst_0       
        //   485: aload_0        
        //   486: aload_0        
        //   487: getfield        net/minecraft/entity/boss/EntityWither.field_82221_e:[F
        //   490: iconst_0       
        //   491: faload         
        //   492: aload_0        
        //   493: getfield        net/minecraft/entity/boss/EntityWither.renderYawOffset:F
        //   496: ldc             10.0
        //   498: invokespecial   net/minecraft/entity/boss/EntityWither.func_82204_b:(FFF)F
        //   501: fastore        
        //   502: iinc            1, 1
        //   505: goto            300
        //   508: aload_0        
        //   509: invokevirtual   net/minecraft/entity/boss/EntityWither.isArmored:()Z
        //   512: istore_1       
        //   513: aload_0        
        //   514: iconst_0       
        //   515: invokespecial   net/minecraft/entity/boss/EntityWither.func_82214_u:(I)D
        //   518: dstore_3       
        //   519: aload_0        
        //   520: iconst_0       
        //   521: invokespecial   net/minecraft/entity/boss/EntityWither.func_82208_v:(I)D
        //   524: dstore          5
        //   526: aload_0        
        //   527: iconst_0       
        //   528: invokespecial   net/minecraft/entity/boss/EntityWither.func_82213_w:(I)D
        //   531: dstore          7
        //   533: aload_0        
        //   534: getfield        net/minecraft/entity/boss/EntityWither.worldObj:Lnet/minecraft/world/World;
        //   537: getstatic       net/minecraft/util/EnumParticleTypes.SMOKE_NORMAL:Lnet/minecraft/util/EnumParticleTypes;
        //   540: dload_3        
        //   541: aload_0        
        //   542: getfield        net/minecraft/entity/boss/EntityWither.rand:Ljava/util/Random;
        //   545: invokevirtual   java/util/Random.nextGaussian:()D
        //   548: ldc2_w          0.30000001192092896
        //   551: dmul           
        //   552: dadd           
        //   553: dload           5
        //   555: aload_0        
        //   556: getfield        net/minecraft/entity/boss/EntityWither.rand:Ljava/util/Random;
        //   559: invokevirtual   java/util/Random.nextGaussian:()D
        //   562: ldc2_w          0.30000001192092896
        //   565: dmul           
        //   566: dadd           
        //   567: dload           7
        //   569: aload_0        
        //   570: getfield        net/minecraft/entity/boss/EntityWither.rand:Ljava/util/Random;
        //   573: invokevirtual   java/util/Random.nextGaussian:()D
        //   576: ldc2_w          0.30000001192092896
        //   579: dmul           
        //   580: dadd           
        //   581: dconst_0       
        //   582: dconst_0       
        //   583: dconst_0       
        //   584: iconst_0       
        //   585: newarray        I
        //   587: invokevirtual   net/minecraft/world/World.spawnParticle:(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V
        //   590: goto            670
        //   593: aload_0        
        //   594: getfield        net/minecraft/entity/boss/EntityWither.worldObj:Lnet/minecraft/world/World;
        //   597: getfield        net/minecraft/world/World.rand:Ljava/util/Random;
        //   600: iconst_4       
        //   601: invokevirtual   java/util/Random.nextInt:(I)I
        //   604: ifne            670
        //   607: aload_0        
        //   608: getfield        net/minecraft/entity/boss/EntityWither.worldObj:Lnet/minecraft/world/World;
        //   611: getstatic       net/minecraft/util/EnumParticleTypes.SPELL_MOB:Lnet/minecraft/util/EnumParticleTypes;
        //   614: dload_3        
        //   615: aload_0        
        //   616: getfield        net/minecraft/entity/boss/EntityWither.rand:Ljava/util/Random;
        //   619: invokevirtual   java/util/Random.nextGaussian:()D
        //   622: ldc2_w          0.30000001192092896
        //   625: dmul           
        //   626: dadd           
        //   627: dload           5
        //   629: aload_0        
        //   630: getfield        net/minecraft/entity/boss/EntityWither.rand:Ljava/util/Random;
        //   633: invokevirtual   java/util/Random.nextGaussian:()D
        //   636: ldc2_w          0.30000001192092896
        //   639: dmul           
        //   640: dadd           
        //   641: dload           7
        //   643: aload_0        
        //   644: getfield        net/minecraft/entity/boss/EntityWither.rand:Ljava/util/Random;
        //   647: invokevirtual   java/util/Random.nextGaussian:()D
        //   650: ldc2_w          0.30000001192092896
        //   653: dmul           
        //   654: dadd           
        //   655: ldc2_w          0.699999988079071
        //   658: ldc2_w          0.699999988079071
        //   661: ldc2_w          0.5
        //   664: iconst_0       
        //   665: newarray        I
        //   667: invokevirtual   net/minecraft/world/World.spawnParticle:(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V
        //   670: iinc            2, 1
        //   673: goto            513
        //   676: aload_0        
        //   677: invokevirtual   net/minecraft/entity/boss/EntityWither.getInvulTime:()I
        //   680: ifle            756
        //   683: aload_0        
        //   684: getfield        net/minecraft/entity/boss/EntityWither.worldObj:Lnet/minecraft/world/World;
        //   687: getstatic       net/minecraft/util/EnumParticleTypes.SPELL_MOB:Lnet/minecraft/util/EnumParticleTypes;
        //   690: aload_0        
        //   691: getfield        net/minecraft/entity/boss/EntityWither.posX:D
        //   694: aload_0        
        //   695: getfield        net/minecraft/entity/boss/EntityWither.rand:Ljava/util/Random;
        //   698: invokevirtual   java/util/Random.nextGaussian:()D
        //   701: dconst_1       
        //   702: dmul           
        //   703: dadd           
        //   704: aload_0        
        //   705: getfield        net/minecraft/entity/boss/EntityWither.posY:D
        //   708: aload_0        
        //   709: getfield        net/minecraft/entity/boss/EntityWither.rand:Ljava/util/Random;
        //   712: invokevirtual   java/util/Random.nextFloat:()F
        //   715: ldc_w           3.3
        //   718: fmul           
        //   719: f2d            
        //   720: dadd           
        //   721: aload_0        
        //   722: getfield        net/minecraft/entity/boss/EntityWither.posZ:D
        //   725: aload_0        
        //   726: getfield        net/minecraft/entity/boss/EntityWither.rand:Ljava/util/Random;
        //   729: invokevirtual   java/util/Random.nextGaussian:()D
        //   732: dconst_1       
        //   733: dmul           
        //   734: dadd           
        //   735: ldc2_w          0.699999988079071
        //   738: ldc2_w          0.699999988079071
        //   741: ldc2_w          0.8999999761581421
        //   744: iconst_0       
        //   745: newarray        I
        //   747: invokevirtual   net/minecraft/world/World.spawnParticle:(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V
        //   750: iinc            2, 1
        //   753: goto            683
        //   756: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void updateWatchedTargetId(final int n, final int n2) {
        this.dataWatcher.updateObject(17 + n, n2);
    }
    
    @Override
    public void addPotionEffect(final PotionEffect potionEffect) {
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6000000238418579);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.setInvulTime(nbtTagCompound.getInteger("Invul"));
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.wither.idle";
    }
    
    @Override
    public void attackEntityWithRangedAttack(final EntityLivingBase entityLivingBase, final float n) {
        this.launchWitherSkullToEntity(0, entityLivingBase);
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    @Override
    public void fall(final float n, final float n2) {
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        final EntityItem dropItem = this.dropItem(Items.nether_star, 1);
        if (dropItem != null) {
            dropItem.setNoDespawn();
        }
        if (!this.worldObj.isRemote) {
            final Iterator<EntityPlayer> iterator = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().expand(50.0, 100.0, 50.0)).iterator();
            while (iterator.hasNext()) {
                iterator.next().triggerAchievement(AchievementList.killWither);
            }
        }
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.wither.death";
    }
    
    public EntityWither(final World world) {
        super(world);
        this.field_82220_d = new float[2];
        this.field_82221_e = new float[2];
        this.field_82217_f = new float[2];
        this.field_82218_g = new float[2];
        this.field_82223_h = new int[2];
        this.field_82224_i = new int[2];
        this.setHealth(this.getMaxHealth());
        this.setSize(0.9f, 3.5f);
        this.isImmuneToFire = true;
        ((PathNavigateGround)this.getNavigator()).setCanSwim(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIArrowAttack(this, 1.0, 40, 20.0f));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, false, false, EntityWither.attackEntitySelector));
        this.experienceValue = 50;
    }
    
    public void setInvulTime(final int n) {
        this.dataWatcher.updateObject(20, n);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(17, new Integer(0));
        this.dataWatcher.addObject(18, new Integer(0));
        this.dataWatcher.addObject(19, new Integer(0));
        this.dataWatcher.addObject(20, new Integer(0));
    }
    
    private double func_82213_w(final int n) {
        if (n <= 0) {
            return this.posZ;
        }
        return this.posZ + MathHelper.sin((this.renderYawOffset + 180 * (n - 1)) / 180.0f * 3.1415927f) * 1.3;
    }
    
    private float func_82204_b(final float n, final float n2, final float n3) {
        float wrapAngleTo180_float = MathHelper.wrapAngleTo180_float(n2 - n);
        if (wrapAngleTo180_float > n3) {
            wrapAngleTo180_float = n3;
        }
        if (wrapAngleTo180_float < -n3) {
            wrapAngleTo180_float = -n3;
        }
        return n + wrapAngleTo180_float;
    }
    
    @Override
    public int getTotalArmorValue() {
        return 4;
    }
}
