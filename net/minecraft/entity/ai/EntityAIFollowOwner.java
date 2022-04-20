package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.player.*;

public class EntityAIFollowOwner extends EntityAIBase
{
    private int field_75343_h;
    private PathNavigate petPathfinder;
    float minDist;
    float maxDist;
    World theWorld;
    private EntityLivingBase theOwner;
    private boolean field_75344_i;
    private EntityTameable thePet;
    private double followSpeed;
    
    @Override
    public void updateTask() {
        this.thePet.getLookHelper().setLookPositionWithEntity(this.theOwner, 10.0f, (float)this.thePet.getVerticalFaceSpeed());
        if (!this.thePet.isSitting() && --this.field_75343_h <= 0) {
            this.field_75343_h = 10;
            if (!this.petPathfinder.tryMoveToEntityLiving(this.theOwner, this.followSpeed) && !this.thePet.getLeashed() && this.thePet.getDistanceSqToEntity(this.theOwner) >= 144.0) {
                final int n = MathHelper.floor_double(this.theOwner.posX) - 2;
                final int n2 = MathHelper.floor_double(this.theOwner.posZ) - 2;
                final int floor_double = MathHelper.floor_double(this.theOwner.getEntityBoundingBox().minY);
                while (!World.doesBlockHaveSolidTopSurface(this.theWorld, new BlockPos(n + 0, floor_double - 1, n2 + 0)) || this != new BlockPos(n + 0, floor_double, n2 + 0) || this != new BlockPos(n + 0, floor_double + 1, n2 + 0)) {
                    int n3 = 0;
                    ++n3;
                }
                this.thePet.setLocationAndAngles(n + 0 + 0.5f, floor_double, n2 + 0 + 0.5f, this.thePet.rotationYaw, this.thePet.rotationPitch);
                this.petPathfinder.clearPathEntity();
            }
        }
    }
    
    public EntityAIFollowOwner(final EntityTameable thePet, final double followSpeed, final float minDist, final float maxDist) {
        this.thePet = thePet;
        this.theWorld = thePet.worldObj;
        this.followSpeed = followSpeed;
        this.petPathfinder = thePet.getNavigator();
        this.minDist = minDist;
        this.maxDist = maxDist;
        this.setMutexBits(3);
        if (!(thePet.getNavigator() instanceof PathNavigateGround)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        }
    }
    
    @Override
    public boolean continueExecuting() {
        return !this.petPathfinder.noPath() && this.thePet.getDistanceSqToEntity(this.theOwner) > this.maxDist * this.maxDist && !this.thePet.isSitting();
    }
    
    @Override
    public void resetTask() {
        this.theOwner = null;
        this.petPathfinder.clearPathEntity();
        ((PathNavigateGround)this.thePet.getNavigator()).setAvoidsWater(true);
    }
    
    @Override
    public void startExecuting() {
        this.field_75343_h = 0;
        this.field_75344_i = ((PathNavigateGround)this.thePet.getNavigator()).getAvoidsWater();
        ((PathNavigateGround)this.thePet.getNavigator()).setAvoidsWater(false);
    }
    
    @Override
    public boolean shouldExecute() {
        final EntityLivingBase owner = this.thePet.getOwner();
        if (owner == null) {
            return false;
        }
        if (owner instanceof EntityPlayer && ((EntityPlayer)owner).isSpectator()) {
            return false;
        }
        if (this.thePet.isSitting()) {
            return false;
        }
        if (this.thePet.getDistanceSqToEntity(owner) < this.minDist * this.minDist) {
            return false;
        }
        this.theOwner = owner;
        return true;
    }
}
