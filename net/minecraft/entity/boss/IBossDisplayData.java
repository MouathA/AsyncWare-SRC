package net.minecraft.entity.boss;

import net.minecraft.util.*;

public interface IBossDisplayData
{
    IChatComponent getDisplayName();
    
    float getHealth();
    
    float getMaxHealth();
}
