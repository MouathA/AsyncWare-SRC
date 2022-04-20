package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.creativetab.*;

public class ItemBoat extends Item
{
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        final float n = 1.0f;
        final float n2 = entityPlayer.prevRotationPitch + (entityPlayer.rotationPitch - entityPlayer.prevRotationPitch) * n;
        final float n3 = entityPlayer.prevRotationYaw + (entityPlayer.rotationYaw - entityPlayer.prevRotationYaw) * n;
        final Vec3 vec3 = new Vec3(entityPlayer.prevPosX + (entityPlayer.posX - entityPlayer.prevPosX) * n, entityPlayer.prevPosY + (entityPlayer.posY - entityPlayer.prevPosY) * n + entityPlayer.getEyeHeight(), entityPlayer.prevPosZ + (entityPlayer.posZ - entityPlayer.prevPosZ) * n);
        final float cos = MathHelper.cos(-n3 * 0.017453292f - 3.1415927f);
        final float sin = MathHelper.sin(-n3 * 0.017453292f - 3.1415927f);
        final float n4 = -MathHelper.cos(-n2 * 0.017453292f);
        final float sin2 = MathHelper.sin(-n2 * 0.017453292f);
        final float n5 = sin * n4;
        final float n6 = cos * n4;
        final double n7 = 5.0;
        if (world.rayTraceBlocks(vec3, vec3.addVector(n5 * n7, sin2 * n7, n6 * n7), true) == null) {
            return itemStack;
        }
        final Vec3 look = entityPlayer.getLook(n);
        final float n8 = 1.0f;
        final List entitiesWithinAABBExcludingEntity = world.getEntitiesWithinAABBExcludingEntity(entityPlayer, entityPlayer.getEntityBoundingBox().addCoord(look.xCoord * n7, look.yCoord * n7, look.zCoord * n7).expand(n8, n8, n8));
        while (0 < entitiesWithinAABBExcludingEntity.size()) {
            final Entity entity = entitiesWithinAABBExcludingEntity.get(0);
            if (entity.canBeCollidedWith()) {
                final float collisionBorderSize = entity.getCollisionBorderSize();
                if (entity.getEntityBoundingBox().expand(collisionBorderSize, collisionBorderSize, collisionBorderSize).isVecInside(vec3)) {}
            }
            int n9 = 0;
            ++n9;
        }
        return itemStack;
    }
    
    public ItemBoat() {
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabTransport);
    }
}
