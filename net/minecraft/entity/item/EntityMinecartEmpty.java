package net.minecraft.entity.item;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;

public class EntityMinecartEmpty extends EntityMinecart
{
    public EntityMinecartEmpty(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3);
    }
    
    @Override
    public EnumMinecartType getMinecartType() {
        return EnumMinecartType.RIDEABLE;
    }
    
    @Override
    public void onActivatorRailPass(final int n, final int n2, final int n3, final boolean b) {
        if (b) {
            if (this.riddenByEntity != null) {
                this.riddenByEntity.mountEntity(null);
            }
            if (this.getRollingAmplitude() == 0) {
                this.setRollingDirection(-this.getRollingDirection());
                this.setRollingAmplitude(10);
                this.setDamage(50.0f);
                this.setBeenAttacked();
            }
        }
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer entityPlayer) {
        if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != entityPlayer) {
            return true;
        }
        if (this.riddenByEntity != null && this.riddenByEntity != entityPlayer) {
            return false;
        }
        if (!this.worldObj.isRemote) {
            entityPlayer.mountEntity(this);
        }
        return true;
    }
    
    public EntityMinecartEmpty(final World world) {
        super(world);
    }
}
