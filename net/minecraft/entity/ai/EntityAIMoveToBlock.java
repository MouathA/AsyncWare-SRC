package net.minecraft.entity.ai;

import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;

public abstract class EntityAIMoveToBlock extends EntityAIBase
{
    private boolean isAboveDestination;
    protected int runDelay;
    private final double movementSpeed;
    protected BlockPos destinationBlock;
    private final EntityCreature theEntity;
    private int searchLength;
    private int timeoutCounter;
    private int field_179490_f;
    
    protected boolean getIsAboveDestination() {
        return this.isAboveDestination;
    }
    
    @Override
    public void resetTask() {
    }
    
    @Override
    public boolean continueExecuting() {
        return this.timeoutCounter >= -this.field_179490_f && this.timeoutCounter <= 1200 && this.shouldMoveTo(this.theEntity.worldObj, this.destinationBlock);
    }
    
    protected abstract boolean shouldMoveTo(final World p0, final BlockPos p1);
    
    @Override
    public boolean shouldExecute() {
        if (this.runDelay > 0) {
            --this.runDelay;
            return false;
        }
        this.runDelay = 200 + this.theEntity.getRNG().nextInt(200);
        return this.searchForDestination();
    }
    
    @Override
    public void updateTask() {
        if (this.theEntity.getDistanceSqToCenter(this.destinationBlock.up()) > 1.0) {
            this.isAboveDestination = false;
            ++this.timeoutCounter;
            if (this.timeoutCounter % 40 == 0) {
                this.theEntity.getNavigator().tryMoveToXYZ((float)this.destinationBlock.getX() + 0.5, this.destinationBlock.getY() + 1, (float)this.destinationBlock.getZ() + 0.5, this.movementSpeed);
            }
        }
        else {
            this.isAboveDestination = true;
            --this.timeoutCounter;
        }
    }
    
    @Override
    public void startExecuting() {
        this.theEntity.getNavigator().tryMoveToXYZ((float)this.destinationBlock.getX() + 0.5, this.destinationBlock.getY() + 1, (float)this.destinationBlock.getZ() + 0.5, this.movementSpeed);
        this.timeoutCounter = 0;
        this.field_179490_f = this.theEntity.getRNG().nextInt(this.theEntity.getRNG().nextInt(1200) + 1200) + 1200;
    }
    
    public EntityAIMoveToBlock(final EntityCreature theEntity, final double movementSpeed, final int searchLength) {
        this.destinationBlock = BlockPos.ORIGIN;
        this.theEntity = theEntity;
        this.movementSpeed = movementSpeed;
        this.searchLength = searchLength;
        this.setMutexBits(5);
    }
    
    private boolean searchForDestination() {
        final int searchLength = this.searchLength;
        final BlockPos blockPos = new BlockPos(this.theEntity);
        while (0 >= searchLength) {}
        BlockPos add = null;
    Block_3:
        while (true) {
            for (int i = 0; i <= 0; i = ((i > 0) ? (-i) : (1 - i))) {
                add = blockPos.add(0, -1, i);
                if (this.theEntity.isWithinHomeDistanceFromPosition(add) && this.shouldMoveTo(this.theEntity.worldObj, add)) {
                    break Block_3;
                }
            }
        }
        this.destinationBlock = add;
        return true;
    }
}
