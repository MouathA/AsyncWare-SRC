package net.minecraft.util;

import net.minecraft.nbt.*;

public class Rotations
{
    protected final float z;
    protected final float y;
    protected final float x;
    
    public NBTTagList writeToNBT() {
        final NBTTagList list = new NBTTagList();
        list.appendTag(new NBTTagFloat(this.x));
        list.appendTag(new NBTTagFloat(this.y));
        list.appendTag(new NBTTagFloat(this.z));
        return list;
    }
    
    public Rotations(final NBTTagList list) {
        this.x = list.getFloatAt(0);
        this.y = list.getFloatAt(1);
        this.z = list.getFloatAt(2);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Rotations)) {
            return false;
        }
        final Rotations rotations = (Rotations)o;
        return this.x == rotations.x && this.y == rotations.y && this.z == rotations.z;
    }
    
    public Rotations(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public float getZ() {
        return this.z;
    }
    
    public float getX() {
        return this.x;
    }
    
    public float getY() {
        return this.y;
    }
}
