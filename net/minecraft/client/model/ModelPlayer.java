package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;

public class ModelPlayer extends ModelBiped
{
    private boolean smallArms;
    private ModelRenderer bipedDeadmau5Head;
    public ModelRenderer bipedRightLegwear;
    public ModelRenderer bipedLeftArmwear;
    public ModelRenderer bipedBodyWear;
    public ModelRenderer bipedLeftLegwear;
    public ModelRenderer bipedRightArmwear;
    private ModelRenderer bipedCape;
    private static final String __OBFID = "CL_00002626";
    
    public void renderDeadmau5Head(final float n) {
        ModelBase.copyModelAngles(this.bipedHead, this.bipedDeadmau5Head);
        this.bipedDeadmau5Head.rotationPointX = 0.0f;
        this.bipedDeadmau5Head.rotationPointY = 0.0f;
        this.bipedDeadmau5Head.render(n);
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        super.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        ModelBase.copyModelAngles(this.bipedLeftLeg, this.bipedLeftLegwear);
        ModelBase.copyModelAngles(this.bipedRightLeg, this.bipedRightLegwear);
        ModelBase.copyModelAngles(this.bipedLeftArm, this.bipedLeftArmwear);
        ModelBase.copyModelAngles(this.bipedRightArm, this.bipedRightArmwear);
        ModelBase.copyModelAngles(this.bipedBody, this.bipedBodyWear);
    }
    
    public void renderLeftArm() {
        this.bipedLeftArm.render(0.0625f);
        this.bipedLeftArmwear.render(0.0625f);
    }
    
    @Override
    public void setInvisible(final boolean b) {
        super.setInvisible(b);
        this.bipedLeftArmwear.showModel = b;
        this.bipedRightArmwear.showModel = b;
        this.bipedLeftLegwear.showModel = b;
        this.bipedRightLegwear.showModel = b;
        this.bipedBodyWear.showModel = b;
        this.bipedCape.showModel = b;
        this.bipedDeadmau5Head.showModel = b;
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        super.render(entity, n, n2, n3, n4, n5, n6);
        if (this.isChild) {
            final float n7 = 2.0f;
            GlStateManager.scale(1.0f / n7, 1.0f / n7, 1.0f / n7);
            GlStateManager.translate(0.0f, 24.0f * n6, 0.0f);
            this.bipedLeftLegwear.render(n6);
            this.bipedRightLegwear.render(n6);
            this.bipedLeftArmwear.render(n6);
            this.bipedRightArmwear.render(n6);
            this.bipedBodyWear.render(n6);
        }
        else {
            if (entity.isSneaking()) {
                GlStateManager.translate(0.0f, 0.2f, 0.0f);
            }
            this.bipedLeftLegwear.render(n6);
            this.bipedRightLegwear.render(n6);
            this.bipedLeftArmwear.render(n6);
            this.bipedRightArmwear.render(n6);
            this.bipedBodyWear.render(n6);
        }
    }
    
    public ModelPlayer(final float n, final boolean smallArms) {
        super(n, 0.0f, 64, 64);
        this.smallArms = smallArms;
        (this.bipedDeadmau5Head = new ModelRenderer(this, 24, 0)).addBox(-3.0f, -6.0f, -1.0f, 6, 6, 1, n);
        (this.bipedCape = new ModelRenderer(this, 0, 0)).setTextureSize(64, 32);
        this.bipedCape.addBox(-5.0f, 0.0f, -1.0f, 10, 16, 1, n);
        if (smallArms) {
            (this.bipedLeftArm = new ModelRenderer(this, 32, 48)).addBox(-1.0f, -2.0f, -2.0f, 3, 12, 4, n);
            this.bipedLeftArm.setRotationPoint(5.0f, 2.5f, 0.0f);
            (this.bipedRightArm = new ModelRenderer(this, 40, 16)).addBox(-2.0f, -2.0f, -2.0f, 3, 12, 4, n);
            this.bipedRightArm.setRotationPoint(-5.0f, 2.5f, 0.0f);
            (this.bipedLeftArmwear = new ModelRenderer(this, 48, 48)).addBox(-1.0f, -2.0f, -2.0f, 3, 12, 4, n + 0.25f);
            this.bipedLeftArmwear.setRotationPoint(5.0f, 2.5f, 0.0f);
            (this.bipedRightArmwear = new ModelRenderer(this, 40, 32)).addBox(-2.0f, -2.0f, -2.0f, 3, 12, 4, n + 0.25f);
            this.bipedRightArmwear.setRotationPoint(-5.0f, 2.5f, 10.0f);
        }
        else {
            (this.bipedLeftArm = new ModelRenderer(this, 32, 48)).addBox(-1.0f, -2.0f, -2.0f, 4, 12, 4, n);
            this.bipedLeftArm.setRotationPoint(5.0f, 2.0f, 0.0f);
            (this.bipedLeftArmwear = new ModelRenderer(this, 48, 48)).addBox(-1.0f, -2.0f, -2.0f, 4, 12, 4, n + 0.25f);
            this.bipedLeftArmwear.setRotationPoint(5.0f, 2.0f, 0.0f);
            (this.bipedRightArmwear = new ModelRenderer(this, 40, 32)).addBox(-3.0f, -2.0f, -2.0f, 4, 12, 4, n + 0.25f);
            this.bipedRightArmwear.setRotationPoint(-5.0f, 2.0f, 10.0f);
        }
        (this.bipedLeftLeg = new ModelRenderer(this, 16, 48)).addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, n);
        this.bipedLeftLeg.setRotationPoint(1.9f, 12.0f, 0.0f);
        (this.bipedLeftLegwear = new ModelRenderer(this, 0, 48)).addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, n + 0.25f);
        this.bipedLeftLegwear.setRotationPoint(1.9f, 12.0f, 0.0f);
        (this.bipedRightLegwear = new ModelRenderer(this, 0, 32)).addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, n + 0.25f);
        this.bipedRightLegwear.setRotationPoint(-1.9f, 12.0f, 0.0f);
        (this.bipedBodyWear = new ModelRenderer(this, 16, 32)).addBox(-4.0f, 0.0f, -2.0f, 8, 12, 4, n + 0.25f);
        this.bipedBodyWear.setRotationPoint(0.0f, 0.0f, 0.0f);
    }
    
    public void renderRightArm() {
        this.bipedRightArm.render(0.0625f);
        this.bipedRightArmwear.render(0.0625f);
    }
    
    @Override
    public void postRenderArm(final float n) {
        if (this.smallArms) {
            final ModelRenderer bipedRightArm = this.bipedRightArm;
            ++bipedRightArm.rotationPointX;
            this.bipedRightArm.postRender(n);
            final ModelRenderer bipedRightArm2 = this.bipedRightArm;
            --bipedRightArm2.rotationPointX;
        }
        else {
            this.bipedRightArm.postRender(n);
        }
    }
    
    public void renderCape(final float n) {
        this.bipedCape.render(n);
    }
}
