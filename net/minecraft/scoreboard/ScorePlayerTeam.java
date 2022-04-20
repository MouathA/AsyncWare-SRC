package net.minecraft.scoreboard;

import net.minecraft.util.*;
import java.util.*;
import com.google.common.collect.*;

public class ScorePlayerTeam extends Team
{
    private boolean canSeeFriendlyInvisibles;
    private boolean allowFriendlyFire;
    private EnumVisible deathMessageVisibility;
    private String colorSuffix;
    private String namePrefixSPT;
    private EnumChatFormatting chatFormat;
    private EnumVisible nameTagVisibility;
    private final String registeredName;
    private final Set membershipSet;
    private String teamNameSPT;
    private final Scoreboard theScoreboard;
    
    public int func_98299_i() {
        if (this.getAllowFriendlyFire()) {}
        if (this.getSeeFriendlyInvisiblesEnabled()) {}
        return 0;
    }
    
    @Override
    public EnumVisible getNameTagVisibility() {
        return this.nameTagVisibility;
    }
    
    @Override
    public Collection getMembershipCollection() {
        return this.membershipSet;
    }
    
    public String getColorSuffix() {
        return this.colorSuffix;
    }
    
    @Override
    public String formatString(final String s) {
        return this.getColorPrefix() + s + this.getColorSuffix();
    }
    
    @Override
    public boolean getAllowFriendlyFire() {
        return this.allowFriendlyFire;
    }
    
    @Override
    public boolean getSeeFriendlyInvisiblesEnabled() {
        return this.canSeeFriendlyInvisibles;
    }
    
    public void setTeamName(final String teamNameSPT) {
        if (teamNameSPT == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.teamNameSPT = teamNameSPT;
        this.theScoreboard.sendTeamUpdate(this);
    }
    
    public void setDeathMessageVisibility(final EnumVisible deathMessageVisibility) {
        this.deathMessageVisibility = deathMessageVisibility;
        this.theScoreboard.sendTeamUpdate(this);
    }
    
    public static String formatPlayerName(final Team team, final String s) {
        return (team == null) ? s : team.formatString(s);
    }
    
    public String getColorPrefix() {
        return this.namePrefixSPT;
    }
    
    public void func_98298_a(final int n) {
        this.setAllowFriendlyFire((n & 0x1) > 0);
        this.setSeeFriendlyInvisiblesEnabled((n & 0x2) > 0);
    }
    
    public void setAllowFriendlyFire(final boolean allowFriendlyFire) {
        this.allowFriendlyFire = allowFriendlyFire;
        this.theScoreboard.sendTeamUpdate(this);
    }
    
    public ScorePlayerTeam(final Scoreboard theScoreboard, final String s) {
        this.membershipSet = Sets.newHashSet();
        this.namePrefixSPT = "";
        this.colorSuffix = "";
        this.allowFriendlyFire = true;
        this.canSeeFriendlyInvisibles = true;
        this.nameTagVisibility = EnumVisible.ALWAYS;
        this.deathMessageVisibility = EnumVisible.ALWAYS;
        this.chatFormat = EnumChatFormatting.RESET;
        this.theScoreboard = theScoreboard;
        this.registeredName = s;
        this.teamNameSPT = s;
    }
    
    public void setNameTagVisibility(final EnumVisible nameTagVisibility) {
        this.nameTagVisibility = nameTagVisibility;
        this.theScoreboard.sendTeamUpdate(this);
    }
    
    @Override
    public EnumVisible getDeathMessageVisibility() {
        return this.deathMessageVisibility;
    }
    
    public void setChatFormat(final EnumChatFormatting chatFormat) {
        this.chatFormat = chatFormat;
    }
    
    public String getTeamName() {
        return this.teamNameSPT;
    }
    
    public void setNameSuffix(final String colorSuffix) {
        this.colorSuffix = colorSuffix;
        this.theScoreboard.sendTeamUpdate(this);
    }
    
    public EnumChatFormatting getChatFormat() {
        return this.chatFormat;
    }
    
    public void setNamePrefix(final String namePrefixSPT) {
        if (namePrefixSPT == null) {
            throw new IllegalArgumentException("Prefix cannot be null");
        }
        this.namePrefixSPT = namePrefixSPT;
        this.theScoreboard.sendTeamUpdate(this);
    }
    
    public void setSeeFriendlyInvisiblesEnabled(final boolean canSeeFriendlyInvisibles) {
        this.canSeeFriendlyInvisibles = canSeeFriendlyInvisibles;
        this.theScoreboard.sendTeamUpdate(this);
    }
    
    @Override
    public String getRegisteredName() {
        return this.registeredName;
    }
}
