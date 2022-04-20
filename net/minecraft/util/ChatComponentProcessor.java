package net.minecraft.util;

import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.command.*;

public class ChatComponentProcessor
{
    public static IChatComponent processComponent(final ICommandSender commandSender, final IChatComponent chatComponent, final Entity entity) throws CommandException {
        IChatComponent matchEntitiesToChatComponent;
        if (chatComponent instanceof ChatComponentScore) {
            final ChatComponentScore chatComponentScore = (ChatComponentScore)chatComponent;
            String s = chatComponentScore.getName();
            if (PlayerSelector.hasArguments(s)) {
                final List matchEntities = PlayerSelector.matchEntities(commandSender, s, Entity.class);
                if (matchEntities.size() != 1) {
                    throw new EntityNotFoundException();
                }
                s = matchEntities.get(0).getName();
            }
            matchEntitiesToChatComponent = ((entity != null && s.equals("*")) ? new ChatComponentScore(entity.getName(), chatComponentScore.getObjective()) : new ChatComponentScore(s, chatComponentScore.getObjective()));
            ((ChatComponentScore)matchEntitiesToChatComponent).setValue(chatComponentScore.getUnformattedTextForChat());
        }
        else if (chatComponent instanceof ChatComponentSelector) {
            matchEntitiesToChatComponent = PlayerSelector.matchEntitiesToChatComponent(commandSender, ((ChatComponentSelector)chatComponent).getSelector());
            if (matchEntitiesToChatComponent == null) {
                matchEntitiesToChatComponent = new ChatComponentText("");
            }
        }
        else if (chatComponent instanceof ChatComponentText) {
            matchEntitiesToChatComponent = new ChatComponentText(((ChatComponentText)chatComponent).getChatComponentText_TextValue());
        }
        else {
            if (!(chatComponent instanceof ChatComponentTranslation)) {
                return chatComponent;
            }
            final Object[] formatArgs = ((ChatComponentTranslation)chatComponent).getFormatArgs();
            while (0 < formatArgs.length) {
                final Object o = formatArgs[0];
                if (o instanceof IChatComponent) {
                    formatArgs[0] = processComponent(commandSender, (IChatComponent)o, entity);
                }
                int n = 0;
                ++n;
            }
            matchEntitiesToChatComponent = new ChatComponentTranslation(((ChatComponentTranslation)chatComponent).getKey(), formatArgs);
        }
        final ChatStyle chatStyle = chatComponent.getChatStyle();
        if (chatStyle != null) {
            matchEntitiesToChatComponent.setChatStyle(chatStyle.createShallowCopy());
        }
        final Iterator<IChatComponent> iterator = chatComponent.getSiblings().iterator();
        while (iterator.hasNext()) {
            matchEntitiesToChatComponent.appendSibling(processComponent(commandSender, iterator.next(), entity));
        }
        return matchEntitiesToChatComponent;
    }
}
