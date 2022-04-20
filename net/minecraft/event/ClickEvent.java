package net.minecraft.event;

import java.util.*;
import com.google.common.collect.*;

public class ClickEvent
{
    private final String value;
    private final Action action;
    
    public ClickEvent(final Action action, final String value) {
        this.action = action;
        this.value = value;
    }
    
    public Action getAction() {
        return this.action;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ClickEvent clickEvent = (ClickEvent)o;
        if (this.action != clickEvent.action) {
            return false;
        }
        if (this.value != null) {
            if (!this.value.equals(clickEvent.value)) {
                return false;
            }
        }
        else if (clickEvent.value != null) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "ClickEvent{action=" + this.action + ", value='" + this.value + '\'' + '}';
    }
    
    @Override
    public int hashCode() {
        return 31 * this.action.hashCode() + ((this.value != null) ? this.value.hashCode() : 0);
    }
    
    public String getValue() {
        return this.value;
    }
    
    public enum Action
    {
        OPEN_URL("OPEN_URL", 0, "open_url", true);
        
        private static final Map nameMapping;
        
        CHANGE_PAGE("CHANGE_PAGE", 5, "change_page", true), 
        OPEN_FILE("OPEN_FILE", 1, "open_file", false);
        
        private final String canonicalName;
        private static final Action[] $VALUES;
        
        RUN_COMMAND("RUN_COMMAND", 2, "run_command", true);
        
        private final boolean allowedInChat;
        
        SUGGEST_COMMAND("SUGGEST_COMMAND", 4, "suggest_command", true), 
        TWITCH_USER_INFO("TWITCH_USER_INFO", 3, "twitch_user_info", false);
        
        public boolean shouldAllowInChat() {
            return this.allowedInChat;
        }
        
        public static Action getValueByCanonicalName(final String s) {
            return Action.nameMapping.get(s);
        }
        
        public String getCanonicalName() {
            return this.canonicalName;
        }
        
        static {
            $VALUES = new Action[] { Action.OPEN_URL, Action.OPEN_FILE, Action.RUN_COMMAND, Action.TWITCH_USER_INFO, Action.SUGGEST_COMMAND, Action.CHANGE_PAGE };
            nameMapping = Maps.newHashMap();
            final Action[] values = values();
            while (0 < values.length) {
                final Action action = values[0];
                Action.nameMapping.put(action.getCanonicalName(), action);
                int n = 0;
                ++n;
            }
        }
        
        private Action(final String s, final int n, final String canonicalName, final boolean allowedInChat) {
            this.canonicalName = canonicalName;
            this.allowedInChat = allowedInChat;
        }
    }
}
