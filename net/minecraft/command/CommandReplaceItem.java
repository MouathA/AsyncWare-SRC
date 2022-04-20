package net.minecraft.command;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.inventory.*;
import net.minecraft.tileentity.*;
import net.minecraft.server.*;
import com.google.common.collect.*;

public class CommandReplaceItem extends CommandBase
{
    private static final Map SHORTCUTS;
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, "entity", "block") : ((array.length == 2 && array[0].equals("entity")) ? CommandBase.getListOfStringsMatchingLastWord(array, this.getUsernames()) : ((array.length >= 2 && array.length <= 4 && array[0].equals("block")) ? CommandBase.func_175771_a(array, 1, blockPos) : (((array.length != 3 || !array[0].equals("entity")) && (array.length != 5 || !array[0].equals("block"))) ? (((array.length != 4 || !array[0].equals("entity")) && (array.length != 6 || !array[0].equals("block"))) ? null : CommandBase.getListOfStringsMatchingLastWord(array, Item.itemRegistry.getKeys())) : CommandBase.getListOfStringsMatchingLastWord(array, CommandReplaceItem.SHORTCUTS.keySet()))));
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 1) {
            throw new WrongUsageException("commands.replaceitem.usage", new Object[0]);
        }
        if (!array[0].equals("entity")) {
            if (!array[0].equals("block")) {
                throw new WrongUsageException("commands.replaceitem.usage", new Object[0]);
            }
        }
        if (array.length < 6) {
            throw new WrongUsageException("commands.replaceitem.block.usage", new Object[0]);
        }
        final int n = 2;
        int n2 = 0;
        ++n2;
        final int slotForShortcut = this.getSlotForShortcut(array[n]);
        final Item itemByText = CommandBase.getItemByText(commandSender, array[2]);
        ++n2;
        int int1;
        if (array.length > 2) {
            final int n3 = 2;
            ++n2;
            int1 = CommandBase.parseInt(array[n3], 1, 64);
        }
        else {
            int1 = 1;
        }
        final int n4 = int1;
        int int2;
        if (array.length > 2) {
            final int n5 = 2;
            ++n2;
            int2 = CommandBase.parseInt(array[n5]);
        }
        else {
            int2 = 0;
        }
        ItemStack itemStack = new ItemStack(itemByText, n4, int2);
        if (array.length > 2) {
            itemStack.setTagCompound(JsonToNBT.getTagFromJson(CommandBase.getChatComponentFromNthArg(commandSender, array, 2).getUnformattedText()));
        }
        if (itemStack.getItem() == null) {
            itemStack = null;
        }
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 0);
        final BlockPos blockPos = CommandBase.parseBlockPos(commandSender, array, 1, false);
        final TileEntity tileEntity = commandSender.getEntityWorld().getTileEntity(blockPos);
        if (tileEntity == null || !(tileEntity instanceof IInventory)) {
            throw new CommandException("commands.replaceitem.noContainer", new Object[] { blockPos.getX(), blockPos.getY(), blockPos.getZ() });
        }
        final IInventory inventory = (IInventory)tileEntity;
        if (slotForShortcut >= 0 && slotForShortcut < inventory.getSizeInventory()) {
            inventory.setInventorySlotContents(slotForShortcut, itemStack);
        }
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, n4);
        CommandBase.notifyOperators(commandSender, this, "commands.replaceitem.success", slotForShortcut, n4, (itemStack == null) ? "Air" : itemStack.getChatComponent());
    }
    
    protected String[] getUsernames() {
        return MinecraftServer.getServer().getAllUsernames();
    }
    
    static {
        SHORTCUTS = Maps.newHashMap();
        while (true) {
            CommandReplaceItem.SHORTCUTS.put("slot.container." + 0, 0);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.replaceitem.usage";
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return array.length > 0 && array[0].equals("entity") && n == 1;
    }
    
    @Override
    public String getCommandName() {
        return "replaceitem";
    }
    
    private int getSlotForShortcut(final String s) throws CommandException {
        if (!CommandReplaceItem.SHORTCUTS.containsKey(s)) {
            throw new CommandException("commands.generic.parameter.invalid", new Object[] { s });
        }
        return CommandReplaceItem.SHORTCUTS.get(s);
    }
}
