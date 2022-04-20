package net.minecraft.client.gui;

import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.resources.*;
import net.minecraft.item.*;
import com.google.common.collect.*;
import java.io.*;
import net.minecraft.util.*;
import org.lwjgl.input.*;
import java.util.*;
import net.minecraft.init.*;
import io.netty.buffer.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.*;

public class GuiScreenBook extends GuiScreen
{
    private NextPageButton buttonPreviousPage;
    private List field_175386_A;
    private static final ResourceLocation bookGuiTextures;
    private NextPageButton buttonNextPage;
    private int currPage;
    private int field_175387_B;
    private boolean bookIsModified;
    private int bookImageWidth;
    private int bookTotalPages;
    private GuiButton buttonSign;
    private static final Logger logger;
    private GuiButton buttonFinalize;
    private final ItemStack bookObj;
    private NBTTagList bookPages;
    private String bookTitle;
    private boolean bookGettingSigned;
    private int bookImageHeight;
    private final EntityPlayer editingPlayer;
    private GuiButton buttonDone;
    private GuiButton buttonCancel;
    private final boolean bookIsUnsigned;
    private int updateCount;
    
    public GuiScreenBook(final EntityPlayer editingPlayer, final ItemStack bookObj, final boolean bookIsUnsigned) {
        this.bookImageWidth = 192;
        this.bookImageHeight = 192;
        this.bookTotalPages = 1;
        this.bookTitle = "";
        this.field_175387_B = -1;
        this.editingPlayer = editingPlayer;
        this.bookObj = bookObj;
        this.bookIsUnsigned = bookIsUnsigned;
        if (bookObj.hasTagCompound()) {
            this.bookPages = bookObj.getTagCompound().getTagList("pages", 8);
            if (this.bookPages != null) {
                this.bookPages = (NBTTagList)this.bookPages.copy();
                this.bookTotalPages = this.bookPages.tagCount();
                if (this.bookTotalPages < 1) {
                    this.bookTotalPages = 1;
                }
            }
        }
        if (this.bookPages == null && bookIsUnsigned) {
            (this.bookPages = new NBTTagList()).appendTag(new NBTTagString(""));
            this.bookTotalPages = 1;
        }
    }
    
