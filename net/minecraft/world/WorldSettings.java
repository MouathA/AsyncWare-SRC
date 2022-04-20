package net.minecraft.world;

import net.minecraft.world.storage.*;
import net.minecraft.entity.player.*;

public final class WorldSettings
{
    private final boolean hardcoreEnabled;
    private boolean bonusChestEnabled;
    private final boolean mapFeaturesEnabled;
    private final GameType theGameType;
    private final long seed;
    private final WorldType terrainType;
    private boolean commandsAllowed;
    private String worldName;
    
    public static GameType getGameTypeById(final int n) {
        return GameType.getByID(n);
    }
    
    public WorldSettings(final long seed, final GameType theGameType, final boolean mapFeaturesEnabled, final boolean hardcoreEnabled, final WorldType terrainType) {
        this.worldName = "";
        this.seed = seed;
        this.theGameType = theGameType;
        this.mapFeaturesEnabled = mapFeaturesEnabled;
        this.hardcoreEnabled = hardcoreEnabled;
        this.terrainType = terrainType;
    }
    
    public GameType getGameType() {
        return this.theGameType;
    }
    
    public boolean getHardcoreEnabled() {
        return this.hardcoreEnabled;
    }
    
    public boolean areCommandsAllowed() {
        return this.commandsAllowed;
    }
    
    public String getWorldName() {
        return this.worldName;
    }
    
    public WorldSettings enableBonusChest() {
        this.bonusChestEnabled = true;
        return this;
    }
    
    public WorldType getTerrainType() {
        return this.terrainType;
    }
    
    public WorldSettings setWorldName(final String worldName) {
        this.worldName = worldName;
        return this;
    }
    
    public boolean isMapFeaturesEnabled() {
        return this.mapFeaturesEnabled;
    }
    
    public WorldSettings enableCommands() {
        this.commandsAllowed = true;
        return this;
    }
    
    public WorldSettings(final WorldInfo worldInfo) {
        this(worldInfo.getSeed(), worldInfo.getGameType(), worldInfo.isMapFeaturesEnabled(), worldInfo.isHardcoreModeEnabled(), worldInfo.getTerrainType());
    }
    
    public boolean isBonusChestEnabled() {
        return this.bonusChestEnabled;
    }
    
    public long getSeed() {
        return this.seed;
    }
    
    public enum GameType
    {
        SURVIVAL("SURVIVAL", 1, 0, "survival");
        
        int id;
        
        NOT_SET("NOT_SET", 0, -1, ""), 
        ADVENTURE("ADVENTURE", 3, 2, "adventure"), 
        SPECTATOR("SPECTATOR", 4, 3, "spectator"), 
        CREATIVE("CREATIVE", 2, 1, "creative");
        
        private static final GameType[] $VALUES;
        String name;
        
        public static GameType getByID(final int n) {
            final GameType[] values = values();
            while (0 < values.length) {
                final GameType gameType = values[0];
                if (gameType.id == n) {
                    return gameType;
                }
                int n2 = 0;
                ++n2;
            }
            return GameType.SURVIVAL;
        }
        
        public String getName() {
            return this.name;
        }
        
        static {
            $VALUES = new GameType[] { GameType.NOT_SET, GameType.SURVIVAL, GameType.CREATIVE, GameType.ADVENTURE, GameType.SPECTATOR };
        }
        
        public int getID() {
            return this.id;
        }
        
        public boolean isCreative() {
            return this == GameType.CREATIVE;
        }
        
        public boolean isSurvivalOrAdventure() {
            return this == GameType.SURVIVAL || this == GameType.ADVENTURE;
        }
        
        private GameType(final String s, final int n, final int id, final String name) {
            this.id = id;
            this.name = name;
        }
        
        public void configurePlayerCapabilities(final PlayerCapabilities p0) {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     1: getstatic       net/minecraft/world/WorldSettings$GameType.CREATIVE:Lnet/minecraft/world/WorldSettings$GameType;
            //     4: if_acmpne       25
            //     7: aload_1        
            //     8: iconst_1       
            //     9: putfield        net/minecraft/entity/player/PlayerCapabilities.allowFlying:Z
            //    12: aload_1        
            //    13: iconst_1       
            //    14: putfield        net/minecraft/entity/player/PlayerCapabilities.isCreativeMode:Z
            //    17: aload_1        
            //    18: iconst_1       
            //    19: putfield        net/minecraft/entity/player/PlayerCapabilities.disableDamage:Z
            //    22: goto            75
            //    25: aload_0        
            //    26: getstatic       net/minecraft/world/WorldSettings$GameType.SPECTATOR:Lnet/minecraft/world/WorldSettings$GameType;
            //    29: if_acmpne       55
            //    32: aload_1        
            //    33: iconst_1       
            //    34: putfield        net/minecraft/entity/player/PlayerCapabilities.allowFlying:Z
            //    37: aload_1        
            //    38: iconst_0       
            //    39: putfield        net/minecraft/entity/player/PlayerCapabilities.isCreativeMode:Z
            //    42: aload_1        
            //    43: iconst_1       
            //    44: putfield        net/minecraft/entity/player/PlayerCapabilities.disableDamage:Z
            //    47: aload_1        
            //    48: iconst_1       
            //    49: putfield        net/minecraft/entity/player/PlayerCapabilities.isFlying:Z
            //    52: goto            75
            //    55: aload_1        
            //    56: iconst_0       
            //    57: putfield        net/minecraft/entity/player/PlayerCapabilities.allowFlying:Z
            //    60: aload_1        
            //    61: iconst_0       
            //    62: putfield        net/minecraft/entity/player/PlayerCapabilities.isCreativeMode:Z
            //    65: aload_1        
            //    66: iconst_0       
            //    67: putfield        net/minecraft/entity/player/PlayerCapabilities.disableDamage:Z
            //    70: aload_1        
            //    71: iconst_0       
            //    72: putfield        net/minecraft/entity/player/PlayerCapabilities.isFlying:Z
            //    75: aload_1        
            //    76: aload_0        
            //    77: if_acmpeq       84
            //    80: iconst_1       
            //    81: goto            85
            //    84: iconst_0       
            //    85: putfield        net/minecraft/entity/player/PlayerCapabilities.allowEdit:Z
            //    88: return         
            // 
            // The error that occurred was:
            // 
            // java.lang.ArrayIndexOutOfBoundsException
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        public static GameType getByName(final String s) {
            final GameType[] values = values();
            while (0 < values.length) {
                final GameType gameType = values[0];
                if (gameType.name.equals(s)) {
                    return gameType;
                }
                int n = 0;
                ++n;
            }
            return GameType.SURVIVAL;
        }
    }
}
