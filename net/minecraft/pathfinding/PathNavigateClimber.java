package net.minecraft.pathfinding;

import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;

public class PathNavigateClimber extends PathNavigateGround
{
    private BlockPos targetPosition;
    
    @Override
    public boolean tryMoveToEntityLiving(final Entity entity, final double speed) {
        final PathEntity pathToEntityLiving = this.getPathToEntityLiving(entity);
        if (pathToEntityLiving != null) {
            return this.setPath(pathToEntityLiving, speed);
        }
        this.targetPosition = new BlockPos(entity);
        this.speed = speed;
        return true;
    }
    
    @Override
    public PathEntity getPathToEntityLiving(final Entity entity) {
        this.targetPosition = new BlockPos(entity);
        return super.getPathToEntityLiving(entity);
    }
    
    @Override
    public void onUpdateNavigation() {
        if (!this.noPath()) {
            super.onUpdateNavigation();
        }
        else if (this.targetPosition != null) {
            final double n = this.theEntity.width * this.theEntity.width;
            if (this.theEntity.getDistanceSqToCenter(this.targetPosition) >= n && (this.theEntity.posY <= this.targetPosition.getY() || this.theEntity.getDistanceSqToCenter(new BlockPos(this.targetPosition.getX(), MathHelper.floor_double(this.theEntity.posY), this.targetPosition.getZ())) >= n)) {
                this.theEntity.getMoveHelper().setMoveTo(this.targetPosition.getX(), this.targetPosition.getY(), this.targetPosition.getZ(), this.speed);
            }
            else {
                this.targetPosition = null;
            }
        }
    }
    
    @Override
    public PathEntity getPathToPos(final BlockPos targetPosition) {
        this.targetPosition = targetPosition;
        return super.getPathToPos(targetPosition);
    }
    
    public PathNavigateClimber(final EntityLiving entityLiving, final World world) {
        super(entityLiving, world);
    }
}
