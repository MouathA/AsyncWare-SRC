package net.minecraft.client.renderer;

import java.nio.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;

public class ActiveRenderInfo
{
    private static float rotationXZ;
    private static float rotationYZ;
    private static Vec3 position;
    private static float rotationXY;
    private static final IntBuffer VIEWPORT;
    private static final FloatBuffer MODELVIEW;
    private static final FloatBuffer PROJECTION;
    private static float rotationZ;
    private static final FloatBuffer OBJECTCOORDS;
    private static float rotationX;
    
    public static float getRotationXZ() {
        return ActiveRenderInfo.rotationXZ;
    }
    
    public static float getRotationZ() {
        return ActiveRenderInfo.rotationZ;
    }
    
    public static float getRotationYZ() {
        return ActiveRenderInfo.rotationYZ;
    }
    
    public static Vec3 projectViewFromEntity(final Entity entity, final double n) {
        return new Vec3(entity.prevPosX + (entity.posX - entity.prevPosX) * n + ActiveRenderInfo.position.xCoord, entity.prevPosY + (entity.posY - entity.prevPosY) * n + ActiveRenderInfo.position.yCoord, entity.prevPosZ + (entity.posZ - entity.prevPosZ) * n + ActiveRenderInfo.position.zCoord);
    }
    
    public static void updateRenderInfo(final EntityPlayer entityPlayer, final boolean b) {
        GlStateManager.getFloat(2982, ActiveRenderInfo.MODELVIEW);
        GlStateManager.getFloat(2983, ActiveRenderInfo.PROJECTION);
        GL11.glGetInteger(2978, ActiveRenderInfo.VIEWPORT);
        GLU.gluUnProject((float)((ActiveRenderInfo.VIEWPORT.get(0) + ActiveRenderInfo.VIEWPORT.get(2)) / 2), (float)((ActiveRenderInfo.VIEWPORT.get(1) + ActiveRenderInfo.VIEWPORT.get(3)) / 2), 0.0f, ActiveRenderInfo.MODELVIEW, ActiveRenderInfo.PROJECTION, ActiveRenderInfo.VIEWPORT, ActiveRenderInfo.OBJECTCOORDS);
        ActiveRenderInfo.position = new Vec3(ActiveRenderInfo.OBJECTCOORDS.get(0), ActiveRenderInfo.OBJECTCOORDS.get(1), ActiveRenderInfo.OBJECTCOORDS.get(2));
        final int n = b ? 1 : 0;
        final float rotationPitch = entityPlayer.rotationPitch;
        final float rotationYaw = entityPlayer.rotationYaw;
        ActiveRenderInfo.rotationX = MathHelper.cos(rotationYaw * 3.1415927f / 180.0f) * (1 - n * 2);
        ActiveRenderInfo.rotationZ = MathHelper.sin(rotationYaw * 3.1415927f / 180.0f) * (1 - n * 2);
        ActiveRenderInfo.rotationYZ = -ActiveRenderInfo.rotationZ * MathHelper.sin(rotationPitch * 3.1415927f / 180.0f) * (1 - n * 2);
        ActiveRenderInfo.rotationXY = ActiveRenderInfo.rotationX * MathHelper.sin(rotationPitch * 3.1415927f / 180.0f) * (1 - n * 2);
        ActiveRenderInfo.rotationXZ = MathHelper.cos(rotationPitch * 3.1415927f / 180.0f);
    }
    
    public static Vec3 getPosition() {
        return ActiveRenderInfo.position;
    }
    
    static {
        VIEWPORT = GLAllocation.createDirectIntBuffer(16);
        MODELVIEW = GLAllocation.createDirectFloatBuffer(16);
        PROJECTION = GLAllocation.createDirectFloatBuffer(16);
        OBJECTCOORDS = GLAllocation.createDirectFloatBuffer(3);
        ActiveRenderInfo.position = new Vec3(0.0, 0.0, 0.0);
    }
    
    public static float getRotationX() {
        return ActiveRenderInfo.rotationX;
    }
    
    public static Block getBlockAtEntityViewpoint(final World world, final Entity entity, final float n) {
        final Vec3 projectViewFromEntity = projectViewFromEntity(entity, n);
        final BlockPos blockPos = new BlockPos(projectViewFromEntity);
        final IBlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (block.getMaterial().isLiquid()) {
            float n2 = 0.0f;
            if (blockState.getBlock() instanceof BlockLiquid) {
                n2 = BlockLiquid.getLiquidHeightPercent((int)blockState.getValue(BlockLiquid.LEVEL)) - 0.11111111f;
            }
            if (projectViewFromEntity.yCoord >= blockPos.getY() + 1 - n2) {
                block = world.getBlockState(blockPos.up()).getBlock();
            }
        }
        return block;
    }
    
    public static float getRotationXY() {
        return ActiveRenderInfo.rotationXY;
    }
}
