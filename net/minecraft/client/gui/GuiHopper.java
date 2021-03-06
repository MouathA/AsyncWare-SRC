package net.minecraft.client.gui;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.client.renderer.*;

public class GuiHopper extends GuiContainer
{
    private static final ResourceLocation HOPPER_GUI_TEXTURE;
    private IInventory playerInventory;
    private IInventory hopperInventory;
    
    public GuiHopper(final InventoryPlayer playerInventory, final IInventory hopperInventory) {
        super(new ContainerHopper(playerInventory, hopperInventory, Minecraft.getMinecraft().thePlayer));
        this.playerInventory = playerInventory;
        this.hopperInventory = hopperInventory;
        this.allowUserInput = false;
        this.ySize = 133;
    }
    
    static {
        HOPPER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/hopper.png");
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        this.fontRendererObj.drawString(this.hopperInventory.getDisplayName().getUnformattedText(), 8.0, 6.0, 4210752);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8.0, this.ySize - 96 + 2, 4210752);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiHopper.HOPPER_GUI_TEXTURE);
        this.drawTexturedModalRect((GuiHopper.width - this.xSize) / 2, (GuiHopper.height - this.ySize) / 2, 0, 0, this.xSize, this.ySize);
    }
}
