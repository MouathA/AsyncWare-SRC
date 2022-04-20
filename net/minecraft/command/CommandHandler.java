package net.minecraft.command;

import net.minecraft.entity.*;
import org.apache.logging.log4j.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;

public class CommandHandler implements ICommandManager
{
    private final Map commandMap;
    private final Set commandSet;
    private static final Logger logger;
    
    private static String[] dropFirstString(final String[] array) {
        final String[] array2 = new String[array.length - 1];
        System.arraycopy(array, 1, array2, 0, array.length - 1);
        return array2;
    }
    
    public CommandHandler() {
        this.commandMap = Maps.newHashMap();
        this.commandSet = Sets.newHashSet();
    }
    
    @Override
    public int executeCommand(final ICommandSender commandSender, String s) {
        s = s.trim();
        if (s.startsWith("/")) {
            s = s.substring(1);
        }
        final String[] split = s.split(" ");
        final String s2 = split[0];
        final String[] dropFirstString = dropFirstString(split);
        final ICommand command = this.commandMap.get(s2);
        final int usernameIndex = this.getUsernameIndex(command, dropFirstString);
        if (command == null) {
            final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.generic.notFound", new Object[0]);
            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
            commandSender.addChatMessage(chatComponentTranslation);
        }
        else if (command.canCommandSenderUseCommand(commandSender)) {
            if (usernameIndex > -1) {
                final List matchEntities = PlayerSelector.matchEntities(commandSender, dropFirstString[usernameIndex], Entity.class);
                final String s3 = dropFirstString[usernameIndex];
                commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, matchEntities.size());
                final Iterator<Entity> iterator = matchEntities.iterator();
                while (iterator.hasNext()) {
                    dropFirstString[usernameIndex] = iterator.next().getUniqueID().toString();
                    if (this.tryExecute(commandSender, dropFirstString, command, s)) {
                        int n = 0;
                        ++n;
                    }
                }
                dropFirstString[usernameIndex] = s3;
            }
            else {
                commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, 1);
                if (this.tryExecute(commandSender, dropFirstString, command, s)) {
                    int n = 0;
                    ++n;
                }
            }
        }
        else {
            final ChatComponentTranslation chatComponentTranslation2 = new ChatComponentTranslation("commands.generic.permission", new Object[0]);
            chatComponentTranslation2.getChatStyle().setColor(EnumChatFormatting.RED);
            commandSender.addChatMessage(chatComponentTranslation2);
        }
        commandSender.setCommandStat(CommandResultStats.Type.SUCCESS_COUNT, 0);
        return 0;
    }
    
    protected boolean tryExecute(final ICommandSender commandSender, final String[] array, final ICommand command, final String s) {
        command.processCommand(commandSender, array);
        return true;
    }
    
    private int getUsernameIndex(final ICommand command, final String[] array) {
        if (command == null) {
            return -1;
        }
        while (0 < array.length) {
            if (command.isUsernameIndex(array, 0) && PlayerSelector.matchesMultiplePlayers(array[0])) {
                return 0;
            }
            int n = 0;
            ++n;
        }
        return -1;
    }
    
    static {
        logger = LogManager.getLogger();
    }
    
    @Override
    public List getTabCompletionOptions(final ICommandSender commandSender, final String s, final BlockPos blockPos) {
        final String[] split = s.split(" ", -1);
        final String s2 = split[0];
        if (split.length == 1) {
            final ArrayList arrayList = Lists.newArrayList();
            for (final Map.Entry<String, V> entry : this.commandMap.entrySet()) {
                if (CommandBase.doesStringStartWith(s2, entry.getKey()) && ((ICommand)entry.getValue()).canCommandSenderUseCommand(commandSender)) {
                    arrayList.add(entry.getKey());
                }
            }
            return arrayList;
        }
        if (split.length > 1) {
            final ICommand command = this.commandMap.get(s2);
            if (command != null && command.canCommandSenderUseCommand(commandSender)) {
                return command.addTabCompletionOptions(commandSender, dropFirstString(split), blockPos);
            }
        }
        return null;
    }
    
    @Override
    public Map getCommands() {
        return this.commandMap;
    }
    
    public ICommand registerCommand(final ICommand command) {
        this.commandMap.put(command.getCommandName(), command);
        this.commandSet.add(command);
        for (final String s : command.getCommandAliases()) {
            final ICommand command2 = this.commandMap.get(s);
            if (command2 == null || !command2.getCommandName().equals(s)) {
                this.commandMap.put(s, command);
            }
        }
        return command;
    }
    
    @Override
    public List getPossibleCommands(final ICommandSender commandSender) {
        final ArrayList arrayList = Lists.newArrayList();
        for (final ICommand command : this.commandSet) {
            if (command.canCommandSenderUseCommand(commandSender)) {
                arrayList.add(command);
            }
        }
        return arrayList;
    }
}
