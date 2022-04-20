package net.minecraft.entity.boss;

import net.minecraft.entity.monster.*;
import com.google.common.collect.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;

public class EntityDragon extends EntityLiving implements IEntityMultiPart, IBossDisplayData, IMob
{
    public EntityDragonPart dragonPartTail1;
    public boolean forceNewTarget;
    public double targetX;
    public EntityDragonPart[] dragonPartArray;
    public double targetZ;
    public EntityDragonPart dragonPartTail2;
    public EntityEnderCrystal healingEnderCrystal;
    public double targetY;
    public EntityDragonPart dragonPartBody;
    public EntityDragonPart dragonPartHead;
    public float prevAnimTime;
    public double[][] ringBuffer;
    private Entity target;
    public boolean slowed;
    public int deathTicks;
    public EntityDragonPart dragonPartTail3;
    public int ringBufferIndex;
    public EntityDragonPart dragonPartWing2;
    public float animTime;
    public EntityDragonPart dragonPartWing1;
    
    @Override
    public boolean canBeCollidedWith() {
        return false;
    }
    
    private void updateDragonEnderCrystal() {
        if (this.healingEnderCrystal != null) {
            if (this.healingEnderCrystal.isDead) {
                if (!this.worldObj.isRemote) {
                    this.attackEntityFromPart(this.dragonPartHead, DamageSource.setExplosionSource(null), 10.0f);
                }
                this.healingEnderCrystal = null;
            }
            else if (this.ticksExisted % 10 == 0 && this.getHealth() < this.getMaxHealth()) {
                this.setHealth(this.getHealth() + 1.0f);
            }
        }
        if (this.rand.nextInt(10) == 0) {
            final float n = 32.0f;
            final List entitiesWithinAABB = this.worldObj.getEntitiesWithinAABB(EntityEnderCrystal.class, this.getEntityBoundingBox().expand(n, n, n));
            EntityEnderCrystal healingEnderCrystal = null;
            double n2 = Double.MAX_VALUE;
            for (final EntityEnderCrystal entityEnderCrystal : entitiesWithinAABB) {
                final double distanceSqToEntity = entityEnderCrystal.getDistanceSqToEntity(this);
                if (distanceSqToEntity < n2) {
                    n2 = distanceSqToEntity;
                    healingEnderCrystal = entityEnderCrystal;
                }
            }
            this.healingEnderCrystal = healingEnderCrystal;
        }
    }
    
    @Override
    protected void despawnEntity() {
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
    }
    
    private void collideWithEntities(final List list) {
        final double n = (this.dragonPartBody.getEntityBoundingBox().minX + this.dragonPartBody.getEntityBoundingBox().maxX) / 2.0;
        final double n2 = (this.dragonPartBody.getEntityBoundingBox().minZ + this.dragonPartBody.getEntityBoundingBox().maxZ) / 2.0;
        for (final Entity entity : list) {
            if (entity instanceof EntityLivingBase) {
                final double n3 = entity.posX - n;
                final double n4 = entity.posZ - n2;
                final double n5 = n3 * n3 + n4 * n4;
                entity.addVelocity(n3 / n5 * 4.0, 0.20000000298023224, n4 / n5 * 4.0);
            }
        }
    }
    
    private float simplifyAngle(final double n) {
        return (float)MathHelper.wrapAngleTo180_double(n);
    }
    
