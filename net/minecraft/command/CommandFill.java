package net.minecraft.command;

import net.minecraft.util.*;
import net.minecraft.nbt.*;
import com.google.common.collect.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;
import java.util.*;

public class CommandFill extends CommandBase
{
    @Override
    public String getCommandName() {
        return "fill";
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.fill.usage";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 7) {
            throw new WrongUsageException("commands.fill.usage", new Object[0]);
        }
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
        final BlockPos blockPos = CommandBase.parseBlockPos(commandSender, array, 0, false);
        final BlockPos blockPos2 = CommandBase.parseBlockPos(commandSender, array, 3, false);
        final Block blockByText = CommandBase.getBlockByText(commandSender, array[6]);
        if (array.length >= 8) {
            CommandBase.parseInt(array[7], 0, 15);
        }
        final BlockPos blockPos3 = new BlockPos(Math.min(blockPos.getX(), blockPos2.getX()), Math.min(blockPos.getY(), blockPos2.getY()), Math.min(blockPos.getZ(), blockPos2.getZ()));
        final BlockPos blockPos4 = new BlockPos(Math.max(blockPos.getX(), blockPos2.getX()), Math.max(blockPos.getY(), blockPos2.getY()), Math.max(blockPos.getZ(), blockPos2.getZ()));
        int n = (blockPos4.getX() - blockPos3.getX() + 1) * (blockPos4.getY() - blockPos3.getY() + 1) * (blockPos4.getZ() - blockPos3.getZ() + 1);
        if (blockPos3.getY() >= 0 && blockPos4.getY() < 256) {
            final World entityWorld = commandSender.getEntityWorld();
            for (int i = blockPos3.getZ(); i < blockPos4.getZ() + 16; i += 16) {
                int x = blockPos3.getX();
                while (1 < blockPos4.getX() + 16) {
                    if (!entityWorld.isBlockLoaded(new BlockPos(1, blockPos4.getY() - blockPos3.getY(), i))) {
                        throw new CommandException("commands.fill.outOfWorld", new Object[0]);
                    }
                    x += 16;
                }
            }
            NBTTagCompound tagFromJson = new NBTTagCompound();
            if (array.length >= 10 && blockByText.hasTileEntity()) {
                tagFromJson = JsonToNBT.getTagFromJson(CommandBase.getChatComponentFromNthArg(commandSender, array, 9).getUnformattedText());
            }
            final ArrayList arrayList = Lists.newArrayList();
            for (int j = blockPos3.getZ(); j <= blockPos4.getZ(); ++j) {
                for (int k = blockPos3.getY(); k <= blockPos4.getY(); ++k) {
                    for (int l = blockPos3.getX(); l <= blockPos4.getX(); ++l) {
                        final BlockPos blockPos5 = new BlockPos(l, k, j);
                        if (array.length >= 9) {
                            if (!array[8].equals("outline") && !array[8].equals("hollow")) {
                                if (array[8].equals("destroy")) {
                                    entityWorld.destroyBlock(blockPos5, true);
                                }
                                else if (array[8].equals("keep")) {
                                    if (!entityWorld.isAirBlock(blockPos5)) {
                                        continue;
                                    }
                                }
                                else if (array[8].equals("replace") && !blockByText.hasTileEntity()) {
                                    if (array.length > 9 && entityWorld.getBlockState(blockPos5).getBlock() != CommandBase.getBlockByText(commandSender, array[9])) {
                                        continue;
                                    }
                                    if (array.length > 10) {
                                        final int int1 = CommandBase.parseInt(array[10]);
                                        final IBlockState blockState = entityWorld.getBlockState(blockPos5);
                                        if (blockState.getBlock().getMetaFromState(blockState) != int1) {
                                            continue;
                                        }
                                    }
                                }
                            }
                            else if (l != blockPos3.getX() && l != blockPos4.getX() && k != blockPos3.getY() && k != blockPos4.getY() && j != blockPos3.getZ() && j != blockPos4.getZ()) {
                                if (array[8].equals("hollow")) {
                                    entityWorld.setBlockState(blockPos5, Blocks.air.getDefaultState(), 2);
                                    arrayList.add(blockPos5);
                                }
                                continue;
                            }
                        }
                        final TileEntity tileEntity = entityWorld.getTileEntity(blockPos5);
                        if (tileEntity != null) {
                            if (tileEntity instanceof IInventory) {
                                ((IInventory)tileEntity).clear();
                            }
                            entityWorld.setBlockState(blockPos5, Blocks.barrier.getDefaultState(), (blockByText == Blocks.barrier) ? 2 : 4);
                        }
                        if (entityWorld.setBlockState(blockPos5, blockByText.getStateFromMeta(0), 2)) {
                            arrayList.add(blockPos5);
                            ++n;
                            final TileEntity tileEntity2 = entityWorld.getTileEntity(blockPos5);
                            if (tileEntity2 != null) {
                                tagFromJson.setInteger("x", blockPos5.getX());
                                tagFromJson.setInteger("y", blockPos5.getY());
                                tagFromJson.setInteger("z", blockPos5.getZ());
                                tileEntity2.readFromNBT(tagFromJson);
                            }
                        }
                    }
                }
            }
            for (final BlockPos blockPos6 : arrayList) {
                entityWorld.notifyNeighborsRespectDebug(blockPos6, entityWorld.getBlockState(blockPos6).getBlock());
            }
            throw new CommandException("commands.fill.failed", new Object[0]);
        }
        throw new CommandException("commands.fill.outOfWorld", new Object[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length > 0 && array.length <= 3) ? CommandBase.func_175771_a(array, 0, blockPos) : ((array.length > 3 && array.length <= 6) ? CommandBase.func_175771_a(array, 3, blockPos) : ((array.length == 7) ? CommandBase.getListOfStringsMatchingLastWord(array, Block.blockRegistry.getKeys()) : ((array.length == 9) ? CommandBase.getListOfStringsMatchingLastWord(array, "replace", "destroy", "keep", "hollow", "outline") : ((array.length == 10 && "replace".equals(array[8])) ? CommandBase.getListOfStringsMatchingLastWord(array, Block.blockRegistry.getKeys()) : null))));
    }
}
