package net.minecraft.command;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.scoreboard.*;
import com.google.common.collect.*;
import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.block.material.*;

public class CommandSpreadPlayers extends CommandBase
{
    private Position[] func_110670_a(final Random random, final int n, final double n2, final double n3, final double n4, final double n5) {
        final Position[] array = new Position[n];
        while (0 < array.length) {
            final Position position = new Position();
            position.func_111097_a(random, n2, n3, n4, n5);
            array[0] = position;
            int n6 = 0;
            ++n6;
        }
        return array;
    }
    
    private int func_110668_a(final Position position, final double n, final World world, final Random random, final double n2, final double n3, final double n4, final double n5, final Position[] array, final boolean b) throws CommandException {
        while (true) {
            while (0 < array.length) {
                final Position position2 = array[0];
                final Position position3 = new Position();
                while (0 < array.length) {
                    int n6 = 0;
                    ++n6;
                }
                if (position2.func_111093_a(n2, n3, n4, n5)) {}
                int n7 = 0;
                ++n7;
            }
            int n8 = 0;
            ++n8;
        }
    }
    
    @Override
    public String getCommandName() {
        return "spreadplayers";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    private double func_110671_a(final List list, final World world, final Position[] array, final boolean b) {
        double n = 0.0;
        final HashMap hashMap = Maps.newHashMap();
        while (0 < list.size()) {
            final Entity entity = list.get(0);
            Position position;
            if (b) {
                final Team team = (entity instanceof EntityPlayer) ? ((EntityPlayer)entity).getTeam() : null;
                if (!hashMap.containsKey(team)) {
                    final HashMap hashMap2 = hashMap;
                    final Team team2 = team;
                    final int n2 = 0;
                    int n3 = 0;
                    ++n3;
                    hashMap2.put(team2, array[n2]);
                }
                position = hashMap.get(team);
            }
            else {
                final int n4 = 0;
                int n3 = 0;
                ++n3;
                position = array[n4];
            }
            entity.setPositionAndUpdate(MathHelper.floor_double(position.field_111101_a) + 0.5f, position.func_111092_a(world), MathHelper.floor_double(position.field_111100_b) + 0.5);
            double min = Double.MAX_VALUE;
            while (0 < array.length) {
                if (position != array[0]) {
                    min = Math.min(position.func_111099_a(array[0]), min);
                }
                int n5 = 0;
                ++n5;
            }
            n += min;
            int n6 = 0;
            ++n6;
        }
        return n / list.size();
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.spreadplayers.usage";
    }
    
    private int func_110667_a(final List list) {
        final HashSet hashSet = Sets.newHashSet();
        for (final Entity entity : list) {
            if (entity instanceof EntityPlayer) {
                hashSet.add(((EntityPlayer)entity).getTeam());
            }
            else {
                hashSet.add(null);
            }
        }
        return hashSet.size();
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 6) {
            throw new WrongUsageException("commands.spreadplayers.usage", new Object[0]);
        }
        final BlockPos position = commandSender.getPosition();
        final double n = position.getX();
        final int n2 = 0;
        int n3 = 0;
        ++n3;
        final double double1 = CommandBase.parseDouble(n, array[n2], true);
        final double n4 = position.getZ();
        final int n5 = 0;
        ++n3;
        final double double2 = CommandBase.parseDouble(n4, array[n5], true);
        final int n6 = 0;
        ++n3;
        final double double3 = CommandBase.parseDouble(array[n6], 0.0);
        final int n7 = 0;
        ++n3;
        final double double4 = CommandBase.parseDouble(array[n7], double3 + 1.0);
        final int n8 = 0;
        ++n3;
        final boolean boolean1 = CommandBase.parseBoolean(array[n8]);
        final ArrayList arrayList = Lists.newArrayList();
        while (0 < array.length) {
            final int n9 = 0;
            ++n3;
            final String s = array[n9];
            if (PlayerSelector.hasArguments(s)) {
                final List matchEntities = PlayerSelector.matchEntities(commandSender, s, Entity.class);
                if (matchEntities.size() == 0) {
                    throw new EntityNotFoundException();
                }
                arrayList.addAll(matchEntities);
            }
            else {
                final EntityPlayerMP playerByUsername = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(s);
                if (playerByUsername == null) {
                    throw new PlayerNotFoundException();
                }
                arrayList.add(playerByUsername);
            }
        }
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, arrayList.size());
        if (arrayList.isEmpty()) {
            throw new EntityNotFoundException();
        }
        commandSender.addChatMessage(new ChatComponentTranslation("commands.spreadplayers.spreading." + (boolean1 ? "teams" : "players"), new Object[] { arrayList.size(), double4, double1, double2, double3 }));
        this.func_110669_a(commandSender, arrayList, new Position(double1, double2), double3, double4, ((EntityPlayerMP)arrayList.get(0)).worldObj, boolean1);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length >= 1 && array.length <= 2) ? CommandBase.func_181043_b(array, 0, blockPos) : null;
    }
    
    private void func_110669_a(final ICommandSender commandSender, final List list, final Position position, final double n, final double n2, final World world, final boolean b) throws CommandException {
        final Random random = new Random();
        final double n3 = position.field_111101_a - n2;
        final double n4 = position.field_111100_b - n2;
        final double n5 = position.field_111101_a + n2;
        final double n6 = position.field_111100_b + n2;
        final Position[] func_110670_a = this.func_110670_a(random, b ? this.func_110667_a(list) : list.size(), n3, n4, n5, n6);
        final int func_110668_a = this.func_110668_a(position, n, world, random, n3, n4, n5, n6, func_110670_a, b);
        final double func_110671_a = this.func_110671_a(list, world, func_110670_a, b);
        CommandBase.notifyOperators(commandSender, this, "commands.spreadplayers.success." + (b ? "teams" : "players"), func_110670_a.length, position.field_111101_a, position.field_111100_b);
        if (func_110670_a.length > 1) {
            commandSender.addChatMessage(new ChatComponentTranslation("commands.spreadplayers.info." + (b ? "teams" : "players"), new Object[] { String.format("%.2f", func_110671_a), func_110668_a }));
        }
    }
    
    static class Position
    {
        double field_111101_a;
        double field_111100_b;
        
        public void func_111094_b(final Position position) {
            this.field_111101_a -= position.field_111101_a;
            this.field_111100_b -= position.field_111100_b;
        }
        
        Position() {
        }
        
        public void func_111097_a(final Random random, final double n, final double n2, final double n3, final double n4) {
            this.field_111101_a = MathHelper.getRandomDoubleInRange(random, n, n3);
            this.field_111100_b = MathHelper.getRandomDoubleInRange(random, n2, n4);
        }
        
        public boolean func_111098_b(final World world) {
            BlockPos down = new BlockPos(this.field_111101_a, 256.0, this.field_111100_b);
            while (down.getY() > 0) {
                down = down.down();
                final Material material = world.getBlockState(down).getBlock().getMaterial();
                if (material != Material.air) {
                    return !material.isLiquid() && material != Material.fire;
                }
            }
            return false;
        }
        
        void func_111095_a() {
            final double n = this.func_111096_b();
            this.field_111101_a /= n;
            this.field_111100_b /= n;
        }
        
        public int func_111092_a(final World world) {
            BlockPos down = new BlockPos(this.field_111101_a, 256.0, this.field_111100_b);
            while (down.getY() > 0) {
                down = down.down();
                if (world.getBlockState(down).getBlock().getMaterial() != Material.air) {
                    return down.getY() + 1;
                }
            }
            return 257;
        }
        
        double func_111099_a(final Position position) {
            final double n = this.field_111101_a - position.field_111101_a;
            final double n2 = this.field_111100_b - position.field_111100_b;
            return Math.sqrt(n * n + n2 * n2);
        }
        
        Position(final double field_111101_a, final double field_111100_b) {
            this.field_111101_a = field_111101_a;
            this.field_111100_b = field_111100_b;
        }
        
        public boolean func_111093_a(final double field_111101_a, final double field_111100_b, final double field_111101_a2, final double field_111100_b2) {
            if (this.field_111101_a < field_111101_a) {
                this.field_111101_a = field_111101_a;
            }
            else if (this.field_111101_a > field_111101_a2) {
                this.field_111101_a = field_111101_a2;
            }
            if (this.field_111100_b < field_111100_b) {
                this.field_111100_b = field_111100_b;
            }
            else if (this.field_111100_b > field_111100_b2) {
                this.field_111100_b = field_111100_b2;
            }
            return true;
        }
        
        float func_111096_b() {
            return MathHelper.sqrt_double(this.field_111101_a * this.field_111101_a + this.field_111100_b * this.field_111100_b);
        }
    }
}
