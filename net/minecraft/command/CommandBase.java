package net.minecraft.command;

import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import com.google.common.primitives.*;
import net.minecraft.server.*;
import java.util.*;
import net.minecraft.entity.player.*;

public abstract class CommandBase implements ICommand
{
    private static IAdminCommand theAdmin;
    
    public static String getPlayerName(final ICommandSender commandSender, final String s) throws PlayerNotFoundException {
        return getPlayer(commandSender, s).getName();
    }
    
    public static long parseLong(final String s, final long n, final long n2) throws NumberInvalidException {
        final long long1 = parseLong(s);
        if (long1 < n) {
            throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[] { long1, n });
        }
        if (long1 > n2) {
            throw new NumberInvalidException("commands.generic.num.tooBig", new Object[] { long1, n2 });
        }
        return long1;
    }
    
    public static int parseInt(final String s) throws NumberInvalidException {
        return Integer.parseInt(s);
    }
    
    public static int parseInt(final String s, final int n) throws NumberInvalidException {
        return parseInt(s, n, Integer.MAX_VALUE);
    }
    
    public static boolean doesStringStartWith(final String s, final String s2) {
        return s2.regionMatches(true, 0, s, 0, s.length());
    }
    
    public static long parseLong(final String s) throws NumberInvalidException {
        return Long.parseLong(s);
    }
    
    public static void notifyOperators(final ICommandSender commandSender, final ICommand command, final int n, final String s, final Object... array) {
        if (CommandBase.theAdmin != null) {
            CommandBase.theAdmin.notifyOperators(commandSender, command, n, s, array);
        }
    }
    
    public static IChatComponent join(final List list) {
        final ChatComponentText chatComponentText = new ChatComponentText("");
        while (0 < list.size()) {
            chatComponentText.appendSibling(list.get(0));
            int n = 0;
            ++n;
        }
        return chatComponentText;
    }
    
    public static double parseDouble(final double n, String substring, final int n2, final int n3, final boolean b) throws NumberInvalidException {
        final boolean startsWith = substring.startsWith("~");
        if (startsWith && Double.isNaN(n)) {
            throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { n });
        }
        double n4 = startsWith ? n : 0.0;
        if (!startsWith || substring.length() > 1) {
            final boolean contains = substring.contains(".");
            if (startsWith) {
                substring = substring.substring(1);
            }
            n4 += parseDouble(substring);
            if (!contains && !startsWith && b) {
                n4 += 0.5;
            }
        }
        if (n2 != 0 || n3 != 0) {
            if (n4 < n2) {
                throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[] { n4, n2 });
            }
            if (n4 > n3) {
                throw new NumberInvalidException("commands.generic.double.tooBig", new Object[] { n4, n3 });
            }
        }
        return n4;
    }
    
    public static Item getItemByText(final ICommandSender commandSender, final String s) throws NumberInvalidException {
        final ResourceLocation resourceLocation = new ResourceLocation(s);
        final Item item = (Item)Item.itemRegistry.getObject(resourceLocation);
        if (item == null) {
            throw new NumberInvalidException("commands.give.item.notFound", new Object[] { resourceLocation });
        }
        return item;
    }
    
    public static Entity func_175768_b(final ICommandSender commandSender, final String s) throws EntityNotFoundException {
        return getEntity(commandSender, s, Entity.class);
    }
    
    public static void notifyOperators(final ICommandSender commandSender, final ICommand command, final String s, final Object... array) {
        notifyOperators(commandSender, command, 0, s, array);
    }
    
    public static List func_175771_a(final String[] array, final int n, final BlockPos blockPos) {
        if (blockPos == null) {
            return null;
        }
        final int n2 = array.length - 1;
        String s;
        if (n2 == n) {
            s = Integer.toString(blockPos.getX());
        }
        else if (n2 == n + 1) {
            s = Integer.toString(blockPos.getY());
        }
        else {
            if (n2 != n + 2) {
                return null;
            }
            s = Integer.toString(blockPos.getZ());
        }
        return Lists.newArrayList((Object[])new String[] { s });
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return false;
    }
    
    public static IChatComponent getChatComponentFromNthArg(final ICommandSender commandSender, final String[] array, final int n) throws CommandException, PlayerNotFoundException {
        return getChatComponentFromNthArg(commandSender, array, n, false);
    }
    
    public static void setAdminCommander(final IAdminCommand theAdmin) {
        CommandBase.theAdmin = theAdmin;
    }
    
    public static CoordinateArg parseCoordinate(final double n, String substring, final int n2, final int n3, final boolean b) throws NumberInvalidException {
        final boolean startsWith = substring.startsWith("~");
        if (startsWith && Double.isNaN(n)) {
            throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { n });
        }
        double n4 = 0.0;
        if (!startsWith || substring.length() > 1) {
            final boolean contains = substring.contains(".");
            if (startsWith) {
                substring = substring.substring(1);
            }
            n4 += parseDouble(substring);
            if (!contains && !startsWith && b) {
                n4 += 0.5;
            }
        }
        if (n2 != 0 || n3 != 0) {
            if (n4 < n2) {
                throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[] { n4, n2 });
            }
            if (n4 > n3) {
                throw new NumberInvalidException("commands.generic.double.tooBig", new Object[] { n4, n3 });
            }
        }
        return new CoordinateArg(n4 + (startsWith ? n : 0.0), n4, startsWith);
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender commandSender) {
        return commandSender.canCommandSenderUseCommand(this.getRequiredPermissionLevel(), this.getCommandName());
    }
    
    public static Block getBlockByText(final ICommandSender commandSender, final String s) throws NumberInvalidException {
        final ResourceLocation resourceLocation = new ResourceLocation(s);
        if (!Block.blockRegistry.containsKey(resourceLocation)) {
            throw new NumberInvalidException("commands.give.block.notFound", new Object[] { resourceLocation });
        }
        final Block block = (Block)Block.blockRegistry.getObject(resourceLocation);
        if (block == null) {
            throw new NumberInvalidException("commands.give.block.notFound", new Object[] { resourceLocation });
        }
        return block;
    }
    
    public static boolean parseBoolean(final String s) throws CommandException {
        if (s.equals("true") || s.equals("1")) {
            return true;
        }
        if (!s.equals("false") && !s.equals("0")) {
            throw new CommandException("commands.generic.boolean.invalid", new Object[] { s });
        }
        return false;
    }
    
    public static List getListOfStringsMatchingLastWord(final String[] array, final Collection collection) {
        final String s = array[array.length - 1];
        final ArrayList arrayList = Lists.newArrayList();
        if (!collection.isEmpty()) {
            for (final String s2 : Iterables.transform((Iterable)collection, Functions.toStringFunction())) {
                if (doesStringStartWith(s, s2)) {
                    arrayList.add(s2);
                }
            }
            if (arrayList.isEmpty()) {
                for (final ResourceLocation next : collection) {
                    if (next instanceof ResourceLocation && doesStringStartWith(s, next.getResourcePath())) {
                        arrayList.add(String.valueOf(next));
                    }
                }
            }
        }
        return arrayList;
    }
    
    public static CoordinateArg parseCoordinate(final double n, final String s, final boolean b) throws NumberInvalidException {
        return parseCoordinate(n, s, -30000000, 30000000, b);
    }
    
    @Override
    public List getCommandAliases() {
        return Collections.emptyList();
    }
    
    public static List func_175763_c(final ICommandSender commandSender, final String s) throws EntityNotFoundException {
        return PlayerSelector.hasArguments(s) ? PlayerSelector.matchEntities(commandSender, s, Entity.class) : Lists.newArrayList((Object[])new Entity[] { func_175768_b(commandSender, s) });
    }
    
    public static double parseDouble(final String s) throws NumberInvalidException {
        final double double1 = Double.parseDouble(s);
        if (!Doubles.isFinite(double1)) {
            throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { s });
        }
        return double1;
    }
    
    public int compareTo(final ICommand command) {
        return this.getCommandName().compareTo(command.getCommandName());
    }
    
    public int getRequiredPermissionLevel() {
        return 4;
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((ICommand)o);
    }
    
    public static String buildString(final String[] array, final int n) {
        final StringBuilder sb = new StringBuilder();
        for (int i = n; i < array.length; ++i) {
            if (i > n) {
                sb.append(" ");
            }
            sb.append(array[i]);
        }
        return sb.toString();
    }
    
    public static double parseDouble(final String s, final double n, final double n2) throws NumberInvalidException {
        final double double1 = parseDouble(s);
        if (double1 < n) {
            throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[] { double1, n });
        }
        if (double1 > n2) {
            throw new NumberInvalidException("commands.generic.double.tooBig", new Object[] { double1, n2 });
        }
        return double1;
    }
    
    public static double parseDouble(final String s, final double n) throws NumberInvalidException {
        return parseDouble(s, n, Double.MAX_VALUE);
    }
    
    public static String getEntityName(final ICommandSender commandSender, final String s) throws EntityNotFoundException {
        return getPlayer(commandSender, s).getName();
    }
    
    public static double parseDouble(final double n, final String s, final boolean b) throws NumberInvalidException {
        return parseDouble(n, s, -30000000, 30000000, b);
    }
    
    public static int parseInt(final String s, final int n, final int n2) throws NumberInvalidException {
        final int int1 = parseInt(s);
        if (int1 < n) {
            throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[] { int1, n });
        }
        if (int1 > n2) {
            throw new NumberInvalidException("commands.generic.num.tooBig", new Object[] { int1, n2 });
        }
        return int1;
    }
    
    public static BlockPos parseBlockPos(final ICommandSender commandSender, final String[] array, final int n, final boolean b) throws NumberInvalidException {
        final BlockPos position = commandSender.getPosition();
        return new BlockPos(parseDouble(position.getX(), array[n], -30000000, 30000000, b), parseDouble(position.getY(), array[n + 1], 0, 256, false), parseDouble(position.getZ(), array[n + 2], -30000000, 30000000, b));
    }
    
    public static String joinNiceString(final Object[] array) {
        final StringBuilder sb = new StringBuilder();
        while (0 < array.length) {
            sb.append(array[0].toString());
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    public static Entity getEntity(final ICommandSender commandSender, final String s, final Class clazz) throws EntityNotFoundException {
        Entity entity = PlayerSelector.matchOneEntity(commandSender, s, clazz);
        final MinecraftServer server = MinecraftServer.getServer();
        if (entity == null) {
            entity = server.getConfigurationManager().getPlayerByUsername(s);
        }
        if (entity == null) {
            final UUID fromString = UUID.fromString(s);
            entity = server.getEntityFromUuid(fromString);
            if (entity == null) {
                entity = server.getConfigurationManager().getPlayerByUUID(fromString);
            }
        }
        if (entity != null && clazz.isAssignableFrom(entity.getClass())) {
            return entity;
        }
        throw new EntityNotFoundException();
    }
    
    public static List getListOfStringsMatchingLastWord(final String[] array, final String... array2) {
        return getListOfStringsMatchingLastWord(array, Arrays.asList(array2));
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return null;
    }
    
    public static IChatComponent getChatComponentFromNthArg(final ICommandSender commandSender, final String[] array, final int n, final boolean b) throws PlayerNotFoundException {
        final ChatComponentText chatComponentText = new ChatComponentText("");
        for (int i = n; i < array.length; ++i) {
            if (i > n) {
                chatComponentText.appendText(" ");
            }
            IChatComponent chatComponent = new ChatComponentText(array[i]);
            if (b) {
                final IChatComponent matchEntitiesToChatComponent = PlayerSelector.matchEntitiesToChatComponent(commandSender, array[i]);
                if (matchEntitiesToChatComponent == null) {
                    if (PlayerSelector.hasArguments(array[i])) {
                        throw new PlayerNotFoundException();
                    }
                }
                else {
                    chatComponent = matchEntitiesToChatComponent;
                }
            }
            chatComponentText.appendSibling(chatComponent);
        }
        return chatComponentText;
    }
    
    public static List func_181043_b(final String[] array, final int n, final BlockPos blockPos) {
        if (blockPos == null) {
            return null;
        }
        final int n2 = array.length - 1;
        String s;
        if (n2 == n) {
            s = Integer.toString(blockPos.getX());
        }
        else {
            if (n2 != n + 1) {
                return null;
            }
            s = Integer.toString(blockPos.getZ());
        }
        return Lists.newArrayList((Object[])new String[] { s });
    }
    
    public static EntityPlayerMP getPlayer(final ICommandSender commandSender, final String s) throws PlayerNotFoundException {
        EntityPlayerMP entityPlayerMP = PlayerSelector.matchOnePlayer(commandSender, s);
        if (entityPlayerMP == null) {
            entityPlayerMP = MinecraftServer.getServer().getConfigurationManager().getPlayerByUUID(UUID.fromString(s));
        }
        if (entityPlayerMP == null) {
            entityPlayerMP = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(s);
        }
        if (entityPlayerMP == null) {
            throw new PlayerNotFoundException();
        }
        return entityPlayerMP;
    }
    
    public static EntityPlayerMP getCommandSenderAsPlayer(final ICommandSender commandSender) throws PlayerNotFoundException {
        if (commandSender instanceof EntityPlayerMP) {
            return (EntityPlayerMP)commandSender;
        }
        throw new PlayerNotFoundException("You must specify which player you wish to perform this action on.", new Object[0]);
    }
    
    public static String joinNiceStringFromCollection(final Collection collection) {
        return joinNiceString(collection.toArray(new String[collection.size()]));
    }
    
    public static class CoordinateArg
    {
        private final boolean field_179632_c;
        private final double field_179633_a;
        private final double field_179631_b;
        
        protected CoordinateArg(final double field_179633_a, final double field_179631_b, final boolean field_179632_c) {
            this.field_179633_a = field_179633_a;
            this.field_179631_b = field_179631_b;
            this.field_179632_c = field_179632_c;
        }
        
        public double func_179628_a() {
            return this.field_179633_a;
        }
        
        public double func_179629_b() {
            return this.field_179631_b;
        }
        
        public boolean func_179630_c() {
            return this.field_179632_c;
        }
    }
}
