package net.minecraft.client.resources;

import net.minecraft.util.*;
import net.minecraft.client.resources.data.*;
import java.io.*;

public interface IResource
{
    ResourceLocation getResourceLocation();
    
    IMetadataSection getMetadata(final String p0);
    
    InputStream getInputStream();
    
    String getResourcePackName();
    
    boolean hasMetadata();
}
