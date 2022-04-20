package optfine;

import net.minecraft.client.settings.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.*;

public class GuiQualitySettingsOF extends GuiScreen
{
    private static GameSettings.Options[] enumOptions;
    private long mouseStillTime;
    private int lastMouseX;
    private GameSettings settings;
    private GuiScreen prevScreen;
    protected String title;
    private int lastMouseY;
    
    private String getButtonName(final String s) {
        final int index = s.indexOf(58);
        return (index < 0) ? s : s.substring(0, index);
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
    
    @Override
    public void drawScreen(final int lastMouseX, final int lastMouseY, final float n) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, GuiQualitySettingsOF.width / 2, 20, 16777215);
        super.drawScreen(lastMouseX, lastMouseY, n);
        if (Math.abs(lastMouseX - this.lastMouseX) <= 5 && Math.abs(lastMouseY - this.lastMouseY) <= 5) {
            if (System.currentTimeMillis() >= this.mouseStillTime + 700) {
                final int n2 = GuiQualitySettingsOF.width / 2 - 150;
                int n3 = GuiQualitySettingsOF.height / 6 - 5;
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
                        final String s = tooltipLines[0];
                        if (s.endsWith("!")) {}
                        this.fontRendererObj.drawStringWithShadow(s, (float)(n2 + 5), (float)(n3 + 5 + 0), 16719904);
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
    
    private String[] getTooltipLines(final String s) {
        return (String[])(s.equals("Mipmap Levels") ? new String[] { "Visual effect which makes distant objects look better", "by smoothing the texture details", "  OFF - no smoothing", "  1 - minimum smoothing", "  4 - maximum smoothing", "This option usually does not affect the performance." } : (s.equals("Mipmap Type") ? new String[] { "Visual effect which makes distant objects look better", "by smoothing the texture details", "  Nearest - rough smoothing (fastest)", "  Linear - normal smoothing", "  Bilinear - fine smoothing", "  Trilinear - finest smoothing (slowest)" } : (s.equals("Anisotropic Filtering") ? new String[] { "Anisotropic Filtering", " OFF - (default) standard texture detail (faster)", " 2-16 - finer details in mipmapped textures (slower)", "The Anisotropic Filtering restores details in", "mipmapped textures.", "When enabled it may substantially decrease the FPS." } : (s.equals("Antialiasing") ? new String[] { "Antialiasing", " OFF - (default) no antialiasing (faster)", " 2-16 - antialiased lines and edges (slower)", "The Antialiasing smooths jagged lines and ", "sharp color transitions.", "When enabled it may substantially decrease the FPS.", "Not all levels are supported by all graphics cards.", "Effective after a RESTART!" } : (s.equals("Clear Water") ? new String[] { "Clear Water", "  ON - clear, transparent water", "  OFF - default water" } : (s.equals("Better Grass") ? new String[] { "Better Grass", "  OFF - default side grass texture, fastest", "  Fast - full side grass texture, slower", "  Fancy - dynamic side grass texture, slowest" } : (s.equals("Better Snow") ? new String[] { "Better Snow", "  OFF - default snow, faster", "  ON - better snow, slower", "Shows snow under transparent blocks (fence, tall grass)", "when bordering with snow blocks" } : (s.equals("Random Mobs") ? new String[] { "Random Mobs", "  OFF - no random mobs, faster", "  ON - random mobs, slower", "Random mobs uses random textures for the game creatures.", "It needs a texture pack which has multiple mob textures." } : (s.equals("Swamp Colors") ? new String[] { "Swamp Colors", "  ON - use swamp colors (default), slower", "  OFF - do not use swamp colors, faster", "The swamp colors affect grass, leaves, vines and water." } : (s.equals("Smooth Biomes") ? new String[] { "Smooth Biomes", "  ON - smoothing of biome borders (default), slower", "  OFF - no smoothing of biome borders, faster", "The smoothing of biome borders is done by sampling and", "averaging the color of all surrounding blocks.", "Affected are grass, leaves, vines and water." } : (s.equals("Custom Fonts") ? new String[] { "Custom Fonts", "  ON - uses custom fonts (default), slower", "  OFF - uses default font, faster", "The custom fonts are supplied by the current", "texture pack" } : (s.equals("Custom Colors") ? new String[] { "Custom Colors", "  ON - uses custom colors (default), slower", "  OFF - uses default colors, faster", "The custom colors are supplied by the current", "texture pack" } : (s.equals("Show Capes") ? new String[] { "Show Capes", "  ON - show player capes (default)", "  OFF - do not show player capes" } : (s.equals("Connected Textures") ? new String[] { "Connected Textures", "  OFF - no connected textures (default)", "  Fast - fast connected textures", "  Fancy - fancy connected textures", "Connected textures joins the textures of glass,", "sandstone and bookshelves when placed next to", "each other. The connected textures are supplied", "by the current texture pack." } : (s.equals("Far View") ? new String[] { "Far View", " OFF - (default) standard view distance", " ON - 3x view distance", "Far View is very resource demanding!", "3x view distance => 9x chunks to be loaded => FPS / 9", "Standard view distances: 32, 64, 128, 256", "Far view distances: 96, 192, 384, 512" } : (s.equals("Natural Textures") ? new String[] { "Natural Textures", "  OFF - no natural textures (default)", "  ON - use natural textures", "Natural textures remove the gridlike pattern", "created by repeating blocks of the same type.", "It uses rotated and flipped variants of the base", "block texture. The configuration for the natural", "textures is supplied by the current texture pack" } : (s.equals("Custom Sky") ? new String[] { "Custom Sky", "  ON - custom sky textures (default), slow", "  OFF - default sky, faster", "The custom sky textures are supplied by the current", "texture pack" } : null)))))))))))))))));
    }
    
    public GuiQualitySettingsOF(final GuiScreen prevScreen, final GameSettings settings) {
        this.title = "Quality Settings";
        this.lastMouseX = 0;
        this.lastMouseY = 0;
        this.mouseStillTime = 0L;
        this.prevScreen = prevScreen;
        this.settings = settings;
    }
    
    static {
        GuiQualitySettingsOF.enumOptions = new GameSettings.Options[] { GameSettings.Options.MIPMAP_LEVELS, GameSettings.Options.MIPMAP_TYPE, GameSettings.Options.AF_LEVEL, GameSettings.Options.AA_LEVEL, GameSettings.Options.CLEAR_WATER, GameSettings.Options.RANDOM_MOBS, GameSettings.Options.BETTER_GRASS, GameSettings.Options.BETTER_SNOW, GameSettings.Options.CUSTOM_FONTS, GameSettings.Options.CUSTOM_COLORS, GameSettings.Options.SWAMP_COLORS, GameSettings.Options.SMOOTH_BIOMES, GameSettings.Options.CONNECTED_TEXTURES, GameSettings.Options.NATURAL_TEXTURES, GameSettings.Options.CUSTOM_SKY };
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
    
    @Override
    public void initGui() {
        final GameSettings.Options[] enumOptions = GuiQualitySettingsOF.enumOptions;
        while (0 < enumOptions.length) {
            final GameSettings.Options options = enumOptions[0];
            final int n = GuiQualitySettingsOF.width / 2 - 155 + 0;
            final int n2 = GuiQualitySettingsOF.height / 6 + 0 - 10;
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
        this.buttonList.add(new GuiButton(200, GuiQualitySettingsOF.width / 2 - 100, GuiQualitySettingsOF.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
    }
}
