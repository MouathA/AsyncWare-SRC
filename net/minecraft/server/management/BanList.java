package net.minecraft.server.management;

import java.net.*;
import com.google.gson.*;
import java.io.*;

public class BanList extends UserList
{
    private String addressToString(final SocketAddress socketAddress) {
        String s = socketAddress.toString();
        if (s.contains("/")) {
            s = s.substring(s.indexOf(47) + 1);
        }
        if (s.contains(":")) {
            s = s.substring(0, s.indexOf(58));
        }
        return s;
    }
    
    public boolean isBanned(final SocketAddress socketAddress) {
        return this.hasEntry(this.addressToString(socketAddress));
    }
    
    @Override
    protected UserListEntry createEntry(final JsonObject jsonObject) {
        return new IPBanEntry(jsonObject);
    }
    
    public IPBanEntry getBanEntry(final SocketAddress socketAddress) {
        return (IPBanEntry)this.getEntry(this.addressToString(socketAddress));
    }
    
    public BanList(final File file) {
        super(file);
    }
}
