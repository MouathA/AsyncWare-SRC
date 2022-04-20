package net.minecraft.nbt;

import java.io.*;

public class NBTTagString extends NBTBase
{
    private String data;
    
    public NBTTagString(final String data) {
        this.data = data;
        if (data == null) {
            throw new IllegalArgumentException("Empty string not allowed");
        }
    }
    
    @Override
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        nbtSizeTracker.read(288L);
        this.data = dataInput.readUTF();
        nbtSizeTracker.read(16 * this.data.length());
    }
    
    public String getString() {
        return this.data;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!super.equals(o)) {
            return false;
        }
        final NBTTagString nbtTagString = (NBTTagString)o;
        return (this.data == null && nbtTagString.data == null) || (this.data != null && this.data.equals(nbtTagString.data));
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ this.data.hashCode();
    }
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.data);
    }
    
    @Override
    public String toString() {
        return "\"" + this.data.replace("\"", "\\\"") + "\"";
    }
    
    @Override
    public boolean hasNoTags() {
        return this.data.isEmpty();
    }
    
    @Override
    public byte getId() {
        return 8;
    }
    
    public NBTTagString() {
        this.data = "";
    }
    
    @Override
    public NBTBase copy() {
        return new NBTTagString(this.data);
    }
}
