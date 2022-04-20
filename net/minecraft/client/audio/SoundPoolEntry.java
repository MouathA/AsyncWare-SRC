package net.minecraft.client.audio;

import net.minecraft.util.*;

public class SoundPoolEntry
{
    private double pitch;
    private double volume;
    private final boolean streamingSound;
    private final ResourceLocation location;
    
    public SoundPoolEntry(final ResourceLocation location, final double pitch, final double volume, final boolean streamingSound) {
        this.location = location;
        this.pitch = pitch;
        this.volume = volume;
        this.streamingSound = streamingSound;
    }
    
    public void setVolume(final double volume) {
        this.volume = volume;
    }
    
    public double getPitch() {
        return this.pitch;
    }
    
    public ResourceLocation getSoundPoolEntryLocation() {
        return this.location;
    }
    
    public boolean isStreamingSound() {
        return this.streamingSound;
    }
    
    public double getVolume() {
        return this.volume;
    }
    
    public SoundPoolEntry(final SoundPoolEntry soundPoolEntry) {
        this.location = soundPoolEntry.location;
        this.pitch = soundPoolEntry.pitch;
        this.volume = soundPoolEntry.volume;
        this.streamingSound = soundPoolEntry.streamingSound;
    }
    
    public void setPitch(final double pitch) {
        this.pitch = pitch;
    }
}
