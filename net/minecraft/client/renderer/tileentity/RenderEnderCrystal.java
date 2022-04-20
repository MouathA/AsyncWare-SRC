package net.minecraft.client.renderer.tileentity;

import net.minecraft.entity.item.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;

public class RenderEnderCrystal extends Render
{
    private ModelBase modelEnderCrystal;
    private static final ResourceLocation enderCrystalTextures;
    
    public void doRender(final EntityEnderCrystal entityEnderCrystal, final double n, final double n2, final double n3, final float n4, final float n5) {
        final float n6 = entityEnderCrystal.innerRotation + n5;
        GlStateManager.translate((float)n, (float)n2, (float)n3);
        this.bindTexture(RenderEnderCrystal.enderCrystalTextures);
        final float n7 = MathHelper.sin(n6 * 0.2f) / 2.0f + 0.5f;
        this.modelEnderCrystal.render(entityEnderCrystal, 0.0f, n6 * 3.0f, (n7 * n7 + n7) * 0.2f, 0.0f, 0.0f, 0.0625f);
        super.doRender(entityEnderCrystal, n, n2, n3, n4, n5);
    }
    
    protected ResourceLocation getEntityTexture(final EntityEnderCrystal entityEnderCrystal) {
        return RenderEnderCrystal.enderCrystalTextures;
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityEnderCrystal)entity, n, n2, n3, n4, n5);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityEnderCrystal)entity);
    }
    
    public RenderEnderCrystal(final RenderManager renderManager) {
        super(renderManager);
        this.modelEnderCrystal = new ModelEnderCrystal(0.0f, true);
        this.shadowSize = 0.5f;
    }
    
    static {
        enderCrystalTextures = new ResourceLocation("textures/entity/endercrystal/endercrystal.png");
    }
}
