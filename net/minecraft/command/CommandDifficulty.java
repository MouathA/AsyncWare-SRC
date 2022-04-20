package net.minecraft.command;

import java.util.*;
import net.minecraft.world.*;
import net.minecraft.server.*;
import net.minecraft.util.*;

public class CommandDifficulty extends CommandBase
{
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.difficulty.usage";
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, "peaceful", "easy", "normal", "hard") : null;
    }
    
    @Override
    public String getCommandName() {
        return "difficulty";
    }
    
    protected EnumDifficulty getDifficultyFromCommand(final String s) throws CommandException, NumberInvalidException {
        return (!s.equalsIgnoreCase("peaceful") && !s.equalsIgnoreCase("p")) ? ((!s.equalsIgnoreCase("easy") && !s.equalsIgnoreCase("e")) ? ((!s.equalsIgnoreCase("normal") && !s.equalsIgnoreCase("n")) ? ((!s.equalsIgnoreCase("hard") && !s.equalsIgnoreCase("h")) ? EnumDifficulty.getDifficultyEnum(CommandBase.parseInt(s, 0, 3)) : EnumDifficulty.HARD) : EnumDifficulty.NORMAL) : EnumDifficulty.EASY) : EnumDifficulty.PEACEFUL;
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length <= 0) {
            throw new WrongUsageException("commands.difficulty.usage", new Object[0]);
        }
        final EnumDifficulty difficultyFromCommand = this.getDifficultyFromCommand(array[0]);
        MinecraftServer.getServer().setDifficultyForAllWorlds(difficultyFromCommand);
        CommandBase.notifyOperators(commandSender, this, "commands.difficulty.success", new ChatComponentTranslation(difficultyFromCommand.getDifficultyResourceKey(), new Object[0]));
    }
}
