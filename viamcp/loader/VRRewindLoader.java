package viamcp.loader;

import java.util.logging.*;
import com.viaversion.viaversion.api.*;
import java.io.*;
import de.gerrygames.viarewind.api.*;

public class VRRewindLoader implements ViaRewindPlatform
{
    public Logger getLogger() {
        return Via.getPlatform().getLogger();
    }
    
    public VRRewindLoader(final File file) {
        final ViaRewindConfigImpl viaRewindConfigImpl = new ViaRewindConfigImpl(file.toPath().resolve("ViaRewind").resolve("config.yml").toFile());
        viaRewindConfigImpl.reloadConfig();
        this.init((ViaRewindConfig)viaRewindConfigImpl);
    }
}
