package net.minecraft.client.stream;

import java.util.*;
import net.minecraft.util.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import tv.twitch.*;
import tv.twitch.broadcast.*;

public class BroadcastController
{
    protected boolean field_152871_g;
    protected ArchivingState field_152889_y;
    protected String field_152880_p;
    protected IngestServerTester field_152860_A;
    protected IngestList ingestList;
    protected List field_152875_k;
    protected Stream field_152873_i;
    private static final ThreadSafeBoundList field_152862_C;
    protected final int field_152865_a = 30;
    protected String field_152868_d;
    protected final int field_152866_b = 3;
    protected boolean field_152877_m;
    private ErrorCode errorCode;
    protected boolean field_152878_n;
    protected BroadcastState broadcastState;
    protected BroadcastListener broadcastListener;
    protected IngestServer field_152884_t;
    protected List field_152874_j;
    protected boolean field_152876_l;
    protected AuthToken authenticationToken;
    private static final Logger logger;
    protected IStreamCallbacks field_177948_B;
    protected IStatCallbacks field_177949_C;
    protected AudioParams audioParamaters;
    protected long field_152890_z;
    private String field_152863_D;
    protected String field_152869_e;
    protected String field_152870_f;
    protected StreamInfo streamInfo;
    protected Core field_152872_h;
    protected UserInfo userInfo;
    protected VideoParams videoParamaters;
    protected ChannelInfo channelInfo;
    
    protected void func_152835_I() {
        final long nanoTime = System.nanoTime();
        if ((nanoTime - this.field_152890_z) / 1000000000L >= 30L) {
            this.field_152890_z = nanoTime;
            final ErrorCode streamInfo = this.field_152873_i.getStreamInfo(this.authenticationToken, this.field_152880_p);
            if (ErrorCode.failed(streamInfo)) {
                this.func_152820_d(String.format("Error in TTV_GetStreamInfo: %s", ErrorCode.getString(streamInfo)));
            }
        }
    }
    
    public IngestList func_152855_t() {
        return this.ingestList;
    }
    
    public boolean func_152849_q() {
        return this.field_152877_m;
    }
    
    static {
        logger = LogManager.getLogger();
        field_152862_C = new ThreadSafeBoundList(String.class, 50);
    }
    
    public ErrorCode submitStreamFrame(final FrameBuffer p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_acmpne       12
        //     4: aload_0        
        //     5: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152854_G:()Z
        //     8: pop            
        //     9: goto            20
        //    12: aload_0        
        //    13: if_acmpeq       20
        //    16: getstatic       tv/twitch/ErrorCode.TTV_EC_STREAM_NOT_STARTED:Ltv/twitch/ErrorCode;
        //    19: areturn        
        //    20: aload_0        
        //    21: getfield        net/minecraft/client/stream/BroadcastController.field_152873_i:Ltv/twitch/broadcast/Stream;
        //    24: aload_1        
        //    25: invokevirtual   tv/twitch/broadcast/Stream.submitVideoFrame:(Ltv/twitch/broadcast/FrameBuffer;)Ltv/twitch/ErrorCode;
        //    28: astore_2       
        //    29: aload_2        
        //    30: getstatic       tv/twitch/ErrorCode.TTV_EC_SUCCESS:Ltv/twitch/ErrorCode;
        //    33: if_acmpeq       107
        //    36: aload_2        
        //    37: invokestatic    tv/twitch/ErrorCode.getString:(Ltv/twitch/ErrorCode;)Ljava/lang/String;
        //    40: astore_3       
        //    41: aload_2        
        //    42: invokestatic    tv/twitch/ErrorCode.succeeded:(Ltv/twitch/ErrorCode;)Z
        //    45: ifeq            68
        //    48: aload_0        
        //    49: ldc             "Warning in SubmitTexturePointer: %s\n"
        //    51: iconst_1       
        //    52: anewarray       Ljava/lang/Object;
        //    55: dup            
        //    56: iconst_0       
        //    57: aload_3        
        //    58: aastore        
        //    59: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //    62: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152832_e:(Ljava/lang/String;)V
        //    65: goto            90
        //    68: aload_0        
        //    69: ldc             "Error in SubmitTexturePointer: %s\n"
        //    71: iconst_1       
        //    72: anewarray       Ljava/lang/Object;
        //    75: dup            
        //    76: iconst_0       
        //    77: aload_3        
        //    78: aastore        
        //    79: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //    82: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152820_d:(Ljava/lang/String;)V
        //    85: aload_0        
        //    86: invokevirtual   net/minecraft/client/stream/BroadcastController.stopBroadcasting:()Z
        //    89: pop            
        //    90: aload_0        
        //    91: getfield        net/minecraft/client/stream/BroadcastController.broadcastListener:Lnet/minecraft/client/stream/BroadcastController$BroadcastListener;
        //    94: ifnull          107
        //    97: aload_0        
        //    98: getfield        net/minecraft/client/stream/BroadcastController.broadcastListener:Lnet/minecraft/client/stream/BroadcastController$BroadcastListener;
        //   101: aload_2        
        //   102: invokeinterface net/minecraft/client/stream/BroadcastController$BroadcastListener.func_152893_b:(Ltv/twitch/ErrorCode;)V
        //   107: aload_2        
        //   108: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    protected PixelFormat func_152826_z() {
        return PixelFormat.TTV_PF_RGBA;
    }
    
    public boolean func_152818_a(final String p0, final AuthToken p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_acmpne       6
        //     4: iconst_0       
        //     5: ireturn        
        //     6: aload_0        
        //     7: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152845_C:()Z
        //    10: pop            
        //    11: aload_1        
        //    12: ifnull          77
        //    15: aload_1        
        //    16: invokevirtual   java/lang/String.isEmpty:()Z
        //    19: ifne            77
        //    22: aload_2        
        //    23: ifnull          69
        //    26: aload_2        
        //    27: getfield        tv/twitch/AuthToken.data:Ljava/lang/String;
        //    30: ifnull          69
        //    33: aload_2        
        //    34: getfield        tv/twitch/AuthToken.data:Ljava/lang/String;
        //    37: invokevirtual   java/lang/String.isEmpty:()Z
        //    40: ifne            69
        //    43: aload_0        
        //    44: aload_1        
        //    45: putfield        net/minecraft/client/stream/BroadcastController.field_152880_p:Ljava/lang/String;
        //    48: aload_0        
        //    49: aload_2        
        //    50: putfield        net/minecraft/client/stream/BroadcastController.authenticationToken:Ltv/twitch/AuthToken;
        //    53: aload_0        
        //    54: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152858_b:()Z
        //    57: ifeq            67
        //    60: aload_0        
        //    61: getstatic       net/minecraft/client/stream/BroadcastController$BroadcastState.Authenticated:Lnet/minecraft/client/stream/BroadcastController$BroadcastState;
        //    64: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152827_a:(Lnet/minecraft/client/stream/BroadcastController$BroadcastState;)V
        //    67: iconst_1       
        //    68: ireturn        
        //    69: aload_0        
        //    70: ldc             "Auth token must be valid"
        //    72: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152820_d:(Ljava/lang/String;)V
        //    75: iconst_0       
        //    76: ireturn        
        //    77: aload_0        
        //    78: ldc             "Username must be valid"
        //    80: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152820_d:(Ljava/lang/String;)V
        //    83: iconst_0       
        //    84: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void statCallback() {
        if (this.broadcastState != BroadcastState.Uninitialized) {
            if (this.field_152860_A != null) {
                this.field_152860_A.func_153039_l();
            }
            while (this.field_152860_A != null) {
                Thread.sleep(200L);
                this.func_152821_H();
            }
            this.func_152851_B();
        }
    }
    
