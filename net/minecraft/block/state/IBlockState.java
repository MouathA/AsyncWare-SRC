package net.minecraft.block.state;

import com.google.common.collect.*;
import java.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;

public interface IBlockState
{
    ImmutableMap getProperties();
    
    Collection getPropertyNames();
    
    IBlockState withProperty(final IProperty p0, final Comparable p1);
    
    Block getBlock();
    
    IBlockState cycleProperty(final IProperty p0);
    
    Comparable getValue(final IProperty p0);
}
