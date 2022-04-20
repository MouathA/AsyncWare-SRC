package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;

public class GuiUtilRenderComponents
{
    public static String func_178909_a(final String s, final boolean b) {
        return (!b && !Minecraft.getMinecraft().gameSettings.chatColours) ? EnumChatFormatting.getTextWithoutFormattingCodes(s) : s;
    }
    
    public static List func_178908_a(final IChatComponent chatComponent, final int n, final FontRenderer fontRenderer, final boolean b, final boolean b2) {
        ChatComponentText chatComponentText = new ChatComponentText("");
        final ArrayList arrayList = Lists.newArrayList();
        final ArrayList arrayList2 = Lists.newArrayList((Iterable)chatComponent);
        while (0 < arrayList2.size()) {
            final IChatComponent chatComponent2 = arrayList2.get(0);
            String s = chatComponent2.getUnformattedTextForChat();
            if (s.contains("\n")) {
                final int index = s.indexOf(10);
                final String substring = s.substring(index + 1);
                s = s.substring(0, index + 1);
                final ChatComponentText chatComponentText2 = new ChatComponentText(substring);
                chatComponentText2.setChatStyle(chatComponent2.getChatStyle().createShallowCopy());
                arrayList2.add(1, chatComponentText2);
            }
            final String func_178909_a = func_178909_a(chatComponent2.getChatStyle().getFormattingCode() + s, b2);
            final String s2 = func_178909_a.endsWith("\n") ? func_178909_a.substring(0, func_178909_a.length() - 1) : func_178909_a;
            int n2 = fontRenderer.getStringWidth(s2);
            ChatComponentText chatComponentText3 = new ChatComponentText(s2);
            chatComponentText3.setChatStyle(chatComponent2.getChatStyle().createShallowCopy());
            if (0 + n2 > n) {
                String s3 = fontRenderer.trimStringToWidth(func_178909_a, n - 0, false);
                String substring2 = (s3.length() < func_178909_a.length()) ? func_178909_a.substring(s3.length()) : null;
                if (substring2 != null && substring2.length() > 0) {
                    int lastIndex = s3.lastIndexOf(" ");
                    if (lastIndex >= 0 && fontRenderer.getStringWidth(func_178909_a.substring(0, lastIndex)) > 0) {
                        s3 = func_178909_a.substring(0, lastIndex);
                        if (b) {
                            ++lastIndex;
                        }
                        substring2 = func_178909_a.substring(lastIndex);
                    }
                    final ChatComponentText chatComponentText4 = new ChatComponentText(substring2);
                    chatComponentText4.setChatStyle(chatComponent2.getChatStyle().createShallowCopy());
                    arrayList2.add(1, chatComponentText4);
                }
                n2 = fontRenderer.getStringWidth(s3);
                chatComponentText3 = new ChatComponentText(s3);
                chatComponentText3.setChatStyle(chatComponent2.getChatStyle().createShallowCopy());
            }
            if (0 + n2 <= n) {
                chatComponentText.appendSibling(chatComponentText3);
            }
            arrayList.add(chatComponentText);
            chatComponentText = new ChatComponentText("");
            int n3 = 0;
            ++n3;
        }
        arrayList.add(chatComponentText);
        return arrayList;
    }
}