    public void func_152824_a(final IngestServer field_152884_t) {
        this.field_152884_t = field_152884_t;
    }
    
    public boolean stopBroadcasting() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_acmpeq       6
        //     4: iconst_0       
        //     5: ireturn        
        //     6: aload_0        
        //     7: getfield        net/minecraft/client/stream/BroadcastController.field_152873_i:Ltv/twitch/broadcast/Stream;
        //    10: iconst_1       
        //    11: invokevirtual   tv/twitch/broadcast/Stream.stop:(Z)Ltv/twitch/ErrorCode;
        //    14: astore_1       
        //    15: aload_1        
        //    16: invokestatic    tv/twitch/ErrorCode.failed:(Ltv/twitch/ErrorCode;)Z
        //    19: ifeq            47
        //    22: aload_1        
        //    23: invokestatic    tv/twitch/ErrorCode.getString:(Ltv/twitch/ErrorCode;)Ljava/lang/String;
        //    26: astore_2       
        //    27: aload_0        
        //    28: ldc_w           "Error while stopping the broadcast: %s"
        //    31: iconst_1       
        //    32: anewarray       Ljava/lang/Object;
        //    35: dup            
        //    36: iconst_0       
        //    37: aload_2        
        //    38: aastore        
        //    39: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //    42: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152820_d:(Ljava/lang/String;)V
        //    45: iconst_0       
        //    46: ireturn        
        //    47: aload_0        
        //    48: getstatic       net/minecraft/client/stream/BroadcastController$BroadcastState.Stopping:Lnet/minecraft/client/stream/BroadcastController$BroadcastState;
        //    51: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152827_a:(Lnet/minecraft/client/stream/BroadcastController$BroadcastState;)V
        //    54: aload_1        
        //    55: invokestatic    tv/twitch/ErrorCode.succeeded:(Ltv/twitch/ErrorCode;)Z
        //    58: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public IngestServer func_152833_s() {
        return this.field_152884_t;
    }
    
    protected void func_152831_M() {
        while (0 < this.field_152874_j.size()) {
            this.field_152874_j.get(0).free();
            int n = 0;
            ++n;
        }
        this.field_152875_k.clear();
        this.field_152874_j.clear();
    }
    
    public VideoParams func_152834_a(final int maxKbps, final int targetFps, final float n, final float n2) {
        final int[] maxResolution = this.field_152873_i.getMaxResolution(maxKbps, targetFps, n, n2);
        final VideoParams videoParams = new VideoParams();
        videoParams.maxKbps = maxKbps;
        videoParams.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_HIGH;
        videoParams.pixelFormat = this.func_152826_z();
        videoParams.targetFps = targetFps;
        videoParams.outputWidth = maxResolution[0];
        videoParams.outputHeight = maxResolution[1];
        videoParams.disableAdaptiveBitrate = false;
        videoParams.verticalFlip = false;
        return videoParams;
    }
    
    protected void func_152832_e(final String s) {
        BroadcastController.field_152862_C.func_152757_a("<Warning> " + s);
        BroadcastController.logger.warn(TwitchStream.STREAM_MARKER, "[Broadcast controller] {}", new Object[] { s });
    }
    
    public boolean func_152845_C() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_acmpne       6
        //     4: iconst_0       
        //     5: ireturn        
        //     6: aload_0        
        //     7: if_acmpeq       19
        //    10: aload_0        
        //    11: getfield        net/minecraft/client/stream/BroadcastController.field_152873_i:Ltv/twitch/broadcast/Stream;
        //    14: iconst_0       
        //    15: invokevirtual   tv/twitch/broadcast/Stream.stop:(Z)Ltv/twitch/ErrorCode;
        //    18: pop            
        //    19: aload_0        
        //    20: ldc_w           ""
        //    23: putfield        net/minecraft/client/stream/BroadcastController.field_152880_p:Ljava/lang/String;
        //    26: aload_0        
        //    27: new             Ltv/twitch/AuthToken;
        //    30: dup            
        //    31: invokespecial   tv/twitch/AuthToken.<init>:()V
        //    34: putfield        net/minecraft/client/stream/BroadcastController.authenticationToken:Ltv/twitch/AuthToken;
        //    37: aload_0        
        //    38: getfield        net/minecraft/client/stream/BroadcastController.field_152877_m:Z
        //    41: ifne            46
        //    44: iconst_0       
        //    45: ireturn        
        //    46: aload_0        
        //    47: iconst_0       
        //    48: putfield        net/minecraft/client/stream/BroadcastController.field_152877_m:Z
        //    51: aload_0        
        //    52: getfield        net/minecraft/client/stream/BroadcastController.field_152878_n:Z
        //    55: ifne            86
        //    58: aload_0        
        //    59: getfield        net/minecraft/client/stream/BroadcastController.broadcastListener:Lnet/minecraft/client/stream/BroadcastController$BroadcastListener;
        //    62: ifnull          74
        //    65: aload_0        
        //    66: getfield        net/minecraft/client/stream/BroadcastController.broadcastListener:Lnet/minecraft/client/stream/BroadcastController$BroadcastListener;
        //    69: invokeinterface net/minecraft/client/stream/BroadcastController$BroadcastListener.func_152895_a:()V
        //    74: goto            86
        //    77: astore_1       
        //    78: aload_0        
        //    79: aload_1        
        //    80: invokevirtual   java/lang/Exception.toString:()Ljava/lang/String;
        //    83: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152820_d:(Ljava/lang/String;)V
        //    86: aload_0        
        //    87: getstatic       net/minecraft/client/stream/BroadcastController$BroadcastState.Initialized:Lnet/minecraft/client/stream/BroadcastController$BroadcastState;
        //    90: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152827_a:(Lnet/minecraft/client/stream/BroadcastController$BroadcastState;)V
        //    93: iconst_1       
        //    94: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public IngestServerTester func_152838_J() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_acmpne       55
        //     4: aload_0        
        //     5: getfield        net/minecraft/client/stream/BroadcastController.ingestList:Ltv/twitch/broadcast/IngestList;
        //     8: ifnull          55
        //    11: aload_0        
        //    12: if_acmpne       17
        //    15: aconst_null    
        //    16: areturn        
        //    17: aload_0        
        //    18: new             Lnet/minecraft/client/stream/IngestServerTester;
        //    21: dup            
        //    22: aload_0        
        //    23: getfield        net/minecraft/client/stream/BroadcastController.field_152873_i:Ltv/twitch/broadcast/Stream;
        //    26: aload_0        
        //    27: getfield        net/minecraft/client/stream/BroadcastController.ingestList:Ltv/twitch/broadcast/IngestList;
        //    30: invokespecial   net/minecraft/client/stream/IngestServerTester.<init>:(Ltv/twitch/broadcast/Stream;Ltv/twitch/broadcast/IngestList;)V
        //    33: putfield        net/minecraft/client/stream/BroadcastController.field_152860_A:Lnet/minecraft/client/stream/IngestServerTester;
        //    36: aload_0        
        //    37: getfield        net/minecraft/client/stream/BroadcastController.field_152860_A:Lnet/minecraft/client/stream/IngestServerTester;
        //    40: invokevirtual   net/minecraft/client/stream/IngestServerTester.func_176004_j:()V
        //    43: aload_0        
        //    44: getstatic       net/minecraft/client/stream/BroadcastController$BroadcastState.IngestTesting:Lnet/minecraft/client/stream/BroadcastController$BroadcastState;
        //    47: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152827_a:(Lnet/minecraft/client/stream/BroadcastController$BroadcastState;)V
        //    50: aload_0        
        //    51: getfield        net/minecraft/client/stream/BroadcastController.field_152860_A:Lnet/minecraft/client/stream/IngestServerTester;
        //    54: areturn        
        //    55: aconst_null    
        //    56: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public boolean func_152854_G() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_acmpne       6
        //     4: iconst_0       
        //     5: ireturn        
        //     6: aload_0        
        //     7: getstatic       net/minecraft/client/stream/BroadcastController$BroadcastState.Broadcasting:Lnet/minecraft/client/stream/BroadcastController$BroadcastState;
        //    10: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152827_a:(Lnet/minecraft/client/stream/BroadcastController$BroadcastState;)V
        //    13: iconst_1       
        //    14: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void setRecordingDeviceVolume(final float n) {
        this.field_152873_i.setVolume(AudioDeviceType.TTV_RECORDER_DEVICE, n);
    }
    
    public boolean func_177947_a(final String s, final long n, final long n2, final String s2, final String s3) {
        if (n2 == -1L) {
            this.func_152820_d(String.format("Invalid sequence id: %d\n", n2));
            return false;
        }
        final ErrorCode sendEndSpanMetaData = this.field_152873_i.sendEndSpanMetaData(this.authenticationToken, s, n, n2, s2, s3);
        if (ErrorCode.failed(sendEndSpanMetaData)) {
            this.func_152820_d(String.format("Error in SendStopSpanMetaData: %s\n", ErrorCode.getString(sendEndSpanMetaData)));
            return false;
        }
        return true;
    }
    
    public long func_152844_x() {
        return this.field_152873_i.getStreamTime();
    }
    
    public boolean func_152836_a(final VideoParams p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnull          181
        //     4: aload_0        
        //     5: if_acmpne       181
        //     8: aload_0        
        //     9: aload_1        
        //    10: invokevirtual   tv/twitch/broadcast/VideoParams.clone:()Ltv/twitch/broadcast/VideoParams;
        //    13: putfield        net/minecraft/client/stream/BroadcastController.videoParamaters:Ltv/twitch/broadcast/VideoParams;
        //    16: aload_0        
        //    17: new             Ltv/twitch/broadcast/AudioParams;
        //    20: dup            
        //    21: invokespecial   tv/twitch/broadcast/AudioParams.<init>:()V
        //    24: putfield        net/minecraft/client/stream/BroadcastController.audioParamaters:Ltv/twitch/broadcast/AudioParams;
        //    27: aload_0        
        //    28: getfield        net/minecraft/client/stream/BroadcastController.audioParamaters:Ltv/twitch/broadcast/AudioParams;
        //    31: aload_0        
        //    32: getfield        net/minecraft/client/stream/BroadcastController.field_152871_g:Z
        //    35: ifeq            49
        //    38: aload_0        
        //    39: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152848_y:()Z
        //    42: ifeq            49
        //    45: iconst_1       
        //    46: goto            50
        //    49: iconst_0       
        //    50: putfield        tv/twitch/broadcast/AudioParams.audioEnabled:Z
        //    53: aload_0        
        //    54: getfield        net/minecraft/client/stream/BroadcastController.audioParamaters:Ltv/twitch/broadcast/AudioParams;
        //    57: aload_0        
        //    58: getfield        net/minecraft/client/stream/BroadcastController.audioParamaters:Ltv/twitch/broadcast/AudioParams;
        //    61: getfield        tv/twitch/broadcast/AudioParams.audioEnabled:Z
        //    64: putfield        tv/twitch/broadcast/AudioParams.enableMicCapture:Z
        //    67: aload_0        
        //    68: getfield        net/minecraft/client/stream/BroadcastController.audioParamaters:Ltv/twitch/broadcast/AudioParams;
        //    71: aload_0        
        //    72: getfield        net/minecraft/client/stream/BroadcastController.audioParamaters:Ltv/twitch/broadcast/AudioParams;
        //    75: getfield        tv/twitch/broadcast/AudioParams.audioEnabled:Z
        //    78: putfield        tv/twitch/broadcast/AudioParams.enablePlaybackCapture:Z
        //    81: aload_0        
        //    82: getfield        net/minecraft/client/stream/BroadcastController.audioParamaters:Ltv/twitch/broadcast/AudioParams;
        //    85: iconst_0       
        //    86: putfield        tv/twitch/broadcast/AudioParams.enablePassthroughAudio:Z
        //    89: aload_0        
        //    90: if_icmpge       105
        //    93: aload_0        
        //    94: aconst_null    
        //    95: putfield        net/minecraft/client/stream/BroadcastController.videoParamaters:Ltv/twitch/broadcast/VideoParams;
        //    98: aload_0        
        //    99: aconst_null    
        //   100: putfield        net/minecraft/client/stream/BroadcastController.audioParamaters:Ltv/twitch/broadcast/AudioParams;
        //   103: iconst_0       
        //   104: ireturn        
        //   105: aload_0        
        //   106: getfield        net/minecraft/client/stream/BroadcastController.field_152873_i:Ltv/twitch/broadcast/Stream;
        //   109: aload_1        
        //   110: aload_0        
        //   111: getfield        net/minecraft/client/stream/BroadcastController.audioParamaters:Ltv/twitch/broadcast/AudioParams;
        //   114: aload_0        
        //   115: getfield        net/minecraft/client/stream/BroadcastController.field_152884_t:Ltv/twitch/broadcast/IngestServer;
        //   118: getstatic       tv/twitch/broadcast/StartFlags.None:Ltv/twitch/broadcast/StartFlags;
        //   121: iconst_1       
        //   122: invokevirtual   tv/twitch/broadcast/Stream.start:(Ltv/twitch/broadcast/VideoParams;Ltv/twitch/broadcast/AudioParams;Ltv/twitch/broadcast/IngestServer;Ltv/twitch/broadcast/StartFlags;Z)Ltv/twitch/ErrorCode;
        //   125: astore_2       
        //   126: aload_2        
        //   127: invokestatic    tv/twitch/ErrorCode.failed:(Ltv/twitch/ErrorCode;)Z
        //   130: ifeq            172
        //   133: aload_0        
        //   134: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152831_M:()V
        //   137: aload_2        
        //   138: invokestatic    tv/twitch/ErrorCode.getString:(Ltv/twitch/ErrorCode;)Ljava/lang/String;
        //   141: astore_3       
        //   142: aload_0        
        //   143: ldc_w           "Error while starting to broadcast: %s"
        //   146: iconst_1       
        //   147: anewarray       Ljava/lang/Object;
        //   150: dup            
        //   151: iconst_0       
        //   152: aload_3        
        //   153: aastore        
        //   154: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   157: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152820_d:(Ljava/lang/String;)V
        //   160: aload_0        
        //   161: aconst_null    
        //   162: putfield        net/minecraft/client/stream/BroadcastController.videoParamaters:Ltv/twitch/broadcast/VideoParams;
        //   165: aload_0        
        //   166: aconst_null    
        //   167: putfield        net/minecraft/client/stream/BroadcastController.audioParamaters:Ltv/twitch/broadcast/AudioParams;
        //   170: iconst_0       
        //   171: ireturn        
        //   172: aload_0        
        //   173: getstatic       net/minecraft/client/stream/BroadcastController$BroadcastState.Starting:Lnet/minecraft/client/stream/BroadcastController$BroadcastState;
        //   176: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152827_a:(Lnet/minecraft/client/stream/BroadcastController$BroadcastState;)V
        //   179: iconst_1       
        //   180: ireturn        
        //   181: iconst_0       
        //   182: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void func_152841_a(final BroadcastListener broadcastListener) {
        this.broadcastListener = broadcastListener;
    }
    
    public FrameBuffer func_152822_N() {
        if (this.field_152875_k.size() == 0) {
            this.func_152820_d(String.format("Out of free buffers, this should never happen", new Object[0]));
            return null;
        }
        final FrameBuffer frameBuffer = this.field_152875_k.get(this.field_152875_k.size() - 1);
        this.field_152875_k.remove(this.field_152875_k.size() - 1);
        return frameBuffer;
    }
    
    public boolean func_152817_A() {
        if (this.field_152876_l) {
            return false;
        }
        this.field_152873_i.setStreamCallbacks(this.field_177948_B);
        final ErrorCode initialize = this.field_152872_h.initialize(this.field_152868_d, "\u3932\u3939\u392e\u3939\u3976\u3934\u3931\u393a\u392a\u3939\u392a\u3921\u3976\u3928\u3939\u392c\u3930");
        if (initialize != 0) {
            this.field_152873_i.setStreamCallbacks((IStreamCallbacks)null);
            this.errorCode = initialize;
            return false;
        }
        final ErrorCode setTraceLevel = this.field_152872_h.setTraceLevel(MessageLevel.TTV_ML_ERROR);
        if (setTraceLevel != 0) {
            this.field_152873_i.setStreamCallbacks((IStreamCallbacks)null);
            this.field_152872_h.shutdown();
            this.errorCode = setTraceLevel;
            return false;
        }
        if (ErrorCode.succeeded(setTraceLevel)) {
            this.field_152876_l = true;
            this.func_152827_a(BroadcastState.Initialized);
            return true;
        }
        this.errorCode = setTraceLevel;
        this.field_152872_h.shutdown();
        return false;
    }
    
    public void captureFramebuffer(final FrameBuffer frameBuffer) {
        this.field_152873_i.captureFrameBuffer_ReadPixels(frameBuffer);
    }
    
    public void setPlaybackDeviceVolume(final float n) {
        this.field_152873_i.setVolume(AudioDeviceType.TTV_PLAYBACK_DEVICE, n);
    }
    
    public BroadcastController() {
        this.field_152863_D = null;
        this.broadcastListener = null;
        this.field_152868_d = "";
        this.field_152869_e = "";
        this.field_152870_f = "";
        this.field_152871_g = true;
        this.field_152872_h = null;
        this.field_152873_i = null;
        this.field_152874_j = Lists.newArrayList();
        this.field_152875_k = Lists.newArrayList();
        this.field_152876_l = false;
        this.field_152877_m = false;
        this.field_152878_n = false;
        this.broadcastState = BroadcastState.Uninitialized;
        this.field_152880_p = null;
        this.videoParamaters = null;
        this.audioParamaters = null;
        this.ingestList = new IngestList(new IngestServer[0]);
        this.field_152884_t = null;
        this.authenticationToken = new AuthToken();
        this.channelInfo = new ChannelInfo();
        this.userInfo = new UserInfo();
        this.streamInfo = new StreamInfo();
        this.field_152889_y = new ArchivingState();
        this.field_152890_z = 0L;
        this.field_152860_A = null;
        this.field_177948_B = (IStreamCallbacks)new IStreamCallbacks() {
            final BroadcastController this$0;
            
            public void getStreamInfoCallback(final ErrorCode errorCode, final StreamInfo streamInfo) {
                if (ErrorCode.succeeded(errorCode)) {
                    this.this$0.streamInfo = streamInfo;
                    if (this.this$0.broadcastListener != null) {
                        this.this$0.broadcastListener.func_152894_a(streamInfo);
                    }
                }
                else {
                    this.this$0.func_152832_e(String.format("StreamInfoDoneCallback got failure: %s", ErrorCode.getString(errorCode)));
                }
            }
            
            public void sendEndSpanMetaDataCallback(final ErrorCode errorCode) {
                if (ErrorCode.failed(errorCode)) {
                    this.this$0.func_152820_d(String.format("sendEndSpanMetaDataCallback got failure: %s", ErrorCode.getString(errorCode)));
                }
            }
            
            public void bufferUnlockCallback(final long n) {
                this.this$0.field_152875_k.add(FrameBuffer.lookupBuffer(n));
            }
            
            public void sendActionMetaDataCallback(final ErrorCode errorCode) {
                if (ErrorCode.failed(errorCode)) {
                    this.this$0.func_152820_d(String.format("sendActionMetaDataCallback got failure: %s", ErrorCode.getString(errorCode)));
                }
            }
            
            public void getGameNameListCallback(final ErrorCode errorCode, final GameInfoList list) {
                if (ErrorCode.failed(errorCode)) {
                    this.this$0.func_152820_d(String.format("GameNameListCallback got failure: %s", ErrorCode.getString(errorCode)));
                }
                if (this.this$0.broadcastListener != null) {
                    this.this$0.broadcastListener.func_152898_a(errorCode, (list == null) ? new GameInfo[0] : list.list);
                }
            }
            
            public void getUserInfoCallback(final ErrorCode errorCode, final UserInfo userInfo) {
                this.this$0.userInfo = userInfo;
                if (ErrorCode.failed(errorCode)) {
                    this.this$0.func_152820_d(String.format("UserInfoDoneCallback got failure: %s", ErrorCode.getString(errorCode)));
                }
            }
            
            public void startCallback(final ErrorCode errorCode) {
                if (ErrorCode.succeeded(errorCode)) {
                    if (this.this$0.broadcastListener != null) {
                        this.this$0.broadcastListener.func_152899_b();
                    }
                    this.this$0.func_152827_a(BroadcastState.Broadcasting);
                }
                else {
                    this.this$0.videoParamaters = null;
                    this.this$0.audioParamaters = null;
                    this.this$0.func_152827_a(BroadcastState.ReadyToBroadcast);
                    if (this.this$0.broadcastListener != null) {
                        this.this$0.broadcastListener.func_152892_c(errorCode);
                    }
                    this.this$0.func_152820_d(String.format("startCallback got failure: %s", ErrorCode.getString(errorCode)));
                }
            }
            
            public void requestAuthTokenCallback(final ErrorCode errorCode, final AuthToken authenticationToken) {
                if (ErrorCode.succeeded(errorCode)) {
                    this.this$0.authenticationToken = authenticationToken;
                    this.this$0.func_152827_a(BroadcastState.Authenticated);
                }
                else {
                    this.this$0.authenticationToken.data = "";
                    this.this$0.func_152827_a(BroadcastState.Initialized);
                    this.this$0.func_152820_d(String.format("RequestAuthTokenDoneCallback got failure: %s", ErrorCode.getString(errorCode)));
                }
                if (this.this$0.broadcastListener != null) {
                    this.this$0.broadcastListener.func_152900_a(errorCode, authenticationToken);
                }
            }
            
            public void getArchivingStateCallback(final ErrorCode errorCode, final ArchivingState field_152889_y) {
                this.this$0.field_152889_y = field_152889_y;
                ErrorCode.failed(errorCode);
            }
            
            public void loginCallback(final ErrorCode errorCode, final ChannelInfo channelInfo) {
                if (ErrorCode.succeeded(errorCode)) {
                    this.this$0.channelInfo = channelInfo;
                    this.this$0.func_152827_a(BroadcastState.LoggedIn);
                    this.this$0.field_152877_m = true;
                }
                else {
                    this.this$0.func_152827_a(BroadcastState.Initialized);
                    this.this$0.field_152877_m = false;
                    this.this$0.func_152820_d(String.format("LoginCallback got failure: %s", ErrorCode.getString(errorCode)));
                }
                if (this.this$0.broadcastListener != null) {
                    this.this$0.broadcastListener.func_152897_a(errorCode);
                }
            }
            
            public void setStreamInfoCallback(final ErrorCode errorCode) {
                if (ErrorCode.failed(errorCode)) {
                    this.this$0.func_152832_e(String.format("SetStreamInfoCallback got failure: %s", ErrorCode.getString(errorCode)));
                }
            }
            
            public void stopCallback(final ErrorCode errorCode) {
                if (ErrorCode.succeeded(errorCode)) {
                    this.this$0.videoParamaters = null;
                    this.this$0.audioParamaters = null;
                    this.this$0.func_152831_M();
                    if (this.this$0.broadcastListener != null) {
                        this.this$0.broadcastListener.func_152901_c();
                    }
                    if (this.this$0.field_152877_m) {
                        this.this$0.func_152827_a(BroadcastState.ReadyToBroadcast);
                    }
                    else {
                        this.this$0.func_152827_a(BroadcastState.Initialized);
                    }
                }
                else {
                    this.this$0.func_152827_a(BroadcastState.ReadyToBroadcast);
                    this.this$0.func_152820_d(String.format("stopCallback got failure: %s", ErrorCode.getString(errorCode)));
                }
            }
            
            public void sendStartSpanMetaDataCallback(final ErrorCode errorCode) {
                if (ErrorCode.failed(errorCode)) {
                    this.this$0.func_152820_d(String.format("sendStartSpanMetaDataCallback got failure: %s", ErrorCode.getString(errorCode)));
                }
            }
            
            public void getIngestServersCallback(final ErrorCode errorCode, final IngestList ingestList) {
                if (ErrorCode.succeeded(errorCode)) {
                    this.this$0.ingestList = ingestList;
                    this.this$0.field_152884_t = this.this$0.ingestList.getDefaultServer();
                    this.this$0.func_152827_a(BroadcastState.ReceivedIngestServers);
                    if (this.this$0.broadcastListener != null) {
                        this.this$0.broadcastListener.func_152896_a(ingestList);
                    }
                }
                else {
                    this.this$0.func_152820_d(String.format("IngestListCallback got failure: %s", ErrorCode.getString(errorCode)));
                    this.this$0.func_152827_a(BroadcastState.LoggingIn);
                }
            }
            
            public void runCommercialCallback(final ErrorCode errorCode) {
                if (ErrorCode.failed(errorCode)) {
                    this.this$0.func_152832_e(String.format("RunCommercialCallback got failure: %s", ErrorCode.getString(errorCode)));
                }
            }
        };
        this.field_177949_C = (IStatCallbacks)new IStatCallbacks() {
            final BroadcastController this$0;
            
            public void statCallback(final StatType statType, final long n) {
            }
        };
        this.field_152872_h = Core.getInstance();
        if (Core.getInstance() == null) {
            this.field_152872_h = new Core((CoreAPI)new StandardCoreAPI());
        }
        this.field_152873_i = new Stream((StreamAPI)new DesktopStreamAPI());
    }
    
    public long func_177946_b(final String s, final long n, final String s2, final String s3) {
        final long sendStartSpanMetaData = this.field_152873_i.sendStartSpanMetaData(this.authenticationToken, s, n, s2, s3);
        if (sendStartSpanMetaData == -1L) {
            this.func_152820_d(String.format("Error in SendStartSpanMetaData\n", new Object[0]));
        }
        return sendStartSpanMetaData;
    }
    
    public boolean func_152858_b() {
        return this.field_152876_l;
    }
    
    public void func_152842_a(final String field_152868_d) {
        this.field_152868_d = field_152868_d;
    }
    
    public boolean requestCommercial() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_acmpeq       6
        //     4: iconst_0       
        //     5: ireturn        
        //     6: aload_0        
        //     7: getfield        net/minecraft/client/stream/BroadcastController.field_152873_i:Ltv/twitch/broadcast/Stream;
        //    10: aload_0        
        //    11: getfield        net/minecraft/client/stream/BroadcastController.authenticationToken:Ltv/twitch/AuthToken;
        //    14: invokevirtual   tv/twitch/broadcast/Stream.runCommercial:(Ltv/twitch/AuthToken;)Ltv/twitch/ErrorCode;
        //    17: astore_1       
        //    18: aload_0        
        //    19: aload_1        
        //    20: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152853_a:(Ltv/twitch/ErrorCode;)Z
        //    23: pop            
        //    24: aload_1        
        //    25: invokestatic    tv/twitch/ErrorCode.succeeded:(Ltv/twitch/ErrorCode;)Z
        //    28: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public IngestServerTester isReady() {
        return this.field_152860_A;
    }
    
    public boolean func_152840_a(final String s, final long n, final String s2, final String s3) {
        final ErrorCode sendActionMetaData = this.field_152873_i.sendActionMetaData(this.authenticationToken, s, n, s2, s3);
        if (ErrorCode.failed(sendActionMetaData)) {
            this.func_152820_d(String.format("Error while sending meta data: %s\n", ErrorCode.getString(sendActionMetaData)));
            return false;
        }
        return true;
    }
    
    protected void func_152827_a(final BroadcastState broadcastState) {
        if (broadcastState != this.broadcastState) {
            this.broadcastState = broadcastState;
            if (this.broadcastListener != null) {
                this.broadcastListener.func_152891_a(broadcastState);
            }
        }
    }
    
    public boolean func_152828_a(String field_152880_p, String gameName, String streamTitle) {
        if (!this.field_152877_m) {
            return false;
        }
        if (field_152880_p == null || field_152880_p.equals("")) {
            field_152880_p = this.field_152880_p;
        }
        if (gameName == null) {
            gameName = "";
        }
        if (streamTitle == null) {
            streamTitle = "";
        }
        final StreamInfoForSetting streamInfoForSetting = new StreamInfoForSetting();
        streamInfoForSetting.streamTitle = streamTitle;
        streamInfoForSetting.gameName = gameName;
        final ErrorCode setStreamInfo = this.field_152873_i.setStreamInfo(this.authenticationToken, field_152880_p, streamInfoForSetting);
        this.func_152853_a(setStreamInfo);
        return ErrorCode.succeeded(setStreamInfo);
    }
    
    protected void func_152820_d(final String field_152863_D) {
        this.field_152863_D = field_152863_D;
        BroadcastController.field_152862_C.func_152757_a("<Error> " + field_152863_D);
        BroadcastController.logger.error(TwitchStream.STREAM_MARKER, "[Broadcast controller] {}", new Object[] { field_152863_D });
    }
    
    public ChannelInfo getChannelInfo() {
        return this.channelInfo;
    }
    
    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
    
    public boolean func_152847_F() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_acmpeq       6
        //     4: iconst_0       
        //     5: ireturn        
        //     6: aload_0        
        //     7: getfield        net/minecraft/client/stream/BroadcastController.field_152873_i:Ltv/twitch/broadcast/Stream;
        //    10: invokevirtual   tv/twitch/broadcast/Stream.pauseVideo:()Ltv/twitch/ErrorCode;
        //    13: astore_1       
        //    14: aload_1        
        //    15: invokestatic    tv/twitch/ErrorCode.failed:(Ltv/twitch/ErrorCode;)Z
        //    18: ifeq            52
        //    21: aload_0        
        //    22: invokevirtual   net/minecraft/client/stream/BroadcastController.stopBroadcasting:()Z
        //    25: pop            
        //    26: aload_1        
        //    27: invokestatic    tv/twitch/ErrorCode.getString:(Ltv/twitch/ErrorCode;)Ljava/lang/String;
        //    30: astore_2       
        //    31: aload_0        
        //    32: ldc_w           "Error pausing stream: %s\n"
        //    35: iconst_1       
        //    36: anewarray       Ljava/lang/Object;
        //    39: dup            
        //    40: iconst_0       
        //    41: aload_2        
        //    42: aastore        
        //    43: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //    46: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152820_d:(Ljava/lang/String;)V
        //    49: goto            59
        //    52: aload_0        
        //    53: getstatic       net/minecraft/client/stream/BroadcastController$BroadcastState.Paused:Lnet/minecraft/client/stream/BroadcastController$BroadcastState;
        //    56: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152827_a:(Lnet/minecraft/client/stream/BroadcastController$BroadcastState;)V
        //    59: aload_1        
        //    60: invokestatic    tv/twitch/ErrorCode.succeeded:(Ltv/twitch/ErrorCode;)Z
        //    63: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void func_152821_H() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/client/stream/BroadcastController.field_152873_i:Ltv/twitch/broadcast/Stream;
        //     4: ifnull          349
        //     7: aload_0        
        //     8: getfield        net/minecraft/client/stream/BroadcastController.field_152876_l:Z
        //    11: ifeq            349
        //    14: aload_0        
        //    15: getfield        net/minecraft/client/stream/BroadcastController.field_152873_i:Ltv/twitch/broadcast/Stream;
        //    18: invokevirtual   tv/twitch/broadcast/Stream.pollTasks:()Ltv/twitch/ErrorCode;
        //    21: astore_1       
        //    22: aload_0        
        //    23: aload_1        
        //    24: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152853_a:(Ltv/twitch/ErrorCode;)Z
        //    27: pop            
        //    28: aload_0        
        //    29: if_acmpne       61
        //    32: aload_0        
        //    33: getfield        net/minecraft/client/stream/BroadcastController.field_152860_A:Lnet/minecraft/client/stream/IngestServerTester;
        //    36: invokevirtual   net/minecraft/client/stream/IngestServerTester.func_153041_j:()V
        //    39: aload_0        
        //    40: getfield        net/minecraft/client/stream/BroadcastController.field_152860_A:Lnet/minecraft/client/stream/IngestServerTester;
        //    43: invokevirtual   net/minecraft/client/stream/IngestServerTester.func_153032_e:()Z
        //    46: ifeq            61
        //    49: aload_0        
        //    50: aconst_null    
        //    51: putfield        net/minecraft/client/stream/BroadcastController.field_152860_A:Lnet/minecraft/client/stream/IngestServerTester;
        //    54: aload_0        
        //    55: getstatic       net/minecraft/client/stream/BroadcastController$BroadcastState.ReadyToBroadcast:Lnet/minecraft/client/stream/BroadcastController$BroadcastState;
        //    58: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152827_a:(Lnet/minecraft/client/stream/BroadcastController$BroadcastState;)V
        //    61: getstatic       net/minecraft/client/stream/BroadcastController$3.$SwitchMap$net$minecraft$client$stream$BroadcastController$BroadcastState:[I
        //    64: aload_0        
        //    65: getfield        net/minecraft/client/stream/BroadcastController.broadcastState:Lnet/minecraft/client/stream/BroadcastController$BroadcastState;
        //    68: invokevirtual   net/minecraft/client/stream/BroadcastController$BroadcastState.ordinal:()I
        //    71: iaload         
        //    72: tableswitch {
        //                2: 136
        //                3: 188
        //                4: 247
        //                5: 342
        //                6: 342
        //                7: 342
        //                8: 342
        //                9: 342
        //               10: 342
        //               11: 342
        //               12: 345
        //               13: 345
        //          default: 342
        //        }
        //   136: aload_0        
        //   137: getstatic       net/minecraft/client/stream/BroadcastController$BroadcastState.LoggingIn:Lnet/minecraft/client/stream/BroadcastController$BroadcastState;
        //   140: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152827_a:(Lnet/minecraft/client/stream/BroadcastController$BroadcastState;)V
        //   143: aload_0        
        //   144: getfield        net/minecraft/client/stream/BroadcastController.field_152873_i:Ltv/twitch/broadcast/Stream;
        //   147: aload_0        
        //   148: getfield        net/minecraft/client/stream/BroadcastController.authenticationToken:Ltv/twitch/AuthToken;
        //   151: invokevirtual   tv/twitch/broadcast/Stream.login:(Ltv/twitch/AuthToken;)Ltv/twitch/ErrorCode;
        //   154: astore_1       
        //   155: aload_1        
        //   156: invokestatic    tv/twitch/ErrorCode.failed:(Ltv/twitch/ErrorCode;)Z
        //   159: ifeq            349
        //   162: aload_1        
        //   163: invokestatic    tv/twitch/ErrorCode.getString:(Ltv/twitch/ErrorCode;)Ljava/lang/String;
        //   166: astore_2       
        //   167: aload_0        
        //   168: ldc_w           "Error in TTV_Login: %s\n"
        //   171: iconst_1       
        //   172: anewarray       Ljava/lang/Object;
        //   175: dup            
        //   176: iconst_0       
        //   177: aload_2        
        //   178: aastore        
        //   179: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   182: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152820_d:(Ljava/lang/String;)V
        //   185: goto            349
        //   188: aload_0        
        //   189: getstatic       net/minecraft/client/stream/BroadcastController$BroadcastState.FindingIngestServer:Lnet/minecraft/client/stream/BroadcastController$BroadcastState;
        //   192: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152827_a:(Lnet/minecraft/client/stream/BroadcastController$BroadcastState;)V
        //   195: aload_0        
        //   196: getfield        net/minecraft/client/stream/BroadcastController.field_152873_i:Ltv/twitch/broadcast/Stream;
        //   199: aload_0        
        //   200: getfield        net/minecraft/client/stream/BroadcastController.authenticationToken:Ltv/twitch/AuthToken;
        //   203: invokevirtual   tv/twitch/broadcast/Stream.getIngestServers:(Ltv/twitch/AuthToken;)Ltv/twitch/ErrorCode;
        //   206: astore_1       
        //   207: aload_1        
        //   208: invokestatic    tv/twitch/ErrorCode.failed:(Ltv/twitch/ErrorCode;)Z
        //   211: ifeq            349
        //   214: aload_0        
        //   215: getstatic       net/minecraft/client/stream/BroadcastController$BroadcastState.LoggedIn:Lnet/minecraft/client/stream/BroadcastController$BroadcastState;
        //   218: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152827_a:(Lnet/minecraft/client/stream/BroadcastController$BroadcastState;)V
        //   221: aload_1        
        //   222: invokestatic    tv/twitch/ErrorCode.getString:(Ltv/twitch/ErrorCode;)Ljava/lang/String;
        //   225: astore_2       
        //   226: aload_0        
        //   227: ldc_w           "Error in TTV_GetIngestServers: %s\n"
        //   230: iconst_1       
        //   231: anewarray       Ljava/lang/Object;
        //   234: dup            
        //   235: iconst_0       
        //   236: aload_2        
        //   237: aastore        
        //   238: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   241: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152820_d:(Ljava/lang/String;)V
        //   244: goto            349
        //   247: aload_0        
        //   248: getstatic       net/minecraft/client/stream/BroadcastController$BroadcastState.ReadyToBroadcast:Lnet/minecraft/client/stream/BroadcastController$BroadcastState;
        //   251: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152827_a:(Lnet/minecraft/client/stream/BroadcastController$BroadcastState;)V
        //   254: aload_0        
        //   255: getfield        net/minecraft/client/stream/BroadcastController.field_152873_i:Ltv/twitch/broadcast/Stream;
        //   258: aload_0        
        //   259: getfield        net/minecraft/client/stream/BroadcastController.authenticationToken:Ltv/twitch/AuthToken;
        //   262: invokevirtual   tv/twitch/broadcast/Stream.getUserInfo:(Ltv/twitch/AuthToken;)Ltv/twitch/ErrorCode;
        //   265: astore_1       
        //   266: aload_1        
        //   267: invokestatic    tv/twitch/ErrorCode.failed:(Ltv/twitch/ErrorCode;)Z
        //   270: ifeq            296
        //   273: aload_1        
        //   274: invokestatic    tv/twitch/ErrorCode.getString:(Ltv/twitch/ErrorCode;)Ljava/lang/String;
        //   277: astore_2       
        //   278: aload_0        
        //   279: ldc_w           "Error in TTV_GetUserInfo: %s\n"
        //   282: iconst_1       
        //   283: anewarray       Ljava/lang/Object;
        //   286: dup            
        //   287: iconst_0       
        //   288: aload_2        
        //   289: aastore        
        //   290: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   293: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152820_d:(Ljava/lang/String;)V
        //   296: aload_0        
        //   297: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152835_I:()V
        //   300: aload_0        
        //   301: getfield        net/minecraft/client/stream/BroadcastController.field_152873_i:Ltv/twitch/broadcast/Stream;
        //   304: aload_0        
        //   305: getfield        net/minecraft/client/stream/BroadcastController.authenticationToken:Ltv/twitch/AuthToken;
        //   308: invokevirtual   tv/twitch/broadcast/Stream.getArchivingState:(Ltv/twitch/AuthToken;)Ltv/twitch/ErrorCode;
        //   311: astore_1       
        //   312: aload_1        
        //   313: invokestatic    tv/twitch/ErrorCode.failed:(Ltv/twitch/ErrorCode;)Z
        //   316: ifeq            342
        //   319: aload_1        
        //   320: invokestatic    tv/twitch/ErrorCode.getString:(Ltv/twitch/ErrorCode;)Ljava/lang/String;
        //   323: astore_2       
        //   324: aload_0        
        //   325: ldc_w           "Error in TTV_GetArchivingState: %s\n"
        //   328: iconst_1       
        //   329: anewarray       Ljava/lang/Object;
        //   332: dup            
        //   333: iconst_0       
        //   334: aload_2        
        //   335: aastore        
        //   336: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   339: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152820_d:(Ljava/lang/String;)V
        //   342: goto            349
        //   345: aload_0        
        //   346: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152835_I:()V
        //   349: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    protected boolean func_152848_y() {
        return true;
    }
    
    public boolean func_152851_B() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/client/stream/BroadcastController.field_152876_l:Z
        //     4: ifne            9
        //     7: iconst_1       
        //     8: ireturn        
        //     9: aload_0        
        //    10: if_acmpne       15
        //    13: iconst_0       
        //    14: ireturn        
        //    15: aload_0        
        //    16: iconst_1       
        //    17: putfield        net/minecraft/client/stream/BroadcastController.field_152878_n:Z
        //    20: aload_0        
        //    21: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152845_C:()Z
        //    24: pop            
        //    25: aload_0        
        //    26: getfield        net/minecraft/client/stream/BroadcastController.field_152873_i:Ltv/twitch/broadcast/Stream;
        //    29: aconst_null    
        //    30: checkcast       Ltv/twitch/broadcast/IStreamCallbacks;
        //    33: invokevirtual   tv/twitch/broadcast/Stream.setStreamCallbacks:(Ltv/twitch/broadcast/IStreamCallbacks;)V
        //    36: aload_0        
        //    37: getfield        net/minecraft/client/stream/BroadcastController.field_152873_i:Ltv/twitch/broadcast/Stream;
        //    40: aconst_null    
        //    41: checkcast       Ltv/twitch/broadcast/IStatCallbacks;
        //    44: invokevirtual   tv/twitch/broadcast/Stream.setStatCallbacks:(Ltv/twitch/broadcast/IStatCallbacks;)V
        //    47: aload_0        
        //    48: getfield        net/minecraft/client/stream/BroadcastController.field_152872_h:Ltv/twitch/Core;
        //    51: invokevirtual   tv/twitch/Core.shutdown:()Ltv/twitch/ErrorCode;
        //    54: astore_1       
        //    55: aload_0        
        //    56: aload_1        
        //    57: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152853_a:(Ltv/twitch/ErrorCode;)Z
        //    60: pop            
        //    61: aload_0        
        //    62: iconst_0       
        //    63: putfield        net/minecraft/client/stream/BroadcastController.field_152876_l:Z
        //    66: aload_0        
        //    67: iconst_0       
        //    68: putfield        net/minecraft/client/stream/BroadcastController.field_152878_n:Z
        //    71: aload_0        
        //    72: getstatic       net/minecraft/client/stream/BroadcastController$BroadcastState.Uninitialized:Lnet/minecraft/client/stream/BroadcastController$BroadcastState;
        //    75: invokevirtual   net/minecraft/client/stream/BroadcastController.func_152827_a:(Lnet/minecraft/client/stream/BroadcastController$BroadcastState;)V
        //    78: iconst_1       
        //    79: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public StreamInfo getStreamInfo() {
        return this.streamInfo;
    }
    
    public enum BroadcastState
    {
        ReadyToBroadcast("ReadyToBroadcast", 8), 
        Starting("Starting", 9), 
        LoggingIn("LoggingIn", 4), 
        Broadcasting("Broadcasting", 10), 
        Authenticating("Authenticating", 2), 
        Stopping("Stopping", 11), 
        Uninitialized("Uninitialized", 0);
        
        private static final BroadcastState[] $VALUES;
        
        Initialized("Initialized", 1), 
        IngestTesting("IngestTesting", 13), 
        Paused("Paused", 12), 
        LoggedIn("LoggedIn", 5), 
        Authenticated("Authenticated", 3), 
        FindingIngestServer("FindingIngestServer", 6), 
        ReceivedIngestServers("ReceivedIngestServers", 7);
        
        static {
            $VALUES = new BroadcastState[] { BroadcastState.Uninitialized, BroadcastState.Initialized, BroadcastState.Authenticating, BroadcastState.Authenticated, BroadcastState.LoggingIn, BroadcastState.LoggedIn, BroadcastState.FindingIngestServer, BroadcastState.ReceivedIngestServers, BroadcastState.ReadyToBroadcast, BroadcastState.Starting, BroadcastState.Broadcasting, BroadcastState.Stopping, BroadcastState.Paused, BroadcastState.IngestTesting };
        }
        
        private BroadcastState(final String s, final int n) {
        }
    }
    
    public interface BroadcastListener
    {
        void func_152894_a(final StreamInfo p0);
        
        void func_152901_c();
        
        void func_152893_b(final ErrorCode p0);
        
        void func_152891_a(final BroadcastState p0);
        
        void func_152898_a(final ErrorCode p0, final GameInfo[] p1);
        
        void func_152897_a(final ErrorCode p0);
        
        void func_152896_a(final IngestList p0);
        
        void func_152899_b();
        
        void func_152900_a(final ErrorCode p0, final AuthToken p1);
        
        void func_152895_a();
        
        void func_152892_c(final ErrorCode p0);
    }
}
