package net.minecraft.command;

import net.minecraft.server.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import java.util.*;

public class CommandEnchant extends CommandBase
{
    @Override
    public String getCommandName() {
        return "enchant";
    }
    
    protected String[] getListOfPlayers() {
        return MinecraftServer.getServer().getAllUsernames();
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 2) {
            throw new WrongUsageException("commands.enchant.usage", new Object[0]);
        }
        final EntityPlayerMP player = CommandBase.getPlayer(commandSender, array[0]);
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 0);
        final int int1 = CommandBase.parseInt(array[1], 0);
        final ItemStack currentEquippedItem = player.getCurrentEquippedItem();
        if (currentEquippedItem == null) {
            throw new CommandException("commands.enchant.noItem", new Object[0]);
        }
        final Enchantment enchantmentById = Enchantment.getEnchantmentById(int1);
        if (enchantmentById == null) {
            throw new NumberInvalidException("commands.enchant.notFound", new Object[] { int1 });
        }
        if (!enchantmentById.canApply(currentEquippedItem)) {
            throw new CommandException("commands.enchant.cantEnchant", new Object[0]);
        }
        if (array.length >= 3) {
            CommandBase.parseInt(array[2], enchantmentById.getMinLevel(), enchantmentById.getMaxLevel());
        }
        if (currentEquippedItem.hasTagCompound()) {
            final NBTTagList enchantmentTagList = currentEquippedItem.getEnchantmentTagList();
            if (enchantmentTagList != null) {
                while (0 < enchantmentTagList.tagCount()) {
                    final short short1 = enchantmentTagList.getCompoundTagAt(0).getShort("id");
                    if (Enchantment.getEnchantmentById(short1) != null) {
                        final Enchantment enchantmentById2 = Enchantment.getEnchantmentById(short1);
                        if (!enchantmentById2.canApplyTogether(enchantmentById)) {
                            throw new CommandException("commands.enchant.cantCombine", new Object[] { enchantmentById.getTranslatedName(1), enchantmentById2.getTranslatedName(enchantmentTagList.getCompoundTagAt(0).getShort("lvl")) });
                        }
                    }
                    int n = 0;
                    ++n;
                }
            }
        }
        currentEquippedItem.addEnchantment(enchantmentById, 1);
        CommandBase.notifyOperators(commandSender, this, "commands.enchant.success", new Object[0]);
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 1);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, this.getListOfPlayers()) : ((array.length == 2) ? CommandBase.getListOfStringsMatchingLastWord(array, Enchantment.func_181077_c()) : null);
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return n == 0;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.enchant.usage";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
}
