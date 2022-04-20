package net.minecraft.server.management;

import com.google.gson.*;
import com.mojang.authlib.*;
import java.util.*;
import java.io.*;

public class UserListOps extends UserList
{
    @Override
    protected UserListEntry createEntry(final JsonObject jsonObject) {
        return new UserListOpsEntry(jsonObject);
    }
    
    public GameProfile getGameProfileFromName(final String s) {
        for (final UserListOpsEntry userListOpsEntry : this.getValues().values()) {
            if (s.equalsIgnoreCase(((GameProfile)userListOpsEntry.getValue()).getName())) {
                return (GameProfile)userListOpsEntry.getValue();
            }
        }
        return null;
    }
    
    protected String getObjectKey(final GameProfile gameProfile) {
        return gameProfile.getId().toString();
    }
    
    public UserListOps(final File file) {
        super(file);
    }
    
    @Override
    protected String getObjectKey(final Object o) {
        return this.getObjectKey((GameProfile)o);
    }
    
    public boolean func_183026_b(final GameProfile gameProfile) {
        final UserListOpsEntry userListOpsEntry = (UserListOpsEntry)this.getEntry(gameProfile);
        return userListOpsEntry != null && userListOpsEntry.func_183024_b();
    }
    
    @Override
    public String[] getKeys() {
        final String[] array = new String[this.getValues().size()];
        for (final UserListOpsEntry userListOpsEntry : this.getValues().values()) {
            final String[] array2 = array;
            final int n = 0;
            int n2 = 0;
            ++n2;
            array2[n] = ((GameProfile)userListOpsEntry.getValue()).getName();
        }
        return array;
    }
}
