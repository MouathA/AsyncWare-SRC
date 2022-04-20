package net.minecraft.world.storage;

import net.minecraft.world.*;
import net.minecraft.crash.*;
import java.util.concurrent.*;
import net.minecraft.server.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public class WorldInfo
{
    private int cleanWeatherTime;
    private String levelName;
    private boolean difficultyLocked;
    private int borderWarningTime;
    private int spawnX;
    private int spawnY;
    private boolean mapFeaturesEnabled;
    private int saveVersion;
    private long worldTime;
    private WorldType terrainType;
    private double borderCenterZ;
    private long totalTime;
    private NBTTagCompound playerTag;
    private double borderCenterX;
    private GameRules theGameRules;
    private String generatorOptions;
    private boolean raining;
    private EnumDifficulty difficulty;
    private int dimension;
    private int thunderTime;
    private double borderSize;
    private int borderWarningDistance;
    private boolean allowCommands;
    public static final EnumDifficulty DEFAULT_DIFFICULTY;
    private double borderSizeLerpTarget;
    private boolean initialized;
    private double borderDamagePerBlock;
    private long borderSizeLerpTime;
    private long lastTimePlayed;
    private WorldSettings.GameType theGameType;
    private long randomSeed;
    private int rainTime;
    private long sizeOnDisk;
    private double borderSafeZone;
    private boolean thundering;
    private boolean hardcore;
    private int spawnZ;
    
    public void setTerrainType(final WorldType terrainType) {
        this.terrainType = terrainType;
    }
    
    public long getLastTimePlayed() {
        return this.lastTimePlayed;
    }
    
    public WorldType getTerrainType() {
        return this.terrainType;
    }
    
    static int access$1200(final WorldInfo worldInfo) {
        return worldInfo.thunderTime;
    }
    
    static int access$300(final WorldInfo worldInfo) {
        return worldInfo.spawnX;
    }
    
    public NBTTagCompound getNBTTagCompound() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.updateTagCompound(nbtTagCompound, this.playerTag);
        return nbtTagCompound;
    }
    
    static WorldSettings.GameType access$1400(final WorldInfo worldInfo) {
        return worldInfo.theGameType;
    }
    
    public void setSpawnY(final int spawnY) {
        this.spawnY = spawnY;
    }
    
    public int getSpawnZ() {
        return this.spawnZ;
    }
    
    public void setDifficulty(final EnumDifficulty difficulty) {
        this.difficulty = difficulty;
    }
    
    public void setCleanWeatherTime(final int cleanWeatherTime) {
        this.cleanWeatherTime = cleanWeatherTime;
    }
    
    static long access$700(final WorldInfo worldInfo) {
        return worldInfo.worldTime;
    }
    
    public void setWorldName(final String levelName) {
        this.levelName = levelName;
    }
    
    public boolean isDifficultyLocked() {
        return this.difficultyLocked;
    }
    
    public long getSeed() {
        return this.randomSeed;
    }
    
    public EnumDifficulty getDifficulty() {
        return this.difficulty;
    }
    
    public void setThundering(final boolean thundering) {
        this.thundering = thundering;
    }
    
    protected WorldInfo() {
        this.terrainType = WorldType.DEFAULT;
        this.generatorOptions = "";
        this.borderCenterX = 0.0;
        this.borderCenterZ = 0.0;
        this.borderSize = 6.0E7;
        this.borderSizeLerpTime = 0L;
        this.borderSizeLerpTarget = 0.0;
        this.borderSafeZone = 5.0;
        this.borderDamagePerBlock = 0.2;
        this.borderWarningDistance = 5;
        this.borderWarningTime = 15;
        this.theGameRules = new GameRules();
    }
    
    public void setGameType(final WorldSettings.GameType theGameType) {
        this.theGameType = theGameType;
    }
    
    public void getBorderCenterZ(final double borderCenterZ) {
        this.borderCenterZ = borderCenterZ;
    }
    
    public int getRainTime() {
        return this.rainTime;
    }
    
    public long getBorderLerpTime() {
        return this.borderSizeLerpTime;
    }
    
    static long access$600(final WorldInfo worldInfo) {
        return worldInfo.totalTime;
    }
    
    public int getBorderWarningDistance() {
        return this.borderWarningDistance;
    }
    
    public boolean isRaining() {
        return this.raining;
    }
    
    public void addToCrashReport(final CrashReportCategory crashReportCategory) {
        crashReportCategory.addCrashSectionCallable("Level seed", new Callable(this) {
            final WorldInfo this$0;
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            @Override
            public String call() throws Exception {
                return String.valueOf(this.this$0.getSeed());
            }
        });
        crashReportCategory.addCrashSectionCallable("Level generator", new Callable(this) {
            final WorldInfo this$0;
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            @Override
            public String call() throws Exception {
                return String.format("ID %02d - %s, ver %d. Features enabled: %b", WorldInfo.access$000(this.this$0).getWorldTypeID(), WorldInfo.access$000(this.this$0).getWorldTypeName(), WorldInfo.access$000(this.this$0).getGeneratorVersion(), WorldInfo.access$100(this.this$0));
            }
        });
        crashReportCategory.addCrashSectionCallable("Level generator options", new Callable(this) {
            final WorldInfo this$0;
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            @Override
            public String call() throws Exception {
                return WorldInfo.access$200(this.this$0);
            }
        });
        crashReportCategory.addCrashSectionCallable("Level spawn location", new Callable(this) {
            final WorldInfo this$0;
            
            @Override
            public String call() throws Exception {
                return CrashReportCategory.getCoordinateInfo(WorldInfo.access$300(this.this$0), WorldInfo.access$400(this.this$0), WorldInfo.access$500(this.this$0));
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
        });
        crashReportCategory.addCrashSectionCallable("Level time", new Callable(this) {
            final WorldInfo this$0;
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            @Override
            public String call() throws Exception {
                return String.format("%d game time, %d day time", WorldInfo.access$600(this.this$0), WorldInfo.access$700(this.this$0));
            }
        });
        crashReportCategory.addCrashSectionCallable("Level dimension", new Callable(this) {
            final WorldInfo this$0;
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            @Override
            public String call() throws Exception {
                return String.valueOf(WorldInfo.access$800(this.this$0));
            }
        });
        crashReportCategory.addCrashSectionCallable("Level storage version", new Callable(this) {
            final WorldInfo this$0;
            
            @Override
            public String call() throws Exception {
                String s = "Unknown?";
                switch (WorldInfo.access$900(this.this$0)) {
                    case 19132: {
                        s = "McRegion";
                        break;
                    }
                    case 19133: {
                        s = "Anvil";
                        break;
                    }
                }
                return String.format("0x%05X - %s", WorldInfo.access$900(this.this$0), s);
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
        });
        crashReportCategory.addCrashSectionCallable("Level weather", new Callable(this) {
            final WorldInfo this$0;
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            @Override
            public String call() throws Exception {
                return String.format("Rain time: %d (now: %b), thunder time: %d (now: %b)", WorldInfo.access$1000(this.this$0), WorldInfo.access$1100(this.this$0), WorldInfo.access$1200(this.this$0), WorldInfo.access$1300(this.this$0));
            }
        });
        crashReportCategory.addCrashSectionCallable("Level game mode", new Callable(this) {
            final WorldInfo this$0;
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            @Override
            public String call() throws Exception {
                return String.format("Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", WorldInfo.access$1400(this.this$0).getName(), WorldInfo.access$1400(this.this$0).getID(), WorldInfo.access$1500(this.this$0), WorldInfo.access$1600(this.this$0));
            }
        });
    }
    
    public boolean isThundering() {
        return this.thundering;
    }
    
    static WorldType access$000(final WorldInfo worldInfo) {
        return worldInfo.terrainType;
    }
    
    public void setRaining(final boolean raining) {
        this.raining = raining;
    }
    
    public long getSizeOnDisk() {
        return this.sizeOnDisk;
    }
    
    public void setThunderTime(final int thunderTime) {
        this.thunderTime = thunderTime;
    }
    
    public void setWorldTime(final long worldTime) {
        this.worldTime = worldTime;
    }
    
    public double getBorderDamagePerBlock() {
        return this.borderDamagePerBlock;
    }
    
    public String getGeneratorOptions() {
        return this.generatorOptions;
    }
    
    public int getSpawnX() {
        return this.spawnX;
    }
    
    public void setBorderSize(final double borderSize) {
        this.borderSize = borderSize;
    }
    
    static boolean access$1600(final WorldInfo worldInfo) {
        return worldInfo.allowCommands;
    }
    
    public WorldInfo(final WorldInfo worldInfo) {
        this.terrainType = WorldType.DEFAULT;
        this.generatorOptions = "";
        this.borderCenterX = 0.0;
        this.borderCenterZ = 0.0;
        this.borderSize = 6.0E7;
        this.borderSizeLerpTime = 0L;
        this.borderSizeLerpTarget = 0.0;
        this.borderSafeZone = 5.0;
        this.borderDamagePerBlock = 0.2;
        this.borderWarningDistance = 5;
        this.borderWarningTime = 15;
        this.theGameRules = new GameRules();
        this.randomSeed = worldInfo.randomSeed;
        this.terrainType = worldInfo.terrainType;
        this.generatorOptions = worldInfo.generatorOptions;
        this.theGameType = worldInfo.theGameType;
        this.mapFeaturesEnabled = worldInfo.mapFeaturesEnabled;
        this.spawnX = worldInfo.spawnX;
        this.spawnY = worldInfo.spawnY;
        this.spawnZ = worldInfo.spawnZ;
        this.totalTime = worldInfo.totalTime;
        this.worldTime = worldInfo.worldTime;
        this.lastTimePlayed = worldInfo.lastTimePlayed;
        this.sizeOnDisk = worldInfo.sizeOnDisk;
        this.playerTag = worldInfo.playerTag;
        this.dimension = worldInfo.dimension;
        this.levelName = worldInfo.levelName;
        this.saveVersion = worldInfo.saveVersion;
        this.rainTime = worldInfo.rainTime;
        this.raining = worldInfo.raining;
        this.thunderTime = worldInfo.thunderTime;
        this.thundering = worldInfo.thundering;
        this.hardcore = worldInfo.hardcore;
        this.allowCommands = worldInfo.allowCommands;
        this.initialized = worldInfo.initialized;
        this.theGameRules = worldInfo.theGameRules;
        this.difficulty = worldInfo.difficulty;
        this.difficultyLocked = worldInfo.difficultyLocked;
        this.borderCenterX = worldInfo.borderCenterX;
        this.borderCenterZ = worldInfo.borderCenterZ;
        this.borderSize = worldInfo.borderSize;
        this.borderSizeLerpTime = worldInfo.borderSizeLerpTime;
        this.borderSizeLerpTarget = worldInfo.borderSizeLerpTarget;
        this.borderSafeZone = worldInfo.borderSafeZone;
        this.borderDamagePerBlock = worldInfo.borderDamagePerBlock;
        this.borderWarningTime = worldInfo.borderWarningTime;
        this.borderWarningDistance = worldInfo.borderWarningDistance;
    }
    
    public double getBorderLerpTarget() {
        return this.borderSizeLerpTarget;
    }
    
    public double getBorderCenterX() {
        return this.borderCenterX;
    }
    
    public boolean areCommandsAllowed() {
        return this.allowCommands;
    }
    
    public void setBorderWarningTime(final int borderWarningTime) {
        this.borderWarningTime = borderWarningTime;
    }
    
    public void setMapFeaturesEnabled(final boolean mapFeaturesEnabled) {
        this.mapFeaturesEnabled = mapFeaturesEnabled;
    }
    
    public GameRules getGameRulesInstance() {
        return this.theGameRules;
    }
    
    public boolean isHardcoreModeEnabled() {
        return this.hardcore;
    }
    
    static boolean access$1500(final WorldInfo worldInfo) {
        return worldInfo.hardcore;
    }
    
    public String getWorldName() {
        return this.levelName;
    }
    
    static boolean access$100(final WorldInfo worldInfo) {
        return worldInfo.mapFeaturesEnabled;
    }
    
    public void setBorderLerpTime(final long borderSizeLerpTime) {
        this.borderSizeLerpTime = borderSizeLerpTime;
    }
    
    public void setHardcore(final boolean hardcore) {
        this.hardcore = hardcore;
    }
    
    public int getSaveVersion() {
        return this.saveVersion;
    }
    
    public double getBorderSafeZone() {
        return this.borderSafeZone;
    }
    
    public void setSaveVersion(final int saveVersion) {
        this.saveVersion = saveVersion;
    }
    
    static int access$1000(final WorldInfo worldInfo) {
        return worldInfo.rainTime;
    }
    
    static {
        DEFAULT_DIFFICULTY = EnumDifficulty.NORMAL;
    }
    
    static boolean access$1100(final WorldInfo worldInfo) {
        return worldInfo.raining;
    }
    
    static int access$500(final WorldInfo worldInfo) {
        return worldInfo.spawnZ;
    }
    
    public WorldInfo(final WorldSettings worldSettings, final String levelName) {
        this.terrainType = WorldType.DEFAULT;
        this.generatorOptions = "";
        this.borderCenterX = 0.0;
        this.borderCenterZ = 0.0;
        this.borderSize = 6.0E7;
        this.borderSizeLerpTime = 0L;
        this.borderSizeLerpTarget = 0.0;
        this.borderSafeZone = 5.0;
        this.borderDamagePerBlock = 0.2;
        this.borderWarningDistance = 5;
        this.borderWarningTime = 15;
        this.theGameRules = new GameRules();
        this.populateFromWorldSettings(worldSettings);
        this.levelName = levelName;
        this.difficulty = WorldInfo.DEFAULT_DIFFICULTY;
        this.initialized = false;
    }
    
    private void updateTagCompound(final NBTTagCompound nbtTagCompound, final NBTTagCompound nbtTagCompound2) {
        nbtTagCompound.setLong("RandomSeed", this.randomSeed);
        nbtTagCompound.setString("generatorName", this.terrainType.getWorldTypeName());
        nbtTagCompound.setInteger("generatorVersion", this.terrainType.getGeneratorVersion());
        nbtTagCompound.setString("generatorOptions", this.generatorOptions);
        nbtTagCompound.setInteger("GameType", this.theGameType.getID());
        nbtTagCompound.setBoolean("MapFeatures", this.mapFeaturesEnabled);
        nbtTagCompound.setInteger("SpawnX", this.spawnX);
        nbtTagCompound.setInteger("SpawnY", this.spawnY);
        nbtTagCompound.setInteger("SpawnZ", this.spawnZ);
        nbtTagCompound.setLong("Time", this.totalTime);
        nbtTagCompound.setLong("DayTime", this.worldTime);
        nbtTagCompound.setLong("SizeOnDisk", this.sizeOnDisk);
        nbtTagCompound.setLong("LastPlayed", MinecraftServer.getCurrentTimeMillis());
        nbtTagCompound.setString("LevelName", this.levelName);
        nbtTagCompound.setInteger("version", this.saveVersion);
        nbtTagCompound.setInteger("clearWeatherTime", this.cleanWeatherTime);
        nbtTagCompound.setInteger("rainTime", this.rainTime);
        nbtTagCompound.setBoolean("raining", this.raining);
        nbtTagCompound.setInteger("thunderTime", this.thunderTime);
        nbtTagCompound.setBoolean("thundering", this.thundering);
        nbtTagCompound.setBoolean("hardcore", this.hardcore);
        nbtTagCompound.setBoolean("allowCommands", this.allowCommands);
        nbtTagCompound.setBoolean("initialized", this.initialized);
        nbtTagCompound.setDouble("BorderCenterX", this.borderCenterX);
        nbtTagCompound.setDouble("BorderCenterZ", this.borderCenterZ);
        nbtTagCompound.setDouble("BorderSize", this.borderSize);
        nbtTagCompound.setLong("BorderSizeLerpTime", this.borderSizeLerpTime);
        nbtTagCompound.setDouble("BorderSafeZone", this.borderSafeZone);
        nbtTagCompound.setDouble("BorderDamagePerBlock", this.borderDamagePerBlock);
        nbtTagCompound.setDouble("BorderSizeLerpTarget", this.borderSizeLerpTarget);
        nbtTagCompound.setDouble("BorderWarningBlocks", this.borderWarningDistance);
        nbtTagCompound.setDouble("BorderWarningTime", this.borderWarningTime);
        if (this.difficulty != null) {
            nbtTagCompound.setByte("Difficulty", (byte)this.difficulty.getDifficultyId());
        }
        nbtTagCompound.setBoolean("DifficultyLocked", this.difficultyLocked);
        nbtTagCompound.setTag("GameRules", this.theGameRules.writeToNBT());
        if (nbtTagCompound2 != null) {
            nbtTagCompound.setTag("Player", nbtTagCompound2);
        }
    }
    
    static int access$900(final WorldInfo worldInfo) {
        return worldInfo.saveVersion;
    }
    
    public long getWorldTotalTime() {
        return this.totalTime;
    }
    
    public void setWorldTotalTime(final long totalTime) {
        this.totalTime = totalTime;
    }
    
    public void setSpawn(final BlockPos blockPos) {
        this.spawnX = blockPos.getX();
        this.spawnY = blockPos.getY();
        this.spawnZ = blockPos.getZ();
    }
    
    public double getBorderSize() {
        return this.borderSize;
    }
    
    public void setAllowCommands(final boolean allowCommands) {
        this.allowCommands = allowCommands;
    }
    
    public NBTTagCompound cloneNBTCompound(final NBTTagCompound nbtTagCompound) {
        final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
        this.updateTagCompound(nbtTagCompound2, nbtTagCompound);
        return nbtTagCompound2;
    }
    
    public void setBorderSafeZone(final double borderSafeZone) {
        this.borderSafeZone = borderSafeZone;
    }
    
    public WorldSettings.GameType getGameType() {
        return this.theGameType;
    }
    
    public int getBorderWarningTime() {
        return this.borderWarningTime;
    }
    
    public void setSpawnX(final int spawnX) {
        this.spawnX = spawnX;
    }
    
    static String access$200(final WorldInfo worldInfo) {
        return worldInfo.generatorOptions;
    }
    
    public void setSpawnZ(final int spawnZ) {
        this.spawnZ = spawnZ;
    }
    
    public boolean isMapFeaturesEnabled() {
        return this.mapFeaturesEnabled;
    }
    
    static int access$800(final WorldInfo worldInfo) {
        return worldInfo.dimension;
    }
    
    public void setBorderDamagePerBlock(final double borderDamagePerBlock) {
        this.borderDamagePerBlock = borderDamagePerBlock;
    }
    
    public WorldInfo(final NBTTagCompound nbtTagCompound) {
        this.terrainType = WorldType.DEFAULT;
        this.generatorOptions = "";
        this.borderCenterX = 0.0;
        this.borderCenterZ = 0.0;
        this.borderSize = 6.0E7;
        this.borderSizeLerpTime = 0L;
        this.borderSizeLerpTarget = 0.0;
        this.borderSafeZone = 5.0;
        this.borderDamagePerBlock = 0.2;
        this.borderWarningDistance = 5;
        this.borderWarningTime = 15;
        this.theGameRules = new GameRules();
        this.randomSeed = nbtTagCompound.getLong("RandomSeed");
        if (nbtTagCompound.hasKey("generatorName", 8)) {
            this.terrainType = WorldType.parseWorldType(nbtTagCompound.getString("generatorName"));
            if (this.terrainType == null) {
                this.terrainType = WorldType.DEFAULT;
            }
            else if (this.terrainType.isVersioned()) {
                if (nbtTagCompound.hasKey("generatorVersion", 99)) {
                    nbtTagCompound.getInteger("generatorVersion");
                }
                this.terrainType = this.terrainType.getWorldTypeForGeneratorVersion(0);
            }
            if (nbtTagCompound.hasKey("generatorOptions", 8)) {
                this.generatorOptions = nbtTagCompound.getString("generatorOptions");
            }
        }
        this.theGameType = WorldSettings.GameType.getByID(nbtTagCompound.getInteger("GameType"));
        if (nbtTagCompound.hasKey("MapFeatures", 99)) {
            this.mapFeaturesEnabled = nbtTagCompound.getBoolean("MapFeatures");
        }
        else {
            this.mapFeaturesEnabled = true;
        }
        this.spawnX = nbtTagCompound.getInteger("SpawnX");
        this.spawnY = nbtTagCompound.getInteger("SpawnY");
        this.spawnZ = nbtTagCompound.getInteger("SpawnZ");
        this.totalTime = nbtTagCompound.getLong("Time");
        if (nbtTagCompound.hasKey("DayTime", 99)) {
            this.worldTime = nbtTagCompound.getLong("DayTime");
        }
        else {
            this.worldTime = this.totalTime;
        }
        this.lastTimePlayed = nbtTagCompound.getLong("LastPlayed");
        this.sizeOnDisk = nbtTagCompound.getLong("SizeOnDisk");
        this.levelName = nbtTagCompound.getString("LevelName");
        this.saveVersion = nbtTagCompound.getInteger("version");
        this.cleanWeatherTime = nbtTagCompound.getInteger("clearWeatherTime");
        this.rainTime = nbtTagCompound.getInteger("rainTime");
        this.raining = nbtTagCompound.getBoolean("raining");
        this.thunderTime = nbtTagCompound.getInteger("thunderTime");
        this.thundering = nbtTagCompound.getBoolean("thundering");
        this.hardcore = nbtTagCompound.getBoolean("hardcore");
        if (nbtTagCompound.hasKey("initialized", 99)) {
            this.initialized = nbtTagCompound.getBoolean("initialized");
        }
        else {
            this.initialized = true;
        }
        if (nbtTagCompound.hasKey("allowCommands", 99)) {
            this.allowCommands = nbtTagCompound.getBoolean("allowCommands");
        }
        else {
            this.allowCommands = (this.theGameType == WorldSettings.GameType.CREATIVE);
        }
        if (nbtTagCompound.hasKey("Player", 10)) {
            this.playerTag = nbtTagCompound.getCompoundTag("Player");
            this.dimension = this.playerTag.getInteger("Dimension");
        }
        if (nbtTagCompound.hasKey("GameRules", 10)) {
            this.theGameRules.readFromNBT(nbtTagCompound.getCompoundTag("GameRules"));
        }
        if (nbtTagCompound.hasKey("Difficulty", 99)) {
            this.difficulty = EnumDifficulty.getDifficultyEnum(nbtTagCompound.getByte("Difficulty"));
        }
        if (nbtTagCompound.hasKey("DifficultyLocked", 1)) {
            this.difficultyLocked = nbtTagCompound.getBoolean("DifficultyLocked");
        }
        if (nbtTagCompound.hasKey("BorderCenterX", 99)) {
            this.borderCenterX = nbtTagCompound.getDouble("BorderCenterX");
        }
        if (nbtTagCompound.hasKey("BorderCenterZ", 99)) {
            this.borderCenterZ = nbtTagCompound.getDouble("BorderCenterZ");
        }
        if (nbtTagCompound.hasKey("BorderSize", 99)) {
            this.borderSize = nbtTagCompound.getDouble("BorderSize");
        }
        if (nbtTagCompound.hasKey("BorderSizeLerpTime", 99)) {
            this.borderSizeLerpTime = nbtTagCompound.getLong("BorderSizeLerpTime");
        }
        if (nbtTagCompound.hasKey("BorderSizeLerpTarget", 99)) {
            this.borderSizeLerpTarget = nbtTagCompound.getDouble("BorderSizeLerpTarget");
        }
        if (nbtTagCompound.hasKey("BorderSafeZone", 99)) {
            this.borderSafeZone = nbtTagCompound.getDouble("BorderSafeZone");
        }
        if (nbtTagCompound.hasKey("BorderDamagePerBlock", 99)) {
            this.borderDamagePerBlock = nbtTagCompound.getDouble("BorderDamagePerBlock");
        }
        if (nbtTagCompound.hasKey("BorderWarningBlocks", 99)) {
            this.borderWarningDistance = nbtTagCompound.getInteger("BorderWarningBlocks");
        }
        if (nbtTagCompound.hasKey("BorderWarningTime", 99)) {
            this.borderWarningTime = nbtTagCompound.getInteger("BorderWarningTime");
        }
    }
    
    public void setDifficultyLocked(final boolean difficultyLocked) {
        this.difficultyLocked = difficultyLocked;
    }
    
    public void getBorderCenterX(final double borderCenterX) {
        this.borderCenterX = borderCenterX;
    }
    
    public boolean isInitialized() {
        return this.initialized;
    }
    
    public void setBorderWarningDistance(final int borderWarningDistance) {
        this.borderWarningDistance = borderWarningDistance;
    }
    
    static boolean access$1300(final WorldInfo worldInfo) {
        return worldInfo.thundering;
    }
    
    public int getThunderTime() {
        return this.thunderTime;
    }
    
    public int getCleanWeatherTime() {
        return this.cleanWeatherTime;
    }
    
    public double getBorderCenterZ() {
        return this.borderCenterZ;
    }
    
    public NBTTagCompound getPlayerNBTTagCompound() {
        return this.playerTag;
    }
    
    public void setBorderLerpTarget(final double borderSizeLerpTarget) {
        this.borderSizeLerpTarget = borderSizeLerpTarget;
    }
    
    public void setRainTime(final int rainTime) {
        this.rainTime = rainTime;
    }
    
    public long getWorldTime() {
        return this.worldTime;
    }
    
    public int getSpawnY() {
        return this.spawnY;
    }
    
    static int access$400(final WorldInfo worldInfo) {
        return worldInfo.spawnY;
    }
    
    public void setServerInitialized(final boolean initialized) {
        this.initialized = initialized;
    }
    
    public void populateFromWorldSettings(final WorldSettings worldSettings) {
        this.randomSeed = worldSettings.getSeed();
        this.theGameType = worldSettings.getGameType();
        this.mapFeaturesEnabled = worldSettings.isMapFeaturesEnabled();
        this.hardcore = worldSettings.getHardcoreEnabled();
        this.terrainType = worldSettings.getTerrainType();
        this.generatorOptions = worldSettings.getWorldName();
        this.allowCommands = worldSettings.areCommandsAllowed();
    }
}
