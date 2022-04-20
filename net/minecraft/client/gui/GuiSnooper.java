package net.minecraft.client.gui;

import net.minecraft.client.settings.*;
import java.io.*;
import net.minecraft.client.resources.*;
import com.google.common.collect.*;
import java.util.*;

public class GuiSnooper extends GuiScreen
{
    private final java.util.List field_146604_g;
    private final GameSettings game_settings_2;
    private GuiButton field_146605_t;
    private List field_146606_s;
    private final GuiScreen field_146608_a;
    private String field_146610_i;
    private String[] field_146607_r;
    private final java.util.List field_146609_h;
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.field_146606_s.handleMouseInput();
    }
    
    @Override
    public void initGui() {
        this.field_146610_i = I18n.format("options.snooper.title", new Object[0]);
        final String format = I18n.format("options.snooper.desc", new Object[0]);
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator iterator = this.fontRendererObj.listFormattedStringToWidth(format, GuiSnooper.width - 30).iterator();
        while (iterator.hasNext()) {
            arrayList.add(iterator.next());
        }
        this.field_146607_r = arrayList.toArray(new String[arrayList.size()]);
        this.field_146604_g.clear();
        this.field_146609_h.clear();
        this.buttonList.add(this.field_146605_t = new GuiButton(1, GuiSnooper.width / 2 - 152, GuiSnooper.height - 30, 150, 20, this.game_settings_2.getKeyBinding(GameSettings.Options.SNOOPER_ENABLED)));
        this.buttonList.add(new GuiButton(2, GuiSnooper.width / 2 + 2, GuiSnooper.height - 30, 150, 20, I18n.format("gui.done", new Object[0])));
        final boolean b = this.mc.getIntegratedServer() != null && this.mc.getIntegratedServer().getPlayerUsageSnooper() != null;
        for (final Map.Entry<String, Object> entry : new TreeMap<String, Object>(this.mc.getPlayerUsageSnooper().getCurrentStats()).entrySet()) {
            this.field_146604_g.add((b ? "C " : "") + entry.getKey());
            this.field_146609_h.add(this.fontRendererObj.trimStringToWidth(entry.getValue(), GuiSnooper.width - 220));
        }
        if (b) {
            for (final Map.Entry<String, Object> entry2 : new TreeMap<String, Object>(this.mc.getIntegratedServer().getPlayerUsageSnooper().getCurrentStats()).entrySet()) {
                this.field_146604_g.add("S " + entry2.getKey());
                this.field_146609_h.add(this.fontRendererObj.trimStringToWidth(entry2.getValue(), GuiSnooper.width - 220));
            }
        }
        this.field_146606_s = new List();
    }
    
    static java.util.List access$000(final GuiSnooper guiSnooper) {
        return guiSnooper.field_146604_g;
    }
    
    static java.util.List access$100(final GuiSnooper guiSnooper) {
        return guiSnooper.field_146609_h;
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.field_146606_s.drawScreen(n, n2, n3);
        this.drawCenteredString(this.fontRendererObj, this.field_146610_i, GuiSnooper.width / 2, 8, 16777215);
        final String[] field_146607_r = this.field_146607_r;
        while (0 < field_146607_r.length) {
            this.drawCenteredString(this.fontRendererObj, field_146607_r[0], GuiSnooper.width / 2, 22, 8421504);
            final int n4 = 22 + this.fontRendererObj.FONT_HEIGHT;
            int n5 = 0;
            ++n5;
        }
        super.drawScreen(n, n2, n3);
    }
    
    public GuiSnooper(final GuiScreen field_146608_a, final GameSettings game_settings_2) {
        this.field_146604_g = Lists.newArrayList();
        this.field_146609_h = Lists.newArrayList();
        this.field_146608_a = field_146608_a;
        this.game_settings_2 = game_settings_2;
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == 2) {
                this.game_settings_2.saveOptions();
                this.game_settings_2.saveOptions();
                this.mc.displayGuiScreen(this.field_146608_a);
            }
            if (guiButton.id == 1) {
                this.game_settings_2.setOptionValue(GameSettings.Options.SNOOPER_ENABLED, 1);
                this.field_146605_t.displayString = this.game_settings_2.getKeyBinding(GameSettings.Options.SNOOPER_ENABLED);
            }
        }
    }
    
    class List extends GuiSlot
    {
        final GuiSnooper this$0;
        
        @Override
        protected int getSize() {
            return GuiSnooper.access$000(this.this$0).size();
        }
        
        @Override
        protected int getScrollBarX() {
            return this.width - 10;
        }
        
        @Override
        protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            this.this$0.fontRendererObj.drawString(GuiSnooper.access$000(this.this$0).get(n), 10.0, n3, 16777215);
            this.this$0.fontRendererObj.drawString(GuiSnooper.access$100(this.this$0).get(n), 230.0, n3, 16777215);
        }
        
        public List(final GuiSnooper this$0) {
            this.this$0 = this$0;
            super(this$0.mc, GuiSnooper.width, GuiSnooper.height, 80, GuiSnooper.height - 40, this$0.fontRendererObj.FONT_HEIGHT + 1);
        }
        
        @Override
        protected void drawBackground() {
        }
        
        @Override
        protected boolean isSelected(final int n) {
            return false;
        }
        
        @Override
        protected void elementClicked(final int n, final boolean b, final int n2, final int n3) {
        }
    }
}
