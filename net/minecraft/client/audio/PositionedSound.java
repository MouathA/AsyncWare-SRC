package net.minecraft.client.audio;

import net.minecraft.util.*;

public abstract class PositionedSound implements ISound
{
    protected AttenuationType attenuationType;
    protected float zPosF;
    protected boolean repeat;
    protected float yPosF;
    protected float volume;
    protected int repeatDelay;
    protected float xPosF;
    protected float pitch;
    protected final ResourceLocation positionedSoundLocation;
    
    @Override
    public boolean canRepeat() {
        return this.repeat;
    }
    
    @Override
    public AttenuationType getAttenuationType() {
        return this.attenuationType;
    }
    
    @Override
    public float getPitch() {
        return this.pitch;
    }
    
    @Override
    public float getVolume() {
        return this.volume;
    }
    
    @Override
    public float getYPosF() {
        return this.yPosF;
    }
    
    @Override
    public float getZPosF() {
        return this.zPosF;
    }
    
    protected PositionedSound(final ResourceLocation positionedSoundLocation) {
        this.volume = 1.0f;
        this.pitch = 1.0f;
        this.repeat = false;
        this.repeatDelay = 0;
        this.attenuationType = AttenuationType.LINEAR;
        this.positionedSoundLocation = positionedSoundLocation;
    }
    
    @Override
    public float getXPosF() {
        return this.xPosF;
    }
    
    @Override
    public ResourceLocation getSoundLocation() {
        return this.positionedSoundLocation;
    }
    
    @Override
    public int getRepeatDelay() {
        return this.repeatDelay;
    }
}
