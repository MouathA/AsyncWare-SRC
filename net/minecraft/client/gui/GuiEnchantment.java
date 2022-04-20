package net.minecraft.client.gui;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.item.*;
import net.minecraft.client.model.*;
import net.minecraft.world.*;
import net.minecraft.inventory.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.util.glu.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import java.io.*;
import com.google.common.collect.*;
import net.minecraft.enchantment.*;
import net.minecraft.util.*;
import net.minecraft.client.resources.*;
import java.util.*;

public class GuiEnchantment extends GuiContainer
{
    private final InventoryPlayer playerInventory;
    public float field_147081_y;
    public float field_147080_z;
    ItemStack field_147077_B;
    private static final ModelBook MODEL_BOOK;
    private static final ResourceLocation ENCHANTMENT_TABLE_GUI_TEXTURE;
    private ContainerEnchantment container;
    public int field_147073_u;
    public float field_147076_A;
    private static final ResourceLocation ENCHANTMENT_TABLE_BOOK_TEXTURE;
    private Random random;
    public float field_147069_w;
    public float field_147071_v;
    private final IWorldNameable field_175380_I;
    public float field_147082_x;
    
    public GuiEnchantment(final InventoryPlayer playerInventory, final World world, final IWorldNameable field_175380_I) {
        super(new ContainerEnchantment(playerInventory, world));
        this.random = new Random();
        this.playerInventory = playerInventory;
        this.container = (ContainerEnchantment)this.inventorySlots;
        this.field_175380_I = field_175380_I;
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        this.func_147068_g();
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiEnchantment.ENCHANTMENT_TABLE_GUI_TEXTURE);
        final int n4 = (GuiEnchantment.width - this.xSize) / 2;
        final int n5 = (GuiEnchantment.height - this.ySize) / 2;
        this.drawTexturedModalRect(n4, n5, 0, 0, this.xSize, this.ySize);
        GlStateManager.matrixMode(5889);
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        GlStateManager.viewport((scaledResolution.getScaledWidth() - 320) / 2 * scaledResolution.getScaleFactor(), (scaledResolution.getScaledHeight() - 240) / 2 * scaledResolution.getScaleFactor(), 320 * scaledResolution.getScaleFactor(), 240 * scaledResolution.getScaleFactor());
        GlStateManager.translate(-0.34f, 0.23f, 0.0f);
        Project.gluPerspective(90.0f, 1.3333334f, 9.0f, 80.0f);
        final float n6 = 1.0f;
        GlStateManager.matrixMode(5888);
        GlStateManager.translate(0.0f, 3.3f, -16.0f);
        GlStateManager.scale(n6, n6, n6);
        final float n7 = 5.0f;
        GlStateManager.scale(n7, n7, n7);
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiEnchantment.ENCHANTMENT_TABLE_BOOK_TEXTURE);
        GlStateManager.rotate(20.0f, 1.0f, 0.0f, 0.0f);
        final float n8 = this.field_147076_A + (this.field_147080_z - this.field_147076_A) * n;
        GlStateManager.translate((1.0f - n8) * 0.2f, (1.0f - n8) * 0.1f, (1.0f - n8) * 0.25f);
        GlStateManager.rotate(-(1.0f - n8) * 90.0f - 90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);
        final float n9 = this.field_147069_w + (this.field_147071_v - this.field_147069_w) * n + 0.25f;
        final float n10 = this.field_147069_w + (this.field_147071_v - this.field_147069_w) * n + 0.75f;
        float n11 = (n9 - MathHelper.truncateDoubleToInt(n9)) * 1.6f - 0.3f;
        float n12 = (n10 - MathHelper.truncateDoubleToInt(n10)) * 1.6f - 0.3f;
        if (n11 < 0.0f) {
            n11 = 0.0f;
        }
        if (n12 < 0.0f) {
            n12 = 0.0f;
        }
        if (n11 > 1.0f) {
            n11 = 1.0f;
        }
        if (n12 > 1.0f) {
            n12 = 1.0f;
        }
        GuiEnchantment.MODEL_BOOK.render(null, 0.0f, n11, n12, n8, 0.0f, 0.0625f);
        GlStateManager.matrixMode(5889);
        GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        GlStateManager.matrixMode(5888);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        EnchantmentNameParts.getInstance().reseedRandomGenerator(this.container.xpSeed);
        final int lapisAmount = this.container.getLapisAmount();
        while (true) {
            final int n13 = n4 + 60;
            final int n14 = n13 + 20;
            final String generateNewRandomName = EnchantmentNameParts.getInstance().generateNewRandomName();
            this.zLevel = 0.0f;
            this.mc.getTextureManager().bindTexture(GuiEnchantment.ENCHANTMENT_TABLE_GUI_TEXTURE);
            final int n15 = this.container.enchantLevels[0];
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            if (n15 == 0) {
                this.drawTexturedModalRect(n13, n5 + 14 + 0, 0, 185, 108, 19);
            }
            else {
                final String string = "" + n15;
                final FontRenderer standardGalacticFontRenderer = this.mc.standardGalacticFontRenderer;
                if ((lapisAmount < 1 || this.mc.thePlayer.experienceLevel < n15) && !this.mc.thePlayer.capabilities.isCreativeMode) {
                    this.drawTexturedModalRect(n13, n5 + 14 + 0, 0, 185, 108, 19);
                    this.drawTexturedModalRect(n13 + 1, n5 + 15 + 0, 0, 239, 16, 16);
                    standardGalacticFontRenderer.drawSplitString(generateNewRandomName, n14, n5 + 16 + 0, 86, 4226832);
                }
                else {
                    final int n16 = n2 - (n4 + 60);
                    final int n17 = n3 - (n5 + 14 + 0);
                    if (n16 >= 0 && n17 >= 0 && n16 < 108 && n17 < 19) {
                        this.drawTexturedModalRect(n13, n5 + 14 + 0, 0, 204, 108, 19);
                    }
                    else {
                        this.drawTexturedModalRect(n13, n5 + 14 + 0, 0, 166, 108, 19);
                    }
                    this.drawTexturedModalRect(n13 + 1, n5 + 15 + 0, 0, 223, 16, 16);
                    standardGalacticFontRenderer.drawSplitString(generateNewRandomName, n14, n5 + 16 + 0, 86, 8453920);
                }
                final FontRenderer fontRendererObj = this.mc.fontRendererObj;
                fontRendererObj.drawStringWithShadow(string, (float)(n14 + 86 - fontRendererObj.getStringWidth(string)), (float)(n5 + 16 + 0 + 7), 8453920);
            }
            int n18 = 0;
            ++n18;
        }
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        final int n4 = (GuiEnchantment.width - this.xSize) / 2;
        final int n5 = (GuiEnchantment.height - this.ySize) / 2;
        while (true) {
            final int n6 = n - (n4 + 60);
            final int n7 = n2 - (n5 + 14 + 0);
            if (n6 >= 0 && n7 >= 0 && n6 < 108 && n7 < 19 && this.container.enchantItem(this.mc.thePlayer, 0)) {
                this.mc.playerController.sendEnchantPacket(this.container.windowId, 0);
            }
            int n8 = 0;
            ++n8;
        }
    }
    
    public void func_147068_g() {
        final ItemStack stack = this.inventorySlots.getSlot(0).getStack();
        if (!ItemStack.areItemStacksEqual(stack, this.field_147077_B)) {
            this.field_147077_B = stack;
            do {
                this.field_147082_x += this.random.nextInt(4) - this.random.nextInt(4);
            } while (this.field_147071_v <= this.field_147082_x + 1.0f && this.field_147071_v >= this.field_147082_x - 1.0f);
        }
        ++this.field_147073_u;
        this.field_147069_w = this.field_147071_v;
        this.field_147076_A = this.field_147080_z;
        while (true) {
            if (this.container.enchantLevels[0] != 0) {}
            int n = 0;
            ++n;
        }
    }
    
    static {
        ENCHANTMENT_TABLE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/enchanting_table.png");
        ENCHANTMENT_TABLE_BOOK_TEXTURE = new ResourceLocation("textures/entity/enchanting_table_book.png");
        MODEL_BOOK = new ModelBook();
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        this.fontRendererObj.drawString(this.field_175380_I.getDisplayName().getUnformattedText(), 12.0, 5.0, 4210752);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8.0, this.ySize - 96 + 2, 4210752);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        super.drawScreen(n, n2, n3);
        final boolean isCreativeMode = this.mc.thePlayer.capabilities.isCreativeMode;
        final int lapisAmount = this.container.getLapisAmount();
        int n4;
        int n5;
        while (true) {
            n4 = this.container.enchantLevels[0];
            n5 = this.container.field_178151_h[0];
            if (this.isPointInRegion(60, 14, 108, 17, n, n2) && n4 > 0 && n5 >= 0) {
                break;
            }
            int n6 = 0;
            ++n6;
        }
        final ArrayList arrayList = Lists.newArrayList();
        if (n5 >= 0 && Enchantment.getEnchantmentById(n5 & 0xFF) != null) {
            arrayList.add(EnumChatFormatting.WHITE.toString() + EnumChatFormatting.ITALIC.toString() + I18n.format("container.enchant.clue", Enchantment.getEnchantmentById(n5 & 0xFF).getTranslatedName((n5 & 0xFF00) >> 8)));
        }
        if (!isCreativeMode) {
            if (n5 >= 0) {
                arrayList.add("");
            }
            if (this.mc.thePlayer.experienceLevel < n4) {
                arrayList.add(EnumChatFormatting.RED.toString() + "Level Requirement: " + this.container.enchantLevels[0]);
            }
            else {
                final String format = I18n.format("container.enchant.lapis.one", new Object[0]);
                if (lapisAmount >= 1) {
                    arrayList.add(EnumChatFormatting.GRAY.toString() + "" + format);
                }
                else {
                    arrayList.add(EnumChatFormatting.RED.toString() + "" + format);
                }
                arrayList.add(EnumChatFormatting.GRAY.toString() + "" + I18n.format("container.enchant.level.one", new Object[0]));
            }
        }
        this.drawHoveringText(arrayList, n, n2);
    }
}
