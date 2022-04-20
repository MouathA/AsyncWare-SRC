package ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.clickgui.component.components.sub;

import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.clickgui.component.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.clickgui.component.components.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;

public class Checkbox extends Component
{
    private boolean hovered;
    private int offset;
    private int x;
    private int y;
    private Setting op;
    private Button parent;
    
    @Override
    public void updateComponent(final int n, final int n2) {
        this.hovered = this.isMouseOnButton(n, n2);
        this.y = this.parent.parent.getY() + this.offset;
        this.x = this.parent.parent.getX();
    }
    
    public Checkbox(final Setting op, final Button parent, final int offset) {
        this.op = op;
        this.parent = parent;
        this.x = parent.parent.getX() + parent.parent.getWidth();
        this.y = parent.parent.getY() + parent.offset;
        this.offset = offset;
    }
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
        if (n > n2 && n3 == 0 && this.parent.open) {
            this.op.setValBoolean(!this.op.getValBoolean());
        }
    }
    
    @Override
    public void setOff(final int offset) {
        this.offset = offset;
    }
    
    @Override
    public void renderComponent() {
        Gui.drawRect(this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth() * 1, this.parent.parent.getY() + this.offset + 12, this.hovered ? -14540254 : -15658735);
        Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset + 12, -15658735);
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.op.getName(), (float)((this.parent.parent.getX() + 10 + 4) * 2 + 5), (float)((this.parent.parent.getY() + this.offset + 2) * 2 + 4), -1);
        GL11.glPopMatrix();
        Gui.drawRect(this.parent.parent.getX() + 3 + 4, this.parent.parent.getY() + this.offset + 3, this.parent.parent.getX() + 9 + 4, this.parent.parent.getY() + this.offset + 9, -6710887);
        if (this.op.getValBoolean()) {
            Gui.drawRect(this.parent.parent.getX() + 4 + 4, this.parent.parent.getY() + this.offset + 4, this.parent.parent.getX() + 8 + 4, this.parent.parent.getY() + this.offset + 8, -10066330);
        }
    }
}
