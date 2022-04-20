package net.minecraft.client.multiplayer;

import java.util.concurrent.atomic.*;
import java.net.*;
import net.minecraft.client.network.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.network.*;
import net.minecraft.network.login.client.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import org.apache.logging.log4j.*;
import java.io.*;
import net.minecraft.util.*;

public class GuiConnecting extends GuiScreen
{
    private static final AtomicInteger CONNECTION_ID;
    private final GuiScreen previousGuiScreen;
    private NetworkManager networkManager;
    private boolean cancel;
    private static final Logger logger;
    
    private void connect(final String s, final int n) {
        GuiConnecting.logger.info("Connecting to " + s + ", " + n);
        new Thread(this, "Server Connector #" + GuiConnecting.CONNECTION_ID.incrementAndGet(), s, n) {
            final int val$port;
            final GuiConnecting this$0;
            final String val$ip;
            
            @Override
            public void run() {
                if (GuiConnecting.access$000(this.this$0)) {
                    return;
                }
                GuiConnecting.access$102(this.this$0, NetworkManager.func_181124_a(InetAddress.getByName(this.val$ip), this.val$port, GuiConnecting.access$200(this.this$0).gameSettings.func_181148_f()));
                GuiConnecting.access$100(this.this$0).setNetHandler(new NetHandlerLoginClient(GuiConnecting.access$100(this.this$0), GuiConnecting.access$300(this.this$0), GuiConnecting.access$400(this.this$0)));
                GuiConnecting.access$100(this.this$0).sendPacket(new C00Handshake(47, this.val$ip, this.val$port, EnumConnectionState.LOGIN));
                GuiConnecting.access$100(this.this$0).sendPacket(new C00PacketLoginStart(GuiConnecting.access$500(this.this$0).getSession().getProfile()));
            }
        }.start();
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, GuiConnecting.width / 2 - 100, GuiConnecting.height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
    }
    
    static Minecraft access$800(final GuiConnecting guiConnecting) {
        return guiConnecting.mc;
    }
    
    static Minecraft access$500(final GuiConnecting guiConnecting) {
        return guiConnecting.mc;
    }
    
    @Override
    public void updateScreen() {
        if (this.networkManager != null) {
            if (this.networkManager.isChannelOpen()) {
                this.networkManager.processReceivedPackets();
            }
            else {
                this.networkManager.checkDisconnected();
            }
        }
    }
    
    static {
        CONNECTION_ID = new AtomicInteger(0);
        logger = LogManager.getLogger();
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 0) {
            this.cancel = true;
            if (this.networkManager != null) {
                this.networkManager.closeChannel(new ChatComponentText("Aborted"));
            }
            this.mc.displayGuiScreen(this.previousGuiScreen);
        }
    }
    
    static NetworkManager access$100(final GuiConnecting guiConnecting) {
        return guiConnecting.networkManager;
    }
    
    static Minecraft access$300(final GuiConnecting guiConnecting) {
        return guiConnecting.mc;
    }
    
    static Logger access$600() {
        return GuiConnecting.logger;
    }
    
    static NetworkManager access$102(final GuiConnecting guiConnecting, final NetworkManager networkManager) {
        return guiConnecting.networkManager = networkManager;
    }
    
    static GuiScreen access$400(final GuiConnecting guiConnecting) {
        return guiConnecting.previousGuiScreen;
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        if (this.networkManager == null) {
            this.drawCenteredString(this.fontRendererObj, I18n.format("connect.connecting", new Object[0]), GuiConnecting.width / 2, GuiConnecting.height / 2 - 50, 16777215);
        }
        else {
            this.drawCenteredString(this.fontRendererObj, I18n.format("connect.authorizing", new Object[0]), GuiConnecting.width / 2, GuiConnecting.height / 2 - 50, 16777215);
        }
        super.drawScreen(n, n2, n3);
    }
    
    public GuiConnecting(final GuiScreen previousGuiScreen, final Minecraft mc, final String s, final int n) {
        this.mc = mc;
        this.previousGuiScreen = previousGuiScreen;
        mc.loadWorld(null);
        this.connect(s, n);
    }
    
    static boolean access$000(final GuiConnecting guiConnecting) {
        return guiConnecting.cancel;
    }
    
    static Minecraft access$200(final GuiConnecting guiConnecting) {
        return guiConnecting.mc;
    }
    
    public GuiConnecting(final GuiScreen previousGuiScreen, final Minecraft mc, final ServerData serverData) {
        this.mc = mc;
        this.previousGuiScreen = previousGuiScreen;
        final ServerAddress func_78860_a = ServerAddress.func_78860_a(serverData.serverIP);
        mc.loadWorld(null);
        mc.setServerData(serverData);
        this.connect(func_78860_a.getIP(), func_78860_a.getPort());
    }
    
    static Minecraft access$700(final GuiConnecting guiConnecting) {
        return guiConnecting.mc;
    }
}
