package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class ModelBlaze extends ModelBase
{
    private ModelRenderer[] blazeSticks;
    private ModelRenderer blazeHead;
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        float n7 = 0.47123894f + n3 * 3.1415927f * -0.05f;
        while (true) {
            this.blazeSticks[8].rotationPointY = 11.0f + MathHelper.cos((8 * 1.5f + n3) * 0.5f);
            this.blazeSticks[8].rotationPointX = MathHelper.cos(n7) * 5.0f;
            this.blazeSticks[8].rotationPointZ = MathHelper.sin(n7) * 5.0f;
            ++n7;
            int n8 = 0;
            ++n8;
        }
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.blazeHead.render(n6);
        while (0 < this.blazeSticks.length) {
            this.blazeSticks[0].render(n6);
            int n7 = 0;
            ++n7;
        }
    }
    
    public ModelBlaze() {
        this.blazeSticks = new ModelRenderer[12];
        while (0 < this.blazeSticks.length) {
            (this.blazeSticks[0] = new ModelRenderer(this, 0, 16)).addBox(0.0f, 0.0f, 0.0f, 2, 8, 2);
            int n = 0;
            ++n;
        }
        (this.blazeHead = new ModelRenderer(this, 0, 0)).addBox(-4.0f, -4.0f, -4.0f, 8, 8, 8);
    }
}
