package net.minecraft.client.gui.achievement;

import net.minecraft.client.resources.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import java.io.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import com.google.common.collect.*;
import net.minecraft.stats.*;
import org.lwjgl.input.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;
import java.util.*;
import net.minecraft.entity.*;

public class GuiStats extends GuiScreen implements IProgressMeter
{
    private GuiSlot displaySlot;
    private StatsGeneral generalStats;
    private StatsMobsList mobStats;
    private StatsBlock blockStats;
    private StatsItem itemStats;
    protected String screenTitle;
    private boolean doesGuiPauseGame;
    protected GuiScreen parentScreen;
    private StatFileWriter field_146546_t;
    
    @Override
    public boolean doesGuiPauseGame() {
        return !this.doesGuiPauseGame;
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        if (this.doesGuiPauseGame) {
            this.drawDefaultBackground();
            this.drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingStats", new Object[0]), GuiStats.width / 2, GuiStats.height / 2, 16777215);
            this.drawCenteredString(this.fontRendererObj, GuiStats.lanSearchStates[(int)(Minecraft.getSystemTime() / 150L % GuiStats.lanSearchStates.length)], GuiStats.width / 2, GuiStats.height / 2 + this.fontRendererObj.FONT_HEIGHT * 2, 16777215);
        }
        else {
            this.displaySlot.drawScreen(n, n2, n3);
            this.drawCenteredString(this.fontRendererObj, this.screenTitle, GuiStats.width / 2, 20, 16777215);
            super.drawScreen(n, n2, n3);
        }
    }
    
    static FontRenderer access$600(final GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }
    
    static FontRenderer access$200(final GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }
    
    static FontRenderer access$900(final GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }
    
    static FontRenderer access$1600(final GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }
    
    @Override
    public void doneLoading() {
        if (this.doesGuiPauseGame) {
            this.func_175366_f();
            this.createButtons();
            this.displaySlot = this.generalStats;
            this.doesGuiPauseGame = false;
        }
    }
    
    static void access$1200(final GuiStats guiStats, final int n, final int n2, final Item item) {
        guiStats.drawStatsScreen(n, n2, item);
    }
    
    static FontRenderer access$2200(final GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }
    
    static FontRenderer access$800(final GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }
    
    static FontRenderer access$500(final GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }
    
    static StatFileWriter access$100(final GuiStats guiStats) {
        return guiStats.field_146546_t;
    }
    
    static FontRenderer access$300(final GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }
    
    static FontRenderer access$2100(final GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }
    
    public void createButtons() {
        this.buttonList.add(new GuiButton(0, GuiStats.width / 2 + 4, GuiStats.height - 28, 150, 20, I18n.format("gui.done", new Object[0])));
        this.buttonList.add(new GuiButton(1, GuiStats.width / 2 - 160, GuiStats.height - 52, 80, 20, I18n.format("stat.generalButton", new Object[0])));
        final GuiButton guiButton;
        this.buttonList.add(guiButton = new GuiButton(2, GuiStats.width / 2 - 80, GuiStats.height - 52, 80, 20, I18n.format("stat.blocksButton", new Object[0])));
        final GuiButton guiButton2;
        this.buttonList.add(guiButton2 = new GuiButton(3, GuiStats.width / 2, GuiStats.height - 52, 80, 20, I18n.format("stat.itemsButton", new Object[0])));
        final GuiButton guiButton3;
        this.buttonList.add(guiButton3 = new GuiButton(4, GuiStats.width / 2 + 80, GuiStats.height - 52, 80, 20, I18n.format("stat.mobsButton", new Object[0])));
        if (this.blockStats.getSize() == 0) {
            guiButton.enabled = false;
        }
        if (this.itemStats.getSize() == 0) {
            guiButton2.enabled = false;
        }
        if (this.mobStats.getSize() == 0) {
            guiButton3.enabled = false;
        }
    }
    
