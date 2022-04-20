package net.minecraft.entity.monster;

import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.util.*;

public class EntityGhast extends EntityFlying implements IMob
{
    private int explosionStrength;
    
    @Override
    protected Item getDropItem() {
        return Items.gunpowder;
    }
    
    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.ghast.moan";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.ghast.scream";
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        int n2 = 0;
        while (0 < this.rand.nextInt(2) + this.rand.nextInt(1 + n)) {
            this.dropItem(Items.ghast_tear, 1);
            ++n2;
        }
        while (0 < this.rand.nextInt(3) + this.rand.nextInt(1 + n)) {
            this.dropItem(Items.gunpowder, 1);
            ++n2;
        }
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey("ExplosionPower", 99)) {
            this.explosionStrength = nbtTagCompound.getInteger("ExplosionPower");
        }
    }
    
    public int getFireballStrength() {
        return this.explosionStrength;
    }
    
    @Override
    public float getEyeHeight() {
        return 2.6f;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(100.0);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, 0);
    }
    
    public void setAttacking(final boolean b) {
        this.dataWatcher.updateObject(16, (byte)(byte)(b ? 1 : 0));
    }
    
    @Override
    public boolean getCanSpawnHere() {
        return this.rand.nextInt(20) == 0 && super.getCanSpawnHere() && this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL;
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("ExplosionPower", this.explosionStrength);
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isRemote && this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL) {
            this.setDead();
        }
    }
    
    @Override
    protected float getSoundVolume() {
        return 10.0f;
    }
    
    public boolean isAttacking() {
        return this.dataWatcher.getWatchableObjectByte(16) != 0;
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.ghast.death";
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }
        if ("fireball".equals(damageSource.getDamageType()) && damageSource.getEntity() instanceof EntityPlayer) {
            super.attackEntityFrom(damageSource, 1000.0f);
            ((EntityPlayer)damageSource.getEntity()).triggerAchievement(AchievementList.ghast);
            return true;
        }
        return super.attackEntityFrom(damageSource, n);
    }
    
    public EntityGhast(final World world) {
        super(world);
        this.explosionStrength = 1;
        this.setSize(4.0f, 4.0f);
        this.isImmuneToFire = true;
        this.experienceValue = 5;
        this.moveHelper = new GhastMoveHelper(this);
        this.tasks.addTask(5, new AIRandomFly(this));
        this.tasks.addTask(7, new AILookAround(this));
        this.tasks.addTask(7, new AIFireballAttack(this));
        this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
    }
    
    static class GhastMoveHelper extends EntityMoveHelper
    {
        private int courseChangeCooldown;
        private EntityGhast parentEntity;
        
        @Override
        public void onUpdateMoveHelper() {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     1: getfield        net/minecraft/entity/monster/EntityGhast$GhastMoveHelper.update:Z
            //     4: ifeq            193
            //     7: aload_0        
            //     8: getfield        net/minecraft/entity/monster/EntityGhast$GhastMoveHelper.posX:D
            //    11: aload_0        
            //    12: getfield        net/minecraft/entity/monster/EntityGhast$GhastMoveHelper.parentEntity:Lnet/minecraft/entity/monster/EntityGhast;
            //    15: getfield        net/minecraft/entity/monster/EntityGhast.posX:D
            //    18: dsub           
            //    19: dstore_1       
            //    20: aload_0        
            //    21: getfield        net/minecraft/entity/monster/EntityGhast$GhastMoveHelper.posY:D
            //    24: aload_0        
            //    25: getfield        net/minecraft/entity/monster/EntityGhast$GhastMoveHelper.parentEntity:Lnet/minecraft/entity/monster/EntityGhast;
            //    28: getfield        net/minecraft/entity/monster/EntityGhast.posY:D
            //    31: dsub           
            //    32: dstore_3       
            //    33: aload_0        
            //    34: getfield        net/minecraft/entity/monster/EntityGhast$GhastMoveHelper.posZ:D
            //    37: aload_0        
            //    38: getfield        net/minecraft/entity/monster/EntityGhast$GhastMoveHelper.parentEntity:Lnet/minecraft/entity/monster/EntityGhast;
            //    41: getfield        net/minecraft/entity/monster/EntityGhast.posZ:D
            //    44: dsub           
            //    45: dstore          5
            //    47: dload_1        
            //    48: dload_1        
            //    49: dmul           
            //    50: dload_3        
            //    51: dload_3        
            //    52: dmul           
            //    53: dadd           
            //    54: dload           5
            //    56: dload           5
            //    58: dmul           
            //    59: dadd           
            //    60: dstore          7
            //    62: aload_0        
            //    63: dup            
            //    64: getfield        net/minecraft/entity/monster/EntityGhast$GhastMoveHelper.courseChangeCooldown:I
            //    67: dup_x1         
            //    68: iconst_1       
            //    69: isub           
            //    70: putfield        net/minecraft/entity/monster/EntityGhast$GhastMoveHelper.courseChangeCooldown:I
            //    73: ifgt            193
            //    76: aload_0        
            //    77: dup            
            //    78: getfield        net/minecraft/entity/monster/EntityGhast$GhastMoveHelper.courseChangeCooldown:I
            //    81: aload_0        
            //    82: getfield        net/minecraft/entity/monster/EntityGhast$GhastMoveHelper.parentEntity:Lnet/minecraft/entity/monster/EntityGhast;
            //    85: invokevirtual   net/minecraft/entity/monster/EntityGhast.getRNG:()Ljava/util/Random;
            //    88: iconst_5       
            //    89: invokevirtual   java/util/Random.nextInt:(I)I
            //    92: iconst_2       
            //    93: iadd           
            //    94: iadd           
            //    95: putfield        net/minecraft/entity/monster/EntityGhast$GhastMoveHelper.courseChangeCooldown:I
            //    98: dload           7
            //   100: invokestatic    net/minecraft/util/MathHelper.sqrt_double:(D)F
            //   103: f2d            
            //   104: dstore          7
            //   106: aload_0        
            //   107: aload_0        
            //   108: getfield        net/minecraft/entity/monster/EntityGhast$GhastMoveHelper.posX:D
            //   111: aload_0        
            //   112: getfield        net/minecraft/entity/monster/EntityGhast$GhastMoveHelper.posY:D
            //   115: aload_0        
            //   116: getfield        net/minecraft/entity/monster/EntityGhast$GhastMoveHelper.posZ:D
            //   119: dload           7
            //   121: ifge            188
            //   124: aload_0        
            //   125: getfield        net/minecraft/entity/monster/EntityGhast$GhastMoveHelper.parentEntity:Lnet/minecraft/entity/monster/EntityGhast;
            //   128: dup            
            //   129: getfield        net/minecraft/entity/monster/EntityGhast.motionX:D
            //   132: dload_1        
            //   133: dload           7
            //   135: ddiv           
            //   136: ldc2_w          0.1
            //   139: dmul           
            //   140: dadd           
            //   141: putfield        net/minecraft/entity/monster/EntityGhast.motionX:D
            //   144: aload_0        
            //   145: getfield        net/minecraft/entity/monster/EntityGhast$GhastMoveHelper.parentEntity:Lnet/minecraft/entity/monster/EntityGhast;
            //   148: dup            
            //   149: getfield        net/minecraft/entity/monster/EntityGhast.motionY:D
            //   152: dload_3        
            //   153: dload           7
            //   155: ddiv           
            //   156: ldc2_w          0.1
            //   159: dmul           
            //   160: dadd           
            //   161: putfield        net/minecraft/entity/monster/EntityGhast.motionY:D
            //   164: aload_0        
            //   165: getfield        net/minecraft/entity/monster/EntityGhast$GhastMoveHelper.parentEntity:Lnet/minecraft/entity/monster/EntityGhast;
            //   168: dup            
            //   169: getfield        net/minecraft/entity/monster/EntityGhast.motionZ:D
            //   172: dload           5
            //   174: dload           7
            //   176: ddiv           
            //   177: ldc2_w          0.1
            //   180: dmul           
            //   181: dadd           
            //   182: putfield        net/minecraft/entity/monster/EntityGhast.motionZ:D
            //   185: goto            193
            //   188: aload_0        
            //   189: iconst_0       
            //   190: putfield        net/minecraft/entity/monster/EntityGhast$GhastMoveHelper.update:Z
            //   193: return         
            // 
            // The error that occurred was:
            // 
            // java.lang.IllegalStateException: Inconsistent stack size at #0193 (coming from #0190).
            //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
            //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:576)
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
        
        public GhastMoveHelper(final EntityGhast parentEntity) {
            super(parentEntity);
            this.parentEntity = parentEntity;
        }
    }
    
    static class AILookAround extends EntityAIBase
    {
        private EntityGhast parentEntity;
        
        public AILookAround(final EntityGhast parentEntity) {
            this.parentEntity = parentEntity;
            this.setMutexBits(2);
        }
        
        @Override
        public boolean shouldExecute() {
            return true;
        }
        
        @Override
        public void updateTask() {
            if (this.parentEntity.getAttackTarget() == null) {
                final EntityGhast parentEntity = this.parentEntity;
                final EntityGhast parentEntity2 = this.parentEntity;
                final float n = -(float)MathHelper.func_181159_b(this.parentEntity.motionX, this.parentEntity.motionZ) * 180.0f / 3.1415927f;
                parentEntity2.rotationYaw = n;
                parentEntity.renderYawOffset = n;
            }
            else {
                final EntityLivingBase attackTarget = this.parentEntity.getAttackTarget();
                final double n2 = 64.0;
                if (attackTarget.getDistanceSqToEntity(this.parentEntity) < n2 * n2) {
                    final double n3 = attackTarget.posX - this.parentEntity.posX;
                    final double n4 = attackTarget.posZ - this.parentEntity.posZ;
                    final EntityGhast parentEntity3 = this.parentEntity;
                    final EntityGhast parentEntity4 = this.parentEntity;
                    final float n5 = -(float)MathHelper.func_181159_b(n3, n4) * 180.0f / 3.1415927f;
                    parentEntity4.rotationYaw = n5;
                    parentEntity3.renderYawOffset = n5;
                }
            }
        }
    }
    
    static class AIRandomFly extends EntityAIBase
    {
        private EntityGhast parentEntity;
        
        @Override
        public boolean continueExecuting() {
            return false;
        }
        
        @Override
        public boolean shouldExecute() {
            final EntityMoveHelper moveHelper = this.parentEntity.getMoveHelper();
            if (!moveHelper.isUpdating()) {
                return true;
            }
            final double n = moveHelper.getX() - this.parentEntity.posX;
            final double n2 = moveHelper.getY() - this.parentEntity.posY;
            final double n3 = moveHelper.getZ() - this.parentEntity.posZ;
            final double n4 = n * n + n2 * n2 + n3 * n3;
            return n4 < 1.0 || n4 > 3600.0;
        }
        
        public AIRandomFly(final EntityGhast parentEntity) {
            this.parentEntity = parentEntity;
            this.setMutexBits(1);
        }
        
        @Override
        public void startExecuting() {
            final Random rng = this.parentEntity.getRNG();
            this.parentEntity.getMoveHelper().setMoveTo(this.parentEntity.posX + (rng.nextFloat() * 2.0f - 1.0f) * 16.0f, this.parentEntity.posY + (rng.nextFloat() * 2.0f - 1.0f) * 16.0f, this.parentEntity.posZ + (rng.nextFloat() * 2.0f - 1.0f) * 16.0f, 1.0);
        }
    }
    
    static class AIFireballAttack extends EntityAIBase
    {
        private EntityGhast parentEntity;
        public int attackTimer;
        
        public AIFireballAttack(final EntityGhast parentEntity) {
            this.parentEntity = parentEntity;
        }
        
        @Override
        public void startExecuting() {
            this.attackTimer = 0;
        }
        
        @Override
        public void resetTask() {
            this.parentEntity.setAttacking(false);
        }
        
        @Override
        public void updateTask() {
            final EntityLivingBase attackTarget = this.parentEntity.getAttackTarget();
            final double n = 64.0;
            if (attackTarget.getDistanceSqToEntity(this.parentEntity) < n * n && this.parentEntity.canEntityBeSeen(attackTarget)) {
                final World worldObj = this.parentEntity.worldObj;
                ++this.attackTimer;
                if (this.attackTimer == 10) {
                    worldObj.playAuxSFXAtEntity(null, 1007, new BlockPos(this.parentEntity), 0);
                }
                if (this.attackTimer == 20) {
                    final double n2 = 4.0;
                    final Vec3 look = this.parentEntity.getLook(1.0f);
                    final double n3 = attackTarget.posX - (this.parentEntity.posX + look.xCoord * n2);
                    final double n4 = attackTarget.getEntityBoundingBox().minY + attackTarget.height / 2.0f - (0.5 + this.parentEntity.posY + this.parentEntity.height / 2.0f);
                    final double n5 = attackTarget.posZ - (this.parentEntity.posZ + look.zCoord * n2);
                    worldObj.playAuxSFXAtEntity(null, 1008, new BlockPos(this.parentEntity), 0);
                    final EntityLargeFireball entityLargeFireball = new EntityLargeFireball(worldObj, this.parentEntity, n3, n4, n5);
                    entityLargeFireball.explosionPower = this.parentEntity.getFireballStrength();
                    entityLargeFireball.posX = this.parentEntity.posX + look.xCoord * n2;
                    entityLargeFireball.posY = this.parentEntity.posY + this.parentEntity.height / 2.0f + 0.5;
                    entityLargeFireball.posZ = this.parentEntity.posZ + look.zCoord * n2;
                    worldObj.spawnEntityInWorld(entityLargeFireball);
                    this.attackTimer = -40;
                }
            }
            else if (this.attackTimer > 0) {
                --this.attackTimer;
            }
            this.parentEntity.setAttacking(this.attackTimer > 10);
        }
        
        @Override
        public boolean shouldExecute() {
            return this.parentEntity.getAttackTarget() != null;
        }
    }
}
