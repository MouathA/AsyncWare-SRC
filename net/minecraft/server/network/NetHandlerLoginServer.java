package net.minecraft.server.network;

import net.minecraft.network.login.*;
import javax.crypto.*;
import java.util.concurrent.atomic.*;
import net.minecraft.entity.player.*;
import com.mojang.authlib.*;
import net.minecraft.server.*;
import org.apache.commons.lang3.*;
import net.minecraft.network.*;
import org.apache.logging.log4j.*;
import com.google.common.base.*;
import io.netty.channel.*;
import io.netty.util.concurrent.*;
import net.minecraft.network.login.server.*;
import net.minecraft.network.login.client.*;
import java.util.*;
import net.minecraft.util.*;
import java.math.*;
import java.security.*;

public class NetHandlerLoginServer implements ITickable, INetHandlerLoginServer
{
    private SecretKey secretKey;
    private static final Logger logger;
    private static final AtomicInteger AUTHENTICATOR_THREAD_ID;
    private String serverId;
    private EntityPlayerMP field_181025_l;
    private LoginState currentLoginState;
    private GameProfile loginGameProfile;
    private static final Random RANDOM;
    private int connectionTimer;
    public final NetworkManager networkManager;
    private final byte[] verifyToken;
    private final MinecraftServer server;
    
    public NetHandlerLoginServer(final MinecraftServer server, final NetworkManager networkManager) {
        this.verifyToken = new byte[4];
        this.currentLoginState = LoginState.HELLO;
        this.serverId = "";
        this.server = server;
        this.networkManager = networkManager;
        NetHandlerLoginServer.RANDOM.nextBytes(this.verifyToken);
    }
    
    static SecretKey access$300(final NetHandlerLoginServer netHandlerLoginServer) {
        return netHandlerLoginServer.secretKey;
    }
    
    @Override
    public void onDisconnect(final IChatComponent chatComponent) {
        NetHandlerLoginServer.logger.info(this.getConnectionInfo() + " lost connection: " + chatComponent.getUnformattedText());
    }
    
    static Logger access$400() {
        return NetHandlerLoginServer.logger;
    }
    
    @Override
    public void processLoginStart(final C00PacketLoginStart c00PacketLoginStart) {
        Validate.validState(this.currentLoginState == LoginState.HELLO, "Unexpected hello packet", new Object[0]);
        this.loginGameProfile = c00PacketLoginStart.getProfile();
        if (this.server.isServerInOnlineMode() && !this.networkManager.isLocalChannel()) {
            this.currentLoginState = LoginState.KEY;
            this.networkManager.sendPacket(new S01PacketEncryptionRequest(this.serverId, this.server.getKeyPair().getPublic(), this.verifyToken));
        }
        else {
            this.currentLoginState = LoginState.READY_TO_ACCEPT;
        }
    }
    
    static {
        AUTHENTICATOR_THREAD_ID = new AtomicInteger(0);
        logger = LogManager.getLogger();
        RANDOM = new Random();
    }
    
    static String access$200(final NetHandlerLoginServer netHandlerLoginServer) {
        return netHandlerLoginServer.serverId;
    }
    
    static GameProfile access$100(final NetHandlerLoginServer netHandlerLoginServer) {
        return netHandlerLoginServer.loginGameProfile;
    }
    
    public void closeConnection(final String s) {
        NetHandlerLoginServer.logger.info("Disconnecting " + this.getConnectionInfo() + ": " + s);
        final ChatComponentText chatComponentText = new ChatComponentText(s);
        this.networkManager.sendPacket(new S00PacketDisconnect(chatComponentText));
        this.networkManager.closeChannel(chatComponentText);
    }
    
    static GameProfile access$102(final NetHandlerLoginServer netHandlerLoginServer, final GameProfile loginGameProfile) {
        return netHandlerLoginServer.loginGameProfile = loginGameProfile;
    }
    
    public String getConnectionInfo() {
        return (this.loginGameProfile != null) ? (this.loginGameProfile.toString() + " (" + this.networkManager.getRemoteAddress().toString() + ")") : String.valueOf(this.networkManager.getRemoteAddress());
    }
    
    static MinecraftServer access$000(final NetHandlerLoginServer netHandlerLoginServer) {
        return netHandlerLoginServer.server;
    }
    
    static LoginState access$502(final NetHandlerLoginServer netHandlerLoginServer, final LoginState currentLoginState) {
        return netHandlerLoginServer.currentLoginState = currentLoginState;
    }
    
    @Override
    public void update() {
        if (this.currentLoginState == LoginState.READY_TO_ACCEPT) {
            this.tryAcceptPlayer();
        }
        else if (this.currentLoginState == LoginState.DELAY_ACCEPT && this.server.getConfigurationManager().getPlayerByUUID(this.loginGameProfile.getId()) == null) {
            this.currentLoginState = LoginState.READY_TO_ACCEPT;
            this.server.getConfigurationManager().initializeConnectionToPlayer(this.networkManager, this.field_181025_l);
            this.field_181025_l = null;
        }
        if (this.connectionTimer++ == 600) {
            this.closeConnection("Took too long to log in");
        }
    }
    
    protected GameProfile getOfflineProfile(final GameProfile gameProfile) {
        return new GameProfile(UUID.nameUUIDFromBytes(("OfflinePlayer:" + gameProfile.getName()).getBytes(Charsets.UTF_8)), gameProfile.getName());
    }
    
