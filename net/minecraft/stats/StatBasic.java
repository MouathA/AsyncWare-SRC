package net.minecraft.stats;

import net.minecraft.util.*;

public class StatBasic extends StatBase
{
    @Override
    public StatBase registerStat() {
        super.registerStat();
        StatList.generalStats.add(this);
        return this;
    }
    
    public StatBasic(final String s, final IChatComponent chatComponent) {
        super(s, chatComponent);
    }
    
    public StatBasic(final String s, final IChatComponent chatComponent, final IStatType statType) {
        super(s, chatComponent, statType);
    }
}
