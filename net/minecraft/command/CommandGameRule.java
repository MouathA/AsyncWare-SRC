package net.minecraft.command;

import net.minecraft.world.*;
import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import java.util.*;
import net.minecraft.util.*;

public class CommandGameRule extends CommandBase
{
    public static void func_175773_a(final GameRules gameRules, final String s) {
        if ("reducedDebugInfo".equals(s)) {
            final byte b = (byte)(gameRules.getBoolean(s) ? 22 : 23);
            for (final EntityPlayerMP entityPlayerMP : MinecraftServer.getServer().getConfigurationManager().func_181057_v()) {
                entityPlayerMP.playerNetServerHandler.sendPacket(new S19PacketEntityStatus(entityPlayerMP, b));
            }
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        if (array.length == 1) {
            return CommandBase.getListOfStringsMatchingLastWord(array, this.getGameRules().getRules());
        }
        if (array.length == 2 && this.getGameRules().areSameType(array[0], GameRules.ValueType.BOOLEAN_VALUE)) {
            return CommandBase.getListOfStringsMatchingLastWord(array, "true", "false");
        }
        return null;
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    private GameRules getGameRules() {
        return MinecraftServer.getServer().worldServerForDimension(0).getGameRules();
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        final GameRules gameRules = this.getGameRules();
        final String s = (array.length > 0) ? array[0] : "";
        final String s2 = (array.length > 1) ? CommandBase.buildString(array, 1) : "";
        switch (array.length) {
            case 0: {
                commandSender.addChatMessage(new ChatComponentText(CommandBase.joinNiceString(gameRules.getRules())));
                break;
            }
            case 1: {
                if (!gameRules.hasRule(s)) {
                    throw new CommandException("commands.gamerule.norule", new Object[] { s });
                }
                commandSender.addChatMessage(new ChatComponentText(s).appendText(" = ").appendText(gameRules.getString(s)));
                commandSender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, gameRules.getInt(s));
                break;
            }
            default: {
                if (gameRules.areSameType(s, GameRules.ValueType.BOOLEAN_VALUE) && !"true".equals(s2) && !"false".equals(s2)) {
                    throw new CommandException("commands.generic.boolean.invalid", new Object[] { s2 });
                }
                gameRules.setOrCreateGameRule(s, s2);
                func_175773_a(gameRules, s);
                CommandBase.notifyOperators(commandSender, this, "commands.gamerule.success", new Object[0]);
                break;
            }
        }
    }
    
    @Override
    public String getCommandName() {
        return "gamerule";
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.gamerule.usage";
    }
}
