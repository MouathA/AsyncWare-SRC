package net.minecraft.nbt;

import net.minecraft.util.*;
import java.io.*;

public class NBTTagFloat extends NBTPrimitive
{
    private float data;
    
    NBTTagFloat() {
    }
    
    @Override
    public byte getId() {
        return 5;
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o) && this.data == ((NBTTagFloat)o).data;
    }
    
    @Override
    public int getInt() {
        return MathHelper.floor_float(this.data);
    }
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeFloat(this.data);
    }
    
    @Override
    public byte getByte() {
        return (byte)(MathHelper.floor_float(this.data) & 0xFF);
    }
    
    @Override
    public String toString() {
        return "" + this.data + "f";
    }
    
    public NBTTagFloat(final float data) {
        this.data = data;
    }
    
    @Override
    public float getFloat() {
        return this.data;
    }
    
    @Override
    public double getDouble() {
        return this.data;
    }
    
    @Override
    public NBTBase copy() {
        return new NBTTagFloat(this.data);
    }
    
    @Override
    public short getShort() {
        return (short)(MathHelper.floor_float(this.data) & 0xFFFF);
    }
    
    @Override
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        nbtSizeTracker.read(96L);
        this.data = dataInput.readFloat();
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ Float.floatToIntBits(this.data);
    }
    
    @Override
    public long getLong() {
        return (long)this.data;
    }
}
