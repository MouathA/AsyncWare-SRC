package net.minecraft.entity.boss;

public final class BossStatus
{
    public static int statusBarTime;
    public static float healthScale;
    public static boolean hasColorModifier;
    public static String bossName;
    
    public static void setBossStatus(final IBossDisplayData bossDisplayData, final boolean hasColorModifier) {
        BossStatus.healthScale = bossDisplayData.getHealth() / bossDisplayData.getMaxHealth();
        BossStatus.statusBarTime = 100;
        BossStatus.bossName = bossDisplayData.getDisplayName().getFormattedText();
        BossStatus.hasColorModifier = hasColorModifier;
    }
}
