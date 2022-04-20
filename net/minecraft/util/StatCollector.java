package net.minecraft.util;

public class StatCollector
{
    private static StringTranslate fallbackTranslator;
    private static StringTranslate localizedName;
    
    public static String translateToFallback(final String s) {
        return StatCollector.fallbackTranslator.translateKey(s);
    }
    
    public static long getLastTranslationUpdateTimeInMilliseconds() {
        return StatCollector.localizedName.getLastUpdateTimeInMilliseconds();
    }
    
    static {
        StatCollector.localizedName = StringTranslate.getInstance();
        StatCollector.fallbackTranslator = new StringTranslate();
    }
    
    public static String translateToLocal(final String s) {
        return StatCollector.localizedName.translateKey(s);
    }
    
    public static String translateToLocalFormatted(final String s, final Object... array) {
        return StatCollector.localizedName.translateKeyFormat(s, array);
    }
    
    public static boolean canTranslate(final String s) {
        return StatCollector.localizedName.isKeyTranslated(s);
    }
}
