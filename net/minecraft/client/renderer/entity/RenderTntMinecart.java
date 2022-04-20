package net.minecraft.client.renderer.entity;

import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.*;

public class RenderTntMinecart extends RenderMinecart
{
    protected void func_180560_a(final EntityMinecartTNT entityMinecartTNT, final float n, final IBlockState blockState) {
        final int fuseTicks = entityMinecartTNT.getFuseTicks();
        if (fuseTicks > -1 && fuseTicks - n + 1.0f < 10.0f) {
            final float clamp_float = MathHelper.clamp_float(1.0f - (fuseTicks - n + 1.0f) / 10.0f, 0.0f, 1.0f);
            final float n2 = clamp_float * clamp_float;
            final float n3 = 1.0f + n2 * n2 * 0.3f;
            GlStateManager.scale(n3, n3, n3);
        }
        super.func_180560_a(entityMinecartTNT, n, blockState);
        if (fuseTicks > -1 && fuseTicks / 5 % 2 == 0) {
            final BlockRendererDispatcher blockRendererDispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
            GlStateManager.blendFunc(770, 772);
            GlStateManager.color(1.0f, 1.0f, 1.0f, (1.0f - (fuseTicks - n + 1.0f) / 100.0f) * 0.8f);
            blockRendererDispatcher.renderBlockBrightness(Blocks.tnt.getDefaultState(), 1.0f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    public RenderTntMinecart(final RenderManager renderManager) {
        super(renderManager);
    }
    
    @Override
    protected void func_180560_a(final EntityMinecart entityMinecart, final float n, final IBlockState blockState) {
        this.func_180560_a((EntityMinecartTNT)entityMinecart, n, blockState);
    }
}
