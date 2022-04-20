package net.minecraft.client.gui.stream;

import net.minecraft.client.stream.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import java.io.*;
import net.minecraft.client.resources.*;
import tv.twitch.chat.*;
import java.util.*;

public class GuiTwitchUserMode extends GuiScreen
{
    private final ChatUserInfo field_152337_h;
    private static final EnumChatFormatting field_152331_a;
    private static final EnumChatFormatting field_152336_g;
    private final IChatComponent field_152338_i;
    private final IStream stream;
    private int field_152334_t;
    private static final EnumChatFormatting field_152335_f;
    private final List field_152332_r;
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.field_152338_i.getUnformattedText(), GuiTwitchUserMode.width / 2, 70, 16777215);
        final Iterator<IChatComponent> iterator = this.field_152332_r.iterator();
        while (iterator.hasNext()) {
            this.drawString(this.fontRendererObj, iterator.next().getFormattedText(), this.field_152334_t, 80, 16777215);
            final int n4 = 80 + this.fontRendererObj.FONT_HEIGHT;
        }
        super.drawScreen(n, n2, n3);
    }
    
    public static IChatComponent func_152329_a(final ChatUserMode chatUserMode, final String s, final boolean b) {
        IChatComponent chatComponent = null;
        if (chatUserMode == ChatUserMode.TTV_CHAT_USERMODE_ADMINSTRATOR) {
            chatComponent = new ChatComponentTranslation("stream.user.mode.administrator", new Object[0]);
            chatComponent.getChatStyle().setColor(GuiTwitchUserMode.field_152336_g);
        }
        else if (chatUserMode == ChatUserMode.TTV_CHAT_USERMODE_BANNED) {
            if (s == null) {
                chatComponent = new ChatComponentTranslation("stream.user.mode.banned", new Object[0]);
            }
            else if (b) {
                chatComponent = new ChatComponentTranslation("stream.user.mode.banned.self", new Object[0]);
            }
            else {
                chatComponent = new ChatComponentTranslation("stream.user.mode.banned.other", new Object[] { s });
            }
            chatComponent.getChatStyle().setColor(GuiTwitchUserMode.field_152335_f);
        }
        else if (chatUserMode == ChatUserMode.TTV_CHAT_USERMODE_BROADCASTER) {
            if (s == null) {
                chatComponent = new ChatComponentTranslation("stream.user.mode.broadcaster", new Object[0]);
            }
            else if (b) {
                chatComponent = new ChatComponentTranslation("stream.user.mode.broadcaster.self", new Object[0]);
            }
            else {
                chatComponent = new ChatComponentTranslation("stream.user.mode.broadcaster.other", new Object[0]);
            }
            chatComponent.getChatStyle().setColor(GuiTwitchUserMode.field_152331_a);
        }
        else if (chatUserMode == ChatUserMode.TTV_CHAT_USERMODE_MODERATOR) {
            if (s == null) {
                chatComponent = new ChatComponentTranslation("stream.user.mode.moderator", new Object[0]);
            }
            else if (b) {
                chatComponent = new ChatComponentTranslation("stream.user.mode.moderator.self", new Object[0]);
            }
            else {
                chatComponent = new ChatComponentTranslation("stream.user.mode.moderator.other", new Object[] { s });
            }
            chatComponent.getChatStyle().setColor(GuiTwitchUserMode.field_152331_a);
        }
        else if (chatUserMode == ChatUserMode.TTV_CHAT_USERMODE_STAFF) {
            chatComponent = new ChatComponentTranslation("stream.user.mode.staff", new Object[0]);
            chatComponent.getChatStyle().setColor(GuiTwitchUserMode.field_152336_g);
        }
        return chatComponent;
    }
    
    public GuiTwitchUserMode(final IStream stream, final ChatUserInfo field_152337_h) {
        this.field_152332_r = Lists.newArrayList();
        this.stream = stream;
        this.field_152337_h = field_152337_h;
        this.field_152338_i = new ChatComponentText(field_152337_h.displayName);
        this.field_152332_r.addAll(func_152328_a(field_152337_h.modes, field_152337_h.subscriptions, stream));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == 0) {
                this.stream.func_152917_b("/ban " + this.field_152337_h.displayName);
            }
            else if (guiButton.id == 3) {
                this.stream.func_152917_b("/unban " + this.field_152337_h.displayName);
            }
            else if (guiButton.id == 2) {
                this.stream.func_152917_b("/mod " + this.field_152337_h.displayName);
            }
            else if (guiButton.id == 4) {
                this.stream.func_152917_b("/unmod " + this.field_152337_h.displayName);
            }
            else if (guiButton.id == 1) {
                this.stream.func_152917_b("/timeout " + this.field_152337_h.displayName);
            }
            this.mc.displayGuiScreen(null);
        }
    }
    
    @Override
    public void initGui() {
        final int n = GuiTwitchUserMode.width / 3;
        final int n2 = n - 130;
        this.buttonList.add(new GuiButton(1, n * 0 + n2 / 2, GuiTwitchUserMode.height - 70, 130, 20, I18n.format("stream.userinfo.timeout", new Object[0])));
        this.buttonList.add(new GuiButton(0, n * 1 + n2 / 2, GuiTwitchUserMode.height - 70, 130, 20, I18n.format("stream.userinfo.ban", new Object[0])));
        this.buttonList.add(new GuiButton(2, n * 2 + n2 / 2, GuiTwitchUserMode.height - 70, 130, 20, I18n.format("stream.userinfo.mod", new Object[0])));
        this.buttonList.add(new GuiButton(5, n * 0 + n2 / 2, GuiTwitchUserMode.height - 45, 130, 20, I18n.format("gui.cancel", new Object[0])));
        this.buttonList.add(new GuiButton(3, n * 1 + n2 / 2, GuiTwitchUserMode.height - 45, 130, 20, I18n.format("stream.userinfo.unban", new Object[0])));
        this.buttonList.add(new GuiButton(4, n * 2 + n2 / 2, GuiTwitchUserMode.height - 45, 130, 20, I18n.format("stream.userinfo.unmod", new Object[0])));
        final Iterator<IChatComponent> iterator = this.field_152332_r.iterator();
        while (iterator.hasNext()) {
            Math.max(0, this.fontRendererObj.getStringWidth(iterator.next().getFormattedText()));
        }
        this.field_152334_t = GuiTwitchUserMode.width / 2 - 0;
    }
    
    public static IChatComponent func_152330_a(final ChatUserSubscription chatUserSubscription, final String s, final boolean b) {
        IChatComponent chatComponent = null;
        if (chatUserSubscription == ChatUserSubscription.TTV_CHAT_USERSUB_SUBSCRIBER) {
            if (s == null) {
                chatComponent = new ChatComponentTranslation("stream.user.subscription.subscriber", new Object[0]);
            }
            else if (b) {
                chatComponent = new ChatComponentTranslation("stream.user.subscription.subscriber.self", new Object[0]);
            }
            else {
                chatComponent = new ChatComponentTranslation("stream.user.subscription.subscriber.other", new Object[] { s });
            }
            chatComponent.getChatStyle().setColor(GuiTwitchUserMode.field_152331_a);
        }
        else if (chatUserSubscription == ChatUserSubscription.TTV_CHAT_USERSUB_TURBO) {
            chatComponent = new ChatComponentTranslation("stream.user.subscription.turbo", new Object[0]);
            chatComponent.getChatStyle().setColor(GuiTwitchUserMode.field_152336_g);
        }
        return chatComponent;
    }
    
    static {
        field_152331_a = EnumChatFormatting.DARK_GREEN;
        field_152335_f = EnumChatFormatting.RED;
        field_152336_g = EnumChatFormatting.DARK_PURPLE;
    }
    
    public static List func_152328_a(final Set set, final Set set2, final IStream stream) {
        final String s = (stream == null) ? null : stream.func_152921_C();
        final boolean b = stream != null && stream.func_152927_B();
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<ChatUserMode> iterator = set.iterator();
        while (iterator.hasNext()) {
            final IChatComponent func_152329_a = func_152329_a(iterator.next(), s, b);
            if (func_152329_a != null) {
                final ChatComponentText chatComponentText = new ChatComponentText("- ");
                chatComponentText.appendSibling(func_152329_a);
                arrayList.add(chatComponentText);
            }
        }
        final Iterator<ChatUserSubscription> iterator2 = set2.iterator();
        while (iterator2.hasNext()) {
            final IChatComponent func_152330_a = func_152330_a(iterator2.next(), s, b);
            if (func_152330_a != null) {
                final ChatComponentText chatComponentText2 = new ChatComponentText("- ");
                chatComponentText2.appendSibling(func_152330_a);
                arrayList.add(chatComponentText2);
            }
        }
        return arrayList;
    }
}
