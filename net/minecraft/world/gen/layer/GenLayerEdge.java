package net.minecraft.world.gen.layer;

public class GenLayerEdge extends GenLayer
{
    private final Mode field_151627_c;
    
    private int[] getIntsHeatIce(final int n, final int n2, final int n3, final int n4) {
        final int n5 = n - 1;
        final int n6 = n2 - 1;
        final int n7 = 1 + n3 + 1;
        final int[] ints = this.parent.getInts(n5, n6, n7, 1 + n4 + 1);
        final int[] intCache = IntCache.getIntCache(n3 * n4);
        while (0 < n4) {
            while (0 < n3) {
                final int n8 = ints[1 + 1 * n7];
                intCache[0 + 0 * n3] = 3;
                int n9 = 0;
                ++n9;
            }
            int n10 = 0;
            ++n10;
        }
        return intCache;
    }
    
    private int[] getIntsSpecial(final int n, final int n2, final int n3, final int n4) {
        final int[] ints = this.parent.getInts(n, n2, n3, n4);
        final int[] intCache = IntCache.getIntCache(n3 * n4);
        while (0 < n4) {
            while (0 < n3) {
                this.initChunkSeed(0 + n, 0 + n2);
                int n5 = ints[0 + 0 * n3];
                if (n5 != 0 && this.nextInt(13) == 0) {
                    n5 |= (1 + this.nextInt(15) << 8 & 0xF00);
                }
                intCache[0 + 0 * n3] = n5;
                int n6 = 0;
                ++n6;
            }
            int n7 = 0;
            ++n7;
        }
        return intCache;
    }
    
    private int[] getIntsCoolWarm(final int n, final int n2, final int n3, final int n4) {
        final int n5 = n - 1;
        final int n6 = n2 - 1;
        final int n7 = 1 + n3 + 1;
        final int[] ints = this.parent.getInts(n5, n6, n7, 1 + n4 + 1);
        final int[] intCache = IntCache.getIntCache(n3 * n4);
        while (0 < n4) {
            while (0 < n3) {
                this.initChunkSeed(0 + n, 0 + n2);
                final int n8 = ints[1 + 1 * n7];
                intCache[0 + 0 * n3] = 2;
                int n9 = 0;
                ++n9;
            }
            int n10 = 0;
            ++n10;
        }
        return intCache;
    }
    
    @Override
    public int[] getInts(final int n, final int n2, final int n3, final int n4) {
        switch (GenLayerEdge$1.$SwitchMap$net$minecraft$world$gen$layer$GenLayerEdge$Mode[this.field_151627_c.ordinal()]) {
            default: {
                return this.getIntsCoolWarm(n, n2, n3, n4);
            }
            case 2: {
                return this.getIntsHeatIce(n, n2, n3, n4);
            }
            case 3: {
                return this.getIntsSpecial(n, n2, n3, n4);
            }
        }
    }
    
    public GenLayerEdge(final long n, final GenLayer parent, final Mode field_151627_c) {
        super(n);
        this.parent = parent;
        this.field_151627_c = field_151627_c;
    }
    
    public enum Mode
    {
        COOL_WARM("COOL_WARM", 0);
        
        private static final Mode[] $VALUES;
        
        SPECIAL("SPECIAL", 2), 
        HEAT_ICE("HEAT_ICE", 1);
        
        private Mode(final String s, final int n) {
        }
        
        static {
            $VALUES = new Mode[] { Mode.COOL_WARM, Mode.HEAT_ICE, Mode.SPECIAL };
        }
    }
}
