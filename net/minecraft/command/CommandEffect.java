package net.minecraft.command;

import net.minecraft.server.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;

public class CommandEffect extends CommandBase
{
    @Override
    public String getCommandName() {
        return "effect";
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return n == 0;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.effect.usage";
    }
    
    protected String[] getAllUsernames() {
        return MinecraftServer.getServer().getAllUsernames();
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, this.getAllUsernames()) : ((array.length == 2) ? CommandBase.getListOfStringsMatchingLastWord(array, Potion.func_181168_c()) : ((array.length == 5) ? CommandBase.getListOfStringsMatchingLastWord(array, "true", "false") : null));
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 2) {
            throw new WrongUsageException("commands.effect.usage", new Object[0]);
        }
        final EntityLivingBase entityLivingBase = (EntityLivingBase)CommandBase.getEntity(commandSender, array[0], EntityLivingBase.class);
        if (array[1].equals("clear")) {
            if (entityLivingBase.getActivePotionEffects().isEmpty()) {
                throw new CommandException("commands.effect.failure.notActive.all", new Object[] { entityLivingBase.getName() });
            }
            entityLivingBase.clearActivePotions();
            CommandBase.notifyOperators(commandSender, this, "commands.effect.success.removed.all", entityLivingBase.getName());
        }
        else {
            final int int1 = CommandBase.parseInt(array[1], 1);
            if (int1 < 0 || int1 >= Potion.potionTypes.length || Potion.potionTypes[int1] == null) {
                throw new NumberInvalidException("commands.effect.notFound", new Object[] { int1 });
            }
            final Potion potion = Potion.potionTypes[int1];
            if (array.length >= 3) {
                CommandBase.parseInt(array[2], 0, 1000000);
                if (potion.isInstant()) {}
            }
            else if (potion.isInstant()) {}
            if (array.length >= 4) {
                CommandBase.parseInt(array[3], 0, 255);
            }
            if (array.length < 5 || "true".equalsIgnoreCase(array[4])) {}
            final PotionEffect potionEffect = new PotionEffect(int1, 1, 0, false, false);
            entityLivingBase.addPotionEffect(potionEffect);
            CommandBase.notifyOperators(commandSender, this, "commands.effect.success", new ChatComponentTranslation(potionEffect.getEffectName(), new Object[0]), int1, 0, entityLivingBase.getName(), 30);
        }
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
}
