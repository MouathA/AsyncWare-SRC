package com.nquantum.module.combat;

import net.minecraft.entity.*;
import java.util.*;
import com.nquantum.event.impl.*;
import com.nquantum.event.*;
import com.nquantum.module.*;

public class BowAimbot extends Module
{
    EntityLivingBase target;
    
    private EntityLivingBase getClosest(final double n) {
        double n2 = n;
        EntityLivingBase entityLivingBase = null;
        for (final Entity next : this.mc.theWorld.loadedEntityList) {
            final EntityLivingBase target = this.target;
            if (next instanceof EntityLivingBase) {
                final double n3;
                if ((n3 = this.mc.thePlayer.getDistanceToEntity(target)) > n2) {
                    continue;
                }
                n2 = n3;
                entityLivingBase = target;
            }
        }
        return entityLivingBase;
    }
    
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate eventUpdate) {
        final KillAura killAura = new KillAura();
        for (final Entity entity : this.mc.theWorld.loadedEntityList) {
            if (this.target != null) {
                this.target = this.getClosest(120.0);
            }
            this.mc.thePlayer.rotationYaw = killAura.getRotations(this.target)[0] + 10.0f;
            this.mc.thePlayer.rotationPitch = killAura.getRotations(this.target)[1];
        }
    }
    
    public BowAimbot() {
        super("Bow Aimbot", 0, Category.COMBAT);
    }
}
