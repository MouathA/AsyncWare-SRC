package viamcp.handler;

import io.netty.buffer.*;
import com.viaversion.viaversion.util.*;
import io.netty.handler.codec.*;
import io.netty.channel.*;
import java.lang.reflect.*;

public class CommonTransformer
{
    public static final String HANDLER_DECODER_NAME = "via-decoder";
    public static final String HANDLER_ENCODER_NAME = "via-encoder";
    
    public static void compress(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf) throws Exception {
        final ByteBuf buffer = channelHandlerContext.alloc().buffer();
        PipelineUtil.callEncode((MessageToByteEncoder)channelHandlerContext.pipeline().get("compress"), channelHandlerContext, (Object)byteBuf, buffer);
        byteBuf.clear().writeBytes(buffer);
        buffer.release();
    }
    
    public static void decompress(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf) throws InvocationTargetException {
        final ChannelHandler value = channelHandlerContext.pipeline().get("decompress");
        final ByteBuf byteBuf2 = (value instanceof MessageToMessageDecoder) ? PipelineUtil.callDecode((MessageToMessageDecoder)value, channelHandlerContext, (Object)byteBuf).get(0) : PipelineUtil.callDecode((ByteToMessageDecoder)value, channelHandlerContext, (Object)byteBuf).get(0);
        byteBuf.clear().writeBytes(byteBuf2);
        byteBuf2.release();
    }
}
