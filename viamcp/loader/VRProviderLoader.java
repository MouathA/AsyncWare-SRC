package viamcp.loader;

import com.viaversion.viaversion.api.platform.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.*;
import com.viaversion.viaversion.bungee.providers.*;
import com.viaversion.viaversion.api.platform.providers.*;
import com.viaversion.viaversion.api.protocol.version.*;
import com.viaversion.viaversion.protocols.base.*;
import com.viaversion.viaversion.api.connection.*;
import viamcp.*;

public class VRProviderLoader implements ViaPlatformLoader
{
    public void load() {
        Via.getManager().getProviders().use((Class)MovementTransmitterProvider.class, (Provider)new BungeeMovementTransmitter());
        Via.getManager().getProviders().use((Class)VersionProvider.class, (Provider)new BaseVersionProvider(this) {
            final VRProviderLoader this$0;
            
            public int getClosestServerProtocol(final UserConnection userConnection) throws Exception {
                if (userConnection.isClientSide()) {
                    return ViaMCP.getInstance().getVersion();
                }
                return super.getClosestServerProtocol(userConnection);
            }
        });
    }
    
    public void unload() {
    }
}
