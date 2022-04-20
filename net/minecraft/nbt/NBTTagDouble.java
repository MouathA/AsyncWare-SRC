package net.minecraft.nbt;

import net.minecraft.util.*;
import java.io.*;

public class NBTTagDouble extends NBTPrimitive
{
    private double data;
    
    @Override
    public int getInt() {
        return MathHelper.floor_double(this.data);
    }
    
    @Override
    public float getFloat() {
        return (float)this.data;
    }
    
    @Override
    public double getDouble() {
        return this.data;
    }
    
    @Override
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        nbtSizeTracker.read(128L);
        this.data = dataInput.readDouble();
    }
    
    @Override
    public NBTBase copy() {
        return new NBTTagDouble(this.data);
    }
    
    @Override
    public String toString() {
        return "" + this.data + "d";
    }
    
    @Override
    public short getShort() {
        return (short)(MathHelper.floor_double(this.data) & 0xFFFF);
    }
    
    @Override
    public byte getByte() {
        return (byte)(MathHelper.floor_double(this.data) & 0xFF);
    }
    
    @Override
    public byte getId() {
        return 6;
    }
    
    @Override
    public long getLong() {
        return (long)Math.floor(this.data);
    }
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeDouble(this.data);
    }
    
    public NBTTagDouble(final double data) {
        this.data = data;
    }
    
    @Override
    public int hashCode() {
        final long doubleToLongBits = Double.doubleToLongBits(this.data);
        return super.hashCode() ^ (int)(doubleToLongBits ^ doubleToLongBits >>> 32);
    }
    
    NBTTagDouble() {
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o) && this.data == ((NBTTagDouble)o).data;
    }
}
