package net.minecraft.client.resources;

import java.io.*;

public class ResourcePackFileNotFoundException extends FileNotFoundException
{
    public ResourcePackFileNotFoundException(final File file, final String s) {
        super(String.format("'%s' in ResourcePack '%s'", s, file));
    }
}
