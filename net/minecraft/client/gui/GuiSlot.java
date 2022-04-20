package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import org.lwjgl.input.*;

public abstract class GuiSlot
{
    protected long lastClicked;
    protected int bottom;
    protected int mouseY;
    protected int right;
    protected int width;
    protected boolean field_178041_q;
    protected final Minecraft mc;
    protected boolean showSelectionBox;
    private int scrollUpButtonID;
    protected int top;
    protected int height;
    protected final int slotHeight;
    protected int selectedElement;
    protected boolean field_148163_i;
    private int scrollDownButtonID;
    protected int mouseX;
    private boolean enabled;
    protected boolean hasListHeader;
    protected float amountScrolled;
    protected int left;
    protected float scrollMultiplier;
    protected int headerPadding;
    protected int initialClickY;
    
    protected abstract void drawBackground();
    
    public int getAmountScrolled() {
        return (int)this.amountScrolled;
    }
    
    public GuiSlot(final Minecraft mc, final int n, final int height, final int top, final int bottom, final int slotHeight) {
        this.field_148163_i = true;
        this.initialClickY = -2;
        this.selectedElement = -1;
        this.field_178041_q = true;
        this.showSelectionBox = true;
        this.enabled = true;
        this.mc = mc;
        this.width = n;
        this.height = height;
        this.top = top;
        this.bottom = bottom;
        this.slotHeight = slotHeight;
        this.left = 0;
        this.right = n;
    }
    
    public int getListWidth() {
        return 220;
    }
    
    public void setSlotXBoundsFromLeft(final int left) {
        this.left = left;
        this.right = left + this.width;
    }
    
    protected abstract int getSize();
    
    public int getSlotIndexFromScreenCoords(final int n, final int n2) {
        final int n3 = this.left + this.width / 2 - this.getListWidth() / 2;
        final int n4 = this.left + this.width / 2 + this.getListWidth() / 2;
        final int n5 = n2 - this.top - this.headerPadding + (int)this.amountScrolled - 4;
        final int n6 = n5 / this.slotHeight;
        return (n < this.getScrollBarX() && n >= n3 && n <= n4 && n6 >= 0 && n5 >= 0 && n6 < this.getSize()) ? n6 : -1;
    }
    
    public boolean getEnabled() {
        return this.enabled;
    }
    
    public void registerScrollButtons(final int scrollUpButtonID, final int scrollDownButtonID) {
        this.scrollUpButtonID = scrollUpButtonID;
        this.scrollDownButtonID = scrollDownButtonID;
    }
    
    protected void drawSelectionBox(final int n, final int n2, final int n3, final int n4) {
        final int size = this.getSize();
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        while (0 < size) {
            final int n5 = n2 + 0 * this.slotHeight + this.headerPadding;
            final int n6 = this.slotHeight - 4;
            if (n5 > this.bottom || n5 + n6 < this.top) {
                this.func_178040_a(0, n, n5);
            }
            if (this.showSelectionBox && this.isSelected(0)) {
                final int n7 = this.left + (this.width / 2 - this.getListWidth() / 2);
                final int n8 = this.left + this.width / 2 + this.getListWidth() / 2;
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                worldRenderer.pos(n7, n5 + n6 + 2, 0.0).tex(0.0, 1.0).color(128, 128, 128, 255).endVertex();
                worldRenderer.pos(n8, n5 + n6 + 2, 0.0).tex(1.0, 1.0).color(128, 128, 128, 255).endVertex();
                worldRenderer.pos(n8, n5 - 2, 0.0).tex(1.0, 0.0).color(128, 128, 128, 255).endVertex();
                worldRenderer.pos(n7, n5 - 2, 0.0).tex(0.0, 0.0).color(128, 128, 128, 255).endVertex();
                worldRenderer.pos(n7 + 1, n5 + n6 + 1, 0.0).tex(0.0, 1.0).color(0, 0, 0, 255).endVertex();
                worldRenderer.pos(n8 - 1, n5 + n6 + 1, 0.0).tex(1.0, 1.0).color(0, 0, 0, 255).endVertex();
                worldRenderer.pos(n8 - 1, n5 - 1, 0.0).tex(1.0, 0.0).color(0, 0, 0, 255).endVertex();
                worldRenderer.pos(n7 + 1, n5 - 1, 0.0).tex(0.0, 0.0).color(0, 0, 0, 255).endVertex();
                instance.draw();
            }
            this.drawSlot(0, n, n5, n6, n3, n4);
            int n9 = 0;
            ++n9;
        }
    }
    
