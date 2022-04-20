package net.minecraft.entity.projectile;

import net.minecraft.world.*;
import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;

public class EntityEgg extends EntityThrowable
{
    public EntityEgg(final World world) {
        super(world);
    }
    
    public EntityEgg(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3);
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition movingObjectPosition) {
        if (movingObjectPosition.entityHit != null) {
            movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0f);
        }
        if (!this.worldObj.isRemote && this.rand.nextInt(8) == 0) {
            if (this.rand.nextInt(32) == 0) {}
            while (true) {
                final EntityChicken entityChicken = new EntityChicken(this.worldObj);
                entityChicken.setGrowingAge(-24000);
                entityChicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
                this.worldObj.spawnEntityInWorld(entityChicken);
                int n = 0;
                ++n;
            }
        }
        else {
            while (true) {
                this.worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX, this.posY, this.posZ, (this.rand.nextFloat() - 0.5) * 0.08, (this.rand.nextFloat() - 0.5) * 0.08, (this.rand.nextFloat() - 0.5) * 0.08, Item.getIdFromItem(Items.egg));
                int n2 = 0;
                ++n2;
            }
        }
    }
    
    public EntityEgg(final World world, final EntityLivingBase entityLivingBase) {
        super(world, entityLivingBase);
    }
}
