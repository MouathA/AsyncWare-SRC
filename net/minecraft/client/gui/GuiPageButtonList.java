package net.minecraft.client.gui;

import net.minecraft.util.*;
import net.minecraft.client.*;
import com.google.common.collect.*;
import java.util.*;
import com.google.common.base.*;

public class GuiPageButtonList extends GuiListExtended
{
    private final List field_178072_w;
    private final GuiListEntry[][] field_178078_x;
    private final IntHashMap field_178073_v;
    private final List field_178074_u;
    private Gui field_178075_A;
    private int field_178077_y;
    private GuiResponder field_178076_z;
    
    public Gui func_178056_g() {
        return this.field_178075_A;
    }
    
    public void func_178064_i() {
        if (this.field_178077_y < this.field_178078_x.length - 1) {
            this.func_181156_c(this.field_178077_y + 1);
        }
    }
    
    public Gui func_178061_c(final int n) {
        return (Gui)this.field_178073_v.lookup(n);
    }
    
    @Override
    public boolean mouseClicked(final int n, final int n2, final int n3) {
        final boolean mouseClicked = super.mouseClicked(n, n2, n3);
        final int slotIndexFromScreenCoords = this.getSlotIndexFromScreenCoords(n, n2);
        if (slotIndexFromScreenCoords >= 0) {
            final GuiEntry listEntry = this.getListEntry(slotIndexFromScreenCoords);
            if (this.field_178075_A != GuiEntry.access$200(listEntry) && this.field_178075_A != null && this.field_178075_A instanceof GuiTextField) {
                ((GuiTextField)this.field_178075_A).setFocused(false);
            }
            this.field_178075_A = GuiEntry.access$200(listEntry);
        }
        return mouseClicked;
    }
    
    public void func_181156_c(final int field_178077_y) {
        if (field_178077_y != this.field_178077_y) {
            final int field_178077_y2 = this.field_178077_y;
            this.field_178077_y = field_178077_y;
            this.func_178055_t();
            this.func_178060_e(field_178077_y2, field_178077_y);
            this.amountScrolled = 0.0f;
        }
    }
    
    @Override
    protected int getScrollBarX() {
        return super.getScrollBarX() + 32;
    }
    
    @Override
    public int getListWidth() {
        return 400;
    }
    
    public void func_178071_h() {
        if (this.field_178077_y > 0) {
            this.func_181156_c(this.field_178077_y - 1);
        }
    }
    
    private GuiListButton func_178065_a(final int n, final int n2, final GuiButtonEntry guiButtonEntry) {
        final GuiListButton guiListButton = new GuiListButton(this.field_178076_z, guiButtonEntry.func_178935_b(), n, n2, guiButtonEntry.func_178936_c(), guiButtonEntry.func_178940_a());
        guiListButton.visible = guiButtonEntry.func_178934_d();
        return guiListButton;
    }
    
    public void func_178062_a(final char c, final int n) {
        if (this.field_178075_A instanceof GuiTextField) {
            final GuiTextField guiTextField = (GuiTextField)this.field_178075_A;
            if (!GuiScreen.isKeyComboCtrlV(n)) {
                if (n == 15) {
                    guiTextField.setFocused(false);
                    int index = this.field_178072_w.indexOf(this.field_178075_A);
                    if (GuiScreen.isShiftKeyDown()) {
                        final int n2 = this.field_178072_w.size() - 1;
                    }
                    else if (0 != this.field_178072_w.size() - 1) {
                        ++index;
                    }
                    this.field_178075_A = this.field_178072_w.get(0);
                    final GuiTextField guiTextField2 = (GuiTextField)this.field_178075_A;
                    guiTextField2.setFocused(true);
                    final int n3 = guiTextField2.yPosition + this.slotHeight;
                    final int yPosition = guiTextField2.yPosition;
                    if (n3 > this.bottom) {
                        this.amountScrolled += n3 - this.bottom;
                    }
                    else if (yPosition < this.top) {
                        this.amountScrolled = (float)yPosition;
                    }
                }
                else {
                    guiTextField.textboxKeyTyped(c, n);
                }
            }
            else {
                final String[] split = GuiScreen.getClipboardString().split(";");
                int index2 = this.field_178072_w.indexOf(this.field_178075_A);
                final String[] array = split;
                while (0 < array.length) {
                    this.field_178072_w.get(0).setText(array[0]);
                    if (0 != this.field_178072_w.size() - 1) {
                        ++index2;
                    }
                    if (index2 == 0) {
                        break;
                    }
                    int n4 = 0;
                    ++n4;
                }
            }
        }
    }
    
