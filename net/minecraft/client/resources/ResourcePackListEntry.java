package net.minecraft.client.resources;

import net.minecraft.client.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;

public abstract class ResourcePackListEntry implements GuiListExtended.IGuiListEntry
{
    protected final GuiScreenResourcePacks resourcePacksGUI;
    private static final IChatComponent field_183021_e;
    private static final IChatComponent field_183022_f;
    private static final IChatComponent field_183020_d;
    protected final Minecraft mc;
    private static final ResourceLocation RESOURCE_PACKS_TEXTURE;
    
    static {
        RESOURCE_PACKS_TEXTURE = new ResourceLocation("textures/gui/resource_packs.png");
        field_183020_d = new ChatComponentTranslation("resourcePack.incompatible", new Object[0]);
        field_183021_e = new ChatComponentTranslation("resourcePack.incompatible.old", new Object[0]);
        field_183022_f = new ChatComponentTranslation("resourcePack.incompatible.new", new Object[0]);
    }
    
    protected boolean func_148308_f() {
        return this.resourcePacksGUI.hasResourcePackEntry(this);
    }
    
    @Override
    public void mouseReleased(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
    }
    
    protected boolean func_148310_d() {
        return true;
    }
    
    protected abstract int func_183019_a();
    
    protected abstract String func_148312_b();
    
    public ResourcePackListEntry(final GuiScreenResourcePacks resourcePacksGUI) {
        this.resourcePacksGUI = resourcePacksGUI;
        this.mc = Minecraft.getMinecraft();
    }
    
