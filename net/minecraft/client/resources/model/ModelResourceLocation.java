package net.minecraft.client.resources.model;

import net.minecraft.util.*;
import org.apache.commons.lang3.*;

public class ModelResourceLocation extends ResourceLocation
{
    private final String variant;
    
    public ModelResourceLocation(final ResourceLocation resourceLocation, final String s) {
        this(resourceLocation.toString(), s);
    }
    
    public ModelResourceLocation(final String s) {
        this(0, parsePathString(s));
    }
    
    public String getVariant() {
        return this.variant;
    }
    
    public ModelResourceLocation(final String s, final String s2) {
        this(0, parsePathString(s + '#' + ((s2 == null) ? "normal" : s2)));
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o instanceof ModelResourceLocation && super.equals(o) && this.variant.equals(((ModelResourceLocation)o).variant));
    }
    
    protected ModelResourceLocation(final int n, final String... array) {
        super(0, new String[] { array[0], array[1] });
        this.variant = (StringUtils.isEmpty((CharSequence)array[2]) ? "normal" : array[2].toLowerCase());
    }
    
    @Override
    public String toString() {
        return super.toString() + '#' + this.variant;
    }
    
    @Override
    public int hashCode() {
        return 31 * super.hashCode() + this.variant.hashCode();
    }
    
    protected static String[] parsePathString(final String s) {
        final String[] array = { null, s, null };
        final int index = s.indexOf(35);
        String substring = s;
        if (index >= 0) {
            array[2] = s.substring(index + 1, s.length());
            if (index > 1) {
                substring = s.substring(0, index);
            }
        }
        System.arraycopy(ResourceLocation.splitObjectName(substring), 0, array, 0, 2);
        return array;
    }
}