    public void tryAcceptPlayer() {
        if (!this.loginGameProfile.isComplete()) {
            this.loginGameProfile = this.getOfflineProfile(this.loginGameProfile);
        }
        final String allowUserToConnect = this.server.getConfigurationManager().allowUserToConnect(this.networkManager.getRemoteAddress(), this.loginGameProfile);
        if (allowUserToConnect != null) {
            this.closeConnection(allowUserToConnect);
        }
        else {
            this.currentLoginState = LoginState.ACCEPTED;
            if (this.server.getNetworkCompressionTreshold() >= 0 && !this.networkManager.isLocalChannel()) {
                this.networkManager.sendPacket(new S03PacketEnableCompression(this.server.getNetworkCompressionTreshold()), (GenericFutureListener)new ChannelFutureListener(this) {
                    final NetHandlerLoginServer this$0;
                    
                    public void operationComplete(final Future future) throws Exception {
                        this.operationComplete((ChannelFuture)future);
                    }
                    
                    public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                        this.this$0.networkManager.setCompressionTreshold(NetHandlerLoginServer.access$000(this.this$0).getNetworkCompressionTreshold());
                    }
                }, new GenericFutureListener[0]);
            }
            this.networkManager.sendPacket(new S02PacketLoginSuccess(this.loginGameProfile));
            if (this.server.getConfigurationManager().getPlayerByUUID(this.loginGameProfile.getId()) != null) {
                this.currentLoginState = LoginState.DELAY_ACCEPT;
                this.field_181025_l = this.server.getConfigurationManager().createPlayerForUser(this.loginGameProfile);
            }
            else {
                this.server.getConfigurationManager().initializeConnectionToPlayer(this.networkManager, this.server.getConfigurationManager().createPlayerForUser(this.loginGameProfile));
            }
        }
    }
    
    @Override
    public void processEncryptionResponse(final C01PacketEncryptionResponse c01PacketEncryptionResponse) {
        Validate.validState(this.currentLoginState == LoginState.KEY, "Unexpected key packet", new Object[0]);
        final PrivateKey private1 = this.server.getKeyPair().getPrivate();
        if (!Arrays.equals(this.verifyToken, c01PacketEncryptionResponse.getVerifyToken(private1))) {
            throw new IllegalStateException("Invalid nonce!");
        }
        this.secretKey = c01PacketEncryptionResponse.getSecretKey(private1);
        this.currentLoginState = LoginState.AUTHENTICATING;
        this.networkManager.enableEncryption(this.secretKey);
        new Thread(this, "User Authenticator #" + NetHandlerLoginServer.AUTHENTICATOR_THREAD_ID.incrementAndGet()) {
            final NetHandlerLoginServer this$0;
            
            @Override
            public void run() {
                final GameProfile access$100 = NetHandlerLoginServer.access$100(this.this$0);
                NetHandlerLoginServer.access$102(this.this$0, NetHandlerLoginServer.access$000(this.this$0).getMinecraftSessionService().hasJoinedServer(new GameProfile((UUID)null, access$100.getName()), new BigInteger(CryptManager.getServerIdHash(NetHandlerLoginServer.access$200(this.this$0), NetHandlerLoginServer.access$000(this.this$0).getKeyPair().getPublic(), NetHandlerLoginServer.access$300(this.this$0))).toString(16)));
                if (NetHandlerLoginServer.access$100(this.this$0) != null) {
                    NetHandlerLoginServer.access$400().info("UUID of player " + NetHandlerLoginServer.access$100(this.this$0).getName() + " is " + NetHandlerLoginServer.access$100(this.this$0).getId());
                    NetHandlerLoginServer.access$502(this.this$0, LoginState.READY_TO_ACCEPT);
                }
                else if (NetHandlerLoginServer.access$000(this.this$0).isSinglePlayer()) {
                    NetHandlerLoginServer.access$400().warn("Failed to verify username but will let them in anyway!");
                    NetHandlerLoginServer.access$102(this.this$0, this.this$0.getOfflineProfile(access$100));
                    NetHandlerLoginServer.access$502(this.this$0, LoginState.READY_TO_ACCEPT);
                }
                else {
                    this.this$0.closeConnection("Failed to verify username!");
                    NetHandlerLoginServer.access$400().error("Username '" + NetHandlerLoginServer.access$100(this.this$0).getName() + "' tried to join with an invalid session");
                }
            }
        }.start();
    }
    
    enum LoginState
    {
        READY_TO_ACCEPT("READY_TO_ACCEPT", 3);
        
        private static final LoginState[] $VALUES;
        
        HELLO("HELLO", 0), 
        DELAY_ACCEPT("DELAY_ACCEPT", 4), 
        KEY("KEY", 1), 
        AUTHENTICATING("AUTHENTICATING", 2), 
        ACCEPTED("ACCEPTED", 5);
        
        private LoginState(final String s, final int n) {
        }
        
        static {
            $VALUES = new LoginState[] { LoginState.HELLO, LoginState.KEY, LoginState.AUTHENTICATING, LoginState.READY_TO_ACCEPT, LoginState.DELAY_ACCEPT, LoginState.ACCEPTED };
        }
    }
}
