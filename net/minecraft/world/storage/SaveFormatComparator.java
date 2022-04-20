package net.minecraft.world.storage;

import net.minecraft.world.*;

public class SaveFormatComparator implements Comparable
{
    private final String fileName;
    private final boolean hardcore;
    private final WorldSettings.GameType theEnumGameType;
    private final long sizeOnDisk;
    private final long lastTimePlayed;
    private final String displayName;
    private final boolean cheatsEnabled;
    private final boolean requiresConversion;
    
    public String getFileName() {
        return this.fileName;
    }
    
    public boolean getCheatsEnabled() {
        return this.cheatsEnabled;
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public boolean requiresConversion() {
        return this.requiresConversion;
    }
    
    public WorldSettings.GameType getEnumGameType() {
        return this.theEnumGameType;
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((SaveFormatComparator)o);
    }
    
    public SaveFormatComparator(final String fileName, final String displayName, final long lastTimePlayed, final long sizeOnDisk, final WorldSettings.GameType theEnumGameType, final boolean requiresConversion, final boolean hardcore, final boolean cheatsEnabled) {
        this.fileName = fileName;
        this.displayName = displayName;
        this.lastTimePlayed = lastTimePlayed;
        this.sizeOnDisk = sizeOnDisk;
        this.theEnumGameType = theEnumGameType;
        this.requiresConversion = requiresConversion;
        this.hardcore = hardcore;
        this.cheatsEnabled = cheatsEnabled;
    }
    
    public long getLastTimePlayed() {
        return this.lastTimePlayed;
    }
    
    public int compareTo(final SaveFormatComparator saveFormatComparator) {
        return (this.lastTimePlayed < saveFormatComparator.lastTimePlayed) ? 1 : ((this.lastTimePlayed > saveFormatComparator.lastTimePlayed) ? -1 : this.fileName.compareTo(saveFormatComparator.fileName));
    }
    
    public boolean isHardcoreModeEnabled() {
        return this.hardcore;
    }
    
    public long getSizeOnDisk() {
        return this.sizeOnDisk;
    }
}
