package net.minecraft.util;

import com.google.common.base.*;

public class Vec3i implements Comparable
{
    private final int x;
    private final int y;
    public static final Vec3i NULL_VECTOR;
    private final int z;
    
    public int getY() {
        return this.y;
    }
    
    public Vec3i crossProduct(final Vec3i vec3i) {
        return new Vec3i(this.getY() * vec3i.getZ() - this.getZ() * vec3i.getY(), this.getZ() * vec3i.getX() - this.getX() * vec3i.getZ(), this.getX() * vec3i.getY() - this.getY() * vec3i.getX());
    }
    
    public double distanceSq(final Vec3i vec3i) {
        return this.distanceSq(vec3i.getX(), vec3i.getY(), vec3i.getZ());
    }
    
    static {
        NULL_VECTOR = new Vec3i(0, 0, 0);
    }
    
    public int getZ() {
        return this.z;
    }
    
    public double distanceSq(final double n, final double n2, final double n3) {
        final double n4 = this.getX() - n;
        final double n5 = this.getY() - n2;
        final double n6 = this.getZ() - n3;
        return n4 * n4 + n5 * n5 + n6 * n6;
    }
    
    public Vec3i(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((Vec3i)o);
    }
    
    public double distanceSqToCenter(final double n, final double n2, final double n3) {
        final double n4 = this.getX() + 0.5 - n;
        final double n5 = this.getY() + 0.5 - n2;
        final double n6 = this.getZ() + 0.5 - n3;
        return n4 * n4 + n5 * n5 + n6 * n6;
    }
    
    public int compareTo(final Vec3i vec3i) {
        return (this.getY() == vec3i.getY()) ? ((this.getZ() == vec3i.getZ()) ? (this.getX() - vec3i.getX()) : (this.getZ() - vec3i.getZ())) : (this.getY() - vec3i.getY());
    }
    
    @Override
    public int hashCode() {
        return (this.getY() + this.getZ() * 31) * 31 + this.getX();
    }
    
    public Vec3i(final double n, final double n2, final double n3) {
        this(MathHelper.floor_double(n), MathHelper.floor_double(n2), MathHelper.floor_double(n3));
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper((Object)this).add("x", this.getX()).add("y", this.getY()).add("z", this.getZ()).toString();
    }
    
    public int getX() {
        return this.x;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vec3i)) {
            return false;
        }
        final Vec3i vec3i = (Vec3i)o;
        return this.getX() == vec3i.getX() && this.getY() == vec3i.getY() && this.getZ() == vec3i.getZ();
    }
}
