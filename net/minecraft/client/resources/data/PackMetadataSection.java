package net.minecraft.client.resources.data;

import net.minecraft.util.*;

public class PackMetadataSection implements IMetadataSection
{
    private final int packFormat;
    private final IChatComponent packDescription;
    
    public PackMetadataSection(final IChatComponent packDescription, final int packFormat) {
        this.packDescription = packDescription;
        this.packFormat = packFormat;
    }
    
    public int getPackFormat() {
        return this.packFormat;
    }
    
    public IChatComponent getPackDescription() {
        return this.packDescription;
    }
}
