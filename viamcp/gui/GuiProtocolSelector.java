package viamcp.gui;

import java.io.*;
import org.lwjgl.opengl.*;
import com.nquantum.*;
import viamcp.protocols.*;
import net.minecraft.client.gui.*;
import viamcp.*;
import net.minecraft.util.*;
import net.minecraft.client.*;

public class GuiProtocolSelector extends GuiScreen
{
    public SlotList list;
    private GuiScreen parent;
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        this.list.actionPerformed(guiButton);
        if (guiButton.id == 1) {
            this.mc.displayGuiScreen(this.parent);
        }
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(1, GuiProtocolSelector.width / 2 - 100, GuiProtocolSelector.height - 27, 200, 20, "Back"));
        this.list = new SlotList(this.mc, GuiProtocolSelector.width, GuiProtocolSelector.height, 32, GuiProtocolSelector.height - 32, 10);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.list.drawScreen(n, n2, n3);
        GL11.glPushMatrix();
        Asyncware.roboto.drawCenteredString("Protocol Switcher", (float)(GuiProtocolSelector.width / 2), 9.0f, -1);
        GL11.glPopMatrix();
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        this.list.handleMouseInput();
        super.handleMouseInput();
    }
    
    public GuiProtocolSelector(final GuiScreen parent) {
        this.parent = parent;
    }
    
    class SlotList extends GuiSlot
    {
        final GuiProtocolSelector this$0;
        
        @Override
        protected int getSize() {
            return ProtocolCollection.values().length;
        }
        
        @Override
        protected boolean isSelected(final int n) {
            return false;
        }
        
        @Override
        protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
            Asyncware.roboto.drawCenteredString((ViaMCP.getInstance().getVersion() == ProtocolCollection.values()[n].getVersion().getVersion()) ? EnumChatFormatting.GREEN.toString() : (EnumChatFormatting.GRAY.toString() + ProtocolCollection.getProtocolById(ProtocolCollection.values()[n].getVersion().getVersion()).getName()), (float)(this.width / 2), (float)(n3 + 2), -1);
        }
        
        @Override
        protected void elementClicked(final int n, final boolean b, final int n2, final int n3) {
            ViaMCP.getInstance().setVersion(ProtocolCollection.values()[n].getVersion().getVersion());
        }
        
        @Override
        protected void drawBackground() {
            final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        }
        
        public SlotList(final GuiProtocolSelector this$0, final Minecraft minecraft, final int n, final int n2, final int n3, final int n4, final int n5) {
            this.this$0 = this$0;
            super(minecraft, n, n2, n3, n4, n5);
        }
    }
}
