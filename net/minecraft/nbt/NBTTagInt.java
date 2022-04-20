package net.minecraft.nbt;

import java.io.*;

public class NBTTagInt extends NBTPrimitive
{
    private int data;
    
    @Override
    public long getLong() {
        return this.data;
    }
    
    @Override
    public float getFloat() {
        return (float)this.data;
    }
    
    @Override
    public byte getId() {
        return 3;
    }
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.data);
    }
    
    NBTTagInt() {
    }
    
    @Override
    public short getShort() {
        return (short)(this.data & 0xFFFF);
    }
    
    @Override
    public String toString() {
        return "" + this.data;
    }
    
    @Override
    public double getDouble() {
        return this.data;
    }
    
    @Override
    public byte getByte() {
        return (byte)(this.data & 0xFF);
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o) && this.data == ((NBTTagInt)o).data;
    }
    
    public NBTTagInt(final int data) {
        this.data = data;
    }
    
    @Override
    public int getInt() {
        return this.data;
    }
    
    @Override
    public NBTBase copy() {
        return new NBTTagInt(this.data);
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ this.data;
    }
    
    @Override
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        nbtSizeTracker.read(96L);
        this.data = dataInput.readInt();
    }
}
