package optfine;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;

public class GuiOtherSettingsOF extends GuiScreen implements GuiYesNoCallback
{
    private int lastMouseX;
    private static GameSettings.Options[] enumOptions;
    private GuiScreen prevScreen;
    private GameSettings settings;
    private long mouseStillTime;
    private int lastMouseY;
    protected String title;
    
    @Override
    public void drawScreen(final int lastMouseX, final int lastMouseY, final float n) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, GuiOtherSettingsOF.width / 2, 20, 16777215);
        super.drawScreen(lastMouseX, lastMouseY, n);
        if (Math.abs(lastMouseX - this.lastMouseX) <= 5 && Math.abs(lastMouseY - this.lastMouseY) <= 5) {
            if (System.currentTimeMillis() >= this.mouseStillTime + 700) {
                final int n2 = GuiOtherSettingsOF.width / 2 - 150;
                int n3 = GuiOtherSettingsOF.height / 6 - 5;
                if (lastMouseY <= n3 + 98) {
                    n3 += 105;
                }
                final int n4 = n2 + 150 + 150;
                final int n5 = n3 + 84 + 10;
                final GuiButton selectedButton = this.getSelectedButton(lastMouseX, lastMouseY);
                if (selectedButton != null) {
                    final String[] tooltipLines = this.getTooltipLines(this.getButtonName(selectedButton.displayString));
                    if (tooltipLines == null) {
                        return;
                    }
                    this.drawGradientRect(n2, n3, n4, n5, -536870912, -536870912);
                    while (0 < tooltipLines.length) {
                        this.fontRendererObj.drawStringWithShadow(tooltipLines[0], (float)(n2 + 5), (float)(n3 + 5 + 0), 14540253);
                        int n6 = 0;
                        ++n6;
                    }
                }
            }
        }
        else {
            this.lastMouseX = lastMouseX;
            this.lastMouseY = lastMouseY;
            this.mouseStillTime = System.currentTimeMillis();
        }
    }
    
    private String getButtonName(final String s) {
        final int index = s.indexOf(58);
        return (index < 0) ? s : s.substring(0, index);
    }
    
    @Override
    public void initGui() {
        final GameSettings.Options[] enumOptions = GuiOtherSettingsOF.enumOptions;
        while (0 < enumOptions.length) {
            final GameSettings.Options options = enumOptions[0];
            final int n = GuiOtherSettingsOF.width / 2 - 155 + 0;
            final int n2 = GuiOtherSettingsOF.height / 6 + 0 - 10;
            if (!options.getEnumFloat()) {
                this.buttonList.add(new GuiOptionButton(options.returnEnumOrdinal(), n, n2, options, this.settings.getKeyBinding(options)));
            }
            else {
                this.buttonList.add(new GuiOptionSlider(options.returnEnumOrdinal(), n, n2, options));
            }
            int n3 = 0;
            ++n3;
            int n4 = 0;
            ++n4;
        }
        this.buttonList.add(new GuiButton(210, GuiOtherSettingsOF.width / 2 - 100, GuiOtherSettingsOF.height / 6 + 168 + 11 - 44, "Reset Video Settings..."));
        this.buttonList.add(new GuiButton(200, GuiOtherSettingsOF.width / 2 - 100, GuiOtherSettingsOF.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
    }
    
    static {
        GuiOtherSettingsOF.enumOptions = new GameSettings.Options[] { GameSettings.Options.LAGOMETER, GameSettings.Options.PROFILER, GameSettings.Options.WEATHER, GameSettings.Options.TIME, GameSettings.Options.USE_FULLSCREEN, GameSettings.Options.FULLSCREEN_MODE, GameSettings.Options.SHOW_FPS, GameSettings.Options.AUTOSAVE_TICKS };
    }
    
    public GuiOtherSettingsOF(final GuiScreen prevScreen, final GameSettings settings) {
        this.title = "Other Settings";
        this.lastMouseX = 0;
        this.lastMouseY = 0;
        this.mouseStillTime = 0L;
        this.prevScreen = prevScreen;
        this.settings = settings;
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id < 200 && guiButton instanceof GuiOptionButton) {
                this.settings.setOptionValue(((GuiOptionButton)guiButton).returnEnumOptions(), 1);
                guiButton.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(guiButton.id));
            }
            if (guiButton.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.prevScreen);
            }
            if (guiButton.id == 210) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiYesNo(this, "Reset all video settings to their default values?", "", 9999));
            }
            if (guiButton.id != GameSettings.Options.CLOUD_HEIGHT.ordinal()) {
                final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
                this.setWorldAndResolution(this.mc, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
            }
        }
    }
    
    @Override
    public void confirmClicked(final boolean b, final int n) {
        if (b) {
            this.mc.gameSettings.resetSettings();
        }
        this.mc.displayGuiScreen(this);
    }
    
    private GuiButton getSelectedButton(final int n, final int n2) {
        while (0 < this.buttonList.size()) {
            final GuiButton guiButton = this.buttonList.get(0);
            final int buttonWidth = GuiVideoSettings.getButtonWidth(guiButton);
            final int buttonHeight = GuiVideoSettings.getButtonHeight(guiButton);
            if (n >= guiButton.xPosition && n2 >= guiButton.yPosition && n < guiButton.xPosition + buttonWidth && n2 < guiButton.yPosition + buttonHeight) {
                return guiButton;
            }
            int n3 = 0;
            ++n3;
        }
        return null;
    }
    
    private String[] getTooltipLines(final String s) {
        return (String[])(s.equals("Autosave") ? new String[] { "Autosave interval", "Default autosave interval (2s) is NOT RECOMMENDED.", "Autosave causes the famous Lag Spike of Death." } : (s.equals("Lagometer") ? new String[] { "Shows the lagometer on the debug screen (F3).", "* Orange - Memory garbage collection", "* Cyan - Tick", "* Blue - Scheduled executables", "* Purple - Chunk upload", "* Red - Chunk updates", "* Yellow - Visibility check", "* Green - Render terrain" } : (s.equals("Debug Profiler") ? new String[] { "Debug Profiler", "  ON - debug profiler is active, slower", "  OFF - debug profiler is not active, faster", "The debug profiler collects and shows debug information", "when the debug screen is open (F3)" } : (s.equals("Time") ? new String[] { "Time", " Default - normal day/night cycles", " Day Only - day only", " Night Only - night only", "The time setting is only effective in CREATIVE mode", "and for local worlds." } : (s.equals("Weather") ? new String[] { "Weather", "  ON - weather is active, slower", "  OFF - weather is not active, faster", "The weather controls rain, snow and thunderstorms.", "Weather control is only possible for local worlds." } : (s.equals("Fullscreen") ? new String[] { "Fullscreen", "  ON - use fullscreen mode", "  OFF - use window mode", "Fullscreen mode may be faster or slower than", "window mode, depending on the graphics card." } : (s.equals("Fullscreen Mode") ? new String[] { "Fullscreen mode", "  Default - use desktop screen resolution, slower", "  WxH - use custom screen resolution, may be faster", "The selected resolution is used in fullscreen mode (F11).", "Lower resolutions should generally be faster." } : (s.equals("3D Anaglyph") ? new String[] { "3D mode used with red-cyan 3D glasses." } : (s.equals("Show FPS") ? new String[] { "Shows compact FPS and render information", "  C: - chunk renderers", "  E: - rendered entities + block entities", "  U: - chunk updates", "The compact FPS information is only shown when the", "debug screen is not visible." } : null)))))))));
    }
}
