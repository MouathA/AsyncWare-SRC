package net.minecraft.util;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import java.io.*;
import net.minecraft.network.*;
import org.apache.logging.log4j.*;

public class MessageSerializer extends MessageToByteEncoder
{
    private static final Logger logger;
    private final EnumPacketDirection direction;
    private static final Marker RECEIVED_PACKET_MARKER;
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (Packet)o, byteBuf);
    }
    
    public MessageSerializer(final EnumPacketDirection direction) {
        this.direction = direction;
    }
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Packet packet, final ByteBuf byteBuf) throws Exception, IOException {
        final Integer packetId = ((EnumConnectionState)channelHandlerContext.channel().attr(NetworkManager.attrKeyConnectionState).get()).getPacketId(this.direction, packet);
        if (MessageSerializer.logger.isDebugEnabled()) {
            MessageSerializer.logger.debug(MessageSerializer.RECEIVED_PACKET_MARKER, "OUT: [{}:{}] {}", new Object[] { channelHandlerContext.channel().attr(NetworkManager.attrKeyConnectionState).get(), packetId, packet.getClass().getName() });
        }
        if (packetId == null) {
            throw new IOException("Can't serialize unregistered packet");
        }
        final PacketBuffer packetBuffer = new PacketBuffer(byteBuf);
        packetBuffer.writeVarIntToBuffer(packetId);
        packet.writePacketData(packetBuffer);
    }
    
    static {
        logger = LogManager.getLogger();
        RECEIVED_PACKET_MARKER = MarkerManager.getMarker("PACKET_SENT", NetworkManager.logMarkerPackets);
    }
}
