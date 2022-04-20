package net.minecraft.entity.passive;

import net.minecraft.entity.effect.*;
import net.minecraft.entity.monster.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;

public class EntityPig extends EntityAnimal
{
    private final EntityAIControlledByPlayer aiControlledByPlayer;
    
    @Override
    public void onStruckByLightning(final EntityLightningBolt entityLightningBolt) {
        if (!this.worldObj.isRemote && !this.isDead) {
            final EntityPigZombie entityPigZombie = new EntityPigZombie(this.worldObj);
            entityPigZombie.setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
            entityPigZombie.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            entityPigZombie.setNoAI(this.isAIDisabled());
            if (this.hasCustomName()) {
                entityPigZombie.setCustomNameTag(this.getCustomNameTag());
                entityPigZombie.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
            }
            this.worldObj.spawnEntityInWorld(entityPigZombie);
            this.setDead();
        }
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean("Saddle", this.getSaddled());
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable entityAgeable) {
        return this.createChild(entityAgeable);
    }
    
    @Override
    public EntityPig createChild(final EntityAgeable entityAgeable) {
        return new EntityPig(this.worldObj);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, 0);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.setSaddled(nbtTagCompound.getBoolean("Saddle"));
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.pig.say";
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        while (0 < this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + n)) {
            if (this.isBurning()) {
                this.dropItem(Items.cooked_porkchop, 1);
            }
            else {
                this.dropItem(Items.porkchop, 1);
            }
            int n2 = 0;
            ++n2;
        }
        if (this != 0) {
            this.dropItem(Items.saddle, 1);
        }
    }
    
    public void setSaddled(final boolean b) {
        if (b) {
            this.dataWatcher.updateObject(16, 1);
        }
        else {
            this.dataWatcher.updateObject(16, 0);
        }
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.pig.death";
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }
    
    public EntityAIControlledByPlayer getAIControlledByPlayer() {
        return this.aiControlledByPlayer;
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.pig.say";
    }
    
    @Override
    protected Item getDropItem() {
        return this.isBurning() ? Items.cooked_porkchop : Items.porkchop;
    }
    
    @Override
    public boolean interact(final EntityPlayer entityPlayer) {
        if (super.interact(entityPlayer)) {
            return true;
        }
        if (this == 0 || this.worldObj.isRemote || (this.riddenByEntity != null && this.riddenByEntity != entityPlayer)) {
            return false;
        }
        entityPlayer.mountEntity(this);
        return true;
    }
    
    public boolean isBreedingItem(final ItemStack itemStack) {
        return itemStack != null && itemStack.getItem() == Items.carrot;
    }
    
    @Override
    public boolean canBeSteered() {
        final ItemStack heldItem = ((EntityPlayer)this.riddenByEntity).getHeldItem();
        return heldItem != null && heldItem.getItem() == Items.carrot_on_a_stick;
    }
    
    @Override
    public void fall(final float n, final float n2) {
        super.fall(n, n2);
        if (n > 5.0f && this.riddenByEntity instanceof EntityPlayer) {
            ((EntityPlayer)this.riddenByEntity).triggerAchievement(AchievementList.flyPig);
        }
    }
    
    @Override
    protected void playStepSound(final BlockPos blockPos, final Block block) {
        this.playSound("mob.pig.step", 0.15f, 1.0f);
    }
    
    public EntityPig(final World world) {
        super(world);
        this.setSize(0.9f, 0.9f);
        ((PathNavigateGround)this.getNavigator()).setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.25));
        this.tasks.addTask(2, this.aiControlledByPlayer = new EntityAIControlledByPlayer(this, 0.3f));
        this.tasks.addTask(3, new EntityAIMate(this, 1.0));
        this.tasks.addTask(4, new EntityAITempt(this, 1.2, Items.carrot_on_a_stick, false));
        this.tasks.addTask(4, new EntityAITempt(this, 1.2, Items.carrot, false));
        this.tasks.addTask(5, new EntityAIFollowParent(this, 1.1));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }
}
