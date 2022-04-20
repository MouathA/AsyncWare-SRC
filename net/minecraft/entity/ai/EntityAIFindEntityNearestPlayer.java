package net.minecraft.entity.ai;

import com.google.common.base.*;
import org.apache.logging.log4j.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.scoreboard.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;

public class EntityAIFindEntityNearestPlayer extends EntityAIBase
{
    private static final Logger field_179436_a;
    private EntityLiving field_179434_b;
    private EntityLivingBase field_179433_e;
    private final Predicate field_179435_c;
    private final EntityAINearestAttackableTarget.Sorter field_179432_d;
    
    static {
        field_179436_a = LogManager.getLogger();
    }
    
    @Override
    public boolean shouldExecute() {
        final double func_179431_f = this.func_179431_f();
        final List entitiesWithinAABB = this.field_179434_b.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.field_179434_b.getEntityBoundingBox().expand(func_179431_f, 4.0, func_179431_f), this.field_179435_c);
        Collections.sort((List<Object>)entitiesWithinAABB, this.field_179432_d);
        if (entitiesWithinAABB.isEmpty()) {
            return false;
        }
        this.field_179433_e = entitiesWithinAABB.get(0);
        return true;
    }
    
    static EntityLiving access$000(final EntityAIFindEntityNearestPlayer entityAIFindEntityNearestPlayer) {
        return entityAIFindEntityNearestPlayer.field_179434_b;
    }
    
    @Override
    public void resetTask() {
        this.field_179434_b.setAttackTarget(null);
        super.startExecuting();
    }
    
    public EntityAIFindEntityNearestPlayer(final EntityLiving field_179434_b) {
        this.field_179434_b = field_179434_b;
        if (field_179434_b instanceof EntityCreature) {
            EntityAIFindEntityNearestPlayer.field_179436_a.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
        }
        this.field_179435_c = (Predicate)new Predicate(this) {
            final EntityAIFindEntityNearestPlayer this$0;
            
            public boolean apply(final Entity entity) {
                if (!(entity instanceof EntityPlayer)) {
                    return false;
                }
                if (((EntityPlayer)entity).capabilities.disableDamage) {
                    return false;
                }
                double func_179431_f = this.this$0.func_179431_f();
                if (entity.isSneaking()) {
                    func_179431_f *= 0.800000011920929;
                }
                if (entity.isInvisible()) {
                    float armorVisibility = ((EntityPlayer)entity).getArmorVisibility();
                    if (armorVisibility < 0.1f) {
                        armorVisibility = 0.1f;
                    }
                    func_179431_f *= 0.7f * armorVisibility;
                }
                return entity.getDistanceToEntity(EntityAIFindEntityNearestPlayer.access$000(this.this$0)) <= func_179431_f && EntityAITarget.isSuitableTarget(EntityAIFindEntityNearestPlayer.access$000(this.this$0), (EntityLivingBase)entity, false, true);
            }
            
            public boolean apply(final Object o) {
                return this.apply((Entity)o);
            }
        };
        this.field_179432_d = new EntityAINearestAttackableTarget.Sorter(field_179434_b);
    }
    
    @Override
    public boolean continueExecuting() {
        final EntityLivingBase attackTarget = this.field_179434_b.getAttackTarget();
        if (attackTarget == null) {
            return false;
        }
        if (!attackTarget.isEntityAlive()) {
            return false;
        }
        if (attackTarget instanceof EntityPlayer && ((EntityPlayer)attackTarget).capabilities.disableDamage) {
            return false;
        }
        final Team team = this.field_179434_b.getTeam();
        final Team team2 = attackTarget.getTeam();
        if (team != null && team2 == team) {
            return false;
        }
        final double func_179431_f = this.func_179431_f();
        return this.field_179434_b.getDistanceSqToEntity(attackTarget) <= func_179431_f * func_179431_f && (!(attackTarget instanceof EntityPlayerMP) || !((EntityPlayerMP)attackTarget).theItemInWorldManager.isCreative());
    }
    
    @Override
    public void startExecuting() {
        this.field_179434_b.setAttackTarget(this.field_179433_e);
        super.startExecuting();
    }
    
    protected double func_179431_f() {
        final IAttributeInstance entityAttribute = this.field_179434_b.getEntityAttribute(SharedMonsterAttributes.followRange);
        return (entityAttribute == null) ? 16.0 : entityAttribute.getAttributeValue();
    }
}
