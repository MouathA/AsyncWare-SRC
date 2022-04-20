package net.minecraft.command;

import java.util.regex.*;
import com.google.common.base.*;
import net.minecraft.util.*;
import net.minecraft.server.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.entity.*;
import net.minecraft.scoreboard.*;

public class PlayerSelector
{
    private static final Set WORLD_BINDING_ARGS;
    private static final Pattern tokenPattern;
    private static final Pattern keyValueListPattern;
    private static final Pattern intListPattern;
    
    public static boolean matchesMultiplePlayers(final String s) {
        final Matcher matcher = PlayerSelector.tokenPattern.matcher(s);
        if (!matcher.matches()) {
            return false;
        }
        final Map argumentMap = getArgumentMap(matcher.group(2));
        final String group = matcher.group(1);
        return parseIntWithDefault(argumentMap, "c", (!"a".equals(group) && !"e".equals(group)) ? 1 : 0) != 1;
    }
    
    private static List filterResults(final Map map, final Class clazz, final List list, final String s, final World world, final BlockPos blockPos) {
        final ArrayList arrayList = Lists.newArrayList();
        final String func_179651_b = func_179651_b(map, "type");
        final String s2 = (func_179651_b != null && func_179651_b.startsWith("!")) ? func_179651_b.substring(1) : func_179651_b;
        final boolean b = !s.equals("e");
        final boolean b2 = s.equals("r") && s2 != null;
        final int intWithDefault = parseIntWithDefault(map, "dx", 0);
        final int intWithDefault2 = parseIntWithDefault(map, "dy", 0);
        final int intWithDefault3 = parseIntWithDefault(map, "dz", 0);
        final int intWithDefault4 = parseIntWithDefault(map, "r", -1);
        final Predicate and = Predicates.and((Iterable)list);
        final Predicate and2 = Predicates.and(EntitySelectors.selectAnything, and);
        if (blockPos != null) {
            final boolean b3 = world.playerEntities.size() < world.loadedEntityList.size() / 16;
            if (!map.containsKey("dx") && !map.containsKey("dy") && !map.containsKey("dz")) {
                if (intWithDefault4 >= 0) {
                    final AxisAlignedBB axisAlignedBB = new AxisAlignedBB(blockPos.getX() - intWithDefault4, blockPos.getY() - intWithDefault4, blockPos.getZ() - intWithDefault4, blockPos.getX() + intWithDefault4 + 1, blockPos.getY() + intWithDefault4 + 1, blockPos.getZ() + intWithDefault4 + 1);
                    if (b && b3 && !b2) {
                        arrayList.addAll(world.getPlayers(clazz, and2));
                    }
                    else {
                        arrayList.addAll(world.getEntitiesWithinAABB(clazz, axisAlignedBB, and2));
                    }
                }
                else if (s.equals("a")) {
                    arrayList.addAll(world.getPlayers(clazz, and));
                }
                else if (!s.equals("p") && (!s.equals("r") || b2)) {
                    arrayList.addAll(world.getEntities(clazz, and2));
                }
                else {
                    arrayList.addAll(world.getPlayers(clazz, and2));
                }
            }
            else {
                final AxisAlignedBB func_179661_a = func_179661_a(blockPos, intWithDefault, intWithDefault2, intWithDefault3);
                if (b && b3 && !b2) {
                    arrayList.addAll(world.getPlayers(clazz, Predicates.and(and2, (Predicate)new Predicate(func_179661_a) {
                        final AxisAlignedBB val$axisalignedbb;
                        
                        public boolean apply(final Object o) {
                            return this.apply((Entity)o);
                        }
                        
                        public boolean apply(final Entity entity) {
                            return entity.posX >= this.val$axisalignedbb.minX && entity.posY >= this.val$axisalignedbb.minY && entity.posZ >= this.val$axisalignedbb.minZ && (entity.posX < this.val$axisalignedbb.maxX && entity.posY < this.val$axisalignedbb.maxY && entity.posZ < this.val$axisalignedbb.maxZ);
                        }
                    })));
                }
                else {
                    arrayList.addAll(world.getEntitiesWithinAABB(clazz, func_179661_a, and2));
                }
            }
        }
        else if (s.equals("a")) {
            arrayList.addAll(world.getPlayers(clazz, and));
        }
        else if (!s.equals("p") && (!s.equals("r") || b2)) {
            arrayList.addAll(world.getEntities(clazz, and2));
        }
        else {
            arrayList.addAll(world.getPlayers(clazz, and2));
        }
        return arrayList;
    }
    
