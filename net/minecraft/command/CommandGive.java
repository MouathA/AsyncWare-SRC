package net.minecraft.command;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.item.*;
import net.minecraft.server.*;

public class CommandGive extends CommandBase
{
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return n == 0;
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandName() {
        return "give";
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, this.getPlayers()) : ((array.length == 2) ? CommandBase.getListOfStringsMatchingLastWord(array, Item.itemRegistry.getKeys()) : null);
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.give.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 2) {
            throw new WrongUsageException("commands.give.usage", new Object[0]);
        }
        final EntityPlayerMP player = CommandBase.getPlayer(commandSender, array[0]);
        final Item itemByText = CommandBase.getItemByText(commandSender, array[1]);
        final int n = (array.length >= 3) ? CommandBase.parseInt(array[2], 1, 64) : 1;
        final ItemStack itemStack = new ItemStack(itemByText, n, (array.length >= 4) ? CommandBase.parseInt(array[3]) : 0);
        if (array.length >= 5) {
            itemStack.setTagCompound(JsonToNBT.getTagFromJson(CommandBase.getChatComponentFromNthArg(commandSender, array, 4).getUnformattedText()));
        }
        final boolean addItemStackToInventory = player.inventory.addItemStackToInventory(itemStack);
        if (addItemStackToInventory) {
            player.worldObj.playSoundAtEntity(player, "random.pop", 0.2f, ((player.getRNG().nextFloat() - player.getRNG().nextFloat()) * 0.7f + 1.0f) * 2.0f);
            player.inventoryContainer.detectAndSendChanges();
        }
        if (addItemStackToInventory && itemStack.stackSize <= 0) {
            itemStack.stackSize = 1;
            commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, n);
            final EntityItem dropPlayerItemWithRandomChoice = player.dropPlayerItemWithRandomChoice(itemStack, false);
            if (dropPlayerItemWithRandomChoice != null) {
                dropPlayerItemWithRandomChoice.func_174870_v();
            }
        }
        else {
            commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, n - itemStack.stackSize);
            final EntityItem dropPlayerItemWithRandomChoice2 = player.dropPlayerItemWithRandomChoice(itemStack, false);
            if (dropPlayerItemWithRandomChoice2 != null) {
                dropPlayerItemWithRandomChoice2.setNoPickupDelay();
                dropPlayerItemWithRandomChoice2.setOwner(player.getName());
            }
        }
        CommandBase.notifyOperators(commandSender, this, "commands.give.success", itemStack.getChatComponent(), n, player.getName());
    }
    
    protected String[] getPlayers() {
        return MinecraftServer.getServer().getAllUsernames();
    }
}
