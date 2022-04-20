package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.client.renderer.*;

public class GuiLockIconButton extends GuiButton
{
    private boolean field_175231_o;
    
    public GuiLockIconButton(final int n, final int n2, final int n3) {
        super(n, n2, n3, 20, 20, "");
        this.field_175231_o = false;
    }
    
    public boolean func_175230_c() {
        return this.field_175231_o;
    }
    
    public void func_175229_b(final boolean field_175231_o) {
        this.field_175231_o = field_175231_o;
    }
    
    @Override
    public void drawButton(final Minecraft minecraft, final int n, final int n2) {
        if (this.visible) {
            minecraft.getTextureManager().bindTexture(GuiButton.buttonTextures);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            final boolean b = n >= this.xPosition && n2 >= this.yPosition && n < this.xPosition + this.width && n2 < this.yPosition + this.height;
            Icon icon;
            if (this.field_175231_o) {
                if (!this.enabled) {
                    icon = Icon.LOCKED_DISABLED;
                }
                else if (b) {
                    icon = Icon.LOCKED_HOVER;
                }
                else {
                    icon = Icon.LOCKED;
                }
            }
            else if (!this.enabled) {
                icon = Icon.UNLOCKED_DISABLED;
            }
            else if (b) {
                icon = Icon.UNLOCKED_HOVER;
            }
            else {
                icon = Icon.UNLOCKED;
            }
            this.drawTexturedModalRect(this.xPosition, this.yPosition, icon.func_178910_a(), icon.func_178912_b(), this.width, this.height);
        }
    }
    
    enum Icon
    {
        LOCKED_HOVER("LOCKED_HOVER", 1, 0, 166), 
        UNLOCKED("UNLOCKED", 3, 20, 146), 
        UNLOCKED_DISABLED("UNLOCKED_DISABLED", 5, 20, 186), 
        LOCKED_DISABLED("LOCKED_DISABLED", 2, 0, 186);
        
        private final int field_178920_h;
        private final int field_178914_g;
        
        LOCKED("LOCKED", 0, 0, 146);
        
        private static final Icon[] $VALUES;
        
        UNLOCKED_HOVER("UNLOCKED_HOVER", 4, 20, 166);
        
        private Icon(final String s, final int n, final int field_178914_g, final int field_178920_h) {
            this.field_178914_g = field_178914_g;
            this.field_178920_h = field_178920_h;
        }
        
        static {
            $VALUES = new Icon[] { Icon.LOCKED, Icon.LOCKED_HOVER, Icon.LOCKED_DISABLED, Icon.UNLOCKED, Icon.UNLOCKED_HOVER, Icon.UNLOCKED_DISABLED };
        }
        
        public int func_178910_a() {
            return this.field_178914_g;
        }
        
        public int func_178912_b() {
            return this.field_178920_h;
        }
    }
}
