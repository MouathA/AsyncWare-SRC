package net.minecraft.command;

public class SyntaxErrorException extends CommandException
{
    public SyntaxErrorException(final String s, final Object... array) {
        super(s, array);
    }
    
    public SyntaxErrorException() {
        this("commands.generic.snytax", new Object[0]);
    }
}
