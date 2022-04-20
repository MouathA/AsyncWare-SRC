package net.minecraft.client.gui;

import net.minecraft.client.resources.*;
import java.io.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.gen.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;

public class GuiCreateFlatWorld extends GuiScreen
{
    private String field_146391_r;
    private GuiButton field_146386_v;
    private String flatWorldTitle;
    private Details createFlatWorldListSlotGui;
    private String field_146394_i;
    private GuiButton field_146389_t;
    private GuiButton field_146388_u;
    private FlatGeneratorInfo theFlatGeneratorInfo;
    private final GuiCreateWorld createWorldGui;
    
    public GuiCreateFlatWorld(final GuiCreateWorld createWorldGui, final String s) {
        this.theFlatGeneratorInfo = FlatGeneratorInfo.getDefaultFlatGenerator();
        this.createWorldGui = createWorldGui;
        this.func_146383_a(s);
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.flatWorldTitle = I18n.format("createWorld.customize.flat.title", new Object[0]);
        this.field_146394_i = I18n.format("createWorld.customize.flat.tile", new Object[0]);
        this.field_146391_r = I18n.format("createWorld.customize.flat.height", new Object[0]);
        this.createFlatWorldListSlotGui = new Details();
        this.buttonList.add(this.field_146389_t = new GuiButton(2, GuiCreateFlatWorld.width / 2 - 154, GuiCreateFlatWorld.height - 52, 100, 20, I18n.format("createWorld.customize.flat.addLayer", new Object[0]) + " (NYI)"));
        this.buttonList.add(this.field_146388_u = new GuiButton(3, GuiCreateFlatWorld.width / 2 - 50, GuiCreateFlatWorld.height - 52, 100, 20, I18n.format("createWorld.customize.flat.editLayer", new Object[0]) + " (NYI)"));
        this.buttonList.add(this.field_146386_v = new GuiButton(4, GuiCreateFlatWorld.width / 2 - 155, GuiCreateFlatWorld.height - 52, 150, 20, I18n.format("createWorld.customize.flat.removeLayer", new Object[0])));
        this.buttonList.add(new GuiButton(0, GuiCreateFlatWorld.width / 2 - 155, GuiCreateFlatWorld.height - 28, 150, 20, I18n.format("gui.done", new Object[0])));
        this.buttonList.add(new GuiButton(5, GuiCreateFlatWorld.width / 2 + 5, GuiCreateFlatWorld.height - 52, 150, 20, I18n.format("createWorld.customize.presets", new Object[0])));
        this.buttonList.add(new GuiButton(1, GuiCreateFlatWorld.width / 2 + 5, GuiCreateFlatWorld.height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
        final GuiButton field_146389_t = this.field_146389_t;
        final GuiButton field_146388_u = this.field_146388_u;
        final boolean b = false;
        field_146388_u.visible = b;
        field_146389_t.visible = b;
        this.theFlatGeneratorInfo.func_82645_d();
        this.func_146375_g();
    }
    
    public void func_146375_g() {
        final boolean func_146382_i = this.func_146382_i();
        this.field_146386_v.enabled = func_146382_i;
        this.field_146388_u.enabled = func_146382_i;
        this.field_146388_u.enabled = false;
        this.field_146389_t.enabled = false;
    }
    
    public String func_146384_e() {
        return this.theFlatGeneratorInfo.toString();
    }
    
    static FlatGeneratorInfo access$000(final GuiCreateFlatWorld guiCreateFlatWorld) {
        return guiCreateFlatWorld.theFlatGeneratorInfo;
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.createFlatWorldListSlotGui.handleMouseInput();
    }
    
    @Override
    protected void actionPerformed(final GuiButton p0) throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/client/gui/GuiCreateFlatWorld.theFlatGeneratorInfo:Lnet/minecraft/world/gen/FlatGeneratorInfo;
        //     4: invokevirtual   net/minecraft/world/gen/FlatGeneratorInfo.getFlatLayers:()Ljava/util/List;
        //     7: invokeinterface java/util/List.size:()I
        //    12: aload_0        
        //    13: getfield        net/minecraft/client/gui/GuiCreateFlatWorld.createFlatWorldListSlotGui:Lnet/minecraft/client/gui/GuiCreateFlatWorld$Details;
        //    16: getfield        net/minecraft/client/gui/GuiCreateFlatWorld$Details.field_148228_k:I
        //    19: isub           
        //    20: iconst_1       
        //    21: isub           
        //    22: istore_2       
        //    23: aload_1        
        //    24: getfield        net/minecraft/client/gui/GuiButton.id:I
        //    27: iconst_1       
        //    28: if_icmpne       45
        //    31: aload_0        
        //    32: getfield        net/minecraft/client/gui/GuiCreateFlatWorld.mc:Lnet/minecraft/client/Minecraft;
        //    35: aload_0        
        //    36: getfield        net/minecraft/client/gui/GuiCreateFlatWorld.createWorldGui:Lnet/minecraft/client/gui/GuiCreateWorld;
        //    39: invokevirtual   net/minecraft/client/Minecraft.displayGuiScreen:(Lnet/minecraft/client/gui/GuiScreen;)V
        //    42: goto            160
        //    45: aload_1        
        //    46: getfield        net/minecraft/client/gui/GuiButton.id:I
        //    49: ifne            77
        //    52: aload_0        
        //    53: getfield        net/minecraft/client/gui/GuiCreateFlatWorld.createWorldGui:Lnet/minecraft/client/gui/GuiCreateWorld;
        //    56: aload_0        
        //    57: invokevirtual   net/minecraft/client/gui/GuiCreateFlatWorld.func_146384_e:()Ljava/lang/String;
        //    60: putfield        net/minecraft/client/gui/GuiCreateWorld.chunkProviderSettingsJson:Ljava/lang/String;
        //    63: aload_0        
        //    64: getfield        net/minecraft/client/gui/GuiCreateFlatWorld.mc:Lnet/minecraft/client/Minecraft;
        //    67: aload_0        
        //    68: getfield        net/minecraft/client/gui/GuiCreateFlatWorld.createWorldGui:Lnet/minecraft/client/gui/GuiCreateWorld;
        //    71: invokevirtual   net/minecraft/client/Minecraft.displayGuiScreen:(Lnet/minecraft/client/gui/GuiScreen;)V
        //    74: goto            160
        //    77: aload_1        
        //    78: getfield        net/minecraft/client/gui/GuiButton.id:I
        //    81: iconst_5       
        //    82: if_icmpne       103
        //    85: aload_0        
        //    86: getfield        net/minecraft/client/gui/GuiCreateFlatWorld.mc:Lnet/minecraft/client/Minecraft;
        //    89: new             Lnet/minecraft/client/gui/GuiFlatPresets;
        //    92: dup            
        //    93: aload_0        
        //    94: invokespecial   net/minecraft/client/gui/GuiFlatPresets.<init>:(Lnet/minecraft/client/gui/GuiCreateFlatWorld;)V
        //    97: invokevirtual   net/minecraft/client/Minecraft.displayGuiScreen:(Lnet/minecraft/client/gui/GuiScreen;)V
        //   100: goto            160
        //   103: aload_1        
        //   104: getfield        net/minecraft/client/gui/GuiButton.id:I
        //   107: iconst_4       
        //   108: if_icmpne       160
        //   111: aload_0        
        //   112: if_icmple       160
        //   115: aload_0        
        //   116: getfield        net/minecraft/client/gui/GuiCreateFlatWorld.theFlatGeneratorInfo:Lnet/minecraft/world/gen/FlatGeneratorInfo;
        //   119: invokevirtual   net/minecraft/world/gen/FlatGeneratorInfo.getFlatLayers:()Ljava/util/List;
        //   122: iload_2        
        //   123: invokeinterface java/util/List.remove:(I)Ljava/lang/Object;
        //   128: pop            
        //   129: aload_0        
        //   130: getfield        net/minecraft/client/gui/GuiCreateFlatWorld.createFlatWorldListSlotGui:Lnet/minecraft/client/gui/GuiCreateFlatWorld$Details;
        //   133: aload_0        
        //   134: getfield        net/minecraft/client/gui/GuiCreateFlatWorld.createFlatWorldListSlotGui:Lnet/minecraft/client/gui/GuiCreateFlatWorld$Details;
        //   137: getfield        net/minecraft/client/gui/GuiCreateFlatWorld$Details.field_148228_k:I
        //   140: aload_0        
        //   141: getfield        net/minecraft/client/gui/GuiCreateFlatWorld.theFlatGeneratorInfo:Lnet/minecraft/world/gen/FlatGeneratorInfo;
        //   144: invokevirtual   net/minecraft/world/gen/FlatGeneratorInfo.getFlatLayers:()Ljava/util/List;
        //   147: invokeinterface java/util/List.size:()I
        //   152: iconst_1       
        //   153: isub           
        //   154: invokestatic    java/lang/Math.min:(II)I
        //   157: putfield        net/minecraft/client/gui/GuiCreateFlatWorld$Details.field_148228_k:I
        //   160: aload_0        
        //   161: getfield        net/minecraft/client/gui/GuiCreateFlatWorld.theFlatGeneratorInfo:Lnet/minecraft/world/gen/FlatGeneratorInfo;
        //   164: invokevirtual   net/minecraft/world/gen/FlatGeneratorInfo.func_82645_d:()V
        //   167: aload_0        
        //   168: invokevirtual   net/minecraft/client/gui/GuiCreateFlatWorld.func_146375_g:()V
        //   171: return         
        //    Exceptions:
        //  throws java.io.IOException
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.createFlatWorldListSlotGui.drawScreen(n, n2, n3);
        this.drawCenteredString(this.fontRendererObj, this.flatWorldTitle, GuiCreateFlatWorld.width / 2, 8, 16777215);
        final int n4 = GuiCreateFlatWorld.width / 2 - 92 - 16;
        this.drawString(this.fontRendererObj, this.field_146394_i, n4, 32, 16777215);
        this.drawString(this.fontRendererObj, this.field_146391_r, n4 + 2 + 213 - this.fontRendererObj.getStringWidth(this.field_146391_r), 32, 16777215);
        super.drawScreen(n, n2, n3);
    }
    
    public void func_146383_a(final String s) {
        this.theFlatGeneratorInfo = FlatGeneratorInfo.createFlatGeneratorFromString(s);
    }
    
    class Details extends GuiSlot
    {
        final GuiCreateFlatWorld this$0;
        public int field_148228_k;
        
        private void func_148225_a(final int n, final int n2, final ItemStack itemStack) {
            this.func_148226_e(n + 1, n2 + 1);
            if (itemStack != null && itemStack.getItem() != null) {
                this.this$0.itemRender.renderItemIntoGUI(itemStack, n + 2, n2 + 2);
            }
        }
        
        @Override
        protected void drawBackground() {
        }
        
        private void func_148224_c(final int n, final int n2, final int n3, final int n4) {
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
        protected int getSize() {
            return GuiCreateFlatWorld.access$000(this.this$0).getFlatLayers().size();
        }
        
        @Override
        protected int getScrollBarX() {
            return this.width - 70;
        }
        
        @Override
        protected boolean isSelected(final int n) {
            return n == this.field_148228_k;
        }
        
        private void func_148226_e(final int n, final int n2) {
            this.func_148224_c(n, n2, 0, 0);
        }
        
        public Details(final GuiCreateFlatWorld this$0) {
            this.this$0 = this$0;
            super(this$0.mc, GuiCreateFlatWorld.width, GuiCreateFlatWorld.height, 43, GuiCreateFlatWorld.height - 60, 24);
            this.field_148228_k = -1;
        }
        
        @Override
        protected void elementClicked(final int field_148228_k, final boolean b, final int n, final int n2) {
            this.field_148228_k = field_148228_k;
            this.this$0.func_146375_g();
        }
        
        @Override
        protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            final FlatLayerInfo flatLayerInfo = GuiCreateFlatWorld.access$000(this.this$0).getFlatLayers().get(GuiCreateFlatWorld.access$000(this.this$0).getFlatLayers().size() - n - 1);
            final IBlockState func_175900_c = flatLayerInfo.func_175900_c();
            final Block block = func_175900_c.getBlock();
            Item item = Item.getItemFromBlock(block);
            ItemStack itemStack = (block != Blocks.air && item != null) ? new ItemStack(item, 1, block.getMetaFromState(func_175900_c)) : null;
            String localizedName = (itemStack == null) ? "Air" : item.getItemStackDisplayName(itemStack);
            if (item == null) {
                if (block != Blocks.water && block != Blocks.flowing_water) {
                    if (block == Blocks.lava || block == Blocks.flowing_lava) {
                        item = Items.lava_bucket;
                    }
                }
                else {
                    item = Items.water_bucket;
                }
                if (item != null) {
                    itemStack = new ItemStack(item, 1, block.getMetaFromState(func_175900_c));
                    localizedName = block.getLocalizedName();
                }
            }
            this.func_148225_a(n2, n3, itemStack);
            this.this$0.fontRendererObj.drawString(localizedName, n2 + 18 + 5, n3 + 3, 16777215);
            String s;
            if (n == 0) {
                s = I18n.format("createWorld.customize.flat.layer.top", flatLayerInfo.getLayerCount());
            }
            else if (n == GuiCreateFlatWorld.access$000(this.this$0).getFlatLayers().size() - 1) {
                s = I18n.format("createWorld.customize.flat.layer.bottom", flatLayerInfo.getLayerCount());
            }
            else {
                s = I18n.format("createWorld.customize.flat.layer", flatLayerInfo.getLayerCount());
            }
            this.this$0.fontRendererObj.drawString(s, n2 + 2 + 213 - this.this$0.fontRendererObj.getStringWidth(s), n3 + 3, 16777215);
        }
    }
}
