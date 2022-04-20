package net.minecraft.command;

import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.server.*;

public class CommandPlaySound extends CommandBase
{
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.playsound.usage";
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return n == 1;
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 2) {
            throw new WrongUsageException(this.getCommandUsage(commandSender), new Object[0]);
        }
        final int n = 0;
        int n2 = 0;
        ++n2;
        final String s = array[n];
        final int n3 = 0;
        ++n2;
        final EntityPlayerMP player = CommandBase.getPlayer(commandSender, array[n3]);
        final Vec3 positionVector = commandSender.getPositionVector();
        double n4 = positionVector.xCoord;
        if (array.length > 0) {
            final double n5 = n4;
            final int n6 = 0;
            ++n2;
            n4 = CommandBase.parseDouble(n5, array[n6], true);
        }
        double n7 = positionVector.yCoord;
        if (array.length > 0) {
            final double n8 = n7;
            final int n9 = 0;
            ++n2;
            n7 = CommandBase.parseDouble(n8, array[n9], 0, 0, false);
        }
        double n10 = positionVector.zCoord;
        if (array.length > 0) {
            final double n11 = n10;
            final int n12 = 0;
            ++n2;
            n10 = CommandBase.parseDouble(n11, array[n12], true);
        }
        double double1 = 1.0;
        if (array.length > 0) {
            final int n13 = 0;
            ++n2;
            double1 = CommandBase.parseDouble(array[n13], 0.0, 3.4028234663852886E38);
        }
        double double2 = 1.0;
        if (array.length > 0) {
            final int n14 = 0;
            ++n2;
            double2 = CommandBase.parseDouble(array[n14], 0.0, 2.0);
        }
        double double3 = 0.0;
        if (array.length > 0) {
            double3 = CommandBase.parseDouble(array[0], 0.0, 1.0);
        }
        if (player.getDistance(n4, n7, n10) > ((double1 > 1.0) ? (double1 * 16.0) : 16.0)) {
            if (double3 <= 0.0) {
                throw new CommandException("commands.playsound.playerTooFar", new Object[] { player.getName() });
            }
            final double n15 = n4 - player.posX;
            final double n16 = n7 - player.posY;
            final double n17 = n10 - player.posZ;
            final double sqrt = Math.sqrt(n15 * n15 + n16 * n16 + n17 * n17);
            if (sqrt > 0.0) {
                n4 = player.posX + n15 / sqrt * 2.0;
                n7 = player.posY + n16 / sqrt * 2.0;
                n10 = player.posZ + n17 / sqrt * 2.0;
            }
            double1 = double3;
        }
        player.playerNetServerHandler.sendPacket(new S29PacketSoundEffect(s, n4, n7, n10, (float)double1, (float)double2));
        CommandBase.notifyOperators(commandSender, this, "commands.playsound.success", s, player.getName());
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 2) ? CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames()) : ((array.length > 2 && array.length <= 5) ? CommandBase.func_175771_a(array, 2, blockPos) : null);
    }
    
    @Override
    public String getCommandName() {
        return "playsound";
    }
}
