package net.minecraft.world.chunk;

public class NibbleArray
{
    private final byte[] data;
    
    public NibbleArray(final byte[] data) {
        this.data = data;
        if (data.length != 2048) {
            throw new IllegalArgumentException("ChunkNibbleArrays should be 2048 bytes not: " + data.length);
        }
    }
    
    public void setIndex(final int n, final int n2) {
        final int nibbleIndex = this.getNibbleIndex(n);
        if (n == 0) {
            this.data[nibbleIndex] = (byte)((this.data[nibbleIndex] & 0xF0) | (n2 & 0xF));
        }
        else {
            this.data[nibbleIndex] = (byte)((this.data[nibbleIndex] & 0xF) | (n2 & 0xF) << 4);
        }
    }
    
    public byte[] getData() {
        return this.data;
    }
    
    public int get(final int n, final int n2, final int n3) {
        return this.getFromIndex(this.getCoordinateIndex(n, n2, n3));
    }
    
    private int getCoordinateIndex(final int n, final int n2, final int n3) {
        return n2 << 8 | n3 << 4 | n;
    }
    
    private int getNibbleIndex(final int n) {
        return n >> 1;
    }
    
    public NibbleArray() {
        this.data = new byte[2048];
    }
    
    public void set(final int n, final int n2, final int n3, final int n4) {
        this.setIndex(this.getCoordinateIndex(n, n2, n3), n4);
    }
    
    public int getFromIndex(final int n) {
        final int nibbleIndex = this.getNibbleIndex(n);
        return (n == 0) ? (this.data[nibbleIndex] & 0xF) : (this.data[nibbleIndex] >> 4 & 0xF);
    }
}
