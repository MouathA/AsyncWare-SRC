package net.minecraft.world.gen.structure;

import net.minecraft.world.*;
import net.minecraft.nbt.*;

public class MapGenStructureData extends WorldSavedData
{
    private NBTTagCompound tagCompound;
    
    public MapGenStructureData(final String s) {
        super(s);
        this.tagCompound = new NBTTagCompound();
    }
    
    public static String formatChunkCoords(final int n, final int n2) {
        return "[" + n + "," + n2 + "]";
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setTag("Features", this.tagCompound);
    }
    
    public NBTTagCompound getTagCompound() {
        return this.tagCompound;
    }
    
    public void writeInstance(final NBTTagCompound nbtTagCompound, final int n, final int n2) {
        this.tagCompound.setTag(formatChunkCoords(n, n2), nbtTagCompound);
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        this.tagCompound = nbtTagCompound.getCompoundTag("Features");
    }
}
