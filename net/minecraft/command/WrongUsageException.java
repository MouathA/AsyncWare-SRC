package net.minecraft.command;

public class WrongUsageException extends SyntaxErrorException
{
    public WrongUsageException(final String s, final Object... array) {
        super(s, array);
    }
}
