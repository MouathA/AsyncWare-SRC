package net.minecraft.client;

import net.minecraft.client.shader.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class LoadingScreenRenderer implements IProgressUpdate
{
    private long systemTime;
    private ScaledResolution scaledResolution;
    private String currentlyDisplayedText;
    private boolean field_73724_e;
    private String message;
    private Framebuffer framebuffer;
    private Minecraft mc;
    
    private void displayString(final String currentlyDisplayedText) {
        this.currentlyDisplayedText = currentlyDisplayedText;
        if (!this.mc.running) {
            if (!this.field_73724_e) {
                throw new MinecraftError();
            }
        }
        else {
            GlStateManager.clear(256);
            GlStateManager.matrixMode(5889);
            if (OpenGlHelper.isFramebufferEnabled()) {
                final int scaleFactor = this.scaledResolution.getScaleFactor();
                GlStateManager.ortho(0.0, this.scaledResolution.getScaledWidth() * scaleFactor, this.scaledResolution.getScaledHeight() * scaleFactor, 0.0, 100.0, 300.0);
            }
            else {
                final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
                GlStateManager.ortho(0.0, scaledResolution.getScaledWidth_double(), scaledResolution.getScaledHeight_double(), 0.0, 100.0, 300.0);
            }
            GlStateManager.matrixMode(5888);
            GlStateManager.translate(0.0f, 0.0f, -200.0f);
        }
    }
    
    @Override
    public void setDoneWorking() {
    }
    
    @Override
    public void displayLoadingString(final String message) {
        if (!this.mc.running) {
            if (!this.field_73724_e) {
                throw new MinecraftError();
            }
        }
        else {
            this.systemTime = 0L;
            this.message = message;
            this.setLoadingProgress(-1);
            this.systemTime = 0L;
        }
    }
    
    public LoadingScreenRenderer(final Minecraft mc) {
        this.message = "";
        this.currentlyDisplayedText = "";
        this.systemTime = Minecraft.getSystemTime();
        this.mc = mc;
        this.scaledResolution = new ScaledResolution(mc);
        (this.framebuffer = new Framebuffer(mc.displayWidth, mc.displayHeight, false)).setFramebufferFilter(9728);
    }
    
    @Override
    public void setLoadingProgress(final int n) {
        if (!this.mc.running) {
            if (!this.field_73724_e) {
                throw new MinecraftError();
            }
        }
        else {
            final long systemTime = Minecraft.getSystemTime();
            if (systemTime - this.systemTime >= 100L) {
                this.systemTime = systemTime;
                final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
                final int scaleFactor = scaledResolution.getScaleFactor();
                final int scaledWidth = scaledResolution.getScaledWidth();
                final int scaledHeight = scaledResolution.getScaledHeight();
                if (OpenGlHelper.isFramebufferEnabled()) {
                    this.framebuffer.framebufferClear();
                }
                else {
                    GlStateManager.clear(256);
                }
                this.framebuffer.bindFramebuffer(false);
                GlStateManager.matrixMode(5889);
                GlStateManager.ortho(0.0, scaledResolution.getScaledWidth_double(), scaledResolution.getScaledHeight_double(), 0.0, 100.0, 300.0);
                GlStateManager.matrixMode(5888);
                GlStateManager.translate(0.0f, 0.0f, -200.0f);
                if (!OpenGlHelper.isFramebufferEnabled()) {
                    GlStateManager.clear(16640);
                }
                final Tessellator instance = Tessellator.getInstance();
                final WorldRenderer worldRenderer = instance.getWorldRenderer();
                this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
                final float n2 = 32.0f;
                worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                worldRenderer.pos(0.0, scaledHeight, 0.0).tex(0.0, scaledHeight / n2).color(64, 64, 64, 255).endVertex();
                worldRenderer.pos(scaledWidth, scaledHeight, 0.0).tex(scaledWidth / n2, scaledHeight / n2).color(64, 64, 64, 255).endVertex();
                worldRenderer.pos(scaledWidth, 0.0, 0.0).tex(scaledWidth / n2, 0.0).color(64, 64, 64, 255).endVertex();
                worldRenderer.pos(0.0, 0.0, 0.0).tex(0.0, 0.0).color(64, 64, 64, 255).endVertex();
                instance.draw();
                if (n >= 0) {
                    final int n3 = scaledWidth / 2 - 0;
                    final int n4 = scaledHeight / 2 + 16;
                    worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
                    worldRenderer.pos(n3, n4, 0.0).color(128, 128, 128, 255).endVertex();
                    worldRenderer.pos(n3, n4 + 2, 0.0).color(128, 128, 128, 255).endVertex();
                    worldRenderer.pos(n3 + 100, n4 + 2, 0.0).color(128, 128, 128, 255).endVertex();
                    worldRenderer.pos(n3 + 100, n4, 0.0).color(128, 128, 128, 255).endVertex();
                    worldRenderer.pos(n3, n4, 0.0).color(128, 255, 128, 255).endVertex();
                    worldRenderer.pos(n3, n4 + 2, 0.0).color(128, 255, 128, 255).endVertex();
                    worldRenderer.pos(n3 + n, n4 + 2, 0.0).color(128, 255, 128, 255).endVertex();
                    worldRenderer.pos(n3 + n, n4, 0.0).color(128, 255, 128, 255).endVertex();
                    instance.draw();
                }
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                this.mc.fontRendererObj.drawStringWithShadow(this.currentlyDisplayedText, (float)((scaledWidth - this.mc.fontRendererObj.getStringWidth(this.currentlyDisplayedText)) / 2), (float)(scaledHeight / 2 - 4 - 16), 16777215);
                this.mc.fontRendererObj.drawStringWithShadow(this.message, (float)((scaledWidth - this.mc.fontRendererObj.getStringWidth(this.message)) / 2), (float)(scaledHeight / 2 - 4 + 8), 16777215);
                this.framebuffer.unbindFramebuffer();
                if (OpenGlHelper.isFramebufferEnabled()) {
                    this.framebuffer.framebufferRender(scaledWidth * scaleFactor, scaledHeight * scaleFactor);
                }
                this.mc.updateDisplay();
                Thread.yield();
            }
        }
    }
    
    @Override
    public void resetProgressAndMessage(final String s) {
        this.field_73724_e = false;
        this.displayString(s);
    }
    
    @Override
    public void displaySavingString(final String s) {
        this.field_73724_e = true;
        this.displayString(s);
    }
}
