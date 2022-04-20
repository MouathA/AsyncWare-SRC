package net.minecraft.world;

public class ColorizerGrass
{
    public static void setGrassBiomeColorizer(final int[] grassBuffer) {
        ColorizerGrass.grassBuffer = grassBuffer;
    }
    
    public static int getGrassColor(final double n, double n2) {
        n2 *= n;
        final int n3 = (int)((1.0 - n2) * 255.0) << 8 | (int)((1.0 - n) * 255.0);
        return (n3 > ColorizerGrass.grassBuffer.length) ? -65281 : ColorizerGrass.grassBuffer[n3];
    }
    
    static {
        ColorizerGrass.grassBuffer = new int[65536];
    }
}
