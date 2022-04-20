package net.minecraft.world;

import net.minecraft.nbt.*;

public class LockCode
{
    private final String lock;
    public static final LockCode EMPTY_CODE;
    
    public boolean isEmpty() {
        return this.lock == null || this.lock.isEmpty();
    }
    
    public String getLock() {
        return this.lock;
    }
    
    public static LockCode fromNBT(final NBTTagCompound nbtTagCompound) {
        if (nbtTagCompound.hasKey("Lock", 8)) {
            return new LockCode(nbtTagCompound.getString("Lock"));
        }
        return LockCode.EMPTY_CODE;
    }
    
    public LockCode(final String lock) {
        this.lock = lock;
    }
    
    static {
        EMPTY_CODE = new LockCode("");
    }
    
    public void toNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setString("Lock", this.lock);
    }
}
