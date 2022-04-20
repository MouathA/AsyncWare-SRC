package viamcp.loader;

import com.viaversion.viabackwards.api.*;
import java.io.*;
import java.util.logging.*;
import viamcp.*;

public class VRBackwardsLoader implements ViaBackwardsPlatform
{
    private final File file;
    
    public VRBackwardsLoader(final File file) {
        this.init(this.file = new File(file, "ViaBackwards"));
    }
    
    public File getDataFolder() {
        return new File(this.file, "config.yml");
    }
    
    public Logger getLogger() {
        return ViaMCP.getInstance().getjLogger();
    }
    
    public boolean isOutdated() {
        return false;
    }
    
    public void disable() {
    }
}
