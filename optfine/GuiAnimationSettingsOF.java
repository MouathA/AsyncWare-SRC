package optfine;

import net.minecraft.client.settings.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.*;

public class GuiAnimationSettingsOF extends GuiScreen
{
    protected String title;
    private GuiScreen prevScreen;
    private GameSettings settings;
    private static GameSettings.Options[] enumOptions;
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, GuiAnimationSettingsOF.width / 2, 20, 16777215);
        super.drawScreen(n, n2, n3);
    }
    
    public GuiAnimationSettingsOF(final GuiScreen prevScreen, final GameSettings settings) {
        this.title = "Animation Settings";
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
                this.mc.gameSettings.setAllAnimations(true);
            }
            if (guiButton.id == 211) {
                this.mc.gameSettings.setAllAnimations(false);
            }
            if (guiButton.id != GameSettings.Options.CLOUD_HEIGHT.ordinal()) {
                final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
                this.setWorldAndResolution(this.mc, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
            }
        }
    }
    
    static {
        GuiAnimationSettingsOF.enumOptions = new GameSettings.Options[] { GameSettings.Options.ANIMATED_WATER, GameSettings.Options.ANIMATED_LAVA, GameSettings.Options.ANIMATED_FIRE, GameSettings.Options.ANIMATED_PORTAL, GameSettings.Options.ANIMATED_REDSTONE, GameSettings.Options.ANIMATED_EXPLOSION, GameSettings.Options.ANIMATED_FLAME, GameSettings.Options.ANIMATED_SMOKE, GameSettings.Options.VOID_PARTICLES, GameSettings.Options.WATER_PARTICLES, GameSettings.Options.RAIN_SPLASH, GameSettings.Options.PORTAL_PARTICLES, GameSettings.Options.POTION_PARTICLES, GameSettings.Options.DRIPPING_WATER_LAVA, GameSettings.Options.ANIMATED_TERRAIN, GameSettings.Options.ANIMATED_TEXTURES, GameSettings.Options.FIREWORK_PARTICLES, GameSettings.Options.PARTICLES };
    }
    
    @Override
    public void initGui() {
        final GameSettings.Options[] enumOptions = GuiAnimationSettingsOF.enumOptions;
        while (0 < enumOptions.length) {
            final GameSettings.Options options = enumOptions[0];
            final int n = GuiAnimationSettingsOF.width / 2 - 155 + 0;
            final int n2 = GuiAnimationSettingsOF.height / 6 + 0 - 10;
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
        this.buttonList.add(new GuiButton(210, GuiAnimationSettingsOF.width / 2 - 155, GuiAnimationSettingsOF.height / 6 + 168 + 11, 70, 20, "All ON"));
        this.buttonList.add(new GuiButton(211, GuiAnimationSettingsOF.width / 2 - 155 + 80, GuiAnimationSettingsOF.height / 6 + 168 + 11, 70, 20, "All OFF"));
        this.buttonList.add(new GuiOptionButton(200, GuiAnimationSettingsOF.width / 2 + 5, GuiAnimationSettingsOF.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
    }
}
