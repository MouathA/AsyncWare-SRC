package net.minecraft.client.gui;

import org.lwjgl.input.*;
import net.minecraft.client.resources.*;
import net.minecraft.world.storage.*;
import net.minecraft.util.*;
import org.apache.commons.lang3.*;
import java.io.*;
import java.util.*;
import net.minecraft.world.*;

public class GuiCreateWorld extends GuiScreen
{
    private GuiButton btnGameMode;
    private boolean field_146344_y;
    public String chunkProviderSettingsJson;
    private int selectedIndex;
    private GuiButton btnMoreOptions;
    private boolean field_146341_s;
    private String field_146329_I;
    private GuiTextField field_146333_g;
    private String gameMode;
    private String field_146336_i;
    private String field_175300_s;
    private String field_146328_H;
    private GuiButton btnBonusItems;
    private GuiButton btnCustomizeType;
    private GuiScreen parentScreen;
    private boolean field_146338_v;
    private GuiButton btnMapFeatures;
    private String field_146323_G;
    private boolean field_146339_u;
    private String field_146330_J;
    private boolean allowCheats;
    private GuiButton btnAllowCommands;
    private boolean field_146337_w;
    private GuiTextField field_146335_h;
    private GuiButton btnMapType;
    private boolean field_146345_x;
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    public void updateScreen() {
        this.field_146333_g.updateCursorCounter();
        this.field_146335_h.updateCursorCounter();
    }
    
    public void func_146318_a(final WorldInfo worldInfo) {
        this.field_146330_J = I18n.format("selectWorld.newWorld.copyOf", worldInfo.getWorldName());
        this.field_146329_I = worldInfo.getSeed() + "";
        this.selectedIndex = worldInfo.getTerrainType().getWorldTypeID();
        this.chunkProviderSettingsJson = worldInfo.getGeneratorOptions();
        this.field_146341_s = worldInfo.isMapFeaturesEnabled();
        this.allowCheats = worldInfo.areCommandsAllowed();
        if (worldInfo.isHardcoreModeEnabled()) {
            this.gameMode = "hardcore";
        }
        else if (worldInfo.getGameType().isSurvivalOrAdventure()) {
            this.gameMode = "survival";
        }
        else if (worldInfo.getGameType().isCreative()) {
            this.gameMode = "creative";
        }
    }
    
    public static String func_146317_a(final ISaveFormat saveFormat, String s) {
        s = s.replaceAll("[\\./\"]", "_");
        final String[] disallowedFilenames = GuiCreateWorld.disallowedFilenames;
        while (0 < disallowedFilenames.length) {
            if (s.equalsIgnoreCase(disallowedFilenames[0])) {
                s = "_" + s + "_";
            }
            int n = 0;
            ++n;
        }
        while (saveFormat.getWorldInfo(s) != null) {
            s += "-";
        }
        return s;
    }
    
