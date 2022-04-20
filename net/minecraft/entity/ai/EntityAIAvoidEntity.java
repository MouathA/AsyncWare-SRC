package net.minecraft.entity.ai;

import net.minecraft.pathfinding.*;
import net.minecraft.entity.*;
import com.google.common.base.*;
import net.minecraft.util.*;
import java.util.*;

public class EntityAIAvoidEntity extends EntityAIBase
{
    private PathNavigate entityPathNavigate;
    private double farSpeed;
    private Predicate avoidTargetSelector;
    private PathEntity entityPathEntity;
    private double nearSpeed;
    protected Entity closestLivingEntity;
    private float avoidDistance;
    protected EntityCreature theEntity;
    private Class field_181064_i;
    private final Predicate canBeSeenSelector;
    
    @Override
    public void updateTask() {
        if (this.theEntity.getDistanceSqToEntity(this.closestLivingEntity) < 49.0) {
            this.theEntity.getNavigator().setSpeed(this.nearSpeed);
        }
        else {
            this.theEntity.getNavigator().setSpeed(this.farSpeed);
        }
    }
    
    @Override
    public void resetTask() {
        this.closestLivingEntity = null;
    }
    
    @Override
    public boolean shouldExecute() {
        final List entitiesWithinAABB = this.theEntity.worldObj.getEntitiesWithinAABB(this.field_181064_i, this.theEntity.getEntityBoundingBox().expand(this.avoidDistance, 3.0, this.avoidDistance), Predicates.and(new Predicate[] { EntitySelectors.NOT_SPECTATING, this.canBeSeenSelector, this.avoidTargetSelector }));
        if (entitiesWithinAABB.isEmpty()) {
            return false;
        }
        this.closestLivingEntity = entitiesWithinAABB.get(0);
        final Vec3 randomTargetBlockAway = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.theEntity, 16, 7, new Vec3(this.closestLivingEntity.posX, this.closestLivingEntity.posY, this.closestLivingEntity.posZ));
        if (randomTargetBlockAway == null) {
            return false;
        }
        if (this.closestLivingEntity.getDistanceSq(randomTargetBlockAway.xCoord, randomTargetBlockAway.yCoord, randomTargetBlockAway.zCoord) < this.closestLivingEntity.getDistanceSqToEntity(this.theEntity)) {
            return false;
        }
        this.entityPathEntity = this.entityPathNavigate.getPathToXYZ(randomTargetBlockAway.xCoord, randomTargetBlockAway.yCoord, randomTargetBlockAway.zCoord);
        return this.entityPathEntity != null && this.entityPathEntity.isDestinationSame(randomTargetBlockAway);
    }
    
    public EntityAIAvoidEntity(final EntityCreature theEntity, final Class field_181064_i, final Predicate avoidTargetSelector, final float avoidDistance, final double farSpeed, final double nearSpeed) {
        this.canBeSeenSelector = (Predicate)new Predicate(this) {
            final EntityAIAvoidEntity this$0;
            
            public boolean apply(final Entity entity) {
                return entity.isEntityAlive() && this.this$0.theEntity.getEntitySenses().canSee(entity);
            }
            
            public boolean apply(final Object o) {
                return this.apply((Entity)o);
            }
        };
        this.theEntity = theEntity;
        this.field_181064_i = field_181064_i;
        this.avoidTargetSelector = avoidTargetSelector;
        this.avoidDistance = avoidDistance;
        this.farSpeed = farSpeed;
        this.nearSpeed = nearSpeed;
        this.entityPathNavigate = theEntity.getNavigator();
        this.setMutexBits(1);
    }
    
    @Override
    public void startExecuting() {
        this.entityPathNavigate.setPath(this.entityPathEntity, this.farSpeed);
    }
    
    @Override
    public boolean continueExecuting() {
        return !this.entityPathNavigate.noPath();
    }
    
    public EntityAIAvoidEntity(final EntityCreature entityCreature, final Class clazz, final float n, final double n2, final double n3) {
        this(entityCreature, clazz, Predicates.alwaysTrue(), n, n2, n3);
    }
}