    public int getSize() {
        return this.field_178074_u.size();
    }
    
    private GuiLabel func_178063_a(final int n, final int n2, final GuiLabelEntry guiLabelEntry, final boolean b) {
        GuiLabel guiLabel;
        if (b) {
            guiLabel = new GuiLabel(this.mc.fontRendererObj, guiLabelEntry.func_178935_b(), n, n2, this.width - n * 2, 20, -1);
        }
        else {
            guiLabel = new GuiLabel(this.mc.fontRendererObj, guiLabelEntry.func_178935_b(), n, n2, 150, 20, -1);
        }
        guiLabel.visible = guiLabelEntry.func_178934_d();
        guiLabel.func_175202_a(guiLabelEntry.func_178936_c());
        guiLabel.setCentered();
        return guiLabel;
    }
    
    public GuiPageButtonList(final Minecraft minecraft, final int n, final int n2, final int n3, final int n4, final int n5, final GuiResponder field_178076_z, final GuiListEntry[]... field_178078_x) {
        super(minecraft, n, n2, n3, n4, n5);
        this.field_178074_u = Lists.newArrayList();
        this.field_178073_v = new IntHashMap();
        this.field_178072_w = Lists.newArrayList();
        this.field_178076_z = field_178076_z;
        this.field_178078_x = field_178078_x;
        this.field_148163_i = false;
        this.func_178069_s();
        this.func_178055_t();
    }
    
    public int func_178057_f() {
        return this.field_178078_x.length;
    }
    
    @Override
    public IGuiListEntry getListEntry(final int n) {
        return this.getListEntry(n);
    }
    
