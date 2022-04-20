package net.minecraft.client.gui;

import net.minecraft.realms.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;

public class GuiSimpleScrolledSelectionListProxy extends GuiSlot
{
    private final RealmsSimpleScrolledSelectionList field_178050_u;
    
    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
    }
    
    @Override
    protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.field_178050_u.renderItem(n, n2, n3, n4, n5, n6);
    }
    
    @Override
    protected void elementClicked(final int n, final boolean b, final int n2, final int n3) {
        this.field_178050_u.selectItem(n, b, n2, n3);
    }
    
    public int getMouseY() {
        return super.mouseY;
    }
    
    @Override
    protected boolean isSelected(final int n) {
        return this.field_178050_u.isSelectedItem(n);
    }
    
    @Override
    protected int getContentHeight() {
        return this.field_178050_u.getMaxPosition();
    }
    
    @Override
    protected void drawBackground() {
        this.field_178050_u.renderBackground();
    }
    
    public int getWidth() {
        return super.width;
    }
    
    @Override
    protected int getSize() {
        return this.field_178050_u.getItemCount();
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float n) {
        if (this.field_178041_q) {
            this.mouseX = mouseX;
            this.mouseY = mouseY;
            this.drawBackground();
            final int scrollBarX = this.getScrollBarX();
            final int n2 = scrollBarX + 6;
            this.bindAmountScrolled();
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            final int n3 = this.left + this.width / 2 - this.getListWidth() / 2 + 2;
            final int n4 = this.top + 4 - (int)this.amountScrolled;
            if (this.hasListHeader) {
                this.drawListHeader(n3, n4, instance);
            }
            this.drawSelectionBox(n3, n4, mouseX, mouseY);
            this.overlayBackground(0, this.top, 255, 255);
            this.overlayBackground(this.bottom, this.height, 255, 255);
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.shadeModel(7425);
            final int func_148135_f = this.func_148135_f();
            if (func_148135_f > 0) {
                final int clamp_int = MathHelper.clamp_int((this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight(), 32, this.bottom - this.top - 8);
                int top = (int)this.amountScrolled * (this.bottom - this.top - clamp_int) / func_148135_f + this.top;
                if (top < this.top) {
                    top = this.top;
                }
                worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                worldRenderer.pos(scrollBarX, this.bottom, 0.0).tex(0.0, 1.0).color(0, 0, 0, 255).endVertex();
                worldRenderer.pos(n2, this.bottom, 0.0).tex(1.0, 1.0).color(0, 0, 0, 255).endVertex();
                worldRenderer.pos(n2, this.top, 0.0).tex(1.0, 0.0).color(0, 0, 0, 255).endVertex();
                worldRenderer.pos(scrollBarX, this.top, 0.0).tex(0.0, 0.0).color(0, 0, 0, 255).endVertex();
                instance.draw();
                worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                worldRenderer.pos(scrollBarX, top + clamp_int, 0.0).tex(0.0, 1.0).color(128, 128, 128, 255).endVertex();
                worldRenderer.pos(n2, top + clamp_int, 0.0).tex(1.0, 1.0).color(128, 128, 128, 255).endVertex();
                worldRenderer.pos(n2, top, 0.0).tex(1.0, 0.0).color(128, 128, 128, 255).endVertex();
                worldRenderer.pos(scrollBarX, top, 0.0).tex(0.0, 0.0).color(128, 128, 128, 255).endVertex();
                instance.draw();
                worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                worldRenderer.pos(scrollBarX, top + clamp_int - 1, 0.0).tex(0.0, 1.0).color(192, 192, 192, 255).endVertex();
                worldRenderer.pos(n2 - 1, top + clamp_int - 1, 0.0).tex(1.0, 1.0).color(192, 192, 192, 255).endVertex();
                worldRenderer.pos(n2 - 1, top, 0.0).tex(1.0, 0.0).color(192, 192, 192, 255).endVertex();
                worldRenderer.pos(scrollBarX, top, 0.0).tex(0.0, 0.0).color(192, 192, 192, 255).endVertex();
                instance.draw();
            }
            this.func_148142_b(mouseX, mouseY);
            GlStateManager.shadeModel(7424);
        }
    }
    
    public int getMouseX() {
        return super.mouseX;
    }
    
    public GuiSimpleScrolledSelectionListProxy(final RealmsSimpleScrolledSelectionList field_178050_u, final int n, final int n2, final int n3, final int n4, final int n5) {
        super(Minecraft.getMinecraft(), n, n2, n3, n4, n5);
        this.field_178050_u = field_178050_u;
    }
    
    @Override
    protected int getScrollBarX() {
        return this.field_178050_u.getScrollbarPosition();
    }
}