    @Override
    public boolean mousePressed(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        if (this.func_148310_d() && n5 <= 32) {
            if (this == 0) {
                this.resourcePacksGUI.markChanged();
                final int func_183019_a = this.func_183019_a();
                if (func_183019_a != 1) {
                    this.mc.displayGuiScreen(new GuiYesNo(new GuiYesNoCallback(this) {
                        final ResourcePackListEntry this$0;
                        
                        @Override
                        public void confirmClicked(final boolean b, final int n) {
                            final List listContaining = this.this$0.resourcePacksGUI.getListContaining(this.this$0);
                            this.this$0.mc.displayGuiScreen(this.this$0.resourcePacksGUI);
                            if (b) {
                                listContaining.remove(this.this$0);
                                this.this$0.resourcePacksGUI.getSelectedResourcePacks().add(0, this.this$0);
                            }
                        }
                    }, I18n.format("resourcePack.incompatible.confirm.title", new Object[0]), I18n.format("resourcePack.incompatible.confirm." + ((func_183019_a > 1) ? "new" : "old"), new Object[0]), 0));
                }
                else {
                    this.resourcePacksGUI.getListContaining(this).remove(this);
                    this.resourcePacksGUI.getSelectedResourcePacks().add(0, this);
                }
                return true;
            }
            if (n5 < 16 && this.func_148308_f()) {
                this.resourcePacksGUI.getListContaining(this).remove(this);
                this.resourcePacksGUI.getAvailableResourcePacks().add(0, this);
                this.resourcePacksGUI.markChanged();
                return true;
            }
            if (n5 > 16 && n6 < 16 && this > 0) {
                final List listContaining = this.resourcePacksGUI.getListContaining(this);
                final int index = listContaining.indexOf(this);
                listContaining.remove(this);
                listContaining.add(index - 1, this);
                this.resourcePacksGUI.markChanged();
                return true;
            }
            if (n5 > 16 && n6 > 16 && this >= 0) {
                final List listContaining2 = this.resourcePacksGUI.getListContaining(this);
                final int index2 = listContaining2.indexOf(this);
                listContaining2.remove(this);
                listContaining2.add(index2 + 1, this);
                this.resourcePacksGUI.markChanged();
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void drawEntry(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b) {
        final int func_183019_a = this.func_183019_a();
        if (func_183019_a != 1) {
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            Gui.drawRect(n2 - 1, n3 - 1, n2 + n4 - 9, n3 + n5 + 1, -8978432);
        }
        this.func_148313_c();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        Gui.drawModalRectWithCustomSizedTexture(n2, n3, 0.0f, 0.0f, 32, 32, 32.0f, 32.0f);
        String s = this.func_148312_b();
        String s2 = this.func_148311_a();
        if ((this.mc.gameSettings.touchscreen || b) && this.func_148310_d()) {
            this.mc.getTextureManager().bindTexture(ResourcePackListEntry.RESOURCE_PACKS_TEXTURE);
            Gui.drawRect(n2, n3, n2 + 32, n3 + 32, -1601138544);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            final int n8 = n6 - n2;
            final int n9 = n7 - n3;
            if (func_183019_a < 1) {
                s = ResourcePackListEntry.field_183020_d.getFormattedText();
                s2 = ResourcePackListEntry.field_183021_e.getFormattedText();
            }
            else if (func_183019_a > 1) {
                s = ResourcePackListEntry.field_183020_d.getFormattedText();
                s2 = ResourcePackListEntry.field_183022_f.getFormattedText();
            }
            if (this == 0) {
                if (n8 < 32) {
                    Gui.drawModalRectWithCustomSizedTexture(n2, n3, 0.0f, 32.0f, 32, 32, 256.0f, 256.0f);
                }
                else {
                    Gui.drawModalRectWithCustomSizedTexture(n2, n3, 0.0f, 0.0f, 32, 32, 256.0f, 256.0f);
                }
            }
            else {
                if (this.func_148308_f()) {
                    if (n8 < 16) {
                        Gui.drawModalRectWithCustomSizedTexture(n2, n3, 32.0f, 32.0f, 32, 32, 256.0f, 256.0f);
                    }
                    else {
                        Gui.drawModalRectWithCustomSizedTexture(n2, n3, 32.0f, 0.0f, 32, 32, 256.0f, 256.0f);
                    }
                }
                if (this > 0) {
                    if (n8 < 32 && n8 > 16 && n9 < 16) {
                        Gui.drawModalRectWithCustomSizedTexture(n2, n3, 96.0f, 32.0f, 32, 32, 256.0f, 256.0f);
                    }
                    else {
                        Gui.drawModalRectWithCustomSizedTexture(n2, n3, 96.0f, 0.0f, 32, 32, 256.0f, 256.0f);
                    }
                }
                if (this >= 0) {
                    if (n8 < 32 && n8 > 16 && n9 > 16) {
                        Gui.drawModalRectWithCustomSizedTexture(n2, n3, 64.0f, 32.0f, 32, 32, 256.0f, 256.0f);
                    }
                    else {
                        Gui.drawModalRectWithCustomSizedTexture(n2, n3, 64.0f, 0.0f, 32, 32, 256.0f, 256.0f);
                    }
                }
            }
        }
        if (this.mc.fontRendererObj.getStringWidth(s) > 157) {
            s = this.mc.fontRendererObj.trimStringToWidth(s, 157 - this.mc.fontRendererObj.getStringWidth("...")) + "...";
        }
        this.mc.fontRendererObj.drawStringWithShadow(s, (float)(n2 + 32 + 2), (float)(n3 + 1), 16777215);
        final List listFormattedStringToWidth = this.mc.fontRendererObj.listFormattedStringToWidth(s2, 157);
        while (0 < listFormattedStringToWidth.size()) {
            this.mc.fontRendererObj.drawStringWithShadow(listFormattedStringToWidth.get(0), (float)(n2 + 32 + 2), (float)(n3 + 12 + 0), 8421504);
            int n10 = 0;
            ++n10;
        }
    }
    
    protected abstract String func_148311_a();
    
    @Override
    public void setSelected(final int n, final int n2, final int n3) {
    }
    
    protected abstract void func_148313_c();
}