    private void setNewTarget() {
        this.forceNewTarget = false;
        final ArrayList arrayList = Lists.newArrayList((Iterable)this.worldObj.playerEntities);
        final Iterator<EntityPlayer> iterator = (Iterator<EntityPlayer>)arrayList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isSpectator()) {
                iterator.remove();
            }
        }
        if (this.rand.nextInt(2) == 0 && !arrayList.isEmpty()) {
            this.target = (EntityPlayer)arrayList.get(this.rand.nextInt(arrayList.size()));
        }
        else {
            double n;
            double n2;
            double n3;
            do {
                this.targetX = 0.0;
                this.targetY = 70.0f + this.rand.nextFloat() * 50.0f;
                this.targetZ = 0.0;
                this.targetX += this.rand.nextFloat() * 120.0f - 60.0f;
                this.targetZ += this.rand.nextFloat() * 120.0f - 60.0f;
                n = this.posX - this.targetX;
                n2 = this.posY - this.targetY;
                n3 = this.posZ - this.targetZ;
            } while (n * n + n2 * n2 + n3 * n3 <= 100.0);
            this.target = null;
        }
    }
    
    public double[] getMovementOffsets(final int n, float n2) {
        if (this.getHealth() <= 0.0f) {
            n2 = 0.0f;
        }
        n2 = 1.0f - n2;
        final int n3 = this.ringBufferIndex - n * 1 & 0x3F;
        final int n4 = this.ringBufferIndex - n * 1 - 1 & 0x3F;
        final double[] array = new double[3];
        final double n5 = this.ringBuffer[n3][0];
        array[0] = n5 + MathHelper.wrapAngleTo180_double(this.ringBuffer[n4][0] - n5) * n2;
        final double n6 = this.ringBuffer[n3][1];
        array[1] = n6 + (this.ringBuffer[n4][1] - n6) * n2;
        array[2] = this.ringBuffer[n3][2] + (this.ringBuffer[n4][2] - this.ringBuffer[n3][2]) * n2;
        return array;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (damageSource instanceof EntityDamageSource && ((EntityDamageSource)damageSource).getIsThornsDamage()) {
            this.attackDragonFrom(damageSource, n);
        }
        return false;
    }
    
    private boolean destroyBlocksInAABB(final AxisAlignedBB axisAlignedBB) {
        final int floor_double = MathHelper.floor_double(axisAlignedBB.minX);
        final int floor_double2 = MathHelper.floor_double(axisAlignedBB.minY);
        final int floor_double3 = MathHelper.floor_double(axisAlignedBB.minZ);
        final int floor_double4 = MathHelper.floor_double(axisAlignedBB.maxX);
        final int floor_double5 = MathHelper.floor_double(axisAlignedBB.maxY);
        final int floor_double6 = MathHelper.floor_double(axisAlignedBB.maxZ);
        for (int i = floor_double; i <= floor_double4; ++i) {
            for (int j = floor_double2; j <= floor_double5; ++j) {
                for (int k = floor_double3; k <= floor_double6; ++k) {
                    final BlockPos blockToAir = new BlockPos(i, j, k);
                    final Block block = this.worldObj.getBlockState(blockToAir).getBlock();
                    if (block.getMaterial() != Material.air && block != Blocks.barrier && block != Blocks.obsidian && block != Blocks.end_stone && block != Blocks.bedrock && block != Blocks.command_block && this.worldObj.getGameRules().getBoolean("mobGriefing")) {
                        if (!this.worldObj.setBlockToAir(blockToAir)) {}
                    }
                }
            }
        }
        return true;
    }
    
    private void generatePortal(final BlockPos blockPos) {
        while (true) {
            final double n = 32;
            if (n <= 12.25) {
                final BlockPos add = blockPos.add(-4, -1, -4);
                if (n <= 6.25) {
                    this.worldObj.setBlockState(add, Blocks.bedrock.getDefaultState());
                }
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    public Entity[] getParts() {
        return this.dragonPartArray;
    }
    
    @Override
    public boolean attackEntityFromPart(final EntityDragonPart entityDragonPart, final DamageSource damageSource, float n) {
        if (entityDragonPart != this.dragonPartHead) {
            n = n / 4.0f + 1.0f;
        }
        final float n2 = this.rotationYaw * 3.1415927f / 180.0f;
        final float sin = MathHelper.sin(n2);
        final float cos = MathHelper.cos(n2);
        this.targetX = this.posX + sin * 5.0f + (this.rand.nextFloat() - 0.5f) * 2.0f;
        this.targetY = this.posY + this.rand.nextFloat() * 3.0f + 1.0;
        this.targetZ = this.posZ - cos * 5.0f + (this.rand.nextFloat() - 0.5f) * 2.0f;
        this.target = null;
        if (damageSource.getEntity() instanceof EntityPlayer || damageSource.isExplosion()) {
            this.attackDragonFrom(damageSource, n);
        }
        return true;
    }
    
    @Override
    protected void onDeathUpdate() {
        ++this.deathTicks;
        if (this.deathTicks >= 180 && this.deathTicks <= 200) {
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX + (this.rand.nextFloat() - 0.5f) * 8.0f, this.posY + 2.0 + (this.rand.nextFloat() - 0.5f) * 4.0f, this.posZ + (this.rand.nextFloat() - 0.5f) * 8.0f, 0.0, 0.0, 0.0, new int[0]);
        }
        final boolean boolean1 = this.worldObj.getGameRules().getBoolean("doMobLoot");
        if (!this.worldObj.isRemote) {
            if (this.deathTicks > 150 && this.deathTicks % 5 == 0 && boolean1) {
                while (true) {
                    this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, EntityXPOrb.getXPSplit(2000)));
                }
            }
            else if (this.deathTicks == 1) {
                this.worldObj.playBroadcastSound(1018, new BlockPos(this), 0);
            }
        }
        this.moveEntity(0.0, 0.10000000149011612, 0.0);
        final float n = this.rotationYaw + 20.0f;
        this.rotationYaw = n;
        this.renderYawOffset = n;
        if (this.deathTicks == 200 && !this.worldObj.isRemote) {
            if (boolean1) {
                while (true) {
                    this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, EntityXPOrb.getXPSplit(2000)));
                }
            }
            else {
                this.generatePortal(new BlockPos(this.posX, 64.0, this.posZ));
                this.setDead();
            }
        }
    }
    
    @Override
    public void onKillCommand() {
        this.setDead();
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.enderdragon.growl";
    }
    
    protected boolean attackDragonFrom(final DamageSource damageSource, final float n) {
        return super.attackEntityFrom(damageSource, n);
    }
    
    private void attackEntitiesInList(final List list) {
        while (0 < list.size()) {
            final Entity entity = list.get(0);
            if (entity instanceof EntityLivingBase) {
                entity.attackEntityFrom(DamageSource.causeMobDamage(this), 10.0f);
                this.applyEnchantments(this, entity);
            }
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public World getWorld() {
        return this.worldObj;
    }
    
    @Override
    protected float getSoundVolume() {
        return 5.0f;
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.enderdragon.hit";
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0);
    }
    
    public EntityDragon(final World world) {
        super(world);
        this.ringBuffer = new double[64][3];
        this.ringBufferIndex = -1;
        this.dragonPartArray = new EntityDragonPart[] { this.dragonPartHead = new EntityDragonPart(this, "head", 6.0f, 6.0f), this.dragonPartBody = new EntityDragonPart(this, "body", 8.0f, 8.0f), this.dragonPartTail1 = new EntityDragonPart(this, "tail", 4.0f, 4.0f), this.dragonPartTail2 = new EntityDragonPart(this, "tail", 4.0f, 4.0f), this.dragonPartTail3 = new EntityDragonPart(this, "tail", 4.0f, 4.0f), this.dragonPartWing1 = new EntityDragonPart(this, "wing", 4.0f, 4.0f), this.dragonPartWing2 = new EntityDragonPart(this, "wing", 4.0f, 4.0f) };
        this.setHealth(this.getMaxHealth());
        this.setSize(16.0f, 8.0f);
        this.noClip = true;
        this.isImmuneToFire = true;
        this.targetY = 100.0;
        this.ignoreFrustumCheck = true;
    }
    
    @Override
    public void onLivingUpdate() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/entity/boss/EntityDragon.worldObj:Lnet/minecraft/world/World;
        //     4: getfield        net/minecraft/world/World.isRemote:Z
        //     7: ifeq            102
        //    10: aload_0        
        //    11: getfield        net/minecraft/entity/boss/EntityDragon.animTime:F
        //    14: ldc_w           3.1415927
        //    17: fmul           
        //    18: fconst_2       
        //    19: fmul           
        //    20: invokestatic    net/minecraft/util/MathHelper.cos:(F)F
        //    23: fstore_1       
        //    24: aload_0        
        //    25: getfield        net/minecraft/entity/boss/EntityDragon.prevAnimTime:F
        //    28: ldc_w           3.1415927
        //    31: fmul           
        //    32: fconst_2       
        //    33: fmul           
        //    34: invokestatic    net/minecraft/util/MathHelper.cos:(F)F
        //    37: fstore_2       
        //    38: fload_2        
        //    39: ldc_w           -0.3
        //    42: fcmpg          
        //    43: ifgt            102
        //    46: fload_1        
        //    47: ldc_w           -0.3
        //    50: fcmpl          
        //    51: iflt            102
        //    54: aload_0        
        //    55: invokevirtual   net/minecraft/entity/boss/EntityDragon.isSilent:()Z
        //    58: ifne            102
        //    61: aload_0        
        //    62: getfield        net/minecraft/entity/boss/EntityDragon.worldObj:Lnet/minecraft/world/World;
        //    65: aload_0        
        //    66: getfield        net/minecraft/entity/boss/EntityDragon.posX:D
        //    69: aload_0        
        //    70: getfield        net/minecraft/entity/boss/EntityDragon.posY:D
        //    73: aload_0        
        //    74: getfield        net/minecraft/entity/boss/EntityDragon.posZ:D
        //    77: ldc_w           "mob.enderdragon.wings"
        //    80: ldc_w           5.0
        //    83: ldc_w           0.8
        //    86: aload_0        
        //    87: getfield        net/minecraft/entity/boss/EntityDragon.rand:Ljava/util/Random;
        //    90: invokevirtual   java/util/Random.nextFloat:()F
        //    93: ldc_w           0.3
        //    96: fmul           
        //    97: fadd           
        //    98: iconst_0       
        //    99: invokevirtual   net/minecraft/world/World.playSound:(DDDLjava/lang/String;FFZ)V
        //   102: aload_0        
        //   103: aload_0        
        //   104: getfield        net/minecraft/entity/boss/EntityDragon.animTime:F
        //   107: putfield        net/minecraft/entity/boss/EntityDragon.prevAnimTime:F
        //   110: aload_0        
        //   111: invokevirtual   net/minecraft/entity/boss/EntityDragon.getHealth:()F
        //   114: fconst_0       
        //   115: fcmpg          
        //   116: ifgt            211
        //   119: aload_0        
        //   120: getfield        net/minecraft/entity/boss/EntityDragon.rand:Ljava/util/Random;
        //   123: invokevirtual   java/util/Random.nextFloat:()F
        //   126: ldc_w           0.5
        //   129: fsub           
        //   130: ldc_w           8.0
        //   133: fmul           
        //   134: fstore_1       
        //   135: aload_0        
        //   136: getfield        net/minecraft/entity/boss/EntityDragon.rand:Ljava/util/Random;
        //   139: invokevirtual   java/util/Random.nextFloat:()F
        //   142: ldc_w           0.5
        //   145: fsub           
        //   146: ldc_w           4.0
        //   149: fmul           
        //   150: fstore_2       
        //   151: aload_0        
        //   152: getfield        net/minecraft/entity/boss/EntityDragon.rand:Ljava/util/Random;
        //   155: invokevirtual   java/util/Random.nextFloat:()F
        //   158: ldc_w           0.5
        //   161: fsub           
        //   162: ldc_w           8.0
        //   165: fmul           
        //   166: fstore_3       
        //   167: aload_0        
        //   168: getfield        net/minecraft/entity/boss/EntityDragon.worldObj:Lnet/minecraft/world/World;
        //   171: getstatic       net/minecraft/util/EnumParticleTypes.EXPLOSION_LARGE:Lnet/minecraft/util/EnumParticleTypes;
        //   174: aload_0        
        //   175: getfield        net/minecraft/entity/boss/EntityDragon.posX:D
        //   178: fload_1        
        //   179: f2d            
        //   180: dadd           
        //   181: aload_0        
        //   182: getfield        net/minecraft/entity/boss/EntityDragon.posY:D
        //   185: ldc2_w          2.0
        //   188: dadd           
        //   189: fload_2        
        //   190: f2d            
        //   191: dadd           
        //   192: aload_0        
        //   193: getfield        net/minecraft/entity/boss/EntityDragon.posZ:D
        //   196: fload_3        
        //   197: f2d            
        //   198: dadd           
        //   199: dconst_0       
        //   200: dconst_0       
        //   201: dconst_0       
        //   202: iconst_0       
        //   203: newarray        I
        //   205: invokevirtual   net/minecraft/world/World.spawnParticle:(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V
        //   208: goto            2214
        //   211: aload_0        
        //   212: invokespecial   net/minecraft/entity/boss/EntityDragon.updateDragonEnderCrystal:()V
        //   215: ldc_w           0.2
        //   218: aload_0        
        //   219: getfield        net/minecraft/entity/boss/EntityDragon.motionX:D
        //   222: aload_0        
        //   223: getfield        net/minecraft/entity/boss/EntityDragon.motionX:D
        //   226: dmul           
        //   227: aload_0        
        //   228: getfield        net/minecraft/entity/boss/EntityDragon.motionZ:D
        //   231: aload_0        
        //   232: getfield        net/minecraft/entity/boss/EntityDragon.motionZ:D
        //   235: dmul           
        //   236: dadd           
        //   237: invokestatic    net/minecraft/util/MathHelper.sqrt_double:(D)F
        //   240: ldc             10.0
        //   242: fmul           
        //   243: fconst_1       
        //   244: fadd           
        //   245: fdiv           
        //   246: fstore_1       
        //   247: fload_1        
        //   248: ldc2_w          2.0
        //   251: aload_0        
        //   252: getfield        net/minecraft/entity/boss/EntityDragon.motionY:D
        //   255: invokestatic    java/lang/Math.pow:(DD)D
        //   258: d2f            
        //   259: fmul           
        //   260: fstore_1       
        //   261: aload_0        
        //   262: getfield        net/minecraft/entity/boss/EntityDragon.slowed:Z
        //   265: ifeq            285
        //   268: aload_0        
        //   269: dup            
        //   270: getfield        net/minecraft/entity/boss/EntityDragon.animTime:F
        //   273: fload_1        
        //   274: ldc_w           0.5
        //   277: fmul           
        //   278: fadd           
        //   279: putfield        net/minecraft/entity/boss/EntityDragon.animTime:F
        //   282: goto            295
        //   285: aload_0        
        //   286: dup            
        //   287: getfield        net/minecraft/entity/boss/EntityDragon.animTime:F
        //   290: fload_1        
        //   291: fadd           
        //   292: putfield        net/minecraft/entity/boss/EntityDragon.animTime:F
        //   295: aload_0        
        //   296: aload_0        
        //   297: getfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //   300: invokestatic    net/minecraft/util/MathHelper.wrapAngleTo180_float:(F)F
        //   303: putfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //   306: aload_0        
        //   307: invokevirtual   net/minecraft/entity/boss/EntityDragon.isAIDisabled:()Z
        //   310: ifeq            323
        //   313: aload_0        
        //   314: ldc_w           0.5
        //   317: putfield        net/minecraft/entity/boss/EntityDragon.animTime:F
        //   320: goto            2214
        //   323: aload_0        
        //   324: getfield        net/minecraft/entity/boss/EntityDragon.ringBufferIndex:I
        //   327: ifge            370
        //   330: iconst_0       
        //   331: aload_0        
        //   332: getfield        net/minecraft/entity/boss/EntityDragon.ringBuffer:[[D
        //   335: arraylength    
        //   336: if_icmpge       370
        //   339: aload_0        
        //   340: getfield        net/minecraft/entity/boss/EntityDragon.ringBuffer:[[D
        //   343: iconst_0       
        //   344: aaload         
        //   345: iconst_0       
        //   346: aload_0        
        //   347: getfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //   350: f2d            
        //   351: dastore        
        //   352: aload_0        
        //   353: getfield        net/minecraft/entity/boss/EntityDragon.ringBuffer:[[D
        //   356: iconst_0       
        //   357: aaload         
        //   358: iconst_1       
        //   359: aload_0        
        //   360: getfield        net/minecraft/entity/boss/EntityDragon.posY:D
        //   363: dastore        
        //   364: iinc            2, 1
        //   367: goto            330
        //   370: aload_0        
        //   371: dup            
        //   372: getfield        net/minecraft/entity/boss/EntityDragon.ringBufferIndex:I
        //   375: iconst_1       
        //   376: iadd           
        //   377: dup_x1         
        //   378: putfield        net/minecraft/entity/boss/EntityDragon.ringBufferIndex:I
        //   381: aload_0        
        //   382: getfield        net/minecraft/entity/boss/EntityDragon.ringBuffer:[[D
        //   385: arraylength    
        //   386: if_icmpne       394
        //   389: aload_0        
        //   390: iconst_0       
        //   391: putfield        net/minecraft/entity/boss/EntityDragon.ringBufferIndex:I
        //   394: aload_0        
        //   395: getfield        net/minecraft/entity/boss/EntityDragon.ringBuffer:[[D
        //   398: aload_0        
        //   399: getfield        net/minecraft/entity/boss/EntityDragon.ringBufferIndex:I
        //   402: aaload         
        //   403: iconst_0       
        //   404: aload_0        
        //   405: getfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //   408: f2d            
        //   409: dastore        
        //   410: aload_0        
        //   411: getfield        net/minecraft/entity/boss/EntityDragon.ringBuffer:[[D
        //   414: aload_0        
        //   415: getfield        net/minecraft/entity/boss/EntityDragon.ringBufferIndex:I
        //   418: aaload         
        //   419: iconst_1       
        //   420: aload_0        
        //   421: getfield        net/minecraft/entity/boss/EntityDragon.posY:D
        //   424: dastore        
        //   425: aload_0        
        //   426: getfield        net/minecraft/entity/boss/EntityDragon.worldObj:Lnet/minecraft/world/World;
        //   429: getfield        net/minecraft/world/World.isRemote:Z
        //   432: ifeq            602
        //   435: aload_0        
        //   436: getfield        net/minecraft/entity/boss/EntityDragon.newPosRotationIncrements:I
        //   439: ifle            1393
        //   442: aload_0        
        //   443: getfield        net/minecraft/entity/boss/EntityDragon.posX:D
        //   446: aload_0        
        //   447: getfield        net/minecraft/entity/boss/EntityDragon.newPosX:D
        //   450: aload_0        
        //   451: getfield        net/minecraft/entity/boss/EntityDragon.posX:D
        //   454: dsub           
        //   455: aload_0        
        //   456: getfield        net/minecraft/entity/boss/EntityDragon.newPosRotationIncrements:I
        //   459: i2d            
        //   460: ddiv           
        //   461: dadd           
        //   462: dstore_2       
        //   463: aload_0        
        //   464: getfield        net/minecraft/entity/boss/EntityDragon.posY:D
        //   467: aload_0        
        //   468: getfield        net/minecraft/entity/boss/EntityDragon.newPosY:D
        //   471: aload_0        
        //   472: getfield        net/minecraft/entity/boss/EntityDragon.posY:D
        //   475: dsub           
        //   476: aload_0        
        //   477: getfield        net/minecraft/entity/boss/EntityDragon.newPosRotationIncrements:I
        //   480: i2d            
        //   481: ddiv           
        //   482: dadd           
        //   483: dstore          4
        //   485: aload_0        
        //   486: getfield        net/minecraft/entity/boss/EntityDragon.posZ:D
        //   489: aload_0        
        //   490: getfield        net/minecraft/entity/boss/EntityDragon.newPosZ:D
        //   493: aload_0        
        //   494: getfield        net/minecraft/entity/boss/EntityDragon.posZ:D
        //   497: dsub           
        //   498: aload_0        
        //   499: getfield        net/minecraft/entity/boss/EntityDragon.newPosRotationIncrements:I
        //   502: i2d            
        //   503: ddiv           
        //   504: dadd           
        //   505: dstore          6
        //   507: aload_0        
        //   508: getfield        net/minecraft/entity/boss/EntityDragon.newRotationYaw:D
        //   511: aload_0        
        //   512: getfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //   515: f2d            
        //   516: dsub           
        //   517: invokestatic    net/minecraft/util/MathHelper.wrapAngleTo180_double:(D)D
        //   520: dstore          8
        //   522: aload_0        
        //   523: aload_0        
        //   524: getfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //   527: f2d            
        //   528: dload           8
        //   530: aload_0        
        //   531: getfield        net/minecraft/entity/boss/EntityDragon.newPosRotationIncrements:I
        //   534: i2d            
        //   535: ddiv           
        //   536: dadd           
        //   537: d2f            
        //   538: putfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //   541: aload_0        
        //   542: aload_0        
        //   543: getfield        net/minecraft/entity/boss/EntityDragon.rotationPitch:F
        //   546: f2d            
        //   547: aload_0        
        //   548: getfield        net/minecraft/entity/boss/EntityDragon.newRotationPitch:D
        //   551: aload_0        
        //   552: getfield        net/minecraft/entity/boss/EntityDragon.rotationPitch:F
        //   555: f2d            
        //   556: dsub           
        //   557: aload_0        
        //   558: getfield        net/minecraft/entity/boss/EntityDragon.newPosRotationIncrements:I
        //   561: i2d            
        //   562: ddiv           
        //   563: dadd           
        //   564: d2f            
        //   565: putfield        net/minecraft/entity/boss/EntityDragon.rotationPitch:F
        //   568: aload_0        
        //   569: dup            
        //   570: getfield        net/minecraft/entity/boss/EntityDragon.newPosRotationIncrements:I
        //   573: iconst_1       
        //   574: isub           
        //   575: putfield        net/minecraft/entity/boss/EntityDragon.newPosRotationIncrements:I
        //   578: aload_0        
        //   579: dload_2        
        //   580: dload           4
        //   582: dload           6
        //   584: invokevirtual   net/minecraft/entity/boss/EntityDragon.setPosition:(DDD)V
        //   587: aload_0        
        //   588: aload_0        
        //   589: getfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //   592: aload_0        
        //   593: getfield        net/minecraft/entity/boss/EntityDragon.rotationPitch:F
        //   596: invokevirtual   net/minecraft/entity/boss/EntityDragon.setRotation:(FF)V
        //   599: goto            1393
        //   602: aload_0        
        //   603: getfield        net/minecraft/entity/boss/EntityDragon.targetX:D
        //   606: aload_0        
        //   607: getfield        net/minecraft/entity/boss/EntityDragon.posX:D
        //   610: dsub           
        //   611: dstore_2       
        //   612: aload_0        
        //   613: getfield        net/minecraft/entity/boss/EntityDragon.targetY:D
        //   616: aload_0        
        //   617: getfield        net/minecraft/entity/boss/EntityDragon.posY:D
        //   620: dsub           
        //   621: dstore          4
        //   623: aload_0        
        //   624: getfield        net/minecraft/entity/boss/EntityDragon.targetZ:D
        //   627: aload_0        
        //   628: getfield        net/minecraft/entity/boss/EntityDragon.posZ:D
        //   631: dsub           
        //   632: dstore          6
        //   634: dload_2        
        //   635: dload_2        
        //   636: dmul           
        //   637: dload           4
        //   639: dload           4
        //   641: dmul           
        //   642: dadd           
        //   643: dload           6
        //   645: dload           6
        //   647: dmul           
        //   648: dadd           
        //   649: dstore          8
        //   651: aload_0        
        //   652: getfield        net/minecraft/entity/boss/EntityDragon.target:Lnet/minecraft/entity/Entity;
        //   655: ifnull          766
        //   658: aload_0        
        //   659: aload_0        
        //   660: getfield        net/minecraft/entity/boss/EntityDragon.target:Lnet/minecraft/entity/Entity;
        //   663: getfield        net/minecraft/entity/Entity.posX:D
        //   666: putfield        net/minecraft/entity/boss/EntityDragon.targetX:D
        //   669: aload_0        
        //   670: aload_0        
        //   671: getfield        net/minecraft/entity/boss/EntityDragon.target:Lnet/minecraft/entity/Entity;
        //   674: getfield        net/minecraft/entity/Entity.posZ:D
        //   677: putfield        net/minecraft/entity/boss/EntityDragon.targetZ:D
        //   680: aload_0        
        //   681: getfield        net/minecraft/entity/boss/EntityDragon.targetX:D
        //   684: aload_0        
        //   685: getfield        net/minecraft/entity/boss/EntityDragon.posX:D
        //   688: dsub           
        //   689: dstore          10
        //   691: aload_0        
        //   692: getfield        net/minecraft/entity/boss/EntityDragon.targetZ:D
        //   695: aload_0        
        //   696: getfield        net/minecraft/entity/boss/EntityDragon.posZ:D
        //   699: dsub           
        //   700: dstore          12
        //   702: dload           10
        //   704: dload           10
        //   706: dmul           
        //   707: dload           12
        //   709: dload           12
        //   711: dmul           
        //   712: dadd           
        //   713: invokestatic    java/lang/Math.sqrt:(D)D
        //   716: dstore          14
        //   718: ldc2_w          0.4000000059604645
        //   721: dload           14
        //   723: ldc2_w          80.0
        //   726: ddiv           
        //   727: dadd           
        //   728: dconst_1       
        //   729: dsub           
        //   730: dstore          16
        //   732: dload           16
        //   734: ldc2_w          10.0
        //   737: dcmpl          
        //   738: ifle            746
        //   741: ldc2_w          10.0
        //   744: dstore          16
        //   746: aload_0        
        //   747: aload_0        
        //   748: getfield        net/minecraft/entity/boss/EntityDragon.target:Lnet/minecraft/entity/Entity;
        //   751: invokevirtual   net/minecraft/entity/Entity.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //   754: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //   757: dload           16
        //   759: dadd           
        //   760: putfield        net/minecraft/entity/boss/EntityDragon.targetY:D
        //   763: goto            806
        //   766: aload_0        
        //   767: dup            
        //   768: getfield        net/minecraft/entity/boss/EntityDragon.targetX:D
        //   771: aload_0        
        //   772: getfield        net/minecraft/entity/boss/EntityDragon.rand:Ljava/util/Random;
        //   775: invokevirtual   java/util/Random.nextGaussian:()D
        //   778: ldc2_w          2.0
        //   781: dmul           
        //   782: dadd           
        //   783: putfield        net/minecraft/entity/boss/EntityDragon.targetX:D
        //   786: aload_0        
        //   787: dup            
        //   788: getfield        net/minecraft/entity/boss/EntityDragon.targetZ:D
        //   791: aload_0        
        //   792: getfield        net/minecraft/entity/boss/EntityDragon.rand:Ljava/util/Random;
        //   795: invokevirtual   java/util/Random.nextGaussian:()D
        //   798: ldc2_w          2.0
        //   801: dmul           
        //   802: dadd           
        //   803: putfield        net/minecraft/entity/boss/EntityDragon.targetZ:D
        //   806: aload_0        
        //   807: getfield        net/minecraft/entity/boss/EntityDragon.forceNewTarget:Z
        //   810: ifne            845
        //   813: dload           8
        //   815: ldc2_w          100.0
        //   818: dcmpg          
        //   819: iflt            845
        //   822: dload           8
        //   824: ldc2_w          22500.0
        //   827: dcmpl          
        //   828: ifgt            845
        //   831: aload_0        
        //   832: getfield        net/minecraft/entity/boss/EntityDragon.isCollidedHorizontally:Z
        //   835: ifne            845
        //   838: aload_0        
        //   839: getfield        net/minecraft/entity/boss/EntityDragon.isCollidedVertically:Z
        //   842: ifeq            849
        //   845: aload_0        
        //   846: invokespecial   net/minecraft/entity/boss/EntityDragon.setNewTarget:()V
        //   849: dload           4
        //   851: dload_2        
        //   852: dload_2        
        //   853: dmul           
        //   854: dload           6
        //   856: dload           6
        //   858: dmul           
        //   859: dadd           
        //   860: invokestatic    net/minecraft/util/MathHelper.sqrt_double:(D)F
        //   863: f2d            
        //   864: ddiv           
        //   865: dstore          4
        //   867: ldc_w           0.6
        //   870: fstore          10
        //   872: dload           4
        //   874: fload           10
        //   876: fneg           
        //   877: f2d            
        //   878: fload           10
        //   880: f2d            
        //   881: invokestatic    net/minecraft/util/MathHelper.clamp_double:(DDD)D
        //   884: dstore          4
        //   886: aload_0        
        //   887: dup            
        //   888: getfield        net/minecraft/entity/boss/EntityDragon.motionY:D
        //   891: dload           4
        //   893: ldc2_w          0.10000000149011612
        //   896: dmul           
        //   897: dadd           
        //   898: putfield        net/minecraft/entity/boss/EntityDragon.motionY:D
        //   901: aload_0        
        //   902: aload_0        
        //   903: getfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //   906: invokestatic    net/minecraft/util/MathHelper.wrapAngleTo180_float:(F)F
        //   909: putfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //   912: ldc2_w          180.0
        //   915: dload_2        
        //   916: dload           6
        //   918: invokestatic    net/minecraft/util/MathHelper.func_181159_b:(DD)D
        //   921: ldc2_w          180.0
        //   924: dmul           
        //   925: ldc2_w          3.141592653589793
        //   928: ddiv           
        //   929: dsub           
        //   930: dstore          11
        //   932: dload           11
        //   934: aload_0        
        //   935: getfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //   938: f2d            
        //   939: dsub           
        //   940: invokestatic    net/minecraft/util/MathHelper.wrapAngleTo180_double:(D)D
        //   943: dstore          13
        //   945: dload           13
        //   947: ldc2_w          50.0
        //   950: dcmpl          
        //   951: ifle            959
        //   954: ldc2_w          50.0
        //   957: dstore          13
        //   959: dload           13
        //   961: ldc2_w          -50.0
        //   964: dcmpg          
        //   965: ifge            973
        //   968: ldc2_w          -50.0
        //   971: dstore          13
        //   973: new             Lnet/minecraft/util/Vec3;
        //   976: dup            
        //   977: aload_0        
        //   978: getfield        net/minecraft/entity/boss/EntityDragon.targetX:D
        //   981: aload_0        
        //   982: getfield        net/minecraft/entity/boss/EntityDragon.posX:D
        //   985: dsub           
        //   986: aload_0        
        //   987: getfield        net/minecraft/entity/boss/EntityDragon.targetY:D
        //   990: aload_0        
        //   991: getfield        net/minecraft/entity/boss/EntityDragon.posY:D
        //   994: dsub           
        //   995: aload_0        
        //   996: getfield        net/minecraft/entity/boss/EntityDragon.targetZ:D
        //   999: aload_0        
        //  1000: getfield        net/minecraft/entity/boss/EntityDragon.posZ:D
        //  1003: dsub           
        //  1004: invokespecial   net/minecraft/util/Vec3.<init>:(DDD)V
        //  1007: invokevirtual   net/minecraft/util/Vec3.normalize:()Lnet/minecraft/util/Vec3;
        //  1010: astore          15
        //  1012: aload_0        
        //  1013: getfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //  1016: ldc_w           3.1415927
        //  1019: fmul           
        //  1020: ldc_w           180.0
        //  1023: fdiv           
        //  1024: invokestatic    net/minecraft/util/MathHelper.cos:(F)F
        //  1027: fneg           
        //  1028: f2d            
        //  1029: dstore          16
        //  1031: new             Lnet/minecraft/util/Vec3;
        //  1034: dup            
        //  1035: aload_0        
        //  1036: getfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //  1039: ldc_w           3.1415927
        //  1042: fmul           
        //  1043: ldc_w           180.0
        //  1046: fdiv           
        //  1047: invokestatic    net/minecraft/util/MathHelper.sin:(F)F
        //  1050: f2d            
        //  1051: aload_0        
        //  1052: getfield        net/minecraft/entity/boss/EntityDragon.motionY:D
        //  1055: dload           16
        //  1057: invokespecial   net/minecraft/util/Vec3.<init>:(DDD)V
        //  1060: invokevirtual   net/minecraft/util/Vec3.normalize:()Lnet/minecraft/util/Vec3;
        //  1063: astore          18
        //  1065: aload           18
        //  1067: aload           15
        //  1069: invokevirtual   net/minecraft/util/Vec3.dotProduct:(Lnet/minecraft/util/Vec3;)D
        //  1072: d2f            
        //  1073: ldc_w           0.5
        //  1076: fadd           
        //  1077: ldc_w           1.5
        //  1080: fdiv           
        //  1081: fstore          19
        //  1083: fload           19
        //  1085: fconst_0       
        //  1086: fcmpg          
        //  1087: ifge            1093
        //  1090: fconst_0       
        //  1091: fstore          19
        //  1093: aload_0        
        //  1094: dup            
        //  1095: getfield        net/minecraft/entity/boss/EntityDragon.randomYawVelocity:F
        //  1098: ldc_w           0.8
        //  1101: fmul           
        //  1102: putfield        net/minecraft/entity/boss/EntityDragon.randomYawVelocity:F
        //  1105: aload_0        
        //  1106: getfield        net/minecraft/entity/boss/EntityDragon.motionX:D
        //  1109: aload_0        
        //  1110: getfield        net/minecraft/entity/boss/EntityDragon.motionX:D
        //  1113: dmul           
        //  1114: aload_0        
        //  1115: getfield        net/minecraft/entity/boss/EntityDragon.motionZ:D
        //  1118: aload_0        
        //  1119: getfield        net/minecraft/entity/boss/EntityDragon.motionZ:D
        //  1122: dmul           
        //  1123: dadd           
        //  1124: invokestatic    net/minecraft/util/MathHelper.sqrt_double:(D)F
        //  1127: fconst_1       
        //  1128: fmul           
        //  1129: fconst_1       
        //  1130: fadd           
        //  1131: fstore          20
        //  1133: aload_0        
        //  1134: getfield        net/minecraft/entity/boss/EntityDragon.motionX:D
        //  1137: aload_0        
        //  1138: getfield        net/minecraft/entity/boss/EntityDragon.motionX:D
        //  1141: dmul           
        //  1142: aload_0        
        //  1143: getfield        net/minecraft/entity/boss/EntityDragon.motionZ:D
        //  1146: aload_0        
        //  1147: getfield        net/minecraft/entity/boss/EntityDragon.motionZ:D
        //  1150: dmul           
        //  1151: dadd           
        //  1152: invokestatic    java/lang/Math.sqrt:(D)D
        //  1155: dconst_1       
        //  1156: dmul           
        //  1157: dconst_1       
        //  1158: dadd           
        //  1159: dstore          21
        //  1161: dload           21
        //  1163: ldc2_w          40.0
        //  1166: dcmpl          
        //  1167: ifle            1175
        //  1170: ldc2_w          40.0
        //  1173: dstore          21
        //  1175: aload_0        
        //  1176: aload_0        
        //  1177: getfield        net/minecraft/entity/boss/EntityDragon.randomYawVelocity:F
        //  1180: f2d            
        //  1181: dload           13
        //  1183: ldc2_w          0.699999988079071
        //  1186: dload           21
        //  1188: ddiv           
        //  1189: fload           20
        //  1191: f2d            
        //  1192: ddiv           
        //  1193: dmul           
        //  1194: dadd           
        //  1195: d2f            
        //  1196: putfield        net/minecraft/entity/boss/EntityDragon.randomYawVelocity:F
        //  1199: aload_0        
        //  1200: dup            
        //  1201: getfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //  1204: aload_0        
        //  1205: getfield        net/minecraft/entity/boss/EntityDragon.randomYawVelocity:F
        //  1208: ldc_w           0.1
        //  1211: fmul           
        //  1212: fadd           
        //  1213: putfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //  1216: ldc2_w          2.0
        //  1219: dload           21
        //  1221: dconst_1       
        //  1222: dadd           
        //  1223: ddiv           
        //  1224: d2f            
        //  1225: fstore          23
        //  1227: ldc_w           0.06
        //  1230: fstore          24
        //  1232: aload_0        
        //  1233: fconst_0       
        //  1234: ldc_w           -1.0
        //  1237: fload           24
        //  1239: fload           19
        //  1241: fload           23
        //  1243: fmul           
        //  1244: fconst_1       
        //  1245: fload           23
        //  1247: fsub           
        //  1248: fadd           
        //  1249: fmul           
        //  1250: invokevirtual   net/minecraft/entity/boss/EntityDragon.moveFlying:(FFF)V
        //  1253: aload_0        
        //  1254: getfield        net/minecraft/entity/boss/EntityDragon.slowed:Z
        //  1257: ifeq            1291
        //  1260: aload_0        
        //  1261: aload_0        
        //  1262: getfield        net/minecraft/entity/boss/EntityDragon.motionX:D
        //  1265: ldc2_w          0.800000011920929
        //  1268: dmul           
        //  1269: aload_0        
        //  1270: getfield        net/minecraft/entity/boss/EntityDragon.motionY:D
        //  1273: ldc2_w          0.800000011920929
        //  1276: dmul           
        //  1277: aload_0        
        //  1278: getfield        net/minecraft/entity/boss/EntityDragon.motionZ:D
        //  1281: ldc2_w          0.800000011920929
        //  1284: dmul           
        //  1285: invokevirtual   net/minecraft/entity/boss/EntityDragon.moveEntity:(DDD)V
        //  1288: goto            1307
        //  1291: aload_0        
        //  1292: aload_0        
        //  1293: getfield        net/minecraft/entity/boss/EntityDragon.motionX:D
        //  1296: aload_0        
        //  1297: getfield        net/minecraft/entity/boss/EntityDragon.motionY:D
        //  1300: aload_0        
        //  1301: getfield        net/minecraft/entity/boss/EntityDragon.motionZ:D
        //  1304: invokevirtual   net/minecraft/entity/boss/EntityDragon.moveEntity:(DDD)V
        //  1307: new             Lnet/minecraft/util/Vec3;
        //  1310: dup            
        //  1311: aload_0        
        //  1312: getfield        net/minecraft/entity/boss/EntityDragon.motionX:D
        //  1315: aload_0        
        //  1316: getfield        net/minecraft/entity/boss/EntityDragon.motionY:D
        //  1319: aload_0        
        //  1320: getfield        net/minecraft/entity/boss/EntityDragon.motionZ:D
        //  1323: invokespecial   net/minecraft/util/Vec3.<init>:(DDD)V
        //  1326: invokevirtual   net/minecraft/util/Vec3.normalize:()Lnet/minecraft/util/Vec3;
        //  1329: astore          25
        //  1331: aload           25
        //  1333: aload           18
        //  1335: invokevirtual   net/minecraft/util/Vec3.dotProduct:(Lnet/minecraft/util/Vec3;)D
        //  1338: d2f            
        //  1339: fconst_1       
        //  1340: fadd           
        //  1341: fconst_2       
        //  1342: fdiv           
        //  1343: fstore          26
        //  1345: ldc_w           0.8
        //  1348: ldc_w           0.15
        //  1351: fload           26
        //  1353: fmul           
        //  1354: fadd           
        //  1355: fstore          26
        //  1357: aload_0        
        //  1358: dup            
        //  1359: getfield        net/minecraft/entity/boss/EntityDragon.motionX:D
        //  1362: fload           26
        //  1364: f2d            
        //  1365: dmul           
        //  1366: putfield        net/minecraft/entity/boss/EntityDragon.motionX:D
        //  1369: aload_0        
        //  1370: dup            
        //  1371: getfield        net/minecraft/entity/boss/EntityDragon.motionZ:D
        //  1374: fload           26
        //  1376: f2d            
        //  1377: dmul           
        //  1378: putfield        net/minecraft/entity/boss/EntityDragon.motionZ:D
        //  1381: aload_0        
        //  1382: dup            
        //  1383: getfield        net/minecraft/entity/boss/EntityDragon.motionY:D
        //  1386: ldc2_w          0.9100000262260437
        //  1389: dmul           
        //  1390: putfield        net/minecraft/entity/boss/EntityDragon.motionY:D
        //  1393: aload_0        
        //  1394: aload_0        
        //  1395: getfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //  1398: putfield        net/minecraft/entity/boss/EntityDragon.renderYawOffset:F
        //  1401: aload_0        
        //  1402: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartHead:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1405: aload_0        
        //  1406: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartHead:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1409: ldc_w           3.0
        //  1412: dup_x1         
        //  1413: putfield        net/minecraft/entity/boss/EntityDragonPart.height:F
        //  1416: putfield        net/minecraft/entity/boss/EntityDragonPart.width:F
        //  1419: aload_0        
        //  1420: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartTail1:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1423: aload_0        
        //  1424: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartTail1:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1427: fconst_2       
        //  1428: dup_x1         
        //  1429: putfield        net/minecraft/entity/boss/EntityDragonPart.height:F
        //  1432: putfield        net/minecraft/entity/boss/EntityDragonPart.width:F
        //  1435: aload_0        
        //  1436: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartTail2:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1439: aload_0        
        //  1440: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartTail2:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1443: fconst_2       
        //  1444: dup_x1         
        //  1445: putfield        net/minecraft/entity/boss/EntityDragonPart.height:F
        //  1448: putfield        net/minecraft/entity/boss/EntityDragonPart.width:F
        //  1451: aload_0        
        //  1452: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartTail3:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1455: aload_0        
        //  1456: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartTail3:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1459: fconst_2       
        //  1460: dup_x1         
        //  1461: putfield        net/minecraft/entity/boss/EntityDragonPart.height:F
        //  1464: putfield        net/minecraft/entity/boss/EntityDragonPart.width:F
        //  1467: aload_0        
        //  1468: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartBody:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1471: ldc_w           3.0
        //  1474: putfield        net/minecraft/entity/boss/EntityDragonPart.height:F
        //  1477: aload_0        
        //  1478: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartBody:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1481: ldc_w           5.0
        //  1484: putfield        net/minecraft/entity/boss/EntityDragonPart.width:F
        //  1487: aload_0        
        //  1488: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartWing1:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1491: fconst_2       
        //  1492: putfield        net/minecraft/entity/boss/EntityDragonPart.height:F
        //  1495: aload_0        
        //  1496: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartWing1:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1499: ldc_w           4.0
        //  1502: putfield        net/minecraft/entity/boss/EntityDragonPart.width:F
        //  1505: aload_0        
        //  1506: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartWing2:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1509: ldc_w           3.0
        //  1512: putfield        net/minecraft/entity/boss/EntityDragonPart.height:F
        //  1515: aload_0        
        //  1516: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartWing2:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1519: ldc_w           4.0
        //  1522: putfield        net/minecraft/entity/boss/EntityDragonPart.width:F
        //  1525: aload_0        
        //  1526: iconst_5       
        //  1527: fconst_1       
        //  1528: invokevirtual   net/minecraft/entity/boss/EntityDragon.getMovementOffsets:(IF)[D
        //  1531: iconst_1       
        //  1532: daload         
        //  1533: aload_0        
        //  1534: bipush          10
        //  1536: fconst_1       
        //  1537: invokevirtual   net/minecraft/entity/boss/EntityDragon.getMovementOffsets:(IF)[D
        //  1540: iconst_1       
        //  1541: daload         
        //  1542: dsub           
        //  1543: d2f            
        //  1544: ldc             10.0
        //  1546: fmul           
        //  1547: ldc_w           180.0
        //  1550: fdiv           
        //  1551: ldc_w           3.1415927
        //  1554: fmul           
        //  1555: fstore_2       
        //  1556: fload_2        
        //  1557: invokestatic    net/minecraft/util/MathHelper.cos:(F)F
        //  1560: fstore_3       
        //  1561: fload_2        
        //  1562: invokestatic    net/minecraft/util/MathHelper.sin:(F)F
        //  1565: fneg           
        //  1566: fstore          4
        //  1568: aload_0        
        //  1569: getfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //  1572: ldc_w           3.1415927
        //  1575: fmul           
        //  1576: ldc_w           180.0
        //  1579: fdiv           
        //  1580: fstore          5
        //  1582: fload           5
        //  1584: invokestatic    net/minecraft/util/MathHelper.sin:(F)F
        //  1587: fstore          6
        //  1589: fload           5
        //  1591: invokestatic    net/minecraft/util/MathHelper.cos:(F)F
        //  1594: fstore          7
        //  1596: aload_0        
        //  1597: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartBody:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1600: invokevirtual   net/minecraft/entity/boss/EntityDragonPart.onUpdate:()V
        //  1603: aload_0        
        //  1604: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartBody:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1607: aload_0        
        //  1608: getfield        net/minecraft/entity/boss/EntityDragon.posX:D
        //  1611: fload           6
        //  1613: ldc_w           0.5
        //  1616: fmul           
        //  1617: f2d            
        //  1618: dadd           
        //  1619: aload_0        
        //  1620: getfield        net/minecraft/entity/boss/EntityDragon.posY:D
        //  1623: aload_0        
        //  1624: getfield        net/minecraft/entity/boss/EntityDragon.posZ:D
        //  1627: fload           7
        //  1629: ldc_w           0.5
        //  1632: fmul           
        //  1633: f2d            
        //  1634: dsub           
        //  1635: fconst_0       
        //  1636: fconst_0       
        //  1637: invokevirtual   net/minecraft/entity/boss/EntityDragonPart.setLocationAndAngles:(DDDFF)V
        //  1640: aload_0        
        //  1641: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartWing1:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1644: invokevirtual   net/minecraft/entity/boss/EntityDragonPart.onUpdate:()V
        //  1647: aload_0        
        //  1648: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartWing1:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1651: aload_0        
        //  1652: getfield        net/minecraft/entity/boss/EntityDragon.posX:D
        //  1655: fload           7
        //  1657: ldc_w           4.5
        //  1660: fmul           
        //  1661: f2d            
        //  1662: dadd           
        //  1663: aload_0        
        //  1664: getfield        net/minecraft/entity/boss/EntityDragon.posY:D
        //  1667: ldc2_w          2.0
        //  1670: dadd           
        //  1671: aload_0        
        //  1672: getfield        net/minecraft/entity/boss/EntityDragon.posZ:D
        //  1675: fload           6
        //  1677: ldc_w           4.5
        //  1680: fmul           
        //  1681: f2d            
        //  1682: dadd           
        //  1683: fconst_0       
        //  1684: fconst_0       
        //  1685: invokevirtual   net/minecraft/entity/boss/EntityDragonPart.setLocationAndAngles:(DDDFF)V
        //  1688: aload_0        
        //  1689: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartWing2:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1692: invokevirtual   net/minecraft/entity/boss/EntityDragonPart.onUpdate:()V
        //  1695: aload_0        
        //  1696: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartWing2:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1699: aload_0        
        //  1700: getfield        net/minecraft/entity/boss/EntityDragon.posX:D
        //  1703: fload           7
        //  1705: ldc_w           4.5
        //  1708: fmul           
        //  1709: f2d            
        //  1710: dsub           
        //  1711: aload_0        
        //  1712: getfield        net/minecraft/entity/boss/EntityDragon.posY:D
        //  1715: ldc2_w          2.0
        //  1718: dadd           
        //  1719: aload_0        
        //  1720: getfield        net/minecraft/entity/boss/EntityDragon.posZ:D
        //  1723: fload           6
        //  1725: ldc_w           4.5
        //  1728: fmul           
        //  1729: f2d            
        //  1730: dsub           
        //  1731: fconst_0       
        //  1732: fconst_0       
        //  1733: invokevirtual   net/minecraft/entity/boss/EntityDragonPart.setLocationAndAngles:(DDDFF)V
        //  1736: aload_0        
        //  1737: getfield        net/minecraft/entity/boss/EntityDragon.worldObj:Lnet/minecraft/world/World;
        //  1740: getfield        net/minecraft/world/World.isRemote:Z
        //  1743: ifne            1856
        //  1746: aload_0        
        //  1747: getfield        net/minecraft/entity/boss/EntityDragon.hurtTime:I
        //  1750: ifne            1856
        //  1753: aload_0        
        //  1754: aload_0        
        //  1755: getfield        net/minecraft/entity/boss/EntityDragon.worldObj:Lnet/minecraft/world/World;
        //  1758: aload_0        
        //  1759: aload_0        
        //  1760: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartWing1:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1763: invokevirtual   net/minecraft/entity/boss/EntityDragonPart.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //  1766: ldc2_w          4.0
        //  1769: ldc2_w          2.0
        //  1772: ldc2_w          4.0
        //  1775: invokevirtual   net/minecraft/util/AxisAlignedBB.expand:(DDD)Lnet/minecraft/util/AxisAlignedBB;
        //  1778: dconst_0       
        //  1779: ldc2_w          -2.0
        //  1782: dconst_0       
        //  1783: invokevirtual   net/minecraft/util/AxisAlignedBB.offset:(DDD)Lnet/minecraft/util/AxisAlignedBB;
        //  1786: invokevirtual   net/minecraft/world/World.getEntitiesWithinAABBExcludingEntity:(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/AxisAlignedBB;)Ljava/util/List;
        //  1789: invokespecial   net/minecraft/entity/boss/EntityDragon.collideWithEntities:(Ljava/util/List;)V
        //  1792: aload_0        
        //  1793: aload_0        
        //  1794: getfield        net/minecraft/entity/boss/EntityDragon.worldObj:Lnet/minecraft/world/World;
        //  1797: aload_0        
        //  1798: aload_0        
        //  1799: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartWing2:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1802: invokevirtual   net/minecraft/entity/boss/EntityDragonPart.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //  1805: ldc2_w          4.0
        //  1808: ldc2_w          2.0
        //  1811: ldc2_w          4.0
        //  1814: invokevirtual   net/minecraft/util/AxisAlignedBB.expand:(DDD)Lnet/minecraft/util/AxisAlignedBB;
        //  1817: dconst_0       
        //  1818: ldc2_w          -2.0
        //  1821: dconst_0       
        //  1822: invokevirtual   net/minecraft/util/AxisAlignedBB.offset:(DDD)Lnet/minecraft/util/AxisAlignedBB;
        //  1825: invokevirtual   net/minecraft/world/World.getEntitiesWithinAABBExcludingEntity:(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/AxisAlignedBB;)Ljava/util/List;
        //  1828: invokespecial   net/minecraft/entity/boss/EntityDragon.collideWithEntities:(Ljava/util/List;)V
        //  1831: aload_0        
        //  1832: aload_0        
        //  1833: getfield        net/minecraft/entity/boss/EntityDragon.worldObj:Lnet/minecraft/world/World;
        //  1836: aload_0        
        //  1837: aload_0        
        //  1838: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartHead:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1841: invokevirtual   net/minecraft/entity/boss/EntityDragonPart.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //  1844: dconst_1       
        //  1845: dconst_1       
        //  1846: dconst_1       
        //  1847: invokevirtual   net/minecraft/util/AxisAlignedBB.expand:(DDD)Lnet/minecraft/util/AxisAlignedBB;
        //  1850: invokevirtual   net/minecraft/world/World.getEntitiesWithinAABBExcludingEntity:(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/AxisAlignedBB;)Ljava/util/List;
        //  1853: invokespecial   net/minecraft/entity/boss/EntityDragon.attackEntitiesInList:(Ljava/util/List;)V
        //  1856: aload_0        
        //  1857: iconst_5       
        //  1858: fconst_1       
        //  1859: invokevirtual   net/minecraft/entity/boss/EntityDragon.getMovementOffsets:(IF)[D
        //  1862: astore          8
        //  1864: aload_0        
        //  1865: iconst_0       
        //  1866: fconst_1       
        //  1867: invokevirtual   net/minecraft/entity/boss/EntityDragon.getMovementOffsets:(IF)[D
        //  1870: astore          9
        //  1872: aload_0        
        //  1873: getfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //  1876: ldc_w           3.1415927
        //  1879: fmul           
        //  1880: ldc_w           180.0
        //  1883: fdiv           
        //  1884: aload_0        
        //  1885: getfield        net/minecraft/entity/boss/EntityDragon.randomYawVelocity:F
        //  1888: ldc_w           0.01
        //  1891: fmul           
        //  1892: fsub           
        //  1893: invokestatic    net/minecraft/util/MathHelper.sin:(F)F
        //  1896: fstore          10
        //  1898: aload_0        
        //  1899: getfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //  1902: ldc_w           3.1415927
        //  1905: fmul           
        //  1906: ldc_w           180.0
        //  1909: fdiv           
        //  1910: aload_0        
        //  1911: getfield        net/minecraft/entity/boss/EntityDragon.randomYawVelocity:F
        //  1914: ldc_w           0.01
        //  1917: fmul           
        //  1918: fsub           
        //  1919: invokestatic    net/minecraft/util/MathHelper.cos:(F)F
        //  1922: fstore          11
        //  1924: aload_0        
        //  1925: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartHead:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1928: invokevirtual   net/minecraft/entity/boss/EntityDragonPart.onUpdate:()V
        //  1931: aload_0        
        //  1932: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartHead:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1935: aload_0        
        //  1936: getfield        net/minecraft/entity/boss/EntityDragon.posX:D
        //  1939: fload           10
        //  1941: ldc_w           5.5
        //  1944: fmul           
        //  1945: fload_3        
        //  1946: fmul           
        //  1947: f2d            
        //  1948: dadd           
        //  1949: aload_0        
        //  1950: getfield        net/minecraft/entity/boss/EntityDragon.posY:D
        //  1953: aload           9
        //  1955: iconst_1       
        //  1956: daload         
        //  1957: aload           8
        //  1959: iconst_1       
        //  1960: daload         
        //  1961: dsub           
        //  1962: dconst_1       
        //  1963: dmul           
        //  1964: dadd           
        //  1965: fload           4
        //  1967: ldc_w           5.5
        //  1970: fmul           
        //  1971: f2d            
        //  1972: dadd           
        //  1973: aload_0        
        //  1974: getfield        net/minecraft/entity/boss/EntityDragon.posZ:D
        //  1977: fload           11
        //  1979: ldc_w           5.5
        //  1982: fmul           
        //  1983: fload_3        
        //  1984: fmul           
        //  1985: f2d            
        //  1986: dsub           
        //  1987: fconst_0       
        //  1988: fconst_0       
        //  1989: invokevirtual   net/minecraft/entity/boss/EntityDragonPart.setLocationAndAngles:(DDDFF)V
        //  1992: aconst_null    
        //  1993: astore          13
        //  1995: aload_0        
        //  1996: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartTail1:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  1999: astore          13
        //  2001: goto            2019
        //  2004: aload_0        
        //  2005: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartTail2:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  2008: astore          13
        //  2010: goto            2019
        //  2013: aload_0        
        //  2014: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartTail3:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  2017: astore          13
        //  2019: aload_0        
        //  2020: bipush          12
        //  2022: fconst_1       
        //  2023: invokevirtual   net/minecraft/entity/boss/EntityDragon.getMovementOffsets:(IF)[D
        //  2026: astore          14
        //  2028: aload_0        
        //  2029: getfield        net/minecraft/entity/boss/EntityDragon.rotationYaw:F
        //  2032: ldc_w           3.1415927
        //  2035: fmul           
        //  2036: ldc_w           180.0
        //  2039: fdiv           
        //  2040: aload_0        
        //  2041: aload           14
        //  2043: iconst_0       
        //  2044: daload         
        //  2045: aload           8
        //  2047: iconst_0       
        //  2048: daload         
        //  2049: dsub           
        //  2050: invokespecial   net/minecraft/entity/boss/EntityDragon.simplifyAngle:(D)F
        //  2053: ldc_w           3.1415927
        //  2056: fmul           
        //  2057: ldc_w           180.0
        //  2060: fdiv           
        //  2061: fconst_1       
        //  2062: fmul           
        //  2063: fadd           
        //  2064: fstore          15
        //  2066: fload           15
        //  2068: invokestatic    net/minecraft/util/MathHelper.sin:(F)F
        //  2071: fstore          16
        //  2073: fload           15
        //  2075: invokestatic    net/minecraft/util/MathHelper.cos:(F)F
        //  2078: fstore          17
        //  2080: ldc_w           1.5
        //  2083: fstore          18
        //  2085: iconst_1       
        //  2086: i2f            
        //  2087: fconst_2       
        //  2088: fmul           
        //  2089: fstore          19
        //  2091: aload           13
        //  2093: invokevirtual   net/minecraft/entity/boss/EntityDragonPart.onUpdate:()V
        //  2096: aload           13
        //  2098: aload_0        
        //  2099: getfield        net/minecraft/entity/boss/EntityDragon.posX:D
        //  2102: fload           6
        //  2104: fload           18
        //  2106: fmul           
        //  2107: fload           16
        //  2109: fload           19
        //  2111: fmul           
        //  2112: fadd           
        //  2113: fload_3        
        //  2114: fmul           
        //  2115: f2d            
        //  2116: dsub           
        //  2117: aload_0        
        //  2118: getfield        net/minecraft/entity/boss/EntityDragon.posY:D
        //  2121: aload           14
        //  2123: iconst_1       
        //  2124: daload         
        //  2125: aload           8
        //  2127: iconst_1       
        //  2128: daload         
        //  2129: dsub           
        //  2130: dconst_1       
        //  2131: dmul           
        //  2132: dadd           
        //  2133: fload           19
        //  2135: fload           18
        //  2137: fadd           
        //  2138: fload           4
        //  2140: fmul           
        //  2141: f2d            
        //  2142: dsub           
        //  2143: ldc2_w          1.5
        //  2146: dadd           
        //  2147: aload_0        
        //  2148: getfield        net/minecraft/entity/boss/EntityDragon.posZ:D
        //  2151: fload           7
        //  2153: fload           18
        //  2155: fmul           
        //  2156: fload           17
        //  2158: fload           19
        //  2160: fmul           
        //  2161: fadd           
        //  2162: fload_3        
        //  2163: fmul           
        //  2164: f2d            
        //  2165: dadd           
        //  2166: fconst_0       
        //  2167: fconst_0       
        //  2168: invokevirtual   net/minecraft/entity/boss/EntityDragonPart.setLocationAndAngles:(DDDFF)V
        //  2171: iinc            12, 1
        //  2174: goto            1992
        //  2177: aload_0        
        //  2178: getfield        net/minecraft/entity/boss/EntityDragon.worldObj:Lnet/minecraft/world/World;
        //  2181: getfield        net/minecraft/world/World.isRemote:Z
        //  2184: ifne            2214
        //  2187: aload_0        
        //  2188: aload_0        
        //  2189: aload_0        
        //  2190: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartHead:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  2193: invokevirtual   net/minecraft/entity/boss/EntityDragonPart.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //  2196: invokespecial   net/minecraft/entity/boss/EntityDragon.destroyBlocksInAABB:(Lnet/minecraft/util/AxisAlignedBB;)Z
        //  2199: aload_0        
        //  2200: aload_0        
        //  2201: getfield        net/minecraft/entity/boss/EntityDragon.dragonPartBody:Lnet/minecraft/entity/boss/EntityDragonPart;
        //  2204: invokevirtual   net/minecraft/entity/boss/EntityDragonPart.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //  2207: invokespecial   net/minecraft/entity/boss/EntityDragon.destroyBlocksInAABB:(Lnet/minecraft/util/AxisAlignedBB;)Z
        //  2210: ior            
        //  2211: putfield        net/minecraft/entity/boss/EntityDragon.slowed:Z
        //  2214: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
