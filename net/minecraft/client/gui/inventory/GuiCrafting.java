package net.minecraft.client.gui.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.resources.*;
import net.minecraft.inventory.*;

public class GuiCrafting extends GuiContainer
{
    private static final ResourceLocation craftingTableGuiTextures;
    
    public GuiCrafting(final InventoryPlayer inventoryPlayer, final World world) {
        this(inventoryPlayer, world, BlockPos.ORIGIN);
    }
    
    static {
        craftingTableGuiTextures = new ResourceLocation("textures/gui/container/crafting_table.png");
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiCrafting.craftingTableGuiTextures);
        this.drawTexturedModalRect((GuiCrafting.width - this.xSize) / 2, (GuiCrafting.height - this.ySize) / 2, 0, 0, this.xSize, this.ySize);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 28.0, 6.0, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8.0, this.ySize - 96 + 2, 4210752);
    }
    
    public GuiCrafting(final InventoryPlayer inventoryPlayer, final World world, final BlockPos blockPos) {
        super(new ContainerWorkbench(inventoryPlayer, world, blockPos));
    }
}
