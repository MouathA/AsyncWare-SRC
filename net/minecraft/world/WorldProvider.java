package net.minecraft.world;

import net.minecraft.world.biome.*;
import net.minecraft.init.*;
import net.minecraft.world.border.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.*;
import net.minecraft.util.*;

public abstract class WorldProvider
{
    private final float[] colorsSunriseSunset;
    protected boolean hasNoSky;
    public static final float[] moonPhaseFactors;
    private String generatorSettings;
    protected int dimensionId;
    private WorldType terrainType;
    protected boolean isHellWorld;
    protected World worldObj;
    protected WorldChunkManager worldChunkMgr;
    protected final float[] lightBrightnessTable;
    
    public WorldProvider() {
        this.lightBrightnessTable = new float[16];
        this.colorsSunriseSunset = new float[4];
    }
    
    public BlockPos getSpawnCoordinate() {
        return null;
    }
    
    public int getMoonPhase(final long n) {
        return (int)(n / 24000L % 8L + 8L) % 8;
    }
    
    public float calculateCelestialAngle(final long n, final float n2) {
        float n3 = ((int)(n % 24000L) + n2) / 24000.0f - 0.25f;
        if (n3 < 0.0f) {
            ++n3;
        }
        if (n3 > 1.0f) {
            --n3;
        }
        final float n4 = 1.0f - (float)((Math.cos(n3 * 3.141592653589793) + 1.0) / 2.0);
        return n4 + (n4 - n4) / 3.0f;
    }
    
    protected void registerWorldChunkManager() {
        final WorldType terrainType = this.worldObj.getWorldInfo().getTerrainType();
        if (terrainType == WorldType.FLAT) {
            this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.getBiomeFromBiomeList(FlatGeneratorInfo.createFlatGeneratorFromString(this.worldObj.getWorldInfo().getGeneratorOptions()).getBiome(), BiomeGenBase.field_180279_ad), 0.5f);
        }
        else if (terrainType == WorldType.DEBUG_WORLD) {
            this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.plains, 0.0f);
        }
        else {
            this.worldChunkMgr = new WorldChunkManager(this.worldObj);
        }
    }
    
    static {
        moonPhaseFactors = new float[] { 1.0f, 0.75f, 0.5f, 0.25f, 0.0f, 0.25f, 0.5f, 0.75f };
    }
    
    public boolean canCoordinateBeSpawn(final int n, final int n2) {
        return this.worldObj.getGroundAboveSeaLevel(new BlockPos(n, 0, n2)) == Blocks.grass;
    }
    
    public int getAverageGroundLevel() {
        return (this.terrainType == WorldType.FLAT) ? 4 : (this.worldObj.func_181545_F() + 1);
    }
    
    public boolean canRespawnHere() {
        return true;
    }
    
    public float[] getLightBrightnessTable() {
        return this.lightBrightnessTable;
    }
    
    public abstract String getDimensionName();
    
    public final void registerWorld(final World worldObj) {
        this.worldObj = worldObj;
        this.terrainType = worldObj.getWorldInfo().getTerrainType();
        this.generatorSettings = worldObj.getWorldInfo().getGeneratorOptions();
        this.registerWorldChunkManager();
        this.generateLightBrightnessTable();
    }
    
    public static WorldProvider getProviderForDimension(final int n) {
        return (n == -1) ? new WorldProviderHell() : ((n == 0) ? new WorldProviderSurface() : ((n == 1) ? new WorldProviderEnd() : null));
    }
    
    public WorldBorder getWorldBorder() {
        return new WorldBorder();
    }
    
    public boolean isSurfaceWorld() {
        return true;
    }
    
    public boolean isSkyColored() {
        return true;
    }
    
    public WorldChunkManager getWorldChunkManager() {
        return this.worldChunkMgr;
    }
    
    public boolean doesXZShowFog(final int n, final int n2) {
        return false;
    }
    
    public float getCloudHeight() {
        return 128.0f;
    }
    
    public IChunkProvider createChunkGenerator() {
        return (this.terrainType == WorldType.FLAT) ? new ChunkProviderFlat(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.generatorSettings) : ((this.terrainType == WorldType.DEBUG_WORLD) ? new ChunkProviderDebug(this.worldObj) : ((this.terrainType == WorldType.CUSTOMIZED) ? new ChunkProviderGenerate(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.generatorSettings) : new ChunkProviderGenerate(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.generatorSettings)));
    }
    
    public int getDimensionId() {
        return this.dimensionId;
    }
    
    protected void generateLightBrightnessTable() {
        final float n = 0.0f;
        while (true) {
            final float n2 = 1.0f - 0 / 15.0f;
            this.lightBrightnessTable[0] = (1.0f - n2) / (n2 * 3.0f + 1.0f) * (1.0f - n) + n;
            int n3 = 0;
            ++n3;
        }
    }
    
    public boolean getHasNoSky() {
        return this.hasNoSky;
    }
    
    public Vec3 getFogColor(final float n, final float n2) {
        final float clamp_float = MathHelper.clamp_float(MathHelper.cos(n * 3.1415927f * 2.0f) * 2.0f + 0.5f, 0.0f, 1.0f);
        return new Vec3(0.7529412f * (clamp_float * 0.94f + 0.06f), 0.84705883f * (clamp_float * 0.94f + 0.06f), 1.0f * (clamp_float * 0.91f + 0.09f));
    }
    
    public abstract String getInternalNameSuffix();
    
    public boolean doesWaterVaporize() {
        return this.isHellWorld;
    }
    
    public double getVoidFogYFactor() {
        return (this.terrainType == WorldType.FLAT) ? 1.0 : 0.03125;
    }
    
    public float[] calcSunriseSunsetColors(final float n, final float n2) {
        final float n3 = 0.4f;
        final float n4 = MathHelper.cos(n * 3.1415927f * 2.0f) - 0.0f;
        final float n5 = -0.0f;
        if (n4 >= n5 - n3 && n4 <= n5 + n3) {
            final float n6 = (n4 - n5) / n3 * 0.5f + 0.5f;
            final float n7 = 1.0f - (1.0f - MathHelper.sin(n6 * 3.1415927f)) * 0.99f;
            final float n8 = n7 * n7;
            this.colorsSunriseSunset[0] = n6 * 0.3f + 0.7f;
            this.colorsSunriseSunset[1] = n6 * n6 * 0.7f + 0.2f;
            this.colorsSunriseSunset[2] = n6 * n6 * 0.0f + 0.2f;
            this.colorsSunriseSunset[3] = n8;
            return this.colorsSunriseSunset;
        }
        return null;
    }
}
