package net.minecraft.client.renderer.texture;

import org.lwjgl.opengl.*;

public abstract class AbstractTexture implements ITextureObject
{
    protected int glTextureId;
    protected boolean blur;
    protected boolean mipmap;
    protected boolean blurLast;
    protected boolean mipmapLast;
    
    @Override
    public void setBlurMipmap(final boolean b, final boolean b2) {
        this.blurLast = this.blur;
        this.mipmapLast = this.mipmap;
        this.setBlurMipmapDirect(b, b2);
    }
    
    @Override
    public int getGlTextureId() {
        if (this.glTextureId == -1) {
            this.glTextureId = TextureUtil.glGenTextures();
        }
        return this.glTextureId;
    }
    
    public AbstractTexture() {
        this.glTextureId = -1;
    }
    
    public void setBlurMipmapDirect(final boolean blur, final boolean mipmap) {
        this.blur = blur;
        this.mipmap = mipmap;
        if (blur) {
            final int n = mipmap ? 9987 : 9729;
        }
        else {
            final int n2 = mipmap ? 9986 : 9728;
        }
        GL11.glTexParameteri(3553, 10241, -1);
        GL11.glTexParameteri(3553, 10240, 9728);
    }
    
    @Override
    public void restoreLastBlurMipmap() {
        this.setBlurMipmapDirect(this.blurLast, this.mipmapLast);
    }
    
    public void deleteGlTexture() {
        if (this.glTextureId != -1) {
            TextureUtil.deleteTexture(this.glTextureId);
            this.glTextureId = -1;
        }
    }
}
