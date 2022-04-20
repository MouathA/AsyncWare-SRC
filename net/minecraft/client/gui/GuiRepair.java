package net.minecraft.client.gui;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import java.io.*;
import org.lwjgl.input.*;
import io.netty.buffer.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.item.*;
import net.minecraft.client.resources.*;
import java.util.*;
import net.minecraft.inventory.*;

public class GuiRepair extends GuiContainer implements ICrafting
{
    private static final ResourceLocation anvilResource;
    private GuiTextField nameField;
    private ContainerRepair anvil;
    private InventoryPlayer playerInventory;
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiRepair.anvilResource);
        final int n4 = (GuiRepair.width - this.xSize) / 2;
        final int n5 = (GuiRepair.height - this.ySize) / 2;
        this.drawTexturedModalRect(n4, n5, 0, 0, this.xSize, this.ySize);
        this.drawTexturedModalRect(n4 + 59, n5 + 20, 0, this.ySize + (this.anvil.getSlot(0).getHasStack() ? 0 : 16), 110, 16);
        if ((this.anvil.getSlot(0).getHasStack() || this.anvil.getSlot(1).getHasStack()) && !this.anvil.getSlot(2).getHasStack()) {
            this.drawTexturedModalRect(n4 + 99, n5 + 45, this.xSize, 0, 28, 21);
        }
    }
    
    public GuiRepair(final InventoryPlayer playerInventory, final World world) {
        super(new ContainerRepair(playerInventory, world, Minecraft.getMinecraft().thePlayer));
        this.playerInventory = playerInventory;
        this.anvil = (ContainerRepair)this.inventorySlots;
    }
    
    static {
        anvilResource = new ResourceLocation("textures/gui/container/anvil.png");
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (this.nameField.textboxKeyTyped(c, n)) {
            this.renameItem();
        }
        else {
            super.keyTyped(c, n);
        }
    }
    
    @Override
    public void initGui() {
        super.initGui();
        Keyboard.enableRepeatEvents(true);
        (this.nameField = new GuiTextField(0, this.fontRendererObj, (GuiRepair.width - this.xSize) / 2 + 62, (GuiRepair.height - this.ySize) / 2 + 24, 103, 12)).setTextColor(-1);
        this.nameField.setDisabledTextColour(-1);
        this.nameField.setEnableBackgroundDrawing(false);
        this.nameField.setMaxStringLength(30);
        this.inventorySlots.removeCraftingFromCrafters(this);
        this.inventorySlots.onCraftGuiOpened(this);
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        this.nameField.mouseClicked(n, n2, n3);
    }
    
    private void renameItem() {
        String text = this.nameField.getText();
        final Slot slot = this.anvil.getSlot(0);
        if (slot != null && slot.getHasStack() && !slot.getStack().hasDisplayName() && text.equals(slot.getStack().getDisplayName())) {
            text = "";
        }
        this.anvil.updateItemName(text);
        this.mc.thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("MC|ItemName", new PacketBuffer(Unpooled.buffer()).writeString(text)));
    }
    
    @Override
    public void sendSlotContents(final Container container, final int n, final ItemStack itemStack) {
        if (n == 0) {
            this.nameField.setText((itemStack == null) ? "" : itemStack.getDisplayName());
            this.nameField.setEnabled(itemStack != null);
            if (itemStack != null) {
                this.renameItem();
            }
        }
    }
    
    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
        this.inventorySlots.removeCraftingFromCrafters(this);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        this.fontRendererObj.drawString(I18n.format("container.repair", new Object[0]), 60.0, 6.0, 4210752);
        if (this.anvil.maximumCost > 0) {
            I18n.format("container.repair.cost", this.anvil.maximumCost);
            if (this.anvil.maximumCost >= 40 && !this.mc.thePlayer.capabilities.isCreativeMode) {
                I18n.format("container.repair.expensive", new Object[0]);
            }
            else if (this.anvil.getSlot(2).getHasStack()) {
                if (!this.anvil.getSlot(2).canTakeStack(this.playerInventory.player)) {}
            }
        }
    }
    
    @Override
    public void sendProgressBarUpdate(final Container container, final int n, final int n2) {
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        super.drawScreen(n, n2, n3);
        this.nameField.drawTextBox();
    }
    
    @Override
    public void updateCraftingInventory(final Container container, final List list) {
        this.sendSlotContents(container, 0, container.getSlot(0).getStack());
    }
    
    @Override
    public void func_175173_a(final Container container, final IInventory inventory) {
    }
}
