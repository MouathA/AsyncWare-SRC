package net.minecraft.client.gui;

import net.minecraft.client.settings.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.stream.*;
import net.minecraft.client.stream.*;
import java.io.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.audio.*;

public class GuiOptions extends GuiScreen implements GuiYesNoCallback
{
    private GuiLockIconButton field_175356_r;
    private static final GameSettings.Options[] field_146440_f;
    private GuiButton field_175357_i;
    private final GameSettings game_settings_1;
    private final GuiScreen field_146441_g;
    protected String field_146442_a;
    
    public GuiOptions(final GuiScreen field_146441_g, final GameSettings game_settings_1) {
        this.field_146442_a = "Options";
        this.field_146441_g = field_146441_g;
        this.game_settings_1 = game_settings_1;
    }
    
    public String func_175355_a(final EnumDifficulty enumDifficulty) {
        final ChatComponentText chatComponentText = new ChatComponentText("");
        chatComponentText.appendSibling(new ChatComponentTranslation("options.difficulty", new Object[0]));
        chatComponentText.appendText(": ");
        chatComponentText.appendSibling(new ChatComponentTranslation(enumDifficulty.getDifficultyResourceKey(), new Object[0]));
        return chatComponentText.getFormattedText();
    }
    
    @Override
    public void confirmClicked(final boolean b, final int n) {
        this.mc.displayGuiScreen(this);
        if (n == 109 && b && this.mc.theWorld != null) {
            this.mc.theWorld.getWorldInfo().setDifficultyLocked(true);
            this.field_175356_r.func_175229_b(true);
            this.field_175356_r.enabled = false;
            this.field_175357_i.enabled = false;
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id < 100 && guiButton instanceof GuiOptionButton) {
                this.game_settings_1.setOptionValue(((GuiOptionButton)guiButton).returnEnumOptions(), 1);
                guiButton.displayString = this.game_settings_1.getKeyBinding(GameSettings.Options.getEnumOptions(guiButton.id));
            }
            if (guiButton.id == 108) {
                this.mc.theWorld.getWorldInfo().setDifficulty(EnumDifficulty.getDifficultyEnum(this.mc.theWorld.getDifficulty().getDifficultyId() + 1));
                this.field_175357_i.displayString = this.func_175355_a(this.mc.theWorld.getDifficulty());
            }
            if (guiButton.id == 109) {
                this.mc.displayGuiScreen(new GuiYesNo(this, new ChatComponentTranslation("difficulty.lock.title", new Object[0]).getFormattedText(), new ChatComponentTranslation("difficulty.lock.question", new Object[] { new ChatComponentTranslation(this.mc.theWorld.getWorldInfo().getDifficulty().getDifficultyResourceKey(), new Object[0]) }).getFormattedText(), 109));
            }
            if (guiButton.id == 110) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiCustomizeSkin(this));
            }
            if (guiButton.id == 8675309) {
                this.mc.entityRenderer.activateNextShader();
            }
            if (guiButton.id == 101) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiVideoSettings(this, this.game_settings_1));
            }
            if (guiButton.id == 100) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiControls(this, this.game_settings_1));
            }
            if (guiButton.id == 102) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiLanguage(this, this.game_settings_1, this.mc.getLanguageManager()));
            }
            if (guiButton.id == 103) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new ScreenChatOptions(this, this.game_settings_1));
            }
            if (guiButton.id == 104) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiSnooper(this, this.game_settings_1));
            }
            if (guiButton.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.field_146441_g);
            }
            if (guiButton.id == 105) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiScreenResourcePacks(this));
            }
            if (guiButton.id == 106) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiScreenOptionsSounds(this, this.game_settings_1));
            }
            if (guiButton.id == 107) {
                this.mc.gameSettings.saveOptions();
                final IStream twitchStream = this.mc.getTwitchStream();
                if (twitchStream.func_152936_l() && twitchStream.func_152928_D()) {
                    this.mc.displayGuiScreen(new GuiStreamOptions(this, this.game_settings_1));
                }
                else {
                    GuiStreamUnavailable.func_152321_a(this);
                }
            }
        }
    }
    
    static {
        field_146440_f = new GameSettings.Options[] { GameSettings.Options.FOV };
    }
    
    @Override
    public void initGui() {
        this.field_146442_a = I18n.format("options.title", new Object[0]);
        final GameSettings.Options[] field_146440_f = GuiOptions.field_146440_f;
        while (0 < field_146440_f.length) {
            final GameSettings.Options options = field_146440_f[0];
            if (options.getEnumFloat()) {
                this.buttonList.add(new GuiOptionSlider(options.returnEnumOrdinal(), GuiOptions.width / 2 - 155 + 0, GuiOptions.height / 6 - 12 + 0, options));
            }
            else {
                this.buttonList.add(new GuiOptionButton(options.returnEnumOrdinal(), GuiOptions.width / 2 - 155 + 0, GuiOptions.height / 6 - 12 + 0, options, this.game_settings_1.getKeyBinding(options)));
            }
            int n = 0;
            ++n;
            int n2 = 0;
            ++n2;
        }
        if (this.mc.theWorld != null) {
            this.field_175357_i = new GuiButton(108, GuiOptions.width / 2 - 155 + 0, GuiOptions.height / 6 - 12 + 0, 150, 20, this.func_175355_a(this.mc.theWorld.getDifficulty()));
            this.buttonList.add(this.field_175357_i);
            if (this.mc.isSingleplayer() && !this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
                this.field_175357_i.setWidth(this.field_175357_i.getButtonWidth() - 20);
                this.field_175356_r = new GuiLockIconButton(109, this.field_175357_i.xPosition + this.field_175357_i.getButtonWidth(), this.field_175357_i.yPosition);
                this.buttonList.add(this.field_175356_r);
                this.field_175356_r.func_175229_b(this.mc.theWorld.getWorldInfo().isDifficultyLocked());
                this.field_175356_r.enabled = !this.field_175356_r.func_175230_c();
                this.field_175357_i.enabled = !this.field_175356_r.func_175230_c();
            }
            else {
                this.field_175357_i.enabled = false;
            }
        }
        this.buttonList.add(new GuiButton(110, GuiOptions.width / 2 - 155, GuiOptions.height / 6 + 48 - 6, 150, 20, I18n.format("options.skinCustomisation", new Object[0])));
        this.buttonList.add(new GuiButton(this, 8675309, GuiOptions.width / 2 + 5, GuiOptions.height / 6 + 48 - 6, 150, 20, "Super Secret Settings...") {
            final GuiOptions this$0;
            
            @Override
            public void playPressSound(final SoundHandler soundHandler) {
                final SoundEventAccessorComposite randomSoundFromCategories = soundHandler.getRandomSoundFromCategories(SoundCategory.ANIMALS, SoundCategory.BLOCKS, SoundCategory.MOBS, SoundCategory.PLAYERS, SoundCategory.WEATHER);
                if (randomSoundFromCategories != null) {
                    soundHandler.playSound(PositionedSoundRecord.create(randomSoundFromCategories.getSoundEventLocation(), 0.5f));
                }
            }
        });
        this.buttonList.add(new GuiButton(106, GuiOptions.width / 2 - 155, GuiOptions.height / 6 + 72 - 6, 150, 20, I18n.format("options.sounds", new Object[0])));
        this.buttonList.add(new GuiButton(107, GuiOptions.width / 2 + 5, GuiOptions.height / 6 + 72 - 6, 150, 20, I18n.format("options.stream", new Object[0])));
        this.buttonList.add(new GuiButton(101, GuiOptions.width / 2 - 155, GuiOptions.height / 6 + 96 - 6, 150, 20, I18n.format("options.video", new Object[0])));
        this.buttonList.add(new GuiButton(100, GuiOptions.width / 2 + 5, GuiOptions.height / 6 + 96 - 6, 150, 20, I18n.format("options.controls", new Object[0])));
        this.buttonList.add(new GuiButton(102, GuiOptions.width / 2 - 155, GuiOptions.height / 6 + 120 - 6, 150, 20, I18n.format("options.language", new Object[0])));
        this.buttonList.add(new GuiButton(103, GuiOptions.width / 2 + 5, GuiOptions.height / 6 + 120 - 6, 150, 20, I18n.format("options.chat.title", new Object[0])));
        this.buttonList.add(new GuiButton(105, GuiOptions.width / 2 - 155, GuiOptions.height / 6 + 144 - 6, 150, 20, I18n.format("options.resourcepack", new Object[0])));
        this.buttonList.add(new GuiButton(104, GuiOptions.width / 2 + 5, GuiOptions.height / 6 + 144 - 6, 150, 20, I18n.format("options.snooper.view", new Object[0])));
        this.buttonList.add(new GuiButton(200, GuiOptions.width / 2 - 100, GuiOptions.height / 6 + 168, I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.field_146442_a, GuiOptions.width / 2, 15, 16777215);
        super.drawScreen(n, n2, n3);
    }
}
