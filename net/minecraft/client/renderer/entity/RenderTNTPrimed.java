package net.minecraft.client.renderer.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.*;

public class RenderTNTPrimed extends Render
{
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityTNTPrimed)entity, n, n2, n3, n4, n5);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityTNTPrimed)entity);
    }
    
    protected ResourceLocation getEntityTexture(final EntityTNTPrimed entityTNTPrimed) {
        return TextureMap.locationBlocksTexture;
    }
    
    public RenderTNTPrimed(final RenderManager renderManager) {
        super(renderManager);
        this.shadowSize = 0.5f;
    }
    
    public void doRender(final EntityTNTPrimed entityTNTPrimed, final double n, final double n2, final double n3, final float n4, final float n5) {
        final BlockRendererDispatcher blockRendererDispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        GlStateManager.translate((float)n, (float)n2 + 0.5f, (float)n3);
        if (entityTNTPrimed.fuse - n5 + 1.0f < 10.0f) {
            final float clamp_float = MathHelper.clamp_float(1.0f - (entityTNTPrimed.fuse - n5 + 1.0f) / 10.0f, 0.0f, 1.0f);
            final float n6 = clamp_float * clamp_float;
            final float n7 = 1.0f + n6 * n6 * 0.3f;
            GlStateManager.scale(n7, n7, n7);
        }
        final float n8 = (1.0f - (entityTNTPrimed.fuse - n5 + 1.0f) / 100.0f) * 0.8f;
        this.bindEntityTexture(entityTNTPrimed);
        GlStateManager.translate(-0.5f, -0.5f, 0.5f);
        blockRendererDispatcher.renderBlockBrightness(Blocks.tnt.getDefaultState(), entityTNTPrimed.getBrightness(n5));
        GlStateManager.translate(0.0f, 0.0f, 1.0f);
        if (entityTNTPrimed.fuse / 5 % 2 == 0) {
            GlStateManager.blendFunc(770, 772);
            GlStateManager.color(1.0f, 1.0f, 1.0f, n8);
            GlStateManager.doPolygonOffset(-3.0f, -3.0f);
            blockRendererDispatcher.renderBlockBrightness(Blocks.tnt.getDefaultState(), 1.0f);
            GlStateManager.doPolygonOffset(0.0f, 0.0f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        }
        super.doRender(entityTNTPrimed, n, n2, n3, n4, n5);
    }
}
