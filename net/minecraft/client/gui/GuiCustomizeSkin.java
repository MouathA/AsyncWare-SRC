package net.minecraft.client.gui;

import net.minecraft.entity.player.*;
import java.io.*;
import net.minecraft.client.resources.*;

public class GuiCustomizeSkin extends GuiScreen
{
    private String title;
    private final GuiScreen parentScreen;
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.parentScreen);
            }
            else if (guiButton instanceof ButtonPart) {
                final EnumPlayerModelParts access$100 = ButtonPart.access$100((ButtonPart)guiButton);
                this.mc.gameSettings.switchModelPartEnabled(access$100);
                guiButton.displayString = this.func_175358_a(access$100);
            }
        }
    }
    
    public GuiCustomizeSkin(final GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, GuiCustomizeSkin.width / 2, 20, 16777215);
        super.drawScreen(n, n2, n3);
    }
    
    static String access$200(final GuiCustomizeSkin guiCustomizeSkin, final EnumPlayerModelParts enumPlayerModelParts) {
        return guiCustomizeSkin.func_175358_a(enumPlayerModelParts);
    }
    
    private String func_175358_a(final EnumPlayerModelParts enumPlayerModelParts) {
        String s;
        if (this.mc.gameSettings.getModelParts().contains(enumPlayerModelParts)) {
            s = I18n.format("options.on", new Object[0]);
        }
        else {
            s = I18n.format("options.off", new Object[0]);
        }
        return enumPlayerModelParts.func_179326_d().getFormattedText() + ": " + s;
    }
    
    @Override
    public void initGui() {
        this.title = I18n.format("options.skinCustomisation.title", new Object[0]);
        final EnumPlayerModelParts[] values = EnumPlayerModelParts.values();
        while (0 < values.length) {
            final EnumPlayerModelParts enumPlayerModelParts = values[0];
            this.buttonList.add(new ButtonPart(enumPlayerModelParts.getPartId(), GuiCustomizeSkin.width / 2 - 155 + 0, GuiCustomizeSkin.height / 6 + 0, 150, 20, enumPlayerModelParts, null));
            int n = 0;
            ++n;
            int n2 = 0;
            ++n2;
        }
        this.buttonList.add(new GuiButton(200, GuiCustomizeSkin.width / 2 - 100, GuiCustomizeSkin.height / 6 + 0, I18n.format("gui.done", new Object[0])));
    }
    
    class ButtonPart extends GuiButton
    {
        final GuiCustomizeSkin this$0;
        private final EnumPlayerModelParts playerModelParts;
        
        static EnumPlayerModelParts access$100(final ButtonPart buttonPart) {
            return buttonPart.playerModelParts;
        }
        
        ButtonPart(final GuiCustomizeSkin guiCustomizeSkin, final int n, final int n2, final int n3, final int n4, final int n5, final EnumPlayerModelParts enumPlayerModelParts, final GuiCustomizeSkin$1 object) {
            this(guiCustomizeSkin, n, n2, n3, n4, n5, enumPlayerModelParts);
        }
        
        private ButtonPart(final GuiCustomizeSkin this$0, final int n, final int n2, final int n3, final int n4, final int n5, final EnumPlayerModelParts playerModelParts) {
            this.this$0 = this$0;
            super(n, n2, n3, n4, n5, GuiCustomizeSkin.access$200(this$0, playerModelParts));
            this.playerModelParts = playerModelParts;
        }
    }
}
