package net.minecraft.client.gui.inventory;

import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.client.renderer.*;

public class GuiBrewingStand extends GuiContainer
{
    private static final ResourceLocation brewingStandGuiTextures;
    private final InventoryPlayer playerInventory;
    private IInventory tileBrewingStand;
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        final String unformattedText = this.tileBrewingStand.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(unformattedText, this.xSize / 2 - this.fontRendererObj.getStringWidth(unformattedText) / 2, 6.0, 4210752);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8.0, this.ySize - 96 + 2, 4210752);
    }
    
    static {
        brewingStandGuiTextures = new ResourceLocation("textures/gui/container/brewing_stand.png");
    }
    
    public GuiBrewingStand(final InventoryPlayer playerInventory, final IInventory tileBrewingStand) {
        super(new ContainerBrewingStand(playerInventory, tileBrewingStand));
        this.playerInventory = playerInventory;
        this.tileBrewingStand = tileBrewingStand;
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiBrewingStand.brewingStandGuiTextures);
        this.drawTexturedModalRect((GuiBrewingStand.width - this.xSize) / 2, (GuiBrewingStand.height - this.ySize) / 2, 0, 0, this.xSize, this.ySize);
        final int field = this.tileBrewingStand.getField(0);
        if (field > 0) {
            final int n4 = (int)(28.0f * (1.0f - field / 400.0f));
            switch (field / 2 % 7) {
            }
        }
    }
}
