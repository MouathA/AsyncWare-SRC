package net.minecraft.world;

import net.minecraft.util.*;

public interface IWorldNameable
{
    boolean hasCustomName();
    
    IChatComponent getDisplayName();
    
    String getName();
}
