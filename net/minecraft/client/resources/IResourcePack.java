package net.minecraft.client.resources;

import java.util.*;
import net.minecraft.util.*;
import java.io.*;
import java.awt.image.*;
import net.minecraft.client.resources.data.*;

public interface IResourcePack
{
    Set getResourceDomains();
    
    boolean resourceExists(final ResourceLocation p0);
    
    InputStream getInputStream(final ResourceLocation p0) throws IOException;
    
    BufferedImage getPackImage() throws IOException;
    
    String getPackName();
    
    IMetadataSection getPackMetadata(final IMetadataSerializer p0, final String p1) throws IOException;
}
