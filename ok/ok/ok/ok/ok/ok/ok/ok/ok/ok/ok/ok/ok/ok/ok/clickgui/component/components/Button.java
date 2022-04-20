package ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.clickgui.component.components;

import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.clickgui.component.*;
import com.nquantum.module.*;
import java.util.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import com.nquantum.util.color.*;
import com.nquantum.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.clickgui.component.components.sub.*;

public class Button extends Component
{
    private boolean isHovered;
    public int offset;
    private ArrayList subcomponents;
    public boolean open;
    public Frame parent;
    public Module mod;
    private int height;
    
    @Override
    public void updateComponent(final int n, final int n2) {
        this.isHovered = this.isMouseOnButton(n, n2);
        if (!this.subcomponents.isEmpty()) {
            final Iterator<Component> iterator = this.subcomponents.iterator();
            while (iterator.hasNext()) {
                iterator.next().updateComponent(n, n2);
            }
        }
    }
    
    @Override
    public void mouseReleased(final int n, final int n2, final int n3) {
        final Iterator<Component> iterator = this.subcomponents.iterator();
        while (iterator.hasNext()) {
            iterator.next().mouseReleased(n, n2, n3);
        }
    }
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
        if (n > n2 && n3 == 0) {
            this.mod.toggle();
        }
        if (n > n2 && n3 == 1) {
            this.open = !this.open;
            this.parent.refresh();
        }
        final Iterator<Component> iterator = this.subcomponents.iterator();
        while (iterator.hasNext()) {
            iterator.next().mouseClicked(n, n2, n3);
        }
    }
    
    @Override
    public void renderComponent() {
        final int rgb = new Color(255, 23, 23, 255).getRGB();
        Gui.drawRect(0.0, 0.0, 0.0, 0.0, 0);
        Gui.drawRect(this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + 12 + this.offset, this.isHovered ? (this.mod.isToggled() ? Colors.darker(rgb, 0.8f) : Colors.darker(rgb, 0.8f)) : (this.mod.isToggled() ? rgb : new Color(24, 24, 24, 255).getRGB()));
        Asyncware.sfboldsmall.drawStringWithShadow(this.mod.getName().toLowerCase(), this.parent.getX() + 2 - Asyncware.sfboldsmall.getStringWidth(this.mod.getName().toLowerCase()) + 96.0f, (float)(this.parent.getY() + this.offset + 3), this.mod.isToggled() ? new Color(255, 255, 255, 255).getRGB() : -1);
        if (this.open && !this.subcomponents.isEmpty()) {
            final Iterator<Component> iterator = this.subcomponents.iterator();
            while (iterator.hasNext()) {
                iterator.next().renderComponent();
            }
            Gui.drawRect(this.parent.getX() + 2, this.parent.getY() + this.offset + 12, this.parent.getX() + 3, this.parent.getY() + this.offset + (this.subcomponents.size() + 1) * 12, new Color(214, 28, 28, 255).getRGB());
        }
    }
    
    @Override
    public int getHeight() {
        if (this.open) {
            return 12 * (this.subcomponents.size() + 1);
        }
        return 12;
    }
    
    public Button(final Module mod, final Frame parent, final int offset) {
        this.mod = mod;
        this.parent = parent;
        this.offset = offset;
        this.subcomponents = new ArrayList();
        this.open = false;
        this.height = 12;
        int n = offset + 12;
        if (Asyncware.instance.settingsManager.getSettingsByMod(mod) != null) {
            for (final Setting setting : Asyncware.instance.settingsManager.getSettingsByMod(mod)) {
                if (setting.isCombo()) {
                    this.subcomponents.add(new ModeButton(setting, this, mod, n));
                    n += 12;
                }
                if (setting.isSlider()) {
                    this.subcomponents.add(new Slider(setting, this, n));
                    n += 12;
                }
                if (setting.isCheck()) {
                    this.subcomponents.add(new Checkbox(setting, this, n));
                    n += 12;
                }
            }
        }
        this.subcomponents.add(new Keybind(this, n));
    }
    
    @Override
    public void setOff(final int offset) {
        this.offset = offset;
        int off = this.offset + 12;
        final Iterator<Component> iterator = (Iterator<Component>)this.subcomponents.iterator();
        while (iterator.hasNext()) {
            iterator.next().setOff(off);
            off += 12;
        }
    }
    
    @Override
    public void keyTyped(final char c, final int n) {
        final Iterator<Component> iterator = this.subcomponents.iterator();
        while (iterator.hasNext()) {
            iterator.next().keyTyped(c, n);
        }
    }
}
