package net.minecraft.world.chunk.storage;

import java.io.*;
import net.minecraft.world.*;
import net.minecraft.world.storage.*;
import net.minecraft.nbt.*;

public class AnvilSaveHandler extends SaveHandler
{
    public AnvilSaveHandler(final File file, final String s, final boolean b) {
        super(file, s, b);
    }
    
    @Override
    public IChunkLoader getChunkLoader(final WorldProvider worldProvider) {
        final File worldDirectory = this.getWorldDirectory();
        if (worldProvider instanceof WorldProviderHell) {
            final File file = new File(worldDirectory, "DIM-1");
            file.mkdirs();
            return new AnvilChunkLoader(file);
        }
        if (worldProvider instanceof WorldProviderEnd) {
            final File file2 = new File(worldDirectory, "DIM1");
            file2.mkdirs();
            return new AnvilChunkLoader(file2);
        }
        return new AnvilChunkLoader(worldDirectory);
    }
    
    @Override
    public void flush() {
        ThreadedFileIOBase.getThreadedIOInstance().waitForFinish();
    }
    
    @Override
    public void saveWorldInfoWithPlayer(final WorldInfo worldInfo, final NBTTagCompound nbtTagCompound) {
        worldInfo.setSaveVersion(19133);
        super.saveWorldInfoWithPlayer(worldInfo, nbtTagCompound);
    }
}
