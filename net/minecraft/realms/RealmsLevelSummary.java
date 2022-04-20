package net.minecraft.realms;

import net.minecraft.world.storage.*;

public class RealmsLevelSummary implements Comparable
{
    private SaveFormatComparator levelSummary;
    
    public long getSizeOnDisk() {
        return this.levelSummary.getSizeOnDisk();
    }
    
    public String getLevelName() {
        return this.levelSummary.getDisplayName();
    }
    
    public boolean isHardcore() {
        return this.levelSummary.isHardcoreModeEnabled();
    }
    
    public int compareTo(final RealmsLevelSummary realmsLevelSummary) {
        return (this.levelSummary.getLastTimePlayed() < realmsLevelSummary.getLastPlayed()) ? 1 : ((this.levelSummary.getLastTimePlayed() > realmsLevelSummary.getLastPlayed()) ? -1 : this.levelSummary.getFileName().compareTo(realmsLevelSummary.getLevelId()));
    }
    
    public int getGameMode() {
        return this.levelSummary.getEnumGameType().getID();
    }
    
    public String getLevelId() {
        return this.levelSummary.getFileName();
    }
    
    public boolean isRequiresConversion() {
        return this.levelSummary.requiresConversion();
    }
    
    public boolean hasCheats() {
        return this.levelSummary.getCheatsEnabled();
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((RealmsLevelSummary)o);
    }
    
    public RealmsLevelSummary(final SaveFormatComparator levelSummary) {
        this.levelSummary = levelSummary;
    }
    
    public long getLastPlayed() {
        return this.levelSummary.getLastTimePlayed();
    }
    
    public int compareTo(final SaveFormatComparator saveFormatComparator) {
        return this.levelSummary.compareTo(saveFormatComparator);
    }
}
