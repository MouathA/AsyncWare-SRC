package net.minecraft.realms;

import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.network.status.*;
import org.apache.commons.lang3.*;
import com.mojang.authlib.*;
import net.minecraft.network.status.server.*;
import net.minecraft.network.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.network.status.client.*;
import java.net.*;
import org.apache.logging.log4j.*;

public class RealmsServerStatusPinger
{
    private final List connections;
    private static final Logger LOGGER;
    
    public void removeAll() {
        // monitorenter(connections = this.connections)
        final Iterator<NetworkManager> iterator = (Iterator<NetworkManager>)this.connections.iterator();
        while (iterator.hasNext()) {
            final NetworkManager networkManager = iterator.next();
            if (networkManager.isChannelOpen()) {
                iterator.remove();
                networkManager.closeChannel(new ChatComponentText("Cancelled"));
            }
        }
    }
    // monitorexit(connections)
    
    public RealmsServerStatusPinger() {
        this.connections = Collections.synchronizedList((List<Object>)Lists.newArrayList());
    }
    
    public void pingServer(final String s, final RealmsServerPing realmsServerPing) throws UnknownHostException {
        if (s != null && !s.startsWith("0.0.0.0") && !s.isEmpty()) {
            final RealmsServerAddress string = RealmsServerAddress.parseString(s);
            final NetworkManager func_181124_a = NetworkManager.func_181124_a(InetAddress.getByName(string.getHost()), string.getPort(), false);
            this.connections.add(func_181124_a);
            func_181124_a.setNetHandler(new INetHandlerStatusClient(this, realmsServerPing, func_181124_a, s) {
                private boolean field_154345_e = false;
                final RealmsServerPing val$p_pingServer_2_;
                final RealmsServerStatusPinger this$0;
                final String val$p_pingServer_1_;
                final NetworkManager val$networkmanager;
                
                @Override
                public void handleServerInfo(final S00PacketServerInfo s00PacketServerInfo) {
                    final ServerStatusResponse response = s00PacketServerInfo.getResponse();
                    if (response.getPlayerCountData() != null) {
                        this.val$p_pingServer_2_.nrOfPlayers = String.valueOf(response.getPlayerCountData().getOnlinePlayerCount());
                        if (ArrayUtils.isNotEmpty((Object[])response.getPlayerCountData().getPlayers())) {
                            final StringBuilder sb = new StringBuilder();
                            final GameProfile[] players = response.getPlayerCountData().getPlayers();
                            while (0 < players.length) {
                                final GameProfile gameProfile = players[0];
                                if (sb.length() > 0) {
                                    sb.append("\n");
                                }
                                sb.append(gameProfile.getName());
                                int n = 0;
                                ++n;
                            }
                            if (response.getPlayerCountData().getPlayers().length < response.getPlayerCountData().getOnlinePlayerCount()) {
                                if (sb.length() > 0) {
                                    sb.append("\n");
                                }
                                sb.append("... and ").append(response.getPlayerCountData().getOnlinePlayerCount() - response.getPlayerCountData().getPlayers().length).append(" more ...");
                            }
                            this.val$p_pingServer_2_.playerList = sb.toString();
                        }
                    }
                    else {
                        this.val$p_pingServer_2_.playerList = "";
                    }
                    this.val$networkmanager.sendPacket(new C01PacketPing(Realms.currentTimeMillis()));
                    this.field_154345_e = true;
                }
                
                @Override
                public void onDisconnect(final IChatComponent chatComponent) {
                    if (!this.field_154345_e) {
                        RealmsServerStatusPinger.access$000().error("Can't ping " + this.val$p_pingServer_1_ + ": " + chatComponent.getUnformattedText());
                    }
                }
                
                @Override
                public void handlePong(final S01PacketPong s01PacketPong) {
                    this.val$networkmanager.closeChannel(new ChatComponentText("Finished"));
                }
            });
            func_181124_a.sendPacket(new C00Handshake(RealmsSharedConstants.NETWORK_PROTOCOL_VERSION, string.getHost(), string.getPort(), EnumConnectionState.STATUS));
            func_181124_a.sendPacket(new C00PacketServerQuery());
        }
    }
    
    public void tick() {
        // monitorenter(connections = this.connections)
        final Iterator<NetworkManager> iterator = (Iterator<NetworkManager>)this.connections.iterator();
        while (iterator.hasNext()) {
            final NetworkManager networkManager = iterator.next();
            if (networkManager.isChannelOpen()) {
                networkManager.processReceivedPackets();
            }
            else {
                iterator.remove();
                networkManager.checkDisconnected();
            }
        }
    }
    // monitorexit(connections)
    
    static Logger access$000() {
        return RealmsServerStatusPinger.LOGGER;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
