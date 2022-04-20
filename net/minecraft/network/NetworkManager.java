package net.minecraft.network;

import io.netty.util.*;
import java.util.*;
import java.util.concurrent.locks.*;
import java.net.*;
import io.netty.channel.socket.nio.*;
import io.netty.bootstrap.*;
import java.io.*;
import io.netty.util.concurrent.*;
import io.netty.channel.*;
import javax.crypto.*;
import java.security.*;
import net.minecraft.util.*;
import com.nquantum.event.impl.*;
import org.apache.commons.lang3.*;
import io.netty.handler.timeout.*;
import org.apache.logging.log4j.*;
import io.netty.channel.nio.*;
import com.google.common.util.concurrent.*;
import io.netty.channel.epoll.*;
import io.netty.channel.local.*;
import com.google.common.collect.*;

public class NetworkManager extends SimpleChannelInboundHandler
{
    private Channel channel;
    public static final Marker logMarkerNetwork;
    public static final Marker logMarkerPackets;
    public static final AttributeKey attrKeyConnectionState;
    private boolean isEncrypted;
    private INetHandler packetListener;
    private SocketAddress socketAddress;
    private boolean disconnected;
    public static final LazyLoadBase CLIENT_NIO_EVENTLOOP;
    private final Queue outboundPacketsQueue;
    public static final LazyLoadBase CLIENT_LOCAL_EVENTLOOP;
    private final EnumPacketDirection direction;
    private IChatComponent terminationReason;
    public static final LazyLoadBase field_181125_e;
    private static final Logger logger;
    private final ReentrantReadWriteLock field_181680_j;
    
    public INetHandler getNetHandler() {
        return this.packetListener;
    }
    
    public void setCompressionTreshold(final int n) {
        if (n >= 0) {
            if (this.channel.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
                ((NettyCompressionDecoder)this.channel.pipeline().get("decompress")).setCompressionTreshold(n);
            }
            else {
                this.channel.pipeline().addBefore("decoder", "decompress", (ChannelHandler)new NettyCompressionDecoder(n));
            }
            if (this.channel.pipeline().get("compress") instanceof NettyCompressionEncoder) {
                ((NettyCompressionEncoder)this.channel.pipeline().get("decompress")).setCompressionTreshold(n);
            }
            else {
                this.channel.pipeline().addBefore("encoder", "compress", (ChannelHandler)new NettyCompressionEncoder(n));
            }
        }
        else {
            if (this.channel.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
                this.channel.pipeline().remove("decompress");
            }
            if (this.channel.pipeline().get("compress") instanceof NettyCompressionEncoder) {
                this.channel.pipeline().remove("compress");
            }
        }
    }
    
    public void processReceivedPackets() {
        this.flushOutboundQueue();
        if (this.packetListener instanceof ITickable) {
            ((ITickable)this.packetListener).update();
        }
        this.channel.flush();
    }
    
    public boolean hasNoChannel() {
        return this.channel == null;
    }
    
    public SocketAddress getRemoteAddress() {
        return this.socketAddress;
    }
    
    protected void channelRead0(final ChannelHandlerContext channelHandlerContext, final Packet packet) throws Exception {
        if (this.channel.isOpen()) {
            final EventReceivePacket eventReceivePacket = new EventReceivePacket(packet);
            eventReceivePacket.call();
            if (eventReceivePacket.isCancelled()) {
                return;
            }
            packet.processPacket(this.packetListener);
        }
    }
    
    public void channelActive(final ChannelHandlerContext channelHandlerContext) throws Exception {
        super.channelActive(channelHandlerContext);
        this.channel = channelHandlerContext.channel();
        this.socketAddress = this.channel.remoteAddress();
        this.setConnectionState(EnumConnectionState.HANDSHAKING);
    }
    
    private void flushOutboundQueue() {
        if (this.channel != null && this.channel.isOpen()) {
            this.field_181680_j.readLock().lock();
            while (!this.outboundPacketsQueue.isEmpty()) {
                final InboundHandlerTuplePacketListener inboundHandlerTuplePacketListener = this.outboundPacketsQueue.poll();
                this.dispatchPacket(InboundHandlerTuplePacketListener.access$100(inboundHandlerTuplePacketListener), InboundHandlerTuplePacketListener.access$200(inboundHandlerTuplePacketListener));
            }
            this.field_181680_j.readLock().unlock();
        }
    }
    
    public boolean getIsencrypted() {
        return this.isEncrypted;
    }
    
