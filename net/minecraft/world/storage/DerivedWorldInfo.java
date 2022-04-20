package net.minecraft.world.storage;

import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class DerivedWorldInfo extends WorldInfo
{
    private final WorldInfo theWorldInfo;
    
    @Override
    public void setTerrainType(final WorldType worldType) {
    }
    
    @Override
    public void setDifficultyLocked(final boolean b) {
    }
    
    @Override
    public boolean isMapFeaturesEnabled() {
        return this.theWorldInfo.isMapFeaturesEnabled();
    }
    
    @Override
    public long getLastTimePlayed() {
        return this.theWorldInfo.getLastTimePlayed();
    }
    
    @Override
    public int getSpawnY() {
        return this.theWorldInfo.getSpawnY();
    }
    
    @Override
    public void setThunderTime(final int n) {
    }
    
    @Override
    public void setServerInitialized(final boolean b) {
    }
    
    @Override
    public void setWorldTime(final long n) {
    }
    
    @Override
    public void setAllowCommands(final boolean b) {
    }
    
    @Override
    public void setWorldName(final String s) {
    }
    
    @Override
    public NBTTagCompound getNBTTagCompound() {
        return this.theWorldInfo.getNBTTagCompound();
    }
    
    @Override
    public int getRainTime() {
        return this.theWorldInfo.getRainTime();
    }
    
    @Override
    public int getSpawnX() {
        return this.theWorldInfo.getSpawnX();
    }
    
    @Override
    public long getSizeOnDisk() {
        return this.theWorldInfo.getSizeOnDisk();
    }
    
    @Override
    public long getWorldTotalTime() {
        return this.theWorldInfo.getWorldTotalTime();
    }
    
    @Override
    public void setSpawnY(final int n) {
    }
    
    @Override
    public WorldSettings.GameType getGameType() {
        return this.theWorldInfo.getGameType();
    }
    
    @Override
    public WorldType getTerrainType() {
        return this.theWorldInfo.getTerrainType();
    }
    
    @Override
    public String getWorldName() {
        return this.theWorldInfo.getWorldName();
    }
    
    @Override
    public void setDifficulty(final EnumDifficulty enumDifficulty) {
    }
    
    @Override
    public void setWorldTotalTime(final long n) {
    }
    
    @Override
    public void setSaveVersion(final int n) {
    }
    
    @Override
    public boolean isInitialized() {
        return this.theWorldInfo.isInitialized();
    }
    
    @Override
    public void setSpawn(final BlockPos blockPos) {
    }
    
    @Override
    public int getSpawnZ() {
        return this.theWorldInfo.getSpawnZ();
    }
    
    @Override
    public int getThunderTime() {
        return this.theWorldInfo.getThunderTime();
    }
    
    @Override
    public void setSpawnX(final int n) {
    }
    
    @Override
    public void setSpawnZ(final int n) {
    }
    
    @Override
    public boolean isDifficultyLocked() {
        return this.theWorldInfo.isDifficultyLocked();
    }
    
    @Override
    public boolean areCommandsAllowed() {
        return this.theWorldInfo.areCommandsAllowed();
    }
    
    @Override
    public boolean isHardcoreModeEnabled() {
        return this.theWorldInfo.isHardcoreModeEnabled();
    }
    
    @Override
    public long getWorldTime() {
        return this.theWorldInfo.getWorldTime();
    }
    
    @Override
    public GameRules getGameRulesInstance() {
        return this.theWorldInfo.getGameRulesInstance();
    }
    
    @Override
    public boolean isRaining() {
        return this.theWorldInfo.isRaining();
    }
    
    @Override
    public long getSeed() {
        return this.theWorldInfo.getSeed();
    }
    
    @Override
    public void setThundering(final boolean b) {
    }
    
    @Override
    public void setRainTime(final int n) {
    }
    
    @Override
    public void setRaining(final boolean b) {
    }
    
    @Override
    public NBTTagCompound getPlayerNBTTagCompound() {
        return this.theWorldInfo.getPlayerNBTTagCompound();
    }
    
    @Override
    public boolean isThundering() {
        return this.theWorldInfo.isThundering();
    }
    
    @Override
    public NBTTagCompound cloneNBTCompound(final NBTTagCompound nbtTagCompound) {
        return this.theWorldInfo.cloneNBTCompound(nbtTagCompound);
    }
    
    @Override
    public int getSaveVersion() {
        return this.theWorldInfo.getSaveVersion();
    }
    
    public DerivedWorldInfo(final WorldInfo theWorldInfo) {
        this.theWorldInfo = theWorldInfo;
    }
    
    @Override
    public EnumDifficulty getDifficulty() {
        return this.theWorldInfo.getDifficulty();
    }
}
