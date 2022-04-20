package net.minecraft.client.gui;

import net.minecraft.util.*;
import net.minecraft.client.*;
import java.awt.*;
import net.minecraft.client.renderer.*;
import com.nquantum.*;
import net.minecraft.client.audio.*;

public class GuiButton extends Gui
{
    protected boolean hovered;
    protected static final ResourceLocation buttonTextures;
    public boolean enabled;
    protected int width;
    public boolean visible;
    protected int height;
    public int xPosition;
    public int yPosition;
    public String displayString;
    public int id;
    
    protected void mouseDragged(final Minecraft minecraft, final int n, final int n2) {
    }
    
    public void drawButtonForegroundLayer(final int n, final int n2) {
    }
    
    public void setWidth(final int width) {
        this.width = width;
    }
    
    static {
        buttonTextures = new ResourceLocation("textures/gui/widgets.png");
    }
    
    public boolean isMouseOver() {
        return this.hovered;
    }
    
    public int getButtonWidth() {
        return this.width;
    }
    
    public GuiButton(final int n, final int n2, final int n3, final String s) {
        this(n, n2, n3, 200, 20, s);
    }
    
    public void drawButton(final Minecraft minecraft, final int n, final int n2) {
        if (this.visible) {
            Color.HSBtoRGB(System.currentTimeMillis() % 12000L / 6000.0f, 0.8f, 0.8f);
            final FontRenderer fontRendererObj = minecraft.fontRendererObj;
            minecraft.getTextureManager().bindTexture(GuiButton.buttonTextures);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.getHoverState(this.hovered = (n >= this.xPosition && n2 >= this.yPosition && n < this.xPosition + this.width && n2 < this.yPosition + this.height));
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, new Color(1, 1, 1, 100).getRGB());
            final Gui gui = new Gui();
            this.mouseDragged(minecraft, n, n2);
            if (this.enabled) {
                if (this.hovered) {
                    new Color(234, 84, 84, 255).getRGB();
                }
            }
            Asyncware.roboto.drawCenteredString(this.displayString, (float)(this.xPosition + this.width / 2), (float)(this.yPosition + (this.height - 10) / 2), -1);
        }
    }
    
    public GuiButton(final int id, final int xPosition, final int yPosition, final int width, final int height, final String displayString) {
        this.width = 200;
        this.height = 20;
        this.enabled = true;
        this.visible = true;
        this.id = id;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.displayString = displayString;
    }
    
    protected int getHoverState(final boolean b) {
        if (this.enabled) {
            if (b) {}
        }
        return 1;
    }
    
    public boolean mousePressed(final Minecraft minecraft, final int n, final int n2) {
        return this.enabled && this.visible && n >= this.xPosition && n2 >= this.yPosition && n < this.xPosition + this.width && n2 < this.yPosition + this.height;
    }
    
    public void mouseReleased(final int n, final int n2) {
    }
    
    public void playPressSound(final SoundHandler soundHandler) {
        soundHandler.playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));
    }
}
