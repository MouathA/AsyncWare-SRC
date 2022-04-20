package net.minecraft.client.renderer;

import net.minecraft.block.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.item.*;

public class ChestRenderer
{
    public void renderChestBrightness(final Block block, final float n) {
        GlStateManager.color(n, n, n, 1.0f);
        GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
        TileEntityItemStackRenderer.instance.renderByItem(new ItemStack(block));
    }
}
