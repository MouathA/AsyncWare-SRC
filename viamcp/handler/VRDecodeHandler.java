package viamcp.handler;

import io.netty.handler.codec.*;
import com.viaversion.viaversion.api.connection.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.*;
import com.viaversion.viaversion.exception.*;
import com.viaversion.viaversion.util.*;

@ChannelHandler.Sharable
public class VRDecodeHandler extends MessageToMessageDecoder
{
    private boolean skipDoubleTransform;
    private final UserConnection info;
    private boolean handledCompression;
    
    private boolean handleCompressionOrder(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf) throws InvocationTargetException {
        if (this.handledCompression) {
            return false;
        }
        final int index = channelHandlerContext.pipeline().names().indexOf("decompress");
        if (index == -1) {
            return false;
        }
        this.handledCompression = true;
        if (index > channelHandlerContext.pipeline().names().indexOf("via-decoder")) {
            CommonTransformer.decompress(channelHandlerContext, byteBuf);
            final ChannelHandler value = channelHandlerContext.pipeline().get("via-encoder");
            final ChannelHandler value2 = channelHandlerContext.pipeline().get("via-decoder");
            channelHandlerContext.pipeline().remove(value);
            channelHandlerContext.pipeline().remove(value2);
            channelHandlerContext.pipeline().addAfter("compress", "via-encoder", value);
            channelHandlerContext.pipeline().addAfter("decompress", "via-decoder", value2);
            return true;
        }
        return false;
    }
    
    protected void decode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        this.decode(channelHandlerContext, (ByteBuf)o, list);
    }
    
    public UserConnection getInfo() {
        return this.info;
    }
    
    public VRDecodeHandler(final UserConnection info) {
        this.info = info;
    }
    
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        if (this.skipDoubleTransform) {
            this.skipDoubleTransform = false;
            list.add(byteBuf.retain());
            return;
        }
        if (!this.info.checkIncomingPacket()) {
            throw CancelDecoderException.generate((Throwable)null);
        }
        if (!this.info.shouldTransformPacket()) {
            list.add(byteBuf.retain());
            return;
        }
        final ByteBuf writeBytes = channelHandlerContext.alloc().buffer().writeBytes(byteBuf);
        final boolean handleCompressionOrder = this.handleCompressionOrder(channelHandlerContext, writeBytes);
        this.info.transformIncoming(writeBytes, (Function)CancelDecoderException::generate);
        if (handleCompressionOrder) {
            CommonTransformer.compress(channelHandlerContext, writeBytes);
            this.skipDoubleTransform = true;
        }
        list.add(writeBytes.retain());
        writeBytes.release();
    }
    
    public void exceptionCaught(final ChannelHandlerContext channelHandlerContext, final Throwable t) throws Exception {
        if (PipelineUtil.containsCause(t, (Class)CancelCodecException.class)) {
            return;
        }
        super.exceptionCaught(channelHandlerContext, t);
    }
}
