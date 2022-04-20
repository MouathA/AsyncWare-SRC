package com.nquantum.processor.pathfinding;

public class Vec3
{
    private final double x;
    private final double y;
    private final double z;
    
    public Vec3(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    @Override
    public String toString() {
        return "[" + this.x + ";" + this.y + ";" + this.z + "]";
    }
    
    public Vec3 addVector(final double n, final double n2, final double n3) {
        return new Vec3(this.x + n, this.y + n2, this.z + n3);
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double squareDistanceTo(final Vec3 vec3) {
        return Math.pow(vec3.x - this.x, 2.0) + Math.pow(vec3.y - this.y, 2.0) + Math.pow(vec3.z - this.z, 2.0);
    }
    
    public Vec3 floor() {
        return new Vec3(Math.floor(this.x), Math.floor(this.y), Math.floor(this.z));
    }
    
    public net.minecraft.util.Vec3 mc() {
        return new net.minecraft.util.Vec3(this.x, this.y, this.z);
    }
    
    public Vec3 add(final Vec3 vec3) {
        return this.addVector(vec3.getX(), vec3.getY(), vec3.getZ());
    }
    
    public double getZ() {
        return this.z;
    }
}
