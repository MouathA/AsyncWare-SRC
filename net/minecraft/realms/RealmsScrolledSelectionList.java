package net.minecraft.realms;

import net.minecraft.client.gui.*;

public class RealmsScrolledSelectionList
{
    private final GuiSlotRealmsProxy proxy;
    
    public void mouseEvent() {
        this.proxy.handleMouseInput();
    }
    
    public int ym() {
        return this.proxy.func_154339_l();
    }
    
    protected void renderItem(final int n, final int n2, final int n3, final int n4, final Tezzelator tezzelator, final int n5, final int n6) {
    }
    
    public int getScrollbarPosition() {
        return this.proxy.func_154338_k() / 2 + 124;
    }
    
    public void scroll(final int n) {
        this.proxy.scrollBy(n);
    }
    
    public int width() {
        return this.proxy.func_154338_k();
    }
    
    protected void renderList(final int n, final int n2, final int n3, final int n4) {
    }
    
    public boolean isSelectedItem(final int n) {
        return false;
    }
    
    public void renderBackground() {
    }
    
    public int xm() {
        return this.proxy.func_154337_m();
    }
    
    public void selectItem(final int n, final boolean b, final int n2, final int n3) {
    }
    
    public int getItemCount() {
        return 0;
    }
    
    public void renderItem(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.renderItem(n, n2, n3, n4, Tezzelator.instance, n5, n6);
    }
    
    public int getScroll() {
        return this.proxy.getAmountScrolled();
    }
    
    public RealmsScrolledSelectionList(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.proxy = new GuiSlotRealmsProxy(this, n, n2, n3, n4, n5);
    }
    
    public void render(final int n, final int n2, final float n3) {
        this.proxy.drawScreen(n, n2, n3);
    }
    
    public int getMaxPosition() {
        return 0;
    }
}
