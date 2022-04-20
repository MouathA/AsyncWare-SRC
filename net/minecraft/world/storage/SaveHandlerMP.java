package net.minecraft.world.storage;

import net.minecraft.world.chunk.storage.*;
import java.io.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;

public class SaveHandlerMP implements ISaveHandler
{
    @Override
    public WorldInfo loadWorldInfo() {
        return null;
    }
    
    @Override
    public IChunkLoader getChunkLoader(final WorldProvider worldProvider) {
        return null;
    }
    
    @Override
    public String getWorldDirectoryName() {
        return "none";
    }
    
    @Override
    public File getMapFileFromName(final String s) {
        return null;
    }
    
    @Override
    public IPlayerFileData getPlayerNBTManager() {
        return null;
    }
    
    @Override
    public void checkSessionLock() throws MinecraftException {
    }
    
    @Override
    public void flush() {
    }
    
    @Override
    public void saveWorldInfo(final WorldInfo worldInfo) {
    }
    
    @Override
    public File getWorldDirectory() {
        return null;
    }
    
    @Override
    public void saveWorldInfoWithPlayer(final WorldInfo worldInfo, final NBTTagCompound nbtTagCompound) {
    }
}
