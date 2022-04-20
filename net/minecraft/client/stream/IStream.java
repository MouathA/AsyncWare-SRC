package net.minecraft.client.stream;

import tv.twitch.broadcast.*;
import tv.twitch.*;
import tv.twitch.chat.*;

public interface IStream
{
    int func_152920_A();
    
    void requestCommercial();
    
    void updateStreamVolume();
    
    void pause();
    
    boolean func_152927_B();
    
    boolean isReadyToBroadcast();
    
    IngestServer[] func_152925_v();
    
    boolean isPaused();
    
    boolean func_152928_D();
    
    ErrorCode func_152912_E();
    
    void unpause();
    
    ChatUserInfo func_152926_a(final String p0);
    
    boolean func_152929_G();
    
    boolean func_152936_l();
    
    boolean isBroadcasting();
    
    void func_176026_a(final Metadata p0, final long p1, final long p2);
    
    void func_152911_a(final Metadata p0, final long p1);
    
    void func_152917_b(final String p0);
    
    void shutdownStream();
    
    void func_152935_j();
    
    AuthFailureReason func_152918_H();
    
    void func_152909_x();
    
    IngestServerTester func_152932_y();
    
    void muteMicrophone(final boolean p0);
    
    String func_152921_C();
    
    boolean func_152908_z();
    
    void func_152922_k();
    
    void func_152930_t();
    
    void stopBroadcasting();
    
    boolean func_152913_F();
    
    public enum AuthFailureReason
    {
        INVALID_TOKEN("INVALID_TOKEN", 1);
        
        private static final AuthFailureReason[] $VALUES;
        
        ERROR("ERROR", 0);
        
        static {
            $VALUES = new AuthFailureReason[] { AuthFailureReason.ERROR, AuthFailureReason.INVALID_TOKEN };
        }
        
        private AuthFailureReason(final String s, final int n) {
        }
    }
}
