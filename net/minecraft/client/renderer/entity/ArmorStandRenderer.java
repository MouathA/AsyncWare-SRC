package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.*;

public class ArmorStandRenderer extends RendererLivingEntity
{
    public static final ResourceLocation TEXTURE_ARMOR_STAND;
    
    protected ResourceLocation getEntityTexture(final EntityArmorStand entityArmorStand) {
        return ArmorStandRenderer.TEXTURE_ARMOR_STAND;
    }
    
    static {
        TEXTURE_ARMOR_STAND = new ResourceLocation("textures/entity/armorstand/wood.png");
    }
    
    @Override
    protected void rotateCorpse(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        this.rotateCorpse((EntityArmorStand)entityLivingBase, n, n2, n3);
    }
    
    protected boolean canRenderName(final EntityArmorStand entityArmorStand) {
        return entityArmorStand.getAlwaysRenderNameTag();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityArmorStand)entity);
    }
    
    public ArmorStandRenderer(final RenderManager renderManager) {
        super(renderManager, new ModelArmorStand(), 0.0f);
        this.addLayer(new LayerBipedArmor(this, this) {
            final ArmorStandRenderer this$0;
            
            @Override
            protected void initArmor() {
                this.field_177189_c = new ModelArmorStandArmor(0.5f);
                this.field_177186_d = new ModelArmorStandArmor(1.0f);
            }
        });
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerCustomHead(this.getMainModel().bipedHead));
    }
    
    @Override
    protected boolean canRenderName(final Entity entity) {
        return this.canRenderName((EntityArmorStand)entity);
    }
    
    @Override
    public ModelArmorStand getMainModel() {
        return (ModelArmorStand)super.getMainModel();
    }
    
    protected void rotateCorpse(final EntityArmorStand entityArmorStand, final float n, final float n2, final float n3) {
        GlStateManager.rotate(180.0f - n2, 0.0f, 1.0f, 0.0f);
    }
    
    @Override
    public ModelBase getMainModel() {
        return this.getMainModel();
    }
    
    protected boolean canRenderName(final EntityLivingBase entityLivingBase) {
        return this.canRenderName((EntityArmorStand)entityLivingBase);
    }
}
