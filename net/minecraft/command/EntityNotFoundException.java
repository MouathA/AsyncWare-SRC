package net.minecraft.command;

public class EntityNotFoundException extends CommandException
{
    public EntityNotFoundException(final String s, final Object... array) {
        super(s, array);
    }
    
    public EntityNotFoundException() {
        this("commands.generic.entity.notFound", new Object[0]);
    }
}
