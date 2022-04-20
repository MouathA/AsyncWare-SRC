package net.minecraft.client.model;

import com.google.common.collect.*;
import java.util.*;
import net.minecraft.entity.*;

public abstract class ModelBase
{
    public boolean isChild;
    public int textureWidth;
    public List boxList;
    public boolean isRiding;
    public int textureHeight;
    public float swingProgress;
    private Map modelTextureMap;
    
    public TextureOffset getTextureOffset(final String s) {
        return this.modelTextureMap.get(s);
    }
    
    public ModelBase() {
        this.isChild = true;
        this.boxList = Lists.newArrayList();
        this.modelTextureMap = Maps.newHashMap();
        this.textureWidth = 64;
        this.textureHeight = 32;
    }
    
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
    }
    
    public static void copyModelAngles(final ModelRenderer modelRenderer, final ModelRenderer modelRenderer2) {
        modelRenderer2.rotateAngleX = modelRenderer.rotateAngleX;
        modelRenderer2.rotateAngleY = modelRenderer.rotateAngleY;
        modelRenderer2.rotateAngleZ = modelRenderer.rotateAngleZ;
        modelRenderer2.rotationPointX = modelRenderer.rotationPointX;
        modelRenderer2.rotationPointY = modelRenderer.rotationPointY;
        modelRenderer2.rotationPointZ = modelRenderer.rotationPointZ;
    }
    
    public ModelRenderer getRandomModelBox(final Random random) {
        return this.boxList.get(random.nextInt(this.boxList.size()));
    }
    
    public void setLivingAnimations(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
    }
    
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
    }
    
    public void setModelAttributes(final ModelBase modelBase) {
        this.swingProgress = modelBase.swingProgress;
        this.isRiding = modelBase.isRiding;
        this.isChild = modelBase.isChild;
    }
    
    protected void setTextureOffset(final String s, final int n, final int n2) {
        this.modelTextureMap.put(s, new TextureOffset(n, n2));
    }
}
