package net.minecraft.server.management;

import com.mojang.authlib.*;
import java.io.*;
import com.google.gson.*;
import java.util.*;

public class UserListBans extends UserList
{
    public boolean isBanned(final GameProfile gameProfile) {
        return this.hasEntry(gameProfile);
    }
    
    public UserListBans(final File file) {
        super(file);
    }
    
    protected String getObjectKey(final GameProfile gameProfile) {
        return gameProfile.getId().toString();
    }
    
    @Override
    protected UserListEntry createEntry(final JsonObject jsonObject) {
        return new UserListBansEntry(jsonObject);
    }
    
    @Override
    protected String getObjectKey(final Object o) {
        return this.getObjectKey((GameProfile)o);
    }
    
    @Override
    public String[] getKeys() {
        final String[] array = new String[this.getValues().size()];
        for (final UserListBansEntry userListBansEntry : this.getValues().values()) {
            final String[] array2 = array;
            final int n = 0;
            int n2 = 0;
            ++n2;
            array2[n] = ((GameProfile)userListBansEntry.getValue()).getName();
        }
        return array;
    }
    
    public GameProfile isUsernameBanned(final String s) {
        for (final UserListBansEntry userListBansEntry : this.getValues().values()) {
            if (s.equalsIgnoreCase(((GameProfile)userListBansEntry.getValue()).getName())) {
                return (GameProfile)userListBansEntry.getValue();
            }
        }
        return null;
    }
}
