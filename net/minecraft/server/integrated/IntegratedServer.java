package net.minecraft.server.integrated;

import net.minecraft.server.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.crash.*;
import net.minecraft.command.*;
import net.minecraft.world.demo.*;
import optfine.*;
import net.minecraft.world.storage.*;
import net.minecraft.client.*;
import com.google.common.collect.*;
import net.minecraft.entity.player.*;
import com.google.common.util.concurrent.*;
import java.io.*;
import net.minecraft.world.*;
import java.net.*;
import java.util.concurrent.*;
import net.minecraft.util.*;
import java.util.*;
import org.apache.logging.log4j.*;
import net.minecraft.server.management.*;
import net.minecraft.profiler.*;

public class IntegratedServer extends MinecraftServer
{
    private boolean isPublic;
    private final Minecraft mc;
    private ThreadLanServerPing lanServerPing;
    private static final Logger logger;
    private final WorldSettings theWorldSettings;
    private static final String __OBFID = "CL_00001129";
    private boolean isGamePaused;
    
    @Override
    protected void finalTick(final CrashReport crashReport) {
        this.mc.crashed(crashReport);
    }
    
    @Override
    protected ServerCommandManager createNewCommandManager() {
        return new IntegratedServerCommandManager();
    }
    
    @Override
    protected void loadAllWorlds(final String s, final String worldName, final long n, final WorldType worldType, final String s2) {
        this.convertMapIfNeeded(s);
        final ISaveHandler saveLoader = this.getActiveAnvilConverter().getSaveLoader(s, true);
        WorldInfo loadWorldInfo = saveLoader.loadWorldInfo();
        if (Reflector.DimensionManager.exists()) {
            final WorldServer worldServer = (WorldServer)(this.isDemo() ? new DemoWorldServer(this, saveLoader, loadWorldInfo, 0, this.theProfiler).init() : ((WorldServer)new WorldServerOF(this, saveLoader, loadWorldInfo, 0, this.theProfiler).init()));
            worldServer.initialize(this.theWorldSettings);
            Integer[] array;
            while (0 < (array = (Integer[])Reflector.call(Reflector.DimensionManager_getStaticDimensionIDs, new Object[0])).length) {
                final int intValue = array[0];
                final WorldServer worldServer2 = (WorldServer)((intValue == 0) ? worldServer : new WorldServerMulti(this, saveLoader, intValue, worldServer, this.theProfiler).init());
                worldServer2.addWorldAccess(new WorldManager(this, worldServer2));
                if (!this.isSinglePlayer()) {
                    worldServer2.getWorldInfo().setGameType(this.getGameType());
                }
                if (Reflector.EventBus.exists()) {
                    Reflector.postForgeBusEvent(Reflector.WorldEvent_Load_Constructor, worldServer2);
                }
                int n2 = 0;
                ++n2;
            }
            this.getConfigurationManager().setPlayerManager(new WorldServer[] { worldServer });
            if (worldServer.getWorldInfo().getDifficulty() == null) {
                this.setDifficultyForAllWorlds(this.mc.gameSettings.difficulty);
            }
        }
        else {
            this.worldServers = new WorldServer[3];
            this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
            this.setResourcePackFromWorld(this.getFolderName(), saveLoader);
            if (loadWorldInfo == null) {
                loadWorldInfo = new WorldInfo(this.theWorldSettings, worldName);
            }
            else {
                loadWorldInfo.setWorldName(worldName);
            }
            while (0 < this.worldServers.length) {
                if (this.isDemo()) {
                    this.worldServers[0] = (WorldServer)new DemoWorldServer(this, saveLoader, loadWorldInfo, 1, this.theProfiler).init();
                }
                else {
                    this.worldServers[0] = (WorldServer)new WorldServerOF(this, saveLoader, loadWorldInfo, 1, this.theProfiler).init();
                }
                this.worldServers[0].initialize(this.theWorldSettings);
                this.worldServers[0].addWorldAccess(new WorldManager(this, this.worldServers[0]));
                int n3 = 0;
                ++n3;
            }
            this.getConfigurationManager().setPlayerManager(this.worldServers);
            if (this.worldServers[0].getWorldInfo().getDifficulty() == null) {
                this.setDifficultyForAllWorlds(this.mc.gameSettings.difficulty);
            }
        }
        this.initialWorldChunkLoad();
    }
    
