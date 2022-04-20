package net.minecraft.entity.monster;

import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;

public class EntityMagmaCube extends EntitySlime
{
    @Override
    public boolean getCanSpawnHere() {
        return this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL;
    }
    
    @Override
    protected void handleJumpLava() {
        this.motionY = 0.22f + this.getSlimeSize() * 0.05f;
        this.isAirBorne = true;
    }
    
    @Override
    protected EntitySlime createInstance() {
        return new EntityMagmaCube(this.worldObj);
    }
    
    @Override
    protected EnumParticleTypes getParticleType() {
        return EnumParticleTypes.FLAME;
    }
    
    @Override
    public boolean isBurning() {
        return false;
    }
    
    protected boolean canDamagePlayer() {
        return true;
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        final Item dropItem = this.getDropItem();
        if (dropItem != null && this.getSlimeSize() > 1) {
            int n2 = this.rand.nextInt(4) - 2;
            if (n > 0) {
                n2 += this.rand.nextInt(n + 1);
            }
            while (0 < n2) {
                this.dropItem(dropItem, 1);
                int n3 = 0;
                ++n3;
            }
        }
    }
    
    @Override
    public float getBrightness(final float n) {
        return 1.0f;
    }
    
    @Override
    public boolean isNotColliding() {
        return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), (Entity)this) && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(this.getEntityBoundingBox());
    }
    
    @Override
    protected void alterSquishAmount() {
        this.squishAmount *= 0.9f;
    }
    
    @Override
    protected Item getDropItem() {
        return Items.magma_cream;
    }
    
    public EntityMagmaCube(final World world) {
        super(world);
        this.isImmuneToFire = true;
    }
    
    @Override
    protected int getJumpDelay() {
        return super.getJumpDelay() * 4;
    }
    
    @Override
    protected void jump() {
        this.motionY = 0.42f + this.getSlimeSize() * 0.1f;
        this.isAirBorne = true;
    }
    
    protected boolean makesSoundOnLand() {
        return true;
    }
    
    @Override
    protected int getAttackStrength() {
        return super.getAttackStrength() + 2;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224);
    }
    
    @Override
    public void fall(final float n, final float n2) {
    }
    
    @Override
    protected String getJumpSound() {
        return (this.getSlimeSize() > 1) ? "mob.magmacube.big" : "mob.magmacube.small";
    }
    
    @Override
    public int getTotalArmorValue() {
        return this.getSlimeSize() * 3;
    }
    
    @Override
    public int getBrightnessForRender(final float n) {
        return 15728880;
    }
}
