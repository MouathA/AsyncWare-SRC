package net.minecraft.command;

public class CommandNotFoundException extends CommandException
{
    public CommandNotFoundException(final String s, final Object... array) {
        super(s, array);
    }
    
    public CommandNotFoundException() {
        this("commands.generic.notFound", new Object[0]);
    }
}
