package viamcp.utils;

import io.netty.channel.*;

public class NettyUtil
{
    public static ChannelPipeline decodeEncodePlacement(final ChannelPipeline channelPipeline, String s, final String s2, final ChannelHandler channelHandler) {
        final String s3 = s;
        switch (s3.hashCode()) {
            case 1542433860: {
                if (s3.equals("decoder")) {
                    break;
                }
                break;
            }
            case -1607367396: {
                if (s3.equals("encoder")) {}
                break;
            }
        }
        switch (true) {
            case 0: {
                if (channelPipeline.get("via-decoder") != null) {
                    s = "via-decoder";
                    break;
                }
                break;
            }
            case 1: {
                if (channelPipeline.get("via-encoder") != null) {
                    s = "via-encoder";
                    break;
                }
                break;
            }
        }
        return channelPipeline.addBefore(s, s2, channelHandler);
    }
}
