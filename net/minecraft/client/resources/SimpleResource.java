package net.minecraft.client.resources;

import java.util.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraft.client.resources.data.*;
import java.io.*;
import com.google.gson.*;
import org.apache.commons.io.*;

public class SimpleResource implements IResource
{
    private JsonObject mcmetaJson;
    private final Map mapMetadataSections;
    private final InputStream resourceInputStream;
    private final ResourceLocation srResourceLocation;
    private final IMetadataSerializer srMetadataSerializer;
    private final String resourcePackName;
    private final InputStream mcmetaInputStream;
    private boolean mcmetaJsonChecked;
    
    @Override
    public String getResourcePackName() {
        return this.resourcePackName;
    }
    
    @Override
    public InputStream getInputStream() {
        return this.resourceInputStream;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SimpleResource)) {
            return false;
        }
        final SimpleResource simpleResource = (SimpleResource)o;
        if (this.srResourceLocation != null) {
            if (!this.srResourceLocation.equals(simpleResource.srResourceLocation)) {
                return false;
            }
        }
        else if (simpleResource.srResourceLocation != null) {
            return false;
        }
        if (this.resourcePackName != null) {
            if (!this.resourcePackName.equals(simpleResource.resourcePackName)) {
                return false;
            }
        }
        else if (simpleResource.resourcePackName != null) {
            return false;
        }
        return true;
    }
    
    public SimpleResource(final String resourcePackName, final ResourceLocation srResourceLocation, final InputStream resourceInputStream, final InputStream mcmetaInputStream, final IMetadataSerializer srMetadataSerializer) {
        this.mapMetadataSections = Maps.newHashMap();
        this.resourcePackName = resourcePackName;
        this.srResourceLocation = srResourceLocation;
        this.resourceInputStream = resourceInputStream;
        this.mcmetaInputStream = mcmetaInputStream;
        this.srMetadataSerializer = srMetadataSerializer;
    }
    
    @Override
    public int hashCode() {
        return 31 * ((this.resourcePackName != null) ? this.resourcePackName.hashCode() : 0) + ((this.srResourceLocation != null) ? this.srResourceLocation.hashCode() : 0);
    }
    
    @Override
    public IMetadataSection getMetadata(final String s) {
        if (this != null) {
            return null;
        }
        if (this.mcmetaJson == null && !this.mcmetaJsonChecked) {
            this.mcmetaJsonChecked = true;
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.mcmetaInputStream));
            this.mcmetaJson = new JsonParser().parse((Reader)bufferedReader).getAsJsonObject();
            IOUtils.closeQuietly((Reader)bufferedReader);
        }
        IMetadataSection metadataSection = this.mapMetadataSections.get(s);
        if (metadataSection == null) {
            metadataSection = this.srMetadataSerializer.parseMetadataSection(s, this.mcmetaJson);
        }
        return metadataSection;
    }
    
    @Override
    public ResourceLocation getResourceLocation() {
        return this.srResourceLocation;
    }
}
