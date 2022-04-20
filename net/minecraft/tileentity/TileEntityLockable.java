package net.minecraft.tileentity;

import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public abstract class TileEntityLockable extends TileEntity implements IInteractionObject, ILockableContainer
{
    private LockCode code;
    
    @Override
    public LockCode getLockCode() {
        return this.code;
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        if (this.code != null) {
            this.code.toNBT(nbtTagCompound);
        }
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.code = LockCode.fromNBT(nbtTagCompound);
    }
    
    public TileEntityLockable() {
        this.code = LockCode.EMPTY_CODE;
    }
    
    @Override
    public IChatComponent getDisplayName() {
        return this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName(), new Object[0]);
    }
    
    @Override
    public void setLockCode(final LockCode code) {
        this.code = code;
    }
    
    @Override
    public boolean isLocked() {
        return this.code != null && !this.code.isEmpty();
    }
}
