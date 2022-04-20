package net.minecraft.world.chunk.storage;

public class NibbleArrayReader
{
    private final int depthBitsPlusFour;
    private final int depthBits;
    public final byte[] data;
    
    public int get(final int n, final int n2, final int n3) {
        final int n4 = n << this.depthBitsPlusFour | n3 << this.depthBits | n2;
        final int n5 = n4 >> 1;
        return ((n4 & 0x1) == 0x0) ? (this.data[n5] & 0xF) : (this.data[n5] >> 4 & 0xF);
    }
    
    public NibbleArrayReader(final byte[] data, final int depthBits) {
        this.data = data;
        this.depthBits = depthBits;
        this.depthBitsPlusFour = depthBits + 4;
    }
}
