package net.minecraft.client.resources;

import java.util.*;
import java.awt.image.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import java.io.*;
import com.google.common.collect.*;
import net.minecraft.client.resources.data.*;

public class DefaultResourcePack implements IResourcePack
{
    private final Map mapAssets;
    public static final Set defaultResourceDomains;
    
    @Override
    public String getPackName() {
        return "Default";
    }
    
    @Override
    public BufferedImage getPackImage() throws IOException {
        return TextureUtil.readBufferedImage(DefaultResourcePack.class.getResourceAsStream("/" + new ResourceLocation("pack.png").getResourcePath()));
    }
    
    public InputStream getInputStreamAssets(final ResourceLocation resourceLocation) throws IOException, FileNotFoundException {
        final File file = this.mapAssets.get(resourceLocation.toString());
        return (file != null && file.isFile()) ? new FileInputStream(file) : null;
    }
    
    private InputStream getResourceStream(final ResourceLocation resourceLocation) {
        return DefaultResourcePack.class.getResourceAsStream("/assets/" + resourceLocation.getResourceDomain() + "/" + resourceLocation.getResourcePath());
    }
    
    @Override
    public boolean resourceExists(final ResourceLocation resourceLocation) {
        return this.getResourceStream(resourceLocation) != null || this.mapAssets.containsKey(resourceLocation.toString());
    }
    
    @Override
    public InputStream getInputStream(final ResourceLocation resourceLocation) throws IOException {
        final InputStream resourceStream = this.getResourceStream(resourceLocation);
        if (resourceStream != null) {
            return resourceStream;
        }
        final InputStream inputStreamAssets = this.getInputStreamAssets(resourceLocation);
        if (inputStreamAssets != null) {
            return inputStreamAssets;
        }
        throw new FileNotFoundException(resourceLocation.getResourcePath());
    }
    
    static {
        defaultResourceDomains = (Set)ImmutableSet.of((Object)"minecraft", (Object)"realms");
    }
    
    @Override
    public IMetadataSection getPackMetadata(final IMetadataSerializer metadataSerializer, final String s) throws IOException {
        return AbstractResourcePack.readMetadata(metadataSerializer, new FileInputStream(this.mapAssets.get("pack.mcmeta")), s);
    }
    
    public DefaultResourcePack(final Map mapAssets) {
        this.mapAssets = mapAssets;
    }
    
    @Override
    public Set getResourceDomains() {
        return DefaultResourcePack.defaultResourceDomains;
    }
}
