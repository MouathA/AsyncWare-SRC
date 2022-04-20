package net.minecraft.world.border;

public enum EnumBorderStatus
{
    STATIONARY("STATIONARY", 2, 2138367);
    
    private final int id;
    
    GROWING("GROWING", 0, 4259712);
    
    private static final EnumBorderStatus[] $VALUES;
    
    SHRINKING("SHRINKING", 1, 16724016);
    
    static {
        $VALUES = new EnumBorderStatus[] { EnumBorderStatus.GROWING, EnumBorderStatus.SHRINKING, EnumBorderStatus.STATIONARY };
    }
    
    public int getID() {
        return this.id;
    }
    
    private EnumBorderStatus(final String s, final int n, final int id) {
        this.id = id;
    }
}
