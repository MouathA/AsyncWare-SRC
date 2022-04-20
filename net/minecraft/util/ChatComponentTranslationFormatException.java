package net.minecraft.util;

public class ChatComponentTranslationFormatException extends IllegalArgumentException
{
    public ChatComponentTranslationFormatException(final ChatComponentTranslation chatComponentTranslation, final Throwable t) {
        super(String.format("Error while parsing: %s", chatComponentTranslation), t);
    }
    
    public ChatComponentTranslationFormatException(final ChatComponentTranslation chatComponentTranslation, final String s) {
        super(String.format("Error parsing: %s: %s", chatComponentTranslation, s));
    }
    
    public ChatComponentTranslationFormatException(final ChatComponentTranslation chatComponentTranslation, final int n) {
        super(String.format("Invalid index %d requested for %s", n, chatComponentTranslation));
    }
}
