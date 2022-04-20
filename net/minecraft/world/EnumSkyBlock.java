package net.minecraft.world;

public enum EnumSkyBlock
{
    private static final EnumSkyBlock[] $VALUES;
    
    BLOCK("BLOCK", 1, 0);
    
    public final int defaultLightValue;
    
    SKY("SKY", 0, 15);
    
    static {
        $VALUES = new EnumSkyBlock[] { EnumSkyBlock.SKY, EnumSkyBlock.BLOCK };
    }
    
    private EnumSkyBlock(final String s, final int n, final int defaultLightValue) {
        this.defaultLightValue = defaultLightValue;
    }
}