    protected void func_148132_a(final int n, final int n2) {
    }
    
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
            this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            final float n3 = 32.0f;
            worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldRenderer.pos(this.left, this.bottom, 0.0).tex(this.left / n3, (this.bottom + (int)this.amountScrolled) / n3).color(32, 32, 32, 255).endVertex();
            worldRenderer.pos(this.right, this.bottom, 0.0).tex(this.right / n3, (this.bottom + (int)this.amountScrolled) / n3).color(32, 32, 32, 255).endVertex();
            worldRenderer.pos(this.right, this.top, 0.0).tex(this.right / n3, (this.top + (int)this.amountScrolled) / n3).color(32, 32, 32, 255).endVertex();
            worldRenderer.pos(this.left, this.top, 0.0).tex(this.left / n3, (this.top + (int)this.amountScrolled) / n3).color(32, 32, 32, 255).endVertex();
            instance.draw();
            final int n4 = this.left + this.width / 2 - this.getListWidth() / 2 + 2;
            final int n5 = this.top + 4 - (int)this.amountScrolled;
            if (this.hasListHeader) {
                this.drawListHeader(n4, n5, instance);
            }
            this.drawSelectionBox(n4, n5, mouseX, mouseY);
            this.overlayBackground(0, this.top, 255, 255);
            this.overlayBackground(this.bottom, this.height, 255, 255);
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.shadeModel(7425);
            worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldRenderer.pos(this.left, this.top + 4, 0.0).tex(0.0, 1.0).color(0, 0, 0, 0).endVertex();
            worldRenderer.pos(this.right, this.top + 4, 0.0).tex(1.0, 1.0).color(0, 0, 0, 0).endVertex();
            worldRenderer.pos(this.right, this.top, 0.0).tex(1.0, 0.0).color(0, 0, 0, 255).endVertex();
            worldRenderer.pos(this.left, this.top, 0.0).tex(0.0, 0.0).color(0, 0, 0, 255).endVertex();
            instance.draw();
            worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldRenderer.pos(this.left, this.bottom, 0.0).tex(0.0, 1.0).color(0, 0, 0, 255).endVertex();
            worldRenderer.pos(this.right, this.bottom, 0.0).tex(1.0, 1.0).color(0, 0, 0, 255).endVertex();
            worldRenderer.pos(this.right, this.bottom - 4, 0.0).tex(1.0, 0.0).color(0, 0, 0, 0).endVertex();
            worldRenderer.pos(this.left, this.bottom - 4, 0.0).tex(0.0, 0.0).color(0, 0, 0, 0).endVertex();
            instance.draw();
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
    
    protected int getScrollBarX() {
        return this.width / 2 + 124;
    }
    
    protected void setHasListHeader(final boolean hasListHeader, final int headerPadding) {
        this.hasListHeader = hasListHeader;
        this.headerPadding = headerPadding;
        if (!hasListHeader) {
            this.headerPadding = 0;
        }
    }
    