    static {
        logger = LogManager.getLogger();
        bookGuiTextures = new ResourceLocation("textures/gui/book.png");
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiScreenBook.bookGuiTextures);
        final int n4 = (GuiScreenBook.width - this.bookImageWidth) / 2;
        this.drawTexturedModalRect(n4, 2, 0, 0, this.bookImageWidth, this.bookImageHeight);
        if (this.bookGettingSigned) {
            String s = this.bookTitle;
            if (this.bookIsUnsigned) {
                if (this.updateCount / 6 % 2 == 0) {
                    s = s + "" + EnumChatFormatting.BLACK + "_";
                }
                else {
                    s = s + "" + EnumChatFormatting.GRAY + "_";
                }
            }
            final String format = I18n.format("book.editTitle", new Object[0]);
            this.fontRendererObj.drawString(format, n4 + 36 + (116 - this.fontRendererObj.getStringWidth(format)) / 2, 34, 0);
            this.fontRendererObj.drawString(s, n4 + 36 + (116 - this.fontRendererObj.getStringWidth(s)) / 2, 50, 0);
            final String format2 = I18n.format("book.byAuthor", this.editingPlayer.getName());
            this.fontRendererObj.drawString(EnumChatFormatting.DARK_GRAY + format2, n4 + 36 + (116 - this.fontRendererObj.getStringWidth(format2)) / 2, 60, 0);
            this.fontRendererObj.drawSplitString(I18n.format("book.finalizeWarning", new Object[0]), n4 + 36, 82, 116, 0);
        }
        else {
            final String format3 = I18n.format("book.pageIndicator", this.currPage + 1, this.bookTotalPages);
            String s2 = "";
            if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount()) {
                s2 = this.bookPages.getStringTagAt(this.currPage);
            }
            if (this.bookIsUnsigned) {
                if (this.fontRendererObj.getBidiFlag()) {
                    s2 += "_";
                }
                else if (this.updateCount / 6 % 2 == 0) {
                    s2 = s2 + "" + EnumChatFormatting.BLACK + "_";
                }
                else {
                    s2 = s2 + "" + EnumChatFormatting.GRAY + "_";
                }
            }
            else if (this.field_175387_B != this.currPage) {
                if (ItemEditableBook.validBookTagContents(this.bookObj.getTagCompound())) {
                    final IChatComponent jsonToComponent = IChatComponent.Serializer.jsonToComponent(s2);
                    this.field_175386_A = ((jsonToComponent != null) ? GuiUtilRenderComponents.func_178908_a(jsonToComponent, 116, this.fontRendererObj, true, true) : null);
                }
                else {
                    this.field_175386_A = Lists.newArrayList((Iterable)new ChatComponentText(EnumChatFormatting.DARK_RED.toString() + "* Invalid book tag *"));
                }
                this.field_175387_B = this.currPage;
            }
            this.fontRendererObj.drawString(format3, n4 - this.fontRendererObj.getStringWidth(format3) + this.bookImageWidth - 44, 18, 0);
            if (this.field_175386_A == null) {
                this.fontRendererObj.drawSplitString(s2, n4 + 36, 34, 116, 0);
            }
            else {
                while (0 < Math.min(128 / this.fontRendererObj.FONT_HEIGHT, this.field_175386_A.size())) {
                    this.fontRendererObj.drawString(this.field_175386_A.get(0).getUnformattedText(), n4 + 36, 34 + 0 * this.fontRendererObj.FONT_HEIGHT, 0);
                    int n5 = 0;
                    ++n5;
                }
                final IChatComponent func_175385_b = this.func_175385_b(n, n2);
                if (func_175385_b != null) {
                    this.handleComponentHover(func_175385_b, n, n2);
                }
            }
        }
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        ++this.updateCount;
    }
    
    @Override
    protected void mouseClicked(final int p0, final int p1, final int p2) throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifne            19
        //     4: aload_0        
        //     5: iload_1        
        //     6: iload_2        
        //     7: invokevirtual   net/minecraft/client/gui/GuiScreenBook.func_175385_b:(II)Lnet/minecraft/util/IChatComponent;
        //    10: astore          4
        //    12: aload_0        
        //    13: aload           4
        //    15: ifnonnull       19
        //    18: return         
        //    19: aload_0        
        //    20: iload_1        
        //    21: iload_2        
        //    22: iload_3        
        //    23: invokespecial   net/minecraft/client/gui/GuiScreen.mouseClicked:(III)V
        //    26: return         
        //    Exceptions:
        //  throws java.io.IOException
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0019 (coming from #0015).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void updateButtons() {
        this.buttonNextPage.visible = (!this.bookGettingSigned && (this.currPage < this.bookTotalPages - 1 || this.bookIsUnsigned));
        this.buttonPreviousPage.visible = (!this.bookGettingSigned && this.currPage > 0);
        this.buttonDone.visible = (!this.bookIsUnsigned || !this.bookGettingSigned);
        if (this.bookIsUnsigned) {
            this.buttonSign.visible = !this.bookGettingSigned;
            this.buttonCancel.visible = this.bookGettingSigned;
            this.buttonFinalize.visible = this.bookGettingSigned;
            this.buttonFinalize.enabled = (this.bookTitle.trim().length() > 0);
        }
    }
    
    private void addNewPage() {
        if (this.bookPages != null && this.bookPages.tagCount() < 50) {
            this.bookPages.appendTag(new NBTTagString(""));
            ++this.bookTotalPages;
            this.bookIsModified = true;
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == 0) {
                this.mc.displayGuiScreen(null);
                this.sendBookToServer(false);
            }
            else if (guiButton.id == 3 && this.bookIsUnsigned) {
                this.bookGettingSigned = true;
            }
            else if (guiButton.id == 1) {
                if (this.currPage < this.bookTotalPages - 1) {
                    ++this.currPage;
                }
                else if (this.bookIsUnsigned) {
                    this.addNewPage();
                    if (this.currPage < this.bookTotalPages - 1) {
                        ++this.currPage;
                    }
                }
            }
            else if (guiButton.id == 2) {
                if (this.currPage > 0) {
                    --this.currPage;
                }
            }
            else if (guiButton.id == 5 && this.bookGettingSigned) {
                this.sendBookToServer(true);
                this.mc.displayGuiScreen(null);
            }
            else if (guiButton.id == 4 && this.bookGettingSigned) {
                this.bookGettingSigned = false;
            }
            this.updateButtons();
        }
    }
    
    static ResourceLocation access$000() {
        return GuiScreenBook.bookGuiTextures;
    }
    
    private void keyTypedInTitle(final char c, final int n) throws IOException {
        switch (n) {
            case 14: {
                if (!this.bookTitle.isEmpty()) {
                    this.bookTitle = this.bookTitle.substring(0, this.bookTitle.length() - 1);
                    this.updateButtons();
                }
            }
            case 28:
            case 156: {
                if (!this.bookTitle.isEmpty()) {
                    this.sendBookToServer(true);
                    this.mc.displayGuiScreen(null);
                }
            }
            default: {
                if (this.bookTitle.length() < 16 && ChatAllowedCharacters.isAllowedCharacter(c)) {
                    this.bookTitle += Character.toString(c);
                    this.updateButtons();
                    this.bookIsModified = true;
                }
            }
        }
    }
    
    private void pageSetCurrent(final String s) {
        if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount()) {
            this.bookPages.set(this.currPage, new NBTTagString(s));
            this.bookIsModified = true;
        }
    }
    
    private void pageInsertIntoCurrent(final String s) {
        final String string = this.pageGetCurrent() + s;
        if (this.fontRendererObj.splitStringWidth(string + "" + EnumChatFormatting.BLACK + "_", 118) <= 128 && string.length() < 256) {
            this.pageSetCurrent(string);
        }
    }
    
    private String pageGetCurrent() {
        return (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount()) ? this.bookPages.getStringTagAt(this.currPage) : "";
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        if (this.bookIsUnsigned) {
            this.buttonList.add(this.buttonSign = new GuiButton(3, GuiScreenBook.width / 2 - 100, 4 + this.bookImageHeight, 98, 20, I18n.format("book.signButton", new Object[0])));
            this.buttonList.add(this.buttonDone = new GuiButton(0, GuiScreenBook.width / 2 + 2, 4 + this.bookImageHeight, 98, 20, I18n.format("gui.done", new Object[0])));
            this.buttonList.add(this.buttonFinalize = new GuiButton(5, GuiScreenBook.width / 2 - 100, 4 + this.bookImageHeight, 98, 20, I18n.format("book.finalizeButton", new Object[0])));
            this.buttonList.add(this.buttonCancel = new GuiButton(4, GuiScreenBook.width / 2 + 2, 4 + this.bookImageHeight, 98, 20, I18n.format("gui.cancel", new Object[0])));
        }
        else {
            this.buttonList.add(this.buttonDone = new GuiButton(0, GuiScreenBook.width / 2 - 100, 4 + this.bookImageHeight, 200, 20, I18n.format("gui.done", new Object[0])));
        }
        final int n = (GuiScreenBook.width - this.bookImageWidth) / 2;
        this.buttonList.add(this.buttonNextPage = new NextPageButton(1, n + 120, 156, true));
        this.buttonList.add(this.buttonPreviousPage = new NextPageButton(2, n + 38, 156, false));
        this.updateButtons();
    }
    
    private void keyTypedInBook(final char c, final int n) {
        if (GuiScreen.isKeyComboCtrlV(n)) {
            this.pageInsertIntoCurrent(GuiScreen.getClipboardString());
        }
        else {
            switch (n) {
                case 14: {
                    final String pageGetCurrent = this.pageGetCurrent();
                    if (pageGetCurrent.length() > 0) {
                        this.pageSetCurrent(pageGetCurrent.substring(0, pageGetCurrent.length() - 1));
                    }
                }
                case 28:
                case 156: {
                    this.pageInsertIntoCurrent("\n");
                }
                default: {
                    if (ChatAllowedCharacters.isAllowedCharacter(c)) {
                        this.pageInsertIntoCurrent(Character.toString(c));
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    public IChatComponent func_175385_b(final int n, final int n2) {
        if (this.field_175386_A == null) {
            return null;
        }
        final int n3 = n - (GuiScreenBook.width - this.bookImageWidth) / 2 - 36;
        final int n4 = n2 - 2 - 16 - 16;
        if (n3 < 0 || n4 < 0) {
            return null;
        }
        final int min = Math.min(128 / this.fontRendererObj.FONT_HEIGHT, this.field_175386_A.size());
        if (n3 <= 116 && n4 < this.mc.fontRendererObj.FONT_HEIGHT * min + min) {
            final int n5 = n4 / this.mc.fontRendererObj.FONT_HEIGHT;
            if (n5 >= 0 && n5 < this.field_175386_A.size()) {
                for (final IChatComponent chatComponent : this.field_175386_A.get(n5)) {
                    if (chatComponent instanceof ChatComponentText) {
                        final int n6 = 0 + this.mc.fontRendererObj.getStringWidth(((ChatComponentText)chatComponent).getChatComponentText_TextValue());
                        if (0 > n3) {
                            return chatComponent;
                        }
                        continue;
                    }
                }
            }
            return null;
        }
        return null;
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        super.keyTyped(c, n);
        if (this.bookIsUnsigned) {
            if (this.bookGettingSigned) {
                this.keyTypedInTitle(c, n);
            }
            else {
                this.keyTypedInBook(c, n);
            }
        }
    }
    
    private void sendBookToServer(final boolean b) throws IOException {
        if (this.bookIsUnsigned && this.bookIsModified && this.bookPages != null) {
            while (this.bookPages.tagCount() > 1 && this.bookPages.getStringTagAt(this.bookPages.tagCount() - 1).length() == 0) {
                this.bookPages.removeTag(this.bookPages.tagCount() - 1);
            }
            if (this.bookObj.hasTagCompound()) {
                this.bookObj.getTagCompound().setTag("pages", this.bookPages);
            }
            else {
                this.bookObj.setTagInfo("pages", this.bookPages);
            }
            String s = "MC|BEdit";
            if (b) {
                s = "MC|BSign";
                this.bookObj.setTagInfo("author", new NBTTagString(this.editingPlayer.getName()));
                this.bookObj.setTagInfo("title", new NBTTagString(this.bookTitle.trim()));
                while (0 < this.bookPages.tagCount()) {
                    this.bookPages.set(0, new NBTTagString(IChatComponent.Serializer.componentToJson(new ChatComponentText(this.bookPages.getStringTagAt(0)))));
                    int n = 0;
                    ++n;
                }
                this.bookObj.setItem(Items.written_book);
            }
            final PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
            packetBuffer.writeItemStackToBuffer(this.bookObj);
            this.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload(s, packetBuffer));
        }
    }
    
    static class NextPageButton extends GuiButton
    {
        private final boolean field_146151_o;
        
        public NextPageButton(final int n, final int n2, final int n3, final boolean field_146151_o) {
            super(n, n2, n3, 23, 13, "");
            this.field_146151_o = field_146151_o;
        }
        
        @Override
        public void drawButton(final Minecraft minecraft, final int n, final int n2) {
            if (this.visible) {
                final boolean b = n >= this.xPosition && n2 >= this.yPosition && n < this.xPosition + this.width && n2 < this.yPosition + this.height;
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                minecraft.getTextureManager().bindTexture(GuiScreenBook.access$000());
                if (b) {
                    final int n3;
                    n3 += 23;
                }
                if (!this.field_146151_o) {
                    final int n4;
                    n4 += 13;
                }
                this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 192, 23, 13);
            }
        }
    }
}
