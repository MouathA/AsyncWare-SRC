package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.model.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.client.*;
import com.google.common.collect.*;
import net.minecraft.client.renderer.texture.*;
import java.util.*;

public class TileEntityBannerRenderer extends TileEntitySpecialRenderer
{
    private static final Map DESIGNS;
    private ModelBanner bannerModel;
    private static final ResourceLocation BANNERTEXTURES;
    
    public TileEntityBannerRenderer() {
        this.bannerModel = new ModelBanner();
    }
    
    static {
        DESIGNS = Maps.newHashMap();
        BANNERTEXTURES = new ResourceLocation("textures/entity/banner_base.png");
    }
    
    public void renderTileEntityAt(final TileEntityBanner tileEntityBanner, final double n, final double n2, final double n3, final float n4, final int n5) {
        final boolean b = tileEntityBanner.getWorld() != null;
        final boolean b2 = !b || tileEntityBanner.getBlockType() == Blocks.standing_banner;
        final int n6 = b ? tileEntityBanner.getBlockMetadata() : 0;
        final long n7 = b ? tileEntityBanner.getWorld().getTotalWorldTime() : 0L;
        final float n8 = 0.6666667f;
        if (b2) {
            GlStateManager.translate((float)n + 0.5f, (float)n2 + 0.75f * n8, (float)n3 + 0.5f);
            GlStateManager.rotate(-(n6 * 360 / 16.0f), 0.0f, 1.0f, 0.0f);
            this.bannerModel.bannerStand.showModel = true;
        }
        else {
            float n9 = 0.0f;
            if (n6 == 2) {
                n9 = 180.0f;
            }
            if (n6 == 4) {
                n9 = 90.0f;
            }
            if (n6 == 5) {
                n9 = -90.0f;
            }
            GlStateManager.translate((float)n + 0.5f, (float)n2 - 0.25f * n8, (float)n3 + 0.5f);
            GlStateManager.rotate(-n9, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(0.0f, -0.3125f, -0.4375f);
            this.bannerModel.bannerStand.showModel = false;
        }
        final BlockPos pos = tileEntityBanner.getPos();
        this.bannerModel.bannerSlate.rotateAngleX = (-0.0125f + 0.01f * MathHelper.cos((pos.getX() * 7 + pos.getY() * 9 + pos.getZ() * 13 + (float)n7 + n4) * 3.1415927f * 0.02f)) * 3.1415927f;
        final ResourceLocation func_178463_a = this.func_178463_a(tileEntityBanner);
        if (func_178463_a != null) {
            this.bindTexture(func_178463_a);
            GlStateManager.scale(n8, -n8, -n8);
            this.bannerModel.renderBanner();
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity tileEntity, final double n, final double n2, final double n3, final float n4, final int n5) {
        this.renderTileEntityAt((TileEntityBanner)tileEntity, n, n2, n3, n4, n5);
    }
    
    private ResourceLocation func_178463_a(final TileEntityBanner tileEntityBanner) {
        final String func_175116_e = tileEntityBanner.func_175116_e();
        if (func_175116_e.isEmpty()) {
            return null;
        }
        TimedBannerTexture timedBannerTexture = TileEntityBannerRenderer.DESIGNS.get(func_175116_e);
        if (timedBannerTexture == null) {
            if (TileEntityBannerRenderer.DESIGNS.size() >= 256) {
                final long currentTimeMillis = System.currentTimeMillis();
                final Iterator<String> iterator = TileEntityBannerRenderer.DESIGNS.keySet().iterator();
                while (iterator.hasNext()) {
                    final TimedBannerTexture timedBannerTexture2 = TileEntityBannerRenderer.DESIGNS.get(iterator.next());
                    if (currentTimeMillis - timedBannerTexture2.systemTime > 60000L) {
                        Minecraft.getMinecraft().getTextureManager().deleteTexture(timedBannerTexture2.bannerTexture);
                        iterator.remove();
                    }
                }
                if (TileEntityBannerRenderer.DESIGNS.size() >= 256) {
                    return null;
                }
            }
            final List patternList = tileEntityBanner.getPatternList();
            final List colorList = tileEntityBanner.getColorList();
            final ArrayList arrayList = Lists.newArrayList();
            final Iterator<TileEntityBanner.EnumBannerPattern> iterator2 = patternList.iterator();
            while (iterator2.hasNext()) {
                arrayList.add("textures/entity/banner/" + iterator2.next().getPatternName() + ".png");
            }
            timedBannerTexture = new TimedBannerTexture(null);
            timedBannerTexture.bannerTexture = new ResourceLocation(func_175116_e);
            Minecraft.getMinecraft().getTextureManager().loadTexture(timedBannerTexture.bannerTexture, new LayeredColorMaskTexture(TileEntityBannerRenderer.BANNERTEXTURES, arrayList, colorList));
            TileEntityBannerRenderer.DESIGNS.put(func_175116_e, timedBannerTexture);
        }
        timedBannerTexture.systemTime = System.currentTimeMillis();
        return timedBannerTexture.bannerTexture;
    }
    
    static class TimedBannerTexture
    {
        public long systemTime;
        public ResourceLocation bannerTexture;
        
        TimedBannerTexture(final TileEntityBannerRenderer$1 object) {
            this();
        }
        
        private TimedBannerTexture() {
        }
    }
}
