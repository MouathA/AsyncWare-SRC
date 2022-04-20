package com.nquantum.util.math;

import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.entity.*;

public class RotationUtil
{
    protected static Minecraft mc;
    
    public static float[] getRotations(final Entity entity) {
        final double n = entity.posX - RotationUtil.mc.thePlayer.posX;
        final double n2 = entity.posZ - RotationUtil.mc.thePlayer.posZ;
        double n3;
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            n3 = entityLivingBase.posY + entityLivingBase.getEyeHeight() - RotationUtil.mc.thePlayer.posY + RotationUtil.mc.thePlayer.getEyeHeight();
        }
        else {
            n3 = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0 - RotationUtil.mc.thePlayer.posY + RotationUtil.mc.thePlayer.getEyeHeight();
        }
        return new float[] { changeRotation(RotationUtil.mc.thePlayer.rotationYaw, (float)(Math.atan2(n2, n) * 180.0 / 3.141592653589793) - 90.0f), changeRotation(RotationUtil.mc.thePlayer.rotationPitch, (float)(-(Math.atan2(n3 - ((entity instanceof EntityPlayer) ? 0.25 : 0.0), MathHelper.sqrt_double(n * n + n2 * n2)) * 180.0 / 3.141592653589793))) };
    }
    
    static {
        RotationUtil.mc = Minecraft.getMinecraft();
    }
    
    public static float roundTo360(final float n) {
        float n2;
        for (n2 = n; n2 > 360.0f; n2 -= 360.0f) {}
        return n2;
    }
    
    public static Vec3 getVectorForRotation(final float n, final float n2) {
        final float cos = MathHelper.cos(-n2 * 0.017453292f - 3.1415927f);
        final float sin = MathHelper.sin(-n2 * 0.017453292f - 3.1415927f);
        final float n3 = -MathHelper.cos(-n * 0.017453292f);
        return new Vec3(sin * n3, MathHelper.sin(-n * 0.017453292f), cos * n3);
    }
    
    public static float[] faceTarget(final Entity entity, final float n, final float n2, final boolean b) {
        final double n3 = entity.posX - RotationUtil.mc.thePlayer.posX;
        final double n4 = entity.posZ - RotationUtil.mc.thePlayer.posZ;
        double n5;
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            n5 = entityLivingBase.posY + entityLivingBase.getEyeHeight() - (RotationUtil.mc.thePlayer.posY + RotationUtil.mc.thePlayer.getEyeHeight());
        }
        else {
            n5 = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0 - (RotationUtil.mc.thePlayer.posY + RotationUtil.mc.thePlayer.getEyeHeight());
        }
        final Random random = new Random();
        return new float[] { changeRotation(RotationUtil.mc.thePlayer.rotationYaw, (float)(Math.atan2(n4, n3) * 180.0 / 3.141592653589793) - 90.0f, n), changeRotation(RotationUtil.mc.thePlayer.rotationPitch, (float)(-(Math.atan2(n5 - ((entity instanceof EntityPlayer) ? 0.25 : 0.0), MathHelper.sqrt_double(n3 * n3 + n4 * n4)) * 180.0 / 3.141592653589793)), n2) };
    }
    
    public static float getPitchChange(final Entity entity, final double n, final float n2, final Double n3, final Double n4) {
        final double n5 = entity.posX - n3;
        final double n6 = entity.posZ - n4;
        return -MathHelper.wrapAngleTo180_float(n2 - (float)(-Math.toDegrees(Math.atan((n - 2.2 + entity.getEyeHeight() - Minecraft.getMinecraft().thePlayer.posY) / MathHelper.sqrt_double(n5 * n5 + n6 * n6))))) - 2.5f;
    }
    
    public static float changeRotation(final float n, final float n2, final float n3) {
        float wrapAngleTo180_float = MathHelper.wrapAngleTo180_float(n2 - n);
        if (wrapAngleTo180_float > n3) {
            wrapAngleTo180_float = n3;
        }
        if (wrapAngleTo180_float < -n3) {
            wrapAngleTo180_float = -n3;
        }
        return n + wrapAngleTo180_float;
    }
    
    public static float changeRotation(final float n, final float n2) {
        float wrapAngleTo180_float = MathHelper.wrapAngleTo180_float(n2 - n);
        if (wrapAngleTo180_float > 1000.0f) {
            wrapAngleTo180_float = 1000.0f;
        }
        if (wrapAngleTo180_float < -1000.0f) {
            wrapAngleTo180_float = -1000.0f;
        }
        return n + wrapAngleTo180_float;
    }
    
    public static float[] getRotationFromPosition(final double n, final double n2, final double n3) {
        final double n4 = n - Minecraft.getMinecraft().thePlayer.posX;
        final double n5 = n2 - Minecraft.getMinecraft().thePlayer.posZ;
        return new float[] { (float)(Math.atan2(n5, n4) * 180.0 / 3.141592653589793) - 90.0f, (float)(-(Math.atan2(n3 - Minecraft.getMinecraft().thePlayer.posY - 1.2, MathHelper.sqrt_double(n4 * n4 + n5 * n5)) * 180.0 / 3.141592653589793)) };
    }
    
    public static float getYawChange(final double n, final double n2, final float n3, final Double n4, final Double n5) {
        final double n6 = n - n4;
        final double n7 = n2 - n5;
        double degrees;
        if (n7 < 0.0 && n6 < 0.0) {
            degrees = 90.0 + Math.toDegrees(Math.atan(n7 / n6));
        }
        else if (n7 < 0.0 && n6 > 0.0) {
            degrees = -90.0 + Math.toDegrees(Math.atan(n7 / n6));
        }
        else {
            degrees = Math.toDegrees(-Math.atan(n6 / n7));
        }
        return MathHelper.wrapAngleTo180_float(-(n3 - (float)degrees));
    }
    
    public static float getYawToEntity(final Entity entity, final boolean b) {
        final EntityPlayerSP thePlayer = RotationUtil.mc.thePlayer;
        final double n = (b ? entity.prevPosX : entity.posX) - (b ? thePlayer.prevPosX : thePlayer.posX);
        final double n2 = (b ? entity.prevPosZ : entity.posZ) - (b ? thePlayer.prevPosZ : thePlayer.posZ);
        final float n3 = b ? RotationUtil.mc.thePlayer.prevRotationYaw : RotationUtil.mc.thePlayer.rotationYaw;
        return n3 + MathHelper.wrapAngleTo180_float((float)(Math.atan2(n2, n) * 180.0 / 3.141592653589793) - 90.0f - n3);
    }
    
    public static float updateRotation(final float n, final float n2, final float n3) {
        float wrapAngleTo180_float = MathHelper.wrapAngleTo180_float(n2 - n);
        if (wrapAngleTo180_float > n3) {
            wrapAngleTo180_float = n3;
        }
        if (wrapAngleTo180_float < -n3) {
            wrapAngleTo180_float = -n3;
        }
        return n + wrapAngleTo180_float;
    }
    
    public static float getYawChange(final float n, final double n2, final double n3) {
        final double n4 = n2 - Minecraft.getMinecraft().thePlayer.posX;
        final double n5 = n3 - Minecraft.getMinecraft().thePlayer.posZ;
        double degrees = 0.0;
        if (n5 < 0.0 && n4 < 0.0) {
            if (n4 != 0.0) {
                degrees = 90.0 + Math.toDegrees(Math.atan(n5 / n4));
            }
        }
        else if (n5 < 0.0 && n4 > 0.0) {
            if (n4 != 0.0) {
                degrees = -90.0 + Math.toDegrees(Math.atan(n5 / n4));
            }
        }
        else if (n5 != 0.0) {
            degrees = Math.toDegrees(-Math.atan(n4 / n5));
        }
        return MathHelper.wrapAngleTo180_float(-(n - (float)degrees));
    }
}
