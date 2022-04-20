package net.minecraft.client.audio;

public class SoundEventAccessor implements ISoundEventAccessor
{
    private final SoundPoolEntry entry;
    private final int weight;
    
    @Override
    public SoundPoolEntry cloneEntry() {
        return new SoundPoolEntry(this.entry);
    }
    
    @Override
    public int getWeight() {
        return this.weight;
    }
    
    @Override
    public Object cloneEntry() {
        return this.cloneEntry();
    }
    
    SoundEventAccessor(final SoundPoolEntry entry, final int weight) {
        this.entry = entry;
        this.weight = weight;
    }
}
