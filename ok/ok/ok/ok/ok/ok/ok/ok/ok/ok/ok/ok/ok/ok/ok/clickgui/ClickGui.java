package ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.clickgui;

import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.clickgui.component.*;
import java.util.*;
import java.io.*;
import com.nquantum.module.*;
import net.minecraft.client.gui.*;

public class ClickGui extends GuiScreen
{
    public static ArrayList frames;
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        for (final Frame frame : ClickGui.frames) {
            if (frame.isWithinHeader(n, n2) && n3 == 0) {
                frame.setDrag(true);
                frame.dragX = n - frame.getX();
                frame.dragY = n2 - frame.getY();
            }
            if (frame.isWithinHeader(n, n2) && n3 == 1) {
                frame.setOpen(!frame.isOpen());
            }
            if (frame.isOpen() && !frame.getComponents().isEmpty()) {
                final Iterator iterator2 = frame.getComponents().iterator();
                while (iterator2.hasNext()) {
                    iterator2.next().mouseClicked(n, n2, n3);
                }
            }
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        for (final Frame frame : ClickGui.frames) {
            frame.renderFrame(this.fontRendererObj);
            frame.updatePosition(n, n2);
            final Iterator iterator2 = frame.getComponents().iterator();
            while (iterator2.hasNext()) {
                iterator2.next().updateComponent(n, n2);
            }
        }
    }
    
    public ClickGui() {
        ClickGui.frames = new ArrayList();
        final Category[] values = Category.values();
        while (0 < values.length) {
            final Frame frame = new Frame(values[0]);
            frame.setX(2);
            ClickGui.frames.add(frame);
            final int n = 2 + (frame.getWidth() + 3);
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    public void onGuiClosed() {
    }
    
    @Override
    protected void keyTyped(final char c, final int n) {
        for (final Frame frame : ClickGui.frames) {
            if (frame.isOpen() && n != 1 && !frame.getComponents().isEmpty()) {
                final Iterator iterator2 = frame.getComponents().iterator();
                while (iterator2.hasNext()) {
                    iterator2.next().keyTyped(c, n);
                }
            }
        }
        if (n == 1) {
            this.mc.displayGuiScreen(null);
        }
    }
    
    @Override
    public void initGui() {
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
    }
    
    @Override
    protected void mouseReleased(final int n, final int n2, final int n3) {
        final Iterator<Frame> iterator = ClickGui.frames.iterator();
        while (iterator.hasNext()) {
            iterator.next().setDrag(false);
        }
        for (final Frame frame : ClickGui.frames) {
            if (frame.isOpen() && !frame.getComponents().isEmpty()) {
                final Iterator iterator3 = frame.getComponents().iterator();
                while (iterator3.hasNext()) {
                    iterator3.next().mouseReleased(n, n2, n3);
                }
            }
        }
    }
}
