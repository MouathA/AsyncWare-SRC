package net.minecraft.command;

import net.minecraft.block.*;
import net.minecraft.world.gen.structure.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;
import java.util.*;

public class CommandClone extends CommandBase
{
    @Override
    public String getCommandName() {
        return "clone";
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length > 0 && array.length <= 3) ? CommandBase.func_175771_a(array, 0, blockPos) : ((array.length > 3 && array.length <= 6) ? CommandBase.func_175771_a(array, 3, blockPos) : ((array.length > 6 && array.length <= 9) ? CommandBase.func_175771_a(array, 6, blockPos) : ((array.length == 10) ? CommandBase.getListOfStringsMatchingLastWord(array, "replace", "masked", "filtered") : ((array.length == 11) ? CommandBase.getListOfStringsMatchingLastWord(array, "normal", "force", "move") : ((array.length == 12 && "filtered".equals(array[9])) ? CommandBase.getListOfStringsMatchingLastWord(array, Block.blockRegistry.getKeys()) : null)))));
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.clone.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 9) {
            throw new WrongUsageException("commands.clone.usage", new Object[0]);
        }
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
        final BlockPos blockPos = CommandBase.parseBlockPos(commandSender, array, 0, false);
        final BlockPos blockPos2 = CommandBase.parseBlockPos(commandSender, array, 3, false);
        final BlockPos blockPos3 = CommandBase.parseBlockPos(commandSender, array, 6, false);
        final StructureBoundingBox structureBoundingBox = new StructureBoundingBox(blockPos, blockPos2);
        final StructureBoundingBox structureBoundingBox2 = new StructureBoundingBox(blockPos3, blockPos3.add(structureBoundingBox.func_175896_b()));
        int n = structureBoundingBox.getXSize() * structureBoundingBox.getYSize() * structureBoundingBox.getZSize();
        Block blockByText = null;
        if ((array.length < 11 || (!array[10].equals("force") && !array[10].equals("move"))) && structureBoundingBox.intersectsWith(structureBoundingBox2)) {
            throw new CommandException("commands.clone.noOverlap", new Object[0]);
        }
        if (array.length < 11 || array[10].equals("move")) {}
        if (structureBoundingBox.minY < 0 || structureBoundingBox.maxY >= 256 || structureBoundingBox2.minY < 0 || structureBoundingBox2.maxY >= 256) {
            throw new CommandException("commands.clone.outOfWorld", new Object[0]);
        }
        final World entityWorld = commandSender.getEntityWorld();
        if (entityWorld.isAreaLoaded(structureBoundingBox) && entityWorld.isAreaLoaded(structureBoundingBox2)) {
            if (array.length >= 10) {
                if (!array[9].equals("masked")) {
                    if (array[9].equals("filtered")) {
                        if (array.length < 12) {
                            throw new WrongUsageException("commands.clone.usage", new Object[0]);
                        }
                        blockByText = CommandBase.getBlockByText(commandSender, array[11]);
                        if (array.length >= 13) {
                            CommandBase.parseInt(array[12], 0, 15);
                        }
                    }
                }
            }
            final ArrayList arrayList = Lists.newArrayList();
            final ArrayList arrayList2 = Lists.newArrayList();
            final ArrayList arrayList3 = Lists.newArrayList();
            final LinkedList linkedList = Lists.newLinkedList();
            final BlockPos blockPos4 = new BlockPos(structureBoundingBox2.minX - structureBoundingBox.minX, structureBoundingBox2.minY - structureBoundingBox.minY, structureBoundingBox2.minZ - structureBoundingBox.minZ);
            for (int i = structureBoundingBox.minZ; i <= structureBoundingBox.maxZ; ++i) {
                for (int j = structureBoundingBox.minY; j <= structureBoundingBox.maxY; ++j) {
                    for (int k = structureBoundingBox.minX; k <= structureBoundingBox.maxX; ++k) {
                        final BlockPos blockPos5 = new BlockPos(k, j, i);
                        final BlockPos add = blockPos5.add(blockPos4);
                        final IBlockState blockState = entityWorld.getBlockState(blockPos5);
                        if (blockState.getBlock() != Blocks.air && (blockByText == null || blockState.getBlock() == blockByText)) {
                            final TileEntity tileEntity = entityWorld.getTileEntity(blockPos5);
                            if (tileEntity != null) {
                                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                                tileEntity.writeToNBT(nbtTagCompound);
                                arrayList2.add(new StaticCloneData(add, blockState, nbtTagCompound));
                                linkedList.addLast(blockPos5);
                            }
                            else if (!blockState.getBlock().isFullBlock() && !blockState.getBlock().isFullCube()) {
                                arrayList3.add(new StaticCloneData(add, blockState, null));
                                linkedList.addFirst(blockPos5);
                            }
                            else {
                                arrayList.add(new StaticCloneData(add, blockState, null));
                                linkedList.addLast(blockPos5);
                            }
                        }
                    }
                }
            }
            for (final BlockPos blockPos6 : linkedList) {
                final TileEntity tileEntity2 = entityWorld.getTileEntity(blockPos6);
                if (tileEntity2 instanceof IInventory) {
                    ((IInventory)tileEntity2).clear();
                }
                entityWorld.setBlockState(blockPos6, Blocks.barrier.getDefaultState(), 2);
            }
            final Iterator<BlockPos> iterator2 = linkedList.iterator();
            while (iterator2.hasNext()) {
                entityWorld.setBlockState(iterator2.next(), Blocks.air.getDefaultState(), 3);
            }
            final ArrayList arrayList4 = Lists.newArrayList();
            arrayList4.addAll(arrayList);
            arrayList4.addAll(arrayList2);
            arrayList4.addAll(arrayList3);
            final List reverse = Lists.reverse((List)arrayList4);
            for (final StaticCloneData staticCloneData : reverse) {
                final TileEntity tileEntity3 = entityWorld.getTileEntity(staticCloneData.field_179537_a);
                if (tileEntity3 instanceof IInventory) {
                    ((IInventory)tileEntity3).clear();
                }
                entityWorld.setBlockState(staticCloneData.field_179537_a, Blocks.barrier.getDefaultState(), 2);
            }
            for (final StaticCloneData staticCloneData2 : arrayList4) {
                if (entityWorld.setBlockState(staticCloneData2.field_179537_a, staticCloneData2.blockState, 2)) {
                    ++n;
                }
            }
            for (final StaticCloneData staticCloneData3 : arrayList2) {
                final TileEntity tileEntity4 = entityWorld.getTileEntity(staticCloneData3.field_179537_a);
                if (staticCloneData3.field_179536_c != null && tileEntity4 != null) {
                    staticCloneData3.field_179536_c.setInteger("x", staticCloneData3.field_179537_a.getX());
                    staticCloneData3.field_179536_c.setInteger("y", staticCloneData3.field_179537_a.getY());
                    staticCloneData3.field_179536_c.setInteger("z", staticCloneData3.field_179537_a.getZ());
                    tileEntity4.readFromNBT(staticCloneData3.field_179536_c);
                    tileEntity4.markDirty();
                }
                entityWorld.setBlockState(staticCloneData3.field_179537_a, staticCloneData3.blockState, 2);
            }
            for (final StaticCloneData staticCloneData4 : reverse) {
                entityWorld.notifyNeighborsRespectDebug(staticCloneData4.field_179537_a, staticCloneData4.blockState.getBlock());
            }
            final List func_175712_a = entityWorld.func_175712_a(structureBoundingBox, false);
            if (func_175712_a != null) {
                for (final NextTickListEntry nextTickListEntry : func_175712_a) {
                    if (structureBoundingBox.isVecInside(nextTickListEntry.position)) {
                        entityWorld.scheduleBlockUpdate(nextTickListEntry.position.add(blockPos4), nextTickListEntry.getBlock(), (int)(nextTickListEntry.scheduledTime - entityWorld.getWorldInfo().getWorldTotalTime()), nextTickListEntry.priority);
                    }
                }
            }
            throw new CommandException("commands.clone.failed", new Object[0]);
        }
        throw new CommandException("commands.clone.outOfWorld", new Object[0]);
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    static class StaticCloneData
    {
        public final BlockPos field_179537_a;
        public final IBlockState blockState;
        public final NBTTagCompound field_179536_c;
        
        public StaticCloneData(final BlockPos field_179537_a, final IBlockState blockState, final NBTTagCompound field_179536_c) {
            this.field_179537_a = field_179537_a;
            this.blockState = blockState;
            this.field_179536_c = field_179536_c;
        }
    }
}
