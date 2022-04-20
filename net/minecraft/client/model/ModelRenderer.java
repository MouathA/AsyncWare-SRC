package net.minecraft.client.model;

import org.lwjgl.opengl.*;
import optfine.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.client.renderer.*;

public class ModelRenderer
{
    public final String boxName;
    public boolean isHidden;
    public List childModels;
    private ModelBase baseModel;
    private int textureOffsetY;
    public float rotationPointZ;
    public float rotateAngleX;
    public float offsetY;
    private static final String __OBFID = "CL_00000874";
    public float offsetZ;
    public float textureWidth;
    private boolean compiled;
    public float rotationPointY;
    public List spriteList;
    private int textureOffsetX;
    public boolean mirror;
    public float offsetX;
    public float textureHeight;
    public boolean showModel;
    public List cubeList;
    public float rotateAngleY;
    public boolean mirrorV;
    public float rotationPointX;
    public float rotateAngleZ;
    private int displayList;
    
    public void addBox(final float n, final float n2, final float n3, final int n4, final int n5, final int n6, final float n7) {
        this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, n, n2, n3, n4, n5, n6, n7));
    }
    
    public void setRotationPoint(final float rotationPointX, final float rotationPointY, final float rotationPointZ) {
        this.rotationPointX = rotationPointX;
        this.rotationPointY = rotationPointY;
        this.rotationPointZ = rotationPointZ;
    }
    
    public ModelRenderer setTextureOffset(final int textureOffsetX, final int textureOffsetY) {
        this.textureOffsetX = textureOffsetX;
        this.textureOffsetY = textureOffsetY;
        return this;
    }
    
    private void compileDisplayList(final float n) {
        GL11.glNewList(this.displayList = GLAllocation.generateDisplayLists(1), 4864);
        final WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
        int n2 = 0;
        while (0 < this.cubeList.size()) {
            this.cubeList.get(0).render(worldRenderer, n);
            ++n2;
        }
        while (0 < this.spriteList.size()) {
            this.spriteList.get(0).render(Tessellator.getInstance(), n);
            ++n2;
        }
        GL11.glEndList();
        this.compiled = true;
    }
    
    public ModelRenderer(final ModelBase baseModel, final String boxName) {
        this.spriteList = new ArrayList();
        this.mirrorV = false;
        this.textureWidth = 64.0f;
        this.textureHeight = 32.0f;
        this.showModel = true;
        this.cubeList = Lists.newArrayList();
        this.baseModel = baseModel;
        baseModel.boxList.add(this);
        this.boxName = boxName;
        this.setTextureSize(baseModel.textureWidth, baseModel.textureHeight);
    }
    
    public void addChild(final ModelRenderer modelRenderer) {
        if (this.childModels == null) {
            this.childModels = Lists.newArrayList();
        }
        this.childModels.add(modelRenderer);
    }
    
    public ModelRenderer addBox(String string, final float n, final float n2, final float n3, final int n4, final int n5, final int n6) {
        string = this.boxName + "." + string;
        final TextureOffset textureOffset = this.baseModel.getTextureOffset(string);
        this.setTextureOffset(textureOffset.textureOffsetX, textureOffset.textureOffsetY);
        this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, n, n2, n3, n4, n5, n6, 0.0f).setBoxName(string));
        return this;
    }
    
    public void postRender(final float n) {
        if (!this.isHidden && this.showModel) {
            if (!this.compiled) {
                this.compileDisplayList(n);
            }
            if (this.rotateAngleX == 0.0f && this.rotateAngleY == 0.0f && this.rotateAngleZ == 0.0f) {
                if (this.rotationPointX != 0.0f || this.rotationPointY != 0.0f || this.rotationPointZ != 0.0f) {
                    GlStateManager.translate(this.rotationPointX * n, this.rotationPointY * n, this.rotationPointZ * n);
                }
            }
            else {
                GlStateManager.translate(this.rotationPointX * n, this.rotationPointY * n, this.rotationPointZ * n);
                if (this.rotateAngleZ != 0.0f) {
                    GlStateManager.rotate(this.rotateAngleZ * 57.295776f, 0.0f, 0.0f, 1.0f);
                }
                if (this.rotateAngleY != 0.0f) {
                    GlStateManager.rotate(this.rotateAngleY * 57.295776f, 0.0f, 1.0f, 0.0f);
                }
                if (this.rotateAngleX != 0.0f) {
                    GlStateManager.rotate(this.rotateAngleX * 57.295776f, 1.0f, 0.0f, 0.0f);
                }
            }
        }
    }
    
    public void addSprite(final float n, final float n2, final float n3, final int n4, final int n5, final int n6, final float n7) {
        this.spriteList.add(new ModelSprite(this, this.textureOffsetX, this.textureOffsetY, n, n2, n3, n4, n5, n6, n7));
    }
    
    public void renderWithRotation(final float n) {
        if (!this.isHidden && this.showModel) {
            if (!this.compiled) {
                this.compileDisplayList(n);
            }
            GlStateManager.translate(this.rotationPointX * n, this.rotationPointY * n, this.rotationPointZ * n);
            if (this.rotateAngleY != 0.0f) {
                GlStateManager.rotate(this.rotateAngleY * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            if (this.rotateAngleX != 0.0f) {
                GlStateManager.rotate(this.rotateAngleX * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            if (this.rotateAngleZ != 0.0f) {
                GlStateManager.rotate(this.rotateAngleZ * 57.295776f, 0.0f, 0.0f, 1.0f);
            }
            GlStateManager.callList(this.displayList);
        }
    }
    
    public ModelRenderer setTextureSize(final int n, final int n2) {
        this.textureWidth = (float)n;
        this.textureHeight = (float)n2;
        return this;
    }
    
    public ModelRenderer addBox(final float n, final float n2, final float n3, final int n4, final int n5, final int n6, final boolean b) {
        this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, n, n2, n3, n4, n5, n6, 0.0f, b));
        return this;
    }
    
    public void render(final float n) {
        if (!this.isHidden && this.showModel) {
            if (!this.compiled) {
                this.compileDisplayList(n);
            }
            GlStateManager.translate(this.offsetX, this.offsetY, this.offsetZ);
            if (this.rotateAngleX == 0.0f && this.rotateAngleY == 0.0f && this.rotateAngleZ == 0.0f) {
                if (this.rotationPointX == 0.0f && this.rotationPointY == 0.0f && this.rotationPointZ == 0.0f) {
                    GlStateManager.callList(this.displayList);
                    if (this.childModels != null) {
                        while (0 < this.childModels.size()) {
                            this.childModels.get(0).render(n);
                            int n2 = 0;
                            ++n2;
                        }
                    }
                }
                else {
                    GlStateManager.translate(this.rotationPointX * n, this.rotationPointY * n, this.rotationPointZ * n);
                    GlStateManager.callList(this.displayList);
                    if (this.childModels != null) {
                        while (0 < this.childModels.size()) {
                            this.childModels.get(0).render(n);
                            int n2 = 0;
                            ++n2;
                        }
                    }
                    GlStateManager.translate(-this.rotationPointX * n, -this.rotationPointY * n, -this.rotationPointZ * n);
                }
            }
            else {
                GlStateManager.translate(this.rotationPointX * n, this.rotationPointY * n, this.rotationPointZ * n);
                if (this.rotateAngleZ != 0.0f) {
                    GlStateManager.rotate(this.rotateAngleZ * 57.295776f, 0.0f, 0.0f, 1.0f);
                }
                if (this.rotateAngleY != 0.0f) {
                    GlStateManager.rotate(this.rotateAngleY * 57.295776f, 0.0f, 1.0f, 0.0f);
                }
                if (this.rotateAngleX != 0.0f) {
                    GlStateManager.rotate(this.rotateAngleX * 57.295776f, 1.0f, 0.0f, 0.0f);
                }
                GlStateManager.callList(this.displayList);
                if (this.childModels != null) {
                    while (0 < this.childModels.size()) {
                        this.childModels.get(0).render(n);
                        int n2 = 0;
                        ++n2;
                    }
                }
            }
            GlStateManager.translate(-this.offsetX, -this.offsetY, -this.offsetZ);
        }
    }
    
    public ModelRenderer(final ModelBase modelBase) {
        this(modelBase, null);
    }
    
    public ModelRenderer(final ModelBase modelBase, final int n, final int n2) {
        this(modelBase);
        this.setTextureOffset(n, n2);
    }
    
    public ModelRenderer addBox(final float n, final float n2, final float n3, final int n4, final int n5, final int n6) {
        this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, n, n2, n3, n4, n5, n6, 0.0f));
        return this;
    }
}
