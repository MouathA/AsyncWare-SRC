package net.minecraft.command;

import net.minecraft.server.*;
import net.minecraft.util.*;
import net.minecraft.event.*;
import net.minecraft.entity.player.*;
import java.util.*;

public class CommandHelp extends CommandBase
{
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        if (array.length == 1) {
            final Set keySet = this.getCommands().keySet();
            return CommandBase.getListOfStringsMatchingLastWord(array, (String[])keySet.toArray(new String[keySet.size()]));
        }
        return null;
    }
    
    protected List getSortedPossibleCommands(final ICommandSender commandSender) {
        final List possibleCommands = MinecraftServer.getServer().getCommandManager().getPossibleCommands(commandSender);
        Collections.sort((List<Comparable>)possibleCommands);
        return possibleCommands;
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        final List sortedPossibleCommands = this.getSortedPossibleCommands(commandSender);
        final int n = (sortedPossibleCommands.size() - 1) / 7;
        final int n2 = (array.length == 0) ? 0 : (CommandBase.parseInt(array[0], 1, n + 1) - 1);
        final int min = Math.min(7, sortedPossibleCommands.size());
        final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.help.header", new Object[] { 1, n + 1 });
        chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
        commandSender.addChatMessage(chatComponentTranslation);
        while (0 < min) {
            final ICommand command = sortedPossibleCommands.get(0);
            final ChatComponentTranslation chatComponentTranslation2 = new ChatComponentTranslation(command.getCommandUsage(commandSender), new Object[0]);
            chatComponentTranslation2.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + command.getCommandName() + " "));
            commandSender.addChatMessage(chatComponentTranslation2);
            int n3 = 0;
            ++n3;
        }
        if (commandSender instanceof EntityPlayer) {
            final ChatComponentTranslation chatComponentTranslation3 = new ChatComponentTranslation("commands.help.footer", new Object[0]);
            chatComponentTranslation3.getChatStyle().setColor(EnumChatFormatting.GREEN);
            commandSender.addChatMessage(chatComponentTranslation3);
        }
    }
    
    @Override
    public List getCommandAliases() {
        return Arrays.asList("?");
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.help.usage";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    @Override
    public String getCommandName() {
        return "help";
    }
    
    protected Map getCommands() {
        return MinecraftServer.getServer().getCommandManager().getCommands();
    }
}
