package net.minecraft.command;

public class CommandException extends Exception
{
    private final Object[] errorObjects;
    
    public CommandException(final String s, final Object... errorObjects) {
        super(s);
        this.errorObjects = errorObjects;
    }
    
    public Object[] getErrorObjects() {
        return this.errorObjects;
    }
}
