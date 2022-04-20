package net.minecraft.util;

import com.mojang.authlib.*;
import com.mojang.util.*;
import java.util.*;
import com.google.common.collect.*;

public class Session
{
    private final Type sessionType;
    private final String username;
    private final String playerID;
    private final String token;
    
    public String getPlayerID() {
        return this.playerID;
    }
    
    public String getToken() {
        return this.token;
    }
    
    public String getSessionID() {
        return "token:" + this.token + ":" + this.playerID;
    }
    
    public Type getSessionType() {
        return this.sessionType;
    }
    
    public GameProfile getProfile() {
        return new GameProfile(UUIDTypeAdapter.fromString(this.getPlayerID()), this.getUsername());
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public Session(final String username, final String playerID, final String token, final String sessionType) {
        this.username = username;
        this.playerID = playerID;
        this.token = token;
        this.sessionType = Type.setSessionType(sessionType);
    }
    
    public enum Type
    {
        LEGACY("LEGACY", 0, "legacy");
        
        private static final Type[] $VALUES;
        
        MOJANG("MOJANG", 1, "mojang");
        
        private final String sessionType;
        private static final Map SESSION_TYPES;
        
        static {
            $VALUES = new Type[] { Type.LEGACY, Type.MOJANG };
            SESSION_TYPES = Maps.newHashMap();
            final Type[] values = values();
            while (0 < values.length) {
                final Type type = values[0];
                Type.SESSION_TYPES.put(type.sessionType, type);
                int n = 0;
                ++n;
            }
        }
        
        private Type(final String s, final int n, final String sessionType) {
            this.sessionType = sessionType;
        }
        
        public static Type setSessionType(final String s) {
            return Type.SESSION_TYPES.get(s.toLowerCase());
        }
    }
}
