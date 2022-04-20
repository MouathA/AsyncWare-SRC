package net.minecraft.command;

import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import java.util.*;

public class CommandClearInventory extends CommandBase
{
    protected String[] func_147209_d() {
        return MinecraftServer.getServer().getAllUsernames();
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        final EntityPlayerMP entityPlayerMP = (array.length == 0) ? CommandBase.getCommandSenderAsPlayer(commandSender) : CommandBase.getPlayer(commandSender, array[0]);
        final Item item = (array.length >= 2) ? CommandBase.getItemByText(commandSender, array[1]) : null;
        final int n = (array.length >= 3) ? CommandBase.parseInt(array[2], -1) : -1;
        final int n2 = (array.length >= 4) ? CommandBase.parseInt(array[3], -1) : -1;
        NBTTagCompound tagFromJson = null;
        if (array.length >= 5) {
            tagFromJson = JsonToNBT.getTagFromJson(CommandBase.buildString(array, 4));
        }
        if (array.length >= 2 && item == null) {
            throw new CommandException("commands.clear.failure", new Object[] { entityPlayerMP.getName() });
        }
        final int clearMatchingItems = entityPlayerMP.inventory.clearMatchingItems(item, n, n2, tagFromJson);
        entityPlayerMP.inventoryContainer.detectAndSendChanges();
        if (!entityPlayerMP.capabilities.isCreativeMode) {
            entityPlayerMP.updateHeldItem();
        }
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, clearMatchingItems);
        if (clearMatchingItems == 0) {
            throw new CommandException("commands.clear.failure", new Object[] { entityPlayerMP.getName() });
        }
        if (n2 == 0) {
            commandSender.addChatMessage(new ChatComponentTranslation("commands.clear.testing", new Object[] { entityPlayerMP.getName(), clearMatchingItems }));
        }
        else {
            CommandBase.notifyOperators(commandSender, this, "commands.clear.success", entityPlayerMP.getName(), clearMatchingItems);
        }
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.clear.usage";
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return n == 0;
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, this.func_147209_d()) : ((array.length == 2) ? CommandBase.getListOfStringsMatchingLastWord(array, Item.itemRegistry.getKeys()) : null);
    }
    
    @Override
    public String getCommandName() {
        return "clear";
    }
}
