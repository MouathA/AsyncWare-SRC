package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.client.resources.*;

public class GuiListButton extends GuiButton
{
    private boolean field_175216_o;
    private String localizationStr;
    private final GuiPageButtonList.GuiResponder guiResponder;
    
    public void func_175212_b(final boolean field_175216_o) {
        this.field_175216_o = field_175216_o;
        this.displayString = this.buildDisplayString();
        this.guiResponder.func_175321_a(this.id, field_175216_o);
    }
    
    @Override
    public boolean mousePressed(final Minecraft minecraft, final int n, final int n2) {
        if (super.mousePressed(minecraft, n, n2)) {
            this.field_175216_o = !this.field_175216_o;
            this.displayString = this.buildDisplayString();
            this.guiResponder.func_175321_a(this.id, this.field_175216_o);
            return true;
        }
        return false;
    }
    
    public GuiListButton(final GuiPageButtonList.GuiResponder guiResponder, final int n, final int n2, final int n3, final String localizationStr, final boolean field_175216_o) {
        super(n, n2, n3, 150, 20, "");
        this.localizationStr = localizationStr;
        this.field_175216_o = field_175216_o;
        this.displayString = this.buildDisplayString();
        this.guiResponder = guiResponder;
    }
    
    private String buildDisplayString() {
        return I18n.format(this.localizationStr, new Object[0]) + ": " + (this.field_175216_o ? I18n.format("gui.yes", new Object[0]) : I18n.format("gui.no", new Object[0]));
    }
}
