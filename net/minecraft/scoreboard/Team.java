package net.minecraft.scoreboard;

import java.util.*;
import com.google.common.collect.*;

public abstract class Team
{
    public abstract boolean getSeeFriendlyInvisiblesEnabled();
    
    public abstract String formatString(final String p0);
    
    public abstract EnumVisible getDeathMessageVisibility();
    
    public abstract EnumVisible getNameTagVisibility();
    
    public boolean isSameTeam(final Team team) {
        return team != null && this == team;
    }
    
    public abstract boolean getAllowFriendlyFire();
    
    public abstract String getRegisteredName();
    
    public abstract Collection getMembershipCollection();
    
    public enum EnumVisible
    {
        public final String field_178830_e;
        
        NEVER("NEVER", 1, "never", 1);
        
        private static Map field_178828_g;
        public final int field_178827_f;
        
        HIDE_FOR_OWN_TEAM("HIDE_FOR_OWN_TEAM", 3, "hideForOwnTeam", 3), 
        ALWAYS("ALWAYS", 0, "always", 0);
        
        private static final EnumVisible[] $VALUES;
        
        HIDE_FOR_OTHER_TEAMS("HIDE_FOR_OTHER_TEAMS", 2, "hideForOtherTeams", 2);
        
        public static String[] func_178825_a() {
            return (String[])EnumVisible.field_178828_g.keySet().toArray(new String[EnumVisible.field_178828_g.size()]);
        }
        
        static {
            $VALUES = new EnumVisible[] { EnumVisible.ALWAYS, EnumVisible.NEVER, EnumVisible.HIDE_FOR_OTHER_TEAMS, EnumVisible.HIDE_FOR_OWN_TEAM };
            EnumVisible.field_178828_g = Maps.newHashMap();
            final EnumVisible[] values = values();
            while (0 < values.length) {
                final EnumVisible enumVisible = values[0];
                EnumVisible.field_178828_g.put(enumVisible.field_178830_e, enumVisible);
                int n = 0;
                ++n;
            }
        }
        
        public static EnumVisible func_178824_a(final String s) {
            return EnumVisible.field_178828_g.get(s);
        }
        
        private EnumVisible(final String s, final int n, final String field_178830_e, final int field_178827_f) {
            this.field_178830_e = field_178830_e;
            this.field_178827_f = field_178827_f;
        }
    }
}
