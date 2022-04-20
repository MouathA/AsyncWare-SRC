package net.minecraft.command.server;

import java.util.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.*;
import net.minecraft.command.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class CommandSummon extends CommandBase
{
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.summon.usage";
    }
    
    @Override
    public String getCommandName() {
        return "summon";
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, EntityList.getEntityNameList()) : ((array.length > 1 && array.length <= 4) ? CommandBase.func_175771_a(array, 1, blockPos) : null);
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 1) {
            throw new WrongUsageException("commands.summon.usage", new Object[0]);
        }
        final String s = array[0];
        BlockPos position = commandSender.getPosition();
        final Vec3 positionVector = commandSender.getPositionVector();
        double n = positionVector.xCoord;
        double n2 = positionVector.yCoord;
        double n3 = positionVector.zCoord;
        if (array.length >= 4) {
            n = CommandBase.parseDouble(n, array[1], true);
            n2 = CommandBase.parseDouble(n2, array[2], false);
            n3 = CommandBase.parseDouble(n3, array[3], true);
            position = new BlockPos(n, n2, n3);
        }
        final World entityWorld = commandSender.getEntityWorld();
        if (!entityWorld.isBlockLoaded(position)) {
            throw new CommandException("commands.summon.outOfWorld", new Object[0]);
        }
        if ("LightningBolt".equals(s)) {
            entityWorld.addWeatherEffect(new EntityLightningBolt(entityWorld, n, n2, n3));
            CommandBase.notifyOperators(commandSender, this, "commands.summon.success", new Object[0]);
        }
        else {
            NBTTagCompound tagFromJson = new NBTTagCompound();
            if (array.length >= 5) {
                tagFromJson = JsonToNBT.getTagFromJson(CommandBase.getChatComponentFromNthArg(commandSender, array, 4).getUnformattedText());
            }
            tagFromJson.setString("id", s);
            final Entity entityFromNBT = EntityList.createEntityFromNBT(tagFromJson, entityWorld);
            if (entityFromNBT == null) {
                throw new CommandException("commands.summon.failed", new Object[0]);
            }
            entityFromNBT.setLocationAndAngles(n, n2, n3, entityFromNBT.rotationYaw, entityFromNBT.rotationPitch);
            entityWorld.spawnEntityInWorld(entityFromNBT);
            Entity entity = entityFromNBT;
            Entity entityFromNBT2;
            for (NBTTagCompound compoundTag = tagFromJson; entity != null && compoundTag.hasKey("Riding", 10); entity = entityFromNBT2, compoundTag = compoundTag.getCompoundTag("Riding")) {
                entityFromNBT2 = EntityList.createEntityFromNBT(compoundTag.getCompoundTag("Riding"), entityWorld);
                if (entityFromNBT2 != null) {
                    entityFromNBT2.setLocationAndAngles(n, n2, n3, entityFromNBT2.rotationYaw, entityFromNBT2.rotationPitch);
                    entityWorld.spawnEntityInWorld(entityFromNBT2);
                    entity.mountEntity(entityFromNBT2);
                }
            }
            CommandBase.notifyOperators(commandSender, this, "commands.summon.success", new Object[0]);
        }
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
}
