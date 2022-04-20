package net.minecraft.command;

public class NumberInvalidException extends CommandException
{
    public NumberInvalidException(final String s, final Object... array) {
        super(s, array);
    }
    
    public NumberInvalidException() {
        this("commands.generic.num.invalid", new Object[0]);
    }
}
