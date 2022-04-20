package net.minecraft.network;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import java.util.*;
import io.netty.buffer.*;
import javax.crypto.*;

public class NettyEncryptingDecoder extends MessageToMessageDecoder
{
    private final NettyEncryptionTranslator decryptionCodec;
    
    protected void decode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        this.decode(channelHandlerContext, (ByteBuf)o, list);
    }
    
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws ShortBufferException, Exception {
        list.add(this.decryptionCodec.decipher(channelHandlerContext, byteBuf));
    }
    
    public NettyEncryptingDecoder(final Cipher cipher) {
        this.decryptionCodec = new NettyEncryptionTranslator(cipher);
    }
}
