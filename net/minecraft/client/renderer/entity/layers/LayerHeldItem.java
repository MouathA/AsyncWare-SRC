package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.client.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.block.model.*;

public class LayerHeldItem implements LayerRenderer
{
    private final RendererLivingEntity livingEntityRenderer;
    
    public LayerHeldItem(final RendererLivingEntity livingEntityRenderer) {
        this.livingEntityRenderer = livingEntityRenderer;
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        ItemStack heldItem = entityLivingBase.getHeldItem();
        if (heldItem != null) {
            if (this.livingEntityRenderer.getMainModel().isChild) {
                final float n8 = 0.5f;
                GlStateManager.translate(0.0f, 0.625f, 0.0f);
                GlStateManager.rotate(-20.0f, -1.0f, 0.0f, 0.0f);
                GlStateManager.scale(n8, n8, n8);
            }
            ((ModelBiped)this.livingEntityRenderer.getMainModel()).postRenderArm(0.0625f);
            GlStateManager.translate(-0.0625f, 0.4375f, 0.0625f);
            if (entityLivingBase instanceof EntityPlayer && ((EntityPlayer)entityLivingBase).fishEntity != null) {
                heldItem = new ItemStack(Items.fishing_rod, 0);
            }
            final Item item = heldItem.getItem();
            final Minecraft minecraft = Minecraft.getMinecraft();
            if (item instanceof ItemBlock && Block.getBlockFromItem(item).getRenderType() == 2) {
                GlStateManager.translate(0.0f, 0.1875f, -0.3125f);
                GlStateManager.rotate(20.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
                final float n9 = 0.375f;
                GlStateManager.scale(-n9, -n9, n9);
            }
            if (entityLivingBase.isSneaking()) {
                GlStateManager.translate(0.0f, 0.203125f, 0.0f);
            }
            minecraft.getItemRenderer().renderItem(entityLivingBase, heldItem, ItemCameraTransforms.TransformType.THIRD_PERSON);
        }
    }
}