    static {
        tokenPattern = Pattern.compile("^@([pare])(?:\\[([\\w=,!-]*)\\])?$");
        intListPattern = Pattern.compile("\\G([-!]?[\\w-]*)(?:$|,)");
        keyValueListPattern = Pattern.compile("\\G(\\w+)=([-!]?[\\w-]*)(?:$|,)");
        WORLD_BINDING_ARGS = Sets.newHashSet((Object[])new String[] { "x", "y", "z", "dx", "dy", "dz", "rm", "r" });
    }
    
    public static Map func_96560_a(final Map map) {
        final HashMap hashMap = Maps.newHashMap();
        for (final String s : map.keySet()) {
            if (s.startsWith("score_") && s.length() > 6) {
                hashMap.put(s.substring(6), MathHelper.parseIntWithDefault((String)map.get(s), 1));
            }
        }
        return hashMap;
    }
    
    public static IChatComponent matchEntitiesToChatComponent(final ICommandSender commandSender, final String s) {
        final List matchEntities = matchEntities(commandSender, s, Entity.class);
        if (matchEntities.isEmpty()) {
            return null;
        }
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<Entity> iterator = matchEntities.iterator();
        while (iterator.hasNext()) {
            arrayList.add(iterator.next().getDisplayName());
        }
        return CommandBase.join(arrayList);
    }
    
    private static String func_179651_b(final Map map, final String s) {
        return map.get(s);
    }
    
    private static List func_179657_e(final Map map) {
        final ArrayList arrayList = Lists.newArrayList();
        final Map func_96560_a = func_96560_a(map);
        if (func_96560_a != null && func_96560_a.size() > 0) {
            arrayList.add(new Predicate(func_96560_a) {
                final Map val$map;
                
                public boolean apply(final Entity entity) {
                    final Scoreboard scoreboard = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
                    for (final Map.Entry<String, V> entry : this.val$map.entrySet()) {
                        String substring = entry.getKey();
                        if (substring.endsWith("_min") && substring.length() > 4) {
                            substring = substring.substring(0, substring.length() - 4);
                        }
                        final ScoreObjective objective = scoreboard.getObjective(substring);
                        if (objective == null) {
                            return false;
                        }
                        final String s = (entity instanceof EntityPlayerMP) ? entity.getName() : entity.getUniqueID().toString();
                        if (!scoreboard.entityHasObjective(s, objective)) {
                            return false;
                        }
                        final int scorePoints = scoreboard.getValueFromObjective(s, objective).getScorePoints();
                        if (scorePoints < (int)entry.getValue()) {
                            return false;
                        }
                        if (scorePoints > (int)entry.getValue()) {
                            continue;
                        }
                    }
                    return true;
                }
                
                public boolean apply(final Object o) {
                    return this.apply((Entity)o);
                }
            });
        }
        return arrayList;
    }
    
    private static BlockPos func_179664_b(final Map map, final BlockPos blockPos) {
        return new BlockPos(parseIntWithDefault(map, "x", blockPos.getX()), parseIntWithDefault(map, "y", blockPos.getY()), parseIntWithDefault(map, "z", blockPos.getZ()));
    }
    
    private static List getWorlds(final ICommandSender commandSender, final Map map) {
        final ArrayList arrayList = Lists.newArrayList();
        if (map != 0) {
            arrayList.add(commandSender.getEntityWorld());
        }
        else {
            Collections.addAll(arrayList, MinecraftServer.getServer().worldServers);
        }
        return arrayList;
    }
    
    private static int parseIntWithDefault(final Map map, final String s, final int n) {
        return map.containsKey(s) ? MathHelper.parseIntWithDefault(map.get(s), n) : n;
    }
    
