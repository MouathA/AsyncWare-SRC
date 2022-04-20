package net.minecraft.server.management;

import com.mojang.authlib.*;
import com.google.gson.*;
import java.util.*;
import java.io.*;

public class UserListWhitelist extends UserList
{
    @Override
    protected String getObjectKey(final Object o) {
        return this.getObjectKey((GameProfile)o);
    }
    
    @Override
    protected UserListEntry createEntry(final JsonObject jsonObject) {
        return new UserListWhitelistEntry(jsonObject);
    }
    
    @Override
    public String[] getKeys() {
        final String[] array = new String[this.getValues().size()];
        for (final UserListWhitelistEntry userListWhitelistEntry : this.getValues().values()) {
            final String[] array2 = array;
            final int n = 0;
            int n2 = 0;
            ++n2;
            array2[n] = ((GameProfile)userListWhitelistEntry.getValue()).getName();
        }
        return array;
    }
    
    protected String getObjectKey(final GameProfile gameProfile) {
        return gameProfile.getId().toString();
    }
    
    public UserListWhitelist(final File file) {
        super(file);
    }
    
    public GameProfile func_152706_a(final String s) {
        for (final UserListWhitelistEntry userListWhitelistEntry : this.getValues().values()) {
            if (s.equalsIgnoreCase(((GameProfile)userListWhitelistEntry.getValue()).getName())) {
                return (GameProfile)userListWhitelistEntry.getValue();
            }
        }
        return null;
    }
}
