package net.minecraft.entity;

import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.nbt.*;
import net.minecraft.enchantment.*;
import net.minecraft.world.*;
import net.minecraft.network.*;
import net.minecraft.potion.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.item.*;
import net.minecraft.scoreboard.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import com.google.common.base.*;
import java.util.*;

public abstract class EntityLivingBase extends Entity
{
    public float moveForward;
    public int hurtTime;
    protected float lastDamage;
    protected int entityAge;
    protected double newRotationPitch;
    private boolean potionsNeedUpdate;
    protected float movedDistance;
    private EntityLivingBase entityLivingToAttack;
    protected int newPosRotationIncrements;
    public float renderYawOffset;
    private int lastAttackerTime;
    public int maxHurtResistantTime;
    protected double newPosX;
    public float cameraPitch;
    public float limbSwingAmount;
    private EntityLivingBase lastAttacker;
    protected boolean dead;
    private static final UUID sprintingSpeedBoostModifierUUID;
    protected float prevMovedDistance;
    public float prevLimbSwingAmount;
    public float swingProgress;
    private float landMovementFactor;
    private final Map activePotionsMap;
    public float moveStrafing;
    public boolean isSwingInProgress;
    public float prevCameraPitch;
    public float field_70769_ao;
    public int swingProgressInt;
    protected float prevOnGroundSpeedFactor;
    private final CombatTracker _combatTracker;
    private final ItemStack[] previousEquipment;
    public float jumpMovementFactor;
    private static final AttributeModifier sprintingSpeedBoostModifier;
    protected double newRotationYaw;
    public float prevSwingProgress;
    protected float onGroundSpeedFactor;
    protected float randomYawVelocity;
    protected float field_70741_aB;
    protected double newPosY;
    protected int scoreValue;
    protected int recentlyHit;
    private float absorptionAmount;
    public float attackedAtYaw;
    public int arrowHitTimer;
    protected double newPosZ;
    protected boolean isJumping;
    public float rotationYawHead;
    private BaseAttributeMap attributeMap;
    public float field_70770_ap;
    public int deathTime;
    private int jumpTicks;
    public float prevRotationYawHead;
    public float prevRenderYawOffset;
    private int revengeTimer;
    public float limbSwing;
    protected EntityPlayer attackingPlayer;
    public int maxHurtTime;
    
    @Override
    public void setRotationYawHead(final float rotationYawHead) {
        this.rotationYawHead = rotationYawHead;
    }
    
