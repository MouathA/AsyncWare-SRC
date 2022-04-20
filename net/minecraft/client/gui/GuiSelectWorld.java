package net.minecraft.client.gui;

import org.apache.logging.log4j.*;
import java.io.*;
import net.minecraft.client.resources.*;
import net.minecraft.world.*;
import java.text.*;
import net.minecraft.world.storage.*;
import org.apache.commons.lang3.*;
import net.minecraft.client.*;
import java.util.*;
import net.minecraft.util.*;

public class GuiSelectWorld extends GuiScreen implements GuiYesNoCallback
{
    private List field_146638_t;
    private GuiButton renameButton;
    private boolean field_146643_x;
    private static final Logger logger;
    private int field_146640_r;
    private String field_146637_u;
    private java.util.List field_146639_s;
    private GuiButton recreateButton;
    private final DateFormat field_146633_h;
    private String field_146636_v;
    protected String field_146628_f;
    private GuiButton deleteButton;
    private String[] field_146635_w;
    protected GuiScreen parentScreen;
    private GuiButton selectButton;
    private boolean field_146634_i;
    
    static String access$600(final GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.field_146637_u;
    }
    
    static java.util.List access$000(final GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.field_146639_s;
    }
    
    static GuiButton access$200(final GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.selectButton;
    }
    
