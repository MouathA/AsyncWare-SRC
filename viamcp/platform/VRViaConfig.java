package viamcp.platform;

import com.viaversion.viaversion.configuration.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class VRViaConfig extends AbstractViaConfig
{
    private static List UNSUPPORTED;
    
    static {
        VRViaConfig.UNSUPPORTED = Arrays.asList("anti-xray-patch", "bungee-ping-interval", "bungee-ping-save", "bungee-servers", "quick-move-action-fix", "nms-player-ticking", "velocity-ping-interval", "velocity-ping-save", "velocity-servers", "blockconnection-method", "change-1_9-hitbox", "change-1_14-hitbox");
    }
    
    public boolean isAntiXRay() {
        return false;
    }
    
    public VRViaConfig(final File file) {
        super(file);
        this.reloadConfig();
    }
    
    public String getBlockConnectionMethod() {
        return "packet";
    }
    
    public URL getDefaultConfigURL() {
        return this.getClass().getClassLoader().getResource("assets/viaversion/config.yml");
    }
    
    public boolean is1_9HitboxFix() {
        return false;
    }
    
    public boolean is1_12QuickMoveActionFix() {
        return false;
    }
    
    public boolean is1_14HitboxFix() {
        return false;
    }
    
    public boolean isNMSPlayerTicking() {
        return false;
    }
    
    protected void handleConfig(final Map map) {
    }
    
    public List getUnsupportedOptions() {
        return VRViaConfig.UNSUPPORTED;
    }
}
