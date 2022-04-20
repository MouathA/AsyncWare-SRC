package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.renderer.*;
import java.awt.*;
import com.nquantum.*;
import java.util.*;
import com.google.common.collect.*;
import org.apache.logging.log4j.*;
import net.minecraft.util.*;

public class GuiNewChat extends Gui
{
    private final List field_146253_i;
    private final Minecraft mc;
    private int scrollPos;
    private final List sentMessages;
    private final List chatLines;
    private boolean isScrolled;
    private static final Logger logger;
    
    public void addToSentMessages(final String s) {
        if (this.sentMessages.isEmpty() || !this.sentMessages.get(this.sentMessages.size() - 1).equals(s)) {
            this.sentMessages.add(s);
        }
    }
    
    public boolean getChatOpen() {
        return this.mc.currentScreen instanceof GuiChat;
    }
    
    public List getSentMessages() {
        return this.sentMessages;
    }
    
    public void drawChat(final int n) {
        if (this.mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
            final int lineCount = this.getLineCount();
            final int size = this.field_146253_i.size();
            final float n2 = this.mc.gameSettings.chatOpacity * 0.9f + 0.1f;
            if (size > 0) {
                if (this.getChatOpen()) {}
                final float chatScale = this.getChatScale();
                final int ceiling_float_int = MathHelper.ceiling_float_int(this.getChatWidth() / chatScale);
                GlStateManager.translate(2.0f, 20.0f, 0.0f);
                GlStateManager.scale(chatScale, chatScale, 1.0f);
                while (0 + this.scrollPos < this.field_146253_i.size() && 0 < lineCount) {
                    final ChatLine chatLine = this.field_146253_i.get(0 + this.scrollPos);
                    if (chatLine != null) {
                        final int n3 = n - chatLine.getUpdatedCounter();
                        final double clamp_double = MathHelper.clamp_double((1.0 - 0 / 200.0) * 10.0, 0.0, 1.0);
                        final int n4 = (int)(255.0 * (clamp_double * clamp_double));
                        final int n5 = (int)(255 * n2);
                        int n6 = 0;
                        ++n6;
                        Gui.drawRect(0.0, 0.0, 0.0, 0.0, 0);
                        Gui.drawRect(0, -9, 0 + ceiling_float_int + 4, 0, new Color(0, 0, 0, 100).getRGB());
                        Asyncware.sftitle.drawStringWithShadow(chatLine.getChatComponent().getFormattedText(), 0, 0 - 9.7f, -1);
                    }
                    int font_HEIGHT = 0;
                    ++font_HEIGHT;
                }
                int font_HEIGHT = this.mc.fontRendererObj.FONT_HEIGHT;
                GlStateManager.translate(-3.0f, 0.0f, 0.0f);
                final int n7 = size * 0 + size;
                final int n8 = this.scrollPos * 0 / size;
                final int n9 = 0 / n7;
                if (n7 != 0) {
                    final int n10 = (n8 > 0) ? 170 : 96;
                    final int n11 = this.isScrolled ? 13382451 : 3355562;
                    Gui.drawRect(0.0, -n8, 2.0, -n8 - n9, new Color(0, 0, 0, 0).getRGB());
                    Gui.drawRect(2.0, -n8, 1.0, -n8 - n9, new Color(0, 0, 0, 0).getRGB());
                }
            }
        }
    }
    
    private void setChatLine(final IChatComponent chatComponent, final int n, final int n2, final boolean b) {
        if (n != 0) {
            this.deleteChatLine(n);
        }
        final List func_178908_a = GuiUtilRenderComponents.func_178908_a(chatComponent, MathHelper.floor_float(this.getChatWidth() / this.getChatScale()), this.mc.fontRendererObj, false, false);
        final boolean chatOpen = this.getChatOpen();
        for (final IChatComponent chatComponent2 : func_178908_a) {
            if (chatOpen && this.scrollPos > 0) {
                this.isScrolled = true;
                this.scroll(1);
            }
            this.field_146253_i.add(0, new ChatLine(n2, chatComponent2, n));
        }
        while (this.field_146253_i.size() > 100) {
            this.field_146253_i.remove(this.field_146253_i.size() - 1);
        }
        if (!b) {
            this.chatLines.add(0, new ChatLine(n2, chatComponent, n));
            while (this.chatLines.size() > 100) {
                this.chatLines.remove(this.chatLines.size() - 1);
            }
        }
    }
    
