package net.minecraft.world.gen;

import net.minecraft.world.chunk.storage.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import org.apache.logging.log4j.*;
import java.util.concurrent.*;
import com.google.common.collect.*;
import net.minecraft.world.chunk.*;
import net.minecraft.util.*;
import java.util.*;

public class ChunkProviderServer implements IChunkProvider
{
    private List loadedChunks;
    private LongHashMap id2ChunkMap;
    public boolean chunkLoadOverride;
    private IChunkProvider serverChunkGenerator;
    private Set droppedChunksSet;
    private static final Logger logger;
    private WorldServer worldObj;
    private Chunk dummyChunk;
    private IChunkLoader chunkLoader;
    
    @Override
    public void populate(final IChunkProvider chunkProvider, final int n, final int n2) {
        final Chunk provideChunk = this.provideChunk(n, n2);
        if (!provideChunk.isTerrainPopulated()) {
            provideChunk.func_150809_p();
            if (this.serverChunkGenerator != null) {
                this.serverChunkGenerator.populate(chunkProvider, n, n2);
                provideChunk.setChunkModified();
            }
        }
    }
    
    @Override
    public Chunk provideChunk(final int n, final int n2) {
        final Chunk chunk = (Chunk)this.id2ChunkMap.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(n, n2));
        return (chunk == null) ? ((!this.worldObj.isFindingSpawnPoint() && !this.chunkLoadOverride) ? this.dummyChunk : this.loadChunk(n, n2)) : chunk;
    }
    
    @Override
    public boolean chunkExists(final int n, final int n2) {
        return this.id2ChunkMap.containsItem(ChunkCoordIntPair.chunkXZ2Int(n, n2));
    }
    
    @Override
    public void saveExtraData() {
        if (this.chunkLoader != null) {
            this.chunkLoader.saveExtraData();
        }
    }
    
    @Override
    public boolean canSave() {
        return !this.worldObj.disableLevelSaving;
    }
    
    private void saveChunkExtraData(final Chunk chunk) {
        if (this.chunkLoader != null) {
            this.chunkLoader.saveExtraChunkData(this.worldObj, chunk);
        }
    }
    
    @Override
    public boolean func_177460_a(final IChunkProvider chunkProvider, final Chunk chunk, final int n, final int n2) {
        if (this.serverChunkGenerator != null && this.serverChunkGenerator.func_177460_a(chunkProvider, chunk, n, n2)) {
            this.provideChunk(n, n2).setChunkModified();
            return true;
        }
        return false;
    }
    
    private void saveChunkData(final Chunk chunk) {
        if (this.chunkLoader != null) {
            chunk.setLastSaveTime(this.worldObj.getTotalWorldTime());
            this.chunkLoader.saveChunk(this.worldObj, chunk);
        }
    }
    
    public Chunk loadChunk(final int n, final int n2) {
        final long chunkXZ2Int = ChunkCoordIntPair.chunkXZ2Int(n, n2);
        this.droppedChunksSet.remove(chunkXZ2Int);
        Chunk chunk = (Chunk)this.id2ChunkMap.getValueByKey(chunkXZ2Int);
        if (chunk == null) {
            chunk = this.loadChunkFromFile(n, n2);
            if (chunk == null) {
                if (this.serverChunkGenerator == null) {
                    chunk = this.dummyChunk;
                }
                else {
                    chunk = this.serverChunkGenerator.provideChunk(n, n2);
                }
            }
            this.id2ChunkMap.add(chunkXZ2Int, chunk);
            this.loadedChunks.add(chunk);
            chunk.onChunkLoad();
            chunk.populateChunk(this, this, n, n2);
        }
        return chunk;
    }
    
    @Override
    public List getPossibleCreatures(final EnumCreatureType enumCreatureType, final BlockPos blockPos) {
        return this.serverChunkGenerator.getPossibleCreatures(enumCreatureType, blockPos);
    }
    
    @Override
    public boolean unloadQueuedChunks() {
        if (this.worldObj.disableLevelSaving) {
            return this.serverChunkGenerator.unloadQueuedChunks();
        }
        while (true) {
            if (!this.droppedChunksSet.isEmpty()) {
                final Long n = this.droppedChunksSet.iterator().next();
                final Chunk chunk = (Chunk)this.id2ChunkMap.getValueByKey(n);
                if (chunk != null) {
                    chunk.onChunkUnload();
                    this.saveChunkData(chunk);
                    this.saveChunkExtraData(chunk);
                    this.id2ChunkMap.remove(n);
                    this.loadedChunks.remove(chunk);
                }
                this.droppedChunksSet.remove(n);
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    public int getLoadedChunkCount() {
        return this.id2ChunkMap.getNumHashElements();
    }
    
    public List func_152380_a() {
        return this.loadedChunks;
    }
    
    public void unloadAllChunks() {
        for (final Chunk chunk : this.loadedChunks) {
            this.dropChunk(chunk.xPosition, chunk.zPosition);
        }
    }
    
    static {
        logger = LogManager.getLogger();
    }
    
    public ChunkProviderServer(final WorldServer worldObj, final IChunkLoader chunkLoader, final IChunkProvider serverChunkGenerator) {
        this.droppedChunksSet = Collections.newSetFromMap(new ConcurrentHashMap<Object, Boolean>());
        this.chunkLoadOverride = true;
        this.id2ChunkMap = new LongHashMap();
        this.loadedChunks = Lists.newArrayList();
        this.dummyChunk = new EmptyChunk(worldObj, 0, 0);
        this.worldObj = worldObj;
        this.chunkLoader = chunkLoader;
        this.serverChunkGenerator = serverChunkGenerator;
    }
    
    @Override
    public void recreateStructures(final Chunk chunk, final int n, final int n2) {
    }
    
    @Override
    public boolean saveChunks(final boolean b, final IProgressUpdate progressUpdate) {
        final ArrayList arrayList = Lists.newArrayList((Iterable)this.loadedChunks);
        while (0 < arrayList.size()) {
            final Chunk chunk = arrayList.get(0);
            if (b) {
                this.saveChunkExtraData(chunk);
            }
            if (chunk.needsSaving(b)) {
                this.saveChunkData(chunk);
                chunk.setModified(false);
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        return true;
    }
    
    @Override
    public String makeString() {
        return "ServerChunkCache: " + this.id2ChunkMap.getNumHashElements() + " Drop: " + this.droppedChunksSet.size();
    }
    
    public void dropChunk(final int n, final int n2) {
        if (this.worldObj.provider.canRespawnHere()) {
            if (!this.worldObj.isSpawnChunk(n, n2)) {
                this.droppedChunksSet.add(ChunkCoordIntPair.chunkXZ2Int(n, n2));
            }
        }
        else {
            this.droppedChunksSet.add(ChunkCoordIntPair.chunkXZ2Int(n, n2));
        }
    }
    
    private Chunk loadChunkFromFile(final int n, final int n2) {
        if (this.chunkLoader == null) {
            return null;
        }
        final Chunk loadChunk = this.chunkLoader.loadChunk(this.worldObj, n, n2);
        if (loadChunk != null) {
            loadChunk.setLastSaveTime(this.worldObj.getTotalWorldTime());
            if (this.serverChunkGenerator != null) {
                this.serverChunkGenerator.recreateStructures(loadChunk, n, n2);
            }
        }
        return loadChunk;
    }
    
    @Override
    public BlockPos getStrongholdGen(final World world, final String s, final BlockPos blockPos) {
        return this.serverChunkGenerator.getStrongholdGen(world, s, blockPos);
    }
    
    @Override
    public Chunk provideChunk(final BlockPos blockPos) {
        return this.provideChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4);
    }
}
