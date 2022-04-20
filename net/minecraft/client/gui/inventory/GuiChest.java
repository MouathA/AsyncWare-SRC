package net.minecraft.client.gui.inventory;

import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

public class GuiChest extends GuiContainer
{
    private static final ResourceLocation CHEST_GUI_TEXTURE;
    private int inventoryRows;
    private IInventory lowerChestInventory;
    private IInventory upperChestInventory;
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiChest.CHEST_GUI_TEXTURE);
        final int n4 = (GuiChest.width - this.xSize) / 2;
        final int n5 = (GuiChest.height - this.ySize) / 2;
        this.drawTexturedModalRect(n4, n5, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        this.drawTexturedModalRect(n4, n5 + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }
    
    static {
        CHEST_GUI_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        this.fontRendererObj.drawString(this.lowerChestInventory.getDisplayName().getUnformattedText(), 8.0, 6.0, 4210752);
        this.fontRendererObj.drawString(this.upperChestInventory.getDisplayName().getUnformattedText(), 8.0, this.ySize - 96 + 2, 4210752);
    }
    
    public GuiChest(final IInventory upperChestInventory, final IInventory lowerChestInventory) {
        super(new ContainerChest(upperChestInventory, lowerChestInventory, Minecraft.getMinecraft().thePlayer));
        this.upperChestInventory = upperChestInventory;
        this.lowerChestInventory = lowerChestInventory;
        this.allowUserInput = false;
        this.inventoryRows = lowerChestInventory.getSizeInventory() / 9;
        this.ySize = 114 + this.inventoryRows * 18;
    }
}
