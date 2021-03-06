package net.minecraft.command;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.server.*;
import net.minecraft.world.storage.*;

public class CommandWeather extends CommandBase
{
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, "clear", "rain", "thunder") : null;
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length >= 1 && array.length <= 2) {
            int thunderTime = (300 + new Random().nextInt(600)) * 20;
            if (array.length >= 2) {
                thunderTime = CommandBase.parseInt(array[1], 1, 1000000) * 20;
            }
            final WorldInfo worldInfo = MinecraftServer.getServer().worldServers[0].getWorldInfo();
            if ("clear".equalsIgnoreCase(array[0])) {
                worldInfo.setCleanWeatherTime(thunderTime);
                worldInfo.setRainTime(0);
                worldInfo.setThunderTime(0);
                worldInfo.setRaining(false);
                worldInfo.setThundering(false);
                CommandBase.notifyOperators(commandSender, this, "commands.weather.clear", new Object[0]);
            }
            else if ("rain".equalsIgnoreCase(array[0])) {
                worldInfo.setCleanWeatherTime(0);
                worldInfo.setRainTime(thunderTime);
                worldInfo.setThunderTime(thunderTime);
                worldInfo.setRaining(true);
                worldInfo.setThundering(false);
                CommandBase.notifyOperators(commandSender, this, "commands.weather.rain", new Object[0]);
            }
            else {
                if (!"thunder".equalsIgnoreCase(array[0])) {
                    throw new WrongUsageException("commands.weather.usage", new Object[0]);
                }
                worldInfo.setCleanWeatherTime(0);
                worldInfo.setRainTime(thunderTime);
                worldInfo.setThunderTime(thunderTime);
                worldInfo.setRaining(true);
                worldInfo.setThundering(true);
                CommandBase.notifyOperators(commandSender, this, "commands.weather.thunder", new Object[0]);
            }
            return;
        }
        throw new WrongUsageException("commands.weather.usage", new Object[0]);
    }
    
    @Override
    public String getCommandName() {
        return "weather";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.weather.usage";
    }
}
