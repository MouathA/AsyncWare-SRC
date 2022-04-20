package net.minecraft.scoreboard;

import java.util.*;

public class ScoreDummyCriteria implements IScoreObjectiveCriteria
{
    private final String dummyName;
    
    @Override
    public String getName() {
        return this.dummyName;
    }
    
    public ScoreDummyCriteria(final String dummyName) {
        this.dummyName = dummyName;
        IScoreObjectiveCriteria.INSTANCES.put(dummyName, this);
    }
    
    @Override
    public EnumRenderType getRenderType() {
        return EnumRenderType.INTEGER;
    }
    
    @Override
    public int func_96635_a(final List list) {
        return 0;
    }
    
    @Override
    public boolean isReadOnly() {
        return false;
    }
}
