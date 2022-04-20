package net.minecraft.client.stream;

import java.util.*;
import tv.twitch.*;
import com.google.common.collect.*;
import tv.twitch.broadcast.*;

public class IngestServerTester
{
    protected float field_153066_x;
    protected boolean field_153056_n;
    protected List field_153055_m;
    protected IngestTestState field_153047_e;
    protected IngestList field_153046_d;
    protected IngestServer field_153059_q;
    protected long field_153054_l;
    protected int field_153062_t;
    protected boolean field_176008_y;
    protected boolean field_176007_z;
    protected IStreamCallbacks field_153057_o;
    protected long field_153050_h;
    protected IngestTestListener field_153044_b;
    protected long field_153048_f;
    protected IStatCallbacks field_176006_B;
    protected AudioParams audioParameters;
    protected long field_153064_v;
    protected boolean field_153061_s;
    protected Stream field_153045_c;
    protected long field_153049_g;
    protected boolean field_153060_r;
    protected float field_153065_w;
    protected RTMPState field_153051_i;
    protected boolean field_176009_x;
    protected int field_153063_u;
    protected VideoParams field_153052_j;
    protected IStreamCallbacks field_176005_A;
    protected IStatCallbacks field_153058_p;
    
    protected void func_153038_n() {
        final float n = (float)this.func_153037_m();
        switch (IngestServerTester$3.$SwitchMap$net$minecraft$client$stream$IngestServerTester$IngestTestState[this.field_153047_e.ordinal()]) {
            case 1:
            case 3:
            case 6:
            case 7:
            case 8:
            case 9: {
                this.field_153066_x = 0.0f;
                break;
            }
            case 2: {
                this.field_153066_x = 1.0f;
                break;
            }
            default: {
                this.field_153066_x = n / this.field_153048_f;
                break;
            }
        }
        switch (this.field_153047_e) {
            case Finished:
            case Cancelled:
            case Failed: {
                this.field_153065_w = 1.0f;
                break;
            }
            default: {
                this.field_153065_w = this.field_153062_t / (float)this.field_153046_d.getServers().length;
                this.field_153065_w += this.field_153066_x / this.field_153046_d.getServers().length;
                break;
            }
        }
    }
    
    public int func_153028_p() {
        return this.field_153062_t;
    }
    
    protected boolean func_153029_c(final IngestServer ingestServer) {
        if (this.field_153061_s || this.field_153060_r || this.func_153037_m() >= this.field_153048_f) {
            this.func_153034_a(IngestTestState.DoneTestingServer);
            return true;
        }
        if (this.field_176008_y || this.field_176007_z) {
            return true;
        }
        if (ErrorCode.failed(this.field_153045_c.submitVideoFrame((FrameBuffer)this.field_153055_m.get(this.field_153063_u)))) {
            this.field_153056_n = false;
            this.func_153034_a(IngestTestState.DoneTestingServer);
            return false;
        }
        this.field_153063_u = (this.field_153063_u + 1) % this.field_153055_m.size();
        this.field_153045_c.pollStats();
        if (this.field_153051_i == RTMPState.SendVideo) {
            this.func_153034_a(IngestTestState.TestingServer);
            if (this.func_153037_m() > 0L && this.field_153050_h > this.field_153064_v) {
                ingestServer.bitrateKbps = this.field_153050_h * 8L / (float)this.func_153037_m();
                this.field_153064_v = this.field_153050_h;
            }
        }
        return true;
    }
    
    public void func_153042_a(final IngestTestListener field_153044_b) {
        this.field_153044_b = field_153044_b;
    }
    
