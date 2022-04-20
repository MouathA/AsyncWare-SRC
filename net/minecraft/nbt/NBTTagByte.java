package net.minecraft.nbt;

import java.io.*;

public class NBTTagByte extends NBTPrimitive
{
    private byte data;
    
    public NBTTagByte(final byte data) {
        this.data = data;
    }
    
    @Override
    public long getLong() {
        return this.data;
    }
    
    @Override
    public String toString() {
        return "" + this.data + "b";
    }
    
    @Override
    public double getDouble() {
        return this.data;
    }
    
    @Override
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        nbtSizeTracker.read(72L);
        this.data = dataInput.readByte();
    }
    
    @Override
    public NBTBase copy() {
        return new NBTTagByte(this.data);
    }
    
    @Override
    public short getShort() {
        return this.data;
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o) && this.data == ((NBTTagByte)o).data;
    }
    
    NBTTagByte() {
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ this.data;
    }
    
    @Override
    public byte getId() {
        return 1;
    }
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(this.data);
    }
    
    @Override
    public byte getByte() {
        return this.data;
    }
    
    @Override
    public float getFloat() {
        return this.data;
    }
    
    @Override
    public int getInt() {
        return this.data;
    }
}
