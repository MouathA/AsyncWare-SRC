package optfine;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;

public class GuiDetailSettingsOF extends GuiScreen
{
    private int lastMouseX;
    private GameSettings settings;
    private static GameSettings.Options[] enumOptions;
    private int lastMouseY;
    private GuiScreen prevScreen;
    private long mouseStillTime;
    protected String title;
    
    private String getButtonName(final String s) {
        final int index = s.indexOf(58);
        return (index < 0) ? s : s.substring(0, index);
    }
    
    @Override
    public void initGui() {
        final GameSettings.Options[] enumOptions = GuiDetailSettingsOF.enumOptions;
        while (0 < enumOptions.length) {
            final GameSettings.Options options = enumOptions[0];
            final int n = GuiDetailSettingsOF.width / 2 - 155 + 0;
            final int n2 = GuiDetailSettingsOF.height / 6 + 0 - 10;
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
        this.buttonList.add(new GuiButton(200, GuiDetailSettingsOF.width / 2 - 100, GuiDetailSettingsOF.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    public void drawScreen(final int lastMouseX, final int lastMouseY, final float n) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, GuiDetailSettingsOF.width / 2, 20, 16777215);
        super.drawScreen(lastMouseX, lastMouseY, n);
        if (Math.abs(lastMouseX - this.lastMouseX) <= 5 && Math.abs(lastMouseY - this.lastMouseY) <= 5) {
            if (System.currentTimeMillis() >= this.mouseStillTime + 700) {
                final int n2 = GuiDetailSettingsOF.width / 2 - 150;
                int n3 = GuiDetailSettingsOF.height / 6 - 5;
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
    
    static {
        GuiDetailSettingsOF.enumOptions = new GameSettings.Options[] { GameSettings.Options.CLOUDS, GameSettings.Options.CLOUD_HEIGHT, GameSettings.Options.TREES, GameSettings.Options.RAIN, GameSettings.Options.SKY, GameSettings.Options.STARS, GameSettings.Options.SUN_MOON, GameSettings.Options.SHOW_CAPES, GameSettings.Options.TRANSLUCENT_BLOCKS, GameSettings.Options.HELD_ITEM_TOOLTIPS, GameSettings.Options.DROPPED_ITEMS, GameSettings.Options.ENTITY_SHADOWS, GameSettings.Options.VIGNETTE };
    }
    
    private String[] getTooltipLines(final String s) {
        return (String[])(s.equals("Clouds") ? new String[] { "Clouds", "  Default - as set by setting Graphics", "  Fast - lower quality, faster", "  Fancy - higher quality, slower", "  OFF - no clouds, fastest", "Fast clouds are rendered 2D.", "Fancy clouds are rendered 3D." } : (s.equals("Cloud Height") ? new String[] { "Cloud Height", "  OFF - default height", "  100% - above world height limit" } : (s.equals("Trees") ? new String[] { "Trees", "  Default - as set by setting Graphics", "  Fast - lower quality, faster", "  Fancy - higher quality, slower", "Fast trees have opaque leaves.", "Fancy trees have transparent leaves." } : (s.equals("Grass") ? new String[] { "Grass", "  Default - as set by setting Graphics", "  Fast - lower quality, faster", "  Fancy - higher quality, slower", "Fast grass uses default side texture.", "Fancy grass uses biome side texture." } : (s.equals("Dropped Items") ? new String[] { "Dropped Items", "  Default - as set by setting Graphics", "  Fast - 2D dropped items, faster", "  Fancy - 3D dropped items, slower" } : (s.equals("Water") ? new String[] { "Water", "  Default - as set by setting Graphics", "  Fast  - lower quality, faster", "  Fancy - higher quality, slower", "Fast water (1 pass) has some visual artifacts", "Fancy water (2 pass) has no visual artifacts" } : (s.equals("Rain & Snow") ? new String[] { "Rain & Snow", "  Default - as set by setting Graphics", "  Fast  - light rain/snow, faster", "  Fancy - heavy rain/snow, slower", "  OFF - no rain/snow, fastest", "When rain is OFF the splashes and rain sounds", "are still active." } : (s.equals("Sky") ? new String[] { "Sky", "  ON - sky is visible, slower", "  OFF  - sky is not visible, faster", "When sky is OFF the moon and sun are still visible." } : (s.equals("Sun & Moon") ? new String[] { "Sun & Moon", "  ON - sun and moon are visible (default)", "  OFF  - sun and moon are not visible (faster)" } : (s.equals("Stars") ? new String[] { "Stars", "  ON - stars are visible, slower", "  OFF  - stars are not visible, faster" } : (s.equals("Depth Fog") ? new String[] { "Depth Fog", "  ON - fog moves closer at bedrock levels (default)", "  OFF - same fog at all levels" } : (s.equals("Show Capes") ? new String[] { "Show Capes", "  ON - show player capes (default)", "  OFF - do not show player capes" } : (s.equals("Held Item Tooltips") ? new String[] { "Held item tooltips", "  ON - show tooltips for held items (default)", "  OFF - do not show tooltips for held items" } : (s.equals("Translucent Blocks") ? new String[] { "Translucent Blocks", "  Fancy - correct color blending (default)", "  Fast - fast color blending (faster)", "Controls the color blending of translucent blocks", "with different color (stained glass, water, ice)", "when placed behind each other with air between them." } : (s.equals("Vignette") ? new String[] { "Visual effect which slightly darkens the screen corners", "  Default - as set by the setting Graphics (default)", "  Fast - vignette disabled (faster)", "  Fancy - vignette enabled (slower)", "The vignette may have a significant effect on the FPS,", "especially when playing fullscreen.", "The vignette effect is very subtle and can safely", "be disabled" } : null)))))))))))))));
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
            if (guiButton.id != GameSettings.Options.CLOUD_HEIGHT.ordinal()) {
                final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
                this.setWorldAndResolution(this.mc, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
            }
        }
    }
    
    public GuiDetailSettingsOF(final GuiScreen prevScreen, final GameSettings settings) {
        this.title = "Detail Settings";
        this.lastMouseX = 0;
        this.lastMouseY = 0;
        this.mouseStillTime = 0L;
        this.prevScreen = prevScreen;
        this.settings = settings;
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
}
