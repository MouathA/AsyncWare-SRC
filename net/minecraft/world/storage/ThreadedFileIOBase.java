package net.minecraft.world.storage;

import com.google.common.collect.*;
import java.util.*;

public class ThreadedFileIOBase implements Runnable
{
    private List threadedIOQueue;
    private volatile long writeQueuedCounter;
    private static final ThreadedFileIOBase threadedIOInstance;
    private volatile long savedIOCounter;
    private volatile boolean isThreadWaiting;
    
    static {
        threadedIOInstance = new ThreadedFileIOBase();
    }
    
    public static ThreadedFileIOBase getThreadedIOInstance() {
        return ThreadedFileIOBase.threadedIOInstance;
    }
    
    private void processQueue() {
        while (0 < this.threadedIOQueue.size()) {
            int n2 = 0;
            if (!this.threadedIOQueue.get(0).writeNextIO()) {
                final List threadedIOQueue = this.threadedIOQueue;
                final int n = 0;
                --n2;
                threadedIOQueue.remove(n);
                ++this.savedIOCounter;
            }
            Thread.sleep(this.isThreadWaiting ? 0L : 10L);
            ++n2;
        }
        if (this.threadedIOQueue.isEmpty()) {
            Thread.sleep(25L);
        }
    }
    
    private ThreadedFileIOBase() {
        this.threadedIOQueue = Collections.synchronizedList((List<Object>)Lists.newArrayList());
        final Thread thread = new Thread(this, "File IO Thread");
        thread.setPriority(1);
        thread.start();
    }
    
    public void waitForFinish() throws InterruptedException {
        this.isThreadWaiting = true;
        while (this.writeQueuedCounter != this.savedIOCounter) {
            Thread.sleep(10L);
        }
        this.isThreadWaiting = false;
    }
    
    @Override
    public void run() {
        while (true) {
            this.processQueue();
        }
    }
    
    public void queueIO(final IThreadedFileIO threadedFileIO) {
        if (!this.threadedIOQueue.contains(threadedFileIO)) {
            ++this.writeQueuedCounter;
            this.threadedIOQueue.add(threadedFileIO);
        }
    }
}
