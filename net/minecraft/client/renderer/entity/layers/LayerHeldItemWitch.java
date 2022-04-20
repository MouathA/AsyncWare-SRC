package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.model.*;
import net.minecraft.client.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.item.*;

public class LayerHeldItemWitch implements LayerRenderer
{
    private final RenderWitch witchRenderer;
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((EntityWitch)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
    
    public void doRenderLayer(final EntityWitch entityWitch, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        final ItemStack heldItem = entityWitch.getHeldItem();
        if (heldItem != null) {
            GlStateManager.color(1.0f, 1.0f, 1.0f);
            if (this.witchRenderer.getMainModel().isChild) {
                GlStateManager.translate(0.0f, 0.625f, 0.0f);
                GlStateManager.rotate(-20.0f, -1.0f, 0.0f, 0.0f);
                final float n8 = 0.5f;
                GlStateManager.scale(n8, n8, n8);
            }
            ((ModelWitch)this.witchRenderer.getMainModel()).villagerNose.postRender(0.0625f);
            GlStateManager.translate(-0.0625f, 0.53125f, 0.21875f);
            final Item item = heldItem.getItem();
            final Minecraft minecraft = Minecraft.getMinecraft();
            if (item instanceof ItemBlock && minecraft.getBlockRendererDispatcher().isRenderTypeChest(Block.getBlockFromItem(item), heldItem.getMetadata())) {
                GlStateManager.translate(0.0f, 0.0625f, -0.25f);
                GlStateManager.rotate(30.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(-5.0f, 0.0f, 1.0f, 0.0f);
                final float n9 = 0.375f;
                GlStateManager.scale(n9, -n9, n9);
            }
            else if (item == Items.bow) {
                GlStateManager.translate(0.0f, 0.125f, -0.125f);
                GlStateManager.rotate(-45.0f, 0.0f, 1.0f, 0.0f);
                final float n10 = 0.625f;
                GlStateManager.scale(n10, -n10, n10);
                GlStateManager.rotate(-100.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(-20.0f, 0.0f, 1.0f, 0.0f);
            }
            else if (item.isFull3D()) {
                if (item.shouldRotateAroundWhenRendering()) {
                    GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
                    GlStateManager.translate(0.0f, -0.0625f, 0.0f);
                }
                this.witchRenderer.transformHeldFull3DItemLayer();
                GlStateManager.translate(0.0625f, -0.125f, 0.0f);
                final float n11 = 0.625f;
                GlStateManager.scale(n11, -n11, n11);
                GlStateManager.rotate(0.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(0.0f, 0.0f, 1.0f, 0.0f);
            }
            else {
                GlStateManager.translate(0.1875f, 0.1875f, 0.0f);
                final float n12 = 0.875f;
                GlStateManager.scale(n12, n12, n12);
                GlStateManager.rotate(-20.0f, 0.0f, 0.0f, 1.0f);
                GlStateManager.rotate(-60.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(-30.0f, 0.0f, 0.0f, 1.0f);
            }
            GlStateManager.rotate(-15.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(40.0f, 0.0f, 0.0f, 1.0f);
            minecraft.getItemRenderer().renderItem(entityWitch, heldItem, ItemCameraTransforms.TransformType.THIRD_PERSON);
        }
    }
    
    public LayerHeldItemWitch(final RenderWitch witchRenderer) {
        this.witchRenderer = witchRenderer;
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