    private void func_178069_s() {
        final GuiListEntry[][] field_178078_x = this.field_178078_x;
        while (0 < field_178078_x.length) {
            final GuiListEntry[] array = field_178078_x[0];
            while (0 < array.length) {
                final GuiListEntry guiListEntry = array[0];
                final GuiListEntry guiListEntry2 = (0 < array.length - 1) ? array[1] : null;
                final Gui func_178058_a = this.func_178058_a(guiListEntry, 0, guiListEntry2 == null);
                final Gui func_178058_a2 = this.func_178058_a(guiListEntry2, 160, guiListEntry == null);
                this.field_178074_u.add(new GuiEntry(func_178058_a, func_178058_a2));
                if (guiListEntry != null && func_178058_a != null) {
                    this.field_178073_v.addKey(guiListEntry.func_178935_b(), func_178058_a);
                    if (func_178058_a instanceof GuiTextField) {
                        this.field_178072_w.add(func_178058_a);
                    }
                }
                if (guiListEntry2 != null && func_178058_a2 != null) {
                    this.field_178073_v.addKey(guiListEntry2.func_178935_b(), func_178058_a2);
                    if (func_178058_a2 instanceof GuiTextField) {
                        this.field_178072_w.add(func_178058_a2);
                    }
                }
                final int n;
                n += 2;
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    private void func_178055_t() {
        this.field_178074_u.clear();
        while (0 < this.field_178078_x[this.field_178077_y].length) {
            final GuiListEntry guiListEntry = this.field_178078_x[this.field_178077_y][0];
            final GuiListEntry guiListEntry2 = (0 < this.field_178078_x[this.field_178077_y].length - 1) ? this.field_178078_x[this.field_178077_y][1] : null;
            this.field_178074_u.add(new GuiEntry((Gui)this.field_178073_v.lookup(guiListEntry.func_178935_b()), (guiListEntry2 != null) ? ((Gui)this.field_178073_v.lookup(guiListEntry2.func_178935_b())) : null));
            final int n;
            n += 2;
        }
    }
    
    private void func_178066_a(final Gui gui, final boolean visible) {
        if (gui instanceof GuiButton) {
            ((GuiButton)gui).visible = visible;
        }
        else if (gui instanceof GuiTextField) {
            ((GuiTextField)gui).setVisible(visible);
        }
        else if (gui instanceof GuiLabel) {
            ((GuiLabel)gui).visible = visible;
        }
    }
    
    private void func_178060_e(final int n, final int n2) {
        final GuiListEntry[] array = this.field_178078_x[n];
        int n3 = 0;
        while (0 < array.length) {
            final GuiListEntry guiListEntry = array[0];
            if (guiListEntry != null) {
                this.func_178066_a((Gui)this.field_178073_v.lookup(guiListEntry.func_178935_b()), false);
            }
            ++n3;
        }
        final GuiListEntry[] array2 = this.field_178078_x[n2];
        while (0 < array2.length) {
            final GuiListEntry guiListEntry2 = array2[0];
            if (guiListEntry2 != null) {
                this.func_178066_a((Gui)this.field_178073_v.lookup(guiListEntry2.func_178935_b()), true);
            }
            ++n3;
        }
    }
    
    public void func_181155_a(final boolean b) {
        for (final GuiEntry guiEntry : this.field_178074_u) {
            if (GuiEntry.access$000(guiEntry) instanceof GuiButton) {
                ((GuiButton)GuiEntry.access$000(guiEntry)).enabled = b;
            }
            if (GuiEntry.access$100(guiEntry) instanceof GuiButton) {
                ((GuiButton)GuiEntry.access$100(guiEntry)).enabled = b;
            }
        }
    }
    
    private GuiSlider func_178067_a(final int n, final int n2, final GuiSlideEntry guiSlideEntry) {
        final GuiSlider guiSlider = new GuiSlider(this.field_178076_z, guiSlideEntry.func_178935_b(), n, n2, guiSlideEntry.func_178936_c(), guiSlideEntry.func_178943_e(), guiSlideEntry.func_178944_f(), guiSlideEntry.func_178942_g(), guiSlideEntry.func_178945_a());
        guiSlider.visible = guiSlideEntry.func_178934_d();
        return guiSlider;
    }
    
    @Override
    public GuiEntry getListEntry(final int n) {
        return this.field_178074_u.get(n);
    }
    
    private GuiTextField func_178068_a(final int n, final int n2, final EditBoxEntry editBoxEntry) {
        final GuiTextField guiTextField = new GuiTextField(editBoxEntry.func_178935_b(), this.mc.fontRendererObj, n, n2, 150, 20);
        guiTextField.setText(editBoxEntry.func_178936_c());
        guiTextField.func_175207_a(this.field_178076_z);
        guiTextField.setVisible(editBoxEntry.func_178934_d());
        guiTextField.func_175205_a(editBoxEntry.func_178950_a());
        return guiTextField;
    }
    
    private Gui func_178058_a(final GuiListEntry guiListEntry, final int n, final boolean b) {
        return (guiListEntry instanceof GuiSlideEntry) ? this.func_178067_a(this.width / 2 - 155 + n, 0, (GuiSlideEntry)guiListEntry) : ((guiListEntry instanceof GuiButtonEntry) ? this.func_178065_a(this.width / 2 - 155 + n, 0, (GuiButtonEntry)guiListEntry) : ((guiListEntry instanceof EditBoxEntry) ? this.func_178068_a(this.width / 2 - 155 + n, 0, (EditBoxEntry)guiListEntry) : ((guiListEntry instanceof GuiLabelEntry) ? this.func_178063_a(this.width / 2 - 155 + n, 0, (GuiLabelEntry)guiListEntry, b) : null)));
    }
    
    public int func_178059_e() {
        return this.field_178077_y;
    }
    
    public static class GuiButtonEntry extends GuiListEntry
    {
        private final boolean field_178941_a;
        
        public GuiButtonEntry(final int n, final String s, final boolean b, final boolean field_178941_a) {
            super(n, s, b);
            this.field_178941_a = field_178941_a;
        }
        
        public boolean func_178940_a() {
            return this.field_178941_a;
        }
    }
    
    public static class GuiListEntry
    {
        private final boolean field_178938_c;
        private final int field_178939_a;
        private final String field_178937_b;
        
        public int func_178935_b() {
            return this.field_178939_a;
        }
        
        public boolean func_178934_d() {
            return this.field_178938_c;
        }
        
        public String func_178936_c() {
            return this.field_178937_b;
        }
        
        public GuiListEntry(final int field_178939_a, final String field_178937_b, final boolean field_178938_c) {
            this.field_178939_a = field_178939_a;
            this.field_178937_b = field_178937_b;
            this.field_178938_c = field_178938_c;
        }
    }
    
    public static class GuiEntry implements IGuiListEntry
    {
        private Gui field_178028_d;
        private final Minecraft field_178031_a;
        private final Gui field_178029_b;
        private final Gui field_178030_c;
        
        @Override
        public void mouseReleased(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            this.func_178016_b(this.field_178029_b, n2, n3, n4);
            this.func_178016_b(this.field_178030_c, n2, n3, n4);
        }
        
        public GuiEntry(final Gui field_178029_b, final Gui field_178030_c) {
            this.field_178031_a = Minecraft.getMinecraft();
            this.field_178029_b = field_178029_b;
            this.field_178030_c = field_178030_c;
        }
        
        private void func_178019_b(final GuiButton guiButton, final int n, final int n2, final int n3) {
            guiButton.mouseReleased(n, n2);
        }
        
        private void func_178027_a(final GuiTextField guiTextField, final int yPosition, final boolean b) {
            guiTextField.yPosition = yPosition;
            if (!b) {
                guiTextField.drawTextBox();
            }
        }
        
        static Gui access$000(final GuiEntry guiEntry) {
            return guiEntry.field_178029_b;
        }
        
        static Gui access$100(final GuiEntry guiEntry) {
            return guiEntry.field_178030_c;
        }
        
        private boolean func_178023_a(final GuiButton field_178028_d, final int n, final int n2, final int n3) {
            final boolean mousePressed = field_178028_d.mousePressed(this.field_178031_a, n, n2);
            if (mousePressed) {
                this.field_178028_d = field_178028_d;
            }
            return mousePressed;
        }
        
        private boolean func_178026_a(final Gui gui, final int n, final int n2, final int n3) {
            if (gui == null) {
                return false;
            }
            if (gui instanceof GuiButton) {
                return this.func_178023_a((GuiButton)gui, n, n2, n3);
            }
            if (gui instanceof GuiTextField) {
                this.func_178018_a((GuiTextField)gui, n, n2, n3);
            }
            return false;
        }
        
        private void func_178017_a(final Gui gui, final int n, final int n2, final int n3, final boolean b) {
            if (gui != null) {
                if (gui instanceof GuiButton) {
                    this.func_178024_a((GuiButton)gui, n, n2, n3, b);
                }
                else if (gui instanceof GuiTextField) {
                    this.func_178027_a((GuiTextField)gui, n, b);
                }
                else if (gui instanceof GuiLabel) {
                    this.func_178025_a((GuiLabel)gui, n, n2, n3, b);
                }
            }
        }
        
        private void func_178025_a(final GuiLabel guiLabel, final int field_146174_h, final int n, final int n2, final boolean b) {
            guiLabel.field_146174_h = field_146174_h;
            if (!b) {
                guiLabel.drawLabel(this.field_178031_a, n, n2);
            }
        }
        
        @Override
        public void drawEntry(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b) {
            this.func_178017_a(this.field_178029_b, n3, n6, n7, false);
            this.func_178017_a(this.field_178030_c, n3, n6, n7, false);
        }
        
        public Gui func_178021_b() {
            return this.field_178030_c;
        }
        
        @Override
        public void setSelected(final int n, final int n2, final int n3) {
            this.func_178017_a(this.field_178029_b, n3, 0, 0, true);
            this.func_178017_a(this.field_178030_c, n3, 0, 0, true);
        }
        
        static Gui access$200(final GuiEntry guiEntry) {
            return guiEntry.field_178028_d;
        }
        
        @Override
        public boolean mousePressed(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            final boolean func_178026_a = this.func_178026_a(this.field_178029_b, n2, n3, n4);
            final boolean func_178026_a2 = this.func_178026_a(this.field_178030_c, n2, n3, n4);
            return func_178026_a || func_178026_a2;
        }
        
        private void func_178024_a(final GuiButton guiButton, final int yPosition, final int n, final int n2, final boolean b) {
            guiButton.yPosition = yPosition;
            if (!b) {
                guiButton.drawButton(this.field_178031_a, n, n2);
            }
        }
        
        private void func_178018_a(final GuiTextField field_178028_d, final int n, final int n2, final int n3) {
            field_178028_d.mouseClicked(n, n2, n3);
            if (field_178028_d.isFocused()) {
                this.field_178028_d = field_178028_d;
            }
        }
        
        private void func_178016_b(final Gui gui, final int n, final int n2, final int n3) {
            if (gui != null && gui instanceof GuiButton) {
                this.func_178019_b((GuiButton)gui, n, n2, n3);
            }
        }
        
        public Gui func_178022_a() {
            return this.field_178029_b;
        }
    }
    
    public interface GuiResponder
    {
        void func_175319_a(final int p0, final String p1);
        
        void onTick(final int p0, final float p1);
        
        void func_175321_a(final int p0, final boolean p1);
    }
    
    public static class GuiSlideEntry extends GuiListEntry
    {
        private final GuiSlider.FormatHelper field_178949_a;
        private final float field_178947_b;
        private final float field_178948_c;
        private final float field_178946_d;
        
        public GuiSlider.FormatHelper func_178945_a() {
            return this.field_178949_a;
        }
        
        public GuiSlideEntry(final int n, final String s, final boolean b, final GuiSlider.FormatHelper field_178949_a, final float field_178947_b, final float field_178948_c, final float field_178946_d) {
            super(n, s, b);
            this.field_178949_a = field_178949_a;
            this.field_178947_b = field_178947_b;
            this.field_178948_c = field_178948_c;
            this.field_178946_d = field_178946_d;
        }
        
        public float func_178943_e() {
            return this.field_178947_b;
        }
        
        public float func_178942_g() {
            return this.field_178946_d;
        }
        
        public float func_178944_f() {
            return this.field_178948_c;
        }
    }
    
    public static class GuiLabelEntry extends GuiListEntry
    {
        public GuiLabelEntry(final int n, final String s, final boolean b) {
            super(n, s, b);
        }
    }
    
    public static class EditBoxEntry extends GuiListEntry
    {
        private final Predicate field_178951_a;
        
        public EditBoxEntry(final int n, final String s, final boolean b, final Predicate predicate) {
            super(n, s, b);
            this.field_178951_a = (Predicate)Objects.firstNonNull((Object)predicate, (Object)Predicates.alwaysTrue());
        }
        
        public Predicate func_178950_a() {
            return this.field_178951_a;
        }
    }
}
