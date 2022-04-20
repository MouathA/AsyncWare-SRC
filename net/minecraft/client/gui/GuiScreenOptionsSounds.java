package net.minecraft.client.gui;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import java.io.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.audio.*;

public class GuiScreenOptionsSounds extends GuiScreen
{
    protected String field_146507_a;
    private final GameSettings game_settings_4;
    private String field_146508_h;
    private final GuiScreen field_146505_f;
    
    static GameSettings access$000(final GuiScreenOptionsSounds guiScreenOptionsSounds) {
        return guiScreenOptionsSounds.game_settings_4;
    }
    
    public GuiScreenOptionsSounds(final GuiScreen field_146505_f, final GameSettings game_settings_4) {
        this.field_146507_a = "Options";
        this.field_146505_f = field_146505_f;
        this.game_settings_4 = game_settings_4;
    }
    
    protected String getSoundVolume(final SoundCategory soundCategory) {
        final float soundLevel = this.game_settings_4.getSoundLevel(soundCategory);
        return (soundLevel == 0.0f) ? this.field_146508_h : ((int)(soundLevel * 100.0f) + "%");
    }
    
    @Override
    public void initGui() {
        this.field_146507_a = I18n.format("options.sounds.title", new Object[0]);
        this.field_146508_h = I18n.format("options.off", new Object[0]);
        this.buttonList.add(new Button(SoundCategory.MASTER.getCategoryId(), GuiScreenOptionsSounds.width / 2 - 155 + 0, GuiScreenOptionsSounds.height / 6 - 12 + 0, SoundCategory.MASTER, true));
        final SoundCategory[] values = SoundCategory.values();
        while (0 < values.length) {
            final SoundCategory soundCategory = values[0];
            if (soundCategory != SoundCategory.MASTER) {
                this.buttonList.add(new Button(soundCategory.getCategoryId(), GuiScreenOptionsSounds.width / 2 - 155 + 0, GuiScreenOptionsSounds.height / 6 - 12 + 0, soundCategory, false));
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        this.buttonList.add(new GuiButton(200, GuiScreenOptionsSounds.width / 2 - 100, GuiScreenOptionsSounds.height / 6 + 168, I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled && guiButton.id == 200) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(this.field_146505_f);
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.field_146507_a, GuiScreenOptionsSounds.width / 2, 15, 16777215);
        super.drawScreen(n, n2, n3);
    }
    
    class Button extends GuiButton
    {
        private final SoundCategory field_146153_r;
        private final String field_146152_s;
        final GuiScreenOptionsSounds this$0;
        public float field_146156_o;
        public boolean field_146155_p;
        
        @Override
        public void mouseReleased(final int n, final int n2) {
            if (this.field_146155_p) {
                if (this.field_146153_r != SoundCategory.MASTER) {
                    GuiScreenOptionsSounds.access$000(this.this$0).getSoundLevel(this.field_146153_r);
                }
                this.this$0.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));
            }
            this.field_146155_p = false;
        }
        
        @Override
        protected int getHoverState(final boolean b) {
            return 0;
        }
        
        @Override
        public boolean mousePressed(final Minecraft minecraft, final int n, final int n2) {
            if (super.mousePressed(minecraft, n, n2)) {
                this.field_146156_o = (n - (this.xPosition + 4)) / (float)(this.width - 8);
                this.field_146156_o = MathHelper.clamp_float(this.field_146156_o, 0.0f, 1.0f);
                minecraft.gameSettings.setSoundLevel(this.field_146153_r, this.field_146156_o);
                minecraft.gameSettings.saveOptions();
                this.displayString = this.field_146152_s + ": " + this.this$0.getSoundVolume(this.field_146153_r);
                return this.field_146155_p = true;
            }
            return false;
        }
        
        @Override
        protected void mouseDragged(final Minecraft minecraft, final int n, final int n2) {
            if (this.visible) {
                if (this.field_146155_p) {
                    this.field_146156_o = (n - (this.xPosition + 4)) / (float)(this.width - 8);
                    this.field_146156_o = MathHelper.clamp_float(this.field_146156_o, 0.0f, 1.0f);
                    minecraft.gameSettings.setSoundLevel(this.field_146153_r, this.field_146156_o);
                    minecraft.gameSettings.saveOptions();
                    this.displayString = this.field_146152_s + ": " + this.this$0.getSoundVolume(this.field_146153_r);
                }
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                this.drawTexturedModalRect(this.xPosition + (int)(this.field_146156_o * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
                this.drawTexturedModalRect(this.xPosition + (int)(this.field_146156_o * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
            }
        }
        
        public Button(final GuiScreenOptionsSounds this$0, final int n, final int n2, final int n3, final SoundCategory field_146153_r, final boolean b) {
            this.this$0 = this$0;
            super(n, n2, n3, b ? 310 : 150, 20, "");
            this.field_146156_o = 1.0f;
            this.field_146153_r = field_146153_r;
            this.field_146152_s = I18n.format("soundCategory." + field_146153_r.getCategoryName(), new Object[0]);
            this.displayString = this.field_146152_s + ": " + this$0.getSoundVolume(field_146153_r);
            this.field_146156_o = GuiScreenOptionsSounds.access$000(this$0).getSoundLevel(field_146153_r);
        }
        
        @Override
        public void playPressSound(final SoundHandler soundHandler) {
        }
    }
}
