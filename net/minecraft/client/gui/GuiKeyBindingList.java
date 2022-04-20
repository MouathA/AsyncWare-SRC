package net.minecraft.client.gui;

import net.minecraft.client.*;
import org.apache.commons.lang3.*;
import java.util.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.settings.*;
import net.minecraft.util.*;

public class GuiKeyBindingList extends GuiListExtended
{
    private final GuiControls field_148191_k;
    private final IGuiListEntry[] listEntries;
    private int maxListLabelWidth;
    private final Minecraft mc;
    
    static GuiControls access$200(final GuiKeyBindingList list) {
        return list.field_148191_k;
    }
    
    @Override
    protected int getSize() {
        return this.listEntries.length;
    }
    
    @Override
    public IGuiListEntry getListEntry(final int n) {
        return this.listEntries[n];
    }
    
    static int access$300(final GuiKeyBindingList list) {
        return list.maxListLabelWidth;
    }
    
    public GuiKeyBindingList(final GuiControls field_148191_k, final Minecraft mc) {
        super(mc, GuiControls.width, GuiControls.height, 63, GuiControls.height - 32, 20);
        this.maxListLabelWidth = 0;
        this.field_148191_k = field_148191_k;
        this.mc = mc;
        final KeyBinding[] array = (KeyBinding[])ArrayUtils.clone((Object[])mc.gameSettings.keyBindings);
        this.listEntries = new IGuiListEntry[array.length + KeyBinding.getKeybinds().size()];
        Arrays.sort(array);
        Object o = null;
        final KeyBinding[] array2 = array;
        while (0 < array2.length) {
            final KeyBinding keyBinding = array2[0];
            final String keyCategory = keyBinding.getKeyCategory();
            int n2 = 0;
            if (!keyCategory.equals(o)) {
                o = keyCategory;
                final IGuiListEntry[] listEntries = this.listEntries;
                final int n = 0;
                ++n2;
                listEntries[n] = new CategoryEntry(keyCategory);
            }
            final int stringWidth = mc.fontRendererObj.getStringWidth(I18n.format(keyBinding.getKeyDescription(), new Object[0]));
            if (stringWidth > this.maxListLabelWidth) {
                this.maxListLabelWidth = stringWidth;
            }
            final IGuiListEntry[] listEntries2 = this.listEntries;
            final int n3 = 0;
            ++n2;
            listEntries2[n3] = new KeyEntry(keyBinding, null);
            int n4 = 0;
            ++n4;
        }
    }
    
    @Override
    public int getListWidth() {
        return super.getListWidth() + 32;
    }
    
    @Override
    protected int getScrollBarX() {
        return super.getScrollBarX() + 15;
    }
    
    static Minecraft access$100(final GuiKeyBindingList list) {
        return list.mc;
    }
    
    public class KeyEntry implements IGuiListEntry
    {
        private final GuiButton btnReset;
        private final String keyDesc;
        private final KeyBinding keybinding;
        private final GuiButton btnChangeKeyBinding;
        final GuiKeyBindingList this$0;
        
        @Override
        public boolean mousePressed(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            if (this.btnChangeKeyBinding.mousePressed(GuiKeyBindingList.access$100(this.this$0), n2, n3)) {
                GuiKeyBindingList.access$200(this.this$0).buttonId = this.keybinding;
                return true;
            }
            if (this.btnReset.mousePressed(GuiKeyBindingList.access$100(this.this$0), n2, n3)) {
                GuiKeyBindingList.access$100(this.this$0).gameSettings.setOptionKeyBinding(this.keybinding, this.keybinding.getKeyCodeDefault());
                return true;
            }
            return false;
        }
        