    public IngestServerTester(final Stream field_153045_c, final IngestList field_153046_d) {
        this.field_153044_b = null;
        this.field_153045_c = null;
        this.field_153046_d = null;
        this.field_153047_e = IngestTestState.Uninitalized;
        this.field_153048_f = 8000L;
        this.field_153049_g = 2000L;
        this.field_153050_h = 0L;
        this.field_153051_i = RTMPState.Invalid;
        this.field_153052_j = null;
        this.audioParameters = null;
        this.field_153054_l = 0L;
        this.field_153055_m = null;
        this.field_153056_n = false;
        this.field_153057_o = null;
        this.field_153058_p = null;
        this.field_153059_q = null;
        this.field_153060_r = false;
        this.field_153061_s = false;
        this.field_153062_t = -1;
        this.field_153063_u = 0;
        this.field_153064_v = 0L;
        this.field_153065_w = 0.0f;
        this.field_153066_x = 0.0f;
        this.field_176009_x = false;
        this.field_176008_y = false;
        this.field_176007_z = false;
        this.field_176005_A = (IStreamCallbacks)new IStreamCallbacks() {
            final IngestServerTester this$0;
            
            public void sendEndSpanMetaDataCallback(final ErrorCode errorCode) {
            }
            
            public void getIngestServersCallback(final ErrorCode errorCode, final IngestList list) {
            }
            
            public void getStreamInfoCallback(final ErrorCode errorCode, final StreamInfo streamInfo) {
            }
            
            public void getGameNameListCallback(final ErrorCode errorCode, final GameInfoList list) {
            }
            
            public void bufferUnlockCallback(final long n) {
            }
            
            public void getUserInfoCallback(final ErrorCode errorCode, final UserInfo userInfo) {
            }
            
            public void setStreamInfoCallback(final ErrorCode errorCode) {
            }
            
            public void runCommercialCallback(final ErrorCode errorCode) {
            }
            
            public void stopCallback(final ErrorCode errorCode) {
                if (ErrorCode.failed(errorCode)) {
                    System.out.println("IngestTester.stopCallback failed to stop - " + this.this$0.field_153059_q.serverName + ": " + errorCode.toString());
                }
                this.this$0.field_176007_z = false;
                this.this$0.field_176009_x = false;
                this.this$0.func_153034_a(IngestTestState.DoneTestingServer);
                this.this$0.field_153059_q = null;
                if (this.this$0.field_153060_r) {
                    this.this$0.func_153034_a(IngestTestState.Cancelling);
                }
            }
            
            public void sendActionMetaDataCallback(final ErrorCode errorCode) {
            }
            
            public void loginCallback(final ErrorCode errorCode, final ChannelInfo channelInfo) {
            }
            
            public void requestAuthTokenCallback(final ErrorCode errorCode, final AuthToken authToken) {
            }
            
            public void sendStartSpanMetaDataCallback(final ErrorCode errorCode) {
            }
            
            public void getArchivingStateCallback(final ErrorCode errorCode, final ArchivingState archivingState) {
            }
            
            public void startCallback(final ErrorCode errorCode) {
                this.this$0.field_176008_y = false;
                if (ErrorCode.succeeded(errorCode)) {
                    this.this$0.field_176009_x = true;
                    this.this$0.field_153054_l = System.currentTimeMillis();
                    this.this$0.func_153034_a(IngestTestState.ConnectingToServer);
                }
                else {
                    this.this$0.field_153056_n = false;
                    this.this$0.func_153034_a(IngestTestState.DoneTestingServer);
                }
            }
        };
        this.field_176006_B = (IStatCallbacks)new IStatCallbacks() {
            final IngestServerTester this$0;
            
            public void statCallback(final StatType statType, final long field_153050_h) {
                switch (IngestServerTester$3.$SwitchMap$tv$twitch$broadcast$StatType[statType.ordinal()]) {
                    case 1: {
                        this.this$0.field_153051_i = RTMPState.lookupValue((int)field_153050_h);
                        break;
                    }
                    case 2: {
                        this.this$0.field_153050_h = field_153050_h;
                        break;
                    }
                }
            }
        };
        this.field_153045_c = field_153045_c;
        this.field_153046_d = field_153046_d;
    }
    
    public IngestServer func_153040_c() {
        return this.field_153059_q;
    }
    
