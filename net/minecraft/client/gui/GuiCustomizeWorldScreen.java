package net.minecraft.client.gui;

import net.minecraft.world.gen.*;
import java.util.*;
import com.google.common.base.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.renderer.*;
import java.io.*;
import com.google.common.primitives.*;
import net.minecraft.world.biome.*;
import net.minecraft.util.*;

public class GuiCustomizeWorldScreen extends GuiScreen implements GuiSlider.FormatHelper, GuiPageButtonList.GuiResponder
{
    private boolean field_175338_A;
    private ChunkProviderSettings.Factory field_175334_E;
    private GuiButton field_175345_v;
    protected String field_175341_a;
    private ChunkProviderSettings.Factory field_175336_F;
    private boolean field_175340_C;
    private GuiButton field_175351_y;
    protected String field_175333_f;
    private GuiPageButtonList field_175349_r;
    private Random random;
    private Predicate field_175332_D;
    private GuiButton field_175344_w;
    private GuiButton field_175350_z;
    private GuiButton field_175348_s;
    private int field_175339_B;
    private GuiButton field_175352_x;
    private GuiButton field_175346_u;
    protected String[] field_175342_h;
    private GuiCreateWorld field_175343_i;
    private GuiButton field_175347_t;
    protected String field_175335_g;
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.field_175349_r.drawScreen(n, n2, n3);
        this.drawCenteredString(this.fontRendererObj, this.field_175341_a, GuiCustomizeWorldScreen.width / 2, 2, 16777215);
        this.drawCenteredString(this.fontRendererObj, this.field_175333_f, GuiCustomizeWorldScreen.width / 2, 12, 16777215);
        this.drawCenteredString(this.fontRendererObj, this.field_175335_g, GuiCustomizeWorldScreen.width / 2, 22, 16777215);
        super.drawScreen(n, n2, n3);
        if (this.field_175339_B != 0) {
            Gui.drawRect(0.0, 0.0, GuiCustomizeWorldScreen.width, GuiCustomizeWorldScreen.height, Integer.MIN_VALUE);
            this.drawHorizontalLine(GuiCustomizeWorldScreen.width / 2 - 91, GuiCustomizeWorldScreen.width / 2 + 90, 99, -2039584);
            this.drawHorizontalLine(GuiCustomizeWorldScreen.width / 2 - 91, GuiCustomizeWorldScreen.width / 2 + 90, 185, -6250336);
            this.drawVerticalLine(GuiCustomizeWorldScreen.width / 2 - 91, 99, 185, -2039584);
            this.drawVerticalLine(GuiCustomizeWorldScreen.width / 2 + 90, 99, 185, -6250336);
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            this.mc.getTextureManager().bindTexture(GuiCustomizeWorldScreen.optionsBackground);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldRenderer.pos(GuiCustomizeWorldScreen.width / 2 - 90, 185.0, 0.0).tex(0.0, 2.65625).color(64, 64, 64, 64).endVertex();
            worldRenderer.pos(GuiCustomizeWorldScreen.width / 2 + 90, 185.0, 0.0).tex(5.625, 2.65625).color(64, 64, 64, 64).endVertex();
            worldRenderer.pos(GuiCustomizeWorldScreen.width / 2 + 90, 100.0, 0.0).tex(5.625, 0.0).color(64, 64, 64, 64).endVertex();
            worldRenderer.pos(GuiCustomizeWorldScreen.width / 2 - 90, 100.0, 0.0).tex(0.0, 0.0).color(64, 64, 64, 64).endVertex();
            instance.draw();
            this.drawCenteredString(this.fontRendererObj, I18n.format("createWorld.customize.custom.confirmTitle", new Object[0]), GuiCustomizeWorldScreen.width / 2, 105, 16777215);
            this.drawCenteredString(this.fontRendererObj, I18n.format("createWorld.customize.custom.confirm1", new Object[0]), GuiCustomizeWorldScreen.width / 2, 125, 16777215);
            this.drawCenteredString(this.fontRendererObj, I18n.format("createWorld.customize.custom.confirm2", new Object[0]), GuiCustomizeWorldScreen.width / 2, 135, 16777215);
            this.field_175352_x.drawButton(this.mc, n, n2);
            this.field_175351_y.drawButton(this.mc, n, n2);
        }
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.field_175349_r.handleMouseInput();
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        if (this.field_175339_B == 0 && !this.field_175340_C) {
            this.field_175349_r.mouseClicked(n, n2, n3);
        }
    }
    
    @Override
    public void onTick(final int n, final float n2) {
        switch (n) {
            case 100: {
                this.field_175336_F.mainNoiseScaleX = n2;
                break;
            }
            case 101: {
                this.field_175336_F.mainNoiseScaleY = n2;
                break;
            }
            case 102: {
                this.field_175336_F.mainNoiseScaleZ = n2;
                break;
            }
            case 103: {
                this.field_175336_F.depthNoiseScaleX = n2;
                break;
            }
            case 104: {
                this.field_175336_F.depthNoiseScaleZ = n2;
                break;
            }
            case 105: {
                this.field_175336_F.depthNoiseScaleExponent = n2;
                break;
            }
            case 106: {
                this.field_175336_F.baseSize = n2;
                break;
            }
            case 107: {
                this.field_175336_F.coordinateScale = n2;
                break;
            }
            case 108: {
                this.field_175336_F.heightScale = n2;
                break;
            }
            case 109: {
                this.field_175336_F.stretchY = n2;
                break;
            }
            case 110: {
                this.field_175336_F.upperLimitScale = n2;
                break;
            }
            case 111: {
                this.field_175336_F.lowerLimitScale = n2;
                break;
            }
            case 112: {
                this.field_175336_F.biomeDepthWeight = n2;
                break;
            }
            case 113: {
                this.field_175336_F.biomeDepthOffset = n2;
                break;
            }
            case 114: {
                this.field_175336_F.biomeScaleWeight = n2;
                break;
            }
            case 115: {
                this.field_175336_F.biomeScaleOffset = n2;
                break;
            }
            case 157: {
                this.field_175336_F.dungeonChance = (int)n2;
                break;
            }
            case 158: {
                this.field_175336_F.waterLakeChance = (int)n2;
                break;
            }
            case 159: {
                this.field_175336_F.lavaLakeChance = (int)n2;
                break;
            }
            case 160: {
                this.field_175336_F.seaLevel = (int)n2;
                break;
            }
            case 162: {
                this.field_175336_F.fixedBiome = (int)n2;
                break;
            }
            case 163: {
                this.field_175336_F.biomeSize = (int)n2;
                break;
            }
            case 164: {
                this.field_175336_F.riverSize = (int)n2;
                break;
            }
            case 165: {
                this.field_175336_F.dirtSize = (int)n2;
                break;
            }
            case 166: {
                this.field_175336_F.dirtCount = (int)n2;
                break;
            }
            case 167: {
                this.field_175336_F.dirtMinHeight = (int)n2;
                break;
            }
            case 168: {
                this.field_175336_F.dirtMaxHeight = (int)n2;
                break;
            }
            case 169: {
                this.field_175336_F.gravelSize = (int)n2;
                break;
            }
            case 170: {
                this.field_175336_F.gravelCount = (int)n2;
                break;
            }
            case 171: {
                this.field_175336_F.gravelMinHeight = (int)n2;
                break;
            }
            case 172: {
                this.field_175336_F.gravelMaxHeight = (int)n2;
                break;
            }
            case 173: {
                this.field_175336_F.graniteSize = (int)n2;
                break;
            }
            case 174: {
                this.field_175336_F.graniteCount = (int)n2;
                break;
            }
            case 175: {
                this.field_175336_F.graniteMinHeight = (int)n2;
                break;
            }
            case 176: {
                this.field_175336_F.graniteMaxHeight = (int)n2;
                break;
            }
            case 177: {
                this.field_175336_F.dioriteSize = (int)n2;
                break;
            }
            case 178: {
                this.field_175336_F.dioriteCount = (int)n2;
                break;
            }
            case 179: {
                this.field_175336_F.dioriteMinHeight = (int)n2;
                break;
            }
            case 180: {
                this.field_175336_F.dioriteMaxHeight = (int)n2;
                break;
            }
            case 181: {
                this.field_175336_F.andesiteSize = (int)n2;
                break;
            }
            case 182: {
                this.field_175336_F.andesiteCount = (int)n2;
                break;
            }
            case 183: {
                this.field_175336_F.andesiteMinHeight = (int)n2;
                break;
            }
            case 184: {
                this.field_175336_F.andesiteMaxHeight = (int)n2;
                break;
            }
            case 185: {
                this.field_175336_F.coalSize = (int)n2;
                break;
            }
            case 186: {
                this.field_175336_F.coalCount = (int)n2;
                break;
            }
            case 187: {
                this.field_175336_F.coalMinHeight = (int)n2;
                break;
            }
            case 189: {
                this.field_175336_F.coalMaxHeight = (int)n2;
                break;
            }
            case 190: {
                this.field_175336_F.ironSize = (int)n2;
                break;
            }
            case 191: {
                this.field_175336_F.ironCount = (int)n2;
                break;
            }
            case 192: {
                this.field_175336_F.ironMinHeight = (int)n2;
                break;
            }
            case 193: {
                this.field_175336_F.ironMaxHeight = (int)n2;
                break;
            }
            case 194: {
                this.field_175336_F.goldSize = (int)n2;
                break;
            }
            case 195: {
                this.field_175336_F.goldCount = (int)n2;
                break;
            }
            case 196: {
                this.field_175336_F.goldMinHeight = (int)n2;
                break;
            }
            case 197: {
                this.field_175336_F.goldMaxHeight = (int)n2;
                break;
            }
            case 198: {
                this.field_175336_F.redstoneSize = (int)n2;
                break;
            }
            case 199: {
                this.field_175336_F.redstoneCount = (int)n2;
                break;
            }
            case 200: {
                this.field_175336_F.redstoneMinHeight = (int)n2;
                break;
            }
            case 201: {
                this.field_175336_F.redstoneMaxHeight = (int)n2;
                break;
            }
            case 202: {
                this.field_175336_F.diamondSize = (int)n2;
                break;
            }
            case 203: {
                this.field_175336_F.diamondCount = (int)n2;
                break;
            }
            case 204: {
                this.field_175336_F.diamondMinHeight = (int)n2;
                break;
            }
            case 205: {
                this.field_175336_F.diamondMaxHeight = (int)n2;
                break;
            }
            case 206: {
                this.field_175336_F.lapisSize = (int)n2;
                break;
            }
            case 207: {
                this.field_175336_F.lapisCount = (int)n2;
                break;
            }
            case 208: {
                this.field_175336_F.lapisCenterHeight = (int)n2;
                break;
            }
            case 209: {
                this.field_175336_F.lapisSpread = (int)n2;
                break;
            }
        }
        if (n >= 100 && n < 116) {
            final Gui func_178061_c = this.field_175349_r.func_178061_c(n - 100 + 132);
            if (func_178061_c != null) {
                ((GuiTextField)func_178061_c).setText(this.func_175330_b(n, n2));
            }
        }
        if (!this.field_175336_F.equals(this.field_175334_E)) {
            this.func_181031_a(true);
        }
    }
    
    private void func_175327_a(final float n) {
        final Gui func_178056_g = this.field_175349_r.func_178056_g();
        if (func_178056_g instanceof GuiTextField) {
            float n2 = n;
            if (GuiScreen.isShiftKeyDown()) {
                n2 = n * 0.1f;
                if (GuiScreen.isCtrlKeyDown()) {
                    n2 *= 0.1f;
                }
            }
            else if (GuiScreen.isCtrlKeyDown()) {
                n2 = n * 10.0f;
                if (GuiScreen.isAltKeyDown()) {
                    n2 *= 10.0f;
                }
            }
            final GuiTextField guiTextField = (GuiTextField)func_178056_g;
            final Float tryParse = Floats.tryParse(guiTextField.getText());
            if (tryParse != null) {
                final Float value = tryParse + n2;
                final int id = guiTextField.getId();
                final String func_175330_b = this.func_175330_b(guiTextField.getId(), value);
                guiTextField.setText(func_175330_b);
                this.func_175319_a(id, func_175330_b);
            }
        }
    }
    
    private void func_181031_a(final boolean b) {
        this.field_175338_A = b;
        this.field_175346_u.enabled = b;
    }
    
    private void func_175322_b(final int field_175339_B) {
        this.field_175339_B = field_175339_B;
        this.func_175329_a(true);
    }
    
    private String func_175330_b(final int n, final float n2) {
        switch (n) {
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 107:
            case 108:
            case 110:
            case 111:
            case 132:
            case 133:
            case 134:
            case 135:
            case 136:
            case 139:
            case 140:
            case 142:
            case 143: {
                return String.format("%5.3f", n2);
            }
            case 105:
            case 106:
            case 109:
            case 112:
            case 113:
            case 114:
            case 115:
            case 137:
            case 138:
            case 141:
            case 144:
            case 145:
            case 146:
            case 147: {
                return String.format("%2.3f", n2);
            }
            default: {
                return String.format("%d", (int)n2);
            }
            case 162: {
                if (n2 < 0.0f) {
                    return I18n.format("gui.all", new Object[0]);
                }
                if ((int)n2 >= BiomeGenBase.hell.biomeID) {
                    final BiomeGenBase biomeGenBase = BiomeGenBase.getBiomeGenArray()[(int)n2 + 2];
                    return (biomeGenBase != null) ? biomeGenBase.biomeName : "?";
                }
                final BiomeGenBase biomeGenBase2 = BiomeGenBase.getBiomeGenArray()[(int)n2];
                return (biomeGenBase2 != null) ? biomeGenBase2.biomeName : "?";
            }
        }
    }
    
    @Override
    protected void mouseReleased(final int n, final int n2, final int n3) {
        super.mouseReleased(n, n2, n3);
        if (this.field_175340_C) {
            this.field_175340_C = false;
        }
        else if (this.field_175339_B == 0) {
            this.field_175349_r.mouseReleased(n, n2, n3);
        }
    }
    
    @Override
    public void func_175321_a(final int n, final boolean useMonuments) {
        switch (n) {
            case 148: {
                this.field_175336_F.useCaves = useMonuments;
                break;
            }
            case 149: {
                this.field_175336_F.useDungeons = useMonuments;
                break;
            }
            case 150: {
                this.field_175336_F.useStrongholds = useMonuments;
                break;
            }
            case 151: {
                this.field_175336_F.useVillages = useMonuments;
                break;
            }
            case 152: {
                this.field_175336_F.useMineShafts = useMonuments;
                break;
            }
            case 153: {
                this.field_175336_F.useTemples = useMonuments;
                break;
            }
            case 154: {
                this.field_175336_F.useRavines = useMonuments;
                break;
            }
            case 155: {
                this.field_175336_F.useWaterLakes = useMonuments;
                break;
            }
            case 156: {
                this.field_175336_F.useLavaLakes = useMonuments;
                break;
            }
            case 161: {
                this.field_175336_F.useLavaOceans = useMonuments;
                break;
            }
            case 210: {
                this.field_175336_F.useMonuments = useMonuments;
                break;
            }
        }
        if (!this.field_175336_F.equals(this.field_175334_E)) {
            this.func_181031_a(true);
        }
    }
    
    @Override
    public String getText(final int n, final String s, final float n2) {
        return s + ": " + this.func_175330_b(n, n2);
    }
    
    private void func_175329_a(final boolean b) {
        this.field_175352_x.visible = b;
        this.field_175351_y.visible = b;
        this.field_175347_t.enabled = !b;
        this.field_175348_s.enabled = !b;
        this.field_175345_v.enabled = !b;
        this.field_175344_w.enabled = !b;
        this.field_175346_u.enabled = (this.field_175338_A && !b);
        this.field_175350_z.enabled = !b;
        this.field_175349_r.func_181155_a(!b);
    }
    
    private void func_175331_h() throws IOException {
        switch (this.field_175339_B) {
            case 300: {
                this.actionPerformed((GuiButton)this.field_175349_r.func_178061_c(300));
                break;
            }
            case 304: {
                this.func_175326_g();
                break;
            }
        }
        this.field_175339_B = 0;
        this.field_175340_C = true;
        this.func_175329_a(false);
    }
    
    private void func_175328_i() {
        this.field_175345_v.enabled = (this.field_175349_r.func_178059_e() != 0);
        this.field_175344_w.enabled = (this.field_175349_r.func_178059_e() != this.field_175349_r.func_178057_f() - 1);
        this.field_175333_f = I18n.format("book.pageIndicator", this.field_175349_r.func_178059_e() + 1, this.field_175349_r.func_178057_f());
        this.field_175335_g = this.field_175342_h[this.field_175349_r.func_178059_e()];
        this.field_175347_t.enabled = (this.field_175349_r.func_178059_e() != this.field_175349_r.func_178057_f() - 1);
    }
    
    public void func_175324_a(final String s) {
        if (s != null && s.length() != 0) {
            this.field_175336_F = ChunkProviderSettings.Factory.jsonToFactory(s);
        }
        else {
            this.field_175336_F = new ChunkProviderSettings.Factory();
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        super.keyTyped(c, n);
        if (this.field_175339_B == 0) {
            switch (n) {
                case 200: {
                    this.func_175327_a(1.0f);
                    break;
                }
                case 208: {
                    this.func_175327_a(-1.0f);
                    break;
                }
                default: {
                    this.field_175349_r.func_178062_a(c, n);
                    break;
                }
            }
        }
    }
    
    @Override
    public void initGui() {
        if (this.field_175349_r != null) {
            this.field_175349_r.func_178059_e();
            this.field_175349_r.getAmountScrolled();
        }
        this.field_175341_a = I18n.format("options.customizeTitle", new Object[0]);
        this.buttonList.clear();
        this.buttonList.add(this.field_175345_v = new GuiButton(302, 20, 5, 80, 20, I18n.format("createWorld.customize.custom.prev", new Object[0])));
        this.buttonList.add(this.field_175344_w = new GuiButton(303, GuiCustomizeWorldScreen.width - 100, 5, 80, 20, I18n.format("createWorld.customize.custom.next", new Object[0])));
        this.buttonList.add(this.field_175346_u = new GuiButton(304, GuiCustomizeWorldScreen.width / 2 - 187, GuiCustomizeWorldScreen.height - 27, 90, 20, I18n.format("createWorld.customize.custom.defaults", new Object[0])));
        this.buttonList.add(this.field_175347_t = new GuiButton(301, GuiCustomizeWorldScreen.width / 2 - 92, GuiCustomizeWorldScreen.height - 27, 90, 20, I18n.format("createWorld.customize.custom.randomize", new Object[0])));
        this.buttonList.add(this.field_175350_z = new GuiButton(305, GuiCustomizeWorldScreen.width / 2 + 3, GuiCustomizeWorldScreen.height - 27, 90, 20, I18n.format("createWorld.customize.custom.presets", new Object[0])));
        this.buttonList.add(this.field_175348_s = new GuiButton(300, GuiCustomizeWorldScreen.width / 2 + 98, GuiCustomizeWorldScreen.height - 27, 90, 20, I18n.format("gui.done", new Object[0])));
        this.field_175346_u.enabled = this.field_175338_A;
        this.field_175352_x = new GuiButton(306, GuiCustomizeWorldScreen.width / 2 - 55, 160, 50, 20, I18n.format("gui.yes", new Object[0]));
        this.field_175352_x.visible = false;
        this.buttonList.add(this.field_175352_x);
        this.field_175351_y = new GuiButton(307, GuiCustomizeWorldScreen.width / 2 + 5, 160, 50, 20, I18n.format("gui.no", new Object[0]));
        this.field_175351_y.visible = false;
        this.buttonList.add(this.field_175351_y);
        if (this.field_175339_B != 0) {
            this.field_175352_x.visible = true;
            this.field_175351_y.visible = true;
        }
        this.func_175325_f();
    }
    
    public GuiCustomizeWorldScreen(final GuiScreen guiScreen, final String s) {
        this.field_175341_a = "Customize World Settings";
        this.field_175333_f = "Page 1 of 3";
        this.field_175335_g = "Basic Settings";
        this.field_175342_h = new String[4];
        this.field_175338_A = false;
        this.field_175339_B = 0;
        this.field_175340_C = false;
        this.field_175332_D = (Predicate)new Predicate() {
            final GuiCustomizeWorldScreen this$0;
            
            public boolean apply(final String s) {
                final Float tryParse = Floats.tryParse(s);
                return s.length() == 0 || (tryParse != null && Floats.isFinite((float)tryParse) && tryParse >= 0.0f);
            }
            
            public boolean apply(final Object o) {
                return this.apply((String)o);
            }
        };
        this.field_175334_E = new ChunkProviderSettings.Factory();
        this.random = new Random();
        this.field_175343_i = (GuiCreateWorld)guiScreen;
        this.func_175324_a(s);
    }
    
    private void func_175325_f() {
        this.field_175349_r = new GuiPageButtonList(this.mc, GuiCustomizeWorldScreen.width, GuiCustomizeWorldScreen.height, 32, GuiCustomizeWorldScreen.height - 32, 25, this, new GuiPageButtonList.GuiListEntry[][] { { new GuiPageButtonList.GuiSlideEntry(160, I18n.format("createWorld.customize.custom.seaLevel", new Object[0]), true, this, 1.0f, 255.0f, (float)this.field_175336_F.seaLevel), new GuiPageButtonList.GuiButtonEntry(148, I18n.format("createWorld.customize.custom.useCaves", new Object[0]), true, this.field_175336_F.useCaves), new GuiPageButtonList.GuiButtonEntry(150, I18n.format("createWorld.customize.custom.useStrongholds", new Object[0]), true, this.field_175336_F.useStrongholds), new GuiPageButtonList.GuiButtonEntry(151, I18n.format("createWorld.customize.custom.useVillages", new Object[0]), true, this.field_175336_F.useVillages), new GuiPageButtonList.GuiButtonEntry(152, I18n.format("createWorld.customize.custom.useMineShafts", new Object[0]), true, this.field_175336_F.useMineShafts), new GuiPageButtonList.GuiButtonEntry(153, I18n.format("createWorld.customize.custom.useTemples", new Object[0]), true, this.field_175336_F.useTemples), new GuiPageButtonList.GuiButtonEntry(210, I18n.format("createWorld.customize.custom.useMonuments", new Object[0]), true, this.field_175336_F.useMonuments), new GuiPageButtonList.GuiButtonEntry(154, I18n.format("createWorld.customize.custom.useRavines", new Object[0]), true, this.field_175336_F.useRavines), new GuiPageButtonList.GuiButtonEntry(149, I18n.format("createWorld.customize.custom.useDungeons", new Object[0]), true, this.field_175336_F.useDungeons), new GuiPageButtonList.GuiSlideEntry(157, I18n.format("createWorld.customize.custom.dungeonChance", new Object[0]), true, this, 1.0f, 100.0f, (float)this.field_175336_F.dungeonChance), new GuiPageButtonList.GuiButtonEntry(155, I18n.format("createWorld.customize.custom.useWaterLakes", new Object[0]), true, this.field_175336_F.useWaterLakes), new GuiPageButtonList.GuiSlideEntry(158, I18n.format("createWorld.customize.custom.waterLakeChance", new Object[0]), true, this, 1.0f, 100.0f, (float)this.field_175336_F.waterLakeChance), new GuiPageButtonList.GuiButtonEntry(156, I18n.format("createWorld.customize.custom.useLavaLakes", new Object[0]), true, this.field_175336_F.useLavaLakes), new GuiPageButtonList.GuiSlideEntry(159, I18n.format("createWorld.customize.custom.lavaLakeChance", new Object[0]), true, this, 10.0f, 100.0f, (float)this.field_175336_F.lavaLakeChance), new GuiPageButtonList.GuiButtonEntry(161, I18n.format("createWorld.customize.custom.useLavaOceans", new Object[0]), true, this.field_175336_F.useLavaOceans), new GuiPageButtonList.GuiSlideEntry(162, I18n.format("createWorld.customize.custom.fixedBiome", new Object[0]), true, this, -1.0f, 37.0f, (float)this.field_175336_F.fixedBiome), new GuiPageButtonList.GuiSlideEntry(163, I18n.format("createWorld.customize.custom.biomeSize", new Object[0]), true, this, 1.0f, 8.0f, (float)this.field_175336_F.biomeSize), new GuiPageButtonList.GuiSlideEntry(164, I18n.format("createWorld.customize.custom.riverSize", new Object[0]), true, this, 1.0f, 5.0f, (float)this.field_175336_F.riverSize) }, { new GuiPageButtonList.GuiLabelEntry(416, I18n.format("tile.dirt.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(165, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, (float)this.field_175336_F.dirtSize), new GuiPageButtonList.GuiSlideEntry(166, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, (float)this.field_175336_F.dirtCount), new GuiPageButtonList.GuiSlideEntry(167, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.dirtMinHeight), new GuiPageButtonList.GuiSlideEntry(168, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.dirtMaxHeight), new GuiPageButtonList.GuiLabelEntry(417, I18n.format("tile.gravel.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(169, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, (float)this.field_175336_F.gravelSize), new GuiPageButtonList.GuiSlideEntry(170, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, (float)this.field_175336_F.gravelCount), new GuiPageButtonList.GuiSlideEntry(171, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.gravelMinHeight), new GuiPageButtonList.GuiSlideEntry(172, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.gravelMaxHeight), new GuiPageButtonList.GuiLabelEntry(418, I18n.format("tile.stone.granite.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(173, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, (float)this.field_175336_F.graniteSize), new GuiPageButtonList.GuiSlideEntry(174, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, (float)this.field_175336_F.graniteCount), new GuiPageButtonList.GuiSlideEntry(175, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.graniteMinHeight), new GuiPageButtonList.GuiSlideEntry(176, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.graniteMaxHeight), new GuiPageButtonList.GuiLabelEntry(419, I18n.format("tile.stone.diorite.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(177, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, (float)this.field_175336_F.dioriteSize), new GuiPageButtonList.GuiSlideEntry(178, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, (float)this.field_175336_F.dioriteCount), new GuiPageButtonList.GuiSlideEntry(179, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.dioriteMinHeight), new GuiPageButtonList.GuiSlideEntry(180, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.dioriteMaxHeight), new GuiPageButtonList.GuiLabelEntry(420, I18n.format("tile.stone.andesite.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(181, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, (float)this.field_175336_F.andesiteSize), new GuiPageButtonList.GuiSlideEntry(182, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, (float)this.field_175336_F.andesiteCount), new GuiPageButtonList.GuiSlideEntry(183, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.andesiteMinHeight), new GuiPageButtonList.GuiSlideEntry(184, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.andesiteMaxHeight), new GuiPageButtonList.GuiLabelEntry(421, I18n.format("tile.oreCoal.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(185, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, (float)this.field_175336_F.coalSize), new GuiPageButtonList.GuiSlideEntry(186, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, (float)this.field_175336_F.coalCount), new GuiPageButtonList.GuiSlideEntry(187, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.coalMinHeight), new GuiPageButtonList.GuiSlideEntry(189, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.coalMaxHeight), new GuiPageButtonList.GuiLabelEntry(422, I18n.format("tile.oreIron.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(190, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, (float)this.field_175336_F.ironSize), new GuiPageButtonList.GuiSlideEntry(191, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, (float)this.field_175336_F.ironCount), new GuiPageButtonList.GuiSlideEntry(192, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.ironMinHeight), new GuiPageButtonList.GuiSlideEntry(193, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.ironMaxHeight), new GuiPageButtonList.GuiLabelEntry(423, I18n.format("tile.oreGold.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(194, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, (float)this.field_175336_F.goldSize), new GuiPageButtonList.GuiSlideEntry(195, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, (float)this.field_175336_F.goldCount), new GuiPageButtonList.GuiSlideEntry(196, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.goldMinHeight), new GuiPageButtonList.GuiSlideEntry(197, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.goldMaxHeight), new GuiPageButtonList.GuiLabelEntry(424, I18n.format("tile.oreRedstone.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(198, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, (float)this.field_175336_F.redstoneSize), new GuiPageButtonList.GuiSlideEntry(199, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, (float)this.field_175336_F.redstoneCount), new GuiPageButtonList.GuiSlideEntry(200, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.redstoneMinHeight), new GuiPageButtonList.GuiSlideEntry(201, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.redstoneMaxHeight), new GuiPageButtonList.GuiLabelEntry(425, I18n.format("tile.oreDiamond.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(202, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, (float)this.field_175336_F.diamondSize), new GuiPageButtonList.GuiSlideEntry(203, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, (float)this.field_175336_F.diamondCount), new GuiPageButtonList.GuiSlideEntry(204, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.diamondMinHeight), new GuiPageButtonList.GuiSlideEntry(205, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.diamondMaxHeight), new GuiPageButtonList.GuiLabelEntry(426, I18n.format("tile.oreLapis.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(206, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0f, 50.0f, (float)this.field_175336_F.lapisSize), new GuiPageButtonList.GuiSlideEntry(207, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0f, 40.0f, (float)this.field_175336_F.lapisCount), new GuiPageButtonList.GuiSlideEntry(208, I18n.format("createWorld.customize.custom.center", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.lapisCenterHeight), new GuiPageButtonList.GuiSlideEntry(209, I18n.format("createWorld.customize.custom.spread", new Object[0]), false, this, 0.0f, 255.0f, (float)this.field_175336_F.lapisSpread) }, { new GuiPageButtonList.GuiSlideEntry(100, I18n.format("createWorld.customize.custom.mainNoiseScaleX", new Object[0]), false, this, 1.0f, 5000.0f, this.field_175336_F.mainNoiseScaleX), new GuiPageButtonList.GuiSlideEntry(101, I18n.format("createWorld.customize.custom.mainNoiseScaleY", new Object[0]), false, this, 1.0f, 5000.0f, this.field_175336_F.mainNoiseScaleY), new GuiPageButtonList.GuiSlideEntry(102, I18n.format("createWorld.customize.custom.mainNoiseScaleZ", new Object[0]), false, this, 1.0f, 5000.0f, this.field_175336_F.mainNoiseScaleZ), new GuiPageButtonList.GuiSlideEntry(103, I18n.format("createWorld.customize.custom.depthNoiseScaleX", new Object[0]), false, this, 1.0f, 2000.0f, this.field_175336_F.depthNoiseScaleX), new GuiPageButtonList.GuiSlideEntry(104, I18n.format("createWorld.customize.custom.depthNoiseScaleZ", new Object[0]), false, this, 1.0f, 2000.0f, this.field_175336_F.depthNoiseScaleZ), new GuiPageButtonList.GuiSlideEntry(105, I18n.format("createWorld.customize.custom.depthNoiseScaleExponent", new Object[0]), false, this, 0.01f, 20.0f, this.field_175336_F.depthNoiseScaleExponent), new GuiPageButtonList.GuiSlideEntry(106, I18n.format("createWorld.customize.custom.baseSize", new Object[0]), false, this, 1.0f, 25.0f, this.field_175336_F.baseSize), new GuiPageButtonList.GuiSlideEntry(107, I18n.format("createWorld.customize.custom.coordinateScale", new Object[0]), false, this, 1.0f, 6000.0f, this.field_175336_F.coordinateScale), new GuiPageButtonList.GuiSlideEntry(108, I18n.format("createWorld.customize.custom.heightScale", new Object[0]), false, this, 1.0f, 6000.0f, this.field_175336_F.heightScale), new GuiPageButtonList.GuiSlideEntry(109, I18n.format("createWorld.customize.custom.stretchY", new Object[0]), false, this, 0.01f, 50.0f, this.field_175336_F.stretchY), new GuiPageButtonList.GuiSlideEntry(110, I18n.format("createWorld.customize.custom.upperLimitScale", new Object[0]), false, this, 1.0f, 5000.0f, this.field_175336_F.upperLimitScale), new GuiPageButtonList.GuiSlideEntry(111, I18n.format("createWorld.customize.custom.lowerLimitScale", new Object[0]), false, this, 1.0f, 5000.0f, this.field_175336_F.lowerLimitScale), new GuiPageButtonList.GuiSlideEntry(112, I18n.format("createWorld.customize.custom.biomeDepthWeight", new Object[0]), false, this, 1.0f, 20.0f, this.field_175336_F.biomeDepthWeight), new GuiPageButtonList.GuiSlideEntry(113, I18n.format("createWorld.customize.custom.biomeDepthOffset", new Object[0]), false, this, 0.0f, 20.0f, this.field_175336_F.biomeDepthOffset), new GuiPageButtonList.GuiSlideEntry(114, I18n.format("createWorld.customize.custom.biomeScaleWeight", new Object[0]), false, this, 1.0f, 20.0f, this.field_175336_F.biomeScaleWeight), new GuiPageButtonList.GuiSlideEntry(115, I18n.format("createWorld.customize.custom.biomeScaleOffset", new Object[0]), false, this, 0.0f, 20.0f, this.field_175336_F.biomeScaleOffset) }, { new GuiPageButtonList.GuiLabelEntry(400, I18n.format("createWorld.customize.custom.mainNoiseScaleX", new Object[0]) + ":", false), new GuiPageButtonList.EditBoxEntry(132, String.format("%5.3f", this.field_175336_F.mainNoiseScaleX), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(401, I18n.format("createWorld.customize.custom.mainNoiseScaleY", new Object[0]) + ":", false), new GuiPageButtonList.EditBoxEntry(133, String.format("%5.3f", this.field_175336_F.mainNoiseScaleY), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(402, I18n.format("createWorld.customize.custom.mainNoiseScaleZ", new Object[0]) + ":", false), new GuiPageButtonList.EditBoxEntry(134, String.format("%5.3f", this.field_175336_F.mainNoiseScaleZ), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(403, I18n.format("createWorld.customize.custom.depthNoiseScaleX", new Object[0]) + ":", false), new GuiPageButtonList.EditBoxEntry(135, String.format("%5.3f", this.field_175336_F.depthNoiseScaleX), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(404, I18n.format("createWorld.customize.custom.depthNoiseScaleZ", new Object[0]) + ":", false), new GuiPageButtonList.EditBoxEntry(136, String.format("%5.3f", this.field_175336_F.depthNoiseScaleZ), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(405, I18n.format("createWorld.customize.custom.depthNoiseScaleExponent", new Object[0]) + ":", false), new GuiPageButtonList.EditBoxEntry(137, String.format("%2.3f", this.field_175336_F.depthNoiseScaleExponent), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(406, I18n.format("createWorld.customize.custom.baseSize", new Object[0]) + ":", false), new GuiPageButtonList.EditBoxEntry(138, String.format("%2.3f", this.field_175336_F.baseSize), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(407, I18n.format("createWorld.customize.custom.coordinateScale", new Object[0]) + ":", false), new GuiPageButtonList.EditBoxEntry(139, String.format("%5.3f", this.field_175336_F.coordinateScale), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(408, I18n.format("createWorld.customize.custom.heightScale", new Object[0]) + ":", false), new GuiPageButtonList.EditBoxEntry(140, String.format("%5.3f", this.field_175336_F.heightScale), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(409, I18n.format("createWorld.customize.custom.stretchY", new Object[0]) + ":", false), new GuiPageButtonList.EditBoxEntry(141, String.format("%2.3f", this.field_175336_F.stretchY), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(410, I18n.format("createWorld.customize.custom.upperLimitScale", new Object[0]) + ":", false), new GuiPageButtonList.EditBoxEntry(142, String.format("%5.3f", this.field_175336_F.upperLimitScale), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(411, I18n.format("createWorld.customize.custom.lowerLimitScale", new Object[0]) + ":", false), new GuiPageButtonList.EditBoxEntry(143, String.format("%5.3f", this.field_175336_F.lowerLimitScale), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(412, I18n.format("createWorld.customize.custom.biomeDepthWeight", new Object[0]) + ":", false), new GuiPageButtonList.EditBoxEntry(144, String.format("%2.3f", this.field_175336_F.biomeDepthWeight), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(413, I18n.format("createWorld.customize.custom.biomeDepthOffset", new Object[0]) + ":", false), new GuiPageButtonList.EditBoxEntry(145, String.format("%2.3f", this.field_175336_F.biomeDepthOffset), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(414, I18n.format("createWorld.customize.custom.biomeScaleWeight", new Object[0]) + ":", false), new GuiPageButtonList.EditBoxEntry(146, String.format("%2.3f", this.field_175336_F.biomeScaleWeight), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(415, I18n.format("createWorld.customize.custom.biomeScaleOffset", new Object[0]) + ":", false), new GuiPageButtonList.EditBoxEntry(147, String.format("%2.3f", this.field_175336_F.biomeScaleOffset), false, this.field_175332_D) } });
        while (true) {
            this.field_175342_h[0] = I18n.format("createWorld.customize.custom.page" + 0, new Object[0]);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            switch (guiButton.id) {
                case 300: {
                    this.field_175343_i.chunkProviderSettingsJson = this.field_175336_F.toString();
                    this.mc.displayGuiScreen(this.field_175343_i);
                    break;
                }
                case 301: {
                    while (0 < this.field_175349_r.getSize()) {
                        final GuiPageButtonList.GuiEntry listEntry = this.field_175349_r.getListEntry(0);
                        final Gui func_178022_a = listEntry.func_178022_a();
                        if (func_178022_a instanceof GuiButton) {
                            final GuiButton guiButton2 = (GuiButton)func_178022_a;
                            if (guiButton2 instanceof GuiSlider) {
                                ((GuiSlider)guiButton2).func_175219_a(MathHelper.clamp_float(((GuiSlider)guiButton2).func_175217_d() * (0.75f + this.random.nextFloat() * 0.5f) + (this.random.nextFloat() * 0.1f - 0.05f), 0.0f, 1.0f));
                            }
                            else if (guiButton2 instanceof GuiListButton) {
                                ((GuiListButton)guiButton2).func_175212_b(this.random.nextBoolean());
                            }
                        }
                        final Gui func_178021_b = listEntry.func_178021_b();
                        if (func_178021_b instanceof GuiButton) {
                            final GuiButton guiButton3 = (GuiButton)func_178021_b;
                            if (guiButton3 instanceof GuiSlider) {
                                ((GuiSlider)guiButton3).func_175219_a(MathHelper.clamp_float(((GuiSlider)guiButton3).func_175217_d() * (0.75f + this.random.nextFloat() * 0.5f) + (this.random.nextFloat() * 0.1f - 0.05f), 0.0f, 1.0f));
                            }
                            else if (guiButton3 instanceof GuiListButton) {
                                ((GuiListButton)guiButton3).func_175212_b(this.random.nextBoolean());
                            }
                        }
                        int n = 0;
                        ++n;
                    }
                }
                case 302: {
                    this.field_175349_r.func_178071_h();
                    this.func_175328_i();
                    break;
                }
                case 303: {
                    this.field_175349_r.func_178064_i();
                    this.func_175328_i();
                    break;
                }
                case 304: {
                    if (this.field_175338_A) {
                        this.func_175322_b(304);
                        break;
                    }
                    break;
                }
                case 305: {
                    this.mc.displayGuiScreen(new GuiScreenCustomizePresets(this));
                    break;
                }
                case 306: {
                    this.func_175331_h();
                    break;
                }
                case 307: {
                    this.field_175339_B = 0;
                    this.func_175331_h();
                    break;
                }
            }
        }
    }
    
    @Override
    public void func_175319_a(final int n, final String s) {
        final float float1 = Float.parseFloat(s);
        float n2 = 0.0f;
        switch (n) {
            case 132: {
                final ChunkProviderSettings.Factory field_175336_F = this.field_175336_F;
                final float clamp_float = MathHelper.clamp_float(float1, 1.0f, 5000.0f);
                field_175336_F.mainNoiseScaleX = clamp_float;
                n2 = clamp_float;
                break;
            }
            case 133: {
                final ChunkProviderSettings.Factory field_175336_F2 = this.field_175336_F;
                final float clamp_float2 = MathHelper.clamp_float(float1, 1.0f, 5000.0f);
                field_175336_F2.mainNoiseScaleY = clamp_float2;
                n2 = clamp_float2;
                break;
            }
            case 134: {
                final ChunkProviderSettings.Factory field_175336_F3 = this.field_175336_F;
                final float clamp_float3 = MathHelper.clamp_float(float1, 1.0f, 5000.0f);
                field_175336_F3.mainNoiseScaleZ = clamp_float3;
                n2 = clamp_float3;
                break;
            }
            case 135: {
                final ChunkProviderSettings.Factory field_175336_F4 = this.field_175336_F;
                final float clamp_float4 = MathHelper.clamp_float(float1, 1.0f, 2000.0f);
                field_175336_F4.depthNoiseScaleX = clamp_float4;
                n2 = clamp_float4;
                break;
            }
            case 136: {
                final ChunkProviderSettings.Factory field_175336_F5 = this.field_175336_F;
                final float clamp_float5 = MathHelper.clamp_float(float1, 1.0f, 2000.0f);
                field_175336_F5.depthNoiseScaleZ = clamp_float5;
                n2 = clamp_float5;
                break;
            }
            case 137: {
                final ChunkProviderSettings.Factory field_175336_F6 = this.field_175336_F;
                final float clamp_float6 = MathHelper.clamp_float(float1, 0.01f, 20.0f);
                field_175336_F6.depthNoiseScaleExponent = clamp_float6;
                n2 = clamp_float6;
                break;
            }
            case 138: {
                final ChunkProviderSettings.Factory field_175336_F7 = this.field_175336_F;
                final float clamp_float7 = MathHelper.clamp_float(float1, 1.0f, 25.0f);
                field_175336_F7.baseSize = clamp_float7;
                n2 = clamp_float7;
                break;
            }
            case 139: {
                final ChunkProviderSettings.Factory field_175336_F8 = this.field_175336_F;
                final float clamp_float8 = MathHelper.clamp_float(float1, 1.0f, 6000.0f);
                field_175336_F8.coordinateScale = clamp_float8;
                n2 = clamp_float8;
                break;
            }
            case 140: {
                final ChunkProviderSettings.Factory field_175336_F9 = this.field_175336_F;
                final float clamp_float9 = MathHelper.clamp_float(float1, 1.0f, 6000.0f);
                field_175336_F9.heightScale = clamp_float9;
                n2 = clamp_float9;
                break;
            }
            case 141: {
                final ChunkProviderSettings.Factory field_175336_F10 = this.field_175336_F;
                final float clamp_float10 = MathHelper.clamp_float(float1, 0.01f, 50.0f);
                field_175336_F10.stretchY = clamp_float10;
                n2 = clamp_float10;
                break;
            }
            case 142: {
                final ChunkProviderSettings.Factory field_175336_F11 = this.field_175336_F;
                final float clamp_float11 = MathHelper.clamp_float(float1, 1.0f, 5000.0f);
                field_175336_F11.upperLimitScale = clamp_float11;
                n2 = clamp_float11;
                break;
            }
            case 143: {
                final ChunkProviderSettings.Factory field_175336_F12 = this.field_175336_F;
                final float clamp_float12 = MathHelper.clamp_float(float1, 1.0f, 5000.0f);
                field_175336_F12.lowerLimitScale = clamp_float12;
                n2 = clamp_float12;
                break;
            }
            case 144: {
                final ChunkProviderSettings.Factory field_175336_F13 = this.field_175336_F;
                final float clamp_float13 = MathHelper.clamp_float(float1, 1.0f, 20.0f);
                field_175336_F13.biomeDepthWeight = clamp_float13;
                n2 = clamp_float13;
                break;
            }
            case 145: {
                final ChunkProviderSettings.Factory field_175336_F14 = this.field_175336_F;
                final float clamp_float14 = MathHelper.clamp_float(float1, 0.0f, 20.0f);
                field_175336_F14.biomeDepthOffset = clamp_float14;
                n2 = clamp_float14;
                break;
            }
            case 146: {
                final ChunkProviderSettings.Factory field_175336_F15 = this.field_175336_F;
                final float clamp_float15 = MathHelper.clamp_float(float1, 1.0f, 20.0f);
                field_175336_F15.biomeScaleWeight = clamp_float15;
                n2 = clamp_float15;
                break;
            }
            case 147: {
                final ChunkProviderSettings.Factory field_175336_F16 = this.field_175336_F;
                final float clamp_float16 = MathHelper.clamp_float(float1, 0.0f, 20.0f);
                field_175336_F16.biomeScaleOffset = clamp_float16;
                n2 = clamp_float16;
                break;
            }
        }
        if (n2 != float1 && float1 != 0.0f) {
            ((GuiTextField)this.field_175349_r.func_178061_c(n)).setText(this.func_175330_b(n, n2));
        }
        ((GuiSlider)this.field_175349_r.func_178061_c(n - 132 + 100)).func_175218_a(n2, false);
        if (!this.field_175336_F.equals(this.field_175334_E)) {
            this.func_181031_a(true);
        }
    }
    
    private void func_175326_g() {
        this.field_175336_F.func_177863_a();
        this.func_175325_f();
        this.func_181031_a(false);
    }
    
    public String func_175323_a() {
        return this.field_175336_F.toString().replace("\n", "");
    }
}
