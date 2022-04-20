package net.minecraft.world.storage;

import java.io.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.storage.*;

public interface ISaveHandler
{
    String getWorldDirectoryName();
    
    void saveWorldInfo(final WorldInfo p0);
    
    File getWorldDirectory();
    
    IPlayerFileData getPlayerNBTManager();
    
    void checkSessionLock() throws MinecraftException;
    
    File getMapFileFromName(final String p0);
    
    void flush();
    
    WorldInfo loadWorldInfo();
    
    void saveWorldInfoWithPlayer(final WorldInfo p0, final NBTTagCompound p1);
    
    IChunkLoader getChunkLoader(final WorldProvider p0);
}
