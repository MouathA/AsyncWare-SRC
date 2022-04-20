package net.minecraft.client.resources.data;

import java.util.*;

public class TextureMetadataSection implements IMetadataSection
{
    private final boolean textureBlur;
    private final List listMipmaps;
    private final boolean textureClamp;
    
    public boolean getTextureBlur() {
        return this.textureBlur;
    }
    
    public boolean getTextureClamp() {
        return this.textureClamp;
    }
    
    public TextureMetadataSection(final boolean textureBlur, final boolean textureClamp, final List listMipmaps) {
        this.textureBlur = textureBlur;
        this.textureClamp = textureClamp;
        this.listMipmaps = listMipmaps;
    }
    
    public List getListMipmaps() {
        return Collections.unmodifiableList((List<?>)this.listMipmaps);
    }
}
