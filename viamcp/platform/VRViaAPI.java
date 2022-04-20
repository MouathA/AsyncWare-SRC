package viamcp.platform;

import com.viaversion.viaversion.*;
import io.netty.buffer.*;
import java.util.*;

public class VRViaAPI extends ViaAPIBase
{
    public void sendRawPacket(final Object o, final ByteBuf byteBuf) {
        super.sendRawPacket((UUID)o, byteBuf);
    }
    
    public int getPlayerVersion(final Object o) {
        return super.getPlayerVersion((UUID)o);
    }
}
