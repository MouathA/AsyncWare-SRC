package net.minecraft.client.gui.stream;

import net.minecraft.client.resources.*;
import java.io.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import tv.twitch.broadcast.*;
import net.minecraft.client.stream.*;
import net.minecraft.client.*;

public class GuiIngestServers extends GuiScreen
{
    private ServerList field_152311_g;
    private final GuiScreen field_152309_a;
    private String field_152310_f;
    
    static FontRenderer access$700(final GuiIngestServers guiIngestServers) {
        return guiIngestServers.fontRendererObj;
    }
    
    static FontRenderer access$600(final GuiIngestServers guiIngestServers) {
        return guiIngestServers.fontRendererObj;
    }
    
    static FontRenderer access$100(final GuiIngestServers guiIngestServers) {
        return guiIngestServers.fontRendererObj;
    }
    
    @Override
    public void initGui() {
        this.field_152310_f = I18n.format("options.stream.ingest.title", new Object[0]);
        this.field_152311_g = new ServerList(this.mc);
        if (!this.mc.getTwitchStream().func_152908_z()) {
            this.mc.getTwitchStream().func_152909_x();
        }
        this.buttonList.add(new GuiButton(1, GuiIngestServers.width / 2 - 155, GuiIngestServers.height - 24 - 6, 150, 20, I18n.format("gui.done", new Object[0])));
        this.buttonList.add(new GuiButton(2, GuiIngestServers.width / 2 + 5, GuiIngestServers.height - 24 - 6, 150, 20, I18n.format("options.stream.ingest.reset", new Object[0])));
    }
    
    static FontRenderer access$300(final GuiIngestServers guiIngestServers) {
        return guiIngestServers.fontRendererObj;
    }
    
    static FontRenderer access$400(final GuiIngestServers guiIngestServers) {
        return guiIngestServers.fontRendererObj;
    }
    
    public GuiIngestServers(final GuiScreen field_152309_a) {
        this.field_152309_a = field_152309_a;
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.field_152311_g.drawScreen(n, n2, n3);
        this.drawCenteredString(this.fontRendererObj, this.field_152310_f, GuiIngestServers.width / 2, 20, 16777215);
        super.drawScreen(n, n2, n3);
    }
    
    static FontRenderer access$500(final GuiIngestServers guiIngestServers) {
        return guiIngestServers.fontRendererObj;
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.field_152311_g.handleMouseInput();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == 1) {
                this.mc.displayGuiScreen(this.field_152309_a);
            }
            else {
                this.mc.gameSettings.streamPreferredServer = "";
                this.mc.gameSettings.saveOptions();
            }
        }
    }
    
    static FontRenderer access$000(final GuiIngestServers guiIngestServers) {
        return guiIngestServers.fontRendererObj;
    }
    
    @Override
    public void onGuiClosed() {
        if (this.mc.getTwitchStream().func_152908_z()) {
            this.mc.getTwitchStream().func_152932_y().func_153039_l();
        }
    }
    
    static FontRenderer access$200(final GuiIngestServers guiIngestServers) {
        return guiIngestServers.fontRendererObj;
    }
    
    class ServerList extends GuiSlot
    {
        final GuiIngestServers this$0;
        
        @Override
        protected void drawSlot(final int n, int n2, final int n3, final int n4, final int n5, final int n6) {
            final IngestServer ingestServer = this.mc.getTwitchStream().func_152925_v()[n];
            String s = ingestServer.serverUrl.replaceAll("\\{stream_key\\}", "");
            String s2 = (int)ingestServer.bitrateKbps + " kbps";
            String s3 = null;
            final IngestServerTester func_152932_y = this.mc.getTwitchStream().func_152932_y();
            if (func_152932_y != null) {
                if (ingestServer == func_152932_y.func_153040_c()) {
                    s = EnumChatFormatting.GREEN + s;
                    s2 = (int)(func_152932_y.func_153030_h() * 100.0f) + "%";
                }
                else if (n < func_152932_y.func_153028_p()) {
                    if (ingestServer.bitrateKbps == 0.0f) {
                        s2 = EnumChatFormatting.RED + "Down!";
                    }
                }
                else {
                    s2 = EnumChatFormatting.OBFUSCATED + "1234" + EnumChatFormatting.RESET + " kbps";
                }
            }
            else if (ingestServer.bitrateKbps == 0.0f) {
                s2 = EnumChatFormatting.RED + "Down!";
            }
            n2 -= 15;
            if (this.isSelected(n)) {
                s3 = EnumChatFormatting.BLUE + "(Preferred)";
            }
            else if (ingestServer.defaultServer) {
                s3 = EnumChatFormatting.GREEN + "(Default)";
            }
            this.this$0.drawString(GuiIngestServers.access$000(this.this$0), ingestServer.serverName, n2 + 2, n3 + 5, 16777215);
            this.this$0.drawString(GuiIngestServers.access$100(this.this$0), s, n2 + 2, n3 + GuiIngestServers.access$200(this.this$0).FONT_HEIGHT + 5 + 3, 3158064);
            this.this$0.drawString(GuiIngestServers.access$300(this.this$0), s2, this.getScrollBarX() - 5 - GuiIngestServers.access$400(this.this$0).getStringWidth(s2), n3 + 5, 8421504);
            if (s3 != null) {
                this.this$0.drawString(GuiIngestServers.access$500(this.this$0), s3, this.getScrollBarX() - 5 - GuiIngestServers.access$600(this.this$0).getStringWidth(s3), n3 + 5 + 3 + GuiIngestServers.access$700(this.this$0).FONT_HEIGHT, 8421504);
            }
        }
        
        @Override
        protected void drawBackground() {
        }
        
        @Override
        protected boolean isSelected(final int n) {
            return this.mc.getTwitchStream().func_152925_v()[n].serverUrl.equals(this.mc.gameSettings.streamPreferredServer);
        }
        
        @Override
        protected int getSize() {
            return this.mc.getTwitchStream().func_152925_v().length;
        }
        
        @Override
        protected int getScrollBarX() {
            return super.getScrollBarX() + 15;
        }
        
        public ServerList(final GuiIngestServers this$0, final Minecraft minecraft) {
            this.this$0 = this$0;
            super(minecraft, GuiIngestServers.width, GuiIngestServers.height, 32, GuiIngestServers.height - 35, (int)(minecraft.fontRendererObj.FONT_HEIGHT * 3.5));
            this.setShowSelectionBox(false);
        }
        
        @Override
        protected void elementClicked(final int n, final boolean b, final int n2, final int n3) {
            this.mc.gameSettings.streamPreferredServer = this.mc.getTwitchStream().func_152925_v()[n].serverUrl;
            this.mc.gameSettings.saveOptions();
        }
    }
}
