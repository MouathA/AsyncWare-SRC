package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import com.google.common.collect.*;

public abstract class LayerArmorBase implements LayerRenderer
{
    private float alpha;
    private float colorR;
    protected ModelBase field_177186_d;
    protected static final ResourceLocation ENCHANTED_ITEM_GLINT_RES;
    private static final Map ARMOR_TEXTURE_RES_MAP;
    private float colorG;
    private float colorB;
    protected ModelBase field_177189_c;
    private final RendererLivingEntity renderer;
    private boolean field_177193_i;
    
    protected abstract void func_177179_a(final ModelBase p0, final int p1);
    
    private ResourceLocation getArmorResource(final ItemArmor itemArmor, final boolean b) {
        return this.getArmorResource(itemArmor, b, null);
    }
    
    private void renderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final int n8) {
        final ItemStack currentArmor = this.getCurrentArmor(entityLivingBase, n8);
        if (currentArmor != null && currentArmor.getItem() instanceof ItemArmor) {
            final ItemArmor itemArmor = (ItemArmor)currentArmor.getItem();
            final ModelBase func_177175_a = this.func_177175_a(n8);
            func_177175_a.setModelAttributes(this.renderer.getMainModel());
            func_177175_a.setLivingAnimations(entityLivingBase, n, n2, n3);
            this.func_177179_a(func_177175_a, n8);
            final boolean slotForLeggings = this.isSlotForLeggings(n8);
            this.renderer.bindTexture(this.getArmorResource(itemArmor, slotForLeggings));
            switch (LayerArmorBase$1.$SwitchMap$net$minecraft$item$ItemArmor$ArmorMaterial[itemArmor.getArmorMaterial().ordinal()]) {
                case 1: {
                    final int color = itemArmor.getColor(currentArmor);
                    GlStateManager.color(this.colorR * ((color >> 16 & 0xFF) / 255.0f), this.colorG * ((color >> 8 & 0xFF) / 255.0f), this.colorB * ((color & 0xFF) / 255.0f), this.alpha);
                    func_177175_a.render(entityLivingBase, n, n2, n4, n5, n6, n7);
                    this.renderer.bindTexture(this.getArmorResource(itemArmor, slotForLeggings, "overlay"));
                }
                case 2:
                case 3:
                case 4:
                case 5: {
                    GlStateManager.color(this.colorR, this.colorG, this.colorB, this.alpha);
                    func_177175_a.render(entityLivingBase, n, n2, n4, n5, n6, n7);
                    break;
                }
            }
            if (!this.field_177193_i && currentArmor.isItemEnchanted()) {
                this.func_177183_a(entityLivingBase, func_177175_a, n, n2, n3, n4, n5, n6, n7);
            }
        }
    }
    
    public ItemStack getCurrentArmor(final EntityLivingBase entityLivingBase, final int n) {
        return entityLivingBase.getCurrentArmor(n - 1);
    }
    
    private ResourceLocation getArmorResource(final ItemArmor itemArmor, final boolean b, final String s) {
        final String format = String.format("textures/models/armor/%s_layer_%d%s.png", itemArmor.getArmorMaterial().getName(), b ? 2 : 1, (s == null) ? "" : String.format("_%s", s));
        ResourceLocation resourceLocation = LayerArmorBase.ARMOR_TEXTURE_RES_MAP.get(format);
        if (resourceLocation == null) {
            resourceLocation = new ResourceLocation(format);
            LayerArmorBase.ARMOR_TEXTURE_RES_MAP.put(format, resourceLocation);
        }
        return resourceLocation;
    }
    
    public LayerArmorBase(final RendererLivingEntity renderer) {
        this.alpha = 1.0f;
        this.colorR = 1.0f;
        this.colorG = 1.0f;
        this.colorB = 1.0f;
        this.renderer = renderer;
        this.initArmor();
    }
    
    protected abstract void initArmor();
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
    
    static {
        ENCHANTED_ITEM_GLINT_RES = new ResourceLocation("textures/misc/enchanted_item_glint.png");
        ARMOR_TEXTURE_RES_MAP = Maps.newHashMap();
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.renderLayer(entityLivingBase, n, n2, n3, n4, n5, n6, n7, 4);
        this.renderLayer(entityLivingBase, n, n2, n3, n4, n5, n6, n7, 3);
        this.renderLayer(entityLivingBase, n, n2, n3, n4, n5, n6, n7, 2);
        this.renderLayer(entityLivingBase, n, n2, n3, n4, n5, n6, n7, 1);
    }
    
    private void func_177183_a(final EntityLivingBase entityLivingBase, final ModelBase modelBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        final float n8 = entityLivingBase.ticksExisted + n3;
        this.renderer.bindTexture(LayerArmorBase.ENCHANTED_ITEM_GLINT_RES);
        GlStateManager.depthFunc(514);
        GlStateManager.depthMask(false);
        final float n9 = 0.5f;
        GlStateManager.color(n9, n9, n9, 1.0f);
        while (true) {
            GlStateManager.blendFunc(768, 1);
            final float n10 = 0.76f;
            GlStateManager.color(0.5f * n10, 0.25f * n10, 0.8f * n10, 1.0f);
            GlStateManager.matrixMode(5890);
            final float n11 = 0.33333334f;
            GlStateManager.scale(n11, n11, n11);
            GlStateManager.rotate(30.0f - 0 * 60.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.translate(0.0f, n8 * (0.001f + 0 * 0.003f) * 20.0f, 0.0f);
            GlStateManager.matrixMode(5888);
            modelBase.render(entityLivingBase, n, n2, n4, n5, n6, n7);
            int n12 = 0;
            ++n12;
        }
    }
    
    public ModelBase func_177175_a(final int n) {
        return (this == n) ? this.field_177189_c : this.field_177186_d;
    }
}
