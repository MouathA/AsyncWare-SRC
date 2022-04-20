package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class RenderBiped extends RenderLiving
{
    protected float field_77070_b;
    private static final ResourceLocation DEFAULT_RES_LOC;
    protected ModelBiped modelBipedMain;
    
    public RenderBiped(final RenderManager renderManager, final ModelBiped modelBipedMain, final float n, final float field_77070_b) {
        super(renderManager, modelBipedMain, n);
        this.modelBipedMain = modelBipedMain;
        this.field_77070_b = field_77070_b;
        this.addLayer(new LayerCustomHead(modelBipedMain.bipedHead));
    }
    
    public RenderBiped(final RenderManager renderManager, final ModelBiped modelBiped, final float n) {
        this(renderManager, modelBiped, n, 1.0f);
        this.addLayer(new LayerHeldItem(this));
    }
    
    static {
        DEFAULT_RES_LOC = new ResourceLocation("textures/entity/steve.png");
    }
    
    @Override
    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.0f, 0.1875f, 0.0f);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityLiving)entity);
    }
    
    protected ResourceLocation getEntityTexture(final EntityLiving entityLiving) {
        return RenderBiped.DEFAULT_RES_LOC;
    }
}