    @Override
    protected boolean startServer() throws IOException {
        IntegratedServer.logger.info("Starting integrated minecraft server version 1.8.8");
        this.setOnlineMode(true);
        this.setCanSpawnAnimals(true);
        this.setCanSpawnNPCs(true);
        this.setAllowPvp(true);
        this.setAllowFlight(true);
        IntegratedServer.logger.info("Generating keypair");
        this.setKeyPair(CryptManager.generateKeyPair());
        if (Reflector.FMLCommonHandler_handleServerAboutToStart.exists() && !Reflector.callBoolean(Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]), Reflector.FMLCommonHandler_handleServerAboutToStart, this)) {
            return false;
        }
        this.loadAllWorlds(this.getFolderName(), this.getWorldName(), this.theWorldSettings.getSeed(), this.theWorldSettings.getTerrainType(), this.theWorldSettings.getWorldName());
        this.setMOTD(this.getServerOwner() + " - " + this.worldServers[0].getWorldInfo().getWorldName());
        if (Reflector.FMLCommonHandler_handleServerStarting.exists()) {
            final Object call = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
            if (Reflector.FMLCommonHandler_handleServerStarting.getReturnType() == Boolean.TYPE) {
                return Reflector.callBoolean(call, Reflector.FMLCommonHandler_handleServerStarting, this);
            }
            Reflector.callVoid(call, Reflector.FMLCommonHandler_handleServerStarting, this);
        }
        return true;
    }
    
    @Override
    public void stopServer() {
        super.stopServer();
        if (this.lanServerPing != null) {
            this.lanServerPing.interrupt();
            this.lanServerPing = null;
        }
    }
    
    @Override
    public boolean func_183002_r() {
        return true;
    }
    
    @Override
    public boolean canStructuresSpawn() {
        return false;
    }
    
    @Override
    public boolean func_181034_q() {
        return true;
    }
    
    @Override
    public CrashReport addServerInfoToCrashReport(CrashReport addServerInfoToCrashReport) {
        addServerInfoToCrashReport = super.addServerInfoToCrashReport(addServerInfoToCrashReport);
        addServerInfoToCrashReport.getCategory().addCrashSectionCallable("Type", new Callable(this) {
            private static final String __OBFID = "CL_00001130";
            final IntegratedServer this$0;
            
            @Override
            public String call() throws Exception {
                return "Integrated Server (map_client.txt)";
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
        });
        addServerInfoToCrashReport.getCategory().addCrashSectionCallable("Is Modded", new Callable(this) {
            private static final String __OBFID = "CL_00001131";
            final IntegratedServer this$0;
            
            @Override
            public String call() throws Exception {
                final String clientModName = ClientBrandRetriever.getClientModName();
                if (!clientModName.equals("vanilla")) {
                    return "Definitely; Client brand changed to '" + clientModName + "'";
                }
                final String serverModName = this.this$0.getServerModName();
                return serverModName.equals("vanilla") ? ((Minecraft.class.getSigners() == null) ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and both client + server brands are untouched.") : ("Definitely; Server brand changed to '" + serverModName + "'");
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
        });
        return addServerInfoToCrashReport;
    }
    
    @Override
    public void initiateShutdown() {
        Futures.getUnchecked((Future)this.addScheduledTask(new Runnable(this) {
            final IntegratedServer this$0;
            private static final String __OBFID = "CL_00002380";
            
            @Override
            public void run() {
                final Iterator<EntityPlayerMP> iterator = Lists.newArrayList((Iterable)this.this$0.getConfigurationManager().func_181057_v()).iterator();
                while (iterator.hasNext()) {
                    this.this$0.getConfigurationManager().playerLoggedOut(iterator.next());
                }
            }
        }));
        super.initiateShutdown();
        if (this.lanServerPing != null) {
            this.lanServerPing.interrupt();
            this.lanServerPing = null;
        }
    }
    
    public boolean getPublic() {
        return this.isPublic;
    }
    
    @Override
    public File getDataDirectory() {
        return this.mc.mcDataDir;
    }
    
    @Override
    public boolean isCommandBlockEnabled() {
        return true;
    }
    
    @Override
    public boolean func_181035_ah() {
        return false;
    }
    
    @Override
    public EnumDifficulty getDifficulty() {
        return (this.mc.theWorld == null) ? this.mc.gameSettings.difficulty : this.mc.theWorld.getWorldInfo().getDifficulty();
    }
    
    public void setStaticInstance() {
        this.setInstance();
    }
    
    @Override
    public String shareToLAN(final WorldSettings.GameType gameType, final boolean commandsAllowedForAll) {
        HttpUtil.getSuitableLanPort();
        this.getNetworkSystem().addLanEndpoint(null, 25564);
        IntegratedServer.logger.info("Started on " + 25564);
        this.isPublic = true;
        (this.lanServerPing = new ThreadLanServerPing(this.getMOTD(), 25564 + "")).start();
        this.getConfigurationManager().setGameType(gameType);
        this.getConfigurationManager().setCommandsAllowedForAll(commandsAllowedForAll);
        return 25564 + "";
    }
    
    @Override
    public boolean isDedicatedServer() {
        return false;
    }
    
    @Override
    public boolean isSnooperEnabled() {
        return Minecraft.getMinecraft().isSnooperEnabled();
    }
    
    @Override
    public void tick() {
        final boolean isGamePaused = this.isGamePaused;
        this.isGamePaused = (Minecraft.getMinecraft().getNetHandler() != null && Minecraft.getMinecraft().isGamePaused());
        if (!isGamePaused && this.isGamePaused) {
            IntegratedServer.logger.info("Saving and pausing game...");
            this.getConfigurationManager().saveAllPlayerData();
            this.saveAllWorlds(false);
        }
        if (this.isGamePaused) {
            final Queue futureTaskQueue = this.futureTaskQueue;
            // monitorenter(futureTaskQueue2 = this.futureTaskQueue)
            while (!this.futureTaskQueue.isEmpty()) {
                Util.func_181617_a(this.futureTaskQueue.poll(), IntegratedServer.logger);
            }
        }
        // monitorexit(futureTaskQueue2)
        else {
            super.tick();
            if (this.mc.gameSettings.renderDistanceChunks != this.getConfigurationManager().getViewDistance()) {
                IntegratedServer.logger.info("Changing view distance to {}, from {}", new Object[] { this.mc.gameSettings.renderDistanceChunks, this.getConfigurationManager().getViewDistance() });
                this.getConfigurationManager().setViewDistance(this.mc.gameSettings.renderDistanceChunks);
            }
            if (this.mc.theWorld != null) {
                final WorldInfo worldInfo = this.worldServers[0].getWorldInfo();
                final WorldInfo worldInfo2 = this.mc.theWorld.getWorldInfo();
                if (!worldInfo.isDifficultyLocked() && worldInfo2.getDifficulty() != worldInfo.getDifficulty()) {
                    IntegratedServer.logger.info("Changing difficulty to {}, from {}", new Object[] { worldInfo2.getDifficulty(), worldInfo.getDifficulty() });
                    this.setDifficultyForAllWorlds(worldInfo2.getDifficulty());
                }
                else if (worldInfo2.isDifficultyLocked() && !worldInfo.isDifficultyLocked()) {
                    IntegratedServer.logger.info("Locking difficulty to {}", new Object[] { worldInfo2.getDifficulty() });
                    final WorldServer[] worldServers = this.worldServers;
                    while (0 < worldServers.length) {
                        final WorldServer worldServer = worldServers[0];
                        if (worldServer != null) {
                            worldServer.getWorldInfo().setDifficultyLocked(true);
                        }
                        int n = 0;
                        ++n;
                    }
                }
            }
        }
    }
    
    static {
        logger = LogManager.getLogger();
    }
    
    @Override
    public void setDifficultyForAllWorlds(final EnumDifficulty enumDifficulty) {
        super.setDifficultyForAllWorlds(enumDifficulty);
        if (this.mc.theWorld != null) {
            this.mc.theWorld.getWorldInfo().setDifficulty(enumDifficulty);
        }
    }
    
    @Override
    public int getOpPermissionLevel() {
        return 4;
    }
    
    public IntegratedServer(final Minecraft mc, final String folderName, final String worldName, final WorldSettings worldSettings) {
        super(new File(mc.mcDataDir, "saves"), mc.getProxy(), new File(mc.mcDataDir, IntegratedServer.USER_CACHE_FILE.getName()));
        this.setServerOwner(mc.getSession().getUsername());
        this.setFolderName(folderName);
        this.setWorldName(worldName);
        this.setDemo(mc.isDemo());
        this.canCreateBonusChest(worldSettings.isBonusChestEnabled());
        this.setBuildLimit(256);
        this.setConfigManager(new IntegratedPlayerList(this));
        this.mc = mc;
        this.theWorldSettings = (this.isDemo() ? DemoWorldServer.demoWorldSettings : worldSettings);
        Reflector.callVoid(Reflector.ModLoader_registerServer, this);
    }
    
    @Override
    public void addServerStatsToSnooper(final PlayerUsageSnooper playerUsageSnooper) {
        super.addServerStatsToSnooper(playerUsageSnooper);
        playerUsageSnooper.addClientStat("snooper_partner", this.mc.getPlayerUsageSnooper().getUniqueID());
    }
    
    @Override
    public boolean isHardcore() {
        return this.theWorldSettings.getHardcoreEnabled();
    }
    
    public IntegratedServer(final Minecraft mc) {
        super(mc.getProxy(), new File(mc.mcDataDir, IntegratedServer.USER_CACHE_FILE.getName()));
        this.mc = mc;
        this.theWorldSettings = null;
    }
    
    @Override
    public void setGameType(final WorldSettings.GameType gameType) {
        this.getConfigurationManager().setGameType(gameType);
    }
    
    @Override
    public WorldSettings.GameType getGameType() {
        return this.theWorldSettings.getGameType();
    }
}
