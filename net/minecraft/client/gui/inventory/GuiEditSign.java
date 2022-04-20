package net.minecraft.client.gui.inventory;

import net.minecraft.client.gui.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.renderer.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.tileentity.*;
import org.lwjgl.input.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.network.*;
import net.minecraft.util.*;
import java.io.*;

public class GuiEditSign extends GuiScreen
{
    private int editLine;
    private GuiButton doneBtn;
    private TileEntitySign tileSign;
    private int updateCounter;
    
    @Override
    public void updateScreen() {
        ++this.updateCounter;
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("sign.edit", new Object[0]), GuiEditSign.width / 2, 40, 16777215);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.translate((float)(GuiEditSign.width / 2), 0.0f, 50.0f);
        final float n4 = 93.75f;
        GlStateManager.scale(-n4, -n4, -n4);
        GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
        if (this.tileSign.getBlockType() == Blocks.standing_sign) {
            GlStateManager.rotate(this.tileSign.getBlockMetadata() * 360 / 16.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(0.0f, -1.0625f, 0.0f);
        }
        else {
            final int blockMetadata = this.tileSign.getBlockMetadata();
            float n5 = 0.0f;
            if (blockMetadata == 2) {
                n5 = 180.0f;
            }
            if (blockMetadata == 4) {
                n5 = 90.0f;
            }
            if (blockMetadata == 5) {
                n5 = -90.0f;
            }
            GlStateManager.rotate(n5, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(0.0f, -1.0625f, 0.0f);
        }
        if (this.updateCounter / 6 % 2 == 0) {
            this.tileSign.lineBeingEdited = this.editLine;
        }
        TileEntityRendererDispatcher.instance.renderTileEntityAt(this.tileSign, -0.5, -0.75, -0.5, 0.0f);
        this.tileSign.lineBeingEdited = -1;
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        this.buttonList.add(this.doneBtn = new GuiButton(0, GuiEditSign.width / 2 - 100, GuiEditSign.height / 4 + 120, I18n.format("gui.done", new Object[0])));
        this.tileSign.setEditable(false);
    }
    
    public GuiEditSign(final TileEntitySign tileSign) {
        this.tileSign = tileSign;
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        final NetHandlerPlayClient netHandler = this.mc.getNetHandler();
        if (netHandler != null) {
            netHandler.addToSendQueue(new C12PacketUpdateSign(this.tileSign.getPos(), this.tileSign.signText));
        }
        this.tileSign.setEditable(true);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (n == 200) {
            this.editLine = (this.editLine - 1 & 0x3);
        }
        if (n == 208 || n == 28 || n == 156) {
            this.editLine = (this.editLine + 1 & 0x3);
        }
        String s = this.tileSign.signText[this.editLine].getUnformattedText();
        if (n == 14 && s.length() > 0) {
            s = s.substring(0, s.length() - 1);
        }
        if (ChatAllowedCharacters.isAllowedCharacter(c) && this.fontRendererObj.getStringWidth(s + c) <= 90) {
            s += c;
        }
        this.tileSign.signText[this.editLine] = new ChatComponentText(s);
        if (n == 1) {
            this.actionPerformed(this.doneBtn);
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled && guiButton.id == 0) {
            this.tileSign.markDirty();
            this.mc.displayGuiScreen(null);
        }
    }
}
