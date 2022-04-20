package net.minecraft.entity.monster;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class EntityGiantZombie extends EntityMob
{
    @Override
    public float getEyeHeight() {
        return 10.440001f;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(50.0);
    }
    
    public EntityGiantZombie(final World world) {
        super(world);
        this.setSize(this.width * 6.0f, this.height * 6.0f);
    }
    
    @Override
    public float getBlockPathWeight(final BlockPos blockPos) {
        return this.worldObj.getLightBrightness(blockPos) - 0.5f;
    }
}
