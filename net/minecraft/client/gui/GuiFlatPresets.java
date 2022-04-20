package net.minecraft.client.gui;

import java.io.*;
import net.minecraft.world.biome.*;
import org.lwjgl.input.*;
import net.minecraft.world.gen.*;
import net.minecraft.client.resources.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;

public class GuiFlatPresets extends GuiScreen
{
    private String field_146436_r;
    private String presetsShare;
    private final GuiCreateFlatWorld parentScreen;
    private static final List FLAT_WORLD_PRESETS;
    private String presetsTitle;
    private GuiTextField field_146433_u;
    private ListSlot field_146435_s;
    private GuiButton field_146434_t;
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        this.field_146433_u.mouseClicked(n, n2, n3);
        super.mouseClicked(n, n2, n3);
    }
    
    @Override
    protected void actionPerformed(final GuiButton p0) throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/client/gui/GuiButton.id:I
        //     4: ifne            39
        //     7: aload_0        
        //     8: if_icmple       39
        //    11: aload_0        
        //    12: getfield        net/minecraft/client/gui/GuiFlatPresets.parentScreen:Lnet/minecraft/client/gui/GuiCreateFlatWorld;
        //    15: aload_0        
        //    16: getfield        net/minecraft/client/gui/GuiFlatPresets.field_146433_u:Lnet/minecraft/client/gui/GuiTextField;
        //    19: invokevirtual   net/minecraft/client/gui/GuiTextField.getText:()Ljava/lang/String;
        //    22: invokevirtual   net/minecraft/client/gui/GuiCreateFlatWorld.func_146383_a:(Ljava/lang/String;)V
        //    25: aload_0        
        //    26: getfield        net/minecraft/client/gui/GuiFlatPresets.mc:Lnet/minecraft/client/Minecraft;
        //    29: aload_0        
        //    30: getfield        net/minecraft/client/gui/GuiFlatPresets.parentScreen:Lnet/minecraft/client/gui/GuiCreateFlatWorld;
        //    33: invokevirtual   net/minecraft/client/Minecraft.displayGuiScreen:(Lnet/minecraft/client/gui/GuiScreen;)V
        //    36: goto            58
        //    39: aload_1        
        //    40: getfield        net/minecraft/client/gui/GuiButton.id:I
        //    43: iconst_1       
        //    44: if_icmpne       58
        //    47: aload_0        
        //    48: getfield        net/minecraft/client/gui/GuiFlatPresets.mc:Lnet/minecraft/client/Minecraft;
        //    51: aload_0        
        //    52: getfield        net/minecraft/client/gui/GuiFlatPresets.parentScreen:Lnet/minecraft/client/gui/GuiCreateFlatWorld;
        //    55: invokevirtual   net/minecraft/client/Minecraft.displayGuiScreen:(Lnet/minecraft/client/gui/GuiScreen;)V
        //    58: return         
        //    Exceptions:
        //  throws java.io.IOException
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private static void func_146425_a(final String s, final Item item, final BiomeGenBase biomeGenBase, final FlatLayerInfo... array) {
        func_175354_a(s, item, 0, biomeGenBase, null, array);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    public void updateScreen() {
        this.field_146433_u.updateCursorCounter();
        super.updateScreen();
    }
    
    static List access$000() {
        return GuiFlatPresets.FLAT_WORLD_PRESETS;
    }
    
    private static void func_175354_a(final String s, final Item item, final int n, final BiomeGenBase biomeGenBase, final List list, final FlatLayerInfo... array) {
        final FlatGeneratorInfo flatGeneratorInfo = new FlatGeneratorInfo();
        for (int i = array.length - 1; i >= 0; --i) {
            flatGeneratorInfo.getFlatLayers().add(array[i]);
        }
        flatGeneratorInfo.setBiome(biomeGenBase.biomeID);
        flatGeneratorInfo.func_82645_d();
        if (list != null) {
            final Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()) {
                flatGeneratorInfo.getWorldFeatures().put(iterator.next(), Maps.newHashMap());
            }
        }
        GuiFlatPresets.FLAT_WORLD_PRESETS.add(new LayerItem(item, n, s, flatGeneratorInfo.toString()));
    }
    
    static GuiTextField access$200(final GuiFlatPresets guiFlatPresets) {
        return guiFlatPresets.field_146433_u;
    }
    
    public void func_146426_g() {
        this.field_146434_t.enabled = this.func_146430_p();
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        this.presetsTitle = I18n.format("createWorld.customize.presets.title", new Object[0]);
        this.presetsShare = I18n.format("createWorld.customize.presets.share", new Object[0]);
        this.field_146436_r = I18n.format("createWorld.customize.presets.list", new Object[0]);
        this.field_146433_u = new GuiTextField(2, this.fontRendererObj, 50, 40, GuiFlatPresets.width - 100, 20);
        this.field_146435_s = new ListSlot();
        this.field_146433_u.setMaxStringLength(1230);
        this.field_146433_u.setText(this.parentScreen.func_146384_e());
        this.buttonList.add(this.field_146434_t = new GuiButton(0, GuiFlatPresets.width / 2 - 155, GuiFlatPresets.height - 28, 150, 20, I18n.format("createWorld.customize.presets.select", new Object[0])));
        this.buttonList.add(new GuiButton(1, GuiFlatPresets.width / 2 + 5, GuiFlatPresets.height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
        this.func_146426_g();
    }
    
    static ListSlot access$100(final GuiFlatPresets guiFlatPresets) {
        return guiFlatPresets.field_146435_s;
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.field_146435_s.handleMouseInput();
    }
    
    private static void func_146421_a(final String s, final Item item, final BiomeGenBase biomeGenBase, final List list, final FlatLayerInfo... array) {
        func_175354_a(s, item, 0, biomeGenBase, list, array);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.field_146435_s.drawScreen(n, n2, n3);
        this.drawCenteredString(this.fontRendererObj, this.presetsTitle, GuiFlatPresets.width / 2, 8, 16777215);
        this.drawString(this.fontRendererObj, this.presetsShare, 50, 30, 10526880);
        this.drawString(this.fontRendererObj, this.field_146436_r, 50, 70, 10526880);
        this.field_146433_u.drawTextBox();
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (!this.field_146433_u.textboxKeyTyped(c, n)) {
            super.keyTyped(c, n);
        }
    }
    
    static {
        FLAT_WORLD_PRESETS = Lists.newArrayList();
        func_146421_a("Classic Flat", Item.getItemFromBlock(Blocks.grass), BiomeGenBase.plains, Arrays.asList("village"), new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(2, Blocks.dirt), new FlatLayerInfo(1, Blocks.bedrock));
        func_146421_a("Tunnelers' Dream", Item.getItemFromBlock(Blocks.stone), BiomeGenBase.extremeHills, Arrays.asList("biome_1", "dungeon", "decoration", "stronghold", "mineshaft"), new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(5, Blocks.dirt), new FlatLayerInfo(230, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock));
        func_146421_a("Water World", Items.water_bucket, BiomeGenBase.deepOcean, Arrays.asList("biome_1", "oceanmonument"), new FlatLayerInfo(90, Blocks.water), new FlatLayerInfo(5, Blocks.sand), new FlatLayerInfo(5, Blocks.dirt), new FlatLayerInfo(5, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock));
        func_175354_a("Overworld", Item.getItemFromBlock(Blocks.tallgrass), BlockTallGrass.EnumType.GRASS.getMeta(), BiomeGenBase.plains, Arrays.asList("village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon", "lake", "lava_lake"), new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(3, Blocks.dirt), new FlatLayerInfo(59, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock));
        func_146421_a("Snowy Kingdom", Item.getItemFromBlock(Blocks.snow_layer), BiomeGenBase.icePlains, Arrays.asList("village", "biome_1"), new FlatLayerInfo(1, Blocks.snow_layer), new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(3, Blocks.dirt), new FlatLayerInfo(59, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock));
        func_146421_a("Bottomless Pit", Items.feather, BiomeGenBase.plains, Arrays.asList("village", "biome_1"), new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(3, Blocks.dirt), new FlatLayerInfo(2, Blocks.cobblestone));
        func_146421_a("Desert", Item.getItemFromBlock(Blocks.sand), BiomeGenBase.desert, Arrays.asList("village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon"), new FlatLayerInfo(8, Blocks.sand), new FlatLayerInfo(52, Blocks.sandstone), new FlatLayerInfo(3, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock));
        func_146425_a("Redstone Ready", Items.redstone, BiomeGenBase.desert, new FlatLayerInfo(52, Blocks.sandstone), new FlatLayerInfo(3, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock));
    }
    
    public GuiFlatPresets(final GuiCreateFlatWorld parentScreen) {
        this.parentScreen = parentScreen;
    }
    
    class ListSlot extends GuiSlot
    {
        final GuiFlatPresets this$0;
        public int field_148175_k;
        
        private void func_148171_c(final int n, final int n2, final int n3, final int n4) {
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.getTextureManager().bindTexture(Gui.statIcons);
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
            worldRenderer.pos(n + 0, n2 + 18, this.this$0.zLevel).tex((n3 + 0) * 0.0078125f, (n4 + 18) * 0.0078125f).endVertex();
            worldRenderer.pos(n + 18, n2 + 18, this.this$0.zLevel).tex((n3 + 18) * 0.0078125f, (n4 + 18) * 0.0078125f).endVertex();
            worldRenderer.pos(n + 18, n2 + 0, this.this$0.zLevel).tex((n3 + 18) * 0.0078125f, (n4 + 0) * 0.0078125f).endVertex();
            worldRenderer.pos(n + 0, n2 + 0, this.this$0.zLevel).tex((n3 + 0) * 0.0078125f, (n4 + 0) * 0.0078125f).endVertex();
            instance.draw();
        }
        
        @Override
        protected void drawBackground() {
        }
        
        @Override
        protected int getSize() {
            return GuiFlatPresets.access$000().size();
        }
        
        @Override
        protected boolean isSelected(final int n) {
            return n == this.field_148175_k;
        }
        
        public ListSlot(final GuiFlatPresets this$0) {
            this.this$0 = this$0;
            super(this$0.mc, GuiFlatPresets.width, GuiFlatPresets.height, 80, GuiFlatPresets.height - 37, 24);
            this.field_148175_k = -1;
        }
        
        private void func_148173_e(final int n, final int n2) {
            this.func_148171_c(n, n2, 0, 0);
        }
        
        @Override
        protected void elementClicked(final int field_148175_k, final boolean b, final int n, final int n2) {
            this.field_148175_k = field_148175_k;
            this.this$0.func_146426_g();
            GuiFlatPresets.access$200(this.this$0).setText(GuiFlatPresets.access$000().get(GuiFlatPresets.access$100(this.this$0).field_148175_k).field_148233_c);
        }
        
        @Override
        protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            final LayerItem layerItem = GuiFlatPresets.access$000().get(n);
            this.func_178054_a(n2, n3, layerItem.field_148234_a, layerItem.field_179037_b);
            this.this$0.fontRendererObj.drawString(layerItem.field_148232_b, n2 + 18 + 5, n3 + 6, 16777215);
        }
        
        private void func_178054_a(final int n, final int n2, final Item item, final int n3) {
            this.func_148173_e(n + 1, n2 + 1);
            this.this$0.itemRender.renderItemIntoGUI(new ItemStack(item, 1, n3), n + 2, n2 + 2);
        }
    }
    
    static class LayerItem
    {
        public String field_148232_b;
        public String field_148233_c;
        public Item field_148234_a;
        public int field_179037_b;
        
        public LayerItem(final Item field_148234_a, final int field_179037_b, final String field_148232_b, final String field_148233_c) {
            this.field_148234_a = field_148234_a;
            this.field_179037_b = field_179037_b;
            this.field_148232_b = field_148232_b;
            this.field_148233_c = field_148233_c;
        }
    }
}
