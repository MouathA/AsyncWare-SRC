package com.nquantum.util;

import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.potion.*;
import net.minecraft.network.*;
import net.minecraft.init.*;
import org.lwjgl.input.*;
import net.minecraft.util.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.client.*;

public class MovementUtil
{
    protected static Minecraft mc;
    
    public static float getDistanceToGround(final Entity entity) {
        if (MovementUtil.mc.thePlayer.isCollidedVertically && MovementUtil.mc.thePlayer.onGround) {
            return 0.0f;
        }
        float n = (float)entity.posY;
        while (n > 0.0f) {
            final int[] array = { 53, 67, 108, 109, 114, 128, 134, 135, 136, 156, 163, 164, 180 };
            final int[] array2 = { 6, 27, 28, 30, 31, 32, 37, 38, 39, 40, 50, 51, 55, 59, 63, 65, 66, 68, 69, 70, 72, 75, 76, 77, 83, 92, 93, 94, 104, 105, 106, 115, 119, 131, 132, 143, 147, 148, 149, 150, 157, 171, 175, 176, 177 };
            final Block block = MovementUtil.mc.theWorld.getBlockState(new BlockPos(entity.posX, n - 1.0f, entity.posZ)).getBlock();
            if (!(block instanceof BlockAir)) {
                if (Block.getIdFromBlock(block) == 44 || Block.getIdFromBlock(block) == 126) {
                    return ((float)(entity.posY - n - 0.5) < 0.0f) ? 0.0f : ((float)(entity.posY - n - 0.5));
                }
                int[] array3;
                int n2 = 0;
                while (0 < (array3 = array).length) {
                    if (Block.getIdFromBlock(block) == array3[0]) {
                        return ((float)(entity.posY - n - 1.0) < 0.0f) ? 0.0f : ((float)(entity.posY - n - 1.0));
                    }
                    ++n2;
                }
                int[] array4;
                while (0 < (array4 = array2).length) {
                    if (Block.getIdFromBlock(block) == array4[0]) {
                        return ((float)(entity.posY - n) < 0.0f) ? 0.0f : ((float)(entity.posY - n));
                    }
                    ++n2;
                }
                return (float)(entity.posY - n + block.getBlockBoundsMaxY() - 1.0);
            }
            else {
                --n;
            }
        }
        return 0.0f;
    }
    
    public static double defaultMoveSpeed() {
        return MovementUtil.mc.thePlayer.isSprinting() ? 0.28700000047683716 : 0.22300000488758087;
    }
    
    public static Block getBlockAtPos(final BlockPos blockPos) {
        return MovementUtil.mc.theWorld.getBlockState(blockPos).getBlock();
    }
    
