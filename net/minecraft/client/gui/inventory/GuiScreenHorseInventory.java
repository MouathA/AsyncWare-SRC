package net.minecraft.client.gui.inventory;

import net.minecraft.util.*;
import net.minecraft.entity.passive.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class GuiScreenHorseInventory extends GuiContainer
{
    private IInventory horseInventory;
    private float mousePosY;
    private IInventory playerInventory;
    private static final ResourceLocation horseGuiTextures;
    private float mousePosx;
    private EntityHorse horseEntity;
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        this.fontRendererObj.drawString(this.horseInventory.getDisplayName().getUnformattedText(), 8.0, 6.0, 4210752);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8.0, this.ySize - 96 + 2, 4210752);
    }
    
    public GuiScreenHorseInventory(final IInventory playerInventory, final IInventory horseInventory, final EntityHorse horseEntity) {
        super(new ContainerHorseInventory(playerInventory, horseInventory, horseEntity, Minecraft.getMinecraft().thePlayer));
        this.playerInventory = playerInventory;
        this.horseInventory = horseInventory;
        this.horseEntity = horseEntity;
        this.allowUserInput = false;
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.mousePosx = (float)n;
        this.mousePosY = (float)n2;
        super.drawScreen(n, n2, n3);
    }
    
    static {
        horseGuiTextures = new ResourceLocation("textures/gui/container/horse.png");
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiScreenHorseInventory.horseGuiTextures);
        final int n4 = (GuiScreenHorseInventory.width - this.xSize) / 2;
        final int n5 = (GuiScreenHorseInventory.height - this.ySize) / 2;
        this.drawTexturedModalRect(n4, n5, 0, 0, this.xSize, this.ySize);
        if (this.horseEntity.isChested()) {
            this.drawTexturedModalRect(n4 + 79, n5 + 17, 0, this.ySize, 90, 54);
        }
        if (this.horseEntity.canWearArmor()) {
            this.drawTexturedModalRect(n4 + 7, n5 + 35, 0, this.ySize + 54, 18, 18);
        }
        GuiInventory.drawEntityOnScreen(n4 + 51, n5 + 60, 17, n4 + 51 - this.mousePosx, n5 + 75 - 50 - this.mousePosY, this.horseEntity);
    }
}
