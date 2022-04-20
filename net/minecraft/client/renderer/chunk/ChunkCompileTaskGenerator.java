package net.minecraft.client.renderer.chunk;

import java.util.concurrent.locks.*;
import net.minecraft.client.renderer.*;
import java.util.*;
import com.google.common.collect.*;

public class ChunkCompileTaskGenerator
{
    private final ReentrantLock lock;
    private Status status;
    private boolean finished;
    private RegionRenderCacheBuilder regionRenderCacheBuilder;
    private final List listFinishRunnables;
    private final Type type;
    private final RenderChunk renderChunk;
    private CompiledChunk compiledChunk;
    
    public RenderChunk getRenderChunk() {
        return this.renderChunk;
    }
    
    public Status getStatus() {
        return this.status;
    }
    
    public void finish() {
        this.lock.lock();
        if (this.type == Type.REBUILD_CHUNK && this.status != Status.DONE) {
            this.renderChunk.setNeedsUpdate(true);
        }
        this.finished = true;
        this.status = Status.DONE;
        final Iterator<Runnable> iterator = this.listFinishRunnables.iterator();
        while (iterator.hasNext()) {
            iterator.next().run();
        }
        this.lock.unlock();
    }
    
    public ChunkCompileTaskGenerator(final RenderChunk renderChunk, final Type type) {
        this.lock = new ReentrantLock();
        this.listFinishRunnables = Lists.newArrayList();
        this.status = Status.PENDING;
        this.renderChunk = renderChunk;
        this.type = type;
    }
    
    public void setStatus(final Status status) {
        this.lock.lock();
        this.status = status;
        this.lock.unlock();
    }
    
    public Type getType() {
        return this.type;
    }
    
    public void addFinishRunnable(final Runnable runnable) {
        this.lock.lock();
        this.listFinishRunnables.add(runnable);
        if (this.finished) {
            runnable.run();
        }
        this.lock.unlock();
    }
    
    public CompiledChunk getCompiledChunk() {
        return this.compiledChunk;
    }
    
    public void setRegionRenderCacheBuilder(final RegionRenderCacheBuilder regionRenderCacheBuilder) {
        this.regionRenderCacheBuilder = regionRenderCacheBuilder;
    }
    
    public RegionRenderCacheBuilder getRegionRenderCacheBuilder() {
        return this.regionRenderCacheBuilder;
    }
    
    public ReentrantLock getLock() {
        return this.lock;
    }
    
    public boolean isFinished() {
        return this.finished;
    }
    
    public void setCompiledChunk(final CompiledChunk compiledChunk) {
        this.compiledChunk = compiledChunk;
    }
    
    public enum Type
    {
        private static final Type[] $VALUES;
        
        REBUILD_CHUNK("REBUILD_CHUNK", 0), 
        RESORT_TRANSPARENCY("RESORT_TRANSPARENCY", 1);
        
        private Type(final String s, final int n) {
        }
        
        static {
            $VALUES = new Type[] { Type.REBUILD_CHUNK, Type.RESORT_TRANSPARENCY };
        }
    }
    
    public enum Status
    {
        UPLOADING("UPLOADING", 2), 
        DONE("DONE", 3), 
        COMPILING("COMPILING", 1), 
        PENDING("PENDING", 0);
        
        private static final Status[] $VALUES;
        
        static {
            $VALUES = new Status[] { Status.PENDING, Status.COMPILING, Status.UPLOADING, Status.DONE };
        }
        
        private Status(final String s, final int n) {
        }
    }
}
