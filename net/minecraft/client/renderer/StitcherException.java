package net.minecraft.client.renderer;

import net.minecraft.client.renderer.texture.*;

public class StitcherException extends RuntimeException
{
    private final Stitcher.Holder holder;
    
    public StitcherException(final Stitcher.Holder holder, final String s) {
        super(s);
        this.holder = holder;
    }
}
