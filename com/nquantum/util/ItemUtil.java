package com.nquantum.util;

import java.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.*;
import net.minecraft.item.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import org.lwjgl.opengl.*;

public class ItemUtil
{
    public static Random random;
    public static double rotation;
    public static long tick;
    public static final ResourceLocation RES_ITEM_GLINT;
    public static RenderItem renderItem;
    public static Minecraft mc;
    
    public static byte getMiniBlockCount(final ItemStack itemStack, final byte b) {
        return b;
    }
    
    public static int func_177078_a(final ItemStack itemStack) {
        if (itemStack.animationsToGo <= 48) {
            if (itemStack.animationsToGo <= 32) {
                if (itemStack.animationsToGo <= 16) {
                    if (itemStack.animationsToGo > 1) {}
                }
            }
        }
        return 2;
    }
    
    static {
        ItemUtil.random = new Random();
        ItemUtil.mc = Minecraft.getMinecraft();
        ItemUtil.renderItem = ItemUtil.mc.getRenderItem();
        RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    }
    
    public static int func_177077_a(final EntityItem entityItem, final double n, final double n2, final double n3, final float n4, final IBakedModel bakedModel) {
        final ItemStack entityItem2 = entityItem.getEntityItem();
        if (entityItem2.getItem() == null) {
            return 0;
        }
        final boolean ambientOcclusion = bakedModel.isAmbientOcclusion();
        final int func_177078_a = func_177078_a(entityItem2);
        GlStateManager.translate((float)n, (float)n2 + 0.0f + 0.25f, (float)n3);
        final float n5 = 0.0f;
        if (ambientOcclusion || (ItemUtil.mc.getRenderManager().renderEngine != null && ItemUtil.mc.gameSettings.fancyGraphics)) {
            GlStateManager.rotate(n5, 0.0f, 1.0f, 0.0f);
        }
        if (!ambientOcclusion) {
            GlStateManager.translate(-0.0f * (func_177078_a - 1) * 0.5f, -0.0f * (func_177078_a - 1) * 0.5f, -0.046875f * (func_177078_a - 1) * 0.5f);
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        return func_177078_a;
    }
    
    public static void doRenderItemPhysic(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        ItemUtil.rotation = (System.nanoTime() - ItemUtil.tick) / 3000000.0 * 1.0;
        if (!ItemUtil.mc.inGameHasFocus) {
            ItemUtil.rotation = 0.0;
        }
        final EntityItem entityItem2;
        final ItemStack entityItem;
        if ((entityItem = (entityItem2 = (EntityItem)entity).getEntityItem()).getItem() != null) {
            ItemUtil.random.setSeed(187L);
            if (TextureMap.locationBlocksTexture != null) {
                ItemUtil.mc.getRenderManager().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
                ItemUtil.mc.getRenderManager().renderEngine.getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
            }
            GlStateManager.alphaFunc(516, 0.1f);
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            final IBakedModel itemModel = ItemUtil.renderItem.getItemModelMesher().getItemModel(entityItem);
            final int func_177077_a = func_177077_a(entityItem2, n, n2, n3, n5, itemModel);
            final BlockPos blockPos = new BlockPos(entityItem2);
            if (entityItem2.rotationPitch > 360.0f) {
                entityItem2.rotationPitch = 0.0f;
            }
            if (entityItem2 != null && !Double.isNaN(entityItem2.getAge()) && !Double.isNaN(entityItem2.getAir()) && !Double.isNaN(entityItem2.getEntityId()) && entityItem2.getPosition() != null) {
                if (entityItem2.onGround) {
                    if (entityItem2.rotationPitch != 0.0f && entityItem2.rotationPitch != 90.0f && entityItem2.rotationPitch != 180.0f && entityItem2.rotationPitch != 270.0f) {
                        final double formPositiv = formPositiv(entityItem2.rotationPitch);
                        final double formPositiv2 = formPositiv(entityItem2.rotationPitch - 90.0f);
                        final double formPositiv3 = formPositiv(entityItem2.rotationPitch - 180.0f);
                        final double formPositiv4 = formPositiv(entityItem2.rotationPitch - 270.0f);
                        if (formPositiv <= formPositiv2 && formPositiv <= formPositiv3 && formPositiv <= formPositiv4) {
                            if (entityItem2.rotationPitch < 0.0f) {
                                final EntityItem entityItem3 = entityItem2;
                                entityItem3.rotationPitch += (float)ItemUtil.rotation;
                            }
                            else {
                                final EntityItem entityItem4 = entityItem2;
                                entityItem4.rotationPitch -= (float)ItemUtil.rotation;
                            }
                        }
                        if (formPositiv2 < formPositiv && formPositiv2 <= formPositiv3 && formPositiv2 <= formPositiv4) {
                            if (entityItem2.rotationPitch - 90.0f < 0.0f) {
                                final EntityItem entityItem5 = entityItem2;
                                entityItem5.rotationPitch += (float)ItemUtil.rotation;
                            }
                            else {
                                final EntityItem entityItem6 = entityItem2;
                                entityItem6.rotationPitch -= (float)ItemUtil.rotation;
                            }
                        }
                        if (formPositiv3 < formPositiv2 && formPositiv3 < formPositiv && formPositiv3 <= formPositiv4) {
                            if (entityItem2.rotationPitch - 180.0f < 0.0f) {
                                final EntityItem entityItem7 = entityItem2;
                                entityItem7.rotationPitch += (float)ItemUtil.rotation;
                            }
                            else {
                                final EntityItem entityItem8 = entityItem2;
                                entityItem8.rotationPitch -= (float)ItemUtil.rotation;
                            }
                        }
                        if (formPositiv4 < formPositiv2 && formPositiv4 < formPositiv3 && formPositiv4 < formPositiv) {
                            if (entityItem2.rotationPitch - 270.0f < 0.0f) {
                                final EntityItem entityItem9 = entityItem2;
                                entityItem9.rotationPitch += (float)ItemUtil.rotation;
                            }
                            else {
                                final EntityItem entityItem10 = entityItem2;
                                entityItem10.rotationPitch -= (float)ItemUtil.rotation;
                            }
                        }
                    }
                }
                else {
                    final BlockPos blockPos2 = new BlockPos(entityItem2);
                    blockPos2.add(0, 1, 0);
                    if (entityItem2.isInsideOfMaterial(Material.water) | entityItem2.worldObj.getBlockState(blockPos2).getBlock().getMaterial() == Material.water | entityItem2.worldObj.getBlockState(blockPos).getBlock().getMaterial() == Material.water | entityItem2.inWater) {
                        final EntityItem entityItem11 = entityItem2;
                        entityItem11.rotationPitch += (float)(ItemUtil.rotation / 4.0);
                    }
                    else {
                        final EntityItem entityItem12 = entityItem2;
                        entityItem12.rotationPitch += (float)(ItemUtil.rotation * 2.0);
                    }
                }
            }
            GL11.glRotatef(entityItem2.rotationYaw, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(entityItem2.rotationPitch + 90.0f, 1.0f, 0.0f, 0.0f);
            while (0 < func_177077_a) {
                if (itemModel.isAmbientOcclusion()) {
                    GlStateManager.scale(0.5f, 0.5f, 0.5f);
                    ItemUtil.renderItem.renderItem(entityItem, itemModel);
                }
                else {
                    ItemUtil.renderItem.renderItem(entityItem, itemModel);
                    if (!shouldSpreadItems()) {
                        GlStateManager.translate(0.0f, 0.0f, 0.046875f);
                    }
                }
                int n6 = 0;
                ++n6;
            }
            ItemUtil.mc.getRenderManager().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
            ItemUtil.mc.getRenderManager().renderEngine.getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
        }
    }
    
    public static double formPositiv(final float n) {
        if (n > 0.0f) {
            return n;
        }
        return -n;
    }
    
    public static byte getMiniItemCount(final ItemStack itemStack, final byte b) {
        return b;
    }
    
    public static boolean shouldSpreadItems() {
        return true;
    }
}
