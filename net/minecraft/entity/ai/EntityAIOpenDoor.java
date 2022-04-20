package net.minecraft.entity.ai;

import net.minecraft.entity.*;

public class EntityAIOpenDoor extends EntityAIDoorInteract
{
    boolean closeDoor;
    int closeDoorTemporisation;
    
    public EntityAIOpenDoor(final EntityLiving theEntity, final boolean closeDoor) {
        super(theEntity);
        this.theEntity = theEntity;
        this.closeDoor = closeDoor;
    }
    
    @Override
    public void resetTask() {
        if (this.closeDoor) {
            this.doorBlock.toggleDoor(this.theEntity.worldObj, this.doorPosition, false);
        }
    }
    
    @Override
    public boolean continueExecuting() {
        return this.closeDoor && this.closeDoorTemporisation > 0 && super.continueExecuting();
    }
    
    @Override
    public void updateTask() {
        --this.closeDoorTemporisation;
        super.updateTask();
    }
    
    @Override
    public void startExecuting() {
        this.closeDoorTemporisation = 20;
        this.doorBlock.toggleDoor(this.theEntity.worldObj, this.doorPosition, true);
    }
}
