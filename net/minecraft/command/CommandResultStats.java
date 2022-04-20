package net.minecraft.command;

import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.scoreboard.*;
import net.minecraft.nbt.*;

public class CommandResultStats
{
    private static final int NUM_RESULT_TYPES;
    private String[] field_179675_c;
    private String[] field_179673_d;
    
    public CommandResultStats() {
        this.field_179675_c = CommandResultStats.STRING_RESULT_TYPES;
        this.field_179673_d = CommandResultStats.STRING_RESULT_TYPES;
    }
    
    public void readStatsFromNBT(final NBTTagCompound nbtTagCompound) {
        if (nbtTagCompound.hasKey("CommandStats", 10)) {
            final NBTTagCompound compoundTag = nbtTagCompound.getCompoundTag("CommandStats");
            final Type[] values = Type.values();
            while (0 < values.length) {
                final Type type = values[0];
                final String string = type.getTypeName() + "Name";
                final String string2 = type.getTypeName() + "Objective";
                if (compoundTag.hasKey(string, 8) && compoundTag.hasKey(string2, 8)) {
                    func_179667_a(this, type, compoundTag.getString(string), compoundTag.getString(string2));
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    public void func_179672_a(final ICommandSender commandSender, final Type type, final int scorePoints) {
        final String s = this.field_179675_c[type.getTypeID()];
        if (s != null) {
            final String entityName = CommandBase.getEntityName(new ICommandSender(this, commandSender) {
                final ICommandSender val$sender;
                final CommandResultStats this$0;
                
                @Override
                public Vec3 getPositionVector() {
                    return this.val$sender.getPositionVector();
                }
                
                @Override
                public BlockPos getPosition() {
                    return this.val$sender.getPosition();
                }
                
                @Override
                public String getName() {
                    return this.val$sender.getName();
                }
                
                @Override
                public boolean sendCommandFeedback() {
                    return this.val$sender.sendCommandFeedback();
                }
                
                @Override
                public void addChatMessage(final IChatComponent chatComponent) {
                    this.val$sender.addChatMessage(chatComponent);
                }
                
                @Override
                public Entity getCommandSenderEntity() {
                    return this.val$sender.getCommandSenderEntity();
                }
                
                @Override
                public boolean canCommandSenderUseCommand(final int n, final String s) {
                    return true;
                }
                
                @Override
                public World getEntityWorld() {
                    return this.val$sender.getEntityWorld();
                }
                
                @Override
                public IChatComponent getDisplayName() {
                    return this.val$sender.getDisplayName();
                }
                
                @Override
                public void setCommandStat(final Type type, final int n) {
                    this.val$sender.setCommandStat(type, n);
                }
            }, s);
            final String s2 = this.field_179673_d[type.getTypeID()];
            if (s2 != null) {
                final Scoreboard scoreboard = commandSender.getEntityWorld().getScoreboard();
                final ScoreObjective objective = scoreboard.getObjective(s2);
                if (objective != null && scoreboard.entityHasObjective(entityName, objective)) {
                    scoreboard.getValueFromObjective(entityName, objective).setScorePoints(scorePoints);
                }
            }
        }
    }
    
    static {
        NUM_RESULT_TYPES = Type.values().length;
        CommandResultStats.STRING_RESULT_TYPES = new String[CommandResultStats.NUM_RESULT_TYPES];
    }
    
    private static void func_179669_a(final CommandResultStats commandResultStats, final Type type) {
        if (commandResultStats.field_179675_c != CommandResultStats.STRING_RESULT_TYPES && commandResultStats.field_179673_d != CommandResultStats.STRING_RESULT_TYPES) {
            commandResultStats.field_179675_c[type.getTypeID()] = null;
            commandResultStats.field_179673_d[type.getTypeID()] = null;
            final Type[] values = Type.values();
            while (0 < values.length) {
                final Type type2 = values[0];
                if (commandResultStats.field_179675_c[type2.getTypeID()] != null && commandResultStats.field_179673_d[type2.getTypeID()] != null) {
                    break;
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    public void writeStatsToNBT(final NBTTagCompound nbtTagCompound) {
        final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
        final Type[] values = Type.values();
        while (0 < values.length) {
            final Type type = values[0];
            final String s = this.field_179675_c[type.getTypeID()];
            final String s2 = this.field_179673_d[type.getTypeID()];
            if (s != null && s2 != null) {
                nbtTagCompound2.setString(type.getTypeName() + "Name", s);
                nbtTagCompound2.setString(type.getTypeName() + "Objective", s2);
            }
            int n = 0;
            ++n;
        }
        if (!nbtTagCompound2.hasNoTags()) {
            nbtTagCompound.setTag("CommandStats", nbtTagCompound2);
        }
    }
    
    public void func_179671_a(final CommandResultStats commandResultStats) {
        final Type[] values = Type.values();
        while (0 < values.length) {
            final Type type = values[0];
            func_179667_a(this, type, commandResultStats.field_179675_c[type.getTypeID()], commandResultStats.field_179673_d[type.getTypeID()]);
            int n = 0;
            ++n;
        }
    }
    
    public static void func_179667_a(final CommandResultStats commandResultStats, final Type type, final String s, final String s2) {
        if (s != null && s.length() != 0 && s2 != null && s2.length() != 0) {
            if (commandResultStats.field_179675_c == CommandResultStats.STRING_RESULT_TYPES || commandResultStats.field_179673_d == CommandResultStats.STRING_RESULT_TYPES) {
                commandResultStats.field_179675_c = new String[CommandResultStats.NUM_RESULT_TYPES];
                commandResultStats.field_179673_d = new String[CommandResultStats.NUM_RESULT_TYPES];
            }
            commandResultStats.field_179675_c[type.getTypeID()] = s;
            commandResultStats.field_179673_d[type.getTypeID()] = s2;
        }
        else {
            func_179669_a(commandResultStats, type);
        }
    }
    
    public enum Type
    {
        private static final Type[] $VALUES;
        
        AFFECTED_ITEMS("AFFECTED_ITEMS", 3, 3, "AffectedItems"), 
        QUERY_RESULT("QUERY_RESULT", 4, 4, "QueryResult");
        
        final int typeID;
        
        AFFECTED_ENTITIES("AFFECTED_ENTITIES", 2, 2, "AffectedEntities"), 
        AFFECTED_BLOCKS("AFFECTED_BLOCKS", 1, 1, "AffectedBlocks");
        
        final String typeName;
        
        SUCCESS_COUNT("SUCCESS_COUNT", 0, 0, "SuccessCount");
        
        static {
            $VALUES = new Type[] { Type.SUCCESS_COUNT, Type.AFFECTED_BLOCKS, Type.AFFECTED_ENTITIES, Type.AFFECTED_ITEMS, Type.QUERY_RESULT };
        }
        
        public String getTypeName() {
            return this.typeName;
        }
        
        public int getTypeID() {
            return this.typeID;
        }
        
        public static String[] getTypeNames() {
            final String[] array = new String[values().length];
            final Type[] values = values();
            while (0 < values.length) {
                final Type type = values[0];
                final String[] array2 = array;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array2[n] = type.getTypeName();
                int n3 = 0;
                ++n3;
            }
            return array;
        }
        
        private Type(final String s, final int n, final int typeID, final String typeName) {
            this.typeID = typeID;
            this.typeName = typeName;
        }
        
        public static Type getTypeByName(final String s) {
            final Type[] values = values();
            while (0 < values.length) {
                final Type type = values[0];
                if (type.getTypeName().equals(s)) {
                    return type;
                }
                int n = 0;
                ++n;
            }
            return null;
        }
    }
}
