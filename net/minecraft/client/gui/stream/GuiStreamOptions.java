package net.minecraft.client.gui.stream;

import net.minecraft.client.settings.*;
import net.minecraft.client.gui.*;
import java.io.*;
import net.minecraft.util.*;
import net.minecraft.client.resources.*;

public class GuiStreamOptions extends GuiScreen
{
    private final GuiScreen parentScreen;
    private static final GameSettings.Options[] field_152312_a;
    private String field_152319_i;
    private String field_152313_r;
    private static final GameSettings.Options[] field_152316_f;
    private boolean field_152315_t;
    private int field_152314_s;
    private final GameSettings field_152318_h;
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id < 100 && guiButton instanceof GuiOptionButton) {
                final GameSettings.Options returnEnumOptions = ((GuiOptionButton)guiButton).returnEnumOptions();
                this.field_152318_h.setOptionValue(returnEnumOptions, 1);
                guiButton.displayString = this.field_152318_h.getKeyBinding(GameSettings.Options.getEnumOptions(guiButton.id));
                if (this.mc.getTwitchStream().isBroadcasting() && returnEnumOptions != GameSettings.Options.STREAM_CHAT_ENABLED && returnEnumOptions != GameSettings.Options.STREAM_CHAT_USER_FILTER) {
                    this.field_152315_t = true;
                }
            }
            else if (guiButton instanceof GuiOptionSlider) {
                if (guiButton.id == GameSettings.Options.STREAM_VOLUME_MIC.returnEnumOrdinal()) {
                    this.mc.getTwitchStream().updateStreamVolume();
                }
                else if (guiButton.id == GameSettings.Options.STREAM_VOLUME_SYSTEM.returnEnumOrdinal()) {
                    this.mc.getTwitchStream().updateStreamVolume();
                }
                else if (this.mc.getTwitchStream().isBroadcasting()) {
                    this.field_152315_t = true;
                }
            }
            if (guiButton.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.parentScreen);
            }
            else if (guiButton.id == 201) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiIngestServers(this));
            }
        }
    }
    
    public GuiStreamOptions(final GuiScreen parentScreen, final GameSettings field_152318_h) {
        this.field_152315_t = false;
        this.parentScreen = parentScreen;
        this.field_152318_h = field_152318_h;
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.field_152319_i, GuiStreamOptions.width / 2, 20, 16777215);
        this.drawCenteredString(this.fontRendererObj, this.field_152313_r, GuiStreamOptions.width / 2, this.field_152314_s, 16777215);
        if (this.field_152315_t) {
            this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.RED + I18n.format("options.stream.changes", new Object[0]), GuiStreamOptions.width / 2, 20 + this.fontRendererObj.FONT_HEIGHT, 16777215);
        }
        super.drawScreen(n, n2, n3);
    }
    
    static {
        field_152312_a = new GameSettings.Options[] { GameSettings.Options.STREAM_BYTES_PER_PIXEL, GameSettings.Options.STREAM_FPS, GameSettings.Options.STREAM_KBPS, GameSettings.Options.STREAM_SEND_METADATA, GameSettings.Options.STREAM_VOLUME_MIC, GameSettings.Options.STREAM_VOLUME_SYSTEM, GameSettings.Options.STREAM_MIC_TOGGLE_BEHAVIOR, GameSettings.Options.STREAM_COMPRESSION };
        field_152316_f = new GameSettings.Options[] { GameSettings.Options.STREAM_CHAT_ENABLED, GameSettings.Options.STREAM_CHAT_USER_FILTER };
    }
    
    @Override
    public void initGui() {
        this.field_152319_i = I18n.format("options.stream.title", new Object[0]);
        this.field_152313_r = I18n.format("options.stream.chat.title", new Object[0]);
        final GameSettings.Options[] field_152312_a = GuiStreamOptions.field_152312_a;
        int n = 0;
        int n2 = 0;
        while (0 < field_152312_a.length) {
            final GameSettings.Options options = field_152312_a[0];
            if (options.getEnumFloat()) {
                this.buttonList.add(new GuiOptionSlider(options.returnEnumOrdinal(), GuiStreamOptions.width / 2 - 155 + 0, GuiStreamOptions.height / 6 + 0, options));
            }
            else {
                this.buttonList.add(new GuiOptionButton(options.returnEnumOrdinal(), GuiStreamOptions.width / 2 - 155 + 0, GuiStreamOptions.height / 6 + 0, options, this.field_152318_h.getKeyBinding(options)));
            }
            ++n;
            ++n2;
        }
        this.field_152314_s = GuiStreamOptions.height / 6 + 0 + 6;
        final GameSettings.Options[] field_152316_f = GuiStreamOptions.field_152316_f;
        while (0 < field_152316_f.length) {
            final GameSettings.Options options2 = field_152316_f[0];
            if (options2.getEnumFloat()) {
                this.buttonList.add(new GuiOptionSlider(options2.returnEnumOrdinal(), GuiStreamOptions.width / 2 - 155 + 0, GuiStreamOptions.height / 6 + 0, options2));
            }
            else {
                this.buttonList.add(new GuiOptionButton(options2.returnEnumOrdinal(), GuiStreamOptions.width / 2 - 155 + 0, GuiStreamOptions.height / 6 + 0, options2, this.field_152318_h.getKeyBinding(options2)));
            }
            ++n;
            ++n2;
        }
        this.buttonList.add(new GuiButton(200, GuiStreamOptions.width / 2 - 155, GuiStreamOptions.height / 6 + 168, 150, 20, I18n.format("gui.done", new Object[0])));
        final GuiButton guiButton = new GuiButton(201, GuiStreamOptions.width / 2 + 5, GuiStreamOptions.height / 6 + 168, 150, 20, I18n.format("options.stream.ingestSelection", new Object[0]));
        guiButton.enabled = ((this.mc.getTwitchStream().isReadyToBroadcast() && this.mc.getTwitchStream().func_152925_v().length > 0) || this.mc.getTwitchStream().func_152908_z());
        this.buttonList.add(guiButton);
    }
}
