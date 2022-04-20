package net.minecraft.potion;

import net.minecraft.util.*;

public class PotionHealth extends Potion
{
    @Override
    public boolean isInstant() {
        return true;
    }
    
    public PotionHealth(final int n, final ResourceLocation resourceLocation, final boolean b, final int n2) {
        super(n, resourceLocation, b, n2);
    }
    
    @Override
    public boolean isReady(final int n, final int n2) {
        return n >= 1;
    }
}
