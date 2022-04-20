package net.minecraft.client.gui.inventory;

import net.minecraft.client.gui.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import com.google.common.collect.*;
import net.minecraft.entity.player.*;
import java.util.*;
import java.io.*;
import net.minecraft.client.renderer.texture.*;

public abstract class GuiContainer extends GuiScreen
{
    private long lastClickTime;
    private boolean ignoreMouseUp;
    private ItemStack shiftClickedSlot;
    private int dragSplittingButton;
    protected boolean dragSplitting;
    protected final Set dragSplittingSlots;
    private Slot currentDragTargetSlot;
    protected int xSize;
    public Container inventorySlots;
    private Slot clickedSlot;
    protected int guiTop;
    private boolean isRightMouseClick;
    private ItemStack draggedStack;
    private int dragSplittingRemnant;
    private boolean doubleClick;
    private long returningStackTime;
    private Slot lastClickSlot;
    protected int ySize;
    protected int guiLeft;
    private Slot theSlot;
    private int touchUpY;
    private long dragItemDropDelay;
    private int lastClickButton;
    private ItemStack returningStack;
    private int touchUpX;
    private int dragSplittingLimit;
    private Slot returningStackDestSlot;
    protected static final ResourceLocation inventoryBackground;
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        final int guiLeft = this.guiLeft;
        final int guiTop = this.guiTop;
        this.drawGuiContainerBackgroundLayer(n3, n, n2);
        super.drawScreen(n, n2, n3);
        GlStateManager.translate((float)guiLeft, (float)guiTop, 0.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.theSlot = null;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240 / 1.0f, 240 / 1.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        while (0 < this.inventorySlots.inventorySlots.size()) {
            final Slot theSlot = this.inventorySlots.inventorySlots.get(0);
            this.drawSlot(theSlot);
            if (this.isMouseOverSlot(theSlot, n, n2) && theSlot.canBeHovered()) {
                this.theSlot = theSlot;
                final int xDisplayPosition = theSlot.xDisplayPosition;
                final int yDisplayPosition = theSlot.yDisplayPosition;
                GlStateManager.colorMask(true, true, true, false);
                this.drawGradientRect(8, yDisplayPosition, 24, yDisplayPosition + 16, -2130706433, -2130706433);
                GlStateManager.colorMask(true, true, true, true);
            }
            int n4 = 0;
            ++n4;
        }
        this.drawGuiContainerForegroundLayer(n, n2);
        final InventoryPlayer inventory = this.mc.thePlayer.inventory;
        ItemStack itemStack = (this.draggedStack == null) ? inventory.getItemStack() : this.draggedStack;
        if (itemStack != null) {
            final int n5 = (this.draggedStack == null) ? 8 : 16;
            String string = null;
            if (this.draggedStack != null && this.isRightMouseClick) {
                itemStack = itemStack.copy();
                itemStack.stackSize = MathHelper.ceiling_float_int(itemStack.stackSize / 2.0f);
            }
            else if (this.dragSplitting && this.dragSplittingSlots.size() > 1) {
                itemStack = itemStack.copy();
                itemStack.stackSize = this.dragSplittingRemnant;
                if (itemStack.stackSize == 0) {
                    string = "" + EnumChatFormatting.YELLOW + "0";
                }
            }
            this.drawItemStack(itemStack, n - guiLeft - 8, n2 - guiTop - n5, string);
        }
        if (this.returningStack != null) {
            float n6 = (Minecraft.getSystemTime() - this.returningStackTime) / 100.0f;
            if (n6 >= 1.0f) {
                n6 = 1.0f;
                this.returningStack = null;
            }
            this.drawItemStack(this.returningStack, this.touchUpX + (int)((this.returningStackDestSlot.xDisplayPosition - this.touchUpX) * n6), this.touchUpY + (int)((this.returningStackDestSlot.yDisplayPosition - this.touchUpY) * n6), null);
        }
        if (inventory.getItemStack() == null && this.theSlot != null && this.theSlot.getHasStack()) {
            this.renderToolTip(this.theSlot.getStack(), n, n2);
        }
    }
    
