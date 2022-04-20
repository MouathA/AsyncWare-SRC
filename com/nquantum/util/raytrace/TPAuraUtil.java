package com.nquantum.util.raytrace;

import net.minecraft.client.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import com.nquantum.util.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.enchantment.*;
import net.minecraft.potion.*;

public class TPAuraUtil
{
    static double yPre;
    static double xPre;
    private static Minecraft mc;
    static double zPreEn;
    static double zPre;
    static double y;
    static double yPreEn;
    static double x;
    static double xPreEn;
    static double z;
    
    public static double normalizeAngle(final double n) {
        return (n + 360.0) % 360.0;
    }
    
    public static BlockPos getBlockPos(final Vec3 vec3) {
        return new BlockPos(vec3.xCoord, vec3.yCoord, vec3.zCoord);
    }
    
    static {
        TPAuraUtil.mc = Minecraft.getMinecraft();
    }
    
    public static boolean infiniteReach(final double n, final double n2, final double n3, final ArrayList list, final ArrayList list2, final EntityLivingBase entityLivingBase) {
        TPAuraUtil.xPreEn = entityLivingBase.posX;
        TPAuraUtil.yPreEn = entityLivingBase.posY;
        TPAuraUtil.zPreEn = entityLivingBase.posZ;
        TPAuraUtil.xPre = TPAuraUtil.mc.thePlayer.posX;
        TPAuraUtil.yPre = TPAuraUtil.mc.thePlayer.posY;
        TPAuraUtil.zPre = TPAuraUtil.mc.thePlayer.posZ;
        TPAuraUtil.mc.thePlayer.isSneaking();
        list2.clear();
        list.clear();
        while (0 < n) {
            int n4 = 0;
            ++n4;
            if (n2 * 0 > n) {
                break;
            }
            int n5 = 0;
            ++n5;
        }
        new Vec3(TPAuraUtil.mc.thePlayer.posX, TPAuraUtil.mc.thePlayer.posY, TPAuraUtil.mc.thePlayer.posZ);
        new Vec3(entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ);
        false;
        false;
        MovingObjectPosition movingObjectPosition;
        if ((movingObjectPosition = rayTracePos(new Vec3(TPAuraUtil.mc.thePlayer.posX, TPAuraUtil.mc.thePlayer.posY + TPAuraUtil.mc.thePlayer.getEyeHeight(), TPAuraUtil.mc.thePlayer.posZ), new Vec3(entityLivingBase.posX, entityLivingBase.posY + TPAuraUtil.mc.thePlayer.getEyeHeight(), entityLivingBase.posZ), false, false, true)) != null) {
            final MovingObjectPosition rayTracePos;
            if ((rayTracePos = rayTracePos(new Vec3(TPAuraUtil.mc.thePlayer.posX, TPAuraUtil.mc.thePlayer.posY, TPAuraUtil.mc.thePlayer.posZ), new Vec3(entityLivingBase.posX, TPAuraUtil.mc.thePlayer.posY, entityLivingBase.posZ), false, false, true)) != null || (movingObjectPosition = rayTracePos(new Vec3(TPAuraUtil.mc.thePlayer.posX, TPAuraUtil.mc.thePlayer.posY + TPAuraUtil.mc.thePlayer.getEyeHeight(), TPAuraUtil.mc.thePlayer.posZ), new Vec3(entityLivingBase.posX, TPAuraUtil.mc.thePlayer.posY + TPAuraUtil.mc.thePlayer.getEyeHeight(), entityLivingBase.posZ), false, false, true)) != null) {
                MovingObjectPosition movingObjectPosition2 = null;
                if (rayTracePos == null) {
                    movingObjectPosition2 = movingObjectPosition;
                }
                if (movingObjectPosition == null) {
                    movingObjectPosition2 = rayTracePos;
                }
                if (movingObjectPosition2 != null) {
                    if (movingObjectPosition2.getBlockPos() == null) {
                        return false;
                    }
                    final BlockPos blockPos = movingObjectPosition2.getBlockPos();
                    TPAuraUtil.y = blockPos.up().getY();
                    TPAuraUtil.yPreEn = blockPos.up().getY();
                    Block block = null;
                    Boolean b = false;
                    while (0 < n3) {
                        final MovingObjectPosition rayTracePos2 = rayTracePos(new Vec3(TPAuraUtil.mc.thePlayer.posX, blockPos.getY() + 0, TPAuraUtil.mc.thePlayer.posZ), new Vec3(entityLivingBase.posX, blockPos.getY() + 0, entityLivingBase.posZ), false, false, true);
                        if (rayTracePos2 != null) {
                            if (rayTracePos2.getBlockPos() != null) {
                                final Block block2 = TPAuraUtil.mc.theWorld.getBlockState(rayTracePos2.getBlockPos()).getBlock();
                                if (block2.getMaterial() == Material.air) {
                                    final boolean b2 = block instanceof BlockFence;
                                    TPAuraUtil.y = blockPos.getY() + 0;
                                    TPAuraUtil.yPreEn = blockPos.getY() + 0;
                                    b = true;
                                    break;
                                }
                                block = block2;
                            }
                        }
                        int n6 = 0;
                        ++n6;
                    }
                    final double n7 = TPAuraUtil.mc.thePlayer.posX - TPAuraUtil.xPreEn;
                    final double n8 = TPAuraUtil.mc.thePlayer.posZ - TPAuraUtil.zPreEn;
                    if (!b) {
                        return false;
                    }
                }
            }
            else {
                final MovingObjectPosition rayTracePos3 = rayTracePos(new Vec3(TPAuraUtil.mc.thePlayer.posX, TPAuraUtil.mc.thePlayer.posY, TPAuraUtil.mc.thePlayer.posZ), new Vec3(entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ), false, false, false);
                if (rayTracePos3 != null && rayTracePos3.entityHit == null) {
                    TPAuraUtil.y = TPAuraUtil.mc.thePlayer.posY;
                    TPAuraUtil.yPreEn = TPAuraUtil.mc.thePlayer.posY;
                }
                else {
                    TPAuraUtil.y = TPAuraUtil.mc.thePlayer.posY;
                    TPAuraUtil.yPreEn = entityLivingBase.posY;
                }
            }
        }
        return false;
    }
    
