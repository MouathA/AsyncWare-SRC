package net.minecraft.client.renderer.chunk;

import java.util.*;
import java.util.concurrent.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import com.google.common.collect.*;
import net.minecraft.client.renderer.vertex.*;
import org.apache.logging.log4j.*;
import com.google.common.util.concurrent.*;
import org.lwjgl.opengl.*;

public class ChunkRenderDispatcher
{
    private static final Logger logger;
    private final BlockingQueue queueFreeRenderBuilders;
    private final ChunkRenderWorker renderWorker;
    private final Queue queueChunkUploads;
    private final WorldVertexBufferUploader worldVertexUploader;
    private final List listThreadedWorkers;
    private static final ThreadFactory threadFactory;
    private final BlockingQueue queueChunkUpdates;
    private final VertexBufferUploader vertexUploader;
    
    public ListenableFuture uploadChunk(final EnumWorldBlockLayer enumWorldBlockLayer, final WorldRenderer worldRenderer, final RenderChunk renderChunk, final CompiledChunk compiledChunk) {
        if (Minecraft.getMinecraft().isCallingFromMinecraftThread()) {
            if (OpenGlHelper.useVbo()) {
                this.uploadVertexBuffer(worldRenderer, renderChunk.getVertexBufferByLayer(enumWorldBlockLayer.ordinal()));
            }
            else {
                this.uploadDisplayList(worldRenderer, ((ListedRenderChunk)renderChunk).getDisplayList(enumWorldBlockLayer, compiledChunk), renderChunk);
            }
            worldRenderer.setTranslation(0.0, 0.0, 0.0);
            return Futures.immediateFuture((Object)null);
        }
        final ListenableFutureTask create = ListenableFutureTask.create((Runnable)new Runnable(this, enumWorldBlockLayer, worldRenderer, renderChunk, compiledChunk) {
            final EnumWorldBlockLayer val$player;
            final CompiledChunk val$compiledChunkIn;
            final ChunkRenderDispatcher this$0;
            final RenderChunk val$chunkRenderer;
            final WorldRenderer val$p_178503_2_;
            
            @Override
            public void run() {
                this.this$0.uploadChunk(this.val$player, this.val$p_178503_2_, this.val$chunkRenderer, this.val$compiledChunkIn);
            }
        }, (Object)null);
        // monitorenter(queueChunkUploads = this.queueChunkUploads)
        this.queueChunkUploads.add(create);
        final ListenableFutureTask listenableFutureTask = create;
        // monitorexit(queueChunkUploads)
        return (ListenableFuture)listenableFutureTask;
    }
    
    public ChunkCompileTaskGenerator getNextChunkUpdate() throws InterruptedException {
        return this.queueChunkUpdates.take();
    }
    
    public boolean updateChunkNow(final RenderChunk renderChunk) {
        renderChunk.getLockCompileTask().lock();
        this.renderWorker.processTask(renderChunk.makeCompileTaskChunk());
        renderChunk.getLockCompileTask().unlock();
        return true;
    }
    
    public void freeRenderBuilder(final RegionRenderCacheBuilder regionRenderCacheBuilder) {
        this.queueFreeRenderBuilders.add(regionRenderCacheBuilder);
    }
    
    public boolean updateTransparencyLater(final RenderChunk renderChunk) {
        renderChunk.getLockCompileTask().lock();
        final ChunkCompileTaskGenerator compileTaskTransparency = renderChunk.makeCompileTaskTransparency();
        if (compileTaskTransparency == null) {
            renderChunk.getLockCompileTask().unlock();
            return true;
        }
        compileTaskTransparency.addFinishRunnable(new Runnable(this, compileTaskTransparency) {
            final ChunkRenderDispatcher this$0;
            final ChunkCompileTaskGenerator val$chunkcompiletaskgenerator;
            
            @Override
            public void run() {
                ChunkRenderDispatcher.access$000(this.this$0).remove(this.val$chunkcompiletaskgenerator);
            }
        });
        this.queueChunkUpdates.offer(compileTaskTransparency);
        renderChunk.getLockCompileTask().unlock();
        return true;
    }
    
    public void clearChunkUpdates() {
        while (!this.queueChunkUpdates.isEmpty()) {
            final ChunkCompileTaskGenerator chunkCompileTaskGenerator = (ChunkCompileTaskGenerator)this.queueChunkUpdates.poll();
            if (chunkCompileTaskGenerator != null) {
                chunkCompileTaskGenerator.finish();
            }
        }
    }
    
