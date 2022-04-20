package net.minecraft.realms;

import java.net.*;
import net.minecraft.client.*;
import net.minecraft.client.network.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.network.*;
import net.minecraft.network.login.client.*;
import org.apache.logging.log4j.*;

public class RealmsConnect
{
    private final RealmsScreen onlineScreen;
    private NetworkManager connection;
    private volatile boolean aborted;
    private static final Logger LOGGER;
    
    static NetworkManager access$100(final RealmsConnect realmsConnect) {
        return realmsConnect.connection;
    }
    
    static RealmsScreen access$200(final RealmsConnect realmsConnect) {
        return realmsConnect.onlineScreen;
    }
    
    static NetworkManager access$102(final RealmsConnect realmsConnect, final NetworkManager connection) {
        return realmsConnect.connection = connection;
    }
    
    public RealmsConnect(final RealmsScreen onlineScreen) {
        this.aborted = false;
        this.onlineScreen = onlineScreen;
    }
    
    static Logger access$300() {
        return RealmsConnect.LOGGER;
    }
    
    static boolean access$000(final RealmsConnect realmsConnect) {
        return realmsConnect.aborted;
    }
    
    public void abort() {
        this.aborted = true;
    }
    
    public void tick() {
        if (this.connection != null) {
            if (this.connection.isChannelOpen()) {
                this.connection.processReceivedPackets();
            }
            else {
                this.connection.checkDisconnected();
            }
        }
    }
    
    public void connect(final String s, final int n) {
        Realms.setConnectedToRealms(true);
        new Thread(this, "Realms-connect-task", s, n) {
            final int val$p_connect_2_;
            final String val$p_connect_1_;
            final RealmsConnect this$0;
            
            @Override
            public void run() {
                final InetAddress byName = InetAddress.getByName(this.val$p_connect_1_);
                if (RealmsConnect.access$000(this.this$0)) {
                    return;
                }
                RealmsConnect.access$102(this.this$0, NetworkManager.func_181124_a(byName, this.val$p_connect_2_, Minecraft.getMinecraft().gameSettings.func_181148_f()));
                if (RealmsConnect.access$000(this.this$0)) {
                    return;
                }
                RealmsConnect.access$100(this.this$0).setNetHandler(new NetHandlerLoginClient(RealmsConnect.access$100(this.this$0), Minecraft.getMinecraft(), RealmsConnect.access$200(this.this$0).getProxy()));
                if (RealmsConnect.access$000(this.this$0)) {
                    return;
                }
                RealmsConnect.access$100(this.this$0).sendPacket(new C00Handshake(47, this.val$p_connect_1_, this.val$p_connect_2_, EnumConnectionState.LOGIN));
                if (RealmsConnect.access$000(this.this$0)) {
                    return;
                }
                RealmsConnect.access$100(this.this$0).sendPacket(new C00PacketLoginStart(Minecraft.getMinecraft().getSession().getProfile()));
            }
        }.start();
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
