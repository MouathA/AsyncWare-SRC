package viamcp;

import java.io.*;
import io.netty.channel.*;
import java.util.logging.*;
import org.apache.logging.log4j.*;
import viamcp.utils.*;
import com.google.common.util.concurrent.*;
import io.netty.channel.local.*;
import com.viaversion.viaversion.*;
import viamcp.platform.*;
import com.viaversion.viaversion.api.platform.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.data.*;
import viamcp.loader.*;
import java.util.concurrent.*;

public class ViaMCP
{
    private File file;
    private String lastServer;
    private int version;
    private final CompletableFuture INIT_FUTURE;
    private static final ViaMCP instance;
    public static final int PROTOCOL_VERSION = 47;
    private EventLoop EVENT_LOOP;
    private final Logger jLogger;
    private ExecutorService ASYNC_EXEC;
    
    public static ViaMCP getInstance() {
        return ViaMCP.instance;
    }
    
    public CompletableFuture getInitFuture() {
        return this.INIT_FUTURE;
    }
    
    public int getVersion() {
        return this.version;
    }
    
    public String getLastServer() {
        return this.lastServer;
    }
    
    public File getFile() {
        return this.file;
    }
    
    public EventLoop getEventLoop() {
        return this.EVENT_LOOP;
    }
    
    public void setLastServer(final String lastServer) {
        this.lastServer = lastServer;
    }
    
    public void setVersion(final int version) {
        this.version = version;
    }
    
    public Logger getjLogger() {
        return this.jLogger;
    }
    
    public ViaMCP() {
        this.jLogger = new JLoggerToLog4j(LogManager.getLogger("ViaMCP"));
        this.INIT_FUTURE = new CompletableFuture();
    }
    
    public void setFile(final File file) {
        this.file = file;
    }
    
    public void start() {
        final ThreadFactory build = new ThreadFactoryBuilder().setDaemon(true).setNameFormat("ViaMCP-%d").build();
        this.ASYNC_EXEC = Executors.newFixedThreadPool(8, build);
        (this.EVENT_LOOP = new LocalEventLoopGroup(1, build).next()).submit((Callable)this.INIT_FUTURE::join);
        this.setVersion(47);
        this.file = new File("ViaMCP");
        if (this.file.mkdir()) {
            this.getjLogger().info("Creating ViaMCP Folder");
        }
        Via.init((ViaManager)ViaManagerImpl.builder().injector((ViaInjector)new VRInjector()).loader((ViaPlatformLoader)new VRProviderLoader()).platform((ViaPlatform)new VRPlatform(this.file)).build());
        MappingDataLoader.enableMappingsCache();
        ((ViaManagerImpl)Via.getManager()).init();
        new VRBackwardsLoader(this.file);
        new VRRewindLoader(this.file);
        this.INIT_FUTURE.complete(null);
    }
    
    public ExecutorService getAsyncExecutor() {
        return this.ASYNC_EXEC;
    }
    
    static {
        instance = new ViaMCP();
    }
}
