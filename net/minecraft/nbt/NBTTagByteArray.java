package net.minecraft.nbt;

import java.io.*;
import java.util.*;

public class NBTTagByteArray extends NBTBase
{
    private byte[] data;
    
    public byte[] getByteArray() {
        return this.data;
    }
    
    NBTTagByteArray() {
    }
    
    @Override
    public String toString() {
        return "[" + this.data.length + " bytes]";
    }
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.data.length);
        dataOutput.write(this.data);
    }
    
    @Override
    public NBTBase copy() {
        final byte[] array = new byte[this.data.length];
        System.arraycopy(this.data, 0, array, 0, this.data.length);
        return new NBTTagByteArray(array);
    }
    
    @Override
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        nbtSizeTracker.read(192L);
        final int int1 = dataInput.readInt();
        nbtSizeTracker.read(8 * int1);
        dataInput.readFully(this.data = new byte[int1]);
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.data);
    }
    
    public NBTTagByteArray(final byte[] data) {
        this.data = data;
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o) && Arrays.equals(this.data, ((NBTTagByteArray)o).data);
    }
    
    @Override
    public byte getId() {
        return 7;
    }
}
