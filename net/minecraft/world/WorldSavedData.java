package net.minecraft.world;

import net.minecraft.nbt.*;

public abstract class WorldSavedData
{
    public final String mapName;
    private boolean dirty;
    
    public boolean isDirty() {
        return this.dirty;
    }
    
    public WorldSavedData(final String mapName) {
        this.mapName = mapName;
    }
    
    public void markDirty() {
        this.setDirty(true);
    }
    
    public void setDirty(final boolean dirty) {
        this.dirty = dirty;
    }
    
    public abstract void writeToNBT(final NBTTagCompound p0);
    
    public abstract void readFromNBT(final NBTTagCompound p0);
}
