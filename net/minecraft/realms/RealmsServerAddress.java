package net.minecraft.realms;

import net.minecraft.client.multiplayer.*;

public class RealmsServerAddress
{
    private final String host;
    private final int port;
    
    public String getHost() {
        return this.host;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public static RealmsServerAddress parseString(final String s) {
        final ServerAddress func_78860_a = ServerAddress.func_78860_a(s);
        return new RealmsServerAddress(func_78860_a.getIP(), func_78860_a.getPort());
    }
    
    protected RealmsServerAddress(final String host, final int port) {
        this.host = host;
        this.port = port;
    }
}