    static {
        inventoryBackground = new ResourceLocation("textures/gui/container/inventory.png");
    }
    
    protected boolean isPointInRegion(final int n, final int n2, final int n3, final int n4, int n5, int n6) {
        final int guiLeft = this.guiLeft;
        final int guiTop = this.guiTop;
        n5 -= guiLeft;
        n6 -= guiTop;
        return n5 >= n - 1 && n5 < n + n3 + 1 && n6 >= n2 - 1 && n6 < n2 + n4 + 1;
    }
    
    public GuiContainer(final Container inventorySlots) {
        this.xSize = 176;
        this.ySize = 166;
        this.dragSplittingSlots = Sets.newHashSet();
        this.inventorySlots = inventorySlots;
        this.ignoreMouseUp = true;
    }
    
    private Slot getSlotAtPosition(final int n, final int n2) {
        while (0 < this.inventorySlots.inventorySlots.size()) {
            final Slot slot = this.inventorySlots.inventorySlots.get(0);
            if (this.isMouseOverSlot(slot, n, n2)) {
                return slot;
            }
            int n3 = 0;
            ++n3;
        }
        return null;
    }
    
    private boolean isMouseOverSlot(final Slot slot, final int n, final int n2) {
        return this.isPointInRegion(slot.xDisplayPosition, slot.yDisplayPosition, 16, 16, n, n2);
    }
    
