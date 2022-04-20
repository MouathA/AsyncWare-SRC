package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import java.util.*;

public class EntityAIFleeSun extends EntityAIBase
{
    private double shelterY;
    private EntityCreature theCreature;
    private double shelterX;
    private double movementSpeed;
    private double shelterZ;
    private World theWorld;
    
    @Override
    public boolean continueExecuting() {
        return !this.theCreature.getNavigator().noPath();
    }
    
    public EntityAIFleeSun(final EntityCreature theCreature, final double movementSpeed) {
        this.theCreature = theCreature;
        this.movementSpeed = movementSpeed;
        this.theWorld = theCreature.worldObj;
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.theWorld.isDaytime()) {
            return false;
        }
        if (!this.theCreature.isBurning()) {
            return false;
        }
        if (!this.theWorld.canSeeSky(new BlockPos(this.theCreature.posX, this.theCreature.getEntityBoundingBox().minY, this.theCreature.posZ))) {
            return false;
        }
        final Vec3 possibleShelter = this.findPossibleShelter();
        if (possibleShelter == null) {
            return false;
        }
        this.shelterX = possibleShelter.xCoord;
        this.shelterY = possibleShelter.yCoord;
        this.shelterZ = possibleShelter.zCoord;
        return true;
    }
    
    @Override
    public void startExecuting() {
        this.theCreature.getNavigator().tryMoveToXYZ(this.shelterX, this.shelterY, this.shelterZ, this.movementSpeed);
    }
    
    private Vec3 findPossibleShelter() {
        final Random rng = this.theCreature.getRNG();
        final BlockPos blockPos = new BlockPos(this.theCreature.posX, this.theCreature.getEntityBoundingBox().minY, this.theCreature.posZ);
        BlockPos add;
        while (true) {
            add = blockPos.add(rng.nextInt(20) - 10, rng.nextInt(6) - 3, rng.nextInt(20) - 10);
            if (!this.theWorld.canSeeSky(add) && this.theCreature.getBlockPathWeight(add) < 0.0f) {
                break;
            }
            int n = 0;
            ++n;
        }
        return new Vec3(add.getX(), add.getY(), add.getZ());
    }
}