    public void handleMouseInput() {
        if (this >= this.mouseY) {
            if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState() && this.mouseY >= this.top && this.mouseY <= this.bottom) {
                final int n = (this.width - this.getListWidth()) / 2;
                final int n2 = (this.width + this.getListWidth()) / 2;
                final int n3 = this.mouseY - this.top - this.headerPadding + (int)this.amountScrolled - 4;
                final int selectedElement = n3 / this.slotHeight;
                if (selectedElement < this.getSize() && this.mouseX >= 1 && this.mouseX <= n2 && selectedElement >= 0 && n3 >= 0) {
                    this.elementClicked(selectedElement, false, this.mouseX, this.mouseY);
                    this.selectedElement = selectedElement;
                }
                else if (this.mouseX >= 1 && this.mouseX <= n2 && n3 < 0) {
                    this.func_148132_a(this.mouseX - 1, this.mouseY - this.top + (int)this.amountScrolled - 4);
                }
            }
            if (Mouse.isButtonDown(0) && this.getEnabled()) {
                if (this.initialClickY == -1) {
                    if (this.mouseY >= this.top && this.mouseY <= this.bottom) {
                        final int n4 = (this.width - this.getListWidth()) / 2;
                        final int n5 = (this.width + this.getListWidth()) / 2;
                        final int n6 = this.mouseY - this.top - this.headerPadding + (int)this.amountScrolled - 4;
                        final int selectedElement2 = n6 / this.slotHeight;
                        if (selectedElement2 < this.getSize() && this.mouseX >= n4 && this.mouseX <= n5 && selectedElement2 >= 0 && n6 >= 0) {
                            this.elementClicked(selectedElement2, selectedElement2 == this.selectedElement && Minecraft.getSystemTime() - this.lastClicked < 250L, this.mouseX, this.mouseY);
                            this.selectedElement = selectedElement2;
                            this.lastClicked = Minecraft.getSystemTime();
                        }
                        else if (this.mouseX >= n4 && this.mouseX <= n5 && n6 < 0) {
                            this.func_148132_a(this.mouseX - n4, this.mouseY - this.top + (int)this.amountScrolled - 4);
                        }
                        final int scrollBarX = this.getScrollBarX();
                        final int n7 = scrollBarX + 6;
                        if (this.mouseX >= scrollBarX && this.mouseX <= n7) {
                            this.scrollMultiplier = -1.0f;
                            this.func_148135_f();
                            this.scrollMultiplier /= (this.bottom - this.top - MathHelper.clamp_int((int)((this.bottom - this.top) * (this.bottom - this.top) / (float)this.getContentHeight()), 32, this.bottom - this.top - 8)) / (float)1;
                        }
                        else {
                            this.scrollMultiplier = 1.0f;
                        }
                        this.initialClickY = this.mouseY;
                    }
                    else {
                        this.initialClickY = -2;
                    }
                }
                else if (this.initialClickY >= 0) {
                    this.amountScrolled -= (this.mouseY - this.initialClickY) * this.scrollMultiplier;
                    this.initialClickY = this.mouseY;
                }
            }
            else {
                this.initialClickY = -1;
            }
            Mouse.getEventDWheel();
            this.amountScrolled += 1 * this.slotHeight / 2;
        }
    }
    
    public void setDimensions(final int n, final int height, final int top, final int bottom) {
        this.width = n;
        this.height = height;
        this.top = top;
        this.bottom = bottom;
        this.left = 0;
        this.right = n;
    }
    
    public int getSlotHeight() {
        return this.slotHeight;
    }
    
    public void scrollBy(final int n) {
        this.amountScrolled += n;
        this.bindAmountScrolled();
        this.initialClickY = -2;
    }
    
    protected void func_178040_a(final int n, final int n2, final int n3) {
    }
    
    public int func_148135_f() {
        return Math.max(0, this.getContentHeight() - (this.bottom - this.top - 4));
    }
    
    protected void func_148142_b(final int n, final int n2) {
    }
    
    protected abstract void drawSlot(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    protected abstract boolean isSelected(final int p0);
    
    protected abstract void elementClicked(final int p0, final boolean p1, final int p2, final int p3);
    
    public void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id == this.scrollUpButtonID) {
                this.amountScrolled -= this.slotHeight * 2 / 3;
                this.initialClickY = -2;
                this.bindAmountScrolled();
            }
            else if (guiButton.id == this.scrollDownButtonID) {
                this.amountScrolled += this.slotHeight * 2 / 3;
                this.initialClickY = -2;
                this.bindAmountScrolled();
            }
        }
    }
    
    public void setShowSelectionBox(final boolean showSelectionBox) {
        this.showSelectionBox = showSelectionBox;
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
    
    protected void overlayBackground(final int n, final int n2, final int n3, final int n4) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldRenderer.pos(this.left, n2, 0.0).tex(0.0, n2 / 32.0f).color(64, 64, 64, n4).endVertex();
        worldRenderer.pos(this.left + this.width, n2, 0.0).tex(this.width / 32.0f, n2 / 32.0f).color(64, 64, 64, n4).endVertex();
        worldRenderer.pos(this.left + this.width, n, 0.0).tex(this.width / 32.0f, n / 32.0f).color(64, 64, 64, n3).endVertex();
        worldRenderer.pos(this.left, n, 0.0).tex(0.0, n / 32.0f).color(64, 64, 64, n3).endVertex();
        instance.draw();
    }
    
    protected void bindAmountScrolled() {
        this.amountScrolled = MathHelper.clamp_float(this.amountScrolled, 0.0f, (float)this.func_148135_f());
    }
    
    protected int getContentHeight() {
        return this.getSize() * this.slotHeight + this.headerPadding;
    }
    
    protected void drawListHeader(final int n, final int n2, final Tessellator tessellator) {
    }
}
