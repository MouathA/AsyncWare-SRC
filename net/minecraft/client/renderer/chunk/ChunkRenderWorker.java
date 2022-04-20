package net.minecraft.client.renderer.chunk;

import net.minecraft.client.renderer.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import com.google.common.util.concurrent.*;
import java.util.concurrent.*;
import net.minecraft.crash.*;
import net.minecraft.entity.*;
import java.util.*;

public class ChunkRenderWorker implements Runnable
{
    private final RegionRenderCacheBuilder regionRenderCacheBuilder;
    private final ChunkRenderDispatcher chunkRenderDispatcher;
    private static final Logger LOGGER;
    
    static {
        LOGGER = LogManager.getLogger();
    }
    
    static Logger access$100() {
        return ChunkRenderWorker.LOGGER;
    }
    
    private void freeRenderBuilder(final ChunkCompileTaskGenerator chunkCompileTaskGenerator) {
        if (this.regionRenderCacheBuilder == null) {
            this.chunkRenderDispatcher.freeRenderBuilder(chunkCompileTaskGenerator.getRegionRenderCacheBuilder());
        }
    }
    
    static void access$000(final ChunkRenderWorker chunkRenderWorker, final ChunkCompileTaskGenerator chunkCompileTaskGenerator) {
        chunkRenderWorker.freeRenderBuilder(chunkCompileTaskGenerator);
    }
    
    private RegionRenderCacheBuilder getRegionRenderCacheBuilder() throws InterruptedException {
        return (this.regionRenderCacheBuilder != null) ? this.regionRenderCacheBuilder : this.chunkRenderDispatcher.allocateRenderBuilder();
    }
    
    @Override
    public void run() {
        while (true) {
            this.processTask(this.chunkRenderDispatcher.getNextChunkUpdate());
        }
    }
    
    public ChunkRenderWorker(final ChunkRenderDispatcher chunkRenderDispatcher) {
        this(chunkRenderDispatcher, null);
    }
    
