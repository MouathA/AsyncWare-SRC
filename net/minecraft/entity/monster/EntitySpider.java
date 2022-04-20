package net.minecraft.entity.monster;

import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.*;

public class EntitySpider extends EntityMob
{
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isRemote) {
            this.setBesideClimbableBlock(this.isCollidedHorizontally);
        }
    }
    
    @Override
    protected void playStepSound(final BlockPos blockPos, final Block block) {
        this.playSound("mob.spider.step", 0.15f, 1.0f);
    }
    
    @Override
    protected PathNavigate getNewNavigator(final World world) {
        return new PathNavigateClimber(this, world);
    }
    
    public boolean isPotionApplicable(final PotionEffect potionEffect) {
        return potionEffect.getPotionID() != Potion.poison.id && super.isPotionApplicable(potionEffect);
    }
    
    public EntitySpider(final World world) {
        super(world);
        this.setSize(1.4f, 0.9f);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4f));
        this.tasks.addTask(4, new AISpiderAttack(this, EntityPlayer.class));
        this.tasks.addTask(4, new AISpiderAttack(this, EntityIronGolem.class));
        this.tasks.addTask(5, new EntityAIWander(this, 0.8));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new AISpiderTarget(this, EntityPlayer.class));
        this.targetTasks.addTask(3, new AISpiderTarget(this, EntityIronGolem.class));
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.spider.say";
    }
    
    public void setBesideClimbableBlock(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(16);
        byte b2;
        if (b) {
            b2 = (byte)(watchableObjectByte | 0x1);
        }
        else {
            b2 = (byte)(watchableObjectByte & 0xFFFFFFFE);
        }
        this.dataWatcher.updateObject(16, b2);
    }
    
    public boolean isOnLadder() {
        return this.isBesideClimbableBlock();
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.spider.death";
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896);
    }
    
    @Override
    public float getEyeHeight() {
        return 0.65f;
    }
    
    public boolean isBesideClimbableBlock() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0x0;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)0));
    }
    
    @Override
    public void setInWeb() {
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }
    
    @Override
    protected Item getDropItem() {
        return Items.string;
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.spider.say";
    }
    
    @Override
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficultyInstance, IEntityLivingData onInitialSpawn) {
        onInitialSpawn = super.onInitialSpawn(difficultyInstance, (IEntityLivingData)onInitialSpawn);
        if (this.worldObj.rand.nextInt(100) == 0) {
            final EntitySkeleton entitySkeleton = new EntitySkeleton(this.worldObj);
            entitySkeleton.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
            entitySkeleton.onInitialSpawn(difficultyInstance, null);
            this.worldObj.spawnEntityInWorld(entitySkeleton);
            entitySkeleton.mountEntity(this);
        }
        if (onInitialSpawn == null) {
            onInitialSpawn = new GroupData();
            if (this.worldObj.getDifficulty() == EnumDifficulty.HARD && this.worldObj.rand.nextFloat() < 0.1f * difficultyInstance.getClampedAdditionalDifficulty()) {
                ((GroupData)onInitialSpawn).func_111104_a(this.worldObj.rand);
            }
        }
        if (onInitialSpawn instanceof GroupData) {
            final int potionEffectId = ((GroupData)onInitialSpawn).potionEffectId;
            if (potionEffectId > 0 && Potion.potionTypes[potionEffectId] != null) {
                this.addPotionEffect(new PotionEffect(potionEffectId, Integer.MAX_VALUE));
            }
        }
        return (IEntityLivingData)onInitialSpawn;
    }
    
    @Override
    public double getMountedYOffset() {
        return this.height * 0.5f;
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        super.dropFewItems(b, n);
        if (b && (this.rand.nextInt(3) == 0 || this.rand.nextInt(1 + n) > 0)) {
            this.dropItem(Items.spider_eye, 1);
        }
    }
    
    public static class GroupData implements IEntityLivingData
    {
        public int potionEffectId;
        
        public void func_111104_a(final Random random) {
            final int nextInt = random.nextInt(5);
            if (nextInt <= 1) {
                this.potionEffectId = Potion.moveSpeed.id;
            }
            else if (nextInt <= 2) {
                this.potionEffectId = Potion.damageBoost.id;
            }
            else if (nextInt <= 3) {
                this.potionEffectId = Potion.regeneration.id;
            }
            else if (nextInt <= 4) {
                this.potionEffectId = Potion.invisibility.id;
            }
        }
    }
    
    static class AISpiderTarget extends EntityAINearestAttackableTarget
    {
        public AISpiderTarget(final EntitySpider entitySpider, final Class clazz) {
            super(entitySpider, clazz, true);
        }
        
        @Override
        public boolean shouldExecute() {
            return this.taskOwner.getBrightness(1.0f) < 0.5f && super.shouldExecute();
        }
    }
    
    static class AISpiderAttack extends EntityAIAttackOnCollide
    {
        @Override
        protected double func_179512_a(final EntityLivingBase entityLivingBase) {
            return 4.0f + entityLivingBase.width;
        }
        
        @Override
        public boolean continueExecuting() {
            if (this.attacker.getBrightness(1.0f) >= 0.5f && this.attacker.getRNG().nextInt(100) == 0) {
                this.attacker.setAttackTarget(null);
                return false;
            }
            return super.continueExecuting();
        }
        
        public AISpiderAttack(final EntitySpider entitySpider, final Class clazz) {
            super(entitySpider, clazz, 1.0, true);
        }
    }
}