    private static List func_179649_c(final Map map) {
        final ArrayList arrayList = Lists.newArrayList();
        final int intWithDefault = parseIntWithDefault(map, "m", WorldSettings.GameType.NOT_SET.getID());
        if (intWithDefault != WorldSettings.GameType.NOT_SET.getID()) {
            arrayList.add(new Predicate(intWithDefault) {
                final int val$i;
                
                public boolean apply(final Entity entity) {
                    return entity instanceof EntityPlayerMP && ((EntityPlayerMP)entity).theItemInWorldManager.getGameType().getID() == this.val$i;
                }
                
                public boolean apply(final Object o) {
                    return this.apply((Entity)o);
                }
            });
        }
        return arrayList;
    }
    
    public static boolean hasArguments(final String s) {
        return PlayerSelector.tokenPattern.matcher(s).matches();
    }
    
    public static EntityPlayerMP matchOnePlayer(final ICommandSender commandSender, final String s) {
        return (EntityPlayerMP)matchOneEntity(commandSender, s, EntityPlayerMP.class);
    }
    
    private static List func_179662_g(final Map map) {
        final ArrayList arrayList = Lists.newArrayList();
        if (map.containsKey("rym") || map.containsKey("ry")) {
            arrayList.add(new Predicate(func_179650_a(parseIntWithDefault(map, "rym", 0)), func_179650_a(parseIntWithDefault(map, "ry", 359))) {
                final int val$j;
                final int val$i;
                
                public boolean apply(final Object o) {
                    return this.apply((Entity)o);
                }
                
                public boolean apply(final Entity entity) {
                    final int func_179650_a = PlayerSelector.func_179650_a((int)Math.floor(entity.rotationYaw));
                    return (this.val$i > this.val$j) ? (func_179650_a >= this.val$i || func_179650_a <= this.val$j) : (func_179650_a >= this.val$i && func_179650_a <= this.val$j);
                }
            });
        }
        if (map.containsKey("rxm") || map.containsKey("rx")) {
            arrayList.add(new Predicate(func_179650_a(parseIntWithDefault(map, "rxm", 0)), func_179650_a(parseIntWithDefault(map, "rx", 359))) {
                final int val$l;
                final int val$k;
                
                public boolean apply(final Entity entity) {
                    final int func_179650_a = PlayerSelector.func_179650_a((int)Math.floor(entity.rotationPitch));
                    return (this.val$k > this.val$l) ? (func_179650_a >= this.val$k || func_179650_a <= this.val$l) : (func_179650_a >= this.val$k && func_179650_a <= this.val$l);
                }
                
                public boolean apply(final Object o) {
                    return this.apply((Entity)o);
                }
            });
        }
        return arrayList;
    }
    
    public static int func_179650_a(int n) {
        n %= 360;
        if (n >= 160) {
            n -= 360;
        }
        if (n < 0) {
            n += 360;
        }
        return n;
    }
    
    private static AxisAlignedBB func_179661_a(final BlockPos blockPos, final int n, final int n2, final int n3) {
        final boolean b = n < 0;
        final boolean b2 = n2 < 0;
        final boolean b3 = n3 < 0;
        return new AxisAlignedBB(blockPos.getX() + (b ? n : 0), blockPos.getY() + (b2 ? n2 : 0), blockPos.getZ() + (b3 ? n3 : 0), blockPos.getX() + (b ? 0 : n) + 1, blockPos.getY() + (b2 ? 0 : n2) + 1, blockPos.getZ() + (b3 ? 0 : n3) + 1);
    }
    
    private static Map getArgumentMap(final String s) {
        final HashMap hashMap = Maps.newHashMap();
        if (s == null) {
            return hashMap;
        }
        final Matcher matcher = PlayerSelector.intListPattern.matcher(s);
        while (matcher.find()) {
            int n = 0;
            ++n;
            final String s2 = "x";
            if (s2 != null && matcher.group(1).length() > 0) {
                hashMap.put(s2, matcher.group(1));
            }
            matcher.end();
        }
        if (-1 < s.length()) {
            final Matcher matcher2 = PlayerSelector.keyValueListPattern.matcher(s);
            while (matcher2.find()) {
                hashMap.put(matcher2.group(1), matcher2.group(2));
            }
        }
        return hashMap;
    }
    
