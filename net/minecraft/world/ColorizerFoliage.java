package net.minecraft.world;

public class ColorizerFoliage
{
    public static int getFoliageColorBirch() {
        return 8431445;
    }
    
    public static int getFoliageColorBasic() {
        return 4764952;
    }
    
    static {
        ColorizerFoliage.foliageBuffer = new int[65536];
    }
    
    public static int getFoliageColor(final double n, double n2) {
        n2 *= n;
        return ColorizerFoliage.foliageBuffer[(int)((1.0 - n2) * 255.0) << 8 | (int)((1.0 - n) * 255.0)];
    }
    
    public static int getFoliageColorPine() {
        return 6396257;
    }
    
    public static void setFoliageBiomeColorizer(final int[] foliageBuffer) {
        ColorizerFoliage.foliageBuffer = foliageBuffer;
    }
}
