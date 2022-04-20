package net.minecraft.client.gui;

import net.minecraft.client.*;
import java.util.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public abstract class GuiResourcePackList extends GuiListExtended
{
    protected final Minecraft mc;
    protected final List field_148204_l;
    
    public List getList() {
        return this.field_148204_l;
    }
    
    @Override
    protected int getSize() {
        return this.getList().size();
    }
    
    @Override
    public int getListWidth() {
        return this.width;
    }
    
    @Override
    public ResourcePackListEntry getListEntry(final int n) {
        return this.getList().get(n);
    }
    
    @Override
    protected int getScrollBarX() {
        return this.right - 6;
    }
    
    public GuiResourcePackList(final Minecraft mc, final int n, final int n2, final List field_148204_l) {
        super(mc, n, n2, 32, n2 - 55 + 4, 36);
        this.mc = mc;
        this.field_148204_l = field_148204_l;
        this.field_148163_i = false;
        this.setHasListHeader(true, (int)(mc.fontRendererObj.FONT_HEIGHT * 1.5f));
    }
    
    @Override
    public IGuiListEntry getListEntry(final int n) {
        return this.getListEntry(n);
    }
    
    protected abstract String getListHeader();
    
    @Override
    protected void drawListHeader(final int n, final int n2, final Tessellator tessellator) {
        final String string = EnumChatFormatting.UNDERLINE + "" + EnumChatFormatting.BOLD + this.getListHeader();
        this.mc.fontRendererObj.drawString(string, n + this.width / 2 - this.mc.fontRendererObj.getStringWidth(string) / 2, Math.min(this.top + 3, n2), 16777215);
    }
}
