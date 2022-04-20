package net.minecraft.util;

public enum EnumWorldBlockLayer
{
    TRANSLUCENT("TRANSLUCENT", 3, "Translucent"), 
    CUTOUT("CUTOUT", 2, "Cutout");
    
    private final String layerName;
    
    CUTOUT_MIPPED("CUTOUT_MIPPED", 1, "Mipped Cutout"), 
    SOLID("SOLID", 0, "Solid");
    
    private static final EnumWorldBlockLayer[] $VALUES;
    
    private EnumWorldBlockLayer(final String s, final int n, final String layerName) {
        this.layerName = layerName;
    }
    
    @Override
    public String toString() {
        return this.layerName;
    }
    
    static {
        $VALUES = new EnumWorldBlockLayer[] { EnumWorldBlockLayer.SOLID, EnumWorldBlockLayer.CUTOUT_MIPPED, EnumWorldBlockLayer.CUTOUT, EnumWorldBlockLayer.TRANSLUCENT };
    }
}
