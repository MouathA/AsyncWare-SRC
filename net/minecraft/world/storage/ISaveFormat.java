package net.minecraft.world.storage;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.*;

public interface ISaveFormat
{
    String getName();
    
    boolean convertMapFormat(final String p0, final IProgressUpdate p1);
    
    void flushCache();
    
    WorldInfo getWorldInfo(final String p0);
    
    boolean canLoadWorld(final String p0);
    
    boolean func_154335_d(final String p0);
    
    void renameWorld(final String p0, final String p1);
    
    ISaveHandler getSaveLoader(final String p0, final boolean p1);
    
    boolean func_154334_a(final String p0);
    
    List getSaveList() throws AnvilConverterException;
    
    boolean deleteWorldDirectory(final String p0);
    
    boolean isOldMapFormat(final String p0);
}
