package net.minecraft.client.audio;

import net.minecraft.util.*;

public interface ISound
{
    AttenuationType getAttenuationType();
    
    float getVolume();
    
    float getYPosF();
    
    float getZPosF();
    
    float getXPosF();
    
    boolean canRepeat();
    
    float getPitch();
    
    int getRepeatDelay();
    
    ResourceLocation getSoundLocation();
    
    public enum AttenuationType
    {
        private static final AttenuationType[] $VALUES;
        
        LINEAR("LINEAR", 1, 2);
        
        private final int type;
        
        NONE("NONE", 0, 0);
        
        public int getTypeInt() {
            return this.type;
        }
        
        private AttenuationType(final String s, final int n, final int type) {
            this.type = type;
        }
        
        static {
            $VALUES = new AttenuationType[] { AttenuationType.NONE, AttenuationType.LINEAR };
        }
    }
}
