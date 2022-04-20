package net.minecraft.client.gui;

import java.util.*;
import com.google.common.collect.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.gen.*;
import org.lwjgl.input.*;
import java.io.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class GuiScreenCustomizePresets extends GuiScreen
{
    private String field_175313_s;
    private ListPreset field_175311_g;
    private GuiTextField field_175317_i;
    private String field_175312_t;
    private GuiCustomizeWorldScreen field_175314_r;
    protected String field_175315_a;
    private GuiButton field_175316_h;
    private static final List field_175310_f;
    
    static {
        (field_175310_f = Lists.newArrayList()).add(new Info(I18n.format("createWorld.customize.custom.preset.waterWorld", new Object[0]), new ResourceLocation("textures/gui/presets/water.png"), ChunkProviderSettings.Factory.jsonToFactory("{ \"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":5000.0, \"mainNoiseScaleY\":1000.0, \"mainNoiseScaleZ\":5000.0, \"baseSize\":8.5, \"stretchY\":8.0, \"biomeDepthWeight\":2.0, \"biomeDepthOffset\":0.5, \"biomeScaleWeight\":2.0, \"biomeScaleOffset\":0.375, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":255 }")));
        GuiScreenCustomizePresets.field_175310_f.add(new Info(I18n.format("createWorld.customize.custom.preset.isleLand", new Object[0]), new ResourceLocation("textures/gui/presets/isles.png"), ChunkProviderSettings.Factory.jsonToFactory("{\"coordinateScale\":3000.0, \"heightScale\":6000.0, \"upperLimitScale\":250.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":80.0, \"mainNoiseScaleY\":160.0, \"mainNoiseScaleZ\":80.0, \"baseSize\":8.5, \"stretchY\":10.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":63 }")));
        GuiScreenCustomizePresets.field_175310_f.add(new Info(I18n.format("createWorld.customize.custom.preset.caveDelight", new Object[0]), new ResourceLocation("textures/gui/presets/delight.png"), ChunkProviderSettings.Factory.jsonToFactory("{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":5000.0, \"mainNoiseScaleY\":1000.0, \"mainNoiseScaleZ\":5000.0, \"baseSize\":8.5, \"stretchY\":5.0, \"biomeDepthWeight\":2.0, \"biomeDepthOffset\":1.0, \"biomeScaleWeight\":4.0, \"biomeScaleOffset\":1.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":63 }")));
        GuiScreenCustomizePresets.field_175310_f.add(new Info(I18n.format("createWorld.customize.custom.preset.mountains", new Object[0]), new ResourceLocation("textures/gui/presets/madness.png"), ChunkProviderSettings.Factory.jsonToFactory("{\"coordinateScale\":738.41864, \"heightScale\":157.69133, \"upperLimitScale\":801.4267, \"lowerLimitScale\":1254.1643, \"depthNoiseScaleX\":374.93652, \"depthNoiseScaleZ\":288.65228, \"depthNoiseScaleExponent\":1.2092624, \"mainNoiseScaleX\":1355.9908, \"mainNoiseScaleY\":745.5343, \"mainNoiseScaleZ\":1183.464, \"baseSize\":1.8758626, \"stretchY\":1.7137525, \"biomeDepthWeight\":1.7553768, \"biomeDepthOffset\":3.4701107, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":2.535211, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":63 }")));
        GuiScreenCustomizePresets.field_175310_f.add(new Info(I18n.format("createWorld.customize.custom.preset.drought", new Object[0]), new ResourceLocation("textures/gui/presets/drought.png"), ChunkProviderSettings.Factory.jsonToFactory("{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":1000.0, \"mainNoiseScaleY\":3000.0, \"mainNoiseScaleZ\":1000.0, \"baseSize\":8.5, \"stretchY\":10.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":20 }")));
        GuiScreenCustomizePresets.field_175310_f.add(new Info(I18n.format("createWorld.customize.custom.preset.caveChaos", new Object[0]), new ResourceLocation("textures/gui/presets/chaos.png"), ChunkProviderSettings.Factory.jsonToFactory("{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":2.0, \"lowerLimitScale\":64.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":80.0, \"mainNoiseScaleY\":160.0, \"mainNoiseScaleZ\":80.0, \"baseSize\":8.5, \"stretchY\":12.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":6 }")));
        GuiScreenCustomizePresets.field_175310_f.add(new Info(I18n.format("createWorld.customize.custom.preset.goodLuck", new Object[0]), new ResourceLocation("textures/gui/presets/luck.png"), ChunkProviderSettings.Factory.jsonToFactory("{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":80.0, \"mainNoiseScaleY\":160.0, \"mainNoiseScaleZ\":80.0, \"baseSize\":8.5, \"stretchY\":12.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":true, \"seaLevel\":40 }")));
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.field_175311_g.handleMouseInput();
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        this.field_175317_i.mouseClicked(n, n2, n3);
        super.mouseClicked(n, n2, n3);
    }
    
    private boolean func_175305_g() {
        return (this.field_175311_g.field_178053_u > -1 && this.field_175311_g.field_178053_u < GuiScreenCustomizePresets.field_175310_f.size()) || this.field_175317_i.getText().length() > 1;
    }
    
    static GuiTextField access$200(final GuiScreenCustomizePresets guiScreenCustomizePresets) {
        return guiScreenCustomizePresets.field_175317_i;
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        switch (guiButton.id) {
            case 0: {
                this.field_175314_r.func_175324_a(this.field_175317_i.getText());
                this.mc.displayGuiScreen(this.field_175314_r);
                break;
            }
            case 1: {
                this.mc.displayGuiScreen(this.field_175314_r);
                break;
            }
        }
    }
    
    public GuiScreenCustomizePresets(final GuiCustomizeWorldScreen field_175314_r) {
        this.field_175315_a = "Customize World Presets";
        this.field_175314_r = field_175314_r;
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (!this.field_175317_i.textboxKeyTyped(c, n)) {
            super.keyTyped(c, n);
        }
    }
    
    static List access$000() {
        return GuiScreenCustomizePresets.field_175310_f;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        this.field_175315_a = I18n.format("createWorld.customize.custom.presets.title", new Object[0]);
        this.field_175313_s = I18n.format("createWorld.customize.presets.share", new Object[0]);
        this.field_175312_t = I18n.format("createWorld.customize.presets.list", new Object[0]);
        this.field_175317_i = new GuiTextField(2, this.fontRendererObj, 50, 40, GuiScreenCustomizePresets.width - 100, 20);
        this.field_175311_g = new ListPreset();
        this.field_175317_i.setMaxStringLength(2000);
        this.field_175317_i.setText(this.field_175314_r.func_175323_a());
        this.buttonList.add(this.field_175316_h = new GuiButton(0, GuiScreenCustomizePresets.width / 2 - 102, GuiScreenCustomizePresets.height - 27, 100, 20, I18n.format("createWorld.customize.presets.select", new Object[0])));
        this.buttonList.add(new GuiButton(1, GuiScreenCustomizePresets.width / 2 + 3, GuiScreenCustomizePresets.height - 27, 100, 20, I18n.format("gui.cancel", new Object[0])));
        this.func_175304_a();
    }
    
    public void func_175304_a() {
        this.field_175316_h.enabled = this.func_175305_g();
    }
    
    @Override
    public void updateScreen() {
        this.field_175317_i.updateCursorCounter();
        super.updateScreen();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.field_175311_g.drawScreen(n, n2, n3);
        this.drawCenteredString(this.fontRendererObj, this.field_175315_a, GuiScreenCustomizePresets.width / 2, 8, 16777215);
        this.drawString(this.fontRendererObj, this.field_175313_s, 50, 30, 10526880);
        this.drawString(this.fontRendererObj, this.field_175312_t, 50, 70, 10526880);
        this.field_175317_i.drawTextBox();
        super.drawScreen(n, n2, n3);
    }
    
    static ListPreset access$100(final GuiScreenCustomizePresets guiScreenCustomizePresets) {
        return guiScreenCustomizePresets.field_175311_g;
    }
    
    static class Info
    {
        public String field_178955_a;
        public ResourceLocation field_178953_b;
        public ChunkProviderSettings.Factory field_178954_c;
        
        public Info(final String field_178955_a, final ResourceLocation field_178953_b, final ChunkProviderSettings.Factory field_178954_c) {
            this.field_178955_a = field_178955_a;
            this.field_178953_b = field_178953_b;
            this.field_178954_c = field_178954_c;
        }
    }
    
    class ListPreset extends GuiSlot
    {
        public int field_178053_u;
        final GuiScreenCustomizePresets this$0;
        
        @Override
        protected boolean isSelected(final int n) {
            return n == this.field_178053_u;
        }
        
        @Override
        protected int getSize() {
            return GuiScreenCustomizePresets.access$000().size();
        }
        
        @Override
        protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            final Info info = GuiScreenCustomizePresets.access$000().get(n);
            this.func_178051_a(n2, n3, info.field_178953_b);
            this.this$0.fontRendererObj.drawString(info.field_178955_a, n2 + 32 + 10, n3 + 14, 16777215);
        }
        
        @Override
        protected void elementClicked(final int field_178053_u, final boolean b, final int n, final int n2) {
            this.field_178053_u = field_178053_u;
            this.this$0.func_175304_a();
            GuiScreenCustomizePresets.access$200(this.this$0).setText(GuiScreenCustomizePresets.access$000().get(GuiScreenCustomizePresets.access$100(this.this$0).field_178053_u).field_178954_c.toString());
        }
        
        public ListPreset(final GuiScreenCustomizePresets this$0) {
            this.this$0 = this$0;
            super(this$0.mc, GuiScreenCustomizePresets.width, GuiScreenCustomizePresets.height, 80, GuiScreenCustomizePresets.height - 32, 38);
            this.field_178053_u = -1;
        }
        
        private void func_178051_a(final int n, final int n2, final ResourceLocation resourceLocation) {
            final int n3 = n + 5;
            this.this$0.drawHorizontalLine(n3 - 1, n3 + 32, n2 - 1, -2039584);
            this.this$0.drawHorizontalLine(n3 - 1, n3 + 32, n2 + 32, -6250336);
            this.this$0.drawVerticalLine(n3 - 1, n2 - 1, n2 + 32, -2039584);
            this.this$0.drawVerticalLine(n3 + 32, n2 - 1, n2 + 32, -6250336);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.getTextureManager().bindTexture(resourceLocation);
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
            worldRenderer.pos(n3 + 0, n2 + 32, 0.0).tex(0.0, 1.0).endVertex();
            worldRenderer.pos(n3 + 32, n2 + 32, 0.0).tex(1.0, 1.0).endVertex();
            worldRenderer.pos(n3 + 32, n2 + 0, 0.0).tex(1.0, 0.0).endVertex();
            worldRenderer.pos(n3 + 0, n2 + 0, 0.0).tex(0.0, 0.0).endVertex();
            instance.draw();
        }
        
        @Override
        protected void drawBackground() {
        }
    }
}