        @Override
        public void drawEntry(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b) {
            final boolean b2 = GuiKeyBindingList.access$200(this.this$0).buttonId == this.keybinding;
            GuiKeyBindingList.access$100(this.this$0).fontRendererObj.drawString(this.keyDesc, n2 + 90 - GuiKeyBindingList.access$300(this.this$0), n3 + n5 / 2 - GuiKeyBindingList.access$100(this.this$0).fontRendererObj.FONT_HEIGHT / 2, 16777215);
            this.btnReset.xPosition = n2 + 190;
            this.btnReset.yPosition = n3;
            this.btnReset.enabled = (this.keybinding.getKeyCode() != this.keybinding.getKeyCodeDefault());
            this.btnReset.drawButton(GuiKeyBindingList.access$100(this.this$0), n6, n7);
            this.btnChangeKeyBinding.xPosition = n2 + 105;
            this.btnChangeKeyBinding.yPosition = n3;
            this.btnChangeKeyBinding.displayString = GameSettings.getKeyDisplayString(this.keybinding.getKeyCode());
            if (this.keybinding.getKeyCode() != 0) {
                final KeyBinding[] keyBindings = GuiKeyBindingList.access$100(this.this$0).gameSettings.keyBindings;
                while (0 < keyBindings.length) {
                    final KeyBinding keyBinding = keyBindings[0];
                    if (keyBinding != this.keybinding && keyBinding.getKeyCode() == this.keybinding.getKeyCode()) {
                        break;
                    }
                    int n8 = 0;
                    ++n8;
                }
            }
            if (b2) {
                this.btnChangeKeyBinding.displayString = EnumChatFormatting.WHITE + "> " + EnumChatFormatting.YELLOW + this.btnChangeKeyBinding.displayString + EnumChatFormatting.WHITE + " <";
            }
            else {
                this.btnChangeKeyBinding.displayString = EnumChatFormatting.RED + this.btnChangeKeyBinding.displayString;
            }
            this.btnChangeKeyBinding.drawButton(GuiKeyBindingList.access$100(this.this$0), n6, n7);
        }
        
        @Override
        public void setSelected(final int n, final int n2, final int n3) {
        }
        
        @Override
        public void mouseReleased(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            this.btnChangeKeyBinding.mouseReleased(n2, n3);
            this.btnReset.mouseReleased(n2, n3);
        }
        
        private KeyEntry(final GuiKeyBindingList this$0, final KeyBinding keybinding) {
            this.this$0 = this$0;
            this.keybinding = keybinding;
            this.keyDesc = I18n.format(keybinding.getKeyDescription(), new Object[0]);
            this.btnChangeKeyBinding = new GuiButton(0, 0, 0, 75, 20, I18n.format(keybinding.getKeyDescription(), new Object[0]));
            this.btnReset = new GuiButton(0, 0, 0, 50, 20, I18n.format("controls.reset", new Object[0]));
        }
        
        KeyEntry(final GuiKeyBindingList list, final KeyBinding keyBinding, final GuiKeyBindingList$1 object) {
            this(list, keyBinding);
        }
    }
    
    public class CategoryEntry implements IGuiListEntry
    {
        private final String labelText;
        final GuiKeyBindingList this$0;
        private final int labelWidth;
        
        @Override
        public void mouseReleased(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        }
        
        @Override
        public boolean mousePressed(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            return false;
        }
        
        @Override
        public void drawEntry(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b) {
            final FontRenderer fontRendererObj = GuiKeyBindingList.access$100(this.this$0).fontRendererObj;
            final String labelText = this.labelText;
            final GuiScreen currentScreen = GuiKeyBindingList.access$100(this.this$0).currentScreen;
            fontRendererObj.drawString(labelText, GuiScreen.width / 2 - this.labelWidth / 2, n3 + n5 - GuiKeyBindingList.access$100(this.this$0).fontRendererObj.FONT_HEIGHT - 1, 16777215);
        }
        
        @Override
        public void setSelected(final int n, final int n2, final int n3) {
        }
        
        public CategoryEntry(final GuiKeyBindingList this$0, final String s) {
            this.this$0 = this$0;
            this.labelText = I18n.format(s, new Object[0]);
            this.labelWidth = GuiKeyBindingList.access$100(this$0).fontRendererObj.getStringWidth(this.labelText);
        }
    }
}
