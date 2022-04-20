package net.minecraft.server.management;

import com.google.gson.*;

public class UserListEntry
{
    private final Object value;
    
    protected void onSerialization(final JsonObject jsonObject) {
    }
    
    public UserListEntry(final Object value) {
        this.value = value;
    }
    
    Object getValue() {
        return this.value;
    }
    
    protected UserListEntry(final Object value, final JsonObject jsonObject) {
        this.value = value;
    }
    
    boolean hasBanExpired() {
        return false;
    }
}
