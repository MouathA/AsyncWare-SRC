package net.minecraft.network;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import javax.crypto.*;

public class NettyEncryptingEncoder extends MessageToByteEncoder
{
    private final NettyEncryptionTranslator encryptionCodec;
    
    public NettyEncryptingEncoder(final Cipher cipher) {
        this.encryptionCodec = new NettyEncryptionTranslator(cipher);
    }
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final ByteBuf byteBuf2) throws ShortBufferException, Exception {
        this.encryptionCodec.cipher(byteBuf, byteBuf2);
    }
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)o, byteBuf);
    }
}
