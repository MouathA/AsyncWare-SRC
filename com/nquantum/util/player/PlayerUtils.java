package com.nquantum.util.player;

import net.minecraft.client.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.*;

public class PlayerUtils
{
    public static boolean isInsideBlock() {
        for (int i = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minX); i < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxX) + 1; ++i) {
            for (int j = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minY); j < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxY) + 1; ++j) {
                for (int k = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minZ); k < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxZ) + 1; ++k) {
                    final Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(i, j, k)).getBlock();
                    if (block != null && !(block instanceof BlockAir)) {
                        AxisAlignedBB collisionBoundingBox = block.getCollisionBoundingBox(Minecraft.getMinecraft().theWorld, new BlockPos(i, j, k), Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(i, j, k)));
                        if (block instanceof BlockHopper) {
                            collisionBoundingBox = new AxisAlignedBB(i, j, k, i + 1, j + 1, k + 1);
                        }
                        if (collisionBoundingBox != null && Minecraft.getMinecraft().thePlayer.boundingBox.intersectsWith(collisionBoundingBox)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean isInLiquid() {
        for (int i = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minY); i < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxX) + 1; ++i) {
            for (int j = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minZ); j < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxZ) + 1; ++j) {
                final Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(i, (int)Minecraft.getMinecraft().thePlayer.boundingBox.minY, j)).getBlock();
                if (block != null && !(block instanceof BlockAir)) {
                    return block instanceof BlockLiquid;
                }
            }
        }
        return false;
    }
}
