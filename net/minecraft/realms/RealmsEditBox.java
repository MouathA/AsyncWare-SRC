package net.minecraft.realms;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;

public class RealmsEditBox
{
    private final GuiTextField editBox;
    
    public void render() {
        this.editBox.drawTextBox();
    }
    
    public void mouseClicked(final int n, final int n2, final int n3) {
        this.editBox.mouseClicked(n, n2, n3);
    }
    
    public void setIsEditable(final boolean enabled) {
        this.editBox.setEnabled(enabled);
    }
    
    public void tick() {
        this.editBox.updateCursorCounter();
    }
    
    public boolean isFocused() {
        return this.editBox.isFocused();
    }
    
    public String getValue() {
        return this.editBox.getText();
    }
    
    public void setFocus(final boolean focused) {
        this.editBox.setFocused(focused);
    }
    
    public void keyPressed(final char c, final int n) {
        this.editBox.textboxKeyTyped(c, n);
    }
    
    public void setValue(final String text) {
        this.editBox.setText(text);
    }
    
    public void setMaxLength(final int maxStringLength) {
        this.editBox.setMaxStringLength(maxStringLength);
    }
    
    public RealmsEditBox(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.editBox = new GuiTextField(n, Minecraft.getMinecraft().fontRendererObj, n2, n3, n4, n5);
    }
}
