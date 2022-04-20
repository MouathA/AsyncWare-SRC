package net.minecraft.client.renderer.entity;

import java.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import com.nquantum.*;
import com.nquantum.util.*;

public class RenderEntityItem extends Render
{
    private final RenderItem itemRenderer;
    private Random field_177079_e;
    
    protected ResourceLocation getEntityTexture(final EntityItem entityItem) {
        return TextureMap.locationBlocksTexture;
    }
    
    private int func_177077_a(final EntityItem entityItem, final double n, final double n2, final double n3, final float n4, final IBakedModel bakedModel) {
        final ItemStack entityItem2 = entityItem.getEntityItem();
        if (entityItem2.getItem() == null) {
            return 0;
        }
        final boolean gui3d = bakedModel.isGui3d();
        final int func_177078_a = this.func_177078_a(entityItem2);
        GlStateManager.translate((float)n, (float)n2 + (MathHelper.sin((entityItem.getAge() + n4) / 10.0f + entityItem.hoverStart) * 0.1f + 0.1f) + 0.25f * bakedModel.getItemCameraTransforms().getTransform(ItemCameraTransforms.TransformType.GROUND).scale.y, (float)n3);
        if (gui3d || this.renderManager.options != null) {
            GlStateManager.rotate(((entityItem.getAge() + n4) / 20.0f + entityItem.hoverStart) * 57.295776f, 0.0f, 1.0f, 0.0f);
        }
        if (!gui3d) {
            GlStateManager.translate(-0.0f * (func_177078_a - 1) * 0.5f, -0.0f * (func_177078_a - 1) * 0.5f, -0.046875f * (func_177078_a - 1) * 0.5f);
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        return func_177078_a;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityItem)entity);
    }
    
    public RenderEntityItem(final RenderManager renderManager, final RenderItem itemRenderer) {
        super(renderManager);
        this.field_177079_e = new Random();
        this.itemRenderer = itemRenderer;
        this.shadowSize = 0.15f;
        this.shadowOpaque = 0.75f;
    }
    
    private int func_177078_a(final ItemStack itemStack) {
        if (itemStack.stackSize <= 48) {
            if (itemStack.stackSize <= 32) {
                if (itemStack.stackSize <= 16) {
                    if (itemStack.stackSize > 1) {}
                }
            }
        }
        return 2;
    }
    
    public void doRender(final EntityItem entityItem, final double n, final double n2, final double n3, final float n4, final float n5) {
        if (!Asyncware.instance.moduleManager.getModuleByName("ItemPhysics").isToggled()) {
            final ItemStack entityItem2 = entityItem.getEntityItem();
            this.field_177079_e.setSeed(187L);
            if (this.bindEntityTexture(entityItem)) {
                this.renderManager.renderEngine.getTexture(this.getEntityTexture(entityItem)).setBlurMipmap(false, false);
            }
            GlStateManager.alphaFunc(516, 0.1f);
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            final IBakedModel itemModel = this.itemRenderer.getItemModelMesher().getItemModel(entityItem2);
            while (0 < this.func_177077_a(entityItem, n, n2, n3, n5, itemModel)) {
                if (itemModel.isGui3d()) {
                    GlStateManager.scale(0.5f, 0.5f, 0.5f);
                    itemModel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GROUND);
                    this.itemRenderer.renderItem(entityItem2, itemModel);
                }
                else {
                    itemModel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GROUND);
                    this.itemRenderer.renderItem(entityItem2, itemModel);
                    GlStateManager.translate(0.0f * itemModel.getItemCameraTransforms().ground.scale.x, 0.0f * itemModel.getItemCameraTransforms().ground.scale.y, 0.046875f * itemModel.getItemCameraTransforms().ground.scale.z);
                }
                int n6 = 0;
                ++n6;
            }
            this.bindEntityTexture(entityItem);
            this.renderManager.renderEngine.getTexture(this.getEntityTexture(entityItem)).restoreLastBlurMipmap();
            super.doRender(entityItem, n, n2, n3, n4, n5);
        }
        else {
            ItemUtil.doRenderItemPhysic(entityItem, n, n2, n3, n4, n5);
        }
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityItem)entity, n, n2, n3, n4, n5);
    }
}
