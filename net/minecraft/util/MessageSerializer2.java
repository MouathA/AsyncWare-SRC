package net.minecraft.util;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import net.minecraft.network.*;

public class MessageSerializer2 extends MessageToByteEncoder
{
    protected void encode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final ByteBuf byteBuf2) throws Exception {
        final int readableBytes = byteBuf.readableBytes();
        final int varIntSize = PacketBuffer.getVarIntSize(readableBytes);
        if (varIntSize > 3) {
            throw new IllegalArgumentException("unable to fit " + readableBytes + " into " + 3);
        }
        final PacketBuffer packetBuffer = new PacketBuffer(byteBuf2);
        packetBuffer.ensureWritable(varIntSize + readableBytes);
        packetBuffer.writeVarIntToBuffer(readableBytes);
        packetBuffer.writeBytes(byteBuf, byteBuf.readerIndex(), readableBytes);
    }
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)o, byteBuf);
    }
}
