package net.minecraft.client.gui.spectator;

import com.google.common.collect.*;
import java.util.*;
import com.google.common.base.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.spectator.categories.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;

public class SpectatorMenu
{
    public static final ISpectatorMenuObject field_178657_a;
    private static final ISpectatorMenuObject field_178655_b;
    private int field_178658_j;
    private static final ISpectatorMenuObject field_178654_e;
    private final List field_178652_g;
    private int field_178660_i;
    private static final ISpectatorMenuObject field_178653_d;
    private final ISpectatorMenuRecipient field_178651_f;
    private ISpectatorMenuView field_178659_h;
    private static final ISpectatorMenuObject field_178656_c;
    
    public List func_178642_a() {
        final ArrayList arrayList = Lists.newArrayList();
        while (true) {
            arrayList.add(this.func_178643_a(0));
            int n = 0;
            ++n;
        }
    }
    
    public ISpectatorMenuObject func_178645_b() {
        return this.func_178643_a(this.field_178660_i);
    }
    
    static int access$102(final SpectatorMenu spectatorMenu, final int field_178658_j) {
        return spectatorMenu.field_178658_j = field_178658_j;
    }
    
    public int func_178648_e() {
        return this.field_178660_i;
    }
    
    public ISpectatorMenuView func_178650_c() {
        return this.field_178659_h;
    }
    
    public void func_178647_a(final ISpectatorMenuView field_178659_h) {
        this.field_178652_g.add(this.func_178646_f());
        this.field_178659_h = field_178659_h;
        this.field_178660_i = -1;
        this.field_178658_j = 0;
    }
    
    public ISpectatorMenuObject func_178643_a(final int n) {
        final int n2 = n + this.field_178658_j * 6;
        return (ISpectatorMenuObject)((this.field_178658_j > 0 && n == 0) ? SpectatorMenu.field_178656_c : ((n == 7) ? ((n2 < this.field_178659_h.func_178669_a().size()) ? SpectatorMenu.field_178653_d : SpectatorMenu.field_178654_e) : ((n == 8) ? SpectatorMenu.field_178655_b : ((n2 >= 0 && n2 < this.field_178659_h.func_178669_a().size()) ? Objects.firstNonNull(this.field_178659_h.func_178669_a().get(n2), (Object)SpectatorMenu.field_178657_a) : SpectatorMenu.field_178657_a))));
    }
    
    public void func_178641_d() {
        this.field_178651_f.func_175257_a(this);
    }
    
    public SpectatorMenu(final ISpectatorMenuRecipient field_178651_f) {
        this.field_178652_g = Lists.newArrayList();
        this.field_178659_h = new BaseSpectatorGroup();
        this.field_178660_i = -1;
        this.field_178651_f = field_178651_f;
    }
    
    static {
        field_178655_b = new EndSpectatorObject(null);
        field_178656_c = new MoveMenuObject(-1, true);
        field_178653_d = new MoveMenuObject(1, true);
        field_178654_e = new MoveMenuObject(1, false);
        field_178657_a = new ISpectatorMenuObject() {
            @Override
            public IChatComponent getSpectatorName() {
                return new ChatComponentText("");
            }
            
            @Override
            public boolean func_178662_A_() {
                return false;
            }
            
            @Override
            public void func_178661_a(final SpectatorMenu spectatorMenu) {
            }
            
            @Override
            public void func_178663_a(final float n, final int n2) {
            }
        };
    }
    
    public void func_178644_b(final int field_178660_i) {
        final ISpectatorMenuObject func_178643_a = this.func_178643_a(field_178660_i);
        if (func_178643_a != SpectatorMenu.field_178657_a) {
            if (this.field_178660_i == field_178660_i && func_178643_a.func_178662_A_()) {
                func_178643_a.func_178661_a(this);
            }
            else {
                this.field_178660_i = field_178660_i;
            }
        }
    }
    
    public SpectatorDetails func_178646_f() {
        return new SpectatorDetails(this.field_178659_h, this.func_178642_a(), this.field_178660_i);
    }
    
    static class EndSpectatorObject implements ISpectatorMenuObject
    {
        @Override
        public boolean func_178662_A_() {
            return true;
        }
        
        @Override
        public void func_178663_a(final float n, final int n2) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
            Gui.drawModalRectWithCustomSizedTexture(0, 0, 128.0f, 0.0f, 16, 16, 256.0f, 256.0f);
        }
        
        EndSpectatorObject(final SpectatorMenu$1 spectatorMenuObject) {
            this();
        }
        
        @Override
        public void func_178661_a(final SpectatorMenu spectatorMenu) {
            spectatorMenu.func_178641_d();
        }
        
        @Override
        public IChatComponent getSpectatorName() {
            return new ChatComponentText("Close menu");
        }
        
        private EndSpectatorObject() {
        }
    }
    
    static class MoveMenuObject implements ISpectatorMenuObject
    {
        private final boolean field_178665_b;
        private final int field_178666_a;
        
        @Override
        public void func_178661_a(final SpectatorMenu spectatorMenu) {
            SpectatorMenu.access$102(spectatorMenu, this.field_178666_a);
        }
        
        @Override
        public IChatComponent getSpectatorName() {
            return (this.field_178666_a < 0) ? new ChatComponentText("Previous Page") : new ChatComponentText("Next Page");
        }
        
        @Override
        public boolean func_178662_A_() {
            return this.field_178665_b;
        }
        
        public MoveMenuObject(final int field_178666_a, final boolean field_178665_b) {
            this.field_178666_a = field_178666_a;
            this.field_178665_b = field_178665_b;
        }
        
        @Override
        public void func_178663_a(final float n, final int n2) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
            if (this.field_178666_a < 0) {
                Gui.drawModalRectWithCustomSizedTexture(0, 0, 144.0f, 0.0f, 16, 16, 256.0f, 256.0f);
            }
            else {
                Gui.drawModalRectWithCustomSizedTexture(0, 0, 160.0f, 0.0f, 16, 16, 256.0f, 256.0f);
            }
        }
    }
}
