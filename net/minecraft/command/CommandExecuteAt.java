package net.minecraft.command;

import net.minecraft.entity.*;
import net.minecraft.server.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import java.util.*;

public class CommandExecuteAt extends CommandBase
{
    @Override
    public String getCommandName() {
        return "execute";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 5) {
            throw new WrongUsageException("commands.execute.usage", new Object[0]);
        }
        final Entity entity = CommandBase.getEntity(commandSender, array[0], Entity.class);
        final double double1 = CommandBase.parseDouble(entity.posX, array[1], false);
        final double double2 = CommandBase.parseDouble(entity.posY, array[2], false);
        final double double3 = CommandBase.parseDouble(entity.posZ, array[3], false);
        final BlockPos blockPos = new BlockPos(double1, double2, double3);
        if ("detect".equals(array[4]) && array.length > 10) {
            final World entityWorld = entity.getEntityWorld();
            final double double4 = CommandBase.parseDouble(double1, array[5], false);
            final double double5 = CommandBase.parseDouble(double2, array[6], false);
            final double double6 = CommandBase.parseDouble(double3, array[7], false);
            final Block blockByText = CommandBase.getBlockByText(commandSender, array[8]);
            final int int1 = CommandBase.parseInt(array[9], -1, 15);
            final IBlockState blockState = entityWorld.getBlockState(new BlockPos(double4, double5, double6));
            if (blockState.getBlock() != blockByText || (int1 >= 0 && blockState.getBlock().getMetaFromState(blockState) != int1)) {
                throw new CommandException("commands.execute.failed", new Object[] { "detect", entity.getName() });
            }
        }
        final String buildString = CommandBase.buildString(array, 10);
        if (MinecraftServer.getServer().getCommandManager().executeCommand(new ICommandSender(this, entity, commandSender, blockPos, double1, double2, double3) {
            final Entity val$entity;
            final double val$d1;
            final double val$d0;
            final double val$d2;
            final ICommandSender val$sender;
            final CommandExecuteAt this$0;
            final BlockPos val$blockpos;
            
            @Override
            public void addChatMessage(final IChatComponent chatComponent) {
                this.val$sender.addChatMessage(chatComponent);
            }
            
            @Override
            public String getName() {
                return this.val$entity.getName();
            }
            
            @Override
            public boolean sendCommandFeedback() {
                final MinecraftServer server = MinecraftServer.getServer();
                return server == null || server.worldServers[0].getGameRules().getBoolean("commandBlockOutput");
            }
            
            @Override
            public World getEntityWorld() {
                return this.val$entity.worldObj;
            }
            
            @Override
            public boolean canCommandSenderUseCommand(final int n, final String s) {
                return this.val$sender.canCommandSenderUseCommand(n, s);
            }
            
            @Override
            public Vec3 getPositionVector() {
                return new Vec3(this.val$d0, this.val$d1, this.val$d2);
            }
            
            @Override
            public BlockPos getPosition() {
                return this.val$blockpos;
            }
            
            @Override
            public Entity getCommandSenderEntity() {
                return this.val$entity;
            }
            
            @Override
            public IChatComponent getDisplayName() {
                return this.val$entity.getDisplayName();
            }
            
            @Override
            public void setCommandStat(final CommandResultStats.Type type, final int n) {
                this.val$entity.setCommandStat(type, n);
            }
        }, buildString) < 1) {
            throw new CommandException("commands.execute.allInvocationsFailed", new Object[] { buildString });
        }
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return n == 0;
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.execute.usage";
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames()) : ((array.length > 1 && array.length <= 4) ? CommandBase.func_175771_a(array, 1, blockPos) : ((array.length > 5 && array.length <= 8 && "detect".equals(array[4])) ? CommandBase.func_175771_a(array, 5, blockPos) : ((array.length == 9 && "detect".equals(array[4])) ? CommandBase.getListOfStringsMatchingLastWord(array, Block.blockRegistry.getKeys()) : null)));
    }
}