    private void func_146316_a(final boolean field_146344_y) {
        this.field_146344_y = field_146344_y;
        if (WorldType.worldTypes[this.selectedIndex] == WorldType.DEBUG_WORLD) {
            this.btnGameMode.visible = !this.field_146344_y;
            this.btnGameMode.enabled = false;
            if (this.field_175300_s == null) {
                this.field_175300_s = this.gameMode;
            }
            this.gameMode = "spectator";
            this.btnMapFeatures.visible = false;
            this.btnBonusItems.visible = false;
            this.btnMapType.visible = this.field_146344_y;
            this.btnAllowCommands.visible = false;
            this.btnCustomizeType.visible = false;
        }
        else {
            this.btnGameMode.visible = !this.field_146344_y;
            this.btnGameMode.enabled = true;
            if (this.field_175300_s != null) {
                this.gameMode = this.field_175300_s;
                this.field_175300_s = null;
            }
            this.btnMapFeatures.visible = (this.field_146344_y && WorldType.worldTypes[this.selectedIndex] != WorldType.CUSTOMIZED);
            this.btnBonusItems.visible = this.field_146344_y;
            this.btnMapType.visible = this.field_146344_y;
            this.btnAllowCommands.visible = this.field_146344_y;
            this.btnCustomizeType.visible = (this.field_146344_y && (WorldType.worldTypes[this.selectedIndex] == WorldType.FLAT || WorldType.worldTypes[this.selectedIndex] == WorldType.CUSTOMIZED));
        }
        this.func_146319_h();
        if (this.field_146344_y) {
            this.btnMoreOptions.displayString = I18n.format("gui.done", new Object[0]);
        }
        else {
            this.btnMoreOptions.displayString = I18n.format("selectWorld.moreWorldOptions", new Object[0]);
        }
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, GuiCreateWorld.width / 2 - 155, GuiCreateWorld.height - 28, 150, 20, I18n.format("selectWorld.create", new Object[0])));
        this.buttonList.add(new GuiButton(1, GuiCreateWorld.width / 2 + 5, GuiCreateWorld.height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
        this.buttonList.add(this.btnGameMode = new GuiButton(2, GuiCreateWorld.width / 2 - 75, 115, 150, 20, I18n.format("selectWorld.gameMode", new Object[0])));
        this.buttonList.add(this.btnMoreOptions = new GuiButton(3, GuiCreateWorld.width / 2 - 75, 187, 150, 20, I18n.format("selectWorld.moreWorldOptions", new Object[0])));
        this.buttonList.add(this.btnMapFeatures = new GuiButton(4, GuiCreateWorld.width / 2 - 155, 100, 150, 20, I18n.format("selectWorld.mapFeatures", new Object[0])));
        this.btnMapFeatures.visible = false;
        this.buttonList.add(this.btnBonusItems = new GuiButton(7, GuiCreateWorld.width / 2 + 5, 151, 150, 20, I18n.format("selectWorld.bonusItems", new Object[0])));
        this.btnBonusItems.visible = false;
        this.buttonList.add(this.btnMapType = new GuiButton(5, GuiCreateWorld.width / 2 + 5, 100, 150, 20, I18n.format("selectWorld.mapType", new Object[0])));
        this.btnMapType.visible = false;
        this.buttonList.add(this.btnAllowCommands = new GuiButton(6, GuiCreateWorld.width / 2 - 155, 151, 150, 20, I18n.format("selectWorld.allowCommands", new Object[0])));
        this.btnAllowCommands.visible = false;
        this.buttonList.add(this.btnCustomizeType = new GuiButton(8, GuiCreateWorld.width / 2 + 5, 120, 150, 20, I18n.format("selectWorld.customizeType", new Object[0])));
        this.btnCustomizeType.visible = false;
        (this.field_146333_g = new GuiTextField(9, this.fontRendererObj, GuiCreateWorld.width / 2 - 100, 60, 200, 20)).setFocused(true);
        this.field_146333_g.setText(this.field_146330_J);
        (this.field_146335_h = new GuiTextField(10, this.fontRendererObj, GuiCreateWorld.width / 2 - 100, 60, 200, 20)).setText(this.field_146329_I);
        this.func_146316_a(this.field_146344_y);
        this.func_146314_g();
        this.func_146319_h();
    }
    
    private void func_146314_g() {
        this.field_146336_i = this.field_146333_g.getText().trim();
        final char[] allowedCharactersArray = ChatAllowedCharacters.allowedCharactersArray;
        while (0 < allowedCharactersArray.length) {
            this.field_146336_i = this.field_146336_i.replace(allowedCharactersArray[0], '_');
            int n = 0;
            ++n;
        }
        if (StringUtils.isEmpty((CharSequence)this.field_146336_i)) {
            this.field_146336_i = "World";
        }
        this.field_146336_i = func_146317_a(this.mc.getSaveLoader(), this.field_146336_i);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (this.field_146333_g.isFocused() && !this.field_146344_y) {
            this.field_146333_g.textboxKeyTyped(c, n);
            this.field_146330_J = this.field_146333_g.getText();
        }
        else if (this.field_146335_h.isFocused() && this.field_146344_y) {
            this.field_146335_h.textboxKeyTyped(c, n);
            this.field_146329_I = this.field_146335_h.getText();
        }
        if (n == 28 || n == 156) {
            this.actionPerformed(this.buttonList.get(0));
        }
        this.buttonList.get(0).enabled = (this.field_146333_g.getText().length() > 0);
        this.func_146314_g();
    }
    
    private void func_146315_i() {
        this.func_146316_a(!this.field_146344_y);
    }
    
    private void func_146319_h() {
        this.btnGameMode.displayString = I18n.format("selectWorld.gameMode", new Object[0]) + ": " + I18n.format("selectWorld.gameMode." + this.gameMode, new Object[0]);
        this.field_146323_G = I18n.format("selectWorld.gameMode." + this.gameMode + ".line1", new Object[0]);
        this.field_146328_H = I18n.format("selectWorld.gameMode." + this.gameMode + ".line2", new Object[0]);
        this.btnMapFeatures.displayString = I18n.format("selectWorld.mapFeatures", new Object[0]) + " ";
        if (this.field_146341_s) {
            this.btnMapFeatures.displayString += I18n.format("options.on", new Object[0]);
        }
        else {
            this.btnMapFeatures.displayString += I18n.format("options.off", new Object[0]);
        }
        this.btnBonusItems.displayString = I18n.format("selectWorld.bonusItems", new Object[0]) + " ";
        if (this.field_146338_v && !this.field_146337_w) {
            this.btnBonusItems.displayString += I18n.format("options.on", new Object[0]);
        }
        else {
            this.btnBonusItems.displayString += I18n.format("options.off", new Object[0]);
        }
        this.btnMapType.displayString = I18n.format("selectWorld.mapType", new Object[0]) + " " + I18n.format(WorldType.worldTypes[this.selectedIndex].getTranslateName(), new Object[0]);
        this.btnAllowCommands.displayString = I18n.format("selectWorld.allowCommands", new Object[0]) + " ";
        if (this.allowCheats && !this.field_146337_w) {
            this.btnAllowCommands.displayString += I18n.format("options.on", new Object[0]);
        }
        else {
            this.btnAllowCommands.displayString += I18n.format("options.off", new Object[0]);
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == 1) {
                this.mc.displayGuiScreen(this.parentScreen);
            }
            else if (guiButton.id == 0) {
                this.mc.displayGuiScreen(null);
                if (this.field_146345_x) {
                    return;
                }
                this.field_146345_x = true;
                long nextLong = new Random().nextLong();
                final String text = this.field_146335_h.getText();
                if (!StringUtils.isEmpty((CharSequence)text)) {
                    final long long1 = Long.parseLong(text);
                    if (long1 != 0L) {
                        nextLong = long1;
                    }
                }
                final WorldSettings worldSettings = new WorldSettings(nextLong, WorldSettings.GameType.getByName(this.gameMode), this.field_146341_s, this.field_146337_w, WorldType.worldTypes[this.selectedIndex]);
                worldSettings.setWorldName(this.chunkProviderSettingsJson);
                if (this.field_146338_v && !this.field_146337_w) {
                    worldSettings.enableBonusChest();
                }
                if (this.allowCheats && !this.field_146337_w) {
                    worldSettings.enableCommands();
                }
                this.mc.launchIntegratedServer(this.field_146336_i, this.field_146333_g.getText().trim(), worldSettings);
            }
            else if (guiButton.id == 3) {
                this.func_146315_i();
            }
            else if (guiButton.id == 2) {
                if (this.gameMode.equals("survival")) {
                    if (!this.field_146339_u) {
                        this.allowCheats = false;
                    }
                    this.field_146337_w = false;
                    this.gameMode = "hardcore";
                    this.field_146337_w = true;
                    this.btnAllowCommands.enabled = false;
                    this.btnBonusItems.enabled = false;
                    this.func_146319_h();
                }
                else if (this.gameMode.equals("hardcore")) {
                    if (!this.field_146339_u) {
                        this.allowCheats = true;
                    }
                    this.field_146337_w = false;
                    this.gameMode = "creative";
                    this.func_146319_h();
                    this.field_146337_w = false;
                    this.btnAllowCommands.enabled = true;
                    this.btnBonusItems.enabled = true;
                }
                else {
                    if (!this.field_146339_u) {
                        this.allowCheats = false;
                    }
                    this.gameMode = "survival";
                    this.func_146319_h();
                    this.btnAllowCommands.enabled = true;
                    this.btnBonusItems.enabled = true;
                    this.field_146337_w = false;
                }
                this.func_146319_h();
            }
            else if (guiButton.id == 4) {
                this.field_146341_s = !this.field_146341_s;
                this.func_146319_h();
            }
            else if (guiButton.id == 7) {
                this.field_146338_v = !this.field_146338_v;
                this.func_146319_h();
            }
            else if (guiButton.id == 5) {
                ++this.selectedIndex;
                if (this.selectedIndex >= WorldType.worldTypes.length) {
                    this.selectedIndex = 0;
                }
                while (this != null) {
                    ++this.selectedIndex;
                    if (this.selectedIndex >= WorldType.worldTypes.length) {
                        this.selectedIndex = 0;
                    }
                }
                this.chunkProviderSettingsJson = "";
                this.func_146319_h();
                this.func_146316_a(this.field_146344_y);
            }
            else if (guiButton.id == 6) {
                this.field_146339_u = true;
                this.allowCheats = !this.allowCheats;
                this.func_146319_h();
            }
            else if (guiButton.id == 8) {
                if (WorldType.worldTypes[this.selectedIndex] == WorldType.FLAT) {
                    this.mc.displayGuiScreen(new GuiCreateFlatWorld(this, this.chunkProviderSettingsJson));
                }
                else {
                    this.mc.displayGuiScreen(new GuiCustomizeWorldScreen(this, this.chunkProviderSettingsJson));
                }
            }
        }
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        if (this.field_146344_y) {
            this.field_146335_h.mouseClicked(n, n2, n3);
        }
        else {
            this.field_146333_g.mouseClicked(n, n2, n3);
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("selectWorld.create", new Object[0]), GuiCreateWorld.width / 2, 20, -1);
        if (this.field_146344_y) {
            this.drawString(this.fontRendererObj, I18n.format("selectWorld.enterSeed", new Object[0]), GuiCreateWorld.width / 2 - 100, 47, -6250336);
            this.drawString(this.fontRendererObj, I18n.format("selectWorld.seedInfo", new Object[0]), GuiCreateWorld.width / 2 - 100, 85, -6250336);
            if (this.btnMapFeatures.visible) {
                this.drawString(this.fontRendererObj, I18n.format("selectWorld.mapFeatures.info", new Object[0]), GuiCreateWorld.width / 2 - 150, 122, -6250336);
            }
            if (this.btnAllowCommands.visible) {
                this.drawString(this.fontRendererObj, I18n.format("selectWorld.allowCommands.info", new Object[0]), GuiCreateWorld.width / 2 - 150, 172, -6250336);
            }
            this.field_146335_h.drawTextBox();
            if (WorldType.worldTypes[this.selectedIndex].showWorldInfoNotice()) {
                this.fontRendererObj.drawSplitString(I18n.format(WorldType.worldTypes[this.selectedIndex].func_151359_c(), new Object[0]), this.btnMapType.xPosition + 2, this.btnMapType.yPosition + 22, this.btnMapType.getButtonWidth(), 10526880);
            }
        }
        else {
            this.drawString(this.fontRendererObj, I18n.format("selectWorld.enterName", new Object[0]), GuiCreateWorld.width / 2 - 100, 47, -6250336);
            this.drawString(this.fontRendererObj, I18n.format("selectWorld.resultFolder", new Object[0]) + " " + this.field_146336_i, GuiCreateWorld.width / 2 - 100, 85, -6250336);
            this.field_146333_g.drawTextBox();
            this.drawString(this.fontRendererObj, this.field_146323_G, GuiCreateWorld.width / 2 - 100, 137, -6250336);
            this.drawString(this.fontRendererObj, this.field_146328_H, GuiCreateWorld.width / 2 - 100, 149, -6250336);
        }
        super.drawScreen(n, n2, n3);
    }
    
    static {
        GuiCreateWorld.disallowedFilenames = new String[] { "CON", "COM", "PRN", "AUX", "CLOCK$", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9" };
    }
    
    public GuiCreateWorld(final GuiScreen parentScreen) {
        this.gameMode = "survival";
        this.field_146341_s = true;
        this.chunkProviderSettingsJson = "";
        this.parentScreen = parentScreen;
        this.field_146329_I = "";
        this.field_146330_J = I18n.format("selectWorld.newWorld", new Object[0]);
    }
}
