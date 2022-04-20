package net.minecraft.client.gui;

import com.google.common.collect.*;
import java.io.*;
import java.util.*;
import net.minecraft.client.resources.*;

public class GuiYesNo extends GuiScreen
{
    protected String confirmButtonText;
    protected int parentButtonClickedId;
    private String messageLine2;
    private final List field_175298_s;
    protected String cancelButtonText;
    private int ticksUntilEnable;
    protected String messageLine1;
    protected GuiYesNoCallback parentScreen;
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        final int ticksUntilEnable = this.ticksUntilEnable - 1;
        this.ticksUntilEnable = ticksUntilEnable;
        if (ticksUntilEnable == 0) {
            final Iterator<GuiButton> iterator = (Iterator<GuiButton>)this.buttonList.iterator();
            while (iterator.hasNext()) {
                iterator.next().enabled = true;
            }
        }
    }
    
    public GuiYesNo(final GuiYesNoCallback parentScreen, final String messageLine1, final String messageLine2, final String confirmButtonText, final String cancelButtonText, final int parentButtonClickedId) {
        this.field_175298_s = Lists.newArrayList();
        this.parentScreen = parentScreen;
        this.messageLine1 = messageLine1;
        this.messageLine2 = messageLine2;
        this.confirmButtonText = confirmButtonText;
        this.cancelButtonText = cancelButtonText;
        this.parentButtonClickedId = parentButtonClickedId;
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        this.parentScreen.confirmClicked(guiButton.id == 0, this.parentButtonClickedId);
    }
    
    public void setButtonDelay(final int ticksUntilEnable) {
        this.ticksUntilEnable = ticksUntilEnable;
        final Iterator<GuiButton> iterator = this.buttonList.iterator();
        while (iterator.hasNext()) {
            iterator.next().enabled = false;
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.messageLine1, GuiYesNo.width / 2, 70, 16777215);
        final Iterator<String> iterator = this.field_175298_s.iterator();
        while (iterator.hasNext()) {
            this.drawCenteredString(this.fontRendererObj, iterator.next(), GuiYesNo.width / 2, 90, 16777215);
            final int n4 = 90 + this.fontRendererObj.FONT_HEIGHT;
        }
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    public void initGui() {
        this.buttonList.add(new GuiOptionButton(0, GuiYesNo.width / 2 - 155, GuiYesNo.height / 6 + 96, this.confirmButtonText));
        this.buttonList.add(new GuiOptionButton(1, GuiYesNo.width / 2 - 155 + 160, GuiYesNo.height / 6 + 96, this.cancelButtonText));
        this.field_175298_s.clear();
        this.field_175298_s.addAll(this.fontRendererObj.listFormattedStringToWidth(this.messageLine2, GuiYesNo.width - 50));
    }
    
    public GuiYesNo(final GuiYesNoCallback parentScreen, final String messageLine1, final String messageLine2, final int parentButtonClickedId) {
        this.field_175298_s = Lists.newArrayList();
        this.parentScreen = parentScreen;
        this.messageLine1 = messageLine1;
        this.messageLine2 = messageLine2;
        this.parentButtonClickedId = parentButtonClickedId;
        this.confirmButtonText = I18n.format("gui.yes", new Object[0]);
        this.cancelButtonText = I18n.format("gui.no", new Object[0]);
    }
}
