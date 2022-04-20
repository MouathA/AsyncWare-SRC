package viamcp.platform;

import java.util.logging.*;
import java.io.*;
import com.viaversion.viaversion.api.*;
import io.netty.util.concurrent.*;
import java.util.*;
import com.viaversion.viaversion.libs.gson.*;
import viamcp.*;
import com.viaversion.viaversion.api.platform.*;
import com.viaversion.viaversion.api.command.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import java.util.concurrent.*;
import java.util.function.*;
import org.apache.logging.log4j.*;
import viamcp.utils.*;
import java.nio.file.*;
import com.viaversion.viaversion.api.configuration.*;

public class VRPlatform implements ViaPlatform
{
    private final Logger logger;
    private final File dataFolder;
    private final ViaAPI api;
    private final VRViaConfig config;
    
    private GenericFutureListener errorLogger() {
        return VRPlatform::lambda$errorLogger$3;
    }
    
    public void sendMessage(final UUID uuid, final String s) {
    }
    
    public String getPluginVersion() {
        return "4.0.0";
    }
    
    public JsonObject getDump() {
        return new JsonObject();
    }
    
    public FutureTaskId runSync(final Runnable runnable) {
        return new FutureTaskId((Future)ViaMCP.getInstance().getEventLoop().submit(runnable).addListener(this.errorLogger()));
    }
    
    public boolean isOldClientsAllowed() {
        return true;
    }
    
    private static void lambda$errorLogger$3(final io.netty.util.concurrent.Future future) throws Exception {
        if (!future.isCancelled() && future.cause() != null) {
            future.cause().printStackTrace();
        }
    }
    
    public boolean isPluginEnabled() {
        return true;
    }
    
    public void onReload() {
    }
    
    private FutureTaskId lambda$runSync$1(final Runnable runnable) throws Exception {
        return this.runSync(runnable);
    }
    
    public boolean kickPlayer(final UUID uuid, final String s) {
        return false;
    }
    
    public PlatformTask runSync(final Runnable runnable, final long n) {
        return (PlatformTask)new FutureTaskId((Future)ViaMCP.getInstance().getEventLoop().schedule((Callable)this::lambda$runSync$1, n * 50L, TimeUnit.MILLISECONDS).addListener(this.errorLogger()));
    }
    
    public PlatformTask runRepeatingSync(final Runnable runnable, final long n) {
        return (PlatformTask)new FutureTaskId((Future)ViaMCP.getInstance().getEventLoop().scheduleAtFixedRate(this::lambda$runRepeatingSync$2, 0L, n * 50L, TimeUnit.MILLISECONDS).addListener(this.errorLogger()));
    }
    
    public ViaVersionConfig getConf() {
        return (ViaVersionConfig)this.config;
    }
    
    public File getDataFolder() {
        return this.dataFolder;
    }
    
    public Logger getLogger() {
        return this.logger;
    }
    
    private ViaCommandSender[] getServerPlayers() {
        return new ViaCommandSender[1337];
    }
    
    public static String legacyToJson(final String s) {
        return (String)GsonComponentSerializer.gson().serialize((Component)LegacyComponentSerializer.legacySection().deserialize(s));
    }
    
    private static Void lambda$runAsync$0(final Throwable t) {
        if (!(t instanceof CancellationException)) {
            t.printStackTrace();
        }
        return null;
    }
    
    private void lambda$runRepeatingSync$2(final Runnable runnable) {
        this.runSync(runnable);
    }
    
    public PlatformTask runSync(final Runnable runnable) {
        return (PlatformTask)this.runSync(runnable);
    }
    
    public FutureTaskId runAsync(final Runnable runnable) {
        return new FutureTaskId(CompletableFuture.runAsync(runnable, ViaMCP.getInstance().getAsyncExecutor()).exceptionally((Function<Throwable, ? extends Void>)VRPlatform::lambda$runAsync$0));
    }
    
    public VRPlatform(final File file) {
        this.logger = new JLoggerToLog4j(LogManager.getLogger("ViaVersion"));
        final Path resolve = file.toPath().resolve("ViaVersion");
        this.config = new VRViaConfig(resolve.resolve("viaversion.yml").toFile());
        this.dataFolder = resolve.toFile();
        this.api = (ViaAPI)new VRViaAPI();
    }
    
    public ViaAPI getApi() {
        return this.api;
    }
    
    public ConfigurationProvider getConfigurationProvider() {
        return (ConfigurationProvider)this.config;
    }
    
    public String getPlatformName() {
        return "ViaForge";
    }
    
    public ViaCommandSender[] getOnlinePlayers() {
        return new ViaCommandSender[1337];
    }
    
    public String getPlatformVersion() {
        return String.valueOf(47);
    }
    
    public PlatformTask runAsync(final Runnable runnable) {
        return (PlatformTask)this.runAsync(runnable);
    }
}
