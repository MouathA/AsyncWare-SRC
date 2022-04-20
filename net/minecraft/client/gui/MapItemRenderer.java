package net.minecraft.client.gui;

import net.minecraft.world.storage.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.block.material.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;

public class MapItemRenderer
{
    private final Map loadedMaps;
    private static final ResourceLocation mapIcons;
    private final TextureManager textureManager;
    
    static ResourceLocation access$500() {
        return MapItemRenderer.mapIcons;
    }
    
    static {
        mapIcons = new ResourceLocation("textures/map/map_icons.png");
    }
    
    public void updateMapTexture(final MapData mapData) {
        Instance.access$000(this.getMapRendererInstance(mapData));
    }
    
    public void clearLoadedMaps() {
        final Iterator<Instance> iterator = this.loadedMaps.values().iterator();
        while (iterator.hasNext()) {
            this.textureManager.deleteTexture(Instance.access$300(iterator.next()));
        }
        this.loadedMaps.clear();
    }
    
    public MapItemRenderer(final TextureManager textureManager) {
        this.loadedMaps = Maps.newHashMap();
        this.textureManager = textureManager;
    }
    
    public void renderMap(final MapData mapData, final boolean b) {
        Instance.access$100(this.getMapRendererInstance(mapData), b);
    }
    
    static TextureManager access$400(final MapItemRenderer mapItemRenderer) {
        return mapItemRenderer.textureManager;
    }
    
    private Instance getMapRendererInstance(final MapData mapData) {
        Instance instance = this.loadedMaps.get(mapData.mapName);
        if (instance == null) {
            instance = new Instance(mapData, null);
            this.loadedMaps.put(mapData.mapName, instance);
        }
        return instance;
    }
    
    class Instance
    {
        private final MapData mapData;
        final MapItemRenderer this$0;
        private final ResourceLocation location;
        private final int[] mapTextureData;
        private final DynamicTexture mapTexture;
        
        static ResourceLocation access$300(final Instance instance) {
            return instance.location;
        }
        
        private void updateMapTexture() {
            while (true) {
                final int n = this.mapData.colors[0] & 0xFF;
                if (n / 4 == 0) {
                    this.mapTextureData[0] = 268435456;
                }
                else {
                    this.mapTextureData[0] = MapColor.mapColorArray[n / 4].func_151643_b(n & 0x3);
                }
                int n2 = 0;
                ++n2;
            }
        }
        
        private void render(final boolean b) {
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            final float n = 0.0f;
            MapItemRenderer.access$400(this.this$0).bindTexture(this.location);
            GlStateManager.tryBlendFuncSeparate(1, 771, 0, 1);
            worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
            worldRenderer.pos(0 + n, 128 - n, -0.009999999776482582).tex(0.0, 1.0).endVertex();
            worldRenderer.pos(128 - n, 128 - n, -0.009999999776482582).tex(1.0, 1.0).endVertex();
            worldRenderer.pos(128 - n, 0 + n, -0.009999999776482582).tex(1.0, 0.0).endVertex();
            worldRenderer.pos(0 + n, 0 + n, -0.009999999776482582).tex(0.0, 0.0).endVertex();
            instance.draw();
            MapItemRenderer.access$400(this.this$0).bindTexture(MapItemRenderer.access$500());
            for (final Vec4b vec4b : this.mapData.mapDecorations.values()) {
                if (!b || vec4b.func_176110_a() == 1) {
                    GlStateManager.translate(0 + vec4b.func_176112_b() / 2.0f + 64.0f, 0 + vec4b.func_176113_c() / 2.0f + 64.0f, -0.02f);
                    GlStateManager.rotate(vec4b.func_176111_d() * 360 / 16.0f, 0.0f, 0.0f, 1.0f);
                    GlStateManager.scale(4.0f, 4.0f, 3.0f);
                    GlStateManager.translate(-0.125f, 0.125f, 0.0f);
                    final byte func_176110_a = vec4b.func_176110_a();
                    final float n2 = (func_176110_a % 4 + 0) / 4.0f;
                    final float n3 = (func_176110_a / 4 + 0) / 4.0f;
                    final float n4 = (func_176110_a % 4 + 1) / 4.0f;
                    final float n5 = (func_176110_a / 4 + 1) / 4.0f;
                    worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
                    worldRenderer.pos(-1.0, 1.0, 0 * -0.001f).tex(n2, n3).endVertex();
                    worldRenderer.pos(1.0, 1.0, 0 * -0.001f).tex(n4, n3).endVertex();
                    worldRenderer.pos(1.0, -1.0, 0 * -0.001f).tex(n4, n5).endVertex();
                    worldRenderer.pos(-1.0, -1.0, 0 * -0.001f).tex(n2, n5).endVertex();
                    instance.draw();
                    int n6 = 0;
                    ++n6;
                }
            }
            GlStateManager.translate(0.0f, 0.0f, -0.04f);
            GlStateManager.scale(1.0f, 1.0f, 1.0f);
        }
        
        static void access$100(final Instance instance, final boolean b) {
            instance.render(b);
        }
        
        Instance(final MapItemRenderer mapItemRenderer, final MapData mapData, final MapItemRenderer$1 object) {
            this(mapItemRenderer, mapData);
        }
        
        static void access$000(final Instance instance) {
            instance.updateMapTexture();
        }
        
        private Instance(final MapItemRenderer this$0, final MapData mapData) {
            this.this$0 = this$0;
            this.mapData = mapData;
            this.mapTexture = new DynamicTexture(128, 128);
            this.mapTextureData = this.mapTexture.getTextureData();
            this.location = MapItemRenderer.access$400(this$0).getDynamicTextureLocation("map/" + mapData.mapName, this.mapTexture);
            while (0 < this.mapTextureData.length) {
                this.mapTextureData[0] = 0;
                int n = 0;
                ++n;
            }
        }
    }
}
