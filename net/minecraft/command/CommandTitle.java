package net.minecraft.command;

import java.util.*;
import net.minecraft.server.*;
import net.minecraft.network.play.server.*;
import org.apache.logging.log4j.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;

public class CommandTitle extends CommandBase
{
    private static final Logger LOGGER;
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return n == 0;
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames()) : ((array.length == 2) ? CommandBase.getListOfStringsMatchingLastWord(array, S45PacketTitle.Type.getNames()) : null);
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.title.usage";
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 2) {
            throw new WrongUsageException("commands.title.usage", new Object[0]);
        }
        if (array.length < 3) {
            if ("title".equals(array[1]) || "subtitle".equals(array[1])) {
                throw new WrongUsageException("commands.title.usage.title", new Object[0]);
            }
            if ("times".equals(array[1])) {
                throw new WrongUsageException("commands.title.usage.times", new Object[0]);
            }
        }
        final EntityPlayerMP player = CommandBase.getPlayer(commandSender, array[0]);
        final S45PacketTitle.Type byName = S45PacketTitle.Type.byName(array[1]);
        if (byName != S45PacketTitle.Type.CLEAR && byName != S45PacketTitle.Type.RESET) {
            if (byName == S45PacketTitle.Type.TIMES) {
                if (array.length != 5) {
                    throw new WrongUsageException("commands.title.usage", new Object[0]);
                }
                player.playerNetServerHandler.sendPacket(new S45PacketTitle(CommandBase.parseInt(array[2]), CommandBase.parseInt(array[3]), CommandBase.parseInt(array[4])));
                CommandBase.notifyOperators(commandSender, this, "commands.title.success", new Object[0]);
            }
            else {
                if (array.length < 3) {
                    throw new WrongUsageException("commands.title.usage", new Object[0]);
                }
                player.playerNetServerHandler.sendPacket(new S45PacketTitle(byName, ChatComponentProcessor.processComponent(commandSender, IChatComponent.Serializer.jsonToComponent(CommandBase.buildString(array, 2)), player)));
                CommandBase.notifyOperators(commandSender, this, "commands.title.success", new Object[0]);
            }
        }
        else {
            if (array.length != 2) {
                throw new WrongUsageException("commands.title.usage", new Object[0]);
            }
            player.playerNetServerHandler.sendPacket(new S45PacketTitle(byName, null));
            CommandBase.notifyOperators(commandSender, this, "commands.title.success", new Object[0]);
        }
    }
    
    @Override
    public String getCommandName() {
        return "title";
    }
}