    public static float getDir() {
        float rotationYaw = MovementUtil.mc.thePlayer.rotationYaw;
        if (MovementUtil.mc.thePlayer.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float n;
        if (MovementUtil.mc.thePlayer.moveForward < 0.0f) {
            n = -0.5f;
        }
        else if (MovementUtil.mc.thePlayer.moveForward > 0.0f) {
            n = 0.5f;
        }
        else {
            n = 1.0f;
        }
        if (MovementUtil.mc.thePlayer.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * n;
        }
        if (MovementUtil.mc.thePlayer.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * n;
        }
        return rotationYaw * 0.017453292f;
    }
    
    public static boolean isMovingOnGround() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: getstatic       com/nquantum/util/MovementUtil.mc:Lnet/minecraft/client/Minecraft;
        //     6: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //     9: getfield        net/minecraft/client/entity/EntityPlayerSP.onGround:Z
        //    12: ifeq            19
        //    15: iconst_1       
        //    16: goto            20
        //    19: iconst_0       
        //    20: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static double defaultSpeed() {
        double n = 0.2873;
        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
            n *= 1.0 + 0.2 * (Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        return n;
    }
    
    public static double getLastDistance() {
        return Math.hypot(MovementUtil.mc.thePlayer.posX - MovementUtil.mc.thePlayer.prevPosX, MovementUtil.mc.thePlayer.posZ - MovementUtil.mc.thePlayer.prevPosZ);
    }
    
    public static void setSpeed(final double n, final float n2, final double n3, final double n4) {
        double n5 = n4;
        double n6 = n3;
        float n7 = n2;
        if (n5 != 0.0) {
            if (n6 > 0.0) {
                n7 += ((n5 > 0.0) ? -45 : 45);
            }
            else if (n6 < 0.0) {
                n7 += ((n5 > 0.0) ? 45 : -45);
            }
            n6 = 0.0;
            if (n5 > 0.0) {
                n5 = 1.0;
            }
            else if (n5 < 0.0) {
                n5 = -1.0;
            }
        }
        if (n6 > 0.0) {
            n6 = 1.0;
        }
        else if (n6 < 0.0) {
            n6 = -1.0;
        }
        final double cos = Math.cos(Math.toRadians(n7 + 90.0f));
        final double sin = Math.sin(Math.toRadians(n7 + 90.0f));
        MovementUtil.mc.thePlayer.motionX = n5 * n * cos + n6 * n * sin;
        MovementUtil.mc.thePlayer.motionZ = n5 * n * sin - n6 * n * cos;
    }
    
    public static double jumpHeight() {
        if (MovementUtil.mc.thePlayer.isPotionActive(Potion.jump)) {
            return 0.419999986886978 + 0.1 * (MovementUtil.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1);
        }
        return 0.419999986886978;
    }
    
    public static void sendPosition(final double n, final double n2, final double n3, final boolean b, final boolean b2) {
        if (!b2) {
            MovementUtil.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(MovementUtil.mc.thePlayer.posX, MovementUtil.mc.thePlayer.posY + n2, MovementUtil.mc.thePlayer.posZ, b));
        }
        else {
            MovementUtil.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(MovementUtil.mc.thePlayer.posX + n, MovementUtil.mc.thePlayer.posY + n2, MovementUtil.mc.thePlayer.posZ + n3, b));
        }
    }
    
    public static boolean checkTeleport(final double n, final double n2, final double n3, final double n4) {
        final double n5 = MovementUtil.mc.thePlayer.posX - n;
        final double n6 = MovementUtil.mc.thePlayer.posY - n2;
        final double n7 = MovementUtil.mc.thePlayer.posZ - n3;
        final double n8 = (double)(Math.round(Math.sqrt(MovementUtil.mc.thePlayer.getDistanceSq(n, n2, n3)) / n4 + 0.49999999999) - 1L);
        double posX = MovementUtil.mc.thePlayer.posX;
        double posY = MovementUtil.mc.thePlayer.posY;
        double posZ = MovementUtil.mc.thePlayer.posZ;
        while (1 < n8) {
            posX += (n - MovementUtil.mc.thePlayer.posX) / n8;
            posZ += (n3 - MovementUtil.mc.thePlayer.posZ) / n8;
            posY += (n2 - MovementUtil.mc.thePlayer.posY) / n8;
            if (!MovementUtil.mc.theWorld.getCollidingBoundingBoxes(MovementUtil.mc.thePlayer, new AxisAlignedBB(posX - 0.3, posY, posZ - 0.3, posX + 0.3, posY + 1.8, posZ + 0.3)).isEmpty()) {
                return false;
            }
            int n9 = 0;
            ++n9;
        }
        return true;
    }
    
    public static double getJumpBoostModifier(double n) {
        if (MovementUtil.mc.thePlayer.isPotionActive(Potion.jump)) {
            n += (MovementUtil.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1f;
        }
        return n;
    }
    
    public static void setMotion(final double n) {
        double n2 = MovementUtil.mc.thePlayer.movementInput.moveForward;
        double n3 = MovementUtil.mc.thePlayer.movementInput.moveStrafe;
        float rotationYaw = MovementUtil.mc.thePlayer.rotationYaw;
        if (n2 == 0.0 && n3 == 0.0) {
            MovementUtil.mc.thePlayer.motionX = 0.0;
            MovementUtil.mc.thePlayer.motionZ = 0.0;
        }
        else {
            if (n2 != 0.0) {
                if (n3 > 0.0) {
                    rotationYaw += ((n2 > 0.0) ? -45 : 45);
                }
                else if (n3 < 0.0) {
                    rotationYaw += ((n2 > 0.0) ? 45 : -45);
                }
                n3 = 0.0;
                if (n2 > 0.0) {
                    n2 = 1.0;
                }
                else if (n2 < 0.0) {
                    n2 = -1.0;
                }
            }
            MovementUtil.mc.thePlayer.motionX = n2 * n * Math.cos(Math.toRadians(rotationYaw + 90.0f)) + n3 * n * Math.sin(Math.toRadians(rotationYaw + 90.0f));
            MovementUtil.mc.thePlayer.motionZ = n2 * n * Math.sin(Math.toRadians(rotationYaw + 90.0f)) - n3 * n * Math.cos(Math.toRadians(rotationYaw + 90.0f));
        }
    }
    
    public static void setSpeed(final float n) {
        MovementUtil.mc.thePlayer.motionX = -Math.sin(getDirection()) * n;
        MovementUtil.mc.thePlayer.motionZ = Math.cos(getDirection()) * n;
    }
    
    public static double getDistanceToGround() {
        BlockPos blockPos;
        while (true) {
            blockPos = new BlockPos(MovementUtil.mc.thePlayer.posX, MovementUtil.mc.thePlayer.posY - 0, MovementUtil.mc.thePlayer.posZ);
            if (MovementUtil.mc.theWorld.getBlockState(blockPos).getBlock() != Blocks.air && MovementUtil.mc.theWorld.getBlockState(blockPos).getBlock() != Blocks.grass && MovementUtil.mc.theWorld.getBlockState(blockPos).getBlock() != Blocks.tallgrass && MovementUtil.mc.theWorld.getBlockState(blockPos).getBlock() != Blocks.red_flower && MovementUtil.mc.theWorld.getBlockState(blockPos).getBlock() != Blocks.yellow_flower) {
                break;
            }
            int n = 0;
            ++n;
        }
        return MovementUtil.mc.thePlayer.posY - blockPos.getY() - 1.0;
    }
    
    public static double getJumpHeight(final double n) {
        return MovementUtil.mc.thePlayer.isPotionActive(Potion.jump) ? (n + 0.1 * (MovementUtil.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1)) : n;
    }
    
    public static float getRetarded() {
        return 0.2873f;
    }
    
    public static double getBaseMoveSpeed() {
        double n = 0.2875;
        if (MovementUtil.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            n *= 1.0 + 0.2 * (MovementUtil.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        return n;
    }
    
    public static float getSpeed() {
        return (float)Math.sqrt(MovementUtil.mc.thePlayer.motionX * MovementUtil.mc.thePlayer.motionX + MovementUtil.mc.thePlayer.motionZ * MovementUtil.mc.thePlayer.motionZ);
    }
    
    static {
        MovementUtil.mc = Minecraft.getMinecraft();
    }
    
    public static boolean isMoving2() {
        return Keyboard.isKeyDown(MovementUtil.mc.gameSettings.keyBindForward.getKeyCode()) || Keyboard.isKeyDown(MovementUtil.mc.gameSettings.keyBindBack.getKeyCode()) || Keyboard.isKeyDown(MovementUtil.mc.gameSettings.keyBindLeft.getKeyCode()) || Keyboard.isKeyDown(MovementUtil.mc.gameSettings.keyBindRight.getKeyCode());
    }
    
    public static void damagePlayer() {
        MovementUtil.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(MovementUtil.mc.thePlayer.posX, MovementUtil.mc.thePlayer.posY + 3.007, MovementUtil.mc.thePlayer.posZ, false));
        MovementUtil.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(MovementUtil.mc.thePlayer.posX, MovementUtil.mc.thePlayer.posY, MovementUtil.mc.thePlayer.posZ, false));
        MovementUtil.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(MovementUtil.mc.thePlayer.posX, MovementUtil.mc.thePlayer.posY, MovementUtil.mc.thePlayer.posZ, true));
    }
    
    public static float getDirection() {
        float rotationYaw = MovementUtil.mc.thePlayer.rotationYaw;
        if (MovementUtil.mc.thePlayer.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float n = 1.0f;
        if (MovementUtil.mc.thePlayer.moveForward < 0.0f) {
            n = -0.5f;
        }
        else if (MovementUtil.mc.thePlayer.moveForward > 0.0f) {
            n = 0.5f;
        }
        if (MovementUtil.mc.thePlayer.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * n;
        }
        if (MovementUtil.mc.thePlayer.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * n;
        }
        return rotationYaw * 0.017453292f;
    }
    
    public static void setSpeed(final double n) {
        setSpeed(n, Minecraft.getMinecraft().thePlayer.rotationYaw, Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe, Minecraft.getMinecraft().thePlayer.movementInput.moveForward);
    }
    
    public static boolean isBlockAboveHead() {
        return !MovementUtil.mc.theWorld.getCollidingBoundingBoxes(MovementUtil.mc.thePlayer, new AxisAlignedBB(MovementUtil.mc.thePlayer.posX - 0.3, MovementUtil.mc.thePlayer.posY + MovementUtil.mc.thePlayer.getEyeHeight(), MovementUtil.mc.thePlayer.posZ + 0.3, MovementUtil.mc.thePlayer.posX + 0.3, MovementUtil.mc.thePlayer.posY + 2.5, MovementUtil.mc.thePlayer.posZ - 0.3)).isEmpty();
    }
    
    public static float[] getRotationsBlock(final BlockPos blockPos, final EnumFacing enumFacing) {
        final double n = blockPos.getX() + 0.5 - MovementUtil.mc.thePlayer.posX + enumFacing.getFrontOffsetX() / 2.0;
        final double n2 = blockPos.getZ() + 0.5 - MovementUtil.mc.thePlayer.posZ + enumFacing.getFrontOffsetZ() / 2.0;
        final double n3 = MovementUtil.mc.thePlayer.posY + MovementUtil.mc.thePlayer.getEyeHeight() - (blockPos.getY() + 0.5);
        final double n4 = MathHelper.sqrt_double(n * n + n2 * n2);
        float n5 = (float)(Math.atan2(n2, n) * 180.0 / 3.141592653589793) - 90.0f;
        final float n6 = (float)(Math.atan2(n3, n4) * 180.0 / 3.141592653589793);
        if (n5 < 0.0f) {
            n5 += 360.0f;
        }
        return new float[] { n5, n6 };
    }
    
    public static int getJumpEffect() {
        if (MovementUtil.mc.thePlayer.isPotionActive(Potion.jump)) {
            return MovementUtil.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1;
        }
        return 0;
    }
    
    public static boolean isRealCollidedH(final double n) {
        final AxisAlignedBB axisAlignedBB = new AxisAlignedBB(MovementUtil.mc.thePlayer.posX - 0.3, MovementUtil.mc.thePlayer.posY + 0.5, MovementUtil.mc.thePlayer.posZ + 0.3, MovementUtil.mc.thePlayer.posX + 0.3, MovementUtil.mc.thePlayer.posY + 1.9, MovementUtil.mc.thePlayer.posZ - 0.3);
        return !MovementUtil.mc.theWorld.getCollidingBoundingBoxes(MovementUtil.mc.thePlayer, axisAlignedBB.offset(0.3 + n, 0.0, 0.0)).isEmpty() || !MovementUtil.mc.theWorld.getCollidingBoundingBoxes(MovementUtil.mc.thePlayer, axisAlignedBB.offset(-0.3 - n, 0.0, 0.0)).isEmpty() || !MovementUtil.mc.theWorld.getCollidingBoundingBoxes(MovementUtil.mc.thePlayer, axisAlignedBB.offset(0.0, 0.0, 0.3 + n)).isEmpty() || !MovementUtil.mc.theWorld.getCollidingBoundingBoxes(MovementUtil.mc.thePlayer, axisAlignedBB.offset(0.0, 0.0, -0.3 - n)).isEmpty();
    }
    
    public static boolean isCollidedH(final double n) {
        final AxisAlignedBB axisAlignedBB = new AxisAlignedBB(MovementUtil.mc.thePlayer.posX - 0.3, MovementUtil.mc.thePlayer.posY + 2.0, MovementUtil.mc.thePlayer.posZ + 0.3, MovementUtil.mc.thePlayer.posX + 0.3, MovementUtil.mc.thePlayer.posY + 3.0, MovementUtil.mc.thePlayer.posZ - 0.3);
        return !MovementUtil.mc.theWorld.getCollidingBoundingBoxes(MovementUtil.mc.thePlayer, axisAlignedBB.offset(0.3 + n, 0.0, 0.0)).isEmpty() || !MovementUtil.mc.theWorld.getCollidingBoundingBoxes(MovementUtil.mc.thePlayer, axisAlignedBB.offset(-0.3 - n, 0.0, 0.0)).isEmpty() || !MovementUtil.mc.theWorld.getCollidingBoundingBoxes(MovementUtil.mc.thePlayer, axisAlignedBB.offset(0.0, 0.0, 0.3 + n)).isEmpty() || !MovementUtil.mc.theWorld.getCollidingBoundingBoxes(MovementUtil.mc.thePlayer, axisAlignedBB.offset(0.0, 0.0, -0.3 - n)).isEmpty();
    }
    
    public static void strafe(final float p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: return         
        //     4: invokestatic    com/nquantum/util/MovementUtil.getDirection:()F
        //     7: f2d            
        //     8: dstore_1       
        //     9: invokestatic    net/minecraft/client/Minecraft.getMinecraft:()Lnet/minecraft/client/Minecraft;
        //    12: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    15: dload_1        
        //    16: invokestatic    java/lang/Math.sin:(D)D
        //    19: dneg           
        //    20: fload_0        
        //    21: f2d            
        //    22: dmul           
        //    23: putfield        net/minecraft/client/entity/EntityPlayerSP.motionX:D
        //    26: invokestatic    net/minecraft/client/Minecraft.getMinecraft:()Lnet/minecraft/client/Minecraft;
        //    29: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    32: dload_1        
        //    33: invokestatic    java/lang/Math.cos:(D)D
        //    36: fload_0        
        //    37: f2d            
        //    38: dmul           
        //    39: putfield        net/minecraft/client/entity/EntityPlayerSP.motionZ:D
        //    42: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static void strafe(final double n) {
        final float n2 = MovementUtil.mc.thePlayer.rotationYaw * 0.017453292f;
        final float n3 = MovementUtil.mc.thePlayer.rotationYaw * 0.017453292f - 4.712389f;
        final float n4 = MovementUtil.mc.thePlayer.rotationYaw * 0.017453292f + 4.712389f;
        final float n5 = MovementUtil.mc.thePlayer.rotationYaw * 0.017453292f + 0.5969026f;
        final float n6 = MovementUtil.mc.thePlayer.rotationYaw * 0.017453292f - 0.5969026f;
        final float n7 = MovementUtil.mc.thePlayer.rotationYaw * 0.017453292f - 2.3876104f;
        final float n8 = MovementUtil.mc.thePlayer.rotationYaw * 0.017453292f + 2.3876104f;
        if (MovementUtil.mc.gameSettings.keyBindForward.pressed) {
            if (MovementUtil.mc.gameSettings.keyBindLeft.pressed && !MovementUtil.mc.gameSettings.keyBindRight.pressed) {
                final EntityPlayerSP thePlayer = MovementUtil.mc.thePlayer;
                thePlayer.motionX -= MathHelper.sin(n6) * n;
                final EntityPlayerSP thePlayer2 = MovementUtil.mc.thePlayer;
                thePlayer2.motionZ += MathHelper.cos(n6) * n;
            }
            else if (MovementUtil.mc.gameSettings.keyBindRight.pressed && !MovementUtil.mc.gameSettings.keyBindLeft.pressed) {
                final EntityPlayerSP thePlayer3 = MovementUtil.mc.thePlayer;
                thePlayer3.motionX -= MathHelper.sin(n5) * n;
                final EntityPlayerSP thePlayer4 = MovementUtil.mc.thePlayer;
                thePlayer4.motionZ += MathHelper.cos(n5) * n;
            }
            else {
                final EntityPlayerSP thePlayer5 = MovementUtil.mc.thePlayer;
                thePlayer5.motionX -= MathHelper.sin(n2) * n;
                final EntityPlayerSP thePlayer6 = MovementUtil.mc.thePlayer;
                thePlayer6.motionZ += MathHelper.cos(n2) * n;
            }
        }
        else if (MovementUtil.mc.gameSettings.keyBindBack.pressed) {
            if (MovementUtil.mc.gameSettings.keyBindLeft.pressed && !MovementUtil.mc.gameSettings.keyBindRight.pressed) {
                final EntityPlayerSP thePlayer7 = MovementUtil.mc.thePlayer;
                thePlayer7.motionX -= MathHelper.sin(n7) * n;
                final EntityPlayerSP thePlayer8 = MovementUtil.mc.thePlayer;
                thePlayer8.motionZ += MathHelper.cos(n7) * n;
            }
            else if (MovementUtil.mc.gameSettings.keyBindRight.pressed && !MovementUtil.mc.gameSettings.keyBindLeft.pressed) {
                final EntityPlayerSP thePlayer9 = MovementUtil.mc.thePlayer;
                thePlayer9.motionX -= MathHelper.sin(n8) * n;
                final EntityPlayerSP thePlayer10 = MovementUtil.mc.thePlayer;
                thePlayer10.motionZ += MathHelper.cos(n8) * n;
            }
            else {
                final EntityPlayerSP thePlayer11 = MovementUtil.mc.thePlayer;
                thePlayer11.motionX += MathHelper.sin(n2) * n;
                final EntityPlayerSP thePlayer12 = MovementUtil.mc.thePlayer;
                thePlayer12.motionZ -= MathHelper.cos(n2) * n;
            }
        }
        else if (MovementUtil.mc.gameSettings.keyBindLeft.pressed && !MovementUtil.mc.gameSettings.keyBindRight.pressed && !MovementUtil.mc.gameSettings.keyBindForward.pressed && !MovementUtil.mc.gameSettings.keyBindBack.pressed) {
            final EntityPlayerSP thePlayer13 = MovementUtil.mc.thePlayer;
            thePlayer13.motionX += MathHelper.sin(n3) * n;
            final EntityPlayerSP thePlayer14 = MovementUtil.mc.thePlayer;
            thePlayer14.motionZ -= MathHelper.cos(n3) * n;
        }
        else if (MovementUtil.mc.gameSettings.keyBindRight.pressed && !MovementUtil.mc.gameSettings.keyBindLeft.pressed && !MovementUtil.mc.gameSettings.keyBindForward.pressed && !MovementUtil.mc.gameSettings.keyBindBack.pressed) {
            final EntityPlayerSP thePlayer15 = MovementUtil.mc.thePlayer;
            thePlayer15.motionX += MathHelper.sin(n4) * n;
            final EntityPlayerSP thePlayer16 = MovementUtil.mc.thePlayer;
            thePlayer16.motionZ -= MathHelper.cos(n4) * n;
        }
    }
    
    public static int getSpeedEffect() {
        if (MovementUtil.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            return MovementUtil.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
        }
        return 0;
    }
    
    public static Block getBlockUnderPlayer(final EntityPlayer entityPlayer, final double n) {
        return Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(entityPlayer.posX, entityPlayer.posY - n, entityPlayer.posZ)).getBlock();
    }
    
    public static void strafe() {
        strafe(getSpeed());
    }
    
    public static boolean isOnGround(final double n) {
        return !MovementUtil.mc.theWorld.getCollidingBoundingBoxes(MovementUtil.mc.thePlayer, MovementUtil.mc.thePlayer.getEntityBoundingBox().offset(0.0, -n, 0.0)).isEmpty();
    }
    
    public double getTickDist() {
        return Math.sqrt(Math.pow(MovementUtil.mc.thePlayer.posX - MovementUtil.mc.thePlayer.lastTickPosX, 2.0) + Math.pow(MovementUtil.mc.thePlayer.posZ - MovementUtil.mc.thePlayer.lastTickPosZ, 2.0));
    }
    
    public static Block getBlockAtPosC(final double n, final double n2, final double n3) {
        final EntityPlayerSP thePlayer = Minecraft.getMinecraft().thePlayer;
        return Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(thePlayer.posX + n, thePlayer.posY + n2, thePlayer.posZ + n3)).getBlock();
    }
    
    public static void damageVerus() {
        double n = 0.0;
        while (true) {
            n += 0.5;
            MovementUtil.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(MovementUtil.mc.thePlayer.posX, MovementUtil.mc.thePlayer.posY + n, MovementUtil.mc.thePlayer.posZ, true));
            MovementUtil.mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(MovementUtil.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
            int n2 = 0;
            ++n2;
        }
    }
}
