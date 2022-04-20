package net.minecraft.event;

import net.minecraft.util.*;
import java.util.*;
import com.google.common.collect.*;

public class HoverEvent
{
    private final IChatComponent value;
    private final Action action;
    
    public HoverEvent(final Action action, final IChatComponent value) {
        this.action = action;
        this.value = value;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final HoverEvent hoverEvent = (HoverEvent)o;
        if (this.action != hoverEvent.action) {
            return false;
        }
        if (this.value != null) {
            if (!this.value.equals(hoverEvent.value)) {
                return false;
            }
        }
        else if (hoverEvent.value != null) {
            return false;
        }
        return true;
    }
    
    public Action getAction() {
        return this.action;
    }
    
    public IChatComponent getValue() {
        return this.value;
    }
    
    @Override
    public String toString() {
        return "HoverEvent{action=" + this.action + ", value='" + this.value + '\'' + '}';
    }
    
    @Override
    public int hashCode() {
        return 31 * this.action.hashCode() + ((this.value != null) ? this.value.hashCode() : 0);
    }
    
    public enum Action
    {
        private final boolean allowedInChat;
        
        SHOW_ITEM("SHOW_ITEM", 2, "show_item", true), 
        SHOW_ENTITY("SHOW_ENTITY", 3, "show_entity", true), 
        SHOW_ACHIEVEMENT("SHOW_ACHIEVEMENT", 1, "show_achievement", true);
        
        private static final Map nameMapping;
        private final String canonicalName;
        
        SHOW_TEXT("SHOW_TEXT", 0, "show_text", true);
        
        private static final Action[] $VALUES;
        
        static {
            $VALUES = new Action[] { Action.SHOW_TEXT, Action.SHOW_ACHIEVEMENT, Action.SHOW_ITEM, Action.SHOW_ENTITY };
            nameMapping = Maps.newHashMap();
            final Action[] values = values();
            while (0 < values.length) {
                final Action action = values[0];
                Action.nameMapping.put(action.getCanonicalName(), action);
                int n = 0;
                ++n;
            }
        }
        
        public boolean shouldAllowInChat() {
            return this.allowedInChat;
        }
        
        public static Action getValueByCanonicalName(final String s) {
            return Action.nameMapping.get(s);
        }
        
        public String getCanonicalName() {
            return this.canonicalName;
        }
        
        private Action(final String s, final int n, final String canonicalName, final boolean allowedInChat) {
            this.canonicalName = canonicalName;
            this.allowedInChat = allowedInChat;
        }
    }
}
