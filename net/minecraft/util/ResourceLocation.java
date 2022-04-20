package net.minecraft.util;

import org.apache.commons.lang3.*;

public class ResourceLocation
{
    protected final String resourcePath;
    protected final String resourceDomain;
    
    @Override
    public int hashCode() {
        return 31 * this.resourceDomain.hashCode() + this.resourcePath.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResourceLocation)) {
            return false;
        }
        final ResourceLocation resourceLocation = (ResourceLocation)o;
        return this.resourceDomain.equals(resourceLocation.resourceDomain) && this.resourcePath.equals(resourceLocation.resourcePath);
    }
    
    protected static String[] splitObjectName(final String s) {
        final String[] array = { null, s };
        final int index = s.indexOf(58);
        if (index >= 0) {
            array[1] = s.substring(index + 1, s.length());
            if (index > 1) {
                array[0] = s.substring(0, index);
            }
        }
        return array;
    }
    
    public String getResourceDomain() {
        return this.resourceDomain;
    }
    
    @Override
    public String toString() {
        return this.resourceDomain + ':' + this.resourcePath;
    }
    
    protected ResourceLocation(final int n, final String... array) {
        this.resourceDomain = (StringUtils.isEmpty((CharSequence)array[0]) ? "minecraft" : array[0].toLowerCase());
        Validate.notNull((Object)(this.resourcePath = array[1]));
    }
    
    public ResourceLocation(final String s) {
        this(0, splitObjectName(s));
    }
    
    public ResourceLocation(final String s, final String s2) {
        this(0, new String[] { s, s2 });
    }
    
    public String getResourcePath() {
        return this.resourcePath;
    }
}
