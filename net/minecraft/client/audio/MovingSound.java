package net.minecraft.client.audio;

import net.minecraft.util.*;

public abstract class MovingSound extends PositionedSound implements ITickableSound
{
    protected boolean donePlaying;
    
    @Override
    public boolean isDonePlaying() {
        return this.donePlaying;
    }
    
    protected MovingSound(final ResourceLocation resourceLocation) {
        super(resourceLocation);
        this.donePlaying = false;
    }
}
