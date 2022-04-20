package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.entity.boss.*;
import net.minecraft.client.renderer.*;

public class ModelDragon extends ModelBase
{
    private ModelRenderer frontLeg;
    private ModelRenderer rearFoot;
    private ModelRenderer rearLeg;
    private ModelRenderer head;
    private ModelRenderer rearLegTip;
    private ModelRenderer wing;
    private ModelRenderer frontLegTip;
    private float partialTicks;
    private ModelRenderer jaw;
    private ModelRenderer body;
    private ModelRenderer wingTip;
    private ModelRenderer frontFoot;
    private ModelRenderer spine;
    
    @Override
    public void setLivingAnimations(final EntityLivingBase entityLivingBase, final float n, final float n2, final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    private float updateRotations(double n) {
        while (n >= 180.0) {
            n -= 360.0;
        }
        while (n < -180.0) {
            n += 360.0;
        }
        return (float)n;
    }
    
    public ModelDragon(final float n) {
        this.textureWidth = 256;
        this.textureHeight = 256;
        this.setTextureOffset("body.body", 0, 0);
        this.setTextureOffset("wing.skin", -56, 88);
        this.setTextureOffset("wingtip.skin", -56, 144);
        this.setTextureOffset("rearleg.main", 0, 0);
        this.setTextureOffset("rearfoot.main", 112, 0);
        this.setTextureOffset("rearlegtip.main", 196, 0);
        this.setTextureOffset("head.upperhead", 112, 30);
        this.setTextureOffset("wing.bone", 112, 88);
        this.setTextureOffset("head.upperlip", 176, 44);
        this.setTextureOffset("jaw.jaw", 176, 65);
        this.setTextureOffset("frontleg.main", 112, 104);
        this.setTextureOffset("wingtip.bone", 112, 136);
        this.setTextureOffset("frontfoot.main", 144, 104);
        this.setTextureOffset("neck.box", 192, 104);
        this.setTextureOffset("frontlegtip.main", 226, 138);
        this.setTextureOffset("body.scale", 220, 53);
        this.setTextureOffset("head.scale", 0, 0);
        this.setTextureOffset("neck.scale", 48, 0);
        this.setTextureOffset("head.nostril", 112, 0);
        final float n2 = -16.0f;
        (this.head = new ModelRenderer(this, "head")).addBox("upperlip", -6.0f, -1.0f, -8.0f + n2, 12, 5, 16);
        this.head.addBox("upperhead", -8.0f, -8.0f, 6.0f + n2, 16, 16, 16);
        this.head.mirror = true;
        this.head.addBox("scale", -5.0f, -12.0f, 12.0f + n2, 2, 4, 6);
        this.head.addBox("nostril", -5.0f, -3.0f, -6.0f + n2, 2, 2, 4);
        this.head.mirror = false;
        this.head.addBox("scale", 3.0f, -12.0f, 12.0f + n2, 2, 4, 6);
        this.head.addBox("nostril", 3.0f, -3.0f, -6.0f + n2, 2, 2, 4);
        (this.jaw = new ModelRenderer(this, "jaw")).setRotationPoint(0.0f, 4.0f, 8.0f + n2);
        this.jaw.addBox("jaw", -6.0f, 0.0f, -16.0f, 12, 4, 16);
        this.head.addChild(this.jaw);
        (this.spine = new ModelRenderer(this, "neck")).addBox("box", -5.0f, -5.0f, -5.0f, 10, 10, 10);
        this.spine.addBox("scale", -1.0f, -9.0f, -3.0f, 2, 4, 6);
        (this.body = new ModelRenderer(this, "body")).setRotationPoint(0.0f, 4.0f, 8.0f);
        this.body.addBox("body", -12.0f, 0.0f, -16.0f, 24, 24, 64);
        this.body.addBox("scale", -1.0f, -6.0f, -10.0f, 2, 6, 12);
        this.body.addBox("scale", -1.0f, -6.0f, 10.0f, 2, 6, 12);
        this.body.addBox("scale", -1.0f, -6.0f, 30.0f, 2, 6, 12);
        (this.wing = new ModelRenderer(this, "wing")).setRotationPoint(-12.0f, 5.0f, 2.0f);
        this.wing.addBox("bone", -56.0f, -4.0f, -4.0f, 56, 8, 8);
        this.wing.addBox("skin", -56.0f, 0.0f, 2.0f, 56, 0, 56);
        (this.wingTip = new ModelRenderer(this, "wingtip")).setRotationPoint(-56.0f, 0.0f, 0.0f);
        this.wingTip.addBox("bone", -56.0f, -2.0f, -2.0f, 56, 4, 4);
        this.wingTip.addBox("skin", -56.0f, 0.0f, 2.0f, 56, 0, 56);
        this.wing.addChild(this.wingTip);
        (this.frontLeg = new ModelRenderer(this, "frontleg")).setRotationPoint(-12.0f, 20.0f, 2.0f);
        this.frontLeg.addBox("main", -4.0f, -4.0f, -4.0f, 8, 24, 8);
        (this.frontLegTip = new ModelRenderer(this, "frontlegtip")).setRotationPoint(0.0f, 20.0f, -1.0f);
        this.frontLegTip.addBox("main", -3.0f, -1.0f, -3.0f, 6, 24, 6);
        this.frontLeg.addChild(this.frontLegTip);
        (this.frontFoot = new ModelRenderer(this, "frontfoot")).setRotationPoint(0.0f, 23.0f, 0.0f);
        this.frontFoot.addBox("main", -4.0f, 0.0f, -12.0f, 8, 4, 16);
        this.frontLegTip.addChild(this.frontFoot);
        (this.rearLeg = new ModelRenderer(this, "rearleg")).setRotationPoint(-16.0f, 16.0f, 42.0f);
        this.rearLeg.addBox("main", -8.0f, -4.0f, -8.0f, 16, 32, 16);
        (this.rearLegTip = new ModelRenderer(this, "rearlegtip")).setRotationPoint(0.0f, 32.0f, -4.0f);
        this.rearLegTip.addBox("main", -6.0f, -2.0f, 0.0f, 12, 32, 12);
        this.rearLeg.addChild(this.rearLegTip);
        (this.rearFoot = new ModelRenderer(this, "rearfoot")).setRotationPoint(0.0f, 31.0f, 4.0f);
        this.rearFoot.addBox("main", -9.0f, 0.0f, -20.0f, 18, 6, 24);
        this.rearLegTip.addChild(this.rearFoot);
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        final EntityDragon entityDragon = (EntityDragon)entity;
        final float n7 = entityDragon.prevAnimTime + (entityDragon.animTime - entityDragon.prevAnimTime) * this.partialTicks;
        this.jaw.rotateAngleX = (float)(Math.sin(n7 * 3.1415927f * 2.0f) + 1.0) * 0.2f;
        final float n8 = (float)(Math.sin(n7 * 3.1415927f * 2.0f - 1.0f) + 1.0);
        final float n9 = (n8 * n8 * 1.0f + n8 * 2.0f) * 0.05f;
        GlStateManager.translate(0.0f, n9 - 2.0f, -3.0f);
        GlStateManager.rotate(n9 * 2.0f, 1.0f, 0.0f, 0.0f);
        float rotationPointX = 0.0f;
        final float n10 = 1.5f;
        final double[] movementOffsets = entityDragon.getMovementOffsets(6, this.partialTicks);
        final float updateRotations = this.updateRotations(entityDragon.getMovementOffsets(5, this.partialTicks)[0] + this.updateRotations(entityDragon.getMovementOffsets(5, this.partialTicks)[0] - entityDragon.getMovementOffsets(10, this.partialTicks)[0]) / 2.0f);
        final float n11 = n7 * 3.1415927f * 2.0f;
        float rotationPointY = 20.0f;
        float rotationPointZ = -12.0f;
        while (true) {
            final double[] movementOffsets2 = entityDragon.getMovementOffsets(5, this.partialTicks);
            final float n12 = (float)Math.cos(0 * 0.45f + n11) * 0.15f;
            this.spine.rotateAngleY = this.updateRotations(movementOffsets2[0] - movementOffsets[0]) * 3.1415927f / 180.0f * n10;
            this.spine.rotateAngleX = n12 + (float)(movementOffsets2[1] - movementOffsets[1]) * 3.1415927f / 180.0f * n10 * 5.0f;
            this.spine.rotateAngleZ = -this.updateRotations(movementOffsets2[0] - updateRotations) * 3.1415927f / 180.0f * n10;
            this.spine.rotationPointY = rotationPointY;
            this.spine.rotationPointZ = rotationPointZ;
            this.spine.rotationPointX = rotationPointX;
            rotationPointY += (float)(Math.sin(this.spine.rotateAngleX) * 10.0);
            rotationPointZ -= (float)(Math.cos(this.spine.rotateAngleY) * Math.cos(this.spine.rotateAngleX) * 10.0);
            rotationPointX -= (float)(Math.sin(this.spine.rotateAngleY) * Math.cos(this.spine.rotateAngleX) * 10.0);
            this.spine.render(n6);
            int n13 = 0;
            ++n13;
        }
    }
}
