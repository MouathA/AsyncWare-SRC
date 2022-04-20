package net.minecraft.client.network;

import net.minecraft.client.multiplayer.*;
import io.netty.bootstrap.*;
import io.netty.channel.*;
import io.netty.util.concurrent.*;
import io.netty.buffer.*;
import com.google.common.base.*;
import io.netty.channel.socket.nio.*;
import net.minecraft.network.status.*;
import net.minecraft.util.*;
import org.apache.commons.lang3.*;
import net.minecraft.client.*;
import com.mojang.authlib.*;
import net.minecraft.network.status.server.*;
import net.minecraft.network.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.network.status.client.*;
import java.net.*;
import com.google.common.collect.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class OldServerPinger
{
    private static final Logger logger;
    private final List pingDestinations;
    private static final Splitter PING_RESPONSE_SPLITTER;
    
    private void tryCompatibilityPing(final ServerData serverData) {
        final ServerAddress func_78860_a = ServerAddress.func_78860_a(serverData.serverIP);
        ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group((EventLoopGroup)NetworkManager.CLIENT_NIO_EVENTLOOP.getValue())).handler((ChannelHandler)new ChannelInitializer(this, func_78860_a, serverData) {
            final ServerAddress val$serveraddress;
            final ServerData val$server;
            final OldServerPinger this$0;
            
            protected void initChannel(final Channel channel) throws Exception {
                channel.config().setOption(ChannelOption.TCP_NODELAY, (Object)true);
                channel.pipeline().addLast(new ChannelHandler[] { (ChannelHandler)new SimpleChannelInboundHandler(this) {
                        final OldServerPinger$2 this$1;
                        
                        public void exceptionCaught(final ChannelHandlerContext channelHandlerContext, final Throwable t) throws Exception {
                            channelHandlerContext.close();
                        }
                        
                        public void channelActive(final ChannelHandlerContext channelHandlerContext) throws Exception {
                            super.channelActive(channelHandlerContext);
                            final ByteBuf buffer = Unpooled.buffer();
                            buffer.writeByte(254);
                            buffer.writeByte(1);
                            buffer.writeByte(250);
                            final char[] charArray = "MC|PingHost".toCharArray();
                            buffer.writeShort(charArray.length);
                            final char[] array = charArray;
                            int n = 0;
                            while (0 < array.length) {
                                buffer.writeChar((int)array[0]);
                                ++n;
                            }
                            buffer.writeShort(7 + 2 * this.this$1.val$serveraddress.getIP().length());
                            buffer.writeByte(127);
                            final char[] charArray2 = this.this$1.val$serveraddress.getIP().toCharArray();
                            buffer.writeShort(charArray2.length);
                            final char[] array2 = charArray2;
                            while (0 < array2.length) {
                                buffer.writeChar((int)array2[0]);
                                ++n;
                            }
                            buffer.writeInt(this.this$1.val$serveraddress.getPort());
                            channelHandlerContext.channel().writeAndFlush((Object)buffer).addListener((GenericFutureListener)ChannelFutureListener.CLOSE_ON_FAILURE);
                            buffer.release();
                        }
                        
                        protected void channelRead0(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf) throws Exception {
                            if (byteBuf.readUnsignedByte() == 255) {
                                final String[] array = (String[])Iterables.toArray(OldServerPinger.access$200().split((CharSequence)new String(byteBuf.readBytes(byteBuf.readShort() * 2).array(), Charsets.UTF_16BE)), (Class)String.class);
                                if ("§1".equals(array[0])) {
                                    MathHelper.parseIntWithDefault(array[1], 0);
                                    final String gameVersion = array[2];
                                    final String serverMOTD = array[3];
                                    final int intWithDefault = MathHelper.parseIntWithDefault(array[4], -1);
                                    final int intWithDefault2 = MathHelper.parseIntWithDefault(array[5], -1);
                                    this.this$1.val$server.version = -1;
                                    this.this$1.val$server.gameVersion = gameVersion;
                                    this.this$1.val$server.serverMOTD = serverMOTD;
                                    this.this$1.val$server.populationInfo = EnumChatFormatting.GRAY + "" + intWithDefault + "" + EnumChatFormatting.DARK_GRAY + "/" + EnumChatFormatting.GRAY + intWithDefault2;
                                }
                            }
                            channelHandlerContext.close();
                        }
                        
                        protected void channelRead0(final ChannelHandlerContext channelHandlerContext, final Object o) throws Exception {
                            this.channelRead0(channelHandlerContext, (ByteBuf)o);
                        }
                    } });
            }
        })).channel((Class)NioSocketChannel.class)).connect(func_78860_a.getIP(), func_78860_a.getPort());
    }
    
    public void ping(final ServerData serverData) throws UnknownHostException {
        final ServerAddress func_78860_a = ServerAddress.func_78860_a(serverData.serverIP);
        final NetworkManager func_181124_a = NetworkManager.func_181124_a(InetAddress.getByName(func_78860_a.getIP()), func_78860_a.getPort(), false);
        this.pingDestinations.add(func_181124_a);
        serverData.serverMOTD = "Pinging...";
        serverData.pingToServer = -1L;
        serverData.playerList = null;
        func_181124_a.setNetHandler(new INetHandlerStatusClient(this, func_181124_a, serverData) {
            final ServerData val$server;
            final OldServerPinger this$0;
            private long field_175092_e = 0L;
            final NetworkManager val$networkmanager;
            private boolean field_183009_e = false;
            private boolean field_147403_d = false;
            
            @Override
            public void handleServerInfo(final S00PacketServerInfo s00PacketServerInfo) {
                if (this.field_183009_e) {
                    this.val$networkmanager.closeChannel(new ChatComponentText("Received unrequested status"));
                }
                else {
                    this.field_183009_e = true;
                    final ServerStatusResponse response = s00PacketServerInfo.getResponse();
                    if (response.getServerDescription() != null) {
                        this.val$server.serverMOTD = response.getServerDescription().getFormattedText();
                    }
                    else {
                        this.val$server.serverMOTD = "";
                    }
                    if (response.getProtocolVersionInfo() != null) {
                        this.val$server.gameVersion = response.getProtocolVersionInfo().getName();
                        this.val$server.version = response.getProtocolVersionInfo().getProtocol();
                    }
                    else {
                        this.val$server.gameVersion = "Old";
                        this.val$server.version = 0;
                    }
                    if (response.getPlayerCountData() != null) {
                        this.val$server.populationInfo = EnumChatFormatting.GRAY + "" + response.getPlayerCountData().getOnlinePlayerCount() + "" + EnumChatFormatting.DARK_GRAY + "/" + EnumChatFormatting.GRAY + response.getPlayerCountData().getMaxPlayers();
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
                            this.val$server.playerList = sb.toString();
                        }
                    }
                    else {
                        this.val$server.populationInfo = EnumChatFormatting.DARK_GRAY + "???";
                    }
                    if (response.getFavicon() != null) {
                        final String favicon = response.getFavicon();
                        if (favicon.startsWith("data:image/png;base64,")) {
                            this.val$server.setBase64EncodedIconData(favicon.substring(22));
                        }
                        else {
                            OldServerPinger.access$000().error("Invalid server icon (unknown format)");
                        }
                    }
                    else {
                        this.val$server.setBase64EncodedIconData(null);
                    }
                    this.field_175092_e = Minecraft.getSystemTime();
                    this.val$networkmanager.sendPacket(new C01PacketPing(this.field_175092_e));
                    this.field_147403_d = true;
                }
            }
            
            @Override
            public void onDisconnect(final IChatComponent chatComponent) {
                if (!this.field_147403_d) {
                    OldServerPinger.access$000().error("Can't ping " + this.val$server.serverIP + ": " + chatComponent.getUnformattedText());
                    this.val$server.serverMOTD = EnumChatFormatting.DARK_RED + "Can't connect to server.";
                    this.val$server.populationInfo = "";
                    OldServerPinger.access$100(this.this$0, this.val$server);
                }
            }
            
            @Override
            public void handlePong(final S01PacketPong s01PacketPong) {
                this.val$server.pingToServer = Minecraft.getSystemTime() - this.field_175092_e;
                this.val$networkmanager.closeChannel(new ChatComponentText("Finished"));
            }
        });
        func_181124_a.sendPacket(new C00Handshake(47, func_78860_a.getIP(), func_78860_a.getPort(), EnumConnectionState.STATUS));
        func_181124_a.sendPacket(new C00PacketServerQuery());
    }
    
    public void pingPendingNetworks() {
        // monitorenter(pingDestinations = this.pingDestinations)
        final Iterator<NetworkManager> iterator = (Iterator<NetworkManager>)this.pingDestinations.iterator();
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
    // monitorexit(pingDestinations)
    
    public OldServerPinger() {
        this.pingDestinations = Collections.synchronizedList((List<Object>)Lists.newArrayList());
    }
    
    static Splitter access$200() {
        return OldServerPinger.PING_RESPONSE_SPLITTER;
    }
    
    static Logger access$000() {
        return OldServerPinger.logger;
    }
    
    static void access$100(final OldServerPinger oldServerPinger, final ServerData serverData) {
        oldServerPinger.tryCompatibilityPing(serverData);
    }
    
    static {
        PING_RESPONSE_SPLITTER = Splitter.on('\0').limit(6);
        logger = LogManager.getLogger();
    }
    
    public void clearPendingNetworks() {
        // monitorenter(pingDestinations = this.pingDestinations)
        final Iterator<NetworkManager> iterator = (Iterator<NetworkManager>)this.pingDestinations.iterator();
        while (iterator.hasNext()) {
            final NetworkManager networkManager = iterator.next();
            if (networkManager.isChannelOpen()) {
                iterator.remove();
                networkManager.closeChannel(new ChatComponentText("Cancelled"));
            }
        }
    }
    // monitorexit(pingDestinations)
}