    private static List func_179647_f(final Map map) {
        final ArrayList arrayList = Lists.newArrayList();
        String s = func_179651_b(map, "name");
        final boolean b = s != null && s.startsWith("!");
        if (b) {
            s = s.substring(1);
        }
        if (s != null) {
            arrayList.add(new Predicate(s, b) {
                final String val$s_f;
                final boolean val$flag;
                
                public boolean apply(final Object o) {
                    return this.apply((Entity)o);
                }
                
                public boolean apply(final Entity entity) {
                    return entity.getName().equals(this.val$s_f) != this.val$flag;
                }
            });
        }
        return arrayList;
    }
    
    private static List func_179663_a(final Map map, final String s) {
        final ArrayList arrayList = Lists.newArrayList();
        String s2 = func_179651_b(map, "type");
        final boolean b = s2 != null && s2.startsWith("!");
        if (b) {
            s2 = s2.substring(1);
        }
        final boolean b2 = !s.equals("e");
        final boolean b3 = s.equals("r") && s2 != null;
        if ((s2 == null || !s.equals("e")) && !b3) {
            if (b2) {
                arrayList.add(new Predicate() {
                    public boolean apply(final Object o) {
                        return this.apply((Entity)o);
                    }
                    
                    public boolean apply(final Entity entity) {
                        return entity instanceof EntityPlayer;
                    }
                });
            }
        }
        else {
            arrayList.add(new Predicate(s2, b) {
                final boolean val$flag;
                final String val$s_f;
                
                public boolean apply(final Entity entity) {
                    return EntityList.isStringEntityName(entity, this.val$s_f) != this.val$flag;
                }
                
                public boolean apply(final Object o) {
                    return this.apply((Entity)o);
                }
            });
        }
        return arrayList;
    }
    
    public static List matchEntities(final ICommandSender commandSender, final String s, final Class clazz) {
        final Matcher matcher = PlayerSelector.tokenPattern.matcher(s);
        if (!matcher.matches() || !commandSender.canCommandSenderUseCommand(1, "@")) {
            return Collections.emptyList();
        }
        final Map argumentMap = getArgumentMap(matcher.group(2));
        if (argumentMap != null) {
            return Collections.emptyList();
        }
        final String group = matcher.group(1);
        final BlockPos func_179664_b = func_179664_b(argumentMap, commandSender.getPosition());
        final List worlds = getWorlds(commandSender, argumentMap);
        final ArrayList arrayList = Lists.newArrayList();
        for (final World world : worlds) {
            if (world != null) {
                final ArrayList arrayList2 = Lists.newArrayList();
                arrayList2.addAll(func_179663_a(argumentMap, group));
                arrayList2.addAll(func_179648_b(argumentMap));
                arrayList2.addAll(func_179649_c(argumentMap));
                arrayList2.addAll(func_179659_d(argumentMap));
                arrayList2.addAll(func_179657_e(argumentMap));
                arrayList2.addAll(func_179647_f(argumentMap));
                arrayList2.addAll(func_180698_a(argumentMap, func_179664_b));
                arrayList2.addAll(func_179662_g(argumentMap));
                arrayList.addAll(filterResults(argumentMap, clazz, arrayList2, group, world, func_179664_b));
            }
        }
        return func_179658_a(arrayList, argumentMap, commandSender, clazz, group, func_179664_b);
    }
    
