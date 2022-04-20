package net.minecraft.entity.monster;

import net.minecraft.entity.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.pathfinding.*;

public class EntitySlime extends EntityLiving implements IMob
{
    public float prevSquishFactor;
    private boolean wasOnGround;
    public float squishAmount;
    public float squishFactor;
    
    @Override
    public float getEyeHeight() {
        return 0.625f * this.height;
    }
    
    protected boolean makesSoundOnJump() {
        return this.getSlimeSize() > 0;
    }
    
    protected EntitySlime createInstance() {
        return new EntitySlime(this.worldObj);
    }
    
    protected void setSlimeSize(final int experienceValue) {
        this.dataWatcher.updateObject(16, (byte)experienceValue);
        this.setSize(0.51000005f * experienceValue, 0.51000005f * experienceValue);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(experienceValue * experienceValue);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2f + 0.1f * experienceValue);
        this.setHealth(this.getMaxHealth());
        this.experienceValue = experienceValue;
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.slime." + ((this.getSlimeSize() > 1) ? "big" : "small");
    }
    
    protected void func_175451_e(final EntityLivingBase entityLivingBase) {
        final int slimeSize = this.getSlimeSize();
        if (this.canEntityBeSeen(entityLivingBase) && this.getDistanceSqToEntity(entityLivingBase) < 0.6 * slimeSize * 0.6 * slimeSize && entityLivingBase.attackEntityFrom(DamageSource.causeMobDamage(this), (float)this.getAttackStrength())) {
            this.playSound("mob.attack", 1.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            this.applyEnchantments(this, entityLivingBase);
        }
    }
    
    @Override
    public void onDataWatcherUpdate(final int n) {
        if (n == 16) {
            final int slimeSize = this.getSlimeSize();
            this.setSize(0.51000005f * slimeSize, 0.51000005f * slimeSize);
            this.rotationYaw = this.rotationYawHead;
            this.renderYawOffset = this.rotationYawHead;
            if (this.isInWater() && this.rand.nextInt(20) == 0) {
                this.resetHeight();
            }
        }
        super.onDataWatcherUpdate(n);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, 1);
    }
    
    @Override
    public int getVerticalFaceSpeed() {
        return 0;
    }
    
    protected int getJumpDelay() {
        return this.rand.nextInt(20) + 10;
    }
    
    @Override
    public void applyEntityCollision(final Entity p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: invokespecial   net/minecraft/entity/EntityLiving.applyEntityCollision:(Lnet/minecraft/entity/Entity;)V
        //     5: aload_1        
        //     6: instanceof      Lnet/minecraft/entity/monster/EntityIronGolem;
        //     9: ifeq            24
        //    12: aload_0        
        //    13: if_icmple       24
        //    16: aload_0        
        //    17: aload_1        
        //    18: checkcast       Lnet/minecraft/entity/EntityLivingBase;
        //    21: invokevirtual   net/minecraft/entity/monster/EntitySlime.func_175451_e:(Lnet/minecraft/entity/EntityLivingBase;)V
        //    24: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public void onUpdate() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/entity/monster/EntitySlime.worldObj:Lnet/minecraft/world/World;
        //     4: getfield        net/minecraft/world/World.isRemote:Z
        //     7: ifne            35
        //    10: aload_0        
        //    11: getfield        net/minecraft/entity/monster/EntitySlime.worldObj:Lnet/minecraft/world/World;
        //    14: invokevirtual   net/minecraft/world/World.getDifficulty:()Lnet/minecraft/world/EnumDifficulty;
        //    17: getstatic       net/minecraft/world/EnumDifficulty.PEACEFUL:Lnet/minecraft/world/EnumDifficulty;
        //    20: if_acmpne       35
        //    23: aload_0        
        //    24: invokevirtual   net/minecraft/entity/monster/EntitySlime.getSlimeSize:()I
        //    27: ifle            35
        //    30: aload_0        
        //    31: iconst_1       
        //    32: putfield        net/minecraft/entity/monster/EntitySlime.isDead:Z
        //    35: aload_0        
        //    36: dup            
        //    37: getfield        net/minecraft/entity/monster/EntitySlime.squishFactor:F
        //    40: aload_0        
        //    41: getfield        net/minecraft/entity/monster/EntitySlime.squishAmount:F
        //    44: aload_0        
        //    45: getfield        net/minecraft/entity/monster/EntitySlime.squishFactor:F
        //    48: fsub           
        //    49: ldc             0.5
        //    51: fmul           
        //    52: fadd           
        //    53: putfield        net/minecraft/entity/monster/EntitySlime.squishFactor:F
        //    56: aload_0        
        //    57: aload_0        
        //    58: getfield        net/minecraft/entity/monster/EntitySlime.squishFactor:F
        //    61: putfield        net/minecraft/entity/monster/EntitySlime.prevSquishFactor:F
        //    64: aload_0        
        //    65: invokespecial   net/minecraft/entity/EntityLiving.onUpdate:()V
        //    68: aload_0        
        //    69: getfield        net/minecraft/entity/monster/EntitySlime.onGround:Z
        //    72: ifeq            266
        //    75: aload_0        
        //    76: getfield        net/minecraft/entity/monster/EntitySlime.wasOnGround:Z
        //    79: ifne            266
        //    82: aload_0        
        //    83: invokevirtual   net/minecraft/entity/monster/EntitySlime.getSlimeSize:()I
        //    86: istore_1       
        //    87: iconst_0       
        //    88: iload_1        
        //    89: bipush          8
        //    91: imul           
        //    92: if_icmpge       216
        //    95: aload_0        
        //    96: getfield        net/minecraft/entity/monster/EntitySlime.rand:Ljava/util/Random;
        //    99: invokevirtual   java/util/Random.nextFloat:()F
        //   102: ldc_w           3.1415927
        //   105: fmul           
        //   106: fconst_2       
        //   107: fmul           
        //   108: fstore_3       
        //   109: aload_0        
        //   110: getfield        net/minecraft/entity/monster/EntitySlime.rand:Ljava/util/Random;
        //   113: invokevirtual   java/util/Random.nextFloat:()F
        //   116: ldc             0.5
        //   118: fmul           
        //   119: ldc             0.5
        //   121: fadd           
        //   122: fstore          4
        //   124: fload_3        
        //   125: invokestatic    net/minecraft/util/MathHelper.sin:(F)F
        //   128: iload_1        
        //   129: i2f            
        //   130: fmul           
        //   131: ldc             0.5
        //   133: fmul           
        //   134: fload           4
        //   136: fmul           
        //   137: fstore          5
        //   139: fload_3        
        //   140: invokestatic    net/minecraft/util/MathHelper.cos:(F)F
        //   143: iload_1        
        //   144: i2f            
        //   145: fmul           
        //   146: ldc             0.5
        //   148: fmul           
        //   149: fload           4
        //   151: fmul           
        //   152: fstore          6
        //   154: aload_0        
        //   155: getfield        net/minecraft/entity/monster/EntitySlime.worldObj:Lnet/minecraft/world/World;
        //   158: astore          7
        //   160: aload_0        
        //   161: invokevirtual   net/minecraft/entity/monster/EntitySlime.getParticleType:()Lnet/minecraft/util/EnumParticleTypes;
        //   164: astore          8
        //   166: aload_0        
        //   167: getfield        net/minecraft/entity/monster/EntitySlime.posX:D
        //   170: fload           5
        //   172: f2d            
        //   173: dadd           
        //   174: dstore          9
        //   176: aload_0        
        //   177: getfield        net/minecraft/entity/monster/EntitySlime.posZ:D
        //   180: fload           6
        //   182: f2d            
        //   183: dadd           
        //   184: dstore          11
        //   186: aload           7
        //   188: aload           8
        //   190: dload           9
        //   192: aload_0        
        //   193: invokevirtual   net/minecraft/entity/monster/EntitySlime.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //   196: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //   199: dload           11
        //   201: dconst_0       
        //   202: dconst_0       
        //   203: dconst_0       
        //   204: iconst_0       
        //   205: newarray        I
        //   207: invokevirtual   net/minecraft/world/World.spawnParticle:(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V
        //   210: iinc            2, 1
        //   213: goto            87
        //   216: aload_0        
        //   217: if_icmple       256
        //   220: aload_0        
        //   221: aload_0        
        //   222: invokevirtual   net/minecraft/entity/monster/EntitySlime.getJumpSound:()Ljava/lang/String;
        //   225: aload_0        
        //   226: invokevirtual   net/minecraft/entity/monster/EntitySlime.getSoundVolume:()F
        //   229: aload_0        
        //   230: getfield        net/minecraft/entity/monster/EntitySlime.rand:Ljava/util/Random;
        //   233: invokevirtual   java/util/Random.nextFloat:()F
        //   236: aload_0        
        //   237: getfield        net/minecraft/entity/monster/EntitySlime.rand:Ljava/util/Random;
        //   240: invokevirtual   java/util/Random.nextFloat:()F
        //   243: fsub           
        //   244: ldc             0.2
        //   246: fmul           
        //   247: fconst_1       
        //   248: fadd           
        //   249: ldc_w           0.8
        //   252: fdiv           
        //   253: invokevirtual   net/minecraft/entity/monster/EntitySlime.playSound:(Ljava/lang/String;FF)V
        //   256: aload_0        
        //   257: ldc_w           -0.5
        //   260: putfield        net/minecraft/entity/monster/EntitySlime.squishAmount:F
        //   263: goto            285
        //   266: aload_0        
        //   267: getfield        net/minecraft/entity/monster/EntitySlime.onGround:Z
        //   270: ifne            285
        //   273: aload_0        
        //   274: getfield        net/minecraft/entity/monster/EntitySlime.wasOnGround:Z
        //   277: ifeq            285
        //   280: aload_0        
        //   281: fconst_1       
        //   282: putfield        net/minecraft/entity/monster/EntitySlime.squishAmount:F
        //   285: aload_0        
        //   286: aload_0        
        //   287: getfield        net/minecraft/entity/monster/EntitySlime.onGround:Z
        //   290: putfield        net/minecraft/entity/monster/EntitySlime.wasOnGround:Z
        //   293: aload_0        
        //   294: invokevirtual   net/minecraft/entity/monster/EntitySlime.alterSquishAmount:()V
        //   297: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.slime." + ((this.getSlimeSize() > 1) ? "big" : "small");
    }
    
    @Override
    public void setDead() {
        final int slimeSize = this.getSlimeSize();
        if (!this.worldObj.isRemote && slimeSize > 1 && this.getHealth() <= 0.0f) {
            while (0 < 2 + this.rand.nextInt(3)) {
                final float n = (0 - 0.5f) * slimeSize / 4.0f;
                final float n2 = (0 - 0.5f) * slimeSize / 4.0f;
                final EntitySlime instance = this.createInstance();
                if (this.hasCustomName()) {
                    instance.setCustomNameTag(this.getCustomNameTag());
                }
                if (this.isNoDespawnRequired()) {
                    instance.enablePersistence();
                }
                instance.setSlimeSize(slimeSize / 2);
                instance.setLocationAndAngles(this.posX + n, this.posY + 0.5, this.posZ + n2, this.rand.nextFloat() * 360.0f, 0.0f);
                this.worldObj.spawnEntityInWorld(instance);
                int n3 = 0;
                ++n3;
            }
        }
        super.setDead();
    }
    
    @Override
    protected float getSoundVolume() {
        return 0.4f * this.getSlimeSize();
    }
    
    protected void alterSquishAmount() {
        this.squishAmount *= 0.6f;
    }
    
    @Override
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficultyInstance, final IEntityLivingData entityLivingData) {
        int nextInt = this.rand.nextInt(3);
        if (nextInt < 2 && this.rand.nextFloat() < 0.5f * difficultyInstance.getClampedAdditionalDifficulty()) {
            ++nextInt;
        }
        this.setSlimeSize(1 << nextInt);
        return super.onInitialSpawn(difficultyInstance, entityLivingData);
    }
    
    @Override
    protected void jump() {
        this.motionY = 0.41999998688697815;
        this.isAirBorne = true;
    }
    
    @Override
    public boolean getCanSpawnHere() {
        final BlockPos blockPos = new BlockPos(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ));
        final Chunk chunkFromBlockCoords = this.worldObj.getChunkFromBlockCoords(blockPos);
        if (this.worldObj.getWorldInfo().getTerrainType() == WorldType.FLAT && this.rand.nextInt(4) != 1) {
            return false;
        }
        if (this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL) {
            if (this.worldObj.getBiomeGenForCoords(blockPos) == BiomeGenBase.swampland && this.posY > 50.0 && this.posY < 70.0 && this.rand.nextFloat() < 0.5f && this.rand.nextFloat() < this.worldObj.getCurrentMoonPhaseFactor() && this.worldObj.getLightFromNeighbors(new BlockPos(this)) <= this.rand.nextInt(8)) {
                return super.getCanSpawnHere();
            }
            if (this.rand.nextInt(10) == 0 && chunkFromBlockCoords.getRandomWithSeed(987234911L).nextInt(10) == 0 && this.posY < 40.0) {
                return super.getCanSpawnHere();
            }
        }
        return false;
    }
    
    protected EnumParticleTypes getParticleType() {
        return EnumParticleTypes.SLIME;
    }
    
    @Override
    protected Item getDropItem() {
        return (this.getSlimeSize() == 1) ? Items.slime_ball : null;
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("Size", this.getSlimeSize() - 1);
        nbtTagCompound.setBoolean("wasOnGround", this.wasOnGround);
    }
    
    public EntitySlime(final World world) {
        super(world);
        this.moveHelper = new SlimeMoveHelper(this);
        this.tasks.addTask(1, new AISlimeFloat(this));
        this.tasks.addTask(2, new AISlimeAttack(this));
        this.tasks.addTask(3, new AISlimeFaceRandom(this));
        this.tasks.addTask(5, new AISlimeHop(this));
        this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
        this.targetTasks.addTask(3, new EntityAIFindEntityNearest(this, EntityIronGolem.class));
    }
    
    @Override
    public void onCollideWithPlayer(final EntityPlayer p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_icmple       9
        //     4: aload_0        
        //     5: aload_1        
        //     6: invokevirtual   net/minecraft/entity/monster/EntitySlime.func_175451_e:(Lnet/minecraft/entity/EntityLivingBase;)V
        //     9: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public int getSlimeSize() {
        return this.dataWatcher.getWatchableObjectByte(16);
    }
    
    protected String getJumpSound() {
        return "mob.slime." + ((this.getSlimeSize() > 1) ? "big" : "small");
    }
    
    protected int getAttackStrength() {
        return this.getSlimeSize();
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        nbtTagCompound.getInteger("Size");
        this.setSlimeSize(1);
        this.wasOnGround = nbtTagCompound.getBoolean("wasOnGround");
    }
    
    static class AISlimeFaceRandom extends EntityAIBase
    {
        private float field_179459_b;
        private int field_179460_c;
        private EntitySlime slime;
        
        public AISlimeFaceRandom(final EntitySlime slime) {
            this.slime = slime;
            this.setMutexBits(2);
        }
        
        @Override
        public void updateTask() {
            final int field_179460_c = this.field_179460_c - 1;
            this.field_179460_c = field_179460_c;
            if (field_179460_c <= 0) {
                this.field_179460_c = 40 + this.slime.getRNG().nextInt(60);
                this.field_179459_b = (float)this.slime.getRNG().nextInt(360);
            }
            ((SlimeMoveHelper)this.slime.getMoveHelper()).func_179920_a(this.field_179459_b, false);
        }
        
        @Override
        public boolean shouldExecute() {
            return this.slime.getAttackTarget() == null && (this.slime.onGround || this.slime.isInWater() || this.slime.isInLava());
        }
    }
    
    static class SlimeMoveHelper extends EntityMoveHelper
    {
        private int field_179924_h;
        private float field_179922_g;
        private EntitySlime slime;
        private boolean field_179923_j;
        
        public void setSpeed(final double speed) {
            this.speed = speed;
            this.update = true;
        }
        
        public SlimeMoveHelper(final EntitySlime slime) {
            super(slime);
            this.slime = slime;
        }
        
        public void func_179920_a(final float field_179922_g, final boolean field_179923_j) {
            this.field_179922_g = field_179922_g;
            this.field_179923_j = field_179923_j;
        }
        
        @Override
        public void onUpdateMoveHelper() {
            this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, this.field_179922_g, 30.0f);
            this.entity.rotationYawHead = this.entity.rotationYaw;
            this.entity.renderYawOffset = this.entity.rotationYaw;
            if (!this.update) {
                this.entity.setMoveForward(0.0f);
            }
            else {
                this.update = false;
                if (this.entity.onGround) {
                    this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()));
                    if (this.field_179924_h-- <= 0) {
                        this.field_179924_h = this.slime.getJumpDelay();
                        if (this.field_179923_j) {
                            this.field_179924_h /= 3;
                        }
                        this.slime.getJumpHelper().setJumping();
                        if (this.slime.makesSoundOnJump()) {
                            this.slime.playSound(this.slime.getJumpSound(), this.slime.getSoundVolume(), ((this.slime.getRNG().nextFloat() - this.slime.getRNG().nextFloat()) * 0.2f + 1.0f) * 0.8f);
                        }
                    }
                    else {
                        final EntitySlime slime = this.slime;
                        final EntitySlime slime2 = this.slime;
                        final float n = 0.0f;
                        slime2.moveForward = n;
                        slime.moveStrafing = n;
                        this.entity.setAIMoveSpeed(0.0f);
                    }
                }
                else {
                    this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()));
                }
            }
        }
    }
    
    static class AISlimeAttack extends EntityAIBase
    {
        private EntitySlime slime;
        private int field_179465_b;
        
        @Override
        public boolean continueExecuting() {
            final EntityLivingBase attackTarget = this.slime.getAttackTarget();
            return attackTarget != null && attackTarget.isEntityAlive() && (!(attackTarget instanceof EntityPlayer) || !((EntityPlayer)attackTarget).capabilities.disableDamage) && --this.field_179465_b > 0;
        }
        
        @Override
        public boolean shouldExecute() {
            final EntityLivingBase attackTarget = this.slime.getAttackTarget();
            return attackTarget != null && attackTarget.isEntityAlive() && (!(attackTarget instanceof EntityPlayer) || !((EntityPlayer)attackTarget).capabilities.disableDamage);
        }
        
        public AISlimeAttack(final EntitySlime slime) {
            this.slime = slime;
            this.setMutexBits(2);
        }
        
        @Override
        public void startExecuting() {
            this.field_179465_b = 300;
            super.startExecuting();
        }
        
        @Override
        public void updateTask() {
            this.slime.faceEntity(this.slime.getAttackTarget(), 10.0f, 10.0f);
            ((SlimeMoveHelper)this.slime.getMoveHelper()).func_179920_a(this.slime.rotationYaw, this.slime.canDamagePlayer());
        }
    }
    
    static class AISlimeHop extends EntityAIBase
    {
        private EntitySlime slime;
        
        @Override
        public void updateTask() {
            ((SlimeMoveHelper)this.slime.getMoveHelper()).setSpeed(1.0);
        }
        
        public AISlimeHop(final EntitySlime slime) {
            this.slime = slime;
            this.setMutexBits(5);
        }
        
        @Override
        public boolean shouldExecute() {
            return true;
        }
    }
    
    static class AISlimeFloat extends EntityAIBase
    {
        private EntitySlime slime;
        
        @Override
        public boolean shouldExecute() {
            return this.slime.isInWater() || this.slime.isInLava();
        }
        
        @Override
        public void updateTask() {
            if (this.slime.getRNG().nextFloat() < 0.8f) {
                this.slime.getJumpHelper().setJumping();
            }
            ((SlimeMoveHelper)this.slime.getMoveHelper()).setSpeed(1.2);
        }
        
        public AISlimeFloat(final EntitySlime slime) {
            this.slime = slime;
            this.setMutexBits(5);
            ((PathNavigateGround)slime.getNavigator()).setCanSwim(true);
        }
    }
}
