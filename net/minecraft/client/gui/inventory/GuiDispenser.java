package net.minecraft.client.gui.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.inventory.*;

public class GuiDispenser extends GuiContainer
{
    private final InventoryPlayer playerInventory;
    public IInventory dispenserInventory;
    private static final ResourceLocation dispenserGuiTextures;
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiDispenser.dispenserGuiTextures);
        this.drawTexturedModalRect((GuiDispenser.width - this.xSize) / 2, (GuiDispenser.height - this.ySize) / 2, 0, 0, this.xSize, this.ySize);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        final String unformattedText = this.dispenserInventory.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(unformattedText, this.xSize / 2 - this.fontRendererObj.getStringWidth(unformattedText) / 2, 6.0, 4210752);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8.0, this.ySize - 96 + 2, 4210752);
    }
    
    static {
        dispenserGuiTextures = new ResourceLocation("textures/gui/container/dispenser.png");
    }
    
    public GuiDispenser(final InventoryPlayer playerInventory, final IInventory dispenserInventory) {
        super(new ContainerDispenser(playerInventory, dispenserInventory));
        this.playerInventory = playerInventory;
        this.dispenserInventory = dispenserInventory;
    }
}
