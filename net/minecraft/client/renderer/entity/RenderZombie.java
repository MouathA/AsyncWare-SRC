package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;
import com.google.common.collect.*;
import net.minecraft.client.renderer.entity.layers.*;

public class RenderZombie extends RenderBiped
{
    private static final ResourceLocation zombieTextures;
    private final List field_177122_o;
    private static final ResourceLocation zombieVillagerTextures;
    private final ModelBiped field_82434_o;
    private final List field_177121_n;
    private final ModelZombieVillager zombieVillagerModel;
    
    private void func_82427_a(final EntityZombie entityZombie) {
        if (entityZombie.isVillager()) {
            this.mainModel = this.zombieVillagerModel;
            this.layerRenderers = this.field_177121_n;
        }
        else {
            this.mainModel = this.field_82434_o;
            this.layerRenderers = this.field_177122_o;
        }
        this.modelBipedMain = (ModelBiped)this.mainModel;
    }
    
    static {
        zombieTextures = new ResourceLocation("textures/entity/zombie/zombie.png");
        zombieVillagerTextures = new ResourceLocation("textures/entity/zombie/zombie_villager.png");
    }
    
    @Override
    public void doRender(final EntityLiving entityLiving, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityZombie)entityLiving, n, n2, n3, n4, n5);
    }
    
    @Override
    protected void rotateCorpse(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        this.rotateCorpse((EntityZombie)entityLivingBase, n, n2, n3);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityZombie)entity);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityZombie)entity, n, n2, n3, n4, n5);
    }
    
    @Override
    public void doRender(final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityZombie)entityLivingBase, n, n2, n3, n4, n5);
    }
    
    protected void rotateCorpse(final EntityZombie entityZombie, final float n, float n2, final float n3) {
        if (entityZombie.isConverting()) {
            n2 += (float)(Math.cos(entityZombie.ticksExisted * 3.25) * 3.141592653589793 * 0.25);
        }
        super.rotateCorpse(entityZombie, n, n2, n3);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityLiving entityLiving) {
        return this.getEntityTexture((EntityZombie)entityLiving);
    }
    
    public void doRender(final EntityZombie entityZombie, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.func_82427_a(entityZombie);
        super.doRender(entityZombie, n, n2, n3, n4, n5);
    }
    
    public RenderZombie(final RenderManager renderManager) {
        super(renderManager, new ModelZombie(), 0.5f, 1.0f);
        final LayerRenderer layerRenderer = this.layerRenderers.get(0);
        this.field_82434_o = this.modelBipedMain;
        this.zombieVillagerModel = new ModelZombieVillager();
        this.addLayer(new LayerHeldItem(this));
        final LayerBipedArmor layerBipedArmor = new LayerBipedArmor(this, this) {
            final RenderZombie this$0;
            
            @Override
            protected void initArmor() {
                this.field_177189_c = new ModelZombie(0.5f, true);
                this.field_177186_d = new ModelZombie(1.0f, true);
            }
        };
        this.addLayer(layerBipedArmor);
        this.field_177122_o = Lists.newArrayList((Iterable)this.layerRenderers);
        if (layerRenderer instanceof LayerCustomHead) {
            this.removeLayer(layerRenderer);
            this.addLayer(new LayerCustomHead(this.zombieVillagerModel.bipedHead));
        }
        this.removeLayer(layerBipedArmor);
        this.addLayer(new LayerVillagerArmor(this));
        this.field_177121_n = Lists.newArrayList((Iterable)this.layerRenderers);
    }
    
    protected ResourceLocation getEntityTexture(final EntityZombie entityZombie) {
        return entityZombie.isVillager() ? RenderZombie.zombieVillagerTextures : RenderZombie.zombieTextures;
    }
}
