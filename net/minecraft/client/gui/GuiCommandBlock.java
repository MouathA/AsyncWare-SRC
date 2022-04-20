package net.minecraft.client.gui;

import net.minecraft.command.server.*;
import java.io.*;
import org.lwjgl.input.*;
import net.minecraft.client.resources.*;
import org.apache.logging.log4j.*;
import io.netty.buffer.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.*;

public class GuiCommandBlock extends GuiScreen
{
    private GuiButton field_175390_s;
    private boolean field_175389_t;
    private GuiTextField previousOutputTextField;
    private GuiButton cancelBtn;
    private GuiTextField commandTextField;
    private final CommandBlockLogic localCommandBlock;
    private GuiButton doneBtn;
    private static final Logger field_146488_a;
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        this.commandTextField.mouseClicked(n, n2, n3);
        this.previousOutputTextField.mouseClicked(n, n2, n3);
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(this.doneBtn = new GuiButton(0, GuiCommandBlock.width / 2 - 4 - 150, GuiCommandBlock.height / 4 + 120 + 12, 150, 20, I18n.format("gui.done", new Object[0])));
        this.buttonList.add(this.cancelBtn = new GuiButton(1, GuiCommandBlock.width / 2 + 4, GuiCommandBlock.height / 4 + 120 + 12, 150, 20, I18n.format("gui.cancel", new Object[0])));
        this.buttonList.add(this.field_175390_s = new GuiButton(4, GuiCommandBlock.width / 2 + 150 - 20, 150, 20, 20, "O"));
        (this.commandTextField = new GuiTextField(2, this.fontRendererObj, GuiCommandBlock.width / 2 - 150, 50, 300, 20)).setMaxStringLength(32767);
        this.commandTextField.setFocused(true);
        this.commandTextField.setText(this.localCommandBlock.getCommand());
        (this.previousOutputTextField = new GuiTextField(3, this.fontRendererObj, GuiCommandBlock.width / 2 - 150, 150, 276, 20)).setMaxStringLength(32767);
        this.previousOutputTextField.setEnabled(false);
        this.previousOutputTextField.setText("-");
        this.field_175389_t = this.localCommandBlock.shouldTrackOutput();
        this.func_175388_a();
        this.doneBtn.enabled = (this.commandTextField.getText().trim().length() > 0);
    }
    
    private void func_175388_a() {
        if (this.localCommandBlock.shouldTrackOutput()) {
            this.field_175390_s.displayString = "O";
            if (this.localCommandBlock.getLastOutput() != null) {
                this.previousOutputTextField.setText(this.localCommandBlock.getLastOutput().getUnformattedText());
            }
        }
        else {
            this.field_175390_s.displayString = "X";
            this.previousOutputTextField.setText("-");
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("advMode.setCommand", new Object[0]), GuiCommandBlock.width / 2, 20, 16777215);
        this.drawString(this.fontRendererObj, I18n.format("advMode.command", new Object[0]), GuiCommandBlock.width / 2 - 150, 37, 10526880);
        this.commandTextField.drawTextBox();
        final FontRenderer fontRendererObj = this.fontRendererObj;
        final String format = I18n.format("advMode.nearestPlayer", new Object[0]);
        final int n4 = GuiCommandBlock.width / 2 - 150;
        final int n5 = 75;
        final int n6 = 0;
        int n7 = 0;
        ++n7;
        this.drawString(fontRendererObj, format, n4, n5 + n6 * this.fontRendererObj.FONT_HEIGHT, 10526880);
        final FontRenderer fontRendererObj2 = this.fontRendererObj;
        final String format2 = I18n.format("advMode.randomPlayer", new Object[0]);
        final int n8 = GuiCommandBlock.width / 2 - 150;
        final int n9 = 75;
        final int n10 = 0;
        ++n7;
        this.drawString(fontRendererObj2, format2, n8, n9 + n10 * this.fontRendererObj.FONT_HEIGHT, 10526880);
        final FontRenderer fontRendererObj3 = this.fontRendererObj;
        final String format3 = I18n.format("advMode.allPlayers", new Object[0]);
        final int n11 = GuiCommandBlock.width / 2 - 150;
        final int n12 = 75;
        final int n13 = 0;
        ++n7;
        this.drawString(fontRendererObj3, format3, n11, n12 + n13 * this.fontRendererObj.FONT_HEIGHT, 10526880);
        final FontRenderer fontRendererObj4 = this.fontRendererObj;
        final String format4 = I18n.format("advMode.allEntities", new Object[0]);
        final int n14 = GuiCommandBlock.width / 2 - 150;
        final int n15 = 75;
        final int n16 = 0;
        ++n7;
        this.drawString(fontRendererObj4, format4, n14, n15 + n16 * this.fontRendererObj.FONT_HEIGHT, 10526880);
        final FontRenderer fontRendererObj5 = this.fontRendererObj;
        final String s = "";
        final int n17 = GuiCommandBlock.width / 2 - 150;
        final int n18 = 75;
        final int n19 = 0;
        ++n7;
        this.drawString(fontRendererObj5, s, n17, n18 + n19 * this.fontRendererObj.FONT_HEIGHT, 10526880);
        if (this.previousOutputTextField.getText().length() > 0) {
            final int n20 = 75 + 0 * this.fontRendererObj.FONT_HEIGHT + 16;
            this.drawString(this.fontRendererObj, I18n.format("advMode.previousOutput", new Object[0]), GuiCommandBlock.width / 2 - 150, 75, 10526880);
            this.previousOutputTextField.drawTextBox();
        }
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    static {
        field_146488_a = LogManager.getLogger();
    }
    
    public GuiCommandBlock(final CommandBlockLogic localCommandBlock) {
        this.localCommandBlock = localCommandBlock;
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == 1) {
                this.localCommandBlock.setTrackOutput(this.field_175389_t);
                this.mc.displayGuiScreen(null);
            }
            else if (guiButton.id == 0) {
                final PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
                packetBuffer.writeByte(this.localCommandBlock.func_145751_f());
                this.localCommandBlock.func_145757_a(packetBuffer);
                packetBuffer.writeString(this.commandTextField.getText());
                packetBuffer.writeBoolean(this.localCommandBlock.shouldTrackOutput());
                this.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload("MC|AdvCdm", packetBuffer));
                if (!this.localCommandBlock.shouldTrackOutput()) {
                    this.localCommandBlock.setLastOutput(null);
                }
                this.mc.displayGuiScreen(null);
            }
            else if (guiButton.id == 4) {
                this.localCommandBlock.setTrackOutput(!this.localCommandBlock.shouldTrackOutput());
                this.func_175388_a();
            }
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        this.commandTextField.textboxKeyTyped(c, n);
        this.previousOutputTextField.textboxKeyTyped(c, n);
        this.doneBtn.enabled = (this.commandTextField.getText().trim().length() > 0);
        if (n != 28 && n != 156) {
            if (n == 1) {
                this.actionPerformed(this.cancelBtn);
            }
        }
        else {
            this.actionPerformed(this.doneBtn);
        }
    }
    
    @Override
    public void updateScreen() {
        this.commandTextField.updateCursorCounter();
    }
}
