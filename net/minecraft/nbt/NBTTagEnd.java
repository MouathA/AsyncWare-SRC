package net.minecraft.nbt;

import java.io.*;

public class NBTTagEnd extends NBTBase
{
    @Override
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        nbtSizeTracker.read(64L);
    }
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
    }
    
    @Override
    public String toString() {
        return "END";
    }
    
    @Override
    public NBTBase copy() {
        return new NBTTagEnd();
    }
    
    @Override
    public byte getId() {
        return 0;
    }
}