    public void func_176004_j() {
        if (this.field_153047_e == IngestTestState.Uninitalized) {
            this.field_153062_t = 0;
            this.field_153060_r = false;
            this.field_153061_s = false;
            this.field_176009_x = false;
            this.field_176008_y = false;
            this.field_176007_z = false;
            this.field_153058_p = this.field_153045_c.getStatCallbacks();
            this.field_153045_c.setStatCallbacks(this.field_176006_B);
            this.field_153057_o = this.field_153045_c.getStreamCallbacks();
            this.field_153045_c.setStreamCallbacks(this.field_176005_A);
            this.field_153052_j = new VideoParams();
            this.field_153052_j.targetFps = 60;
            this.field_153052_j.maxKbps = 3500;
            this.field_153052_j.outputWidth = 1280;
            this.field_153052_j.outputHeight = 720;
            this.field_153052_j.pixelFormat = PixelFormat.TTV_PF_BGRA;
            this.field_153052_j.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_HIGH;
            this.field_153052_j.disableAdaptiveBitrate = true;
            this.field_153052_j.verticalFlip = false;
            this.field_153045_c.getDefaultParams(this.field_153052_j);
            this.audioParameters = new AudioParams();
            this.audioParameters.audioEnabled = false;
            this.audioParameters.enableMicCapture = false;
            this.audioParameters.enablePlaybackCapture = false;
            this.audioParameters.enablePassthroughAudio = false;
            this.field_153055_m = Lists.newArrayList();
            while (true) {
                final FrameBuffer allocateFrameBuffer = this.field_153045_c.allocateFrameBuffer(this.field_153052_j.outputWidth * this.field_153052_j.outputHeight * 4);
                if (!allocateFrameBuffer.getIsValid()) {
                    break;
                }
                this.field_153055_m.add(allocateFrameBuffer);
                this.field_153045_c.randomizeFrameBuffer(allocateFrameBuffer);
                int n = 0;
                ++n;
            }
            this.func_153031_o();
            this.func_153034_a(IngestTestState.Failed);
        }
    }
    
    public float func_153030_h() {
        return this.field_153066_x;
    }
    
    protected void func_153034_a(final IngestTestState field_153047_e) {
        if (field_153047_e != this.field_153047_e) {
            this.field_153047_e = field_153047_e;
            if (this.field_153044_b != null) {
                this.field_153044_b.func_152907_a(this, field_153047_e);
            }
        }
    }
    
    protected long func_153037_m() {
        return System.currentTimeMillis() - this.field_153054_l;
    }
    
    protected boolean func_153036_a(final IngestServer field_153059_q) {
        this.field_153056_n = true;
        this.field_153050_h = 0L;
        this.field_153051_i = RTMPState.Idle;
        this.field_153059_q = field_153059_q;
        this.field_176008_y = true;
        this.func_153034_a(IngestTestState.ConnectingToServer);
        if (ErrorCode.failed(this.field_153045_c.start(this.field_153052_j, this.audioParameters, field_153059_q, StartFlags.TTV_Start_BandwidthTest, true))) {
            this.field_176008_y = false;
            this.field_153056_n = false;
            this.func_153034_a(IngestTestState.DoneTestingServer);
            return false;
        }
        this.field_153064_v = this.field_153050_h;
        field_153059_q.bitrateKbps = 0.0f;
        this.field_153063_u = 0;
        return true;
    }
    
    protected void func_153035_b(final IngestServer ingestServer) {
        if (this.field_176008_y) {
            this.field_153061_s = true;
        }
        else if (this.field_176009_x) {
            this.field_176007_z = true;
            final ErrorCode stop = this.field_153045_c.stop(true);
            if (ErrorCode.failed(stop)) {
                this.field_176005_A.stopCallback(ErrorCode.TTV_EC_SUCCESS);
                System.out.println("Stop failed: " + stop.toString());
            }
            this.field_153045_c.pollStats();
        }
        else {
            this.field_176005_A.stopCallback(ErrorCode.TTV_EC_SUCCESS);
        }
    }
    
    protected void func_153031_o() {
        this.field_153059_q = null;
        if (this.field_153055_m != null) {
            while (0 < this.field_153055_m.size()) {
                this.field_153055_m.get(0).free();
                int n = 0;
                ++n;
            }
            this.field_153055_m = null;
        }
        if (this.field_153045_c.getStatCallbacks() == this.field_176006_B) {
            this.field_153045_c.setStatCallbacks(this.field_153058_p);
            this.field_153058_p = null;
        }
        if (this.field_153045_c.getStreamCallbacks() == this.field_176005_A) {
            this.field_153045_c.setStreamCallbacks(this.field_153057_o);
            this.field_153057_o = null;
        }
    }
    
