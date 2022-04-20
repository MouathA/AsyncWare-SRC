package net.minecraft.nbt;

public class NBTSizeTracker
{
    public static final NBTSizeTracker INFINITE;
    private long read;
    private final long max;
    
    static {
        INFINITE = new NBTSizeTracker(0L) {
            @Override
            public void read(final long n) {
            }
        };
    }
    
    public NBTSizeTracker(final long max) {
        this.max = max;
    }
    
    public void read(final long n) {
        this.read += n / 8L;
        if (this.read > this.max) {
            throw new RuntimeException("Tried to read NBT tag that was too big; tried to allocate: " + this.read + "bytes where max allowed: " + this.max);
        }
    }
}
