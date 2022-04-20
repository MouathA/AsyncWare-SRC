package net.minecraft.command.server;

import net.minecraft.entity.player.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.server.*;

public class CommandTeleport extends CommandBase
{
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 1) {
            throw new WrongUsageException("commands.tp.usage", new Object[0]);
        }
        Entity entity;
        if (array.length != 2 && array.length != 4 && array.length != 6) {
            entity = CommandBase.getCommandSenderAsPlayer(commandSender);
        }
        else {
            entity = CommandBase.func_175768_b(commandSender, array[0]);
        }
        if (array.length != 1 && array.length != 2) {
            if (array.length < 4) {
                throw new WrongUsageException("commands.tp.usage", new Object[0]);
            }
            if (entity.worldObj != null) {
                final CoordinateArg coordinate = CommandBase.parseCoordinate(entity.posX, array[1], true);
                final double posY = entity.posY;
                final int n = 2;
                int n2 = 0;
                ++n2;
                final CoordinateArg coordinate2 = CommandBase.parseCoordinate(posY, array[n], 0, 0, false);
                final double posZ = entity.posZ;
                final int n3 = 2;
                ++n2;
                final CoordinateArg coordinate3 = CommandBase.parseCoordinate(posZ, array[n3], true);
                final double n4 = entity.rotationYaw;
                String s;
                if (array.length > 2) {
                    final int n5 = 2;
                    ++n2;
                    s = array[n5];
                }
                else {
                    s = "~";
                }
                final CoordinateArg coordinate4 = CommandBase.parseCoordinate(n4, s, false);
                final CoordinateArg coordinate5 = CommandBase.parseCoordinate(entity.rotationPitch, (array.length > 2) ? array[2] : "~", false);
                if (entity instanceof EntityPlayerMP) {
                    final EnumSet<S08PacketPlayerPosLook.EnumFlags> none = EnumSet.noneOf(S08PacketPlayerPosLook.EnumFlags.class);
                    if (coordinate.func_179630_c()) {
                        none.add(S08PacketPlayerPosLook.EnumFlags.X);
                    }
                    if (coordinate2.func_179630_c()) {
                        none.add(S08PacketPlayerPosLook.EnumFlags.Y);
                    }
                    if (coordinate3.func_179630_c()) {
                        none.add(S08PacketPlayerPosLook.EnumFlags.Z);
                    }
                    if (coordinate5.func_179630_c()) {
                        none.add(S08PacketPlayerPosLook.EnumFlags.X_ROT);
                    }
                    if (coordinate4.func_179630_c()) {
                        none.add(S08PacketPlayerPosLook.EnumFlags.Y_ROT);
                    }
                    float rotationYawHead = (float)coordinate4.func_179629_b();
                    if (!coordinate4.func_179630_c()) {
                        rotationYawHead = MathHelper.wrapAngleTo180_float(rotationYawHead);
                    }
                    float n6 = (float)coordinate5.func_179629_b();
                    if (!coordinate5.func_179630_c()) {
                        n6 = MathHelper.wrapAngleTo180_float(n6);
                    }
                    if (n6 > 90.0f || n6 < -90.0f) {
                        n6 = MathHelper.wrapAngleTo180_float(180.0f - n6);
                        rotationYawHead = MathHelper.wrapAngleTo180_float(rotationYawHead + 180.0f);
                    }
                    entity.mountEntity(null);
                    ((EntityPlayerMP)entity).playerNetServerHandler.setPlayerLocation(coordinate.func_179629_b(), coordinate2.func_179629_b(), coordinate3.func_179629_b(), rotationYawHead, n6, none);
                    entity.setRotationYawHead(rotationYawHead);
                }
                else {
                    float wrapAngleTo180_float = (float)MathHelper.wrapAngleTo180_double(coordinate4.func_179628_a());
                    float wrapAngleTo180_float2 = (float)MathHelper.wrapAngleTo180_double(coordinate5.func_179628_a());
                    if (wrapAngleTo180_float2 > 90.0f || wrapAngleTo180_float2 < -90.0f) {
                        wrapAngleTo180_float2 = MathHelper.wrapAngleTo180_float(180.0f - wrapAngleTo180_float2);
                        wrapAngleTo180_float = MathHelper.wrapAngleTo180_float(wrapAngleTo180_float + 180.0f);
                    }
                    entity.setLocationAndAngles(coordinate.func_179628_a(), coordinate2.func_179628_a(), coordinate3.func_179628_a(), wrapAngleTo180_float, wrapAngleTo180_float2);
                    entity.setRotationYawHead(wrapAngleTo180_float);
                }
                CommandBase.notifyOperators(commandSender, this, "commands.tp.success.coordinates", entity.getName(), coordinate.func_179628_a(), coordinate2.func_179628_a(), coordinate3.func_179628_a());
            }
        }
        else {
            final Entity func_175768_b = CommandBase.func_175768_b(commandSender, array[array.length - 1]);
            if (func_175768_b.worldObj != entity.worldObj) {
                throw new CommandException("commands.tp.notSameDimension", new Object[0]);
            }
            entity.mountEntity(null);
            if (entity instanceof EntityPlayerMP) {
                ((EntityPlayerMP)entity).playerNetServerHandler.setPlayerLocation(func_175768_b.posX, func_175768_b.posY, func_175768_b.posZ, func_175768_b.rotationYaw, func_175768_b.rotationPitch);
            }
            else {
                entity.setLocationAndAngles(func_175768_b.posX, func_175768_b.posY, func_175768_b.posZ, func_175768_b.rotationYaw, func_175768_b.rotationPitch);
            }
            CommandBase.notifyOperators(commandSender, this, "commands.tp.success", entity.getName(), func_175768_b.getName());
        }
    }
    
    @Override
    public String getCommandName() {
        return "tp";
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return n == 0;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.tp.usage";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length != 1 && array.length != 2) ? null : CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
    }
}
