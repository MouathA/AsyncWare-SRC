package net.minecraft.command.server;

import net.minecraft.util.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.init.*;
import net.minecraft.command.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.state.*;

public class CommandSetBlock extends CommandBase
{
    @Override
    public String getCommandName() {
        return "setblock";
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length > 0 && array.length <= 3) ? CommandBase.func_175771_a(array, 0, blockPos) : ((array.length == 4) ? CommandBase.getListOfStringsMatchingLastWord(array, Block.blockRegistry.getKeys()) : ((array.length == 6) ? CommandBase.getListOfStringsMatchingLastWord(array, "replace", "destroy", "keep") : null));
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.setblock.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 4) {
            throw new WrongUsageException("commands.setblock.usage", new Object[0]);
        }
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
        final BlockPos blockPos = CommandBase.parseBlockPos(commandSender, array, 0, false);
        final Block blockByText = CommandBase.getBlockByText(commandSender, array[3]);
        if (array.length >= 5) {
            CommandBase.parseInt(array[4], 0, 15);
        }
        final World entityWorld = commandSender.getEntityWorld();
        if (!entityWorld.isBlockLoaded(blockPos)) {
            throw new CommandException("commands.setblock.outOfWorld", new Object[0]);
        }
        NBTTagCompound tagFromJson = new NBTTagCompound();
        if (array.length >= 7 && blockByText.hasTileEntity()) {
            tagFromJson = JsonToNBT.getTagFromJson(CommandBase.getChatComponentFromNthArg(commandSender, array, 6).getUnformattedText());
        }
        if (array.length >= 6) {
            if (array[5].equals("destroy")) {
                entityWorld.destroyBlock(blockPos, true);
                if (blockByText == Blocks.air) {
                    CommandBase.notifyOperators(commandSender, this, "commands.setblock.success", new Object[0]);
                    return;
                }
            }
            else if (array[5].equals("keep") && !entityWorld.isAirBlock(blockPos)) {
                throw new CommandException("commands.setblock.noChange", new Object[0]);
            }
        }
        final TileEntity tileEntity = entityWorld.getTileEntity(blockPos);
        if (tileEntity != null) {
            if (tileEntity instanceof IInventory) {
                ((IInventory)tileEntity).clear();
            }
            entityWorld.setBlockState(blockPos, Blocks.air.getDefaultState(), (blockByText == Blocks.air) ? 2 : 4);
        }
        final IBlockState stateFromMeta = blockByText.getStateFromMeta(0);
        if (!entityWorld.setBlockState(blockPos, stateFromMeta, 2)) {
            throw new CommandException("commands.setblock.noChange", new Object[0]);
        }
        final TileEntity tileEntity2 = entityWorld.getTileEntity(blockPos);
        if (tileEntity2 != null) {
            tagFromJson.setInteger("x", blockPos.getX());
            tagFromJson.setInteger("y", blockPos.getY());
            tagFromJson.setInteger("z", blockPos.getZ());
            tileEntity2.readFromNBT(tagFromJson);
        }
        entityWorld.notifyNeighborsRespectDebug(blockPos, stateFromMeta.getBlock());
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 1);
        CommandBase.notifyOperators(commandSender, this, "commands.setblock.success", new Object[0]);
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
}
