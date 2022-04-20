package net.minecraft.entity.monster;

import net.minecraft.village.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.nbt.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import com.google.common.base.*;

public class EntityIronGolem extends EntityGolem
{
    private int homeCheckTimer;
    private int holdRoseTick;
    private int attackTimer;
    Village villageObj;
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        while (0 < this.rand.nextInt(3)) {
            this.dropItemWithOffset(Item.getItemFromBlock(Blocks.red_flower), 1, (float)BlockFlower.EnumFlowerType.POPPY.getMeta());
            int n2 = 0;
            ++n2;
        }
        int n2 = 3 + this.rand.nextInt(3);
    }
    
    @Override
    public void handleStatusUpdate(final byte b) {
        if (b == 4) {
            this.attackTimer = 10;
            this.playSound("mob.irongolem.throw", 1.0f, 1.0f);
        }
        else if (b == 11) {
            this.holdRoseTick = 400;
        }
        else {
            super.handleStatusUpdate(b);
        }
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean("PlayerCreated", this.isPlayerCreated());
    }
    
    public void setHoldingRose(final boolean b) {
        this.holdRoseTick = (b ? 400 : 0);
        this.worldObj.setEntityState(this, (byte)11);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.setPlayerCreated(nbtTagCompound.getBoolean("PlayerCreated"));
    }
    
    @Override
    protected void collideWithEntity(final Entity entity) {
        if (entity instanceof IMob && !(entity instanceof EntityCreeper) && this.getRNG().nextInt(20) == 0) {
            this.setAttackTarget((EntityLivingBase)entity);
        }
        super.collideWithEntity(entity);
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.irongolem.death";
    }
    
    public Village getVillage() {
        return this.villageObj;
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.attackTimer > 0) {
            --this.attackTimer;
        }
        if (this.holdRoseTick > 0) {
            --this.holdRoseTick;
        }
        if (this.motionX * this.motionX + this.motionZ * this.motionZ > 2.500000277905201E-7 && this.rand.nextInt(5) == 0) {
            final IBlockState blockState = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - 0.20000000298023224), MathHelper.floor_double(this.posZ)));
            if (blockState.getBlock().getMaterial() != Material.air) {
                this.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (this.rand.nextFloat() - 0.5) * this.width, this.getEntityBoundingBox().minY + 0.1, this.posZ + (this.rand.nextFloat() - 0.5) * this.width, 4.0 * (this.rand.nextFloat() - 0.5), 0.5, (this.rand.nextFloat() - 0.5) * 4.0, Block.getStateId(blockState));
            }
        }
    }
    
    @Override
    protected void playStepSound(final BlockPos blockPos, final Block block) {
        this.playSound("mob.irongolem.walk", 1.0f, 1.0f);
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        this.attackTimer = 10;
        this.worldObj.setEntityState(this, (byte)4);
        final boolean attackEntity = entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float)(7 + this.rand.nextInt(15)));
        if (attackEntity) {
            entity.motionY += 0.4000000059604645;
            this.applyEnchantments(this, entity);
        }
        this.playSound("mob.irongolem.throw", 1.0f, 1.0f);
        return attackEntity;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }
    
    public EntityIronGolem(final World world) {
        super(world);
        this.setSize(1.4f, 2.9f);
        ((PathNavigateGround)this.getNavigator()).setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAIAttackOnCollide(this, 1.0, true));
        this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.9, 32.0f));
        this.tasks.addTask(3, new EntityAIMoveThroughVillage(this, 0.6, true));
        this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0));
        this.tasks.addTask(5, new EntityAILookAtVillager(this));
        this.tasks.addTask(6, new EntityAIWander(this, 0.6));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIDefendVillage(this));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(3, new AINearestAttackableTargetNonCreeper(this, EntityLiving.class, 10, false, true, IMob.VISIBLE_MOB_SELECTOR));
    }
    
    @Override
    protected int decreaseAirSupply(final int n) {
        return n;
    }
    
    public int getAttackTimer() {
        return this.attackTimer;
    }
    
    @Override
    public void onDeath(final DamageSource damageSource) {
        if (this != 0 && this.attackingPlayer != null && this.villageObj != null) {
            this.villageObj.setReputationForPlayer(this.attackingPlayer.getName(), -5);
        }
        super.onDeath(damageSource);
    }
    
    public void setPlayerCreated(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(16);
        if (b) {
            this.dataWatcher.updateObject(16, (byte)(watchableObjectByte | 0x1));
        }
        else {
            this.dataWatcher.updateObject(16, (byte)(watchableObjectByte & 0xFFFFFFFE));
        }
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.irongolem.hit";
    }
    
    @Override
    protected void updateAITasks() {
        final int homeCheckTimer = this.homeCheckTimer - 1;
        this.homeCheckTimer = homeCheckTimer;
        if (homeCheckTimer <= 0) {
            this.homeCheckTimer = 70 + this.rand.nextInt(50);
            this.villageObj = this.worldObj.getVillageCollection().getNearestVillage(new BlockPos(this), 32);
            if (this.villageObj == null) {
                this.detachHome();
            }
            else {
                this.setHomePosAndDistance(this.villageObj.getCenter(), (int)(this.villageObj.getVillageRadius() * 0.6f));
            }
        }
        super.updateAITasks();
    }
    
    public int getHoldRoseTick() {
        return this.holdRoseTick;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, 0);
    }
    
    static class AINearestAttackableTargetNonCreeper extends EntityAINearestAttackableTarget
    {
        static double access$000(final AINearestAttackableTargetNonCreeper aiNearestAttackableTargetNonCreeper) {
            return aiNearestAttackableTargetNonCreeper.getTargetDistance();
        }
        
        public AINearestAttackableTargetNonCreeper(final EntityCreature entityCreature, final Class clazz, final int n, final boolean b, final boolean b2, final Predicate predicate) {
            super(entityCreature, clazz, n, b, b2, predicate);
            this.targetEntitySelector = (Predicate)new Predicate(this, predicate, entityCreature) {
                final Predicate val$p_i45858_6_;
                final EntityCreature val$creature;
                final AINearestAttackableTargetNonCreeper this$0;
                
                public boolean apply(final Object o) {
                    return this.apply((EntityLivingBase)o);
                }
                
                public boolean apply(final EntityLivingBase entityLivingBase) {
                    if (this.val$p_i45858_6_ != null && !this.val$p_i45858_6_.apply((Object)entityLivingBase)) {
                        return false;
                    }
                    if (entityLivingBase instanceof EntityCreeper) {
                        return false;
                    }
                    if (entityLivingBase instanceof EntityPlayer) {
                        double access$000 = AINearestAttackableTargetNonCreeper.access$000(this.this$0);
                        if (entityLivingBase.isSneaking()) {
                            access$000 *= 0.800000011920929;
                        }
                        if (entityLivingBase.isInvisible()) {
                            float armorVisibility = ((EntityPlayer)entityLivingBase).getArmorVisibility();
                            if (armorVisibility < 0.1f) {
                                armorVisibility = 0.1f;
                            }
                            access$000 *= 0.7f * armorVisibility;
                        }
                        if (entityLivingBase.getDistanceToEntity(this.val$creature) > access$000) {
                            return false;
                        }
                    }
                    return AINearestAttackableTargetNonCreeper.access$100(this.this$0, entityLivingBase, false);
                }
            };
        }
        
        static boolean access$100(final AINearestAttackableTargetNonCreeper aiNearestAttackableTargetNonCreeper, final EntityLivingBase entityLivingBase, final boolean b) {
            return aiNearestAttackableTargetNonCreeper.isSuitableTarget(entityLivingBase, b);
        }
    }
}
