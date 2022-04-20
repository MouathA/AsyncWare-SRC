package com.nquantum.util.block;

import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;

public class BlockUtil
{
    public EnumFacing facing;
    private static Minecraft mc;
    public BlockPos blockPos;
    
    private static float[] getDirectionToEntity(final Entity entity) {
        return new float[] { getYaw(entity) + BlockUtil.mc.thePlayer.rotationYaw, getPitch(entity) + BlockUtil.mc.thePlayer.rotationPitch };
    }
    
    public Vec3 getVector() {
        final Vec3i directionVec = this.facing.getDirectionVec();
        return new Vec3(this.blockPos).addVector(0.5, 0.5, 0.5).addVector(directionVec.getX() * 0.5, directionVec.getY() * 0.5, directionVec.getZ() * 0.5);
    }
    
    static {
        BlockUtil.mc = Minecraft.getMinecraft();
    }
    
    public static float[] getDirectionToBlock(final int n, final int n2, final int n3, final EnumFacing enumFacing) {
        final EntityEgg entityEgg = new EntityEgg(BlockUtil.mc.theWorld);
        entityEgg.posX = n + 0.5;
        entityEgg.posY = n2 + 0.5;
        entityEgg.posZ = n3 + 0.5;
        final EntityEgg entityEgg2 = entityEgg;
        entityEgg2.posX += enumFacing.getDirectionVec().getX() * 0.25;
        final EntityEgg entityEgg3 = entityEgg;
        entityEgg3.posY += enumFacing.getDirectionVec().getY() * 0.25;
        final EntityEgg entityEgg4 = entityEgg;
        entityEgg4.posZ += enumFacing.getDirectionVec().getZ() * 0.25;
        return getDirectionToEntity(entityEgg);
    }
    
    public static float getYaw(final Entity entity) {
        final double n = entity.posX - BlockUtil.mc.thePlayer.posX;
        final double n2 = entity.posZ - BlockUtil.mc.thePlayer.posZ;
        double degrees;
        if (n2 < 0.0 && n < 0.0) {
            degrees = 90.0 + Math.toDegrees(Math.atan(n2 / n));
        }
        else if (n2 < 0.0 && n > 0.0) {
            degrees = -90.0 + Math.toDegrees(Math.atan(n2 / n));
        }
        else {
            degrees = Math.toDegrees(-Math.atan(n / n2));
        }
        return MathHelper.wrapAngleTo180_float(-(BlockUtil.mc.thePlayer.rotationYaw - (float)degrees));
    }
    
    public BlockUtil(final BlockPos blockPos, final EnumFacing facing) {
        this.blockPos = blockPos;
        this.facing = facing;
    }
    
    public static float[] getRotationNeededForBlock(final EntityPlayer entityPlayer, final BlockPos blockPos) {
        final double n = blockPos.getX() - entityPlayer.posX;
        final double n2 = blockPos.getY() + 0.5 - (entityPlayer.posY + entityPlayer.getEyeHeight());
        final double n3 = blockPos.getZ() - entityPlayer.posZ;
        return new float[] { (float)(Math.atan2(n3, n) * 180.0 / 3.141592653589793) - 90.0f, (float)(-(Math.atan2(n2, Math.sqrt(n * n + n3 * n3)) * 180.0 / 3.141592653589793)) };
    }
    
    public static float getPitch(final Entity entity) {
        final double n = entity.posX - BlockUtil.mc.thePlayer.posX;
        final double n2 = entity.posZ - BlockUtil.mc.thePlayer.posZ;
        return -MathHelper.wrapAngleTo180_float(BlockUtil.mc.thePlayer.rotationPitch - (float)(-Math.toDegrees(Math.atan((entity.posY - 1.6 + entity.getEyeHeight() - BlockUtil.mc.thePlayer.posY) / MathHelper.sqrt_double(n * n + n2 * n2)))));
    }
}
