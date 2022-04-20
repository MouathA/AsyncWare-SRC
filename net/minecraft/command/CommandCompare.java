package net.minecraft.command;

import net.minecraft.world.gen.structure.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;
import java.util.*;

public class CommandCompare extends CommandBase
{
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.compare.usage";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandName() {
        return "testforblocks";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 9) {
            throw new WrongUsageException("commands.compare.usage", new Object[0]);
        }
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
        final BlockPos blockPos = CommandBase.parseBlockPos(commandSender, array, 0, false);
        final BlockPos blockPos2 = CommandBase.parseBlockPos(commandSender, array, 3, false);
        final BlockPos blockPos3 = CommandBase.parseBlockPos(commandSender, array, 6, false);
        final StructureBoundingBox structureBoundingBox = new StructureBoundingBox(blockPos, blockPos2);
        final StructureBoundingBox structureBoundingBox2 = new StructureBoundingBox(blockPos3, blockPos3.add(structureBoundingBox.func_175896_b()));
        int n = structureBoundingBox.getXSize() * structureBoundingBox.getYSize() * structureBoundingBox.getZSize();
        if (structureBoundingBox.minY < 0 || structureBoundingBox.maxY >= 256 || structureBoundingBox2.minY < 0 || structureBoundingBox2.maxY >= 256) {
            throw new CommandException("commands.compare.outOfWorld", new Object[0]);
        }
        final World entityWorld = commandSender.getEntityWorld();
        if (entityWorld.isAreaLoaded(structureBoundingBox) && entityWorld.isAreaLoaded(structureBoundingBox2)) {
            if (array.length <= 9 || array[9].equals("masked")) {}
            final BlockPos blockPos4 = new BlockPos(structureBoundingBox2.minX - structureBoundingBox.minX, structureBoundingBox2.minY - structureBoundingBox.minY, structureBoundingBox2.minZ - structureBoundingBox.minZ);
            final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
            final BlockPos.MutableBlockPos mutableBlockPos2 = new BlockPos.MutableBlockPos();
            for (int i = structureBoundingBox.minZ; i <= structureBoundingBox.maxZ; ++i) {
                for (int j = structureBoundingBox.minY; j <= structureBoundingBox.maxY; ++j) {
                    for (int k = structureBoundingBox.minX; k <= structureBoundingBox.maxX; ++k) {
                        mutableBlockPos.func_181079_c(k, j, i);
                        mutableBlockPos2.func_181079_c(k + blockPos4.getX(), j + blockPos4.getY(), i + blockPos4.getZ());
                        final IBlockState blockState = entityWorld.getBlockState(mutableBlockPos);
                        if (blockState.getBlock() != Blocks.air) {
                            if (blockState == entityWorld.getBlockState(mutableBlockPos2)) {
                                final TileEntity tileEntity = entityWorld.getTileEntity(mutableBlockPos);
                                final TileEntity tileEntity2 = entityWorld.getTileEntity(mutableBlockPos2);
                                if (tileEntity != null && tileEntity2 != null) {
                                    final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                                    tileEntity.writeToNBT(nbtTagCompound);
                                    nbtTagCompound.removeTag("x");
                                    nbtTagCompound.removeTag("y");
                                    nbtTagCompound.removeTag("z");
                                    final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                                    tileEntity2.writeToNBT(nbtTagCompound2);
                                    nbtTagCompound2.removeTag("x");
                                    nbtTagCompound2.removeTag("y");
                                    nbtTagCompound2.removeTag("z");
                                    if (!nbtTagCompound.equals(nbtTagCompound2)) {}
                                }
                                else if (tileEntity != null) {}
                            }
                            ++n;
                            throw new CommandException("commands.compare.failed", new Object[0]);
                        }
                    }
                }
            }
            commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
            CommandBase.notifyOperators(commandSender, this, "commands.compare.success", 0);
            return;
        }
        throw new CommandException("commands.compare.outOfWorld", new Object[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length > 0 && array.length <= 3) ? CommandBase.func_175771_a(array, 0, blockPos) : ((array.length > 3 && array.length <= 6) ? CommandBase.func_175771_a(array, 3, blockPos) : ((array.length > 6 && array.length <= 9) ? CommandBase.func_175771_a(array, 6, blockPos) : ((array.length == 10) ? CommandBase.getListOfStringsMatchingLastWord(array, "masked", "all") : null)));
    }
}
