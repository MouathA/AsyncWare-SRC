package net.minecraft.client.gui;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.resources.*;
import net.minecraft.village.*;
import net.minecraft.item.*;
import org.apache.logging.log4j.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.inventory.*;
import io.netty.buffer.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import java.io.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;

public class GuiMerchant extends GuiContainer
{
    private MerchantButton nextButton;
    private IMerchant merchant;
    private MerchantButton previousButton;
    private static final ResourceLocation MERCHANT_GUI_TEXTURE;
    private static final Logger logger;
    private int selectedMerchantRecipe;
    private IChatComponent chatComponent;
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        super.drawScreen(n, n2, n3);
        final MerchantRecipeList recipes = this.merchant.getRecipes(this.mc.thePlayer);
        if (recipes != null && !recipes.isEmpty()) {
            final int n4 = (GuiMerchant.width - this.xSize) / 2;
            final int n5 = (GuiMerchant.height - this.ySize) / 2;
            final MerchantRecipe merchantRecipe = recipes.get(this.selectedMerchantRecipe);
            final ItemStack itemToBuy = merchantRecipe.getItemToBuy();
            final ItemStack secondItemToBuy = merchantRecipe.getSecondItemToBuy();
            final ItemStack itemToSell = merchantRecipe.getItemToSell();
            this.itemRender.zLevel = 100.0f;
            this.itemRender.renderItemAndEffectIntoGUI(itemToBuy, n4 + 36, n5 + 24);
            this.itemRender.renderItemOverlays(this.fontRendererObj, itemToBuy, n4 + 36, n5 + 24);
            if (secondItemToBuy != null) {
                this.itemRender.renderItemAndEffectIntoGUI(secondItemToBuy, n4 + 62, n5 + 24);
                this.itemRender.renderItemOverlays(this.fontRendererObj, secondItemToBuy, n4 + 62, n5 + 24);
            }
            this.itemRender.renderItemAndEffectIntoGUI(itemToSell, n4 + 120, n5 + 24);
            this.itemRender.renderItemOverlays(this.fontRendererObj, itemToSell, n4 + 120, n5 + 24);
            this.itemRender.zLevel = 0.0f;
            if (this.isPointInRegion(36, 24, 16, 16, n, n2) && itemToBuy != null) {
                this.renderToolTip(itemToBuy, n, n2);
            }
            else if (secondItemToBuy != null && this.isPointInRegion(62, 24, 16, 16, n, n2) && secondItemToBuy != null) {
                this.renderToolTip(secondItemToBuy, n, n2);
            }
            else if (itemToSell != null && this.isPointInRegion(120, 24, 16, 16, n, n2) && itemToSell != null) {
                this.renderToolTip(itemToSell, n, n2);
            }
            else if (merchantRecipe.isRecipeDisabled() && (this.isPointInRegion(83, 21, 28, 21, n, n2) || this.isPointInRegion(83, 51, 28, 21, n, n2))) {
                this.drawCreativeTabHoveringText(I18n.format("merchant.deprecated", new Object[0]), n, n2);
            }
        }
    }
    
    public IMerchant getMerchant() {
        return this.merchant;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        final int n = (GuiMerchant.width - this.xSize) / 2;
        final int n2 = (GuiMerchant.height - this.ySize) / 2;
        this.buttonList.add(this.nextButton = new MerchantButton(1, n + 120 + 27, n2 + 24 - 1, true));
        this.buttonList.add(this.previousButton = new MerchantButton(2, n + 36 - 19, n2 + 24 - 1, false));
        this.nextButton.enabled = false;
        this.previousButton.enabled = false;
    }
    
    static {
        logger = LogManager.getLogger();
        MERCHANT_GUI_TEXTURE = new ResourceLocation("textures/gui/container/villager.png");
    }
    
    public GuiMerchant(final InventoryPlayer inventoryPlayer, final IMerchant merchant, final World world) {
        super(new ContainerMerchant(inventoryPlayer, merchant, world));
        this.merchant = merchant;
        this.chatComponent = merchant.getDisplayName();
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        final String unformattedText = this.chatComponent.getUnformattedText();
        this.fontRendererObj.drawString(unformattedText, this.xSize / 2 - this.fontRendererObj.getStringWidth(unformattedText) / 2, 6.0, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8.0, this.ySize - 96 + 2, 4210752);
    }
    
    static ResourceLocation access$000() {
        return GuiMerchant.MERCHANT_GUI_TEXTURE;
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        final MerchantRecipeList recipes = this.merchant.getRecipes(this.mc.thePlayer);
        if (recipes != null) {
            this.nextButton.enabled = (this.selectedMerchantRecipe < recipes.size() - 1);
            this.previousButton.enabled = (this.selectedMerchantRecipe > 0);
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton == this.nextButton) {
            ++this.selectedMerchantRecipe;
            final MerchantRecipeList recipes = this.merchant.getRecipes(this.mc.thePlayer);
            if (recipes != null && this.selectedMerchantRecipe >= recipes.size()) {
                this.selectedMerchantRecipe = recipes.size() - 1;
            }
        }
        else if (guiButton == this.previousButton) {
            --this.selectedMerchantRecipe;
            if (this.selectedMerchantRecipe < 0) {
                this.selectedMerchantRecipe = 0;
            }
        }
        ((ContainerMerchant)this.inventorySlots).setCurrentRecipeIndex(this.selectedMerchantRecipe);
        final PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
        packetBuffer.writeInt(this.selectedMerchantRecipe);
        this.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload("MC|TrSel", packetBuffer));
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiMerchant.MERCHANT_GUI_TEXTURE);
        this.drawTexturedModalRect((GuiMerchant.width - this.xSize) / 2, (GuiMerchant.height - this.ySize) / 2, 0, 0, this.xSize, this.ySize);
        final MerchantRecipeList recipes = this.merchant.getRecipes(this.mc.thePlayer);
        if (recipes != null && !recipes.isEmpty()) {
            final int selectedMerchantRecipe = this.selectedMerchantRecipe;
            if (selectedMerchantRecipe < 0 || selectedMerchantRecipe >= recipes.size()) {
                return;
            }
            if (recipes.get(selectedMerchantRecipe).isRecipeDisabled()) {
                this.mc.getTextureManager().bindTexture(GuiMerchant.MERCHANT_GUI_TEXTURE);
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                this.drawTexturedModalRect(this.guiLeft + 83, this.guiTop + 21, 212, 0, 28, 21);
                this.drawTexturedModalRect(this.guiLeft + 83, this.guiTop + 51, 212, 0, 28, 21);
            }
        }
    }
    
    static class MerchantButton extends GuiButton
    {
        private final boolean field_146157_o;
        
        public MerchantButton(final int n, final int n2, final int n3, final boolean field_146157_o) {
            super(n, n2, n3, 12, 19, "");
            this.field_146157_o = field_146157_o;
        }
        
        @Override
        public void drawButton(final Minecraft minecraft, final int n, final int n2) {
            if (this.visible) {
                minecraft.getTextureManager().bindTexture(GuiMerchant.access$000());
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                final boolean b = n >= this.xPosition && n2 >= this.yPosition && n < this.xPosition + this.width && n2 < this.yPosition + this.height;
                if (!this.enabled) {
                    final int n3 = 176 + this.width * 2;
                }
                else if (b) {
                    final int n4 = 176 + this.width;
                }
                if (!this.field_146157_o) {
                    final int n5 = 0 + this.height;
                }
                this.drawTexturedModalRect(this.xPosition, this.yPosition, 176, 0, this.width, this.height);
            }
        }
    }
}
