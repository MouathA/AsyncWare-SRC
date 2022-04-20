package net.minecraft.client.audio;

import net.minecraft.util.*;
import java.util.*;
import com.google.common.collect.*;

public class SoundRegistry extends RegistrySimple
{
    private Map soundRegistry;
    
    public void registerSound(final SoundEventAccessorComposite soundEventAccessorComposite) {
        this.putObject(soundEventAccessorComposite.getSoundEventLocation(), soundEventAccessorComposite);
    }
    
    @Override
    protected Map createUnderlyingMap() {
        return this.soundRegistry = Maps.newHashMap();
    }
    
    public void clearMap() {
        this.soundRegistry.clear();
    }
}
