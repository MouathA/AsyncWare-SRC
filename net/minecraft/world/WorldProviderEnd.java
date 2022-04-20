package net.minecraft.world;

import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.*;
import net.minecraft.util.*;

public class WorldProviderEnd extends WorldProvider
{
    @Override
    public int getAverageGroundLevel() {
        return 50;
    }
    
    @Override
    public float[] calcSunriseSunsetColors(final float n, final float n2) {
        return null;
    }
    
    @Override
    public BlockPos getSpawnCoordinate() {
        return new BlockPos(100, 50, 0);
    }
    
    public void registerWorldChunkManager() {
        this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.sky, 0.0f);
        this.dimensionId = 1;
        this.hasNoSky = true;
    }
    
    @Override
    public float calculateCelestialAngle(final long n, final float n2) {
        return 0.0f;
    }
    
    @Override
    public String getDimensionName() {
        return "The End";
    }
    
    @Override
    public float getCloudHeight() {
        return 8.0f;
    }
    
    @Override
    public boolean isSurfaceWorld() {
        return false;
    }
    
    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderEnd(this.worldObj, this.worldObj.getSeed());
    }
    
    @Override
    public boolean canRespawnHere() {
        return false;
    }
    
    @Override
    public Vec3 getFogColor(final float n, final float n2) {
        final float clamp_float = MathHelper.clamp_float(MathHelper.cos(n * 3.1415927f * 2.0f) * 2.0f + 0.5f, 0.0f, 1.0f);
        return new Vec3(160 / 255.0f * (clamp_float * 0.0f + 0.15f), 128 / 255.0f * (clamp_float * 0.0f + 0.15f), 160 / 255.0f * (clamp_float * 0.0f + 0.15f));
    }
    
    @Override
    public String getInternalNameSuffix() {
        return "_end";
    }
    
    @Override
    public boolean isSkyColored() {
        return false;
    }
    
    @Override
    public boolean doesXZShowFog(final int n, final int n2) {
        return true;
    }
    
    @Override
    public boolean canCoordinateBeSpawn(final int n, final int n2) {
        return this.worldObj.getGroundAboveSeaLevel(new BlockPos(n, 0, n2)).getMaterial().blocksMovement();
    }
}
