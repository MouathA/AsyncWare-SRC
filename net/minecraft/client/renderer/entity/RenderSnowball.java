package net.minecraft.client.renderer.entity;

import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class RenderSnowball extends Render
{
    protected final Item field_177084_a;
    private final RenderItem field_177083_e;
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        GlStateManager.translate((float)n, (float)n2, (float)n3);
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        GlStateManager.rotate(-this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        this.bindTexture(TextureMap.locationBlocksTexture);
        this.field_177083_e.func_181564_a(this.func_177082_d(entity), ItemCameraTransforms.TransformType.GROUND);
        super.doRender(entity, n, n2, n3, n4, n5);
    }
    
    public RenderSnowball(final RenderManager renderManager, final Item field_177084_a, final RenderItem field_177083_e) {
        super(renderManager);
        this.field_177084_a = field_177084_a;
        this.field_177083_e = field_177083_e;
    }
    
    public ItemStack func_177082_d(final Entity entity) {
        return new ItemStack(this.field_177084_a, 1, 0);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return TextureMap.locationBlocksTexture;
    }
}
