package net.minecraft.world;

import net.minecraft.nbt.*;
import java.util.*;

public class GameRules
{
    private static final String __OBFID = "CL_00000136";
    private TreeMap theGameRules;
    
    public String[] getRules() {
        final Set keySet = this.theGameRules.keySet();
        return (String[])keySet.toArray(new String[keySet.size()]);
    }
    
    public void setOrCreateGameRule(final String s, final String value) {
        final Value value2 = this.theGameRules.get(s);
        if (value2 != null) {
            value2.setValue(value);
        }
        else {
            this.addGameRule(s, value, ValueType.ANY_VALUE);
        }
    }
    
    public boolean areSameType(final String s, final ValueType valueType) {
        final Value value = this.theGameRules.get(s);
        return value != null && (value.getType() == valueType || valueType == ValueType.ANY_VALUE);
    }
    
    public boolean hasRule(final String s) {
        return this.theGameRules.containsKey(s);
    }
    
    public boolean getBoolean(final String s) {
        final Value value = this.theGameRules.get(s);
        return value != null && value.getBoolean();
    }
    
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        for (final String s : nbtTagCompound.getKeySet()) {
            this.setOrCreateGameRule(s, nbtTagCompound.getString(s));
        }
    }
    
    public void addGameRule(final String s, final String s2, final ValueType valueType) {
        this.theGameRules.put(s, new Value(s2, valueType));
    }
    
    public String getString(final String s) {
        final Value value = this.theGameRules.get(s);
        return (value != null) ? value.getString() : "";
    }
    
    public GameRules() {
        this.theGameRules = new TreeMap();
        this.addGameRule("doFireTick", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("mobGriefing", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("keepInventory", "false", ValueType.BOOLEAN_VALUE);
        this.addGameRule("doMobSpawning", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("doMobLoot", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("doTileDrops", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("doEntityDrops", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("commandBlockOutput", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("naturalRegeneration", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("doDaylightCycle", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("logAdminCommands", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("showDeathMessages", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("randomTickSpeed", "3", ValueType.NUMERICAL_VALUE);
        this.addGameRule("sendCommandFeedback", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("reducedDebugInfo", "false", ValueType.BOOLEAN_VALUE);
    }
    
    public int getInt(final String s) {
        final Value value = this.theGameRules.get(s);
        return (value != null) ? value.getInt() : 0;
    }
    
    public NBTTagCompound writeToNBT() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        for (final String next : this.theGameRules.keySet()) {
            nbtTagCompound.setString(next, ((Value)this.theGameRules.get(next)).getString());
        }
        return nbtTagCompound;
    }
    
    static class Value
    {
        private boolean valueBoolean;
        private double valueDouble;
        private String valueString;
        private static final String __OBFID = "CL_00000137";
        private final ValueType type;
        private int valueInteger;
        
        public int getInt() {
            return this.valueInteger;
        }
        
        public ValueType getType() {
            return this.type;
        }
        
        public String getString() {
            return this.valueString;
        }
        
        public void setValue(final String valueString) {
            this.valueString = valueString;
            if (valueString != null) {
                if (valueString.equals("false")) {
                    this.valueBoolean = false;
                    return;
                }
                if (valueString.equals("true")) {
                    this.valueBoolean = true;
                    return;
                }
            }
            this.valueBoolean = Boolean.parseBoolean(valueString);
            this.valueInteger = (this.valueBoolean ? 1 : 0);
            this.valueInteger = Integer.parseInt(valueString);
            this.valueDouble = Double.parseDouble(valueString);
        }
        
        public Value(final String value, final ValueType type) {
            this.type = type;
            this.setValue(value);
        }
        
        public boolean getBoolean() {
            return this.valueBoolean;
        }
    }
    
    public enum ValueType
    {
        NUMERICAL_VALUE("NUMERICAL_VALUE", 2, "NUMERICAL_VALUE", 2), 
        BOOLEAN_VALUE("BOOLEAN_VALUE", 1, "BOOLEAN_VALUE", 1), 
        ANY_VALUE("ANY_VALUE", 0, "ANY_VALUE", 0);
        
        private static final String __OBFID = "CL_00002151";
        private static final ValueType[] $VALUES;
        private static final ValueType[] $VALUES$;
        
        private ValueType(final String s, final int n, final String s2, final int n2) {
        }
        
        static {
            $VALUES$ = new ValueType[] { ValueType.ANY_VALUE, ValueType.BOOLEAN_VALUE, ValueType.NUMERICAL_VALUE };
            $VALUES = new ValueType[] { ValueType.ANY_VALUE, ValueType.BOOLEAN_VALUE, ValueType.NUMERICAL_VALUE };
        }
    }
}
