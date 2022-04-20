package net.minecraft.client.stream;

import tv.twitch.broadcast.*;
import tv.twitch.*;
import tv.twitch.chat.*;

public class NullStream implements IStream
{
    private final Throwable field_152938_a;
    
    @Override
    public void func_152911_a(final Metadata metadata, final long n) {
    }
    
    @Override
    public IngestServer[] func_152925_v() {
        return new IngestServer[0];
    }
    
    @Override
    public void stopBroadcasting() {
    }
    
    @Override
    public AuthFailureReason func_152918_H() {
        return AuthFailureReason.ERROR;
    }
    
    @Override
    public boolean isReadyToBroadcast() {
        return false;
    }
    
    @Override
    public void func_152922_k() {
    }
    
    @Override
    public boolean func_152913_F() {
        return false;
    }
    
    @Override
    public void requestCommercial() {
    }
    
    @Override
    public ErrorCode func_152912_E() {
        return null;
    }
    
    @Override
    public void func_176026_a(final Metadata metadata, final long n, final long n2) {
    }
    
    @Override
    public boolean func_152908_z() {
        return false;
    }
    
    @Override
    public boolean func_152936_l() {
        return false;
    }
    
    @Override
    public void func_152935_j() {
    }
    
    @Override
    public boolean func_152929_G() {
        return false;
    }
    
    @Override
    public boolean func_152928_D() {
        return false;
    }
    
    @Override
    public void muteMicrophone(final boolean b) {
    }
    
    public Throwable func_152937_a() {
        return this.field_152938_a;
    }
    
    @Override
    public int func_152920_A() {
        return 0;
    }
    
    @Override
    public void updateStreamVolume() {
    }
    
    @Override
    public boolean isBroadcasting() {
        return false;
    }
    
    @Override
    public void func_152909_x() {
    }
    
    @Override
    public IngestServerTester func_152932_y() {
        return null;
    }
    
    @Override
    public boolean func_152927_B() {
        return false;
    }
    
    @Override
    public void shutdownStream() {
    }
    
    @Override
    public void unpause() {
    }
    
    @Override
    public void pause() {
    }
    
    @Override
    public String func_152921_C() {
        return null;
    }
    
    @Override
    public boolean isPaused() {
        return false;
    }
    
    @Override
    public ChatUserInfo func_152926_a(final String s) {
        return null;
    }
    
    public NullStream(final Throwable field_152938_a) {
        this.field_152938_a = field_152938_a;
    }
    
    @Override
    public void func_152930_t() {
    }
    
    @Override
    public void func_152917_b(final String s) {
    }
}