    private static List func_179658_a(List list, final Map map, final ICommandSender commandSender, final Class clazz, final String s, final BlockPos blockPos) {
        final int intWithDefault = parseIntWithDefault(map, "c", (!s.equals("a") && !s.equals("e")) ? 1 : 0);
        if (!s.equals("p") && !s.equals("a") && !s.equals("e")) {
            if (s.equals("r")) {
                Collections.shuffle(list);
            }
        }
        else if (blockPos != null) {
            Collections.sort(list, new Comparator(blockPos) {
                final BlockPos val$p_179658_5_;
                
                @Override
                public int compare(final Object o, final Object o2) {
                    return this.compare((Entity)o, (Entity)o2);
                }
                
                public int compare(final Entity entity, final Entity entity2) {
                    return ComparisonChain.start().compare(entity.getDistanceSq(this.val$p_179658_5_), entity2.getDistanceSq(this.val$p_179658_5_)).result();
                }
            });
        }
        final Entity commandSenderEntity = commandSender.getCommandSenderEntity();
        if (commandSenderEntity != null && clazz.isAssignableFrom(commandSenderEntity.getClass()) && intWithDefault == 1 && list.contains(commandSenderEntity) && !"r".equals(s)) {
            list = (List<Object>)Lists.newArrayList((Object[])new Entity[] { commandSenderEntity });
        }
        if (intWithDefault != 0) {
            if (intWithDefault < 0) {
                Collections.reverse(list);
            }
            list = list.subList(0, Math.min(Math.abs(intWithDefault), list.size()));
        }
        return list;
    }
    
    private static List func_179659_d(final Map map) {
        final ArrayList arrayList = Lists.newArrayList();
        String s = func_179651_b(map, "team");
        final boolean b = s != null && s.startsWith("!");
        if (b) {
            s = s.substring(1);
        }
        if (s != null) {
            arrayList.add(new Predicate(s, b) {
                final boolean val$flag;
                final String val$s_f;
                
                public boolean apply(final Object o) {
                    return this.apply((Entity)o);
                }
                
                public boolean apply(final Entity entity) {
                    if (!(entity instanceof EntityLivingBase)) {
                        return false;
                    }
                    final Team team = ((EntityLivingBase)entity).getTeam();
                    return ((team == null) ? "" : team.getRegisteredName()).equals(this.val$s_f) != this.val$flag;
                }
            });
        }
        return arrayList;
    }
    
    private static List func_179648_b(final Map map) {
        final ArrayList arrayList = Lists.newArrayList();
        final int intWithDefault = parseIntWithDefault(map, "lm", -1);
        final int intWithDefault2 = parseIntWithDefault(map, "l", -1);
        if (intWithDefault > -1 || intWithDefault2 > -1) {
            arrayList.add(new Predicate(intWithDefault, intWithDefault2) {
                final int val$i;
                final int val$j;
                
                public boolean apply(final Object o) {
                    return this.apply((Entity)o);
                }
                
                public boolean apply(final Entity entity) {
                    if (!(entity instanceof EntityPlayerMP)) {
                        return false;
                    }
                    final EntityPlayerMP entityPlayerMP = (EntityPlayerMP)entity;
                    return (this.val$i <= -1 || entityPlayerMP.experienceLevel >= this.val$i) && (this.val$j <= -1 || entityPlayerMP.experienceLevel <= this.val$j);
                }
            });
        }
        return arrayList;
    }
    
    public static Entity matchOneEntity(final ICommandSender commandSender, final String s, final Class clazz) {
        final List matchEntities = matchEntities(commandSender, s, clazz);
        return (matchEntities.size() == 1) ? matchEntities.get(0) : null;
    }
    
    private static List func_180698_a(final Map map, final BlockPos blockPos) {
        final ArrayList arrayList = Lists.newArrayList();
        final int intWithDefault = parseIntWithDefault(map, "rm", -1);
        final int intWithDefault2 = parseIntWithDefault(map, "r", -1);
        if (blockPos != null && (intWithDefault >= 0 || intWithDefault2 >= 0)) {
            arrayList.add(new Predicate(blockPos, intWithDefault, intWithDefault * intWithDefault, intWithDefault2, intWithDefault2 * intWithDefault2) {
                final int val$i;
                final int val$j;
                final int val$k;
                final BlockPos val$p_180698_1_;
                final int val$l;
                
                public boolean apply(final Object o) {
                    return this.apply((Entity)o);
                }
                
                public boolean apply(final Entity entity) {
                    final int n = (int)entity.getDistanceSqToCenter(this.val$p_180698_1_);
                    return (this.val$i < 0 || n >= this.val$k) && (this.val$j < 0 || n <= this.val$l);
                }
            });
        }
        return arrayList;
    }
}
