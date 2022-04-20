package ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.clickgui.component.components.sub;

import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.clickgui.component.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings.*;
import com.nquantum.module.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.clickgui.component.components.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;

public class ModeButton extends Component
{
    private Setting set;
    private int offset;
    private Module mod;
    private int modeIndex;
    private Button parent;
    private int y;
    private boolean hovered;
    private int x;
    
    @Override
    public void setOff(final int offset) {
        this.offset = offset;
    }
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
        if (n > n2 && n3 == 0 && this.parent.open) {
            if (this.modeIndex + 1 > this.set.getOptions().size()) {
                this.modeIndex = 0;
            }
            else {
                ++this.modeIndex;
            }
            this.set.setValString(this.set.getOptions().get(this.modeIndex));
        }
    }
    
    public ModeButton(final Setting set, final Button parent, final Module mod, final int offset) {
        this.set = set;
        this.parent = parent;
        this.mod = mod;
        this.x = parent.parent.getX() + parent.parent.getWidth();
        this.y = parent.parent.getY() + parent.offset;
        this.offset = offset;
        this.modeIndex = 0;
    }
    
    @Override
    public void renderComponent() {
        Gui.drawRect(this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth() * 1, this.parent.parent.getY() + this.offset + 12, this.hovered ? -14540254 : -15658735);
        Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset + 12, -15658735);
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.set.getName() + ": " + this.set.getValString(), (float)((this.parent.parent.getX() + 7) * 2), (float)((this.parent.parent.getY() + this.offset + 2) * 2 + 5), -1);
        GL11.glPopMatrix();
    }
    
    @Override
    public void updateComponent(final int n, final int n2) {
        this.hovered = this.isMouseOnButton(n, n2);
        this.y = this.parent.parent.getY() + this.offset;
        this.x = this.parent.parent.getX();
    }
}