    @Override
    protected void mouseReleased(final int n, final int n2, final int n3) {
        final Slot slotAtPosition = this.getSlotAtPosition(n, n2);
        final int guiLeft = this.guiLeft;
        final int guiTop = this.guiTop;
        final boolean b = n < guiLeft || n2 < guiTop || n >= guiLeft + this.xSize || n2 >= guiTop + this.ySize;
        if (slotAtPosition != null) {
            final int slotNumber = slotAtPosition.slotNumber;
        }
        if (b) {}
        if (this.doubleClick && slotAtPosition != null && n3 == 0 && this.inventorySlots.canMergeSlot(null, slotAtPosition)) {
            if (isShiftKeyDown()) {
                if (slotAtPosition != null && slotAtPosition.inventory != null && this.shiftClickedSlot != null) {
                    for (final Slot slot : this.inventorySlots.inventorySlots) {
                        if (slot != null && slot.canTakeStack(this.mc.thePlayer) && slot.getHasStack() && slot.inventory == slotAtPosition.inventory && Container.canAddItemToSlot(slot, this.shiftClickedSlot, true)) {
                            this.handleMouseClick(slot, slot.slotNumber, n3, 1);
                        }
                    }
                }
            }
            else {
                this.handleMouseClick(slotAtPosition, -999, n3, 6);
            }
            this.doubleClick = false;
            this.lastClickTime = 0L;
        }
        else {
            if (this.dragSplitting && this.dragSplittingButton != n3) {
                this.dragSplitting = false;
                this.dragSplittingSlots.clear();
                this.ignoreMouseUp = true;
                return;
            }
            if (this.ignoreMouseUp) {
                this.ignoreMouseUp = false;
                return;
            }
            if (this.clickedSlot != null && this.mc.gameSettings.touchscreen) {
                if (n3 == 0 || n3 == 1) {
                    if (this.draggedStack == null && slotAtPosition != this.clickedSlot) {
                        this.draggedStack = this.clickedSlot.getStack();
                    }
                    final boolean canAddItemToSlot = Container.canAddItemToSlot(slotAtPosition, this.draggedStack, false);
                    if (this.draggedStack != null && canAddItemToSlot) {
                        this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, n3, 0);
                        this.handleMouseClick(slotAtPosition, -999, 0, 0);
                        if (this.mc.thePlayer.inventory.getItemStack() != null) {
                            this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, n3, 0);
                            this.touchUpX = n - guiLeft;
                            this.touchUpY = n2 - guiTop;
                            this.returningStackDestSlot = this.clickedSlot;
                            this.returningStack = this.draggedStack;
                            this.returningStackTime = Minecraft.getSystemTime();
                        }
                        else {
                            this.returningStack = null;
                        }
                    }
                    else if (this.draggedStack != null) {
                        this.touchUpX = n - guiLeft;
                        this.touchUpY = n2 - guiTop;
                        this.returningStackDestSlot = this.clickedSlot;
                        this.returningStack = this.draggedStack;
                        this.returningStackTime = Minecraft.getSystemTime();
                    }
                    this.draggedStack = null;
                    this.clickedSlot = null;
                }
            }
            else if (this.dragSplitting && !this.dragSplittingSlots.isEmpty()) {
                this.handleMouseClick(null, -999, Container.func_94534_d(0, this.dragSplittingLimit), 5);
                for (final Slot slot2 : this.dragSplittingSlots) {
                    this.handleMouseClick(slot2, slot2.slotNumber, Container.func_94534_d(1, this.dragSplittingLimit), 5);
                }
                this.handleMouseClick(null, -999, Container.func_94534_d(2, this.dragSplittingLimit), 5);
            }
            else if (this.mc.thePlayer.inventory.getItemStack() != null) {
                if (n3 == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
                    this.handleMouseClick(slotAtPosition, -999, n3, 3);
                }
                else {
                    final int n4 = 0;
                    if (n4 != 0) {
                        this.shiftClickedSlot = ((slotAtPosition != null && slotAtPosition.getHasStack()) ? slotAtPosition.getStack() : null);
                    }
                    this.handleMouseClick(slotAtPosition, -999, n3, n4);
                }
            }
        }
        if (this.mc.thePlayer.inventory.getItemStack() == null) {
            this.lastClickTime = 0L;
        }
        this.dragSplitting = false;
    }
    
    @Override
    public void onGuiClosed() {
        if (this.mc.thePlayer != null) {
            this.inventorySlots.onContainerClosed(this.mc.thePlayer);
        }
    }
    
    protected boolean checkHotbarKeys(final int i) {
        if (this.mc.thePlayer.inventory.getItemStack() == null && this.theSlot != null) {
            while (i != this.mc.gameSettings.keyBindsHotbar[0].getKeyCode()) {
                int n = 0;
                ++n;
            }
            this.handleMouseClick(this.theSlot, this.theSlot.slotNumber, 0, 2);
            return true;
        }
        return false;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.mc.thePlayer.openContainer = this.inventorySlots;
        this.guiLeft = (GuiContainer.width - this.xSize) / 2;
        this.guiTop = (GuiContainer.height - this.ySize) / 2;
    }
    
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (n == 1 || n == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
            this.mc.thePlayer.closeScreen();
        }
        this.checkHotbarKeys(n);
        if (this.theSlot != null && this.theSlot.getHasStack()) {
            if (n == this.mc.gameSettings.keyBindPickBlock.getKeyCode()) {
                this.handleMouseClick(this.theSlot, this.theSlot.slotNumber, 0, 3);
            }
            else if (n == this.mc.gameSettings.keyBindDrop.getKeyCode()) {
                this.handleMouseClick(this.theSlot, this.theSlot.slotNumber, isCtrlKeyDown() ? 1 : 0, 4);
            }
        }
    }
    
    private void updateDragSplitting() {
        final ItemStack itemStack = this.mc.thePlayer.inventory.getItemStack();
        if (itemStack != null && this.dragSplitting) {
            this.dragSplittingRemnant = itemStack.stackSize;
            for (final Slot slot : this.dragSplittingSlots) {
                final ItemStack copy = itemStack.copy();
                final int n = (slot.getStack() == null) ? 0 : slot.getStack().stackSize;
                Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, copy, n);
                if (copy.stackSize > copy.getMaxStackSize()) {
                    copy.stackSize = copy.getMaxStackSize();
                }
                if (copy.stackSize > slot.getItemStackLimit(copy)) {
                    copy.stackSize = slot.getItemStackLimit(copy);
                }
                this.dragSplittingRemnant -= copy.stackSize - n;
            }
        }
    }
    
    @Override
    protected void mouseClickMove(final int n, final int n2, final int n3, final long n4) {
        final Slot slotAtPosition = this.getSlotAtPosition(n, n2);
        final ItemStack itemStack = this.mc.thePlayer.inventory.getItemStack();
        if (this.clickedSlot != null && this.mc.gameSettings.touchscreen) {
            if (n3 == 0 || n3 == 1) {
                if (this.draggedStack == null) {
                    if (slotAtPosition != this.clickedSlot && this.clickedSlot.getStack() != null) {
                        this.draggedStack = this.clickedSlot.getStack().copy();
                    }
                }
                else if (this.draggedStack.stackSize > 1 && slotAtPosition != null && Container.canAddItemToSlot(slotAtPosition, this.draggedStack, false)) {
                    final long systemTime = Minecraft.getSystemTime();
                    if (this.currentDragTargetSlot == slotAtPosition) {
                        if (systemTime - this.dragItemDropDelay > 500L) {
                            this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, 0);
                            this.handleMouseClick(slotAtPosition, slotAtPosition.slotNumber, 1, 0);
                            this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, 0);
                            this.dragItemDropDelay = systemTime + 750L;
                            final ItemStack draggedStack = this.draggedStack;
                            --draggedStack.stackSize;
                        }
                    }
                    else {
                        this.currentDragTargetSlot = slotAtPosition;
                        this.dragItemDropDelay = systemTime;
                    }
                }
            }
        }
        else if (this.dragSplitting && slotAtPosition != null && itemStack != null && itemStack.stackSize > this.dragSplittingSlots.size() && Container.canAddItemToSlot(slotAtPosition, itemStack, true) && slotAtPosition.isItemValid(itemStack) && this.inventorySlots.canDragIntoSlot(slotAtPosition)) {
            this.dragSplittingSlots.add(slotAtPosition);
            this.updateDragSplitting();
        }
    }
    
    protected void handleMouseClick(final Slot slot, int slotNumber, final int n, final int n2) {
        if (slot != null) {
            slotNumber = slot.slotNumber;
        }
        this.mc.playerController.windowClick(this.inventorySlots.windowId, slotNumber, n, n2, this.mc.thePlayer);
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        if (!this.mc.thePlayer.isEntityAlive() || this.mc.thePlayer.isDead) {
            this.mc.thePlayer.closeScreen();
        }
    }
    
    private void drawItemStack(final ItemStack itemStack, final int n, final int n2, final String s) {
        GlStateManager.translate(0.0f, 0.0f, 32.0f);
        this.zLevel = 200.0f;
        this.itemRender.zLevel = 200.0f;
        this.itemRender.renderItemAndEffectIntoGUI(itemStack, n, n2);
        this.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, itemStack, n, n2 - ((this.draggedStack == null) ? 0 : 8), s);
        this.zLevel = 0.0f;
        this.itemRender.zLevel = 0.0f;
    }
    
    private void drawSlot(final Slot slot) {
        final int xDisplayPosition = slot.xDisplayPosition;
        final int yDisplayPosition = slot.yDisplayPosition;
        ItemStack itemStack = slot.getStack();
        final boolean b = slot == this.clickedSlot && this.draggedStack != null && !this.isRightMouseClick;
        final ItemStack itemStack2 = this.mc.thePlayer.inventory.getItemStack();
        if (slot == this.clickedSlot && this.draggedStack != null && this.isRightMouseClick && itemStack != null) {
            final ItemStack copy;
            itemStack = (copy = itemStack.copy());
            copy.stackSize /= 2;
        }
        else if (this.dragSplitting && this.dragSplittingSlots.contains(slot) && itemStack2 != null) {
            if (this.dragSplittingSlots.size() == 1) {
                return;
            }
            if (Container.canAddItemToSlot(slot, itemStack2, true) && this.inventorySlots.canDragIntoSlot(slot)) {
                itemStack = itemStack2.copy();
                Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, itemStack, (slot.getStack() == null) ? 0 : slot.getStack().stackSize);
                if (itemStack.stackSize > itemStack.getMaxStackSize()) {
                    new StringBuilder().append(EnumChatFormatting.YELLOW).append("").append(itemStack.getMaxStackSize()).toString();
                    itemStack.stackSize = itemStack.getMaxStackSize();
                }
                if (itemStack.stackSize > slot.getItemStackLimit(itemStack)) {
                    new StringBuilder().append(EnumChatFormatting.YELLOW).append("").append(slot.getItemStackLimit(itemStack)).toString();
                    itemStack.stackSize = slot.getItemStackLimit(itemStack);
                }
            }
            else {
                this.dragSplittingSlots.remove(slot);
                this.updateDragSplitting();
            }
        }
        this.zLevel = 100.0f;
        this.itemRender.zLevel = 100.0f;
        if (itemStack == null) {
            final String slotTexture = slot.getSlotTexture();
            if (slotTexture != null) {
                final TextureAtlasSprite atlasSprite = this.mc.getTextureMapBlocks().getAtlasSprite(slotTexture);
                this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
                this.drawTexturedModalRect(xDisplayPosition, yDisplayPosition, atlasSprite, 16, 16);
            }
        }
        this.itemRender.zLevel = 0.0f;
        this.zLevel = 0.0f;
    }
    
    protected abstract void drawGuiContainerBackgroundLayer(final float p0, final int p1, final int p2);
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        final boolean b = n3 == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100;
        final Slot slotAtPosition = this.getSlotAtPosition(n, n2);
        final long systemTime = Minecraft.getSystemTime();
        this.doubleClick = (this.lastClickSlot == slotAtPosition && systemTime - this.lastClickTime < 250L && this.lastClickButton == n3);
        this.ignoreMouseUp = false;
        if (n3 == 0 || n3 == 1 || b) {
            final int guiLeft = this.guiLeft;
            final int guiTop = this.guiTop;
            final boolean b2 = n < guiLeft || n2 < guiTop || n >= guiLeft + this.xSize || n2 >= guiTop + this.ySize;
            if (slotAtPosition != null) {
                final int slotNumber = slotAtPosition.slotNumber;
            }
            if (b2) {}
            if (this.mc.gameSettings.touchscreen && b2 && this.mc.thePlayer.inventory.getItemStack() == null) {
                this.mc.displayGuiScreen(null);
                return;
            }
            if (this.mc.gameSettings.touchscreen) {
                if (slotAtPosition != null && slotAtPosition.getHasStack()) {
                    this.clickedSlot = slotAtPosition;
                    this.draggedStack = null;
                    this.isRightMouseClick = (n3 == 1);
                }
                else {
                    this.clickedSlot = null;
                }
            }
            else if (!this.dragSplitting) {
                if (this.mc.thePlayer.inventory.getItemStack() == null) {
                    if (n3 == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
                        this.handleMouseClick(slotAtPosition, -999, n3, 3);
                    }
                    else {
                        if (false) {
                            this.shiftClickedSlot = ((slotAtPosition != null && slotAtPosition.getHasStack()) ? slotAtPosition.getStack() : null);
                        }
                        this.handleMouseClick(slotAtPosition, -999, n3, 4);
                    }
                    this.ignoreMouseUp = true;
                }
                else {
                    this.dragSplitting = true;
                    this.dragSplittingButton = n3;
                    this.dragSplittingSlots.clear();
                    if (n3 == 0) {
                        this.dragSplittingLimit = 0;
                    }
                    else if (n3 == 1) {
                        this.dragSplittingLimit = 1;
                    }
                    else if (n3 == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
                        this.dragSplittingLimit = 2;
                    }
                }
            }
        }
        this.lastClickSlot = slotAtPosition;
        this.lastClickTime = systemTime;
        this.lastClickButton = n3;
    }
}