    public static NetworkManager func_181124_a(final InetAddress inetAddress, final int n, final boolean b) {
        final NetworkManager networkManager = new NetworkManager(EnumPacketDirection.CLIENTBOUND);
        Serializable s;
        LazyLoadBase lazyLoadBase;
        if (Epoll.isAvailable() && b) {
            s = EpollSocketChannel.class;
            lazyLoadBase = NetworkManager.field_181125_e;
        }
        else {
            s = NioSocketChannel.class;
            lazyLoadBase = NetworkManager.CLIENT_NIO_EVENTLOOP;
        }
        ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group((EventLoopGroup)lazyLoadBase.getValue())).handler((ChannelHandler)new ChannelInitializer(networkManager) {
            final NetworkManager val$networkmanager;
            
            protected void initChannel(final Channel channel) throws Exception {
                channel.config().setOption(ChannelOption.TCP_NODELAY, (Object)true);
                channel.pipeline().addLast("timeout", (ChannelHandler)new ReadTimeoutHandler(30)).addLast("splitter", (ChannelHandler)new MessageDeserializer2()).addLast("decoder", (ChannelHandler)new MessageDeserializer(EnumPacketDirection.CLIENTBOUND)).addLast("prepender", (ChannelHandler)new MessageSerializer2()).addLast("encoder", (ChannelHandler)new MessageSerializer(EnumPacketDirection.SERVERBOUND)).addLast("packet_handler", (ChannelHandler)this.val$networkmanager);
            }
        })).channel((Class)s)).connect(inetAddress, n).syncUninterruptibly();
        return networkManager;
    }
    
    public void disableAutoRead() {
        this.channel.config().setAutoRead(false);
    }
    
    private void dispatchPacket(final Packet packet, final GenericFutureListener[] array) {
        final EnumConnectionState fromPacket = EnumConnectionState.getFromPacket(packet);
        final EnumConnectionState enumConnectionState = (EnumConnectionState)this.channel.attr(NetworkManager.attrKeyConnectionState).get();
        if (enumConnectionState != fromPacket) {
            NetworkManager.logger.debug("Disabled auto read");
            this.channel.config().setAutoRead(false);
        }
        if (this.channel.eventLoop().inEventLoop()) {
            if (fromPacket != enumConnectionState) {
                this.setConnectionState(fromPacket);
            }
            final ChannelFuture writeAndFlush = this.channel.writeAndFlush((Object)packet);
            if (array != null) {
                writeAndFlush.addListeners(array);
            }
            writeAndFlush.addListener((GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
        }
        else {
            this.channel.eventLoop().execute((Runnable)new Runnable(this, fromPacket, enumConnectionState, packet, array) {
                final EnumConnectionState val$enumconnectionstate;
                final GenericFutureListener[] val$futureListeners;
                final NetworkManager this$0;
                final EnumConnectionState val$enumconnectionstate1;
                final Packet val$inPacket;
                
                @Override
                public void run() {
                    if (this.val$enumconnectionstate != this.val$enumconnectionstate1) {
                        this.this$0.setConnectionState(this.val$enumconnectionstate);
                    }
                    final ChannelFuture writeAndFlush = NetworkManager.access$000(this.this$0).writeAndFlush((Object)this.val$inPacket);
                    if (this.val$futureListeners != null) {
                        writeAndFlush.addListeners(this.val$futureListeners);
                    }
                    writeAndFlush.addListener((GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
                }
            });
        }
    }
    
    public static NetworkManager provideLocalClient(final SocketAddress socketAddress) {
        final NetworkManager networkManager = new NetworkManager(EnumPacketDirection.CLIENTBOUND);
        ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group((EventLoopGroup)NetworkManager.CLIENT_LOCAL_EVENTLOOP.getValue())).handler((ChannelHandler)new ChannelInitializer(networkManager) {
            final NetworkManager val$networkmanager;
            
            protected void initChannel(final Channel channel) throws Exception {
                channel.pipeline().addLast("packet_handler", (ChannelHandler)this.val$networkmanager);
            }
        })).channel((Class)LocalChannel.class)).connect(socketAddress).syncUninterruptibly();
        return networkManager;
    }
    
    public void channelInactive(final ChannelHandlerContext channelHandlerContext) throws Exception {
        this.closeChannel(new ChatComponentTranslation("disconnect.endOfStream", new Object[0]));
    }
    
    public void closeChannel(final IChatComponent terminationReason) {
        if (this.channel.isOpen()) {
            this.channel.close().awaitUninterruptibly();
            this.terminationReason = terminationReason;
        }
    }
    
    public void enableEncryption(final SecretKey secretKey) {
        this.isEncrypted = true;
        this.channel.pipeline().addBefore("splitter", "decrypt", (ChannelHandler)new NettyEncryptingDecoder(CryptManager.createNetCipherInstance(2, secretKey)));
        this.channel.pipeline().addBefore("prepender", "encrypt", (ChannelHandler)new NettyEncryptingEncoder(CryptManager.createNetCipherInstance(1, secretKey)));
    }
    
    public void checkDisconnected() {
        if (this.channel != null && !this.channel.isOpen()) {
            if (!this.disconnected) {
                this.disconnected = true;
                if (this.getExitMessage() != null) {
                    this.getNetHandler().onDisconnect(this.getExitMessage());
                }
                else if (this.getNetHandler() != null) {
                    this.getNetHandler().onDisconnect(new ChatComponentText("Disconnected"));
                }
            }
            else {
                NetworkManager.logger.warn("handleDisconnection() called twice");
            }
        }
    }
    
    public void setNetHandler(final INetHandler packetListener) {
        Validate.notNull((Object)packetListener, "packetListener", new Object[0]);
        NetworkManager.logger.debug("Set listener of {} to {}", new Object[] { this, packetListener });
        this.packetListener = packetListener;
    }
    
    public void sendPacket(final Packet packet) {
        final EventSendPacket eventSendPacket = new EventSendPacket(packet);
        eventSendPacket.call();
        if (eventSendPacket.isCancelled()) {
            return;
        }
        if (this != null) {
            this.flushOutboundQueue();
            this.dispatchPacket(packet, null);
        }
        else {
            this.field_181680_j.writeLock().lock();
            this.outboundPacketsQueue.add(new InboundHandlerTuplePacketListener(packet, (GenericFutureListener[])null));
            this.field_181680_j.writeLock().unlock();
        }
    }
    
    static Channel access$000(final NetworkManager networkManager) {
        return networkManager.channel;
    }
    
    public void sendPacket(final Packet packet, final GenericFutureListener genericFutureListener, final GenericFutureListener... array) {
        if (this != null) {
            this.flushOutboundQueue();
            this.dispatchPacket(packet, (GenericFutureListener[])ArrayUtils.add((Object[])array, 0, (Object)genericFutureListener));
        }
        else {
            this.field_181680_j.writeLock().lock();
            this.outboundPacketsQueue.add(new InboundHandlerTuplePacketListener(packet, (GenericFutureListener[])ArrayUtils.add((Object[])array, 0, (Object)genericFutureListener)));
            this.field_181680_j.writeLock().unlock();
        }
    }
    
    public void setConnectionState(final EnumConnectionState enumConnectionState) {
        this.channel.attr(NetworkManager.attrKeyConnectionState).set((Object)enumConnectionState);
        this.channel.config().setAutoRead(true);
        NetworkManager.logger.debug("Enabled auto read");
    }
    
    public void exceptionCaught(final ChannelHandlerContext channelHandlerContext, final Throwable t) throws Exception {
        ChatComponentTranslation chatComponentTranslation;
        if (t instanceof TimeoutException) {
            chatComponentTranslation = new ChatComponentTranslation("disconnect.timeout", new Object[0]);
        }
        else {
            chatComponentTranslation = new ChatComponentTranslation("disconnect.genericReason", new Object[] { "Internal Exception: " + t });
        }
        this.closeChannel(chatComponentTranslation);
    }
    
    protected void channelRead0(final ChannelHandlerContext channelHandlerContext, final Object o) throws Exception {
        this.channelRead0(channelHandlerContext, (Packet)o);
    }
    
    public boolean isLocalChannel() {
        return this.channel instanceof LocalChannel || this.channel instanceof LocalServerChannel;
    }
    
    static {
        logger = LogManager.getLogger();
        logMarkerNetwork = MarkerManager.getMarker("NETWORK");
        logMarkerPackets = MarkerManager.getMarker("NETWORK_PACKETS", NetworkManager.logMarkerNetwork);
        attrKeyConnectionState = AttributeKey.valueOf("protocol");
        CLIENT_NIO_EVENTLOOP = new LazyLoadBase() {
            @Override
            protected Object load() {
                return this.load();
            }
            
            @Override
            protected NioEventLoopGroup load() {
                return new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Client IO #%d").setDaemon(true).build());
            }
        };
        field_181125_e = new LazyLoadBase() {
            @Override
            protected Object load() {
                return this.load();
            }
            
            @Override
            protected EpollEventLoopGroup load() {
                return new EpollEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Epoll Client IO #%d").setDaemon(true).build());
            }
        };
        CLIENT_LOCAL_EVENTLOOP = new LazyLoadBase() {
            @Override
            protected Object load() {
                return this.load();
            }
            
            @Override
            protected LocalEventLoopGroup load() {
                return new LocalEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Local Client IO #%d").setDaemon(true).build());
            }
        };
    }
    
    public NetworkManager(final EnumPacketDirection direction) {
        this.outboundPacketsQueue = Queues.newConcurrentLinkedQueue();
        this.field_181680_j = new ReentrantReadWriteLock();
        this.direction = direction;
    }
    
    public IChatComponent getExitMessage() {
        return this.terminationReason;
    }
    
    static class InboundHandlerTuplePacketListener
    {
        private final Packet packet;
        private final GenericFutureListener[] futureListeners;
        
        static Packet access$100(final InboundHandlerTuplePacketListener inboundHandlerTuplePacketListener) {
            return inboundHandlerTuplePacketListener.packet;
        }
        
        public InboundHandlerTuplePacketListener(final Packet packet, final GenericFutureListener... futureListeners) {
            this.packet = packet;
            this.futureListeners = futureListeners;
        }
        
        static GenericFutureListener[] access$200(final InboundHandlerTuplePacketListener inboundHandlerTuplePacketListener) {
            return inboundHandlerTuplePacketListener.futureListeners;
        }
    }
}
