package net.minecraft.pathfinding;

import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import java.util.*;

public abstract class PathNavigate
{
    protected double speed;
    private final IAttributeInstance pathSearchRange;
    private float heightRequirement;
    private final PathFinder pathFinder;
    private int totalTicks;
    private int ticksAtLastPos;
    private Vec3 lastPosCheck;
    protected World worldObj;
    protected PathEntity currentPath;
    protected EntityLiving theEntity;
    
    protected abstract PathFinder getPathFinder();
    
    protected abstract boolean canNavigate();
    
    public PathNavigate(final EntityLiving theEntity, final World worldObj) {
        this.lastPosCheck = new Vec3(0.0, 0.0, 0.0);
        this.heightRequirement = 1.0f;
        this.theEntity = theEntity;
        this.worldObj = worldObj;
        this.pathSearchRange = theEntity.getEntityAttribute(SharedMonsterAttributes.followRange);
        this.pathFinder = this.getPathFinder();
    }
    
    public PathEntity getPath() {
        return this.currentPath;
    }
    
    public boolean tryMoveToXYZ(final double n, final double n2, final double n3, final double n4) {
        return this.setPath(this.getPathToXYZ(MathHelper.floor_double(n), (int)n2, MathHelper.floor_double(n3)), n4);
    }
    
    public void clearPathEntity() {
        this.currentPath = null;
    }
    
    protected boolean isInLiquid() {
        return this.theEntity.isInWater() || this.theEntity.isInLava();
    }
    
    public PathEntity getPathToPos(final BlockPos blockPos) {
        if (!this.canNavigate()) {
            return null;
        }
        final float pathSearchRange = this.getPathSearchRange();
        this.worldObj.theProfiler.startSection("pathfind");
        final BlockPos blockPos2 = new BlockPos(this.theEntity);
        final int n = (int)(pathSearchRange + 8.0f);
        final PathEntity entityPathTo = this.pathFinder.createEntityPathTo(new ChunkCache(this.worldObj, blockPos2.add(-n, -n, -n), blockPos2.add(n, n, n), 0), this.theEntity, blockPos, pathSearchRange);
        this.worldObj.theProfiler.endSection();
        return entityPathTo;
    }
    
    public void onUpdateNavigation() {
        ++this.totalTicks;
        if (this != null) {
            if (this.canNavigate()) {
                this.pathFollow();
            }
            else if (this.currentPath != null && this.currentPath.getCurrentPathIndex() < this.currentPath.getCurrentPathLength()) {
                final Vec3 entityPosition = this.getEntityPosition();
                final Vec3 vectorFromIndex = this.currentPath.getVectorFromIndex(this.theEntity, this.currentPath.getCurrentPathIndex());
                if (entityPosition.yCoord > vectorFromIndex.yCoord && !this.theEntity.onGround && MathHelper.floor_double(entityPosition.xCoord) == MathHelper.floor_double(vectorFromIndex.xCoord) && MathHelper.floor_double(entityPosition.zCoord) == MathHelper.floor_double(vectorFromIndex.zCoord)) {
                    this.currentPath.setCurrentPathIndex(this.currentPath.getCurrentPathIndex() + 1);
                }
            }
            if (this != null) {
                final Vec3 position = this.currentPath.getPosition(this.theEntity);
                if (position != null) {
                    final AxisAlignedBB expand = new AxisAlignedBB(position.xCoord, position.yCoord, position.zCoord, position.xCoord, position.yCoord, position.zCoord).expand(0.5, 0.5, 0.5);
                    final List collidingBoundingBoxes = this.worldObj.getCollidingBoundingBoxes(this.theEntity, expand.addCoord(0.0, -1.0, 0.0));
                    double calculateYOffset = -1.0;
                    final AxisAlignedBB offset = expand.offset(0.0, 1.0, 0.0);
                    final Iterator<AxisAlignedBB> iterator = collidingBoundingBoxes.iterator();
                    while (iterator.hasNext()) {
                        calculateYOffset = iterator.next().calculateYOffset(offset, calculateYOffset);
                    }
                    this.theEntity.getMoveHelper().setMoveTo(position.xCoord, position.yCoord + calculateYOffset, position.zCoord, this.speed);
                }
            }
        }
    }
    