    static {
        logger = LogManager.getLogger();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == 2) {
                final String func_146614_d = this.func_146614_d(this.field_146640_r);
                if (func_146614_d != null) {
                    this.field_146643_x = true;
                    this.mc.displayGuiScreen(func_152129_a(this, func_146614_d, this.field_146640_r));
                }
            }
            else if (guiButton.id == 1) {
                this.func_146615_e(this.field_146640_r);
            }
            else if (guiButton.id == 3) {
                this.mc.displayGuiScreen(new GuiCreateWorld(this));
            }
            else if (guiButton.id == 6) {
                this.mc.displayGuiScreen(new GuiRenameWorld(this, this.func_146621_a(this.field_146640_r)));
            }
            else if (guiButton.id == 0) {
                this.mc.displayGuiScreen(this.parentScreen);
            }
            else if (guiButton.id == 7) {
                final GuiCreateWorld guiCreateWorld = new GuiCreateWorld(this);
                final ISaveHandler saveLoader = this.mc.getSaveLoader().getSaveLoader(this.func_146621_a(this.field_146640_r), false);
                final WorldInfo loadWorldInfo = saveLoader.loadWorldInfo();
                saveLoader.flush();
                guiCreateWorld.func_146318_a(loadWorldInfo);
                this.mc.displayGuiScreen(guiCreateWorld);
            }
            else {
                this.field_146638_t.actionPerformed(guiButton);
            }
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.field_146638_t.drawScreen(n, n2, n3);
        this.drawCenteredString(this.fontRendererObj, this.field_146628_f, GuiSelectWorld.width / 2, 20, 16777215);
        super.drawScreen(n, n2, n3);
    }
    
    static GuiButton access$500(final GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.recreateButton;
    }
    
    static String[] access$900(final GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.field_146635_w;
    }
    
    static GuiButton access$400(final GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.renameButton;
    }
    
    static DateFormat access$700(final GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.field_146633_h;
    }
    
    @Override
    public void confirmClicked(final boolean b, final int n) {
        if (this.field_146643_x) {
            this.field_146643_x = false;
            if (b) {
                final ISaveFormat saveLoader = this.mc.getSaveLoader();
                saveLoader.flushCache();
                saveLoader.deleteWorldDirectory(this.func_146621_a(n));
                this.func_146627_h();
            }
            this.mc.displayGuiScreen(this);
        }
    }
    
    static int access$102(final GuiSelectWorld guiSelectWorld, final int field_146640_r) {
        return guiSelectWorld.field_146640_r = field_146640_r;
    }
    
    public static GuiYesNo func_152129_a(final GuiYesNoCallback guiYesNoCallback, final String s, final int n) {
        return new GuiYesNo(guiYesNoCallback, I18n.format("selectWorld.deleteQuestion", new Object[0]), "'" + s + "' " + I18n.format("selectWorld.deleteWarning", new Object[0]), I18n.format("selectWorld.deleteButton", new Object[0]), I18n.format("gui.cancel", new Object[0]), n);
    }
    
    static int access$100(final GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.field_146640_r;
    }
    
    static GuiButton access$300(final GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.deleteButton;
    }
    
    @Override
    public void initGui() {
        this.field_146628_f = I18n.format("selectWorld.title", new Object[0]);
        this.func_146627_h();
        this.field_146637_u = I18n.format("selectWorld.world", new Object[0]);
        this.field_146636_v = I18n.format("selectWorld.conversion", new Object[0]);
        this.field_146635_w[WorldSettings.GameType.SURVIVAL.getID()] = I18n.format("gameMode.survival", new Object[0]);
        this.field_146635_w[WorldSettings.GameType.CREATIVE.getID()] = I18n.format("gameMode.creative", new Object[0]);
        this.field_146635_w[WorldSettings.GameType.ADVENTURE.getID()] = I18n.format("gameMode.adventure", new Object[0]);
        this.field_146635_w[WorldSettings.GameType.SPECTATOR.getID()] = I18n.format("gameMode.spectator", new Object[0]);
        (this.field_146638_t = new List(this.mc)).registerScrollButtons(4, 5);
        this.func_146618_g();
    }
    
    public void func_146615_e(final int n) {
        this.mc.displayGuiScreen(null);
        if (!this.field_146634_i) {
            this.field_146634_i = true;
            String s = this.func_146621_a(n);
            if (s == null) {
                s = "World" + n;
            }
            String s2 = this.func_146614_d(n);
            if (s2 == null) {
                s2 = "World" + n;
            }
            if (this.mc.getSaveLoader().canLoadWorld(s)) {
                this.mc.launchIntegratedServer(s, s2, null);
            }
        }
    }
    
    public GuiSelectWorld(final GuiScreen parentScreen) {
        this.field_146633_h = new SimpleDateFormat();
        this.field_146628_f = "Select world";
        this.field_146635_w = new String[4];
        this.parentScreen = parentScreen;
    }
    
    static String access$800(final GuiSelectWorld guiSelectWorld) {
        return guiSelectWorld.field_146636_v;
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.field_146638_t.handleMouseInput();
    }
    
    protected String func_146621_a(final int n) {
        return this.field_146639_s.get(n).getFileName();
    }
    
    protected String func_146614_d(final int n) {
        String s = this.field_146639_s.get(n).getDisplayName();
        if (StringUtils.isEmpty((CharSequence)s)) {
            s = I18n.format("selectWorld.world", new Object[0]) + " " + (n + 1);
        }
        return s;
    }
    
    public void func_146618_g() {
        this.buttonList.add(this.selectButton = new GuiButton(1, GuiSelectWorld.width / 2 - 154, GuiSelectWorld.height - 52, 150, 20, I18n.format("selectWorld.select", new Object[0])));
        this.buttonList.add(new GuiButton(3, GuiSelectWorld.width / 2 + 4, GuiSelectWorld.height - 52, 150, 20, I18n.format("selectWorld.create", new Object[0])));
        this.buttonList.add(this.renameButton = new GuiButton(6, GuiSelectWorld.width / 2 - 154, GuiSelectWorld.height - 28, 72, 20, I18n.format("selectWorld.rename", new Object[0])));
        this.buttonList.add(this.deleteButton = new GuiButton(2, GuiSelectWorld.width / 2 - 76, GuiSelectWorld.height - 28, 72, 20, I18n.format("selectWorld.delete", new Object[0])));
        this.buttonList.add(this.recreateButton = new GuiButton(7, GuiSelectWorld.width / 2 + 4, GuiSelectWorld.height - 28, 72, 20, I18n.format("selectWorld.recreate", new Object[0])));
        this.buttonList.add(new GuiButton(0, GuiSelectWorld.width / 2 + 82, GuiSelectWorld.height - 28, 72, 20, I18n.format("gui.cancel", new Object[0])));
        this.selectButton.enabled = false;
        this.deleteButton.enabled = false;
        this.renameButton.enabled = false;
        this.recreateButton.enabled = false;
    }
    
    private void func_146627_h() throws AnvilConverterException {
        Collections.sort((java.util.List<Comparable>)(this.field_146639_s = this.mc.getSaveLoader().getSaveList()));
        this.field_146640_r = -1;
    }
    
    class List extends GuiSlot
    {
        final GuiSelectWorld this$0;
        
        @Override
        protected int getContentHeight() {
            return GuiSelectWorld.access$000(this.this$0).size() * 36;
        }
        
        public List(final GuiSelectWorld this$0, final Minecraft minecraft) {
            this.this$0 = this$0;
            super(minecraft, GuiSelectWorld.width, GuiSelectWorld.height, 32, GuiSelectWorld.height - 64, 36);
        }
        
        @Override
        protected boolean isSelected(final int n) {
            return n == GuiSelectWorld.access$100(this.this$0);
        }
        
        @Override
        protected int getSize() {
            return GuiSelectWorld.access$000(this.this$0).size();
        }
        
        @Override
        protected void elementClicked(final int n, final boolean b, final int n2, final int n3) {
            GuiSelectWorld.access$102(this.this$0, n);
            final boolean b2 = GuiSelectWorld.access$100(this.this$0) >= 0 && GuiSelectWorld.access$100(this.this$0) < this.getSize();
            GuiSelectWorld.access$200(this.this$0).enabled = b2;
            GuiSelectWorld.access$300(this.this$0).enabled = b2;
            GuiSelectWorld.access$400(this.this$0).enabled = b2;
            GuiSelectWorld.access$500(this.this$0).enabled = b2;
            if (b && b2) {
                this.this$0.func_146615_e(n);
            }
        }
        
        @Override
        protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            final SaveFormatComparator saveFormatComparator = GuiSelectWorld.access$000(this.this$0).get(n);
            String s = saveFormatComparator.getDisplayName();
            if (StringUtils.isEmpty((CharSequence)s)) {
                s = GuiSelectWorld.access$600(this.this$0) + " " + (n + 1);
            }
            final String string = saveFormatComparator.getFileName() + " (" + GuiSelectWorld.access$700(this.this$0).format(new Date(saveFormatComparator.getLastTimePlayed())) + ")";
            final String s2 = "";
            String s3;
            if (saveFormatComparator.requiresConversion()) {
                s3 = GuiSelectWorld.access$800(this.this$0) + " " + s2;
            }
            else {
                s3 = GuiSelectWorld.access$900(this.this$0)[saveFormatComparator.getEnumGameType().getID()];
                if (saveFormatComparator.isHardcoreModeEnabled()) {
                    s3 = EnumChatFormatting.DARK_RED + I18n.format("gameMode.hardcore", new Object[0]) + EnumChatFormatting.RESET;
                }
                if (saveFormatComparator.getCheatsEnabled()) {
                    s3 = s3 + ", " + I18n.format("selectWorld.cheats", new Object[0]);
                }
            }
            this.this$0.drawString(this.this$0.fontRendererObj, s, n2 + 2, n3 + 1, 16777215);
            this.this$0.drawString(this.this$0.fontRendererObj, string, n2 + 2, n3 + 12, 8421504);
            this.this$0.drawString(this.this$0.fontRendererObj, s3, n2 + 2, n3 + 12 + 10, 8421504);
        }
        
        @Override
        protected void drawBackground() {
            this.this$0.drawDefaultBackground();
        }
    }
}
