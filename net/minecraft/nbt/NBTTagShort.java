package net.minecraft.nbt;

import java.io.*;

public class NBTTagShort extends NBTPrimitive
{
    private short data;
    
    @Override
    public byte getByte() {
        return (byte)(this.data & 0xFF);
    }
    
    @Override
    public int getInt() {
        return this.data;
    }
    
    @Override
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        nbtSizeTracker.read(80L);
        this.data = dataInput.readShort();
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o) && this.data == ((NBTTagShort)o).data;
    }
    
    @Override
    public NBTBase copy() {
        return new NBTTagShort(this.data);
    }
    
    public NBTTagShort(final short data) {
        this.data = data;
    }
    
    @Override
    public double getDouble() {
        return this.data;
    }
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeShort(this.data);
    }
    
    @Override
    public byte getId() {
        return 2;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ this.data;
    }
    
    @Override
    public String toString() {
        return "" + this.data + "s";
    }
    
    @Override
    public short getShort() {
        return this.data;
    }
    
    @Override
    public float getFloat() {
        return this.data;
    }
    
    @Override
    public long getLong() {
        return this.data;
    }
    
    public NBTTagShort() {
    }
}
