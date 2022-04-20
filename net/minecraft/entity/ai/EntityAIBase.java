package net.minecraft.entity.ai;

public abstract class EntityAIBase
{
    private int mutexBits;
    
    public boolean isInterruptible() {
        return true;
    }
    
    public boolean continueExecuting() {
        return this.shouldExecute();
    }
    
    public void startExecuting() {
    }
    
    public int getMutexBits() {
        return this.mutexBits;
    }
    
    public abstract boolean shouldExecute();
    
    public void updateTask() {
    }
    
    public void setMutexBits(final int mutexBits) {
        this.mutexBits = mutexBits;
    }
    
    public void resetTask() {
    }
}
