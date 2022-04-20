package net.minecraft.client.gui;

import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class GuiStreamIndicator
{
    private static final ResourceLocation locationStreamIndicator;
    private final Minecraft mc;
    private int field_152444_d;
    private float field_152443_c;
    
    private void render(final int n, final int n2, final int n3, final int n4) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 0.65f + 0.35000002f * this.field_152443_c);
        this.mc.getTextureManager().bindTexture(GuiStreamIndicator.locationStreamIndicator);
        final float n5 = 150.0f;
        final float n6 = 0.0f;
        final float n7 = n3 * 0.015625f;
        final float n8 = 1.0f;
        final float n9 = (n3 + 16) * 0.015625f;
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(n - 16 - n4, n2 + 16, n5).tex(n6, n9).endVertex();
        worldRenderer.pos(n - n4, n2 + 16, n5).tex(n8, n9).endVertex();
        worldRenderer.pos(n - n4, n2 + 0, n5).tex(n8, n7).endVertex();
        worldRenderer.pos(n - 16 - n4, n2 + 0, n5).tex(n6, n7).endVertex();
        instance.draw();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public void render(final int n, final int n2) {
        if (this.mc.getTwitchStream().isBroadcasting()) {
            final int func_152920_A = this.mc.getTwitchStream().func_152920_A();
            if (func_152920_A > 0) {
                final String string = "" + func_152920_A;
                final int stringWidth = this.mc.fontRendererObj.getStringWidth(string);
                final int n3 = n - stringWidth - 1;
                final int n4 = n2 + 20 - 1;
                final int n5 = n2 + 20 + this.mc.fontRendererObj.FONT_HEIGHT - 1;
                final Tessellator instance = Tessellator.getInstance();
                final WorldRenderer worldRenderer = instance.getWorldRenderer();
                GlStateManager.color(0.0f, 0.0f, 0.0f, (0.65f + 0.35000002f * this.field_152443_c) / 2.0f);
                worldRenderer.begin(7, DefaultVertexFormats.POSITION);
                worldRenderer.pos(n3, n5, 0.0).endVertex();
                worldRenderer.pos(n, n5, 0.0).endVertex();
                worldRenderer.pos(n, n4, 0.0).endVertex();
                worldRenderer.pos(n3, n4, 0.0).endVertex();
                instance.draw();
                this.mc.fontRendererObj.drawString(string, n - stringWidth, n2 + 20, 16777215);
            }
            this.render(n, n2, this.func_152440_b(), 0);
            this.render(n, n2, this.func_152438_c(), 17);
        }
    }
    
    static {
        locationStreamIndicator = new ResourceLocation("textures/gui/stream_indicator.png");
    }
    
    private int func_152438_c() {
        return this.mc.getTwitchStream().func_152929_G() ? 48 : 32;
    }
    
    private int func_152440_b() {
        return this.mc.getTwitchStream().isPaused() ? 16 : 0;
    }
    
    public GuiStreamIndicator(final Minecraft mc) {
        this.field_152443_c = 1.0f;
        this.field_152444_d = 1;
        this.mc = mc;
    }
    
    public void func_152439_a() {
        if (this.mc.getTwitchStream().isBroadcasting()) {
            this.field_152443_c += 0.025f * this.field_152444_d;
            if (this.field_152443_c < 0.0f) {
                this.field_152444_d *= -1;
                this.field_152443_c = 0.0f;
            }
            else if (this.field_152443_c > 1.0f) {
                this.field_152444_d *= -1;
                this.field_152443_c = 1.0f;
            }
        }
        else {
            this.field_152443_c = 1.0f;
            this.field_152444_d = 1;
        }
    }
}
