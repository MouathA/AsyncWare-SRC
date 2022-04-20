package net.minecraft.world;

public class WorldType
{
    public static final WorldType AMPLIFIED;
    private boolean hasNotificationData;
    public static final WorldType CUSTOMIZED;
    public static final WorldType DEFAULT;
    public static final WorldType FLAT;
    public static final WorldType DEBUG_WORLD;
    private final int generatorVersion;
    private boolean canBeCreated;
    private final String worldType;
    private final int worldTypeId;
    public static final WorldType[] worldTypes;
    public static final WorldType LARGE_BIOMES;
    private boolean isWorldTypeVersioned;
    public static final WorldType DEFAULT_1_1;
    
    static {
        worldTypes = new WorldType[16];
        DEFAULT = new WorldType(0, "default", 1).setVersioned();
        FLAT = new WorldType(1, "flat");
        LARGE_BIOMES = new WorldType(2, "largeBiomes");
        AMPLIFIED = new WorldType(3, "amplified").setNotificationData();
        CUSTOMIZED = new WorldType(4, "customized");
        DEBUG_WORLD = new WorldType(5, "debug_all_block_states");
        DEFAULT_1_1 = new WorldType(8, "default_1_1", 0).setCanBeCreated(false);
    }
    
    private WorldType setCanBeCreated(final boolean canBeCreated) {
        this.canBeCreated = canBeCreated;
        return this;
    }
    
    public int getWorldTypeID() {
        return this.worldTypeId;
    }
    
    public int getGeneratorVersion() {
        return this.generatorVersion;
    }
    
    private WorldType setNotificationData() {
        this.hasNotificationData = true;
        return this;
    }
    
    public static WorldType parseWorldType(final String s) {
        while (0 < WorldType.worldTypes.length) {
            if (WorldType.worldTypes[0] != null && WorldType.worldTypes[0].worldType.equalsIgnoreCase(s)) {
                return WorldType.worldTypes[0];
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    public boolean isVersioned() {
        return this.isWorldTypeVersioned;
    }
    
    public String func_151359_c() {
        return this.getTranslateName() + ".info";
    }
    
    public String getWorldTypeName() {
        return this.worldType;
    }
    
    private WorldType(final int worldTypeId, final String worldType, final int generatorVersion) {
        this.worldType = worldType;
        this.generatorVersion = generatorVersion;
        this.canBeCreated = true;
        this.worldTypeId = worldTypeId;
        WorldType.worldTypes[worldTypeId] = this;
    }
    
    public String getTranslateName() {
        return "generator." + this.worldType;
    }
    
    public boolean getCanBeCreated() {
        return this.canBeCreated;
    }
    
    public WorldType getWorldTypeForGeneratorVersion(final int n) {
        return (this == WorldType.DEFAULT && n == 0) ? WorldType.DEFAULT_1_1 : this;
    }
    
    public boolean showWorldInfoNotice() {
        return this.hasNotificationData;
    }
    
    private WorldType setVersioned() {
        this.isWorldTypeVersioned = true;
        return this;
    }
    
    private WorldType(final int n, final String s) {
        this(n, s, 0);
    }
}
