package ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.clickgui.component.components.sub;

import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.clickgui.component.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.clickgui.component.components.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import org.lwjgl.input.*;

public class Keybind extends Component
{
    private int x;
    private int offset;
    private boolean binding;
    private Button parent;
    private int y;
    private boolean hovered;
    
    public Keybind(final Button parent, final int offset) {
        this.parent = parent;
        this.x = parent.parent.getX() + parent.parent.getWidth();
        this.y = parent.parent.getY() + parent.offset;
        this.offset = offset;
    }
    
    @Override
    public void keyTyped(final char c, final int key) {
        if (this.binding) {
            this.parent.mod.setKey(key);
            this.binding = false;
        }
    }
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
        if (n > n2 && n3 == 0 && this.parent.open) {
            this.binding = !this.binding;
        }
    }
    
    @Override
    public void renderComponent() {
        Gui.drawRect(this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth() * 1, this.parent.parent.getY() + this.offset + 12, this.hovered ? -14540254 : -15658735);
        Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset + 12, -15658735);
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.binding ? "Press a key..." : ("Key: " + Keyboard.getKeyName(this.parent.mod.getKey())), (float)((this.parent.parent.getX() + 7) * 2), (float)((this.parent.parent.getY() + this.offset + 2) * 2 + 5), -1);
        GL11.glPopMatrix();
    }
    
    @Override
    public void updateComponent(final int n, final int n2) {
        this.hovered = this.isMouseOnButton(n, n2);
        this.y = this.parent.parent.getY() + this.offset;
        this.x = this.parent.parent.getX();
    }
    
    @Override
    public void setOff(final int offset) {
        this.offset = offset;
    }
}
