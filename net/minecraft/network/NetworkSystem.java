package net.minecraft.network;

import net.minecraft.server.*;
import org.apache.logging.log4j.*;
import io.netty.channel.nio.*;
import com.google.common.util.concurrent.*;
import com.google.common.collect.*;
import io.netty.bootstrap.*;
import net.minecraft.client.network.*;
import io.netty.channel.local.*;
import java.util.*;
import java.net.*;
import io.netty.channel.epoll.*;
import io.netty.channel.socket.nio.*;
import io.netty.channel.*;
import io.netty.handler.timeout.*;
import net.minecraft.util.*;
import net.minecraft.server.network.*;
import java.io.*;

public class NetworkSystem
{
    public static final LazyLoadBase eventLoops;
    private final List endpoints;
    private final List networkManagers;
    public static final LazyLoadBase SERVER_LOCAL_EVENTLOOP;
    public static final LazyLoadBase field_181141_b;
    public volatile boolean isAlive;
    private static final Logger logger;
    private final MinecraftServer mcServer;
    
    static {
        logger = LogManager.getLogger();
        eventLoops = new LazyLoadBase() {
            @Override
            protected NioEventLoopGroup load() {
                return new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Server IO #%d").setDaemon(true).build());
            }
            
            @Override
            protected Object load() {
                return this.load();
            }
        };
        field_181141_b = new LazyLoadBase() {
            @Override
            protected Object load() {
                return this.load();
            }
            
            @Override
            protected EpollEventLoopGroup load() {
                return new EpollEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Epoll Server IO #%d").setDaemon(true).build());
            }
        };
        SERVER_LOCAL_EVENTLOOP = new LazyLoadBase() {
            @Override
            protected Object load() {
                return this.load();
            }
            
            @Override
            protected LocalEventLoopGroup load() {
                return new LocalEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Local Server IO #%d").setDaemon(true).build());
            }
        };
    }
    
    public NetworkSystem(final MinecraftServer mcServer) {
        this.endpoints = Collections.synchronizedList((List<Object>)Lists.newArrayList());
        this.networkManagers = Collections.synchronizedList((List<Object>)Lists.newArrayList());
        this.mcServer = mcServer;
        this.isAlive = true;
    }
    
    public SocketAddress addLocalEndpoint() {
        // monitorenter(endpoints = this.endpoints)
        final ChannelFuture syncUninterruptibly = ((ServerBootstrap)((ServerBootstrap)new ServerBootstrap().channel((Class)LocalServerChannel.class)).childHandler((ChannelHandler)new ChannelInitializer(this) {
            final NetworkSystem this$0;
            
            protected void initChannel(final Channel channel) throws Exception {
                final NetworkManager networkManager = new NetworkManager(EnumPacketDirection.SERVERBOUND);
                networkManager.setNetHandler(new NetHandlerHandshakeMemory(NetworkSystem.access$100(this.this$0), networkManager));
                NetworkSystem.access$000(this.this$0).add(networkManager);
                channel.pipeline().addLast("packet_handler", (ChannelHandler)networkManager);
            }
        }).group((EventLoopGroup)NetworkSystem.eventLoops.getValue()).localAddress((SocketAddress)LocalAddress.ANY)).bind().syncUninterruptibly();
        this.endpoints.add(syncUninterruptibly);
        // monitorexit(endpoints)
        return syncUninterruptibly.channel().localAddress();
    }
    
    public MinecraftServer getServer() {
        return this.mcServer;
    }
    
    public void networkTick() {
        // monitorenter(networkManagers = this.networkManagers)
        final Iterator<NetworkManager> iterator = (Iterator<NetworkManager>)this.networkManagers.iterator();
        while (iterator.hasNext()) {
            final NetworkManager networkManager = iterator.next();
            if (!networkManager.hasNoChannel()) {
                if (!networkManager.isChannelOpen()) {
                    iterator.remove();
                    networkManager.checkDisconnected();
                }
                else {
                    networkManager.processReceivedPackets();
                }
            }
        }
    }
    // monitorexit(networkManagers)
    
    public void terminateEndpoints() {
        this.isAlive = false;
        final Iterator<ChannelFuture> iterator = this.endpoints.iterator();
        while (iterator.hasNext()) {
            iterator.next().channel().close().sync();
        }
    }
    
    public void addLanEndpoint(final InetAddress inetAddress, final int n) throws IOException {
        // monitorenter(endpoints = this.endpoints)
        Serializable s;
        LazyLoadBase lazyLoadBase;
        if (Epoll.isAvailable() && this.mcServer.func_181035_ah()) {
            s = EpollServerSocketChannel.class;
            lazyLoadBase = NetworkSystem.field_181141_b;
            NetworkSystem.logger.info("Using epoll channel type");
        }
        else {
            s = NioServerSocketChannel.class;
            lazyLoadBase = NetworkSystem.eventLoops;
            NetworkSystem.logger.info("Using default channel type");
        }
        this.endpoints.add(((ServerBootstrap)((ServerBootstrap)new ServerBootstrap().channel((Class)s)).childHandler((ChannelHandler)new ChannelInitializer(this) {
            final NetworkSystem this$0;
            
            protected void initChannel(final Channel channel) throws Exception {
                channel.config().setOption(ChannelOption.TCP_NODELAY, (Object)true);
                channel.pipeline().addLast("timeout", (ChannelHandler)new ReadTimeoutHandler(30)).addLast("legacy_query", (ChannelHandler)new PingResponseHandler(this.this$0)).addLast("splitter", (ChannelHandler)new MessageDeserializer2()).addLast("decoder", (ChannelHandler)new MessageDeserializer(EnumPacketDirection.SERVERBOUND)).addLast("prepender", (ChannelHandler)new MessageSerializer2()).addLast("encoder", (ChannelHandler)new MessageSerializer(EnumPacketDirection.CLIENTBOUND));
                final NetworkManager networkManager = new NetworkManager(EnumPacketDirection.SERVERBOUND);
                NetworkSystem.access$000(this.this$0).add(networkManager);
                channel.pipeline().addLast("packet_handler", (ChannelHandler)networkManager);
                networkManager.setNetHandler(new NetHandlerHandshakeTCP(NetworkSystem.access$100(this.this$0), networkManager));
            }
        }).group((EventLoopGroup)lazyLoadBase.getValue()).localAddress(inetAddress, n)).bind().syncUninterruptibly());
    }
    // monitorexit(endpoints)
    
    static MinecraftServer access$100(final NetworkSystem networkSystem) {
        return networkSystem.mcServer;
    }
    
    static List access$000(final NetworkSystem networkSystem) {
        return networkSystem.networkManagers;
    }
}