    public static Vec3 getVec3(final BlockPos blockPos) {
        return new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }
    
    public static TeleportResult pathFinderTeleportTo(Vec3 vec3, final Vec3 vec4) {
        final boolean sneaking = TPAuraUtil.mc.thePlayer.isSneaking();
        final ArrayList<Vec3> list = new ArrayList<Vec3>();
        final ArrayList list2 = new ArrayList();
        final BlockPos blockPos = new BlockPos(getBlockPos(vec4));
        final BlockPos blockPos2 = getBlockPos(vec3);
        if (!NodeProcessor.isPassable(NodeProcessor.getBlock(blockPos2))) {
            float n = (float)Math.toDegrees(Math.atan2(vec4.zCoord - vec3.getZ(), vec4.xCoord - vec3.getX())) + 180.0f;
            if (n < 0.0f) {
                n += 360.0f;
            }
            System.out.println(n);
            vec3 = getVec3(blockPos2.offset(EnumFacing.fromAngle(normalizeAngle(n))).add(0.5, 0.0, 0.5));
        }
        BlockPos blockPos3 = blockPos;
        if (!NodeProcessor.isPassable(NodeProcessor.getBlock(blockPos))) {
            blockPos3 = blockPos.up();
            final boolean passable;
            if (!(passable = NodeProcessor.isPassable(NodeProcessor.getBlock(blockPos.up())))) {
                blockPos3 = blockPos.up(2);
                if (!passable) {}
            }
        }
        float n2 = (float)Math.toDegrees(Math.atan2(vec4.zCoord - blockPos3.getZ(), vec4.xCoord - blockPos3.getX())) + 180.0f;
        if (n2 < 0.0f) {
            n2 += 360.0f;
        }
        final BlockPos offset = blockPos.offset(EnumFacing.fromAngle(normalizeAngle(n2)));
        final NodeProcessor nodeProcessor = new NodeProcessor();
        nodeProcessor.getPath(new BlockPos(vec3.xCoord, vec3.yCoord, vec3.zCoord), offset);
        final ArrayList triedPaths = nodeProcessor.triedPaths;
        if (nodeProcessor.path == null) {
            return new TeleportResult(list, null, triedPaths, null, null, false);
        }
        Vec3 vec5 = null;
        if (sneaking) {
            TPAuraUtil.mc.getNetHandler().getNetworkManager().sendPacket(new C0BPacketEntityAction(TPAuraUtil.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
        }
        for (final Node node : nodeProcessor.path) {
            final BlockPos blockpos = node.getBlockpos();
            PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(node.getBlockpos().getX() + 0.5, node.getBlockpos().getY(), node.getBlockpos().getZ() + 0.5, true));
            list.add(vec5 = new Vec3(blockpos.getX() + 0.5, blockpos.getY(), blockpos.getZ() + 0.5));
        }
        if (sneaking) {
            TPAuraUtil.mc.getNetHandler().getNetworkManager().sendPacket(new C0BPacketEntityAction(TPAuraUtil.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
        }
        return new TeleportResult(list, null, triedPaths, nodeProcessor.path, vec5, true);
    }
    
    public static float[] getFacePosRemote(final Vec3 vec3, final Vec3 vec4) {
        final double n = vec4.xCoord - vec3.xCoord;
        final double n2 = vec4.yCoord - vec3.yCoord;
        final double n3 = vec4.zCoord - vec3.zCoord;
        return new float[] { MathHelper.wrapAngleTo180_float((float)(Math.atan2(n3, n) * 180.0 / 3.141592653589793) - 90.0f), MathHelper.wrapAngleTo180_float((float)(-(Math.atan2(n2, MathHelper.sqrt_double(n * n + n3 * n3)) * 180.0 / 3.141592653589793))) };
    }
    
    public static void sendPacket(final boolean b, final ArrayList list, final ArrayList list2) {
        TPAuraUtil.mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(TPAuraUtil.x, TPAuraUtil.y, TPAuraUtil.z, true));
        if (b) {
            list.add(new Vec3(TPAuraUtil.x, TPAuraUtil.y, TPAuraUtil.z));
            return;
        }
        list2.add(new Vec3(TPAuraUtil.x, TPAuraUtil.y, TPAuraUtil.z));
    }
    
    private static void sendPacket(final Packet packet) {
        TPAuraUtil.mc.getNetHandler().addToSendQueue(packet);
    }
    
    public static TeleportResult pathFinderTeleportBack(final ArrayList list) {
        final boolean sneaking = TPAuraUtil.mc.thePlayer.isSneaking();
        final ArrayList<Vec3> list2 = new ArrayList<Vec3>();
        if (sneaking) {
            TPAuraUtil.mc.getNetHandler().getNetworkManager().sendPacket(new C0BPacketEntityAction(TPAuraUtil.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
        }
        for (int i = list.size() - 1; i > -1; --i) {
            PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(list.get(i).xCoord, list.get(i).yCoord, list.get(i).zCoord, true));
            list2.add(list.get(i));
        }
        if (sneaking) {
            TPAuraUtil.mc.getNetHandler().getNetworkManager().sendPacket(new C0BPacketEntityAction(TPAuraUtil.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
        }
        return new TeleportResult(list, list2, null, null, null, false);
    }
    
    public static float normalizeAngle(final float n) {
        return (n + 360.0f) % 360.0f;
    }
    
    private static void attackInf(final EntityLivingBase entityLivingBase) {
        TPAuraUtil.mc.thePlayer.swingItem();
        TPAuraUtil.mc.getNetHandler().getNetworkManager().sendPacket(new C02PacketUseEntity(entityLivingBase, C02PacketUseEntity.Action.ATTACK));
        final float func_152377_a = EnchantmentHelper.func_152377_a(TPAuraUtil.mc.thePlayer.getHeldItem(), entityLivingBase.getCreatureAttribute());
        final boolean b = TPAuraUtil.mc.thePlayer.fallDistance > 0.0f && !TPAuraUtil.mc.thePlayer.onGround && !TPAuraUtil.mc.thePlayer.isOnLadder() && !TPAuraUtil.mc.thePlayer.isInWater() && !TPAuraUtil.mc.thePlayer.isPotionActive(Potion.blindness) && TPAuraUtil.mc.thePlayer.ridingEntity == null;
        if (func_152377_a > 0.0f) {
            TPAuraUtil.mc.thePlayer.onEnchantmentCritical(entityLivingBase);
        }
    }
    
    public static MovingObjectPosition rayTracePos(final Vec3 vec3, final Vec3 vec4, final boolean b, final boolean b2, final boolean b3) {
        final float n = getFacePosRemote(vec4, vec3)[0];
        final double radians = Math.toRadians(normalizeAngle(n));
        final double radians2 = Math.toRadians(normalizeAngle(n) + 180.0f);
        final double n2 = 2.1;
        final double n3 = 2.1;
        final Vec3 vec5 = new Vec3(vec3.xCoord + Math.cos(radians) * n2, vec3.yCoord, vec3.zCoord + Math.sin(radians) * n2);
        final Vec3 vec6 = new Vec3(vec3.xCoord + Math.cos(radians2) * n2, vec3.yCoord, vec3.zCoord + Math.sin(radians2) * n2);
        final Vec3 vec7 = new Vec3(vec4.xCoord + Math.cos(radians) * n2, vec4.yCoord, vec4.zCoord + Math.sin(radians) * n2);
        final Vec3 vec8 = new Vec3(vec4.xCoord + Math.cos(radians2) * n2, vec4.yCoord, vec4.zCoord + Math.sin(radians2) * n2);
        final Vec3 vec9 = new Vec3(vec3.xCoord + Math.cos(radians) * n3, vec3.yCoord, vec3.zCoord + Math.sin(radians) * n3);
        final Vec3 vec10 = new Vec3(vec3.xCoord + Math.cos(radians2) * n3, vec3.yCoord, vec3.zCoord + Math.sin(radians2) * n3);
        final Vec3 vec11 = new Vec3(vec4.xCoord + Math.cos(radians) * n3, vec4.yCoord, vec4.zCoord + Math.sin(radians) * n3);
        final Vec3 vec12 = new Vec3(vec4.xCoord + Math.cos(radians2) * n3, vec4.yCoord, vec4.zCoord + Math.sin(radians2) * n3);
        final MovingObjectPosition rayTraceBlocks = TPAuraUtil.mc.theWorld.rayTraceBlocks(vec5, vec7, b, b2, b3);
        final MovingObjectPosition rayTraceBlocks2 = TPAuraUtil.mc.theWorld.rayTraceBlocks(vec3, vec4, b, b2, b3);
        final MovingObjectPosition rayTraceBlocks3 = TPAuraUtil.mc.theWorld.rayTraceBlocks(vec6, vec8, b, b2, b3);
        final MovingObjectPosition movingObjectPosition = null;
        final MovingObjectPosition movingObjectPosition2 = null;
        if (rayTraceBlocks2 != null || rayTraceBlocks != null || rayTraceBlocks3 != null || movingObjectPosition != null || movingObjectPosition2 != null) {
            if (b3) {
                if (movingObjectPosition2 != null && (NodeProcessor.getBlock(movingObjectPosition2.getBlockPos()).getMaterial() != Material.air || movingObjectPosition2.entityHit != null)) {
                    return movingObjectPosition2;
                }
                if (movingObjectPosition != null && (NodeProcessor.getBlock(movingObjectPosition.getBlockPos()).getMaterial() != Material.air || movingObjectPosition.entityHit != null)) {
                    return movingObjectPosition;
                }
                if (rayTraceBlocks3 != null && (NodeProcessor.getBlock(rayTraceBlocks3.getBlockPos()).getMaterial() != Material.air || rayTraceBlocks3.entityHit != null)) {
                    return rayTraceBlocks3;
                }
                if (rayTraceBlocks != null && (NodeProcessor.getBlock(rayTraceBlocks.getBlockPos()).getMaterial() != Material.air || rayTraceBlocks.entityHit != null)) {
                    return rayTraceBlocks;
                }
                if (rayTraceBlocks2 != null && (NodeProcessor.getBlock(rayTraceBlocks2.getBlockPos()).getMaterial() != Material.air || rayTraceBlocks2.entityHit != null)) {
                    return rayTraceBlocks2;
                }
            }
            else {
                if (movingObjectPosition2 != null) {
                    return movingObjectPosition2;
                }
                if (movingObjectPosition != null) {
                    return movingObjectPosition;
                }
                if (rayTraceBlocks3 != null) {
                    return rayTraceBlocks3;
                }
                if (rayTraceBlocks != null) {
                    return rayTraceBlocks;
                }
                if (rayTraceBlocks2 != null) {
                    return rayTraceBlocks2;
                }
            }
        }
        if (rayTraceBlocks2 != null) {
            return rayTraceBlocks2;
        }
        if (rayTraceBlocks3 != null) {
            return rayTraceBlocks3;
        }
        if (rayTraceBlocks != null) {
            return rayTraceBlocks;
        }
        if (movingObjectPosition2 != null) {
            return movingObjectPosition2;
        }
        if (movingObjectPosition == null) {
            return null;
        }
        return movingObjectPosition;
    }
}
