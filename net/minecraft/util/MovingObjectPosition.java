package net.minecraft.util;

import net.minecraft.entity.*;

public class MovingObjectPosition
{
    public Vec3 hitVec;
    public EnumFacing sideHit;
    private BlockPos blockPos;
    public MovingObjectType typeOfHit;
    public Entity entityHit;
    
    public MovingObjectPosition(final Vec3 vec3, final EnumFacing enumFacing) {
        this(MovingObjectType.BLOCK, vec3, enumFacing, BlockPos.ORIGIN);
    }
    
    public MovingObjectPosition(final Entity entityHit, final Vec3 hitVec) {
        this.typeOfHit = MovingObjectType.ENTITY;
        this.entityHit = entityHit;
        this.hitVec = hitVec;
    }
    
    @Override
    public String toString() {
        return "HitResult{type=" + this.typeOfHit + ", blockpos=" + this.blockPos + ", f=" + this.sideHit + ", pos=" + this.hitVec + ", entity=" + this.entityHit + '}';
    }
    
    public MovingObjectPosition(final Vec3 vec3, final EnumFacing enumFacing, final BlockPos blockPos) {
        this(MovingObjectType.BLOCK, vec3, enumFacing, blockPos);
    }
    
    public MovingObjectPosition(final MovingObjectType typeOfHit, final Vec3 vec3, final EnumFacing sideHit, final BlockPos blockPos) {
        this.typeOfHit = typeOfHit;
        this.blockPos = blockPos;
        this.sideHit = sideHit;
        this.hitVec = new Vec3(vec3.xCoord, vec3.yCoord, vec3.zCoord);
    }
    
    public BlockPos getBlockPos() {
        return this.blockPos;
    }
    
    public MovingObjectPosition(final Entity entity) {
        this(entity, new Vec3(entity.posX, entity.posY, entity.posZ));
    }
    
    public enum MovingObjectType
    {
        MISS("MISS", 0), 
        BLOCK("BLOCK", 1);
        
        private static final MovingObjectType[] $VALUES;
        
        ENTITY("ENTITY", 2);
        
        private MovingObjectType(final String s, final int n) {
        }
        
        static {
            $VALUES = new MovingObjectType[] { MovingObjectType.MISS, MovingObjectType.BLOCK, MovingObjectType.ENTITY };
        }
    }
}
