package net.minecraft.world;

import net.minecraft.inventory.*;

public interface ILockableContainer extends IInteractionObject, IInventory
{
    LockCode getLockCode();
    
    void setLockCode(final LockCode p0);
    
    boolean isLocked();
}
