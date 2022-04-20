package net.minecraft.entity.ai;

import net.minecraft.entity.monster.*;
import net.minecraft.village.*;
import net.minecraft.entity.*;

public class EntityAIDefendVillage extends EntityAITarget
{
    EntityLivingBase villageAgressorTarget;
    EntityIronGolem irongolem;
    
    @Override
    public boolean shouldExecute() {
        final Village village = this.irongolem.getVillage();
        if (village == null) {
            return false;
        }
        this.villageAgressorTarget = village.findNearestVillageAggressor(this.irongolem);
        if (this.villageAgressorTarget instanceof EntityCreeper) {
            return false;
        }
        if (this.isSuitableTarget(this.villageAgressorTarget, false)) {
            return true;
        }
        if (this.taskOwner.getRNG().nextInt(20) == 0) {
            this.villageAgressorTarget = village.getNearestTargetPlayer(this.irongolem);
            return this.isSuitableTarget(this.villageAgressorTarget, false);
        }
        return false;
    }
    
    public EntityAIDefendVillage(final EntityIronGolem irongolem) {
        super(irongolem, false, true);
        this.irongolem = irongolem;
        this.setMutexBits(1);
    }
    
    @Override
    public void startExecuting() {
        this.irongolem.setAttackTarget(this.villageAgressorTarget);
        super.startExecuting();
    }
}