    protected void processTask(final ChunkCompileTaskGenerator chunkCompileTaskGenerator) throws InterruptedException {
        chunkCompileTaskGenerator.getLock().lock();
        if (chunkCompileTaskGenerator.getStatus() != ChunkCompileTaskGenerator.Status.PENDING) {
            if (!chunkCompileTaskGenerator.isFinished()) {
                ChunkRenderWorker.LOGGER.warn("Chunk render task was " + chunkCompileTaskGenerator.getStatus() + " when I expected it to be pending; ignoring task");
            }
            chunkCompileTaskGenerator.getLock().unlock();
            return;
        }
        chunkCompileTaskGenerator.setStatus(ChunkCompileTaskGenerator.Status.COMPILING);
        chunkCompileTaskGenerator.getLock().unlock();
        final Entity renderViewEntity = Minecraft.getMinecraft().getRenderViewEntity();
        if (renderViewEntity == null) {
            chunkCompileTaskGenerator.finish();
        }
        else {
            chunkCompileTaskGenerator.setRegionRenderCacheBuilder(this.getRegionRenderCacheBuilder());
            final float n = (float)renderViewEntity.posX;
            final float n2 = (float)renderViewEntity.posY + renderViewEntity.getEyeHeight();
            final float n3 = (float)renderViewEntity.posZ;
            final ChunkCompileTaskGenerator.Type type = chunkCompileTaskGenerator.getType();
            if (type == ChunkCompileTaskGenerator.Type.REBUILD_CHUNK) {
                chunkCompileTaskGenerator.getRenderChunk().rebuildChunk(n, n2, n3, chunkCompileTaskGenerator);
            }
            else if (type == ChunkCompileTaskGenerator.Type.RESORT_TRANSPARENCY) {
                chunkCompileTaskGenerator.getRenderChunk().resortTransparency(n, n2, n3, chunkCompileTaskGenerator);
            }
            chunkCompileTaskGenerator.getLock().lock();
            if (chunkCompileTaskGenerator.getStatus() != ChunkCompileTaskGenerator.Status.COMPILING) {
                if (!chunkCompileTaskGenerator.isFinished()) {
                    ChunkRenderWorker.LOGGER.warn("Chunk render task was " + chunkCompileTaskGenerator.getStatus() + " when I expected it to be compiling; aborting task");
                }
                this.freeRenderBuilder(chunkCompileTaskGenerator);
                chunkCompileTaskGenerator.getLock().unlock();
                return;
            }
            chunkCompileTaskGenerator.setStatus(ChunkCompileTaskGenerator.Status.UPLOADING);
            chunkCompileTaskGenerator.getLock().unlock();
            final CompiledChunk compiledChunk = chunkCompileTaskGenerator.getCompiledChunk();
            final ArrayList arrayList = Lists.newArrayList();
            if (type == ChunkCompileTaskGenerator.Type.REBUILD_CHUNK) {
                final EnumWorldBlockLayer[] values = EnumWorldBlockLayer.values();
                while (0 < values.length) {
                    final EnumWorldBlockLayer enumWorldBlockLayer = values[0];
                    if (compiledChunk.isLayerStarted(enumWorldBlockLayer)) {
                        arrayList.add(this.chunkRenderDispatcher.uploadChunk(enumWorldBlockLayer, chunkCompileTaskGenerator.getRegionRenderCacheBuilder().getWorldRendererByLayer(enumWorldBlockLayer), chunkCompileTaskGenerator.getRenderChunk(), compiledChunk));
                    }
                    int n4 = 0;
                    ++n4;
                }
            }
            else if (type == ChunkCompileTaskGenerator.Type.RESORT_TRANSPARENCY) {
                arrayList.add(this.chunkRenderDispatcher.uploadChunk(EnumWorldBlockLayer.TRANSLUCENT, chunkCompileTaskGenerator.getRegionRenderCacheBuilder().getWorldRendererByLayer(EnumWorldBlockLayer.TRANSLUCENT), chunkCompileTaskGenerator.getRenderChunk(), compiledChunk));
            }
            final ListenableFuture allAsList = Futures.allAsList((Iterable)arrayList);
            chunkCompileTaskGenerator.addFinishRunnable(new Runnable(this, allAsList) {
                final ListenableFuture val$listenablefuture;
                final ChunkRenderWorker this$0;
                
                @Override
                public void run() {
                    this.val$listenablefuture.cancel(false);
                }
            });
            Futures.addCallback(allAsList, (FutureCallback)new FutureCallback(this, chunkCompileTaskGenerator, compiledChunk) {
                final CompiledChunk val$lvt_7_1_;
                final ChunkRenderWorker this$0;
                final ChunkCompileTaskGenerator val$generator;
                
                public void onSuccess(final Object o) {
                    this.onSuccess((List)o);
                }
                
                public void onSuccess(final List list) {
                    ChunkRenderWorker.access$000(this.this$0, this.val$generator);
                    this.val$generator.getLock().lock();
                    if (this.val$generator.getStatus() == ChunkCompileTaskGenerator.Status.UPLOADING) {
                        this.val$generator.setStatus(ChunkCompileTaskGenerator.Status.DONE);
                        this.val$generator.getLock().unlock();
                        this.val$generator.getRenderChunk().setCompiledChunk(this.val$lvt_7_1_);
                        return;
                    }
                    if (!this.val$generator.isFinished()) {
                        ChunkRenderWorker.access$100().warn("Chunk render task was " + this.val$generator.getStatus() + " when I expected it to be uploading; aborting task");
                    }
                    this.val$generator.getLock().unlock();
                }
                
                public void onFailure(final Throwable t) {
                    ChunkRenderWorker.access$000(this.this$0, this.val$generator);
                    if (!(t instanceof CancellationException) && !(t instanceof InterruptedException)) {
                        Minecraft.getMinecraft().crashed(CrashReport.makeCrashReport(t, "Rendering chunk"));
                    }
                }
            });
        }
    }
    
    public ChunkRenderWorker(final ChunkRenderDispatcher chunkRenderDispatcher, final RegionRenderCacheBuilder regionRenderCacheBuilder) {
        this.chunkRenderDispatcher = chunkRenderDispatcher;
        this.regionRenderCacheBuilder = regionRenderCacheBuilder;
    }
}
