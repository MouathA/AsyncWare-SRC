package net.minecraft.entity.monster;

import com.google.common.base.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.potion.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.projectile.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import net.minecraft.entity.ai.*;

public class EntityGuardian extends EntityMob
{
    private EntityAIWander wander;
    private float field_175486_bm;
    private float field_175483_bk;
    private float field_175484_c;
    private float field_175485_bl;
    private EntityLivingBase targetedEntity;
    private boolean field_175480_bp;
    private int field_175479_bo;
    private float field_175482_b;
    
    public void setElder(final boolean b) {
        this.setSyncedFlag(4, b);
        if (b) {
            this.setSize(1.9975f, 1.9975f);
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896);
            this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0);
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0);
            this.enablePersistence();
            this.wander.setExecutionChance(400);
        }
    }
    
    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        if (this.isElder()) {
            if ((this.ticksExisted + this.getEntityId()) % 1200 == 0) {
                final Potion digSlowdown = Potion.digSlowdown;
                for (final EntityPlayerMP entityPlayerMP : this.worldObj.getPlayers(EntityPlayerMP.class, (Predicate)new Predicate(this) {
                    final EntityGuardian this$0;
                    
                    public boolean apply(final Object o) {
                        return this.apply((EntityPlayerMP)o);
                    }
                    
                    public boolean apply(final EntityPlayerMP entityPlayerMP) {
                        return this.this$0.getDistanceSqToEntity(entityPlayerMP) < 2500.0 && entityPlayerMP.theItemInWorldManager.survivalOrAdventure();
                    }
                })) {
                    if (!entityPlayerMP.isPotionActive(digSlowdown) || entityPlayerMP.getActivePotionEffect(digSlowdown).getAmplifier() < 2 || entityPlayerMP.getActivePotionEffect(digSlowdown).getDuration() < 1200) {
                        entityPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(10, 0.0f));
                        entityPlayerMP.addPotionEffect(new PotionEffect(digSlowdown.id, 6000, 2));
                    }
                }
            }
            if (!this.hasHome()) {
                this.setHomePosAndDistance(new BlockPos(this), 16);
            }
        }
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    private void setSyncedFlag(final int n, final boolean b) {
        final int watchableObjectInt = this.dataWatcher.getWatchableObjectInt(16);
        if (b) {
            this.dataWatcher.updateObject(16, watchableObjectInt | n);
        }
        else {
            this.dataWatcher.updateObject(16, watchableObjectInt & ~n);
        }
    }
    
    public boolean func_175472_n() {
        return this.isSyncedFlagSet(2);
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        final int n2 = this.rand.nextInt(3) + this.rand.nextInt(n + 1);
        if (n2 > 0) {
            this.entityDropItem(new ItemStack(Items.prismarine_shard, n2, 0), 1.0f);
        }
        if (this.rand.nextInt(3 + n) > 1) {
            this.entityDropItem(new ItemStack(Items.fish, 1, ItemFishFood.FishType.COD.getMetadata()), 1.0f);
        }
        else if (this.rand.nextInt(3 + n) > 1) {
            this.entityDropItem(new ItemStack(Items.prismarine_crystals, 1, 0), 1.0f);
        }
        if (b && this.isElder()) {
            this.entityDropItem(new ItemStack(Blocks.sponge, 1, 1), 1.0f);
        }
    }
    
    private boolean isSyncedFlagSet(final int n) {
        return (this.dataWatcher.getWatchableObjectInt(16) & n) != 0x0;
    }
    
    protected boolean isValidLightLevel() {
        return true;
    }
    
    static void access$000(final EntityGuardian entityGuardian, final int targetedEntity) {
        entityGuardian.setTargetedEntity(targetedEntity);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.setElder(nbtTagCompound.getBoolean("Elder"));
    }
    
    @Override
    public float getBlockPathWeight(final BlockPos blockPos) {
        return (this.worldObj.getBlockState(blockPos).getBlock().getMaterial() == Material.water) ? (10.0f + this.worldObj.getLightBrightness(blockPos) - 0.5f) : super.getBlockPathWeight(blockPos);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, 0);
        this.dataWatcher.addObject(17, 0);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
    }
    
    private void setTargetedEntity(final int n) {
        this.dataWatcher.updateObject(17, n);
    }
    
    @Override
    protected PathNavigate getNewNavigator(final World world) {
        return new PathNavigateSwimmer(this, world);
    }
    
    public int func_175464_ck() {
        return this.isElder() ? 60 : 80;
    }
    
    public void setElder() {
        this.setElder(true);
        final float n = 1.0f;
        this.field_175485_bl = n;
        this.field_175486_bm = n;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (!this.func_175472_n() && !damageSource.isMagicDamage() && damageSource.getSourceOfDamage() instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase = (EntityLivingBase)damageSource.getSourceOfDamage();
            if (!damageSource.isExplosion()) {
                entityLivingBase.attackEntityFrom(DamageSource.causeThornsDamage(this), 2.0f);
                entityLivingBase.playSound("damage.thorns", 0.5f, 1.0f);
            }
        }
        this.wander.makeUpdate();
        return super.attackEntityFrom(damageSource, n);
    }
    
    @Override
    public boolean getCanSpawnHere() {
        return (this.rand.nextInt(20) == 0 || !this.worldObj.canBlockSeeSky(new BlockPos(this))) && super.getCanSpawnHere();
    }
    
    @Override
    protected void addRandomDrop() {
        this.entityDropItem(((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, EntityFishHook.func_174855_j())).getItemStack(this.rand), 1.0f);
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.worldObj.isRemote) {
            this.field_175484_c = this.field_175482_b;
            if (!this.isInWater()) {
                this.field_175483_bk = 2.0f;
                if (this.motionY > 0.0 && this.field_175480_bp && !this.isSilent()) {
                    this.worldObj.playSound(this.posX, this.posY, this.posZ, "mob.guardian.flop", 1.0f, 1.0f, false);
                }
                this.field_175480_bp = (this.motionY < 0.0 && this.worldObj.isBlockNormalCube(new BlockPos(this).down(), false));
            }
            else if (this.func_175472_n()) {
                if (this.field_175483_bk < 0.5f) {
                    this.field_175483_bk = 4.0f;
                }
                else {
                    this.field_175483_bk += (0.5f - this.field_175483_bk) * 0.1f;
                }
            }
            else {
                this.field_175483_bk += (0.125f - this.field_175483_bk) * 0.2f;
            }
            this.field_175482_b += this.field_175483_bk;
            this.field_175486_bm = this.field_175485_bl;
            if (!this.isInWater()) {
                this.field_175485_bl = this.rand.nextFloat();
            }
            else if (this.func_175472_n()) {
                this.field_175485_bl += (0.0f - this.field_175485_bl) * 0.25f;
            }
            else {
                this.field_175485_bl += (1.0f - this.field_175485_bl) * 0.06f;
            }
            if (this.func_175472_n() && this.isInWater()) {
                final Vec3 look = this.getLook(0.0f);
                while (true) {
                    this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + (this.rand.nextDouble() - 0.5) * this.width - look.xCoord * 1.5, this.posY + this.rand.nextDouble() * this.height - look.yCoord * 1.5, this.posZ + (this.rand.nextDouble() - 0.5) * this.width - look.zCoord * 1.5, 0.0, 0.0, 0.0, new int[0]);
                    int n = 0;
                    ++n;
                }
            }
            else if (this != 0) {
                if (this.field_175479_bo < this.func_175464_ck()) {
                    ++this.field_175479_bo;
                }
                final EntityLivingBase targetedEntity = this.getTargetedEntity();
                if (targetedEntity != null) {
                    this.getLookHelper().setLookPositionWithEntity(targetedEntity, 90.0f, 90.0f);
                    this.getLookHelper().onUpdateLook();
                    final double n2 = this.func_175477_p(0.0f);
                    final double n3 = targetedEntity.posX - this.posX;
                    final double n4 = targetedEntity.posY + targetedEntity.height * 0.5f - (this.posY + this.getEyeHeight());
                    final double n5 = targetedEntity.posZ - this.posZ;
                    final double sqrt = Math.sqrt(n3 * n3 + n4 * n4 + n5 * n5);
                    final double n6 = n3 / sqrt;
                    final double n7 = n4 / sqrt;
                    final double n8 = n5 / sqrt;
                    double nextDouble = this.rand.nextDouble();
                    while (nextDouble < sqrt) {
                        nextDouble += 1.8 - n2 + this.rand.nextDouble() * (1.7 - n2);
                        this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + n6 * nextDouble, this.posY + n7 * nextDouble + this.getEyeHeight(), this.posZ + n8 * nextDouble, 0.0, 0.0, 0.0, new int[0]);
                    }
                }
            }
        }
        if (this.inWater) {
            this.setAir(300);
        }
        else if (this.onGround) {
            this.motionY += 0.5;
            this.motionX += (this.rand.nextFloat() * 2.0f - 1.0f) * 0.4f;
            this.motionZ += (this.rand.nextFloat() * 2.0f - 1.0f) * 0.4f;
            this.rotationYaw = this.rand.nextFloat() * 360.0f;
            this.onGround = false;
            this.isAirBorne = true;
        }
        if (this != 0) {
            this.rotationYaw = this.rotationYawHead;
        }
        super.onLivingUpdate();
    }
    
    @Override
    protected String getLivingSound() {
        return this.isInWater() ? (this.isElder() ? "mob.guardian.elder.idle" : "mob.guardian.idle") : "mob.guardian.land.idle";
    }
    
    public boolean isElder() {
        return this.isSyncedFlagSet(4);
    }
    
    public float func_175471_a(final float n) {
        return this.field_175484_c + (this.field_175482_b - this.field_175484_c) * n;
    }
    
    @Override
    public int getVerticalFaceSpeed() {
        return 180;
    }
    
    @Override
    public float getEyeHeight() {
        return this.height * 0.5f;
    }
    
    private void func_175476_l(final boolean b) {
        this.setSyncedFlag(2, b);
    }
    
    public float func_175469_o(final float n) {
        return this.field_175486_bm + (this.field_175485_bl - this.field_175486_bm) * n;
    }
    
    @Override
    protected String getDeathSound() {
        return this.isInWater() ? (this.isElder() ? "mob.guardian.elder.death" : "mob.guardian.death") : "mob.guardian.land.death";
    }
    
    public EntityGuardian(final World world) {
        super(world);
        this.experienceValue = 10;
        this.setSize(0.85f, 0.85f);
        this.tasks.addTask(4, new AIGuardianAttack(this));
        final EntityAIMoveTowardsRestriction entityAIMoveTowardsRestriction;
        this.tasks.addTask(5, entityAIMoveTowardsRestriction = new EntityAIMoveTowardsRestriction(this, 1.0));
        this.tasks.addTask(7, this.wander = new EntityAIWander(this, 1.0, 80));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityGuardian.class, 12.0f, 0.01f));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.wander.setMutexBits(3);
        entityAIMoveTowardsRestriction.setMutexBits(3);
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, 10, true, false, (Predicate)new GuardianTargetSelector(this)));
        this.moveHelper = new GuardianMoveHelper(this);
        final float nextFloat = this.rand.nextFloat();
        this.field_175482_b = nextFloat;
        this.field_175484_c = nextFloat;
    }
    
    @Override
    public int getTalkInterval() {
        return 160;
    }
    
    @Override
    protected String getHurtSound() {
        return this.isInWater() ? (this.isElder() ? "mob.guardian.elder.hit" : "mob.guardian.hit") : "mob.guardian.land.hit";
    }
    
    public float func_175477_p(final float n) {
        return (this.field_175479_bo + n) / this.func_175464_ck();
    }
    
    @Override
    public void onDataWatcherUpdate(final int n) {
        super.onDataWatcherUpdate(n);
        if (n == 16) {
            if (this.isElder() && this.width < 1.0f) {
                this.setSize(1.9975f, 1.9975f);
            }
        }
        else if (n == 17) {
            this.field_175479_bo = 0;
            this.targetedEntity = null;
        }
    }
    
    static void access$200(final EntityGuardian entityGuardian, final boolean b) {
        entityGuardian.func_175476_l(b);
    }
    
    @Override
    public void moveEntityWithHeading(final float n, final float n2) {
        if (this.isServerWorld()) {
            if (this.isInWater()) {
                this.moveFlying(n, n2, 0.1f);
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                this.motionX *= 0.8999999761581421;
                this.motionY *= 0.8999999761581421;
                this.motionZ *= 0.8999999761581421;
                if (!this.func_175472_n() && this.getAttackTarget() == null) {
                    this.motionY -= 0.005;
                }
            }
            else {
                super.moveEntityWithHeading(n, n2);
            }
        }
        else {
            super.moveEntityWithHeading(n, n2);
        }
    }
    
    @Override
    public boolean isNotColliding() {
        return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), (Entity)this) && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty();
    }
    
    static EntityAIWander access$100(final EntityGuardian entityGuardian) {
        return entityGuardian.wander;
    }
    
    public EntityLivingBase getTargetedEntity() {
        if (this != 0) {
            return null;
        }
        if (!this.worldObj.isRemote) {
            return this.getAttackTarget();
        }
        if (this.targetedEntity != null) {
            return this.targetedEntity;
        }
        final Entity entityByID = this.worldObj.getEntityByID(this.dataWatcher.getWatchableObjectInt(17));
        if (entityByID instanceof EntityLivingBase) {
            return this.targetedEntity = (EntityLivingBase)entityByID;
        }
        return null;
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean("Elder", this.isElder());
    }
    
    static class GuardianTargetSelector implements Predicate
    {
        private EntityGuardian parentEntity;
        
        public boolean apply(final EntityLivingBase entityLivingBase) {
            return (entityLivingBase instanceof EntityPlayer || entityLivingBase instanceof EntitySquid) && entityLivingBase.getDistanceSqToEntity(this.parentEntity) > 9.0;
        }
        
        public boolean apply(final Object o) {
            return this.apply((EntityLivingBase)o);
        }
        
        public GuardianTargetSelector(final EntityGuardian parentEntity) {
            this.parentEntity = parentEntity;
        }
    }
    
    static class AIGuardianAttack extends EntityAIBase
    {
        private int tickCounter;
        private EntityGuardian theEntity;
        
        @Override
        public boolean continueExecuting() {
            return super.continueExecuting() && (this.theEntity.isElder() || this.theEntity.getDistanceSqToEntity(this.theEntity.getAttackTarget()) > 9.0);
        }
        
        @Override
        public void resetTask() {
            EntityGuardian.access$000(this.theEntity, 0);
            this.theEntity.setAttackTarget(null);
            EntityGuardian.access$100(this.theEntity).makeUpdate();
        }
        
        public AIGuardianAttack(final EntityGuardian theEntity) {
            this.theEntity = theEntity;
            this.setMutexBits(3);
        }
        
        @Override
        public void startExecuting() {
            this.tickCounter = -10;
            this.theEntity.getNavigator().clearPathEntity();
            this.theEntity.getLookHelper().setLookPositionWithEntity(this.theEntity.getAttackTarget(), 90.0f, 90.0f);
            this.theEntity.isAirBorne = true;
        }
        
        @Override
        public void updateTask() {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     1: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.theEntity:Lnet/minecraft/entity/monster/EntityGuardian;
            //     4: invokevirtual   net/minecraft/entity/monster/EntityGuardian.getAttackTarget:()Lnet/minecraft/entity/EntityLivingBase;
            //     7: astore_1       
            //     8: aload_0        
            //     9: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.theEntity:Lnet/minecraft/entity/monster/EntityGuardian;
            //    12: invokevirtual   net/minecraft/entity/monster/EntityGuardian.getNavigator:()Lnet/minecraft/pathfinding/PathNavigate;
            //    15: invokevirtual   net/minecraft/pathfinding/PathNavigate.clearPathEntity:()V
            //    18: aload_0        
            //    19: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.theEntity:Lnet/minecraft/entity/monster/EntityGuardian;
            //    22: invokevirtual   net/minecraft/entity/monster/EntityGuardian.getLookHelper:()Lnet/minecraft/entity/ai/EntityLookHelper;
            //    25: aload_1        
            //    26: ldc             90.0
            //    28: ldc             90.0
            //    30: invokevirtual   net/minecraft/entity/ai/EntityLookHelper.setLookPositionWithEntity:(Lnet/minecraft/entity/Entity;FF)V
            //    33: aload_0        
            //    34: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.theEntity:Lnet/minecraft/entity/monster/EntityGuardian;
            //    37: aload_1        
            //    38: invokevirtual   net/minecraft/entity/monster/EntityGuardian.canEntityBeSeen:(Lnet/minecraft/entity/Entity;)Z
            //    41: ifne            58
            //    44: aload_0        
            //    45: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.theEntity:Lnet/minecraft/entity/monster/EntityGuardian;
            //    48: aconst_null    
            //    49: checkcast       Lnet/minecraft/entity/EntityLivingBase;
            //    52: invokevirtual   net/minecraft/entity/monster/EntityGuardian.setAttackTarget:(Lnet/minecraft/entity/EntityLivingBase;)V
            //    55: goto            240
            //    58: aload_0        
            //    59: dup            
            //    60: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.tickCounter:I
            //    63: iconst_1       
            //    64: iadd           
            //    65: putfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.tickCounter:I
            //    68: aload_0        
            //    69: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.tickCounter:I
            //    72: ifne            111
            //    75: aload_0        
            //    76: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.theEntity:Lnet/minecraft/entity/monster/EntityGuardian;
            //    79: aload_0        
            //    80: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.theEntity:Lnet/minecraft/entity/monster/EntityGuardian;
            //    83: invokevirtual   net/minecraft/entity/monster/EntityGuardian.getAttackTarget:()Lnet/minecraft/entity/EntityLivingBase;
            //    86: invokevirtual   net/minecraft/entity/EntityLivingBase.getEntityId:()I
            //    89: invokestatic    net/minecraft/entity/monster/EntityGuardian.access$000:(Lnet/minecraft/entity/monster/EntityGuardian;I)V
            //    92: aload_0        
            //    93: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.theEntity:Lnet/minecraft/entity/monster/EntityGuardian;
            //    96: getfield        net/minecraft/entity/monster/EntityGuardian.worldObj:Lnet/minecraft/world/World;
            //    99: aload_0        
            //   100: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.theEntity:Lnet/minecraft/entity/monster/EntityGuardian;
            //   103: bipush          21
            //   105: invokevirtual   net/minecraft/world/World.setEntityState:(Lnet/minecraft/entity/Entity;B)V
            //   108: goto            236
            //   111: aload_0        
            //   112: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.tickCounter:I
            //   115: aload_0        
            //   116: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.theEntity:Lnet/minecraft/entity/monster/EntityGuardian;
            //   119: invokevirtual   net/minecraft/entity/monster/EntityGuardian.func_175464_ck:()I
            //   122: if_icmplt       220
            //   125: fconst_1       
            //   126: fstore_2       
            //   127: aload_0        
            //   128: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.theEntity:Lnet/minecraft/entity/monster/EntityGuardian;
            //   131: getfield        net/minecraft/entity/monster/EntityGuardian.worldObj:Lnet/minecraft/world/World;
            //   134: invokevirtual   net/minecraft/world/World.getDifficulty:()Lnet/minecraft/world/EnumDifficulty;
            //   137: getstatic       net/minecraft/world/EnumDifficulty.HARD:Lnet/minecraft/world/EnumDifficulty;
            //   140: if_acmpne       147
            //   143: fload_2        
            //   144: fconst_2       
            //   145: fadd           
            //   146: fstore_2       
            //   147: aload_0        
            //   148: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.theEntity:Lnet/minecraft/entity/monster/EntityGuardian;
            //   151: invokevirtual   net/minecraft/entity/monster/EntityGuardian.isElder:()Z
            //   154: ifeq            161
            //   157: fload_2        
            //   158: fconst_2       
            //   159: fadd           
            //   160: fstore_2       
            //   161: aload_1        
            //   162: aload_0        
            //   163: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.theEntity:Lnet/minecraft/entity/monster/EntityGuardian;
            //   166: aload_0        
            //   167: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.theEntity:Lnet/minecraft/entity/monster/EntityGuardian;
            //   170: invokestatic    net/minecraft/util/DamageSource.causeIndirectMagicDamage:(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;)Lnet/minecraft/util/DamageSource;
            //   173: fload_2        
            //   174: invokevirtual   net/minecraft/entity/EntityLivingBase.attackEntityFrom:(Lnet/minecraft/util/DamageSource;F)Z
            //   177: pop            
            //   178: aload_1        
            //   179: aload_0        
            //   180: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.theEntity:Lnet/minecraft/entity/monster/EntityGuardian;
            //   183: invokestatic    net/minecraft/util/DamageSource.causeMobDamage:(Lnet/minecraft/entity/EntityLivingBase;)Lnet/minecraft/util/DamageSource;
            //   186: aload_0        
            //   187: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.theEntity:Lnet/minecraft/entity/monster/EntityGuardian;
            //   190: getstatic       net/minecraft/entity/SharedMonsterAttributes.attackDamage:Lnet/minecraft/entity/ai/attributes/IAttribute;
            //   193: invokevirtual   net/minecraft/entity/monster/EntityGuardian.getEntityAttribute:(Lnet/minecraft/entity/ai/attributes/IAttribute;)Lnet/minecraft/entity/ai/attributes/IAttributeInstance;
            //   196: invokeinterface net/minecraft/entity/ai/attributes/IAttributeInstance.getAttributeValue:()D
            //   201: d2f            
            //   202: invokevirtual   net/minecraft/entity/EntityLivingBase.attackEntityFrom:(Lnet/minecraft/util/DamageSource;F)Z
            //   205: pop            
            //   206: aload_0        
            //   207: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.theEntity:Lnet/minecraft/entity/monster/EntityGuardian;
            //   210: aconst_null    
            //   211: checkcast       Lnet/minecraft/entity/EntityLivingBase;
            //   214: invokevirtual   net/minecraft/entity/monster/EntityGuardian.setAttackTarget:(Lnet/minecraft/entity/EntityLivingBase;)V
            //   217: goto            236
            //   220: aload_0        
            //   221: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.tickCounter:I
            //   224: bipush          60
            //   226: if_icmplt       236
            //   229: aload_0        
            //   230: getfield        net/minecraft/entity/monster/EntityGuardian$AIGuardianAttack.tickCounter:I
            //   233: bipush          20
            //   235: irem           
            //   236: aload_0        
            //   237: invokespecial   net/minecraft/entity/ai/EntityAIBase.updateTask:()V
            //   240: return         
            // 
            // The error that occurred was:
            // 
            // java.lang.IllegalStateException: Inconsistent stack size at #0236 (coming from #0235).
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
        
        @Override
        public boolean shouldExecute() {
            final EntityLivingBase attackTarget = this.theEntity.getAttackTarget();
            return attackTarget != null && attackTarget.isEntityAlive();
        }
    }
    
    static class GuardianMoveHelper extends EntityMoveHelper
    {
        private EntityGuardian entityGuardian;
        
        public GuardianMoveHelper(final EntityGuardian entityGuardian) {
            super(entityGuardian);
            this.entityGuardian = entityGuardian;
        }
        
        @Override
        public void onUpdateMoveHelper() {
            if (this.update && !this.entityGuardian.getNavigator().noPath()) {
                final double n = this.posX - this.entityGuardian.posX;
                final double n2 = this.posY - this.entityGuardian.posY;
                final double n3 = this.posZ - this.entityGuardian.posZ;
                final double n4 = MathHelper.sqrt_double(n * n + n2 * n2 + n3 * n3);
                final double n5 = n2 / n4;
                this.entityGuardian.rotationYaw = this.limitAngle(this.entityGuardian.rotationYaw, (float)(MathHelper.func_181159_b(n3, n) * 180.0 / 3.141592653589793) - 90.0f, 30.0f);
                this.entityGuardian.renderYawOffset = this.entityGuardian.rotationYaw;
                this.entityGuardian.setAIMoveSpeed(this.entityGuardian.getAIMoveSpeed() + ((float)(this.speed * this.entityGuardian.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()) - this.entityGuardian.getAIMoveSpeed()) * 0.125f);
                final double n6 = Math.sin((this.entityGuardian.ticksExisted + this.entityGuardian.getEntityId()) * 0.5) * 0.05;
                final double cos = Math.cos(this.entityGuardian.rotationYaw * 3.1415927f / 180.0f);
                final double sin = Math.sin(this.entityGuardian.rotationYaw * 3.1415927f / 180.0f);
                final EntityGuardian entityGuardian = this.entityGuardian;
                entityGuardian.motionX += n6 * cos;
                final EntityGuardian entityGuardian2 = this.entityGuardian;
                entityGuardian2.motionZ += n6 * sin;
                final double n7 = Math.sin((this.entityGuardian.ticksExisted + this.entityGuardian.getEntityId()) * 0.75) * 0.05;
                final EntityGuardian entityGuardian3 = this.entityGuardian;
                entityGuardian3.motionY += n7 * (sin + cos) * 0.25;
                final EntityGuardian entityGuardian4 = this.entityGuardian;
                entityGuardian4.motionY += this.entityGuardian.getAIMoveSpeed() * n5 * 0.1;
                final EntityLookHelper lookHelper = this.entityGuardian.getLookHelper();
                final double n8 = this.entityGuardian.posX + n / n4 * 2.0;
                final double n9 = this.entityGuardian.getEyeHeight() + this.entityGuardian.posY + n5 / n4 * 1.0;
                final double n10 = this.entityGuardian.posZ + n3 / n4 * 2.0;
                double lookPosX = lookHelper.getLookPosX();
                double lookPosY = lookHelper.getLookPosY();
                double lookPosZ = lookHelper.getLookPosZ();
                if (!lookHelper.getIsLooking()) {
                    lookPosX = n8;
                    lookPosY = n9;
                    lookPosZ = n10;
                }
                this.entityGuardian.getLookHelper().setLookPosition(lookPosX + (n8 - lookPosX) * 0.125, lookPosY + (n9 - lookPosY) * 0.125, lookPosZ + (n10 - lookPosZ) * 0.125, 10.0f, 40.0f);
                EntityGuardian.access$200(this.entityGuardian, true);
            }
            else {
                this.entityGuardian.setAIMoveSpeed(0.0f);
                EntityGuardian.access$200(this.entityGuardian, false);
            }
        }
    }
}
