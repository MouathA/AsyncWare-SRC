package net.minecraft.nbt;

import java.util.*;
import com.google.common.collect.*;
import org.apache.logging.log4j.*;
import java.io.*;

public class NBTTagList extends NBTBase
{
    private static final Logger LOGGER;
    private byte tagType;
    private List tagList;
    
    public int tagCount() {
        return this.tagList.size();
    }
    
    @Override
    public NBTBase copy() {
        final NBTTagList list = new NBTTagList();
        list.tagType = this.tagType;
        final Iterator<NBTBase> iterator = this.tagList.iterator();
        while (iterator.hasNext()) {
            list.tagList.add(iterator.next().copy());
        }
        return list;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("[");
        while (0 < this.tagList.size()) {
            sb.append(0).append(':').append(this.tagList.get(0));
            int n = 0;
            ++n;
        }
        return sb.append(']').toString();
    }
    
    public double getDoubleAt(final int n) {
        if (n >= 0 && n < this.tagList.size()) {
            final NBTBase nbtBase = this.tagList.get(n);
            return (nbtBase.getId() == 6) ? ((NBTTagDouble)nbtBase).getDouble() : 0.0;
        }
        return 0.0;
    }
    
    public NBTTagList() {
        this.tagList = Lists.newArrayList();
        this.tagType = 0;
    }
    
    public void set(final int n, final NBTBase nbtBase) {
        if (nbtBase.getId() == 0) {
            NBTTagList.LOGGER.warn("Invalid TagEnd added to ListTag");
        }
        else if (n >= 0 && n < this.tagList.size()) {
            if (this.tagType == 0) {
                this.tagType = nbtBase.getId();
            }
            else if (this.tagType != nbtBase.getId()) {
                NBTTagList.LOGGER.warn("Adding mismatching tag types to tag list");
                return;
            }
            this.tagList.set(n, nbtBase);
        }
        else {
            NBTTagList.LOGGER.warn("index out of bounds to set tag in tag list");
        }
    }
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
        if (!this.tagList.isEmpty()) {
            this.tagType = this.tagList.get(0).getId();
        }
        else {
            this.tagType = 0;
        }
        dataOutput.writeByte(this.tagType);
        dataOutput.writeInt(this.tagList.size());
        while (0 < this.tagList.size()) {
            this.tagList.get(0).write(dataOutput);
            int n = 0;
            ++n;
        }
    }
    
    public int getTagType() {
        return this.tagType;
    }
    
    public String getStringTagAt(final int n) {
        if (n >= 0 && n < this.tagList.size()) {
            final NBTBase nbtBase = this.tagList.get(n);
            return (nbtBase.getId() == 8) ? nbtBase.getString() : nbtBase.toString();
        }
        return "";
    }
    
    public float getFloatAt(final int n) {
        if (n >= 0 && n < this.tagList.size()) {
            final NBTBase nbtBase = this.tagList.get(n);
            return (nbtBase.getId() == 5) ? ((NBTTagFloat)nbtBase).getFloat() : 0.0f;
        }
        return 0.0f;
    }
    
    public NBTBase removeTag(final int n) {
        return this.tagList.remove(n);
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
    
    public NBTBase get(final int n) {
        return (n >= 0 && n < this.tagList.size()) ? this.tagList.get(n) : new NBTTagEnd();
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ this.tagList.hashCode();
    }
    
    public int[] getIntArrayAt(final int n) {
        if (n >= 0 && n < this.tagList.size()) {
            final NBTBase nbtBase = this.tagList.get(n);
            return (nbtBase.getId() == 11) ? ((NBTTagIntArray)nbtBase).getIntArray() : new int[0];
        }
        return new int[0];
    }
    
    @Override
    public byte getId() {
        return 9;
    }
    
    @Override
    public boolean hasNoTags() {
        return this.tagList.isEmpty();
    }
    
    public void appendTag(final NBTBase nbtBase) {
        if (nbtBase.getId() == 0) {
            NBTTagList.LOGGER.warn("Invalid TagEnd added to ListTag");
        }
        else {
            if (this.tagType == 0) {
                this.tagType = nbtBase.getId();
            }
            else if (this.tagType != nbtBase.getId()) {
                NBTTagList.LOGGER.warn("Adding mismatching tag types to tag list");
                return;
            }
            this.tagList.add(nbtBase);
        }
    }
    
    public NBTTagCompound getCompoundTagAt(final int n) {
        if (n >= 0 && n < this.tagList.size()) {
            final NBTBase nbtBase = this.tagList.get(n);
            return (NBTTagCompound)((nbtBase.getId() == 10) ? nbtBase : new NBTTagCompound());
        }
        return new NBTTagCompound();
    }
    
    @Override
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        nbtSizeTracker.read(296L);
        if (n > 512) {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
        }
        this.tagType = dataInput.readByte();
        final int int1 = dataInput.readInt();
        if (this.tagType == 0 && int1 > 0) {
            throw new RuntimeException("Missing type on ListTag");
        }
        nbtSizeTracker.read(32L * int1);
        this.tagList = Lists.newArrayListWithCapacity(int1);
        while (0 < int1) {
            final NBTBase newByType = NBTBase.createNewByType(this.tagType);
            newByType.read(dataInput, n + 1, nbtSizeTracker);
            this.tagList.add(newByType);
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        if (super.equals(o)) {
            final NBTTagList list = (NBTTagList)o;
            if (this.tagType == list.tagType) {
                return this.tagList.equals(list.tagList);
            }
        }
        return false;
    }
}
