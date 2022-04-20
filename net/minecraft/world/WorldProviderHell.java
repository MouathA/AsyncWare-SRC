package net.minecraft.world;

import net.minecraft.world.biome.*;
import net.minecraft.util.*;
import net.minecraft.world.border.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.*;

public class WorldProviderHell extends WorldProvider
{
    @Override
    public boolean isSurfaceWorld() {
        return false;
    }
    
    @Override
    public String getInternalNameSuffix() {
        return "_nether";
    }
    
    public void registerWorldChunkManager() {
        this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.hell, 0.0f);
        this.isHellWorld = true;
        this.hasNoSky = true;
        this.dimensionId = -1;
    }
    
    @Override
    protected void generateLightBrightnessTable() {
        final float n = 0.1f;
        while (true) {
            final float n2 = 1.0f - 0 / 15.0f;
            this.lightBrightnessTable[0] = (1.0f - n2) / (n2 * 3.0f + 1.0f) * (1.0f - n) + n;
            int n3 = 0;
            ++n3;
        }
    }
    
    @Override
    public String getDimensionName() {
        return "Nether";
    }
    
    @Override
    public Vec3 getFogColor(final float n, final float n2) {
        return new Vec3(0.20000000298023224, 0.029999999329447746, 0.029999999329447746);
    }
    
    @Override
    public boolean doesXZShowFog(final int n, final int n2) {
        return true;
    }
    
    @Override
    public boolean canCoordinateBeSpawn(final int n, final int n2) {
        return false;
    }
    
    @Override
    public WorldBorder getWorldBorder() {
        return new WorldBorder(this) {
            final WorldProviderHell this$0;
            
            @Override
            public double getCenterZ() {
                return super.getCenterZ() / 8.0;
            }
            
            @Override
            public double getCenterX() {
                return super.getCenterX() / 8.0;
            }
        };
    }
    
    @Override
    public boolean canRespawnHere() {
        return false;
    }
    
    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderHell(this.worldObj, this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.worldObj.getSeed());
    }
    
    @Override
    public float calculateCelestialAngle(final long n, final float n2) {
        return 0.5f;
    }
}
