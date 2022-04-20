package net.minecraft.world.chunk;

import net.minecraft.util.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.world.*;

public interface IChunkProvider
{
    boolean saveChunks(final boolean p0, final IProgressUpdate p1);
    
    Chunk provideChunk(final int p0, final int p1);
    
    boolean unloadQueuedChunks();
    
    Chunk provideChunk(final BlockPos p0);
    
    String makeString();
    
    void populate(final IChunkProvider p0, final int p1, final int p2);
    
    List getPossibleCreatures(final EnumCreatureType p0, final BlockPos p1);
    
    int getLoadedChunkCount();
    
    void saveExtraData();
    
    void recreateStructures(final Chunk p0, final int p1, final int p2);
    
    BlockPos getStrongholdGen(final World p0, final String p1, final BlockPos p2);
    
    boolean func_177460_a(final IChunkProvider p0, final Chunk p1, final int p2, final int p3);
    
    boolean canSave();
    
    boolean chunkExists(final int p0, final int p1);
}
