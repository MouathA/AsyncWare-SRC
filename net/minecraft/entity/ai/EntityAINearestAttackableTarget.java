package net.minecraft.entity.ai;

import net.minecraft.util.*;
import com.google.common.base.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;

public class EntityAINearestAttackableTarget extends EntityAITarget
{
    protected final Sorter theNearestAttackableTargetSorter;
    protected final Class targetClass;
    private final int targetChance;
    protected Predicate targetEntitySelector;
    protected EntityLivingBase targetEntity;
    
    @Override
    public boolean shouldExecute() {
        if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0) {
            return false;
        }
        final double targetDistance = this.getTargetDistance();
        final List entitiesWithinAABB = this.taskOwner.worldObj.getEntitiesWithinAABB(this.targetClass, this.taskOwner.getEntityBoundingBox().expand(targetDistance, 4.0, targetDistance), Predicates.and(this.targetEntitySelector, EntitySelectors.NOT_SPECTATING));
        Collections.sort((List<Object>)entitiesWithinAABB, this.theNearestAttackableTargetSorter);
        if (entitiesWithinAABB.isEmpty()) {
            return false;
        }
        this.targetEntity = entitiesWithinAABB.get(0);
        return true;
    }
    
    public EntityAINearestAttackableTarget(final EntityCreature entityCreature, final Class targetClass, final int targetChance, final boolean b, final boolean b2, final Predicate predicate) {
        super(entityCreature, b, b2);
        this.targetClass = targetClass;
        this.targetChance = targetChance;
        this.theNearestAttackableTargetSorter = new Sorter(entityCreature);
        this.setMutexBits(1);
        this.targetEntitySelector = (Predicate)new Predicate(this, predicate) {
            final EntityAINearestAttackableTarget this$0;
            final Predicate val$targetSelector;
            
            public boolean apply(final Object o) {
                return this.apply((EntityLivingBase)o);
            }
            
            public boolean apply(final EntityLivingBase entityLivingBase) {
                if (this.val$targetSelector != null && !this.val$targetSelector.apply((Object)entityLivingBase)) {
                    return false;
                }
                if (entityLivingBase instanceof EntityPlayer) {
                    double targetDistance = this.this$0.getTargetDistance();
                    if (entityLivingBase.isSneaking()) {
                        targetDistance *= 0.800000011920929;
                    }
                    if (entityLivingBase.isInvisible()) {
                        float armorVisibility = ((EntityPlayer)entityLivingBase).getArmorVisibility();
                        if (armorVisibility < 0.1f) {
                            armorVisibility = 0.1f;
                        }
                        targetDistance *= 0.7f * armorVisibility;
                    }
                    if (entityLivingBase.getDistanceToEntity(this.this$0.taskOwner) > targetDistance) {
                        return false;
                    }
                }
                return this.this$0.isSuitableTarget(entityLivingBase, false);
            }
        };
    }
    
    @Override
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.targetEntity);
        super.startExecuting();
    }
    
    public EntityAINearestAttackableTarget(final EntityCreature entityCreature, final Class clazz, final boolean b, final boolean b2) {
        this(entityCreature, clazz, 10, b, b2, null);
    }
    
    public EntityAINearestAttackableTarget(final EntityCreature entityCreature, final Class clazz, final boolean b) {
        this(entityCreature, clazz, b, false);
    }
    
    public static class Sorter implements Comparator
    {
        private final Entity theEntity;
        
        @Override
        public int compare(final Object o, final Object o2) {
            return this.compare((Entity)o, (Entity)o2);
        }
        
        public int compare(final Entity entity, final Entity entity2) {
            final double distanceSqToEntity = this.theEntity.getDistanceSqToEntity(entity);
            final double distanceSqToEntity2 = this.theEntity.getDistanceSqToEntity(entity2);
            return (distanceSqToEntity < distanceSqToEntity2) ? -1 : (distanceSqToEntity > distanceSqToEntity2);
        }
        
        public Sorter(final Entity theEntity) {
            this.theEntity = theEntity;
        }
    }
}