    public void clearChatMessages() {
        this.field_146253_i.clear();
        this.chatLines.clear();
        this.sentMessages.clear();
    }
    
    public float getChatScale() {
        return this.mc.gameSettings.chatScale;
    }
    
    public int getChatHeight() {
        return calculateChatboxHeight(this.getChatOpen() ? this.mc.gameSettings.chatHeightFocused : this.mc.gameSettings.chatHeightUnfocused);
    }
    
    public void refreshChat() {
        this.field_146253_i.clear();
        this.resetScroll();
        for (int i = this.chatLines.size() - 1; i >= 0; --i) {
            final ChatLine chatLine = this.chatLines.get(i);
            this.setChatLine(chatLine.getChatComponent(), chatLine.getChatLineID(), chatLine.getUpdatedCounter(), true);
        }
    }
    
    public static int calculateChatboxHeight(final float n) {
        return MathHelper.floor_float(n * 160 + 20);
    }
    
    public GuiNewChat(final Minecraft mc) {
        this.sentMessages = Lists.newArrayList();
        this.chatLines = Lists.newArrayList();
        this.field_146253_i = Lists.newArrayList();
        this.mc = mc;
    }
    
    public int getChatWidth() {
        return calculateChatboxWidth(this.mc.gameSettings.chatWidth);
    }
    
    public void printChatMessageWithOptionalDeletion(final IChatComponent chatComponent, final int n) {
        this.setChatLine(chatComponent, n, this.mc.ingameGUI.getUpdateCounter(), false);
        GuiNewChat.logger.info("[CHAT] " + chatComponent.getUnformattedText());
    }
    
    public void deleteChatLine(final int n) {
        final Iterator<ChatLine> iterator = (Iterator<ChatLine>)this.field_146253_i.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getChatLineID() == n) {
                iterator.remove();
            }
        }
        final Iterator<ChatLine> iterator2 = (Iterator<ChatLine>)this.chatLines.iterator();
        while (iterator2.hasNext()) {
            if (iterator2.next().getChatLineID() == n) {
                iterator2.remove();
                break;
            }
        }
    }
    
    public void printChatMessage(final IChatComponent chatComponent) {
        this.printChatMessageWithOptionalDeletion(chatComponent, 0);
    }
    
    public void scroll(final int n) {
        this.scrollPos += n;
        final int size = this.field_146253_i.size();
        if (this.scrollPos > size - this.getLineCount()) {
            this.scrollPos = size - this.getLineCount();
        }
        if (this.scrollPos <= 0) {
            this.scrollPos = 0;
            this.isScrolled = false;
        }
    }
    
    public static int calculateChatboxWidth(final float n) {
        return MathHelper.floor_float(n * 280 + 40);
    }
    
    public void resetScroll() {
        this.scrollPos = 0;
        this.isScrolled = false;
    }
    
    public int getLineCount() {
        return this.getChatHeight() / 9;
    }
    
    static {
        logger = LogManager.getLogger();
    }
    
    public IChatComponent getChatComponent(final int n, final int n2) {
        if (!this.getChatOpen()) {
            return null;
        }
        final int scaleFactor = new ScaledResolution(this.mc).getScaleFactor();
        final float chatScale = this.getChatScale();
        final int n3 = n / scaleFactor - 3;
        final int n4 = n2 / scaleFactor - 27;
        final int floor_float = MathHelper.floor_float(n3 / chatScale);
        final int floor_float2 = MathHelper.floor_float(n4 / chatScale);
        if (floor_float < 0 || floor_float2 < 0) {
            return null;
        }
        final int min = Math.min(this.getLineCount(), this.field_146253_i.size());
        if (floor_float <= MathHelper.floor_float(this.getChatWidth() / this.getChatScale()) && floor_float2 < this.mc.fontRendererObj.FONT_HEIGHT * min + min) {
            final int n5 = floor_float2 / this.mc.fontRendererObj.FONT_HEIGHT + this.scrollPos;
            if (n5 >= 0 && n5 < this.field_146253_i.size()) {
                for (final IChatComponent chatComponent : this.field_146253_i.get(n5).getChatComponent()) {
                    if (chatComponent instanceof ChatComponentText) {
                        final int n6 = 0 + this.mc.fontRendererObj.getStringWidth(GuiUtilRenderComponents.func_178909_a(((ChatComponentText)chatComponent).getChatComponentText_TextValue(), false));
                        if (0 > floor_float) {
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
}
