package com.nquantum.command;

import com.nquantum.event.impl.*;
import java.util.*;
import com.nquantum.command.impl.*;

public class CommandManager
{
    public String prefix;
    public List commands;
    
    public void handleChat(final EventChat eventChat) {
        final String message = eventChat.getMessage();
        if (message.startsWith(this.prefix)) {
            return;
        }
        eventChat.setCancelled(true);
        final String substring = message.substring(this.prefix.length());
        if (substring.split(" ")[0].length() > 0) {
            final String s = substring.split(" ")[0];
            for (final Command command : this.commands) {
                if (command.aliases.contains(s)) {
                    command.onCommand(Arrays.copyOfRange(substring.split(" "), 1, substring.split(" ").length), substring);
                }
            }
        }
    }
    
    public CommandManager() {
        this.commands = new ArrayList();
        this.prefix = ".";
        this.setup();
    }
    
    public void registerCommands() {
        this.registerCommand(new Toggle());
    }
    
    public void registerCommand(final Command command) {
        this.commands.add(command);
    }
    
    public void setup() {
        this.registerCommands();
    }
}
