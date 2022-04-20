package net.minecraft.client.renderer.texture;

import net.minecraft.client.resources.*;
import java.io.*;

public interface ITextureObject
{
    int getGlTextureId();
    
    void restoreLastBlurMipmap();
    
    void loadTexture(final IResourceManager p0) throws IOException;
    
    void setBlurMipmap(final boolean p0, final boolean p1);
}
