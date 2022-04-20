package net.minecraft.command;

import java.util.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class CommandParticle extends CommandBase
{
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, EnumParticleTypes.getParticleNames()) : ((array.length > 1 && array.length <= 4) ? CommandBase.func_175771_a(array, 1, blockPos) : ((array.length == 10) ? CommandBase.getListOfStringsMatchingLastWord(array, "normal", "force") : null));
    }
    
    @Override
    public String getCommandName() {
        return "particle";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 8) {
            throw new WrongUsageException("commands.particle.usage", new Object[0]);
        }
        EnumParticleTypes enumParticleTypes = null;
        final EnumParticleTypes[] values = EnumParticleTypes.values();
        while (true) {
            while (0 < values.length) {
                final EnumParticleTypes enumParticleTypes2 = values[0];
                if (enumParticleTypes2.hasArguments()) {
                    if (array[0].startsWith(enumParticleTypes2.getParticleName())) {
                        enumParticleTypes = enumParticleTypes2;
                        break;
                    }
                }
                else if (array[0].equals(enumParticleTypes2.getParticleName())) {
                    enumParticleTypes = enumParticleTypes2;
                    break;
                }
                int n = 0;
                ++n;
                continue;
                final String s = array[0];
                final Vec3 positionVector = commandSender.getPositionVector();
                final double n2 = (float)CommandBase.parseDouble(positionVector.xCoord, array[1], true);
                final double n3 = (float)CommandBase.parseDouble(positionVector.yCoord, array[2], true);
                final double n4 = (float)CommandBase.parseDouble(positionVector.zCoord, array[3], true);
                final double n5 = (float)CommandBase.parseDouble(array[4]);
                final double n6 = (float)CommandBase.parseDouble(array[5]);
                final double n7 = (float)CommandBase.parseDouble(array[6]);
                final double n8 = (float)CommandBase.parseDouble(array[7]);
                if (array.length > 8) {
                    CommandBase.parseInt(array[8], 0);
                }
                if (array.length <= 9 || "force".equals(array[9])) {}
                final World entityWorld = commandSender.getEntityWorld();
                if (entityWorld instanceof WorldServer) {
                    final WorldServer worldServer = (WorldServer)entityWorld;
                    final int[] array2 = new int[enumParticleTypes.getArgumentCount()];
                    if (enumParticleTypes.hasArguments()) {
                        final String[] split = array[0].split("_", 3);
                        while (1 < split.length) {
                            array2[0] = Integer.parseInt(split[1]);
                            int n9 = 0;
                            ++n9;
                        }
                    }
                    worldServer.spawnParticle(enumParticleTypes, true, n2, n3, n4, 0, n5, n6, n7, n8, array2);
                    CommandBase.notifyOperators(commandSender, this, "commands.particle.success", s, Math.max(0, 1));
                }
                return;
            }
            continue;
        }
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.particle.usage";
    }
}
