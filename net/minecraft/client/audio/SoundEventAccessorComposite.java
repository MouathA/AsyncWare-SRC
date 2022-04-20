package net.minecraft.client.audio;

import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;

public class SoundEventAccessorComposite implements ISoundEventAccessor
{
    private double eventPitch;
    private final ResourceLocation soundLocation;
    private final List soundPool;
    private final Random rnd;
    private final SoundCategory category;
    private double eventVolume;
    
    @Override
    public Object cloneEntry() {
        return this.cloneEntry();
    }
    
    public SoundCategory getSoundCategory() {
        return this.category;
    }
    
    public SoundEventAccessorComposite(final ResourceLocation soundLocation, final double eventPitch, final double eventVolume, final SoundCategory category) {
        this.soundPool = Lists.newArrayList();
        this.rnd = new Random();
        this.soundLocation = soundLocation;
        this.eventVolume = eventVolume;
        this.eventPitch = eventPitch;
        this.category = category;
    }
    
    public ResourceLocation getSoundEventLocation() {
        return this.soundLocation;
    }
    
    @Override
    public int getWeight() {
        final Iterator<ISoundEventAccessor> iterator = this.soundPool.iterator();
        while (iterator.hasNext()) {
            final int n = 0 + iterator.next().getWeight();
        }
        return 0;
    }
    
    @Override
    public SoundPoolEntry cloneEntry() {
        final int weight = this.getWeight();
        if (!this.soundPool.isEmpty() && weight != 0) {
            int nextInt = this.rnd.nextInt(weight);
            for (final ISoundEventAccessor soundEventAccessor : this.soundPool) {
                nextInt -= soundEventAccessor.getWeight();
                if (nextInt < 0) {
                    final SoundPoolEntry soundPoolEntry = (SoundPoolEntry)soundEventAccessor.cloneEntry();
                    soundPoolEntry.setPitch(soundPoolEntry.getPitch() * this.eventPitch);
                    soundPoolEntry.setVolume(soundPoolEntry.getVolume() * this.eventVolume);
                    return soundPoolEntry;
                }
            }
            return SoundHandler.missing_sound;
        }
        return SoundHandler.missing_sound;
    }
    
    public void addSoundToEventPool(final ISoundEventAccessor soundEventAccessor) {
        this.soundPool.add(soundEventAccessor);
    }
}
