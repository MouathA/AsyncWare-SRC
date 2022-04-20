package net.minecraft.entity.monster;

import java.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;

public class EntityPigZombie extends EntityZombie
{
    private UUID angerTargetUUID;
    private int angerLevel;
    private int randomSoundDelay;
    private static final AttributeModifier ATTACK_SPEED_BOOST_MODIFIER;
    private static final UUID ATTACK_SPEED_BOOST_MODIFIER_UUID;
    
    @Override
    protected void setEquipmentBasedOnDifficulty(final DifficultyInstance difficultyInstance) {
        this.setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }
        final Entity entity = damageSource.getEntity();
        if (entity instanceof EntityPlayer) {
            this.becomeAngryAt(entity);
        }
        return super.attackEntityFrom(damageSource, n);
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        int n2 = 0;
        while (0 < this.rand.nextInt(2 + n)) {
            this.dropItem(Items.rotten_flesh, 1);
            ++n2;
        }
        while (0 < this.rand.nextInt(2 + n)) {
            this.dropItem(Items.gold_nugget, 1);
            ++n2;
        }
    }
    
    static {
        ATTACK_SPEED_BOOST_MODIFIER_UUID = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
        ATTACK_SPEED_BOOST_MODIFIER = new AttributeModifier(EntityPigZombie.ATTACK_SPEED_BOOST_MODIFIER_UUID, "Attacking speed boost", 0.05, 0).setSaved(false);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setShort("Anger", (short)this.angerLevel);
        if (this.angerTargetUUID != null) {
            nbtTagCompound.setString("HurtBy", this.angerTargetUUID.toString());
        }
        else {
            nbtTagCompound.setString("HurtBy", "");
        }
    }
    
    @Override
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficultyInstance, final IEntityLivingData entityLivingData) {
        super.onInitialSpawn(difficultyInstance, entityLivingData);
        this.setVillager(false);
        return entityLivingData;
    }
    
    @Override
    public boolean isNotColliding() {
        return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), (Entity)this) && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(this.getEntityBoundingBox());
    }
    
    private void becomeAngryAt(final Entity entity) {
        this.angerLevel = 400 + this.rand.nextInt(400);
        this.randomSoundDelay = this.rand.nextInt(40);
        if (entity instanceof EntityLivingBase) {
            this.setRevengeTarget((EntityLivingBase)entity);
        }
    }
    
    @Override
    public void setRevengeTarget(final EntityLivingBase revengeTarget) {
        super.setRevengeTarget(revengeTarget);
        if (revengeTarget != null) {
            this.angerTargetUUID = revengeTarget.getUniqueID();
        }
    }
    
    @Override
    protected void addRandomDrop() {
        this.dropItem(Items.gold_ingot, 1);
    }
    
    @Override
    public boolean getCanSpawnHere() {
        return this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL;
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.zombiepig.zpig";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.zombiepig.zpighurt";
    }
    
    @Override
    protected void updateAITasks() {
        final IAttributeInstance entityAttribute = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        if (this > 0) {
            if (!this.isChild() && !entityAttribute.hasModifier(EntityPigZombie.ATTACK_SPEED_BOOST_MODIFIER)) {
                entityAttribute.applyModifier(EntityPigZombie.ATTACK_SPEED_BOOST_MODIFIER);
            }
            --this.angerLevel;
        }
        else if (entityAttribute.hasModifier(EntityPigZombie.ATTACK_SPEED_BOOST_MODIFIER)) {
            entityAttribute.removeModifier(EntityPigZombie.ATTACK_SPEED_BOOST_MODIFIER);
        }
        if (this.randomSoundDelay > 0 && --this.randomSoundDelay == 0) {
            this.playSound("mob.zombiepig.zpigangry", this.getSoundVolume() * 2.0f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) * 1.8f);
        }
        if (this.angerLevel > 0 && this.angerTargetUUID != null && this.getAITarget() == null) {
            final EntityPlayer playerEntityByUUID = this.worldObj.getPlayerEntityByUUID(this.angerTargetUUID);
            this.setRevengeTarget(playerEntityByUUID);
            this.attackingPlayer = playerEntityByUUID;
            this.recentlyHit = this.getRevengeTimer();
        }
        super.updateAITasks();
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(EntityPigZombie.reinforcementChance).setBaseValue(0.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0);
    }
    
    @Override
    protected void applyEntityAI() {
        this.targetTasks.addTask(1, new AIHurtByAggressor(this));
        this.targetTasks.addTask(2, new AITargetAggressor(this));
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.angerLevel = nbtTagCompound.getShort("Anger");
        final String string = nbtTagCompound.getString("HurtBy");
        if (string.length() > 0) {
            this.angerTargetUUID = UUID.fromString(string);
            final EntityPlayer playerEntityByUUID = this.worldObj.getPlayerEntityByUUID(this.angerTargetUUID);
            this.setRevengeTarget(playerEntityByUUID);
            if (playerEntityByUUID != null) {
                this.attackingPlayer = playerEntityByUUID;
                this.recentlyHit = this.getRevengeTimer();
            }
        }
    }
    
    @Override
    public boolean interact(final EntityPlayer entityPlayer) {
        return false;
    }
    
    static void access$000(final EntityPigZombie entityPigZombie, final Entity entity) {
        entityPigZombie.becomeAngryAt(entity);
    }
    
    public EntityPigZombie(final World world) {
        super(world);
        this.isImmuneToFire = true;
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.zombiepig.zpigdeath";
    }
    
    static class AITargetAggressor extends EntityAINearestAttackableTarget
    {
        public AITargetAggressor(final EntityPigZombie entityPigZombie) {
            super(entityPigZombie, EntityPlayer.class, true);
        }
        
        @Override
        public boolean shouldExecute() {
            return ((EntityPigZombie)this.taskOwner).isAngry() && super.shouldExecute();
        }
    }
    
    static class AIHurtByAggressor extends EntityAIHurtByTarget
    {
        @Override
        protected void setEntityAttackTarget(final EntityCreature entityCreature, final EntityLivingBase entityLivingBase) {
            super.setEntityAttackTarget(entityCreature, entityLivingBase);
            if (entityCreature instanceof EntityPigZombie) {
                EntityPigZombie.access$000((EntityPigZombie)entityCreature, entityLivingBase);
            }
        }
        
        public AIHurtByAggressor(final EntityPigZombie entityPigZombie) {
            super(entityPigZombie, true, new Class[0]);
        }
    }
}
