package net.minecraft.server.management;

import com.google.gson.*;
import com.mojang.authlib.*;
import java.util.*;

public class UserListOpsEntry extends UserListEntry
{
    private final boolean field_183025_b;
    private final int field_152645_a;
    
    public UserListOpsEntry(final JsonObject jsonObject) {
        super(func_152643_b(jsonObject), jsonObject);
        this.field_152645_a = (jsonObject.has("level") ? jsonObject.get("level").getAsInt() : 0);
        this.field_183025_b = (jsonObject.has("bypassesPlayerLimit") && jsonObject.get("bypassesPlayerLimit").getAsBoolean());
    }
    
    public UserListOpsEntry(final GameProfile gameProfile, final int field_152645_a, final boolean field_183025_b) {
        super(gameProfile);
        this.field_152645_a = field_152645_a;
        this.field_183025_b = field_183025_b;
    }
    
    private static GameProfile func_152643_b(final JsonObject jsonObject) {
        if (jsonObject.has("uuid") && jsonObject.has("name")) {
            return new GameProfile(UUID.fromString(jsonObject.get("uuid").getAsString()), jsonObject.get("name").getAsString());
        }
        return null;
    }
    
    public boolean func_183024_b() {
        return this.field_183025_b;
    }
    
    public int getPermissionLevel() {
        return this.field_152645_a;
    }
    
    @Override
    protected void onSerialization(final JsonObject jsonObject) {
        if (this.getValue() != null) {
            jsonObject.addProperty("uuid", (((GameProfile)this.getValue()).getId() == null) ? "" : ((GameProfile)this.getValue()).getId().toString());
            jsonObject.addProperty("name", ((GameProfile)this.getValue()).getName());
            super.onSerialization(jsonObject);
            jsonObject.addProperty("level", (Number)this.field_152645_a);
            jsonObject.addProperty("bypassesPlayerLimit", Boolean.valueOf(this.field_183025_b));
        }
    }
}
