package net.minecraft.stats;

import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

public class Achievement extends StatBase
{
    public final int displayColumn;
    public final int displayRow;
    public final Achievement parentAchievement;
    private final String achievementDescription;
    private boolean isSpecial;
    private IStatStringFormat statStringFormatter;
    public final ItemStack theItemStack;
    
    @Override
    public StatBase registerStat() {
        return this.registerStat();
    }
    
    public boolean getSpecial() {
        return this.isSpecial;
    }
    
    @Override
    public Achievement func_150953_b(final Class clazz) {
        return (Achievement)super.func_150953_b(clazz);
    }
    
    @Override
    public Achievement registerStat() {
        super.registerStat();
        AchievementList.achievementList.add(this);
        return this;
    }
    
    @Override
    public IChatComponent getStatName() {
        final IChatComponent statName = super.getStatName();
        statName.getChatStyle().setColor(this.getSpecial() ? EnumChatFormatting.DARK_PURPLE : EnumChatFormatting.GREEN);
        return statName;
    }
    
    public Achievement setSpecial() {
        this.isSpecial = true;
        return this;
    }
    
    public Achievement(final String s, final String s2, final int n, final int n2, final Block block, final Achievement achievement) {
        this(s, s2, n, n2, new ItemStack(block), achievement);
    }
    
    public String getDescription() {
        return (this.statStringFormatter != null) ? this.statStringFormatter.formatString(StatCollector.translateToLocal(this.achievementDescription)) : StatCollector.translateToLocal(this.achievementDescription);
    }
    
    @Override
    public StatBase func_150953_b(final Class clazz) {
        return this.func_150953_b(clazz);
    }
    
    public Achievement setStatStringFormatter(final IStatStringFormat statStringFormatter) {
        this.statStringFormatter = statStringFormatter;
        return this;
    }
    
    @Override
    public Achievement initIndependentStat() {
        this.isIndependent = true;
        return this;
    }
    
    @Override
    public StatBase initIndependentStat() {
        return this.initIndependentStat();
    }
    
    @Override
    public boolean isAchievement() {
        return true;
    }
    
    public Achievement(final String s, final String s2, final int maxDisplayColumn, final int maxDisplayRow, final ItemStack theItemStack, final Achievement parentAchievement) {
        super(s, new ChatComponentTranslation("achievement." + s2, new Object[0]));
        this.theItemStack = theItemStack;
        this.achievementDescription = "achievement." + s2 + ".desc";
        this.displayColumn = maxDisplayColumn;
        this.displayRow = maxDisplayRow;
        if (maxDisplayColumn < AchievementList.minDisplayColumn) {
            AchievementList.minDisplayColumn = maxDisplayColumn;
        }
        if (maxDisplayRow < AchievementList.minDisplayRow) {
            AchievementList.minDisplayRow = maxDisplayRow;
        }
        if (maxDisplayColumn > AchievementList.maxDisplayColumn) {
            AchievementList.maxDisplayColumn = maxDisplayColumn;
        }
        if (maxDisplayRow > AchievementList.maxDisplayRow) {
            AchievementList.maxDisplayRow = maxDisplayRow;
        }
        this.parentAchievement = parentAchievement;
    }
    
    public Achievement(final String s, final String s2, final int n, final int n2, final Item item, final Achievement achievement) {
        this(s, s2, n, n2, new ItemStack(item), achievement);
    }
}
