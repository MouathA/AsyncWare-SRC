package com.nquantum.util.raytrace;

import net.minecraft.client.*;
import net.minecraft.entity.*;
import com.google.common.base.*;
import optfine.*;
import java.util.*;
import net.minecraft.util.*;

public class RayCastUtil
{
    private static final Minecraft mc;
    
    public static Entity rayCast(final double n, final float n2, final float n3) {
        final Vec3 positionEyes = RayCastUtil.mc.thePlayer.getPositionEyes(1.0f);
        if (n > 3.0) {}
        final Vec3 vectorForRotation = getVectorForRotation(n3, n2);
        final Vec3 addVector = positionEyes.addVector(vectorForRotation.xCoord * n, vectorForRotation.yCoord * n, vectorForRotation.zCoord * n);
        Entity entity = null;
        final float n4 = 1.0f;
        final List entitiesInAABBexcluding = RayCastUtil.mc.theWorld.getEntitiesInAABBexcluding(RayCastUtil.mc.getRenderViewEntity(), RayCastUtil.mc.getRenderViewEntity().getEntityBoundingBox().addCoord(vectorForRotation.xCoord * n, vectorForRotation.yCoord * n, vectorForRotation.zCoord * n).expand(n4, n4, n4), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
        double n5 = n;
        while (0 < entitiesInAABBexcluding.size()) {
            final Entity entity2 = entitiesInAABBexcluding.get(0);
            final float collisionBorderSize = entity2.getCollisionBorderSize();
            final AxisAlignedBB expand = entity2.getEntityBoundingBox().expand(collisionBorderSize, collisionBorderSize, collisionBorderSize);
            final MovingObjectPosition calculateIntercept = expand.calculateIntercept(positionEyes, addVector);
            if (expand.isVecInside(positionEyes)) {
                if (n5 >= 0.0) {
                    entity = entity2;
                    final Vec3 vec3 = (calculateIntercept == null) ? positionEyes : calculateIntercept.hitVec;
                    n5 = 0.0;
                }
            }
            else if (calculateIntercept != null) {
                final double distanceTo = positionEyes.distanceTo(calculateIntercept.hitVec);
                if (distanceTo < n5 || n5 == 0.0) {
                    if (Reflector.ForgeEntity_canRiderInteract.exists()) {
                        Reflector.callBoolean(entity2, Reflector.ForgeEntity_canRiderInteract, new Object[0]);
                    }
                    if (entity2 == RayCastUtil.mc.getRenderViewEntity().ridingEntity) {
                        if (n5 == 0.0) {
                            entity = entity2;
                            final Vec3 hitVec = calculateIntercept.hitVec;
                        }
                    }
                    else {
                        entity = entity2;
                        final Vec3 hitVec2 = calculateIntercept.hitVec;
                        n5 = distanceTo;
                    }
                }
            }
            int n6 = 0;
            ++n6;
        }
        return entity;
    }
    
    public static Vec3 getVectorForRotation(final float n, final float n2) {
        final float cos = MathHelper.cos(-n2 * 0.017453292f - 3.1415927f);
        final float sin = MathHelper.sin(-n2 * 0.017453292f - 3.1415927f);
        final float n3 = -MathHelper.cos(-n * 0.017453292f);
        return new Vec3(sin * n3, MathHelper.sin(-n * 0.017453292f), cos * n3);
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
