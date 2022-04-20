package net.minecraft.client.gui;

import net.minecraft.client.settings.*;
import java.io.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.*;
import com.google.common.collect.*;
import java.util.*;

public class GuiLanguage extends GuiScreen
{
    private List list;
    private GuiOptionButton confirmSettingsBtn;
    private final GameSettings game_settings_3;
    private final LanguageManager languageManager;
    private GuiOptionButton forceUnicodeFontBtn;
    protected GuiScreen parentScreen;
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            switch (guiButton.id) {
                case 5: {
                    break;
                }
                case 6: {
                    this.mc.displayGuiScreen(this.parentScreen);
                    break;
                }
                case 100: {
                    if (guiButton instanceof GuiOptionButton) {
                        this.game_settings_3.setOptionValue(((GuiOptionButton)guiButton).returnEnumOptions(), 1);
                        guiButton.displayString = this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
                        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
                        this.setWorldAndResolution(this.mc, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
                        break;
                    }
                    break;
                }
                default: {
                    this.list.actionPerformed(guiButton);
                    break;
                }
            }
        }
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.list.handleMouseInput();
    }
    
    static LanguageManager access$000(final GuiLanguage guiLanguage) {
        return guiLanguage.languageManager;
    }
    
    static GuiOptionButton access$300(final GuiLanguage guiLanguage) {
        return guiLanguage.forceUnicodeFontBtn;
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.list.drawScreen(n, n2, n3);
        this.drawCenteredString(this.fontRendererObj, I18n.format("options.language", new Object[0]), GuiLanguage.width / 2, 16, 16777215);
        this.drawCenteredString(this.fontRendererObj, "(" + I18n.format("options.languageWarning", new Object[0]) + ")", GuiLanguage.width / 2, GuiLanguage.height - 56, 8421504);
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    public void initGui() {
        this.buttonList.add(this.forceUnicodeFontBtn = new GuiOptionButton(100, GuiLanguage.width / 2 - 155, GuiLanguage.height - 38, GameSettings.Options.FORCE_UNICODE_FONT, this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT)));
        this.buttonList.add(this.confirmSettingsBtn = new GuiOptionButton(6, GuiLanguage.width / 2 - 155 + 160, GuiLanguage.height - 38, I18n.format("gui.done", new Object[0])));
        (this.list = new List(this.mc)).registerScrollButtons(7, 8);
    }
    
    public GuiLanguage(final GuiScreen parentScreen, final GameSettings game_settings_3, final LanguageManager languageManager) {
        this.parentScreen = parentScreen;
        this.game_settings_3 = game_settings_3;
        this.languageManager = languageManager;
    }
    
    static GameSettings access$100(final GuiLanguage guiLanguage) {
        return guiLanguage.game_settings_3;
    }
    
    static GuiOptionButton access$200(final GuiLanguage guiLanguage) {
        return guiLanguage.confirmSettingsBtn;
    }
    
    class List extends GuiSlot
    {
        private final Map languageMap;
        private final java.util.List langCodeList;
        final GuiLanguage this$0;
        
        @Override
        protected int getSize() {
            return this.langCodeList.size();
        }
        
        @Override
        protected boolean isSelected(final int n) {
            return this.langCodeList.get(n).equals(GuiLanguage.access$000(this.this$0).getCurrentLanguage().getLanguageCode());
        }
        
        @Override
        protected void elementClicked(final int n, final boolean b, final int n2, final int n3) {
            final Language currentLanguage = this.languageMap.get(this.langCodeList.get(n));
            GuiLanguage.access$000(this.this$0).setCurrentLanguage(currentLanguage);
            GuiLanguage.access$100(this.this$0).language = currentLanguage.getLanguageCode();
            this.mc.refreshResources();
            this.this$0.fontRendererObj.setUnicodeFlag(GuiLanguage.access$000(this.this$0).isCurrentLocaleUnicode() || GuiLanguage.access$100(this.this$0).forceUnicodeFont);
            this.this$0.fontRendererObj.setBidiFlag(GuiLanguage.access$000(this.this$0).isCurrentLanguageBidirectional());
            GuiLanguage.access$200(this.this$0).displayString = I18n.format("gui.done", new Object[0]);
            GuiLanguage.access$300(this.this$0).displayString = GuiLanguage.access$100(this.this$0).getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
            GuiLanguage.access$100(this.this$0).saveOptions();
        }
        
        @Override
        protected void drawBackground() {
            this.this$0.drawDefaultBackground();
        }
        
        @Override
        protected int getContentHeight() {
            return this.getSize() * 18;
        }
        
        public List(final GuiLanguage this$0, final Minecraft minecraft) {
            this.this$0 = this$0;
            super(minecraft, GuiLanguage.width, GuiLanguage.height, 32, GuiLanguage.height - 65 + 4, 18);
            this.langCodeList = Lists.newArrayList();
            this.languageMap = Maps.newHashMap();
            for (final Language language : GuiLanguage.access$000(this$0).getLanguages()) {
                this.languageMap.put(language.getLanguageCode(), language);
                this.langCodeList.add(language.getLanguageCode());
            }
        }
        
        @Override
        protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            this.this$0.fontRendererObj.setBidiFlag(true);
            this.this$0.drawCenteredString(this.this$0.fontRendererObj, this.languageMap.get(this.langCodeList.get(n)).toString(), this.width / 2, n3 + 1, 16777215);
            this.this$0.fontRendererObj.setBidiFlag(GuiLanguage.access$000(this.this$0).getCurrentLanguage().isBidirectional());
        }
    }
}
