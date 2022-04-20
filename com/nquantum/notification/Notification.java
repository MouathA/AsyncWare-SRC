package com.nquantum.notification;

import java.awt.*;
import net.minecraft.client.*;
import com.nquantum.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class Notification
{
    private long fadedIn;
    private long start;
    private NotificationType type;
    private String messsage;
    private long fadeOut;
    private String title;
    private long end;
    
    public void render() {
        final long time = this.getTime();
        double n;
        if (time < this.fadedIn) {
            n = Math.tanh(time / (double)this.fadedIn * 3.0) * 120;
        }
        else if (time > this.fadeOut) {
            n = Math.tanh(3.0 - (time - this.fadeOut) / (double)(this.end - this.fadeOut) * 3.0) * 120;
        }
        else {
            n = 120;
        }
        Color color = new Color(0, 0, 0, 220);
        Color color2;
        if (this.type == NotificationType.SUCCESS) {
            color2 = new Color(68, 220, 68);
        }
        else if (this.type == NotificationType.INFO) {
            color2 = new Color(255, 255, 255);
        }
        else if (this.type == NotificationType.WARNING) {
            color2 = new Color(255, 247, 113);
        }
        else {
            color2 = new Color(222, 36, 36);
            color = new Color(Math.max(0, Math.min(255, (int)(Math.sin(time / 100.0) * 255.0 / 2.0 + 127.5))), 0, 0, 170);
        }
        final FontRenderer fontRendererObj = Minecraft.getMinecraft().fontRendererObj;
        drawRect(GuiScreen.width - n, GuiScreen.height - 10 - 30, GuiScreen.width, GuiScreen.height - 5, color.getRGB());
        drawRect(GuiScreen.width - n, GuiScreen.height - 10 - 30, GuiScreen.width - n + 2.0, GuiScreen.height - 5, color2.getRGB());
        Asyncware.roboto.drawStringWithShadow(this.title, (float)(int)(GuiScreen.width - n + 6.0), (float)(GuiScreen.height - 2 - 30), -1);
        Asyncware.s.drawStringWithShadow(this.messsage, (float)(int)(GuiScreen.width - n + 6.0), (float)(GuiScreen.height - 20), new Color(144, 144, 144).getRGB());
    }
    
    private long getTime() {
        return System.currentTimeMillis() - this.start;
    }
    
    public Notification(final NotificationType type, final String title, final String messsage, final int n) {
        this.type = type;
        this.title = title;
        this.messsage = messsage;
        this.fadedIn = 200 * n;
        this.fadeOut = this.fadedIn + 500 * n;
        this.end = this.fadeOut + this.fadedIn;
    }
    
    public static void drawRect(final int n, double n2, double n3, double n4, double n5, final int n6) {
        if (n2 < n4) {
            final double n7 = n2;
            n2 = n4;
            n4 = n7;
        }
        if (n3 < n5) {
            final double n8 = n3;
            n3 = n5;
            n5 = n8;
        }
        final float n9 = (n6 >> 24 & 0xFF) / 255.0f;
        final float n10 = (n6 >> 16 & 0xFF) / 255.0f;
        final float n11 = (n6 >> 8 & 0xFF) / 255.0f;
        final float n12 = (n6 & 0xFF) / 255.0f;
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(n10, n11, n12, n9);
        worldRenderer.begin(n, DefaultVertexFormats.POSITION);
        worldRenderer.pos(n2, n5, 0.0).endVertex();
        worldRenderer.pos(n4, n5, 0.0).endVertex();
        worldRenderer.pos(n4, n3, 0.0).endVertex();
        worldRenderer.pos(n2, n3, 0.0).endVertex();
        instance.draw();
    }
    
    public static void drawRect(double n, double n2, double n3, double n4, final int n5) {
        if (n < n3) {
            final double n6 = n;
            n = n3;
            n3 = n6;
        }
        if (n2 < n4) {
            final double n7 = n2;
            n2 = n4;
            n4 = n7;
        }
        final float n8 = (n5 >> 24 & 0xFF) / 255.0f;
        final float n9 = (n5 >> 16 & 0xFF) / 255.0f;
        final float n10 = (n5 >> 8 & 0xFF) / 255.0f;
        final float n11 = (n5 & 0xFF) / 255.0f;
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(n9, n10, n11, n8);
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(n, n4, 0.0).endVertex();
        worldRenderer.pos(n3, n4, 0.0).endVertex();
        worldRenderer.pos(n3, n2, 0.0).endVertex();
        worldRenderer.pos(n, n2, 0.0).endVertex();
        instance.draw();
    }
    
    public void show() {
        this.start = System.currentTimeMillis();
    }
    
    public boolean isShown() {
        return this.getTime() <= this.end;
    }
}
