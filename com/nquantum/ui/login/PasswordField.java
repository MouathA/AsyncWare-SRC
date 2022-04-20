package com.nquantum.ui.login;

import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;

public class PasswordField extends Gui
{
    private final int yPos;
    private boolean canLoseFocus;
    private final int xPos;
    private final FontRenderer fontRenderer;
    private final int width;
    private int cursorCounter;
    private String text;
    private int disabledColor;
    private int cursorPosition;
    private int selectionEnd;
    private boolean isEnabled;
    private int maxStringLength;
    private boolean enableBackgroundDrawing;
    private int i;
    public boolean isFocused;
    private final int height;
    private boolean b;
    private int enabledColor;
    
    public void setCanLoseFocus(final boolean canLoseFocus) {
        this.canLoseFocus = canLoseFocus;
    }
    
    public void drawTextBox() {
        if (this.func_73778_q()) {
            if (this.getEnableBackgroundDrawing()) {
                Gui.drawRect(this.xPos - 1, this.yPos - 1, this.xPos + this.width + 1, this.yPos + this.height + 1, -6250336);
                Gui.drawRect(this.xPos, this.yPos, this.xPos + this.width, this.yPos + this.height, -16777216);
            }
            final int n = this.isEnabled ? this.enabledColor : this.disabledColor;
            final int n2 = this.cursorPosition - this.i;
            int length = this.selectionEnd - this.i;
            final String trimStringToWidth = this.fontRenderer.trimStringToWidth(this.text.substring(this.i), this.getWidth());
            final boolean b = n2 >= 0 && n2 <= trimStringToWidth.length();
            final boolean b2 = this.isFocused && this.cursorCounter / 6 % 2 == 0 && b;
            final int n3 = this.enableBackgroundDrawing ? (this.xPos + 4) : this.xPos;
            final int n4 = this.enableBackgroundDrawing ? (this.yPos + (this.height - 8) / 2) : this.yPos;
            int drawStringWithShadow = n3;
            if (length > trimStringToWidth.length()) {
                length = trimStringToWidth.length();
            }
            if (trimStringToWidth.length() > 0) {
                if (b) {
                    trimStringToWidth.substring(0, n2);
                }
                drawStringWithShadow = Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.text.replaceAll("(?s).", "*"), (float)n3, (float)n4, n);
            }
            final boolean b3 = this.cursorPosition < this.text.length() || this.text.length() >= this.getMaxStringLength();
            int n5 = drawStringWithShadow;
            if (!b) {
                n5 = ((n2 > 0) ? (n3 + this.width) : n3);
            }
            else if (b3) {
                n5 = drawStringWithShadow - 1;
                --drawStringWithShadow;
            }
            if (trimStringToWidth.length() > 0 && b && n2 < trimStringToWidth.length()) {
                Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(trimStringToWidth.substring(n2), (float)drawStringWithShadow, (float)n4, n);
            }
            if (b2) {
                if (b3) {
                    Gui.drawRect(n5, n4 - 1, n5 + 1, n4 + 1 + this.fontRenderer.FONT_HEIGHT, -3092272);
                }
                else {
                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("_", (float)n5, (float)n4, n);
                }
            }
            if (length != n2) {
                this.drawCursorVertical(n5, n4 - 1, n3 + this.fontRenderer.getStringWidth(trimStringToWidth.substring(0, length)) - 1, n4 + 1 + this.fontRenderer.FONT_HEIGHT);
            }
        }
    }
    
    public boolean getEnableBackgroundDrawing() {
        return this.enableBackgroundDrawing;
    }
    
    public int getCursorPosition() {
        return this.cursorPosition;
    }
    
    public int type(final int n, final int n2, final boolean b) {
        int index = n2;
        final boolean b2 = n < 0;
        while (0 < Math.abs(n)) {
            if (!b2) {
                final int length = this.text.length();
                index = this.text.indexOf(32, index);
                if (index == -1) {
                    index = length;
                }
                else {
                    while (b) {
                        if (index >= length) {
                            break;
                        }
                        if (this.text.charAt(index) != ' ') {
                            break;
                        }
                        ++index;
                    }
                }
            }
            else {
                while (b) {
                    if (index <= 0) {
                        break;
                    }
                    if (this.text.charAt(index - 1) != ' ') {
                        break;
                    }
                    --index;
                }
                while (index > 0 && this.text.charAt(index - 1) != ' ') {
                    --index;
                }
            }
            int n3 = 0;
            ++n3;
        }
        return index;
    }
    
    public void setText(final String text) {
        if (text.length() > this.maxStringLength) {
            this.text = text.substring(0, this.maxStringLength);
        }
        else {
            this.text = text;
        }
        this.setCursorPositionEnd();
    }
    
    public int getNthWordFromCursor(final int n) {
        return this.getNthWordFromPos(n, this.getCursorPosition());
    }
    
    public boolean isFocused() {
        return this.isFocused;
    }
    
    public int getMaxStringLength() {
        return this.maxStringLength;
    }
    
    public void func_73794_g(final int enabledColor) {
        this.enabledColor = enabledColor;
    }
    
    public void setEnableBackgroundDrawing(final boolean enableBackgroundDrawing) {
        this.enableBackgroundDrawing = enableBackgroundDrawing;
    }
    
    public boolean func_73778_q() {
        return this.b;
    }
    
    public void func_73790_e(final boolean b) {
        this.b = b;
    }
    
    public String getText() {
        return this.text.replaceAll(" ", "");
    }
    
    private void drawCursorVertical(int n, int n2, int n3, int n4) {
        if (n < n3) {
            final int n5 = n;
            n = n3;
            n3 = n5;
        }
        if (n2 < n4) {
            final int n6 = n2;
            n2 = n4;
            n4 = n6;
        }
        final WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
        GL11.glColor4f(0.0f, 0.0f, 255.0f, 255.0f);
        GL11.glDisable(3553);
        GL11.glEnable(3058);
        GL11.glLogicOp(5387);
        worldRenderer.begin(7, worldRenderer.getVertexFormat());
        worldRenderer.pos(n, n4, 0.0);
        worldRenderer.pos(n3, n4, 0.0);
        worldRenderer.pos(n3, n2, 0.0);
        worldRenderer.pos(n, n2, 0.0);
        worldRenderer.finishDrawing();
        GL11.glDisable(3058);
        GL11.glEnable(3553);
    }
    
    public int getWidth() {
        return this.getEnableBackgroundDrawing() ? (this.width - 8) : this.width;
    }
    
    public void writeText(final String s) {
        String string = "";
        final String filterAllowedCharacters = ChatAllowedCharacters.filterAllowedCharacters(s);
        final int n = (this.cursorPosition < this.selectionEnd) ? this.cursorPosition : this.selectionEnd;
        final int n2 = (this.cursorPosition < this.selectionEnd) ? this.selectionEnd : this.cursorPosition;
        final int n3 = this.maxStringLength - this.text.length() - (n - this.selectionEnd);
        if (this.text.length() > 0) {
            string = String.valueOf(String.valueOf(string)) + this.text.substring(0, n);
        }
        String s2;
        int length;
        if (n3 < filterAllowedCharacters.length()) {
            s2 = String.valueOf(String.valueOf(string)) + filterAllowedCharacters.substring(0, n3);
            length = n3;
        }
        else {
            s2 = String.valueOf(String.valueOf(string)) + filterAllowedCharacters;
            length = filterAllowedCharacters.length();
        }
        if (this.text.length() > 0 && n2 < this.text.length()) {
            s2 = String.valueOf(String.valueOf(s2)) + this.text.substring(n2);
        }
        this.text = s2.replaceAll(" ", "");
        this.cursorPos(n - this.selectionEnd + length);
    }
    
    public void setCursorPosition(final int cursorPosition) {
        this.cursorPosition = cursorPosition;
        final int length = this.text.length();
        if (this.cursorPosition < 0) {
            this.cursorPosition = 0;
        }
        if (this.cursorPosition > length) {
            this.cursorPosition = length;
        }
        this.func_73800_i(this.cursorPosition);
    }
    
    public PasswordField(final FontRenderer fontRenderer, final int xPos, final int yPos, final int width, final int height) {
        this.text = "";
        this.maxStringLength = 50;
        this.enableBackgroundDrawing = true;
        this.canLoseFocus = true;
        this.isFocused = false;
        this.isEnabled = true;
        this.i = 0;
        this.cursorPosition = 0;
        this.selectionEnd = 0;
        this.enabledColor = 14737632;
        this.disabledColor = 7368816;
        this.b = true;
        this.fontRenderer = fontRenderer;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
    }
    
    public void setMaxStringLength(final int maxStringLength) {
        this.maxStringLength = maxStringLength;
        if (this.text.length() > maxStringLength) {
            this.text = this.text.substring(0, maxStringLength);
        }
    }
    
    public int getSelectionEnd() {
        return this.selectionEnd;
    }
    
    public int getNthWordFromPos(final int n, final int n2) {
        return this.type(n, this.getCursorPosition(), true);
    }
    
    public void setCursorPositionEnd() {
        this.setCursorPosition(this.text.length());
    }
    
    public void setCursorPositionZero() {
        this.setCursorPosition(0);
    }
    
    public void deleteFromCursor(final int n) {
        if (this.text.length() != 0) {
            if (this.selectionEnd != this.cursorPosition) {
                this.writeText("");
            }
            else {
                final boolean b = n < 0;
                final int n2 = b ? (this.cursorPosition + n) : this.cursorPosition;
                final int n3 = b ? this.cursorPosition : (this.cursorPosition + n);
                String text = "";
                if (n2 >= 0) {
                    text = this.text.substring(0, n2);
                }
                if (n3 < this.text.length()) {
                    text = String.valueOf(String.valueOf(text)) + this.text.substring(n3);
                }
                this.text = text;
                if (b) {
                    this.cursorPos(n);
                }
            }
        }
    }
    
    public String getSelectedtext() {
        return this.text.substring((this.cursorPosition < this.selectionEnd) ? this.cursorPosition : this.selectionEnd, (this.cursorPosition < this.selectionEnd) ? this.selectionEnd : this.cursorPosition);
    }
    
    public boolean textboxKeyTyped(final char c, final int n) {
        if (!this.isEnabled || !this.isFocused) {
            return false;
        }
        switch (c) {
            case '\u0001': {
                this.setCursorPositionEnd();
                this.func_73800_i(0);
                return true;
            }
            case '\u0003': {
                GuiScreen.setClipboardString(this.getSelectedtext());
                return true;
            }
            case '\u0016': {
                this.writeText(GuiScreen.getClipboardString());
                return true;
            }
            case '\u0018': {
                GuiScreen.setClipboardString(this.getSelectedtext());
                this.writeText("");
                return true;
            }
            default: {
                switch (n) {
                    case 14: {
                        if (GuiScreen.isCtrlKeyDown()) {
                            this.func_73779_a(-1);
                        }
                        else {
                            this.deleteFromCursor(-1);
                        }
                        return true;
                    }
                    case 199: {
                        if (GuiScreen.isShiftKeyDown()) {
                            this.func_73800_i(0);
                        }
                        else {
                            this.setCursorPositionZero();
                        }
                        return true;
                    }
                    case 203: {
                        if (GuiScreen.isShiftKeyDown()) {
                            if (GuiScreen.isCtrlKeyDown()) {
                                this.func_73800_i(this.getNthWordFromPos(-1, this.getSelectionEnd()));
                            }
                            else {
                                this.func_73800_i(this.getSelectionEnd() - 1);
                            }
                        }
                        else if (GuiScreen.isCtrlKeyDown()) {
                            this.setCursorPosition(this.getNthWordFromCursor(-1));
                        }
                        else {
                            this.cursorPos(-1);
                        }
                        return true;
                    }
                    case 205: {
                        if (GuiScreen.isShiftKeyDown()) {
                            if (GuiScreen.isCtrlKeyDown()) {
                                this.func_73800_i(this.getNthWordFromPos(1, this.getSelectionEnd()));
                            }
                            else {
                                this.func_73800_i(this.getSelectionEnd() + 1);
                            }
                        }
                        else if (GuiScreen.isCtrlKeyDown()) {
                            this.setCursorPosition(this.getNthWordFromCursor(1));
                        }
                        else {
                            this.cursorPos(1);
                        }
                        return true;
                    }
                    case 207: {
                        if (GuiScreen.isShiftKeyDown()) {
                            this.func_73800_i(this.text.length());
                        }
                        else {
                            this.setCursorPositionEnd();
                        }
                        return true;
                    }
                    case 211: {
                        if (GuiScreen.isCtrlKeyDown()) {
                            this.func_73779_a(1);
                        }
                        else {
                            this.deleteFromCursor(1);
                        }
                        return true;
                    }
                    default: {
                        if (ChatAllowedCharacters.isAllowedCharacter(c)) {
                            this.writeText(Character.toString(c));
                            return true;
                        }
                        return false;
                    }
                }
                break;
            }
        }
    }
    
    public void setFocused(final boolean isFocused) {
        if (isFocused && !this.isFocused) {
            this.cursorCounter = 0;
        }
        this.isFocused = isFocused;
    }
    
    public void updateCursorCounter() {
        ++this.cursorCounter;
    }
    
    public void mouseClicked(final int n, final int n2, final int n3) {
        final boolean b = n >= this.xPos && n < this.xPos + this.width && n2 >= this.yPos && n2 < this.yPos + this.height;
        if (this.canLoseFocus) {
            this.setFocused(this.isEnabled && b);
        }
        if (this.isFocused && n3 == 0) {
            int n4 = n - this.xPos;
            if (this.enableBackgroundDrawing) {
                n4 -= 4;
            }
            this.setCursorPosition(this.fontRenderer.trimStringToWidth(this.fontRenderer.trimStringToWidth(this.text.substring(this.i), this.getWidth()), n4).length() + this.i);
        }
    }
    
    public void func_73800_i(int n) {
        final int length = this.text.length();
        if (0 > length) {
            n = length;
        }
        this.selectionEnd = 0;
        if (this.fontRenderer != null) {
            if (this.i > length) {
                this.i = length;
            }
            final int width = this.getWidth();
            final int n2 = this.fontRenderer.trimStringToWidth(this.text.substring(this.i), width).length() + this.i;
            if (0 == this.i) {
                this.i -= this.fontRenderer.trimStringToWidth(this.text, width, true).length();
            }
            if (0 > n2) {
                this.i += 0 - n2;
            }
            else if (0 <= this.i) {
                this.i -= this.i - 0;
            }
            if (this.i < 0) {
                this.i = 0;
            }
            if (this.i > length) {
                this.i = length;
            }
        }
    }
    
    public void func_73779_a(final int n) {
        if (this.text.length() != 0) {
            if (this.selectionEnd != this.cursorPosition) {
                this.writeText("");
            }
            else {
                this.deleteFromCursor(this.getNthWordFromCursor(n) - this.cursorPosition);
            }
        }
    }
    
    public void cursorPos(final int n) {
        this.setCursorPosition(this.selectionEnd + n);
    }
}
