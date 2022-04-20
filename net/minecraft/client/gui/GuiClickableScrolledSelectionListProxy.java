package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.realms.*;
import org.lwjgl.input.*;

public class GuiClickableScrolledSelectionListProxy extends GuiSlot
{
    private final RealmsClickableScrolledSelectionList field_178046_u;
    
    public GuiClickableScrolledSelectionListProxy(final RealmsClickableScrolledSelectionList field_178046_u, final int n, final int n2, final int n3, final int n4, final int n5) {
        super(Minecraft.getMinecraft(), n, n2, n3, n4, n5);
        this.field_178046_u = field_178046_u;
    }
    
    @Override
    protected void drawBackground() {
        this.field_178046_u.renderBackground();
    }
    
    @Override
    protected int getSize() {
        return this.field_178046_u.getItemCount();
    }
    
    @Override
    protected void elementClicked(final int n, final boolean b, final int n2, final int n3) {
        this.field_178046_u.selectItem(n, b, n2, n3);
    }
    
    public int func_178045_g() {
        return super.mouseX;
    }
    
    @Override
    protected void drawSelectionBox(final int n, final int n2, final int n3, final int n4) {
        while (0 < this.getSize()) {
            final int n5 = n2 + 0 * this.slotHeight + this.headerPadding;
            final int n6 = this.slotHeight - 4;
            if (n5 > this.bottom || n5 + n6 < this.top) {
                this.func_178040_a(0, n, n5);
            }
            if (this.showSelectionBox && this.isSelected(0)) {
                this.func_178043_a(this.width, n5, n6, Tezzelator.instance);
            }
            this.drawSlot(0, n, n5, n6, n3, n4);
            int n7 = 0;
            ++n7;
        }
    }
    
    public int func_178044_e() {
        return super.width;
    }
    
    @Override
    protected boolean isSelected(final int n) {
        return this.field_178046_u.isSelectedItem(n);
    }
    
    @Override
    protected int getContentHeight() {
        return this.field_178046_u.getMaxPosition();
    }
    
    @Override
    protected int getScrollBarX() {
        return this.field_178046_u.getScrollbarPosition();
    }
    
    public int func_178042_f() {
        return super.mouseY;
    }
    
    @Override
    protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.field_178046_u.renderItem(n, n2, n3, n4, n5, n6);
    }
    
    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        if (this.scrollMultiplier > 0.0f && Mouse.getEventButtonState()) {
            this.field_178046_u.customMouseEvent(this.top, this.bottom, this.headerPadding, this.amountScrolled, this.slotHeight);
        }
    }
    
    public void func_178043_a(final int n, final int n2, final int n3, final Tezzelator tezzelator) {
        this.field_178046_u.renderSelected(n, n2, n3, tezzelator);
    }
}