    static BlockingQueue access$000(final ChunkRenderDispatcher chunkRenderDispatcher) {
        return chunkRenderDispatcher.queueChunkUpdates;
    }
    
    public ChunkRenderDispatcher() {
        this.listThreadedWorkers = Lists.newArrayList();
        this.queueChunkUpdates = Queues.newArrayBlockingQueue(100);
        this.queueFreeRenderBuilders = Queues.newArrayBlockingQueue(5);
        this.worldVertexUploader = new WorldVertexBufferUploader();
        this.vertexUploader = new VertexBufferUploader();
        this.queueChunkUploads = Queues.newArrayDeque();
        while (true) {
            final ChunkRenderWorker chunkRenderWorker = new ChunkRenderWorker(this);
            ChunkRenderDispatcher.threadFactory.newThread(chunkRenderWorker).start();
            this.listThreadedWorkers.add(chunkRenderWorker);
            int n = 0;
            ++n;
        }
    }
    
    public void stopChunkUpdates() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   net/minecraft/client/renderer/chunk/ChunkRenderDispatcher.clearChunkUpdates:()V
        //     4: aload_0        
        //     5: lconst_0       
        //     6: ifne            12
        //     9: goto            4
        //    12: invokestatic    com/google/common/collect/Lists.newArrayList:()Ljava/util/ArrayList;
        //    15: astore_1       
        //    16: aload_1        
        //    17: invokeinterface java/util/List.size:()I
        //    22: iconst_5       
        //    23: if_icmpeq       44
        //    26: aload_1        
        //    27: aload_0        
        //    28: invokevirtual   net/minecraft/client/renderer/chunk/ChunkRenderDispatcher.allocateRenderBuilder:()Lnet/minecraft/client/renderer/RegionRenderCacheBuilder;
        //    31: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //    36: pop            
        //    37: goto            16
        //    40: astore_2       
        //    41: goto            16
        //    44: aload_0        
        //    45: getfield        net/minecraft/client/renderer/chunk/ChunkRenderDispatcher.queueFreeRenderBuilders:Ljava/util/concurrent/BlockingQueue;
        //    48: aload_1        
        //    49: invokeinterface java/util/concurrent/BlockingQueue.addAll:(Ljava/util/Collection;)Z
        //    54: pop            
        //    55: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0004 (coming from #0009).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public RegionRenderCacheBuilder allocateRenderBuilder() throws InterruptedException {
        return this.queueFreeRenderBuilders.take();
    }
    
    private void uploadVertexBuffer(final WorldRenderer worldRenderer, final VertexBuffer vertexBuffer) {
        this.vertexUploader.setVertexBuffer(vertexBuffer);
        this.vertexUploader.func_181679_a(worldRenderer);
    }
    
    static {
        logger = LogManager.getLogger();
        threadFactory = new ThreadFactoryBuilder().setNameFormat("Chunk Batcher %d").setDaemon(true).build();
    }
    
    public boolean updateChunkLater(final RenderChunk renderChunk) {
        renderChunk.getLockCompileTask().lock();
        final ChunkCompileTaskGenerator compileTaskChunk = renderChunk.makeCompileTaskChunk();
        compileTaskChunk.addFinishRunnable(new Runnable(this, compileTaskChunk) {
            final ChunkCompileTaskGenerator val$chunkcompiletaskgenerator;
            final ChunkRenderDispatcher this$0;
            
            @Override
            public void run() {
                ChunkRenderDispatcher.access$000(this.this$0).remove(this.val$chunkcompiletaskgenerator);
            }
        });
        final boolean offer = this.queueChunkUpdates.offer(compileTaskChunk);
        if (!offer) {
            compileTaskChunk.finish();
        }
        final boolean b = offer;
        renderChunk.getLockCompileTask().unlock();
        return b;
    }
    
    public String getDebugInfo() {
        return String.format("pC: %03d, pU: %1d, aB: %1d", this.queueChunkUpdates.size(), this.queueChunkUploads.size(), this.queueFreeRenderBuilders.size());
    }
    
    private void uploadDisplayList(final WorldRenderer worldRenderer, final int n, final RenderChunk renderChunk) {
        GL11.glNewList(n, 4864);
        renderChunk.multModelviewMatrix();
        this.worldVertexUploader.func_181679_a(worldRenderer);
        GL11.glEndList();
    }
}
