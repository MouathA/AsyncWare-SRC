package net.minecraft.client.network;

import net.minecraft.network.login.*;
import net.minecraft.client.*;
import com.mojang.authlib.*;
import net.minecraft.client.gui.*;
import org.apache.logging.log4j.*;
import com.mojang.authlib.minecraft.*;
import net.minecraft.network.login.server.*;
import net.minecraft.util.*;
import java.math.*;
import net.minecraft.network.login.client.*;
import javax.crypto.*;
import io.netty.util.concurrent.*;
import net.minecraft.network.*;
import java.security.*;

public class NetHandlerLoginClient implements INetHandlerLoginClient
{
    private static final Logger logger;
    private final NetworkManager networkManager;
    private final Minecraft mc;
    private GameProfile gameProfile;
    private final GuiScreen previousGuiScreen;
    
    @Override
    public void onDisconnect(final IChatComponent chatComponent) {
        this.mc.displayGuiScreen(new GuiDisconnected(this.previousGuiScreen, "connect.failed", chatComponent));
    }
    
    @Override
    public void handleEnableCompression(final S03PacketEnableCompression s03PacketEnableCompression) {
        if (!this.networkManager.isLocalChannel()) {
            this.networkManager.setCompressionTreshold(s03PacketEnableCompression.getCompressionTreshold());
        }
    }
    
    @Override
    public void handleDisconnect(final S00PacketDisconnect s00PacketDisconnect) {
        this.networkManager.closeChannel(s00PacketDisconnect.func_149603_c());
    }
    
    static {
        logger = LogManager.getLogger();
    }
    
    static NetworkManager access$000(final NetHandlerLoginClient netHandlerLoginClient) {
        return netHandlerLoginClient.networkManager;
    }
    
    private MinecraftSessionService getSessionService() {
        return this.mc.getSessionService();
    }
    
    @Override
    public void handleLoginSuccess(final S02PacketLoginSuccess s02PacketLoginSuccess) {
        this.gameProfile = s02PacketLoginSuccess.getProfile();
        this.networkManager.setConnectionState(EnumConnectionState.PLAY);
        this.networkManager.setNetHandler(new NetHandlerPlayClient(this.mc, this.previousGuiScreen, this.networkManager, this.gameProfile));
    }
    
    @Override
    public void handleEncryptionRequest(final S01PacketEncryptionRequest s01PacketEncryptionRequest) {
        final SecretKey newSharedKey = CryptManager.createNewSharedKey();
        final String serverId = s01PacketEncryptionRequest.getServerId();
        final PublicKey publicKey = s01PacketEncryptionRequest.getPublicKey();
        final String string = new BigInteger(CryptManager.getServerIdHash(serverId, publicKey, newSharedKey)).toString(16);
        if (this.mc.getCurrentServerData() != null && this.mc.getCurrentServerData().func_181041_d()) {
            this.getSessionService().joinServer(this.mc.getSession().getProfile(), this.mc.getSession().getToken(), string);
        }
        else {
            this.getSessionService().joinServer(this.mc.getSession().getProfile(), this.mc.getSession().getToken(), string);
        }
        this.networkManager.sendPacket(new C01PacketEncryptionResponse(newSharedKey, publicKey, s01PacketEncryptionRequest.getVerifyToken()), (GenericFutureListener)new GenericFutureListener(this, newSharedKey) {
            final SecretKey val$secretkey;
            final NetHandlerLoginClient this$0;
            
            public void operationComplete(final Future future) throws Exception {
                NetHandlerLoginClient.access$000(this.this$0).enableEncryption(this.val$secretkey);
            }
        }, new GenericFutureListener[0]);
    }
    
    public NetHandlerLoginClient(final NetworkManager networkManager, final Minecraft mc, final GuiScreen previousGuiScreen) {
        this.networkManager = networkManager;
        this.mc = mc;
        this.previousGuiScreen = previousGuiScreen;
    }
}
