package net.minecraft.server;

import java.security.*;
import net.minecraft.network.*;
import java.net.*;
import com.mojang.authlib.minecraft.*;
import com.mojang.authlib.yggdrasil.*;
import net.minecraft.server.management.*;
import net.minecraft.profiler.*;
import com.google.common.collect.*;
import net.minecraft.world.chunk.storage.*;
import com.google.common.util.concurrent.*;
import org.apache.commons.lang3.*;
import java.util.concurrent.*;
import net.minecraft.crash.*;
import com.mojang.authlib.*;
import java.awt.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import org.apache.logging.log4j.*;
import javax.imageio.*;
import io.netty.handler.codec.base64.*;
import com.google.common.base.*;
import io.netty.buffer.*;
import java.awt.image.*;
import net.minecraft.command.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.world.storage.*;
import java.io.*;

public abstract class MinecraftServer implements ICommandSender, IPlayerUsage, IThreadListener, Runnable
{
    private String userMessage;
    private boolean isDemo;
    private boolean canSpawnNPCs;
    private boolean allowFlight;
    private final PlayerProfileCache profileCache;
    private String serverOwner;
    private KeyPair serverKeyPair;
    private final PlayerUsageSnooper usageSnooper;
    private static final Logger logger;
    private final List playersOnline;
    private int serverPort;
    private final Random random;
    public String currentTask;
    private boolean startProfiling;
    private boolean onlineMode;
    private final ServerStatusResponse statusResponse;
    protected final ICommandManager commandManager;
    private boolean pvpEnabled;
    private int buildLimit;
    private boolean serverStopped;
    public long[][] timeOfLastDimensionTick;
    private final NetworkSystem networkSystem;
    private boolean serverRunning;
    private int maxPlayerIdleMinutes;
    private boolean serverIsRunning;
    public int percentDone;
    private boolean enableBonusChest;
    private final ISaveFormat anvilConverterForAnvilFile;
    protected final Queue futureTaskQueue;
    public final long[] tickTimeArray;
    private String folderName;
    private final GameProfileRepository profileRepo;
    private final File anvilFile;
    protected final Proxy serverProxy;
    private int tickCounter;
    private Thread serverThread;
    private boolean isGamemodeForced;
    private boolean worldIsBeingDeleted;
    private String resourcePackUrl;
    private String resourcePackHash;
    private final MinecraftSessionService sessionService;
    private long timeOfLastWarning;
    private static MinecraftServer mcServer;
    private long nanoTimeSinceStatusRefresh;
    private final YggdrasilAuthenticationService authService;
    private String motd;
    public static final File USER_CACHE_FILE;
    private ServerConfigurationManager serverConfigManager;
    public WorldServer[] worldServers;
    private long currentTime;
    private String worldName;
    public final Profiler theProfiler;
    private boolean canSpawnAnimals;
    
    public synchronized String getUserMessage() {
        return this.userMessage;
    }
    
    public boolean isDemo() {
        return this.isDemo;
    }
    
    public void setResourcePack(final String resourcePackUrl, final String resourcePackHash) {
        this.resourcePackUrl = resourcePackUrl;
        this.resourcePackHash = resourcePackHash;
    }
    
    public KeyPair getKeyPair() {
        return this.serverKeyPair;
    }
    
    public File getDataDirectory() {
        return new File(".");
    }
    
    public MinecraftServer(final File anvilFile, final Proxy serverProxy, final File file) {
        this.usageSnooper = new PlayerUsageSnooper("server", this, getCurrentTimeMillis());
        this.playersOnline = Lists.newArrayList();
        this.theProfiler = new Profiler();
        this.statusResponse = new ServerStatusResponse();
        this.random = new Random();
        this.serverPort = -1;
        this.serverRunning = true;
        this.maxPlayerIdleMinutes = 0;
        this.tickTimeArray = new long[100];
        this.resourcePackUrl = "";
        this.resourcePackHash = "";
        this.nanoTimeSinceStatusRefresh = 0L;
        this.futureTaskQueue = Queues.newArrayDeque();
        this.currentTime = getCurrentTimeMillis();
        this.serverProxy = serverProxy;
        MinecraftServer.mcServer = this;
        this.anvilFile = anvilFile;
        this.networkSystem = new NetworkSystem(this);
        this.profileCache = new PlayerProfileCache(this, file);
        this.commandManager = this.createNewCommandManager();
        this.anvilConverterForAnvilFile = new AnvilSaveConverter(anvilFile);
        this.authService = new YggdrasilAuthenticationService(serverProxy, UUID.randomUUID().toString());
        this.sessionService = this.authService.createMinecraftSessionService();
        this.profileRepo = this.authService.createProfileRepository();
    }
    
