package net.minecraft.scoreboard;

import net.minecraft.util.*;
import java.util.*;

public class GoalColor implements IScoreObjectiveCriteria
{
    private final String goalName;
    
    @Override
    public String getName() {
        return this.goalName;
    }
    
    public GoalColor(final String s, final EnumChatFormatting enumChatFormatting) {
        this.goalName = s + enumChatFormatting.getFriendlyName();
        IScoreObjectiveCriteria.INSTANCES.put(this.goalName, this);
    }
    
    @Override
    public int func_96635_a(final List list) {
        return 0;
    }
    
    @Override
    public EnumRenderType getRenderType() {
        return EnumRenderType.INTEGER;
    }
    
    @Override
    public boolean isReadOnly() {
        return false;
    }
}
