package net.minecraft.client.renderer.entity;

import net.minecraft.entity.effect.*;
import net.minecraft.client.renderer.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class RenderLightningBolt extends Render
{
    public void doRender(final EntityLightningBolt entityLightningBolt, final double n, final double n2, final double n3, final float n4, final float n5) {
        Tessellator.getInstance().getWorldRenderer();
        GlStateManager.blendFunc(770, 1);
        final double[] array = new double[8];
        final double[] array2 = new double[8];
        double n6 = 0.0;
        double n7 = 0.0;
        final Random random = new Random(entityLightningBolt.boltVertex);
        while (true) {
            array[0] = n6;
            array2[0] = n7;
            n6 += random.nextInt(11) - 5;
            n7 += random.nextInt(11) - 5;
            int n8 = 0;
            --n8;
        }
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityLightningBolt)entity);
    }
    
    protected ResourceLocation getEntityTexture(final EntityLightningBolt entityLightningBolt) {
        return null;
    }
    
    public RenderLightningBolt(final RenderManager renderManager) {
        super(renderManager);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityLightningBolt)entity, n, n2, n3, n4, n5);
    }
}