    public ListenableFuture callFromMainThread(final Callable p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokestatic    org/apache/commons/lang3/Validate.notNull:(Ljava/lang/Object;)Ljava/lang/Object;
        //     4: pop            
        //     5: aload_0        
        //     6: if_acmpne       50
        //     9: aload_0        
        //    10: invokevirtual   net/minecraft/server/MinecraftServer.isServerStopped:()Z
        //    13: ifne            50
        //    16: aload_1        
        //    17: invokestatic    com/google/common/util/concurrent/ListenableFutureTask.create:(Ljava/util/concurrent/Callable;)Lcom/google/common/util/concurrent/ListenableFutureTask;
        //    20: astore_2       
        //    21: aload_0        
        //    22: getfield        net/minecraft/server/MinecraftServer.futureTaskQueue:Ljava/util/Queue;
        //    25: dup            
        //    26: astore_3       
        //    27: monitorenter   
        //    28: aload_0        
        //    29: getfield        net/minecraft/server/MinecraftServer.futureTaskQueue:Ljava/util/Queue;
        //    32: aload_2        
        //    33: invokeinterface java/util/Queue.add:(Ljava/lang/Object;)Z
        //    38: pop            
        //    39: aload_2        
        //    40: aload_3        
        //    41: monitorexit    
        //    42: areturn        
        //    43: astore          4
        //    45: aload_3        
        //    46: monitorexit    
        //    47: aload           4
        //    49: athrow         
        //    50: aload_1        
        //    51: invokeinterface java/util/concurrent/Callable.call:()Ljava/lang/Object;
        //    56: invokestatic    com/google/common/util/concurrent/Futures.immediateFuture:(Ljava/lang/Object;)Lcom/google/common/util/concurrent/ListenableFuture;
        //    59: areturn        
        //    60: astore_2       
        //    61: aload_2        
        //    62: invokestatic    com/google/common/util/concurrent/Futures.immediateFailedCheckedFuture:(Ljava/lang/Exception;)Lcom/google/common/util/concurrent/CheckedFuture;
        //    65: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public abstract EnumDifficulty getDifficulty();
    
    @Override
    public BlockPos getPosition() {
        return BlockPos.ORIGIN;
    }
    
    @Override
    public ListenableFuture addScheduledTask(final Runnable runnable) {
        Validate.notNull((Object)runnable);
        return this.callFromMainThread(Executors.callable(runnable));
    }
    
    public void setDemo(final boolean isDemo) {
        this.isDemo = isDemo;
    }
    
    public CrashReport addServerInfoToCrashReport(final CrashReport crashReport) {
        crashReport.getCategory().addCrashSectionCallable("Profiler Position", new Callable(this) {
            final MinecraftServer this$0;
            
            @Override
            public String call() throws Exception {
                return this.this$0.theProfiler.profilingEnabled ? this.this$0.theProfiler.getNameOfLastSection() : "N/A (disabled)";
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
        });
        if (this.serverConfigManager != null) {
            crashReport.getCategory().addCrashSectionCallable("Player Count", new Callable(this) {
                final MinecraftServer this$0;
                
                @Override
                public Object call() throws Exception {
                    return this.call();
                }
                
                @Override
                public String call() {
                    return MinecraftServer.access$100(this.this$0).getCurrentPlayerCount() + " / " + MinecraftServer.access$100(this.this$0).getMaxPlayers() + "; " + MinecraftServer.access$100(this.this$0).func_181057_v();
                }
            });
        }
        return crashReport;
    }
    
    public String getServerModName() {
        return "vanilla";
    }
    
    public ICommandManager getCommandManager() {
        return this.commandManager;
    }
    
    public void setAllowPvp(final boolean pvpEnabled) {
        this.pvpEnabled = pvpEnabled;
    }
    
    public ServerConfigurationManager getConfigurationManager() {
        return this.serverConfigManager;
    }
    
    public GameProfile[] getGameProfiles() {
        return this.serverConfigManager.getAllProfiles();
    }
    
    public boolean isBlockProtected(final World world, final BlockPos blockPos, final EntityPlayer entityPlayer) {
        return false;
    }
    
    @Override
    public void setCommandStat(final CommandResultStats.Type type, final int n) {
    }
    
    public abstract boolean func_181034_q();
    
    public void startServerThread() {
        (this.serverThread = new Thread(this, "Server thread")).start();
    }
    
    public void logWarning(final String s) {
        MinecraftServer.logger.warn(s);
    }
    
    @Override
    public World getEntityWorld() {
        return this.worldServers[0];
    }
    
    @Override
    public Vec3 getPositionVector() {
        return new Vec3(0.0, 0.0, 0.0);
    }
    
    public MinecraftServer(final Proxy serverProxy, final File file) {
        this.usageSnooper = new PlayerUsageSnooper("server", this, getCurrentTimeMillis());
        this.playersOnline = Lists.newArrayList();
        this.theProfiler = new Profiler();
        this.statusResponse = new ServerStatusResponse();
        this.random = new Random();
        this.serverPort = -1;
        this.serverRunning = true;
        this.maxPlayerIdleMinutes = 0;
        this.tickTimeArray = new long[100];
        this.resourcePackUrl = "";
        this.resourcePackHash = "";
        this.nanoTimeSinceStatusRefresh = 0L;
        this.futureTaskQueue = Queues.newArrayDeque();
        this.currentTime = getCurrentTimeMillis();
        this.serverProxy = serverProxy;
        MinecraftServer.mcServer = this;
        this.anvilFile = null;
        this.networkSystem = null;
        this.profileCache = new PlayerProfileCache(this, file);
        this.commandManager = null;
        this.anvilConverterForAnvilFile = null;
        this.authService = new YggdrasilAuthenticationService(serverProxy, UUID.randomUUID().toString());
        this.sessionService = this.authService.createMinecraftSessionService();
        this.profileRepo = this.authService.createProfileRepository();
    }
    
    public void setAllowFlight(final boolean allowFlight) {
        this.allowFlight = allowFlight;
    }
    
    public boolean isServerRunning() {
        return this.serverRunning;
    }
    
    protected void setInstance() {
        MinecraftServer.mcServer = this;
    }
    
    @Override
    public void run() {
        if (this.startServer()) {
            this.currentTime = getCurrentTimeMillis();
            long n = 0L;
            this.statusResponse.setServerDescription(new ChatComponentText(this.motd));
            this.statusResponse.setProtocolVersionInfo(new ServerStatusResponse.MinecraftProtocolVersionIdentifier("1.8.8", 47));
            this.addFaviconToStatusResponse(this.statusResponse);
            while (this.serverRunning) {
                final long currentTimeMillis = getCurrentTimeMillis();
                long n2 = currentTimeMillis - this.currentTime;
                if (n2 > 2000L && this.currentTime - this.timeOfLastWarning >= 15000L) {
                    MinecraftServer.logger.warn("Can't keep up! Did the system time change, or is the server overloaded? Running {}ms behind, skipping {} tick(s)", new Object[] { n2, n2 / 50L });
                    n2 = 2000L;
                    this.timeOfLastWarning = this.currentTime;
                }
                if (n2 < 0L) {
                    MinecraftServer.logger.warn("Time ran backwards! Did the system time change?");
                    n2 = 0L;
                }
                n += n2;
                this.currentTime = currentTimeMillis;
                if (this.worldServers[0].areAllPlayersAsleep()) {
                    this.tick();
                    n = 0L;
                }
                else {
                    while (n > 50L) {
                        n -= 50L;
                        this.tick();
                    }
                }
                Thread.sleep(Math.max(1L, 50L - n));
                this.serverIsRunning = true;
            }
        }
        else {
            this.finalTick(null);
        }
        this.serverStopped = true;
        this.stopServer();
        this.systemExitNow();
    }
    
    public void setServerOwner(final String serverOwner) {
        this.serverOwner = serverOwner;
    }
    
    protected void outputPercentRemaining(final String currentTask, final int percentDone) {
        this.currentTask = currentTask;
        this.percentDone = percentDone;
        MinecraftServer.logger.info(currentTask + ": " + percentDone + "%");
    }
    
    public String getResourcePackHash() {
        return this.resourcePackHash;
    }
    
    public boolean isFlightAllowed() {
        return this.allowFlight;
    }
    
    public boolean isServerInOnlineMode() {
        return this.onlineMode;
    }
    
    public abstract boolean isHardcore();
    
    public void setDifficultyForAllWorlds(final EnumDifficulty enumDifficulty) {
        while (0 < this.worldServers.length) {
            final WorldServer worldServer = this.worldServers[0];
            if (worldServer != null) {
                if (worldServer.getWorldInfo().isHardcoreModeEnabled()) {
                    worldServer.getWorldInfo().setDifficulty(EnumDifficulty.HARD);
                    worldServer.setAllowedSpawnTypes(true, true);
                }
                else if (this != null) {
                    worldServer.getWorldInfo().setDifficulty(enumDifficulty);
                    worldServer.setAllowedSpawnTypes(worldServer.getDifficulty() != EnumDifficulty.PEACEFUL, true);
                }
                else {
                    worldServer.getWorldInfo().setDifficulty(enumDifficulty);
                    worldServer.setAllowedSpawnTypes(this.allowSpawnMonsters(), this.canSpawnAnimals);
                }
            }
            int n = 0;
            ++n;
        }
    }
    
    public String getWorldName() {
        return this.worldName;
    }
    
    public String[] getAllUsernames() {
        return this.serverConfigManager.getAllUsernames();
    }
    
    @Override
    public boolean isSnooperEnabled() {
        return true;
    }
    
    protected void saveAllWorlds(final boolean b) {
        if (!this.worldIsBeingDeleted) {
            final WorldServer[] worldServers = this.worldServers;
            while (0 < worldServers.length) {
                final WorldServer worldServer = worldServers[0];
                if (worldServer != null) {
                    if (!b) {
                        MinecraftServer.logger.info("Saving chunks for level '" + worldServer.getWorldInfo().getWorldName() + "'/" + worldServer.provider.getDimensionName());
                    }
                    worldServer.saveAllChunks(true, null);
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    public void setFolderName(final String folderName) {
        this.folderName = folderName;
    }
    
    public void enableProfiling() {
        this.startProfiling = true;
    }
    
    public void setKeyPair(final KeyPair serverKeyPair) {
        this.serverKeyPair = serverKeyPair;
    }
    
    public File getFile(final String s) {
        return new File(this.getDataDirectory(), s);
    }
    
    public boolean isServerStopped() {
        return this.serverStopped;
    }
    
    @Override
    public void addServerTypeToSnooper(final PlayerUsageSnooper playerUsageSnooper) {
        playerUsageSnooper.addStatToSnooper("singleplayer", this.isSinglePlayer());
        playerUsageSnooper.addStatToSnooper("server_brand", this.getServerModName());
        playerUsageSnooper.addStatToSnooper("gui_supported", GraphicsEnvironment.isHeadless() ? "headless" : "supported");
        playerUsageSnooper.addStatToSnooper("dedicated", this.isDedicatedServer());
    }
    
    public void canCreateBonusChest(final boolean enableBonusChest) {
        this.enableBonusChest = enableBonusChest;
    }
    
    public int getMaxWorldSize() {
        return 29999984;
    }
    
    public abstract boolean func_181035_ah();
    
    public String getMinecraftVersion() {
        return "1.8.8";
    }
    
    public PlayerUsageSnooper getPlayerUsageSnooper() {
        return this.usageSnooper;
    }
    
    static Logger access$000() {
        return MinecraftServer.logger;
    }
    
    protected boolean allowSpawnMonsters() {
        return true;
    }
    
    public abstract boolean isDedicatedServer();
    
    public void setPlayerIdleTimeout(final int maxPlayerIdleMinutes) {
        this.maxPlayerIdleMinutes = maxPlayerIdleMinutes;
    }
    
    public void setGameType(final WorldSettings.GameType gameType) {
        while (0 < this.worldServers.length) {
            getServer().worldServers[0].getWorldInfo().setGameType(gameType);
            int n = 0;
            ++n;
        }
    }
    
    public Proxy getServerProxy() {
        return this.serverProxy;
    }
    
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }
    
    public int getSpawnProtectionSize() {
        return 16;
    }
    
    public WorldServer worldServerForDimension(final int n) {
        return (n == -1) ? this.worldServers[1] : ((n == 1) ? this.worldServers[2] : this.worldServers[0]);
    }
    
    public String getFolderName() {
        return this.folderName;
    }
    
    public void tick() {
        final long nanoTime = System.nanoTime();
        ++this.tickCounter;
        if (this.startProfiling) {
            this.startProfiling = false;
            this.theProfiler.profilingEnabled = true;
            this.theProfiler.clearProfiling();
        }
        this.theProfiler.startSection("root");
        this.updateTimeLightAndEntities();
        if (nanoTime - this.nanoTimeSinceStatusRefresh >= 5000000000L) {
            this.nanoTimeSinceStatusRefresh = nanoTime;
            this.statusResponse.setPlayerCountData(new ServerStatusResponse.PlayerCountData(this.getMaxPlayers(), this.getCurrentPlayerCount()));
            final GameProfile[] players = new GameProfile[Math.min(this.getCurrentPlayerCount(), 12)];
            final int randomIntegerInRange = MathHelper.getRandomIntegerInRange(this.random, 0, this.getCurrentPlayerCount() - players.length);
            while (0 < players.length) {
                players[0] = ((EntityPlayerMP)this.serverConfigManager.func_181057_v().get(randomIntegerInRange + 0)).getGameProfile();
                int n = 0;
                ++n;
            }
            Collections.shuffle(Arrays.asList(players));
            this.statusResponse.getPlayerCountData().setPlayers(players);
        }
        if (this.tickCounter % 900 == 0) {
            this.theProfiler.startSection("save");
            this.serverConfigManager.saveAllPlayerData();
            this.saveAllWorlds(true);
            this.theProfiler.endSection();
        }
        this.theProfiler.startSection("tallying");
        this.tickTimeArray[this.tickCounter % 100] = System.nanoTime() - nanoTime;
        this.theProfiler.endSection();
        this.theProfiler.startSection("snooper");
        if (!this.usageSnooper.isSnooperRunning() && this.tickCounter > 100) {
            this.usageSnooper.startSnooper();
        }
        if (this.tickCounter % 6000 == 0) {
            this.usageSnooper.addMemoryStatsToSnooper();
        }
        this.theProfiler.endSection();
        this.theProfiler.endSection();
    }
    
    public int getBuildLimit() {
        return this.buildLimit;
    }
    
    public boolean isPVPEnabled() {
        return this.pvpEnabled;
    }
    
    public void setBuildLimit(final int buildLimit) {
        this.buildLimit = buildLimit;
    }
    
    public void setOnlineMode(final boolean onlineMode) {
        this.onlineMode = onlineMode;
    }
    
    public ServerStatusResponse getServerStatusResponse() {
        return this.statusResponse;
    }
    
    @Override
    public IChatComponent getDisplayName() {
        return new ChatComponentText(this.getName());
    }
    
    public abstract boolean isCommandBlockEnabled();
    
    public abstract String shareToLAN(final WorldSettings.GameType p0, final boolean p1);
    
    public abstract WorldSettings.GameType getGameType();
    
    public void setWorldName(final String worldName) {
        this.worldName = worldName;
    }
    
    static {
        logger = LogManager.getLogger();
        USER_CACHE_FILE = new File("usercache.json");
    }
    
    private void addFaviconToStatusResponse(final ServerStatusResponse serverStatusResponse) {
        final File file = this.getFile("server-icon.png");
        if (file.isFile()) {
            final ByteBuf buffer = Unpooled.buffer();
            final BufferedImage read = ImageIO.read(file);
            Validate.validState(read.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
            Validate.validState(read.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
            ImageIO.write(read, "PNG", (OutputStream)new ByteBufOutputStream(buffer));
            serverStatusResponse.setFavicon("data:image/png;base64," + Base64.encode(buffer).toString(Charsets.UTF_8));
            buffer.release();
        }
    }
    
    public static MinecraftServer getServer() {
        return MinecraftServer.mcServer;
    }
    
    public void setCanSpawnAnimals(final boolean canSpawnAnimals) {
        this.canSpawnAnimals = canSpawnAnimals;
    }
    
    public void setConfigManager(final ServerConfigurationManager serverConfigManager) {
        this.serverConfigManager = serverConfigManager;
    }
    
    public void stopServer() {
        if (!this.worldIsBeingDeleted) {
            MinecraftServer.logger.info("Stopping server");
            if (this.getNetworkSystem() != null) {
                this.getNetworkSystem().terminateEndpoints();
            }
            if (this.serverConfigManager != null) {
                MinecraftServer.logger.info("Saving players");
                this.serverConfigManager.saveAllPlayerData();
                this.serverConfigManager.removeAllPlayers();
            }
            if (this.worldServers != null) {
                MinecraftServer.logger.info("Saving worlds");
                this.saveAllWorlds(false);
                while (0 < this.worldServers.length) {
                    this.worldServers[0].flush();
                    int n = 0;
                    ++n;
                }
            }
            if (this.usageSnooper.isSnooperRunning()) {
                this.usageSnooper.stopSnooper();
            }
        }
    }
    
    public abstract boolean func_183002_r();
    
    public int getMaxPlayers() {
        return this.serverConfigManager.getMaxPlayers();
    }
    
    protected ServerCommandManager createNewCommandManager() {
        return new ServerCommandManager();
    }
    
    @Override
    public boolean sendCommandFeedback() {
        return getServer().worldServers[0].getGameRules().getBoolean("sendCommandFeedback");
    }
    
    public int getCurrentPlayerCount() {
        return this.serverConfigManager.getCurrentPlayerCount();
    }
    
    public boolean getGuiEnabled() {
        return false;
    }
    
    public void updateTimeLightAndEntities() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/server/MinecraftServer.theProfiler:Lnet/minecraft/profiler/Profiler;
        //     4: ldc_w           "jobs"
        //     7: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //    10: aload_0        
        //    11: getfield        net/minecraft/server/MinecraftServer.futureTaskQueue:Ljava/util/Queue;
        //    14: dup            
        //    15: astore_1       
        //    16: monitorenter   
        //    17: aload_0        
        //    18: getfield        net/minecraft/server/MinecraftServer.futureTaskQueue:Ljava/util/Queue;
        //    21: invokeinterface java/util/Queue.isEmpty:()Z
        //    26: ifne            51
        //    29: aload_0        
        //    30: getfield        net/minecraft/server/MinecraftServer.futureTaskQueue:Ljava/util/Queue;
        //    33: invokeinterface java/util/Queue.poll:()Ljava/lang/Object;
        //    38: checkcast       Ljava/util/concurrent/FutureTask;
        //    41: getstatic       net/minecraft/server/MinecraftServer.logger:Lorg/apache/logging/log4j/Logger;
        //    44: invokestatic    net/minecraft/util/Util.func_181617_a:(Ljava/util/concurrent/FutureTask;Lorg/apache/logging/log4j/Logger;)Ljava/lang/Object;
        //    47: pop            
        //    48: goto            17
        //    51: aload_1        
        //    52: monitorexit    
        //    53: goto            61
        //    56: astore_2       
        //    57: aload_1        
        //    58: monitorexit    
        //    59: aload_2        
        //    60: athrow         
        //    61: aload_0        
        //    62: getfield        net/minecraft/server/MinecraftServer.theProfiler:Lnet/minecraft/profiler/Profiler;
        //    65: ldc_w           "levels"
        //    68: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //    71: iconst_0       
        //    72: aload_0        
        //    73: getfield        net/minecraft/server/MinecraftServer.worldServers:[Lnet/minecraft/world/WorldServer;
        //    76: arraylength    
        //    77: if_icmpge       337
        //    80: invokestatic    java/lang/System.nanoTime:()J
        //    83: lstore_2       
        //    84: goto            94
        //    87: aload_0        
        //    88: invokevirtual   net/minecraft/server/MinecraftServer.getAllowNether:()Z
        //    91: ifeq            312
        //    94: aload_0        
        //    95: getfield        net/minecraft/server/MinecraftServer.worldServers:[Lnet/minecraft/world/WorldServer;
        //    98: iconst_0       
        //    99: aaload         
        //   100: astore          4
        //   102: aload_0        
        //   103: getfield        net/minecraft/server/MinecraftServer.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   106: aload           4
        //   108: invokevirtual   net/minecraft/world/WorldServer.getWorldInfo:()Lnet/minecraft/world/storage/WorldInfo;
        //   111: invokevirtual   net/minecraft/world/storage/WorldInfo.getWorldName:()Ljava/lang/String;
        //   114: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //   117: aload_0        
        //   118: getfield        net/minecraft/server/MinecraftServer.tickCounter:I
        //   121: bipush          20
        //   123: irem           
        //   124: ifne            187
        //   127: aload_0        
        //   128: getfield        net/minecraft/server/MinecraftServer.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   131: ldc_w           "timeSync"
        //   134: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //   137: aload_0        
        //   138: getfield        net/minecraft/server/MinecraftServer.serverConfigManager:Lnet/minecraft/server/management/ServerConfigurationManager;
        //   141: new             Lnet/minecraft/network/play/server/S03PacketTimeUpdate;
        //   144: dup            
        //   145: aload           4
        //   147: invokevirtual   net/minecraft/world/WorldServer.getTotalWorldTime:()J
        //   150: aload           4
        //   152: invokevirtual   net/minecraft/world/WorldServer.getWorldTime:()J
        //   155: aload           4
        //   157: invokevirtual   net/minecraft/world/WorldServer.getGameRules:()Lnet/minecraft/world/GameRules;
        //   160: ldc_w           "doDaylightCycle"
        //   163: invokevirtual   net/minecraft/world/GameRules.getBoolean:(Ljava/lang/String;)Z
        //   166: invokespecial   net/minecraft/network/play/server/S03PacketTimeUpdate.<init>:(JJZ)V
        //   169: aload           4
        //   171: getfield        net/minecraft/world/WorldServer.provider:Lnet/minecraft/world/WorldProvider;
        //   174: invokevirtual   net/minecraft/world/WorldProvider.getDimensionId:()I
        //   177: invokevirtual   net/minecraft/server/management/ServerConfigurationManager.sendPacketToAllPlayersInDimension:(Lnet/minecraft/network/Packet;I)V
        //   180: aload_0        
        //   181: getfield        net/minecraft/server/MinecraftServer.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   184: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   187: aload_0        
        //   188: getfield        net/minecraft/server/MinecraftServer.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   191: ldc_w           "tick"
        //   194: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //   197: aload           4
        //   199: invokevirtual   net/minecraft/world/WorldServer.tick:()V
        //   202: goto            235
        //   205: astore          5
        //   207: aload           5
        //   209: ldc_w           "Exception ticking world"
        //   212: invokestatic    net/minecraft/crash/CrashReport.makeCrashReport:(Ljava/lang/Throwable;Ljava/lang/String;)Lnet/minecraft/crash/CrashReport;
        //   215: astore          6
        //   217: aload           4
        //   219: aload           6
        //   221: invokevirtual   net/minecraft/world/WorldServer.addWorldInfoToCrashReport:(Lnet/minecraft/crash/CrashReport;)Lnet/minecraft/crash/CrashReportCategory;
        //   224: pop            
        //   225: new             Lnet/minecraft/util/ReportedException;
        //   228: dup            
        //   229: aload           6
        //   231: invokespecial   net/minecraft/util/ReportedException.<init>:(Lnet/minecraft/crash/CrashReport;)V
        //   234: athrow         
        //   235: aload           4
        //   237: invokevirtual   net/minecraft/world/WorldServer.updateEntities:()V
        //   240: goto            273
        //   243: astore          5
        //   245: aload           5
        //   247: ldc_w           "Exception ticking world entities"
        //   250: invokestatic    net/minecraft/crash/CrashReport.makeCrashReport:(Ljava/lang/Throwable;Ljava/lang/String;)Lnet/minecraft/crash/CrashReport;
        //   253: astore          6
        //   255: aload           4
        //   257: aload           6
        //   259: invokevirtual   net/minecraft/world/WorldServer.addWorldInfoToCrashReport:(Lnet/minecraft/crash/CrashReport;)Lnet/minecraft/crash/CrashReportCategory;
        //   262: pop            
        //   263: new             Lnet/minecraft/util/ReportedException;
        //   266: dup            
        //   267: aload           6
        //   269: invokespecial   net/minecraft/util/ReportedException.<init>:(Lnet/minecraft/crash/CrashReport;)V
        //   272: athrow         
        //   273: aload_0        
        //   274: getfield        net/minecraft/server/MinecraftServer.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   277: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   280: aload_0        
        //   281: getfield        net/minecraft/server/MinecraftServer.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   284: ldc_w           "tracker"
        //   287: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //   290: aload           4
        //   292: invokevirtual   net/minecraft/world/WorldServer.getEntityTracker:()Lnet/minecraft/entity/EntityTracker;
        //   295: invokevirtual   net/minecraft/entity/EntityTracker.updateTrackedEntities:()V
        //   298: aload_0        
        //   299: getfield        net/minecraft/server/MinecraftServer.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   302: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   305: aload_0        
        //   306: getfield        net/minecraft/server/MinecraftServer.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   309: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   312: aload_0        
        //   313: getfield        net/minecraft/server/MinecraftServer.timeOfLastDimensionTick:[[J
        //   316: iconst_0       
        //   317: aaload         
        //   318: aload_0        
        //   319: getfield        net/minecraft/server/MinecraftServer.tickCounter:I
        //   322: bipush          100
        //   324: irem           
        //   325: invokestatic    java/lang/System.nanoTime:()J
        //   328: lload_2        
        //   329: lsub           
        //   330: lastore        
        //   331: iinc            1, 1
        //   334: goto            71
        //   337: aload_0        
        //   338: getfield        net/minecraft/server/MinecraftServer.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   341: ldc_w           "connection"
        //   344: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   347: aload_0        
        //   348: invokevirtual   net/minecraft/server/MinecraftServer.getNetworkSystem:()Lnet/minecraft/network/NetworkSystem;
        //   351: invokevirtual   net/minecraft/network/NetworkSystem.networkTick:()V
        //   354: aload_0        
        //   355: getfield        net/minecraft/server/MinecraftServer.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   358: ldc_w           "players"
        //   361: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   364: aload_0        
        //   365: getfield        net/minecraft/server/MinecraftServer.serverConfigManager:Lnet/minecraft/server/management/ServerConfigurationManager;
        //   368: invokevirtual   net/minecraft/server/management/ServerConfigurationManager.onTick:()V
        //   371: aload_0        
        //   372: getfield        net/minecraft/server/MinecraftServer.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   375: ldc_w           "tickables"
        //   378: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   381: iconst_0       
        //   382: aload_0        
        //   383: getfield        net/minecraft/server/MinecraftServer.playersOnline:Ljava/util/List;
        //   386: invokeinterface java/util/List.size:()I
        //   391: if_icmpge       418
        //   394: aload_0        
        //   395: getfield        net/minecraft/server/MinecraftServer.playersOnline:Ljava/util/List;
        //   398: iconst_0       
        //   399: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   404: checkcast       Lnet/minecraft/util/ITickable;
        //   407: invokeinterface net/minecraft/util/ITickable.update:()V
        //   412: iinc            1, 1
        //   415: goto            381
        //   418: aload_0        
        //   419: getfield        net/minecraft/server/MinecraftServer.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   422: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   425: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    protected void finalTick(final CrashReport crashReport) {
    }
    
    public List getTabCompletions(final ICommandSender commandSender, String substring, final BlockPos blockPos) {
        final ArrayList arrayList = Lists.newArrayList();
        if (substring.startsWith("/")) {
            substring = substring.substring(1);
            final boolean b = !substring.contains(" ");
            final List tabCompletionOptions = this.commandManager.getTabCompletionOptions(commandSender, substring, blockPos);
            if (tabCompletionOptions != null) {
                for (final String s : tabCompletionOptions) {
                    if (b) {
                        arrayList.add("/" + s);
                    }
                    else {
                        arrayList.add(s);
                    }
                }
            }
            return arrayList;
        }
        final String[] split = substring.split(" ", -1);
        final String s2 = split[split.length - 1];
        final String[] allUsernames = this.serverConfigManager.getAllUsernames();
        while (0 < allUsernames.length) {
            final String s3 = allUsernames[0];
            if (CommandBase.doesStringStartWith(s2, s3)) {
                arrayList.add(s3);
            }
            int n = 0;
            ++n;
        }
        return arrayList;
    }
    
    public void deleteWorldAndStopServer() {
        this.worldIsBeingDeleted = true;
        this.getActiveAnvilConverter().flushCache();
        while (0 < this.worldServers.length) {
            final WorldServer worldServer = this.worldServers[0];
            if (worldServer != null) {
                worldServer.flush();
            }
            int n = 0;
            ++n;
        }
        this.getActiveAnvilConverter().deleteWorldDirectory(this.worldServers[0].getSaveHandler().getWorldDirectoryName());
        this.initiateShutdown();
    }
    
    public void setMOTD(final String motd) {
        this.motd = motd;
    }
    
    public PlayerProfileCache getPlayerProfileCache() {
        return this.profileCache;
    }
    
    public int getNetworkCompressionTreshold() {
        return 256;
    }
    
    public NetworkSystem getNetworkSystem() {
        return this.networkSystem;
    }
    
    public int getMaxPlayerIdleMinutes() {
        return this.maxPlayerIdleMinutes;
    }
    
    public String getResourcePackUrl() {
        return this.resourcePackUrl;
    }
    
    public boolean getAllowNether() {
        return true;
    }
    
    public abstract int getOpPermissionLevel();
    
    public GameProfileRepository getGameProfileRepository() {
        return this.profileRepo;
    }
    
    public boolean getForceGamemode() {
        return this.isGamemodeForced;
    }
    
    public void initiateShutdown() {
        this.serverRunning = false;
    }
    
    @Override
    public Entity getCommandSenderEntity() {
        return null;
    }
    
    protected void convertMapIfNeeded(final String s) {
        if (this.getActiveAnvilConverter().isOldMapFormat(s)) {
            MinecraftServer.logger.info("Converting map!");
            this.setUserMessage("menu.convertingLevel");
            this.getActiveAnvilConverter().convertMapFormat(s, new IProgressUpdate(this) {
                final MinecraftServer this$0;
                private long startTime = System.currentTimeMillis();
                
                @Override
                public void resetProgressAndMessage(final String s) {
                }
                
                @Override
                public void setDoneWorking() {
                }
                
                @Override
                public void setLoadingProgress(final int n) {
                    if (System.currentTimeMillis() - this.startTime >= 1000L) {
                        this.startTime = System.currentTimeMillis();
                        MinecraftServer.access$000().info("Converting... " + n + "%");
                    }
                }
                
                @Override
                public void displaySavingString(final String s) {
                }
                
                @Override
                public void displayLoadingString(final String s) {
                }
            });
        }
    }
    
    public ISaveFormat getActiveAnvilConverter() {
        return this.anvilConverterForAnvilFile;
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final int n, final String s) {
        return true;
    }
    
    public Entity getEntityFromUuid(final UUID uuid) {
        final WorldServer[] worldServers = this.worldServers;
        while (0 < worldServers.length) {
            final WorldServer worldServer = worldServers[0];
            if (worldServer != null) {
                final Entity entityFromUuid = worldServer.getEntityFromUuid(uuid);
                if (entityFromUuid != null) {
                    return entityFromUuid;
                }
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    public abstract boolean canStructuresSpawn();
    
    public String getMOTD() {
        return this.motd;
    }
    
    public boolean isAnvilFileSet() {
        return this.anvilFile != null;
    }
    
    static ServerConfigurationManager access$100(final MinecraftServer minecraftServer) {
        return minecraftServer.serverConfigManager;
    }
    
    public boolean serverIsInRunLoop() {
        return this.serverIsRunning;
    }
    
    @Override
    public void addServerStatsToSnooper(final PlayerUsageSnooper playerUsageSnooper) {
        playerUsageSnooper.addClientStat("whitelist_enabled", false);
        playerUsageSnooper.addClientStat("whitelist_count", 0);
        if (this.serverConfigManager != null) {
            playerUsageSnooper.addClientStat("players_current", this.getCurrentPlayerCount());
            playerUsageSnooper.addClientStat("players_max", this.getMaxPlayers());
            playerUsageSnooper.addClientStat("players_seen", this.serverConfigManager.getAvailablePlayerDat().length);
        }
        playerUsageSnooper.addClientStat("uses_auth", this.onlineMode);
        playerUsageSnooper.addClientStat("gui_state", this.getGuiEnabled() ? "enabled" : "disabled");
        playerUsageSnooper.addClientStat("run_time", (getCurrentTimeMillis() - playerUsageSnooper.getMinecraftStartTimeMillis()) / 60L * 1000L);
        playerUsageSnooper.addClientStat("avg_tick_ms", (int)(MathHelper.average(this.tickTimeArray) * 1.0E-6));
        if (this.worldServers != null) {
            while (0 < this.worldServers.length) {
                if (this.worldServers[0] != null) {
                    final WorldServer worldServer = this.worldServers[0];
                    final WorldInfo worldInfo = worldServer.getWorldInfo();
                    playerUsageSnooper.addClientStat("world[" + 0 + "][dimension]", worldServer.provider.getDimensionId());
                    playerUsageSnooper.addClientStat("world[" + 0 + "][mode]", worldInfo.getGameType());
                    playerUsageSnooper.addClientStat("world[" + 0 + "][difficulty]", worldServer.getDifficulty());
                    playerUsageSnooper.addClientStat("world[" + 0 + "][hardcore]", worldInfo.isHardcoreModeEnabled());
                    playerUsageSnooper.addClientStat("world[" + 0 + "][generator_name]", worldInfo.getTerrainType().getWorldTypeName());
                    playerUsageSnooper.addClientStat("world[" + 0 + "][generator_version]", worldInfo.getTerrainType().getGeneratorVersion());
                    playerUsageSnooper.addClientStat("world[" + 0 + "][height]", this.buildLimit);
                    playerUsageSnooper.addClientStat("world[" + 0 + "][chunks_loaded]", worldServer.getChunkProvider().getLoadedChunkCount());
                    int n = 0;
                    ++n;
                }
                int n2 = 0;
                ++n2;
            }
        }
        playerUsageSnooper.addClientStat("worlds", 0);
    }
    
    protected void loadAllWorlds(final String p0, final String p1, final long p2, final WorldType p3, final String p4) {
        // 
        // This method could not be decompiled.
        // 
        // Could not show original bytecode, likely due to the same error.
        // 
        // The error that occurred was:
        // 
        // com.strobel.assembler.metadata.MethodBodyParseException: An error occurred while parsing the bytecode of method 'net/minecraft/server/MinecraftServer.loadAllWorlds:(Ljava/lang/String;Ljava/lang/String;JLnet/minecraft/world/WorldType;Ljava/lang/String;)V'.
        //     at com.strobel.assembler.metadata.MethodReader.readBody(MethodReader.java:66)
        //     at com.strobel.assembler.metadata.MethodDefinition.tryLoadBody(MethodDefinition.java:729)
        //     at com.strobel.assembler.metadata.MethodDefinition.getBody(MethodDefinition.java:83)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:202)
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
        // Caused by: java.lang.IndexOutOfBoundsException: No instruction found at offset 172.
        //     at com.strobel.assembler.ir.InstructionCollection.atOffset(InstructionCollection.java:38)
        //     at com.strobel.assembler.metadata.MethodReader.readBodyCore(MethodReader.java:235)
        //     at com.strobel.assembler.metadata.MethodReader.readBody(MethodReader.java:62)
        //     ... 17 more
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void setCanSpawnNPCs(final boolean canSpawnNPCs) {
        this.canSpawnNPCs = canSpawnNPCs;
    }
    
    protected void setResourcePackFromWorld(final String s, final ISaveHandler saveHandler) {
        final File file = new File(saveHandler.getWorldDirectory(), "resources.zip");
        if (file.isFile()) {
            this.setResourcePack("level://" + s + "/" + file.getName(), "");
        }
    }
    
    public MinecraftSessionService getMinecraftSessionService() {
        return this.sessionService;
    }
    
    public boolean getCanSpawnNPCs() {
        return this.canSpawnNPCs;
    }
    
    public String getServerOwner() {
        return this.serverOwner;
    }
    
    protected void initialWorldChunkLoad() {
        this.setUserMessage("menu.generatingTerrain");
        MinecraftServer.logger.info("Preparing start region for level " + 0);
        final WorldServer worldServer = this.worldServers[0];
        final BlockPos spawnPoint = worldServer.getSpawnPoint();
        long currentTimeMillis = getCurrentTimeMillis();
        while (this.isServerRunning()) {
            while (this.isServerRunning()) {
                final long currentTimeMillis2 = getCurrentTimeMillis();
                if (currentTimeMillis2 - currentTimeMillis > 1000L) {
                    this.outputPercentRemaining("Preparing spawn area", 0);
                    currentTimeMillis = currentTimeMillis2;
                }
                int n = 0;
                ++n;
                worldServer.theChunkProviderServer.loadChunk(spawnPoint.getX() - 192 >> 4, spawnPoint.getZ() - 192 >> 4);
                final int n2;
                n2 += 16;
            }
            final int n3;
            n3 += 16;
        }
        this.clearCurrentTask();
    }
    
    protected void systemExitNow() {
    }
    
    public void refreshStatusNextTick() {
        this.nanoTimeSinceStatusRefresh = 0L;
    }
    
    protected synchronized void setUserMessage(final String userMessage) {
        this.userMessage = userMessage;
    }
    
    public boolean getCanSpawnAnimals() {
        return this.canSpawnAnimals;
    }
    
    @Override
    public String getName() {
        return "Server";
    }
    
    public int getTickCounter() {
        return this.tickCounter;
    }
    
    public boolean isAnnouncingPlayerAchievements() {
        return true;
    }
    
    @Override
    public void addChatMessage(final IChatComponent chatComponent) {
        MinecraftServer.logger.info(chatComponent.getUnformattedText());
    }
    
    protected void clearCurrentTask() {
        this.currentTask = null;
        this.percentDone = 0;
    }
    
    protected abstract boolean startServer() throws IOException;
}