    public void func_153041_j() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_acmpeq       247
        //     4: aload_0        
        //     5: getfield        net/minecraft/client/stream/IngestServerTester.field_153047_e:Lnet/minecraft/client/stream/IngestServerTester$IngestTestState;
        //     8: getstatic       net/minecraft/client/stream/IngestServerTester$IngestTestState.Uninitalized:Lnet/minecraft/client/stream/IngestServerTester$IngestTestState;
        //    11: if_acmpeq       247
        //    14: aload_0        
        //    15: getfield        net/minecraft/client/stream/IngestServerTester.field_176008_y:Z
        //    18: ifne            247
        //    21: aload_0        
        //    22: getfield        net/minecraft/client/stream/IngestServerTester.field_176007_z:Z
        //    25: ifne            247
        //    28: getstatic       net/minecraft/client/stream/IngestServerTester$3.$SwitchMap$net$minecraft$client$stream$IngestServerTester$IngestTestState:[I
        //    31: aload_0        
        //    32: getfield        net/minecraft/client/stream/IngestServerTester.field_153047_e:Lnet/minecraft/client/stream/IngestServerTester$IngestTestState;
        //    35: invokevirtual   net/minecraft/client/stream/IngestServerTester$IngestTestState.ordinal:()I
        //    38: iaload         
        //    39: tableswitch {
        //                2: 72
        //                3: 72
        //                4: 200
        //                5: 200
        //                6: 212
        //          default: 219
        //        }
        //    72: aload_0        
        //    73: getfield        net/minecraft/client/stream/IngestServerTester.field_153059_q:Ltv/twitch/broadcast/IngestServer;
        //    76: ifnull          112
        //    79: aload_0        
        //    80: getfield        net/minecraft/client/stream/IngestServerTester.field_153061_s:Z
        //    83: ifne            93
        //    86: aload_0        
        //    87: getfield        net/minecraft/client/stream/IngestServerTester.field_153056_n:Z
        //    90: ifne            101
        //    93: aload_0        
        //    94: getfield        net/minecraft/client/stream/IngestServerTester.field_153059_q:Ltv/twitch/broadcast/IngestServer;
        //    97: fconst_0       
        //    98: putfield        tv/twitch/broadcast/IngestServer.bitrateKbps:F
        //   101: aload_0        
        //   102: aload_0        
        //   103: getfield        net/minecraft/client/stream/IngestServerTester.field_153059_q:Ltv/twitch/broadcast/IngestServer;
        //   106: invokevirtual   net/minecraft/client/stream/IngestServerTester.func_153035_b:(Ltv/twitch/broadcast/IngestServer;)V
        //   109: goto            219
        //   112: aload_0        
        //   113: lconst_0       
        //   114: putfield        net/minecraft/client/stream/IngestServerTester.field_153054_l:J
        //   117: aload_0        
        //   118: iconst_0       
        //   119: putfield        net/minecraft/client/stream/IngestServerTester.field_153061_s:Z
        //   122: aload_0        
        //   123: iconst_1       
        //   124: putfield        net/minecraft/client/stream/IngestServerTester.field_153056_n:Z
        //   127: aload_0        
        //   128: getfield        net/minecraft/client/stream/IngestServerTester.field_153047_e:Lnet/minecraft/client/stream/IngestServerTester$IngestTestState;
        //   131: getstatic       net/minecraft/client/stream/IngestServerTester$IngestTestState.Starting:Lnet/minecraft/client/stream/IngestServerTester$IngestTestState;
        //   134: if_acmpeq       147
        //   137: aload_0        
        //   138: dup            
        //   139: getfield        net/minecraft/client/stream/IngestServerTester.field_153062_t:I
        //   142: iconst_1       
        //   143: iadd           
        //   144: putfield        net/minecraft/client/stream/IngestServerTester.field_153062_t:I
        //   147: aload_0        
        //   148: getfield        net/minecraft/client/stream/IngestServerTester.field_153062_t:I
        //   151: aload_0        
        //   152: getfield        net/minecraft/client/stream/IngestServerTester.field_153046_d:Ltv/twitch/broadcast/IngestList;
        //   155: invokevirtual   tv/twitch/broadcast/IngestList.getServers:()[Ltv/twitch/broadcast/IngestServer;
        //   158: arraylength    
        //   159: if_icmpge       190
        //   162: aload_0        
        //   163: aload_0        
        //   164: getfield        net/minecraft/client/stream/IngestServerTester.field_153046_d:Ltv/twitch/broadcast/IngestList;
        //   167: invokevirtual   tv/twitch/broadcast/IngestList.getServers:()[Ltv/twitch/broadcast/IngestServer;
        //   170: aload_0        
        //   171: getfield        net/minecraft/client/stream/IngestServerTester.field_153062_t:I
        //   174: aaload         
        //   175: putfield        net/minecraft/client/stream/IngestServerTester.field_153059_q:Ltv/twitch/broadcast/IngestServer;
        //   178: aload_0        
        //   179: aload_0        
        //   180: getfield        net/minecraft/client/stream/IngestServerTester.field_153059_q:Ltv/twitch/broadcast/IngestServer;
        //   183: invokevirtual   net/minecraft/client/stream/IngestServerTester.func_153036_a:(Ltv/twitch/broadcast/IngestServer;)Z
        //   186: pop            
        //   187: goto            219
        //   190: aload_0        
        //   191: getstatic       net/minecraft/client/stream/IngestServerTester$IngestTestState.Finished:Lnet/minecraft/client/stream/IngestServerTester$IngestTestState;
        //   194: invokevirtual   net/minecraft/client/stream/IngestServerTester.func_153034_a:(Lnet/minecraft/client/stream/IngestServerTester$IngestTestState;)V
        //   197: goto            219
        //   200: aload_0        
        //   201: aload_0        
        //   202: getfield        net/minecraft/client/stream/IngestServerTester.field_153059_q:Ltv/twitch/broadcast/IngestServer;
        //   205: invokevirtual   net/minecraft/client/stream/IngestServerTester.func_153029_c:(Ltv/twitch/broadcast/IngestServer;)Z
        //   208: pop            
        //   209: goto            219
        //   212: aload_0        
        //   213: getstatic       net/minecraft/client/stream/IngestServerTester$IngestTestState.Cancelled:Lnet/minecraft/client/stream/IngestServerTester$IngestTestState;
        //   216: invokevirtual   net/minecraft/client/stream/IngestServerTester.func_153034_a:(Lnet/minecraft/client/stream/IngestServerTester$IngestTestState;)V
        //   219: aload_0        
        //   220: invokevirtual   net/minecraft/client/stream/IngestServerTester.func_153038_n:()V
        //   223: aload_0        
        //   224: getfield        net/minecraft/client/stream/IngestServerTester.field_153047_e:Lnet/minecraft/client/stream/IngestServerTester$IngestTestState;
        //   227: getstatic       net/minecraft/client/stream/IngestServerTester$IngestTestState.Cancelled:Lnet/minecraft/client/stream/IngestServerTester$IngestTestState;
        //   230: if_acmpeq       243
        //   233: aload_0        
        //   234: getfield        net/minecraft/client/stream/IngestServerTester.field_153047_e:Lnet/minecraft/client/stream/IngestServerTester$IngestTestState;
        //   237: getstatic       net/minecraft/client/stream/IngestServerTester$IngestTestState.Finished:Lnet/minecraft/client/stream/IngestServerTester$IngestTestState;
        //   240: if_acmpne       247
        //   243: aload_0        
        //   244: invokevirtual   net/minecraft/client/stream/IngestServerTester.func_153031_o:()V
        //   247: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void func_153039_l() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_acmpeq       31
        //     4: aload_0        
        //     5: getfield        net/minecraft/client/stream/IngestServerTester.field_153060_r:Z
        //     8: ifne            31
        //    11: aload_0        
        //    12: iconst_1       
        //    13: putfield        net/minecraft/client/stream/IngestServerTester.field_153060_r:Z
        //    16: aload_0        
        //    17: getfield        net/minecraft/client/stream/IngestServerTester.field_153059_q:Ltv/twitch/broadcast/IngestServer;
        //    20: ifnull          31
        //    23: aload_0        
        //    24: getfield        net/minecraft/client/stream/IngestServerTester.field_153059_q:Ltv/twitch/broadcast/IngestServer;
        //    27: fconst_0       
        //    28: putfield        tv/twitch/broadcast/IngestServer.bitrateKbps:F
        //    31: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public enum IngestTestState
    {
        ConnectingToServer("ConnectingToServer", 2), 
        Uninitalized("Uninitalized", 0);
        
        private static final IngestTestState[] $VALUES;
        
        TestingServer("TestingServer", 3), 
        Cancelling("Cancelling", 6), 
        DoneTestingServer("DoneTestingServer", 4), 
        Cancelled("Cancelled", 7), 
        Failed("Failed", 8), 
        Finished("Finished", 5), 
        Starting("Starting", 1);
        
        private IngestTestState(final String s, final int n) {
        }
        
        static {
            $VALUES = new IngestTestState[] { IngestTestState.Uninitalized, IngestTestState.Starting, IngestTestState.ConnectingToServer, IngestTestState.TestingServer, IngestTestState.DoneTestingServer, IngestTestState.Finished, IngestTestState.Cancelling, IngestTestState.Cancelled, IngestTestState.Failed };
        }
    }
    
    public interface IngestTestListener
    {
        void func_152907_a(final IngestServerTester p0, final IngestTestState p1);
    }
}
