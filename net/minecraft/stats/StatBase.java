package net.minecraft.stats;

import java.text.*;
import net.minecraft.scoreboard.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.event.*;

public class StatBase
{
    private static DecimalFormat decimalFormat;
    private static NumberFormat numberFormat;
    private final IStatType type;
    public boolean isIndependent;
    public static IStatType timeStatType;
    public static IStatType distanceStatType;
    public static IStatType field_111202_k;
    private final IChatComponent statName;
    public static IStatType simpleStatType;
    private final IScoreObjectiveCriteria field_150957_c;
    private Class field_150956_d;
    public final String statId;
    
    public boolean isAchievement() {
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.statId.hashCode();
    }
    
    @Override
    public String toString() {
        return "Stat{id=" + this.statId + ", nameId=" + this.statName + ", awardLocallyOnly=" + this.isIndependent + ", formatter=" + this.type + ", objectiveCriteria=" + this.field_150957_c + '}';
    }
    
    public StatBase registerStat() {
        if (StatList.oneShotStats.containsKey(this.statId)) {
            throw new RuntimeException("Duplicate stat id: \"" + StatList.oneShotStats.get(this.statId).statName + "\" and \"" + this.statName + "\" at id " + this.statId);
        }
        StatList.allStats.add(this);
        StatList.oneShotStats.put(this.statId, this);
        return this;
    }
    
    static {
        StatBase.numberFormat = NumberFormat.getIntegerInstance(Locale.US);
        StatBase.simpleStatType = new IStatType() {
            @Override
            public String format(final int n) {
                return StatBase.access$000().format(n);
            }
        };
        StatBase.decimalFormat = new DecimalFormat("########0.00");
        StatBase.timeStatType = new IStatType() {
            @Override
            public String format(final int n) {
                final double n2 = n / 20.0;
                final double n3 = n2 / 60.0;
                final double n4 = n3 / 60.0;
                final double n5 = n4 / 24.0;
                final double n6 = n5 / 365.0;
                return (n6 > 0.5) ? (StatBase.access$100().format(n6) + " y") : ((n5 > 0.5) ? (StatBase.access$100().format(n5) + " d") : ((n4 > 0.5) ? (StatBase.access$100().format(n4) + " h") : ((n3 > 0.5) ? (StatBase.access$100().format(n3) + " m") : (n2 + " s"))));
            }
        };
        StatBase.distanceStatType = new IStatType() {
            @Override
            public String format(final int n) {
                final double n2 = n / 100.0;
                final double n3 = n2 / 1000.0;
                return (n3 > 0.5) ? (StatBase.access$100().format(n3) + " km") : ((n2 > 0.5) ? (StatBase.access$100().format(n2) + " m") : (n + " cm"));
            }
        };
        StatBase.field_111202_k = new IStatType() {
            @Override
            public String format(final int n) {
                return StatBase.access$100().format(n * 0.1);
            }
        };
    }
    
    static NumberFormat access$000() {
        return StatBase.numberFormat;
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o != null && this.getClass() == o.getClass() && this.statId.equals(((StatBase)o).statId));
    }
    
    public StatBase func_150953_b(final Class field_150956_d) {
        this.field_150956_d = field_150956_d;
        return this;
    }
    
    public Class func_150954_l() {
        return this.field_150956_d;
    }
    
    public StatBase initIndependentStat() {
        this.isIndependent = true;
        return this;
    }
    
    static DecimalFormat access$100() {
        return StatBase.decimalFormat;
    }
    
    public IChatComponent func_150955_j() {
        final IChatComponent statName = this.getStatName();
        final IChatComponent appendText = new ChatComponentText("[").appendSibling(statName).appendText("]");
        appendText.setChatStyle(statName.getChatStyle());
        return appendText;
    }
    
    public IChatComponent getStatName() {
        final IChatComponent copy = this.statName.createCopy();
        copy.getChatStyle().setColor(EnumChatFormatting.GRAY);
        copy.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ACHIEVEMENT, new ChatComponentText(this.statId)));
        return copy;
    }
    
    public IScoreObjectiveCriteria func_150952_k() {
        return this.field_150957_c;
    }
    
    public StatBase(final String s, final IChatComponent chatComponent) {
        this(s, chatComponent, StatBase.simpleStatType);
    }
    
    public String format(final int n) {
        return this.type.format(n);
    }
    
    public StatBase(final String statId, final IChatComponent statName, final IStatType type) {
        this.statId = statId;
        this.statName = statName;
        this.type = type;
        this.field_150957_c = new ObjectiveStat(this);
        IScoreObjectiveCriteria.INSTANCES.put(this.field_150957_c.getName(), this.field_150957_c);
    }
}
