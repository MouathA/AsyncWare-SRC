package net.minecraft.client.renderer.block.statemap;

import net.minecraft.block.state.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.block.*;
import com.google.common.collect.*;
import net.minecraft.block.properties.*;
import java.util.*;

public abstract class StateMapperBase implements IStateMapper
{
    protected Map mapStateModelLocations;
    
    protected abstract ModelResourceLocation getModelResourceLocation(final IBlockState p0);
    
    public StateMapperBase() {
        this.mapStateModelLocations = Maps.newLinkedHashMap();
    }
    
    @Override
    public Map putStateModelLocations(final Block block) {
        for (final IBlockState blockState : block.getBlockState().getValidStates()) {
            this.mapStateModelLocations.put(blockState, this.getModelResourceLocation(blockState));
        }
        return this.mapStateModelLocations;
    }
    
    public String getPropertyString(final Map map) {
        final StringBuilder sb = new StringBuilder();
        for (final Map.Entry<IProperty, V> entry : map.entrySet()) {
            if (sb.length() != 0) {
                sb.append(",");
            }
            final IProperty property = entry.getKey();
            final Comparable comparable = (Comparable)entry.getValue();
            sb.append(property.getName());
            sb.append("=");
            sb.append(property.getName(comparable));
        }
        if (sb.length() == 0) {
            sb.append("normal");
        }
        return sb.toString();
    }
}