    public float getPathSearchRange() {
        return (float)this.pathSearchRange.getAttributeValue();
    }
    
    public final PathEntity getPathToXYZ(final double n, final double n2, final double n3) {
        return this.getPathToPos(new BlockPos(MathHelper.floor_double(n), (int)n2, MathHelper.floor_double(n3)));
    }
    
    public boolean tryMoveToEntityLiving(final Entity entity, final double n) {
        final PathEntity pathToEntityLiving = this.getPathToEntityLiving(entity);
        return pathToEntityLiving != null && this.setPath(pathToEntityLiving, n);
    }
    
    protected void pathFollow() {
        final Vec3 entityPosition = this.getEntityPosition();
        int currentPathLength = this.currentPath.getCurrentPathLength();
        for (int i = this.currentPath.getCurrentPathIndex(); i < this.currentPath.getCurrentPathLength(); ++i) {
            if (this.currentPath.getPathPointFromIndex(i).yCoord != (int)entityPosition.yCoord) {
                currentPathLength = i;
                break;
            }
        }
        final float n = this.theEntity.width * this.theEntity.width * this.heightRequirement;
        for (int j = this.currentPath.getCurrentPathIndex(); j < currentPathLength; ++j) {
            if (entityPosition.squareDistanceTo(this.currentPath.getVectorFromIndex(this.theEntity, j)) < n) {
                this.currentPath.setCurrentPathIndex(j + 1);
            }
        }
        final int ceiling_float_int = MathHelper.ceiling_float_int(this.theEntity.width);
        final int n2 = (int)this.theEntity.height + 1;
        final int n3 = ceiling_float_int;
        for (int k = currentPathLength - 1; k >= this.currentPath.getCurrentPathIndex(); --k) {
            if (this.isDirectPathBetweenPoints(entityPosition, this.currentPath.getVectorFromIndex(this.theEntity, k), ceiling_float_int, n2, n3)) {
                this.currentPath.setCurrentPathIndex(k);
                break;
            }
        }
        this.checkForStuck(entityPosition);
    }
    
    protected void checkForStuck(final Vec3 lastPosCheck) {
        if (this.totalTicks - this.ticksAtLastPos > 100) {
            if (lastPosCheck.squareDistanceTo(this.lastPosCheck) < 2.25) {
                this.clearPathEntity();
            }
            this.ticksAtLastPos = this.totalTicks;
            this.lastPosCheck = lastPosCheck;
        }
    }
    
    public void setHeightRequirement(final float heightRequirement) {
        this.heightRequirement = heightRequirement;
    }
    
    protected abstract boolean isDirectPathBetweenPoints(final Vec3 p0, final Vec3 p1, final int p2, final int p3, final int p4);
    
    public boolean setPath(final PathEntity currentPath, final double speed) {
        if (currentPath == null) {
            this.currentPath = null;
            return false;
        }
        if (!currentPath.isSamePath(this.currentPath)) {
            this.currentPath = currentPath;
        }
        this.removeSunnyPath();
        if (this.currentPath.getCurrentPathLength() == 0) {
            return false;
        }
        this.speed = speed;
        final Vec3 entityPosition = this.getEntityPosition();
        this.ticksAtLastPos = this.totalTicks;
        this.lastPosCheck = entityPosition;
        return true;
    }
    
    protected void removeSunnyPath() {
    }
    
    protected abstract Vec3 getEntityPosition();
    
    public PathEntity getPathToEntityLiving(final Entity entity) {
        if (!this.canNavigate()) {
            return null;
        }
        final float pathSearchRange = this.getPathSearchRange();
        this.worldObj.theProfiler.startSection("pathfind");
        final BlockPos up = new BlockPos(this.theEntity).up();
        final int n = (int)(pathSearchRange + 16.0f);
        final PathEntity entityPathTo = this.pathFinder.createEntityPathTo(new ChunkCache(this.worldObj, up.add(-n, -n, -n), up.add(n, n, n), 0), this.theEntity, entity, pathSearchRange);
        this.worldObj.theProfiler.endSection();
        return entityPathTo;
    }
    
    public void setSpeed(final double speed) {
        this.speed = speed;
    }
}
