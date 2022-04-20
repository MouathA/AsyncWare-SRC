package net.minecraft.client.renderer.chunk;

import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import com.google.common.collect.*;

public class CompiledChunk
{
    private boolean empty;
    private final List tileEntities;
    private SetVisibility setVisibility;
    private final boolean[] layersStarted;
    private final boolean[] layersUsed;
    public static final CompiledChunk DUMMY;
    private WorldRenderer.State state;
    
    public void setVisibility(final SetVisibility setVisibility) {
        this.setVisibility = setVisibility;
    }
    
    public void addTileEntity(final TileEntity tileEntity) {
        this.tileEntities.add(tileEntity);
    }
    
    public boolean isLayerEmpty(final EnumWorldBlockLayer enumWorldBlockLayer) {
        return !this.layersUsed[enumWorldBlockLayer.ordinal()];
    }
    
    protected void setLayerUsed(final EnumWorldBlockLayer enumWorldBlockLayer) {
        this.empty = false;
        this.layersUsed[enumWorldBlockLayer.ordinal()] = true;
    }
    
    public boolean isLayerStarted(final EnumWorldBlockLayer enumWorldBlockLayer) {
        return this.layersStarted[enumWorldBlockLayer.ordinal()];
    }
    
    static {
        DUMMY = new CompiledChunk() {
            @Override
            protected void setLayerUsed(final EnumWorldBlockLayer enumWorldBlockLayer) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public boolean isVisible(final EnumFacing enumFacing, final EnumFacing enumFacing2) {
                return false;
            }
            
            @Override
            public void setLayerStarted(final EnumWorldBlockLayer enumWorldBlockLayer) {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    public WorldRenderer.State getState() {
        return this.state;
    }
    
    public CompiledChunk() {
        this.layersUsed = new boolean[EnumWorldBlockLayer.values().length];
        this.layersStarted = new boolean[EnumWorldBlockLayer.values().length];
        this.empty = true;
        this.tileEntities = Lists.newArrayList();
        this.setVisibility = new SetVisibility();
    }
    
    public List getTileEntities() {
        return this.tileEntities;
    }
    
    public void setState(final WorldRenderer.State state) {
        this.state = state;
    }
    
    public void setLayerStarted(final EnumWorldBlockLayer enumWorldBlockLayer) {
        this.layersStarted[enumWorldBlockLayer.ordinal()] = true;
    }
    
    public boolean isVisible(final EnumFacing enumFacing, final EnumFacing enumFacing2) {
        return this.setVisibility.isVisible(enumFacing, enumFacing2);
    }
    
    public boolean isEmpty() {
        return this.empty;
    }
}
