package net.minecraft.client.gui;

import net.minecraft.realms.*;
import net.minecraft.client.*;

public class GuiSlotRealmsProxy extends GuiSlot
{
    private final RealmsScrolledSelectionList selectionList;
    
    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
    }
    
    @Override
    protected int getContentHeight() {
        return this.selectionList.getMaxPosition();
    }
    
    public GuiSlotRealmsProxy(final RealmsScrolledSelectionList selectionList, final int n, final int n2, final int n3, final int n4, final int n5) {
        super(Minecraft.getMinecraft(), n, n2, n3, n4, n5);
        this.selectionList = selectionList;
    }
    
    public int func_154337_m() {
        return super.mouseX;
    }
    
    @Override
    protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.selectionList.renderItem(n, n2, n3, n4, n5, n6);
    }
    
    @Override
    protected int getScrollBarX() {
        return this.selectionList.getScrollbarPosition();
    }
    
    public int func_154339_l() {
        return super.mouseY;
    }
    
    @Override
    protected void elementClicked(final int n, final boolean b, final int n2, final int n3) {
        this.selectionList.selectItem(n, b, n2, n3);
    }
    
    @Override
    protected boolean isSelected(final int n) {
        return this.selectionList.isSelectedItem(n);
    }
    
    @Override
    protected void drawBackground() {
        this.selectionList.renderBackground();
    }
    
    @Override
    protected int getSize() {
        return this.selectionList.getItemCount();
    }
    
    public int func_154338_k() {
        return super.width;
    }
}