    @Override
    public void fall(final float n, final float n2) {
        super.fall(n, n2);
        final PotionEffect activePotionEffect = this.getActivePotionEffect(Potion.jump);
        final int ceiling_float_int = MathHelper.ceiling_float_int((n - 3.0f - ((activePotionEffect != null) ? ((float)(activePotionEffect.getAmplifier() + 1)) : 0.0f)) * n2);
        if (ceiling_float_int > 0) {
            this.playSound(this.getFallSoundString(ceiling_float_int), 1.0f, 1.0f);
            this.attackEntityFrom(DamageSource.fall, (float)ceiling_float_int);
            final Block block = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - 0.20000000298023224), MathHelper.floor_double(this.posZ))).getBlock();
            if (block.getMaterial() != Material.air) {
                final Block.SoundType stepSound = block.stepSound;
                this.playSound(stepSound.getStepSound(), stepSound.getVolume() * 0.5f, stepSound.getFrequency() * 0.75f);
            }
        }
    }
    
    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(7, 0);
        this.dataWatcher.addObject(8, 0);
        this.dataWatcher.addObject(9, 0);
        this.dataWatcher.addObject(6, 1.0f);
    }
    
    protected float getJumpUpwardsMotion() {
        return 0.42f;
    }
    
    @Override
    public Vec3 getLookVec() {
        return this.getLook(1.0f);
    }
    
    public float getSwingProgress(final float n) {
        float n2 = this.swingProgress - this.prevSwingProgress;
        if (n2 < 0.0f) {
            ++n2;
        }
        return this.prevSwingProgress + n2 * n;
    }
    
    @Override
    public abstract ItemStack[] getInventory();
    
    public boolean isChild() {
        return false;
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        this.setAbsorptionAmount(nbtTagCompound.getFloat("AbsorptionAmount"));
        if (nbtTagCompound.hasKey("Attributes", 9) && this.worldObj != null && !this.worldObj.isRemote) {
            SharedMonsterAttributes.func_151475_a(this.getAttributeMap(), nbtTagCompound.getTagList("Attributes", 10));
        }
        if (nbtTagCompound.hasKey("ActiveEffects", 9)) {
            final NBTTagList tagList = nbtTagCompound.getTagList("ActiveEffects", 10);
            while (0 < tagList.tagCount()) {
                final PotionEffect customPotionEffectFromNBT = PotionEffect.readCustomPotionEffectFromNBT(tagList.getCompoundTagAt(0));
                if (customPotionEffectFromNBT != null) {
                    this.activePotionsMap.put(customPotionEffectFromNBT.getPotionID(), customPotionEffectFromNBT);
                }
                int n = 0;
                ++n;
            }
        }
        if (nbtTagCompound.hasKey("HealF", 99)) {
            this.setHealth(nbtTagCompound.getFloat("HealF"));
        }
        else {
            final NBTBase tag = nbtTagCompound.getTag("Health");
            if (tag == null) {
                this.setHealth(this.getMaxHealth());
            }
            else if (tag.getId() == 5) {
                this.setHealth(((NBTTagFloat)tag).getFloat());
            }
            else if (tag.getId() == 2) {
                this.setHealth(((NBTTagShort)tag).getShort());
            }
        }
        this.hurtTime = nbtTagCompound.getShort("HurtTime");
        this.deathTime = nbtTagCompound.getShort("DeathTime");
        this.revengeTimer = nbtTagCompound.getInteger("HurtByTimestamp");
    }
    
    @Override
    public boolean getAlwaysRenderNameTagForRender() {
        return false;
    }
    
    public void setAbsorptionAmount(float absorptionAmount) {
        if (absorptionAmount < 0.0f) {
            absorptionAmount = 0.0f;
        }
        this.absorptionAmount = absorptionAmount;
    }
    
    public final int getArrowCountInEntity() {
        return this.dataWatcher.getWatchableObjectByte(9);
    }
    
    public final void setArrowCountInEntity(final int n) {
        this.dataWatcher.updateObject(9, (byte)n);
    }
    
    protected float getSoundVolume() {
        return 1.0f;
    }
    
    protected void onDeathUpdate() {
        ++this.deathTime;
        if (this.deathTime != 20) {
            return;
        }
        int experiencePoints = 0;
        if (!this.worldObj.isRemote && (this.recentlyHit > 0 || this.isPlayer()) && this == 0 && this.worldObj.getGameRules().getBoolean("doMobLoot")) {
            experiencePoints = this.getExperiencePoints(this.attackingPlayer);
        }
        this.setDead();
        while (true) {
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, this.rand.nextGaussian() * 0.02, this.rand.nextGaussian() * 0.02, this.rand.nextGaussian() * 0.02, new int[0]);
            ++experiencePoints;
        }
    }
    
    protected void handleJumpLava() {
        this.motionY += 0.03999999910593033;
    }
    
    public boolean canBreatheUnderwater() {
        return false;
    }
    
    private int getArmSwingAnimationEnd() {
        return this.isPotionActive(Potion.digSpeed) ? (6 - (1 + this.getActivePotionEffect(Potion.digSpeed).getAmplifier()) * 1) : (this.isPotionActive(Potion.digSlowdown) ? (6 + (1 + this.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) * 2) : 14);
    }
    
    public void removePotionEffect(final int n) {
        final PotionEffect potionEffect = this.activePotionsMap.remove(n);
        if (potionEffect != null) {
            this.onFinishedPotionEffect(potionEffect);
        }
    }
    
    public void onLivingUpdate() {
        if (this.jumpTicks > 0) {
            --this.jumpTicks;
        }
        if (this.newPosRotationIncrements > 0) {
            final double n = this.posX + (this.newPosX - this.posX) / this.newPosRotationIncrements;
            final double n2 = this.posY + (this.newPosY - this.posY) / this.newPosRotationIncrements;
            final double n3 = this.posZ + (this.newPosZ - this.posZ) / this.newPosRotationIncrements;
            this.rotationYaw += (float)(MathHelper.wrapAngleTo180_double(this.newRotationYaw - this.rotationYaw) / this.newPosRotationIncrements);
            this.rotationPitch += (float)((this.newRotationPitch - this.rotationPitch) / this.newPosRotationIncrements);
            --this.newPosRotationIncrements;
            this.setPosition(n, n2, n3);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }
        else if (this == 0) {
            this.motionX *= 0.98;
            this.motionY *= 0.98;
            this.motionZ *= 0.98;
        }
        if (Math.abs(this.motionX) < 0.005) {
            this.motionX = 0.0;
        }
        if (Math.abs(this.motionY) < 0.005) {
            this.motionY = 0.0;
        }
        if (Math.abs(this.motionZ) < 0.005) {
            this.motionZ = 0.0;
        }
        this.worldObj.theProfiler.startSection("ai");
        if (this <= 0) {
            this.isJumping = false;
            this.moveStrafing = 0.0f;
            this.moveForward = 0.0f;
            this.randomYawVelocity = 0.0f;
        }
        else if (this == 0) {
            this.worldObj.theProfiler.startSection("newAi");
            this.updateEntityActionState();
            this.worldObj.theProfiler.endSection();
        }
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("jump");
        if (this.isJumping) {
            if (this.isInWater()) {
                this.updateAITick();
            }
            else if (this.isInLava()) {
                this.handleJumpLava();
            }
            else if (this.onGround && this.jumpTicks == 0) {
                this.jump();
                this.jumpTicks = 10;
            }
        }
        else {
            this.jumpTicks = 0;
        }
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("travel");
        this.moveStrafing *= 0.98f;
        this.moveForward *= 0.98f;
        this.randomYawVelocity *= 0.9f;
        this.moveEntityWithHeading(this.moveStrafing, this.moveForward);
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("push");
        if (!this.worldObj.isRemote) {
            this.collideWithNearbyEntities();
        }
        this.worldObj.theProfiler.endSection();
    }
    
    protected void applyEntityAttributes() {
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.maxHealth);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.knockbackResistance);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.movementSpeed);
    }
    
    public abstract ItemStack getEquipmentInSlot(final int p0);
    
    public boolean isPotionActive(final int n) {
        return this.activePotionsMap.containsKey(n);
    }
    
    protected boolean isPlayer() {
        return false;
    }
    
    @Override
    public abstract void setCurrentItemOrArmor(final int p0, final ItemStack p1);
    
    static {
        sprintingSpeedBoostModifierUUID = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
        sprintingSpeedBoostModifier = new AttributeModifier(EntityLivingBase.sprintingSpeedBoostModifierUUID, "Sprinting speed boost", 0.30000001192092896, 2).setSaved(false);
    }
    
    @Override
    public boolean canBePushed() {
        return !this.isDead;
    }
    
    protected String getHurtSound() {
        return "game.neutral.hurt";
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setFloat("HealF", this.getHealth());
        nbtTagCompound.setShort("Health", (short)Math.ceil(this.getHealth()));
        nbtTagCompound.setShort("HurtTime", (short)this.hurtTime);
        nbtTagCompound.setInteger("HurtByTimestamp", this.revengeTimer);
        nbtTagCompound.setShort("DeathTime", (short)this.deathTime);
        nbtTagCompound.setFloat("AbsorptionAmount", this.getAbsorptionAmount());
        final ItemStack[] inventory = this.getInventory();
        int n = 0;
        while (0 < inventory.length) {
            final ItemStack itemStack = inventory[0];
            if (itemStack != null) {
                this.attributeMap.removeAttributeModifiers(itemStack.getAttributeModifiers());
            }
            ++n;
        }
        nbtTagCompound.setTag("Attributes", SharedMonsterAttributes.writeBaseAttributeMapToNBT(this.getAttributeMap()));
        final ItemStack[] inventory2 = this.getInventory();
        while (0 < inventory2.length) {
            final ItemStack itemStack2 = inventory2[0];
            if (itemStack2 != null) {
                this.attributeMap.applyAttributeModifiers(itemStack2.getAttributeModifiers());
            }
            ++n;
        }
        if (!this.activePotionsMap.isEmpty()) {
            final NBTTagList list = new NBTTagList();
            final Iterator<PotionEffect> iterator = this.activePotionsMap.values().iterator();
            while (iterator.hasNext()) {
                list.appendTag(iterator.next().writeCustomPotionEffectToNBT(new NBTTagCompound()));
            }
            nbtTagCompound.setTag("ActiveEffects", list);
        }
    }
    
    public boolean canEntityBeSeen(final Entity entity) {
        return this.worldObj.rayTraceBlocks(new Vec3(this.posX, this.posY + this.getEyeHeight(), this.posZ), new Vec3(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ)) == null;
    }
    
    public CombatTracker getCombatTracker() {
        return this._combatTracker;
    }
    
    protected int decreaseAirSupply(final int n) {
        final int respiration = EnchantmentHelper.getRespiration(this);
        return (respiration > 0 && this.rand.nextInt(respiration + 1) > 0) ? n : (n - 1);
    }
    
    public void setHealth(final float n) {
        this.dataWatcher.updateObject(6, MathHelper.clamp_float(n, 0.0f, this.getMaxHealth()));
    }
    
    protected void updateEntityActionState() {
    }
    
    public void setJumping(final boolean isJumping) {
        this.isJumping = isJumping;
    }
    
    public void onDeath(final DamageSource damageSource) {
        final Entity entity = damageSource.getEntity();
        final EntityLivingBase func_94060_bK = this.func_94060_bK();
        if (this.scoreValue >= 0 && func_94060_bK != null) {
            func_94060_bK.addToPlayerScore(this, this.scoreValue);
        }
        if (entity != null) {
            entity.onKillEntity(this);
        }
        this.dead = true;
        this.getCombatTracker().reset();
        if (!this.worldObj.isRemote) {
            if (entity instanceof EntityPlayer) {
                EnchantmentHelper.getLootingModifier((EntityLivingBase)entity);
            }
            if (this == 0 && this.worldObj.getGameRules().getBoolean("doMobLoot")) {
                this.dropFewItems(this.recentlyHit > 0, 0);
                this.dropEquipment(this.recentlyHit > 0, 0);
                if (this.recentlyHit > 0 && this.rand.nextFloat() < 0.025f + 0 * 0.01f) {
                    this.addRandomDrop();
                }
            }
        }
        this.worldObj.setEntityState(this, (byte)3);
    }
    
    protected int getExperiencePoints(final EntityPlayer entityPlayer) {
        return 0;
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }
    
    public boolean isPlayerSleeping() {
        return false;
    }
    
    public void dismountEntity(final Entity entity) {
        final double posX = entity.posX;
        final double n = entity.getEntityBoundingBox().minY + entity.height;
        final double posZ = entity.posZ;
        while (true) {
            final int n2 = (int)(this.posX - 1);
            final int n3 = (int)(this.posZ - 1);
            if (this.worldObj.func_147461_a(this.getEntityBoundingBox().offset(-1, 1.0, -1)).isEmpty()) {
                if (World.doesBlockHaveSolidTopSurface(this.worldObj, new BlockPos(n2, (int)this.posY, n3))) {
                    break;
                }
                if (World.doesBlockHaveSolidTopSurface(this.worldObj, new BlockPos(n2, (int)this.posY - 1, n3)) || this.worldObj.getBlockState(new BlockPos(n2, (int)this.posY - 1, n3)).getBlock().getMaterial() == Material.water) {
                    final double n4 = this.posX - 1;
                    final double n5 = this.posY + 1.0;
                    final double n6 = this.posZ - 1;
                }
            }
            int n7 = 0;
            ++n7;
        }
        this.setPositionAndUpdate(this.posX - 1, this.posY + 1.0, this.posZ - 1);
    }
    
    protected void jump() {
        this.motionY = this.getJumpUpwardsMotion();
        if (this.isPotionActive(Potion.jump)) {
            this.motionY += (this.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1f;
        }
        if (this.isSprinting()) {
            final float n = this.rotationYaw * 0.017453292f;
            this.motionX -= MathHelper.sin(n) * 0.2f;
            this.motionZ += MathHelper.cos(n) * 0.2f;
        }
        this.isAirBorne = true;
    }
    
    protected void dropFewItems(final boolean b, final int n) {
    }
    
    protected void updateArmSwingProgress() {
        final int armSwingAnimationEnd = this.getArmSwingAnimationEnd();
        if (this.isSwingInProgress) {
            ++this.swingProgressInt;
            if (this.swingProgressInt >= armSwingAnimationEnd) {
                this.swingProgressInt = 0;
                this.isSwingInProgress = false;
            }
        }
        else {
            this.swingProgressInt = 0;
        }
        this.swingProgress = this.swingProgressInt / (float)armSwingAnimationEnd;
    }
    
    protected void markPotionsDirty() {
        this.potionsNeedUpdate = true;
    }
    
    public boolean isPotionActive(final Potion potion) {
        return this.activePotionsMap.containsKey(potion.id);
    }
    
    public void setRevengeTarget(final EntityLivingBase entityLivingToAttack) {
        this.entityLivingToAttack = entityLivingToAttack;
        this.revengeTimer = this.ticksExisted;
    }
    
    public EntityLivingBase getAITarget() {
        return this.entityLivingToAttack;
    }
    
    @Override
    public void updateRidden() {
        super.updateRidden();
        this.prevOnGroundSpeedFactor = this.onGroundSpeedFactor;
        this.onGroundSpeedFactor = 0.0f;
        this.fallDistance = 0.0f;
    }
    
    public void setAIMoveSpeed(final float landMovementFactor) {
        this.landMovementFactor = landMovementFactor;
    }
    
    protected void damageArmor(final float n) {
    }
    
    public void moveEntityWithHeading(final float p0, final float p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifne            850
        //     4: aload_0        
        //     5: invokevirtual   net/minecraft/entity/EntityLivingBase.isInWater:()Z
        //     8: ifeq            31
        //    11: aload_0        
        //    12: instanceof      Lnet/minecraft/entity/player/EntityPlayer;
        //    15: ifeq            642
        //    18: aload_0        
        //    19: checkcast       Lnet/minecraft/entity/player/EntityPlayer;
        //    22: getfield        net/minecraft/entity/player/EntityPlayer.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //    25: getfield        net/minecraft/entity/player/PlayerCapabilities.isFlying:Z
        //    28: ifeq            642
        //    31: aload_0        
        //    32: invokevirtual   net/minecraft/entity/EntityLivingBase.isInLava:()Z
        //    35: ifeq            58
        //    38: aload_0        
        //    39: instanceof      Lnet/minecraft/entity/player/EntityPlayer;
        //    42: ifeq            517
        //    45: aload_0        
        //    46: checkcast       Lnet/minecraft/entity/player/EntityPlayer;
        //    49: getfield        net/minecraft/entity/player/EntityPlayer.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //    52: getfield        net/minecraft/entity/player/PlayerCapabilities.isFlying:Z
        //    55: ifeq            517
        //    58: ldc_w           0.91
        //    61: fstore_3       
        //    62: aload_0        
        //    63: getfield        net/minecraft/entity/EntityLivingBase.onGround:Z
        //    66: ifeq            122
        //    69: aload_0        
        //    70: getfield        net/minecraft/entity/EntityLivingBase.worldObj:Lnet/minecraft/world/World;
        //    73: new             Lnet/minecraft/util/BlockPos;
        //    76: dup            
        //    77: aload_0        
        //    78: getfield        net/minecraft/entity/EntityLivingBase.posX:D
        //    81: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    84: aload_0        
        //    85: invokevirtual   net/minecraft/entity/EntityLivingBase.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //    88: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //    91: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    94: iconst_1       
        //    95: isub           
        //    96: aload_0        
        //    97: getfield        net/minecraft/entity/EntityLivingBase.posZ:D
        //   100: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   103: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   106: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   109: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   114: getfield        net/minecraft/block/Block.slipperiness:F
        //   117: ldc_w           0.91
        //   120: fmul           
        //   121: fstore_3       
        //   122: ldc_w           0.16277136
        //   125: fload_3        
        //   126: fload_3        
        //   127: fmul           
        //   128: fload_3        
        //   129: fmul           
        //   130: fdiv           
        //   131: fstore          4
        //   133: aload_0        
        //   134: getfield        net/minecraft/entity/EntityLivingBase.onGround:Z
        //   137: ifeq            152
        //   140: aload_0        
        //   141: invokevirtual   net/minecraft/entity/EntityLivingBase.getAIMoveSpeed:()F
        //   144: fload           4
        //   146: fmul           
        //   147: fstore          5
        //   149: goto            158
        //   152: aload_0        
        //   153: getfield        net/minecraft/entity/EntityLivingBase.jumpMovementFactor:F
        //   156: fstore          5
        //   158: aload_0        
        //   159: fload_1        
        //   160: fload_2        
        //   161: fload           5
        //   163: invokevirtual   net/minecraft/entity/EntityLivingBase.moveFlying:(FFF)V
        //   166: ldc_w           0.91
        //   169: fstore_3       
        //   170: aload_0        
        //   171: getfield        net/minecraft/entity/EntityLivingBase.onGround:Z
        //   174: ifeq            230
        //   177: aload_0        
        //   178: getfield        net/minecraft/entity/EntityLivingBase.worldObj:Lnet/minecraft/world/World;
        //   181: new             Lnet/minecraft/util/BlockPos;
        //   184: dup            
        //   185: aload_0        
        //   186: getfield        net/minecraft/entity/EntityLivingBase.posX:D
        //   189: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   192: aload_0        
        //   193: invokevirtual   net/minecraft/entity/EntityLivingBase.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //   196: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //   199: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   202: iconst_1       
        //   203: isub           
        //   204: aload_0        
        //   205: getfield        net/minecraft/entity/EntityLivingBase.posZ:D
        //   208: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   211: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   214: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   217: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   222: getfield        net/minecraft/block/Block.slipperiness:F
        //   225: ldc_w           0.91
        //   228: fmul           
        //   229: fstore_3       
        //   230: aload_0        
        //   231: if_acmpeq       338
        //   234: ldc_w           0.15
        //   237: fstore          6
        //   239: aload_0        
        //   240: aload_0        
        //   241: getfield        net/minecraft/entity/EntityLivingBase.motionX:D
        //   244: fload           6
        //   246: fneg           
        //   247: f2d            
        //   248: fload           6
        //   250: f2d            
        //   251: invokestatic    net/minecraft/util/MathHelper.clamp_double:(DDD)D
        //   254: putfield        net/minecraft/entity/EntityLivingBase.motionX:D
        //   257: aload_0        
        //   258: aload_0        
        //   259: getfield        net/minecraft/entity/EntityLivingBase.motionZ:D
        //   262: fload           6
        //   264: fneg           
        //   265: f2d            
        //   266: fload           6
        //   268: f2d            
        //   269: invokestatic    net/minecraft/util/MathHelper.clamp_double:(DDD)D
        //   272: putfield        net/minecraft/entity/EntityLivingBase.motionZ:D
        //   275: aload_0        
        //   276: fconst_0       
        //   277: putfield        net/minecraft/entity/EntityLivingBase.fallDistance:F
        //   280: aload_0        
        //   281: getfield        net/minecraft/entity/EntityLivingBase.motionY:D
        //   284: ldc2_w          -0.15
        //   287: dcmpg          
        //   288: ifge            298
        //   291: aload_0        
        //   292: ldc2_w          -0.15
        //   295: putfield        net/minecraft/entity/EntityLivingBase.motionY:D
        //   298: aload_0        
        //   299: invokevirtual   net/minecraft/entity/EntityLivingBase.isSneaking:()Z
        //   302: ifeq            316
        //   305: aload_0        
        //   306: instanceof      Lnet/minecraft/entity/player/EntityPlayer;
        //   309: ifeq            316
        //   312: iconst_1       
        //   313: goto            317
        //   316: iconst_0       
        //   317: istore          7
        //   319: iload           7
        //   321: ifeq            338
        //   324: aload_0        
        //   325: getfield        net/minecraft/entity/EntityLivingBase.motionY:D
        //   328: dconst_0       
        //   329: dcmpg          
        //   330: ifge            338
        //   333: aload_0        
        //   334: dconst_0       
        //   335: putfield        net/minecraft/entity/EntityLivingBase.motionY:D
        //   338: aload_0        
        //   339: aload_0        
        //   340: getfield        net/minecraft/entity/EntityLivingBase.motionX:D
        //   343: aload_0        
        //   344: getfield        net/minecraft/entity/EntityLivingBase.motionY:D
        //   347: aload_0        
        //   348: getfield        net/minecraft/entity/EntityLivingBase.motionZ:D
        //   351: invokevirtual   net/minecraft/entity/EntityLivingBase.moveEntity:(DDD)V
        //   354: aload_0        
        //   355: getfield        net/minecraft/entity/EntityLivingBase.isCollidedHorizontally:Z
        //   358: ifeq            372
        //   361: aload_0        
        //   362: if_acmpeq       372
        //   365: aload_0        
        //   366: ldc2_w          0.2
        //   369: putfield        net/minecraft/entity/EntityLivingBase.motionY:D
        //   372: aload_0        
        //   373: getfield        net/minecraft/entity/EntityLivingBase.worldObj:Lnet/minecraft/world/World;
        //   376: getfield        net/minecraft/world/World.isRemote:Z
        //   379: ifeq            468
        //   382: aload_0        
        //   383: getfield        net/minecraft/entity/EntityLivingBase.worldObj:Lnet/minecraft/world/World;
        //   386: new             Lnet/minecraft/util/BlockPos;
        //   389: dup            
        //   390: aload_0        
        //   391: getfield        net/minecraft/entity/EntityLivingBase.posX:D
        //   394: d2i            
        //   395: iconst_0       
        //   396: aload_0        
        //   397: getfield        net/minecraft/entity/EntityLivingBase.posZ:D
        //   400: d2i            
        //   401: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   404: invokevirtual   net/minecraft/world/World.isBlockLoaded:(Lnet/minecraft/util/BlockPos;)Z
        //   407: ifeq            441
        //   410: aload_0        
        //   411: getfield        net/minecraft/entity/EntityLivingBase.worldObj:Lnet/minecraft/world/World;
        //   414: new             Lnet/minecraft/util/BlockPos;
        //   417: dup            
        //   418: aload_0        
        //   419: getfield        net/minecraft/entity/EntityLivingBase.posX:D
        //   422: d2i            
        //   423: iconst_0       
        //   424: aload_0        
        //   425: getfield        net/minecraft/entity/EntityLivingBase.posZ:D
        //   428: d2i            
        //   429: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   432: invokevirtual   net/minecraft/world/World.getChunkFromBlockCoords:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/world/chunk/Chunk;
        //   435: invokevirtual   net/minecraft/world/chunk/Chunk.isLoaded:()Z
        //   438: ifne            468
        //   441: aload_0        
        //   442: getfield        net/minecraft/entity/EntityLivingBase.posY:D
        //   445: dconst_0       
        //   446: dcmpl          
        //   447: ifle            460
        //   450: aload_0        
        //   451: ldc2_w          -0.1
        //   454: putfield        net/minecraft/entity/EntityLivingBase.motionY:D
        //   457: goto            480
        //   460: aload_0        
        //   461: dconst_0       
        //   462: putfield        net/minecraft/entity/EntityLivingBase.motionY:D
        //   465: goto            480
        //   468: aload_0        
        //   469: dup            
        //   470: getfield        net/minecraft/entity/EntityLivingBase.motionY:D
        //   473: ldc2_w          0.08
        //   476: dsub           
        //   477: putfield        net/minecraft/entity/EntityLivingBase.motionY:D
        //   480: aload_0        
        //   481: dup            
        //   482: getfield        net/minecraft/entity/EntityLivingBase.motionY:D
        //   485: ldc2_w          0.9800000190734863
        //   488: dmul           
        //   489: putfield        net/minecraft/entity/EntityLivingBase.motionY:D
        //   492: aload_0        
        //   493: dup            
        //   494: getfield        net/minecraft/entity/EntityLivingBase.motionX:D
        //   497: fload_3        
        //   498: f2d            
        //   499: dmul           
        //   500: putfield        net/minecraft/entity/EntityLivingBase.motionX:D
        //   503: aload_0        
        //   504: dup            
        //   505: getfield        net/minecraft/entity/EntityLivingBase.motionZ:D
        //   508: fload_3        
        //   509: f2d            
        //   510: dmul           
        //   511: putfield        net/minecraft/entity/EntityLivingBase.motionZ:D
        //   514: goto            850
        //   517: aload_0        
        //   518: getfield        net/minecraft/entity/EntityLivingBase.posY:D
        //   521: dstore_3       
        //   522: aload_0        
        //   523: fload_1        
        //   524: fload_2        
        //   525: ldc_w           0.02
        //   528: invokevirtual   net/minecraft/entity/EntityLivingBase.moveFlying:(FFF)V
        //   531: aload_0        
        //   532: aload_0        
        //   533: getfield        net/minecraft/entity/EntityLivingBase.motionX:D
        //   536: aload_0        
        //   537: getfield        net/minecraft/entity/EntityLivingBase.motionY:D
        //   540: aload_0        
        //   541: getfield        net/minecraft/entity/EntityLivingBase.motionZ:D
        //   544: invokevirtual   net/minecraft/entity/EntityLivingBase.moveEntity:(DDD)V
        //   547: aload_0        
        //   548: dup            
        //   549: getfield        net/minecraft/entity/EntityLivingBase.motionX:D
        //   552: ldc2_w          0.5
        //   555: dmul           
        //   556: putfield        net/minecraft/entity/EntityLivingBase.motionX:D
        //   559: aload_0        
        //   560: dup            
        //   561: getfield        net/minecraft/entity/EntityLivingBase.motionY:D
        //   564: ldc2_w          0.5
        //   567: dmul           
        //   568: putfield        net/minecraft/entity/EntityLivingBase.motionY:D
        //   571: aload_0        
        //   572: dup            
        //   573: getfield        net/minecraft/entity/EntityLivingBase.motionZ:D
        //   576: ldc2_w          0.5
        //   579: dmul           
        //   580: putfield        net/minecraft/entity/EntityLivingBase.motionZ:D
        //   583: aload_0        
        //   584: dup            
        //   585: getfield        net/minecraft/entity/EntityLivingBase.motionY:D
        //   588: ldc2_w          0.02
        //   591: dsub           
        //   592: putfield        net/minecraft/entity/EntityLivingBase.motionY:D
        //   595: aload_0        
        //   596: getfield        net/minecraft/entity/EntityLivingBase.isCollidedHorizontally:Z
        //   599: ifeq            639
        //   602: aload_0        
        //   603: aload_0        
        //   604: getfield        net/minecraft/entity/EntityLivingBase.motionX:D
        //   607: aload_0        
        //   608: getfield        net/minecraft/entity/EntityLivingBase.motionY:D
        //   611: ldc2_w          0.6000000238418579
        //   614: dadd           
        //   615: aload_0        
        //   616: getfield        net/minecraft/entity/EntityLivingBase.posY:D
        //   619: dsub           
        //   620: dload_3        
        //   621: dadd           
        //   622: aload_0        
        //   623: getfield        net/minecraft/entity/EntityLivingBase.motionZ:D
        //   626: invokevirtual   net/minecraft/entity/EntityLivingBase.isOffsetPositionInLiquid:(DDD)Z
        //   629: ifeq            639
        //   632: aload_0        
        //   633: ldc2_w          0.30000001192092896
        //   636: putfield        net/minecraft/entity/EntityLivingBase.motionY:D
        //   639: goto            850
        //   642: aload_0        
        //   643: getfield        net/minecraft/entity/EntityLivingBase.posY:D
        //   646: dstore_3       
        //   647: ldc_w           0.8
        //   650: fstore          5
        //   652: ldc_w           0.02
        //   655: fstore          6
        //   657: aload_0        
        //   658: invokestatic    net/minecraft/enchantment/EnchantmentHelper.getDepthStriderModifier:(Lnet/minecraft/entity/Entity;)I
        //   661: i2f            
        //   662: fstore          7
        //   664: fload           7
        //   666: ldc             3.0
        //   668: fcmpl          
        //   669: ifle            676
        //   672: ldc             3.0
        //   674: fstore          7
        //   676: aload_0        
        //   677: getfield        net/minecraft/entity/EntityLivingBase.onGround:Z
        //   680: ifne            690
        //   683: fload           7
        //   685: ldc             0.5
        //   687: fmul           
        //   688: fstore          7
        //   690: fload           7
        //   692: fconst_0       
        //   693: fcmpl          
        //   694: ifle            734
        //   697: fload           5
        //   699: ldc_w           0.54600006
        //   702: fload           5
        //   704: fsub           
        //   705: fload           7
        //   707: fmul           
        //   708: ldc             3.0
        //   710: fdiv           
        //   711: fadd           
        //   712: fstore          5
        //   714: fload           6
        //   716: aload_0        
        //   717: invokevirtual   net/minecraft/entity/EntityLivingBase.getAIMoveSpeed:()F
        //   720: fconst_1       
        //   721: fmul           
        //   722: fload           6
        //   724: fsub           
        //   725: fload           7
        //   727: fmul           
        //   728: ldc             3.0
        //   730: fdiv           
        //   731: fadd           
        //   732: fstore          6
        //   734: aload_0        
        //   735: fload_1        
        //   736: fload_2        
        //   737: fload           6
        //   739: invokevirtual   net/minecraft/entity/EntityLivingBase.moveFlying:(FFF)V
        //   742: aload_0        
        //   743: aload_0        
        //   744: getfield        net/minecraft/entity/EntityLivingBase.motionX:D
        //   747: aload_0        
        //   748: getfield        net/minecraft/entity/EntityLivingBase.motionY:D
        //   751: aload_0        
        //   752: getfield        net/minecraft/entity/EntityLivingBase.motionZ:D
        //   755: invokevirtual   net/minecraft/entity/EntityLivingBase.moveEntity:(DDD)V
        //   758: aload_0        
        //   759: dup            
        //   760: getfield        net/minecraft/entity/EntityLivingBase.motionX:D
        //   763: fload           5
        //   765: f2d            
        //   766: dmul           
        //   767: putfield        net/minecraft/entity/EntityLivingBase.motionX:D
        //   770: aload_0        
        //   771: dup            
        //   772: getfield        net/minecraft/entity/EntityLivingBase.motionY:D
        //   775: ldc2_w          0.800000011920929
        //   778: dmul           
        //   779: putfield        net/minecraft/entity/EntityLivingBase.motionY:D
        //   782: aload_0        
        //   783: dup            
        //   784: getfield        net/minecraft/entity/EntityLivingBase.motionZ:D
        //   787: fload           5
        //   789: f2d            
        //   790: dmul           
        //   791: putfield        net/minecraft/entity/EntityLivingBase.motionZ:D
        //   794: aload_0        
        //   795: dup            
        //   796: getfield        net/minecraft/entity/EntityLivingBase.motionY:D
        //   799: ldc2_w          0.02
        //   802: dsub           
        //   803: putfield        net/minecraft/entity/EntityLivingBase.motionY:D
        //   806: aload_0        
        //   807: getfield        net/minecraft/entity/EntityLivingBase.isCollidedHorizontally:Z
        //   810: ifeq            850
        //   813: aload_0        
        //   814: aload_0        
        //   815: getfield        net/minecraft/entity/EntityLivingBase.motionX:D
        //   818: aload_0        
        //   819: getfield        net/minecraft/entity/EntityLivingBase.motionY:D
        //   822: ldc2_w          0.6000000238418579
        //   825: dadd           
        //   826: aload_0        
        //   827: getfield        net/minecraft/entity/EntityLivingBase.posY:D
        //   830: dsub           
        //   831: dload_3        
        //   832: dadd           
        //   833: aload_0        
        //   834: getfield        net/minecraft/entity/EntityLivingBase.motionZ:D
        //   837: invokevirtual   net/minecraft/entity/EntityLivingBase.isOffsetPositionInLiquid:(DDD)Z
        //   840: ifeq            850
        //   843: aload_0        
        //   844: ldc2_w          0.30000001192092896
        //   847: putfield        net/minecraft/entity/EntityLivingBase.motionY:D
        //   850: aload_0        
        //   851: aload_0        
        //   852: getfield        net/minecraft/entity/EntityLivingBase.limbSwingAmount:F
        //   855: putfield        net/minecraft/entity/EntityLivingBase.prevLimbSwingAmount:F
        //   858: aload_0        
        //   859: getfield        net/minecraft/entity/EntityLivingBase.posX:D
        //   862: aload_0        
        //   863: getfield        net/minecraft/entity/EntityLivingBase.prevPosX:D
        //   866: dsub           
        //   867: dstore_3       
        //   868: aload_0        
        //   869: getfield        net/minecraft/entity/EntityLivingBase.posZ:D
        //   872: aload_0        
        //   873: getfield        net/minecraft/entity/EntityLivingBase.prevPosZ:D
        //   876: dsub           
        //   877: dstore          5
        //   879: dload_3        
        //   880: dload_3        
        //   881: dmul           
        //   882: dload           5
        //   884: dload           5
        //   886: dmul           
        //   887: dadd           
        //   888: invokestatic    net/minecraft/util/MathHelper.sqrt_double:(D)F
        //   891: ldc_w           4.0
        //   894: fmul           
        //   895: fstore          7
        //   897: fload           7
        //   899: fconst_1       
        //   900: fcmpl          
        //   901: ifle            907
        //   904: fconst_1       
        //   905: fstore          7
        //   907: aload_0        
        //   908: dup            
        //   909: getfield        net/minecraft/entity/EntityLivingBase.limbSwingAmount:F
        //   912: fload           7
        //   914: aload_0        
        //   915: getfield        net/minecraft/entity/EntityLivingBase.limbSwingAmount:F
        //   918: fsub           
        //   919: ldc_w           0.4
        //   922: fmul           
        //   923: fadd           
        //   924: putfield        net/minecraft/entity/EntityLivingBase.limbSwingAmount:F
        //   927: aload_0        
        //   928: dup            
        //   929: getfield        net/minecraft/entity/EntityLivingBase.limbSwing:F
        //   932: aload_0        
        //   933: getfield        net/minecraft/entity/EntityLivingBase.limbSwingAmount:F
        //   936: fadd           
        //   937: putfield        net/minecraft/entity/EntityLivingBase.limbSwing:F
        //   940: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.worldObj.isRemote) {
            this.onLivingUpdate();
            final double n = this.posX - this.prevPosX;
            final double n2 = this.posZ - this.prevPosZ;
            final float n3 = (float)(n * n + n2 * n2);
            float n4 = this.renderYawOffset;
            float n5 = 0.0f;
            this.prevOnGroundSpeedFactor = this.onGroundSpeedFactor;
            float n6 = 0.0f;
            if (n3 > 0.0025000002f) {
                n6 = 1.0f;
                n5 = (float)Math.sqrt(n3) * 3.0f;
                n4 = (float)MathHelper.func_181159_b(n2, n) * 180.0f / 3.1415927f - 90.0f;
            }
            if (this.swingProgress > 0.0f) {
                n4 = this.rotationYaw;
            }
            if (!this.onGround) {
                n6 = 0.0f;
            }
            this.onGroundSpeedFactor += (n6 - this.onGroundSpeedFactor) * 0.3f;
            this.worldObj.theProfiler.startSection("headTurn");
            final float func_110146_f = this.func_110146_f(n4, n5);
            this.worldObj.theProfiler.endSection();
            this.worldObj.theProfiler.startSection("rangeChecks");
            while (this.rotationYaw - this.prevRotationYaw < -180.0f) {
                this.prevRotationYaw -= 360.0f;
            }
            while (this.rotationYaw - this.prevRotationYaw >= 180.0f) {
                this.prevRotationYaw += 360.0f;
            }
            while (this.renderYawOffset - this.prevRenderYawOffset < -180.0f) {
                this.prevRenderYawOffset -= 360.0f;
            }
            while (this.renderYawOffset - this.prevRenderYawOffset >= 180.0f) {
                this.prevRenderYawOffset += 360.0f;
            }
            while (this.rotationPitch - this.prevRotationPitch < -180.0f) {
                this.prevRotationPitch -= 360.0f;
            }
            while (this.rotationPitch - this.prevRotationPitch >= 180.0f) {
                this.prevRotationPitch += 360.0f;
            }
            while (this.rotationYawHead - this.prevRotationYawHead < -180.0f) {
                this.prevRotationYawHead -= 360.0f;
            }
            while (this.rotationYawHead - this.prevRotationYawHead >= 180.0f) {
                this.prevRotationYawHead += 360.0f;
            }
            this.worldObj.theProfiler.endSection();
            this.movedDistance += func_110146_f;
            return;
        }
        final int arrowCountInEntity = this.getArrowCountInEntity();
        if (arrowCountInEntity > 0) {
            if (this.arrowHitTimer <= 0) {
                this.arrowHitTimer = 20 * (30 - arrowCountInEntity);
            }
            --this.arrowHitTimer;
            if (this.arrowHitTimer <= 0) {
                this.setArrowCountInEntity(arrowCountInEntity - 1);
            }
        }
        while (true) {
            final ItemStack itemStack = this.previousEquipment[0];
            final ItemStack equipmentInSlot = this.getEquipmentInSlot(0);
            if (!ItemStack.areItemStacksEqual(equipmentInSlot, itemStack)) {
                ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, new S04PacketEntityEquipment(this.getEntityId(), 0, equipmentInSlot));
                if (itemStack != null) {
                    this.attributeMap.removeAttributeModifiers(itemStack.getAttributeModifiers());
                }
                if (equipmentInSlot != null) {
                    this.attributeMap.applyAttributeModifiers(equipmentInSlot.getAttributeModifiers());
                }
                this.previousEquipment[0] = ((equipmentInSlot == null) ? null : equipmentInSlot.copy());
            }
            int n7 = 0;
            ++n7;
        }
    }
    
    protected float getSoundPitch() {
        return this.isChild() ? ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.5f) : ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
    }
    
    public float getAbsorptionAmount() {
        return this.absorptionAmount;
    }
    
    protected void collideWithEntity(final Entity entity) {
        entity.applyEntityCollision(this);
    }
    
    @Override
    public void mountEntity(final Entity entity) {
        if (this.ridingEntity != null && entity == null) {
            if (!this.worldObj.isRemote) {
                this.dismountEntity(this.ridingEntity);
            }
            if (this.ridingEntity != null) {
                this.ridingEntity.riddenByEntity = null;
            }
            this.ridingEntity = null;
        }
        else {
            super.mountEntity(entity);
        }
    }
    
    public EntityLivingBase func_94060_bK() {
        return (this._combatTracker.func_94550_c() != null) ? this._combatTracker.func_94550_c() : ((this.attackingPlayer != null) ? this.attackingPlayer : ((this.entityLivingToAttack != null) ? this.entityLivingToAttack : null));
    }
    
    public void swingItem() {
        if (!this.isSwingInProgress || this.swingProgressInt >= this.getArmSwingAnimationEnd() / 2 || this.swingProgressInt < 0) {
            this.swingProgressInt = -1;
            this.isSwingInProgress = true;
            if (this.worldObj instanceof WorldServer) {
                ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, new S0BPacketAnimation(this, 0));
            }
        }
    }
    
    protected void updatePotionMetadata() {
        if (this.activePotionsMap.isEmpty()) {
            this.resetPotionEffectMetadata();
            this.setInvisible(false);
        }
        else {
            final int calcPotionLiquidColor = PotionHelper.calcPotionLiquidColor(this.activePotionsMap.values());
            this.dataWatcher.updateObject(8, (byte)(byte)(PotionHelper.getAreAmbient(this.activePotionsMap.values()) ? 1 : 0));
            this.dataWatcher.updateObject(7, calcPotionLiquidColor);
            this.setInvisible(this.isPotionActive(Potion.invisibility.id));
        }
    }
    
    public void removePotionEffectClient(final int n) {
        this.activePotionsMap.remove(n);
    }
    
    public void onItemPickup(final Entity entity, final int n) {
        if (!entity.isDead && !this.worldObj.isRemote) {
            final EntityTracker entityTracker = ((WorldServer)this.worldObj).getEntityTracker();
            if (entity instanceof EntityItem) {
                entityTracker.sendToAllTrackingEntity(entity, new S0DPacketCollectItem(entity.getEntityId(), this.getEntityId()));
            }
            if (entity instanceof EntityArrow) {
                entityTracker.sendToAllTrackingEntity(entity, new S0DPacketCollectItem(entity.getEntityId(), this.getEntityId()));
            }
            if (entity instanceof EntityXPOrb) {
                entityTracker.sendToAllTrackingEntity(entity, new S0DPacketCollectItem(entity.getEntityId(), this.getEntityId()));
            }
        }
    }
    
    @Override
    public void setPositionAndRotation2(final double newPosX, final double newPosY, final double newPosZ, final float n, final float n2, final int newPosRotationIncrements, final boolean b) {
        this.newPosX = newPosX;
        this.newPosY = newPosY;
        this.newPosZ = newPosZ;
        this.newRotationYaw = n;
        this.newRotationPitch = n2;
        this.newPosRotationIncrements = newPosRotationIncrements;
    }
    
    public boolean attackEntityAsMob(final Entity lastAttacker) {
        this.setLastAttacker(lastAttacker);
        return false;
    }
    
    protected void onChangedPotionEffect(final PotionEffect potionEffect, final boolean b) {
        this.potionsNeedUpdate = true;
        if (b && !this.worldObj.isRemote) {
            Potion.potionTypes[potionEffect.getPotionID()].removeAttributesModifiersFromEntity(this, this.getAttributeMap(), potionEffect.getAmplifier());
            Potion.potionTypes[potionEffect.getPotionID()].applyAttributesModifiersToEntity(this, this.getAttributeMap(), potionEffect.getAmplifier());
        }
    }
    
    @Override
    public void performHurtAnimation() {
        final int n = 10;
        this.maxHurtTime = n;
        this.hurtTime = n;
        this.attackedAtYaw = 0.0f;
    }
    
    @Override
    protected void kill() {
        this.attackEntityFrom(DamageSource.outOfWorld, 4.0f);
    }
    
    @Override
    protected void setBeenAttacked() {
        this.velocityChanged = (this.rand.nextDouble() >= this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue());
    }
    
    public Random getRNG() {
        return this.rand;
    }
    
    public final float getHealth() {
        return this.dataWatcher.getWatchableObjectFloat(6);
    }
    
    @Override
    public void setSprinting(final boolean sprinting) {
        super.setSprinting(sprinting);
        final IAttributeInstance entityAttribute = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        if (entityAttribute.getModifier(EntityLivingBase.sprintingSpeedBoostModifierUUID) != null) {
            entityAttribute.removeModifier(EntityLivingBase.sprintingSpeedBoostModifier);
        }
        if (sprinting) {
            entityAttribute.applyModifier(EntityLivingBase.sprintingSpeedBoostModifier);
        }
    }
    
    public abstract ItemStack getCurrentArmor(final int p0);
    
    public BaseAttributeMap getAttributeMap() {
        if (this.attributeMap == null) {
            this.attributeMap = new ServersideAttributeMap();
        }
        return this.attributeMap;
    }
    
    public Team getTeam() {
        return this.worldObj.getScoreboard().getPlayersTeam(this.getUniqueID().toString());
    }
    
    public int getLastAttackerTime() {
        return this.lastAttackerTime;
    }
    
    public EntityLivingBase getLastAttacker() {
        return this.lastAttacker;
    }
    
    public void heal(final float n) {
        final float health = this.getHealth();
        if (health > 0.0f) {
            this.setHealth(health + n);
        }
    }
    
    protected void onNewPotionEffect(final PotionEffect potionEffect) {
        this.potionsNeedUpdate = true;
        if (!this.worldObj.isRemote) {
            Potion.potionTypes[potionEffect.getPotionID()].applyAttributesModifiersToEntity(this, this.getAttributeMap(), potionEffect.getAmplifier());
        }
    }
    
    public int getTotalArmorValue() {
        final ItemStack[] inventory = this.getInventory();
        while (0 < inventory.length) {
            final ItemStack itemStack = inventory[0];
            if (itemStack != null && itemStack.getItem() instanceof ItemArmor) {
                final int n = 0 + ((ItemArmor)itemStack.getItem()).damageReduceAmount;
            }
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    public Collection getActivePotionEffects() {
        return this.activePotionsMap.values();
    }
    
    protected float applyArmorCalculations(final DamageSource damageSource, float n) {
        if (!damageSource.isUnblockable()) {
            final float n2 = n * (25 - this.getTotalArmorValue());
            this.damageArmor(n);
            n = n2 / 25.0f;
        }
        return n;
    }
    
    @Override
    public Vec3 getLook(final float n) {
        if (n == 1.0f) {
            return this.getVectorForRotation(this.rotationPitch, this.rotationYawHead);
        }
        return this.getVectorForRotation(this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * n, this.prevRotationYawHead + (this.rotationYawHead - this.prevRotationYawHead) * n);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }
        if (this.worldObj.isRemote) {
            return false;
        }
        this.entityAge = 0;
        if (this.getHealth() <= 0.0f) {
            return false;
        }
        if (damageSource.isFireDamage() && this.isPotionActive(Potion.fireResistance)) {
            return false;
        }
        if ((damageSource == DamageSource.anvil || damageSource == DamageSource.fallingBlock) && this.getEquipmentInSlot(4) != null) {
            this.getEquipmentInSlot(4).damageItem((int)(n * 4.0f + this.rand.nextFloat() * n * 2.0f), this);
            n *= 0.75f;
        }
        this.limbSwingAmount = 1.5f;
        if (this.hurtResistantTime > this.maxHurtResistantTime / 2.0f) {
            if (n <= this.lastDamage) {
                return false;
            }
            this.damageEntity(damageSource, n - this.lastDamage);
            this.lastDamage = n;
        }
        else {
            this.lastDamage = n;
            this.hurtResistantTime = this.maxHurtResistantTime;
            this.damageEntity(damageSource, n);
            final int n2 = 10;
            this.maxHurtTime = n2;
            this.hurtTime = n2;
        }
        this.attackedAtYaw = 0.0f;
        final Entity entity = damageSource.getEntity();
        if (entity != null) {
            if (entity instanceof EntityLivingBase) {
                this.setRevengeTarget((EntityLivingBase)entity);
            }
            if (entity instanceof EntityPlayer) {
                this.recentlyHit = 100;
                this.attackingPlayer = (EntityPlayer)entity;
            }
            else if (entity instanceof EntityWolf && ((EntityWolf)entity).isTamed()) {
                this.recentlyHit = 100;
                this.attackingPlayer = null;
            }
        }
        if (this.getHealth() <= 0.0f) {
            this.getDeathSound();
            this.onDeath(damageSource);
        }
        else {
            this.getHurtSound();
        }
        return true;
    }
    
    protected float func_110146_f(final float n, float n2) {
        this.renderYawOffset += MathHelper.wrapAngleTo180_float(n - this.renderYawOffset) * 0.3f;
        float wrapAngleTo180_float = MathHelper.wrapAngleTo180_float(this.rotationYaw - this.renderYawOffset);
        final boolean b = wrapAngleTo180_float < -90.0f || wrapAngleTo180_float >= 90.0f;
        if (wrapAngleTo180_float < -75.0f) {
            wrapAngleTo180_float = -75.0f;
        }
        if (wrapAngleTo180_float >= 75.0f) {
            wrapAngleTo180_float = 75.0f;
        }
        this.renderYawOffset = this.rotationYaw - wrapAngleTo180_float;
        if (wrapAngleTo180_float * wrapAngleTo180_float > 2500.0f) {
            this.renderYawOffset += wrapAngleTo180_float * 0.2f;
        }
        if (b) {
            n2 *= -1.0f;
        }
        return n2;
    }
    
    public abstract ItemStack getHeldItem();
    
    public PotionEffect getActivePotionEffect(final Potion potion) {
        return this.activePotionsMap.get(potion.id);
    }
    
    public int getAge() {
        return this.entityAge;
    }
    
    public boolean isOnTeam(final Team team) {
        return this.getTeam() != null && this.getTeam().isSameTeam(team);
    }
    
    protected void resetPotionEffectMetadata() {
        this.dataWatcher.updateObject(8, 0);
        this.dataWatcher.updateObject(7, 0);
    }
    
    public void addPotionEffect(final PotionEffect potionEffect) {
        if (this == potionEffect) {
            if (this.activePotionsMap.containsKey(potionEffect.getPotionID())) {
                this.activePotionsMap.get(potionEffect.getPotionID()).combine(potionEffect);
                this.onChangedPotionEffect(this.activePotionsMap.get(potionEffect.getPotionID()), true);
            }
            else {
                this.activePotionsMap.put(potionEffect.getPotionID(), potionEffect);
                this.onNewPotionEffect(potionEffect);
            }
        }
    }
    
    public void sendEndCombat() {
    }
    
    protected float applyPotionDamageCalculations(final DamageSource damageSource, float n) {
        if (damageSource.isDamageAbsolute()) {
            return n;
        }
        if (this.isPotionActive(Potion.resistance) && damageSource != DamageSource.outOfWorld) {
            final int n2 = (this.getActivePotionEffect(Potion.resistance).getAmplifier() + 1) * 5;
            n = n * 5 / 25.0f;
        }
        if (n <= 0.0f) {
            return 0.0f;
        }
        EnchantmentHelper.getEnchantmentModifierDamage(this.getInventory(), damageSource);
        n = n * 5 / 25.0f;
        return n;
    }
    
    public IAttributeInstance getEntityAttribute(final IAttribute attribute) {
        return this.getAttributeMap().getAttributeInstance(attribute);
    }
    
    protected void damageEntity(final DamageSource damageSource, float n) {
        if (!this.isEntityInvulnerable(damageSource)) {
            n = this.applyArmorCalculations(damageSource, n);
            final float applyPotionDamageCalculations;
            n = (applyPotionDamageCalculations = this.applyPotionDamageCalculations(damageSource, n));
            n = Math.max(n - this.getAbsorptionAmount(), 0.0f);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - (applyPotionDamageCalculations - n));
            if (n != 0.0f) {
                final float health = this.getHealth();
                this.setHealth(health - n);
                this.getCombatTracker().trackDamage(damageSource, health, n);
                this.setAbsorptionAmount(this.getAbsorptionAmount() - n);
            }
        }
    }
    
    @Override
    public void func_181013_g(final float renderYawOffset) {
        this.renderYawOffset = renderYawOffset;
    }
    
    @Override
    public void onEntityUpdate() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_0        
        //     2: getfield        net/minecraft/entity/EntityLivingBase.swingProgress:F
        //     5: putfield        net/minecraft/entity/EntityLivingBase.prevSwingProgress:F
        //     8: aload_0        
        //     9: invokespecial   net/minecraft/entity/Entity.onEntityUpdate:()V
        //    12: aload_0        
        //    13: getfield        net/minecraft/entity/EntityLivingBase.worldObj:Lnet/minecraft/world/World;
        //    16: getfield        net/minecraft/world/World.theProfiler:Lnet/minecraft/profiler/Profiler;
        //    19: ldc_w           "livingEntityBaseTick"
        //    22: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //    25: aload_0        
        //    26: instanceof      Lnet/minecraft/entity/player/EntityPlayer;
        //    29: istore_1       
        //    30: aload_0        
        //    31: ifne            132
        //    34: aload_0        
        //    35: invokevirtual   net/minecraft/entity/EntityLivingBase.isEntityInsideOpaqueBlock:()Z
        //    38: ifeq            53
        //    41: aload_0        
        //    42: getstatic       net/minecraft/util/DamageSource.inWall:Lnet/minecraft/util/DamageSource;
        //    45: fconst_1       
        //    46: invokevirtual   net/minecraft/entity/EntityLivingBase.attackEntityFrom:(Lnet/minecraft/util/DamageSource;F)Z
        //    49: pop            
        //    50: goto            132
        //    53: iload_1        
        //    54: ifeq            132
        //    57: aload_0        
        //    58: getfield        net/minecraft/entity/EntityLivingBase.worldObj:Lnet/minecraft/world/World;
        //    61: invokevirtual   net/minecraft/world/World.getWorldBorder:()Lnet/minecraft/world/border/WorldBorder;
        //    64: aload_0        
        //    65: invokevirtual   net/minecraft/entity/EntityLivingBase.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //    68: invokevirtual   net/minecraft/world/border/WorldBorder.contains:(Lnet/minecraft/util/AxisAlignedBB;)Z
        //    71: ifne            132
        //    74: aload_0        
        //    75: getfield        net/minecraft/entity/EntityLivingBase.worldObj:Lnet/minecraft/world/World;
        //    78: invokevirtual   net/minecraft/world/World.getWorldBorder:()Lnet/minecraft/world/border/WorldBorder;
        //    81: aload_0        
        //    82: invokevirtual   net/minecraft/world/border/WorldBorder.getClosestDistance:(Lnet/minecraft/entity/Entity;)D
        //    85: aload_0        
        //    86: getfield        net/minecraft/entity/EntityLivingBase.worldObj:Lnet/minecraft/world/World;
        //    89: invokevirtual   net/minecraft/world/World.getWorldBorder:()Lnet/minecraft/world/border/WorldBorder;
        //    92: invokevirtual   net/minecraft/world/border/WorldBorder.getDamageBuffer:()D
        //    95: dadd           
        //    96: dstore_2       
        //    97: dload_2        
        //    98: dconst_0       
        //    99: dcmpg          
        //   100: ifge            132
        //   103: aload_0        
        //   104: getstatic       net/minecraft/util/DamageSource.inWall:Lnet/minecraft/util/DamageSource;
        //   107: iconst_1       
        //   108: dload_2        
        //   109: dneg           
        //   110: aload_0        
        //   111: getfield        net/minecraft/entity/EntityLivingBase.worldObj:Lnet/minecraft/world/World;
        //   114: invokevirtual   net/minecraft/world/World.getWorldBorder:()Lnet/minecraft/world/border/WorldBorder;
        //   117: invokevirtual   net/minecraft/world/border/WorldBorder.getDamageAmount:()D
        //   120: dmul           
        //   121: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   124: invokestatic    java/lang/Math.max:(II)I
        //   127: i2f            
        //   128: invokevirtual   net/minecraft/entity/EntityLivingBase.attackEntityFrom:(Lnet/minecraft/util/DamageSource;F)Z
        //   131: pop            
        //   132: aload_0        
        //   133: invokevirtual   net/minecraft/entity/EntityLivingBase.isImmuneToFire:()Z
        //   136: ifne            149
        //   139: aload_0        
        //   140: getfield        net/minecraft/entity/EntityLivingBase.worldObj:Lnet/minecraft/world/World;
        //   143: getfield        net/minecraft/world/World.isRemote:Z
        //   146: ifeq            153
        //   149: aload_0        
        //   150: invokevirtual   net/minecraft/entity/EntityLivingBase.extinguish:()V
        //   153: iload_1        
        //   154: ifeq            174
        //   157: aload_0        
        //   158: checkcast       Lnet/minecraft/entity/player/EntityPlayer;
        //   161: getfield        net/minecraft/entity/player/EntityPlayer.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //   164: getfield        net/minecraft/entity/player/PlayerCapabilities.disableDamage:Z
        //   167: ifeq            174
        //   170: iconst_1       
        //   171: goto            175
        //   174: iconst_0       
        //   175: istore_2       
        //   176: aload_0        
        //   177: ifne            400
        //   180: aload_0        
        //   181: getstatic       net/minecraft/block/material/Material.water:Lnet/minecraft/block/material/Material;
        //   184: invokevirtual   net/minecraft/entity/EntityLivingBase.isInsideOfMaterial:(Lnet/minecraft/block/material/Material;)Z
        //   187: ifeq            393
        //   190: aload_0        
        //   191: invokevirtual   net/minecraft/entity/EntityLivingBase.canBreatheUnderwater:()Z
        //   194: ifne            355
        //   197: aload_0        
        //   198: getstatic       net/minecraft/potion/Potion.waterBreathing:Lnet/minecraft/potion/Potion;
        //   201: getfield        net/minecraft/potion/Potion.id:I
        //   204: invokevirtual   net/minecraft/entity/EntityLivingBase.isPotionActive:(I)Z
        //   207: ifne            355
        //   210: iload_2        
        //   211: ifne            355
        //   214: aload_0        
        //   215: aload_0        
        //   216: aload_0        
        //   217: invokevirtual   net/minecraft/entity/EntityLivingBase.getAir:()I
        //   220: invokevirtual   net/minecraft/entity/EntityLivingBase.decreaseAirSupply:(I)I
        //   223: invokevirtual   net/minecraft/entity/EntityLivingBase.setAir:(I)V
        //   226: aload_0        
        //   227: invokevirtual   net/minecraft/entity/EntityLivingBase.getAir:()I
        //   230: bipush          -20
        //   232: if_icmpne       355
        //   235: aload_0        
        //   236: iconst_0       
        //   237: invokevirtual   net/minecraft/entity/EntityLivingBase.setAir:(I)V
        //   240: aload_0        
        //   241: getfield        net/minecraft/entity/EntityLivingBase.rand:Ljava/util/Random;
        //   244: invokevirtual   java/util/Random.nextFloat:()F
        //   247: aload_0        
        //   248: getfield        net/minecraft/entity/EntityLivingBase.rand:Ljava/util/Random;
        //   251: invokevirtual   java/util/Random.nextFloat:()F
        //   254: fsub           
        //   255: fstore          4
        //   257: aload_0        
        //   258: getfield        net/minecraft/entity/EntityLivingBase.rand:Ljava/util/Random;
        //   261: invokevirtual   java/util/Random.nextFloat:()F
        //   264: aload_0        
        //   265: getfield        net/minecraft/entity/EntityLivingBase.rand:Ljava/util/Random;
        //   268: invokevirtual   java/util/Random.nextFloat:()F
        //   271: fsub           
        //   272: fstore          5
        //   274: aload_0        
        //   275: getfield        net/minecraft/entity/EntityLivingBase.rand:Ljava/util/Random;
        //   278: invokevirtual   java/util/Random.nextFloat:()F
        //   281: aload_0        
        //   282: getfield        net/minecraft/entity/EntityLivingBase.rand:Ljava/util/Random;
        //   285: invokevirtual   java/util/Random.nextFloat:()F
        //   288: fsub           
        //   289: fstore          6
        //   291: aload_0        
        //   292: getfield        net/minecraft/entity/EntityLivingBase.worldObj:Lnet/minecraft/world/World;
        //   295: getstatic       net/minecraft/util/EnumParticleTypes.WATER_BUBBLE:Lnet/minecraft/util/EnumParticleTypes;
        //   298: aload_0        
        //   299: getfield        net/minecraft/entity/EntityLivingBase.posX:D
        //   302: fload           4
        //   304: f2d            
        //   305: dadd           
        //   306: aload_0        
        //   307: getfield        net/minecraft/entity/EntityLivingBase.posY:D
        //   310: fload           5
        //   312: f2d            
        //   313: dadd           
        //   314: aload_0        
        //   315: getfield        net/minecraft/entity/EntityLivingBase.posZ:D
        //   318: fload           6
        //   320: f2d            
        //   321: dadd           
        //   322: aload_0        
        //   323: getfield        net/minecraft/entity/EntityLivingBase.motionX:D
        //   326: aload_0        
        //   327: getfield        net/minecraft/entity/EntityLivingBase.motionY:D
        //   330: aload_0        
        //   331: getfield        net/minecraft/entity/EntityLivingBase.motionZ:D
        //   334: iconst_0       
        //   335: newarray        I
        //   337: invokevirtual   net/minecraft/world/World.spawnParticle:(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V
        //   340: iinc            3, 1
        //   343: goto            240
        //   346: aload_0        
        //   347: getstatic       net/minecraft/util/DamageSource.drown:Lnet/minecraft/util/DamageSource;
        //   350: fconst_2       
        //   351: invokevirtual   net/minecraft/entity/EntityLivingBase.attackEntityFrom:(Lnet/minecraft/util/DamageSource;F)Z
        //   354: pop            
        //   355: aload_0        
        //   356: getfield        net/minecraft/entity/EntityLivingBase.worldObj:Lnet/minecraft/world/World;
        //   359: getfield        net/minecraft/world/World.isRemote:Z
        //   362: ifne            400
        //   365: aload_0        
        //   366: invokevirtual   net/minecraft/entity/EntityLivingBase.isRiding:()Z
        //   369: ifeq            400
        //   372: aload_0        
        //   373: getfield        net/minecraft/entity/EntityLivingBase.ridingEntity:Lnet/minecraft/entity/Entity;
        //   376: instanceof      Lnet/minecraft/entity/EntityLivingBase;
        //   379: ifeq            400
        //   382: aload_0        
        //   383: aconst_null    
        //   384: checkcast       Lnet/minecraft/entity/Entity;
        //   387: invokevirtual   net/minecraft/entity/EntityLivingBase.mountEntity:(Lnet/minecraft/entity/Entity;)V
        //   390: goto            400
        //   393: aload_0        
        //   394: sipush          300
        //   397: invokevirtual   net/minecraft/entity/EntityLivingBase.setAir:(I)V
        //   400: aload_0        
        //   401: ifne            415
        //   404: aload_0        
        //   405: invokevirtual   net/minecraft/entity/EntityLivingBase.isWet:()Z
        //   408: ifeq            415
        //   411: aload_0        
        //   412: invokevirtual   net/minecraft/entity/EntityLivingBase.extinguish:()V
        //   415: aload_0        
        //   416: aload_0        
        //   417: getfield        net/minecraft/entity/EntityLivingBase.cameraPitch:F
        //   420: putfield        net/minecraft/entity/EntityLivingBase.prevCameraPitch:F
        //   423: aload_0        
        //   424: getfield        net/minecraft/entity/EntityLivingBase.hurtTime:I
        //   427: ifle            440
        //   430: aload_0        
        //   431: dup            
        //   432: getfield        net/minecraft/entity/EntityLivingBase.hurtTime:I
        //   435: iconst_1       
        //   436: isub           
        //   437: putfield        net/minecraft/entity/EntityLivingBase.hurtTime:I
        //   440: aload_0        
        //   441: getfield        net/minecraft/entity/EntityLivingBase.hurtResistantTime:I
        //   444: ifle            464
        //   447: aload_0        
        //   448: instanceof      Lnet/minecraft/entity/player/EntityPlayerMP;
        //   451: ifne            464
        //   454: aload_0        
        //   455: dup            
        //   456: getfield        net/minecraft/entity/EntityLivingBase.hurtResistantTime:I
        //   459: iconst_1       
        //   460: isub           
        //   461: putfield        net/minecraft/entity/EntityLivingBase.hurtResistantTime:I
        //   464: aload_0        
        //   465: invokevirtual   net/minecraft/entity/EntityLivingBase.getHealth:()F
        //   468: fconst_0       
        //   469: fcmpg          
        //   470: ifgt            477
        //   473: aload_0        
        //   474: invokevirtual   net/minecraft/entity/EntityLivingBase.onDeathUpdate:()V
        //   477: aload_0        
        //   478: getfield        net/minecraft/entity/EntityLivingBase.recentlyHit:I
        //   481: ifle            497
        //   484: aload_0        
        //   485: dup            
        //   486: getfield        net/minecraft/entity/EntityLivingBase.recentlyHit:I
        //   489: iconst_1       
        //   490: isub           
        //   491: putfield        net/minecraft/entity/EntityLivingBase.recentlyHit:I
        //   494: goto            502
        //   497: aload_0        
        //   498: aconst_null    
        //   499: putfield        net/minecraft/entity/EntityLivingBase.attackingPlayer:Lnet/minecraft/entity/player/EntityPlayer;
        //   502: aload_0        
        //   503: getfield        net/minecraft/entity/EntityLivingBase.lastAttacker:Lnet/minecraft/entity/EntityLivingBase;
        //   506: ifnull          521
        //   509: aload_0        
        //   510: getfield        net/minecraft/entity/EntityLivingBase.lastAttacker:Lnet/minecraft/entity/EntityLivingBase;
        //   513: ifne            521
        //   516: aload_0        
        //   517: aconst_null    
        //   518: putfield        net/minecraft/entity/EntityLivingBase.lastAttacker:Lnet/minecraft/entity/EntityLivingBase;
        //   521: aload_0        
        //   522: getfield        net/minecraft/entity/EntityLivingBase.entityLivingToAttack:Lnet/minecraft/entity/EntityLivingBase;
        //   525: ifnull          568
        //   528: aload_0        
        //   529: getfield        net/minecraft/entity/EntityLivingBase.entityLivingToAttack:Lnet/minecraft/entity/EntityLivingBase;
        //   532: ifne            546
        //   535: aload_0        
        //   536: aconst_null    
        //   537: checkcast       Lnet/minecraft/entity/EntityLivingBase;
        //   540: invokevirtual   net/minecraft/entity/EntityLivingBase.setRevengeTarget:(Lnet/minecraft/entity/EntityLivingBase;)V
        //   543: goto            568
        //   546: aload_0        
        //   547: getfield        net/minecraft/entity/EntityLivingBase.ticksExisted:I
        //   550: aload_0        
        //   551: getfield        net/minecraft/entity/EntityLivingBase.revengeTimer:I
        //   554: isub           
        //   555: bipush          100
        //   557: if_icmple       568
        //   560: aload_0        
        //   561: aconst_null    
        //   562: checkcast       Lnet/minecraft/entity/EntityLivingBase;
        //   565: invokevirtual   net/minecraft/entity/EntityLivingBase.setRevengeTarget:(Lnet/minecraft/entity/EntityLivingBase;)V
        //   568: aload_0        
        //   569: invokevirtual   net/minecraft/entity/EntityLivingBase.updatePotionEffects:()V
        //   572: aload_0        
        //   573: aload_0        
        //   574: getfield        net/minecraft/entity/EntityLivingBase.movedDistance:F
        //   577: putfield        net/minecraft/entity/EntityLivingBase.prevMovedDistance:F
        //   580: aload_0        
        //   581: aload_0        
        //   582: getfield        net/minecraft/entity/EntityLivingBase.renderYawOffset:F
        //   585: putfield        net/minecraft/entity/EntityLivingBase.prevRenderYawOffset:F
        //   588: aload_0        
        //   589: aload_0        
        //   590: getfield        net/minecraft/entity/EntityLivingBase.rotationYawHead:F
        //   593: putfield        net/minecraft/entity/EntityLivingBase.prevRotationYawHead:F
        //   596: aload_0        
        //   597: aload_0        
        //   598: getfield        net/minecraft/entity/EntityLivingBase.rotationYaw:F
        //   601: putfield        net/minecraft/entity/EntityLivingBase.prevRotationYaw:F
        //   604: aload_0        
        //   605: aload_0        
        //   606: getfield        net/minecraft/entity/EntityLivingBase.rotationPitch:F
        //   609: putfield        net/minecraft/entity/EntityLivingBase.prevRotationPitch:F
        //   612: aload_0        
        //   613: getfield        net/minecraft/entity/EntityLivingBase.worldObj:Lnet/minecraft/world/World;
        //   616: getfield        net/minecraft/world/World.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   619: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   622: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    protected void dropEquipment(final boolean b, final int n) {
    }
    
    public void clearActivePotions() {
        final Iterator<Integer> iterator = this.activePotionsMap.keySet().iterator();
        while (iterator.hasNext()) {
            final PotionEffect potionEffect = this.activePotionsMap.get(iterator.next());
            if (!this.worldObj.isRemote) {
                iterator.remove();
                this.onFinishedPotionEffect(potionEffect);
            }
        }
    }
    
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEFINED;
    }
    
    public float getAIMoveSpeed() {
        return this.landMovementFactor;
    }
    
    public void sendEnterCombat() {
    }
    
    protected String getDeathSound() {
        return "game.neutral.die";
    }
    
    public void renderBrokenItemStack(final ItemStack itemStack) {
        this.playSound("random.break", 0.8f, 0.8f + this.worldObj.rand.nextFloat() * 0.4f);
        while (true) {
            final Vec3 rotateYaw = new Vec3((this.rand.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0).rotatePitch(-this.rotationPitch * 3.1415927f / 180.0f).rotateYaw(-this.rotationYaw * 3.1415927f / 180.0f);
            final Vec3 addVector = new Vec3((this.rand.nextFloat() - 0.5) * 0.3, -this.rand.nextFloat() * 0.6 - 0.3, 0.6).rotatePitch(-this.rotationPitch * 3.1415927f / 180.0f).rotateYaw(-this.rotationYaw * 3.1415927f / 180.0f).addVector(this.posX, this.posY + this.getEyeHeight(), this.posZ);
            this.worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, addVector.xCoord, addVector.yCoord, addVector.zCoord, rotateYaw.xCoord, rotateYaw.yCoord + 0.05, rotateYaw.zCoord, Item.getIdFromItem(itemStack.getItem()));
            int n = 0;
            ++n;
        }
    }
    
    protected void updateAITick() {
        this.motionY += 0.03999999910593033;
    }
    
    protected String getFallSoundString(final int n) {
        return (n > 4) ? "game.neutral.hurt.fall.big" : "game.neutral.hurt.fall.small";
    }
    
    protected void onFinishedPotionEffect(final PotionEffect potionEffect) {
        this.potionsNeedUpdate = true;
        if (!this.worldObj.isRemote) {
            Potion.potionTypes[potionEffect.getPotionID()].removeAttributesModifiersFromEntity(this, this.getAttributeMap(), potionEffect.getAmplifier());
        }
    }
    
    public void knockBack(final Entity entity, final float n, final double n2, final double n3) {
        if (this.rand.nextDouble() >= this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue()) {
            this.isAirBorne = true;
            final float sqrt_double = MathHelper.sqrt_double(n2 * n2 + n3 * n3);
            final float n4 = 0.4f;
            this.motionX /= 2.0;
            this.motionY /= 2.0;
            this.motionZ /= 2.0;
            this.motionX -= n2 / sqrt_double * n4;
            this.motionY += n4;
            this.motionZ -= n3 / sqrt_double * n4;
            if (this.motionY > 0.4000000059604645) {
                this.motionY = 0.4000000059604645;
            }
        }
    }
    
    public boolean isEntityUndead() {
        return this.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD;
    }
    
    public void setLastAttacker(final Entity entity) {
        if (entity instanceof EntityLivingBase) {
            this.lastAttacker = (EntityLivingBase)entity;
        }
        else {
            this.lastAttacker = null;
        }
        this.lastAttackerTime = this.ticksExisted;
    }
    
    protected void addRandomDrop() {
    }
    
    @Override
    protected void updateFallState(final double n, final boolean b, final Block block, final BlockPos blockPos) {
        if (!this.isInWater()) {
            this.handleWaterMovement();
        }
        if (!this.worldObj.isRemote && this.fallDistance > 3.0f && b) {
            final IBlockState blockState = this.worldObj.getBlockState(blockPos);
            final Block block2 = blockState.getBlock();
            final float n2 = (float)MathHelper.ceiling_float_int(this.fallDistance - 3.0f);
            if (block2.getMaterial() != Material.air) {
                double n3 = Math.min(0.2f + n2 / 15.0f, 10.0f);
                if (n3 > 2.5) {
                    n3 = 2.5;
                }
                ((WorldServer)this.worldObj).spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX, this.posY, this.posZ, (int)(150.0 * n3), 0.0, 0.0, 0.0, 0.15000000596046448, Block.getStateId(blockState));
            }
        }
        super.updateFallState(n, b, block, blockPos);
    }
    
    public int getRevengeTimer() {
        return this.revengeTimer;
    }
    
    @Override
    public float getRotationYawHead() {
        return this.rotationYawHead;
    }
    
    public EntityLivingBase(final World world) {
        super(world);
        this._combatTracker = new CombatTracker(this);
        this.activePotionsMap = Maps.newHashMap();
        this.previousEquipment = new ItemStack[5];
        this.maxHurtResistantTime = 20;
        this.jumpMovementFactor = 0.02f;
        this.potionsNeedUpdate = true;
        this.applyEntityAttributes();
        this.setHealth(this.getMaxHealth());
        this.preventEntitySpawning = true;
        this.field_70770_ap = (float)((Math.random() + 1.0) * 0.009999999776482582);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.field_70769_ao = (float)Math.random() * 12398.0f;
        this.rotationYaw = (float)(Math.random() * 3.141592653589793 * 2.0);
        this.rotationYawHead = this.rotationYaw;
        this.stepHeight = 0.6f;
    }
    
    protected void updatePotionEffects() {
        final Iterator<Integer> iterator = this.activePotionsMap.keySet().iterator();
        while (iterator.hasNext()) {
            final PotionEffect potionEffect = this.activePotionsMap.get(iterator.next());
            if (!potionEffect.onUpdate(this)) {
                if (this.worldObj.isRemote) {
                    continue;
                }
                iterator.remove();
                this.onFinishedPotionEffect(potionEffect);
            }
            else {
                if (potionEffect.getDuration() % 600 != 0) {
                    continue;
                }
                this.onChangedPotionEffect(potionEffect, false);
            }
        }
        if (this.potionsNeedUpdate) {
            if (!this.worldObj.isRemote) {
                this.updatePotionMetadata();
            }
            this.potionsNeedUpdate = false;
        }
        final int watchableObjectInt = this.dataWatcher.getWatchableObjectInt(7);
        final boolean b = this.dataWatcher.getWatchableObjectByte(8) > 0;
        if (watchableObjectInt > 0) {
            if (!this.isInvisible()) {
                this.rand.nextBoolean();
            }
            else {
                final boolean b2 = this.rand.nextInt(15) == 0;
            }
            if (b) {
                final boolean b3 = false & this.rand.nextInt(5) == 0;
            }
        }
    }
    
    protected void collideWithNearbyEntities() {
        final List entitiesInAABBexcluding = this.worldObj.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox().expand(0.20000000298023224, 0.0, 0.20000000298023224), Predicates.and(EntitySelectors.NOT_SPECTATING, (Predicate)new Predicate(this) {
            final EntityLivingBase this$0;
            
            public boolean apply(final Object o) {
                return this.apply((Entity)o);
            }
            
            public boolean apply(final Entity entity) {
                return entity.canBePushed();
            }
        }));
        if (!entitiesInAABBexcluding.isEmpty()) {
            while (0 < entitiesInAABBexcluding.size()) {
                this.collideWithEntity(entitiesInAABBexcluding.get(0));
                int n = 0;
                ++n;
            }
        }
    }
    
    public final float getMaxHealth() {
        return (float)this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
    }
    
    public boolean isOnSameTeam(final EntityLivingBase entityLivingBase) {
        return this.isOnTeam(entityLivingBase.getTeam());
    }
    
    @Override
    public void onKillCommand() {
        this.attackEntityFrom(DamageSource.outOfWorld, Float.MAX_VALUE);
    }
    
    @Override
    public void handleStatusUpdate(final byte b) {
        if (b == 2) {
            this.limbSwingAmount = 1.5f;
            this.hurtResistantTime = this.maxHurtResistantTime;
            final int n = 10;
            this.maxHurtTime = n;
            this.hurtTime = n;
            this.attackedAtYaw = 0.0f;
            if (this.getHurtSound() != null) {
                this.playSound(this.getHurtSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            }
            this.attackEntityFrom(DamageSource.generic, 0.0f);
        }
        else if (b == 3) {
            if (this.getDeathSound() != null) {
                this.playSound(this.getDeathSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            }
            this.setHealth(0.0f);
            this.onDeath(DamageSource.generic);
        }
        else {
            super.handleStatusUpdate(b);
        }
    }
}
