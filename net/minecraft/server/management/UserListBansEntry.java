package net.minecraft.server.management;

import com.google.gson.*;
import com.mojang.authlib.*;
import java.util.*;

public class UserListBansEntry extends BanEntry
{
    private static GameProfile func_152648_b(final JsonObject jsonObject) {
        if (jsonObject.has("uuid") && jsonObject.has("name")) {
            return new GameProfile(UUID.fromString(jsonObject.get("uuid").getAsString()), jsonObject.get("name").getAsString());
        }
        return null;
    }
    
    @Override
    protected void onSerialization(final JsonObject jsonObject) {
        if (this.getValue() != null) {
            jsonObject.addProperty("uuid", (((GameProfile)this.getValue()).getId() == null) ? "" : ((GameProfile)this.getValue()).getId().toString());
            jsonObject.addProperty("name", ((GameProfile)this.getValue()).getName());
            super.onSerialization(jsonObject);
        }
    }
    
    public UserListBansEntry(final GameProfile gameProfile, final Date date, final String s, final Date date2, final String s2) {
        super(gameProfile, date2, s, date2, s2);
    }
    
    public UserListBansEntry(final GameProfile gameProfile) {
        this(gameProfile, null, null, null, null);
    }
    
    public UserListBansEntry(final JsonObject jsonObject) {
        super(func_152648_b(jsonObject), jsonObject);
    }
}
