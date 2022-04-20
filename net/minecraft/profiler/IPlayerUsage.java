package net.minecraft.profiler;

public interface IPlayerUsage
{
    boolean isSnooperEnabled();
    
    void addServerStatsToSnooper(final PlayerUsageSnooper p0);
    
    void addServerTypeToSnooper(final PlayerUsageSnooper p0);
}