    public void func_175366_f() {
        (this.generalStats = new StatsGeneral(this.mc)).registerScrollButtons(1, 1);
        (this.itemStats = new StatsItem(this.mc)).registerScrollButtons(1, 1);
        (this.blockStats = new StatsBlock(this.mc)).registerScrollButtons(1, 1);
        (this.mobStats = new StatsMobsList(this.mc)).registerScrollButtons(1, 1);
    }
    
    static FontRenderer access$1500(final GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == 0) {
                this.mc.displayGuiScreen(this.parentScreen);
            }
            else if (guiButton.id == 1) {
                this.displaySlot = this.generalStats;
            }
            else if (guiButton.id == 3) {
                this.displaySlot = this.itemStats;
            }
            else if (guiButton.id == 2) {
                this.displaySlot = this.blockStats;
            }
            else if (guiButton.id == 4) {
                this.displaySlot = this.mobStats;
            }
            else {
                this.displaySlot.actionPerformed(guiButton);
            }
        }
    }
    
    static void access$1000(final GuiStats guiStats, final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        guiStats.drawGradientRect(n, n2, n3, n4, n5, n6);
    }
    
    static void access$000(final GuiStats guiStats, final int n, final int n2, final int n3, final int n4) {
        guiStats.drawSprite(n, n2, n3, n4);
    }
    
    static FontRenderer access$1100(final GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }
    
    static FontRenderer access$1800(final GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }
    
    static FontRenderer access$1700(final GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }
    
    private void drawButtonBackground(final int n, final int n2) {
        this.drawSprite(n, n2, 0, 0);
    }
    
    private void drawSprite(final int n, final int n2, final int n3, final int n4) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiStats.statIcons);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(n + 0, n2 + 18, this.zLevel).tex((n3 + 0) * 0.0078125f, (n4 + 18) * 0.0078125f).endVertex();
        worldRenderer.pos(n + 18, n2 + 18, this.zLevel).tex((n3 + 18) * 0.0078125f, (n4 + 18) * 0.0078125f).endVertex();
        worldRenderer.pos(n + 18, n2 + 0, this.zLevel).tex((n3 + 18) * 0.0078125f, (n4 + 0) * 0.0078125f).endVertex();
        worldRenderer.pos(n + 0, n2 + 0, this.zLevel).tex((n3 + 0) * 0.0078125f, (n4 + 0) * 0.0078125f).endVertex();
        instance.draw();
    }
    
    static void access$700(final GuiStats guiStats, final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        guiStats.drawGradientRect(n, n2, n3, n4, n5, n6);
    }
    
    private void drawStatsScreen(final int n, final int n2, final Item item) {
        this.drawButtonBackground(n + 1, n2 + 1);
        this.itemRender.renderItemIntoGUI(new ItemStack(item, 1, 0), n + 2, n2 + 2);
    }
    
    static FontRenderer access$1300(final GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }
    
    static FontRenderer access$2000(final GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }
    
    @Override
    public void initGui() {
        this.screenTitle = I18n.format("gui.stats", new Object[0]);
        this.doesGuiPauseGame = true;
        this.mc.getNetHandler().addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.REQUEST_STATS));
    }
    
    static FontRenderer access$400(final GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }
    
    static FontRenderer access$1900(final GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }
    
    public GuiStats(final GuiScreen parentScreen, final StatFileWriter field_146546_t) {
        this.screenTitle = "Select world";
        this.doesGuiPauseGame = true;
        this.parentScreen = parentScreen;
        this.field_146546_t = field_146546_t;
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        if (this.displaySlot != null) {
            this.displaySlot.handleMouseInput();
        }
    }
    
    static FontRenderer access$1400(final GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }
    
    class StatsGeneral extends GuiSlot
    {
        final GuiStats this$0;
        
        @Override
        protected void elementClicked(final int n, final boolean b, final int n2, final int n3) {
        }
        
        @Override
        protected void drawBackground() {
            this.this$0.drawDefaultBackground();
        }
        
        @Override
        protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            final StatBase statBase = StatList.generalStats.get(n);
            this.this$0.drawString(GuiStats.access$1300(this.this$0), statBase.getStatName().getUnformattedText(), n2 + 2, n3 + 1, (n % 2 == 0) ? 16777215 : 9474192);
            final String format = statBase.format(GuiStats.access$100(this.this$0).readStat(statBase));
            this.this$0.drawString(GuiStats.access$1400(this.this$0), format, n2 + 2 + 213 - GuiStats.access$1500(this.this$0).getStringWidth(format), n3 + 1, (n % 2 == 0) ? 16777215 : 9474192);
        }
        
        @Override
        protected int getContentHeight() {
            return this.getSize() * 10;
        }
        
        @Override
        protected int getSize() {
            return StatList.generalStats.size();
        }
        
        public StatsGeneral(final GuiStats this$0, final Minecraft minecraft) {
            this.this$0 = this$0;
            super(minecraft, GuiStats.width, GuiStats.height, 32, GuiStats.height - 64, 10);
            this.setShowSelectionBox(false);
        }
        
        @Override
        protected boolean isSelected(final int n) {
            return false;
        }
    }
    
    class StatsBlock extends Stats
    {
        final GuiStats this$0;
        
        @Override
        protected void drawListHeader(final int n, final int n2, final Tessellator tessellator) {
            super.drawListHeader(n, n2, tessellator);
            if (this.field_148218_l == 0) {
                GuiStats.access$000(this.this$0, n + 115 - 18 + 1, n2 + 1 + 1, 18, 18);
            }
            else {
                GuiStats.access$000(this.this$0, n + 115 - 18, n2 + 1, 18, 18);
            }
            if (this.field_148218_l == 1) {
                GuiStats.access$000(this.this$0, n + 165 - 18 + 1, n2 + 1 + 1, 36, 18);
            }
            else {
                GuiStats.access$000(this.this$0, n + 165 - 18, n2 + 1, 36, 18);
            }
            if (this.field_148218_l == 2) {
                GuiStats.access$000(this.this$0, n + 215 - 18 + 1, n2 + 1 + 1, 54, 18);
            }
            else {
                GuiStats.access$000(this.this$0, n + 215 - 18, n2 + 1, 54, 18);
            }
        }
        
        public StatsBlock(final GuiStats this$0, final Minecraft minecraft) {
            this.this$0 = this$0.super(minecraft);
            this.statsHolder = Lists.newArrayList();
            for (final StatCrafting statCrafting : StatList.objectMineStats) {
                final int idFromItem = Item.getIdFromItem(statCrafting.func_150959_a());
                if (GuiStats.access$100(this$0).readStat(statCrafting) <= 0) {
                    if (StatList.objectUseStats[idFromItem] == null || GuiStats.access$100(this$0).readStat(StatList.objectUseStats[idFromItem]) <= 0) {
                        if (StatList.objectCraftStats[idFromItem] == null || GuiStats.access$100(this$0).readStat(StatList.objectCraftStats[idFromItem]) > 0) {}
                    }
                }
                this.statsHolder.add(statCrafting);
            }
            this.statSorter = new Comparator(this, this$0) {
                final StatsBlock this$1;
                final GuiStats val$this$0;
                
                public int compare(final StatCrafting statCrafting, final StatCrafting statCrafting2) {
                    final int idFromItem = Item.getIdFromItem(statCrafting.func_150959_a());
                    final int idFromItem2 = Item.getIdFromItem(statCrafting2.func_150959_a());
                    StatBase statBase = null;
                    StatBase statBase2 = null;
                    if (this.this$1.field_148217_o == 2) {
                        statBase = StatList.mineBlockStatArray[idFromItem];
                        statBase2 = StatList.mineBlockStatArray[idFromItem2];
                    }
                    else if (this.this$1.field_148217_o == 0) {
                        statBase = StatList.objectCraftStats[idFromItem];
                        statBase2 = StatList.objectCraftStats[idFromItem2];
                    }
                    else if (this.this$1.field_148217_o == 1) {
                        statBase = StatList.objectUseStats[idFromItem];
                        statBase2 = StatList.objectUseStats[idFromItem2];
                    }
                    if (statBase != null || statBase2 != null) {
                        if (statBase == null) {
                            return 1;
                        }
                        if (statBase2 == null) {
                            return -1;
                        }
                        final int stat = GuiStats.access$100(this.this$1.this$0).readStat(statBase);
                        final int stat2 = GuiStats.access$100(this.this$1.this$0).readStat(statBase2);
                        if (stat != stat2) {
                            return (stat - stat2) * this.this$1.field_148215_p;
                        }
                    }
                    return idFromItem - idFromItem2;
                }
                
                @Override
                public int compare(final Object o, final Object o2) {
                    return this.compare((StatCrafting)o, (StatCrafting)o2);
                }
            };
        }
        
        @Override
        protected String func_148210_b(final int n) {
            return (n == 0) ? "stat.crafted" : ((n == 1) ? "stat.used" : "stat.mined");
        }
        
        @Override
        protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            final StatCrafting func_148211_c = this.func_148211_c(n);
            final Item func_150959_a = func_148211_c.func_150959_a();
            GuiStats.access$1200(this.this$0, n2 + 40, n3, func_150959_a);
            final int idFromItem = Item.getIdFromItem(func_150959_a);
            this.func_148209_a(StatList.objectCraftStats[idFromItem], n2 + 115, n3, n % 2 == 0);
            this.func_148209_a(StatList.objectUseStats[idFromItem], n2 + 165, n3, n % 2 == 0);
            this.func_148209_a(func_148211_c, n2 + 215, n3, n % 2 == 0);
        }
    }
    
    abstract class Stats extends GuiSlot
    {
        final GuiStats this$0;
        protected int field_148217_o;
        protected List statsHolder;
        protected int field_148215_p;
        protected Comparator statSorter;
        protected int field_148218_l;
        
        @Override
        protected void elementClicked(final int n, final boolean b, final int n2, final int n3) {
        }
        
        @Override
        protected boolean isSelected(final int n) {
            return false;
        }
        
        @Override
        protected void drawListHeader(final int n, final int n2, final Tessellator tessellator) {
            if (!Mouse.isButtonDown(0)) {
                this.field_148218_l = -1;
            }
            if (this.field_148218_l == 0) {
                GuiStats.access$000(this.this$0, n + 115 - 18, n2 + 1, 0, 0);
            }
            else {
                GuiStats.access$000(this.this$0, n + 115 - 18, n2 + 1, 0, 18);
            }
            if (this.field_148218_l == 1) {
                GuiStats.access$000(this.this$0, n + 165 - 18, n2 + 1, 0, 0);
            }
            else {
                GuiStats.access$000(this.this$0, n + 165 - 18, n2 + 1, 0, 18);
            }
            if (this.field_148218_l == 2) {
                GuiStats.access$000(this.this$0, n + 215 - 18, n2 + 1, 0, 0);
            }
            else {
                GuiStats.access$000(this.this$0, n + 215 - 18, n2 + 1, 0, 18);
            }
            if (this.field_148217_o != -1) {
                if (this.field_148217_o != 1) {
                    if (this.field_148217_o == 2) {}
                }
                if (this.field_148215_p == 1) {}
                GuiStats.access$000(this.this$0, n + 179, n2 + 1, 36, 0);
            }
        }
        
        @Override
        protected final int getSize() {
            return this.statsHolder.size();
        }
        
        protected void func_148213_a(final StatCrafting statCrafting, final int n, final int n2) {
            if (statCrafting != null) {
                final String trim = ("" + I18n.format(new ItemStack(statCrafting.func_150959_a()).getUnlocalizedName() + ".name", new Object[0])).trim();
                if (trim.length() > 0) {
                    final int n3 = n + 12;
                    final int n4 = n2 - 12;
                    GuiStats.access$1000(this.this$0, n3 - 3, n4 - 3, n3 + GuiStats.access$900(this.this$0).getStringWidth(trim) + 3, n4 + 8 + 3, -1073741824, -1073741824);
                    GuiStats.access$1100(this.this$0).drawStringWithShadow(trim, (float)n3, (float)n4, -1);
                }
            }
        }
        
        protected abstract String func_148210_b(final int p0);
        
        @Override
        protected void drawBackground() {
            this.this$0.drawDefaultBackground();
        }
        
        protected void func_148209_a(final StatBase statBase, final int n, final int n2, final boolean b) {
            if (statBase != null) {
                final String format = statBase.format(GuiStats.access$100(this.this$0).readStat(statBase));
                this.this$0.drawString(GuiStats.access$200(this.this$0), format, n - GuiStats.access$300(this.this$0).getStringWidth(format), n2 + 5, b ? 16777215 : 9474192);
            }
            else {
                final String s = "-";
                this.this$0.drawString(GuiStats.access$400(this.this$0), s, n - GuiStats.access$500(this.this$0).getStringWidth(s), n2 + 5, b ? 16777215 : 9474192);
            }
        }
        
        protected final StatCrafting func_148211_c(final int n) {
            return this.statsHolder.get(n);
        }
        
        @Override
        protected void func_148132_a(final int n, final int n2) {
            this.field_148218_l = -1;
            if (n >= 79 && n < 115) {
                this.field_148218_l = 0;
            }
            else if (n >= 129 && n < 165) {
                this.field_148218_l = 1;
            }
            else if (n >= 179 && n < 215) {
                this.field_148218_l = 2;
            }
            if (this.field_148218_l >= 0) {
                this.func_148212_h(this.field_148218_l);
                this.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));
            }
        }
        
        @Override
        protected void func_148142_b(final int n, final int n2) {
            if (n2 >= this.top && n2 <= this.bottom) {
                final int slotIndexFromScreenCoords = this.getSlotIndexFromScreenCoords(n, n2);
                final int n3 = this.width / 2 - 92 - 16;
                if (slotIndexFromScreenCoords >= 0) {
                    if (n < n3 + 40 || n > n3 + 40 + 20) {
                        return;
                    }
                    this.func_148213_a(this.func_148211_c(slotIndexFromScreenCoords), n, n2);
                }
                else {
                    String s;
                    if (n >= n3 + 115 - 18 && n <= n3 + 115) {
                        s = this.func_148210_b(0);
                    }
                    else if (n >= n3 + 165 - 18 && n <= n3 + 165) {
                        s = this.func_148210_b(1);
                    }
                    else {
                        if (n < n3 + 215 - 18 || n > n3 + 215) {
                            return;
                        }
                        s = this.func_148210_b(2);
                    }
                    final String trim = ("" + I18n.format(s, new Object[0])).trim();
                    if (trim.length() > 0) {
                        final int n4 = n + 12;
                        final int n5 = n2 - 12;
                        GuiStats.access$700(this.this$0, n4 - 3, n5 - 3, n4 + GuiStats.access$600(this.this$0).getStringWidth(trim) + 3, n5 + 8 + 3, -1073741824, -1073741824);
                        GuiStats.access$800(this.this$0).drawStringWithShadow(trim, (float)n4, (float)n5, -1);
                    }
                }
            }
        }
        
        protected Stats(final GuiStats this$0, final Minecraft minecraft) {
            this.this$0 = this$0;
            super(minecraft, GuiStats.width, GuiStats.height, 32, GuiStats.height - 64, 20);
            this.field_148218_l = -1;
            this.field_148217_o = -1;
            this.setShowSelectionBox(false);
            this.setHasListHeader(true, 20);
        }
        
        protected void func_148212_h(final int field_148217_o) {
            if (field_148217_o != this.field_148217_o) {
                this.field_148217_o = field_148217_o;
                this.field_148215_p = -1;
            }
            else if (this.field_148215_p == -1) {
                this.field_148215_p = 1;
            }
            else {
                this.field_148217_o = -1;
                this.field_148215_p = 0;
            }
            Collections.sort((List<Object>)this.statsHolder, this.statSorter);
        }
    }
    
    class StatsMobsList extends GuiSlot
    {
        private final List field_148222_l;
        final GuiStats this$0;
        
        public StatsMobsList(final GuiStats this$0, final Minecraft minecraft) {
            this.this$0 = this$0;
            super(minecraft, GuiStats.width, GuiStats.height, 32, GuiStats.height - 64, GuiStats.access$1600(this$0).FONT_HEIGHT * 4);
            this.field_148222_l = Lists.newArrayList();
            this.setShowSelectionBox(false);
            for (final EntityList.EntityEggInfo entityEggInfo : EntityList.entityEggs.values()) {
                if (GuiStats.access$100(this$0).readStat(entityEggInfo.field_151512_d) > 0 || GuiStats.access$100(this$0).readStat(entityEggInfo.field_151513_e) > 0) {
                    this.field_148222_l.add(entityEggInfo);
                }
            }
        }
        
        @Override
        protected int getSize() {
            return this.field_148222_l.size();
        }
        
        @Override
        protected boolean isSelected(final int n) {
            return false;
        }
        
        @Override
        protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            final EntityList.EntityEggInfo entityEggInfo = this.field_148222_l.get(n);
            final String format = I18n.format("entity." + EntityList.getStringFromID(entityEggInfo.spawnedID) + ".name", new Object[0]);
            final int stat = GuiStats.access$100(this.this$0).readStat(entityEggInfo.field_151512_d);
            final int stat2 = GuiStats.access$100(this.this$0).readStat(entityEggInfo.field_151513_e);
            String s = I18n.format("stat.entityKills", stat, format);
            String s2 = I18n.format("stat.entityKilledBy", format, stat2);
            if (stat == 0) {
                s = I18n.format("stat.entityKills.none", format);
            }
            if (stat2 == 0) {
                s2 = I18n.format("stat.entityKilledBy.none", format);
            }
            this.this$0.drawString(GuiStats.access$1800(this.this$0), format, n2 + 2 - 10, n3 + 1, 16777215);
            this.this$0.drawString(GuiStats.access$1900(this.this$0), s, n2 + 2, n3 + 1 + GuiStats.access$2000(this.this$0).FONT_HEIGHT, (stat == 0) ? 6316128 : 9474192);
            this.this$0.drawString(GuiStats.access$2100(this.this$0), s2, n2 + 2, n3 + 1 + GuiStats.access$2200(this.this$0).FONT_HEIGHT * 2, (stat2 == 0) ? 6316128 : 9474192);
        }
        
        @Override
        protected void elementClicked(final int n, final boolean b, final int n2, final int n3) {
        }
        
        @Override
        protected int getContentHeight() {
            return this.getSize() * GuiStats.access$1700(this.this$0).FONT_HEIGHT * 4;
        }
        
        @Override
        protected void drawBackground() {
            this.this$0.drawDefaultBackground();
        }
    }
    
    class StatsItem extends Stats
    {
        final GuiStats this$0;
        
        @Override
        protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            final StatCrafting func_148211_c = this.func_148211_c(n);
            final Item func_150959_a = func_148211_c.func_150959_a();
            GuiStats.access$1200(this.this$0, n2 + 40, n3, func_150959_a);
            final int idFromItem = Item.getIdFromItem(func_150959_a);
            this.func_148209_a(StatList.objectBreakStats[idFromItem], n2 + 115, n3, n % 2 == 0);
            this.func_148209_a(StatList.objectCraftStats[idFromItem], n2 + 165, n3, n % 2 == 0);
            this.func_148209_a(func_148211_c, n2 + 215, n3, n % 2 == 0);
        }
        
        public StatsItem(final GuiStats this$0, final Minecraft minecraft) {
            this.this$0 = this$0.super(minecraft);
            this.statsHolder = Lists.newArrayList();
            for (final StatCrafting statCrafting : StatList.itemStats) {
                final int idFromItem = Item.getIdFromItem(statCrafting.func_150959_a());
                if (GuiStats.access$100(this$0).readStat(statCrafting) <= 0) {
                    if (StatList.objectBreakStats[idFromItem] == null || GuiStats.access$100(this$0).readStat(StatList.objectBreakStats[idFromItem]) <= 0) {
                        if (StatList.objectCraftStats[idFromItem] == null || GuiStats.access$100(this$0).readStat(StatList.objectCraftStats[idFromItem]) > 0) {}
                    }
                }
                this.statsHolder.add(statCrafting);
            }
            this.statSorter = new Comparator(this, this$0) {
                final GuiStats val$this$0;
                final StatsItem this$1;
                
                public int compare(final StatCrafting statCrafting, final StatCrafting statCrafting2) {
                    final int idFromItem = Item.getIdFromItem(statCrafting.func_150959_a());
                    final int idFromItem2 = Item.getIdFromItem(statCrafting2.func_150959_a());
                    StatBase statBase = null;
                    StatBase statBase2 = null;
                    if (this.this$1.field_148217_o == 0) {
                        statBase = StatList.objectBreakStats[idFromItem];
                        statBase2 = StatList.objectBreakStats[idFromItem2];
                    }
                    else if (this.this$1.field_148217_o == 1) {
                        statBase = StatList.objectCraftStats[idFromItem];
                        statBase2 = StatList.objectCraftStats[idFromItem2];
                    }
                    else if (this.this$1.field_148217_o == 2) {
                        statBase = StatList.objectUseStats[idFromItem];
                        statBase2 = StatList.objectUseStats[idFromItem2];
                    }
                    if (statBase != null || statBase2 != null) {
                        if (statBase == null) {
                            return 1;
                        }
                        if (statBase2 == null) {
                            return -1;
                        }
                        final int stat = GuiStats.access$100(this.this$1.this$0).readStat(statBase);
                        final int stat2 = GuiStats.access$100(this.this$1.this$0).readStat(statBase2);
                        if (stat != stat2) {
                            return (stat - stat2) * this.this$1.field_148215_p;
                        }
                    }
                    return idFromItem - idFromItem2;
                }
                
                @Override
                public int compare(final Object o, final Object o2) {
                    return this.compare((StatCrafting)o, (StatCrafting)o2);
                }
            };
        }
        
        @Override
        protected void drawListHeader(final int n, final int n2, final Tessellator tessellator) {
            super.drawListHeader(n, n2, tessellator);
            if (this.field_148218_l == 0) {
                GuiStats.access$000(this.this$0, n + 115 - 18 + 1, n2 + 1 + 1, 72, 18);
            }
            else {
                GuiStats.access$000(this.this$0, n + 115 - 18, n2 + 1, 72, 18);
            }
            if (this.field_148218_l == 1) {
                GuiStats.access$000(this.this$0, n + 165 - 18 + 1, n2 + 1 + 1, 18, 18);
            }
            else {
                GuiStats.access$000(this.this$0, n + 165 - 18, n2 + 1, 18, 18);
            }
            if (this.field_148218_l == 2) {
                GuiStats.access$000(this.this$0, n + 215 - 18 + 1, n2 + 1 + 1, 36, 18);
            }
            else {
                GuiStats.access$000(this.this$0, n + 215 - 18, n2 + 1, 36, 18);
            }
        }
        
        @Override
        protected String func_148210_b(final int n) {
            return (n == 1) ? "stat.crafted" : ((n == 2) ? "stat.used" : "stat.depleted");
        }
    }
}
