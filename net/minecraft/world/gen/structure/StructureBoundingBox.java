package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import net.minecraft.nbt.*;
import com.google.common.base.*;

public class StructureBoundingBox
{
    public int maxX;
    public int minZ;
    public int minX;
    public int maxZ;
    public int minY;
    public int maxY;
    
    public StructureBoundingBox() {
    }
    
    public int getYSize() {
        return this.maxY - this.minY + 1;
    }
    
    public void offset(final int n, final int n2, final int n3) {
        this.minX += n;
        this.minY += n2;
        this.minZ += n3;
        this.maxX += n;
        this.maxY += n2;
        this.maxZ += n3;
    }
    
    public Vec3i getCenter() {
        return new BlockPos(this.minX + (this.maxX - this.minX + 1) / 2, this.minY + (this.maxY - this.minY + 1) / 2, this.minZ + (this.maxZ - this.minZ + 1) / 2);
    }
    
    public StructureBoundingBox(final Vec3i vec3i, final Vec3i vec3i2) {
        this.minX = Math.min(vec3i.getX(), vec3i2.getX());
        this.minY = Math.min(vec3i.getY(), vec3i2.getY());
        this.minZ = Math.min(vec3i.getZ(), vec3i2.getZ());
        this.maxX = Math.max(vec3i.getX(), vec3i2.getX());
        this.maxY = Math.max(vec3i.getY(), vec3i2.getY());
        this.maxZ = Math.max(vec3i.getZ(), vec3i2.getZ());
    }
    
    public StructureBoundingBox(final int[] array) {
        if (array.length == 6) {
            this.minX = array[0];
            this.minY = array[1];
            this.minZ = array[2];
            this.maxX = array[3];
            this.maxY = array[4];
            this.maxZ = array[5];
        }
    }
    
    public StructureBoundingBox(final int minX, final int minY, final int minZ, final int maxX, final int maxY, final int maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }
    
    public int getXSize() {
        return this.maxX - this.minX + 1;
    }
    
    public static StructureBoundingBox func_175899_a(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        return new StructureBoundingBox(Math.min(n, n4), Math.min(n2, n5), Math.min(n3, n6), Math.max(n, n4), Math.max(n2, n5), Math.max(n3, n6));
    }
    
    public static StructureBoundingBox getNewBoundingBox() {
        return new StructureBoundingBox(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    }
    
    public boolean intersectsWith(final int n, final int n2, final int n3, final int n4) {
        return this.maxX >= n && this.minX <= n3 && this.maxZ >= n2 && this.minZ <= n4;
    }
    
    public boolean intersectsWith(final StructureBoundingBox structureBoundingBox) {
        return this.maxX >= structureBoundingBox.minX && this.minX <= structureBoundingBox.maxX && this.maxZ >= structureBoundingBox.minZ && this.minZ <= structureBoundingBox.maxZ && this.maxY >= structureBoundingBox.minY && this.minY <= structureBoundingBox.maxY;
    }
    
    public static StructureBoundingBox getComponentToAddBoundingBox(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final EnumFacing enumFacing) {
        switch (StructureBoundingBox$1.$SwitchMap$net$minecraft$util$EnumFacing[enumFacing.ordinal()]) {
            case 1: {
                return new StructureBoundingBox(n + n4, n2 + n5, n3 - n9 + 1 + n6, n + n7 - 1 + n4, n2 + n8 - 1 + n5, n3 + n6);
            }
            case 2: {
                return new StructureBoundingBox(n + n4, n2 + n5, n3 + n6, n + n7 - 1 + n4, n2 + n8 - 1 + n5, n3 + n9 - 1 + n6);
            }
            case 3: {
                return new StructureBoundingBox(n - n9 + 1 + n6, n2 + n5, n3 + n4, n + n6, n2 + n8 - 1 + n5, n3 + n7 - 1 + n4);
            }
            case 4: {
                return new StructureBoundingBox(n + n6, n2 + n5, n3 + n4, n + n9 - 1 + n6, n2 + n8 - 1 + n5, n3 + n7 - 1 + n4);
            }
            default: {
                return new StructureBoundingBox(n + n4, n2 + n5, n3 + n6, n + n7 - 1 + n4, n2 + n8 - 1 + n5, n3 + n9 - 1 + n6);
            }
        }
    }
    
    public int getZSize() {
        return this.maxZ - this.minZ + 1;
    }
    
    public void expandTo(final StructureBoundingBox structureBoundingBox) {
        this.minX = Math.min(this.minX, structureBoundingBox.minX);
        this.minY = Math.min(this.minY, structureBoundingBox.minY);
        this.minZ = Math.min(this.minZ, structureBoundingBox.minZ);
        this.maxX = Math.max(this.maxX, structureBoundingBox.maxX);
        this.maxY = Math.max(this.maxY, structureBoundingBox.maxY);
        this.maxZ = Math.max(this.maxZ, structureBoundingBox.maxZ);
    }
    
    public NBTTagIntArray toNBTTagIntArray() {
        return new NBTTagIntArray(new int[] { this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ });
    }
    
    public StructureBoundingBox(final int minX, final int minZ, final int maxX, final int maxZ) {
        this.minX = minX;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxZ = maxZ;
        this.minY = 1;
        this.maxY = 512;
    }
    
    public StructureBoundingBox(final StructureBoundingBox structureBoundingBox) {
        this.minX = structureBoundingBox.minX;
        this.minY = structureBoundingBox.minY;
        this.minZ = structureBoundingBox.minZ;
        this.maxX = structureBoundingBox.maxX;
        this.maxY = structureBoundingBox.maxY;
        this.maxZ = structureBoundingBox.maxZ;
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper((Object)this).add("x0", this.minX).add("y0", this.minY).add("z0", this.minZ).add("x1", this.maxX).add("y1", this.maxY).add("z1", this.maxZ).toString();
    }
    
    public boolean isVecInside(final Vec3i vec3i) {
        return vec3i.getX() >= this.minX && vec3i.getX() <= this.maxX && vec3i.getZ() >= this.minZ && vec3i.getZ() <= this.maxZ && vec3i.getY() >= this.minY && vec3i.getY() <= this.maxY;
    }
    
    public Vec3i func_175896_b() {
        return new Vec3i(this.maxX - this.minX, this.maxY - this.minY, this.maxZ - this.minZ);
    }
}
