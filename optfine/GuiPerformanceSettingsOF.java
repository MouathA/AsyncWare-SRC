package optfine;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;

public class GuiPerformanceSettingsOF extends GuiScreen
{
    private GameSettings settings;
    private GuiScreen prevScreen;
    private int lastMouseY;
    private int lastMouseX;
    protected String title;
    private static GameSettings.Options[] enumOptions;
    private long mouseStillTime;
    
    public GuiPerformanceSettingsOF(final GuiScreen prevScreen, final GameSettings settings) {
        this.title = "Performance Settings";
        this.lastMouseX = 0;
        this.lastMouseY = 0;
        this.mouseStillTime = 0L;
        this.prevScreen = prevScreen;
        this.settings = settings;
    }
    
    private String[] getTooltipLines(final String s) {
        return (String[])(s.equals("Smooth FPS") ? new String[] { "Stabilizes FPS by flushing the graphic driver buffers", "  OFF - no stabilization, FPS may fluctuate", "  ON - FPS stabilization", "This option is graphics driver dependant and its effect", "is not always visible" } : (s.equals("Smooth World") ? new String[] { "Removes lag spikes caused by the internal server.", "  OFF - no stabilization, FPS may fluctuate", "  ON - FPS stabilization", "Stabilizes FPS by distributing the internal server load.", "Effective only for local worlds (single player)." } : (s.equals("Load Far") ? new String[] { "Loads the world chunks at distance Far.", "Switching the render distance does not cause all chunks ", "to be loaded again.", "  OFF - world chunks loaded up to render distance", "  ON - world chunks loaded at distance Far, allows", "       fast render distance switching" } : (s.equals("Preloaded Chunks") ? new String[] { "Defines an area in which no chunks will be loaded", "  OFF - after 5m new chunks will be loaded", "  2 - after 32m  new chunks will be loaded", "  8 - after 128m new chunks will be loaded", "Higher values need more time to load all the chunks", "and may decrease the FPS." } : (s.equals("Chunk Updates") ? new String[] { "Chunk updates", " 1 - slower world loading, higher FPS (default)", " 3 - faster world loading, lower FPS", " 5 - fastest world loading, lowest FPS", "Number of chunk updates per rendered frame,", "higher values may destabilize the framerate." } : (s.equals("Dynamic Updates") ? new String[] { "Dynamic chunk updates", " OFF - (default) standard chunk updates per frame", " ON - more updates while the player is standing still", "Dynamic updates force more chunk updates while", "the player is standing still to load the world faster." } : (s.equals("Lazy Chunk Loading") ? new String[] { "Lazy Chunk Loading", " OFF - default server chunk loading", " ON - lazy server chunk loading (smoother)", "Smooths the integrated server chunk loading by", "distributing the chunks over several ticks.", "Turn it OFF if parts of the world do not load correctly.", "Effective only for local worlds and single-core CPU." } : (s.equals("Fast Math") ? new String[] { "Fast Math", " OFF - standard math (default)", " ON - faster math", "Uses optimized sin() and cos() functions which can", "better utilize the CPU cache and increase the FPS." } : (s.equals("Fast Render") ? new String[] { "Fast Render", " OFF - standard rendering (default)", " ON - optimized rendering (faster)", "Uses optimized rendering algorithm which decreases", "the GPU load and may substantionally increase the FPS." } : null)))))))));
    }
    
    @Override
    public void initGui() {
        final GameSettings.Options[] enumOptions = GuiPerformanceSettingsOF.enumOptions;
        while (0 < enumOptions.length) {
            final GameSettings.Options options = enumOptions[0];
            final int n = GuiPerformanceSettingsOF.width / 2 - 155 + 0;
            final int n2 = GuiPerformanceSettingsOF.height / 6 + 0 - 10;
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
        this.buttonList.add(new GuiButton(200, GuiPerformanceSettingsOF.width / 2 - 100, GuiPerformanceSettingsOF.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    public void drawScreen(final int lastMouseX, final int lastMouseY, final float n) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, GuiPerformanceSettingsOF.width / 2, 20, 16777215);
        super.drawScreen(lastMouseX, lastMouseY, n);
        if (Math.abs(lastMouseX - this.lastMouseX) <= 5 && Math.abs(lastMouseY - this.lastMouseY) <= 5) {
            if (System.currentTimeMillis() >= this.mouseStillTime + 700) {
                final int n2 = GuiPerformanceSettingsOF.width / 2 - 150;
                int n3 = GuiPerformanceSettingsOF.height / 6 - 5;
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
    
    private String getButtonName(final String s) {
        final int index = s.indexOf(58);
        return (index < 0) ? s : s.substring(0, index);
    }
    
    static {
        GuiPerformanceSettingsOF.enumOptions = new GameSettings.Options[] { GameSettings.Options.SMOOTH_FPS, GameSettings.Options.SMOOTH_WORLD, GameSettings.Options.FAST_RENDER, GameSettings.Options.FAST_MATH, GameSettings.Options.CHUNK_UPDATES, GameSettings.Options.CHUNK_UPDATES_DYNAMIC, GameSettings.Options.LAZY_CHUNK_LOADING };
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
}
