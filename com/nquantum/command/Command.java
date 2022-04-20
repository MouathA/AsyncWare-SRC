package com.nquantum.command;

import java.util.*;

public abstract class Command
{
    public String desc;
    public String syntax;
    public List aliases;
    public String name;
    
    public void setSyntax(final String syntax) {
        this.syntax = syntax;
    }
    
    public Command(final String name, final String desc, final String syntax, final List aliases) {
        this.aliases = new ArrayList();
        this.name = name;
        this.desc = desc;
        this.syntax = syntax;
        this.aliases = aliases;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getSyntax() {
        return this.syntax;
    }
    
    public void setAliases(final List aliases) {
        this.aliases = aliases;
    }
    
    public List getAliases() {
        return this.aliases;
    }
    
    public abstract void onCommand(final String[] p0, final String p1);
    
    public void setDesc(final String desc) {
        this.desc = desc;
    }
    
    public String getDesc() {
        return this.desc;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
}
