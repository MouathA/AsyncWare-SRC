package net.minecraft.nbt;

import java.io.*;

public abstract class NBTBase
{
    abstract void write(final DataOutput p0) throws IOException;
    
    @Override
    public int hashCode() {
        return this.getId();
    }
    
    protected static NBTBase createNewByType(final byte b) {
        switch (b) {
            case 0: {
                return new NBTTagEnd();
            }
            case 1: {
                return new NBTTagByte();
            }
            case 2: {
                return new NBTTagShort();
            }
            case 3: {
                return new NBTTagInt();
            }
            case 4: {
                return new NBTTagLong();
            }
            case 5: {
                return new NBTTagFloat();
            }
            case 6: {
                return new NBTTagDouble();
            }
            case 7: {
                return new NBTTagByteArray();
            }
            case 8: {
                return new NBTTagString();
            }
            case 9: {
                return new NBTTagList();
            }
            case 10: {
                return new NBTTagCompound();
            }
            case 11: {
                return new NBTTagIntArray();
            }
            default: {
                return null;
            }
        }
    }
    
    static {
        NBTBase.NBT_TYPES = new String[] { "END", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE", "BYTE[]", "STRING", "LIST", "COMPOUND", "INT[]" };
    }
    
    abstract void read(final DataInput p0, final int p1, final NBTSizeTracker p2) throws IOException;
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof NBTBase && this.getId() == ((NBTBase)o).getId();
    }
    
    public abstract NBTBase copy();
    
    public boolean hasNoTags() {
        return false;
    }
    
    protected String getString() {
        return this.toString();
    }
    
    public abstract byte getId();
    
    @Override
    public abstract String toString();
    
    public abstract static class NBTPrimitive extends NBTBase
    {
        public abstract int getInt();
        
        public abstract float getFloat();
        
        public abstract long getLong();
        
        public abstract byte getByte();
        
        public abstract double getDouble();
        
        public abstract short getShort();
    }
}
