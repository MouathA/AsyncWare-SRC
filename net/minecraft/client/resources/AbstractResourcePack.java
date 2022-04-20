package net.minecraft.client.resources;

import java.awt.image.*;
import net.minecraft.client.renderer.texture.*;
import org.apache.logging.log4j.*;
import net.minecraft.util.*;
import net.minecraft.client.resources.data.*;
import com.google.common.base.*;
import java.io.*;
import org.apache.commons.io.*;
import com.google.gson.*;

public abstract class AbstractResourcePack implements IResourcePack
{
    private static final Logger resourceLog;
    public final File resourcePackFile;
    
    protected void logNameNotLowercase(final String s) {
        AbstractResourcePack.resourceLog.warn("ResourcePack: ignored non-lowercase namespace: %s in %s", new Object[] { s, this.resourcePackFile });
    }
    
    @Override
    public BufferedImage getPackImage() throws IOException {
        return TextureUtil.readBufferedImage(this.getInputStreamByName("pack.png"));
    }
    
    static {
        resourceLog = LogManager.getLogger();
    }
    
    public AbstractResourcePack(final File resourcePackFile) {
        this.resourcePackFile = resourcePackFile;
    }
    
    protected abstract InputStream getInputStreamByName(final String p0) throws IOException;
    
    @Override
    public String getPackName() {
        return this.resourcePackFile.getName();
    }
    
    protected abstract boolean hasResourceName(final String p0);
    
    @Override
    public boolean resourceExists(final ResourceLocation resourceLocation) {
        return this.hasResourceName(locationToName(resourceLocation));
    }
    
    protected static String getRelativeName(final File file, final File file2) {
        return file.toURI().relativize(file2.toURI()).getPath();
    }
    
    @Override
    public InputStream getInputStream(final ResourceLocation resourceLocation) throws IOException {
        return this.getInputStreamByName(locationToName(resourceLocation));
    }
    
    static IMetadataSection readMetadata(final IMetadataSerializer metadataSerializer, final InputStream inputStream, final String s) {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charsets.UTF_8));
        final JsonObject asJsonObject = new JsonParser().parse((Reader)bufferedReader).getAsJsonObject();
        IOUtils.closeQuietly((Reader)bufferedReader);
        return metadataSerializer.parseMetadataSection(s, asJsonObject);
    }
    
    @Override
    public IMetadataSection getPackMetadata(final IMetadataSerializer metadataSerializer, final String s) throws IOException {
        return readMetadata(metadataSerializer, this.getInputStreamByName("pack.mcmeta"), s);
    }
    
    private static String locationToName(final ResourceLocation resourceLocation) {
        return String.format("%s/%s/%s", "assets", resourceLocation.getResourceDomain(), resourceLocation.getResourcePath());
    }
}
