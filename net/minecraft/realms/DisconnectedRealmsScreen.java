package net.minecraft.realms;

import net.minecraft.util.*;
import java.util.*;

public class DisconnectedRealmsScreen extends RealmsScreen
{
    private int textHeight;
    private List lines;
    private String title;
    private final RealmsScreen parent;
    private IChatComponent reason;
    
    @Override
    public void init() {
        Realms.setConnectedToRealms(false);
        this.buttonsClear();
        this.lines = this.fontSplit(this.reason.getFormattedText(), this.width() - 50);
        this.textHeight = this.lines.size() * this.fontLineHeight();
        this.buttonsAdd(RealmsScreen.newButton(0, this.width() / 2 - 100, this.height() / 2 + this.textHeight / 2 + this.fontLineHeight(), "\uca6b\uca79\uca65\uca22\uca6e\uca6d\uca6f\uca67"));
    }
    
    @Override
    public void keyPressed(final char c, final int n) {
        if (n == 1) {
            Realms.setScreen(this.parent);
        }
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        this.renderBackground();
        this.drawCenteredString(this.title, this.width() / 2, this.height() / 2 - this.textHeight / 2 - this.fontLineHeight() * 2, 11184810);
        int n4 = this.height() / 2 - this.textHeight / 2;
        if (this.lines != null) {
            final Iterator<String> iterator = this.lines.iterator();
            while (iterator.hasNext()) {
                this.drawCenteredString(iterator.next(), this.width() / 2, n4, 16777215);
                n4 += this.fontLineHeight();
            }
        }
        super.render(n, n2, n3);
    }
    
    @Override
    public void buttonClicked(final RealmsButton realmsButton) {
        if (realmsButton.id() == 0) {
            Realms.setScreen(this.parent);
        }
    }
    
    public DisconnectedRealmsScreen(final RealmsScreen parent, final String s, final IChatComponent reason) {
        this.parent = parent;
        this.title = RealmsScreen.getLocalizedString(s);
        this.reason = reason;
    }
}
